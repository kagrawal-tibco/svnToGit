/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/7/2010
 */

package com.tibco.cep.runtime.service.cluster.system;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.deploy.LoadExternalClasses;
import com.tibco.cep.runtime.service.om.api.ControlDao;
import com.tibco.cep.runtime.service.om.api.ControlDaoType;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 24, 2008
 * Time: 5:02:57 PM
 * To change this template use File | Settings | File Templates.
 */

public class ExternalClassesCache {
    Cluster cluster;
    ControlDao<String, byte[]> externalCatalog;
    ControlDao<String, Object> externalCatalogLockDao;
    private static final String IS_LOADED = new String("-2");
    private static final String LOADED_LOCK_KEY = new String("-1");
    Map loadInfo = new HashMap();
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ExternalClassesCache.class);

    /**
     * @param cluster
     * @throws Exception
     */

    public ExternalClassesCache() {
    }

    public void init(Cluster cluster) throws Exception {
        this.cluster = cluster;
        externalCatalog = cluster.getDaoProvider().createControlDao(String.class, byte[].class, ControlDaoType.ExternalClasses);
        externalCatalog.start();
        externalCatalogLockDao = cluster.getDaoProvider().createControlDao(String.class, Object.class, ControlDaoType.ExternalClassesLock);
        externalCatalogLockDao.start();
    }
    
    protected byte[] loadClassFile(File classFile) throws Exception {
        FileInputStream fi = new FileInputStream(classFile);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        while (true) {
            int b = fi.read();
            if (b != -1)
                byteStream.write(b);
            else
                break;
        }
        fi.close();
        return byteStream.toByteArray();

    }

    /**
     * Load a class specified clasName from the file specified by dirPath.
     * <p>
     * The dirPath can be a directory or a jar file
     * </p>
     *
     * @param dirPath
     * @param className
     * @throws Exception
     */
    public void loadClass(String dirPath, String className) throws Exception {
        externalCatalog.clear();
        byte[] classBytes = LoadExternalClasses.readClassFromArchiveOrDirectory(dirPath, className);
        externalCatalog.put(className, classBytes);
    }
    
    public void loadClass(String dirPath, String vrfURI, String implName) throws Exception {
        String className = resolveClass(vrfURI, implName);
        loadClass(dirPath, className);
    }
    
    
    public synchronized void loadClasses(String dirPath) throws Exception {
        String packageExclusions = cluster.getClusterConfig()
                .getExternalClassPackageExclusions();
        
        Map<String, byte[]> classes = LoadExternalClasses.loadClasses(dirPath, packageExclusions);

        if (classes.size() == 0) {
            // Flag a warning
            LOGGER.log(Level.WARN, "No classes present in the directory to load");
        }

        for (Map.Entry<String, byte[]> stringEntry : classes.entrySet()) {
            String className = LoadExternalClasses.pathToClassName(stringEntry.getKey());
            byte[] clzBytes = stringEntry.getValue();
            externalCatalog.put(className, clzBytes);
        }
    }

    /**
     * Remove the specified external class from the cache.
     * Also updates the {@link ExternalClassInfo}
     *
     * @param className
     * @return className if the remove operation was successful
     */
    public String removeExternalClass(String className) throws Exception {
        RuleServiceProvider rsp = cluster.getRuleServiceProvider();

        boolean isClassLoaded = externalCatalog.containsKey(className);
        if (!isClassLoaded) {
            LOGGER.log(Level.WARN, "No class matching the specified resources loaded in cache.");
            return null;
        }
        LOGGER.log(Level.INFO, "Unloading class %s from all nodes", className);

        byte[] clazz = externalCatalog.remove(className);
        //Update load info
        ExternalClassInfo info = (ExternalClassInfo) loadInfo.get(className);
        if (info == null) {
            info = new ExternalClassInfo();
        }
        info.className = className;
        info.loaded = false;
        loadInfo.remove(className);
        if (clazz != null) {
            return className;
        }
        return null;
    }

    public Map<String, byte[]> getExternalClasses() {
        Map<String, byte[]> map = new HashMap<String, byte[]>();
        Iterator allClasses = externalCatalog.entrySet().iterator();
        while (allClasses.hasNext()) {
            Map.Entry entry = (Map.Entry) allClasses.next();
            map.put((String) entry.getKey(), (byte[]) entry.getValue());
        }
        return map;
    }


    public void shutdown() {
        externalCatalog.discard();
        externalCatalogLockDao.discard();
    }

    public void updateLoadInfo(String className, boolean loaded, Date loadedWhen, String message) {
        ExternalClassInfo info = (ExternalClassInfo) loadInfo.get(className);
        if (info == null) {
            info = new ExternalClassInfo();
        }
        info.className = className;
        info.loaded = loaded;
        info.loadedWhen = loadedWhen;
        info.message = message;
        loadInfo.put(className, info);
    }

    public String[] getLoadInfo() {
        String[] ret = new String[loadInfo.size()];
        Iterator allInfo = loadInfo.values().iterator();
        int j = 0;
        while (allInfo.hasNext()) {
            ExternalClassInfo info = (ExternalClassInfo) allInfo.next();
            ret[j++] = "Function[" + info.className + "] {loaded=" + info.loaded + ", time=" + ((info.loadedWhen == null) ? "" : info.loadedWhen) + ", message=" + ((info.message == null) ? "" : info.message) + "}";
        }
        return ret;
    }

    class ExternalClassInfo {
        String className;
        boolean loaded;
        Date loadedWhen;
        String message;
    }
    
    private String resolveClass(String vrfURI, String implName) throws Exception {
    	Entity entity = cluster.getRuleServiceProvider().getProject().getOntology().getEntity(vrfURI);
    	Logger logger = cluster.getRuleServiceProvider().getLogger(ExternalClassesCache.class);
        return LoadExternalClasses.resolveClass(entity, implName, logger);
    }
    
    public void deployExternalClasses() throws Exception {
        Map<String, byte []> allExternalClasses = getExternalClasses();
        LoadExternalClasses.redefineClasses(allExternalClasses, cluster.getRuleServiceProvider(), cluster, true);
    }
    
    public void loadExternalClasses() throws Exception {
        if (!cluster.getClusterConfig().isClassLoader()) {
            return;
        }
        if (cluster.getClusterConfig().getExternalClassesDir() == null) {
            return;
        }
        boolean isLocked = false;
        try {
            isLocked = externalCatalogLockDao.lock(LOADED_LOCK_KEY, -1);
            Boolean b = (Boolean) externalCatalogLockDao.get(IS_LOADED);
            boolean isLoaded = (b != null && b.booleanValue());
            if (isLoaded) {
                LOGGER.log(Level.INFO, "Cluster %s : Already Loaded Classes From File System", cluster.getClusterName());
                return;
            }
            LOGGER.log(Level.INFO, "Cluster %s : Loading Classes From File System", cluster.getClusterName());
            loadClasses(cluster.getClusterConfig().getExternalClassesDir());
            externalCatalogLockDao.put(IS_LOADED, true);
            LOGGER.log(Level.INFO, "Cluster %s : Loading Classes From File System complete.", cluster.getClusterName());
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e, "Failed to load external classes");
        } finally {
            if (isLocked) {
                externalCatalogLockDao.unlock(LOADED_LOCK_KEY);
            }
        }            
    }
}


