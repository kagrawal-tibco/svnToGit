package com.tibco.cep.dashboard.tools;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.tools.streamer.EventStreamer;

public class Launcher {
	
	//Modified by Anand to fix BE-10946 on 01/28/2011
	private static final String HELP_ARG = "-help";
	
	private static List<String> HIDDEN_OPTIONS = Arrays.asList("streamer");
	
	private static Map<String,Launchable> launchables;
	
	static {
		launchables = new LinkedHashMap<String, Launchable>();
		launchables.put("basicread", new BasicReadTest());
		launchables.put("completeread", new CompleteReadTest());
		launchables.put("subscription", new SubscriptionTest());
		launchables.put("gettimezoneid", new GetTimeZoneID());
		
		try {
			launchables.put("streamer", new EventStreamer());
		} catch (Throwable t) {
			//Modified by Anand to fix BE-10946 on 2/8/2011
			//do not add streamer option if it fails to instantiate 
		}
	}
	
	public Launcher(String[] args){
		if (args.length < 1) {
			printUsage("No arguments specified");
			return;
		}
		//Modified by Anand to fix BE-10946 on 01/28/2011
		if (args[0].equals(HELP_ARG) == true) {
			printUsage(null);
			return;
		}
		if (launchables.containsKey(args[0]) == false){
			printUsage("Unknown launch option");
			return;
		}
		Launchable launchable = launchables.get(args[0]);
		String[] launchArgs = new String[args.length-1];
		System.arraycopy(args, 1, launchArgs, 0, launchArgs.length);
		try {
			launchable.launch(launchArgs);
		} catch (IllegalArgumentException e) {
			printUsage(e.getMessage());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void printUsage(String msg){
		if (msg != null) {
			System.err.println(msg);
		}
		System.err.println("views-tools launchoption launchoptionarguments");
		System.err.println("\tlaunchoptions are ");
		for (Map.Entry<String, Launchable> entry : launchables.entrySet()) {
			if (HIDDEN_OPTIONS.contains(entry.getKey()) == false) {
				System.err.println("\t\t"+entry.getKey()+" "+entry.getValue().getArgmentUsage());
			}
		}		
	}
	
	public static void main(String[] args) {
		/*Launcher launcher = */new Launcher(args);
	}

}
