package com.tibco.cep.dashboard.plugin.beviews.streaming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.FieldValueArray;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.management.ManagementClient;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.data.ConceptTupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleConvertor;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.psvr.common.FatalException;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandler;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandlerFactory;
import com.tibco.cep.dashboard.psvr.streaming.PubSubHandler;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.metric.api.Notification;
import com.tibco.cep.metric.api.NotificationPacket;
import com.tibco.cep.metric.api.PubSubManager;
import com.tibco.cep.metric.api.Subscription;
import com.tibco.cep.metric.runtime.PubSubManagerImpl;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;

//PORT need more sophisticated thread capacity handling, what if we get massive amount of update notifications
public class BEViewsPubSubHandler extends PubSubHandler {

	private ManagementClient managementClient;

	private String serverName;

	private PubSubManager pubSubManager;

	private boolean keepProcessing;

	private Thread updateProcessingThread;

	private ArrayBlockingQueue<UpdateNotification> updateNotificationsQueue;

	// PORT fix init to throw PluginException
	@Override
	protected void init() {		
		try {
			managementClient = (ManagementClient) ManagementUtils.getContext().lookup("management");
		} catch (NamingException e) {
			String msg = messageGenerator.getMessage("pubsubhandler.management.lookup.failure");
			throw new RuntimeException(msg, e);
		}
		serverName = managementClient.getName();
		try {
			pubSubManager = new PubSubManagerImpl(plugIn.getServiceContext().getRuleSession(), CacheClusterProvider.getInstance().getCacheCluster());
			pubSubManager.initialize(serverName, new NotificationHandler());
		} catch (Exception e) {
			String msg = messageGenerator.getMessage("pubsubhandler.initialization.failure", new MessageGeneratorArgs(e, serverName));
			throw new RuntimeException(msg, e);
		}


		updateNotificationsQueue = new ArrayBlockingQueue<UpdateNotification>((Integer)BEViewsProperties.UPDATES_BUFFER_SIZE.getValue(properties));
		keepProcessing = true;
		updateProcessingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (keepProcessing == true) {
					try {
						UpdateNotification updateNotification = updateNotificationsQueue.take();
						if (updateNotification != null) {
							long waitTime = System.currentTimeMillis() - updateNotification.creationTime;
							NotificationPerformanceStats.NOTIFICATIONS_PROCESSING_WAIT_TIME.add(System.currentTimeMillis(), waitTime);
							if (updateNotification.isEmpty() == false) {
								long sTime = System.currentTimeMillis();
								NotificationPacket packet = updateNotification.packet;
								List<String> subscriptions = packet.getSubscriptions();
								try {
									Tuple tuple = createTuple(packet);
									List<Tuple> tuples = Arrays.asList(tuple);
									if (logger.isEnabledFor(Level.DEBUG) == true) {
										logger.log(Level.DEBUG, "Received " + tuples + " against " + subscriptions + " of type " + packet.getType());
									}
									DataSourceUpdateHandlerFactory instance = DataSourceUpdateHandlerFactory.getInstance();
									for (String subscriptionName : subscriptions) {
										DataSourceUpdateHandler[] updateHandlers = instance.getPreCreatedHandlers(subscriptionName);
										for (DataSourceUpdateHandler dataSourceUpdateHandler : updateHandlers) {
											dataSourceUpdateHandler.updateData(updateNotification.updateType, tuples);
										}
									}
								} catch (FatalException e) {
									String msg = messageGenerator.getMessage("pubsubhandler.notification.processing.failure", new MessageGeneratorArgs(e, subscriptions.toString(), packet.getURI()));
									logger.log(Level.WARN, msg);
								} catch (Throwable t) {
									logger.log(Level.WARN, "Throwable caught in updating datasourcehandler", t);
								}
								finally {
									long eTime = System.currentTimeMillis();
									long timeTaken = eTime - sTime;
									NotificationPerformanceStats.NOTIFICATIONS_PROCESSING_TIME.add(System.currentTimeMillis(), timeTaken);
								}
							}
						}
					} catch (InterruptedException e) {
						//PORT should I log and then move on?
						logger.log(Level.WARN, "Thread BEViews Updates Processor interrupted", e);
					}
				}
			}

		}, "BEViews Updates Processor");

//		updateProcessingThread.setDaemon(true);
		updateProcessingThread.start();
	}

	@Override
	protected void subscribe(String subscriptionName, String contextName, MALSourceElement sourceElement, String condition) throws StreamingException {
		if (condition == null) {
			condition = "";
		}
        Entity entity = (Entity) sourceElement.getSource();
		String entityName = entity.getName();
		try {
			Subscription subscription = new Subscription(subscriptionName, entityName, condition);
			pubSubManager.subscribe(subscription);
		} catch (Exception e) {
			String msg = messageGenerator.getMessage("pubsubhandler.addsubscription.failure", new MessageGeneratorArgs(e, subscriptionName, entityName, condition));
			throw new StreamingException(msg, e);
		}
	}

	@Override
	protected void unsubscribe(String subscriptionName) throws StreamingException {
		pubSubManager.unSubscribe(subscriptionName);
	}

	@Override
	public void shutdown() throws NonFatalException {
		updateNotificationsQueue.clear();
		keepProcessing = false;
		updateNotificationsQueue.add(new UpdateNotification());
	}

	private Tuple createTuple(NotificationPacket packet) throws FatalException {
		if (packet.getConcept() != null) {
			return TupleConvertor.getInstance().convertToTuple(packet.getConcept());
		}
		ConceptTupleSchema tupleSchema = (ConceptTupleSchema) TupleSchemaFactory.getInstance().getTupleSchema(EntityCache.getInstance().getEntityByFullPath(packet.getURI()));
		HashMap<String, FieldValue> fieldIdToFieldValueMap = new HashMap<String, FieldValue>();
		int fieldCount = tupleSchema.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField field = tupleSchema.getFieldByPosition(i);
			FieldValue value = null;
			if (field == tupleSchema.getIDField()) {
				value = new FieldValue(field.getFieldDataType(), packet.getId());
			}
			else if (field == tupleSchema.getExtIdField()) {
				value = new FieldValue(field.getFieldDataType(), packet.getExtId());
			}
			else if (field.isArray() == true) {
				value = new FieldValueArray(field.getFieldDataType(), true);
			}
			else {
				value = new FieldValue(field.getFieldDataType(), true);
			}
			fieldIdToFieldValueMap.put(field.getFieldID(), value);
		}
		return new Tuple(tupleSchema, fieldIdToFieldValueMap);
	}

	class NotificationHandler implements Notification {

		@Override
		public void notify(NotificationPacket packet) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Received "+packet);
			}
			boolean offerSucceeded = updateNotificationsQueue.offer(new UpdateNotification(packet));
			if (offerSucceeded == false){
				NotificationPerformanceStats.REFUSED_NOTIFICATIONS_COUNTER.add(System.currentTimeMillis(), 1);
				String msg = messageGenerator.getMessage("pubsubhandler.notification.offer.failure", new MessageGeneratorArgs(null,packet.getSubscriptions().toString(),packet.getURI()));
				logger.log(Level.WARN, msg);
			}
			else {
				NotificationPerformanceStats.ACCEPTED_NOTIFICATIONS_COUNTER.add(System.currentTimeMillis(), updateNotificationsQueue.size());
			}
		}
	}

	class UpdateNotification {

		long creationTime;
		NotificationPacket packet;
		Updateable.UpdateType updateType;

		UpdateNotification() {
			creationTime = System.currentTimeMillis();
		}

		UpdateNotification(NotificationPacket packet) {
			this();
			this.packet = packet;
			switch (this.packet.getType()) {
				case CREATE:
					updateType = Updateable.UpdateType.CREATE;
					break;
				case UPDATE:
					updateType = Updateable.UpdateType.UPDATE;
					break;
				case DELETE:
					updateType = Updateable.UpdateType.DELETE;
					break;
				case UNUSABLE:
					updateType = Updateable.UpdateType.UNUSABLE;
					break;
				default:
					throw new IllegalArgumentException("Unsupported Notification Type["+this.packet.getType()+"]");
			}
		}

		boolean isEmpty() {
			return packet == null;
		}
	}
}
