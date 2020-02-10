package com.tibco.cep.modules.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.CEPModule;
import com.tibco.cep.modules.ModuleRegistry;
import com.tibco.cep.modules.RTFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 17, 2007
 * Time: 12:18:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModuleManagerImpl implements com.tibco.cep.modules.ModuleManager {

    RuleServiceProvider provider;
    List modules;
    ModuleRegistry moduleRegistry;

    public ModuleManagerImpl(RuleServiceProvider provider) {
        this.provider = provider;
        modules = new ArrayList();
        moduleRegistry = ModuleRegistry.getInstance();
    }

    public void init() throws Exception {
        Iterator r = moduleRegistry.getModules().iterator();
        while (r.hasNext()) {
            CEPModule dtmodule = (CEPModule) r.next();
            try {
                RTFactory rtFactory = dtmodule.getRuntimeFactory();
                if (null != rtFactory) {
                    rtFactory.initialize(this);
                }
            }
            catch (Exception e) {
                this.provider.getLogger(ModuleManagerImpl.class).log(Level.ERROR, e,
                        "Failed to initialize module %s", dtmodule.getName());
            }
        }
    }

    public void startModules(int mode) throws Exception {
        Iterator r = moduleRegistry.getModules().iterator();
        while (r.hasNext()) {
            CEPModule dtmodule = (CEPModule) r.next();
            try {
                RTFactory rtFactory = dtmodule.getRuntimeFactory();
                if (null != rtFactory) {
                    rtFactory.start(mode);
                }
            }
            catch (Exception e) {
                this.provider.getLogger(ModuleManagerImpl.class).log(Level.ERROR, e,
                        "Failed to start module %s", dtmodule.getName());
            }

        }
    }

    public void stopModules() throws Exception {
        Iterator r = moduleRegistry.getModules().iterator();
        while (r.hasNext()) {
            CEPModule dtmodule = (CEPModule) r.next();
            try {
                RTFactory rtFactory = dtmodule.getRuntimeFactory();
                if (null != rtFactory) {
                    rtFactory.stop();
                }
            }
            catch (Exception e) {
                this.provider.getLogger(ModuleManagerImpl.class).log(Level.ERROR, e,
                        "Failed to stop module %s", dtmodule.getName());
            }

        }
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return provider;
    }

}
