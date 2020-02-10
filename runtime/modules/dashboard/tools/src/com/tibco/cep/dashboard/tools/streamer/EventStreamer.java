package com.tibco.cep.dashboard.tools.streamer;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.tools.Launchable;
import com.tibco.tibrv.TibrvException;
import com.tibco.tibrv.TibrvMsg;

public class EventStreamer implements Launchable {

	private static final long DEFAULT_PERIOD = 2 * DateTimeUtils.SECOND; //5 seconds

	protected TIBRvMessagePublisher publisher;

	protected File file;
	
	protected StreamerFileReader streamerFileReader;
	
	protected long period;
	
	private Timer timer;
	
	public EventStreamer(){
		
	}

	private void startStreaming() {
		if (period < 5000){
			try {
				EventInfo eventInfo = readNextEventInfo();
				while (eventInfo != null){
					try {
						TibrvMsg msgSent = publisher.sendMessage(eventInfo.subject, eventInfo.fieldnames, eventInfo.dataTypes, eventInfo.values);
						System.out.println("Published "+msgSent+" on "+eventInfo.subject);
					} catch (TibrvException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(period);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					eventInfo = readNextEventInfo();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			timer = new Timer("Streamer Timer",false);
			timer.schedule(new TimerTask(){

				@Override
				public void run() {
					try {
						EventInfo eventInfo = readNextEventInfo();
						if (eventInfo == null){
							cancel();
							timer.cancel();
							return;
						}
						try {
							TibrvMsg msgSent = publisher.sendMessage(eventInfo.subject, eventInfo.fieldnames, eventInfo.dataTypes, eventInfo.values);
							System.out.println("Published "+msgSent+" on "+eventInfo.subject);
						} catch (TibrvException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
						cancel();
						timer.cancel();
					}
				}
				
			}, DateTimeUtils.SECOND, period);
		}
	}
	
	private EventInfo readNextEventInfo() throws IOException {
		return streamerFileReader.getNextEventInfo();
	}
	
	@Override
	public String getArgmentUsage() {
		return "[-service service] [-network network] [-daemon daemon] [-period period] <filename>";
	}

	@Override
	public void launch(String[] args) throws IllegalArgumentException, Exception {
		Object[] parsedArgs = parseArgs(args);
		String service = parsedArgs[0] == null ? null : (String)parsedArgs[0];
		String network = parsedArgs[1] == null ? null : (String)parsedArgs[1];
		String daemon = parsedArgs[2] == null ? null : (String)parsedArgs[2];
		
		if (parsedArgs[3] instanceof String){
			throw new IllegalArgumentException((String) parsedArgs[3]);
		}
		
		if (parsedArgs[4] == null || parsedArgs[4] instanceof String){
			if (parsedArgs[4] != null) {
				throw new IllegalArgumentException((String) parsedArgs[4]);
			}
		}
		publisher = new TIBRvMessagePublisher(service,network,daemon);
		file = (File) parsedArgs[4];
		period = (Long)parsedArgs[3];
		if (file.getName().endsWith(".events") == true){
			streamerFileReader = new RawDataStreamerFileReader(file);
		}
		else if (file.getName().endsWith(".eventconfig") == true){
			streamerFileReader = new DynDataStreamerFileReader(file);
		}
		else {
			throw new IllegalArgumentException("Unknown input file type");
		}
		startStreaming();
	}	
	
	private static Object[] parseArgs(String[] args){
		Object[] parsedArgs = new Object[]{null,null,null,DEFAULT_PERIOD,"No file specified"};
		int i = 0;
		while (i < args.length) {
			if (args[i].equals("-service")) {
				parsedArgs[0] = args[i + 1];
				i += 2;
			} else if (args[i].equals("-network")) {
				parsedArgs[1] = args[i + 1];
				i += 2;
			} else if (args[i].equals("-daemon")) {
				parsedArgs[2] = args[i + 1];
				i += 2;
			} else if (args[i].equals("-period")) {
				try {
					parsedArgs[3] = Long.parseLong(args[i + 1]);
				} catch (NumberFormatException e) {
					parsedArgs[3] = "Non numeric period";
				}				
				i += 2;
			} else {
				parsedArgs[4] = args[i];
				if (StringUtil.isEmptyOrBlank(args[i]) == true) {
					parsedArgs[4] = "Incorrect filename";
				}
				File file = new File(args[i]);
				if (file.exists() == false) {
					parsedArgs[4] = "non existent file";
				}
				else if (file.isDirectory() == true) {
					parsedArgs[4] = "directory specified";
				}
				else {
					parsedArgs[4] = file;
				}
				i++;
			}
		}
		return parsedArgs;
	}
	
	public static void main(String[] args) {
		EventStreamer eventStreamer = new EventStreamer();
		try {
			eventStreamer.launch(args);
		} catch (IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			System.err.println("java " + EventStreamer.class.getName() + eventStreamer.getArgmentUsage());			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	
}
