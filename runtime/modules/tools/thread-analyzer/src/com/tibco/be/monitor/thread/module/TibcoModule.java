/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.module;


/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 25, 2009 / Time: 4:57:17 PM
 */
public class TibcoModule extends BaseModule {

    public TibcoModule() {
        this.name = "Tibco";
        this.packages = new String[] {
            "com.tibco"
        };
        this.subModules = new Module[]{
                    new BEModule(this),
                    new RVModuleImpl(this),
                    new EMSModuleImpl(this),
                    new HawkAMIModuleImpl(this)
                };
    }

    class HawkAMIModuleImpl extends BaseModule {

        HawkAMIModuleImpl(Module parent) {
            this.parent = parent;
            this.name = "Tibco Hawk AMI";
            this.packages = new String[] {
                "com.tibco.hawk.ami"
            };
            this.subModules = new Module[0];
        }
    }

    class RVModuleImpl extends BaseModule {

        RVModuleImpl(Module parent) {
            this.parent = parent;
            this.name = "Tibco RV";
            this.packages = new String[] {
                "com.tibco.tibrv"
            };
            this.subModules = new Module[0];
        }
    }

    class EMSModuleImpl extends BaseModule {

        EMSModuleImpl(Module parent) {
            this.parent = parent;
            this.name = "Tibco EMS";
            this.packages = new String[] {
                "com.tibco.tibjms"
            };
            this.subModules = new Module[0];
        }
    }
}
