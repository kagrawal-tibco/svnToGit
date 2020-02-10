package com.tibco.rta.service.metric;

import java.util.concurrent.Callable;

import com.tibco.rta.common.service.RtaTransaction;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.Transaction;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.om.ObjectManager;

public class AssetBasedMetricComputeJob implements Callable<Transaction> {
	
    protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

	protected AssetBasedAggregator job;

	protected MetricService metricService;
	
	protected ObjectManager omService;

	protected boolean updateFactMetric;
	

	public AssetBasedMetricComputeJob(MetricService metricService, AssetBasedAggregator job, boolean updateFactMetric) throws Exception {
		this.metricService = metricService;
		this.job = job;
		this.updateFactMetric = updateFactMetric;
		this.omService = ServiceProviderManager.getInstance().getObjectManager();
		
	}

	@Override
	public Transaction call() throws Exception {
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Started %d", System.currentTimeMillis());
		}

		Transaction txn = null;
		boolean isSuccess = false;
		Exception ex = null;
		int i = 0;
		
		int transactionRetryCount = metricService.getTxnRetryCount();

		try {
			for (i = 0; i < transactionRetryCount || transactionRetryCount < 0; i++) {
				txn = RtaTransaction.get();
				try {
					txn.beginTransaction();
					job.computeMetric();
					if (updateFactMetric) {
						omService.save(job.getFact(), job.getHierarchy());
					}
					txn.commit();
					isSuccess = true;
					break;

				} catch (Exception e) {
					LOGGER.log(Level.ERROR,
							"Database exception while processing transaction. Will retry in 10 seconds.. Retry count: %d", e, i + 1);
					try {
						txn.rollback();
					} catch (Exception e1) {
					}
					txn.clear();
					ex = e;
					Thread.sleep(10000);

				}
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "Ended %d",
							System.currentTimeMillis());
				}
			}

			if (isSuccess) {
				metricService.publishTxn(txn);
			} else if (ex != null) {
				LOGGER.log(
						Level.ERROR,
						"Error while performing transaction %d times. Giving up..",
						ex, i + 1);
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR,
					"Error while performing transaction %d times. Giving up..",
					e, i + 1);
		} finally {
			if (txn != null) {
				txn.remove();
			}
		}
		// txn is cleared, so returning it is a no-op
		return txn;
	}
}
