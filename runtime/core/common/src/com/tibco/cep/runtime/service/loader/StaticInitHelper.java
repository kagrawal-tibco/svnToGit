package com.tibco.cep.runtime.service.loader;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Feb 17, 2009
 * Time: 6:43:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaticInitHelper {
    public static boolean initializeClass(Class cls, Logger logger, RuleServiceProvider rsp) {
        if(cls != null && NeedsStaticInitialization.class.isAssignableFrom(cls)) {
            return invokeStaticInit(cls, NeedsStaticInitialization.INIT_METHOD_NAME, logger, rsp);
        }
        return true;
    }

    public static boolean uninitializeClass(Class cls, Logger logger, RuleServiceProvider rsp) {
        if(cls != null && NeedsStaticUninitialization.class.isAssignableFrom(cls)) {
            return invokeStaticInit(cls, NeedsStaticUninitialization.UNINIT_METHOD_NAME, logger, rsp);
        }
        return true;
    }

    private static boolean invokeStaticInit(Class cls, String methodName, Logger logger, RuleServiceProvider rsp) {
        try {
            Method initMethod = cls.getMethod(methodName, RuleServiceProvider.class);
            if((initMethod.getModifiers() & Modifier.STATIC) != 0) {
                initMethod.invoke(null, rsp);
            } else {
                return false;
            }
        } catch (NoSuchMethodException nsme) {
            logger.log(Level.DEBUG, nsme, "StaticInitHelper: error initializing class %s", cls.getName());
            return false;
        } catch (InvocationTargetException ite) {
            logger.log(Level.DEBUG, ite, "StaticInitHelper: error initializing class %s", cls.getName());
            return false;
        } catch (IllegalAccessException iae) {
            logger.log(Level.DEBUG, iae, "StaticInitHelper: error initializing class %s", cls.getName());
            return false;
        }
        return true;
    }
}
