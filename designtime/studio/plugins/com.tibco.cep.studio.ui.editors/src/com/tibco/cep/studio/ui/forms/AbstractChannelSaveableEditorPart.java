package com.tibco.cep.studio.ui.forms;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.ChannelFormEditorInput;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractChannelSaveableEditorPart extends AbstractSaveableEntityEditorPart{

    protected Channel channel;
    protected IFile file = null;
    protected ChannelFormEditorInput channelFormEditorInput;
    
    public Channel getChannel() {
		return (Channel)getEntity();
	}
   
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave();
	}
    /**
	 * This is how the framework determines which interfaces we implement.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(Channel.class))
			return getChannel();
		return super.getAdapter(key);
	}
	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
}