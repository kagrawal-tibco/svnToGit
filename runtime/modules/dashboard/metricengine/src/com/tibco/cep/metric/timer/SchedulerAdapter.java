package com.tibco.cep.metric.timer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.METRIC_TYPE;
import com.tibco.cep.designtime.core.model.TIME_UNITS;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.repo.emf.DeployedEMFProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.MultiValuedMap;

public class SchedulerAdapter {

	private Cluster cluster;
	private RuleSession session;
	private MultiValuedMap frequencyMap;
	private Map<Long, List<Metric>> schedulerMap;
	private static SchedulerAdapter adapter;
	
	public SchedulerAdapter(RuleSession session, Cluster cluster) {
		this.cluster = cluster;
		this.session = session;
		frequencyMap = new ArrayListValuedHashMap();
		schedulerMap = new HashMap<Long, List<com.tibco.cep.designtime.core.model.element.Metric>>();
	}
	
	public static synchronized SchedulerAdapter start(RuleSession session, Cluster cluster) throws Exception {
		if (adapter == null) {
			adapter = new SchedulerAdapter(session, cluster);
			adapter.start();
		}
		return adapter;
	}
	
	private void start() throws Exception {
		DeployedEMFProject project = (DeployedEMFProject) session.getRuleServiceProvider().getProject();
		DesignerProject index = (DesignerProject) (project.getRuntimeIndex(AddOnType.CORE));
		for (EntityElement entityElement : index.getEntityElements()) {
			Entity entity = entityElement.getEntity();
			if (entity instanceof Metric) {
				Metric metric = (Metric) entity;
				if (entity.eIsProxy() == true && METRIC_TYPE.ROLLING_TIME == metric.getType()) {

					long windowSize = metric.getWindowSize();
					TIME_UNITS windowType = metric.getWindowType();
					long recurringFrequency = metric.getRecurringFrequency();
					TIME_UNITS recurringTimeType = metric.getRecurringTimeType();
					
					windowSize = windowSize * getMultiplier(windowType);
					recurringFrequency = recurringFrequency * getMultiplier(recurringTimeType);
					
					if (schedulerMap.containsKey(recurringFrequency)) {
						List<Metric> values = schedulerMap.get(recurringFrequency);
						values.add(metric);
					} else {
						ArrayList<Metric> value = new ArrayList<Metric>();
						value.add(metric);
						schedulerMap.put(recurringFrequency, value);
					}
					
					frequencyMap.put(recurringFrequency, windowSize);

				}
			}
		}
		for (long recurringFrequency : schedulerMap.keySet()) {
			List<Long> windowSizes = (List<Long>) frequencyMap.get(recurringFrequency);
			for (long windowSize : windowSizes) {
//				Scheduler scheduler = new SchedulerImpl(session, cluster);
//				scheduler.initialize(recurringFrequency, windowSize);
//				scheduler.schedule(schedulerMap.get(recurringFrequency));
				Scheduler scheduler = new TimerMetricScheduler(cluster);
				scheduler.initialize(recurringFrequency, windowSize);
				scheduler.schedule(schedulerMap.get(recurringFrequency));
			}
		}
	}
	
	private long getMultiplier(TIME_UNITS units) {
        long multiplier = 1;
        switch(units.getValue()) {
            case TIME_UNITS.MILLISECONDS_VALUE:
                multiplier = 1L;
                break;
            case TIME_UNITS.SECONDS_VALUE:
                multiplier = 1000L;
                break;
            case TIME_UNITS.MINUTES_VALUE:
                multiplier = 60 * 1000L;
                break;
            case TIME_UNITS.HOURS_VALUE:
                multiplier = 60 * 60 * 1000L;
                break;
            case TIME_UNITS.DAYS_VALUE:
                multiplier = 24 * 60 * 60 * 1000L;
                break;
            case TIME_UNITS.WEEK_DAYS_VALUE:
                multiplier = 5 * 24 * 60 * 60 * 1000L;
                break;
            case TIME_UNITS.WEEKS_VALUE:
                multiplier = 7 * 24 * 60 * 60 * 1000L;
                break;
            case TIME_UNITS.MONTHS_VALUE:
                multiplier = 30 * 24 * 60 * 60 * 1000L;
                break;
            default:
                multiplier = 1L;
        }

        return multiplier;
	}
}
