package com.tibco.be.util.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * generic bounded object pool with lazy initialization
 * 
 * @author mjinia
 * 
 * @param <T>
 */
public abstract class ObjectPool<T> {
    private final Set<T> createdObjects;
    private final LinkedBlockingQueue<T> pool;
    private final int maxSize;
    private final int initialSize;

    /**
     * Creates a new object.
     * 
     * @return T new object
     * @throws Exception
     */
    protected abstract T createObject() throws Exception;
    
    /**
     * creates the pool. with one object upfront, rest are created as needed.
     * 
     * @param maxSize
     *            max size of the pool
     */            
    public ObjectPool(final int maxSize) throws Exception {
    	this(1, maxSize, true);
    }

    /**
     * creates the pool. with initial size upfront, rest are created as needed.
     * 
     * @param initialSize
     *            initial size of the pool
     * 
     * @param maxSize
     *            max size of the pool
     *            
     * @param initialize
     * 			  whether to initialize the pool during construction
     * @throws Exception
     */
    public ObjectPool(final int initialSize, final int maxSize, boolean initialize) throws Exception {
        this.maxSize = maxSize;
        this.initialSize = initialSize;
        this.pool = new LinkedBlockingQueue<>(this.maxSize);
        this.createdObjects = new HashSet<T>();
        
        if (initialize) initialize();
    }
    
    /**
     * Intializes the pool with initialSize Objects
     * 
     * @throws Exception
     */
    protected void initialize() throws Exception {
    	T pooledObject;
    	for (int i=0; i < initialSize; i++) {
    		pooledObject = createObject();
            this.pool.add(pooledObject);
            this.createdObjects.add(pooledObject);
    	}
    }

    /**
     * Gets an object from the pool. If the pool doesn't contain any objects, the calling thread will block until one is
     * available (if pool max is reached). else will create a new object and return it.
     * 
     * @return T pooled object
     * @throws Exception
     */
    public T takeFromPool() throws Exception {
        if (this.createdObjects.size() < this.maxSize) {
            T pooledObject = this.pool.poll();
            if (pooledObject == null) {
                synchronized (this) {
                    if (this.createdObjects.size() < this.maxSize) {
                        pooledObject = this.pool.poll();
                        if (pooledObject == null) {
                            pooledObject = createObject();
                            this.createdObjects.add(pooledObject);
                        }
                    }
                }
            }
            if (pooledObject != null) {
                return pooledObject;
            }
        }

        return this.pool.take();
    }

    /**
     * Gets an object from the pool. If the pool doesn't contain any objects, the calling thread will block for
     * specified amount of time (if pool max is reached). else will create a new object and return it.
     * 
     * @param timeout
     * @param unit
     * @return T pooled object
     * @throws Exception
     */
    public T takeFromPool(final long timeout, final TimeUnit unit) throws Exception {
        if (this.createdObjects.size() < this.maxSize) {
            T pooledObject = this.pool.poll();
            if (pooledObject == null) {
                synchronized (this) {
                    if (this.createdObjects.size() < this.maxSize) {
                        pooledObject = this.pool.poll();
                        if (pooledObject == null) {
                            pooledObject = createObject();
                            this.createdObjects.add(pooledObject);
                        }
                    }
                }
            }
            if (pooledObject != null) {
                return pooledObject;
            }
        }

        return this.pool.poll(timeout, unit);
    }

    /**
     * Returns object back to the pool.
     * 
     * @param object
     *            object to be returned
     */
    public void returnToPool(final T object) {
        if ((object == null) || !(this.createdObjects.contains(object))) {
            return;
        }

        this.pool.add(object);

    }
    
    /**
     * Removes all objects/cleans up from the pool
     *
     * @return
     * @throws Exception
     */
    public boolean removeAllFromPool() throws Exception {
    	boolean removeAll = false;
    	for (T object : createdObjects) {
    		removeAll = this.removeFromPool(object);
    	}
    	return removeAll;
    }
    
    /**
     * Removes the created object.
     * 
     * @return T object to remove
     * @throws Exception
     */
    public boolean removeFromPool(final T object) throws Exception {
    	boolean removeFromPool = true;
    	if (object != null && createdObjects.contains(object)) {
    		if (pool.contains(object)) pool.remove(object);
    		createdObjects.remove(object);
    		removeFromPool = removeObject(object);
    	}
    	return removeFromPool;
    }
    
    /**
     * Removes/cleanups the object
     * 
     * @param object
     * @return
     * @throws Exception
     */
    public abstract boolean removeObject(final T object) throws Exception;
    
}
