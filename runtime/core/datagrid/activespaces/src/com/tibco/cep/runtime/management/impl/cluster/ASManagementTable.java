package com.tibco.cep.runtime.management.impl.cluster;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.ASException;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.cep.as.kit.map.SpaceMap;
import com.tibco.cep.as.kit.map.SpaceMapCreator;
import com.tibco.cep.as.kit.tuple.SerializableTupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.ASUtil;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.DomainKey;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMember;
import com.tibco.cep.runtime.service.cluster.gmp.GroupMemberServiceListener;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASDaoProvider;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.GvCommonUtils;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * @author Nick
 *
 */
public class ASManagementTable implements InternalManagementTable {
    
    public static final String DATA_SPACE = "tibco-be-internal-domain-data-cache";
    public static final String MEMBER_SPACE = "tibco-be-internal-domain-member-cache";
    public static final long EXPIRY_MILLIS = 15 * 60 * 1000;
    Metaspace metaspace;
    SpaceMap<FQName, Domain> spaceMap;
    SpaceMap<String, String> memberMap;
    MemberDef cd;
    String memberId;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.cep.runtime.management.impl.cluster.InternalManagementTable
     * #discard()
     */
    @Override
    public void discard() {
        spaceMap = null;
    }
    
    public void setConnectionDef(MemberDef cd) {
    	this.cd = cd;
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.impl.cluster.InternalManagementTable#init(java.lang.String)
     */
    @Override
    public void init(String clusterName, String role) {
        String metaspaceName = this.sanitizeName(clusterName);
        String backupCountStr = "1";
        Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
        if (cluster != null) {
        	memberId = ((ASDaoProvider)cluster.getDaoProvider()).getMetaspace().getSelfMember().getName();
        	if(GvCommonUtils.isGlobalVar(metaspaceName)){
             	GlobalVariables gvs=cluster.getRuleServiceProvider().getGlobalVariables();
             	GlobalVariableDescriptor gv =gvs.getVariable(GvCommonUtils.stripGvMarkers(metaspaceName));
             	metaspaceName=gv.getValueAsString();
             }
        }
        if (cd == null) {
        	String listenUrl,remoteListenUrl,discoverUrl;
        	if (cluster != null) {
        		GlobalVariables gVs = cluster.getRuleServiceProvider().getGlobalVariables();
    	    	listenUrl = gVs.substituteVariables(System.getProperty(ASConstants.PROP_LISTEN_URL)).toString();
    	        remoteListenUrl = gVs.substituteVariables(System.getProperty(ASConstants.PROP_REMOTE_LISTEN_URL)).toString();
    	    	discoverUrl = gVs.substituteVariables(System.getProperty(ASConstants.PROP_DISCOVER_URL)).toString();
    	        backupCountStr = gVs.substituteVariables(System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1")).toString();
        	} else {
        		listenUrl = System.getProperty(ASConstants.PROP_LISTEN_URL).toString();
    	        remoteListenUrl = System.getProperty(ASConstants.PROP_REMOTE_LISTEN_URL);
    	    	discoverUrl = System.getProperty(ASConstants.PROP_DISCOVER_URL);
    	        backupCountStr = System.getProperty(SystemProperty.CLUSTER_BACKUP_COUNT.getPropertyName(), "1");
        	}
            cd = MemberDef.create()
                .setDiscovery(discoverUrl)
                .setListen(listenUrl)
                .setRemoteListen(remoteListenUrl);
        }
        metaspace = ASCommon.getMetaspace(metaspaceName);
        if (metaspace == null) {
            try {
                metaspace = Metaspace.connect(metaspaceName, cd);
            } catch (ASException e) {
            	throw new RuntimeException(e);
            }
        }
        DistributionRole drole = null;
        if (role != null) {
            if (role.equals("seeder")) {
                drole = DistributionRole.SEEDER;
            } else {
                drole = DistributionRole.LEECH;
            }
        } else {
            drole = DistributionRole.LEECH;
        }

        SpaceMapCreator.Parameters<FQName, Domain> dataParameters =
                new SpaceMapCreator.Parameters<FQName, Domain>()
                        .setSpaceName(DATA_SPACE)
                        .setRole(drole)
                        .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                        .setKeyClass(FQName.class)
                        .setValueClass(Domain.class)
                        .setTupleCodec(new SerializableTupleCodec())
                        .setTtl(EXPIRY_MILLIS)
                        .setReplicationCount(Integer.parseInt(backupCountStr))
                        .setMinSeeders(1);

        SpaceMapCreator.Parameters<String, String> memberParameters =
                new SpaceMapCreator.Parameters<String, String>()
                        .setSpaceName(MEMBER_SPACE)
                        .setRole(drole)
                        .setDistributionPolicy(DistributionPolicy.DISTRIBUTED)
                        .setKeyClass(String.class)
                        .setValueClass(String.class)
                        .setTupleCodec(new SerializableTupleCodec())
                        .setTtl(EXPIRY_MILLIS)
                        .setReplicationCount(Integer.parseInt(backupCountStr))
                        .setMinSeeders(1);

        try {
            spaceMap = SpaceMapCreator.create(metaspace, dataParameters);
            memberMap = SpaceMapCreator.create(metaspace, memberParameters);

            if (drole == DistributionRole.SEEDER) {
            	SpaceMemberAdapter memberListener = new SpaceMemberAdapter();
            	cluster.getGroupMembershipService().addGroupMemberServiceListener(memberListener);
            }
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
    
    public String sanitizeName(String s) {
        return ASUtil.asFriendlyEncode(s, null);
    }
    
    public void setMemberInfo(String memberInfo){
    	memberMap.put(memberId, memberInfo);
    }

	public Map<String, String> getMembersInfo() {
		return (new LinkedHashMap<>(memberMap));
	}

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.ManagementTable#getDomain(com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public Domain getDomain(FQName key) {
        return spaceMap.get(key);
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.ManagementTable#getDomains()
     */
    @Override
    public Collection<Domain> getDomains() {
        return spaceMap.values();
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.ManagementTable#putOrRenewDomain(com.tibco.cep.runtime.management.Domain, long)
     */
    @Override
    public void putOrRenewDomain(Domain domain, long leaseDurationMillis) {
        FQName domainName = domain.safeGet(DomainKey.NAME);
        try {
        	spaceMap.put(domainName, domain);
        } catch(Throwable t) {
        	if (t.getMessage().contains("not_enough_seeders")) {
        		LogManagerFactory.getLogManager().getLogger(getClass())
                	.log(Level.DEBUG, "Domain renew failed", t);
        	}
        	else {
        		throw new RuntimeException(t);
        	}
        }
    }

    /* (non-Javadoc)
     * @see com.tibco.cep.runtime.management.ManagementTable#removeDomain(com.tibco.cep.runtime.util.FQName)
     */
    @Override
    public Domain removeDomain(FQName key) {
        return spaceMap.remove(key);
    }
    protected class SpaceMemberAdapter implements GroupMemberServiceListener {
    	public void memberJoined(GroupMember member) {
        	
        }
        public void memberStatusChanged(GroupMember member, Status oldStatus, Status newStatus) {
        
        }        

        public void memberLeft(GroupMember member) {
            String info = memberMap.remove(member.getMemberId().getAsString());
            if (info != null && info.length() > 0 && spaceMap!=null) {
            	for(Domain domain:spaceMap.values()) {
    				if (domain == null) {
    					continue;
    				}			
    				FQName domainName = domain.safeGet(DomainKey.NAME);
    				String[] componentNames = domainName.getComponentNames();
    				if(componentNames[1] == null || componentNames[1] == "") break;
    				String nameInTable = componentNames[1].toLowerCase();
    				if (nameInTable.equalsIgnoreCase(info) || nameInTable.startsWith(info) || info.startsWith(nameInTable)) {
    					removeDomain(domainName);
    					LogManagerFactory.getLogManager().getLogger(getClass()).log(Level.INFO,domainName.toString()+" has been removed from Management Table.");
    				}
            	}
            }
        }
    }
    
//    public static void main(String[] args) {
//        ASManagementTable mt = new ASManagementTable();
//        mt.init("test","leech");
//    }
}
