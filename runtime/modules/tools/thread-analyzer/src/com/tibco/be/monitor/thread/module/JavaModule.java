/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.module;

import java.util.logging.Logger;
/**
 *
 * Author: Karthikeyan Subramanian / Date: Nov 30, 2009 / Time: 1:12:02 PM
 */
public class JavaModule extends BaseModule {
    private static final Logger logger = Logger.getLogger(
                                              JavaModule.class.getName());
    
    public JavaModule() {
        this.name = "JDK Provided Module";
        this.packages = new String[] {
            "java.io", "java.util", "java.util.logging"
        };
        this.subModules = new Module[0];
    }
}
