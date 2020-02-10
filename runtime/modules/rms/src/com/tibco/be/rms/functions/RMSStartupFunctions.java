package com.tibco.be.rms.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.JVMHeapWatcher;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.Startup",
        synopsis = "RMS Utility Functions For Startup",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS.Startup", value=true))

public class RMSStartupFunctions {
    @com.tibco.be.model.functions.BEFunction(
            name = "setMemoryThreshold",
            synopsis = "Sets memory threshold beyond which warning message is displayed on the\nengine console.",
            signature = "setMemoryThreshold(double threshold)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "threshold", type = "double", desc = "The value of threshold e.g.: 0.7 -> 70%")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets memory threshold beyond which warning message is displayed on the\nengine console.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
        )
        public static void setMemoryThreshold(double threshold) {
            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
            JVMHeapWatcher heapWatcher = registry.getHeapWatcher();
            heapWatcher.setThreshold(threshold);
        }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "registerMBean",
            synopsis = "Registers the MBeans",
            signature = "registerMBean(String mbeanName, String className)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "mbeanName", type = "String", desc = "Name of the MBean to be registered"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "className", type = "String", desc = "Classname of the MBean to be registered")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Registers the MBeans",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
        )
   public static void registerMBean(String mbeanName, String className) {
       try {
           MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
           ObjectName name = new ObjectName(mbeanName);
           ClassLoader cl = RMSFunctionUtils.class.getClassLoader();
           Class<?> clazz = cl.loadClass(className);
           Object instance = clazz.newInstance();
           mbs.registerMBean(instance, name);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }
}
