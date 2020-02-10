package com.tibco.be.functions.bw;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.pe.core.Engine;
import com.tibco.pe.plugin.EngineContext;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 6, 2007
 * Time: 3:47:12 AM
 * To change this template use File | Settings | File Templates.
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BusinessWorks",
        category = "BusinessWorks",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.BusinessWorks", value=false),
        synopsis = "Functions for BusinessWorks integration")
public class BWSupport {

    static {
        // Forces loading of java.util.TimeZone$Display for BE-BW.
        java.util.TimeZone.getDefault().getDisplayName();
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "init",
        synopsis = "Initializes the Process Engine. Use of this function is not required. It is provided as a convenience to start BusinessWorks before the first process invocation.",
        signature = "void init()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Initializes the Process Engine.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void init() throws RuntimeException {
        try {
            PEContainer.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "startProcess",
        synopsis = "Starts a BusinessWorks process and executes asynchronously. Also initializes the process engine\nif it has not been already initialized. Upon completion invokes the RuleFunction\nto notify the return values. The BusinessWorks process that needs to be started should be a non-process starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvents\nEvent Schema.",
        signature = "long startProcess(String processName, Event input, String ruleFnURI, Object context)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processName", type = "String", desc = "The BusinessWorks process name. The process must not be a process starter"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "input", type = "Event", desc = "The Event which is mapped one-to-one to the Input (Start Activity Input Type) BusinessWorks process. It can also be null"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "ruleFnURI", type = "String",
                    desc = "URI of the rule function to invoke upon completion of the BusinessWorks process. "
                        + "The completion status could be success or failure.\n"
                        + "The rule function must have the following signature and must be action only:\n"
                        + "<p><code><b>void ruleFn(long jobId, int status, Event outputEvent, Object closure);</b></code>\n"
                        + "<ul><li>jobId - The Id of the BusinessWorks Job that was completed</li>\n"
                        + "<li>status - The status of the Job</li>\n"
                        + "<li>outputEvent - The output of the BusinessWorks process. The End Activity of the BusinessWorks process must return an Event, or null</li>\n"
                        + "<li>closure - The closure object that you passed along with the startProcess.</li></ul>\n"
                        + "</p>"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "context", type = "Object", desc = "as a parameter back to the rule function")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "long", desc = "A successful Job Id, which can be used for query status or cancellation of the Job"),
        version = "2.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Starts a BusinessWorks process and executes asynchronously. Also initializes the process engine\nif it has not been already initialized. Upon completion invokes the RuleFunction\nto notify the return values. The BusinessWorks process that needs to be started should be a non-process starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvents\nEvent Schema.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static long startProcess(String processName, Event input, String ruleFnURI, Object context) {
        try {
            if (context != null) {
                if (context instanceof Event || context instanceof Concept) {
                    throw new RuntimeException("Cannot pass Events or Concepts as part of closure");
                }
            }
            return PEContainer.startProcess(processName, input, ruleFnURI, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "invokeProcess",
        synopsis = "Invokes a BusinessWorks process, executes synchronously and returns a SimpleEvent.\nAlso initializes the process engine if it has not been already initialized.\nThe BusinessWorks process that needs to be started should be a non-process starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvents\nEvent Schema. An AdvisoryEvent will be created and asserted should the invoked BusinessWorks process fails\nor times out.",
        signature = "SimpleEvent invokeProcess(String processName, Event input, long timeout)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processName", type = "String", desc = "The BusinessWorks process name. The process must not be a process starter"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "input", type = "Event", desc = "The Event which is mapped one-to-one to the Input BusinessWorks process. It can also be null"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "long", desc = "The amount of time in milliseconds to wait for the process to complete. 0 means indefinite.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "SimpleEvent", desc = "A SimpleEvent as per the process definition's output transformed. If the output is a null, then it is null"),
        version = "2.2.0",
        see = "startProcess",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Invokes a BusinessWorks process, executes synchronously and returns a SimpleEvent.\nAlso initializes the process engine if it has not been already initialized.\nThe BusinessWorks process that needs to be started should be a non-process starter\nand must return an Event in XML - meaning the End Activity's input must be selected from the BusinessEvents\nEvent Schema. An AdvisoryEvent will be created or asserted should the invoked BusinessWorks process fails\nor times out.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Event invokeProcess(String processName, Event input, long timeout) {
        try {
            return PEContainer.invokeProcess(processName, input, timeout);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "cancelProcess",
        synopsis = "Cancels a BusinessWorks process by this jobId",
        signature = "void cancelProcess(long jobId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "long", desc = "The job identifier returned by the startProcess that needs to be canceled.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A status describing which job was cancelled in which engine."),
        version = "2.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Cancels a BusinessWorks process by this jobId",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static String cancelProcess(long id) throws RuntimeException {
        try {
            return PEContainer.cancelProcess(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getClosure",
        synopsis = "Returns the closure specified in the startProcess",
        signature = "Object getClosure()",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = "")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The closure object specified in the startProcess"),
        version = "2.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the closure as specified in the BW startProcess",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getClosure() throws RuntimeException {
        try {
            if (Thread.currentThread() instanceof BEManagedThread) {
                BEManagedThread cur= (BEManagedThread) Thread.currentThread();
                Runnable curJob= cur.getCurrentJob();
                if (curJob instanceof BEBWJob) {
                    BEBWJob bwJob= (BEBWJob) curJob;
                    return bwJob.getClosure();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "notifyWaitActivity",
        synopsis = "notifies a WaitActivity of Job which matches the Key",
        signature = "void notifyWaitActivity(String groupName, String key, int timeOut, Object payload",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.BusinessWorks.asyncSupport", value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "groupName", type = "The", desc = "fully qualified Wait-Notify shared configuration name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "The", desc = "key on which Wait Activity is waiting. See BW's documentation for Wait Activity"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeOut", type = "How", desc = "0 means indefinite, +ve value in milliseconds."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "payload", type = "The", desc = "shared configuration.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "-  Use notifyWaitActivity, when BW invokeBE RuleFunction in an Asynchronous mode. Typical usage\nscenario is\nTime T1=>Job1:BWEventSource->BEInvokeRule(ASync)->Wait(General Activity->Wait Key:nnnn)->ResponseTosource\nTime T2=>BERuleMatching on SomeEvent Action->Notify Job1,Key:nnnn.",
        cautions = "The user has to be aware of the following limitation and should code accordingly\n1.> The shared-configuration must use event/concept schemas as Element-Reference only. No other\ntypes are supported\n2.> The Waits, and Notifies are not persistable, meaning the wait-notify states are\nnot recoverable on failure.",
        fndomain = {ACTION},
        example = ""
    )
    public static void notifyWaitActivity(String groupName, String key, long timeOut, Object payload) throws RuntimeException {
        try {
            EngineContext ec = Engine.getEngineContext();
            if (ec == null) {
                throw new RuntimeException("BW is not running ");
            }

            XiNode doc = XiFactoryFactory.newInstance().createDocument();
            com.tibco.xml.xdata.xpath.Variable pVar = null;

            if (payload instanceof SimpleEvent) {
                XiNode output = doc.appendElement(((SimpleEvent)payload).getExpandedName());
                ((SimpleEvent)payload).toXiNode(output);
                pVar = new com.tibco.xml.xdata.xpath.Variable(doc);
            }
            else if (payload instanceof Concept) {
                XiNode output = doc.appendElement(((Concept)payload).getExpandedName());
                ((Concept)payload).toXiNode(output);
                pVar = new com.tibco.xml.xdata.xpath.Variable(doc);
            }
            else {
                throw new RuntimeException("Invalid Object type passed - Must be concept or event :" + payload.getClass());
            }
            ec.wakeUp(groupName, key, timeOut, pVar);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return;
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "shutdown",
        synopsis = "Shuts down the Process Engine.",
        signature = "void shutdown()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Shuts down the Process Engine.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
    public static void shutdown() {
        try {
            PEContainer.shutdown();
        } catch (Exception e) {
            // e.printStackTrace();
            // throw new RuntimeException(e);
        }
    }

    public static  Object getStaticField(Class cls, Class fieldType) throws Exception{
           Field[] flds = cls.getDeclaredFields();
        for (int i = 0; i < flds.length; i++) {
            Field f = flds[i];
            if ((f.getType() == fieldType) && Modifier.isStatic(f.getModifiers())) {
                f.setAccessible(true);
                return f.get(null);
            }
        }
        return null;
    }

	public static Object getField(Class cls, Class fieldType, Object inst) throws Exception {
        Field[] flds = cls.getDeclaredFields();
        for (int i = 0; i < flds.length; i++) {
            Field f = flds[i];
            if ((f.getType() == fieldType)) {
                f.setAccessible(true);
                // System.out.println("** found **");
                return f.get(inst);
            }
        }
        return null;
    }

	public static Object getFieldByName(Class cls, String name, Object inst)
			throws Exception {
		Object ret = null;
		Field[] flds = cls.getDeclaredFields();
		for (int i = 0; i < flds.length; i++) {
			Field f = flds[i];
			if (name.equals(f.getName())) {
				f.setAccessible(true);
				ret = f.get(inst);
			}
		}
		return ret;
	}

	public static void setFieldValue(Class cls, String name, Object inst, Object fldObj) throws Exception {
		Field[] flds = cls.getDeclaredFields();
		for (int i = 0; i < flds.length; i++) {
			Field f = flds[i];
			if (name.equals(f.getName())) {
				f.setAccessible(true);
				f.set(inst, fldObj);
				return;
			}
		}
	}

	public static void setFieldValue(Class cls, Class name, Object inst, Object fldObj) throws Exception {
		Field[] flds = cls.getDeclaredFields();
		for (int i = 0; i < flds.length; i++) {
			Field f = flds[i];

			if (f.getType().equals(name)) {
				f.setAccessible(true);
				f.set(inst, fldObj);
				return;
			}
		}
	}
}
