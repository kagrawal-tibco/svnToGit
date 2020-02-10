package com.tibco.be.custom.channel.file;

import java.util.Iterator;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.be.custom.channel.framework.Destination;

public class FileChannel extends BaseChannel {

	@Override
	public void init() {
	}

	@Override
	public void connect() throws Exception {

	}

	@Override
	public void close() throws Exception {
	}

	@Override
	public void start() throws Exception {
		final Iterator i = getDestinations().values().iterator();
		while (i.hasNext()) {
			((Destination) i.next()).start();
		}
	}
}
