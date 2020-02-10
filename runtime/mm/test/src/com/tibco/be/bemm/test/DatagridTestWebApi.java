package com.tibco.be.bemm.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.tibco.as.space.ASException;
import com.tibco.cep.runtime.management.impl.cluster.ASStatCollector;
import com.tibco.cep.runtime.management.impl.cluster.data.member.MemberStatistics;
import com.tibco.cep.runtime.management.impl.cluster.data.space.SpaceStatistics;

public class DatagridTestWebApi{
	
	private static final String METASPACE_CONTEXT_PATH = "/datagrid/metaspace";
	private static final String SPACE_CONTEXT_PATH = "/datagrid/spaces";
	private static final String MEMBER_CONTEXT_PATH = "/datagrid/members";
	
	private static int serverPort = 8020;
	private static String metaspaceName = "";
	private static String discoveryUrl = "";
	private static String listenUrl = "";
	
	static int requests = 0;
	
	public static void main(String[] args) throws Exception{
		if(args.length < 2){
			System.err.println("Usage: java DatagridTestWebApi <serverPort> <metaspaceName> [<discoverUrl>, <listenUrl>]");
			return;
		}
		
		serverPort = Integer.parseInt(args[0]);
		metaspaceName = args[1];
		discoveryUrl = args.length > 2 ? args[2]:"";
		listenUrl = args.length > 3 ? args[3]:"";
		
//		ASConnectionManager.init(metaspaceName, discoveryUrl, listenUrl);
//		ASConnectionManager.stop();
		
		HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
		server.createContext(METASPACE_CONTEXT_PATH, new MetaspaceSessionManager());
		server.createContext(SPACE_CONTEXT_PATH, new SpaceSessionManager());
		server.createContext(MEMBER_CONTEXT_PATH, new MemberSessionManager());
		server.setExecutor(null);
		
		//Initialize the AS connection for this server and setup to close on shutdown
		ASConnectionManager.init(metaspaceName, discoveryUrl, listenUrl);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Server Shutdown...");
				ASConnectionManager.stop();
			}
		});
		
		System.out.println("Starting Server...");
		server.start();
		System.out.println("Server Started...");
	}
	
	static class MetaspaceSessionManager implements HttpHandler{
		@Override
		public void handle(HttpExchange exchange){
			String urlPath = exchange.getRequestURI().getPath();
			System.out.println("MetaspaceSessionManager - " + urlPath);
			String command = urlPath.replace(METASPACE_CONTEXT_PATH, "").replace("/", "");
			JsonObject responseJsonObj = null;
			if(command.equals("statFields")){
				responseJsonObj = buildMetaspaceStatNames();
			}
			else{
				responseJsonObj = buildMetaspaceData();
			}
			SessionHelper.sendResponse(responseJsonObj, exchange);
		}
		
		public static JsonObject buildMetaspaceStatNames(){
			JsonObject statNamesObj = new JsonObject();
			JsonArray statNamesArray = new JsonArray();
			for(String statName : ASConnectionManager.get().getStatFieldNames()){
				statNamesArray.add(statName);
			}
			statNamesObj.put("statNames", statNamesArray);
			return statNamesObj;
		}
		
		public static JsonObject buildMetaspaceData(){
			JsonObject metaspaceObj = new JsonObject();
			try{
				metaspaceObj = JsonObjectFactory.build(ASConnectionManager.get().getMetaspaceStats());
			}
			catch(ASException ex){
				System.err.println("MetaspaceSessionManager.buildMetaSpaceData - " + ex.getMessage());
			}
			return metaspaceObj;
		}
	}
	
	static class SpaceSessionManager implements HttpHandler{
		@Override
		public void handle(HttpExchange exchange){
			String urlPath = exchange.getRequestURI().getPath();
			System.out.println("SpaceSessionManager - " + urlPath);
			String spaceName = urlPath.replace(SPACE_CONTEXT_PATH, "").replace("/", "");
			JsonObject spaceData = spaceName.equals("") ? buildAllSpaceData():buildSpaceData(spaceName);
			SessionHelper.sendResponse(spaceData, exchange);
		}
		
		public static JsonObject buildSpaceData(String spaceName){
			JsonObject spaceStatsObj = null;
			try{
				spaceStatsObj = JsonObjectFactory.build(ASConnectionManager.get().getSpaceStats(spaceName));
			}
			catch(ASException ex){
				System.err.println(ex.getMessage());
			}
			return spaceStatsObj;
		}
		
		public static JsonObject buildAllSpaceData(){
			JsonObject spaceStatsObj = new JsonObject();
			try{
				Map<String, SpaceStatistics> spaceStatsMap = ASConnectionManager.get().getAllSpaceStats();
				for(Map.Entry<String, SpaceStatistics> entry : spaceStatsMap.entrySet()){
					spaceStatsObj.put(entry.getKey(), JsonObjectFactory.build(entry.getValue()));
				}
			}
			catch(ASException ex){
				System.err.println(ex.getMessage());
			}
			return spaceStatsObj;
		}
	}
	
	static class MemberSessionManager implements HttpHandler{
		@Override
		public void handle(HttpExchange exchange){
			String urlPath = exchange.getRequestURI().getPath();
			System.out.println("MemberSessionManager - " + urlPath);
			String memberId = urlPath.replace(MEMBER_CONTEXT_PATH, "").replace("/", "");
			JsonObject memberData = memberId.equals("") ? buildAllMemberData():buildMemberData(memberId);
			SessionHelper.sendResponse(memberData, exchange);
		}
		
		public static JsonObject buildMemberData(String memberId){
			JsonObject memberStatsObj = null;
			try{
				memberStatsObj = JsonObjectFactory.build(ASConnectionManager.get().getMemberStats(memberId));
			}
			catch(ASException ex){
				System.err.println(ex.getMessage());
			}
			return memberStatsObj;
		}
		
		public static JsonObject buildAllMemberData(){
			JsonObject memberStatsObj = new JsonObject();
			try{
				Map<String, MemberStatistics> allMemberStats = ASConnectionManager.get().getAllMembersStats();
				for(Map.Entry<String, MemberStatistics> entry : allMemberStats.entrySet()){
					memberStatsObj.put(entry.getKey(), JsonObjectFactory.build(entry.getValue()));
				}
			}
			catch(ASException ex){
				System.err.println(ex.getMessage());
			}
			return memberStatsObj;
		}
	}
	
}

class SessionHelper{
	public static void sendResponse(JsonObject data, HttpExchange exchange){
		try{
			String response = data.toString();
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
		catch(IOException ex){
			System.err.println("SessionHelper.sendResponse - " + ex.getMessage());
		}
	}
}

class ASConnectionManager{
	private static ASStatCollector asCollector;
	
	public static void init(String metaspaceName, String discoveryUrl, String listenUrl){
		asCollector = new ASStatCollector();
		asCollector.connect(metaspaceName, discoveryUrl, listenUrl);
	}
	
	public static ASStatCollector get(){
		if(asCollector == null){
			System.err.println("WARNING: ASStatCollector not initialized.");
		}
		return asCollector;
	}
	
	public static void stop(){
		try{
			asCollector.disconnect();
			asCollector = null;
		}
		catch(ASException ex){
			System.err.println(ex.getMessage());
		}
	}
}