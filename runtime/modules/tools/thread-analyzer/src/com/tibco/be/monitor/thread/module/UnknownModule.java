/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.module;

import java.util.logging.Logger;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Nov 30, 2009 / Time: 2:22:16 PM
 */
public class UnknownModule extends BaseModule {

    private static final Logger logger = Logger.getLogger(
            UnknownModule.class.getName());

    public UnknownModule(String pkg) {
        this.name = "Unknown Module";
        this.packages = new String[] {pkg};
        this.subModules = new Module[0];
    }
}
