package com.tibco.cep.query.stream.impl;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.query.stream.cache.CacheBuilder;
import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.core.ContextRepository;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.cache.SimpleCacheBuilder;
import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.monitor.jmx.JMXHook;
import com.tibco.cep.query.stream.impl.query.CQScheduler;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.util.DelegatedLogger;
import com.tibco.cep.query.stream.monitor.CustomMultiSourceException;
import com.tibco.cep.query.stream.monitor.Flags;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.monitor.ThreadCentral;

/*
 * Author: Ashwin Jayaprakash Date: Oct 15, 2007 Time: 11:10:32 AM
 */

public class Master {
    public Master() {
        if (Flags.DEV_MODE) {
            printWarning(System.err);
            printWarning(System.out);
        }
    }

    private static void printWarning(PrintStream printStream) {
        printStream.println();
        printStream.println("+---------------------------------------------+");
        printStream.println("| Warning: Query running in DEVELOPMENT mode! |");
        printStream.println("+---------------------------------------------+");
        printStream.println();
    }

    public void start(Properties properties) throws Exception {
        Registry registry = Registry.getInstance();

        registerComponents(registry);

        initAndStartComponents(properties, registry);
    }

    protected void registerComponents(Registry registry) {
        Logger logger = new DelegatedLogger();
        registry.register(Logger.class, logger);

        ThreadCentral threadCentral = new ThreadCentral();
        registry.register(ThreadCentral.class, threadCentral);

        ContextRepository contextRepository = new ContextRepository();
        registry.register(ContextRepository.class, contextRepository);

        CacheBuilder cacheBuilder = new SimpleCacheBuilder();
        registry.register(CacheBuilder.class, cacheBuilder);

        Manager manager = new Manager();
        registry.register(Manager.class, manager);

        QueryMonitor queryMonitor = new QueryMonitor();
        registry.register(QueryMonitor.class, queryMonitor);

        JMXHook jmxHook = new JMXHook();
        registry.register(JMXHook.class, jmxHook);

        CQScheduler cqScheduler = new CQScheduler();
        registry.register(CQScheduler.class, cqScheduler);
    }

    /**
     * Attempts to start the registered components. If any error occurs, all the components are stopped and
     * unregistered.
     *
     * @param properties
     * @param registry
     * @throws Exception
     */
    protected void initAndStartComponents(Properties properties, Registry registry)
            throws Exception {
        //Make a snapshot first.
        List<Component> copy = new ArrayList<Component>(registry.getComponents());

        // --------

        try {
            for (Component component : copy) {
                component.init(properties);
            }
        }
        catch (Throwable t) {
            doStopAndUnregister(false);

            if (t instanceof Exception) {
                throw (Exception) t;
            }

            throw new Exception(t);
        }

        // --------

        try {
            for (Component component : copy) {
                component.start();
            }
        }
        catch (Throwable t) {
            doStopAndUnregister(false);

            if (t instanceof Exception) {
                throw (Exception) t;
            }

            throw new Exception(t);
        }
    }

    /**
     * <p>{@link com.tibco.cep.query.stream.core.Component#stop() Stops} the components and {@link
     * com.tibco.cep.query.stream.core.Registry#unregister(Class) unregisters} if the stop succeeded.</p>
     *
     * @throws CustomMultiSourceException The unregistration is completed even if errors occur. If any errors occur,
     *                                    they are recorded and thrown finally as one single exception.
     */
    public void stop() throws CustomMultiSourceException {
        doStopAndUnregister(true);
    }

    /**
     * @param logErrors
     * @throws CustomMultiSourceException Thrown only if "logErrors" is true. Otherwise they are swallowed.
     */
    protected void doStopAndUnregister(boolean logErrors) throws CustomMultiSourceException {
        Registry registry = Registry.getInstance();
        //Make a snapshot first.
        List<Component> copy = new ArrayList<Component>(registry.getComponents());
        Collections.reverse(copy);

        CustomMultiSourceException errorRecorder = logErrors ?
                new CustomMultiSourceException(new ResourceId(Master.class.getName())) : null;

        for (Component component : copy) {
            String id = component.getResourceId().generateSequenceToParentString();

            try {
                component.stop();
            }
            catch (Throwable t) {
                if (logErrors) {
                    String msg = "Error occurred while stopping component: " + id;

                    errorRecorder.addSource(new CustomMultiSourceException.Source(msg, t));
                }
            }

            try {
                component.discard();
            }
            catch (Throwable t) {
                if (logErrors) {
                    String msg = "Error occurred while shutting down component: " + id;

                    errorRecorder.addSource(new CustomMultiSourceException.Source(msg, t));
                }
            }

            registry.unregister(component.getClass());
        }

        if (logErrors && errorRecorder.hasSources()) {
            throw errorRecorder;
        }
    }
}
