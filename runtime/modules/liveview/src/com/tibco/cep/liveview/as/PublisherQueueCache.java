/**
 * 
 */
package com.tibco.cep.liveview.as;

import java.util.Collection;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;

/**
 * @author vpatil
 *
 */
public class PublisherQueueCache {
	private ControlDao<Long, TupleContent> publisherQueueCache;
	
	public PublisherQueueCache() {
	}
	
	public void init(Cluster cluster) {
		publisherQueueCache = cluster.getDaoProvider().createControlDao(Long.class, TupleContent.class, ControlDaoType.PublisherQueue);
		publisherQueueCache.start();
	}
	
	public void add(TupleContent tupleContent) {
		publisherQueueCache.put(tupleContent.getTuple().getLong("id"), tupleContent);
	}
	
	public void remove(Long key) {
		publisherQueueCache.remove(key);
	}
	
	public TupleContent get(Long key) {
		return publisherQueueCache.get(key);
	}
	
	public Collection<TupleContent> getAll() {
		return publisherQueueCache.getAll(publisherQueueCache.keySet());
	}
}
