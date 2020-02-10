package com.tibco.cep.runtime.management.impl.cluster.data.member;

import java.util.HashMap;
import java.util.Map;

import com.tibco.as.space.Tuple;
import com.tibco.as.space.impl.ASMember;

/**
 * Maintains statistics data pertaining to an ActiveSpaces Member.
 * @author mwiley
 */
public class MemberStatistics{

	public static MemberStatistics build(ASMember tupleOwner, Tuple statsTuple){
		MemberStatistics mStats = new MemberStatistics(tupleOwner.getId())
			.setName(tupleOwner.getName())
			.addMemberSpaceFromTuple(tupleOwner, statsTuple);
		return mStats;
	}

	protected String id;
	protected String name;
	protected long totalOriginals;
	protected long totalReplicas;
	protected long totalPuts;
	protected long totalTakes;
	protected long totalGets;
	protected long totalExpires;
	protected Map<String, MemberSpaceStatistics> joinedSpaces;

	public MemberStatistics(String id){
		this.id = id;
		totalOriginals = 0;
		totalReplicas = 0;
		totalPuts = 0;
		totalTakes = 0;
		totalGets = 0;
		totalExpires = 0;
		joinedSpaces = new HashMap<String, MemberSpaceStatistics>();
	}

	/**
	 * @return The Member's ID.
	 */
	public String getId(){
		return id;
	}

	/**
	 * @return The Member's name.
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return The total number of originals that belong to this member across all Spaces.
	 */
	public long getTotalOriginals(){
		return totalOriginals;
	}

	/**
	 * @return The total number of replicas that belong to this member across all Spaces.
	 */
	public long getTotalReplicas(){
		return totalReplicas;
	}

	/**
	 * @return The total number of puts that this member has performed across all Spaces.
	 */
	public long getTotalPuts(){
		return totalPuts;
	}

	/**
	 * @return The total number of takes that this member has performed across all Spaces.
	 */
	public long getTotalTakes(){
		return totalTakes;
	}

	/**
	 * @return The total number of gets that this member has performed across all Spaces.
	 */
	public long getTotalGets(){
		return totalGets;
	}

	/**
	 * @return The total number of expires that this member has performed across all Spaces.
	 */
	public long getTotalExpires(){
		return totalExpires;
	}

	/**
	 * @return Map of spaceName->{@link MemberSpaceStatistics} for all Spaces to which this Member belongs.
	 */
	public Map<String, MemberSpaceStatistics> getJoinedSpaces(){
		return joinedSpaces;
	}

	/**
	 * @param name The name to use for this Member
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics setName(String name){
		this.name = name;
		return this;
	}

	/**
	 * @param originals The number of originals to add to this Member's total.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addOriginals(long originals){
		this.totalOriginals += originals;
		return this;
	}

	/**
	 * @param replicas The number of replicas to add to this Member's total.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addReplicas(long replicas){
		this.totalReplicas += replicas;
		return this;
	}

	/**
	 * @param puts The number of puts to add to this Member's total.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addPuts(long puts){
		this.totalPuts += puts;
		return this;
	}

	/**
	 * @param takes The number of takes to add to this Member's total.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addTakes(long takes){
		this.totalTakes += takes;
		return this;
	}

	/**
	 * @param gets The number of gets to add to this Member's total.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addGets(long gets){
		this.totalGets += gets;
		return this;
	}

	/**
	 * @param expires The number of expires to add to this Member's total.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addExpires(long expires){
		this.totalExpires += expires;
		return this;
	}
	
	/**
	 * Adds a {@link MemberSpaceStatistics} object to this Member's set of joined Spaces.
	 * @param spaceName The name of the space that the member belogs to
	 * @param msStats The {@link MemberSpaceStatistics} for this Member in the specified space.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addMemberSpace(String spaceName, MemberSpaceStatistics msStats){
		joinedSpaces.put(spaceName, msStats);
		return this;
	}

	/**
	 * Creates or updates a member space using values from the provided tuple.
	 * @param tupleOwner {@link ASMember} that corresponds to this Member
	 * @param tuple the {@link Tuple} to pull the values from when creating/updating a {@link MemberSpaceStatistics} space.
	 * @return this {@link MemberStatistics} for convenience
	 */
	public MemberStatistics addMemberSpaceFromTuple(ASMember tupleOwner, Tuple tuple){
		long originals, replicas, puts, takes, gets, expires;
		MemberSpaceStatistics msStat = null;
		
		//Get stats from tuple
		originals = tuple.getLong("original_count");
		replicas = tuple.getLong("replica_count");
		puts = tuple.getLong("put_count");
		takes = tuple.getLong("take_count");
		gets = tuple.getLong("get_count");
		expires = tuple.getLong("expire_count");
		
		//Add stats to member totals
		totalOriginals += originals;
		totalReplicas += replicas;
		totalPuts += puts;
		totalTakes += takes;
		totalGets += gets;
		totalExpires += expires;
		
		//Create and add (or update existing) MemberSpaceStatistics for the space
		String spaceName = tuple.getString("space_name");
		if(joinedSpaces.containsKey(spaceName)){
			//This shouldn't really happen and is a rather ambiguous state. Options:
				//1) leave the old stats
				//2) add the new stats to the existing
				//3) replace existing stats with new ones
			//Since the function is named "add..." we're going with option 2
			msStat = joinedSpaces.get(spaceName);
			msStat.setOriginals(msStat.getOriginals() + originals);
			msStat.setReplicas(msStat.getReplicas() + replicas);
			msStat.setPuts(msStat.getPuts() + puts);
			msStat.setTakes(msStat.getTakes() + takes);
			msStat.setGets(msStat.getGets() + gets);
			msStat.setExpires(msStat.getExpires() + expires);
		}
		else{
			String distRole = tupleOwner.getDistributionRole(spaceName).toString().toLowerCase();
			msStat = new MemberSpaceStatistics(spaceName, distRole, originals, replicas, puts, takes, gets, expires);
		}
		joinedSpaces.put(spaceName, msStat);

		return this;
	}

}