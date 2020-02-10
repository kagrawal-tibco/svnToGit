package com.tibco.cep.driver.ftl;

import static com.tibco.cep.driver.ftl.FTLConstants.DESTINATION_PROPERTY_APPNAME;
import static com.tibco.cep.driver.ftl.FTLConstants.DESTINATION_PROPERTY_ENDPOINTNAME;
import static com.tibco.cep.driver.ftl.FTLConstants.DESTINATION_PROPERTY_FORMATS;
import static com.tibco.cep.driver.ftl.FTLConstants.DESTINATION_PROPERTY_INSTANCENAME;
import static com.tibco.cep.driver.ftl.FTLConstants.DESTINATION_PROPERTY_MATCH;
import static com.tibco.cep.driver.ftl.FTLConstants.DESTINATION_PROPERTY_DURABLE_NAME;
import static com.tibco.cep.driver.ftl.FTLConstants.FTL_INBOX_NAME;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_FILE;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_STRING;
import static com.tibco.cep.driver.ftl.FTLConstants.CHANNEL_PROPERTY_TRUST_TYPE_EVERYONE;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.driver.ftl.internal.FTLMessageContext;
import com.tibco.cep.driver.ftl.util.FTLUtils;
import com.tibco.cep.driver.ftl.util.FTLUtils.FtlApiType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.DestinationConfig;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.ftl.ContentMatcher;
import com.tibco.ftl.EventQueue;
import com.tibco.ftl.FTL;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Inbox;
import com.tibco.ftl.InboxSubscriber;
import com.tibco.ftl.Message;
import com.tibco.ftl.Publisher;
import com.tibco.ftl.Realm;
import com.tibco.ftl.Subscriber;
import com.tibco.ftl.SubscriberListener;
import com.tibco.ftl.TibProperties;
import com.tibco.ftl.exceptions.FTLAlreadyExistsException;
import com.tibco.xml.data.primitive.ExpandedName;

public class FTLDestination extends AbstractDestination<FTLChannel> {
	private RuleSession session;
	private Logger logger;
	private String appName, instName, endpointName;
	private String match;
	private String durableName;
	//private boolean receive, send, receiveInbox;
	private Realm realm;
	private Publisher pub;
	private Subscriber sub;
	private InboxSubscriber inboxSub;
//	private String formatsType;
	private String formats;
	EventQueue queue_;
	SubscriberListener subListener;
	
	private Properties beProperties;

	public Subscriber getInboxSub() {
		return inboxSub;
	}

	public void setInboxSub(InboxSubscriber inboxSub) {
		this.inboxSub = inboxSub;
	}

	public RuleSession getSession() {
		return session;
	}

	public void setSession(RuleSession session) {
		this.session = session;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getEndpointName() {
		return endpointName;
	}

	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}

	public Realm getRealm() {
		return realm;
	}

	public void setRealm(Realm realm) {
		this.realm = realm;
	}

	public Publisher getPub() {
		return pub;
	}

	public void setPub(Publisher pub) {
		this.pub = pub;
	}

	public Subscriber getSub() {
		return sub;
	}

	public void setSub(Subscriber sub) {
		this.sub = sub;
	}

	public String getFormats() {
		return formats;
	}

	public void setFormats(String formats) {
		this.formats = formats;
	}
	
	public String getMatch() {
		return match;
	}

	public void setMatch(String match) {
		this.match = match;
	}
	
	public String getDurableName() {
		return durableName;
	}

	public void setDurableName(String durableName) {
		this.durableName = durableName;
	}

//	public String getFormatsType() {
//		return formatsType;
//	}
//
//	public void setFormatsType(String formatsType) {
//		this.formatsType = formatsType;
//	}

	public FTLDestination(FTLChannel channel, DestinationConfig config) {
		super(channel, config);
		logger = channel.getLogger();

		Properties props = config.getProperties();
		String receiveStr, sendStr, receiveInboxStr, sendInboxStr;
		appName =channel.getGlobalVariables().substituteVariables( props.getProperty(DESTINATION_PROPERTY_APPNAME)).toString();
		instName = channel.getGlobalVariables().substituteVariables( props.getProperty(DESTINATION_PROPERTY_INSTANCENAME)).toString();
		endpointName = channel.getGlobalVariables().substituteVariables( props.getProperty(DESTINATION_PROPERTY_ENDPOINTNAME)).toString();
		match = channel.getGlobalVariables().substituteVariables( props.getProperty(DESTINATION_PROPERTY_MATCH)).toString();
		durableName = channel.getGlobalVariables().substituteVariables( props.getProperty(DESTINATION_PROPERTY_DURABLE_NAME)).toString();
		beProperties = channel.getBeProperties();

//		formatsType = (String)props.getProperty(DESTINATION_PROPERTY_MESSAGE_TYPE);
		formats = (String) props.getProperty(DESTINATION_PROPERTY_FORMATS);
	}

	public void connect() throws Exception {
			logger.log(Level.INFO, "Connect Destination: " + getURI());
			FTLChannel channel = (FTLChannel) this.getChannel();
			String realmServer = channel.getRealmServer();
			String username = channel.getUsername();
			String password = channel.getPassword();
			
			
			TibProperties props = FTL.createProperties();
			props.set(Realm.PROPERTY_STRING_USERNAME, username);
			props.set(Realm.PROPERTY_STRING_USERPASSWORD, password);
			
			Boolean useSsl = channel.isUseSsl();
			if(useSsl){
				String trustType = channel.getTrustType();
				if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_FILE)){
					String trustFileLocation = channel.getTrustFileLocation();
					props.set(Realm.PROPERTY_STRING_TRUST_FILE, trustFileLocation);
					props.set(Realm.PROPERTY_LONG_TRUST_TYPE, Realm.HTTPS_CONNECTION_USE_SPECIFIED_TRUST_FILE);
				}else if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_STRING)){
					String trustString = channel.getTrustFileString();
					props.set(Realm.PROPERTY_STRING_TRUST_PEM_STRING, trustString);
					props.set(Realm.PROPERTY_LONG_TRUST_TYPE, Realm.HTTPS_CONNECTION_USE_SPECIFIED_TRUST_STRING);
				}else if(trustType.equals(CHANNEL_PROPERTY_TRUST_TYPE_EVERYONE)){
					props.set(Realm.PROPERTY_LONG_TRUST_TYPE, Realm.HTTPS_CONNECTION_TRUST_EVERYONE);
				}
			}
			
			
			if (instName != null && !"".equals(instName)) {
				try {
					props.set(Realm.PROPERTY_STRING_APPINSTANCE_IDENTIFIER,
							instName);
				} catch (FTLException e) {
					logger.log(Level.WARN, "Issue while setting property " + Realm.PROPERTY_STRING_APPINSTANCE_IDENTIFIER);
				}
			}
			String secondary = channel.getSecondary();
			if (secondary != null && !"".equals(secondary)) {
				props.set(Realm.PROPERTY_STRING_SECONDARY_SERVER, secondary);
			}
			
			logger.log(Level.INFO, "Primary Server: " + realmServer + ", Secondary server: " + secondary );
			try{
				FTLUtils.loadOverridenProperties(beProperties, props, FtlApiType.REALM, channel.getURI(), this.uri, logger);
				realm = FTL.connectToRealmServer(realmServer, appName, props);
				logger.log(Level.INFO, "Connected to FTL server: ");
			}catch (Exception e) {
				logger.log(Level.INFO, "Failed to connect to FTL server: ");
				throw e;
			}
			props.destroy();
	}

	public void bind(RuleSession session) throws Exception {
		this.session = session;
		TibProperties props = FTL.createProperties();
		FTLUtils.loadOverridenProperties(beProperties, props, FtlApiType.EVENT_QUEUE, channel.getURI(), this.uri, logger);
		final EventQueue queue = realm.createEventQueue(props);
		queue_ = queue;

		subListener = new SubscriberListener() {
			public void messagesReceived(List<Message> messages, EventQueue paramEventQueue) {
				int i;
				int msgNum = messages.size();
				for (i = 0; i < msgNum; i++) {
					onMessage(messages.get(i).mutableCopy());
				}
			}
		};

		ContentMatcher  cm = null;
		if(match != null && !"".equals(match)) {
			 cm = realm.createContentMatcher(match);
		}
		TibProperties propsSubs = FTL.createProperties();
		if(durableName!=null && !durableName.equals("")){
			propsSubs.set(Subscriber.PROPERTY_STRING_DURABLE_NAME, durableName);
		}
		FTLUtils.loadOverridenProperties(beProperties, propsSubs, FtlApiType.SUBSCRIBER, channel.getURI(), this.uri, logger);
		sub = realm.createSubscriber(endpointName, cm, propsSubs);
		//BE-26500: Moved to start() method, to enable start() after stop()
		//queue.addSubscriber(sub, subListener);

		Thread t = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						queue.dispatch();
					} catch (FTLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				// TODO Auto-generated method stub
				logger.log(Level.ERROR, "Exception:" + e.getMessage());
			}
		});
		t.start();
		
		super.bind(session);
	}
	
	@Override
	public void suspend() {
		try {
			queue_.removeSubscriber(sub);
		} catch (FTLException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			this.logger.log(Level.DEBUG, "Cannot suspend destination - check if the destination is added for this agent - " + this.getURI());
		}
		super.suspend();
	}
	
	@Override
	public void resume() {
		try {
			if(queue_!=null && sub!=null && subListener!=null){
				queue_.addSubscriber(sub, subListener);
				this.logger.log(Level.DEBUG, "Resumed - " + this.getURI());
			}
		} catch (FTLAlreadyExistsException e) {
			this.logger.log(Level.DEBUG, "Already resumed/binded - " +  this.getURI());
		}catch (FTLException e) {
			e.printStackTrace();
		}
		super.resume();
	}

	public void start(int mode) throws Exception {
		logger.log(Level.INFO, "Start Destination: " + getURI());

		//If destination is not bound to rule-session, subscriber & listener are not initialized
		if(sub != null && subListener != null) {
			queue_.addSubscriber(sub, subListener);
		}
		
		//Realm does not have an api to check if published is created for a endpoint
		//Hence close previously created publisher & create new
		if(pub != null) {
			pub.close();
		}
		TibProperties props = FTL.createProperties();
		FTLUtils.loadOverridenProperties(beProperties, props, FtlApiType.PUBLISHER , channel.getURI(), this.uri, logger);
		pub = realm.createPublisher(endpointName, props);
	}
	
    public void stop() {
		logger.log(Level.INFO, "Stop Destination: " + getURI());
		
		try {
			//If destination is not bound to rule session, subscriber is not initialized
			if(sub != null) {
				queue_.removeSubscriber(sub);
			}
		} catch (FTLException e) {
			logger.log(Level.ERROR, e, e.getMessage());
		}catch (NullPointerException e) {
			this.logger.log(Level.DEBUG, "Cannot stop destination - check if the destination is added for this agent - " + this.getURI());
		}
		super.stop();
    }

	public void onMessage(Message msg) {
		EventContext ctx = new FTLMessageContext(this, msg);
		try {
			session.getTaskController().processEvent(this, null, ctx);
			// Count only if this server received the message.
			this.getStats().addEventIn();
		} catch (Throwable e) {
			logger.log(Level.ERROR, e, "Handle message failed.");
		}
	}

	public int send(SimpleEvent event, @SuppressWarnings("rawtypes") Map overrideData) throws Exception {
		//BE-26500: Messages should not be send if channel is stopped
        if ((channel.getState() == Channel.State.CONNECTED) || (channel.getState() == Channel.State.STARTED)) {
			Message msg = (Message) serializer.serialize(event, new DefaultSerializationContext(session, this));
			pub.send(msg);
	
			return super.send(event, overrideData);
        } else {
            this.channel.getLogger().log(Level.ERROR, "Channel in an invalid state: %s", this.channel.getState());
            return -1;
        }
	}

	public void close() {
		try {
			if (pub != null) {
				channel.getLogger().log(Level.INFO, "Start to close Publisher at " + endpointName);
				pub.close();
			}
			if (sub != null) {
				channel.getLogger().log(Level.INFO, "Start to close Subscriber at " + endpointName);
				sub.close();
			}
			if (inboxSub != null) {
				channel.getLogger().log(Level.INFO, "Start to close Subscriber at " + endpointName);
				inboxSub.close();
			}
			if (realm != null) {
				channel.getLogger().log(Level.INFO, "Start to close Realm at " + endpointName);
				realm.close();
			}
		} catch (Exception e) {
			channel.getLogger().log(Level.ERROR, e, "Close be ftl failed: " + endpointName);
		}
	}

	protected void destroy(RuleSession arg0) throws Exception {
		try {
			if (pub != null) {
				channel.getLogger().log(Level.INFO, "Start to destroy Publisher at " + endpointName);
				pub.close();
			}
			if (sub != null) {
				channel.getLogger().log(Level.INFO, "Start to destroy Subscriber at " + endpointName);
				sub.close();
			}
			if (inboxSub != null) {
				channel.getLogger().log(Level.INFO, "Start to destroy Subscriber at " + endpointName);
				sub.close();
			}
			if (realm != null) {
				channel.getLogger().log(Level.INFO, "Start to destroy Realm at " + endpointName);
				realm.close();
			}
		} catch (Exception e) {
			channel.getLogger().log(Level.ERROR, e, "destroy be ftl failed: " + endpointName);
		} finally {
			pub = null;
			sub = null;
			inboxSub = null;
			realm = null;
		}
	}
	
	public Object requestEvent(SimpleEvent event, ExpandedName responseEventURI, String serializerClass, long timeout, Map overrideData) throws Exception {
		//BE-26837: If channel is not connected or started, return
        if ((getChannel().getState() != Channel.State.CONNECTED) && (getChannel().getState() != Channel.State.STARTED)) {
            this.getLogger().log(Level.ERROR, "Channel in an invalid state: %s", getChannel().getState());
            return null;
        }
		
		if(overrideData!=null){
			throw new Exception("OverrideData not in scope");
		}
		
		Inbox box = null;
		InboxSubscriber inSubscriber = null;
		Message msg = null;
		try{
			Channel.Destination self = this;
			final SimpleEvent[] recEvent = new SimpleEvent[1];
			
			msg = (Message) serializer.serialize(event, new DefaultSerializationContext(session, this));
			
			inSubscriber = realm.createInboxSubscriber(endpointName);
			box = inSubscriber.getInbox(); 
			msg.setInbox(FTL_INBOX_NAME, box);
			
			Object lock = new Object();
			//EventQueue queue = realm.createEventQueue();
			queue_.addSubscriber(inSubscriber, new SubscriberListener() {
				
				@Override
				public void messagesReceived(List<Message> messages, EventQueue eQ) {
					
					try{
						synchronized (lock) {
							recEvent[0] = serializer.deserialize(messages.get(0), new DefaultSerializationContext(session, self));
							lock.notify();
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			pub.send(msg);
			
			synchronized (lock) {
				if(timeout == -1){
					lock.wait();
				}else{
					lock.wait(timeout);
				}
			}
			
			return recEvent[0];
		}catch(Exception e){
			logger.log(Level.ERROR, e, "Problem while processing requestEvent");
			throw e;
		}finally {
			if(inSubscriber!=null)
				queue_.removeSubscriber(inSubscriber);
			if(inSubscriber!=null)
				inSubscriber.close();
			msg.destroy();
		}
		
	}
	
	/*private String readFile(String fileLocation){
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		StringBuffer stringBuffer = null;
		try {
			File file = new File(fileLocation);
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			return stringBuffer.toString();
		} catch (IOException e) {
			logger.log(Level.ERROR, e, "Issue while reading file " + fileLocation);
			return null;
		}finally{
			try {
				bufferedReader.close();
				if(fileReader!=null){
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

}
