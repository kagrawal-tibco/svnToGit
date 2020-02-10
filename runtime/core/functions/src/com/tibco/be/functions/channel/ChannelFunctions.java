package com.tibco.be.functions.channel;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.driver.local.LocalQueueDestination;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.Channel.State;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.be.model.functions.Enabled;

import java.util.ArrayList;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Channel",
        synopsis = "Functions to operate on Channels and Destination")
public class ChannelFunctions {

	@com.tibco.be.model.functions.BEFunction(
        name = "startChannel",
        synopsis = "Starts a channel based on the specified URI only if the channel is in stopped state.",
        signature = "boolean startChannel(String channelURI)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "channelURI", type = "String", desc = "The channel URI ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if successful else false."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Starts a channel to active mode based on the specified URI only if the channel is in stopped state.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
	public static boolean startChannel(String channelURI) {
		Channel channel = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager().getChannel(channelURI);
		ChannelManager cmgr =RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager();
		if (channel != null) {
			try {
				State state = channel.getState();
				if (state.equals(Channel.State.STOPPED)) {
					cmgr.startChannel(channelURI, ChannelManager.ACTIVE_MODE);
					return true;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return false;
	}
	@com.tibco.be.model.functions.BEFunction(
        name = "stopChannel",
        synopsis = "Stops an active channel based on the specified URI only if the channel is in started state.",
        signature = "boolean stopChannel(String channelURI)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "channelURI", type = "String", desc = "The channel URI ")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Returns true if successful else false."),
        version = "5.1.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Stops an active channel based on the specified URI only if the channel is in started state.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
	public static boolean stopChannel(String channelURI) {
		Channel channel = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager().getChannel(channelURI);
		ChannelManager cmgr =RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager();
		if (channel != null) {
			try {
				State state = channel.getState();
				if (state.equals(Channel.State.STARTED)) {
					cmgr.stopChannel(channelURI);
					return true;
				} 
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

    @com.tibco.be.model.functions.BEFunction(
        name = "getAllDestinations",
        synopsis = "Returns an array of all active and suspended destination URI's that are bound to this rule session",
        signature = "String[] getAllDestinations()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Destinations URI's that are enabled for the current RuleSession, are started, are active or suspended."),
        version = "3.0.1 HF5",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of all active and suspended destinations URI's that are bound to this RuleSession.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String[] getAllDestinations() {
        RuleSessionConfig.InputDestinationConfig config[] = RuleSessionManager.getCurrentRuleSession().getConfig().getInputDestinations();
        ArrayList destinations = new ArrayList();
        for (int i=0; i<config.length;i++) {
             destinations.add(config[i].getURI());
        }

        return (String[]) destinations.toArray(new String[destinations.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getActiveDestinations",
        synopsis = "Returns an array of active destination uri's that are bound to this RuleSession",
        signature = "String[] getActiveDestinations()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Destinations URI's that are enabled for the current RuleSession, are started, and are not suspended."),
        version = "2.0.0 HF3",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of active destinations URI's that are bound to this RuleSession.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String[] getActiveDestinations() {
        RuleSessionConfig.InputDestinationConfig config[] = RuleSessionManager.getCurrentRuleSession().getConfig().getInputDestinations();
        ChannelManager cmgr = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager();

        ArrayList destinations = new ArrayList();
        for (int i=0; i<config.length;i++) {

            Channel.Destination dst = cmgr.getDestination(config[i].getURI());
            if (!dst.isSuspended()) {
                destinations.add(config[i].getURI());
            }
        }

        return (String[]) destinations.toArray(new String[destinations.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getSuspendedDestinations",
        synopsis = "Returns an array of suspended destination uri's that are bound to this rule session",
        signature = "String[] getSuspendedDestinations()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Destinations URI's that are enabled for the current rule session, are started, and are suspended."),
        version = "3.0.1 HF5",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of suspended destinations URI's that are bound to this rule session.",
        cautions = "none",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static String[] getSuspendedDestinations() {
        RuleSessionConfig.InputDestinationConfig config[] = RuleSessionManager.getCurrentRuleSession().getConfig().getInputDestinations();
        ChannelManager cmgr = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getChannelManager();

        ArrayList destinations = new ArrayList();
        for (int i=0; i<config.length;i++) {

            Channel.Destination dst = cmgr.getDestination(config[i].getURI());
            if (dst.isSuspended()) {
                destinations.add(config[i].getURI());
            }
        }

        return (String[]) destinations.toArray(new String[destinations.size()]);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "bindDestination",
        synopsis = "Bind a pre-existing destination to a rule session and start listening on it.",
        enabled = @Enabled(value=false),
        signature = "void bindDestination(String destinationURI, String properties) throws Exception",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationURI", type = "String", desc = "The destination URI that is bound to this rule session"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "properties", type = "String", desc = "It is optional parameter meaning you can pass null. When you pass null, default it will run in activemode")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0.0 HF3",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Bind a pre-existing destination to a rule session and start listening on it.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void bindDestination(String destinationURI, String properties)  {

        try {
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            RuleServiceProvider rsp = session.getRuleServiceProvider();
            RuleSessionConfig.InputDestinationConfig config[] = session.getConfig().getInputDestinations();

            ChannelManager chManager = rsp.getChannelManager();
            Channel.Destination dest = chManager.getDestination(destinationURI);
            if (dest == null) {
                throw new Exception ("Invalid Destination[" + destinationURI + "] specified for binding.");
            }
            for (int i=0; i<config.length;i++) {
                if (destinationURI.equalsIgnoreCase(config[i].getURI())) {
                    throw new Exception ("Invalid Destination[" + destinationURI + "] specified for binding.");
                }
            }

            //SS : TODO Need to update session's input destination config.
            dest.bind(session);
        }
        catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "unbindDestination",
        synopsis = "Unbind a pre-existing destination from the rule session and stop listening to its messages.",
        enabled = @Enabled(value=false),
        signature = "void unbindDestination(String destinationURI) throws Exception",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationURI", type = "String", desc = "The destination URI that is bound to this rule session")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "none", desc = ""),
        version = "2.0.0 HF3",
        see = "bindDestination",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Unbind a pre-existing destination from the rule session and stop listening to its messages.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void unbindDestination(String destinationURI)  {

        try {
            RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
            ChannelManager chManager = rsp.getChannelManager();
            Channel.Destination dest = chManager.getDestination(destinationURI);
            if (dest == null) {
                throw new Exception ("Invalid Destination[" + destinationURI + "] specified for unbinding.");
            }
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
        //there is no unbind - will have to work on this
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "suspendDestination",
        synopsis = "Suspend a pre-existing destination.",
        signature = "void suspendDestination(String destinationURI) throws Exception",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationURI", type = "String", desc = "The destination URI that is to be suspended")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0.0 HF3",
        see = "resumeDestination",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Suspend a pre-existing destination.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void suspendDestination(String destinationURI) {
        try {
            RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
            ChannelManager chManager = rsp.getChannelManager();
            Channel.Destination dest = chManager.getDestination(destinationURI);
            if (dest == null) {
                throw new Exception ("Invalid Destination[" + destinationURI + "] specified for suspending.");
            }
            ((AbstractDestination)dest).suspendEx();
        }
        catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "resumeDestination",
        synopsis = "Resume a pre-existing destination.",
        signature = "void resumeDestination(String destinationURI) throws Exception",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationURI", type = "String", desc = "The destination URI that is to be resumed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0.0 HF3",
        see = "suspendDestination",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Resume a pre-existing destination.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void resumeDestination(String destinationURI)  {
        try {
            RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
            ChannelManager chManager = rsp.getChannelManager();
            Channel.Destination dest = chManager.getDestination(destinationURI);
            if (dest == null) {
                throw new Exception ("Invalid Destination[" + destinationURI + "] specified for resuming");
            }
            ((AbstractDestination)dest).resumeEx();
        } catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getQueueDepth",
        synopsis = "Get the queue depth for this local destination.",
        signature = "int getQueueDepth(String destinationURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "localDestinationURI", type = "String", desc = "The local channel's destination URI whose queue depth is being requested.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "A value &gt;= 0 for local channel's destination, else -1 if any other type of destination is specified"),
        version = "2.0.0 HF3",
        see = "suspendDestination",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the queue depth for this local destination.",
        cautions = "",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int getQueueDepth(String localDestinationURI)  {
        try {
            RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
            ChannelManager chManager = rsp.getChannelManager();
            Channel.Destination dest = chManager.getDestination(localDestinationURI);
            if (dest == null) {
                throw new Exception ("Invalid Destination[" + localDestinationURI + "] specified for getQueueDepth");
            }

            if (!(dest instanceof LocalQueueDestination )) return -1;
            LocalQueueDestination lqdest = (LocalQueueDestination) dest;
            return lqdest.getQueueDepth();
        }
        catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getQueueCapacity",
        synopsis = "Return the remaining queue capacity for this local destination.",
        signature = "int getQueueCapacity(String destinationURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "destinationURI", type = "String", desc = "The local channel's destination URI whose queue capacity is being requested.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "A value greater than or equal to zero (0) for local channel's destination; else -1 if any other type of destination is specified"),
        version = "2.0.0 HF3",
        see = "suspendDestination",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Return the remaining queue capacity for this local destination",
        cautions = "",
        fndomain = {ACTION, CONDITION},
        example = ""
    )
    public static int getQueueCapacity(String localDestinationURI)  {
        try {
            RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
            ChannelManager chManager = rsp.getChannelManager();
            Channel.Destination dest = chManager.getDestination(localDestinationURI);
            if (dest == null) {
                throw new Exception ("Invalid Destination[" + localDestinationURI + "] specified for getQueueCapacity");
            }

            if (!(dest instanceof LocalQueueDestination )) return -1;
            LocalQueueDestination lqdest = (LocalQueueDestination) dest;
            return lqdest.getQueueCapacity();
        }
        catch (java.lang.Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
