package com.tibco.cep.runtime.service;

import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.service.basic.DependencyWatcher;
import com.tibco.cep.runtime.service.basic.JVMHeapWatcher;
import com.tibco.cep.runtime.service.basic.ShutdownWatcher;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* Author: Ashwin Jayaprakash Date: Oct 20, 2008 Time: 11:45:41 AM
*/
public class ServiceRegistry {
    static final ServiceRegistry singletonServiceRegistry = new ServiceRegistry();

    protected DependencyWatcher dependencyWatcher;

    protected JVMHeapWatcher heapWatcher;

    protected ShutdownWatcher shutdownWatcher;

    protected AsyncWorkerServiceWatcher workManagerWatcher;

    protected Configuration configuration;

    protected LinkedHashMap<Class<? extends Service>, Service> secondaryServices;

    protected final AtomicBoolean alreadyInited;

    protected final AtomicBoolean basicAlreadyInited;

    protected final AtomicBoolean bStopped;

    public ServiceRegistry() {
        this.alreadyInited = new AtomicBoolean(false);
        this.basicAlreadyInited = new AtomicBoolean(false);
        this.bStopped = new AtomicBoolean(false);
    }

    /**
     * @return <code>this</code>.
     */
    public ServiceRegistry initBasic() {
        if (basicAlreadyInited.get()) {
            return this;
        }

        synchronized (basicAlreadyInited) {
            if (basicAlreadyInited.get()) {
                return this;
            }

            dependencyWatcher = new DependencyWatcher();
            dependencyWatcher.init();

            

            heapWatcher = new JVMHeapWatcher();
            heapWatcher.init();

            workManagerWatcher = new AsyncWorkerServiceWatcher(heapWatcher);
            workManagerWatcher.init();

            basicAlreadyInited.set(true);
        }

        return this;
    }

    /**
     * {@link #init(java.util.Properties)} must be invoked before accessing this instance for the
     * first time.
     *
     * @return Never <code>null</code>. But the instance could've been {@link #discard() discarded}
     *         and hence might not have any objects registered.
     */
    public static ServiceRegistry getSingletonServiceRegistry() {
        return singletonServiceRegistry;
    }

    //-------------

    /**
     * Ignored if already invoked before. {@link #initBasic()} must be invoked before this.
     *
     * @param env
     * @return <code>this</code>.
     */
    public ServiceRegistry init(Properties env) {
        if (alreadyInited.get()) {
            return this;
        }

        //------------

        synchronized (alreadyInited) {
            if (alreadyInited.get()) {
                return this;
            }

            initBasic();

            configuration = new Configuration(Configuration.ROOT_CONFIG_NAME, env);

            secondaryServices = new LinkedHashMap<Class<? extends Service>, Service>();
            SecondaryServiceHelper.init();

            alreadyInited.set(true);
        }

        return this;
    }

    /**
     * @return <code>null</code> if the service was never {@link #init(java.util.Properties)
     *         initialized} or has already been {@link #discard() discarded}.
     */
    public JVMHeapWatcher getHeapWatcher() {
        return heapWatcher;
    }

    /**
     * @return <code>null</code> if the service was never {@link #init(java.util.Properties)
     *         initialized}.
     */
    public ShutdownWatcher getShutdownWatcher() {
        return shutdownWatcher;
    }

    /**
     * @return <code>null</code> if the service was never {@link #init(java.util.Properties)
     *         initialized} or has already been {@link #discard() discarded}.
     */
    public AsyncWorkerServiceWatcher getWorkManagerWatcher() {
        return workManagerWatcher;
    }

    /**
     * @return <code>null</code> if the service was never {@link #init(java.util.Properties)
     *         initialized} or has already been {@link #discard() discarded}.
     */
    public DependencyWatcher getDependencyWatcher() {
        return dependencyWatcher;
    }

    /**
     * @return <code>null</code> if the service was never {@link #init(java.util.Properties)
     *         initialized} or has already been {@link #discard() discarded}.
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    //-------------

    public <S extends Service> void registerService(Class<S> clazz, S service) {
        secondaryServices.put(clazz, service);
    }

    public <S extends Service> S getService(Class<S> clazz) {
        Service service = secondaryServices.get(clazz);
        if (service != null) {
            return clazz.cast(service);
        }

        return null;
    }

    /**
     * @return Same order in which the services were registered.
     */
    public Collection<? extends Service> getServices() {
        return secondaryServices.values();
    }

    public void unregisterService(Class<? extends Service> clazz) {
        secondaryServices.remove(clazz);
    }

    //-------------

    /**
     * Should be invoked once during shutdown.
     */
    public void discard() {

        if (bStopped.get()) {
            return ;
        }
        synchronized (bStopped) {
            if (bStopped.get()) return;

            for (Service service : secondaryServices.values()) {
                try {
                    service.stop();
                }
                catch (Exception e) {
                    new RuntimeException("Error occurred while stopping secondary services", e)
                            .printStackTrace();
                }
            }

            secondaryServices.clear();

            workManagerWatcher.stop();

            heapWatcher.stop();

            dependencyWatcher.stop();

            configuration.discard();
            bStopped.set(true);
        }
    }

	public void initShutdownWatcher() {
		shutdownWatcher = new ShutdownWatcher();
        shutdownWatcher.init();		
	}
}
