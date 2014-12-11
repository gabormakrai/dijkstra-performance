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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A base class for heap implementations.
 * <p>
 * This class provides the following:
 * <ul>
 * <li>Implementation of {@link Heap#getKeys()}</code></li>
 * <li>Implementation of {@link Heap#getValues()}</code></li>
 * <li>Implementation of {@link Heap#getEntries()}</code></li>
 * <li>Implementation of {@link Heap#insertAll(Heap)}</code></li>
 * <li>Implementation of {@link Heap#containsEntry(Heap.Entry)}</code></li>
 * <li>Implementation of {@link Heap#containsEntry(Heap.Entry)}</code></li>
 * <li>Implementation of {@link Heap#forEach(Action)}</code></li>
 * <li>Implementation of {@link Heap#isEmpty()}</code></li>
 * <li>Implementation of {@link Heap#equals(Object)}</li>
 * <li>Implementation of {@link Heap#hashCode()}</li>
 * <li>Implementation of {@link Heap#toString()}</code></li>
 * <li>Methods for entry and entry key comparison.</li>
 * <li>A base class for entry implementations.</li>
 * </ul>
 * <p>
 * This class is capable of supporting both the {@link java.lang.Cloneable} and
 * {@link java.io.Serializable} interfaces (although it implements neither
 * directly).
 * 
 * @param <TKey> the key type.
 * @param <TValue> the value type.
 * @author Fran Lattanzio
 * @version $Revision$ $Date$
 */
public abstract class AbstractHeap<TKey, TValue>
	extends Object
	implements Heap<TKey, TValue>, Iterable<Heap.Entry<TKey, TValue>>
{

	/**
	 * Compare two objects for equality, considering <code>null</code> values.
	 * 
	 * @param o1 the first object.
	 * @param o2 the second object.
	 * @return <code>true</code> if <code>o1</code> is equal to <code>o2</code>;
	 *         <code>false</code> otherwise.
	 */
	static boolean objectEquals(final Object o1, final Object o2)
	{
		return (o1 == null) ? (o2 == null) : o1.equals(o2);
	}

	/**
	 * Get the hashcode for the specified Object, or 0 if <code>o</code> is
	 * <code>null</code>.
	 * 
	 * @param anObject the Object for which to get a hashcode.
	 * @return the hashcode or 0 if <code>o</code> is <code>null</code>.
	 */
	static int objectHashCode(final Object anObject)
	{
		return (anObject == null) ? 0 : anObject.hashCode();
	}

	/**
	 * This field is initialized to contain an instance of the key set view the
	 * first time this view is requested. The view is stateless, so there's no
	 * reason to create more than one.
	 */
	private transient volatile Collection<TKey> keys;

	/**
	 * This field is initialized to contain an instance of the values collection
	 * view the first time this view is requested.
	 */
	private transient volatile Collection<TValue> values;

	/**
	 * Entry collection view.
	 * <p>
	 * Like other collection-view fields, this one is lazy initialized the first
	 * time it's accessed.
	 */
	private transient volatile Collection<Heap.Entry<TKey, TValue>> entries;

	/**
	 * Constructor.
	 * <p>
	 * Should be considered <code>private protected</code>. Does nothing; here
	 * only for access protection.
	 */
	protected AbstractHeap()
	{
		super();
	}

	/**
	 * Entry compare utility method.
	 * 
	 * @param node1 the first node.
	 * @param node2 the second node.
	 * @return an integer as like {@link java.lang.Comparable#compareTo(Object)}.
	 * @throws ClassCastException If the keys of the nodes are not mutally
	 *             comparable.
	 * @throws NullPointerException If <code>node1</code> or <code>node2</code>
	 *             is <code>null</code>. This probably shouldn't happen.
	 */
	protected int compare(final Entry<TKey, TValue> node1,
			final Entry<TKey, TValue> node2)
		throws ClassCastException, NullPointerException
	{
		return compareKeys(node1.getKey(), node2.getKey());
	}

	/**
	 * Key compare utility method.
	 * <p>
	 * Note that if this heap uses natural ordering, a <code>null</code> key is
	 * always considered <i>smaller</i> than a non-null key. This is different
	 * from <code>SortedMap</code>, which will generally reject
	 * <code>null</code> keys when using natural ordering. This behavior is
	 * debateably useful (and/or correct) but you'll just have to learn to like
	 * it. Or use your own comparator. Or override this method.
	 * 
	 * @param k1 the first key.
	 * @param k2 the second key.
	 * @return an integer as like <code>Comparable.compare()</code>.
	 * @throws ClassCastException If <code>k1</code> and <code>k2</code> are
	 *             not mutually comparable.
	 * @see java.util.Comparator#compare(Object,Object)
	 */
	@SuppressWarnings("unchecked")
	protected int compareKeys(final TKey k1, final TKey k2)
		throws ClassCastException
	{
		return (getComparator() == null ? (((Comparable<TKey>) k1).compareTo(k2))
				: getComparator().compare(k1, k2));
	}

	/**
	 * @see org.teneighty.heap.Heap#insertAll(org.teneighty.heap.Heap)
	 */
	@Override
	public void insertAll(final Heap<? extends TKey, ? extends TValue> other)
		throws NullPointerException, ClassCastException,
		IllegalArgumentException
	{
		if (other == null)
		{
			throw new NullPointerException();
		}

		if (other == this)
		{
			throw new IllegalArgumentException();
		}

		if (other.isEmpty())
		{
			return;
		}

		// Loop over entries and stuff.
		Iterator<? extends Heap.Entry<? extends TKey, ? extends TValue>> it = other
				.getEntries().iterator();
		Entry<? extends TKey, ? extends TValue> entry;
		while (it.hasNext())
		{
			entry = it.next();

			// Might throw class cast.
			insert(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * @see org.teneighty.heap.Heap#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return (getSize() == 0);
	}

	/**
	 * @see org.teneighty.heap.Heap#forEach(org.teneighty.heap.Action)
	 */
	@Override
	public void forEach(final Action<Heap.Entry<TKey, TValue>> action)
		throws NullPointerException
	{
		if (action == null)
		{
			throw new NullPointerException();
		}

		// note that if action changes the state of this heap - and the
		// iterator detects concurrent modification - we'll die right here
		// with a ConcurrentModificationException (or something equally
		// sinister).
		Iterator<Heap.Entry<TKey, TValue>> entryIterator = iterator();
		while (entryIterator.hasNext())
		{
			action.action(entryIterator.next());
		}
	}

	/**
	 * A dumb (but generic) version of equals.
	 * <p>
	 * Two heaps are considered if they <i>contain</i> exactly the same set of
	 * entries. (Note that two entries are considered equal if they contain
	 * exactly the same key and value.)
	 * <p>
	 * This method works by comparing the entry sets of each class, a process
	 * that takes time <code>O(n<sup>2</sup>)</code> in the worst case.
	 * 
	 * @param other the object to which to compare this heap.
	 * @return <code>true</code> if <code>other</code> is logically equal to
	 *         this heap; <code>false</code> otherwise.
	 * @see java.lang.Object#equals(Object)
	 * @see #hashCode()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object other)
	{
		if (other == null)
		{
			return false;
		}

		if (this == other)
		{
			return true;
		}

		if (Heap.class.isInstance(other) == false)
		{
			return false;
		}

		// erased cast... a little bit evil. We should also figure out
		// here if we want to just cast to Heap<K,V>.
		Heap<? extends TKey, ? extends TValue> that = (Heap<? extends TKey, ? extends TValue>) other;
		return getEntries().equals(that.getEntries());
	}

	/**
	 * @see Heap#hashCode()
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		int code = 0;
		Iterator<Heap.Entry<TKey, TValue>> it = getEntries().iterator();
		while (it.hasNext())
		{
			code += it.next().hashCode();
		}

		return code;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder buffer = new StringBuilder();
		buffer.append(getClass().getName());
		buffer.append("(");
		buffer.append(getSize());
		buffer.append(") ");
		buffer.append("[");

		Iterator<Heap.Entry<TKey, TValue>> it = getEntries().iterator();
		boolean next = it.hasNext();
		Heap.Entry<TKey, TValue> entry = null;
		TKey k = null;
		TValue v = null;
		while (next)
		{
			// Get next entry.
			entry = it.next();
			k = entry.getKey();
			v = entry.getValue();

			// Append mapping.
			buffer.append((k == this) ? "[self-reference]" : String.valueOf(k));
			buffer.append("->");
			buffer.append((v == this) ? "[self-reference]" : String.valueOf(v));

			// Check advance.
			next = it.hasNext();
			if (next)
			{
				buffer.append(", ");
			}
		}

		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * @see org.teneighty.heap.Heap#containsEntry(org.teneighty.heap.Heap.Entry)
	 */
	@Override
	public boolean containsEntry(final Entry<TKey, TValue> entry)
		throws NullPointerException
	{
		if (entry == null)
		{
			throw new NullPointerException();
		}

		// Iterate over all entries...
		Iterator<Heap.Entry<TKey, TValue>> it = getEntries().iterator();
		Entry<TKey, TValue> next = null;
		while (it.hasNext())
		{
			next = it.next();
			if (next.equals(entry))
			{
				return true;
			}
		}

		// Nope.
		return false;
	}

	/**
	 * @see org.teneighty.heap.Heap#getKeys()
	 */
	@Override
	public Collection<TKey> getKeys()
	{
		if (keys == null)
		{
			keys = new KeyCollection();
		}

		return keys;
	}

	/**
	 * @see org.teneighty.heap.Heap#getValues()
	 */
	@Override
	public Collection<TValue> getValues()
	{
		if (values == null)
		{
			values = new ValueCollection();
		}

		return values;
	}

	/**
	 * @see org.teneighty.heap.Heap#getEntries()
	 */
	@Override
	public Collection<Heap.Entry<TKey, TValue>> getEntries()
	{
		if (entries == null)
		{
			entries = new EntryCollection();
		}

		return entries;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object clone()
		throws CloneNotSupportedException
	{
		// May throw clone not supported.
		AbstractHeap<TKey, TValue> ah = (AbstractHeap<TKey, TValue>) super.clone();

		// Clear lame fields.
		ah.keys = null;
		ah.values = null;
		ah.entries = null;

		return ah;
	}

	/**
	 * Basic implementation of a collection that's backed by a heap (or, more
	 * precisely, the enclosing heap instance, as this is an inner class).
	 * 
	 * @param <TElement> the collection elemtn type.
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	private abstract class AbstractHeapCollection<TElement>
		extends AbstractCollection<TElement>
	{

		/**
		 * Constructor.
		 * <p>
		 * Does nothing. Here only for access protection and should be
		 * considered <code>private protected</code>.
		 */
		protected AbstractHeapCollection()
		{
		}

		/**
		 * @see java.util.AbstractCollection#size()
		 */
		@Override
		public final int size()
		{
			return getSize();
		}
		
		/**
		 * @see java.util.AbstractCollection#add(java.lang.Object)
		 */
		@Override
		public boolean add(final TElement objectToAdd)
			throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * @see java.util.AbstractCollection#addAll(java.util.Collection)
		 */
		@Override
		public boolean addAll(final Collection<? extends TElement> collectionToAdd)
			throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * @see java.util.AbstractCollection#clear()
		 */
		@Override
		public final void clear()
			throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * @see java.util.AbstractCollection#remove(java.lang.Object)
		 */
		@Override
		public final boolean remove(final Object objectToRemove)
			throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}

		/**
		 * @see java.util.AbstractCollection#removeAll(java.util.Collection)
		 */
		@Override
		public final boolean removeAll(final Collection<?> objectsToRemove)
			throws NullPointerException, UnsupportedOperationException
		{
			if (objectsToRemove == null)
			{
				throw new NullPointerException();
			}

			throw new UnsupportedOperationException();
		}

		/**
		 * @see java.util.AbstractCollection#retainAll(java.util.Collection)
		 */
		@Override
		public final boolean retainAll(final Collection<?> objectsToRetain)
			throws NullPointerException, UnsupportedOperationException
		{
			if (objectsToRetain == null)
			{
				throw new NullPointerException();
			}

			throw new UnsupportedOperationException();
		}

		/**
		 * @see java.util.AbstractCollection#contains(java.lang.Object)
		 */
		@Override
		public boolean contains(final Object objectToCheck)
		{
			// get an iterator over this collection.
			Iterator<TElement> iterator = iterator();

			while (iterator.hasNext())
			{
				if (objectEquals(objectToCheck, iterator.next()))
				{
					return true;
				}

			}

			return false;
		}

		/**
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public final boolean equals(final Object other)
		{
			if (other == null)
			{
				return false;
			}

			if (other == this)
			{
				return true;
			}

			if (Collection.class.isInstance(other) == false)
			{
				return false;
			}

			// we have some work to do...

			// erased cast... here so that if/when Java ever starts retaining
			// generic type information at runtime, this method will be "more"
			// correct.
			Collection<TElement> that = (Collection<TElement>) other;

			// cheap check sizes.
			if (that.size() != size())
			{
				return false;
			}

			// create shallow, mutable clones of both collections (this and
			// that). We use linked lists here because they support removal at
			// point (via their iterator) in O( 1 ) time, as well as O( 1 )
			// removal of the first element.
			List<TElement> itemsInThis = new LinkedList<TElement>(this);
			List<TElement> itemsInThat = new LinkedList<TElement>(that);

			boolean foundElement;
			TElement elementInThis;
			TElement elementInThat;
			Iterator<TElement> iteratorOverThat;
			while (itemsInThis.size() > 0)
			{
				// basically, we're going to start pulling elements off the
				// queue of elements in this collection, and make sure each one
				// exists in the list of elements in that. When we find one, we
				// remove it from the list of elements in that and repeat until
				// both lists are empty, or we don't find a corresponding
				// element.
				foundElement = false;
				elementInThis = itemsInThis.remove(0);
				iteratorOverThat = itemsInThat.iterator();

				while (iteratorOverThat.hasNext())
				{
					elementInThat = iteratorOverThat.next();

					if (objectEquals(elementInThis, elementInThat))
					{
						// remove element in that.
						iteratorOverThat.remove();

						// we found one equal - break to next element,
						foundElement = true;
						break;
					}
				}

				if (foundElement == false)
				{
					return false;
				}
			}

			// they must be equal!
			return true;
		}
		
		/**
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public final int hashCode()
		{
			int hashCode = 0;

			Iterator<TElement> iterator = iterator();
			TElement next;
			while (iterator.hasNext())
			{
				next = iterator.next();
				hashCode ^= objectHashCode(next);
			}

			return hashCode;
		}

	}

	/**
	 * Entry collection helper class.
	 * <p>
	 * This collection is readonly.
	 * <p>
	 * Note that the <code>contains</code> method of the returned collection is
	 * implemented in terms of <code>containsEntry</code>, rather than
	 * <code>holdsEntry</code>.
	 * 
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	private final class EntryCollection
		extends AbstractHeapCollection<Heap.Entry<TKey, TValue>>
	{

		/**
		 * Constructor.
		 * <p>
		 * Does nothing; here only for access protection.
		 */
		EntryCollection()
		{
			super();
		}

		/**
		 * @see java.util.AbstractCollection#iterator()
		 */
		@Override
		public Iterator<Heap.Entry<TKey, TValue>> iterator()
		{
			return AbstractHeap.this.iterator();
		}

		/**
		 * @see org.teneighty.heap.AbstractHeap.AbstractHeapCollection#contains(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public final boolean contains(final Object o)
		{
			if (o == null)
			{
				return false;
			}

			if (Entry.class.isAssignableFrom(o.getClass()) == false)
			{
				return false;
			}

			Heap.Entry<TKey, TValue> e = (Heap.Entry<TKey, TValue>) o;
			return containsEntry(e);
		}

	}

	/**
	 * Collection view over the keys in this enclosing heap.
	 * <p>
	 * Instances of this class are readonly (readonly collections, that is).
	 * 
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	private final class KeyCollection
		extends AbstractHeapCollection<TKey>
	{

		/**
		 * Constructor.
		 * <p>
		 * Does nothing; here only for access protection.
		 */
		KeyCollection()
		{
			super();
		}

		/**
		 * @see java.util.AbstractCollection#iterator()
		 */
		@Override
		public Iterator<TKey> iterator()
		{
			return new KeyIterator();
		}

		/**
		 * Iterator over the keys in enclosing heap.
		 * 
		 * @author Fran Lattanzio
		 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
		 *          2009) $
		 */
		private final class KeyIterator
			extends Object
			implements Iterator<TKey>
		{

			/**
			 * Backing iterator.
			 */
			private final Iterator<Heap.Entry<TKey, TValue>> backingIterator;

			/**
			 * Constructor.
			 */
			KeyIterator()
			{
				super();

				backingIterator = AbstractHeap.this.iterator();
			}

			/**
			 * @see java.util.Iterator#hasNext()
			 */
			@Override
			public boolean hasNext()
			{
				return backingIterator.hasNext();
			}

			/**
			 * @see java.util.Iterator#next()
			 */
			@Override
			public TKey next()
			{
				return backingIterator.next().getKey();
			}

			/**
			 * @see java.util.Iterator#remove()
			 */
			@Override
			public void remove()
			{
				backingIterator.remove();
			}

		}
		
	}

	/**
	 * Collection view over the values in this heap.
	 * 
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	private final class ValueCollection
		extends AbstractHeapCollection<TValue>
	{

		/**
		 * Constructor.
		 * <p>
		 * Does nothing; here only for access protection.
		 */
		ValueCollection()
		{
			super();
		}

		/**
		 * Get an iterator over this collection.
		 * 
		 * @return an iterator over this collection.
		 */
		@Override
		public Iterator<TValue> iterator()
		{
			// Everything is implemented atop the entry collection iterator.
			return new ValueIterator();
		}

		/**
		 * Iterates over the values in this heap.
		 * 
		 * @author Fran Lattanzio
		 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
		 *          2009) $
		 */
		private final class ValueIterator
			extends Object
			implements Iterator<TValue>
		{

			/**
			 * Backing iterator.
			 */
			private final Iterator<Heap.Entry<TKey, TValue>> backingIterator;

			/**
			 * Constructor.
			 */
			ValueIterator()
			{
				super();

				// get backing iterator.
				backingIterator = AbstractHeap.this.iterator();
			}

			/**
			 * @see java.util.Iterator#hasNext()
			 */
			@Override
			public boolean hasNext()
			{
				return backingIterator.hasNext();
			}

			/**
			 * @see java.util.Iterator#next()
			 */
			@Override
			public TValue next()
			{
				return backingIterator.next().getValue();
			}

			/**
			 * @see java.util.Iterator#remove()
			 */
			@Override
			public void remove()
			{
				backingIterator.remove();
			}

		}

	}

	/**
	 * Basic heap entry/node.
	 * <p>
	 * This entry stores the keys and values for the node, provides basic
	 * <code>get/set</code> methods on them, and contains the "correct"
	 * definitions for <code>equals(Object)</code> and <code>hashCode()</code>
	 * (based on their by-fiat definitions in the <code>Heap.Entry</code>
	 * interface. This class also provides a handy and safe implementation of
	 * <code>toString()</code>. (It's safe in the sense that it checks for
	 * self-references.)
	 * 
	 * @param <TKey> the key type.
	 * @param <TValue> the value type.
	 * @author Fran Lattanzio
	 * @version $Revision$ $Date: 2009-10-29 23:54:44 -0400 (Thu, 29 Oct
	 *          2009) $
	 */
	protected static abstract class AbstractHeapEntry<TKey, TValue>
		extends Object
		implements Heap.Entry<TKey, TValue>
	{

		/**
		 * The key.
		 */
		private TKey key;

		/**
		 * The value.
		 */
		private TValue value;

		/**
		 * Constructor.
		 * 
		 * @param key the key.
		 * @param value the value.
		 */
		protected AbstractHeapEntry(final TKey key, final TValue value)
		{
			super();

			// Store key and value.
			this.key = key;
			this.value = value;
		}
	
		/**
		 * @see org.teneighty.heap.Heap.Entry#getKey()
		 */
		@Override
		public final TKey getKey()
		{
			return key;
		}

		/**
		 * Set the key for this entry.
		 * <p>
		 * Generally, this method call should only be made in the context of a
		 * <code>decreaseKey()</code> call.
		 * <p>
		 * This is not part of the <code>Heap.Entry</code> interface.
		 * 
		 * @param key the new key.
		 * @see #getKey()
		 */
		public final void setKey(final TKey key)
		{
			this.key = key;
		}

		/**
		 * @see org.teneighty.heap.Heap.Entry#getValue()
		 */
		@Override
		public final TValue getValue()
		{
			return value;
		}

		/**
		 * @see org.teneighty.heap.Heap.Entry#setValue(java.lang.Object)
		 */
		@Override
		public final TValue setValue(final TValue value)
		{
			TValue tmp = value;
			this.value = value;
			return tmp;
		}

		/**
		 * @see Heap.Entry#equals(Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public final boolean equals(final Object other)
		{
			if (other == null)
			{
				return false;
			}

			if (this == other)
			{
				return true;
			}

			if (Heap.Entry.class.isInstance(other) == false)
			{
				return false;
			}

			Heap.Entry that = (Heap.Entry) other;

			// Use happier version to check for null.
			return (objectEquals(key, that.getKey()) && objectEquals(
					value, that.getValue()));
		}

		/**
		 * @see Heap.Entry#hashCode()
		 */
		@Override
		public final int hashCode()
		{
			return (objectHashCode(key) ^ objectHashCode(value));
		}

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(getKey() == this ? "[self-reference]" : String
					.valueOf(getKey()));
			sb.append("->");
			sb.append(getValue() == this ? "[self-reference]" : String
					.valueOf(getValue()));
			return sb.toString();
		}

	}

}