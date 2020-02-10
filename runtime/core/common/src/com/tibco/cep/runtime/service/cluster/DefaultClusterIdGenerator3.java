/**
 * @author bala
 * 
 * 
 */

package com.tibco.cep.runtime.service.cluster;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.runtime.service.cluster.system.ClusterIdGenerator;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.sequences.SequenceManager;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author bala
 * 
 */

/**
 * This Id Generator issues out Blocked Ids to an entity or for it type;
 */
public class DefaultClusterIdGenerator3 implements ClusterIdGenerator {

	Cluster cluster;
	private final Lock idLock = new ReentrantLock();
	private final String CLUSTER_SEQUENCE = "ClusterSequence";
	private long startBlock;
	private final static int BLOCK_SIZE = Integer
			.parseInt(System.getProperty(SystemProperty.CLUSTER_BLOCK_ID_SIZE.getPropertyName(), "10000"));

	SequenceManager seqManager;

	public DefaultClusterIdGenerator3() {

	}

	public void init(Cluster cluster) throws Exception {
		this.cluster = cluster;
		seqManager = cluster.getSequenceManager();
		((RuleServiceProviderImpl) cluster.getRuleServiceProvider()).setIdGenerator(this);
		seqManager.createSequence(CLUSTER_SEQUENCE, 0, Long.MAX_VALUE, BLOCK_SIZE,
				cluster.getClusterConfig().isHasBackingStore());
		seqManager.nextSequence(CLUSTER_SEQUENCE);
	}

	public void start() {
	}

	public long nextEntityId(Class clz) {
		idLock.lock();
		try {
			long id = seqManager.nextSequence(CLUSTER_SEQUENCE);
			return id;
		} catch (Exception e) {
			throw new RuntimeException("Error while fetching nextEntityId from CLUSTER_ID_GENERATOR sequence");
		} finally {
			idLock.unlock();
		}
	}

	@Override
	public long nextEntityId() {
		return nextEntityId(null);
	}

	@Override
	public void setMinEntityId(long min) {
		// TODO:6:BS Auto-generated method stub

	}
}
