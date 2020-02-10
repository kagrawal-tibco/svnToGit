package com.tibco.cep.runtime.service.management;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 5, 2010
 * Time: 9:13:15 PM
 */

public abstract class EntityMBeansManager extends EntityMBeansHelper {

    protected String[] mBeanGroups;
    protected String[] mBeanClassGroups;    //Used to match the class name patterns for names with spaces.
                                            // For example Working Memory (WM), Rule Service Provider(RSP)
    protected byte numGroups;

    protected boolean isAgent;
    protected CacheAgent cacheAgent;
    protected int agentId;

    protected ObjectName parentObjName;
    protected ObjectName[] objectNames;
    protected Class<?>[] mBeanClassName;
    protected Class<?>[] mBeanInterfaceName;

    protected Logger logger;
    protected RuleServiceProvider ruleServiceProvider;

    protected MBeanServer mbs;


    protected EntityMBeansManager(String[] mBeanGroups, String[] mBeanClassGroups) {
        if (mBeanGroups.length != mBeanClassGroups.length) {
            throw new IndexOutOfBoundsException("Arrays with Group names and corresponding implementation " +
                                                "classes names must have the same size");
        }

        this.mBeanGroups = mBeanGroups;
        this.mBeanClassGroups = mBeanClassGroups;
        this.numGroups = (byte) ((null == mBeanGroups) ? 0 : mBeanGroups.length);

        mbs = ManagementFactory.getPlatformMBeanServer();
    }

    protected void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        this.ruleServiceProvider = ruleServiceProvider;
    }

    protected void setLogger(Class classs) {
        this.logger = ruleServiceProvider.getLogger(classs);
    }

    protected void setObjectNames(String parentObjNameStr) {
        try {
            parentObjName = new ObjectName(parentObjNameStr);
            objectNames = new ObjectName[numGroups];
        } catch (MalformedObjectNameException e) {
            logger.log(Level.ERROR, e, "Error creating object name\\n" + e.getMessage());
        }
    }

    // Registers all of the MBean interfaces and corresponding implementation classes that follow the pattern:
    // className = packageName.AgentMBEANGROUPMBean or className = packageName.EngineMBEANGROUPMBean
    // interfaceName = packageName.AgentMBEANGROUPMBeanImpl or interfaceName = packageName.EngineMBEANGROUPMBeanImpl
    // MBEANGROUP is one of the strings in the field String[] mBeanGroups
    protected void registerMBeanClasses(Class mBeanClass) {
        mBeanClassName = new Class<?>[numGroups];
        mBeanInterfaceName = new Class<?>[numGroups];
        String packagee = mBeanClass.getPackage().getName();
        String className, interfaceName;
        int i=0;

        try {
            for (i=0; i< numGroups; i++) {
                if (isAgent) {     //it is an agent
                    interfaceName = packagee+".Agent"+ mBeanClassGroups[i] + "MBean";
                    className =  packagee+".impl.Agent"+ mBeanClassGroups[i] + "MBeanImpl";
                }
                else {                        //it is a process (engine)
                    interfaceName = packagee+".Engine"+ mBeanClassGroups[i] + "MBean";
                    className = packagee+".impl.Engine"+ mBeanClassGroups[i] + "MBeanImpl";
                }

                mBeanClassName[i] = Class.forName(className);
                mBeanInterfaceName[i] = Class.forName(interfaceName);
            }
        } catch (ClassNotFoundException e) {
            if (isAgent)
                logger.log(Level.ERROR,e ,"Error registering (Method Group) MBean classes for Agent ID: " + agentId +
                    ". Class " + mBeanClassName[i] + " and/or Interface " + mBeanInterfaceName[i] + " do not exist");
            else
                logger.log(Level.ERROR,e ,"Error registering Engine MBean classes. Class " + mBeanClassName[i] +
                    " and/or Interface " + mBeanInterfaceName[i] + " do not exist");
        }
    }

    //creates this ObjectName from parent ObjectName
    protected void init(int index) {
        try {
            objectNames[index]= new ObjectName(parentObjName + ",Group="+mBeanGroups[index]);
        } catch (MalformedObjectNameException e) {
            logger.log(Level.ERROR,"Illegal ObjectName: " + objectNames[index]);
        }
    }

    //registers MBean
   protected void register(int index) {
        try {
            Class mBeanClass = mBeanClassName[index];
            Object mBeanObj  = mBeanClassName[index].newInstance();
            //Sets the RSP and Logger in each of the MBean classes
            mBeanClass.getMethod("setRuleServiceProvider", RuleServiceProvider.class).invoke(mBeanObj, ruleServiceProvider);
            mBeanClass.getMethod("setLogger",Logger.class).invoke(mBeanObj, logger);

            if (isAgent) {
                if (cacheAgent != null)  //only used at the agent level
                    mBeanClass.getMethod("setCacheAgent",CacheAgent.class).invoke(mBeanObj, cacheAgent);

                if (agentName != null) {
                    mBeanClass.getMethod("setAgentName",String.class).invoke(mBeanObj, agentName);
                }
            }

            StandardMBean mBean = new StandardMBean(mBeanObj, (Class<Object>)mBeanInterfaceName[index]);
            if (!mbs.isRegistered(objectNames[index])) {
            	mbs.registerMBean(mBean, objectNames[index]);
            } else {
            	logger.log(Level.DEBUG,mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index] + " already registered");
            }
        }
        catch (MBeanRegistrationException e) {
           logger.log(Level.DEBUG,"Error registering " + mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index]);
           unregister(index);
           e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            logger.log(Level.DEBUG,"Compliance error registering " + mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index]);
           unregister(index);
           e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
        	// This exception should not occur as we are explicitly checking whether the MBean is already registered.
        	// However if it does occur for some reason print the stack trace to distinguish this message from the 'already registered' message above
            logger.log(Level.DEBUG,mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index] + " already registered");
           e.printStackTrace(); 
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //TODO
        } catch (InstantiationException e) {
            e.printStackTrace();  //TODO
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();  
        }
    }//register()

    //Unregisters MBean case an exception is thrown during registration
    private void unregister(int index) {
        try {
            logger.log(Level.DEBUG,"Unregistering Method Group: " + mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index]);

            if (mbs.isRegistered(objectNames[index]))
                mbs.unregisterMBean(objectNames[index]);

        } catch (InstanceNotFoundException e) {
            logger.log(Level.DEBUG,"No Method Group: " + mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index] + " was found");
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
             logger.log(Level.DEBUG,"No Method Group: " + mBeanClassGroups[index] + " MBean with ObjectName: " + objectNames[index] + "had been registered");
            e.printStackTrace();
        }
    }

    /** Helper method to register the specified MBean object in this JVM's MBeanServer */
    public static <T> void registerStdMBean(String objectNameStr, T obj, Class<T> impl) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        try {
            ObjectName on = new ObjectName(objectNameStr);

            if(!mbs.isRegistered(on)) {
                StandardMBean mb = new StandardMBean(obj, impl);
                mbs.registerMBean(mb, on);
            }

        } catch (Exception e) {
            LogManagerFactory.getLogManager().getLogger(EntityMBeansManager.class).
                log(Level.ERROR, e, "Error occurred while registering MBean for object '%s', " +
                        "with interface '%s', with object name '%s' ",
                        obj.toString(), impl.getClass().getName(), objectNameStr);
        }
    }

} //class
