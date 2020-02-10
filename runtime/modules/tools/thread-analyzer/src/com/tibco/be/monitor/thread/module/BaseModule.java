/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.monitor.thread.module;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 25, 2009 / Time: 4:20:11 PM
 */
public abstract class BaseModule implements Module {

    protected Module[] subModules;
    protected String name;
    protected String[] packages;
    protected Module parent;

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Module[] getSubModules() {
        return this.subModules;
    }

    @Override
    public boolean isAcceptablePackage(String name) {
        return (findModule(name) != null);
    }

    @Override
    public String[] getPackages() {
        return this.packages;
    }

    @Override
    public Module getParentModule() {
        return this.parent;
    }

    @Override
    public Module findModule(String packageName) {
        if (packageName == null || packages == null
                || packages.length == 0) {
            return null;
        }
        packageName = packageName.trim().toLowerCase();
        for (Module module : subModules) {
            if (module.isAcceptablePackage(name)) {
                return module;
            }
        }
        // Check if name belongs to this module.
        for (String pkgName : packages) {
            if (pkgName.equals(packageName)) {
                return this;
            }
        }
        return null;
    }
    
    @Override
    public int doPrefixMatch(String packageName) {
        return matchPrefix(packageName);
    }

    private int matchPrefix(String packageName) {
        // implement prefix match logic here.
        if(packageName == null || packageName.length() == 0) {
            return 0;
        }
        packageName = packageName.trim().toLowerCase();
        int max = 0;
        for(int i = 0; i < this.subModules.length; i++) {
            int match = this.subModules[i].doPrefixMatch(packageName);
            max = ( match > max? match : max);
        }
        for(int i = 0; i < packages.length; i++) {
            String pkg = packages[i];
            String[] parts = pkg.trim().split(".");
            int match = matchParts(packageName, parts);
            max = ( match > max? match : max);
        }
        return max;
    }

    private int matchParts(String packageName, String[] parts) {
        int match = 0;
        String[] pkgParts = packageName.split(".");
        int length = (pkgParts.length < parts.length ?
            pkgParts.length : parts.length);
        for(int i = 0; i < length; i++) {            
            if(!pkgParts[i].equals(parts[i])) {
                match = i + 1;
                break;
            }
        }
        return match;
    }

}
