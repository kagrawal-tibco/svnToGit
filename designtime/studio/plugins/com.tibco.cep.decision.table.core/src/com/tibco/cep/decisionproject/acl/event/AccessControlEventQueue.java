/**
 *
 */
package com.tibco.cep.decisionproject.acl.event;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread-safe queue for queuing {@code AccessControlEvent}
 * events.
 * @author aathalye
 *
 */
public class AccessControlEventQueue<T extends AccessControlEvent> {

	private AtomicInteger size = new AtomicInteger();
	
	private ConcurrentLinkedQueue<T> backingQueue = new ConcurrentLinkedQueue<T>();

	/**
	 * Add a new event if one does not exist already.
	 * <p>
	 * If an event exists with the given uid, then
	 * simply reset its source to the one of the passed
	 * event.
	 * </p>
	 * <p>
	 * If however, this event is not present, a new event
	 * is created.
	 * </p>
	 * @param event
	 * @return
	 */
	public boolean addEvent(final T event) {
		if (event == null) {
			throw new IllegalArgumentException("Input to the queue cannot be null");
		}
		//Check if this event is already present
		T match = containsEvent(event);
		if (match != null) {
			//Change the contents
			match.setSource(event.getSource());
			return false;
		}
		//Increment the size
		size.incrementAndGet();
		return backingQueue.offer(event);
	}

	/**
	 * Poll, and remove the head event from the queue.
	 * <p>
	 * What if there was an exception while processing ?
	 * </p>
	 * <p>
	 * Should it put the event back into the queue?
	 * @return
	 */
	public T poll() {
		if (!backingQueue.isEmpty()) {
			size.decrementAndGet();
			return backingQueue.poll();
		}
		return null;
	}

	/**
	 * Peek, at the head event from the queue.
	 * <p>
	 * This will not remove the event
	 * </p>
	 * @return
	 */
	public T getFirst() {
		if (!backingQueue.isEmpty()) {
			return backingQueue.peek();
		}
		return null;
	}

	/**
	 * Return whether this queue is empty
	 * @return
	 */
	public boolean isEmpty() {
		return backingQueue.isEmpty();
	}

	public Iterator<T> iterator() {
		return backingQueue.iterator();
	}

	/**
	 * Flushes queue of any residual events
	 */
	public void clear() {
		if (!backingQueue.isEmpty()) {
			backingQueue.clear();
			size.set(0);
		}
	}

	/**
	 * Remove the passed object from the queue
	 * @param event
	 * @return
	 */
	public boolean remove(T event) {
		if (event == null) {
			throw new IllegalArgumentException("Event parameter cannot be null");
		}
		if (backingQueue.contains(event)) {
			boolean removed = backingQueue.remove(event);
			if (removed) {
				//Decrement the size
				size.decrementAndGet();
			}
		}
		return false;
	}

	/**
	 * This method checks to see if this event queue
	 * contains an event which has the same unique
	 * id as this event does.
	 * @param event
	 * @return
	 */
	public T containsEvent(final T event) {
		if (event == null) {
			return null;
		}
		String uid = event.getUid();
		return searchEvent(uid);
	}
	
	/**
	 * This method checks to see if this event queue
	 * contains an event which has the same unique
	 * id the one passed in.
	 * @param uid: The uid to search an event for
	 * @return
	 */
	public T searchEvent(final String uid) {
		if (uid == null) {
			return null;
		}
		
		Iterator<T> events = backingQueue.iterator();
		while (events.hasNext()) {
			T tmp = events.next();
			String uid1 = tmp.getUid();
			if (uid == null && uid1 == null) {
				return tmp;
			}
			if (uid != null) {
				if (uid.equals(uid1)) {
					return tmp;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the current size of this queue
	 * @return
	 */
	public int size() {
		return size.get();
	}

	/**
	 * Returns an array representation of this queue
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T[] toArray(final T[] type) {
		T[] array = type;
		if (type.length < size.get()) {
			array = (T[])Array.newInstance(type.getClass().getComponentType(), size());
		}
		return backingQueue.toArray(array);
	}
}
