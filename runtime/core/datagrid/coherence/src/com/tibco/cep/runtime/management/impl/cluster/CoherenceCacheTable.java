package com.tibco.cep.runtime.management.impl.cluster;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.DistributedCacheService;
import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Apr 16, 2009 Time: 1:55:47 PM
*/
public class CoherenceCacheTable implements InternalCacheTable {
    /**
     * {@value}.
     */
    public static final String NAME_DISTRIBUTED_CACHE = "DistributedCache";

    /**
     * {@value}.
     */
    public static final String NAME_DISTRIBUTED_CACHE_MBEAN_NODES =
            "Coherence:type=Cache,service=DistributedCache,name=%s,*";

    protected DistributedCacheService distributedCacheService;

    protected MBeanServer mBeanServer;

    public void init(String clusterURL) {
        this.distributedCacheService =
                (DistributedCacheService) CacheFactory.getService(NAME_DISTRIBUTED_CACHE);

        this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    public void discard() {
        distributedCacheService = null;
        mBeanServer = null;
    }

    public Collection<CacheInfo> getCacheInfos() {
        HashMap<FQName, CacheInfo> cacheInfos = collectCacheInfos();

        return cacheInfos.values();
    }

    protected HashMap<FQName, CacheInfo> collectCacheInfos() {
        HashMap<FQName, CacheInfo> cacheInfos = new HashMap<FQName, CacheInfo>();

        Enumeration<String> cacheNames = distributedCacheService.getCacheNames();
        while (cacheNames.hasMoreElements()) {
            String cacheName = cacheNames.nextElement();
            String cacheNameQueryStr = String.format(NAME_DISTRIBUTED_CACHE_MBEAN_NODES, cacheName);

            try {
                Set<? extends ObjectName> objectNamesOfCaches =
                        mBeanServer.queryNames(new ObjectName(cacheNameQueryStr), null);
                List<CacheInfo> infoList = new ArrayList<CacheInfo>();
                for (ObjectName objectNameOfCache : objectNamesOfCaches) {
                    CacheInfo cacheInfo =
                            CoherenceCacheInfoConverter
                                    .convert(mBeanServer, cacheName, objectNameOfCache);
                    if(cacheInfo!=null)
                    	infoList.add(cacheInfo);

                }
                if(infoList.size() > 0){
	                CacheInfoImpl cacheInfo = new CacheInfoImpl();
	                cacheInfo.setName(new FQName(cacheName));
	                long size = 0;
	                long numberOfGets = 0;
	                long numberOfPuts = 0;
	                double avgGetTimeMillis = 0;
	                double avgPutTimeMillis = 0;
	                double hitRatio = 0;
	                long maxSize = 0;
	                long minSize = 0;
	                long expiryDelayMillis = 0;
	                int len = infoList.size();
	                for(int i=0;i<len;i++){
	                	size = size + infoList.get(i).getSize();
	                	numberOfGets = numberOfGets + infoList.get(i).getNumberOfGets();
	                	numberOfPuts = numberOfPuts + infoList.get(i).getNumberOfPuts();
	                	avgGetTimeMillis = avgGetTimeMillis + infoList.get(i).getAvgGetTimeMillis();
	                	avgPutTimeMillis = avgPutTimeMillis + infoList.get(i).getAvgPutTimeMillis();
	                }
            		hitRatio = infoList.get(0).getHitRatio();
            		maxSize = infoList.get(0).getMaxSize();
            		minSize = infoList.get(0).getMinSize();
            		expiryDelayMillis = infoList.get(0).getExpiryDelayMillis();
            		
	                cacheInfo.setSize(size);
	                cacheInfo.setNumberOfGets(numberOfGets);
	                cacheInfo.setNumberOfPuts(numberOfPuts);
	                cacheInfo.setAvgGetTimeMillis(avgGetTimeMillis/len);
	                cacheInfo.setAvgPutTimeMillis(avgPutTimeMillis/len);
	                cacheInfo.setHitRatio(hitRatio);
	                cacheInfo.setMaxSize(maxSize);
	                cacheInfo.setMinSize(minSize);
	                cacheInfo.setExpiryDelayMillis(expiryDelayMillis);
	                
	                cacheInfos.put(cacheInfo.getName(), cacheInfo);
	                infoList.clear();
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return cacheInfos;
    }

    public CacheInfo getCacheInfo(FQName name) {
        HashMap<FQName, CacheInfo> cacheInfos = collectCacheInfos();
        CacheInfo cacheInfo = cacheInfos.get(name);
        cacheInfos.clear();

        return cacheInfo;
    }
}
