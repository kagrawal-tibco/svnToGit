/*
 * Copyright (c) 2011.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.metric.runtime;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.metric.api.Notification;
import com.tibco.cep.metric.api.PubSubManager;
import com.tibco.cep.metric.api.Subscription;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityMediator;
import com.tibco.cep.runtime.service.cluster.events.notification.EntityMediator;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.session.RuleSession;

/*
 * Author: Anil Jeswani / Date: Mar 29, 2010 / Time: 3:11:45 PM
 */
public class PubSubManagerImpl implements PubSubManager {

	@SuppressWarnings("unused")
	private RuleSession ruleSession;
	private Cluster cacheCluster;

	private Logger logger;
	private Logger subscriberLogger;

	@SuppressWarnings("unused")
	private TypeManager typeManager;

	private Map<String, Subscription> subscriptionMap; // subscriptionName, subscription
	private Map<String, Map<String, String>> subscriptionMapByMetricTypeId; // conceptName,(subscriptionName,condition)

	private InferenceAgent inferenceAgent;
	private SubscriberImpl subscriber;

	@SuppressWarnings("unchecked")
	public PubSubManagerImpl(final RuleSession ruleSession, final Cluster cacheCluster) {
		this.ruleSession = ruleSession;
		this.cacheCluster = cacheCluster;

		this.logger = ruleSession.getRuleServiceProvider().getLogger("channels");

		this.subscriberLogger = ruleSession.getRuleServiceProvider().getLogger("subscriber");

		ObjectManager objectManager = ruleSession.getObjectManager();
		inferenceAgent = (InferenceAgent) ((DistributedCacheBasedStore) objectManager).getCacheAgent();

		MetadataCache metadataCache = cacheCluster.getMetadataCache();
		LinkedList<Integer> allConceptTypeIds = new LinkedList<Integer>();
		allConceptTypeIds.addAll(metadataCache.getRegisteredConceptTypes());
		cacheCluster.getTopicRegistry().register(inferenceAgent,
				allConceptTypeIds, allConceptTypeIds);
	}

	public final void initialize(final String subscriberName, final Notification notification)
			throws Exception {
		subscriptionMap = new ConcurrentHashMap<String, Subscription>();
		subscriptionMapByMetricTypeId = new ConcurrentHashMap<String, Map<String, String>>();
		subscriber = new SubscriberImpl(subscriberLogger,cacheCluster,subscriptionMap, subscriptionMapByMetricTypeId, notification);

        EntityMediator entityMediator = new ClusterEntityMediator(cacheCluster, subscriber, false);
        entityMediator.startMediator(inferenceAgent.getAgentId());

	}

	public final void subscribe(final Subscription subscription) throws Exception {
		boolean hasPrevSubsription = subscriptionMap.containsKey(subscription.getName());
		subscriptionMap.put(subscription.getName(), subscription);
		if (hasPrevSubsription == true) {
			subscriber.removeCondition(subscription.getName());
			logger.log(Level.DEBUG, "Found previous subscription for [" + subscription.getName() +
					"], removing from cached subscriptions to resubscribe");
		}

		Map<String, String> subMap = subscriptionMapByMetricTypeId
				.get(subscription.getMetricName());
		if (subMap == null) {
			subMap = new ConcurrentHashMap<String, String>();
		}
		subMap.put(subscription.getName(), subscription.getCondition());
		subscriptionMapByMetricTypeId.put(subscription.getMetricName(), subMap);
		logger.log(Level.DEBUG, "Added subscription for Metric [" + subscription.getMetricName() +
				"] with condition [" + subscription.getCondition() + "]");
	}

	public final void unSubscribe(final String subscriptionName) {
		Map<String, String> subMap = subscriptionMapByMetricTypeId
				.get(subscriptionMap.get(subscriptionName));
		if (subMap != null) {
			subMap.remove(subscriptionName);
		}

		Subscription prevSubscription = subscriptionMap.remove(subscriptionName);
		if (prevSubscription != null) {
			subscriber.removeCondition(subscriptionName);
		}
		logger.log(Level.DEBUG, "Removed subscription for Metric [" + subscriptionName + "]");
	}
}
