package com.tibco.cep.dashboard.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ManagementConfigurator.MODE;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public final class ManagementService extends Service {

	private static final String SERVICES_REG_LOCATION = "services.registry";

	private static ManagementService instance;

	static final synchronized ManagementService getInstance() {
		if (instance == null) {
			instance = new ManagementService();
		}
		return instance;
	}

	private ManagementService() {
		super("management","Presentation Server Management Service");
	}

	@Override
	public void init(Logger parentLogger, MODE mode, Properties properties, ServiceContext serviceContext) throws ManagementException {
		//INFO forcing ManagementService's logger to be the root logger
		this.logger = LoggingService.getRootLogger();
		super.init(parentLogger, mode, properties, serviceContext);
	}

	@Override
	protected void doInit() throws ManagementException {
		if (serviceContext.getRuleSession() == null){
			throw new ManagementException("session cannot be null");
		}
		//get all the services based on mode
		List<Service> servicesRegistry = loadServicesRegistry(mode.toString());
		//add all the services as child services
		for (Service service : servicesRegistry) {
			addChildService(service);
		}
	}

	private List<Service> loadServicesRegistry(String mode) throws ManagementException{
        logger.log(Level.INFO,"Reading services registry...");
        ServicesRegistry registry = new ServicesRegistry(logger, SERVICES_REG_LOCATION);
        List<String> startupSequence = registry.getServices(mode);
        ArrayList<Service> startUpServicesList = new ArrayList<Service>(startupSequence.size());
        for (String serviceClassName : startupSequence) {
            try {
                Class<? extends Service> controllableServiceClass = Class.forName(serviceClassName).asSubclass(Service.class);
                Service lifeCycleControllableInstance = (Service) controllableServiceClass.newInstance();
                startUpServicesList.add(lifeCycleControllableInstance);
            } catch (ClassNotFoundException ex) {
            	logger.log(Level.INFO, "could not find "+serviceClassName, ex);
                throw new ManagementException("could not find "+serviceClassName);
            } catch (InstantiationException e) {
            	logger.log(Level.INFO, "could not instantiate "+serviceClassName, e);
                throw new ManagementException("could not instantiate "+serviceClassName);
            } catch (IllegalAccessException e) {
            	logger.log(Level.INFO, "could not access "+serviceClassName, e);
                throw new ManagementException("could not access "+serviceClassName);
            }
		}
        logger.log(Level.INFO,"Read "+startUpServicesList.size()+" services from the services registry...");
        return startUpServicesList;
	}

	public String getInstanceName(){
		return serviceContext.getRuleServiceProvider().getName();
	}

	public Properties getProperties(){
		return properties;
	}

	@Override
	public void setBeanServer(MBeanServer beanServer) {
		super.setBeanServer(beanServer);
	}

	@Override
	public void setBaseName(ObjectName baseName) {
		super.setBaseName(baseName);
	}

//	@Override
//	protected void registerMBeans(MBeanServer server, ObjectName baseName) {
//		String registrationName = baseName.toString() + ",service=lifecycle";
//		try {
//			ObjectName name = new ObjectName(registrationName);
//			ManangementMXBeanImpl impl = new ManangementMXBeanImpl(serviceContext.getRuleSession());
//			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(impl, ManagementMXBean.class, true);
//			server.registerMBean(standardMBean, name);
//		} catch (InstanceAlreadyExistsException e) {
//			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,SecurityMXBean.class.getName(),registrationName));
//			exceptionHandler.handleException(message, e, Level.WARN);
//		} catch (MBeanRegistrationException e) {
//			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,SecurityMXBean.class.getName(),registrationName));
//			exceptionHandler.handleException(message, e, Level.WARN);
//		} catch (NotCompliantMBeanException e) {
//			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,SecurityMXBean.class));
//			exceptionHandler.handleException(message, e, Level.WARN);
//		} catch (MalformedObjectNameException e) {
//			throw new RuntimeException(e);
//		} catch (NullPointerException e) {
//			throw new RuntimeException(e);
//		}
//	}

}