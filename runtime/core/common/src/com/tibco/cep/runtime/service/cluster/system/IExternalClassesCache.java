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

import java.util.Date;
import java.util.Map;

import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Apr 24, 2008 Time: 5:02:57 PM
 * To change this template use File | Settings | File Templates.
 */

public interface IExternalClassesCache {

	void init(Cluster cluster) throws Exception;

	void loadClass(String dirPath, String className) throws Exception;

	void loadClass(String dirPath, String vrfURI, String implName) throws Exception;

	void loadClasses(String dirPath) throws Exception;

	String removeExternalClass(String className) throws Exception;

	Map<String, byte[]> getExternalClasses() throws Exception;

	void shutdown();

	void updateLoadInfo(String className, boolean loaded, Date loadedWhen, String message);

	String[] getLoadInfo();

	void deployExternalClasses() throws Exception;

	void loadExternalClasses() throws Exception;
}
