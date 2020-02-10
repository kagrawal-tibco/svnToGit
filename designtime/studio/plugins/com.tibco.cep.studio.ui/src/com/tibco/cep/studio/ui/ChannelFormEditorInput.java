package com.tibco.cep.studio.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;

public class ChannelFormEditorInput extends FileEditorInput {

	@SuppressWarnings("unused")
	private IFile file;
	
	private Channel channel;
	private Destination destination;
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public ChannelFormEditorInput(IFile file, Channel channel) {
		super(file);
		this.file = file;
		this.channel = channel;
	}

	public Destination getSelectedDestination() {
		return destination;
	}
	
	public void setSelectedDestination(Destination destination) {
		this.destination = destination;
	}
}
