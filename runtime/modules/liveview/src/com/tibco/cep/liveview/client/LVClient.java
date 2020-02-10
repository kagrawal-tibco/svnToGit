/**
 * 
 */
package com.tibco.cep.liveview.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.streambase.liveview.client.LiveViewConnection;
import com.streambase.liveview.client.Table;
import com.streambase.liveview.client.TablePublisher;
import com.streambase.sb.Schema;
import com.streambase.sb.Tuple;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.liveview.agent.LiveViewAgentConfiguration;
import com.tibco.cep.liveview.as.ASToLVConverter;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Client to interact with the Live DataMart server
 * 
 * @author vpatil
 */
public class LVClient {
	
	private static final String TRANSFORMER_FACTORY_PROPERTY = "javax.xml.transform.TransformerFactory";
	private static final String DEFAULT_TRANSFORMER_FACTORY_IMPL = "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl";
	
	// publish retry and wait times
	private static final String PUBLISH_WAIT_TIME_PROPERTY = "be.engine.ldm.publish.retry.wait";
	private static final String MAX_PUBLISH_RETRIES_PROPERTY = "be.engine.ldm.publish.max.retries";
	
	private static long PUBLISH_WAIT_TIME;
	private static int MAX_PUBLISH_RETRIES;
	
	private LVConnectionPool lvConnectionPool;
	
	public final String PUBLISHER_NAME_SUFFIX = "_Publisher";
	
	private Map<String, TablePublisher> tableToPublisherMap = new ConcurrentHashMap<String, TablePublisher>();
	
	private Logger logger;
	
	private AtomicLong sequenceNo = new AtomicLong(1000);
	
	public LVClient(LiveViewAgentConfiguration lvAgentConf, RuleServiceProvider rsp) throws Exception {
		lvConnectionPool = new LVConnectionPool(lvAgentConf.getConnectionURI(),
				lvAgentConf.getUserName(),
				lvAgentConf.getUserPassword(),
				lvAgentConf.getInitialSize(),
				lvAgentConf.getMaxSize());
		
		PUBLISH_WAIT_TIME = Long.parseLong(rsp.getProperties().getProperty(PUBLISH_WAIT_TIME_PROPERTY, "2000"));
		MAX_PUBLISH_RETRIES = Integer.parseInt(rsp.getProperties().getProperty(MAX_PUBLISH_RETRIES_PROPERTY, "5"));
		
		this.logger = LogManagerFactory.getLogManager().getLogger(LVClient.class);
	}
	
	public void intialize() throws Exception {
		lvConnectionPool.initialize();
	}
	
	public void publish(final String spaceName, final com.tibco.as.space.Tuple asTuple, final boolean isDelete) {
		LiveViewConnection lvConnection = null;
		
		String lvTableName = ASToLVConverter.spaceToLVTableName(spaceName);
		
		TablePublisher tablePublisher = null;
		boolean publishSuccess = false;
		int publishCount = 1;
		Tuple lvTuple  = null;
		do {
			try {
				lvConnection = lvConnectionPool.takeFromPool();

				if (lvConnection != null && lvConnection.isConnected()) {
					if (spaceName != null && !spaceName.isEmpty() && asTuple != null) {
						setTransformerFactory();

						tablePublisher = tableToPublisherMap.get(lvTableName);
						if (tablePublisher == null) {
							Table lvTable = lvConnection.getTable(lvTableName);
							if (lvTable != null) {
								tablePublisher = lvTable.getTablePublisher(lvTableName + PUBLISHER_NAME_SUFFIX);
								tableToPublisherMap.put(lvTableName, tablePublisher);
							}
						}
						lvTuple = makeTuple(tablePublisher.getSchema(), spaceName, asTuple);
						setTransformerFactory();
						tablePublisher.publish(sequenceNo.getAndIncrement(), isDelete, lvTuple);
						publishSuccess = true;
					}
				}
			} catch (Exception lvException) {
				// remove the table publisher and add it next time 
				tableToPublisherMap.remove(lvTableName);
				if (tablePublisher != null) {
					tablePublisher.close();
					tablePublisher = null;
				}

				String exceptionMessage = String.format("Error publishing from Space[%s] to LV Table[%s]. Attempt [%s], Error [%s].", spaceName, lvTableName, publishCount, lvException.getMessage());
				if (publishCount == MAX_PUBLISH_RETRIES) {
					exceptionMessage += " Reached max retries, giving up.";
				}
				logger.log(Level.ERROR, exceptionMessage);
				
				if (publishCount == MAX_PUBLISH_RETRIES) {
					throw new RuntimeException(exceptionMessage, lvException);
				}  else {
					try {
						Thread.sleep(PUBLISH_WAIT_TIME);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
					publishCount++;
				}
			} finally {
				if (lvConnection != null) {
					lvConnectionPool.returnToPool(lvConnection);
					lvConnection = null;
				}
			}
		} while (!publishSuccess && publishCount <= MAX_PUBLISH_RETRIES);
	}
	
	private Tuple makeTuple(Schema schema, final String spaceName, final com.tibco.as.space.Tuple spaceTuple) throws Exception {
		Tuple lvTuple = schema.createTuple();
		ASToLVConverter.spaceToLVTuple(spaceName, spaceTuple, lvTuple);
		
		return lvTuple;
	}
	
	public void shutdown() throws Exception {
		lvConnectionPool.removeAllFromPool();
		
		for (TablePublisher tablePublisher : tableToPublisherMap.values()) {
			if (tablePublisher != null) tablePublisher.close();
		}
		tableToPublisherMap.clear();
	}
	
	private void setTransformerFactory() {
		String existingTransformerFactoryImpl = System.getProperty(TRANSFORMER_FACTORY_PROPERTY);
		
		// set it back to java impl of TransformerFactory
		if (existingTransformerFactoryImpl != null && !existingTransformerFactoryImpl.equals(DEFAULT_TRANSFORMER_FACTORY_IMPL)) {
			System.setProperty(TRANSFORMER_FACTORY_PROPERTY, DEFAULT_TRANSFORMER_FACTORY_IMPL);
		}
	}
}
