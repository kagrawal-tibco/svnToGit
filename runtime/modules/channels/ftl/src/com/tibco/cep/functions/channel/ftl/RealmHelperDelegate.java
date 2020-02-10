package com.tibco.cep.functions.channel.ftl;

import com.tibco.ftl.FTL;
import com.tibco.ftl.FTLException;
import com.tibco.ftl.Realm;
import com.tibco.ftl.TibProperties;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.tibco.security.ObfuscationEngine;


public class RealmHelperDelegate {
	public static ConcurrentHashMap<String,Object> pubMap = new ConcurrentHashMap<String,Object>();
	public static ConcurrentHashMap<String,Object> subMap = new ConcurrentHashMap<String,Object>();
	private static AtomicInteger subCounter = new AtomicInteger();
	private static AtomicInteger pubCounter = new AtomicInteger();
	
   
    public static Object connectToRealmServer (String realmUrl, String applicationName, String username, String password, Object tibProperties) {
    	TibProperties tp = TibProperties.class.cast(tibProperties);
        try {
        	if(tp == null){
        		tp = FTL.createProperties();
        	}
        	if(username != null){
        		tp.set(Realm.PROPERTY_STRING_USERNAME,username);
        	}
        	if(password != null){
        		//Check if the password passed is already encrypted
        		if(ObfuscationEngine.hasEncryptionPrefix(password)){
        			//Decrypt the encrypted password
        			char[] decryptedPassword = ObfuscationEngine.decrypt(password);
        			password = String.valueOf(decryptedPassword);
        		}
        		tp.set(Realm.PROPERTY_STRING_USERPASSWORD,password);
        	}
            return FTL.connectToRealmServer(realmUrl, applicationName, tp);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    
    public static String getVersionInformation () {
        return FTL.getVersionInformation();
    }

    
    public static void setLogFiles (String filePrefix, long maxFileSize, int maxFiles, Object arg) {
        try {
            FTL.setLogFiles(filePrefix, maxFileSize, maxFiles, null);
        } catch (FTLException e) {
            e.printStackTrace();
        }
        return;
    }

    
    public static void setLogLevel (String level) {
        try {
            FTL.setLogLevel(level);
        } catch (FTLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    public static Object getPublisher(Object realmObject, String endPointName, String appInstanceIdentifier, Object props){
    	try{
    		Realm realm = Realm.class.cast(realmObject);
    		TibProperties property = TibProperties.class.cast(props);
    		if(property == null){
    			property = FTL.createProperties();
    		}
    		property.set(Realm.PROPERTY_STRING_APPINSTANCE_IDENTIFIER, appInstanceIdentifier);
    		
    		//Object realm = connectToRealmServer(realmUrl, applicationName, props);
    		return new FTLPublisher(realm, endPointName, property);
    	} catch(Exception exp){
    		throw new RuntimeException(exp);
    	}
    }
    
    
    
    public static Object getSubscriber(Object realmObject, String endPointName, String appInstanceIdentifier, Object props){
    	try{
    		Realm realm = Realm.class.cast(realmObject);
    		TibProperties property = TibProperties.class.cast(props);
    		if(property == null) {
    			property = FTL.createProperties();
    		}
    		property.set(Realm.PROPERTY_STRING_APPINSTANCE_IDENTIFIER, appInstanceIdentifier);
    		
    		//Object realm = connectToRealmServer(realmUrl, applicationName, props);
    		
    		return new FTLSubscriber(realm, endPointName, property);
    	} catch(Exception exp){
    		throw new RuntimeException(exp);
    	}
    }
    
    
}
