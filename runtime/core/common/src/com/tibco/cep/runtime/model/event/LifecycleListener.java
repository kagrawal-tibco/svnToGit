package com.tibco.cep.runtime.model.event;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;

public interface LifecycleListener {
	
	public void eventSent(Event event, Destination dest);
	
	public void workScheduled(WorkEntry entry, String schedulerId, String workKey);

	public void workRemoved(WorkEntry work, String schedulerId, String workKey);

}
