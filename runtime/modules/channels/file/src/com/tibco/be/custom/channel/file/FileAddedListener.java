package com.tibco.be.custom.channel.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import com.tibco.be.custom.channel.BaseEventSerializer;
import com.tibco.be.custom.channel.Event;
import com.tibco.be.custom.channel.EventProcessor;

public class FileAddedListener implements Runnable {

	String filePath = "";
	File file = null;
	long pollInterval = 10000;
	boolean isRunning = false;
	long lastReadTime = 0;
	Object context;
	EventProcessor eventCallback = null;
	BaseEventSerializer serializer;
	WatchService watcher;

	public FileAddedListener(String filePath, long pollInterval, EventProcessor eventCallback, Object context,
			BaseEventSerializer serializer) {

		this.filePath = filePath;
		file = new File(filePath);
		this.pollInterval = pollInterval;
		this.eventCallback = eventCallback;
		this.context = context;
		this.serializer = serializer;
	}

	public void run() {
		// define a folder root
		Path watchDir = Paths.get(filePath);
		try {
			watcher = watchDir.getFileSystem().newWatchService();

			watchDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (isRunning) {
			try {

				WatchKey watchKey = watcher.poll();
				if (watchKey != null) {
					List<WatchEvent<?>> events = watchKey.pollEvents();
					for (WatchEvent event : events) {
						if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
							Event userEvent = null;
							try {
								userEvent = serializer.deserializeUserEvent(event.context(), null);
							} catch (Exception e) {
								System.out.println("Exception occurred while deserializing" + e);
							}
							eventCallback.processEvent(userEvent);
						}
					}
					boolean valid = watchKey.reset();
					if (!valid) {
						break;
					}
				}
				Thread.sleep(pollInterval);
			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}

	public void stop() {
		if (watcher != null) {
			try {
				watcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		isRunning = false;
	}

	public void start() {
		isRunning = true;
		Thread t = new Thread(this, "file-added-listener");
		t.start();

	}

}
