package com.tibco.be.bemm.test;

import java.util.Iterator;
import java.util.Map;

import com.tibco.as.space.ASException;
import com.tibco.cep.runtime.management.impl.cluster.ASStatCollector;
import com.tibco.cep.runtime.management.impl.cluster.data.member.MemberStatistics;

public class BasicTest{
	
	public static void main(String[] args){
		try{
			ASStatCollector asCollector = new ASStatCollector();
			asCollector.connect("AcmeSystems", "tibpgm://6666", "");
			Map<String, MemberStatistics> memberStats = asCollector.getAllMembersStats();
			Iterator<String> keys = memberStats.keySet().iterator();
			while(keys.hasNext()){
				System.out.println(JsonObjectFactory.build(memberStats.get(keys.next())).toString());
			}
		}
		catch(ASException ex){
			System.err.println(ex.getMessage());
		}
	}
	
}
