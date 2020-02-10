package com.tibco.rta.test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaCommand;
import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.RtaConnection;
import com.tibco.rta.RtaConnectionFactory;
import com.tibco.rta.RtaConnectionFactoryEx;
import com.tibco.rta.RtaSession;
//import com.tibco.rta.client.demo.EmbeddedRtaEngineTest.MyCommandListener; //shekhar - commented temp
import com.tibco.rta.engine.RtaEngine;
import com.tibco.rta.engine.RtaEngineExFactory;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.model.DataType;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.property.impl.PropertyAtomBoolean;
import com.tibco.rta.property.impl.PropertyAtomInt;
import com.tibco.rta.property.impl.PropertyAtomLong;
import com.tibco.rta.property.impl.PropertyAtomString;

@RunWith(Suite.class)
@SuiteClasses({AggTestClass1.class})
public class AggtestSuite {
	
	private static RtaEngine engine;
	public static RtaSession session;
    private static String sessionName;
    
    private static Properties configuration;
    private static String propFileName = "D:/devWorkspace/5.3/runtime/mm/tea/config/be-teagent.props";  //"C:/tibco/spm/2.3/config/spm-config.properties";
	
	 @BeforeClass
	 public static void setUp() 
	 {
		 /**
			* Enable when testing for Database
			* 
		 */
			/*TestUtilities.clearDbData();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		 
	    	System.out.println("setting up suite");
	    	try
	    	{
	    		getConfiguration();
	    		startRtaEngine();
	    		Thread.sleep(5000);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	 }
	 
	private static Properties getConfiguration() throws Exception
	{		
		 if (configuration == null) {
	           configuration = new Properties();
	           configuration.load(new FileInputStream(new File(propFileName)));
		 }
	     return configuration;
	}
	 
	private static void startRtaEngine() throws Exception
	{
			engine = RtaEngineExFactory.getInstance().getEngine();		
			engine.init(getConfiguration());
			engine.start();
			Thread.sleep(1000);    	
	}
	 
	@Parameters
	public static RtaSession initSession() throws Exception {

        String serverUrl = "local"; //(String) ConfigProperty.JMS_PROVIDER_JNDI_URL.getValue(configuration);
        RtaConnectionFactory connectionFac = new RtaConnectionFactoryEx();
        Map<ConfigProperty, PropertyAtom<?>> configurationMap = new HashMap<ConfigProperty, PropertyAtom<?>>();
        Enumeration<?> propertyNamesEnumeration = configuration.propertyNames();

        while (propertyNamesEnumeration.hasMoreElements()) {
            String propertyName = (String) propertyNamesEnumeration.nextElement();
            ConfigProperty configProperty = ConfigProperty.getByPropertyName(propertyName);
            if (configProperty != null) {
                configurationMap.put(configProperty, getPropertyAtom(configProperty.getDataType(), (String) configProperty.getValue(configuration)));
            }
        }

        RtaConnection connection = connectionFac.getConnection(serverUrl, ConfigProperty.CONNECTION_USERNAME.getValue(configuration).toString(), ConfigProperty.CONNECTION_PASSWORD.getValue(configuration).toString(), configurationMap);
        sessionName = UUID.randomUUID().toString();
        if (session == null) {
            session = (DefaultRtaSession) connection.createSession(sessionName, configurationMap);
            //session.setNotificationListener(new MyRtaNotificationListener(), NotificationListenerKey.EVENT_SERVER_HEALTH | NotificationListenerKey.EVENT_FACT_DROP | NotificationListenerKey.EVENT_FACT_PUBLISH | NotificationListenerKey.EVENT_TASK_REJECT);
            session.init();
            session.getAllActionFunctionDescriptors();
            session.setCommandListener(new MyCommandListener());
        }
        
        return session;
    }
	
	/*@Parameters
	public static RtaSession initSession() throws Exception {

		session =  BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
		return session;
    }*/
	
	
	
	 private static PropertyAtom<?> getPropertyAtom(DataType dataType, String value) {
	        switch (dataType) {
	            case BOOLEAN:
	                return new PropertyAtomBoolean(Boolean.parseBoolean(value));
	            case STRING:
	                return new PropertyAtomString(value);
	            case INTEGER:
	                return new PropertyAtomInt(Integer.parseInt(value));
	            case LONG:
	                return new PropertyAtomLong(Long.parseLong(value));
	            default:
	                return null;
	        }
	    }

	@AfterClass
	public static void tearDown()
	{
		System.out.println("tearing down suite");
		engine.stop();
	}
	
	

	

}
