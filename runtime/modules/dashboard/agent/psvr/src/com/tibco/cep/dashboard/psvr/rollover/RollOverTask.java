package com.tibco.cep.dashboard.psvr.rollover;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.psvr.data.DataCacheListener;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandlerCache;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandler;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandlerFactory;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.dashboard.timer.TimerProvider;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

class RollOverTask extends ServiceDependent implements DataCacheListener {

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("HH:mm:ss a z");

	private TimeZone timeZone;

	private String rolloverTime;

	private int hourOfDay;

	private int minute;

	private long offset;

	private long period;

	private String periodAsStr;

	private RolloverTimerTask timerTask;

	private boolean startedInDST;

	private Set<String> dataSourceHandlerUniqueNames;

	private boolean purge;

	RollOverTask(TimeZone timeZone, String rolloverTime, long offset, ROLLOVER_POLICY policy) {
		super("rollovertask","Rollover Task");
		this.timeZone = timeZone;
		this.rolloverTime = rolloverTime;
		this.offset = offset;
		this.period = DateTimeUtils.DAY;
		this.periodAsStr = DateTimeUtils.getTimeAsStr(period);
		this.purge = policy.equals(ROLLOVER_POLICY.PURGE);
		splitTime();
		this.dataSourceHandlerUniqueNames = new HashSet<String>();
	}

	private void splitTime() {
		String[] timeSplits = rolloverTime.split(":");
		hourOfDay = Integer.parseInt(timeSplits[0]);
		minute = (timeSplits.length > 1) ? Integer.parseInt(timeSplits[1]) : 0;
	}

	@Override
	protected void doStart() throws ManagementException {
		if (this.logger.isEnabledFor(Level.DEBUG)) {
			this.logger.log(Level.DEBUG, "Configured rollover to occur every day at " + rolloverTime + " in " + timeZone.getDisplayName());
		}
		DataSourceHandlerCache.getInstance().addDataCacheListener(this);
		doResume();
	}

	@Override
	protected void doPause() throws ManagementException {
		timerTask.cancel();
	}

	@Override
	protected void doResume() throws ManagementException {
		Date scheduledTime = getNextScheduleTime();
		schedule(scheduledTime);
	}

	private void schedule(Date scheduledTime) {
		Date logDate = new Date(scheduledTime.getTime() + offset);
		logger.log(Level.INFO, "Scheduling to rollover every " + periodAsStr + " at " + DATE_FORMATTER.format(logDate));
		timerTask = new RolloverTimerTask(this.logger, this.exceptionHandler);
		TimerProvider.getInstance().schedule(timerTask, scheduledTime.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		startedInDST = timeZone.inDaylightTime(scheduledTime);
		logger.log(Level.INFO, "Scheduled to rollover every " + periodAsStr + " at " + DATE_FORMATTER.format(logDate));
	}

	private Date getNextScheduleTime() {
		Calendar todayScheduledCalendar = getTodayScheduledCalendar();
		Date scheduledTime = new Date(todayScheduledCalendar.getTime().getTime() - offset);
		if (scheduledTime.before(new Date())) {
			// today's rollover time is passed already. Get it for tomorrow.
			todayScheduledCalendar.add(Calendar.DATE, 1);
			scheduledTime = new Date(todayScheduledCalendar.getTime().getTime() - offset);
		}
		return scheduledTime;
	}

	private Calendar getTodayScheduledCalendar() {
		Calendar _cal = Calendar.getInstance(timeZone);
		_cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		_cal.set(Calendar.MINUTE, minute);
		_cal.set(Calendar.SECOND, 0);
		_cal.set(Calendar.MILLISECOND, 0);
		return _cal;
	}

	private void rollover() {
		logger.log(Level.INFO, "Initiating rollover...");
		List<String> localUniqueNames = new LinkedList<String>();
		Set<DataSourceUpdateHandler> unSubscribeFailedDataSrcUpdateHndlrs = new HashSet<DataSourceUpdateHandler>();
		try {
			synchronized (dataSourceHandlerUniqueNames) {
				localUniqueNames.addAll(dataSourceHandlerUniqueNames);
			}
			int count = localUniqueNames.size();
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Unregistering " + count + " datasource handlers from updates...");
			}
			// log when the unregistration process has started
			long startTime = System.currentTimeMillis();
			for (String dataSrcUniqueName : localUniqueNames) {
				DataSourceUpdateHandler[] dataSourceUpdateHandlers = DataSourceUpdateHandlerFactory.getInstance().getPreCreatedHandlers(dataSrcUniqueName);
				for (DataSourceUpdateHandler dataSourceUpdateHandler : dataSourceUpdateHandlers) {
					// first stop the update handler
					dataSourceUpdateHandler.stop();
					try {
						// trigger an unsubscribe
						dataSourceUpdateHandler.unsubscribe();
					} catch (StreamingException e) {
						String message = messageGenerator.getMessage("rollover.unsubscribe.failure", new MessageGeneratorArgs(e, dataSrcUniqueName));
						exceptionHandler.handleException(message, e, Level.WARN);
						unSubscribeFailedDataSrcUpdateHndlrs.add(dataSourceUpdateHandler);
					}
				}
			}
			long endTime = System.currentTimeMillis();
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Unregistering " + count + " datasource handlers from updates...");
			}
			long timeTaken = endTime - startTime;
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Unregisteration process took " + timeTaken + " millisecond(s)...");
			}
			if (timeTaken < offset) {
				// we spend less time then offset in unregistration , we need to
				// wait
				long timeToSleep = offset - timeTaken + 2 * DateTimeUtils.SECOND; //we add couple of seconds to
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Will wait for " + timeToSleep + " millisecond(s)...");
				}
				try {
					Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					logger.log(Level.WARN, "Could to wait " + timeTaken + " millisecond(s)...");
				}
			}
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Rolling over " + count + " datasource handlers...");
			}
			startTime = System.currentTimeMillis();
			for (String dataSrcUniqueName : localUniqueNames) {
				DataSourceUpdateHandler[] dataSourceUpdateHandlers = DataSourceUpdateHandlerFactory.getInstance().getPreCreatedHandlers(dataSrcUniqueName);
				for (DataSourceUpdateHandler dataSourceUpdateHandler : dataSourceUpdateHandlers) {
					// first start the update handler or the resetData will not be processed
					dataSourceUpdateHandler.start();
					if (unSubscribeFailedDataSrcUpdateHndlrs.contains(dataSourceUpdateHandler) == false) {
						try {
							// reset the datasource handler via the update
							// handler
							dataSourceUpdateHandler.resetData(purge);
						} catch (DataException e) {
							String message = messageGenerator.getMessage("rollover.reset.failure", new MessageGeneratorArgs(e, dataSrcUniqueName));
							exceptionHandler.handleException(message, e, Level.WARN);
						}
					} else {
						String message = messageGenerator.getMessage("rollover.reset.warning", new MessageGeneratorArgs(null, dataSrcUniqueName));
						logger.log(Level.WARN, message);
					}
				}
			}
			endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Rollover process took " + timeTaken + " millisecond(s)...");
			}
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Reregisterating "+ count + " datasource handlers...");
			}
			startTime = System.currentTimeMillis();
			for (String dataSrcUniqueName : localUniqueNames) {
				DataSourceUpdateHandler[] dataSourceUpdateHandlers = DataSourceUpdateHandlerFactory.getInstance().getPreCreatedHandlers(dataSrcUniqueName);
				for (DataSourceUpdateHandler dataSourceUpdateHandler : dataSourceUpdateHandlers) {
					try {
						// trigger an subscribe
						dataSourceUpdateHandler.subscribe();
					} catch (StreamingException e) {
						String message = messageGenerator.getMessage("rollover.subscribe.failure", new MessageGeneratorArgs(e, dataSrcUniqueName));
						exceptionHandler.handleException(message, e, Level.WARN);
					}
				}
			}
			endTime = System.currentTimeMillis();
			timeTaken = endTime - startTime;
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Reregisteration process took " + timeTaken + " millisecond(s)...");
			}
			logger.log(Level.INFO, "Completed rollover for "+count+" datasource(s)...");
		} finally {
			unSubscribeFailedDataSrcUpdateHndlrs.clear();
			unSubscribeFailedDataSrcUpdateHndlrs = null;
			localUniqueNames.clear();
			localUniqueNames = null;

		}
		try {
			checkAndReschedule();
		} catch (Exception e) {
			logger.log(Level.WARN, "Failed to rescehdule rollover...", e);
		}
	}

	private void checkAndReschedule() {
		// Check if the next rollover start time is in DST mode
		Date nextScheduleTime = getNextScheduleTime();
		boolean currentInDST = timeZone.inDaylightTime(nextScheduleTime);
		// If the timer schedule start time's DST state is different from the
		// next run time's DST state
		// then cancel the old timer and trigger it to start in new mode
		if (currentInDST != startedInDST) {
			logger.log(Level.INFO, "Rescheduling because of DST changes from DST:" + (startedInDST ? "on" : "off") + " to DST:" + (currentInDST ? "on" : "off"));
			timerTask.cancel();
			schedule(nextScheduleTime);
		}
	}

	@Override
	protected boolean doStop() {
		timerTask.cancel();
		DataSourceHandlerCache.getInstance().removeDataCacheListener(this);
		return true;
	}

	@Override
	public void dataSourceHandlerAdded(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx) {
		if (handler instanceof Updateable) {
			synchronized (dataSourceHandlerUniqueNames) {
				String uniqueName = handler.getUniqueName();
				boolean added = dataSourceHandlerUniqueNames.add(uniqueName);
				if (added == true && logger.isEnabledFor(Level.DEBUG) == true){
					logger.log(Level.DEBUG, "Added "+uniqueName+" to rollover registry...");
				}
			}
		}
	}

	@Override
	public void dataSourceHandlerRemoved(MALSeriesConfig seriesConfig, DataSourceHandler handler, PresentationContext pCtx) {
		if (handler instanceof Updateable) {
			synchronized (dataSourceHandlerUniqueNames) {
				String uniqueName = handler.getUniqueName();
				boolean removed = dataSourceHandlerUniqueNames.remove(uniqueName);
				if (removed == true && logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Added " + uniqueName + " to rollover registry...");
				}
			}
		}
	}

	class RolloverTimerTask extends ExceptionResistentTimerTask {

		public RolloverTimerTask(Logger logger, ExceptionHandler exceptionHandler) {
			super("RollOverTask", logger, exceptionHandler, false);
		}

		@Override
		protected void doRun() {
			rollover();
		}

	}
}
