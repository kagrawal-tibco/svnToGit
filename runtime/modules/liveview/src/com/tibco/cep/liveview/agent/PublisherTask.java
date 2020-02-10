/**
 * 
 */
package com.tibco.cep.liveview.agent;

import com.tibco.as.space.event.SpaceEvent.EventType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.liveview.as.TupleContent;

/**
 * @author vpatil
 *
 */
public class PublisherTask implements Runnable {	
	private TupleContent tupleContent;
	private LiveViewAgent lvAgent;
	private Logger logger;
	
	public PublisherTask() {
	}
	
	public PublisherTask(TupleContent tupleContent) {
		this.tupleContent = tupleContent;
		this.logger = LogManagerFactory.getLogManager().getLogger(PublisherTask.class);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			boolean isDelete = true;
			if (tupleContent.getEventType() == EventType.PUT) {
				isDelete = false;
			} 
			lvAgent.getLVClient().publish(tupleContent.getSpaceName(), tupleContent.getTuple(), isDelete);
		} catch (Exception exception) {
			String exceptionMessage = String.format("Error publishing event[%s] from Space[%s].", tupleContent.getEventType().name(), tupleContent.getSpaceName());
			logger.log(Level.ERROR, exceptionMessage);
			throw new RuntimeException(exceptionMessage, exception);
		}
	}
	
	public TupleContent getTupleContent() {
		return this.tupleContent;
	}
	
	public void setLiveViewAgent(LiveViewAgent lvAgent) {
		this.lvAgent = lvAgent;
	}
}
