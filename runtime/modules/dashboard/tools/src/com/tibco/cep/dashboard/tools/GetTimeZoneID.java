package com.tibco.cep.dashboard.tools;

import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTimeZoneID implements Launchable {

	@Override
	public String getArgmentUsage() {
		return "gettimezoneid [timezonestring]";
	}
	
	@Override
	public void launch(String[] args) throws IllegalArgumentException, Exception {
		String [] ids = TimeZone.getAvailableIDs();
		
		if (args.length < 1) {
			for(String id:ids){				
				System.err.println(id);				
			}
			System.exit(0);
		}
		String userInput = args[0];
		
		Pattern pattern = Pattern.compile(".*"+userInput+".*", Pattern.CASE_INSENSITIVE);
		int matches = 0;
		for(String id:ids){
			Matcher matcher = pattern.matcher(id);
			if(matcher.matches()){
				System.err.println(id);
				++matches;
			}
		}
		if(matches < 1) {
			System.err.println("No matching timezone id found for '"+userInput+"'. Remove the argument to get all timezone ids");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GetTimeZoneID getTimeZone = new GetTimeZoneID();
		try {
			getTimeZone.launch(args);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
