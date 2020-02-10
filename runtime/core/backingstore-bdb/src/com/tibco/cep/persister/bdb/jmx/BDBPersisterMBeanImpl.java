package com.tibco.cep.persister.bdb.jmx;

import com.tibco.cep.persister.bdb.BDBPersistenceConstants;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import java.lang.management.ManagementFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pgowrish
 * Date: 8/29/12
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class BDBPersisterMBeanImpl extends ASPersisterMBeanImpl {

    public void register() throws Exception {
        MBeanServer platform = ManagementFactory.getPlatformMBeanServer();
        StandardMBean standardMBean = new StandardMBean(this, PersisterMBean.class);
        ObjectName on = new ObjectName(BDBPersistenceConstants.JMX_ROOT_NAME+", space="+this.name);
        platform.registerMBean(standardMBean, on);
    }

}
