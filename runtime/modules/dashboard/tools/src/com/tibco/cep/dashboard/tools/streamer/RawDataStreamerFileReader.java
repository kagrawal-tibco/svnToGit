package com.tibco.cep.dashboard.tools.streamer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class RawDataStreamerFileReader implements StreamerFileReader {

	protected File file;
	
	protected BufferedReader reader;
	
	RawDataStreamerFileReader(File file) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(file));
	}
	
	public EventInfo getNextEventInfo() throws IOException{
		EventInfo eventInfo = null;
		String data = reader.readLine();
		while (data != null && eventInfo == null) {
			if (data.startsWith("#") == false) {
				try {
					eventInfo = new EventInfo(data);
				} catch (Exception e) {
					eventInfo = null;
					data = reader.readLine();
				}
			}
			else {
				data = reader.readLine();
			}
		}
		return eventInfo;		
	}
}
