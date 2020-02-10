/* Copyright(c) 2001 TIBCO Software Inc.
 *  All rights reserved.
 */

package com.tibco.hawk.jshma.util;

/**
 * Queue is a collector class serves as a queue of Objects. It can be FIFO or
 * FILO.
 * 
 */

public final class Queue {
	public static final int FIFO = 0;
	public static final int FILO = 1;

	private int mDiscipline = FIFO;
	private Object[] mQueue;
	private int mStart;
	private int mSize;
	private int mMaxKeep;

	/**
	 * Constructor with initialial size. By default, it's FIFO and no maxKeep
	 * limit.
	 * 
	 * @param initialSize
	 *            the initial Queue size
	 */
	public Queue(int initialSize) {
		Queue(initialSize, FIFO, 0x7fffffff);
	}

	/**
	 * Constructor with initialial size and Queue discipline. By default,
	 * there's no maxKeep limit.
	 * 
	 * @param initialSize
	 *            the initial Queue size
	 * @param discipline
	 *            FIFO or FILO
	 */
	public Queue(int initialSize, int discipline) {
		Queue(initialSize, discipline, 0x7fffffff);
	}

	/**
	 * Constructor with initialial size, Queue discipline and a maxKeep limit.
	 * When the Queue size reaches the maxKeep limit, for FIFO, the oldest item
	 * will be discarded; for FILO, the new item will be discarded.
	 * 
	 * @param initialSize
	 *            the initial Queue size
	 * @param discipline
	 *            FIFO or FILO
	 * @param maxKeep
	 *            The maximum number of items to be kept in Queue. 0 or negative
	 *            number means no limit.
	 */
	public Queue(int initialSize, int discipline, int maxKeep) {
		Queue(initialSize, discipline, maxKeep);
	}

	private void Queue(int initialSize, int discipline, int maxKeep) {
		if (discipline != FIFO && discipline != FILO) {
			throw (new IllegalArgumentException("Invalid queue discipline."));
		}
		if (initialSize <= 0)
			initialSize = 20;
		mQueue = new Object[initialSize];
		this.mDiscipline = discipline;
		if (maxKeep <= 0)
			mMaxKeep = 0x7fffffff;
		else
			mMaxKeep = maxKeep;
		mStart = 0;
		mSize = 0;
	}

	private synchronized void add(Object item) {
		int len = mQueue.length;

		if (len <= mSize) {
			int inc = len / 4;
			inc = inc < 10 ? 10 : inc;
			Object[] old = mQueue;
			int i, j;

			mQueue = new Object[len + inc];
			for (i = 0, j = mStart; j < len; i++, j++) {
				mQueue[i] = old[j];
			}
			for (j = 0; j < mStart; i++, j++) {
				mQueue[i] = old[j];
			}
			mStart = 0;
			len += inc;
		}
		int pos = mStart + mSize;

		if (len <= pos) {
			pos -= len;
		}
		mQueue[pos] = item;
		mSize++;
	}

	/**
	 * This method enqueues an object with the specified discipline.
	 * 
	 * @param obj
	 *            the object to be enqueud. It can no tbe null.
	 * @return void
	 * @exception IllegalArgumentException
	 *                If obj is null.
	 */

	public void enqueue(Object obj) {
		if (obj == null) {
			throw (new IllegalArgumentException("Null object can not be enqueued."));
		}
		if (mDiscipline == FILO && mSize >= mMaxKeep)
			return;

		add(obj);

		if (mSize > mMaxKeep)
			dequeue();
	}

	private synchronized Object remove() {
		if (0 >= mSize) {
			return null;
		}
		Object item = mQueue[mStart];
		mQueue[mStart] = null; // clear so that gc can get it.

		mStart++;
		mSize--;
		if (mQueue.length <= mStart) {
			mStart = 0;
		}
		return item;
	}

	private synchronized Object removeLast() {
		if (0 >= mSize) {
			return null;
		}
		int index = mStart + mSize - 1;
		if (index >= mQueue.length)
			index -= mQueue.length;

		Object item = mQueue[index];
		mQueue[index] = null; // clear so that gc can get it.

		mSize--;
		return item;
	}

	/**
	 * This method dequeues an object with the specified discipline.
	 * 
	 * @return Object
	 */

	public Object dequeue() {
		if (mDiscipline == FIFO)
			return remove();
		else
			return removeLast();
	}

	/**
	 * This method returns the queue length.
	 * 
	 * @return queue length
	 */

	public int length() {
		return mSize;
	}

	/**
	 * This method returns the whole list in the Queue ordered according to the
	 * discipline.
	 * 
	 * @return the whole list
	 */

	public Object[] getList() {

		if (mSize <= 0)
			return null;
		int i, j;
		int len = mQueue.length;

		Object result[] = new Object[mSize];
		if (mDiscipline == FIFO) {
			for (i = 0, j = mStart; i < mSize && j < len; i++, j++) {
				result[i] = mQueue[j];
			}
			for (j = 0; i < mSize; i++, j++) {
				result[i] = mQueue[j];
			}
		} else {
			int start = mStart + mSize - 1;
			if (start >= mQueue.length)
				start -= mQueue.length;
			for (i = 0, j = start; i < mSize && j >= 0; i++, j--) {
				result[i] = mQueue[j];
			}
			for (j = mQueue.length - 1; i < mSize; i++, j--) {
				result[i] = mQueue[j];
			}
		}

		return result;

	}

	/**
	 * This method changes the max size of the Queue.
	 * 
	 * @param maxKeep
	 *            The maximum number of items to be kept in Queue. 0 or negative
	 *            number means no limit.
	 */
	public void setMaxKeep(int maxKeep) {
		if (maxKeep > 0)
			mMaxKeep = maxKeep;
		else
			mMaxKeep = 0x7fffffff;

		while (mSize > mMaxKeep)
			dequeue();

	}

}
