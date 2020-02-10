package com.tibco.be.bw.plugin;


import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.Filter;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.TimeManager;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.scheduler.impl.DefaultTaskController;
import com.tibco.cep.runtime.session.*;
import com.tibco.pe.plugin.EventSourceContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 15, 2006
 * Time: 1:23:32 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class is just a proxy class for RuleSession
 * An instance of this class is provided to the Channel.Destination during its bind process
 * The Destination uses the rulesession to assert a SimpleEvent. It might also uses
 * a.> RuleServiceProvider to construct entities
 * b.> Logger
 * c.> any other required feature
 */
public class BWSession implements RuleSession {

    RuleServiceProvider provider;
    EventSourceContext context;
    static XiFactory xiFactory = XiFactoryFactory.newInstance();
    ExpandedName outElementName;
    BWSessionConfig config = null;
    TaskController controller;
    private final ProcessingContextProvider processingContextProvider = new ProcessingContextProvider();


    public BWSession(RuleServiceProvider provider, EventSourceContext context, String outputName, Event filter, Channel.Destination dest) {
        this.provider = provider;
        this.context = context;
        outElementName = ExpandedName.makeName(BEPluginConstants.NAMESPACE, outputName);
        config = new BWSessionConfig(dest, filter);
        controller = new DefaultTaskController(this);

    }

    public RuleSessionConfig getConfig() {
        return config;
    }


    public void assertObject(Object object, boolean executeRules) throws DuplicateExtIdException {


        SimpleEvent event = (SimpleEvent)object;
        XiNode doc = xiFactory.createDocument();
        XiNode ele = doc.appendElement(outElementName);

        try {
            event.toXiNode(ele);
            BEEventContext ctx = new BEEventContext(event, doc);
            context.newEvent(doc, ctx);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Set<String> getDeployedRuleUris() {
        throw new AbstractMethodError("BWSession does not implement this method - getDeployedRuleUris");
    }

    public Set getInputDestinations() {
        throw new AbstractMethodError("BWSession does not implement this method - getInputDestinations");
    }

    public List getObjects(Filter filter) throws Exception {
        throw new AbstractMethodError("BWSession does not implement this method");
    }

    public boolean getActiveMode() {
        throw new AbstractMethodError("BWSession does not implement this method");
    }

    public Object invokeFunction(String functionURI, Map args, boolean synchronize) {
        throw new AbstractMethodError("BWSession does not implement this method - invokeFunction");
    }

    public Object invokeFunction(String functionURI, Object[] args, boolean synchronize) {
        throw new AbstractMethodError("BWSession does not implement this method - invokeFunction");
    }
    
    

    @Override
	public Object invokeCatalog(String functionFQName, Object... args) {
    	throw new AbstractMethodError("BWSession does not implement this method - invokeCatalog");
	}

	public void executeRules() {
        throw new AbstractMethodError("BWSession does not implement this method - executeRules");
    }


    public String getName() {
        return context.getProcessModelName();
    }

    public ObjectManager getObjectManager() {
        throw new AbstractMethodError("BWSession does not implement this method");
    }

    public List getObjects() throws Exception {
        throw new AbstractMethodError("BWSession does not implement this method");
    }

    public RuleRuntime getRuleRuntime() {
        return provider.getRuleRuntime();
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return provider;
    }

    public TimeManager getTimeManager() {
        throw new AbstractMethodError("BWSession does not implement this method");
    }

    /*
    public void modifyObject(Object modifedObject, boolean executeRules) {
        throw new AbstractMethodError("BWSession does not implement this method");
    }   */

    public void reset() throws Exception {
        throw new AbstractMethodError("BWSession does not implement this method");
    }

    public void retractObject(Object object, boolean executeRules) {

    }

    public void init() throws Exception {

    }

    public void stopAndShutdown() {

    }

    public void start(boolean activeMode) throws Exception {

    }


    public void stop()  {

    }

    public void setActiveMode(boolean active) {
        provider.getRuleRuntime().setActiveMode(active);
    }

    public TaskController getTaskController() {
        return controller;
    }


    @Override
    public ProcessingContextProvider getProcessingContextProvider() {
        return this.processingContextProvider;
    }


    class BWSessionConfig implements RuleSessionConfig {

        InputDestinationConfig dcs[] = null;
        BWSessionConfig(Channel.Destination dest, Event filter) {
            dcs = new InputDestinationConfig[] {new BWInputDestinationConfig(dest.getURI(), filter)};
        }

        public String getName() {
            return context.getEngineContext().getEngineName() + "." + context.getProcessModelName();
        }

        public Set<String> getDeployedRuleUris() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public InputDestinationConfig[] getInputDestinations() {
            return dcs;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Properties getCacheConfig() {
            return new Properties();  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Class getStartupClass() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Class getShutdownClass() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean deleteStateTimeoutOnStateExit() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public boolean removeRefOnDeleteNonRepeatingTimeEvent() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
        
        public boolean allowEventModificationInRTC() {
        	return false;
        }
    }

    class BWInputDestinationConfig implements RuleSessionConfig.InputDestinationConfig {

        String destURI;
        Event filter;
        BWInputDestinationConfig(String destURI, Event filter) {
            this.destURI = destURI;
            this.filter = filter;
        }

        public String getURI() {
            return destURI;
        }

        public Event getFilter() {
            return filter;
        }

        public RuleFunction getPreprocessor() {
            return null;
        }

        public int getNumWorker() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public int getQueueSize() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public int getWeight() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        /**
         * @return
         */
        //TODO Not sure if this is default
        public RuleSessionConfig.ThreadingModel getThreadingModel() {
            return RuleSessionConfig.ThreadingModel.WORKERS;
        }

		@Override
		public RuleFunction getThreadAffinityRuleFunction() {
			return null;
		}
        
        

    }

	@Override
	public RuleSessionMetrics getRuleSessionMetrics() {
		return null;
	}
	
	@Override
    public String getLogComponent() {
        return getName();
    }
}
