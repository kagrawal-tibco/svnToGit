package com.tibco.cep.test.ems;

/* 
 * Copyright 2001-2011 TIBCO Software Inc.
 * All rights reserved.
 */

import java.io.*;
import java.util.*;
import javax.jms.*;
import javax.naming.*;

public class BEJMSMessageSimulator implements Runnable {
	// parameters
	private String serverUrl = "tcp://localhost:7222";
	private String username = null;
	private String password = null;
	private String destName = "topic.sample";
	private String payloadFile = null;
	private String factoryName = null;
	private boolean useTopic = true;
	private boolean uniqueDests = false;
	private boolean compression = false;
	private int msgRate = 0;
	private int txnSize = 0;
	private int count = 10000;
	private int runTime = 0;
	private int msgSize = 0;
	private int threads = 1;
	private int connections = 1;
	private int deliveryMode = DeliveryMode.NON_PERSISTENT;
	private String messageEvent = null;
	private String startEvent = null;
	private String endEvent = null;
	private String messageInputFile = null;
	private Vector<MsgProperty> properties = new Vector<MsgProperty>(10);
	private HashMap<String, MessageTask> taskMap = new HashMap<String, MessageTask>();
	private List<String> executionPlan = new LinkedList<String>();

	final private String EVENT_NAMESPACE = "www.tibco.com/be/ontology";

	////// RECEIVE functionality
	private String durableName = null;
	private String selector = null;
	private int ackMode = Session.AUTO_ACKNOWLEDGE;
	// variables
	private int nameIter;
	private int recvCount;
	/////////////////////////////

	private class MsgProperty {
		String name;
		String type;
		String value;
		boolean autoIncr = false;
		boolean mustMatch = true;
		public String toString() {
			return new String("PROPERTY name:" + name + " type:" + type + " value:"
					+ value + " autoIncr:" + autoIncr);
		}
	}

	private class MessageTask {
		String name;
		String description;
		String transport;
		boolean send;
		String targetType;
		String targetDest;
		long count;
		String event;
		boolean eventMustMatch = true;
		List<MsgProperty> properties;
		String logger;
		String command;
		public String toString() {
			return new String("Name:" + name + 
					" transport:" + transport + " targetType:" + targetType +
					" targetDest:" + targetDest + " count:" + count + " event:" + event +
					" shellcmd:" + command + " property list:" + properties.size());
		}
	}

	// variables
	private int connIter;
	private int destIter;
	private int sentCount;
	private long startTime;
	private long endTime;
	private long elapsed;
	private boolean stopNow;
	private Vector connsVector;

	/**
	 * Constructor
	 * 
	 * @param args the command line arguments
	 */
	public BEJMSMessageSimulator(String[] args)  {
		parseArgs(args);

		try {
			tibjmsUtilities.initSSLParams(serverUrl,args);

			// print parameters
			System.err.println();
			System.err.println("------------------------------------------------------------------------");
			System.err.println("TIBCO BusinessEvents EMS Message Manager Information");
			System.err.println("------------------------------------------------------------------------");
			System.err.println("Server....................... " + serverUrl);
			System.err.println("User......................... " + username);
			if (this.messageInputFile == null)
				System.err.println("Destination.................. " + destName);
			if (this.messageInputFile == null)
				System.err.println("Message Size................. " + (payloadFile != null ? payloadFile : String.valueOf(msgSize)));
			if (this.messageInputFile == null && count > 0)
				System.err.println("Count........................ " + count);
			if (runTime > 0)
				System.err.println("Duration..................... " + runTime);
			System.err.println("Production Threads........... " + threads);
			System.err.println("Production Connections....... " + connections);
			System.err.println("DeliveryMode................. " + deliveryModeName(deliveryMode));
			
            System.err.println("Ack Mode..................... " + ackModeName(ackMode));
            System.err.println("Durable...................... " + (durableName != null));
            System.err.println("Selector..................... " + selector);
			
			
			System.err.println("Compression.................. " + compression);
			if (messageInputFile != null) {
				System.err.println("Message Input File........... " + messageInputFile);
			}
			if (messageEvent != null) {
				System.err.println("Message event................ " + messageEvent);
			}
			if (startEvent != null) {
				System.err.println("Start event.................. " + startEvent);
			}
			if (endEvent != null) {
				System.err.println("End event.................... " + endEvent);
			}
			for (MsgProperty prop : properties) {
				System.err.println("Message property");
				System.err.println("\tName................. " +	prop.name);
				System.err.println("\tValue................ "+ prop.value);
				System.err.println("\tType................. " + prop.type);
				System.err.println("\tAuto Increment....... " + prop.autoIncr);
			}
			if (msgRate > 0)
				System.err.println("Message Rate................. " + msgRate);
			if (txnSize > 0)
				System.err.println("Transaction Size............. " + txnSize);
			if (factoryName != null)
				System.err.println("Connection Factory........... " + factoryName);
			System.err.println("------------------------------------------------------------------------");
			System.err.println();

			// lookup the connection factory
			ConnectionFactory factory = null;
			if (factoryName != null)
			{
				tibjmsUtilities.initJNDI(serverUrl);
				factory = (ConnectionFactory) tibjmsUtilities.lookup(factoryName);
			}
			else 
			{
				factory = new com.tibco.tibjms.TibjmsConnectionFactory(serverUrl);
			}

			// create the connections
			connsVector = new Vector(connections);
			for (int i=0;i<connections;i++)
			{
				Connection conn = factory.createConnection(username, password);
				conn.start();
				connsVector.add(conn);
			}

			// create the producer threads
			Vector tv = new Vector(threads);
			for (int i=0;i<threads;i++)
			{
				Thread t = new Thread(this);
				tv.add(t);
				t.start();
			}

			// run for the specified amout of time
			if (runTime > 0)
			{
				try 
				{
					Thread.sleep(runTime * 1000);
				} 
				catch (InterruptedException e) {}

				// ensure producer threads stop now
				stopNow = true;
				for (int i=0;i<threads;i++)
				{
					Thread t = (Thread)tv.elementAt(i);
					t.interrupt();
				}
			}

			// wait for the producer threads to exit
			for (int i=0;i<threads;i++)
			{
				Thread t = (Thread)tv.elementAt(i);
				try 
				{
					t.join();
				} 
				catch (InterruptedException e) {}
			}

			// close the connections
			for (int i=0;i<connections;i++) 
			{
				Connection conn = (Connection)connsVector.elementAt(i);
				conn.close();
			}

			// print performance
			System.err.println(getPerformance(true));
		}
		catch (NamingException e)
		{
			e.printStackTrace();
		}
		catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Returns a connection.
	 */
	private synchronized Connection getConnection()
	{
		Connection connection = (Connection)connsVector.elementAt(connIter++);
		if (connIter == connections)
			connIter = 0;
		return connection;
	}

	/**
	 * Returns a destination.
	 */
	private synchronized Destination getDestination(Session s) throws JMSException
	{
		if (useTopic)
		{
			if (!uniqueDests)
				return s.createTopic(destName);
			else
				return s.createTopic(destName + "." + ++destIter);
		}
		else
		{
			if (!uniqueDests)
				return s.createQueue(destName);
			else
				return s.createQueue(destName + "." + ++destIter);
		}
	}

	/////////////
	/**
	 * Returns a unique subscription name if durable subscriptions are specified.
	 */
	private synchronized String getSubscriptionName() {
		if (durableName != null)
			return durableName + ++nameIter;
		else
			return null;
	}

	/**
	 * Update the total receive count.
	 */
	private synchronized void countReceives(int count) {
		recvCount += count;
	}

	/**
	 * Convert acknowledge mode to a string.
	 */
	private static String ackModeName(int ackMode) {
		switch(ackMode)
		{
		case Session.DUPS_OK_ACKNOWLEDGE:      
			return "DUPS_OK_ACKNOWLEDGE";
		case Session.AUTO_ACKNOWLEDGE:         
			return "AUTO_ACKNOWLEDGE";
		case Session.CLIENT_ACKNOWLEDGE:       
			return "CLIENT_ACKNOWLEDGE";
		case com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE:         
			return "EXPLICIT_CLIENT_ACKNOWLEDGE";
		case com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE:         
			return "EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE";
		case com.tibco.tibjms.Tibjms.NO_ACKNOWLEDGE:     
			return "NO_ACKNOWLEDGE";
		default:                                         
			return "(unknown)";
		}
	}

	/////////////////////

	/**
	 * Update the total sent count.
	 */
	private synchronized void countSends(int count)
	{
		sentCount += count;
	}

	/**
	 * The producer thread's run method.
	 */
	public void run()
	{
		int msgCount = 0;
		MsgRateChecker msgRateChecker = null;

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}

		try {
			// get the connection
			Connection connection = getConnection();

			// create a session
			Session session = connection.createSession(txnSize > 0, ackMode);

			// get the destination
			// TODO: read destination from MessageTask!
			Destination destination = getDestination(session);

			// create the producer
			MessageProducer msgProducer = session.createProducer(destination);

			// set the delivery mode
			msgProducer.setDeliveryMode(deliveryMode);

			// performance settings
			msgProducer.setDisableMessageID(true);
			msgProducer.setDisableMessageTimestamp(true);

			// initialize message rate checking
			if (msgRate > 0)
				msgRateChecker = new MsgRateChecker(msgRate);

			List<MessageTask> msgTaskList = null;
			if (this.messageInputFile != null) {
				msgTaskList = this.parseFileMessages();
				// recreate message producer, used only for start/end events
				msgProducer = session.createProducer(getDestination(session));
				msgProducer.setDeliveryMode(deliveryMode);
				msgProducer.setDisableMessageID(true);
				msgProducer.setDisableMessageTimestamp(true);
			}
			else {
				MessageTask msgTask = new MessageTask();
				msgTask.name = "<DEFAULT>";
				msgTask.transport = "ems";
				msgTask.targetType = (useTopic ? "topic" : "queue");
				msgTask.targetDest = this.destName;
				msgTask.event = messageEvent;
				msgTask.count = count;
				msgTask.properties = properties;
				msgTaskList = new LinkedList();
				msgTaskList.add(msgTask);
			}

			startTiming();            

			// OVERALL START EVENT
			if (startEvent != null) {
				TextMessage startMsg = createBoundaryMessage(session, startEvent);
				startMsg.setBooleanProperty("isStart", true);
				startMsg.setBooleanProperty("isEnd", false);
				msgProducer.send(startMsg);
			}

			// go through all message tasks and process them
			msgCount = processTaskList(msgTaskList, session, msgRateChecker);

			// OVERALL END EVENT 
			if (endEvent != null) {
				TextMessage endMsg = createBoundaryMessage(session, endEvent);
				endMsg.setBooleanProperty("isStart", false);
				endMsg.setBooleanProperty("isEnd", true);
				msgProducer.send(endMsg);
			}

			// commit any remaining messages
			if (txnSize > 0)
				session.commit();

		}
		catch (JMSException e)
		{
			if (!stopNow)
				e.printStackTrace();
		}

		stopTiming();

		countSends(msgCount);
	}

	private int processTaskList(List<MessageTask> msgTaskList,
			Session session,
			MsgRateChecker msgRateChecker) throws JMSException {

		int totalMsgCount = 0;
		int msgCount = 0;
		int matchedMsgCount = 0;

		Destination destination = null;
		MessageProducer msgProducer = null;
		MessageConsumer msgConsumer = null;
		String eventPath = null;
		String eventName = null;
		List<MessageTask> tasksToBeExecuted = null;

		if (this.executionPlan.size() > 0) {
			tasksToBeExecuted = new LinkedList<MessageTask>();
			MessageTask currTask;
			for (String taskName : this.executionPlan) {

				Scanner lineScanner = new Scanner(taskName);
				lineScanner.useDelimiter("\\*");
				String currToken = lineScanner.next();
				int repeat = 1;
				if (lineScanner.hasNext()) {
					repeat = lineScanner.nextInt();
				}
				currTask = (MessageTask) this.taskMap.get(currToken);
				if (currTask == null) {
					System.err.println("Cannot find task " + currToken + " defined.");
				}
				else {
					while (repeat-- > 0) {
						tasksToBeExecuted.add(currTask);
					}
				}
			}
		}
		else {
			tasksToBeExecuted = msgTaskList;
		}

		for (MessageTask task : tasksToBeExecuted) {
			System.out.println("Executing task: " + task.name);
			msgCount = 0;
			matchedMsgCount = 0;

			// create the message
			Message msg = createMessage(session);
			if (compression) {
				msg.setBooleanProperty("JMS_TIBCO_COMPRESS", true);
			}

			// create destination from task
			if (task.targetType.compareTo("queue")==0) {
				destination = session.createQueue(task.targetDest);
			}
			else if (task.targetType.compareTo("topic")==0) {
				destination = session.createTopic(task.targetDest);
			}

			if (task.send) {
				// create message producer
				msgProducer = session.createProducer(destination);
				msgProducer.setDeliveryMode(deliveryMode);
				msgProducer.setDisableMessageID(true);
				msgProducer.setDisableMessageTimestamp(true);
			}
			else {
				// create message consumer
				String subscriptionName = getSubscriptionName();
				if (subscriptionName == null)
					msgConsumer = session.createConsumer(destination, selector);
				else
					msgConsumer = session.createDurableSubscriber(
							(Topic)destination, subscriptionName, selector, false);
			}

			// figure out event
			if (task.event != null) {
				int index = task.event.lastIndexOf("/");
				if (index < 0 || index >= task.event.length()) {
					usage();
					continue;
				}
				else {
					eventName = task.event.substring(index+1, task.event.length());
					eventPath = EVENT_NAMESPACE + task.event.substring(0, index) + "/" + eventName;
				}
				msg.setStringProperty("_nm_", eventName);
				msg.setStringProperty("_ns_", eventPath);
			}   

			if (task.send) {
				// publish messages
				while ((task.count == 0 || msgCount < (task.count/threads)) && !stopNow)
				{
					// TODO: performance improvement: separate property types so we don't 
					// check what type they are inside the loop, thus speeding up the msg pumping...
					for (MsgProperty prop : task.properties) {
						if (prop.type.compareTo("String") == 0) {
							if (prop.autoIncr) {
								msg.setStringProperty(prop.name, prop.value + String.valueOf(msgCount));
							}
							else {
								msg.setStringProperty(prop.name, prop.value);
							}
						}
						else if (prop.type.compareTo("Int") == 0) {
							if (prop.autoIncr) {
								msg.setIntProperty(prop.name, Integer.parseInt(prop.value) + msgCount);
							}
							else {
								msg.setIntProperty(prop.name, Integer.parseInt(prop.value));
							}
						}
						else if (prop.type.compareTo("Double") == 0) {
							if (prop.autoIncr) {
								msg.setDoubleProperty(prop.name, Double.parseDouble(prop.value) + msgCount);
							}
							else {
								msg.setDoubleProperty(prop.name, Double.parseDouble(prop.value));
							}
						}
						else if (prop.type.compareTo("Boolean") == 0) {
							msg.setBooleanProperty(prop.name, Boolean.parseBoolean(prop.value));
						}
					}

					msgProducer.send(msg);

					msgCount++;

					// commit the transaction if necessary
					if (txnSize > 0 && msgCount % txnSize == 0)
						session.commit();

					// check the message rate
					if (msgRate > 0)
						msgRateChecker.checkMsgRate(msgCount);
				}    		
				totalMsgCount += msgCount;
			}
			else {
				// receive messages
				boolean matched = true;
				PrintStream ps = null;
				if (task.logger.compareTo("stdout")==0) {
					ps = System.out;
				}
				else if (task.logger.compareTo("stderr")==0) {
					ps = System.err;
				}
				else {
					try {
						ps = new PrintStream(task.logger);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				while ((task.count == 0 || msgCount < (task.count/threads)) && !stopNow) {
					matched = true;
					msg = msgConsumer.receive();

					if (msg == null)
						break;

					if (msgCount == 0)
						startTiming();

					msgCount++;

					// acknowledge the message if necessary
					if (ackMode == Session.CLIENT_ACKNOWLEDGE ||
							ackMode == com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE ||
							ackMode == com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE)
						msg.acknowledge();

					// commit the transaction if necessary
					if (txnSize > 0 && msgCount % txnSize == 0)
						session.commit();

					// force the decompression of compressed messages
					if (msg.getBooleanProperty("JMS_TIBCO_COMPRESS"))
						((BytesMessage) msg).getBodyLength();

					// check first if we matched the event
					if (task.eventMustMatch) {
						if (!msg.propertyExists("_nm_")) {
							// System.out.println("Can't find event!!");
							matched = false;
							continue;
						}
						else {
							// hack (for now)
							if (task.event.endsWith(msg.getStringProperty("_nm_"))) {
								if (task.logger != null && ps != null) {
									ps.println("MSG RECEIVED: " + msg.getJMSDestination() +
											" nm: " + msg.getStringProperty("_nm_"));
								}
							}
							else {
//								System.out.println("Event did not match: " + task.event +
//									" and " + msg.getStringProperty("_nm_"));
								matched = false;
								continue;
							}
						}
					}

					for (MsgProperty prop : task.properties) {
						if (!msg.propertyExists(prop.name)) {
							if (prop.mustMatch) {
								System.out.println("Skipping message, must-match property not found: " + prop.name);
								matched = false;
								break;
							}
						}
						else if (prop.type.compareTo("String") == 0) {
							if (prop.mustMatch) {
								if (prop.value.compareTo(msg.getStringProperty(prop.name))!=0) {
									matched = false;
									break;
								}
								if (task.logger != null && ps != null) {
									ps.println("\tString:  " + msg.getStringProperty(prop.name));
								}
							}
						}
						else if (prop.type.compareTo("Int") == 0) {
							if (prop.mustMatch) {
								if (Integer.parseInt(prop.value) != msg.getIntProperty(prop.name)) {
									matched = false;
									break;
								}
								if (task.logger != null && ps != null) {
									ps.println("\tInt:     " + msg.getIntProperty(prop.name));
								}
							}
						}
						else if (prop.type.compareTo("Double") == 0) {
							if (prop.mustMatch) {
								if (Double.parseDouble(prop.value) != msg.getDoubleProperty(prop.name)) {
									matched = false;
									break;
								}
								if (task.logger != null && ps != null) {
									ps.println("\tDouble:  " + msg.getDoubleProperty(prop.name));
								}
							}
						}
						else if (prop.type.compareTo("Boolean") == 0) {
							if (prop.mustMatch) {
								if (Boolean.parseBoolean(prop.value) != msg.getBooleanProperty(prop.name)) {
									matched = false;
									break;
								}
								if (task.logger != null && ps != null) {
									ps.println("\tBoolean:   " + msg.getBooleanProperty(prop.name));
								}
							}
						}
					}                    	

					/*
                    if (task.logger != null && ps != null) {
                    	ps.println("MSG RECEIVED: " + msg.getJMSDestination() +
                    		" nm: " + msg.getStringProperty("_nm_"));
    	    			for (MsgProperty prop : task.properties) {
    	    				if (!msg.propertyExists(prop.name))
    	    					continue;
    	    				if (prop.type.compareTo("String") == 0)
    	    					ps.println("\tString:  " + msg.getStringProperty(prop.name));
    	    				else if (prop.type.compareTo("Int") == 0)
    	    					ps.println("\tInt:     " + msg.getIntProperty(prop.name));
    	    				else if (prop.type.compareTo("Double") == 0)
    	    					ps.println("\tDouble:  " + msg.getDoubleProperty(prop.name));
    	    				else if (prop.type.compareTo("Boolean") == 0)
    	    					ps.println("\tBoolean:   " + msg.getBooleanProperty(prop.name));
    	    			}                    	
                    }
					 */

					if (matched) {
						matchedMsgCount++;
					}

				} // end while still have messages to send
				

				ps.println(matchedMsgCount + " out of " + msgCount + " matched filtering criteria.");
				
				if (ps != null) {
					ps.flush();
					ps.close();
				}
				totalMsgCount += msgCount;
			} // end if this is a receive message task

			// now execute the command for this task
			this.execShellCommand(task.command);
		}

		System.out.println("\n");

		return totalMsgCount;
	}


	private List<MessageTask> parseFileMessages() {

		List<MessageTask> msgTaskList = new LinkedList<MessageTask>();
		MessageTask msgTask = null;
		MsgProperty msgProperty = null;
		Scanner lineScanner = null;
		Scanner fileScanner = null;

		try {
			fileScanner = new Scanner(new File(this.messageInputFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}

		String line;
		String currToken;
		while (fileScanner.hasNextLine()) {
			line = fileScanner.nextLine();
			if (line.startsWith("#") || line.length() == 0) {
				continue;
			}
			else {
				lineScanner = new Scanner(line);
				lineScanner.useDelimiter(" ");
			}
			currToken = lineScanner.next();

			if (currToken.compareTo("startEvent")==0) {
				this.startEvent = lineScanner.next();
				this.destName = lineScanner.next();
				this.useTopic = false;
			}
			if (currToken.compareTo("endEvent")==0) {
				this.endEvent = lineScanner.next();
				this.destName = lineScanner.next();
				this.useTopic = false;
			}
			if (currToken.compareTo("name")==0) {
				msgTask = new MessageTask();
				msgTask.name = lineScanner.next();
				taskMap.put(msgTask.name, msgTask);
			}
			if (currToken.compareTo("description")==0) {
				msgTask.description = line.substring("description".length()+1, line.length());
			}
			if (currToken.compareTo("transport")==0) {
				msgTask.properties = new LinkedList<MsgProperty>();
				msgTask.transport = lineScanner.next();
				msgTask.send = true;
				if (lineScanner.hasNext() &&
						(lineScanner.next().compareTo("receive")==0)) {
					msgTask.send = false;
				}
				msgTaskList.add(msgTask);
			}
			if (currToken.compareTo("target")==0) {
				currToken = lineScanner.next();
				if (currToken.compareTo("queue")==0) {
					msgTask.targetType = "queue";
					msgTask.targetDest = lineScanner.next();
					this.useTopic = false;
					this.destName = msgTask.targetDest;
				}
				else if (currToken.compareTo("topic")==0) {
					msgTask.targetType = "topic";
					msgTask.targetDest = lineScanner.next();
					this.useTopic = true;
					this.destName = msgTask.targetDest;
				}
				else {
					System.err.println("Skipping line due to error parsing: " + currToken
							+ " in line: " + line);
					continue;
				}
			}
			if (currToken.compareTo("count")==0) {
				msgTask.count = Long.parseLong(lineScanner.next());
				this.count = (int) msgTask.count;
			}
			if (currToken.compareTo("event")==0) {
				msgTask.event = lineScanner.next();
				this.messageEvent = msgTask.event;
				if (lineScanner.hasNextBoolean()) {
					msgTask.eventMustMatch = lineScanner.nextBoolean();
				}
			}
			if (currToken.compareTo("property")==0) {
				msgProperty = new MsgProperty();
				msgProperty.name = lineScanner.next();
				msgProperty.value = lineScanner.next();
				msgProperty.type = lineScanner.next();
				msgProperty.autoIncr = lineScanner.nextBoolean();
				if (lineScanner.hasNextBoolean()) {
					msgProperty.mustMatch = lineScanner.nextBoolean();
				}
				msgTask.properties.add(msgProperty);
			}
			if (currToken.compareTo("log")==0) {
				msgTask.logger = lineScanner.next();
			}
			if (currToken.compareTo("shellcmd")==0) {
				msgTask.command = line.substring("shellcmd".length()+1, line.length());
			}	
			if (currToken.compareTo("execute")==0) {
				while (lineScanner.hasNext()) {
					executionPlan.add(lineScanner.next());
				}
			}
		}

		return msgTaskList;
	}

	private void execShellCommand(String cmd) {
		if (cmd == null) {
			return;
		}

		Runtime run = Runtime.getRuntime();
		Process pr = null;
		try {
			pr = run.exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		/*
		try {
			pr.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
		BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		try {
			while ((line=buf.readLine())!=null) {
				System.out.println("cmd " + cmd + " output: " + line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
	}

	/**
	 * Create an optional start or end event (to surround bulk messages)
	 */
	private TextMessage createBoundaryMessage(Session session, String boundaryEvent) throws JMSException {
		TextMessage boundaryMsg = session.createTextMessage();
		int index = boundaryEvent.lastIndexOf("/");
		if (index < 0 || index >= boundaryEvent.length()) {
			usage();
		}
		else {
			boundaryMsg.setStringProperty("_nm_",
					boundaryEvent.substring(index+1, boundaryEvent.length()));
			boundaryMsg.setStringProperty("_ns_",
					EVENT_NAMESPACE + boundaryEvent.substring(0, index) + "/" +
					boundaryEvent.substring(index+1, boundaryEvent.length()));
		} 

		return boundaryMsg;    	
	}

	/**
	 * Create the message.
	 */
	private Message createMessage(Session session) throws JMSException
	{
		String payload = null;

		// create the message
		BytesMessage msg = session.createBytesMessage();

		// add the payload
		if (payloadFile != null)
		{
			try
			{
				InputStream instream = 
					new BufferedInputStream(new FileInputStream(payloadFile));
				int size = instream.available();
				byte[] bytesRead = new byte[size];
				instream.read(bytesRead);
				payload = new String(bytesRead);
			}
			catch(IOException e)
			{
				System.err.println("Error: unable to load payload file - " + e.getMessage());
			}
		}
		else if (msgSize > 0)
		{
			StringBuffer sb = new StringBuffer(msgSize);
			char c = 'A';
			for (int i = 0; i < msgSize; i++)
			{
				sb.append(c++);
				if (c > 'z')
					c = 'A';
			}
			payload = sb.toString();
		}

		if (payload != null)
		{
			// add the payload to the message
			msg.writeBytes(payload.getBytes());
		}

		return msg;
	}

	private synchronized void startTiming()
	{
		if (startTime == 0)
			startTime = System.currentTimeMillis();
	}

	private synchronized void stopTiming()
	{
		endTime = System.currentTimeMillis();
	}

	/**
	 * Convert delivery mode to a string.
	 */
	private static String deliveryModeName(int mode) {
		switch(mode)
		{
		case javax.jms.DeliveryMode.PERSISTENT:         
			return "PERSISTENT";
		case javax.jms.DeliveryMode.NON_PERSISTENT:     
			return "NON_PERSISTENT";
		case com.tibco.tibjms.Tibjms.RELIABLE_DELIVERY: 
			return "RELIABLE";
		default:                                        
			return "(unknown)";
		}
	}

	/**
	 * Get the EMS performance results, even though we're more interested in 
	 * how fast BE can actually process them.
	 */
	private String getPerformance(boolean send)
	{
		int count;
		if (send)
			count = sentCount;
		else
			count = recvCount;

		if (endTime > startTime)
		{
			elapsed = endTime - startTime;
			double seconds = elapsed/1000.0;
			int perf = (int)((count * 1000.0)/elapsed);
			return (count + " times took " + seconds + " seconds, performance is " + perf + " messages/second");
		}
		else
		{
			return "interval too short to calculate a message rate";
		}
	}

	/**
	 * Print the usage and exit.
	 */
	private void usage()
	{
		System.err.println("\nUsage: java BEJMSMessageSimulator [options] [ssl options]");
		System.err.println("\n");
		System.err.println("   where options are:");
		System.err.println("");
		System.err.println("   -server       <server URL>  - EMS server URL, default is local server");
		System.err.println("   -user         <user name>   - user name, default is null");
		System.err.println("   -password     <password>    - password, default is null");
		System.err.println("   -topic        <topic-name>  - topic name, default is \"topic.sample\"");
		System.err.println("   -queue        <queue-name>  - queue name, no default");
		System.err.println("   -size         <nnnn>        - Message payload in bytes");
		System.err.println("   -count        <nnnn>        - Number of messages to send, default 10k");
		System.err.println("   -time         <seconds>     - Number of seconds to run");
		System.err.println("   -threads      <nnnn>        - Number of threads to use for sends");
		System.err.println("   -connections  <nnnn>        - Number of connections to use for sends");
		System.err.println("   -delivery     <nnnn>        - DeliveryMode, default NON_PERSISTENT");
		System.err.println("   -txnsize      <count>       - Number of messages per transaction");
		System.err.println("   -rate         <msg/sec>     - Message rate for each producer thread");
		System.err.println("   -payload      <file name>   - File containing message payload.");
		System.err.println("   -factory      <lookup name> - Lookup name for connection factory.");
        System.err.println("   -durable      <name>        - Durable subscription name. No default.");
        System.err.println("   -selector     <selector>    - Message selector for consumers. No default.");
        System.err.println("   -ackmode      <mode>        - Message acknowledge mode. Default is AUTO.");
        System.err.println("                                 Other values: DUPS_OK, CLIENT EXPLICIT_CLIENT,");
        System.err.println("                                 EXPLICIT_CLIENT_DUPS_OK and NO.");
		System.err.println("   -uniquedests                - Each producer uses a different destination");
		System.err.println("   -compression                - Enable compression while sending msgs ");
		System.err.println("   -help-ssl                   - help on ssl parameters");
		System.err.println("   -messageInput <file name>   - File containing messages to send.");
		System.err.println("   -event        <event path>  - Full path of event for message");
		System.err.println("   -event:start	 <event path>  - Full path of start event (timing marker)");
		System.err.println("   -event:end	 <event path>  - Full path of end event (timing marker)");
		System.err.println("   -property     <name value type autoIncrement>  - Message property");
		System.err.println("                         type  - Can be: String, Double, Int, Boolean");
		System.err.println("   -property     <...>         - Any number of properties can be specified\n");

		System.exit(0);
	}


	/**
	 * Parse the command line arguments.
	 */
	private void parseArgs(String[] args)
	{
		int i=0;
		while(i < args.length)
		{
			if (args[i].compareTo("-event")==0)
			{
				if ((i+1) >= args.length) usage();
				messageEvent = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-event:start")==0)
			{
				if ((i+1) >= args.length) usage();
				startEvent = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-event:end")==0)
			{
				if ((i+1) >= args.length) usage();
				endEvent = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-property")==0)
			{
				if ((i+4) >= args.length) usage();
				MsgProperty prop = new MsgProperty();
				prop.name = args[i+1];
				prop.value = args[i+2];
				prop.type = args[i+3];
				prop.autoIncr = Boolean.parseBoolean(args[i+4]);
				i += 5;

				properties.add(prop);
			}
			else if (args[i].compareTo("-messageInput")==0)
			{
				if ((i+1) >= args.length) usage();
				this.messageInputFile = args[i+1];
				i += 2;
			}        	
			else if (args[i].compareTo("-server")==0)
			{
				if ((i+1) >= args.length) usage();
				serverUrl = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-queue")==0)
			{
				if ((i+1) >= args.length) usage();
				destName = args[i+1];
				i += 2;
				useTopic = false;
			}
			else if (args[i].compareTo("-topic")==0)
			{
				if ((i+1) >= args.length) usage();
				destName = args[i+1];
				i += 2;
				useTopic = true;
			}
			else if (args[i].compareTo("-user")==0)
			{
				if ((i+1) >= args.length) usage();
				username = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-password")==0)
			{
				if ((i+1) >= args.length) usage();
				password = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-delivery")==0)
			{
				if ((i+1) >= args.length) usage();
				String dm = args[i+1];
				i += 2;
				if (dm.compareTo("PERSISTENT")==0)
					deliveryMode = javax.jms.DeliveryMode.PERSISTENT;
				else if (dm.compareTo("NON_PERSISTENT")==0)
					deliveryMode = javax.jms.DeliveryMode.NON_PERSISTENT;
				else if (dm.compareTo("RELIABLE")==0)
					deliveryMode = com.tibco.tibjms.Tibjms.RELIABLE_DELIVERY;
				else {
					System.err.println("Error: invalid value of -delivery parameter");
					usage();
				}
			}
			else if (args[i].compareTo("-count")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					count = Integer.parseInt(args[i+1]);
				}
				catch(NumberFormatException e) {
					System.err.println("Error: invalid value of -count parameter");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-time")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					runTime = Integer.parseInt(args[i+1]);
				}
				catch(NumberFormatException e) {
					System.err.println("Error: invalid value of -time parameter");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-threads")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					threads = Integer.parseInt(args[i+1]);
				}
				catch(NumberFormatException e) 
				{
					System.err.println("Error: invalid value of -threads parameter");
					usage();
				}
				if (threads < 1) {
					System.err.println("Error: invalid value of -threads parameter, must be >= 1");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-connections")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					connections = Integer.parseInt(args[i+1]);
				}
				catch(NumberFormatException e) {
					System.err.println("Error: invalid value of -connections parameter");
					usage();
				}
				if (connections < 1) 
				{
					System.err.println("Error: invalid value of -connections parameter, must be >= 1");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-size")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					msgSize = Integer.parseInt(args[i+1]);
				}
				catch(NumberFormatException e) 
				{
					System.err.println("Error: invalid value of -size parameter");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-txnsize")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					txnSize = Integer.parseInt(args[i+1]);
				}
				catch(NumberFormatException e) 
				{
					System.err.println("Error: invalid value of -txnsize parameter");
					usage();
				}
				if (txnSize < 1) 
				{
					System.err.println("Error: invalid value of -txnsize parameter");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-rate")==0)
			{
				if ((i+1) >= args.length) usage();
				try 
				{
					msgRate = Integer.parseInt(args[i+1]);
				}
				catch (NumberFormatException e)
				{
					System.err.println("Error: invalid value of -rate parameter");
					usage();
				}
				if (msgRate < 1)
				{
					System.err.println("Error: invalid value of -rate parameter");
					usage();
				}
				i += 2;
			}
			else if (args[i].compareTo("-payload")==0)
			{
				if ((i+1) >= args.length) usage();
				payloadFile = args[i+1];
				i += 2;
			}
			else if (args[i].compareTo("-factory")==0)
			{
				if ((i+1) >= args.length) usage();
				factoryName = args[i+1];
				i += 2;
			}
			else if (args[i].startsWith("-ssl"))
			{
				i += 2;
			}
			else if (args[i].compareTo("-uniquedests")==0)
			{
				uniqueDests = true;
				i += 1;
			}
			else if (args[i].compareTo("-compression")==0)
			{
				compression = true;
				i += 1;
			} 
			else if (args[i].compareTo("-help")==0)
			{
				usage();
			}
			else if (args[i].compareTo("-help-ssl")==0)
			{
				tibjmsUtilities.sslUsage();
			}
			//////////////////// RECEIVE
			else if (args[i].compareTo("-durable")==0)
			{
				if ((i+1) >= args.length) usage();
				durableName = args[i+1];
				i += 2;
			}        	
			else if (args[i].compareTo("-ackmode")==0)
			{
				if ((i+1) >= args.length) usage();
				String dm = args[i+1];
				i += 2;
				if (dm.compareTo("DUPS_OK")==0)
					ackMode = javax.jms.Session.DUPS_OK_ACKNOWLEDGE;
				else if (dm.compareTo("AUTO")==0)
					ackMode = javax.jms.Session.AUTO_ACKNOWLEDGE;
				else if (dm.compareTo("CLIENT")==0)
					ackMode = javax.jms.Session.CLIENT_ACKNOWLEDGE;
				else if (dm.compareTo("EXPLICIT_CLIENT")==0)
					ackMode = com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_ACKNOWLEDGE;
				else if (dm.compareTo("EXPLICIT_CLIENT_DUPS_OK")==0)
					ackMode = com.tibco.tibjms.Tibjms.EXPLICIT_CLIENT_DUPS_OK_ACKNOWLEDGE;
				else if (dm.compareTo("NO")==0)
					ackMode = com.tibco.tibjms.Tibjms.NO_ACKNOWLEDGE;
				else {
					System.err.println("Error: invalid value of -ackMode parameter");
					usage();
				}
			}
			else if (args[i].compareTo("-selector")==0)
			{
				if ((i+1) >= args.length) usage();
				selector = args[i+1];
				i += 2;
			}
			else if(args[i].startsWith("-ssl"))
			{
				i += 2;
			}        	
			/////////////////////////////////
			else
			{
				System.err.println("Error: invalid option: " + args[i]);
				usage();
			}
		}

		if (durableName != null && !useTopic)
		{
			System.err.println("Error: -durable cannot be used with -topic");
			usage();
		}        
	}

	/**
	 * Get the total elapsed time.
	 */
	public long getElapsedTime()
	{
		return elapsed;
	}

	/**
	 * Get the total produced message count.
	 */
	public int getSentCount()
	{
		return sentCount;
	}

	/**
	 * Get the total consumed message count.
	 */
	public int getReceiveCount()
	{
		return recvCount;
	}    

	/**
	 * Class used to control the producer's send rate.
	 */
	private class MsgRateChecker 
	{
		int rate;
		long sampleStart;
		int sampleTime;
		int sampleCount;

		MsgRateChecker(int rate)
		{
			this.rate = rate;
			// initialize
			this.sampleTime = 10;
		}

		void checkMsgRate(int count)
		{
			if (msgRate < 100)
			{
				if (count % 10 == 0)
				{
					try {
						long sleepTime = (long)((10.0/(double)msgRate)*1000);
						Thread.sleep(sleepTime);
					} catch(InterruptedException e) {}
				}
			}
			else if (sampleStart == 0)
			{
				sampleStart = System.currentTimeMillis();
			}
			else
			{
				long elapsed = System.currentTimeMillis() - sampleStart;
				if (elapsed >= sampleTime)
				{
					int actualMsgs = count - sampleCount;
					int expectedMsgs = (int)(elapsed*((double)msgRate/1000.0));
					if (actualMsgs > expectedMsgs)
					{
						long sleepTime = (long)((double)(actualMsgs-expectedMsgs)/((double)msgRate/1000.0));
						try 
						{
							Thread.sleep(sleepTime);
						}
						catch (InterruptedException e) {}
						if (sampleTime > 20)
							sampleTime -= 10;
					}
					else
					{
						if (sampleTime < 300)
							sampleTime += 10;
					}
					sampleStart = System.currentTimeMillis();
					sampleCount = count;
				}
			}
		}
	}

	public static void main(String[] args)
	{
		BEJMSMessageSimulator t = new BEJMSMessageSimulator(args);
	}
}
