package com.tibco.rta.queues;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/11/12
 * Time: 3:17 PM
 * Basic batch size based buffering.
 */
public class BatchSizeBufferCriterion extends BatchSizeOnlyBufferCriterion {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());
    
    /**
     * Diff in milliseconds for last entry on queue for residual entries to be cleaned up.
     */
    private long expiryDiff;

    public BatchSizeBufferCriterion(int batchSize, long expiryDiff) {
        this(batchSize, 1, expiryDiff);
    }

    public BatchSizeBufferCriterion(int batchSize, int divisionFactor, long expiryDiff) {
        super(batchSize, divisionFactor);
        this.expiryDiff = expiryDiff;
        if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Fact batch expiry in milliseconds : [%d]", this.expiryDiff);
        }
    }

    @Override
    public <A extends AbstractBatchAwareQueue> boolean isMet(A queue) {
        return isExpired(queue) || super.isMet(queue);
    }

    private <A extends AbstractBatchAwareQueue> boolean isExpired(A queue) {
        BatchJob firstJob = queue.peek();
        if (firstJob == null) {
            return false;
        }
        boolean expiryCriterion = (queue.size() == 0) || (System.currentTimeMillis() - firstJob.getCreationTime()) >= expiryDiff;
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Expiry criterion : [%s]", expiryCriterion);
        }
        return expiryCriterion;
    }
}
