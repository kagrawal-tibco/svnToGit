package com.tibco.be.bemm.test;

import java.util.Iterator;
import java.util.Map;

import org.apache.jena.atlas.json.JsonObject;

import com.tibco.cep.runtime.management.impl.cluster.data.metaspace.MetaspaceStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.member.MemberStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.member.MemberSpaceStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceMemberStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceField;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceIndex;

public class JsonObjectFactory{
	
	public static JsonObject build(Object o){
		JsonObject jObj = new JsonObject();
		
		if(o instanceof MetaspaceStatistics){
			MetaspaceStatistics stats = (MetaspaceStatistics) o;
			jObj.put("name", stats.getName());
			jObj.put("discoverUrl", stats.getDiscoverUrl());
			jObj.put("listenUrl", stats.getListenUrl());
			
			JsonObject spacesObj = new JsonObject();
			for(Map.Entry<String, SpaceStatistics> spaceEntry : stats.getSpaces().entrySet()){
				spacesObj.put(spaceEntry.getKey(), build(spaceEntry.getValue()));
			}
			jObj.put("spaces", spacesObj);
			
			JsonObject membersObj = new JsonObject();
			for(Map.Entry<String, MemberStatistics> memberEntry : stats.getMembers().entrySet()){
				membersObj.put(memberEntry.getKey(), build(memberEntry.getValue()));
			}
			jObj.put("members", membersObj);
		}
		
		else if(o instanceof MemberStatistics){
			MemberStatistics stats = (MemberStatistics) o;
			jObj.put("id", stats.getId());
			jObj.put("name", stats.getName());
			jObj.put("totalOriginals", stats.getTotalOriginals());
			jObj.put("totalReplicas", stats.getTotalReplicas());
			jObj.put("totalPuts", stats.getTotalPuts());
			jObj.put("totalTakes", stats.getTotalTakes());
			jObj.put("totalGets", stats.getTotalGets());
			jObj.put("totalExpires", stats.getTotalExpires());

			JsonObject spaceStatsObj = new JsonObject();
			Iterator<String> spaceStatItr = stats.getJoinedSpaces().keySet().iterator();
			while(spaceStatItr.hasNext()){
				String spacename = spaceStatItr.next();
				spaceStatsObj.put(spacename, build(stats.getJoinedSpaces().get(spacename)));
			}
			jObj.put("joinedSpaces", spaceStatsObj);
		}
		
		else if(o instanceof MemberSpaceStatistics){
			MemberSpaceStatistics stats = (MemberSpaceStatistics) o;
			jObj.put("spaceName", stats.getSpaceName());
			jObj.put("distributionRole", stats.getDistributionRole());
			jObj.put("originals", stats.getOriginals());
			jObj.put("replicas", stats.getReplicas());
			jObj.put("puts", stats.getPuts());
			jObj.put("takes", stats.getTakes());
			jObj.put("gets", stats.getGets());
			jObj.put("expires", stats.getExpires());			
		}
		
		else if(o instanceof SpaceStatistics){
			SpaceStatistics stats = (SpaceStatistics) o;
			jObj.put("name", stats.getName());
			jObj.put("totalOriginals", stats.getTotalOriginals());
			jObj.put("totalReplicas", stats.getTotalReplicas()); 
			jObj.put("totalPuts", stats.getTotalPuts());
			jObj.put("totalTakes", stats.getTotalTakes());
			jObj.put("totalGets", stats.getTotalGets());
			jObj.put("totalExpires", stats.getTotalExpires());
			
			JsonObject defObj = new JsonObject();
			for(Map.Entry<String, String> defItem : stats.getDefinition().entrySet()){
				defObj.put(defItem.getKey(), defItem.getValue());
			}
			jObj.put("definition", defObj);
			
			JsonObject schemaObj = new JsonObject();
			for(Map.Entry<String, SpaceField> schemaItem : stats.getSchema().entrySet()){
				schemaObj.put(schemaItem.getKey(), build(schemaItem.getValue()));
			}
			jObj.put("schema", schemaObj);
			
			JsonObject membersObj = new JsonObject();
			for(Map.Entry<String, SpaceMemberStatistics> memberItem : stats.getMembers().entrySet()){
				membersObj.put(memberItem.getKey(), build(memberItem.getValue()));
			}
			jObj.put("members", membersObj);
			
			JsonObject indicesObj = new JsonObject();
			for(Map.Entry<String, SpaceIndex> indexItem : stats.getIndices().entrySet()){
				indicesObj.put(indexItem.getKey(), build(indexItem.getValue()));
			}
			jObj.put("indices", indicesObj);
		}
		
		else if(o instanceof SpaceMemberStatistics){
			SpaceMemberStatistics stats = (SpaceMemberStatistics) o;
			jObj.put("id", stats.getId());
			jObj.put("name", stats.getName());
			jObj.put("distributionRole", stats.getDistributionRole());
			jObj.put("originals", stats.getOriginals());
			jObj.put("replicas", stats.getReplicas());
			jObj.put("puts", stats.getPuts());
			jObj.put("takes", stats.getTakes());
			jObj.put("gets", stats.getGets());
			jObj.put("expires", stats.getExpires());
		}
		
		else if(o instanceof SpaceField){
			SpaceField stats = (SpaceField) o;
			jObj.put("name", stats.getName());
			jObj.put("type", stats.getTypeName());
			jObj.put("nullable", stats.isNullable());
			jObj.put("key", stats.isKey());
		}
		
		else if(o instanceof SpaceIndex){
			SpaceIndex stats = (SpaceIndex) o;
			jObj.put("name", stats.getName());
			jObj.put("type", stats.getTypeName());
			jObj.put("fields", stats.getFields());
		}
		
		return jObj;
	}

}
