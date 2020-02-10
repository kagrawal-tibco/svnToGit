package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StreamerRegistry {

	private static StreamerRegistry instance;

	public static final synchronized StreamerRegistry getInstance() {
		if (instance == null) {
			instance = new StreamerRegistry();
		}
		return instance;
	}

	private Map<String, Streamer> streamers;

	private StreamerRegistry() {
		streamers = new ConcurrentHashMap<String, Streamer>();
	}

	public void registerStreamer(String name,Streamer streamer){
		streamers.put(name, streamer);
	}

	public void unregisterStreamer(String name){
		streamers.remove(name);
	}

	public Streamer getRegisteredStreamer(String name){
		return streamers.get(name);
	}

	void shutdown(){
		for (Streamer streamer : streamers.values()) {
			streamer.close();
		}
		streamers.clear();
	}
}
