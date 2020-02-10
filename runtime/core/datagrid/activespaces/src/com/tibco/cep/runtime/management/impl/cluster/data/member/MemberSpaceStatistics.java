package com.tibco.cep.runtime.management.impl.cluster.data.member;

import com.tibco.as.space.Member;
import com.tibco.as.space.Space;

/**
 * Maintains statistics data pertaining to an ActiveSpaces {@link Member}
 * belonging to a particular {@link Space}.
 * 
 * @author mwiley
 */
public class MemberSpaceStatistics{

	protected String spaceName;
	protected String distributionRole;
	protected long originals;
	protected long replicas;
	protected long puts;
	protected long takes;
	protected long gets;
	protected long expires;

	public MemberSpaceStatistics(String spaceName, String distributionRole, long originals, long replicas, long puts, long takes, long gets, long expires){
		this.spaceName = spaceName;
		this.distributionRole = distributionRole;
		this.originals = originals;
		this.replicas = replicas;
		this.puts = puts;
		this.takes = takes;
		this.gets = gets;
		this.expires = expires;
	}

	/**
	 * @return the spaceName
	 */
	public String getSpaceName(){
		return spaceName;
	}

	/**
	 * @return the distributionRole (a string value corresponding to {@link Member.DistributionRole}
	 */
	public String getDistributionRole(){
		return distributionRole;
	}

	/**
	 * @return the originals
	 */
	public long getOriginals(){
		return originals;
	}

	/**
	 * @return the replicas
	 */
	public long getReplicas(){
		return replicas;
	}

	/**
	 * @return the puts
	 */
	public long getPuts(){
		return puts;
	}

	/**
	 * @return the takes
	 */
	public long getTakes(){
		return takes;
	}

	/**
	 * @return the gets
	 */
	public long getGets(){
		return gets;
	}

	/**
	 * @return the expires
	 */
	public long getExpires(){
		return expires;
	}

	/**
	 * @param spaceName the spaceName to set
	 */
	public void setSpaceName(String spaceName){
		this.spaceName = spaceName;
	}

	/**
	 * @param distributionRole the distributionRole to set
	 */
	public void setDistributionRole(String distributionRole){
		this.distributionRole = distributionRole;
	}

	/**
	 * @param originals the originals to set
	 */
	public void setOriginals(long originals){
		this.originals = originals;
	}

	/**
	 * @param replicas the replicas to set
	 */
	public void setReplicas(long replicas){
		this.replicas = replicas;
	}

	/**
	 * @param puts the puts to set
	 */
	public void setPuts(long puts){
		this.puts = puts;
	}

	/**
	 * @param takes the takes to set
	 */
	public void setTakes(long takes){
		this.takes = takes;
	}

	/**
	 * @param gets the gets to set
	 */
	public void setGets(long gets){
		this.gets = gets;
	}

	/**
	 * @param expires the expires to set
	 */
	public void setExpires(long expires){
		this.expires = expires;
	}

}
