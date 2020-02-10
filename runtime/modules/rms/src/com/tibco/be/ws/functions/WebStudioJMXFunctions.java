/**
 * 
 */
package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.be.ws.jmx.JMXHotDeploy;
import com.tibco.be.ws.jmx.impl.NativeJMXHotDeployImpl;

/**
 * Class lists methods to lookup and invoke JMX MBean methods. These are largely used for RTI and/or DT hot deployment's.
 * 
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.JMX",
        synopsis = "Functions lookup and invoke Remote JMX MBean methods",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.JMX", value=true))

public class WebStudioJMXFunctions {
	
	public static JMXHotDeploy jmxHotDeployInstance = null;

    /**
     * Enum for JMXInstance types for hotdeployment
     */
    enum JMXInstanceType {
    	JMX(new NativeJMXHotDeployImpl()),
    	HTTP(null);// TODO include instanceof Jolokia
    	
    	private JMXHotDeploy jmxInstance;
    	
    	private JMXInstanceType( JMXHotDeploy jmxInstance) {
			this.jmxInstance = jmxInstance;
		}
    	
    	public JMXHotDeploy getJMXInstance() {
    		return jmxInstance;
    	}
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "setJMXHotDeployInstance",
            signature = "void setJMXHotDeployInstance(String connectionType) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="connectionType",
                            type="String",
                            desc="Connection type jmx/http"),
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the JMX connection instance based on the connection type",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static void setJMXHotDeployInstance(String type) {
    	jmxHotDeployInstance = JMXInstanceType.valueOf(type).getJMXInstance();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeploy",
            synopsis = "Invokes the JMX MBean method loadAndDeploy()",
            signature = "void loadAndDeploy(String host, int port, String cn, inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="host",
                            type="String",
                            desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="port",
                            type="int",
                            desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="cn",
                            type="String",
                            desc="Cluster Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeploy() " +
                    "located at the domain com.tibco.be:service=Cluster,name=CLUSTER_NAME$cluster. " +
                    "CLUSTER_NAME is replaced by the value of the parameter cn",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeploy(String host,
                                                  int port,
                                                  String cn,
                                                  boolean inMemory) throws RuntimeException {
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployVRF(host, port, null, null, cn, null, null, inMemory);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeploySecurely",
            synopsis = "Invokes the JMX MBean method loadAndDeploy()",
            signature = "void loadAndDeploySecurely(String host, int port, String  user, String pwd, String cn, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="host",
                            type="String",
                            desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="port",
                            type="int",
                            desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="user",
                            type="String",
                            desc="Username"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="pwd",
                            type="String",
                            desc="Password"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="cn",
                            type="String",
                            desc="Cluster Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeploy() " +
                    "located at the domain com.tibco.be:service=Cluster,name=CLUSTER_NAME$cluster. " +
                    "CLUSTER_NAME is replaced by the value of the parameter cn",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeploySecurely(String host,
                                     int port,
                                     String user,
                                     String pwd,
                                     String cn,
                                     boolean inMemory) throws RuntimeException {
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployVRF(host, port, user, pwd, cn, null, null, inMemory);
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeployVRF",
            synopsis = "Invokes the JMX MBean method loadAndDeploy(...)",
            signature = "void loadAndDeployVRF(String host, int port, " +
                    "String cn, String vrfURI, String implName, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="host",
                            type="String",
                            desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="port",
                            type="int",
                            desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="cn",
                            type="String",
                            desc="Cluster Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="vrfURI",
                            type="String",
                            desc="Virtual Rulefunction URI"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="implName",
                            type="String",
                            desc="Virtual Rulefunction Implementation Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeploy(...) " +
                    "located at the domain com.tibco.be:service=Cluster,name=CLUSTER_NAME$cluster. " +
                    "CLUSTER_NAME is replaced by the value of the parameter cn",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeployVRF(String host,
                                                  int port,
                                                  String cn,
                                                  String vrfURI,
                                                  String implName,
                                                  boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployVRF(host, port, null, null, cn, vrfURI, implName, inMemory);
    }


    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeployVRFSecurely",
            synopsis = "Invokes the JMX MBean method loadAndDeploy(...)",
            signature = "void loadAndDeployVRFSecurely(String host, int port, String  user, " +
                    "String pwd, String cn, String vrfURI, String implName, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="host",
                        type="String",
                        desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="port",
                        type="int",
                        desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="user",
                        type="String",
                        desc="Username"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="pwd",
                        type="String",
                        desc="Password"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="cn",
                        type="String",
                        desc="Cluster Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="vrfURI",
                        type="String",
                        desc="Virtual Rulefunction URI"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="implName",
                        type="String",
                        desc="Virtual Rulefunction Implementation Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeploy(...) " +
                    "located at the domain com.tibco.be:service=Cluster,name=CLUSTER_NAME$cluster. " +
                    "CLUSTER_NAME is replaced by the value of the parameter cn",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeployVRFSecurely(String host,
                                     int port,
                                     String user,
                                     String pwd,
                                     String cn,
                                     String vrfURI,
                                     String implName,
                                     boolean inMemory) throws RuntimeException{

    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployVRF(host, port, user, pwd, cn, vrfURI, implName, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeployAllRTIs",
            synopsis = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...)",
            signature = "void loadAndDeployAllRTIs(String host, int port, " +
                    "String agentName, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="host",
                        type="String",
                        desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="port",
                        type="int",
                        desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="agentName",
                        type="String",
                        desc="Agent Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
                    
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...) ",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeployAllRTIs(String host,
            int port, String agentName, boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployRTI(host, port, null, null, agentName, null, null, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeployAllRTIsSecurely",
            synopsis = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...)",
            signature = "void loadAndDeployAllRTIsSecurely(String host, int port, String user, String pwd, " +
                    "String agentName, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="host",
                        type="String",
                        desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="port",
                        type="int",
                        desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="user",
                        type="String",
                        desc="Username"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="pwd",
                        type="String",
                        desc="Password"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="agentName",
                        type="String",
                        desc="Agent Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...) ",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeployAllRTIsSecurely(String host,
            int port,
            String user,
            String pwd,
            String agentName,
            boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployRTI(host, port, user, pwd, agentName, null, null, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeployRTI",
            synopsis = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...)",
            signature = "void loadAndDeployRTI(String host, int port, " +
                    "String agentName, String projectName, String ruleTemplateInstanceFQN, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="host",
                        type="String",
                        desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="port",
                        type="int",
                        desc="JMX Port"),                
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="agentName",
                        type="String",
                        desc="Agent Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="projectName",
                        type="String",
                        desc="Project Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="ruleTemplateInstanceFQN",
                        type="String",
                        desc="Fully Qualified Name of the Rule Template Intance"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...) ",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeployRTI(String host,
            int port,
            String agentName,
            String projectName,
            String ruleTemplateInstanceFQN,
            boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployRTI(host, port, null, null, agentName, projectName, ruleTemplateInstanceFQN, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "loadAndDeployRTISecurely",
            synopsis = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...)",
            signature = "void loadAndDeployRTISecurely(String host, int port, String user, String pwd, " +
                    "String agentName, String projectName, String ruleTemplateInstanceFQN, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="host",
                        type="String",
                        desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="port",
                        type="int",
                        desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="user",
                        type="String",
                        desc="Username"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="pwd",
                        type="String",
                        desc="Password"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="agentName",
                        type="String",
                        desc="Agent Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="projectName",
                        type="String",
                        desc="Project Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="ruleTemplateInstanceFQN",
                        type="String",
                        desc="Fully Qualified Name of the Rule Template Intance"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method loadAndDeployRuleTemplateInstances(...) ",
            cautions = "",
            fndomain = {ACTION},
            example = ""
    )
    public static synchronized void loadAndDeployRTISecurely(String host,
            int port,
            String user,
            String pwd,
            String agentName,
            String projectName,
            String ruleTemplateInstanceFQN,
            boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.deployRTI(host, port, user, pwd, agentName, projectName, ruleTemplateInstanceFQN, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "unDeployAllRTIs",
        synopsis = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...)",
        signature = "void unDeployAllRTIs(String host, int port, String agentName, boolean inMemory) throws Exception",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="host",
                    type="String",
                    desc="IP address"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="port",
                    type="int",
                    desc="JMX Port"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="agentName",
                    type="String",
                    desc="Agent Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="inMemory",
                        type="boolean",
                        desc="If the project is inMemory")
        },
       freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...) ",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static synchronized void unDeployAllRTIs(String host,
            int port,
            String agentName,
            boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.unDeployRTI(host, port, null, null, agentName, null, null, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "unDeployAllRTIsSecurely",
        synopsis = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...)",
        signature = "void unDeployAllRTIsSecurely(String host, int port, String user, String pass, String agentName, boolean inMemory) throws Exception",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="host",
                    type="String",
                    desc="IP address"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="port",
                    type="int",
                    desc="JMX Port"),
	            @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="user",
                    type="String",
                    desc="Username"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="pwd",
                    type="String",
                    desc="Password"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="agentName",
                    type="String",
                    desc="Agent Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="inMemory",
                        type="boolean",
                        desc="If the project is inMemory")
        },
       freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...) ",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static synchronized void unDeployAllRTIsSecurely(String host,
            int port,
            String user,
            String pwd,
            String agentName,
            boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.unDeployRTI(host, port, user, pwd, agentName, null, null, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "unDeployRTI",
        synopsis = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...)",
        signature = "void unDeployRTI(String host, int port, String agentName, String projectName, String ruleTemplateInstanceFQN, boolean inMemory) throws Exception",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="host",
                    type="String",
                    desc="IP address"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="port",
                    type="int",
                    desc="JMX Port"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="agentName",
                    type="String",
                    desc="Agent Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="projectName",
                    type="String",
                    desc="Project Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="ruleTemplateInstanceFQN",
                    type="String",
                    desc="Fully Qualified Name of the Rule Template Intance"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="inMemory",
                        type="boolean",
                        desc="If the project is inMemory")
        },
       freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...) ",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static synchronized void unDeployRTI(String host,
            int port,
            String agentName,
            String projectName,
            String ruleTemplateInstanceFQN,
            boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.unDeployRTI(host, port, null, null, agentName, projectName, ruleTemplateInstanceFQN, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "unDeployRTISecurely",
        synopsis = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...)",
        signature = "void unDeployAllRTIsSecurely(String host, int port, String user, String pass, String agentName, String projectName, String ruleTemplateInstanceFQN, boolean inMemory) throws Exception",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="host",
                    type="String",
                    desc="IP address"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="port",
                    type="int",
                    desc="JMX Port"),
	            @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="user",
                    type="String",
                    desc="Username"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="pwd",
                    type="String",
                    desc="Password"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="agentName",
                    type="String",
                    desc="Agent Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="projectName",
                    type="String",
                    desc="Project Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="ruleTemplateInstanceFQN",
                    type="String",
                    desc="Fully Qualified Name of the Rule Template Intance"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="inMemory",
                        type="boolean",
                        desc="If the project is inMemory")
        },
       freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes the JMX MBean method unDeployRuleTemplateInstances(...) ",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static synchronized void unDeployRTISecurely(String host,
            int port,
            String user,
            String pwd,
            String agentName,
            String projectName,
            String ruleTemplateInstanceFQN,
            boolean inMemory) throws RuntimeException {
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.unDeployRTI(host, port, user, pwd, agentName, projectName, ruleTemplateInstanceFQN, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "unDeployVRF",
            synopsis = "Invokes the JMX MBean method unloadClass(...)",
            signature = "void unDeployVRF(String host, int port, " +
                     "String cn, String vrfURI, String implName, boolean inMemory) throws Exception",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="host",
                        type="String",
                        desc="IP address"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="port",
                        type="int",
                        desc="JMX Port"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="cn",
                        type="String",
                        desc="Cluster Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="vrfURI",
                        type="String",
                        desc="Virtual Rulefunction URI"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="implName",
                        type="String",
                        desc="Virtual Rulefunction Implementation Name"),
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name="inMemory",
                            type="boolean",
                            desc="If the project is inMemory")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Invokes the JMX MBean method unloadClass(...) " +
                    "located at the domain com.tibco.be:service=Cluster,name=CLUSTER_NAME$cluster. " +
                    "CLUSTER_NAME is replaced by the value of the parameter cn",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
        public static synchronized void unDeployVRF(String host,
                                         int port,
                                         String cn,
                                         String vrfURI,
                                         String implName,
                                         boolean inMemory) throws RuntimeException {
    	
    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.unDeployVRF(host, port, null, null, cn, vrfURI, implName, inMemory);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "unDeployVRFSecurely",
        synopsis = "Invokes the JMX MBean method unloadClass(...)",
        signature = "void unDeployVRFSecurely(String host, int port, String  user, " +
                "String pwd, String cn, String vrfURI, String implName, inMemory) throws Exception",
        params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="host",
                    type="String",
                    desc="IP address"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="port",
                    type="int",
                    desc="JMX Port"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="user",
                    type="String",
                    desc="Username"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="pwd",
                    type="String",
                    desc="Password"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="cn",
                    type="String",
                    desc="Cluster Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="vrfURI",
                    type="String",
                    desc="Virtual Rulefunction URI"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name="implName",
                    type="String",
                    desc="Virtual Rulefunction Implementation Name"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(
                        name="inMemory",
                        type="boolean",
                        desc="If the project is inMemory")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes the JMX MBean method unloadClass(...) " +
                "located at the domain com.tibco.be:service=Cluster,name=CLUSTER_NAME$cluster. " +
                "CLUSTER_NAME is replaced by the value of the parameter cn",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static synchronized void unDeployVRFSecurely(String host,
                                     int port,
                                     String user,
                                     String pwd,
                                     String cn,
                                     String vrfURI,
                                     String implName,
                                     boolean inMemory) throws RuntimeException {

    	if (jmxHotDeployInstance == null) throw new RuntimeException("JMX instance for hot deploy not set. Use api setJMXHotDeployInstance");
    	jmxHotDeployInstance.unDeployVRF(host, port, user, pwd, cn, vrfURI, implName, inMemory);
    }
    
    public static void main(String[] args) {
    	setJMXHotDeployInstance("JMX");
    }
    
}
