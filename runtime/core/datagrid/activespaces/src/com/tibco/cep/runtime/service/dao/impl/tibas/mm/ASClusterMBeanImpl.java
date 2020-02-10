package com.tibco.cep.runtime.service.dao.impl.tibas.mm;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.mm.AbstractClusterMBeanImpl;

public class ASClusterMBeanImpl extends AbstractClusterMBeanImpl implements ASClusterMBean {

	public ASClusterMBeanImpl(Cluster cluster) {
		super(cluster);
	}
	
	public void registerMBean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("com.tibco.be:service=Cluster,name=" + this.cluster.getClusterName() + "$cluster");
            StandardMBean mBean = new StandardMBean(this, ASClusterMBean.class);
            mbs.registerMBean(mBean, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
