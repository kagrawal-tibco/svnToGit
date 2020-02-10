package com.tibco.cep.runtime.management.impl.cluster.data.space;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.as.space.ASException;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.IndexDef;
import com.tibco.as.space.KeyDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.ASMember;
import com.tibco.as.space.impl.ASMetaspace;

/**
 * Maintains statistics data pertaining to an ActiveSpaces Space.
 * @author mwiley
 */
public class SpaceStatistics{
	
	protected String name;
	protected Map<String, String> definition;
	protected Map<String, SpaceField> schema;
	protected Map<String, SpaceMemberStatistics> members;
	protected Map<String, SpaceIndex> indices;
	protected long totalOriginals;
	protected long totalReplicas;
	protected long totalPuts;
	protected long totalTakes;
	protected long totalGets;
	protected long totalExpires;
	protected long totalEvicts;
	protected long totalLocks;
	protected long totalUnlocks;
	protected long totalInvokes;
	protected long totalQueries;
	protected long totalMisses;
	protected long totalPendingPersists;
	
	/**
	 * @return The Space name.
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The Space Definition (a map of parameterName->value)
	 */
	public Map<String, String> getDefinition(){
		return definition;
	}

	/**
	 * @return The Space Schema. A map of fieldName->{@link SpaceField}
	 */
	public Map<String, SpaceField> getSchema(){
		return schema;
	}

	/**
	 * @return The Space Members. A map of memberId->{@link SpaceMemberStatistics}
	 */
	public Map<String, SpaceMemberStatistics> getMembers(){
		return members;
	}

	/**
	 * @return The Space Indices. A map of indexName->{@link SpaceIndex}
	 */
	public Map<String, SpaceIndex> getIndices(){
		return indices;
	}

	/**
	 * @return The aggregate sum of all Member's originals values.
	 */
	public long getTotalOriginals(){
		return totalOriginals;
	}

	/**
	 * @return The aggregate sum of all Member's replicas values.
	 */
	public long getTotalReplicas(){
		return totalReplicas;
	}

	/**
	 * @return The aggregate sum of all Member's puts values.
	 */
	public long getTotalPuts(){
		return totalPuts;
	}

	/**
	 * @return the The aggregate sum of all Member's takes values.
	 */
	public long getTotalTakes(){
		return totalTakes;
	}

	/**
	 * @return the The aggregate sum of all Member's gets values.
	 */
	public long getTotalGets(){
		return totalGets;
	}

	/**
	 * @return The aggregate sum of all Member's expires values.
	 */
	public long getTotalExpires(){
		return totalExpires;
	}
	

	/**
	 * @return the totalEvicts
	 */
	public long getTotalEvicts(){
		return totalEvicts;
	}

	/**
	 * @return the totalLocks
	 */
	public long getTotalLocks(){
		return totalLocks;
	}

	/**
	 * @return the totalUnlocks
	 */
	public long getTotalUnlocks(){
		return totalUnlocks;
	}
	
	/**
	 * @return the totalInvokes
	 */
	public long getTotalInvokes(){
		return totalInvokes;
	}

	/**
	 * @return the totalQueries
	 */
	public long getTotalQueries(){
		return totalQueries;
	}

	/**
	 * @return the totalMisses
	 */
	public long getTotalMisses(){
		return totalMisses;
	}

	/**
	 * @return the totalPendingPersists
	 */
	public long getTotalPendingPersists(){
		return totalPendingPersists;
	}

	/**
	 * @param spaceName The name of the Space.
	 */
	public SpaceStatistics(String spaceName){
		name = spaceName;
		definition = new HashMap<String, String>();
		schema = new HashMap<String, SpaceField>();
		members = new HashMap<String, SpaceMemberStatistics>();
		indices = new HashMap<String, SpaceIndex>();
		totalOriginals = totalReplicas = totalPuts = totalTakes = totalGets = totalExpires = 0;
	}
	
	/**
	 * Creates the statistics for this Space.    
	 * @param ownerMetaspace {@link Metaspace} to which the space belongs.
	 */
	public void initForMetaspace(Metaspace ownerMetaspace){
		try{
			SpaceDef spaceDef = ownerMetaspace.getSpace(name).getSpaceDef();
			buildAndSetDefinition(ownerMetaspace, spaceDef);
			buildAndSetSchema(spaceDef);
			buildAndSetIndicies(spaceDef);
		}
		catch(ASException ex){
			System.err.println("SpaceStatistics.initForMetaspace - " + ex.getMessage());
		}
	}
	
	/**
	 * Creates or updates a space member using values from the provided tuple.
	 * @param metaspace {@link Metaspace} to which the space belongs
	 * @param tuple the {@link Tuple} to pull the values from when creating/updating a {@link SpaceMemberStatistics} member.
	 */
	public void addMemberStatFromTuple(Metaspace metaspace, Tuple tuple){
		String memberId = tuple.getString("member_id");
		long originals = tuple.getLong("original_count");
		long replicas = tuple.getLong("replica_count");
		long puts = tuple.getLong("put_count");
		long takes = tuple.getLong("take_count");
		long gets = tuple.getLong("get_count");
		long expires  = tuple.getLong("expire_count");
		long evicts = tuple.getLong("evict_count");
		long locks = tuple.getLong("lock_count");
		long unlocks = tuple.getLong("unlock_count");
		long invokes = tuple.getLong("invoke_count");
		long queries = tuple.getLong("query_count");
		long misses = tuple.getLong("miss_count");
		long pendingPersists = tuple.getLong("pending_persist_count");
		
		if(members.containsKey(memberId)){
			SpaceMemberStatistics memberStats = members.get(memberId);
			memberStats.setOriginals(memberStats.getOriginals() + originals);
			memberStats.setReplicas(memberStats.getReplicas() + replicas);
			memberStats.setPuts(memberStats.getPuts() + puts);
			memberStats.setTakes(memberStats.getTakes() + takes);
			memberStats.setGets(memberStats.getGets() + gets);
			memberStats.setExpires(memberStats.getExpires() + expires);
			memberStats.setEvicts(memberStats.getEvicts() + evicts);
			memberStats.setLocks(memberStats.getLocks() + locks);
			memberStats.setUnlocks(memberStats.getUnlocks() + unlocks);
			memberStats.setInvokes(memberStats.getInvokes() + invokes);
			memberStats.setQueries(memberStats.getQueries() + queries);
			memberStats.setMisses(memberStats.getMisses() + misses);
			memberStats.setPendingPersists(memberStats.getPendingPersists() + pendingPersists);
		}
		else{
			ASMember tupleOwner = new ASMember(metaspace, memberId);
			SpaceMemberStatistics spaceMemberStats = new SpaceMemberStatistics(memberId)
				.setName(tupleOwner.getName())
				.setDistributionRole(tupleOwner.getDistributionRole(name).toString().toLowerCase())
				.setOriginals(originals)
				.setReplicas(replicas)
				.setPuts(puts)
				.setTakes(takes)
				.setGets(gets)
				.setExpires(expires)
				.setEvicts(evicts)
				.setLocks(locks)
				.setUnlocks(unlocks)
				.setInvokes(invokes)
				.setQueries(queries)
				.setMisses(misses)
				.setPendingPersists(pendingPersists);
			members.put(memberId, spaceMemberStats);
		}
		totalOriginals += originals;
		totalReplicas += replicas;
		totalPuts += puts;
		totalTakes += takes;
		totalGets += gets;
		totalExpires += expires;
		totalEvicts += evicts;
		totalLocks += locks;
		totalUnlocks += unlocks;
		totalInvokes += invokes;
		totalQueries += queries;
		totalMisses += misses;
		totalPendingPersists += pendingPersists;
	}
	
	/**
	 * Builds and sets the Space parameterName->parameterValue map that defines this Space.
	 * @param metaspace {@link Metaspace} to which the Space belongs
	 * @param spaceDef {@link SpaceDef} from which Space definition prameters should be used 
	 */
	public void buildAndSetDefinition(Metaspace metaspace, SpaceDef spaceDef){
		if(spaceDef == null || spaceDef.getName().equals(this.name) == false){
			throw new IllegalArgumentException("Provided SpaceDef is invalid for does not correspond with this space.");
		}
		String state = "unknown";
		try{
			state = ((ASMetaspace) metaspace).getSpaceState(this.name).toString();
		}
		catch(Exception ex){
			System.err.println("SpaceStatistics.buildAndSetDefinition - Building space definition for space " + name + " failed due to: " + ex.getMessage());
			return;
		}
		//Makes AS calls because there's really no reason to store this in the cache
		definition = new HashMap<String, String>();
		definition.put("state", state);
		definition.put("distributionPolicy", spaceDef.getDistributionPolicy().toString());
		definition.put("replicationCount", String.valueOf(spaceDef.getReplicationCount()));
		definition.put("replicationPolicy", spaceDef.getReplicationPolicy().toString());
		definition.put("capacity", String.valueOf(spaceDef.getCapacity()));
		definition.put("evictionPolicy", spaceDef.getEvictionPolicy().toString());
		definition.put("minSeeders", String.valueOf(spaceDef.getMinSeederCount()));
		definition.put("persistencePolicy", spaceDef.getPersistencePolicy().toString());
		definition.put("persistenceType", spaceDef.getPersistenceType().toString());
		definition.put("updateTransport", spaceDef.getUpdateTransport().toString());
		definition.put("entryTTL", String.valueOf(spaceDef.getTTL()));
		definition.put("lockScope", spaceDef.getLockScope().toString());
		definition.put("lockWait", String.valueOf(spaceDef.getLockWait()));
		definition.put("lockTTL", String.valueOf(spaceDef.getLockTTL()));
	}
	
	/**
	 * Builds and sets the map of Space indexName->{@link SpaceIndex} that stores all indices for this Space.
	 * @param spaceDef {@link SpaceDef} from which Space indices should be parsed
	 */
	public void buildAndSetIndicies(SpaceDef spaceDef){
		if(spaceDef == null || spaceDef.getName().equals(this.name) == false){
			throw new IllegalArgumentException("Provided SpaceDef is invalid for does not correspond with this space.");
		}
		this.indices = new HashMap<String, SpaceIndex>();
		
		//add primary key
		String currentKeyType = spaceDef.getKeyDef().getIndexType().toString();
		String currentKeyFields = stringCollectionToString(spaceDef.getKeyDef().getFieldNames());
		this.indices.put("_PrimaryKey", new SpaceIndex("_PrimaryKey", currentKeyType, currentKeyFields));
		
		//add remaining indices
		for(IndexDef index:spaceDef.getIndexDefList()){
			currentKeyType = index.getIndexType().toString();
			currentKeyFields = stringCollectionToString(index.getFieldNames());
			this.indices.put(index.getName(), new SpaceIndex(index.getName(), currentKeyType, currentKeyFields));
		}
	}
	
	/**
	 * Builds and sets the map of Space fieldName->{@link SpaceField} that stores all fields for this Space.
	 * @param spaceDef {@link SpaceDef} from which Space fields should be parsed
	 */
	public void buildAndSetSchema(SpaceDef spaceDef){
		if(spaceDef == null || spaceDef.getName().equals(this.name) == false){
			throw new IllegalArgumentException("Provided SpaceDef is invalid for does not correspond with this space.");
		}
		this.schema = new HashMap<String, SpaceField>();
		KeyDef keys = spaceDef.getKeyDef();
		Iterator<FieldDef> fieldDefsItr = spaceDef.getFieldDefs().iterator();
		while(fieldDefsItr.hasNext()){
			FieldDef fieldDef = fieldDefsItr.next();
			schema.put(
				fieldDef.getName(),
				new SpaceField(
					fieldDef.getName(),
					fieldDef.getType().name(),
					fieldDef.isNullable(),
					keys.getFieldNames().contains(fieldDef.getName())
				)
			);
		}
	}
	
	/**
	 * Combines all Strings in a collection into a single string enclosed with square brackets and delimited with commas.
	 * @param strings {@link Collection} of strings to combine
	 * @return combined single string
	 */
	protected static String stringCollectionToString(Collection<String> strings){
		StringBuilder strBuilder = new StringBuilder("[");
		Iterator<String> iter = strings.iterator();
		while(iter.hasNext()){
			strBuilder.append(iter.next());
			if(iter.hasNext()){ strBuilder.append(", "); }
		}
		strBuilder.append("]");
		return strBuilder.toString();
	}

}
