package com.tibco.cep.runtime.management.impl.cluster.data.metaspace;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.runtime.management.impl.cluster.data.member.MemberStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceStatistics;

/**
 * Maintains statistics data pertaining to an ActiveSpaces Metaspace.
 * @author mwiley
 */
public class MetaspaceStatistics{
	
	protected String name;
	protected String discoverUrl;
	protected String listenUrl;
	Map<String, SpaceStatistics> spaces;
	Map<String, MemberStatistics> members;
	
	public MetaspaceStatistics(String name, String discoverUrl, String listenUrl){
		this.name = name;
		this.discoverUrl = discoverUrl;
		this.listenUrl = listenUrl;
		spaces = new HashMap<String, SpaceStatistics>();
		members = new HashMap<String, MemberStatistics>();
	}
	
	public String getName(){
		return name;
	}
	
	public String getDiscoverUrl(){
		return discoverUrl;
	}
	
	public String getListenUrl(){
		return listenUrl;
	}
	
	public Map<String, SpaceStatistics> getSpaces(){
		return spaces;
	}
	
	public Map<String, MemberStatistics> getMembers(){
		return members;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDiscoverUrl(String discoverUrl){
		this.discoverUrl = discoverUrl;
	}
	
	public void setListenUrl(String listenUrl){
		this.listenUrl = listenUrl;
	}
	
	public void setSpaces(Map<String, SpaceStatistics> spaces){
		this.spaces = spaces;
	}
	
	public void setMembers(Map<String, MemberStatistics> members){
		this.members = members;
	}
	
	public void addSpace(String spaceName, SpaceStatistics spaceStatistics){
		spaces.put(spaceName, spaceStatistics);
	}
	
	public void addMember(String memberId, MemberStatistics memberStatistics){
		members.put(memberId, memberStatistics);
	}
	
	public void removeSpace(String spaceName){
		spaces.remove(spaceName);
	}
	
	public void removeMember(String memberId){
		members.remove(memberId);
	}
	
}
