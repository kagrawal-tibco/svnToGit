/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.module;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 25, 2009 / Time: 4:22:32 PM
 */
public class CoherenceModule extends BaseModule {

    public CoherenceModule() {
        this.name = "Coherence";
        this.packages = new String[] {
            "com.tangosol.coherence.component"
        };
        this.subModules = new Module[] {
            new NetModule(this), new UtilModule(this),
            new CacheModule(this)
        };
    }

    class NetModule extends BaseModule {

        NetModule(Module parent) {
            this.parent = parent;
            this.name = "Coherence net";
            this.packages = new String[] {
                "com.tangosol.coherence.component.net"
            };
            this.subModules = new Module[] {};
        }
    }

    

    class UtilModule extends BaseModule {

        UtilModule(Module parent) {
            this.parent = parent;
            this.packages = new String[] {
                "com.tangosol.util",
                "com.tangosol.coherence.component.util"
            };
            this.subModules = new Module[] {
                new PacketProcessor(this)
            };
        }
    }

    class CacheModule extends BaseModule {

        CacheModule(Module parent) {
            this.parent = parent;
            this.packages = new String[] {
                "com.tangosol.net.cache"
            };
        }
    }

    class PacketProcessor extends BaseModule {

        PacketProcessor(Module parent) {
            this.parent = parent;
            this.packages = new String[] {
                "com.tangosol.coherence.component.util.daemon.queueProcessor",
                "com.tangosol.coherence.component.util.daemon.queueProcessor.service",
                "com.tangosol.coherence.component.util.daemon.queueProcessor.packetProcessor"
            };
            this.subModules = new Module[0];
        }
    }
}
