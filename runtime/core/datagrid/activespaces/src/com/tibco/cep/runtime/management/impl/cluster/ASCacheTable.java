/**
 * 
 */
package com.tibco.cep.runtime.management.impl.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.tibco.as.space.ASException;
import com.tibco.cep.runtime.management.CacheInfo;
import com.tibco.cep.runtime.management.impl.cluster.data.metaspace.MetaspaceStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceStatistics;
import com.tibco.cep.runtime.util.FQName;

/**
 * @author Nick & mwiley
 *
 */
public class ASCacheTable implements InternalCacheTable {
	
	protected ASStatCollector asStatCollector;

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.management.impl.cluster.InternalCacheTable#discard()
	 */
	@Override
	public void discard() {
		asStatCollector = null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.management.impl.cluster.InternalCacheTable#init(java.lang.String)
	 */
	@Override
	public void init(String clusterURL) {
		asStatCollector = new ASStatCollector();
		if(asStatCollector.connect(clusterURL, "", "") == false && asStatCollector.useConnectedMetaspace(clusterURL) == false){
			//TODO: logging?
			System.err.println("ASCacheTable.init - Unable to initialize ASStatCollector for Metaspace " + clusterURL);
			return;
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.management.CacheTable#getCacheInfo(com.tibco.cep.runtime.util.FQName)
	 */
	@Override
	public CacheInfo getCacheInfo(FQName name) {
		if(asStatCollector == null){
			return null;
		}
		try{
			return getCacheInfo(name, asStatCollector.getMetaspaceStats());
		}
		catch(ASException e){
			//TODO: Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public CacheInfo getCacheInfo(FQName name, MetaspaceStatistics msStats) {
		CacheInfoImpl cacheInfo = null;
		try{
			SpaceStatistics spaceStats = msStats.getSpaces().get(name.toString());
			cacheInfo = new CacheInfoImpl();
	        cacheInfo.setName(name);
	        cacheInfo.setSize(spaceStats.getTotalOriginals());
	        cacheInfo.setNumberOfGets(spaceStats.getTotalGets());
	        cacheInfo.setNumberOfPuts(spaceStats.getTotalPuts());
	        cacheInfo.setHitRatio(spaceStats.getTotalGets() == 0 ? 0:(spaceStats.getTotalGets()-spaceStats.getTotalMisses())/spaceStats.getTotalGets());
		}
	    catch(Exception e){
	    	//TODO: logging?
			e.printStackTrace();
	    }
		return cacheInfo;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.management.CacheTable#getCacheInfos()
	 */
	@Override
	public Collection<CacheInfo> getCacheInfos() {
		if(asStatCollector == null){
			return null;
		}
		Collection<CacheInfo> cacheInfos = null;
		try{
			MetaspaceStatistics msStats = asStatCollector.getMetaspaceStats();
	        Iterator<String> cacheNameItr = asStatCollector.getSpaceNames().iterator();
	        cacheInfos = new ArrayList<CacheInfo>();
	        while (cacheNameItr.hasNext()) {
	            cacheInfos.add(getCacheInfo(new FQName(cacheNameItr.next()), msStats));
	        }
		}
		catch(ASException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return cacheInfos;
	}

}
