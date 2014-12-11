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

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * A Fibonacci heap implementation.
 * <p>
 * A Fibonacci heap is a node-disjoint set of trees (i.e. a forest), each of
 * which of satisfy the heap property: The key of a child node is never greater
 * than the key of its parent. Clearly, this means that the smallest node is at
 * the root of any tree, and that the minimum of the heap as a whole must be
 * among the roots of the forest. Fibonacci heaps have a relaxed structure - the
 * trees have no prescribed shape (allowing for an arbitrary number of children)
 * nor are there restrictions on the number of trees in the forest. This
 * flexibility allows Fibonacci heaps to do work in a lazy manner. We postpone
 * doing work on the heap structure until it is convenient (or easy) to do.
 * <p>
 * Two basic operations determine the structure of a Fibonacci heap:
 * <ul>
 * <li><b>Linking:</b><br>
 * Linking reduces the number of trees in the forest by linking trees whose
 * roots have the same degree (here, degree means the number of direct
 * children). The linking is performed in such a way that the degree of any
 * given node never exceeds <code>O(log n)</code>.</li>
 * <li><b>Cutting:</b><br>
 * Cutting creates an additional tree in the forest by removing a child from a
 * node. Any non-root node can have only one child cut from it before the node
 * itself needs to be cut. This ensures that the size of subtree, whose parent
 * has degree <code>d</code>, is at least <code>F<sub>d+2</sub></code>, where
 * <code>F<sub>i</sub></code> is the <code>i<sup>th</sup></code> Fibonacci
 * number.</li>
 * </ul>
 * <p>
 * All methods/algorithms of this class are based on the code in <i>Introduction
 * to Algorithms</i> by Cormen et al (refered to as CLRS hereafter). Generally,
 * we use the same variable names and such with those in the text. You should
 * refer to the text if you want a better/deeper understanding of a particular
 * procedure's purpose or correctness.
 * <p>
 * The collection-view methods of this class are backed by iterators over the
 * heap structure which are <i>fail-fast</i>: If the heap is structurally
 * modified at any time after the iterator is created, the iterator throws a
 * <code>ConcurrentModificationException</code>. Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 * The collection-views returned by this class do not support the
 * <code>remove</code> operation. This is, unfortunately, not possible using
 * stateless iterators. (By stateless, we simply mean that the iterator holds a
 * reference to a single entry in the heap, and asks the heap itself for the
 * "successor" to its current entry to find the next entry.) As long as the heap
 * structure remains the same, this function will work across multiple calls.
 * However, the several of the operations may result in substantial modification
 * to the heap structure, depending on the state of the heap. At this point, the
 * semantics of the "successor" call (with no context as to which nodes have
 * already been iterated over and which have not) are no longer well-defined.
 * Alternatively, the iterators could support a single remove operation, after
 * which they would fail with a <code>ConcurrentModificationException</code>.
 * However, this is almost certainly more annoying, and thus this is not done.
 * <p>
 * This class is not synchronized (by choice). You must ensure sequential access
 * externally, or you may damage instances of this class. Damage may be subtle
 * and difficult to detect, or it may be pronounced. You can use
 * {@link org.teneighty.heap.Heaps#synchronizedHeap(Heap)} to obtain
 * synchronized instances of this class.
 * <p>
 * The serialization mechanism of this class warrants some discussion. The full
 * heap structure is not serialized to the stream. It would fairly stupid to do
 *  Instead, this class serializes only the key/value pairs, and restores
 * the heap completely "flat". In other words, any internal balancing that was
 * performed by the serialized instance will be forgotten and not restored by
 * any deserialized versions. Of course, a deserialized version will exhibit the
 * same external behavior and amortized time bounds, despite the fact the the
 * internal structure may be vastly different. Note that the worst-case time
 * bounds for a deserialized version may be worse, but a thorough
 * discussion/investigation is beyond the scope of these comments.
 * 
 * @param <TKey> the key type.
 * @param <TValue> the value type.
 * @author Fran Lattanzio
 * @version $Revision$ $Date$
 * @see "<a href='http://en.wikipedia.org/wiki/Introduction_to_Algorithms'>Cormen, T. H.; Leiserson C. E.; Rivest R. L.; &amp; Stein, C (2001) <i>Introduction to Algorithms</i>. MIT Press.</a>"
 */
public class FibonacciHeap<TKey, TValue>
	extends AbstractLinkedHeap<TKey, TValue>
	implements Serializable
{

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 9802348L;

	/**
	 * Comparator.
	 */
	private final Comparator<? super TKey> comp;
	
	/**
	 * The minimum entry of this heap.
	 */
	transient FibonacciHeapEntry<TKey, TValue> minimum;

	/**
	 * The size of this heap.
	 */
	private transient int size;

	/**
	 * The mod count.
	 */
	transient volatile int mod_count;

	/**
	 * The heap reference.
	 */
	private transient HeapReference source_heap;

	/**
	 * Constructor.
	 * <p>
	 * The nodes of this heap will be ordered by their keys' <i>natural
	 * ordering</i>.
	 * <p>
	 * The keys of all nodes inserted into the heap must implement the
	 * <code>Comparable</code> interface. Furthermore, all such keys must be
	 * <i>mutually comparable</i>:<code>k1.compareTo(k2)</code> must not throw a
	 * <code>ClassCastException</code> for any elements <code>k1</code> and
	 * <code>k2</code> in the heap.
	 */
	public FibonacciHeap()
	{
		this(null);
	}

	/**
	 * Constructor.
	 * <p>
	 * The keys of all nodes inserted into the heap must be <i>mutually
	 * comparable</i> by the given <code>Comparator</code>:
	 * <code>comparator.compare(k1,k2)</code> must not throw a
	 * <code>ClassCastException</code> for any keys <code>k1</code> and
	 * <code>k2</code> in the heap.
	 * 
	 * @param comp the comparator to use. A <code>null</code> means the keys'
	 *        natural ordering will be used.
	 */
	public FibonacciHeap(final Comparator<? super TKey> comp)
	{
		super();

		// Null min.
		minimum = null;
		size = 0;
		mod_count = 0;
		this.comp = comp;
		source_heap = new HeapReference(this);
	}

	/**
	 * @see org.teneighty.heap.Heap#getComparator()
	 */
	@Override
	public Comparator<? super TKey> getComparator()
	{
		return comp;
	}
	
	/**
	 * @see org.teneighty.heap.Heap#getSize()
	 */
	@Override
	public int getSize()
	{
		return size;
	}

	/**
	 * @see org.teneighty.heap.Heap#holdsEntry(org.teneighty.heap.Heap.Entry)
	 */
	@Override
	public boolean holdsEntry(final Heap.Entry<TKey, TValue> e)
		throws NullPointerException
	{
		if (e == null)
		{
			throw new NullPointerException();
		}

		// Obvious check.
		if (e.getClass().equals(FibonacciHeapEntry.class) == false)
		{
			return false;
		}

		// Narrow.
		FibonacciHeapEntry<TKey, TValue> entry = (FibonacciHeapEntry<TKey, TValue>) e;

		// Use reference trickery.
		return entry.isContainedBy(this);
	}
	
	/**
	 * @see org.teneighty.heap.Heap#insert(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Entry<TKey, TValue> insert(final TKey key, final TValue value)
		throws ClassCastException, NullPointerException
	{
		FibonacciHeapEntry<TKey, TValue> node = new FibonacciHeapEntry<TKey, TValue>(key, value, source_heap);

		// Do some node housekeeping.
		node.degree = 0;
		node.marked = false;
		node.left = node.right = node;
		node.parent = null;
		node.child = null;

		// Connect to root node.
		if (minimum == null)
		{
			minimum = node;
		}
		else
		{
			// Check for key compatibility before inserting.
			// May throw class cast...
			int cmp = compare(node, minimum);

			// Insert into root list.
			minimum.right.left = node;
			node.right = minimum.right;
			minimum.right = node;
			node.left = minimum;

			// We have a new winner...
			if (cmp < 0)
			{
				minimum = node;
			}
		}

		// Inc size
		size += 1;

		// Inc mod cout.
		mod_count += 1;

		// Return the new node.
		return node;
	}

	/**
	 * @see org.teneighty.heap.Heap#union(org.teneighty.heap.Heap)
	 */
	@Override
	public void union(final Heap<TKey, TValue> other)
		throws ClassCastException, NullPointerException, IllegalArgumentException
	{
		if (other == null)
		{
			throw new NullPointerException();
		}

		if (this == other)
		{
			throw new IllegalArgumentException();
		}

		if (other.isEmpty())
		{
			return;
		}

		if (other.getClass().equals(FibonacciHeap.class))
		{
			// Get other root.
			FibonacciHeap<TKey, TValue> that = (FibonacciHeap<TKey, TValue>) other;

			try
			{
				int cmp = 0;
				if (minimum != null && that.minimum != null)
				{
					// May throw class cast.
					cmp = compare(that.minimum, minimum);
				}

				// Cat root list of other heap together with this one's.
				minimum.left.right = that.minimum.right;
				that.minimum.right.left = minimum.left;
				minimum.left = that.minimum;
				that.minimum.right = minimum;

				if (cmp < 0)
				{
					// Point to new min.
					minimum = that.minimum;
				}

				// Update stuff.
				size += that.size;
				mod_count += 1;

				// Change that heap's heap reference to point to this heap.
				// Thus, all children of that become children of 
				that.source_heap.setHeap(this);
				that.source_heap = new HeapReference(that);
			}
			finally
			{
				// Actually clear the other heap. Always done!
				that.clear();
			}
		}
		else
		{
			throw new ClassCastException();
		}
	}

	
	/**
	 * @see org.teneighty.heap.Heap#extractMinimum()
	 */
	@Override
	public Entry<TKey, TValue> extractMinimum()
		throws NoSuchElementException
	{
		if (isEmpty())
		{
			// throw new NoSuchElementException();
			FibonacciHeapEntry<TKey, TValue> t = new FibonacciHeapEntry<TKey, TValue>(null, null, null);
			return t;
		}

		// References that will be needed. See CLRS.
		FibonacciHeapEntry<TKey, TValue> t;
		FibonacciHeapEntry<TKey, TValue> w;
		FibonacciHeapEntry<TKey, TValue> z = minimum;

		if (z.child != null)
		{
			// Remove parent references for all of z's children.
			w = z.child;
			t = w;

			do
			{
				t.parent = null;
				t = t.right;
			}
			while (t != w);

			// Add the children to the root list.
			minimum.left.right = w.right;
			w.right.left = minimum.left;
			minimum.left = w;
			w.right = minimum;
		}

		// Remove z from the root list.
		z.left.right = z.right;
		z.right.left = z.left;

		if (z == z.right)
		{
			// We hope the heap is now empty...
			minimum = null;
		}
		else
		{
			// We have some work to do.
			minimum = z.right;
			consolidate();
		}

		// Dec size, inc mod.
		size -= 1;
		mod_count += 1;

		// Clear old heap reference.
		z.clearSourceReference();

		// Return old minimum.
		return z;
	}

	/**
	 * @see org.teneighty.heap.Heap#getMinimum()
	 */
	@Override
	public Entry<TKey, TValue> getMinimum()
		throws NoSuchElementException
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}

		// Return it.
		return minimum;
	}

	/**
	 * Run the consolidate operation.
	 * <p>
	 * Based on CLRS code, but with minor mods...
	 */
	@SuppressWarnings("unchecked")
	private void consolidate()
	{
		// Create the auxiliary array.
		int dn = (int) Math.floor(Math.log(size) / Math.log(2)) + 2;
		FibonacciHeapEntry[] a = new FibonacciHeapEntry[dn];

		// Iterating node - node at which to stop iterating...
		FibonacciHeapEntry<TKey, TValue> iter = minimum;

		// The node we're on now; w from CLRS.
		FibonacciHeapEntry<TKey, TValue> w = iter;

		// x and y from CLRS code.
		FibonacciHeapEntry<TKey, TValue> x;
		FibonacciHeapEntry<TKey, TValue> y;

		// temp ref.
		FibonacciHeapEntry<TKey, TValue> temp;

		// d from CLRS code.
		int d;

		do
		{
			x = w;
			d = x.degree;

			if (a[d] != x)
			{
				while (a[d] != null)
				{
					// y has same degree as x... This much we know.
					y = a[d];

					if (compare(y, x) < 0)
					{
						// Swap x and y.
						temp = x;
						x = y;
						y = temp;
					}

					// Make y a child of x.
					link(y, x);
					iter = x;
					w = x;
					a[d] = null;
					d += 1;
				}

				a[d] = x;
			}

			// Next node.
			w = w.right;
		}
		while (w != iter);

		// Reset... we need to iterate over the root list again.
		minimum = iter;
		w = iter;

		// Find the new minimum in the root list (if we don't already have it).
		do
		{
			if (compare(w, minimum) < 0)
			{
				// Found a new minimum node.
				minimum = w;
			}

			// Next.
			w = w.right;
		}
		while (w != iter);

	}

	/**
	 * Link <code>y</code> to <code>x</code>, by removing <code>y</code> from
	 * the root list and making it a child of <code>x</code>.
	 * 
	 * @param y the new child node.
	 * @param x the new parent node.
	 */
	private void link(final FibonacciHeapEntry<TKey, TValue> y, final FibonacciHeapEntry<TKey, TValue> x)
	{
		// Remove y from the root list.
		y.left.right = y.right;
		y.right.left = y.left;

		if (x.child == null)
		{
			// x is all alone in the world.
			y.right = y;
			y.left = y;
			x.child = y;
		}
		else
		{
			// Concat into child list of x.
			y.right = x.child.right;
			y.left = x.child;
			x.child.right.left = y;
			x.child.right = y;
		}

		// Some housekeeping for the nodes.
		y.parent = x;
		x.degree += 1;
		y.marked = false;
	}

	/**
	 * @see org.teneighty.heap.Heap#decreaseKey(org.teneighty.heap.Heap.Entry, java.lang.Object)
	 */
	@Override
	public void decreaseKey(final Heap.Entry<TKey, TValue> e, final TKey k)
		throws IllegalArgumentException, ClassCastException
	{
		// Check and cast.
		if (holdsEntry(e) == false)
		{
			throw new IllegalArgumentException();
		}

		// x from CLRS.
		FibonacciHeapEntry<TKey, TValue> x = (FibonacciHeapEntry<TKey, TValue>) e;

		// Check key... May throw class cast as well.
		if (compareKeys(k, x.getKey()) > 0)
		{
			throw new IllegalArgumentException();
		}

		// Store the new key value.
		x.setKey(k);

		// Restore the heap structure.
		decreaseKeyImpl(x);
	}
	
	/**
	 * Decrease key implementation. Basically, we restore the heap structure by
	 * cutting <code>x</code> from it's parent (if necessary) and making it's
	 * parent a child of <code>x</code>.
	 * 
	 * @param x the whose key has just been decreased and needs to be percolated
	 *        toward the top of the heap.
	 */
	private void decreaseKeyImpl(final FibonacciHeapEntry<TKey, TValue> x)
	{
		// Get x's parent.
		FibonacciHeapEntry<TKey, TValue> y = x.parent;

		// If x has a lower key than it's parent (and assuming x was not already
		// in the root list) then we have work to do.
		if (y != null && compare(x, y) < 0)
		{
			cut(x, y);
			cascadingCut(y);
		}

		// See if the new node is smaller.
		if (compare(x, minimum) < 0)
		{
			minimum = x;
		}

		mod_count += 1;
	}
	
	/**
	 * Remove <code>x</code> from the child list of <code>y</code> and add
	 * <code>x</code> to the root list.
	 * 
	 * @param x the node to cut.
	 * @param y the node from which to cut <code>x</code>.
	 */
	private void cut(final FibonacciHeapEntry<TKey, TValue> x, final FibonacciHeapEntry<TKey, TValue> y)
	{
		if (x.right == x)
		{
			// Last child.
			y.child = null;
		}
		else
		{
			// Next yutz over.
			y.child = x.right;
		}

		// Remove x from the child list.
		x.left.right = x.right;
		x.right.left = x.left;

		// y has one less child.
		y.degree -= 1;

		// Add x to the root list.
		minimum.right.left = x;
		x.right = minimum.right;
		minimum.right = x;
		x.left = minimum;
		x.parent = null;

		// Unmark x, since it has just been cut.
		x.marked = false;
	}

	/**
	 * Perform a cascading cut across <code>y</code>, by cutting <code>y</code>
	 * from it's parent and then performing <code>cascadingCut()</code> on
	 * <code>y</code>'s parent.
	 * 
	 * @param y the node on which to perform a cascading cut.
	 */
	private void cascadingCut(final FibonacciHeapEntry<TKey, TValue> y)
	{
		FibonacciHeapEntry<TKey, TValue> z = y.parent;

		if (z != null)
		{
			if (y.marked == false)
			{
				// Simply mark y.
				y.marked = true;
			}
			else
			{
				// Otherwise, cut y and recursively cascade on z.
				cut(y, z);
				cascadingCut(z);
			}
		}
	}
	
	/**
	 * @see org.teneighty.heap.Heap#delete(org.teneighty.heap.Heap.Entry)
	 */
	@Override
	public void delete(final Heap.Entry<TKey, TValue> e)
		throws IllegalArgumentException, NullPointerException
	{
		// Check and cast.
		if (holdsEntry(e) == false)
		{
			throw new IllegalArgumentException();
		}

		// Narrow.
		FibonacciHeapEntry<TKey, TValue> entry = (FibonacciHeapEntry<TKey, TValue>) e;

		// Make it infinitely small.
		entry.is_infinite = true;

		// Percolate the top,
		decreaseKeyImpl(entry);

		// Remove.
		extractMinimum();

		// Reset entry state.
		entry.is_infinite = false;
	}

	/**
	 * @see org.teneighty.heap.Heap#clear()
	 */
	@Override
	public void clear()
	{
		// Clear lame fields.
		minimum = null;
		size = 0;
		mod_count += 1;

		// Clear the heap ref that all the existing nodes have been using.
		// All contained nodes now have null containing heap.
		source_heap.clearHeap();

		// Recreate the reference object.
		source_heap = new HeapReference(this);
	}

	/**
	 * @see org.teneighty.heap.Heap#iterator()
	 */
	@Override
	public Iterator<Heap.Entry<TKey, TValue>> iterator()
	{
		return new EntryIterator();
	}

	/**
	 * Serialize the object to the specified output stream.
	 * <p>
	 * This method takes time <code>O(n)</code> where <code>n</code> is the size
	 * this heap.
	 * 
	 * @param out the stream to which to serialize this object.
	 * @throws IOException If this object cannot be serialized.
	 */
	private void writeObject(final ObjectOutputStream out)
		throws IOException
	{
		out.defaultWriteObject();
		out.writeInt(size);

		// Write out all key/value pairs.
		Iterator<Heap.Entry<TKey, TValue>> it = new EntryIterator();
		Heap.Entry<TKey, TValue> et = null;
		while (it.hasNext())
		{
			try
			{
				et = it.next();

				// May result in NotSerializableExceptions, but we there's not a
				// whole helluva lot we can do about that.
				out.writeObject(et.getKey());
				out.writeObject(et.getValue());
			}
			catch (final ConcurrentModificationException cme)
			{
				// User's fault.
				throw (IOException) new IOException("Heap structure changed during serialization").initCause(cme);
			}
		}
	}

	/**
	 * Deserialize the restore this object from the specified stream.
	 * <p>
	 * This method takes time <code>O(n)</code> where <code>n</code> is the size
	 * this heap.
	 * 
	 * @param in the stream from which to read data.
	 * @throws IOException If this object cannot properly read from the
	 *         specified
	 *         stream.
	 * @throws ClassNotFoundException If deserialization tries to classload an
	 *         undefined class.
	 */
	@SuppressWarnings("unchecked")
	private void readObject(final ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		// do the magic.
		in.defaultReadObject();
		
		// get comparator and size.
		int rsize = in.readInt();

		// Create new ref object.
		source_heap = new HeapReference(this);

		// Read and insert all the keys and values.
		TKey key;
		TValue value;
		for (int index = 0; index < rsize; index++)
		{
			key = (TKey) in.readObject();
			value = (TValue) in.readObject();
			insert(key, value);
		}
	}

	/**
	 * Entry iterator class.
	 * <p>
	 * This iterator does not support the <code>remove()</code> operation. Any
	 * call to <code>remove()</code> will fail with a
	 * <code>UnsupportedOperationException</code>.
	 * 
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date$
	 */
	private class EntryIterator
		extends Object
		implements Iterator<Heap.Entry<TKey, TValue>>
	{

		/**
		 * The next entry.
		 */
		private FibonacciHeapEntry<TKey, TValue> next;

		/**
		 * The mod count.
		 */
		private final int my_mod_count;

		/**
		 * Constructor.
		 */
		EntryIterator()
		{
			super();

			// Start at min.
			next = FibonacciHeap.this.minimum;

			// Copy mod count.
			my_mod_count = FibonacciHeap.this.mod_count;
		}
		
		/**
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext()
		{
			if (my_mod_count != FibonacciHeap.this.mod_count)
			{
				throw new ConcurrentModificationException();
			}

			return (next != null);
		}

		/**
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Heap.Entry<TKey, TValue> next()
			throws NoSuchElementException, ConcurrentModificationException
		{
			if (hasNext() == false)
			{
				throw new NoSuchElementException();
			}

			// Get the next node.
			FibonacciHeapEntry<TKey, TValue> n = next;
			next = getSuccessor(next);
			return n;
		}

		/**
		 * Return the successor entry to the specified entry.
		 * 
		 * @param entry the given entry.
		 * @return the successor entry.
		 */
		private FibonacciHeapEntry<TKey, TValue> getSuccessor(FibonacciHeapEntry<TKey, TValue> entry)
		{
			if (entry.child != null)
			{
				return entry.child;
			}

			// The first entry.
			FibonacciHeapEntry<TKey, TValue> first;

			do
			{
				first = (entry.parent == null) ? FibonacciHeap.this.minimum : entry.parent.child;

				// Look for siblings.
				if (entry.right != first)
				{
					return entry.right;
				}

				// Look at entry parent.
				entry = entry.parent;
			}
			while (entry != null);

			// Reached the root node, no more successors.
			return null;
		}

		/**
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove()
			throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Fibonacci heap entry.
	 * 
	 * @param <TKey> the key type.
	 * @param <TValue> the value type.
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date$
	 */
	private static final class FibonacciHeapEntry<TKey, TValue>
		extends AbstractLinkedHeap.AbstractLinkedHeapEntry<TKey, TValue>
		implements Serializable
	{

		/**
		 * Serial version.
		 */
		private static final long serialVersionUID = 2348L;

		/**
		 * Is this node marked?
		 */
		transient boolean marked;

		/**
		 * The node degree - number of children.
		 */
		transient int degree;

		/**
		 * Parent node.
		 */
		transient FibonacciHeapEntry<TKey, TValue> parent;

		/**
		 * Child node.
		 */
		transient FibonacciHeapEntry<TKey, TValue> child;

		/**
		 * Left sibling node.
		 */
		transient FibonacciHeapEntry<TKey, TValue> left;

		/**
		 * Right sibling node.
		 */
		transient FibonacciHeapEntry<TKey, TValue> right;

		/**
		 * Constructor.
		 * 
		 * @param key the key.
		 * @param value the value.
		 * @param source_ref a wrapped weak reference to the creating heap.
		 */
		FibonacciHeapEntry(final TKey key, final TValue value, final HeapReference source_ref)
		{
			super(key, value, source_ref);
		}

	}

}