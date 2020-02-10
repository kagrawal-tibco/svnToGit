/**
 * 
 */
package com.tibco.cep.liveview.as;

import com.tibco.as.space.event.EvictEvent;
import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.event.SpaceEvent;
import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.listener.EvictListener;
import com.tibco.as.space.listener.ExpireListener;
import com.tibco.as.space.listener.PutListener;
import com.tibco.as.space.listener.TakeListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.liveview.agent.LiveViewAgent;

/**
 * @author vpatil
 */
public class LVSpaceListener implements PutListener, TakeListener, EvictListener, ExpireListener {
	
	private LiveViewAgent lvAgent;
	
	public static LVSpaceListener lvSpaceListener;
	
	private Logger logger;
	
	public static synchronized LVSpaceListener getInstance(LiveViewAgent lvAgent) {
		if (lvSpaceListener == null) {
			lvSpaceListener = new LVSpaceListener(lvAgent);
		}
		return lvSpaceListener;
	}
	
	public LVSpaceListener(LiveViewAgent lvAgent) {
		this.lvAgent = lvAgent;
		this.logger = LogManagerFactory.getLogManager().getLogger(LVSpaceListener.class);
	}
	
	@Override
	public void onPut(PutEvent putEvent) {
		onMessage(putEvent);
	}
	
	@Override
	public void onTake(TakeEvent takeEvent) {
		onMessage(takeEvent);
	}
	
	@Override
	public void onEvict(EvictEvent evictEvent) {
		onMessage(evictEvent);
	}
	
	@Override
	public void onExpire(ExpireEvent expireEvent) {
		onMessage(expireEvent);
	}
	
	private void onMessage(SpaceEvent spaceEvent) {
		logger.log(Level.DEBUG, "Received event %s on space %s", spaceEvent.getType().name(), spaceEvent.getSpaceName());
		lvAgent.getLVClient().publish(spaceEvent.getSpaceName(), spaceEvent.getTuple(), (spaceEvent.getType() == SpaceEvent.EventType.TAKE));
	}
}
