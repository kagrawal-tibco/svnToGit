package com.tibco.rta.common.service.transport.jms;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Message;

import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.log.Level;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/4/13
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class JMSFactMessageContext extends JMSMessageContext implements FactMessageContext {

    private int factBatchSize;

    private long createdTime;
    
    private String id = "<undef>";

    /**
     * Number of times acknowledge was called. Should be eventually equal to batchsize.
     */
    private AtomicInteger ackTimes = new AtomicInteger(0);

    public JMSFactMessageContext(Message incomingMessage) {
        super(incomingMessage);
        createdTime = System.currentTimeMillis();
        try {
			id = incomingMessage.getJMSMessageID();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void acknowledge() {
        try {
            int currentValue = ackTimes.incrementAndGet();
            if (currentValue == factBatchSize) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Acknowledging message when batch size [%d]", currentValue);
                }
                //Acknowledge the batch
                super.acknowledge();
            }
        } catch (JMSException e) {
            LOGGER.log(Level.ERROR, "Error acknowledging message", e);
        }
    }

    @Override
    public void setBatchSize(int factBatchSize) {
        this.factBatchSize = factBatchSize;
    }

    public long getCreatedTime() {
        return createdTime;
    }

	@Override
	public String getId() {
		return id;
	}

}
