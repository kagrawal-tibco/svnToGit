package com.tibco.cep.dashboard.psvr.vizengine;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGeneratorFactory;
import com.tibco.cep.dashboard.psvr.vizengine.formatters.DataFormatHandlerFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.jmx.AnnotationAwareStandardMBean;

/**
 * @author anpatil
 *
 */
public class VizEngineService extends Service {

	public VizEngineService() {
		super("vizengine", "Visualization Engine");
	}

	@Override
	protected void doInit() throws ManagementException {
		addDependent(ActionConfigGeneratorFactory.getInstance());
		addDependent(DataFormatHandlerFactory.getInstance());
		addDependent(VisualizationHandlerFactory.getInstance());
		addDependent(ComponentHandlerFactory.getInstance());
		addDependent(ComponentRendererFactory.getInstance());
		addDependent(LayoutConfigGeneratorFactory.getInstance());
		addDependent(VisualizationEngine.getInstance());
		addDependent(LinkGeneratorFactory.getInstance());
	}

	@Override
	protected void registerMBeans(MBeanServer server, ObjectName baseName) {
		String registrationName = baseName.toString() + ",service=vizengine";
		try {
			ObjectName name = new ObjectName(registrationName);
			AnnotationAwareStandardMBean standardMBean = new AnnotationAwareStandardMBean(new VizEngineMXBeanImpl(), VizEngineMXBean.class, true);
			server.registerMBean(standardMBean, name);
		} catch (InstanceAlreadyExistsException e) {
			String message = messageGenerator.getMessage("service.mbean.instancealreadyexisting.failure", new MessageGeneratorArgs(e,VizEngineMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MBeanRegistrationException e) {
			String message = messageGenerator.getMessage("service.mbean.registration.failure", new MessageGeneratorArgs(e,VizEngineMXBean.class.getName(),registrationName));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (NotCompliantMBeanException e) {
			String message = messageGenerator.getMessage("service.mbean.noncompliantbean.failure", new MessageGeneratorArgs(e,VizEngineMXBean.class));
			exceptionHandler.handleException(message, e, Level.WARN);
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
	}
}
