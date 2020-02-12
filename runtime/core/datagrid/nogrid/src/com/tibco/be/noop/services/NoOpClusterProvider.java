package com.tibco.be.noop.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.tibco.be.noop.kit.LocalMap;
import com.tibco.be.util.config.ClusterProviderConfig;
import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.service.cluster.AbstractClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMembershipService.State;
import com.tibco.cep.runtime.service.cluster.gmp.MemberConfiguration;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.service.om.api.GroupMemberMediator;

public class NoOpClusterProvider extends AbstractClusterProvider {

	public NoOpClusterProvider(ClusterProviderConfig beClusterConfig) {
		super(beClusterConfig);
		groupMemberMediator = new Gmp();
	}

	@Override
	public void init(Properties props, Object... objects) throws Exception {
		super.init(props, objects);
		for (ControlDaoType cdType : ControlDaoType.values()) {
			setDaoGroupMember(cdType, groupMemberMediator.getLocalMember().getMemberName());
		}
		
	}

	@Override
	public synchronized <K, V> ControlDao<K, V> createControlDao(Class<K> keyClass, Class<V> valueClass,
			ControlDaoType daoType, Object... additionalProps) throws Exception {
		ControlDao cDao = createControlDaoInternal(daoType);
		return cDao;
	}
	
	
	public void setDaoGroupMember(ControlDaoType daoType, String memberName) {
		Properties props = cluster.getRuleServiceProvider().getProperties();
		ControlDao cDao = controlDaos.get(daoType);
	}

	private <V, K> ControlDao createControlDaoInternal(ControlDaoType daoType) throws Exception {
		Properties props = cluster.getRuleServiceProvider().getProperties();
		ControlDao cDao = controlDaos.get(daoType);
		if (cDao == null) {
			ControlDao<K, V> dao = new LocalMap<K, V>(daoType.getAlias(), daoType);
			controlDaos.put(daoType.getAlias(), dao);
			cDao = dao;
		}
		return cDao;
	}

	class Gmp implements GroupMemberMediator {
		
		GroupMember gm = new LocalGroupMember();
		List list = new ArrayList();
		@Override
		public void init(Cluster cluster) throws Exception {
			list.add(gm);
			
		}

		@Override
		public void addGroupMemberServiceListener(GroupMemberServiceListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeGroupMemberServiceListener(GroupMemberServiceListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public GroupMember getLocalMember() {
			return gm;
		}

		@Override
		public Collection<? extends GroupMember> getMembers() {
			return list;
		}

		@Override
		public Collection<? extends GroupMember> updateAllMembers() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	class LocalGroupMember implements GroupMember {
		
		String internal = UUID.randomUUID().toString();
		UID memberId = new UID (internal);
		private State state;
		private boolean isSeeder = true;

		@Override
		public UID getMemberId() {
			// TODO Auto-generated method stub
			return memberId;
		}

		@Override
		public String getMemberName() {
			return internal;
		}

		@Override
		public void setMemberConfig(MemberConfiguration config) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public MemberConfiguration getMemberConfig() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setMemberState(State state) {
			this.state = state;
		}

		@Override
		public State getMemberState() {
			return state;
		}

		@Override
		public boolean isSeeder() {
			return isSeeder;
		}

		@Override
		public Object getNativeMember() {
			return memberId;
		}
		
	}

}
