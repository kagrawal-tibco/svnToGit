/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.scheduler;

import java.lang.reflect.Constructor;
import java.util.Properties;

import com.tibco.cep.runtime.scheduler.impl.WorkerBasedControllerV2;
import com.tibco.cep.runtime.service.cluster.ClusterTaskController;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Feb 5, 2007
 * Time: 6:09:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaskControllerFactory {

    static public TaskController createTaskController(RuleSession session, Properties beprops) throws Exception{
        String name = session.getName();
        String className = WorkerBasedControllerV2.class.getName();
        if (session.getObjectManager() instanceof DistributedCacheBasedStore) {
            className= ClusterTaskController.class.getName();
        }

//                DefaultTaskController.class.getName();
        String dclassName = beprops.getProperty("com.tibco.cep.runtime.scheduler." + name);
//        if (name.equalsIgnoreCase(session.getName())) {
//            className = beprops.getProperty("com.tibco.cep.runtime.scheduler."+name, DefaultTaskController.class.getName());
//        }
        if ((dclassName == null) || (dclassName.length() == 0)) {
            dclassName = beprops.getProperty("com.tibco.cep.runtime.scheduler.*", className);
        }
        Class klazz = Class.forName(dclassName);

        Constructor ctor = klazz.getConstructor(new Class[] {RuleSession.class});
        TaskController controller = (TaskController) ctor.newInstance(new Object[]{session});
        return controller;
    }
}
