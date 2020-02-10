package com.tibco.cep.bemm.common.service.impl;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularDataSupport;
import javax.management.remote.JMXConnector;

import com.tibco.cep.bemm.common.exception.MBeanOperationFailException;
import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.MemoryUsage;
import com.tibco.cep.bemm.common.model.ThreadDetail;
import com.tibco.cep.bemm.common.model.impl.CPUUsageImpl;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;
import com.tibco.cep.bemm.common.model.impl.MemoryUsageImpl;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.common.model.impl.ThreadDetailImpl;
import com.tibco.cep.bemm.common.operations.model.Arg;
import com.tibco.cep.bemm.common.operations.model.Column;
import com.tibco.cep.bemm.common.operations.model.Columns;
import com.tibco.cep.bemm.common.operations.model.Header;
import com.tibco.cep.bemm.common.operations.model.Headers;
import com.tibco.cep.bemm.common.operations.model.Method;
import com.tibco.cep.bemm.common.operations.model.OperationResult;
import com.tibco.cep.bemm.common.operations.model.Row;
import com.tibco.cep.bemm.common.operations.model.Rows;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MBeanService;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.BeAgentMemoryPoolNames;
import com.tibco.cep.bemm.common.util.MemoryPoolNames;
import com.tibco.cep.bemm.management.exception.JmxConnectionException;
import com.tibco.cep.bemm.management.util.Constants;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.model.impl.BEMMModelFactoryImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.NameValuePair;
import com.tibco.cep.bemm.model.impl.NameValuePairs;
import com.tibco.cep.bemm.model.impl.ObjectFactory;
import com.tibco.cep.bemm.monitoring.exception.RemoteMetricsCollectorServiceException;
import com.tibco.cep.bemm.monitoring.service.RemoteMetricsCollectorService;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.runtime.management.Domain;
import com.tibco.cep.runtime.management.ManagementTableMBean;
import com.tibco.cep.runtime.management.impl.cluster.ManagementTableMBeanImpl;
import com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployerMBean;
import com.tibco.cep.runtime.service.dao.impl.tibas.mm.ASClusterMBean;
import com.tibco.cep.runtime.service.management.exception.BEMMEntityNotFoundException;
import com.tibco.cep.runtime.service.management.exception.BEMMIllegalSetupException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.service.management.exception.JMXConnClientException;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnClient;
import com.tibco.cep.runtime.service.management.process.EngineEngineMBean;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

public class MBeanServiceImpl extends AbstractStartStopServiceImpl implements MBeanService {

	private MessageService messageService;
	private static final String AS_CLUSTER_MBEAN = "com.tibco.be:service=Cluster,name={0}$cluster";

	private static final String RULE_TEMPLATE_DEPLOYER_MBEAN = "com.tibco.be:service=RuleTemplateDeployer,name=RuleTemplateInstanceDeployer";

	/**
	 * Logger Object
	 */
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(MBeanServiceImpl.class);

	private static final String OBJ_NAME_MNGMT_TABLE = "Management Table";
	private static final String PROCESS_OBJ_NAME_PATTERN = "com.tibco.be:dir=Methods,Group={0}";
	private static final String AGENT_OBJ_NAME_PATTERN = "com.tibco.be:type=Agent,agentId={0},dir=Methods,Group={1}";
	private ConcurrentHashMap<String, JMXConnector> jmxConnectionMap = new ConcurrentHashMap<String, JMXConnector>();
	private Set<String> excludedProps = null;

	@Override
	public void init(Properties configuration) throws Exception {
		messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		excludedProps = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementDataStoreService()
				.getExcludedProperties();
	}

	@Override
	public List<ProcessMemoryUsageImpl> getMemoryUsage(ServiceInstance instance) throws MBeanOperationFailException {
		Host host = instance.getHost();
		if (null != instance && null != host && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
			String password = instance.getJmxPassword();
			String username = instance.getJmxUserName();

			String decodedPassword = ManagementUtil.getDecodedPwd(password);
			try {
				List<ProcessMemoryUsageImpl> memoryUsages = new ArrayList<ProcessMemoryUsageImpl>();

				// Get MBean Connection
				MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
						instance.getJmxPort(), instance.getKey(), username, decodedPassword);

				if (null != mBeanServerConnection) {

					// Get the data from only if mbean is registered
					String objectName = MessageFormat.format(PROCESS_OBJ_NAME_PATTERN, "Engine");
					ObjectName objectNameInstance = ObjectName.getInstance(objectName);
					if (isRegistered(mBeanServerConnection, objectNameInstance)) {

						EngineEngineMBean proxy = JMX.newMBeanProxy(mBeanServerConnection, objectNameInstance,
								EngineEngineMBean.class);

						TabularDataSupport memeoryUsage = proxy.GetMemoryUsage();
						for (Iterator<?> iter = ((TabularDataSupport) memeoryUsage).values().iterator(); iter
								.hasNext();) {
							CompositeDataSupport currentRow = (CompositeDataSupport) iter.next();

							long free = Long.parseLong(currentRow.get("Free").toString().replaceAll("\"", ""));
							long max = Long.parseLong(currentRow.get("Max").toString().replaceAll("\"", ""));
							double percentUsed = Double
									.parseDouble(currentRow.get("Percentage Used").toString().replaceAll("\"", ""));
							long used = Long.parseLong(currentRow.get("Used").toString().replaceAll("\"", ""));

							ProcessMemoryUsageImpl processMemoryUsage = new ProcessMemoryUsageImpl(free, max,
									percentUsed, used);
							memoryUsages.add(processMemoryUsage);
						}
					}
				}
				return memoryUsages;

			} catch (NumberFormatException | MalformedObjectNameException | NullPointerException
					| JMXConnClientException ex) {
				throw new MBeanOperationFailException(ex);
			} catch (Exception ex) {
				throw new MBeanOperationFailException(ex);
			}
		}
		return null;
	}

	@Override
	public List<LogDetailImpl> getLoggerDetails(ServiceInstance instance) throws MBeanOperationFailException {
		List<LogDetailImpl> logDetails = new ArrayList<LogDetailImpl>();
		Host host = instance.getHost();
		if (null != instance && null != host && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
			String password = instance.getJmxPassword();
			String username = instance.getJmxUserName();

			String decodedPassword = ManagementUtil.getDecodedPwd(password);
			try {
				// Get MBean Connection
				MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
						instance.getJmxPort(), instance.getKey(), username, decodedPassword);

				if (null != mBeanServerConnection) {

					// Get the data from only if mbean is registered
					String objectName = MessageFormat.format(PROCESS_OBJ_NAME_PATTERN, "Engine");
					ObjectName objectNameInstance = ObjectName.getInstance(objectName);
					if (isRegistered(mBeanServerConnection, objectNameInstance)) {
						EngineEngineMBean proxy = JMX.newMBeanProxy(mBeanServerConnection, objectNameInstance,
								EngineEngineMBean.class);

						TabularDataSupport events = proxy.GetLoggerNamesWithLevels();
						for (Iterator<?> iter = events.values().iterator(); iter.hasNext();) {
							CompositeDataSupport currentRow = (CompositeDataSupport) iter.next();

							String name = currentRow.get("Logger Name").toString().replaceAll("\"", "");
							String level = currentRow.get("Log Level").toString().replaceAll("\"", "");

							LogDetailImpl instanceCount = new LogDetailImpl(name, level);
							logDetails.add(instanceCount);

						}
					}
				}

				return logDetails;

			} catch (NumberFormatException | MalformedObjectNameException | NullPointerException
					| JMXConnClientException ex) {
				throw new MBeanOperationFailException(ex);
			} catch (Exception ex) {
				throw new MBeanOperationFailException(ex);
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Domain> getDomains(ServiceInstance instance) throws MBeanOperationFailException {
		if (null != instance) {
			Host host = instance.getHost();
			if (null != instance && null != host) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);

				try {
					// Get Mbean Connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					String objectName = MessageFormat.format(PROCESS_OBJ_NAME_PATTERN, OBJ_NAME_MNGMT_TABLE);
					if (mBeanServerConnection != null
							&& isRegistered(mBeanServerConnection, ObjectName.getInstance(objectName))) {
						return (Collection<Domain>) mBeanServerConnection.invoke(ObjectName.getInstance(objectName),
								"getDomains", new Object[] {}, new String[] {});
					}

				} catch (JMXConnClientException | MalformedObjectNameException | InstanceNotFoundException
						| MBeanException | ReflectionException | IOException ex) {
					throw new MBeanOperationFailException(ex);
				}
			}
		}

		return null;

	}

	@Override
	public OperationResult invoke(String entityType, String methodGroup, String methodName, Map<String, String> params,
			String username, String password, String hostIp, int jmxPort, int agentId, String instanceKey,
			String clusterName) throws MBeanOperationFailException {

		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		OperationResult operationResult = new OperationResult();
		try {
			Method method = BEMMServiceProviderManager.getInstance().getEntityMethodsDescriptorService()
					.getEnityMethod(entityType, methodGroup, methodName);
			if (null != method) {

				if ("loadAndDeployRuleTemplate".equals(methodName)) {
					loadAndDeployRuleTemplates(params.get("agentName"), params.get("projectName"),
							params.get("ruleTemplateInstanceFQN"), username, password, jmxPort, hostIp, instanceKey);

				} else if ("loadAndDeploy".equals(methodName)) {
					loadAndDeploy(params.get("vrfURI"), params.get("implName"), username, password, jmxPort, hostIp,
							instanceKey, clusterName);
				} else {
					// Get the connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(hostIp, jmxPort, instanceKey,
							username, decodedPassword);

					String objectName = MessageFormat.format(PROCESS_OBJ_NAME_PATTERN, methodGroup);
					if (!"process".equalsIgnoreCase(entityType)) {
						objectName = MessageFormat.format(AGENT_OBJ_NAME_PATTERN, agentId, methodGroup);
					}
					if (mBeanServerConnection != null
							&& isRegistered(mBeanServerConnection, ObjectName.getInstance(objectName))) {
						Object[] parameters = new Object[] {};
						String[] signature = new String[] {};
						List<Arg> args = method.getArg();

						if (null != args && !args.isEmpty()) {
							removeInvalidArgs(args);
							if (null != args && !args.isEmpty()) {
								parameters = new Object[args.size()];
								signature = new String[args.size()];
							} else {
								params = new HashMap<String, String>();
							}

						} else {
							params = new HashMap<String, String>();
						}
						int index = 0;
						if (null != params && !params.isEmpty()) {
							for (Arg arg : args) {
								if (null != arg) {
									String argName = arg.getName().trim();

									String paramValue = String.valueOf(params.get(argName));
									if ("Scorecard URI".equals(argName))
										if (null != paramValue)
											paramValue = "";
									String paramType = arg.getType();
									if ("GetInstance".equalsIgnoreCase(methodName)
											|| "GetEvent".equalsIgnoreCase(methodName)) {
										if ("boolean".equalsIgnoreCase(paramType)) {
											paramType = "string";
										}
									}
									String javaType = getJavaType(paramType);

									parameters[index] = getValue(paramValue, paramType);
									signature[index] = javaType;
									index++;
								}
							}
						}

						Object obj = null;
						try {
							obj = mBeanServerConnection.invoke(ObjectName.getInstance(objectName), methodName,
									parameters, signature);
						} catch (Exception e) {
						}
						if (null != obj && obj instanceof TabularDataSupport) {
							TabularDataSupport tabularData = (TabularDataSupport) obj;
							if (null != tabularData && !tabularData.isEmpty()) {
								Headers headers = new Headers();
								Rows rows = new Rows();
								for (Iterator<?> iter = ((TabularDataSupport) tabularData).values().iterator(); iter
										.hasNext();) {
									CompositeDataSupport currentRow = (CompositeDataSupport) iter.next();
									Set<String> columnKeysSet = currentRow.getCompositeType().keySet();
									ArrayList<String> columnKeysArray = new ArrayList<>(columnKeysSet);
									// sort array in ascending order for
									// consistency
									Collections.sort(columnKeysArray); // TODO.
																		// clean
									Row row = new Row();
									Columns columns = new Columns(); // this up
									for (String key : columnKeysArray) {

										if (null == operationResult.getHeaders()
												|| null == operationResult.getHeaders().getHeader()
												|| operationResult.getHeaders().getHeader().isEmpty()) {
											Header header = new Header();
											key = key.replaceAll("\"", "").trim();
											String columnName = Character.toUpperCase(key.charAt(0)) + key.substring(1);
											if (!"Row".equalsIgnoreCase(columnName)) {
												header.setValue(columnName);
												headers.getHeader().add(header);
											}
										}
										if (!key.contains("Row")) {
											Column column = new Column();
											Object value = currentRow.get(key);
											if (null != value) {
												column.setValue(value.toString().replaceAll("\"", "").trim());
											}
											columns.getColumn().add(column);
										}
									}
									row.setColumns(columns);
									rows.getRow().add(row);
									if (null == operationResult.getHeaders()
											|| null == operationResult.getHeaders().getHeader()
											|| operationResult.getHeaders().getHeader().isEmpty()) {
										operationResult.setHeaders(headers);
									}
								}
								operationResult.setRows(rows);

							}

						}
					}
				}

			}
		} catch (Exception ex) {
			LOGGER.log(Level.DEBUG, ex.getMessage());
		}
		return operationResult;
	}

	@Override
	public OperationResult getGarbageCollectionDetails(ServiceInstance instance) throws MBeanOperationFailException {
		OperationResult operationreturndata = null;
		if (null != instance && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {

			Host host = instance.getHost();
			if (null != host) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// GET connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementFactory.RUNTIME_MXBEAN_NAME);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {
						try {
							RuntimeMXBean runTimeMXBean = ManagementFactory.newPlatformMXBeanProxy(
									mBeanServerConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
							ObjectName gcName = ObjectName
									.getInstance(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
							Set<ObjectName> mbeanNames = mBeanServerConnection.queryNames(gcName, null);
							List<GarbageCollectorMXBean> gcMBeans = new ArrayList<GarbageCollectorMXBean>();
							if (mbeanNames != null) {

								Iterator<ObjectName> iterator = mbeanNames.iterator();
								while (iterator.hasNext()) {
									ObjectName objName = (ObjectName) iterator.next();
									GarbageCollectorMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(
											mBeanServerConnection, objName.getCanonicalName(),
											GarbageCollectorMXBean.class);
									gcMBeans.add(proxy);
								}
							}

							if (runTimeMXBean == null || gcMBeans == null || gcMBeans.isEmpty() == true) {
								return null;
							}
							operationreturndata = new OperationResult();
							Headers headers = new Headers();
							String[] headerNames = new String[] { "Pool Name", "UpTime", "Pool Collection Count",
									"Pool Collection Time" };
							for (String headerName : headerNames) {
								Header header = new Header();
								header.setValue(headerName);
								headers.getHeader().add(header);
							}
							operationreturndata.setHeaders(headers);
							Rows rows = new Rows();
							for (GarbageCollectorMXBean gcMBean : gcMBeans) {
								Row row = new Row();
								Columns columns = new Columns();

								Column column = new Column();
								column.setValue(gcMBean.getName());
								columns.getColumn().add(column);

								long uptime = runTimeMXBean.getUptime();
								column = new Column();
								column.setValue(ManagementUtil.formatTime(uptime));
								columns.getColumn().add(column);

								column = new Column();
								column.setValue(String.valueOf(gcMBean.getCollectionCount()));
								columns.getColumn().add(column);

								column = new Column();
								column.setValue(ManagementUtil.formatTime(gcMBean.getCollectionTime()));
								columns.getColumn().add(column);

								row.setColumns(columns);
								rows.getRow().add(row);
							}
							operationreturndata.setRows(rows);
						} catch (MalformedObjectNameException e) {
							throw new RuntimeException(messageService
									.getMessage(MessageKey.QUERY_GARBAGE_COLLECTION_POOL_NAMES_ERROR_MESSAGE), e);
						}
					}
				} catch (Exception e) {
					String msg;
					if ((msg = ManagementUtil.getClassExceptionMsg(e, BEMMUserActivityException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e, BEMMEntityNotFoundException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e, BEMMInvalidAccessException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e,
									BEMMInvalidUserRoleException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e, JMXConnClientException.class)) != null) {
						throw new MBeanOperationFailException(msg);

					} else if ((msg = ManagementUtil.getClassExceptionMsg(e,
							BEMMIllegalSetupException.class)) != null) {
						throw new MBeanOperationFailException(msg);
					} else {
						throw new MBeanOperationFailException(e.getMessage());
					}
				}

			}
		}
		return operationreturndata;
	}

	@Override
	public long getProcessStartTime(ServiceInstance instance) {
		long jvmUptime = -1;
		Host host = instance.getHost();
		String password = instance.getJmxPassword();
		String username = instance.getJmxUserName();
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		try {
			// Get JMX Connection and invoke method
			if (BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
				MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
						instance.getJmxPort(), instance.getKey(), username, decodedPassword);
				ObjectName objectName = ObjectName.getInstance(ManagementFactory.RUNTIME_MXBEAN_NAME);
				if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {
					RuntimeMXBean runTimeMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
							ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
					jvmUptime = runTimeMXBean.getUptime();
				}
			}

		} catch (Exception ex) {
			LOGGER.log(Level.DEBUG, ex.getMessage());
		}
		return jvmUptime;
	}

	@Override
	public ThreadDetail getThreadDetails(ServiceInstance instance) throws MBeanOperationFailException {
		ThreadDetail threadDetails = null;
		if (null != instance) {

			Host host = instance.getHost();
			if (null != host) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// Get connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementFactory.THREAD_MXBEAN_NAME);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {

						ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
								ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
						long threadCount = threadMXBean.getThreadCount();
						long[] deadlockedThreads = threadMXBean.findMonitorDeadlockedThreads();
						long deadlockedThreadCount = 0;
						if (null != deadlockedThreads && deadlockedThreads.length > 0) {
							deadlockedThreadCount = deadlockedThreads.length;
						}
						threadDetails = new ThreadDetailImpl(threadCount, deadlockedThreadCount);
					}
				} catch (JMXConnClientException | IOException | MalformedObjectNameException ex) {
					LOGGER.log(Level.DEBUG, ex.getMessage());
				}
			}
		}
		return threadDetails;
	}

	@Override
	public List<String> getMemoryPools() throws MBeanOperationFailException {
		MemoryPoolNames[] memoryPoolNames = MemoryPoolNames.values();
		List<String> memoryPools = new ArrayList<String>();
		for (MemoryPoolNames POOL_NAME : memoryPoolNames) {
			memoryPools.add(POOL_NAME.getName());
		}
		return memoryPools;

	}

	@Override
	public MemoryUsage getMemoryByPoolName(String poolName, ServiceInstance instance)
			throws MBeanOperationFailException {
		com.tibco.cep.bemm.common.model.MemoryUsage memoryUsageDetails = null;
		MemoryPoolNames[] memoryPoolNames = MemoryPoolNames.values();
		String cannonicalName = null;
		for (MemoryPoolNames memoryPoolName : memoryPoolNames) {
			if (memoryPoolName.getName().equals(poolName)) {
				cannonicalName = memoryPoolName.getCannonicalName();
			}
		}
		if (null == cannonicalName) {
			throw new MBeanOperationFailException(messageService.getMessage(MessageKey.INVALID_POOL_NAME_MESSAGE));
		}

		if (null != instance && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
			Host host = instance.getHost();
			if (null != host) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// Get MBean Connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementFactory.MEMORY_MXBEAN_NAME);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {

						if (MemoryPoolNames.NON_HEAP_MEMORY_USAGE.getName().equals(poolName)) {
							MemoryMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
									ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
							java.lang.management.MemoryUsage memoryUsage = proxy.getNonHeapMemoryUsage();
							memoryUsageDetails = new MemoryUsageImpl(memoryUsage.getInit(), memoryUsage.getCommitted(),
									memoryUsage.getMax(), memoryUsage.getUsed());

						} else if (MemoryPoolNames.HEAP_MEMORY_USAGE.getName().equals(poolName)) {
							MemoryMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
									ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
							java.lang.management.MemoryUsage memoryUsage = proxy.getHeapMemoryUsage();
							memoryUsageDetails = new MemoryUsageImpl(memoryUsage.getInit(), memoryUsage.getCommitted(),
									memoryUsage.getMax(), memoryUsage.getUsed());

						} else {
							MemoryPoolMXBean memoryPoolMXBean = ManagementFactory.newPlatformMXBeanProxy(
									mBeanServerConnection, cannonicalName, MemoryPoolMXBean.class);
							java.lang.management.MemoryUsage memoryUsage = memoryPoolMXBean.getUsage();
							memoryUsageDetails = new MemoryUsageImpl(memoryUsage.getInit(), memoryUsage.getCommitted(),
									memoryUsage.getMax(), memoryUsage.getUsed());
						}
					}
				} catch (JMXConnClientException | IOException | MalformedObjectNameException ex) {
					LOGGER.log(Level.DEBUG, ex.getMessage());
				}
			}
		}
		return memoryUsageDetails;
	}

	@Override
	public CPUUsage getCPUUsage(ServiceInstance instance) {
		RemoteMetricsCollectorService remoteMetricsCollectorService = instance.getRemoteMetricsCollectorService();
		CPUUsage cpuUsage = null;
		if (null != remoteMetricsCollectorService) {
			try {
				Map<String, String> dataMap = remoteMetricsCollectorService.populate();
				int cpucnt = Integer.parseInt(dataMap.get("cpucnt"));
				long currUpTime = Long.parseLong(dataMap.get("cputime"));
				double cpuUsageInPercent = Double.parseDouble(dataMap.get("usage"));
				long currCPUTime = Long.parseLong(dataMap.get("uptime"));
				cpuUsage = new CPUUsageImpl(cpucnt, currUpTime, currCPUTime, cpuUsageInPercent);
			} catch (RemoteMetricsCollectorServiceException ex) {
				LOGGER.log(Level.DEBUG, ex.getMessage());
			}
		}

		return cpuUsage;
	}

	@Override
	public void setLoggerDetails(ServiceInstance instance, DeploymentVariables deploymentVariables)
			throws MBeanOperationFailException {
		Host host = instance.getHost();
		if (null != instance && null != host && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
			String password = instance.getJmxPassword();
			String username = instance.getJmxUserName();

			String decodedPassword = ManagementUtil.getDecodedPwd(password);

			try {
				// Get Mbean connection
				MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
						instance.getJmxPort(), instance.getKey(), username, decodedPassword);
				if (null != mBeanServerConnection) {

					// Get the data from only if mbean is registered
					String objectName = MessageFormat.format(PROCESS_OBJ_NAME_PATTERN, "Engine");
					ObjectName objectNameInstance = ObjectName.getInstance(objectName);
					if (isRegistered(mBeanServerConnection, objectNameInstance)) {
						EngineEngineMBean proxy = JMX.newMBeanProxy(mBeanServerConnection, objectNameInstance,
								EngineEngineMBean.class);

						if (DeploymentVariableType.LOG_PATTERNS.equals(deploymentVariables.getType())) {
							for (NameValuePair nameValuePair : deploymentVariables.getNameValuePairs()
									.getNameValuePair()) {
								if (null != nameValuePair.getDefaultValue()) {
									String[] patterns = nameValuePair.getName().split(" ");
									if (null != patterns && patterns.length > 0) {
										for (String pattern : patterns) {
											if (null != pattern && !pattern.trim().isEmpty()) {
												proxy.SetLogLevel(pattern, nameValuePair.getDefaultValue());
											}
										}
									}

								}
							}
						}
					}
				}
			} catch (NumberFormatException | MalformedObjectNameException | NullPointerException
					| JMXConnClientException e) {
				throw new MBeanOperationFailException(e);
			} catch (Exception e) {
				throw new MBeanOperationFailException(e);
			}
		}
	}

	@Override
	public String getThreadDump(ServiceInstance instance) throws MBeanOperationFailException {
		if (null != instance) {

			Host host = instance.getHost();
			if (null != host) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// Get MBean connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementFactory.THREAD_MXBEAN_NAME);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {
						ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
								ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
						return buildThreadDump(threadMXBean);
					}
				} catch (JMXConnClientException | IOException | MalformedObjectNameException ex) {
					LOGGER.log(Level.DEBUG, ex.getMessage());
				}
			}
		}
		return null;
	}

	@Override
	public void applyLogPatterns(ServiceInstance instance, Map<String, String> logPatternsAndLevel)
			throws MBeanOperationFailException {

		Host host = instance.getHost();
		if (null != instance && null != host && BETeaAgentStatus.RUNNING.getStatus().equals(instance.getStatus())) {
			String password = instance.getJmxPassword();
			String username = instance.getJmxUserName();

			String decodedPassword = ManagementUtil.getDecodedPwd(password);
			try {
				// Get MBean connection
				MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
						instance.getJmxPort(), instance.getKey(), username, decodedPassword);
				if (null != mBeanServerConnection) {

					// Get the data from only if mbean is registered
					String objectName = MessageFormat.format(PROCESS_OBJ_NAME_PATTERN, "Engine");
					ObjectName objectNameInstance = ObjectName.getInstance(objectName);
					if (isRegistered(mBeanServerConnection, objectNameInstance)) {
						EngineEngineMBean proxy = JMX.newMBeanProxy(mBeanServerConnection, objectNameInstance,
								EngineEngineMBean.class);

						if (null != logPatternsAndLevel && !logPatternsAndLevel.isEmpty()) {
							for (Entry<String, String> entry : logPatternsAndLevel.entrySet()) {
								String[] patterns = entry.getKey().split(" ");
								if (null != patterns && patterns.length > 0) {
									for (String pattern : patterns) {
										if (null != pattern && !pattern.trim().isEmpty()) {
											proxy.SetLogLevel(pattern, entry.getValue());
										}
									}
								}

							}
						}
					}
				}
			} catch (NumberFormatException | MalformedObjectNameException | NullPointerException
					| JMXConnClientException e) {
				throw new MBeanOperationFailException(e);
			} catch (Exception e) {
				throw new MBeanOperationFailException(e);
			}
		}

	}

	@Override
	public void loadAndDeploy(String vrfURI, String implName, ServiceInstance instance)
			throws MBeanOperationFailException {
		if (!instance.isRunning())
			throw new MBeanOperationFailException(messageService.getMessage(MessageKey.INSTANCE_NOT_RUNNING_ERROR));
		loadAndDeploy(vrfURI, implName, instance.getJmxUserName(), instance.getJmxPassword(), instance.getJmxPort(),
				instance.getHost().getHostIp(), instance.getKey(),
				instance.getHost().getApplication().getClusterName());
	}

	@Override
	public void loadAndDeployRuleTemplates(String agentName, String projectName, String ruleTemplateInstanceFQN,
			ServiceInstance instance) throws MBeanOperationFailException {
		if (!instance.isRunning())
			throw new MBeanOperationFailException(messageService.getMessage(MessageKey.INSTANCE_NOT_RUNNING_ERROR));
		loadAndDeployRuleTemplates(agentName, projectName, ruleTemplateInstanceFQN, instance.getJmxUserName(),
				instance.getJmxPassword(), instance.getJmxPort(), instance.getHost().getHostIp(), instance.getKey());
	}

	@Override
	public synchronized MBeanServerConnection getMbeanConnection(String hostIp, int jmxPort, String instanceKey,
			String username, String decodedPassword) throws JMXConnClientException {
		// Get the existing connection ,if it is null
		JMXConnector jmxConnector = jmxConnectionMap.get(instanceKey);
		MBeanServerConnection mBeanServerConnection = null;
		if (null == jmxConnector) {
			LOGGER.log(Level.DEBUG, "MBean connection does not exist.");
			/**
			 * Get the processId and update status
			 */
			JMXConnClient client = new JMXConnClient(hostIp, jmxPort, username, decodedPassword, true);
			try {
				jmxConnector = client.getJMXConnector();
				jmxConnector.addConnectionNotificationListener(new JMXConnectorNotificationListener(instanceKey), null,
						jmxConnector);
				mBeanServerConnection = jmxConnector.getMBeanServerConnection();
				jmxConnectionMap.put(instanceKey, jmxConnector);

			} catch (JMXConnClientException | IOException e) {

				closeJMXConnection(instanceKey);
				throw new JMXConnClientException(messageService.getMessage(
						MessageKey.HOST_GET_JMX_CONNECTION_PORT_ERROR_MESSAGE, String.valueOf(jmxPort), hostIp));
			}
		} else {
			try {
				mBeanServerConnection = jmxConnector.getMBeanServerConnection();
			} catch (IOException e) {
				closeJMXConnection(instanceKey);
				throw new JMXConnClientException(messageService
						.getMessage(MessageKey.HOST_GET_JMX_CONNECTION_PORT_ERROR_MESSAGE, jmxPort, hostIp));
			}
		}
		return mBeanServerConnection;
	}

	@Override
	public ServiceInstance getServiceInstanceTopologyData(ServiceInstance serviceInstance)
			throws JmxConnectionException {
		LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RETRIVING_TOPOLOGY_DATA, serviceInstance.getName(),
				serviceInstance.getHost().getApplication().getName()));
		String password = serviceInstance.getJmxPassword();
		String username = serviceInstance.getJmxUserName();
		String decodedPassword = ManagementUtil.getDecodedPwd(password);
		MBeanServerConnection mBeanServerConnection = null;
		try {
			mBeanServerConnection = getMbeanConnection(serviceInstance.getHost().getHostIp(),
					serviceInstance.getJmxPort(), serviceInstance.getKey(), username, decodedPassword);
		} catch (JMXConnClientException e) {
			throw new JmxConnectionException(e.getMessage(), e);
		}
		ServiceInstance instance = null;
		try {

			ObjectName mgmtMBeanObjName = ObjectName.getInstance(ManagementTableMBeanImpl.OBJ_NAME_MNGMT_TABLE);
			if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, mgmtMBeanObjName)) {
				if (!serviceInstance.getHost().getApplication().isMonitorableOnly())
					instance = populateInstanceData(mBeanServerConnection, mgmtMBeanObjName);

				else
					instance = populateMonitorableInstanceData(mBeanServerConnection, mgmtMBeanObjName);
			}
		} catch (Exception ex) {
			if (ex instanceof ObjectCreationException) {
				throw new JmxConnectionException(messageService.getMessage(MessageKey.UNABLE_CONNECT_INSTANCE,
						serviceInstance.getName(), String.valueOf(serviceInstance.getJmxPort())), ex);
			} else {
				throw new JmxConnectionException(
						messageService.getMessage(MessageKey.UNABLE_CONNECT_INSTANCE, serviceInstance.getName(),
								String.valueOf(serviceInstance.getJmxPort())) + " " + ex.getMessage(),
						ex);
			}
		}
		return instance;
	}

	private AgentType getAgentType(String agentType) {
		switch (agentType) {
		case "INFERENCE":
			return AgentType.INFERENCE;
		case "CACHESERVER":
			return AgentType.CACHE;
		case "QUERY":
			return AgentType.QUERY;
		case "PROCESS":
			return AgentType.PROCESS;
		case "DASHBOARD":
			return AgentType.DASHBOARD;
		}
		return null;
	}

	/**
	 * Load and deploy classes
	 * 
	 * @param vrfURI
	 *            - Virtual Rule Function Name
	 * @param implName
	 *            - Virtual Rule Function implementation name
	 * @param username
	 *            - JMX User Name
	 * @param password
	 *            - JMX encoded password
	 * @param jmxPort
	 *            - JMX Port
	 * @param hostIp
	 *            - Host IP address
	 * @param instanceKey
	 *            - Instance key
	 * @param clusterName
	 * @throws MBeanOperationFailException
	 *             -Exception is throws if MBean operation fails.
	 */
	private void loadAndDeploy(String vrfURI, String implName, String username, String password, int jmxPort,
			String hostIp, String instanceKey, String clusterName) throws MBeanOperationFailException {

		// Decode password
		String decodedPassword = ManagementUtil.getDecodedPwd(password);

		try {
			// Get Connection and invoke appropriate method
			MBeanServerConnection mBeanServerConnection = getMbeanConnection(hostIp, jmxPort, instanceKey, username,
					decodedPassword);
			if (null != mBeanServerConnection) {
				ObjectName objectName = ObjectName.getInstance(MessageFormat.format(AS_CLUSTER_MBEAN, clusterName));
				if (isRegistered(mBeanServerConnection, objectName)) {
					ASClusterMBean asClusterMBean = JMX.newMXBeanProxy(mBeanServerConnection, objectName,
							ASClusterMBean.class);
					if (null != asClusterMBean) {
						if (null != vrfURI && !vrfURI.trim().isEmpty() && null != implName
								&& !implName.trim().isEmpty()) {
							LOGGER.log(Level.DEBUG, "Invoked ASClusterMBean.loadAndDeploy(vrfURI,implName) method");
							asClusterMBean.loadAndDeploy(vrfURI, implName);
						} else {
							LOGGER.log(Level.DEBUG, "Invoked ASClusterMBean.loadAndDeploy() method");
							asClusterMBean.loadAndDeploy();
						}
					}
				}
			}
		} catch (JMXConnClientException | MalformedObjectNameException | IOException e) {
			throw new MBeanOperationFailException(e);
		}
	}

	/**
	 * Build Thread dump
	 * 
	 * @param threadMXBean
	 *            -Thread Mbean
	 */
	private String buildThreadDump(ThreadMXBean threadMXBean) {
		ThreadInfo[] threadsInfo = threadMXBean.dumpAllThreads(true, true);
		StringBuilder builder = new StringBuilder();
		if (null != threadsInfo && threadsInfo.length > 0) {
			for (ThreadInfo threadInfo : threadsInfo) {
				builder.append(threadInfo);
			}

		}
		return builder.toString();
	}

	/**
	 * Load and deploy rule templates
	 * 
	 * @param agentName
	 *            - Name of the agent(required)
	 * @param projectName
	 *            - Project name
	 * @param ruleTemplateInstanceFQN
	 *            - Rule template instance FQN
	 * @param username
	 *            - JMX User Name
	 * @param password
	 *            - JMX encoded password
	 * @param jmxPort
	 *            - JMX Port
	 * @param hostIp
	 *            - Host IP address
	 * @param ipAddress
	 *            - Instance key
	 * @throws MBeanOperationFailException
	 *             - Exception is throws if MBean operation fails.
	 */
	private void loadAndDeployRuleTemplates(String agentName, String projectName, String ruleTemplateInstanceFQN,
			String username, String password, int jmxPort, String ipAddress, String instanceKey)
			throws MBeanOperationFailException {

		// Decode JMX password
		String decodedPassword = ManagementUtil.getDecodedPwd(password);

		try {
			// Get Mbean Connection
			MBeanServerConnection mBeanServerConnection = getMbeanConnection(ipAddress, jmxPort, instanceKey, username,
					decodedPassword);

			if (null != mBeanServerConnection) {
				ObjectName objectName = ObjectName.getInstance(RULE_TEMPLATE_DEPLOYER_MBEAN);

				// Check mbean is registered or not
				if (isRegistered(mBeanServerConnection, objectName)) {

					RuleTemplateDeployerMBean templateDeployerMBean = JMX.newMBeanProxy(mBeanServerConnection,
							objectName, RuleTemplateDeployerMBean.class);
					if (null != templateDeployerMBean) {

						// Invoke MBean method as per parameter
						if (null != agentName && !agentName.trim().isEmpty() && null != projectName
								&& !projectName.trim().isEmpty() && null != ruleTemplateInstanceFQN
								&& !ruleTemplateInstanceFQN.trim().isEmpty()) {
							LOGGER.log(Level.DEBUG,
									"Invoked RuleTemplateDeployerMBean.loadAndDeployRuleTemplateInstance(agentName, projectName,ruleTemplateInstanceFQN) method");
							templateDeployerMBean.loadAndDeployRuleTemplateInstance(agentName, projectName,
									ruleTemplateInstanceFQN);
						} else {
							LOGGER.log(Level.DEBUG,
									"Invoked RuleTemplateDeployerMBean.loadAndDeployRuleTemplateInstance(agentName) method");
							templateDeployerMBean.loadAndDeployRuleTemplateInstances(agentName);
						}
					}
				}
			}
		} catch (JMXConnClientException | MalformedObjectNameException | IOException e) {
			throw new MBeanOperationFailException(e);
		}
	}

	/**
	 * Remove invalid arguments
	 * 
	 * @param args
	 *            - List of arguments
	 */
	private void removeInvalidArgs(List<Arg> args) {
		Iterator<Arg> itr = args.iterator();
		while (itr.hasNext()) {
			Arg arg = (Arg) itr.next();
			if (null != arg && (null == arg.getName() || arg.getName().isEmpty())) {
				itr.remove();
			}
		}
	}

	/**
	 * Get value from string value for given type
	 * 
	 * @param paramValue
	 *            - Parameter value
	 * @param paramType
	 *            - Parameter type
	 * @return Value of given type
	 */
	private Object getValue(String paramValue, String paramType) {
		paramType = paramType.toLowerCase();
		Object value = null;
		if (paramType.equals("string"))
			value = paramValue;
		else if (paramType.equals("boolean"))
			value = Boolean.parseBoolean(paramValue);
		else if (paramType.equals("integer") || paramType.equals("int"))
			value = Integer.parseInt(paramValue);
		else if (paramType.equals("char"))
			value = paramValue.toCharArray()[0];
		else if (paramType.equals("long"))
			value = Long.parseLong(paramValue);
		else if (paramType.equals("short"))
			value = Short.parseShort(paramValue);
		else if (paramType.equals("byte"))
			value = Byte.parseByte(paramValue);
		else if (paramType.equals("double"))
			value = Double.parseDouble(paramValue);
		else if (paramType.equals("float"))
			value = Float.parseFloat(paramValue);
		return value;
	}

	/**
	 * convert the String with the type description parsed from the XML file, to
	 * a String with the corresponding Java type
	 * 
	 * @param type
	 *            Type of parameter
	 * @return Return Java Type
	 */
	private String getJavaType(String type) {
		type = type.toLowerCase();

		if (type.equals("string"))
			type = String.class.getName();
		else if (type.equals("boolean"))
			type = Boolean.TYPE.getName();
		else if (type.equals("integer") || type.equals("int"))
			type = Integer.TYPE.getName();
		else if (type.equals("char"))
			type = Character.TYPE.getName();
		else if (type.equals("long"))
			type = Long.TYPE.getName();
		else if (type.equals("short"))
			type = Short.TYPE.getName();
		else if (type.equals("byte"))
			type = Byte.TYPE.getName();
		else if (type.equals("double"))
			type = Double.TYPE.getName();
		else if (type.equals("float"))
			type = Float.TYPE.getName();
		else if (type.equals("void"))
			type = Void.TYPE.getName();
		return type;
	} // getJavaType

	/**
	 * Check Mbean is registered or nor
	 * 
	 * @param mBeanServerConnection
	 *            - Mbean server connection
	 * @param name
	 *            - Name of Mbean
	 * @return True/false
	 * @throws IOException
	 */
	private boolean isRegistered(MBeanServerConnection mBeanServerConnection, ObjectName name) throws IOException {
		if (mBeanServerConnection.isRegistered(name))
			return true;
		else {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.NOT_REGISTERED, name.getCanonicalName()));
			return false;
		}
	}

	@Override
	public synchronized void removeJMXConnection(String key) {
		jmxConnectionMap.remove(key);
	}

	/**
	 * Notification Listener for JMX Connection
	 * 
	 * @author dijadhav
	 *
	 */
	public class JMXConnectorNotificationListener implements NotificationListener {
		private String instanceKey;

		public JMXConnectorNotificationListener(String instanceKey) {
			this.instanceKey = instanceKey;
		}

		@Override
		public void handleNotification(Notification notification, Object handback) {
			if (notification.getSource() instanceof JMXConnector)
				if ("jmx.remote.connection.closed".equalsIgnoreCase(notification.getType())) {
					LOGGER.log(Level.DEBUG, "JMX connection closed for URL=[%s]",
							handback == null ? "" : handback.toString());
					closeJMXConnection(instanceKey);
				}
		}

	}

	/**
	 * Close the JMX Connection
	 * 
	 * @param key
	 *            - Key whose JMX Connector need to close
	 */
	private void closeJMXConnection(String key) {
		JMXConnector connector = jmxConnectionMap.remove(key);
		if (null != connector) {
			try {
				connector.close();
			} catch (IOException e1) {
			}
		}
	}

	@Override
	public void stopInstance(ServiceInstance serviceInstance) throws MBeanOperationFailException {

		if (null != serviceInstance) {

			Host host = serviceInstance.getHost();
			if (null != host) {
				String password = serviceInstance.getJmxPassword();
				String username = serviceInstance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// Get MBean connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(host.getHostIp(),
							serviceInstance.getJmxPort(), serviceInstance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementTableMBeanImpl.OBJ_NAME_MNGMT_TABLE);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {
						ManagementTableMBean proxy = JMX.newMBeanProxy(mBeanServerConnection, objectName,
								ManagementTableMBean.class);
						proxy.stopInstance();
					}
				} catch (JMXConnClientException | IOException | MalformedObjectNameException ex) {
					throw new MBeanOperationFailException(ex);
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl#stop()
	 */
	@Override
	synchronized public void stop() throws Exception {
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.STOPPING_MBEANSERVICE));
		for (Entry<String, JMXConnector> entry : jmxConnectionMap.entrySet()) {
			if (null != entry) {
				JMXConnector connector = entry.getValue();
				if (null != connector) {
					connector.close();
				}
			}
		}
		LOGGER.log(Level.INFO, messageService.getMessage(MessageKey.STOPPED_MBEANSERVICE));
	}

	@Override
	public OperationResult getBeAgentGarbageCollectionDetails(BeTeaAgentMonitorable instance)
			throws MBeanOperationFailException {
		OperationResult operationreturndata = null;
		if (null != instance && BETeaAgentStatus.RUNNING.getStatus().equalsIgnoreCase(instance.getStatus())) {

			if (null != instance.getHostIp()) {
				String username = instance.getJmxUserName();
				String password = instance.getJmxPassword();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// GET connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(instance.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementFactory.RUNTIME_MXBEAN_NAME);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {
						try {
							RuntimeMXBean runTimeMXBean = ManagementFactory.newPlatformMXBeanProxy(
									mBeanServerConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
							ObjectName gcName = ObjectName
									.getInstance(ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
							Set<ObjectName> mbeanNames = mBeanServerConnection.queryNames(gcName, null);
							List<GarbageCollectorMXBean> gcMBeans = new ArrayList<GarbageCollectorMXBean>();
							if (mbeanNames != null) {

								Iterator<ObjectName> iterator = mbeanNames.iterator();
								while (iterator.hasNext()) {
									ObjectName objName = (ObjectName) iterator.next();
									GarbageCollectorMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(
											mBeanServerConnection, objName.getCanonicalName(),
											GarbageCollectorMXBean.class);
									gcMBeans.add(proxy);
								}
							}

							if (runTimeMXBean == null || gcMBeans == null || gcMBeans.isEmpty() == true) {
								return null;
							}
							operationreturndata = new OperationResult();
							Headers headers = new Headers();
							String[] headerNames = new String[] { "Pool Name", "UpTime", "Pool Collection Count",
									"Pool Collection Time" };
							for (String headerName : headerNames) {
								Header header = new Header();
								header.setValue(headerName);
								headers.getHeader().add(header);
							}
							operationreturndata.setHeaders(headers);
							Rows rows = new Rows();
							for (GarbageCollectorMXBean gcMBean : gcMBeans) {
								Row row = new Row();
								Columns columns = new Columns();

								Column column = new Column();
								column.setValue(gcMBean.getName());
								columns.getColumn().add(column);

								long uptime = runTimeMXBean.getUptime();
								column = new Column();
								column.setValue(ManagementUtil.formatTime(uptime));
								columns.getColumn().add(column);

								column = new Column();
								column.setValue(String.valueOf(gcMBean.getCollectionCount()));
								columns.getColumn().add(column);

								column = new Column();
								column.setValue(ManagementUtil.formatTime(gcMBean.getCollectionTime()));
								columns.getColumn().add(column);

								row.setColumns(columns);
								rows.getRow().add(row);
							}
							operationreturndata.setRows(rows);
						} catch (MalformedObjectNameException e) {
							throw new RuntimeException(messageService
									.getMessage(MessageKey.QUERY_GARBAGE_COLLECTION_POOL_NAMES_ERROR_MESSAGE), e);
						}
					}
				} catch (Exception e) {
					String msg;
					if ((msg = ManagementUtil.getClassExceptionMsg(e, BEMMUserActivityException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e, BEMMEntityNotFoundException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e, BEMMInvalidAccessException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e,
									BEMMInvalidUserRoleException.class)) != null
							|| (msg = ManagementUtil.getClassExceptionMsg(e, JMXConnClientException.class)) != null) {
						throw new MBeanOperationFailException(msg);

					} else if ((msg = ManagementUtil.getClassExceptionMsg(e,
							BEMMIllegalSetupException.class)) != null) {
						throw new MBeanOperationFailException(msg);
					} else {
						throw new MBeanOperationFailException(e.getMessage());
					}
				}

			}
		}
		return operationreturndata;
	}

	@Override
	public MemoryUsage getBeAgentMemoryByPoolName(String poolName, BeTeaAgentMonitorable instance)
			throws MBeanOperationFailException {
		com.tibco.cep.bemm.common.model.MemoryUsage memoryUsageDetails = null;
		MemoryPoolNames[] memoryPoolNames = MemoryPoolNames.values();
		String cannonicalName = null;
		for (MemoryPoolNames memoryPoolName : memoryPoolNames) {
			if (memoryPoolName.getName().equals(poolName)) {
				cannonicalName = memoryPoolName.getCannonicalName();
			}
		}
		if (null == cannonicalName) {
			throw new MBeanOperationFailException(messageService.getMessage(MessageKey.INVALID_POOL_NAME_MESSAGE));
		}

		if (null != instance && BETeaAgentStatus.RUNNING.getStatus().equalsIgnoreCase(instance.getStatus())) {

			if (null != instance.getHostIp()) {
				String password = instance.getJmxPassword();
				String username = instance.getJmxUserName();

				String decodedPassword = ManagementUtil.getDecodedPwd(password);
				try {
					// Get MBean Connection and invoke method
					MBeanServerConnection mBeanServerConnection = getMbeanConnection(instance.getHostIp(),
							instance.getJmxPort(), instance.getKey(), username, decodedPassword);
					ObjectName objectName = ObjectName.getInstance(ManagementFactory.MEMORY_MXBEAN_NAME);
					if (null != mBeanServerConnection && isRegistered(mBeanServerConnection, objectName)) {

						if (MemoryPoolNames.NON_HEAP_MEMORY_USAGE.getName().equals(poolName)) {
							MemoryMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
									ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
							java.lang.management.MemoryUsage memoryUsage = proxy.getNonHeapMemoryUsage();
							memoryUsageDetails = new MemoryUsageImpl(memoryUsage.getInit(), memoryUsage.getCommitted(),
									memoryUsage.getMax(), memoryUsage.getUsed());

						} else if (MemoryPoolNames.HEAP_MEMORY_USAGE.getName().equals(poolName)) {
							MemoryMXBean proxy = ManagementFactory.newPlatformMXBeanProxy(mBeanServerConnection,
									ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
							java.lang.management.MemoryUsage memoryUsage = proxy.getHeapMemoryUsage();
							memoryUsageDetails = new MemoryUsageImpl(memoryUsage.getInit(), memoryUsage.getCommitted(),
									memoryUsage.getMax(), memoryUsage.getUsed());

						} else {
							MemoryPoolMXBean memoryPoolMXBean = ManagementFactory.newPlatformMXBeanProxy(
									mBeanServerConnection, cannonicalName, MemoryPoolMXBean.class);
							java.lang.management.MemoryUsage memoryUsage = memoryPoolMXBean.getUsage();
							memoryUsageDetails = new MemoryUsageImpl(memoryUsage.getInit(), memoryUsage.getCommitted(),
									memoryUsage.getMax(), memoryUsage.getUsed());
						}
					}
				} catch (JMXConnClientException | IOException | MalformedObjectNameException ex) {
					LOGGER.log(Level.DEBUG, ex.getMessage());
				}
			}
		}
		return memoryUsageDetails;
	}

	@Override
	public List<String> getBeAgentMemoryPools() throws MBeanOperationFailException {
		BeAgentMemoryPoolNames[] memoryPoolNames = BeAgentMemoryPoolNames.values();
		List<String> memoryPools = new ArrayList<String>();
		for (BeAgentMemoryPoolNames POOL_NAME : memoryPoolNames) {
			memoryPools.add(POOL_NAME.getName());
		}
		return memoryPools;
	}

	private ServiceInstance populateMonitorableInstanceData(MBeanServerConnection mBeanServerConnection,
			ObjectName mgmtMBeanObjName) throws ObjectCreationException, AttributeNotFoundException,
			InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		ServiceInstance instance = null;
		@SuppressWarnings("unchecked")
		Map<String, Object> instanceInfo = (Map<String, Object>) mBeanServerConnection.getAttribute(mgmtMBeanObjName,
				"InstanceDetails");

		Application application = BEMMModelFactoryImpl.getInstance().getApplication();
		application.setClusterName((String) instanceInfo.get("CLUSTER_NAME"));

		Host host = BEMMModelFactoryImpl.getInstance().getHost();
		host.setApplication(application);
		application.addHost(host);

		instance = BEMMModelFactoryImpl.getInstance().getServiceInstance();
		instance.setName((String) instanceInfo.get("INSTANCE_NAME"));
		instance.setProcessId((String) instanceInfo.get("HOST_PROCESS_ID"));

		instance.getMemoryUsage().setPercentUsed((Double) instanceInfo.get("INSTANCE_PERCENT_USED_MEMORY"));
		instance.getCpuUsage().setCpuUsageInPercent((Double) instanceInfo.get("INSTANCE_CPU_USAGE"));
		instance.setPuId((String) instanceInfo.get("PROCESSING_UNIT_ID"));

		Object status = instanceInfo.get("STATUS");
		if (null != status) {
			instance.setEngineStatus(Byte.valueOf(status.toString()).byteValue());
		} else {
			instance.setEngineStatus(RuleServiceProviderImpl.STATE_RUNNING);
		}
		instance.setHost(host);
		host.addInstance(instance);
		ProcessingUnit processingUnit = BEMMModelFactoryImpl.getInstance().getProcessingUnit();
		processingUnit.setPuId(instance.getPuId());
		populateGlobalVariables(mBeanServerConnection, mgmtMBeanObjName, instance, application);
		populateSystemProperties(mBeanServerConnection, mgmtMBeanObjName, instance, application);
		populateBEProperties(mBeanServerConnection, mgmtMBeanObjName, instance, application);
		populateJVMProperties(mBeanServerConnection, mgmtMBeanObjName, instance, application);
		@SuppressWarnings("unchecked")
		Map<String, String> instanceAgents = (Map<String, String>) mBeanServerConnection.getAttribute(mgmtMBeanObjName,
				"InstanceAgentsDetails");
		Set<Entry<String, String>> instanceAgentsSet = instanceAgents.entrySet();
		for (Entry<String, String> instanceAgent : instanceAgentsSet) {
			Agent agent = BEMMModelFactoryImpl.getInstance().getAgent();
			AgentConfig agentConfig = BEMMModelFactoryImpl.getInstance().getAgentConfig();
			agent.setAgentName((String) instanceAgent.getKey());
			agentConfig.setAgentName((String) instanceAgent.getKey());
			try {
				String agentIdAndType = (String) instanceAgent.getValue();
				agentConfig.setAgentType(getAgentType(agentIdAndType.split(":")[1]));
				agent.setAgentId(Integer.parseInt(agentIdAndType.split(":")[0]));
				agent.setAgentType(getAgentType(agentIdAndType.split(":")[1]));

			} catch (NumberFormatException nfex) {
				// Do nothing
			}
			agent.setInstance(instance);
			instance.addAgent(agent);
			processingUnit.addAgent(agentConfig);
		}
		application.addProcessingUnit(processingUnit);
		return instance;
	}

	private ServiceInstance populateInstanceData(MBeanServerConnection mBeanServerConnection,
			ObjectName mgmtMBeanObjName) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException,
			ReflectionException, IOException, ObjectCreationException {
		ServiceInstance instance = null;
		@SuppressWarnings("unchecked")
		Map<String, Object> instanceInfo = (Map<String, Object>) mBeanServerConnection.getAttribute(mgmtMBeanObjName,
				"InstanceInfo");

		Application application = BEMMModelFactoryImpl.getInstance().getApplication();
		application.setClusterName((String) instanceInfo.get("CLUSTER_NAME"));

		Host host = BEMMModelFactoryImpl.getInstance().getHost();
		host.setApplication(application);
		application.addHost(host);

		instance = BEMMModelFactoryImpl.getInstance().getServiceInstance();
		instance.setName((String) instanceInfo.get("INSTANCE_NAME"));
		instance.setProcessId((String) instanceInfo.get("HOST_PROCESS_ID"));
		instance.getMemoryUsage().setPercentUsed((Double) instanceInfo.get("INSTANCE_PERCENT_USED_MEMORY"));
		instance.getCpuUsage().setCpuUsageInPercent((Double) instanceInfo.get("INSTANCE_CPU_USAGE"));
		Object status = instanceInfo.get("STATUS");
		if (null != status) {
			instance.setEngineStatus(Byte.valueOf(status.toString()).byteValue());
		} else {
			instance.setEngineStatus(RuleServiceProviderImpl.STATE_RUNNING);
		}
		instance.setHost(host);
		host.addInstance(instance);

		@SuppressWarnings("unchecked")
		Map<String, String> instanceAgents = (Map<String, String>) mBeanServerConnection.getAttribute(mgmtMBeanObjName,
				"InstanceAgentsInfo");
		Set<Entry<String, String>> instanceAgentsSet = instanceAgents.entrySet();
		for (Entry<String, String> instanceAgent : instanceAgentsSet) {
			Agent agent = BEMMModelFactoryImpl.getInstance().getAgent();
			agent.setAgentName(instanceAgent.getKey());
			try {
				agent.setAgentId(Integer.parseInt(instanceAgent.getValue()));
			} catch (NumberFormatException nfex) {
				// Do nothing
			}
			instance.addAgent(agent);
		}
		return instance;
	}

	private void populateGlobalVariables(MBeanServerConnection mBeanServerConnection, ObjectName mgmtMBeanObjName,
			ServiceInstance instance, Application application) throws MBeanException, AttributeNotFoundException,
			InstanceNotFoundException, ReflectionException, IOException {
		Map<String, Object> globalVariables = (Map<String, Object>) mBeanServerConnection.getAttribute(mgmtMBeanObjName,
				"GlobalVariables");

		if (null != globalVariables && !globalVariables.isEmpty()) {
			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			DeploymentVariables deploymentVariables = deployVariablesObjectFactory.createDeploymentVariables();
			NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
			for (Object map : globalVariables.values()) {
				Map<String, Object> gv = (Map<String, Object>) map;
				if ((boolean) gv.get(GlobalVariableDescriptor.FIELD_ISSERVICE)) {
					NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
					String name = gv.get(GlobalVariableDescriptor.FIELD_NAME).toString()
							.replace(Constants.GLOBAL_VARIABL_PREFIX, "").trim();
					if (!excludedProps.contains(name.trim())) {
						nameValuePair.setName(name);
						nameValuePair.setValue(gv.get(GlobalVariableDescriptor.FIELD_VALUE).toString());
						nameValuePair.setType(gv.get(GlobalVariableDescriptor.FIELD_TYPE).toString());
						nameValuePair.setIsDeleted(false);
						nameValuePair.setHasDefaultValue(true);
						nameValues.add(nameValuePair);
					}
				}

			}
			nameValuePairs.setNameValuePair(nameValues);
			deploymentVariables.setNameValuePairs(nameValuePairs);
			deploymentVariables.setType(DeploymentVariableType.GLOBAL_VARIABLES);
			deploymentVariables.setName(application.getName());
			if (instance.getGlobalVariables() != null) {
				deploymentVariables.setVersion(instance.getGlobalVariables().getVersion());
			}
			deploymentVariables.setKey(instance.getKey() + "/" + DeploymentVariableType.GLOBAL_VARIABLES.name());
			instance.setGlobalVariables(deploymentVariables);
		}
	}

	private void populateSystemProperties(MBeanServerConnection mBeanServerConnection, ObjectName mgmtMBeanObjName,
			ServiceInstance instance, Application application) throws MBeanException, AttributeNotFoundException,
			InstanceNotFoundException, ReflectionException, IOException {
		Map<String, Object> systemProperties = (Map<String, Object>) mBeanServerConnection
				.getAttribute(mgmtMBeanObjName, "SystemProperties");

		if (null != systemProperties && !systemProperties.isEmpty()) {
			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			DeploymentVariables deploymentVariables = deployVariablesObjectFactory.createDeploymentVariables();
			NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
			for (Entry<String, Object> entry : systemProperties.entrySet()) {

				NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
				String name = entry.getKey().toString().replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim();
				if (!excludedProps.contains(name.trim()) || "be.engine.jmx.connector.port".equals(name.trim())) {
					nameValuePair.setName(name);
					nameValuePair.setValue(entry.getValue().toString());
					nameValuePair.setIsDeleted(false);
					nameValuePair.setHasDefaultValue(true);
					nameValues.add(nameValuePair);
				}

			}
			nameValuePairs.setNameValuePair(nameValues);
			deploymentVariables.setNameValuePairs(nameValuePairs);
			deploymentVariables.setType(DeploymentVariableType.SYSTEM_VARIABLES);
			deploymentVariables.setName(application.getName());
			if (instance.getSystemVariables() != null) {
				deploymentVariables.setVersion(instance.getBEProperties().getVersion());
			}
			deploymentVariables.setKey(instance.getKey() + "/" + DeploymentVariableType.SYSTEM_VARIABLES.name());
			instance.setSystemVariables(deploymentVariables);
		}
	}

	private void populateBEProperties(MBeanServerConnection mBeanServerConnection, ObjectName mgmtMBeanObjName,
			ServiceInstance instance, Application application) throws MBeanException, AttributeNotFoundException,
			InstanceNotFoundException, ReflectionException, IOException {
		Map<String, Object> systemProperties = (Map<String, Object>) mBeanServerConnection
				.getAttribute(mgmtMBeanObjName, "BEProperties");

		if (null != systemProperties && !systemProperties.isEmpty()) {
			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			DeploymentVariables deploymentVariables = deployVariablesObjectFactory.createDeploymentVariables();
			NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
			for (Entry<String, Object> entry : systemProperties.entrySet()) {

				NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
				String name = entry.getKey().toString().trim();
				if (!(name.startsWith(Constants.GLOBAL_VARIABL_PREFIX)
						|| name.startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
						|| name.startsWith(Constants.TIBCO_ENV_VARIABLE_PREFIX))
						&& !excludedProps.contains(name.replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {
					nameValuePair.setName(name);
					nameValuePair.setValue(null != entry.getValue() ? entry.getValue().toString() : "");
					nameValuePair.setIsDeleted(false);
					nameValuePair.setHasDefaultValue(true);
					nameValues.add(nameValuePair);
				}

			}
			nameValuePairs.setNameValuePair(nameValues);
			deploymentVariables.setNameValuePairs(nameValuePairs);
			deploymentVariables.setType(DeploymentVariableType.BE_PROPERTIES);
			deploymentVariables.setName(application.getName());
			if (instance.getBEProperties() != null) {
				deploymentVariables.setVersion(instance.getBEProperties().getVersion());
			}
			deploymentVariables.setKey(instance.getKey() + "/" + DeploymentVariableType.BE_PROPERTIES.name());
			instance.setBEProperties(deploymentVariables);
		}
	}
	private void populateJVMProperties(MBeanServerConnection mBeanServerConnection, ObjectName mgmtMBeanObjName,
			ServiceInstance instance, Application application) throws MBeanException, AttributeNotFoundException,
			InstanceNotFoundException, ReflectionException, IOException {
		Map<String, Object> jvmProperties = (Map<String, Object>) mBeanServerConnection
				.getAttribute(mgmtMBeanObjName, "JVMProperties");

		if (null != jvmProperties && !jvmProperties.isEmpty()) {
			ObjectFactory deployVariablesObjectFactory = new ObjectFactory();
			DeploymentVariables deploymentVariables = deployVariablesObjectFactory.createDeploymentVariables();
			NameValuePairs nameValuePairs = deployVariablesObjectFactory.createNameValuePairs();
			List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
			for (Entry<String, Object> entry : jvmProperties.entrySet()) {

				NameValuePair nameValuePair = deployVariablesObjectFactory.createNameValuePair();
				String name = entry.getKey().toString().trim();
				if (!(name.startsWith(Constants.GLOBAL_VARIABL_PREFIX)
						|| name.startsWith(Constants.SYSTEM_PROPERTY_PREFIX)
						|| name.startsWith(Constants.TIBCO_ENV_VARIABLE_PREFIX))
						&& !excludedProps.contains(name.replace(Constants.SYSTEM_PROPERTY_PREFIX, "").trim())) {
					nameValuePair.setDescription(name);
					nameValuePair.setValue(null != entry.getValue() ? entry.getValue().toString() : "");
					nameValuePair.setIsDeleted(false);
					nameValuePair.setHasDefaultValue(true);
					nameValues.add(nameValuePair);
				}

			}
			nameValuePairs.setNameValuePair(nameValues);
			deploymentVariables.setNameValuePairs(nameValuePairs);
			deploymentVariables.setType(DeploymentVariableType.JVM_PROPERTIES);
			deploymentVariables.setName(application.getName());
			if (instance.getJVMProperties() != null) {
				deploymentVariables.setVersion(instance.getJVMProperties().getVersion());
			}
			deploymentVariables.setKey(instance.getKey() + "/" + DeploymentVariableType.JVM_PROPERTIES.name());
			instance.setJVMProperties(deploymentVariables);
		}
	}

}
