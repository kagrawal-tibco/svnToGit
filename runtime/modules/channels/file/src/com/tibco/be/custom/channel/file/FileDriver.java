package com.tibco.be.custom.channel.file;

import com.tibco.be.custom.channel.BaseChannel;
import com.tibco.be.custom.channel.BaseDestination;
import com.tibco.be.custom.channel.BaseDriver;

public class FileDriver extends BaseDriver {

	@Override
	public BaseChannel getChannel() {
		return new FileChannel();
	}

	@Override
	public BaseDestination getDestination() {
		return new FileDestination();
	}

}
