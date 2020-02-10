package com.tibco.cep.dashboard.tools.streamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.tibco.cep.dashboard.common.utils.StringUtil;

public class DynDataStreamerFileReader implements StreamerFileReader {
	
	protected File file;
	
	protected List<EventConfigInfo> eventConfigInfos;
	
	private Random random;
	
	DynDataStreamerFileReader(File file) throws FileNotFoundException, IOException {
		this.file = file;
		this.eventConfigInfos = new LinkedList<EventConfigInfo>();
		Properties props = new Properties();
		props.load(new FileInputStream(file));
		Enumeration<?> eventSubjects = props.propertyNames();
		while (eventSubjects.hasMoreElements()) {
			String subject = (String) eventSubjects.nextElement();
			String subjectConfig = props.getProperty(subject);
			if (StringUtil.isEmptyOrBlank(subjectConfig) == false){
				EventConfigInfo eventConfigInfo = new EventConfigInfo(subject,subjectConfig);
				eventConfigInfos.add(eventConfigInfo);
			}
		}
		random = new Random();
	}
	
	@Override
	public EventInfo getNextEventInfo() throws IOException {
		int idx = random.nextInt(eventConfigInfos.size());
		return eventConfigInfos.get(idx).createEventInfo();
	}

}
