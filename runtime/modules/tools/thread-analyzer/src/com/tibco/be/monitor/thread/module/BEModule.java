/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.module;


/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 25, 2009 / Time: 4:22:22 PM
 */
public class BEModule extends BaseModule {

    public BEModule(Module parent) {
        this.parent = parent;
        name = "Business Events";
        packages = new String[] {"com.tibco.cep"};
        subModules = new Module[] {
            new BERuntime(this), new BEKernelHelper(this),
            new BEContainerModuleImpl(this)
        };
    }

    class BEKernelHelper extends BaseModule {
        BEKernelHelper(Module parent) {
            this.name = "BE Kernel Helper";
            this.packages = new String[] {
                "com.tibco.cep.kernel.helper"
            };
            this.subModules = new Module[0];
            this.parent = parent;
        }
    }

    class BERuntime extends BaseModule {

        BERuntime(Module parent) {
            this.parent = parent;
            this.name = "BE Runtime";
            this.packages = new String[] {
                "com.tibco.cep.runtime"
            };
            subModules = new Module[] {
                new BERuntimeService(this),
                new BERuntimeSession(this),
            };
        }
    }

    class BERuntimeService extends BaseModule {

        BERuntimeService(Module parent) {
            this.parent = parent;
            this.name = "BE Runtime Service";
            this.packages = new String[] {
                "com.tibco.cep.runtime.service.basic"
            };
            subModules = new Module[0];
        }
    }

    class BERuntimeSession extends BaseModule {

        BERuntimeSession(Module parent) {
            this.parent = parent;
            this.name = "BE Runtime Session";
            this.packages = new String[] {
                "com.tibco.cep.runtime.session"
            };
            subModules = new Module[0];
        }
    }

    class BEContainerModuleImpl extends BaseModule {
        
        BEContainerModuleImpl(Module parent) {
            this.parent = parent;
            this.name = "BE Container";
            this.packages = new String[] {
                "com.tibco.cep.container.standalone"
            };
            subModules = new Module[]{new Hawk(this)};
        }
    }

    class Hawk extends BaseModule {

        Hawk(Module parent) {
            this.parent = parent;
            this.name = "BE Hawk";
            this.packages = new String[]{
                "com.tibco.cep.container.standalone.hawk"
            };
            this.subModules = new Module[0];
        }
    }

}
