package com.tibco.cep.runtime.service.management.jmx.connectors;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Principal;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.remote.JMXPrincipal;
import javax.management.remote.MBeanServerForwarder;
import javax.security.auth.Subject;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidAccessException;
import com.tibco.cep.runtime.service.management.exception.BEMMInvalidUserRoleException;
import com.tibco.cep.runtime.service.management.jmx.principals.MMJmxPrincipal;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/27/11
 * Time: 12:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class SecureJMXProxy {
    public static class MBSFInvocationHandler implements InvocationHandler {
        private MBeanServer mbs;
        private Logger logger = LogManagerFactory.getLogManager().getLogger(SecureJMXProxy.class);

        private static String JMX_OPERATION_INVOKE = "invoke";
        private static String JMX_OPERATION_SET_ATTRIBUTE = "setAttribute";


        public static MBeanServerForwarder newProxyInstance() {
            final InvocationHandler handler = new MBSFInvocationHandler();

            final Class[] interfaces =
                new Class[] {MBeanServerForwarder.class};

            Object proxy = Proxy.newProxyInstance(
                    MBeanServerForwarder.class.getClassLoader(),
                    interfaces,
                    handler);

            return MBeanServerForwarder.class.cast(proxy);
        }

        public Object invoke(Object proxy, Method method, Object[] args)
                throws BEMMInvalidAccessException, BEMMInvalidUserRoleException, InvocationTargetException, IllegalAccessException {

            final String methodName = method.getName();

            if (methodName.equals("getMBeanServer")) {
                return mbs;
            }

            if (methodName.equals("setMBeanServer")) {
                if (args[0] == null)
                    throw new IllegalArgumentException("Null MBeanServer");
                if (mbs != null)
                    throw new IllegalArgumentException("MBeanServer object " +
                                                       "already initialized");
                mbs = (MBeanServer) args[0];
                return null;
            }

            // Retrieve Subject from current AccessControlContext
            AccessControlContext acc = AccessController.getContext();
            Subject subject = Subject.getSubject(acc);

            // Allow operations performed locally on behalf of the connector server itself
            if (subject == null) {
                return method.invoke(mbs, args);
            }

            // Retrieve JMXPrincipal from Subject
            Set<JMXPrincipal> principals = subject.getPrincipals(JMXPrincipal.class);
            if (principals == null || principals.isEmpty()) {
                throw new SecurityException("Access denied");
            }

            for(Principal userPrincipal : principals) {
                if (userPrincipal instanceof MMJmxPrincipal) {
                    String userName = userPrincipal.getName();

                    //if it is an authenticated system operation, authorize it.
                    if( ((MMJmxPrincipal) userPrincipal).isAuthenticSysOperation() ) {
                        logger.log(Level.DEBUG, "Authorized authentic system operation");
                        return method.invoke(mbs, args);
                    }

                    // If security is not enabled, allow any kind of operation without checking authorization level.
                     if ( !((MMJmxPrincipal) userPrincipal).isSecurityEnabled() ) {
                        logger.log(Level.DEBUG, "Operation authorized because security is not enabled for user: ", userName);
                        return method.invoke(mbs, args);
                    }

                    //else security is enabled, so check if the user is authorized to execute the operation he pretends
                    //allow MM_ADMINISTRATORS to do every operation.
                    //restrict MM_USERS to read operations (do not allow them to set attributes)
                    logger.log(Level.DEBUG,"MBean proxy is checking access for user %s", userName);

                    if (((MMJmxPrincipal) userPrincipal).isMMAdministrator()) {
                        return method.invoke(mbs, args);
                    } else if (((MMJmxPrincipal) userPrincipal).isMMUser()) {
                        if ( methodName.equals(JMX_OPERATION_INVOKE) || methodName.equals(JMX_OPERATION_SET_ATTRIBUTE)) {
                            throw new BEMMInvalidAccessException("Only users with with [MM_ADMINISTRATOR] privileges are " +
                                    "authorized to execute 'invoke' operations. User [" + userName + "] is member of the " +
                                    "following user roles: " + ((MMJmxPrincipal) userPrincipal).getRoles().toString().toUpperCase() );
                        } else {
                            return method.invoke(mbs, args);
                        }
                    } else { //invalid user role, throws exception.
                        throw new BEMMInvalidUserRoleException( "User '" + userName + "' has an invalid user roles list: " +
                                ((MMJmxPrincipal) userPrincipal).getRoles().toString().toUpperCase() + ". Valid roles are: " +
                                 ((MMJmxPrincipal) userPrincipal).getValidRoles().toString().toUpperCase() );
                    }
                }
                //else do nothing for now
            }
            throw new BEMMInvalidAccessException("Unexpected state. Access denied");
        }
    }

}
