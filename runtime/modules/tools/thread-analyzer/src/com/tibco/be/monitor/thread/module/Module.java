/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.thread.module;

/**
 *
 * @author ksubrama
 */
public interface Module {

    String getName();
    
    String[] getPackages();

    boolean isAcceptablePackage(String packageName);

    int doPrefixMatch(String packageName);

    Module findModule(String packageName);

    Module[] getSubModules();

    Module getParentModule();
}
