package com.tibco.be.rms.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.Approval",
        synopsis = "RMS Utility Functions For Approval",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS.Approval", value=true))

public class RMSApprovalFunctions {
    @com.tibco.be.model.functions.BEFunction(
            name = "fetchServedProjectsList",
            synopsis = "Gets a list of all directories present inside a folder",
            signature = "String[] fetchServedProjectsList(String baseDirPath)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "baseDirPath", type = "String", desc = "the root directory absolute path")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = ""),
            version = "3.0.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets a list of all directories present inside a folder",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static String[] fetchServedProjectsList(final String baseDirPath) {
            if (baseDirPath == null) {
                throw new IllegalArgumentException("Projects base directory cannot be null");
            }
            File baseDir = new File(baseDirPath);
            if (!baseDir.exists()) {
                throw new IllegalArgumentException("Projects base directory does not exist");
            }
            String[] projects = baseDir.list(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    File temp = new File(dir + File.separator + name);
                    return temp.isDirectory() && !temp.isHidden();
                }
            });
            return projects;
        }

    @com.tibco.be.model.functions.BEFunction(
            name = "unloadClass",
            synopsis = "Unloads a class deployed into the production engine.\nThis class has been created out of an implementation of\na rule function/rule.",
            signature = "unloadClass(String vrfUri, String implName)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfUri", type = "String", desc = "The fully qualified name of the rule function/rule resource"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "implName", type = "String", desc = "The name of the implementation")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Unloads a class deployed into the production engine.\nThis class has been created out of an implementation of\na rule function/rule.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
        )
        public static void unloadClass(String vrfUri,
                                       String implName) {
            invoke("unloadClass", new String[]{"java.lang.String", "java.lang.String"}, new Object[]{vrfUri, implName});
        }

    /**
     * Invoke a JMX operation
     * @param operationName
     * @param paramsClasses
     * @param params
     */
    public static void invoke(String operationName,
                              String[] paramsClasses,
                              Object[] params) {
        try {
            RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
            if (ruleSession != null) {
                RuleServiceProvider RSP = ruleSession.getRuleServiceProvider();
                BEProperties allProps = (BEProperties)RSP.getProperties();
                //Get be.engine branch
                BEProperties clusterProps = allProps.getBranch("be.engine");
                if (clusterProps != null) {
                    String clusterName = clusterProps.getProperty("cluster.name", null);;
                    String rmiHost = clusterProps.getProperty("cluster.rmi.host", "localhost");
                    int rmiPort = clusterProps.getInt("cluster.rmi.port", 9999);
                    JMXServiceURL url =
                            new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + rmiHost + ":" + rmiPort + "/server");
                    JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
                    MBeanServerConnection mbc = jmxc.getMBeanServerConnection();
                    ObjectName name = new ObjectName("com.tibco.be:service=Cluster,name=" + clusterName);
                    Object invocationResult = mbc.invoke(name, operationName, params, paramsClasses);
                    return;
                }
            }
            throw new RuntimeException("Current thread rulesession was null. MBean invocation failed.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "deployClass",
            synopsis = "Deploys a class matching specified resourceURI into all nodes of a cluster.",
            signature = "deployClass(String projectName, String artifactPath, String artifactExtension)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project name in which to look for this resource."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "The fully qualified name of the rule function/rule resource"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactExtension", type = "String", desc = "File extension of the artifact.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "",
            cautions = "Do not use this function outside the RTC thread",
            fndomain = {ACTION},
            example = ""
        )
        public static void deployClass(String projectName,
                                       String artifactPath,
                                       String artifactExtension) {
            if (com.tibco.cep.studio.rms.artifacts.ArtifactsType.DECISIONTABLE.getLiteral().intern() == artifactExtension.intern()) {
                com.tibco.be.rms.repo.ManagedEMFProject managedProject = (com.tibco.be.rms.repo.ManagedEMFProject) com.tibco.be.functions.java.util.MapHelper.getObject("MANAGED_PROJECTS", projectName);
                if (managedProject != null) {
                    //Get table instance
                    com.tibco.cep.decision.table.model.dtmodel.Table table = managedProject.getDecisionTable(artifactPath);
                    if (table == null) {
                        throw new RuntimeException("Table " + artifactPath + " not found in project " + projectName);
                    }
                    //Get VRF implemented
                    String implementedVRFPath = table.getImplements();
                    invoke("loadAndDeploy", new String[] {"java.lang.String", "java.lang.String"}, new Object[] {implementedVRFPath, table.getName()});
                }
            }
            invoke("loadAndDeploy", new String[] {"java.lang.String"}, new Object[] {artifactPath});
        }

    @com.tibco.be.model.functions.BEFunction(
            name = "generateRequestId",
            synopsis = "Generate a unique request id upon checkin.",
            signature = "String generateRequestId(String username)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "username", type = "String", desc = "the request id.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "request id as a string"),
            version = "1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Generate a unique request id upon checkin.",
            cautions = "none",
            fndomain = {ACTION},
            example = ""
        )
        public static String generateRequestId(String username) {
            username = (username == null) ? "" : username;
            Random random = new Random(System.currentTimeMillis());
            int id = Math.abs((username.hashCode() << 8) + random.nextInt());
            String requestId = Integer.toString(id, 16);
            return requestId.toUpperCase();
        }

    

}
