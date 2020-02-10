package com.tibco.cep.functions.channel.as;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.cep.functions.channel.as.ASMemberDefHelper;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.FunctionParamDescriptor;

@BEPackage(
		catalog = "ActiveSpaces", 
		category = "Metaspace.MemberDef", 
		synopsis = "Member Definition functions")
public class MemberDefHelper extends MetaspaceHelper {

	@com.tibco.be.model.functions.BEFunction(
			name = "create", 
            synopsis = "Creates Member definition using default connection definition.",
			signature = "Object create ()", 
			params = {
			},
			freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "instance of MemberDef"), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Creates Member definition using default connection definition",
			cautions = "none",
			fndomain = {ACTION},
			example = ""
	)
    public static Object create () {
		return ASMemberDefHelper.create();
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getContext", 
			synopsis = "Gets context for this Member as a Tuple (collection of name-value pairs).",
            signature = "Object getContext (Object memberDef)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "memberDef", desc = "member definition ") 
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "context values as Tuple. Use the Tuple API to access the values."), 
			version = "5.1.1", 
			see = "Tuple",
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "returns tuple context", 
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
    public static Object getContext (Object memberDef) {
		return ASMemberDefHelper.getContext(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getDataStore", 
            synopsis = "Returns datastore name for provided member definition.",
            signature = "String getDataStore (Object memberDef)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ") 
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "datastore name."), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "returns datastore name.", 
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
	public static String getDataStore (Object memberDef) {
		return ASMemberDefHelper.getDataStore(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getDiscovery", 
            synopsis = "Returns discovery url for provided member definition.",
            signature = "String getDiscovery (Object memberDef)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition") 
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "discovery url"), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "returns discovery url",
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
	public static String getDiscovery (Object memberDef) {
		return ASMemberDefHelper.getDiscovery(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getListen", 
            synopsis = "Returns listen url for provided member definition.",
			signature = "String getListen (Object memberDef)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ") 
			},
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "listen url"), 
			version = "5.1.1", 
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "returns listen url",
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
    public static String getListen (Object memberDef) {
		return ASMemberDefHelper.getListen(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getMemberName",
            synopsis = "Returns member name for provided member definition",
            signature = "String getMemberName (Object memberDef)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ") 
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "member name"),
			version = "5.1.1",
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "return member name",
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static String getMemberName (Object memberDef) {
		return ASMemberDefHelper.getMemberName(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getProcessName",
            synopsis = "Returns process name for provided member definition",
            signature = "String getProcessName (Object memberDef)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ") 
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "process name"),
			version = "5.6.1",
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "return process name",
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static String getProcessName (Object memberDef) {
		return ASMemberDefHelper.getProcessName(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
            name = "getConnectTimeout",
            synopsis = "Gets connect timeout property from the member definition.",
            signature = "long getConnectTimeout (Object memberDef)",
            params = {
            		@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
            },
            freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "timeout period in milliseconds"),
            version = "5.2.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets connect timeout property from the member definition.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static long getConnectTimeout (Object memberDef) {
		return ASMemberDefHelper.getConnectTimeout(memberDef);
    }

	@com.tibco.be.model.functions.BEFunction(
            name = "setConnectTimeout",
            synopsis = "Sets connect timeout property for the member to join the cluster.",
            signature = "Object setConnectTimeout (Object memberDef, long timeInMillis)",
            params = {
            		@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "timeInMillis", type = "long", desc = "time period in milliseconds ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "member definition"),
            version = "5.1.1",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets connect timeout property for the member to join the cluster.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static Object setConnectTimeout (Object memberDef, long timeInMillis) {
		return ASMemberDefHelper.setConnectTimeout(memberDef, timeInMillis);
    }

	@com.tibco.be.model.functions.BEFunction(
            name = "getMemberTimeout",
            synopsis = "Gets member timeout property from the member definition.",
            signature = "long getMemberTimeout (Object memberDef)",
            params = {
            		@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
            },
            freturn = @FunctionParamDescriptor(name = "", type = "long", desc = "member timeout period in milliseconds"),
            version = "5.3.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets member timeout property from the member definition.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static long getMemberTimeout (Object memberDef) {
		return ASMemberDefHelper.getMemberTimeout(memberDef);
    }

	@com.tibco.be.model.functions.BEFunction(
            name = "setMemberTimeout",
            synopsis = "Sets member timeout property, which controls how long each member waits/tries to reconnect a lost member",
            signature = "Object setMemberTimeout (Object memberDef, long timeInMillis)",
            params = {
            		@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "timeInMillis", type = "long", desc = "member timeout period in milliseconds ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "Object", desc = "member definition"),
            version = "5.3.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets member timeout property, which controls how long each member waits/tries to reconnect a lost member.",
            cautions = "Member timeout must be set to the same value for all members in the cluster.",
			fndomain = {ACTION}, 
            example = ""
    )
    public static Object setMemberTimeout (Object memberDef, long timeInMillis) {
		return ASMemberDefHelper.setMemberTimeout(memberDef, timeInMillis);
    }

	@com.tibco.be.model.functions.BEFunction(
			name = "getRemoteDiscovery",
            synopsis = "Gets remote discovery url for provided member definition.",
            signature = "String getRemoteDiscovery (Object memberDef)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ") 
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "remote discovery url"),
			version = "5.1.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "get remote discovery url", 
			cautions = "none", 
			fndomain = {ACTION},
			example = ""
	)
    public static String getRemoteDiscovery (Object memberDef) {
		return ASMemberDefHelper.getRemoteDiscovery(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getRemoteListen",
            synopsis = "Gets remote listen url for provided member definition.",
            signature = "String getRemoteListen (Object memberDef)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ") 
			},
			freturn = @FunctionParamDescriptor(name = "", type = "String", desc = "remote listen url"),
			version = "5.1.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "get remote listen url",
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static String getRemoteListen (Object memberDef) {
		return ASMemberDefHelper.getRemoteListen(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "getWorkerThreadCount",
            synopsis = "Returns worker thread count for the metaspace using provided member definition.",
            signature = "int getWorkerThreadCount (Object memberDef)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "int", desc = "worker thread count for the metaspace"),
			version = "5.1.1",
			see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "returns worker thread count for the metaspace",
			cautions = "none",
			fndomain = {ACTION},
			example = ""
	)
    public static int getWorkerThreadCount (Object memberDef) {
		return ASMemberDefHelper.getWorkerThreadCount(memberDef);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setContext", 
			synopsis = "Sets context tuple of the local member.",
            signature = "void setContext (Object memberDef, Object contextTuple)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "), 
					@FunctionParamDescriptor(name = "contextTuple", type = "Object", desc = "context as Tuple. See Tuple ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "sets context tuple", 
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setContext (Object memberDef, Object contextTuple) {
		ASMemberDefHelper.setContext(memberDef, contextTuple);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setDataStore", 
			synopsis = "Sets datastore name.", 
			signature = "void setDataStore (Object memberDef, String dataStore)", 
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "), 
					@FunctionParamDescriptor(name = "dataStore", type = "String", desc = "data store name ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "Sets datastore name.", 
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setDataStore (Object memberDef, String dataStore) {
		ASMemberDefHelper.setDataStore(memberDef, dataStore);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setDiscovery", 
			synopsis = "Sets the discovery protocol with the Member.",
            signature = "void setDiscovery (Object memberDef, String discoveryUrl)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "), 
					@FunctionParamDescriptor(name = "discoveryUrl", type = "String", desc = "discovery url ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Sets the discovery protocol with the Member.", 
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setDiscovery (Object memberDef, String discoveryUrl) {
		ASMemberDefHelper.setDiscovery(memberDef, discoveryUrl);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setListen", 
			synopsis = "Sets the listen URL with the Member.", 
            signature = "void setListen (Object memberDef, String listenUrl)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "), 
					@FunctionParamDescriptor(name = "listenUrl", type = "String", desc = "listen url  ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "Sets listen url", 
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setListen (Object memberDef, String listenUrl) {
		ASMemberDefHelper.setListen(memberDef, listenUrl);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setMemberName", 
			synopsis = "Sets member name.", 
            signature = "void setMemberName (Object memberDef, String memberName)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition"), 
					@FunctionParamDescriptor(name = "memberName", type = "String", desc = "member name. Use naming convention<em> &lt;group_name&gt;.&lt;member_name&gt;</em> to <br/>enable host-aware replication in case this member is a seeder")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "sets member name", 
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setMemberName (Object memberDef, String memberName) {
		ASMemberDefHelper.setMemberName(memberDef, memberName);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setProcessName", 
			synopsis = "Sets process name.", 
            signature = "void setProcessName (Object memberDef, String processName)",
			params = {
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition"), 
					@FunctionParamDescriptor(name = "processName", type = "String", desc = "process name. Use naming convention<em> &lt;group_name&gt;.&lt;process_name&gt;</em> to <br/>enable host-aware replication in case this member is a seeder")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.6.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "sets process name", 
			cautions = "none",
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setProcessName (Object memberDef, String processName) {
		ASMemberDefHelper.setProcessName(memberDef, processName);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setRemoteDiscovery", 
			synopsis = "Sets remote discovery url.", 
            signature = "void setRemoteDiscovery (Object memberDef, String remoteDiscovery)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = " member definition"), 
					@FunctionParamDescriptor(name = "remoteDiscovery", type = "String", desc = "discovery Url")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "Sets remote discovery url",
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
			)
    public static void setRemoteDiscovery (Object memberDef, String remoteDiscovery) {
		ASMemberDefHelper.setRemoteDiscovery(memberDef, remoteDiscovery);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setRemoteListen", 
			synopsis = "Sets remote listen url.", 
            signature = "void setRemoteListen (Object memberDef, String remoteListen)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition"), 
					@FunctionParamDescriptor(name = "remoteListen", type = "String", desc = "remote listen url ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(), 
			description = "sets remote listen url",
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setRemoteListen (Object memberDef, String remoteListen) {
		ASMemberDefHelper.setRemoteListen(memberDef, remoteListen);
	}

	@com.tibco.be.model.functions.BEFunction(
			name = "setWorkerThreadCount", 
			synopsis = "Sets the number of threads to be used for invocation.",
            signature = "void setWorkerThreadCount (Object memberDef, int numWorkers)",
			params = { 
					@FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "), 
					@FunctionParamDescriptor(name = "numWorkers", type = "int", desc = "number of worker threads ")
			}, 
			freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""), 
			version = "5.1.1", 
			see = "", 
			mapper = @com.tibco.be.model.functions.BEMapper(),
			description = "Sets the number of threads to be used for invocation.", 
			cautions = "none", 
			fndomain = {ACTION}, 
			example = ""
	)
    public static void setWorkerThreadCount (Object memberDef, int numWorkers) {
		ASMemberDefHelper.setWorkerThreadCount(memberDef, numWorkers);
	}
    
	@com.tibco.be.model.functions.BEFunction(
            name = "setSecurity",
            synopsis = "Sets the security using security-config file (policy or token file)",
            signature = "void setSecurity (Object memberDef, boolean isController, String filePath)",
            params = {
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "isController", type = "boolean", desc = "join the cluster as a controller or requester "),
                    @FunctionParamDescriptor(name = "filePath", type = "String", desc = "path to the security config file. ")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.1.1",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the security .",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static void setSecurity (Object memberDef, boolean isController, String filePath) {
		ASMemberDefHelper.setSecurity(memberDef, isController, filePath);
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "setIdentityPassword",
            synopsis = "Sets the identity password for (policy or token file)",
            signature = "void setIdentityPassword (Object memberDef, String password)",
            params = {
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "password", type = "String", desc = "identity password")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the identity password.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static void setIdentityPassword (Object memberDef, String password) {
		ASMemberDefHelper.setIdentityPassword(memberDef, password);
    }
	
	@com.tibco.be.model.functions.BEFunction(
            name = "setUserName",
            synopsis = "Sets the authentication username",
            signature = "void setUserName (Object memberDef, String userName)",
            params = {
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "userName", type = "String", desc = "authentication username")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the authentication username.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static void setUserName (Object memberDef, String userName) {
		ASMemberDefHelper.setUserName(memberDef, userName);
    }

	@com.tibco.be.model.functions.BEFunction(
            name = "setPassword",
            synopsis = "Sets the authentication password",
            signature = "void setPassword (Object memberDef, String password)",
            params = {
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "password", type = "String", desc = "authentication password")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the authentication password.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static void setPassword (Object memberDef, String password) {
		ASMemberDefHelper.setPassword(memberDef, password);
    }
    
	@com.tibco.be.model.functions.BEFunction(
            name = "setDomainName",
            synopsis = "Sets the domain name for system based authentication",
            signature = "void setDomainName (Object memberDef, String domainName)",
            params = {
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "domainName", type = "String", desc = "authentication domain")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the authentication domain.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
	public static void setDomainName (Object memberDef, String domainName) {
		ASMemberDefHelper.setDomainName(memberDef, domainName);
    }
    
	@com.tibco.be.model.functions.BEFunction(
            name = "setKeyFile",
            synopsis = "Sets the certificate key file for certificate based authentication",
            signature = "void setKeyFile (Object memberDef, String keyFile)",
            params = {
                    @FunctionParamDescriptor(name = "memberDef", type = "Object", desc = "member definition "),
                    @FunctionParamDescriptor(name = "keyFile", type = "String", desc = "certificate key file")
            },
            freturn = @FunctionParamDescriptor(name = "", type = "void", desc = ""),
            version = "5.2.0",
            see = "",
			mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Sets the authentication certificate key file.",
            cautions = "none",
			fndomain = {ACTION}, 
            example = ""
    )
    public static void setKeyFile (Object memberDef, String keyFile) {
		ASMemberDefHelper.setKeyFile(memberDef, keyFile);
    }
}