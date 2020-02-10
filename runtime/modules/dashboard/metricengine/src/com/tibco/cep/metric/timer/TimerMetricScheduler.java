package com.tibco.cep.metric.timer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.be.functions.entity.EntityHelper;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.metric.query.QueryManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Metric;
import com.tibco.cep.runtime.model.element.MetricDVM;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.scheduler.SchedulerCache;
import com.tibco.cep.runtime.service.cluster.scheduler.WorkEntry;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

public class TimerMetricScheduler implements Scheduler {
	
	private static final String $TIMERMETRICS = ".$timermetrics";
	private Logger schedulerLogger;
	private RuleSession ruleSession;
	private Cluster cacheCluster;

	private static final String CODE_GEN_PREFIX = BEProperties.loadDefault().getString("be.codegen.rootPackage", "be.gen");
    private int siteId;
    private long delay;
    private long window;

    protected static long lockTimeout = 1000;
	protected static int lockRetryCount = 10;
	protected static boolean lockIsLocal = true;

    public TimerMetricScheduler(Cluster cacheCluster) {
		this.cacheCluster = cacheCluster;
	}

	@Override
	public void initialize(long delay, long window) {
		this.delay = delay;
		this.window = window;
	}
    
    public void schedule(List<com.tibco.cep.designtime.core.model.element.Metric>  metricsDT) throws Exception{
        siteId = cacheCluster.getClusterConfig().getSiteId();
        String schedulerKey = cacheCluster.getClusterName() + "." + siteId + $TIMERMETRICS;
        cacheCluster.getSchedulerCache().createScheduler(schedulerKey, window, delay, false, SchedulerCache.MODE_ALL);
        
        WorkEntry workEntry = new MetricSchedulerTask(window, metricsDT);
        cacheCluster.getSchedulerCache().schedule(schedulerKey, ""+delay, workEntry);
    }

    public void remove(String key) throws Exception{
        String schedulerKey = cacheCluster.getClusterName() + "." + siteId + $TIMERMETRICS;
        cacheCluster.getSchedulerCache().remove(schedulerKey, key);
    }
    
    class MetricSchedulerTask implements WorkEntry {
        long window;
        long scheduledTime;
    	private List<com.tibco.cep.designtime.core.model.element.Metric> metricsDTList;

        public MetricSchedulerTask(long window, List<com.tibco.cep.designtime.core.model.element.Metric> metricsDT) {
            this.window=window;
            this.metricsDTList = metricsDT;
        }

        public MetricSchedulerTask() {
        }

        public void execute(String key, Object cacheAgent) throws Exception {
    		
			List<Concept> metricsRTList = null;
			List<Concept> metricsDBList = null;
			List<Concept> metricDVMDBList = null;
			List<Concept> metricDVMOldDBList = null;

			for (com.tibco.cep.designtime.core.model.element.Metric metricDT : metricsDTList) {

				String nameDT = metricDT.getFullPath();
				nameDT = nameDT.replaceAll("/", ".");
                String nameRT = CODE_GEN_PREFIX + nameDT;
                
                try {
                	Metric metricRT = null;
	                ClassLoader classLoader = (ClassLoader) ruleSession.getRuleServiceProvider().getTypeManager();
					Class<?> clz = classLoader.loadClass(nameRT);
//    					Method method = clz.getMethod("lookup", new Class[] { String.class });
					StringBuffer extId = new StringBuffer();
					extId.append(nameRT);
					extId.append("^");

	                Collection<PropertyDefinition> propDefs = metricDT.getPropertyDefinitions(false);
	                for (PropertyDefinition prop : propDefs) {
	                	if (prop.isGroupByField()) {
	    					extId.append(prop.getName());
	    			        ObjectManager om = ruleSession.getObjectManager();
	    			        metricRT = (Metric) om.getElement(extId.toString());
	                	}
	                }
	                
	                if (metricRT == null) {
	                	/* Call C_Lock before updating metric
	        			int lockCurrentRetryCount = 0;
	        			boolean lockStatus = false;
	        			do {
	        				lockStatus = com.tibco.be.functions.coherence.CoherenceFunctions.C_Lock(extId.toString(), lockTimeout, lockIsLocal);
	        				lockCurrentRetryCount++;
	        			} while (lockStatus == false
	        					&& lockCurrentRetryCount < lockRetryCount);
	        			if (lockStatus == false) {
	        				schedulerLogger.log(com.tibco.cep.kernel.service.logging.Level.WARN,
	        						"Failed to acquire lock for metric : " + extId);
	        			}*/
	                    if (((RuleSessionImpl) ruleSession).lock(extId.toString(), lockTimeout, lockIsLocal)) {
							metricRT = (Metric) clz.newInstance();
							metricRT.setExtId(extId.toString());
		                }
	                }

					metricsRTList = getMetricsFromCache(nameDT);
					metricsDBList = getMetricsFromDB(nameRT);
					metricDVMDBList = getMetricsDVMFromDB(nameRT + "DVM", window);
					metricDVMOldDBList = getOldMetricsDVMFromDB(nameRT + "DVM", window);

					for (Concept metric : metricsDBList) {
						clz = metric.getClass();
						
						Method method = clz.getMethod("evictProperties", new Class<?>[] {});
						try {
							method.invoke(null, new Object[] {});
						} catch (NullPointerException npe) {
							schedulerLogger.log(Level.ERROR, "New metric instance [" + metricRT.getClass().getName() + "] created");
						}
						method = clz.getMethod("compute", List.class);
						method.invoke(null, metricDVMDBList);
					}
					schedulerLogger.log(Level.ERROR, "Ran metric at " + System.currentTimeMillis());
					try {
						ruleSession.assertObject(metricRT, true);
						for (Concept metricDVMOld : metricDVMOldDBList) {
							EntityHelper.deleteEntityByExtId(metricDVMOld.getExtId());
						}
					} catch (DuplicateExtIdException e) {
						schedulerLogger.log(Level.ERROR, "Error occurred while asserting metric [" + metricRT.getClass().getName() + "]");
					}
				} catch (Exception e1) {
					schedulerLogger.log(Level.ERROR, "Error occurred while retrieving metric [" + metricDT.getName() + "]");
				}
			}
        }

        public long getScheduleTime() {
            return scheduledTime;
        }

        public long setScheduleTime(long newScheduledTime) {
            long oldScheduledTime = scheduledTime;
            scheduledTime = newScheduledTime;
            return oldScheduledTime;
        }

        public long getRepeatInterval() {
            return 0L;
        }

        public void readExternal(DataInput dataInput) throws IOException {
            scheduledTime=dataInput.readLong();
        }

        public void writeExternal(DataOutput dataOutput) throws IOException {
            dataOutput.writeLong(scheduledTime);
        }
    }
	
	@SuppressWarnings("unchecked")
	private List<Concept> getMetricsFromCache(String metricName) throws Exception {
		List<Concept> runtimeMetrics = new ArrayList<Concept>();
		
		MetadataCache metadataCache = cacheCluster.getMetadataCache();
		ArrayList<Integer> conceptIds = metadataCache.getRegisteredConceptTypes();
		for (int typeId : conceptIds) {
			Class<?> clazz = metadataCache.getClass(typeId);
			if (com.tibco.cep.runtime.model.element.MetricDVM.class.isAssignableFrom(clazz) &&
					clazz.getName().endsWith(metricName)) {
				EntityDao entityProvider = metadataCache.getEntityDao(clazz);
				
				for (Iterator metricIterator = entityProvider.getAll().iterator(); metricIterator.hasNext();) {
					Map.Entry<Long, Concept> tuple = (Map.Entry<Long, Concept>) metricIterator.next();
					runtimeMetrics.add((MetricDVM) tuple.getValue());
				}
			}
		}
		return runtimeMetrics;
	}
	
	@SuppressWarnings("unchecked")
	private List<Concept> getMetricsFromDB(String metricName) throws Exception {
		List<Concept> runtimeMetrics = new ArrayList<Concept>();

		MetadataCache metadataCache = cacheCluster.getMetadataCache();
		ArrayList<Integer> conceptIds = metadataCache.getRegisteredConceptTypes();
		for (int typeId : conceptIds) {
			Class<?> clazz = metadataCache.getClass(typeId);
			if (com.tibco.cep.runtime.model.element.Metric.class.isAssignableFrom(clazz) &&
					clazz.getName().endsWith(metricName)) {
				metricName = clazz.getName();
			}
		}
		
		QueryManager queryManager = QueryManager.getInstance();
		queryManager.init();

		Iterator iter = queryManager.query("select * from " + metricName);
		while (iter != null && iter.hasNext()) {
			runtimeMetrics.add((Concept) iter.next());
		}
		return runtimeMetrics;
	}
	
	@SuppressWarnings("unchecked")
	private List<Concept> getMetricsDVMFromDB(String metricName, long window) throws Exception {
		List<Concept> runtimeMetrics = new ArrayList<Concept>();

		MetadataCache metadataCache = cacheCluster.getMetadataCache();
		ArrayList<Integer> conceptIds = metadataCache.getRegisteredConceptTypes();
		for (int typeId : conceptIds) {
			Class<?> clazz = metadataCache.getClass(typeId);
			if (com.tibco.cep.runtime.model.element.MetricDVM.class.isAssignableFrom(clazz) &&
					clazz.getName().endsWith(metricName)) {
				metricName = clazz.getName();
			}
		}
		
		QueryManager queryManager = QueryManager.getInstance();
		queryManager.init();
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		String date = dateFormat.format(new Date(System.currentTimeMillis()-window));
		Iterator iter = queryManager.query("select * from " + metricName + " where @time_last_modified >= \'" + date + "\'");
		while (iter != null && iter.hasNext()) {
			runtimeMetrics.add((Concept) iter.next());
		}
		return runtimeMetrics;
	}
	
	@SuppressWarnings("unchecked")
	private List<Concept> getOldMetricsDVMFromDB(String metricName, long window) throws Exception {
		List<Concept> runtimeMetrics = new ArrayList<Concept>();

		MetadataCache metadataCache = cacheCluster.getMetadataCache();
		ArrayList<Integer> conceptIds = metadataCache.getRegisteredConceptTypes();
		for (int typeId : conceptIds) {
			Class<?> clazz = metadataCache.getClass(typeId);
			if (com.tibco.cep.runtime.model.element.MetricDVM.class.isAssignableFrom(clazz) &&
					clazz.getName().endsWith(metricName)) {
				metricName = clazz.getName();
			}
		}
		
		QueryManager queryManager = QueryManager.getInstance();
		queryManager.init();
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		String date = dateFormat.format(new Date(System.currentTimeMillis()-window));
		Iterator iter = queryManager.query("select * from " + metricName + " where @time_last_modified < \'" + date + "\'");
		while (iter != null && iter.hasNext()) {
			runtimeMetrics.add((Concept) iter.next());
		}
		return runtimeMetrics;
	}
}
