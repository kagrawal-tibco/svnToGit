package com.tibco.cep.query.stream.impl.monitor.jmx;

import java.lang.management.ManagementFactory;

import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;

import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.monitor.model.QueryInfo;
import com.tibco.cep.query.stream.impl.monitor.view.NoGetterMBeanInfo;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro                                    N
 * Date: Jul 25, 2008
 * Time: 3:41:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryInfoImpl extends QueryInfo implements com.tibco.cep.query.stream.impl.monitor.model.Info {

    private ObjectName objNameQueryMBean;
    private QueryTraceInfoImpl queryTraceInfo;
    private MBeanServer mbs;

    //constructor
    public QueryInfoImpl(QueryMonitor monitor, ReteQuery currentReteQueryObj,  ObjectName objNameServiceMBean) throws MalformedObjectNameException {
        super(currentReteQueryObj, monitor);
        this.objNameQueryMBean = new ObjectName (objNameServiceMBean + ",query=" + currentReteQueryObj.getName().replace(":","."));
    }

        //Operations Methods
    public void ping(){
        monitor.pingQuery(currentReteQueryObj);
    }

    public void pause() {
        monitor.pauseQuery(currentReteQueryObj);
    }

    public void resumeQuery() {
         monitor.resumeQuery(currentReteQueryObj);
    }

    public void enableTracing() throws Exception {
        monitor.enableTracing(currentReteQueryObj);
        if ( !mbs.isRegistered(new ObjectName(objNameQueryMBean+",QueryTrace=QueryTrace") ) ) {
            queryTraceInfo = new QueryTraceInfoImpl(objNameQueryMBean, monitor.fetchTrace(currentReteQueryObj), this);
            queryTraceInfo.register();
        }
    }

    public void disableTracing() throws MBeanRegistrationException, InstanceNotFoundException {
        if (queryTraceInfo != null ) {
            monitor.disableTracing(currentReteQueryObj);
            queryTraceInfo.unregister();
        }
    }

    public void register() throws NoSuchMethodException, IntrospectionException {          //todo see exception handling
        mbs = ManagementFactory.getPlatformMBeanServer();
        try {
                //create attributes descriptor and metadata
            String[] attributeName = {"paused","stopped","tracingEnabled","pendingEntityCount","accumulatedEntityCountDuringSS"};
            String[] attrGetMethods = {"isPaused", "isStopped", "isTracingEnabled", "getPendingEntityCount", "getAccumulatedEntityCountDuringSS"};
            final String attDescConst = "Boolean attribute indicating if this query ";
            String[] attributeDescription = {attDescConst + "is paused",attDescConst + "is stopped",attDescConst + "trace is enabled",
                                             "Number of cluster messages received by Query but pending Query processing",
                                             "Only for SS + CQ mode: The real time cache changes that are pending while Query is still processing SS messages"};

            Descriptor[] attributeDescs = new DescriptorSupport[attributeName.length];
            for (int j=0; j<attributeName.length; j++){
                attributeDescs[j] = new DescriptorSupport();
                attributeDescs[j].setField("name",attributeName[j]);
                attributeDescs[j].setField("descriptorType","attribute");
                attributeDescs[j].setField("getMethod",attrGetMethods[j] );
            }

            ModelMBeanAttributeInfo[] mmbai = new ModelMBeanAttributeInfo[attributeName.length];
            for (int j=0; j<attributeName.length; j++)                                                       //todo review descriptions of attributes
                mmbai[j] = new ModelMBeanAttributeInfo(attributeName[j],attributeDescription[j], QueryInfo.class.getDeclaredMethod(attrGetMethods[j]) ,null, attributeDescs[j]);

                //create operations descriptor and metadata
            String[] operName = {"ping","pause","resumeQuery","enableTracing","disableTracing","isPaused","isStopped",
                                 "isTracingEnabled","getPendingEntityCount","getAccumulatedEntityCountDuringSS"};

            String[] operDescription = { "Pings this query","Pauses this query processing. Click 'resumeQuery' to re-initiate query processing",
                                         "Re-initiates query processing. Click 'pause' to hold query processing ","Enables query tracing","Disables query tracing",
                                         null, null, null, null, null };

            //this descriptor is used to avoid the (useless but required) operations with the same name of the corresponding attributes
            //to be visible in the JMX client console. The trick is to assign the value "getter" to the "role", and create
            //an instance of the NoGetterMBeanInfo class in the RequiredModelMBean(..) constructor. By doing this we are serializing
            //an instance of ModelMBeanInfoSupport after filtering the undesired operations, thus avoiding them to appear in the console
            Descriptor operDescs[] = new DescriptorSupport[operName.length];
            for (int i=5; i<operDescs.length; i++){
                operDescs[i] = new DescriptorSupport();
                operDescs[i].setField("name",operName[i]);
                operDescs[i].setField("descriptorType","operation");
                operDescs[i].setField("role","getter");
            }

            ModelMBeanOperationInfo[] mmboi = new ModelMBeanOperationInfo[operName.length];
            for (int i=0; i<operName.length; i++)
                mmboi[i] = new ModelMBeanOperationInfo(operDescription[i], QueryInfoImpl.class.getMethod(operName[i]), operDescs[i] );   //todo CHANGED HERE

                //create MBean metadata info
            ModelMBeanInfo mmbi = new ModelMBeanInfoSupport(QueryInfoImpl.class.getName(), "MBean that is managing and monitoring query: " +
                                        currentReteQueryObj.getName().replace(":","."), mmbai, null, mmboi, null, null);

            RequiredModelMBean requiredModelMBean = new RequiredModelMBean(new NoGetterMBeanInfo(mmbi)); //instruments the resource (ie creates metadata)
            requiredModelMBean.setManagedResource(this, "ObjectReference");         //defines the management interface
            mbs.registerMBean(requiredModelMBean, objNameQueryMBean);               //registers (exposes) resource for monitoring and management

                //if this query starts with trace already enabled (before one clicks the enableTracing() button), then register QueryTraceInfo MBean.
            if ( isTracingEnabled() ) {
                if ( !mbs.isRegistered(new ObjectName(objNameQueryMBean+",QueryTrace=QueryTrace") ) ) {
                    queryTraceInfo = new QueryTraceInfoImpl(objNameQueryMBean, monitor.fetchTrace(currentReteQueryObj), this);          //todo ask ASHWIN
                    queryTraceInfo.register();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregister() throws MBeanRegistrationException, InstanceNotFoundException, MalformedObjectNameException {
        if (queryTraceInfo != null)
            queryTraceInfo.unregister();
        if (mbs.isRegistered(objNameQueryMBean))
            mbs.unregisterMBean(objNameQueryMBean);
    }

    public void refresh() {
        //todo ASHWIN - I don't really need this in here ... leave it anyways ??? Interface implications ...
    }
}
