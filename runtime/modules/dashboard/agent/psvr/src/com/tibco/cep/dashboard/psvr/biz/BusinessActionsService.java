package com.tibco.cep.dashboard.psvr.biz;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.jmx.AnnotationAwareStandardMBean;

public class BusinessActionsService extends Service {

	public BusinessActionsService() {
		super("bizactions", "Business Actions Service");
	}

	@Override
	protected void doInit() throws ManagementException {
		addDependent(BizSessionProvider.getInstance());
		addDependent(BusinessActionsController.getInstance());
	}

	@Override
    protected void registerMBeans(MBeanServer server, ObjectName baseName) {
		String registrationName = baseName.toString() + ",service=actions";
		try {
			ObjectName name = new ObjectName(registrationName);
			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(new BusinessActionsMXBeanImpl(), BusinessActionsMXBean.class, true);
			server.registerMBean(standardMBean, name);
		} catch (InstanceAlreadyExistsException e) {
			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,BusinessActionsMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MBeanRegistrationException e) {
			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,BusinessActionsMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (NotCompliantMBeanException e) {
			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,BusinessActionsMXBean.class));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
    }
}