package com.tibco.cep.driver.as.internal;

import com.tibco.as.space.ASException;
import com.tibco.as.space.TakeOptions;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.AbstractEventContext;

public class ASMessageContext extends AbstractEventContext {
    private Destination dest;
    private Tuple msg;

    /**
     *
     * @param dest
     * @param msg
     */
    public ASMessageContext(Destination dest, Tuple msg) {
        this.dest = dest;
        this.msg = msg;
    }

	@Override
	public boolean reply(SimpleEvent paramSimpleEvent) {
		return false;
	}

	@Override
	public Destination getDestination()	{
		return dest;
	}

	@Override
	public Object getMessage() {
		return msg;
	}

	@Override
	public String replyImmediate(SimpleEvent replyEvent) {
		return null;
	}

    @Override
    public void acknowledge() {
        ASDestination asdestination = (ASDestination)dest;
        if (asdestination.getBrowserType() == BrowserType.LOCK) {
        	try {
                asdestination.getSpace().take(msg, TakeOptions.create().setUnlock(true));
        		asdestination.getLogger().log(Level.TRACE, "Acknowledged: %s", msg);
        	} catch (ASException e) {
        		asdestination.getLogger().log(Level.ERROR, e, "Failed to acknowledge, will attempt to unlock: %s", msg);
                try {
					asdestination.getSpace().unlock(msg);
	        		asdestination.getLogger().log(Level.TRACE, "Unlocked: %s", msg);
				} catch (ASException e1) {
	        		asdestination.getLogger().log(Level.ERROR, e1, "Failed to unlock: %s", msg);
				}
            }
        }
    }

    @Override
    public void rollBack() {
        ASDestination asdestination = (ASDestination)dest;
        if (asdestination.getBrowserType() == BrowserType.LOCK) {
	        try {
				asdestination.getSpace().unlock(msg);
        		asdestination.getLogger().log(Level.TRACE, "Unlocked: %s", msg);
			} catch (ASException e) {
	    		asdestination.getLogger().log(Level.ERROR, e, "Failed to unlock: %s", msg);
			}
        }
    }
}