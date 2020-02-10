package com.tibco.cep.metric.runtime;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.metric.api.Notification;
import com.tibco.cep.metric.api.NotificationPacket;
import com.tibco.cep.metric.api.Subscription;
import com.tibco.cep.metric.evaluator.ASTWalker;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.events.notification.CacheChangeType;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;

public class SubscriberImpl implements ClusterEntityListener {

	protected Cluster cluster;

	protected Map<String, Subscription> subscriptionMap;
	protected Map<String, Map<String, String>> subscriptionByMetricTypeId;
	protected Map<String, ASTWalker> conditionMapper;

	protected Notification notification;
	protected Logger logger;

	public SubscriberImpl(Logger logger, Cluster cluster, Map<String, Subscription> subscriptionMap, Map<String, Map<String, String>> metricSubscriptionMap, Notification notification) {
		this.logger = logger;
		this.cluster = cluster;
		this.subscriptionMap = subscriptionMap;
		this.subscriptionByMetricTypeId = metricSubscriptionMap;
		this.notification = notification;
		conditionMapper = new ConcurrentHashMap<String, ASTWalker>();
	}

	//Added to support Rollover feature
	protected void removeCondition(String subscriptionName) {
		conditionMapper.remove(subscriptionName);
	}

	public boolean requireAsyncInvocation() {
		return false;
	}

	public Filter getEntityFilter() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void onEntity(Object obj) {
		Iterator<? extends RtcTransaction.ReadFromCache> ops = deserializeTxn((byte[]) obj);
		while (ops.hasNext()) {
			RtcTransaction.ReadFromCache entry = ops.next();
			CacheChangeType changeType = entry.getChangeType();
			MetadataCache metadataCache = cluster.getMetadataCache();
			Class<?> clazz = metadataCache.getClass(entry.getTypeId());
			EntityDao dao = metadataCache.getEntityDao(entry.getTypeId());
			String entityURI = dao.getConfig().getUri();
			Entity entity = dao.get(entry.getId());
			if (Concept.class.isAssignableFrom(clazz) == true) {
				Concept concept = (Concept) entity;
				String conceptSimpleName = clazz.getSimpleName();
				switch (changeType) {
					case NEW:
						if (entry.getVersion() <= 1) {
							if (logger.isEnabledFor(Level.DEBUG) == true) {
								logger.log(Level.DEBUG, "An entry with extid [" + entry.getExtId() + "] has been created");
							}
							notifyCreateOrUpdate(conceptSimpleName, CacheChangeType.NEW, entityURI, concept);
						} else {
							if (logger.isEnabledFor(Level.DEBUG) == true) {
								logger.log(Level.DEBUG, "An entry with extid [" + entry.getExtId() + "] has been updated");
							}
							notifyCreateOrUpdate(conceptSimpleName, CacheChangeType.UPDATE, entityURI, concept);
						}
						break;
					case UPDATE:
						if (logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "An entry with extid [" + entry.getExtId() + "] has been updated");
						}
						notifyCreateOrUpdate(conceptSimpleName, CacheChangeType.UPDATE, entityURI, concept);
						break;
					case DELETE:
						if (logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "An entry with extid [" + entry.getExtId() + "] has been deleted");
						}
						notifyDelete(conceptSimpleName, entityURI, entry.getId(), entry.getExtId());
						break;
					default:
						throw new UnsupportedOperationException("Invalid CacheChangeType[" + changeType + "]");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Iterator<? extends RtcTransaction.ReadFromCache> deserializeTxn(byte[] b) {
		ByteArrayInputStream bufStream = new ByteArrayInputStream(b);
		DataInput buf = new DataInputStream(bufStream);
		RtcTransaction txn = new RtcTransaction(null);
		return (Iterator<? extends RtcTransaction.ReadFromCache>) txn.readFromCacheOps(buf);
	}

	private void notifyCreateOrUpdate(String conceptName, CacheChangeType type, String uri, Concept concept) {
		//get the subscriptions for concept name
		Map<String, String> subscriptionMap = subscriptionByMetricTypeId.get(conceptName);
		if (subscriptionMap != null) {
			//isolate subscriptions by processing
			ArrayList<String> acceptableSubscriptionNames = new ArrayList<String>();
			ArrayList<String> unAcceptableSubscriptionNames = new ArrayList<String>();
			for (String subscriptionName : subscriptionMap.keySet()) {
				if (logger.isEnabledFor(Level.DEBUG)) {
					logger.log(Level.DEBUG, "Processing update[changetype=%s, uri=%s,id=%d,extId=%s] for subscription[name=%s,condition=%s]", type.toString(), uri, concept.getId(), concept.getExtId(), subscriptionName, subscriptionMap.get(subscriptionName));
				}
				try {
					//walk thru each condition
					ASTWalker mapper = conditionMapper.get(subscriptionName);
					if (mapper == null) {
						//create the mapper if it does not exist
						mapper = new ASTWalker(subscriptionMap.get(subscriptionName));
						conditionMapper.put(subscriptionName, mapper);
					}
					if (mapper.evaluate(concept) == true) {
						//yes it is, so we put the subscription as a create or update
						acceptableSubscriptionNames.add(subscriptionName);
					} else {
						//the condition is not satisfied, we should deal with this as delete
						unAcceptableSubscriptionNames.add(subscriptionName);
					}
				} catch (Exception e) {
					logger.log(Level.ERROR, e, "could not process update[changetype=%s, uri=%s,id=%d,extId=%s] for subscription[name=%s,condition=%s]", type.toString(), uri, concept.getId(), concept.getExtId(), subscriptionName, subscriptionMap.get(subscriptionName));
					String conceptToStr = toString(concept);
					logger.log(Level.ERROR, conceptToStr);
				}
			}
			NotificationPacket.NotificationType nType = NotificationPacket.NotificationType.CREATE;
			if (type.compareTo(CacheChangeType.UPDATE) == 0) {
				nType = NotificationPacket.NotificationType.UPDATE;
			}
			if (acceptableSubscriptionNames.isEmpty() == false) {
				if (logger.isEnabledFor(Level.DEBUG)) {
					logger.log(Level.DEBUG, "Notifying subscriber with notification type [" + nType + "] for concept [" + concept.getExtId() + "]");
				}
				notification.notify(new NotificationPacket(nType, acceptableSubscriptionNames, uri, concept));
			}
			if (unAcceptableSubscriptionNames.isEmpty() == false) {
				if (logger.isEnabledFor(Level.DEBUG)) {
					logger.log(Level.DEBUG, "Notifying subscriber with notification type [" + NotificationPacket.NotificationType.UNUSABLE + "] for concept [" + concept.getExtId() + "]");
				}
				notification.notify(new NotificationPacket(NotificationPacket.NotificationType.UNUSABLE, unAcceptableSubscriptionNames, uri, concept));
			}
		}
	}

	private void notifyDelete(String conceptName, String uri, long id, String extId) {
		//get the subscriptions for concept name
		Map<String, String> subscriptionMap = subscriptionByMetricTypeId.get(conceptName);
		if (subscriptionMap != null) {
			ArrayList<String> deleteSubscriptionNames = new ArrayList<String>(subscriptionMap.keySet());
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.log(Level.DEBUG, "Notifying subscriber with notification type [" + NotificationPacket.NotificationType.DELETE + "] for concept [" + extId + "]");
			}
			notification.notify(new NotificationPacket(deleteSubscriptionNames, uri, id, extId));
		}
	}

	public String getListenerName() {
		return "Dashboard Subscriber";
	}
	
	@Override
	public void entitiesAdded() {};
	
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}
    
    private String toString(Concept concept) {
    	try {
			StringBuilder sb = new StringBuilder(String.format("%s[id=%d,extId=%s,",concept.getType(), concept.getId(), concept.getExtId()));
			Property[] properties = concept.getProperties();
			for (int i = 0; i < properties.length; i++) {
				Property property = properties[i];
				sb.append(property.getName());
				sb.append("=");
				if (property instanceof PropertyArray) {
					PropertyArray propertyArray = concept.getPropertyArray(property.getName());
					PropertyAtom[] atoms = propertyArray.toArray();
					List<Object> values = new ArrayList<Object>();
					for (PropertyAtom propertyAtom : atoms) {
						Object value = propertyAtom.getValue();
						if (value instanceof Concept) {
							values.add(((Concept) value).getExtId());
						}
						else {
							values.add(String.valueOf(value));
						}
					}
					sb.append(values.toString());
				}
				else {
					sb.append(String.valueOf(concept.getPropertyValue(property.getName())));
				}
				if (i + 1 < properties.length) {
					sb.append(",");
				}
			}
			sb.append("]");
			return sb.toString();
		} catch (Throwable e) {
			logger.log(Level.ERROR, e, "could not log [type=%s,id=%d,extId=%s]", concept.getType(), concept.getId(), concept.getExtId());
			return null;
		}
    }
}
