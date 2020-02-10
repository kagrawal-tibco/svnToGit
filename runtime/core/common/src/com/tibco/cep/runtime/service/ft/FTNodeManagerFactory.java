package com.tibco.cep.runtime.service.ft;

import java.lang.reflect.Constructor;
import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 1, 2006
 * Time: 6:33:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
public class FTNodeManagerFactory {



    public static FTNodeManager createFTNodeManager(RuleServiceProvider rsp, FTNodeController controller, String name) throws Exception {
        Properties props = rsp.getProperties();
        Logger logger = rsp.getLogger(FTNodeManagerFactory.class);

        //Suresh TODO: Directly using Tangosol Node Manager Impl
        String ftNodeManagerClass = props.getProperty("com.tibco.cep.runtime.service.om.NodeManager", "com.tibco.cep.runtime.service.om.impl.coherence.tangosol.FTNodeManagerImpl");
        Class clazz = Class.forName(ftNodeManagerClass);
        Constructor constructor = clazz.getConstructor(new Class[] {Properties.class, Logger.class, FTNodeController.class, String.class});
        return (FTNodeManager) constructor.newInstance(new Object[] {props, logger, controller, name});

    }

}
