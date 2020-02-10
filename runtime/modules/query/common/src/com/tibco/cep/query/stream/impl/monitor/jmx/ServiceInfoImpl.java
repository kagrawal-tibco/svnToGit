package com.tibco.cep.query.stream.impl.monitor.jmx;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.monitor.model.ServiceInfo;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jul 25, 2008
 * Time: 4:45:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceInfoImpl extends ServiceInfo implements com.tibco.cep.query.stream.impl.monitor.model.Info {
    private MBeanServer mbs;            //todo - inherits field monitor from super class - IS THIS THE RIGHT PRACTICE ???
    private static final String[] servicesDescription = {"Queries - Continuous", "Queries - Snapshot"};
    private Map<String, Members> queryMembersInfoMap;          //Keeps an instance of Members per regionName. Detailed desc below

    public ServiceInfoImpl( QueryMonitor monitor) {
        super (monitor);
        this.mbs = ManagementFactory.getPlatformMBeanServer();
        this.queryMembersInfoMap = new HashMap<String, Members>();
    }

        //initiates the registration process of the Service MBeans (one per region), and calls refresh() to register the
        //Query MBeans, in case there are any queries active at this point  todo - verify querymonitor code to be able to start without having to do refresh()
    public void register() {
        ObjectName objNameServiceMBean, objNameAgentMBean;

        try {
            for ( String regionName : listRegionNames() ) {
                    //Create one Members class instance per regionName. The queryMembersInfoMap is used to handle the
                    //registration, update, and unregistration of Model MBeans
                if ( !queryMembersInfoMap.containsKey(regionName) )                         //todo should I leave the If in here ? queryMembersInfoMap ==null ||
                    queryMembersInfoMap.put(regionName,new Members());

                    // Initialize the JMX UI by registering the service MBeans for continuous queries / snapshot queries
                objNameAgentMBean = new ObjectName("com.tibco.be:type=Query,partition=" + regionName);

                AgentInfoMBeanImpl agentInfo = new AgentInfoMBeanImpl();
                agentInfo.init(objNameAgentMBean, monitor, regionName);
                agentInfo.register();

                AgentTraceInfoMBeanImpl agentTraceInfo = new AgentTraceInfoMBeanImpl();
                agentTraceInfo.init(objNameAgentMBean, monitor, regionName);
                agentTraceInfo.register();

                    //register service MBean for continuous/snapshot queries
                for (String servDesc : servicesDescription) {
                    objNameServiceMBean = new ObjectName(objNameAgentMBean + ",service=" + servDesc);
                    if ( !mbs.isRegistered(objNameServiceMBean) ) {             //todo Doesn't need this if, just for security reasons - SHOULD i LEAVE IT
                        registerServiceModelMBean(objNameServiceMBean);
                        setServiceObjectName(regionName, servDesc, objNameServiceMBean);
                    }
                }
            }
            refresh();                 //registers Query MBeans, if any queries active at this point
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //registers the Query MBeans for continuous or snapshot queries, depending on the collection passed in the parameter "activeQueries"
    private void registerQueryMBeans( String regionName, String serviceDescription, Collection<ReteQuery> activeQueries ) {
        Iterator<ReteQuery> activeQueriesIter, localHashsetIterator;
        ObjectName objNameServiceMBean;
        ReteQuery currentReteQueryObj;
        Collection<ReteQuery> localHashsetQueries;
        Map<ReteQuery, QueryInfoImpl> localHashmapQueryInfoObj;

        try {
                //Get local list of queries (queries that were active before the last refresh() )from this instance HashSet
            localHashsetQueries = getServiceQueries(regionName, serviceDescription);
                //Get local Map of queries and QueryInfoImpl objects
            localHashmapQueryInfoObj = getQueryInfoObjMap(regionName, serviceDescription);
            activeQueriesIter = activeQueries.iterator();
                // if active queries collection NOT empty, iterates through the list of queries (either continous or snapshot depending on the method call)
            while (activeQueriesIter.hasNext()) {
                currentReteQueryObj = activeQueriesIter.next();
                    //If a Query object is NOT registered (ie not in localHashsetQueries) as a Query MBean yet, registers it and updates local HashSet
                if ( !localHashsetQueries.contains(currentReteQueryObj) ) {
                    localHashsetQueries.add(currentReteQueryObj);
                    objNameServiceMBean = new ObjectName("com.tibco.be:type=Query,partition=" + regionName + ",service=" + serviceDescription);
                        //Creates a new instance of Queryinfo and  registers it as an MBean
                    QueryInfoImpl queryInfoMBean = new QueryInfoImpl (monitor, currentReteQueryObj,  objNameServiceMBean);
                    queryInfoMBean.register();
                        //adds entry (ReteQuery, Object) pair to the QueryInfo Object map. This is necessary for the unregistration step
                    localHashmapQueryInfoObj.put(currentReteQueryObj, queryInfoMBean);
                }
            }
            //IF the collection of active queries NO longer contains a query that is in the local HashSet, remove it from the
            //local HashSet, unregister QueryMBean, and remove it from the QueryInfo Object hashmap.  ELSE do nothing
            if (localHashsetQueries != null) {           // handles the case when one does refresh and there are no queries running, and no queries have been created yet
            localHashsetIterator = localHashsetQueries.iterator();
                while (localHashsetIterator.hasNext()) {
                        //reuse the variable currentReteQueryObj in the local HashSet because we no longer need its values
                    currentReteQueryObj = localHashsetIterator.next();
                    if ( !activeQueries.contains(currentReteQueryObj) ) {
                        localHashsetIterator.remove();
                        if (localHashmapQueryInfoObj != null)               //todo this should be redundant, leave it anyways?????????/   ask ashwin
                            localHashmapQueryInfoObj.remove(currentReteQueryObj).unregister();
                    }
                }
            }
                //update the local QueryInfoObjMap with up to date MBean registration information, and update the local
                // collection of ReteQuery with the list of currently active queries.
            setServiceQueries(regionName, serviceDescription, localHashsetQueries);
            setQueryInfoObjMap(regionName, serviceDescription, localHashmapQueryInfoObj);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

        //creates metadata information for what we call the service MBean (the base MBean in our tree hierarchy of MBeans),
        // uses this metadata to instrument the resource (active queries), and registers the created MBean in the MBean server
        //  thus making it available for management in the JMX client (J-Console in our case)
    private void registerServiceModelMBean(ObjectName objNameMBean) {
        try {
                //operation's descriptor
            Descriptor serviceDesc = new DescriptorSupport();
            serviceDesc.setField("name","refresh");                     //required
            serviceDesc.setField("descriptorType","operation");         //fields

            ModelMBeanOperationInfo[] mmboi = {  new ModelMBeanOperationInfo("Refreshes the displayed query MBeans with up-to-date active query information",
                                                        this.getClass().getMethod("refresh"), serviceDesc) };
                //MBean descriptor
            Descriptor descMBean = new DescriptorSupport();
            descMBean.setField("name",ServiceInfoImpl.class.getName());                //todo change this description
            descMBean.setField("descriptorType","MBean");

                //create MBean metadata info
            ModelMBeanInfo mmbi = new ModelMBeanInfoSupport(ServiceInfoImpl.class.getName(), "Displays one MBean per each active query. Click 'refresh' to update the displayed MBeans",
                                                            null, null, mmboi, null, descMBean);

            RequiredModelMBean requiredModelMBean = new RequiredModelMBean(mmbi); //instruments the resource (ie sets the metadata info)
            requiredModelMBean.setManagedResource(this, "ObjectReference");     //defines the management interface
            mbs.registerMBean(requiredModelMBean, objNameMBean);                //registers (exposes) the resource for monitoring and management
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregister() throws Exception, InstanceNotFoundException, MalformedObjectNameException {     //todo talk to ASHWIN
        Iterator<QueryInfoImpl> queryInfoObjIter;

        for (String regName : listRegionNames()) {
            for (String servDesc : servicesDescription) {
                    //unregister Query Model MBean for every continuous and snapshot queries
                queryInfoObjIter = getQueryInfoObjMap(regName, servDesc).values().iterator();
                while (queryInfoObjIter.hasNext()) {
                    queryInfoObjIter.next().unregister();
                    queryInfoObjIter.remove();
                }
                    //unregister Query objectNames (Queries - Continuous/Snapshot) at the Agent Level
                if ( mbs.isRegistered( getServiceObjectName(regName,servDesc) ) )
                    mbs.unregisterMBean( getServiceObjectName(regName, servDesc) );
            }
        }
    }

    public void refresh() throws MalformedObjectNameException {                                      //todo confirm exceptions handling
        for ( String regionName : listRegionNames() ) {
                // register query MBean for continuous queries
            registerQueryMBeans( regionName, servicesDescription[0],listContinuousQueries(regionName) );
                //register query MBean for snapshot queries
            registerQueryMBeans( regionName, servicesDescription[1], listSnapshotQueries(regionName) );
        }
    }

    //-------------

        // this class contains all the collections and variables that handle the registration, update, and unresgistration
        // of the Service(agents) and Query Model MBeans
     public static class Members {                              //todo rename this class  + comments
         protected Collection<ReteQuery> continuousQueries;
         protected Collection<ReteQuery> snapshotQueries;
         protected Map<ReteQuery, QueryInfoImpl> contQueryInfoObjMap;
         protected Map<ReteQuery, QueryInfoImpl> snapshotQueryInfoObjMap;
         protected ObjectName contQueriesObjName;
         protected ObjectName snapshotQueriesObjName;

         public Members() throws MalformedObjectNameException {
             this.continuousQueries = new HashSet<ReteQuery>();
             this.snapshotQueries = new HashSet<ReteQuery>();
             this.contQueryInfoObjMap = new HashMap<ReteQuery, QueryInfoImpl>();              //TODO is this the best way to do it?
             this.snapshotQueryInfoObjMap = new HashMap<ReteQuery, QueryInfoImpl>();
             this.contQueriesObjName = new ObjectName("");
             this.snapshotQueriesObjName = new ObjectName("");
         }
    }

//    getter and setter methods    TODO change the name from put to set ????
    protected void setServiceQueries (String regName, String servDesc, Collection<ReteQuery> reteQuery) {
        if (servDesc.equals(servicesDescription[0]))             //continuous queries
            queryMembersInfoMap.get(regName).continuousQueries = reteQuery;
        else if (servDesc.equals(servicesDescription[1]))        //snapshot queries
            queryMembersInfoMap.get(regName).snapshotQueries = reteQuery;
    }

    protected Collection<ReteQuery> getServiceQueries (String regName, String servDesc) {
        if ( servDesc.equals(servicesDescription[0]) )                                          //todo WHAT's this ????
            return queryMembersInfoMap.get(regName).continuousQueries;
        else if (servDesc.equals(servicesDescription[1]))
            return queryMembersInfoMap.get(regName).snapshotQueries;
        else
            return null;
    }

    protected Map<ReteQuery, QueryInfoImpl> getQueryInfoObjMap(String regName, String servDesc) {
        if (servDesc.equals(servicesDescription[0]))
            return queryMembersInfoMap.get(regName).contQueryInfoObjMap;
        else if (servDesc.equals(servicesDescription[1]))
            return queryMembersInfoMap.get(regName).snapshotQueryInfoObjMap;
        else
            return null;
    }
                                                //TODO change name from put to set ????
    protected void setQueryInfoObjMap (String regName, String servDesc, Map<ReteQuery, QueryInfoImpl> queryInfoObjMap) {
        if (servDesc.equals(servicesDescription[0]))                     //continuous queries
            queryMembersInfoMap.get(regName).contQueryInfoObjMap = queryInfoObjMap;           //TODO Is it better to put {} anyways ???
        else if (servDesc.equals(servicesDescription[1]))             //snapshot queries
            queryMembersInfoMap.get(regName).snapshotQueryInfoObjMap = queryInfoObjMap;
    }

    protected void setServiceObjectName(String regName, String servDesc, ObjectName objNameServiceMBean) {
        if (servDesc.equals(servicesDescription[0]))
            queryMembersInfoMap.get(regName).contQueriesObjName = objNameServiceMBean;
        else if (servDesc.equals(servicesDescription[1]))
            queryMembersInfoMap.get(regName).snapshotQueriesObjName = objNameServiceMBean;
    }

    protected ObjectName getServiceObjectName(String regName, String servDesc) {
        if (servDesc.equals(servicesDescription[0]))
            return queryMembersInfoMap.get(regName).contQueriesObjName;
        else if (servDesc.equals(servicesDescription[1]))
            return queryMembersInfoMap.get(regName).snapshotQueriesObjName;
        else
            return null;
    }
}
