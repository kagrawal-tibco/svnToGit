package com.tibco.rta.service.metric;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

public class MessageContextWrapper {
	
    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

	
	FactMessageContext context;
	AtomicInteger counter = new AtomicInteger(0);
	AtomicLong totalTimeForFactTrip;
	AtomicLong totalFacts;
	int clientBatch;

	public MessageContextWrapper(FactMessageContext context, int clientBatch, AtomicLong totalTimeForFactTrip, AtomicLong totalFacts) {
		this.context = context;
		this.clientBatch = clientBatch;
		this.totalTimeForFactTrip = totalTimeForFactTrip;
		this.totalFacts = totalFacts;
	}
	public void incrementCount() {
		counter.incrementAndGet();
	}
	public void tryToAck() {
		if (counter.decrementAndGet() == 0) {
			FactMessageContext fmc = context;
			fmc.setBatchSize(1);
			try {
				fmc.acknowledge();
				long createdTime = context.getCreatedTime();
				totalTimeForFactTrip.addAndGet(System.currentTimeMillis() - createdTime);
                totalFacts.addAndGet(clientBatch);
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, "Error while acknowlege message", e);
			}
		}
	}
}