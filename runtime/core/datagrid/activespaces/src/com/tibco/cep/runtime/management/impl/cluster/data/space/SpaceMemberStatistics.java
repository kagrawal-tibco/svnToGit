package com.tibco.cep.runtime.management.impl.cluster.data.space;

public class SpaceMemberStatistics{
	
	protected String id, name, distributionRole;
	protected long originals;
	protected long replicas;
	protected long puts;
	protected long takes;
	protected long gets;
	protected long expires;
	protected long evicts;
	protected long locks;
	protected long unlocks;
	protected long invokes;
	protected long queries;
	protected long misses;
	protected long pendingPersists;
	
	public SpaceMemberStatistics(String memberId){
		this.id = memberId;
		name = distributionRole = "";
		originals = 0;
		replicas = 0;
		puts = 0;
		takes = 0;
		gets = 0;
		expires = 0;
		evicts = 0;
		locks = 0;
		unlocks = 0;
		invokes = 0;
		queries = 0;
		misses = 0;
		pendingPersists = 0;
	}
	
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getDistributionRole(){
		return this.distributionRole;
	}
	public long getOriginals(){
		return this.originals;
	}
	public long getReplicas(){
		return this.replicas;
	}
	public long getPuts(){
		return this.puts;
	}
	public long getTakes(){
		return this.takes;
	}
	public long getGets(){
		return this.gets;
	}
	public long getExpires(){
		return this.expires;
	}
	public long getEvicts(){
		return this.evicts;
	}
	public long getLocks(){
		return this.locks;
	}
	public long getUnlocks(){
		return this.unlocks;
	}
	public long getInvokes(){
		return this.invokes;
	}
	public long getQueries(){
		return this.queries;
	}
	public long getMisses(){
		return this.misses;
	}
	public long getPendingPersists(){
		return this.pendingPersists;
	}
	
	public SpaceMemberStatistics setName(String name){
		this.name = name;
		return this;
	}
	public SpaceMemberStatistics setDistributionRole(String distributionRole){
		this.distributionRole = distributionRole;
		return this;
	}
	public SpaceMemberStatistics setOriginals(long originals){
		this.originals = originals;
		return this;
	}
	public SpaceMemberStatistics setReplicas(long replicas){
		this.replicas = replicas;
		return this;
	}
	public SpaceMemberStatistics setPuts(long puts){
		this.puts = puts;
		return this;
	}
	public SpaceMemberStatistics setTakes(long takes){
		this.takes = takes;
		return this;
	}
	public SpaceMemberStatistics setGets(long gets){
		this.gets = gets;
		return this;
	}
	public SpaceMemberStatistics setExpires(long expires){
		this.expires = expires;
		return this;
	}
	public SpaceMemberStatistics setEvicts(long evicts){
		this.evicts = evicts;
		return this;
	}
	public SpaceMemberStatistics setLocks(long locks){
		this.locks = locks;
		return this;
	}
	public SpaceMemberStatistics setUnlocks(long unlocks){
		this.unlocks = unlocks;
		return this;
	}
	public SpaceMemberStatistics setInvokes(long invokes){
		this.invokes = invokes;
		return this;
	}
	public SpaceMemberStatistics setQueries(long queries){
		this.queries = queries;
		return this;
	}
	public SpaceMemberStatistics setMisses(long misses){
		this.misses = misses;
		return this;
	}
	public SpaceMemberStatistics setPendingPersists(long pendingPersists){
		this.pendingPersists = pendingPersists;
		return this;
	}
	
}
