package com.tibco.cep.runtime.management.impl.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.as.space.ASCommon;
import com.tibco.as.space.ASException;
import com.tibco.as.space.FieldDef;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.Browser;
import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.impl.ASMember;
import com.tibco.cep.runtime.management.impl.cluster.data.member.MemberStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.metaspace.MetaspaceStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceStatistics;

public class ASStatCollector{
	
	protected Metaspace metaspace;
	protected String discoverUrl;
	protected String listenUrl;
	
	public ASStatCollector(){}
	
	/**
	 * Connects to an ActiveSpaces Metaspace using the provided parameters. The connection is kept alive so that subsequent
	 * statistic gathering calls will not each create and destroy an ActiveSpaces connection. Once statistic gathering is
	 * complete, disconnect should be called.
	 * @param metaspaceName The name of the Metaspace to connect to.
	 * @param metaspaceDiscoverUrl The discovery URL to use when connecting to the Metaspace.
	 * @param metaspaceListenerUrl The listener URL to use when connecting to the Metaspace.
	 * @return
	 */
	public boolean connect(String metaspaceName, String metaspaceDiscoverUrl, String metaspaceListenerUrl){
		try{
			if(metaspace != null){ disconnect(); }
			discoverUrl = metaspaceDiscoverUrl;
			listenUrl = metaspaceListenerUrl;
			metaspace = Metaspace.connect(metaspaceName, MemberDef.create().setDiscovery(metaspaceDiscoverUrl).setListen(metaspaceListenerUrl));
		}
		catch(ASException e){
			//TODO: logging?
			System.err.println("ASStatCollector.connect - Failed connecting to Metaspace " + metaspaceName + ": "  + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean useConnectedMetaspace(String metaspaceName){
		try{
			metaspace = ASCommon.getMetaspace(metaspaceName);
		}
		catch(Exception e){
			//TODO: logging?
			System.err.println("ASStatCollector.useConnectedMetaspace - Using connected Metaspace (" + metaspaceName + "): "  + e.getMessage());
			return false;
		}
		return true;
	}
	
	public void releaseMetaspace(){
		metaspace = null;
		discoverUrl = null;
		listenUrl = null;
	}
	
	/**
	 * Disconnects from the currently connected Metaspace.
	 * @throws ASException
	 */
	public void disconnect() throws ASException{
		if(metaspace != null){
			metaspace.closeAll();
		}
		releaseMetaspace();
	}
	
	public List<String> getStatFieldNames(){
		if(metaspace == null){
			System.err.println("ASStatCollector.getStatFieldNames - metaspace is not ready, can't get stat field names.");
			return null;
		}
		List<String> fieldNames = new ArrayList<String>();
		try{
			Iterator<FieldDef> fieldDefItr = metaspace.getSpace("$space_stats").getSpaceDef().getFieldDefs().iterator();
			while(fieldDefItr.hasNext()){
				fieldNames.add(fieldDefItr.next().getName());
			}
		}
		catch(ASException ex){
			System.err.println("ASStatCollector.getStatFieldNames - " + ex.getMessage());
		}
		return fieldNames;
	}
	
	public Collection<String> getSpaceNames(){
		if(metaspace == null){
			System.err.println("ASStatCollector.getSpaceNames - metaspace is not ready, can't get Space names.");
			return null;
		}
		try{
			return metaspace.getUserSpaceNames();
		}
		catch(ASException ex){
			System.err.println(ex.getMessage());
			return Collections.emptyList();
		}
	}
	
	/**
	 * Gathers statistics for the currently connected Metaspace
	 * @return {@link MetaspaceStatistics} for the currently connected metaspace.
	 * @throws ASException
	 */
	public MetaspaceStatistics getMetaspaceStats() throws ASException{
		if(metaspace == null){
			System.err.println("ASStatCollector.getMetaspaceStats - metaspace is not ready, can't get statistics.");
			return null;
		}
		//NOTE: we don't just call getAllSpaceStats and getAllMemberStats for the space and member statistics
		//as it will initiate and iterate through 2 separate Browsers
		MetaspaceStatistics metaspaceStats = new MetaspaceStatistics(metaspace.getName(), discoverUrl, listenUrl);
		Map<String, SpaceStatistics> allSpaceStats = new HashMap<String, SpaceStatistics>();
		Map<String, MemberStatistics> allMemberStats = new HashMap<String, MemberStatistics>();
		Browser statBrowser = metaspace.browse("$space_stats", BrowserType.GET, BrowserDef.create());
		Tuple tuple = null;
		while((tuple = statBrowser.next()) != null){
			String spaceName = tuple.getString("space_name");
			if(spaceName.charAt(0) == '$'){
				continue;
			}
			
			//create space stats from tuple and store in allSpaceStats map
			SpaceStatistics spaceStats = allSpaceStats.get(spaceName);
			if(spaceStats == null){
				spaceStats = new SpaceStatistics(spaceName);
				spaceStats.initForMetaspace(metaspace);
				allSpaceStats.put(spaceName, spaceStats);
			}
			spaceStats.addMemberStatFromTuple(metaspace, tuple);
			
			//create member stats from tuple and store in allMemberStats map
			String memberId = tuple.getString("member_id");
			ASMember tupleOwner = new ASMember(metaspace, memberId);
			if(allMemberStats.containsKey(memberId) == false){
				allMemberStats.put(memberId, MemberStatistics.build(tupleOwner, tuple));
			}
			else{
				MemberStatistics memberStats = allMemberStats.get(memberId);
				memberStats.addMemberSpaceFromTuple(tupleOwner, tuple);
			}
		}
		statBrowser.stop();
		metaspaceStats.setSpaces(allSpaceStats);
		metaspaceStats.setMembers(allMemberStats);
		return metaspaceStats;
	}
	
	/**
	 * Gathers statistics for the specified space in the currently connected Metaspace.
	 * @param spaceName Name of the space to fetch statistics for
	 * @return {@link SpaceStatistics} for the space identified by <code>spaceName</code>.
	 * @throws ASException
	 */
	public SpaceStatistics getSpaceStats(String spaceName) throws ASException{
		if(metaspace == null){
			System.err.println("ASStatCollector.getSpaceStats - metaspace is not ready, can't get statistics.");
			return null;
		}
		SpaceStatistics stats = new SpaceStatistics(spaceName);
		stats.initForMetaspace(metaspace);
		//scan tuples for those whose space_name == spaceName
		Browser statBrowser = metaspace.browse("$space_stats", BrowserType.GET, BrowserDef.create());
		Tuple tuple = null;
		while((tuple = statBrowser.next()) != null){
			//ignore system spaces and tuples not belonging to the named space
			if(tuple.getString("space_name").equals(spaceName) == false){
				continue;
			}
			stats.addMemberStatFromTuple(metaspace, tuple);
		}
		statBrowser.stop();
		return stats;
	}
	
	/**
	 * Gathers statistics for all spaces in the currently connected Metaspace.
	 * @return A Map of all {@link SpaceStatistics}, indexed by space name.
	 * @throws ASException
	 */
	public Map<String, SpaceStatistics> getAllSpaceStats() throws ASException{
		if(metaspace == null){
			System.err.println("ASStatCollector.getAllSpaceStats - metaspace is not ready, can't get statistics.");
			return null;
		}
		Map<String, SpaceStatistics> allSpaceStats = new HashMap<String, SpaceStatistics>();
		Browser statBrowser = metaspace.browse("$space_stats", BrowserType.GET, BrowserDef.create());
		Tuple tuple = null;
		while((tuple = statBrowser.next()) != null){
			String spaceName = tuple.getString("space_name");
			if(spaceName.charAt(0) == '$'){
				continue;
			}
			SpaceStatistics spaceStats = allSpaceStats.get(spaceName);
			if(spaceStats == null){
				spaceStats = new SpaceStatistics(spaceName);
				spaceStats.initForMetaspace(metaspace);
				allSpaceStats.put(spaceName, spaceStats);
			}
			spaceStats.addMemberStatFromTuple(metaspace, tuple);
		}
		statBrowser.stop();
		return allSpaceStats;
	}
	
	/**
	 * Gathers statistics for all members in the currently connected Metaspace whose ID matches the provided <code>memberId</code>.
	 * @param memberId The ID of the member to gather statistics for.
	 * @return {@link MemberStatistics} for the member identified by <code>memberId</code>
	 * @throws ASException
	 */
	public MemberStatistics getMemberStats(String memberId) throws ASException{
		if(metaspace == null){
			System.err.println("ASStatCollector.getSpaceStats - metaspace is not ready, can't get statistics.");
			return null;
		}
		MemberStatistics stats = null;
		//scan tuples for those whose member ID == memberId
		Browser statBrowser = metaspace.browse("$space_stats", BrowserType.GET, BrowserDef.create());
		ASMember tupleOwner = new ASMember(metaspace, memberId);
		Tuple tuple = null;
		while((tuple = statBrowser.next()) != null){
			String spaceName = tuple.getString("space_name");
			//ignore system spaces and tuples not owned by the given member
			if(spaceName.charAt(0) == '$' || memberId.equals(tuple.getString("member_id")) == false){ continue; }
			if(stats == null){
				stats = MemberStatistics.build(tupleOwner, tuple);
			}
			else{
				stats.addMemberSpaceFromTuple(tupleOwner, tuple);
			}
		}
		statBrowser.stop();
		return stats;
	}
	
	/**
	 * Gathers statistics for all members in the currently connected Metaspace.
	 * @return A Map of all {@link MemberStatistics}, indexed by member ID.
	 * @throws ASException
	 */
	public Map<String, MemberStatistics> getAllMembersStats() throws ASException{
		if(metaspace == null){
			System.err.println("ASStatCollector.getSpaceStats - metaspace is not ready, can't get statistics.");
			return null;
		}
		Map<String, MemberStatistics> allMemberStats = new HashMap<String, MemberStatistics>();
		//scan tuples for those whose member ID == memberId
		Browser statBrowser = metaspace.browse("$space_stats", BrowserType.GET, BrowserDef.create());
		Tuple tuple = null;
		while((tuple = statBrowser.next()) != null){
			String spaceName = tuple.getString("space_name");
			if(spaceName.charAt(0) == '$'){ continue; } //ignore system spaces
			String memberId = tuple.getString("member_id");
			ASMember tupleOwner = new ASMember(metaspace, memberId);
			if(allMemberStats.containsKey(memberId) == false){
				allMemberStats.put(memberId, MemberStatistics.build(tupleOwner, tuple));
			}
			else{
				MemberStatistics memberStats = allMemberStats.get(memberId);
				memberStats.addMemberSpaceFromTuple(tupleOwner, tuple);
			}
		}
		statBrowser.stop();
		return allMemberStats;
	}
	
}

