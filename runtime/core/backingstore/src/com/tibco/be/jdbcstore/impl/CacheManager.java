package com.tibco.be.jdbcstore.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

// Provide a synchronized access and MRU based cache manager.
// The iteration order is based on the last access operation (get or put).
// The oldest object is dropped is the size of the cache exceeds the limit.
@SuppressWarnings("serial")
public class CacheManager extends LinkedHashMap {

    private int mMaxCacheLimit = 100;
    private int mHitCount = 0;
    private int mMissCount = 0;
    private boolean mMaxSizeExceeded = false;
    private RemovalPolicyProvider mRPP = null;
    private CleanUpCallBack mCb = null;

    public static class CacheMgrSet {
        public Map mSyncMap;
        public Map mBackingMap;

        public CacheMgrSet(Map syncMap, Map backingMap)
        {
            mSyncMap = syncMap;
            mBackingMap = backingMap;
        }
    }

    public interface RemovalPolicyProvider {
        public boolean removeEldestEntry(Object obj, int size);
    }

    public interface CleanUpCallBack {
        public void cleanUp(Object obj);
    }

    protected CacheManager(int maxCacheLimit)
    {
        // Set initial capacity to be about the limit.
        // Maintain iterator order to be access order.
        super(maxCacheLimit, (float)0.75, true);
        mMaxCacheLimit = maxCacheLimit;
    }

    public static Map newInstance(int maxCacheLimit)
    {
        if (maxCacheLimit < 1)
            throw new IllegalArgumentException();

        CacheManager    cacheMgr = new CacheManager(maxCacheLimit);
        return Collections.synchronizedMap(cacheMgr);
    }

    public static Map newInstance(int maxCacheLimit, RemovalPolicyProvider rpp) {
        if (maxCacheLimit < 1)
            throw new IllegalArgumentException();

        CacheManager    cacheMgr = new CacheManager(maxCacheLimit);
        cacheMgr.mRPP = rpp;
        return Collections.synchronizedMap(cacheMgr);
    }

    public static CacheMgrSet newInstanceSet(int maxCacheLimit)
    {
        if (maxCacheLimit < 1)
            throw new IllegalArgumentException();

        CacheManager    cacheMgr = new CacheManager(maxCacheLimit);
        Map syncMap = Collections.synchronizedMap(cacheMgr);
        return new CacheMgrSet(syncMap, cacheMgr);
    }

    public static CacheMgrSet newInstanceSet(int maxCacheLimit, RemovalPolicyProvider rpp)
    {
        if (maxCacheLimit < 1)
            throw new IllegalArgumentException();

        CacheManager    cacheMgr = new CacheManager(maxCacheLimit);
        cacheMgr.mRPP = rpp;
        Map syncMap = Collections.synchronizedMap(cacheMgr);
        return new CacheMgrSet(syncMap, cacheMgr);
    }

    public Object get(Object key)
    {
        Object  value = super.get(key);

        if (value == null)
            mMissCount++;
        else
            mHitCount++;

        return value;
    }

    // Override baseclass's method to delete the eldest entry.
    protected boolean removeEldestEntry(Map.Entry eldest)
    {
        boolean remove = false;
        if (mRPP == null) {
            // Remove the eldest entry when size reaches limit.
            if (size() > mMaxCacheLimit)
            {
                remove = true;
                if (false == mMaxSizeExceeded) {
                    mMaxSizeExceeded = true;
                }
            }
        }
        else {
            remove = mRPP.removeEldestEntry(eldest.getValue(), size());
            if (true == remove) {
                if (false == mMaxSizeExceeded) {
                    mMaxSizeExceeded = true;
                }
            }
        }
        if (remove && mCb != null) {
            mCb.cleanUp(eldest.getValue());
        }
        return remove;
    }

    public boolean isCacheOverflow()
    {
        return mMaxSizeExceeded;
    }

    public String toString()
    {
        return  "Cache - size: " + size() + ",  hits: " + mHitCount + ",  misses: " + mMissCount +
                ",  total: " + (mHitCount + mMissCount);
    }

    public void setCleanUpCallBack(CleanUpCallBack cb) {
        mCb = cb;
    }
}
