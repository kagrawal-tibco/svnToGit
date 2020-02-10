package com.tibco.cep.bemm.management.functions;

import java.io.IOException;
import java.net.URLDecoder;

import javax.management.ObjectName;
import javax.management.openmbean.TabularDataSupport;

import com.tibco.be.bemm.functions.UtilFunctions;
import com.tibco.be.functions.java.util.MapHelper;
import com.tibco.cep.bemm.management.util.XMLHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.service.management.exception.BEMMEntityNotFoundException;
import com.tibco.cep.runtime.service.management.exception.BEMMIllegalSetupException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 15, 2009
 * Time: 6:58:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoteInvoke extends RemoteMngmtProvider {
    //when stopping the process we need to close the JMX client connector to avoid exceptions
    private static final String STOP_ENGINE_OPERATION = "StopEngine";
    private static String methodFQName = "";

    //monitoredEntityName is necessary in TopologyEntityProperties
   public static Object invoke( String monitoredEntityName, String entityType, String methodGroup, String methodName,
                                            String[] properties, String[] params, String userName, String encodedPwd ) {
       try {
           entityType = entityType.toLowerCase();  //normalize to lower case
           String[] signature;
           Object[] paramsWithTypes;
           EntityMethodsDescriptor.MethodDescriptor methodDescriptor;

           //Decode to support multibyte and multilingual characters
           UtilFunctions.urlDecodeArray(properties,"UTF-8");
           UtilFunctions.urlDecodeArray(params,"UTF-8");
           userName = URLDecoder.decode(userName, "UTF-8");
           final String pwd = UtilFunctions.decodeBase64Pwd( URLDecoder.decode(encodedPwd, "UTF-8") );

           //Set important variables
           final String userMonitoredKey = getUserMonitoredKey(monitoredEntityName, entityType, userName);
           final String monitoredKey = getMonitoredKey(monitoredEntityName, entityType);
           final int agentID = getAgentID(monitoredEntityName, entityType);
           final ObjectName objectName= getObjName(agentID, entityType, methodGroup);

           methodFQName = "/"+entityType+"/"+methodGroup+"/"+methodName;

           // Establishes a JMX client connection to a connector server and obtains a reference
           // to the remote MBeanServer. This reference is used to invoke operations in the remote MBeans.
           // A new connection is established for each user
           if ( establishRemoteMBeanConnection(userMonitoredKey, entityType,
                   monitoredEntityName, properties, objectName, userName, pwd) ) {

               methodDescriptor = getMethodDescriptor(entityType, methodGroup, methodName);
               signature = getSignature(methodDescriptor);
               paramsWithTypes = getParametersWithTypes(params, signature);

               //after finding the necessary arguments use java reflection to invoke the MBean operation/attribute
               return invoke(monitoredKey, userMonitoredKey, entityType, objectName, methodName, paramsWithTypes, signature);
           }
           else{ //Remote MBean connection was not successfully established
               if(entityType.equals("process"))
                   throw new IOException("MBean Server connection invalid for: " + methodFQName +
                                            " on monitored entity: "+monitoredEntityName);
               else
                    throw new IOException("MBean Server connection invalid for: " + methodFQName +
                                            " with ID: " + agentID + " on monitored entity: "+monitoredEntityName);
           }
       } catch(Exception e) {
           String msg ;
           if ( (msg = UtilFunctions.getClassExceptionMsg(e, BEMMUserActivityException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMEntityNotFoundException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidAccessException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, BEMMInvalidUserRoleException.class)) != null ||
                (msg = UtilFunctions.getClassExceptionMsg(e, JMXConnClientException.class)) != null ) {

               logger.log(Level.WARN, msg);
               return XMLHandler.resultXML(methodFQName, XMLHandler.WARNING, "FAILED!\n\n" + msg);

           } else if ((msg = UtilFunctions.getClassExceptionMsg(e, BEMMIllegalSetupException.class)) != null ) {
               logger.log(Level.ERROR, msg);
               return XMLHandler.errorXML(methodFQName, msg, null /*todo error code */);
           } else { //studio does not allow catalog functions that throw checked exceptions,
                    //so wrap the checked exception in an unchecked (Runtime) exception
               logger.log(Level.ERROR, e, e.getMessage());
               throw new RuntimeException(e.getMessage(), e);
           }
       }
   }//invoke

    private static Object[] getParametersWithTypes(String[] params, String[] signature) {
        if (signature == null)
            return null;

        Object[] paramsWithTypes = new Object[params.length];

        for (int i=0; i < params.length; i++) {
            String type = signature[i].toLowerCase();

            if(type.equals(String.class.getName().toLowerCase()) || type.equals("string"))
                paramsWithTypes[i] = params[i];
            else if (type.equals("integer") || type.equals("int"))
                paramsWithTypes[i] = Integer.parseInt(params[i]);
            else if (type.equals("long"))
                paramsWithTypes[i] = Long.parseLong(params[i]);
            else if (type.equals("short"))
                paramsWithTypes[i] = Short.parseShort(params[i]);
            else if (type.equals("byte"))
                paramsWithTypes[i] = Byte.parseByte(params[i]);
            else if (type.equals("double"))
                paramsWithTypes[i] = Double.parseDouble(params[i]);
            else if (type.equals("float"))
                paramsWithTypes[i] = Float.parseFloat(params[i]);
            else if (type.equals("boolean"))
                paramsWithTypes[i] = Boolean.parseBoolean(params[i]);
            else if (type.equals("char"))
                paramsWithTypes[i] = params[i].charAt(0);
            else if (type.equals("void"))
                paramsWithTypes[i] = null;
        }
        return paramsWithTypes;
    }

    private static String[] getSignature(EntityMethodsDescriptor.MethodDescriptor methodDescriptor) {
       //no arguments ==> no method signature
       if ( methodDescriptor!= null && methodDescriptor.getArgTypes().size() == 0)
            return null;
       else if (methodDescriptor != null)    //Retrieve the method signature
            return methodDescriptor.getArgTypes().toArray( new String[methodDescriptor.getArgTypes().size()] );
       else //it is null
            throw new RuntimeException("Could not find method descriptor for method.");
    }

    /** retrieves the method descriptor object from the Map with the method descriptors
     * @return Method descriptor for this method*/
    private static EntityMethodsDescriptor.MethodDescriptor getMethodDescriptor(String agentType, String methodGroup, String methodName) {
        String methodQualifiedName = getMethodQualifiedName(methodGroup, methodName);

        //if method descriptor for this method not entered in the Map yet enters it.
        if (MapHelper.getObject(agentType,methodQualifiedName)==null) {
            EntityMethodsDescriptor.getMethodDescriptor(agentType, methodGroup, methodName);
        }
        return (EntityMethodsDescriptor.MethodDescriptor)(MapHelper.getObject(agentType,methodQualifiedName));
    } //getMethodDescriptor

    //To be able to use invoke for the getters and setters methods you need to specify the following
    //property: -Djmx.invoke.getters=true. Note however that calling getters/setters in this way is not
    //compliant with the JMX specification. Rather than use invoke one should use
    // MBeanServer.getAttribute(objName,"NAME_OF_ATTRIBUTE"). NAME_OF_ATTRIBUTE is what comes after the get.
    private static Object invoke(String monitoredKey, String userMonitoredKey, String entityType, ObjectName objName,
                                 String operName, Object[] paramsWithTypes, String[] signature) throws Exception {
        //invoke the MBean operation/attribute using the client MBeanServer (proxy) connection stored in the HashMap.
        //The MBean operation/attribute in the remote MBean is fully resolved from the arguments passed to the method "invoke"
        Object obj = getMBeanServerConnection(userMonitoredKey).invoke(objName, operName, paramsWithTypes, signature);

        if (obj == null) {      //operation returning null or void
            if (operName.equals(STOP_ENGINE_OPERATION)) {
                logger.log(Level.INFO, "Stopped entity with key: %s", monitoredKey);
                //close the JMX Client connector to avoid exceptions and remove entries from HashMaps
                removeJMXClientConnection(monitoredKey, entityType);
            }
            return XMLHandler.resultXML(methodFQName, XMLHandler.SUCCESS, true);
        }
        else if ( obj.getClass().equals(TabularDataSupport.class) )  //it's tabular data
            return XMLHandler.resultXML(methodFQName, XMLHandler.TABLE, obj);
        else //it is a singular (primitive) value               
            return XMLHandler.resultXML(methodFQName, XMLHandler.VALUE, obj);
    }//invoke

//    // ****************** For testing purposes only. *******************
//    public static void main (String[] args) {
//        try {
////            String monitoredEntityName = "cacheserver#1";
//            final String entityType = "cache";
//            final String monitoredEntityName = "cache:1#1";
//            final int agentID = getAgentID(monitoredEntityName, entityType);
//            final String[] properties = {"localhost", "5912@hlouro-lt-T61","address=localhost,port=5566,sslEnabled=false",
//                  "domain=HugosDomain,service=7474,daemon=tcp:7474,network=xx"};
//            final ObjectName objectName=null;
//            final String userName = "admin";
//            final String pwd="";
//            final String userMonitoredKey = getUserMonitoredKey(monitoredEntityName, entityType, userName);
//            final String monitoredKey = getMonitoredKey(monitoredEntityName, entityType);
//
////            establishRemoteMBeanConnection(userMonitoredKey, entityType, monitoredEntityName, properties, objectName, userName, pwd);
//
////            String methodName = "getDestinations";
////            String group = "Channels";
////            String[] params = {null, null};
////            String[] signature = {"java.lang.String", "java.lang.String"};
//
//            final String methodName = "stopEngine";
//            final String group = "Agent";
////            final String[] params = null;//{null};
//            final String[] params = {"%u563F"};
////            final String[] signature = null;//{null};
//            final String[] signature = {"null"};
//
//            Object ret1 = RemoteInvoke.invoke(monitoredKey, userMonitoredKey, entityType,
//                                                objectName, methodName, params, signature);
////            Object ret = getAttribute(1, "AgentName");
//            System.err.println(ret1.toString());         //todo fix this
//        } catch (Exception e) {
//            e.printStackTrace(); 
//        }
//    }//main

} //class RemoteInvoke
