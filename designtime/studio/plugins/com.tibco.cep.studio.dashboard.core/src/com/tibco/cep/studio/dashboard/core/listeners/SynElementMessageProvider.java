package com.tibco.cep.studio.dashboard.core.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

/**
 * This message provider implementation uses a topic=>subscriber HashMap to ensure a more efficient message delivery mechanism; instead of blindly broadcasting messages to every subscriber the provider will only broacast to
 * those that are interested in particular topic types.
 *
 */
public class SynElementMessageProvider implements IMessageProvider {

	private LocalElement messageProviderElement;

	private List<String> topics;

	private HashMap<String, List<ISynElementChangeListener>> elementTypeTopicMap = new HashMap<String, List<ISynElementChangeListener>>();

	private Map<String, List<ISynElementChangeListener>> propertyTopicMap = new HashMap<String, List<ISynElementChangeListener>>();

	private String providerType = "";

	private List<String> providerTypes = new ArrayList<String>();

	@SuppressWarnings("unused")
	private int subscriberCount = 0;

	private Logger logger = Logger.getLogger(SynElementMessageProvider.class.getName());

	private List<ISynElementChangeListener> ignoreList = new ArrayList<ISynElementChangeListener>();

	/**
     *
     */
	public SynElementMessageProvider(LocalElement messageProviderElement) {
		this.messageProviderElement = messageProviderElement;

	}

	public boolean hasSubscriber() {
		return elementTypeTopicMap.size() + propertyTopicMap.size() > 0;
	}

	/**
	 * Returns a list of topics that a listener may be interested in
	 */
	public List<String> getTopics() {

		if (null != topics) {
			return topics;
		}

		topics = new ArrayList<String>();

		if (null != messageProviderElement) {

			/*
			 * First get all the property names
			 */
			for (Iterator<String> iter = messageProviderElement.getPropertyNames().iterator(); iter.hasNext();) {
				topics.add(iter.next().toString());
			}

			/*
			 * Then get all the particle names
			 */
			for (Iterator<String> iter = messageProviderElement.getParticleNames(true).iterator(); iter.hasNext();) {
				topics.add(iter.next().toString());
			}
		}

		return topics;
	}

	public List<String> getPropertyTopics() {
		List<String> topics = new ArrayList<String>();

		if (null != messageProviderElement) {

			/*
			 * Property topics are the property names
			 */
			for (Iterator<String> iter = messageProviderElement.getPropertyNames().iterator(); iter.hasNext();) {
				topics.add(iter.next().toString());
			}
		}

		return topics;
	}

	public List<String> getElementTypeTopics() {
		List<String> topics = new ArrayList<String>();

		if (null != messageProviderElement) {

			/*
			 * ElementType topics are the names of the particles in the element
			 */
			for (Iterator<String> iter = messageProviderElement.getParticleNames(true).iterator(); iter.hasNext();) {
				topics.add(iter.next().toString());
			}
		}

		return topics;
	}

	public void subscribe(ISynElementChangeListener subscriber, String topicName) {
		List<ISynElementChangeListener> subscribers = elementTypeTopicMap.get(topicName);
		if (null == subscribers) {
			subscribers = new ArrayList<ISynElementChangeListener>();
			elementTypeTopicMap.put(topicName, subscribers);
		}
		subscribers.add(subscriber);
		subscriberCount++;
		logger.finest("Adding " + subscriber + " as an element listener to: " + topicName);
	}

	public void subscribe(ISynElementChangeListener subscriber, List<String> topicList) {
		for (Iterator<String> iter = topicList.iterator(); iter.hasNext();) {
			subscribe(subscriber, iter.next());
		}
	}

	public void subscribeToPropertyTopic(ISynElementChangeListener subscriber, String topicName) {

		List<ISynElementChangeListener> subscribers = propertyTopicMap.get(topicName);

		if (null == subscribers) {
			subscribers = new ArrayList<ISynElementChangeListener>();
			propertyTopicMap.put(topicName, subscribers);
		}

		subscribers.add(subscriber);
		subscriberCount++;
		logger.finest("Adding " + subscriber + " as a property listener to: " + topicName);
	}

	public void unsubscribe(ISynElementChangeListener subscriber, List<String> topicList) {
		for (Iterator<String> iter = topicList.iterator(); iter.hasNext();) {
			unsubscribe(subscriber, iter.next());
		}
	}

	public void unsubscribe(ISynElementChangeListener subscriber, String elementTypeTopicName) {
		List<ISynElementChangeListener> subscribers = elementTypeTopicMap.get(elementTypeTopicName);

		if (null != subscribers) {
			if (true == subscribers.remove(subscriber)) {
				logger.finest("Removing " + subscriber + " as an element listener to: " + elementTypeTopicName);

				/*
				 * If the list of subscribers for this topic is empty then remove it from the topic map also
				 */
				if (true == subscribers.isEmpty()) {
					elementTypeTopicMap.remove(subscribers);
				}
			}
		}

	}

	public void unsubscribe(ISynElementChangeListener subscriber, String elementTypeTopicName, String propertyTopicName) {

		unsubscribe(subscriber, elementTypeTopicName);

		List<ISynElementChangeListener> propertySubscribers = propertyTopicMap.get(propertyTopicName);

		if (null != propertySubscribers) {
			if (true == propertySubscribers.remove(subscriber)) {
				logger.finest("Removing " + subscriber + " as a property listener to: " + propertyTopicName + " for element type: " + elementTypeTopicName);
				/*
				 * If the list of subscribers for this topic is empty then remove it from the topic map also
				 */
				if (true == propertySubscribers.isEmpty()) {
					propertyTopicMap.remove(propertySubscribers);
				}
			}

		}
	}

	public void unsubscribeFromPropertyTopic(ISynElementChangeListener subscriber, String topicName) {
		List<ISynElementChangeListener> subscribers = propertyTopicMap.get(topicName);
		if (null != subscribers) {
			if (true == subscribers.remove(subscriber)) {
				subscriberCount++;
				logger.finest("Removing " + subscriber + " as a property listener to: " + topicName);
			}
		}
	}

	public List<String> getSubscribedTopics(ISynElementChangeListener subscriber) {

		List<String> subscribedTopics = new ArrayList<String>();
		for (Iterator<String> iter = elementTypeTopicMap.keySet().iterator(); iter.hasNext();) {
			String topicKey = iter.next().toString();
			List<ISynElementChangeListener> subscribers = elementTypeTopicMap.get(topicKey);

			if (true == subscribers.contains(subscriber)) {
				subscribedTopics.add(topicKey);
			}

		}
		return subscribedTopics;
	}

	public void subscribeToAll(ISynElementChangeListener subscriber) {
		for (Iterator<String> iter = getPropertyTopics().iterator(); iter.hasNext();) {
			String topicKey = iter.next().toString();
			subscribeToPropertyTopic(subscriber, topicKey);
		}

		for (Iterator<String> iter = getElementTypeTopics().iterator(); iter.hasNext();) {
			String topicKey = iter.next().toString();
			subscribe(subscriber, topicKey);
		}

		subscribe(subscriber, getProviderType());
		logger.finest("Adding " + subscriber + " as a listener to all topics");

	}

	public void unsubscribeAll(ISynElementChangeListener subscriber) {

		for (Iterator<String> iter = getElementTypeTopics().iterator(); iter.hasNext();) {
			String topicKey = iter.next().toString();
			unsubscribe(subscriber, topicKey);

			for (Iterator<String> iter2 = getPropertyTopics().iterator(); iter2.hasNext();) {
				String propertyKey = iter2.next().toString();
				unsubscribe(subscriber, topicKey, propertyKey);
			}
		}

		unsubscribe(subscriber, getProviderType());
		logger.finest("Removing " + subscriber + " as a listener to all topics");

	}

	/**
	 * Returns a list of subscribers interest in the given elementType
	 *
	 * @param elementTypeTopicName
	 * @return
	 */
	public List<ISynElementChangeListener> getSubscribers(String elementTypeTopicName) {
		List<ISynElementChangeListener> subscribers = elementTypeTopicMap.get(elementTypeTopicName);

		if (null == subscribers) {
			return Collections.emptyList();
		}

		return subscribers;
	}

	/**
	 * Returns a list of only subscribers interested in both the elementType and property
	 *
	 * @param elementTypeTopicName
	 * @param propertyTopicName
	 * @return
	 */
	public List<ISynElementChangeListener> getSubscribers(String elementTypeTopicName, String propertyTopicName) {
		// if (elementTypeTopicName.equals(getProviderType()) == false) {
		// return Collections.emptyList();
		// }
		List<ISynElementChangeListener> elementTypeSubscribers = elementTypeTopicMap.get(elementTypeTopicName);

		if (null == elementTypeSubscribers) {
			return Collections.emptyList();
		}

		List<ISynElementChangeListener> propertySubscribers = propertyTopicMap.get(propertyTopicName);
		List<ISynElementChangeListener> qualifiedSubscribers = new ArrayList<ISynElementChangeListener>();

		for (Iterator<ISynElementChangeListener> iter = elementTypeSubscribers.iterator(); iter.hasNext();) {
			ISynElementChangeListener elementTypeSubscriber = iter.next();
			if (null != propertySubscribers && true == propertySubscribers.contains(elementTypeSubscriber)) {
				qualifiedSubscribers.add(elementTypeSubscriber);
			}
		}
		return qualifiedSubscribers;
	}

	public void pause(ISynElementChangeListener subscriber) {
		ignoreList.add(subscriber);
	}

	public void resume(ISynElementChangeListener subscriber) {
		ignoreList.remove(subscriber);
	}

	private boolean isPaused(ISynElementChangeListener subscriber) {
		return ignoreList.contains(subscriber);
	}

	/**
	 * The following set of fireXXX(...) API's are implemented purposely without the use of a list iterator to allow concurrent access to the subscriber list. This allows the ability for the listener to be removed from the
	 * list if the thread that fires off the methods has a call to unsubscribe( <current listener>)
	 *
	 * A common use is for a client to subscribe to the message provider and upon receiving the first fireXXX(...) event the client can unsubscribe to the provider because the status has been acknowledge.
	 *
	 * This is very true as in the case of an editor UI; once it has received one of these events it can be marked as 'dirty' and once marked as 'dirty' the editor will remain dirty so there is no need to listen to any
	 * additional messages from this element
	 *
	 * The main reason for this pattern is to simply cut down on unnecessary broadcast
	 */
	public synchronized void fireElementAdded(IMessageProvider provider, IMessageProvider newElement) {
		for (String providerType : newElement.getProviderTypes()) {
			List<ISynElementChangeListener> subscribers = getSubscribers(providerType);
			logger.finest("Broadcasting element_added event to [" + subscribers.size() + "] listener(s)");
			for (int i = 0; i < subscribers.size(); i++) {
				ISynElementChangeListener subscriber = (ISynElementChangeListener) subscribers.get(i);
				if (false == isPaused(subscriber))
					subscriber.elementAdded(provider, newElement);
			}
		}
	}

	public synchronized void fireElementRemoved(IMessageProvider provider, IMessageProvider removedElement) {
		for (Iterator<String> iter = removedElement.getProviderTypes().iterator(); iter.hasNext();) {
			List<ISynElementChangeListener> subscribers = getSubscribers(iter.next());
			logger.finest("Broadcasting element_removed event to [" + subscribers.size() + "] listener(s)");
			for (int i = 0; i < subscribers.size(); i++) {
				ISynElementChangeListener subscriber = (ISynElementChangeListener) subscribers.get(i);
				if (false == isPaused(subscriber))
					subscriber.elementRemoved(provider, removedElement);
			}
		}
	}

	public void fireElementChanged(IMessageProvider provider, IMessageProvider changedElement) {
		for (Iterator<String> iter = changedElement.getProviderTypes().iterator(); iter.hasNext();) {
			List<ISynElementChangeListener> subscribers = getSubscribers(iter.next());
			logger.finest("Broadcasting element_changed event to [" + subscribers.size() + "] listener(s)");
			for (int i = 0; i < subscribers.size(); i++) {
				ISynElementChangeListener subscriber = (ISynElementChangeListener) subscribers.get(i);
				if (false == isPaused(subscriber))
					subscriber.elementChanged(provider, changedElement);
			}
		}
	}

	public void fireStatusChanged(IMessageProvider provider, InternalStatusEnum status) {
		for (Iterator<String> iter = provider.getProviderTypes().iterator(); iter.hasNext();) {
			List<ISynElementChangeListener> subscribers = getSubscribers(iter.next());
			logger.finest("Broadcasting element_status changed event to [" + subscribers.size() + "] listener(s)");
			for (int i = 0; i < subscribers.size(); i++) {
				ISynElementChangeListener subscriber = (ISynElementChangeListener) subscribers.get(i);
				if (false == isPaused(subscriber))
					subscriber.elementStatusChanged(provider, status);
			}
		}
	}

	public synchronized void firePropertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
		try {
			List<ISynElementChangeListener> duplicateTracker = new ArrayList<ISynElementChangeListener>();
			/*
			 * First fire off the event to subscribers for specific property
			 */
			for (Iterator<String> iter = provider.getProviderTypes().iterator(); iter.hasNext();) {
				List<ISynElementChangeListener> subscribers = getSubscribers(iter.next(), property.getName());
				// logger.finest("Broadcasting property_changed event to [" + subscribers.size() + "] listener(s)");
				for (int i = 0; i < subscribers.size(); i++) {
					ISynElementChangeListener subscriber = (ISynElementChangeListener) subscribers.get(i);
					if (false == isPaused(subscriber)) {
						subscriber.propertyChanged(provider, property, oldValue, newValue);

						/*
						 * Remember these
						 */
						duplicateTracker.add(subscriber);
					}
				}
			}
			/*
			 * Then fire off the event to subscribers of the parent element
			 */
			for (Iterator<String> iter = provider.getProviderTypes().iterator(); iter.hasNext();) {
				List<ISynElementChangeListener> subscribers = getSubscribers(iter.next());
				logger.finest("Broadcasting property_changed event to [" + subscribers.size() + "] listener(s)");
				for (int i = 0; i < subscribers.size(); i++) {
					ISynElementChangeListener subscriber = (ISynElementChangeListener) subscribers.get(i);

					/*
					 * Filter out those that have been handled already
					 */
					if (false == isPaused(subscriber) && false == duplicateTracker.contains(subscriber)) {
						subscriber.elementChanged(provider, this);
					}
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public String getProviderType() {
		if (null != messageProviderElement) {
			providerType = messageProviderElement.getElementType();
		}
		return providerType;
	}

	public List<String> getProviderTypes() {
		if (null != messageProviderElement) {
			providerTypes = messageProviderElement.getAllElementTypes();
		}
		return providerTypes;
	}
}
