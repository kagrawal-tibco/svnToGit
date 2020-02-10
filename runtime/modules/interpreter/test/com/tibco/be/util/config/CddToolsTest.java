package com.tibco.be.util.config;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DomainObjectsConfig;
import com.tibco.be.util.config.cdd.impl.ClusterConfigSerializableWrapper;
import com.tibco.be.util.config.cdd.impl.DomainObjectsConfigSerializableWrapper;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.util.SystemProperty;

import junit.framework.TestCase;

public class CddToolsTest extends TestCase {

	private RuleServiceProvider rsp;

	public CddToolsTest() throws Exception {
		 final Properties props = new Properties();
	        props.load(new FileReader("/home/nprade/workspace/runtime/5.1/build/015/be/5.1/bin/be-engine.tra"));
	        props.setProperty("tibco.repourl", "/home/nprade/workspace/runtime/5.1/issues/test/test.ear");
	        props.setProperty(SystemProperty.CLUSTER_CONFIG_PATH.getPropertyName(), "cache.cdd");
	        rsp = RuleServiceProviderManager.getInstance().newProvider("test", props);
	        rsp.initProject();
	}
	
	
//	@Test
//	public void testSerialize() throws IOException {
//		final ClusterConfig cluster1 = this.rsp.getProject().getClusterConfig();
//    	
//		final ClusterConfig cluster2 = CddTools.deserialize(CddTools.serialize(cluster1));
//		
//		assertTrue(cluster1.getAgentClasses().toProperties().equals(cluster2.getAgentClasses().toProperties()));
//		assertTrue(cluster1.getDestinationGroups().toProperties().equals(cluster2.getDestinationGroups().toProperties()));
//		assertTrue(cluster1.getFunctionGroups().toProperties().equals(cluster2.getFunctionGroups().toProperties()));
//		assertTrue(cluster1.getLogConfigs().toProperties().equals(cluster2.getLogConfigs().toProperties()));
//		assertTrue(CddTools.getValueFromMixed(cluster1.getName()).equals(CddTools.getValueFromMixed(cluster2.getName())));
//		assertTrue(cluster1.getObjectManagement().toProperties().equals(cluster2.getObjectManagement().toProperties()));
//		assertTrue(cluster1.getProcessingUnits().toProperties().equals(cluster2.getProcessingUnits().toProperties()));
//		assertTrue(cluster1.getPropertyGroup().toProperties().equals(cluster2.getPropertyGroup().toProperties()));
//		assertTrue(cluster1.getRevision().toProperties().equals(cluster2.getRevision().toProperties()));
//		assertTrue(cluster1.getRulesets().toProperties().equals(cluster2.getRulesets().toProperties()));
//    }
	
//	@Test
//	public void testSerializableLite() throws IOException, ClassNotFoundException {
//		final ClusterConfig cluster1 = this.rsp.getProject().getClusterConfig();
//    	
//		ClusterConfigSerializableWrapper cluster2 = new ClusterConfigSerializableWrapper(cluster1);
//		
//		assertTrue(cluster1.getAgentClasses().toProperties().equals(cluster2.getAgentClasses().toProperties()));
//		assertTrue(cluster1.getDestinationGroups().toProperties().equals(cluster2.getDestinationGroups().toProperties()));
//		assertTrue(cluster1.getFunctionGroups().toProperties().equals(cluster2.getFunctionGroups().toProperties()));
//		assertTrue(cluster1.getLogConfigs().toProperties().equals(cluster2.getLogConfigs().toProperties()));
//		assertTrue(CddTools.getValueFromMixed(cluster1.getName()).equals(CddTools.getValueFromMixed(cluster2.getName())));
//		assertTrue(cluster1.getObjectManagement().toProperties().equals(cluster2.getObjectManagement().toProperties()));
//		assertTrue(cluster1.getProcessingUnits().toProperties().equals(cluster2.getProcessingUnits().toProperties()));
//		assertTrue(cluster1.getPropertyGroup().toProperties().equals(cluster2.getPropertyGroup().toProperties()));
//		assertTrue(cluster1.getRevision().toProperties().equals(cluster2.getRevision().toProperties()));
//		assertTrue(cluster1.getRulesets().toProperties().equals(cluster2.getRulesets().toProperties()));
//
//		final File tempFile = File.createTempFile("testCddTools", ".tmp");
//		
//		final DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));
//		cluster2.writeExternal(out);
//		out.close();
//		
//		cluster2 = new ClusterConfigSerializableWrapper(null);
//
//		final DataInputStream in = new DataInputStream(new FileInputStream(tempFile)); 
//		cluster2.readExternal(in);
//		in.close();
//
//		assertTrue(cluster1.getAgentClasses().toProperties().equals(cluster2.getAgentClasses().toProperties()));
//		assertTrue(cluster1.getDestinationGroups().toProperties().equals(cluster2.getDestinationGroups().toProperties()));
//		assertTrue(cluster1.getFunctionGroups().toProperties().equals(cluster2.getFunctionGroups().toProperties()));
//		assertTrue(cluster1.getLogConfigs().toProperties().equals(cluster2.getLogConfigs().toProperties()));
//		assertTrue(CddTools.getValueFromMixed(cluster1.getName()).equals(CddTools.getValueFromMixed(cluster2.getName())));
//		assertTrue(cluster1.getObjectManagement().toProperties().equals(cluster2.getObjectManagement().toProperties()));
//		assertTrue(cluster1.getProcessingUnits().toProperties().equals(cluster2.getProcessingUnits().toProperties()));
//		assertTrue(cluster1.getPropertyGroup().toProperties().equals(cluster2.getPropertyGroup().toProperties()));
//		assertTrue(cluster1.getRevision().toProperties().equals(cluster2.getRevision().toProperties()));
//		assertTrue(cluster1.getRulesets().toProperties().equals(cluster2.getRulesets().toProperties()));
//		
//		final ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(tempFile));
//		out2.writeObject(cluster2);
//		out2.close();
//		
//		final ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(tempFile));
//		cluster2 = (ClusterConfigSerializableWrapper) in2.readObject();
//		in.close();
//
//		assertTrue(cluster1.getAgentClasses().toProperties().equals(cluster2.getAgentClasses().toProperties()));
//		assertTrue(cluster1.getDestinationGroups().toProperties().equals(cluster2.getDestinationGroups().toProperties()));
//		assertTrue(cluster1.getFunctionGroups().toProperties().equals(cluster2.getFunctionGroups().toProperties()));
//		assertTrue(cluster1.getLogConfigs().toProperties().equals(cluster2.getLogConfigs().toProperties()));
//		assertTrue(CddTools.getValueFromMixed(cluster1.getName()).equals(CddTools.getValueFromMixed(cluster2.getName())));
//		assertTrue(cluster1.getObjectManagement().toProperties().equals(cluster2.getObjectManagement().toProperties()));
//		assertTrue(cluster1.getProcessingUnits().toProperties().equals(cluster2.getProcessingUnits().toProperties()));
//		assertTrue(cluster1.getPropertyGroup().toProperties().equals(cluster2.getPropertyGroup().toProperties()));
//		assertTrue(cluster1.getRevision().toProperties().equals(cluster2.getRevision().toProperties()));
//		assertTrue(cluster1.getRulesets().toProperties().equals(cluster2.getRulesets().toProperties()));
//}
	
	
	
	@Test
	public void testSerializableLite2() throws IOException, ClassNotFoundException {
		final ClusterConfig cluster1 = this.rsp.getProject().getClusterConfig();
    	Map<Object, Object> p = cluster1.getObjectManagement().getCacheManager().toProperties(); 
    	DomainObjectsConfigSerializableWrapper doc1 = (DomainObjectsConfigSerializableWrapper) p.get(Constants.PROPERTY_NAME_OM_CACHE_ADVANCED_ENTITIES);
    	
		final File tempFile = File.createTempFile("testCddTools", ".tmp");
		
		final DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));
		doc1.writeExternal(out);
		out.close();
		
		DomainObjectsConfigSerializableWrapper doc2 = new DomainObjectsConfigSerializableWrapper(null);

		final DataInputStream in = new DataInputStream(new FileInputStream(tempFile)); 
		doc2.readExternal(in);
		in.close();

		assertTrue(CddTools.getValueFromMixed(doc1.getCacheLimited()).equals(CddTools.getValueFromMixed(doc2.getCacheLimited())));
		assertTrue(CddTools.getValueFromMixed(doc1.getCheckForVersion()).equals(CddTools.getValueFromMixed(doc2.getCheckForVersion())));
		assertTrue(CddTools.getValueFromMixed(doc1.getConstant()).equals(CddTools.getValueFromMixed(doc2.getConstant())));
		assertTrue(CddTools.getValueFromMixed(doc1.getEnableTracking()).equals(CddTools.getValueFromMixed(doc2.getEnableTracking())));
		assertTrue(CddTools.getValueFromMixed(doc1.getEvictOnUpdate()).equals(CddTools.getValueFromMixed(doc2.getEvictOnUpdate())));
		assertTrue(CddTools.getValueFromMixed(doc1.getPreLoadEnabled()).equals(CddTools.getValueFromMixed(doc2.getPreLoadEnabled())));
		assertTrue(CddTools.getValueFromMixed(doc1.getPreLoadFetchSize()).equals(CddTools.getValueFromMixed(doc2.getPreLoadFetchSize())));
		assertTrue(CddTools.getValueFromMixed(doc1.getPreLoadHandles()).equals(CddTools.getValueFromMixed(doc2.getPreLoadHandles())));
		assertTrue(CddTools.getValueFromMixed(doc1.getSubscribe()).equals(CddTools.getValueFromMixed(doc2.getSubscribe())));
		
		final ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(tempFile));
		out2.writeObject(doc2);
		out2.close();
		
		final ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(tempFile));
		doc2 = (DomainObjectsConfigSerializableWrapper) in2.readObject();
		in.close();

		assertTrue(CddTools.getValueFromMixed(doc1.getCacheLimited()).equals(CddTools.getValueFromMixed(doc2.getCacheLimited())));
		assertTrue(CddTools.getValueFromMixed(doc1.getCheckForVersion()).equals(CddTools.getValueFromMixed(doc2.getCheckForVersion())));
		assertTrue(CddTools.getValueFromMixed(doc1.getConstant()).equals(CddTools.getValueFromMixed(doc2.getConstant())));
		assertTrue(CddTools.getValueFromMixed(doc1.getEnableTracking()).equals(CddTools.getValueFromMixed(doc2.getEnableTracking())));
		assertTrue(CddTools.getValueFromMixed(doc1.getEvictOnUpdate()).equals(CddTools.getValueFromMixed(doc2.getEvictOnUpdate())));
		assertTrue(CddTools.getValueFromMixed(doc1.getPreLoadEnabled()).equals(CddTools.getValueFromMixed(doc2.getPreLoadEnabled())));
		assertTrue(CddTools.getValueFromMixed(doc1.getPreLoadFetchSize()).equals(CddTools.getValueFromMixed(doc2.getPreLoadFetchSize())));
		assertTrue(CddTools.getValueFromMixed(doc1.getPreLoadHandles()).equals(CddTools.getValueFromMixed(doc2.getPreLoadHandles())));
		assertTrue(CddTools.getValueFromMixed(doc1.getSubscribe()).equals(CddTools.getValueFromMixed(doc2.getSubscribe())));
}
	

}
