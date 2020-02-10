package com.tibco.cep.functions.channel.ftl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.ftl.ContentMatcher;
import com.tibco.ftl.EventQueue;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Message;
import com.tibco.ftl.MessageFieldRef;
import com.tibco.ftl.Realm;
import com.tibco.ftl.Subscriber;
import com.tibco.ftl.SubscriberListener;
import com.tibco.ftl.TibDateTime;
import com.tibco.ftl.TibProperties;

/*
 * Author: Suresh Subramani / Date: 1/28/13 / Time: 6:51 PM
 */
public class FTLSubscriber implements SubscriberListener {

	Realm realm;
	String endPointName;
	String ruleFunctionName;
	String requestEventType;
	boolean executeRules;
	Subscriber nativeSubscriber;
	EventQueue eventQueue;
	ExecutorService executorService;
	String contentMatcher;
	TibProperties subscriberProps;
	private RuleSessionImpl ruleSession;
	private RuleFunction ruleFunc;
	private String eventUri;
	private String messageFormatType;
	RuleSession rs;
	Logger logger = LogManagerFactory.getLogManager().getLogger(FTLSubscriber.class);
	ExecutorService workerService;
	int numThreads = 1;

	public FTLSubscriber(Object realm, String endPointName, Object subscriberProps) {
		this.realm = (Realm) realm;
		this.endPointName = endPointName;
		this.subscriberProps = (TibProperties) subscriberProps;
		executorService = Executors.newSingleThreadExecutor();
	}
	
	

	public int getNumThreads() {
		return numThreads;
	}



	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}



	public String getRuleFunctionName() {
		return ruleFunctionName;
	}

	public void setRuleFunctionName(String ruleFunctionName) {
		this.ruleFunctionName = ruleFunctionName;
	}

	// public String getRequestEventType() {
	// return requestEventType;
	// }
	//
	// public void setRequestEventType(String requestEventType) {
	// this.requestEventType = requestEventType;
	// }

	public boolean isExecuteRules() {
		return executeRules;
	}

	public void setExecuteRules(boolean executeRules) {
		this.executeRules = executeRules;
	}

	// public Subscriber getNativeSubscriber() {
	// return nativeSubscriber;
	// }
	//
	// public void setNativeSubscriber(Subscriber nativeSubscriber) {
	// this.nativeSubscriber = nativeSubscriber;
	// }
	//
	// public String getContentMatcher() {
	// return contentMatcher;
	// }
	//
	// public void setContentMatcher(String contentMatcher) {
	// this.contentMatcher = contentMatcher;
	// }

	public void startListening() throws Exception {
		ContentMatcher cm = this.contentMatcher == null ? null : this.realm.createContentMatcher(this.contentMatcher);
		this.nativeSubscriber = this.realm.createSubscriber(this.endPointName, cm, this.subscriberProps);
		this.eventQueue = realm.createEventQueue(this.subscriberProps);
		this.eventQueue.addSubscriber(this.nativeSubscriber, this);
		ruleSession = (RuleSessionImpl) getDefaultRuleSession();
		workerService = Executors.newFixedThreadPool(numThreads);

		if (ruleSession == null)
			throw new RuntimeException("No Rulesession configured...");
		if (this.ruleFunctionName == null)
			throw new RuntimeException("No Rulefunction configured...");

		ruleFunc = ruleSession.getRuleFunction(this.ruleFunctionName);

		this.executorService.submit(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						FTLSubscriber.this.eventQueue.dispatch();
					} catch (FTLException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void messagesReceived(final List<Message> messages, final EventQueue eventQueue) {
		for (Message msg : messages) {
			try {
				final SimpleEvent se = FTLSubscriber.this.requestEventType == null ? null : transform2Event(msg);
				final Object[] args = new Object[] { se == null ? msg : se };
				workerService.execute(new Runnable() {

					@Override
					public void run() {
						if (executeRules)
							ruleSession.preprocessPassthru(ruleFunc, se);
						else
							ruleSession.invokeFunction(ruleFunc, args, true);
						

					}
				});
			} catch (Exception ex) {
				logger.log(Level.ERROR, ex, "Failed to process message:"+msg);
			}
			
		}
		

	}

	private RuleSession getDefaultRuleSession() {
		RuleSession[] ruleSessions = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions();
		if (ruleSessions.length > 0)
			return ruleSessions[0];
		return null;
	}

	public void close() {
		try {
			this.eventQueue.destroy();
			this.nativeSubscriber.close();
			this.executorService.shutdown();
			this.workerService.shutdown();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void bind(String eventUri, String messageFormatType, Object contentMap) {
		Map<String, Object> contentMatch = Map.class.cast(contentMap);
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (contentMatch != null && !contentMatch.isEmpty()) {

			// int i = 0;
			for (String entry : contentMatch.keySet()) {
				Object val = contentMatch.get(entry);
				if (val instanceof String) {
					sb.append("\"").append(entry).append("\":").append(String.class.cast("\"" + val + "\""));
				} else if (val instanceof Number) {
					sb.append("\"").append(entry).append("\":").append(contentMatch.get(entry));
				} else if (val instanceof Boolean) {
					sb.append("\"").append(entry).append("\":").append(Boolean.class.cast(val));
				} else if (val instanceof Calendar) {
					TibDateTime td = new TibDateTime();
					td.setFromMillis(((Calendar) val).getTimeInMillis());
					sb.append("\"").append(entry).append("\":").append(td.toString());
				} else {
					sb.append("\"").append(entry).append("\":").append(contentMatch.get(entry).toString());
				}
				sb.append(",");
			}
			// sb.append("}");
		}
		rs = RuleSessionManager.getCurrentRuleSession();
		final Logger logger = rs.getRuleServiceProvider().getLogger(FTLSubscriber.class);
		logger.log(Level.INFO, String.format("Binding event : %s, messageFormat : %s", eventUri, messageFormatType));
		this.eventUri = eventUri;
		this.messageFormatType = messageFormatType;
		this.requestEventType = eventUri;
		Ontology ontology = rs.getRuleServiceProvider().getProject().getOntology();
		Event eventType = ontology.getEvent(eventUri);

		if (this.contentMatcher == null) {
			// StringBuilder sb = new StringBuilder();
			// sb.append("{");
			int i = 0;
			for (Object prop : eventType.getAllUserProperties()) {
				EventPropertyDefinition evp = (EventPropertyDefinition) prop;
				String name = evp.getPropertyName();
				if (contentMatch != null && !contentMatch.isEmpty() && !contentMatch.containsKey(name)) {
					if (i > 0 || sb.length() <= 0)
						sb.append(",");
					sb.append("\"").append(evp.getPropertyName()).append("\"").append(":").append("true");
					i++;
				}
			}
			sb.append("}");

			this.contentMatcher = sb.toString();
			logger.log(Level.INFO, this.contentMatcher);
		}

	}

	protected SimpleEvent transform2Event(Message msg) throws Exception {
		SimpleEvent se = (SimpleEvent) ruleSession.getRuleServiceProvider().getTypeManager().createEntity(requestEventType);

		for (String propName : se.getPropertyNames()) {
			MessageFieldRef mfr = MessageHelper.getOrCreateRef(this.realm, propName);

			// MessageFieldRef mfr =
			// MessageHelper.getOrCreateRef(this.realm,ExpandedName.makeName(se.getType(),
			// propName).toString());

			int fieldType = msg.getFieldType(mfr);

			// int fieldType = msg.getFieldType(propName);
			switch (fieldType) {
			case Message.TIB_FIELD_TYPE_LONG:
				se.setProperty(propName, msg.getLong(mfr));
				// se.setProperty(propName, msg.getLong(propName));
				break;

			case Message.TIB_FIELD_TYPE_DOUBLE:
				se.setProperty(propName, msg.getDouble(mfr));
				// se.setProperty(propName, msg.getDouble(propName));
				break;

			case Message.TIB_FIELD_TYPE_STRING:
				se.setProperty(propName, msg.getString(mfr));
				// se.setProperty(propName, msg.getString(propName));
				break;

			case Message.TIB_FIELD_TYPE_DATETIME:
				TibDateTime tdt = msg.getDateTime(mfr);
				// TibDateTime tdt = msg.getDateTime(propName);
				Calendar cal = new GregorianCalendar();
				cal.setTime(tdt.toDate());
				se.setProperty(propName, cal);
				break;

			case Message.TIB_FIELD_TYPE_OPAQUE:
			default:
				break;
			}
		}
		// To-DO
		MessageFieldRef mfr = MessageHelper.getOrCreateRef(this.realm, EventPayload.PAYLOAD_PROPERTY);

		int fieldType = msg.getFieldType(mfr);
		if (fieldType == Message.TIB_FIELD_TYPE_STRING) {
			// RuleSession rs = RuleSessionManager.getCurrentRuleSession();
			final Logger logger = rs.getRuleServiceProvider().getLogger(FTLSubscriber.class);
			logger.log(Level.DEBUG, String.format("Setting message payload : %s", msg.getString(mfr)));
			RuleServiceProvider provider = ruleSession.getRuleServiceProvider();
			EventPayload payload = provider.getTypeManager().getPayloadFactory().createPayload(se.getExpandedName(), msg.getString(mfr));
			se.setPayload(payload);
		}

		return se;
	}

	public String toString() {
		return "{" + "\n\t End Point Name : " + this.endPointName + "\n\t Event Uri : " + this.eventUri + "\n\t Message Format Type Name : "
				+ this.messageFormatType + "\n}";
	}
}
