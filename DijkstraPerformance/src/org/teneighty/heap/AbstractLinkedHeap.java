/*
 * $Id$
 * 
 * Copyright (c) 2005-2013 Fran Lattanzio
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.teneighty.heap;

import java.lang.ref.WeakReference;

/**
 * Abstract linked heap. This class provides extra functionality for
 * linked-based heap implementations, i.e. where the heap structure is
 * maintained by having nodes point to parent/child/sibling nodes (c.f. a
 * typical binary heap implementation that is backed by an array).
 * <p>
 * The main things here are:
 * <ol>
 * <li>The node class contains a weak back-reference to the containing heap.
 * This solves the <code>holdsEntry</code> problem - basically, examining the
 * value of this reference will tell you the holding heap. I used a weak
 * reference here so that the holding help can still be garbage collected even
 * if some of the entries are still strongly reachable. This is a semi-minor
 * hack in the grand scheme of things: Dijkstra wouldn't like this, but it gets
 * the job done fairly cheaply.</li>
 * <li>The node class (and compare method) can deal with "infinite" values:
 * Setting the <code>is_infinite</code> flag on the node automatically makes it
 * smaller than any (finite) node. Generally, there should not be more than one
 * infinite node in any heap. If there are two, it's a good indication of a
 * serious programming error or concurrent modification. This is also a
 * semi-minor hack, I think; could be better, but we've all seen much worse.</li>
 * </ol>
 * 
 * @param <TKey> the key type.
 * @param <TValue> the value type.
 * @author Fran Lattanzio
 * @version $Revision$ $Date$
 */
public abstract class AbstractLinkedHeap<TKey, TValue>
	extends AbstractHeap<TKey, TValue>
{

	/**
	 * Constructor.
	 * <p>
	 * Should be considered <code>private protected</code>. This constructor
	 * does nothing and is here only for access protection.
	 */
	protected AbstractLinkedHeap()
	{
		super();
	}

	/**
	 * @see org.teneighty.heap.AbstractHeap#compare(org.teneighty.heap.Heap.Entry, org.teneighty.heap.Heap.Entry)
	 */
	@Override
	protected int compare(final Entry<TKey, TValue> node1,
			final Entry<TKey, TValue> node2)
		throws ClassCastException, NullPointerException
	{
		AbstractLinkedHeapEntry<TKey, TValue> e1 = (AbstractLinkedHeapEntry<TKey, TValue>) node1;
		AbstractLinkedHeapEntry<TKey, TValue> e2 = (AbstractLinkedHeapEntry<TKey, TValue>) node2;

		if (e1.is_infinite && e2.is_infinite)
		{
			// Probably shouldn't happen. A good indication of concurrent
			// modification... figure out if we should just toss an exception
			// here.
			return 0;
		}
		else if (e1.is_infinite)
		{
			return -1;
		}
		else if (e2.is_infinite)
		{
			return 1;
		}
		else
		{
			return super.compare(node1, node2);
		}
	}

	/**
	 * Abstract linked heap entry.
	 * 
	 * @param <K> the key type.
	 * @param <V> the value type.
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	protected static abstract class AbstractLinkedHeapEntry<K, V>
		extends AbstractHeap.AbstractHeapEntry<K, V>
	{

		/**
		 * Infinite flag. Hack used for delete.
		 */
		protected transient volatile boolean is_infinite;

		/**
		 * Containing heap reference.
		 */
		private transient volatile HeapReference containing_ref;

		/**
		 * Constructor.
		 * <p>
		 * Should be considered <code>private protected</code>.
		 * 
		 * @param key the key.
		 * @param value the value.
		 * @param ref the heap reference.
		 */
		protected AbstractLinkedHeapEntry(final K key, final V value,
				final HeapReference ref)
		{
			super(key, value);

			// Store ref.
			containing_ref = ref;
			is_infinite = false;
		}

		/**
		 * Is this node contained by the specified heap?
		 * 
		 * @param heap the heap for which to test membership.
		 * @return boolean true if this node is contained by the specified heap.
		 * @throws NullPointerException If <code>heap</code> is
		 *             <code>null</code>. Probably shouldn't happen.
		 */
		protected final boolean isContainedBy(final AbstractLinkedHeap<K, V> heap)
			throws NullPointerException
		{
			if (heap == null)
			{
				throw new NullPointerException();
			}

			if (containing_ref == null)
			{
				// Means that this node was orphaned from it's parent heap
				// via a clear or garbage collect.
				return false;
			}

			// Straight reference comparison.
			return (containing_ref.getHeap() == heap);
		}

		/**
		 * Clear this object reference to the source heap.
		 */
		protected final void clearSourceReference()
		{
			containing_ref = null;
		}

	}

	/**
	 * Heap weak reference container.
	 * <p>
	 * The point of this class is to make sure that nodes are not deleted or
	 * have their key's decrease in the context of a heap of which they are not
	 * a member.
	 * <p>
	 * We use weak reference here to help the garbage collector. It also means
	 * that if an entry's containing heap is garbage collected, the node is
	 * considered "orphaned" and no longer a member of the heap.
	 * 
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	@SuppressWarnings("unchecked")
	protected static final class HeapReference
		extends Object
	{

		/**
		 * A weak reference to a heap.
		 */
		private WeakReference<AbstractLinkedHeap> heap_ref;

		/**
		 * Constructor.
		 * 
		 * @param fh the heap to which this object's WeakReference should point.
		 */
		protected HeapReference(final AbstractLinkedHeap fh)
		{
			super();

			// Create stuff.
			heap_ref = new WeakReference<AbstractLinkedHeap>(fh);
		}

		/**
		 * Get the heap reference contained by this object.
		 * 
		 * @return FibonacciHeap the contained heap.
		 * @see #setHeap(AbstractLinkedHeap)
		 */
		protected final AbstractLinkedHeap getHeap()
		{
			return heap_ref.get();
		}

		/**
		 * Set the heap reference contained by this object.
		 * 
		 * @param heap the new heap.
		 * @throws NullPointerException If <code>heap</code> is
		 *             <code>null</code>.
		 * @see #getHeap()
		 */
		protected final void setHeap(final AbstractLinkedHeap heap)
			throws NullPointerException
		{
			if (heap == null)
			{
				throw new NullPointerException();
			}

			// Clear ref.
			clearHeap();

			// Create new reference object.
			heap_ref = new WeakReference<AbstractLinkedHeap>(heap);
		}

		/**
		 * Clear the reference.
		 */
		protected final void clearHeap()
		{
			heap_ref.clear();
		}

	}

}