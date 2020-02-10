/**
 * 
 */
package com.tibco.be.ws.functions.process;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.ws.process.CommonProcessUtil;
import com.tibco.be.ws.process.FlowNodeHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

/**
 * Catalog Functions to work with Flow nodes under Process EMF models.
 * 
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Process.FlowNode",
        synopsis = "Functions to work with Flow nodes under Process EMF models.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Process.FlowNode", value=true))

public class WebStudioFlowNodeFunctions {

	@com.tibco.be.model.functions.BEFunction(
        name = "getLanes",
        signature = "Object getLanes(Object object)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "object", type = "Object", desc = "Sequence/Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Lane Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Lane details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getLanes(Object object) {
		if (!(object instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence/Flow Node EObject");
		} else {
			EObject eObject = (EObject) object;
			return FlowNodeHelper.getLanes(eObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getAllFlowNodes",
		signature = "Object[] getAllFlowNodes(Object processWrapper)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Sequence Flows."),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrieves all Flow nodes.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAllFlowNodes(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return FlowNodeHelper.getAllFlowNodes(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getAllTextAnnotations",
		signature = "Object[] getAllTextAnnotations(Object processWrapper)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Text Annotations."),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrieves all Text Annotations.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAllTextAnnotations(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return FlowNodeHelper.getAllTextAnnotations(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeDetails",
        signature = "Object getFlowNodeDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lane", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Flow Node Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getFlowNodeDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getTextAnnotationValue",
        signature = "String getTextAnnotationValue(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Text Annotation EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Text Annotation Value."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Text Annotation value.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getTextAnnotationValue(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getTextAnnotationValue(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeVersion",
        signature = "int getFlowNodeVersion(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Flow Node Version."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node Version.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static int getFlowNodeVersion(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeVersion(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeCheckPoint",
        signature = "boolean getFlowNodeCheckPoint(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Flow Node Check point."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node check point value.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static boolean getFlowNodeCheckPoint(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeCheckPoint(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeLabel",
        signature = "String getFlowNodeLabel(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Flow node label."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node Label.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getFlowNodeLabel(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeLabel(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeToolId",
        signature = "String getFlowNodeToolId(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Flow node tool Id."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node tool Id.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getFlowNodeToolId(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeToolId(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeIncomingSequenceDetails",
        signature = "Object getFlowNodeIncomingSequenceDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Incoming Sequence Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node incoming sequence details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getFlowNodeIncomingSequenceDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeSequences(flowNodeObject, true);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeOutgoingSequenceDetails",
        signature = "Object getFlowNodeOutgoingSequenceDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Incoming Sequence Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Flow Node outgoing sequence details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getFlowNodeOutgoingSequenceDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow Node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeSequences(flowNodeObject, false);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodePoints",
        signature = "Object getFlowNodePoints(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Flow node point details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Flow Node point.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getFlowNodePoints(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodePoint(flowNodeObject, true);
		}
	}
		
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeLabelPoints",
        signature = "Object getFlowNodeLabelPoints(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Flow node label point details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Flow Node label point.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getFlowNodeLabelPoints(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodePoint(flowNodeObject, false);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeInputMapper",
        signature = "String getFlowNodeInputMapper(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Flow Node Input Mapper XSLT."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Flow Node input mapper XSLT",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getFlowNodeInputMapper(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeInputMapperXslt(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeOutputMapper",
        signature = "String getFlowNodeOutputMapper(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Flow node Output Mapper XSLT."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Flow Node output mapper XSLT.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getFlowNodeOutputMapper(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeOutputMapperXslt(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "isTimerEnabled",
        signature = "boolean isTimerEnabled(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "True, if timer is enabled"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Checks if Timer is enabled.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static boolean isTimerEnabled(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.isTimerEnabled(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getTimerDetails",
        signature = "Object getTimerDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Timer Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves timer related details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getTimerDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getTimerDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLoopCharacteristics",
        signature = "Object getLoopCharacteristics(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Loop Characteristics Object."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves loop characteristic object.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getLoopCharacteristics(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getLoopCharacteristics(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLoopCharacteristicDetails",
        signature = "Object getLoopCharacteristicDetails(Object loopCharacteristic)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loopCharacteristic", type = "Object", desc = "Loop Characteristics EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Loop Characteristics Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Loops characteristic details",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getLoopCharacteristicDetails(Object loopCharacteristic) {
		if (!(loopCharacteristic instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Loop Characteristics EObject");
		} else {
			EObject loopCharacteristicObject = (EObject) loopCharacteristic;
			return FlowNodeHelper.getLoopCharacteristicDetails(loopCharacteristicObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getFlowNodeType",
        signature = "Object getFlowNodeType(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Flow Node Type."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the flow node type",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getFlowNodeType(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getFlowNodeType(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getImplementationURIList",
        signature = "String[] getImplementationURIList(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "List of implementation URI's."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves Implementation URI's for Business Rule Flow Node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String[] getImplementationURIList(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getImplementationURIList(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getServiceDetails",
        signature = "Object getServiceDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Service Object details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the details for Web Service Flow node.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getServiceDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getServiceDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getKeyExpression",
        signature = "String getKeyExpression(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Key Expression for Receive Message"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the Key Expression for Receive Message Flow Node.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getKeyExpression(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getKeyExpression(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getMessageStarterList",
        signature = "Object[] getMessageStarterList(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "List of Message starter attributes"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the list of Message Starters for End Event node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getMessageStarterList(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getMessageStarterList(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getMessageStarterDetails",
        signature = "Object getMessageStarterDetails(Object messageStarterObject)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageStarterObject", type = "Object", desc = "Message Starter EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Message starter details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the details for Message Starters for End Event",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getMessageStarterDetails(Object messageStarterObject) {
		if (!(messageStarterObject instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Message Starter EObject");
		} else {
			EObject messageStarterEObject = (EObject) messageStarterObject;
			return FlowNodeHelper.getMessageStarterDetails(messageStarterEObject);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getPriority",
        signature = "int getPriority(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Priority"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the priority of the Start/End Node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static int getPriority(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getPriority(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getGatewayDirection",
        signature = "String getGatewayDirection(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Direction"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve direction for Gateway flow node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getGatewayDirection(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getGatewayDirection(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getExclusiveGatewayDetails",
        signature = "Object getExclusiveGatewayDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Exclusive Gateway details"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves details for exclusive gateway",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getExclusiveGatewayDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getExclusiveGatewayDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getParallelGatewayDetails",
        signature = "Object getParallelGatewayDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Parallel Gateway details"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves details for parallel gateway",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getParallelGatewayDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getParallelGatewayDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getDocumentation",
        signature = "Object getDocumentation(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Documentation details"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves documentation related to the flow node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getDocumentation(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getDocumentationDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getEventDefinitionDetails",
        signature = "Object getEventDefinitionDetails(Object flowNode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowNode", type = "Object", desc = "Flow Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Event Definition Details"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves the event definition details",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getEventDefinitionDetails(Object flowNode) {
		if (!(flowNode instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Flow node EObject");
		} else {
			EObject flowNodeObject = (EObject) flowNode;
			return FlowNodeHelper.getEventDefinitionDetails(flowNodeObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setEventDefinitionType",
        signature = "void setEventDefinitionType(Object processObjWrapper, Object flowElementWrapper, String eventDefinitionType, String eventDefId, String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventDefinitionType", type = "String", desc = "Event Definition Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventDefId", type = "String", desc = "Event Definition Id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Task Name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Flow Element Event definition Type",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setEventDefinitionType(Object processObjWrapper, Object flowElementWrapper, String eventDefinitionType, String eventDefId, String name) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setEventDefinitionType(processModelWrapper, flowNodeWrapper, eventDefinitionType, eventDefId, name);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "createFlowNode",
        signature = "Object createFlowNode(Object processObjWrapper, String flowElementType, String laneIds)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementType", type = "String", desc = "Flow Element type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "laneIds", type = "String", desc = "Lane Id's the flow element belong to")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Flow Element Wrapper"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new Flow Element",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object createFlowNode(Object processObjWrapper, String flowElementType, String laneIds) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		String[] laneIdList = laneIds.split(",");
		EObjectWrapper<EClass, EObject> flowNodeElement = FlowNodeHelper.createFlowNode(processModelWrapper, flowElementType, laneIdList);
		
		return flowNodeElement;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "createTextAnnotationNode",
        signature = "Object createTextAnnotationNode(Object processObjWrapper, String flowElementType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementType", type = "String", desc = "Flow Element type")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Flow Element Wrapper"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new Text Annotation Element",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object createTextAnnotationNode(Object processObjWrapper, String flowElementType) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		EObjectWrapper<EClass, EObject> flowNodeElement = FlowNodeHelper.createTextAnnotationNode(processModelWrapper, flowElementType);
		
		return flowNodeElement;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowElementBaseAttributes",
        signature = "void setFlowElementBaseAttributes(Object processObjWrapper, Object flowElementWrapper, int uniqueId, String name, String resourcePath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uniqueId", type = "int", desc = "Unique Id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resourcePath", type = "String", desc = "Resource Path"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementId", type = "String", desc = "Flow Element Id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Flow Element Base Attributes",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowElementBaseAttributes(Object processObjWrapper, Object flowElementWrapper, int uniqueId, String name, String resourcePath, String flowElementId) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setFlowElementBaseAttributes(processModelWrapper, flowNodeWrapper, uniqueId, name, resourcePath, flowElementId);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowElementBaseExtentionAttributes",
        signature = "void setFlowElementBaseExtentionAttributes(Object flowElementWrapper, int version, String label, boolean checkpoint)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "version", type = "int", desc = "Version"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "label", type = "String", desc = "Label"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "checkpoint", type = "Boolean", desc = "Check Point"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "toolId", type = "String", desc = "Tool Id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Flow Element Base Extention Attributes",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowElementBaseExtentionAttributes(Object flowElementWrapper, int version, String label, boolean checkpoint, String toolId) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(flowNodeWrapper, version, label, checkpoint, toolId);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowNodePoints",
        signature = "void setFlowNodePoints(Object flowElementWrapper, double x, double y)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "x", type = "double", desc = "X"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "y", type = "double", desc = "Y")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Flow Element's Node Points",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowNodePoints(Object flowElementWrapper, double x, double y) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setFlowNodePoint(flowNodeWrapper, x, y, true);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowNodeLabelPoints",
        signature = "void setFlowNodeLabelPoints(Object flowElementWrapper, double x, double y)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "x", type = "double", desc = "X"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "y", type = "double", desc = "Y")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Flow Element's Label Points",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowNodeLabelPoints(Object flowElementWrapper, double x, double y) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setFlowNodePoint(flowNodeWrapper, x, y, false);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setLoopCharacteristics",
        signature = "void setLoopCharacteristics(Object flowElementWrapper, String type, boolean testBefore, String loopCondition, int loopCount, boolean isSequential, String loopCardinality, String behavior, String completionCondition)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "testBefore", type = "boolean", desc = "Test Before"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loopCondition", type = "String", desc = "Loop Condition"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loopCount", type = "String", desc = "Loop Count"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isSequential", type = "boolean", desc = "Is Sequential"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "loopCardinality", type = "String", desc = "Loop Cardinality"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "behavior", type = "String", desc = "Behavior"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "completionCondition", type = "String", desc = "Completion Condition")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Flow Elements Loop Characteristics",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setLoopCharacteristics(Object flowElementWrapper, String type, boolean testBefore, String loopCondition, String loopCount, boolean isSequential, String loopCardinality, String behavior, String completionCondition) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setLoopCharacteristics(flowNodeWrapper, type, testBefore, loopCondition, loopCount, isSequential, loopCardinality, behavior, completionCondition);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setTimeout",
        signature = "void setTimeout(Object flowElementWrapper, String expression, String event, String unit)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "expression", type = "String", desc = "Expression"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "event", type = "String", desc = "Event"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "unit", type = "String", desc = "Unit")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Timeout for Flow Elements",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setTimeout(Object flowElementWrapper, String expression, String event, String unit) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setTimeout(flowNodeWrapper, expression, event, unit);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowNodeInputMapperXslt",
        signature = "void setFlowNodeInputMapperXslt(Object flowElementWrapper, String xslt)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt", type = "String", desc = "Xslt")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Flow element input mapper xslt",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowNodeInputMapperXslt(Object flowElementWrapper, String xslt) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setFlowNodeInputMapperXslt(flowNodeWrapper, xslt);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowNodeOutputMapperXslt",
        signature = "void setFlowNodeOutputMapperXslt(Object flowElementWrapper, String xslt)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "xslt", type = "String", desc = "Xslt")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Flow element output mapper xslt",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowNodeOutputMapperXslt(Object flowElementWrapper, String xslt) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setFlowNodeOutputMapperXslt(flowNodeWrapper, xslt);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setMessageStarters",
        signature = "void setMessageStarters(Object flowElementWrapper, String messageStarterId, boolean replyTo, boolean consume)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "messageStarterId", type = "String", desc = "Message Starter Id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "replyTo", type = "boolean", desc = "Reply To"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "consume", type = "boolean", desc = "Consume")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the Message starters in the flow node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setMessageStarters(Object flowElementWrapper, String messageStarterId, boolean replyTo, boolean consume) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setMessageStarters(flowNodeWrapper, messageStarterId, replyTo, consume);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setServiceDetails",
        signature = "void setServiceDetails(Object flowElementWrapper, String service, String port, String operation, String soapAction, long timeout, String endPoint, String bindingType)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "service", type = "String", desc = "Service"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "port", type = "String", desc = "Port"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "operation", type = "String", desc = "Operation"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "soapAction", type = "String", desc = "Soap Action"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "long", desc = "Timeout"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "endPoint", type = "String", desc = "End Point"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "bindingType", type = "String", desc = "Binding Type")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the service details for the flow element",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setServiceDetails(Object flowElementWrapper, String service, String port, String operation, String soapAction, long timeout, String endPoint, String bindingType) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setServiceDetails(flowNodeWrapper, service, port, operation, soapAction, timeout, endPoint, bindingType);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setImplementationURIs",
        signature = "void setImplementationURIs(Object flowElementWrapper, String uri, boolean isDeployed)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "Url"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isDeployed", type = "boolean", desc = "Is Deployed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set Implementation Urls for the flow element",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setImplementationURIs(Object flowElementWrapper, String uri, boolean isDeployed) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setImplementationURIs(flowNodeWrapper, uri, isDeployed);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setPriority",
        signature = "void setPriority(Object flowElementWrapper, int priority)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "priority", type = "int", desc = "Priority")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the priority",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setPriority(Object flowElementWrapper, int priority) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setPriority(flowNodeWrapper, priority);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setKeyExpression",
        signature = "void setKeyExpression(Object flowElementWrapper, String expressions)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "expressions", type = "String", desc = "Key Expression")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's the key expression",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setKeyExpression(Object flowElementWrapper, String expressions) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setKeyExpression(flowNodeWrapper, expressions);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setGatewayDirection",
		signature = "void setGatewayDirection(Object flowElementWrapper, String direction)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "direction", type = "String", desc = "Gateway direction")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set's the gateway direction",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void setGatewayDirection(Object flowElementWrapper, String direction) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}

		FlowNodeHelper.setGatewayDirection(flowNodeWrapper, direction);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setExclusiveGateway",
		signature = "void setExclusiveGateway(Object processObjWrapper, Object flowElementWrapper, String defaultSequence, String transformation, String sequenceId)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "defaultSequence", type = "String", desc = "Default Sequence"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "transformation", type = "String", desc = "Transformation"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceId", type = "String", desc = "Sequence Id")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set's the exclusive gateway details",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void setExclusiveGateway(Object processObjWrapper, Object flowElementWrapper, String defaultSequence, String transformation, String sequenceId) {
		EObjectWrapper<EClass, EObject> processNodeWrapper = null;
		if (processObjWrapper instanceof EObjectWrapper) {
			processNodeWrapper = (EObjectWrapper<EClass, EObject>) processObjWrapper;
		}

		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}

		FlowNodeHelper.setExclusiveGateway(processNodeWrapper, flowNodeWrapper, defaultSequence, transformation, sequenceId);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setParallelGateway",
		signature = "void setParallelGateway(Object flowElementWrapper, String joinRuleFunction, String mergeExpression, String forkRuleFunction)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "joinRuleFunction", type = "String", desc = "Join rule function"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "mergeExpression", type = "String", desc = "Merge Expression"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "forkRuleFunction", type = "String", desc = "fork Rule function")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set's the parallel gateway details",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void setParallelGateway(Object flowElementWrapper, String joinRuleFunction, String mergeExpression, String forkRuleFunction) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}

		FlowNodeHelper.setParallelGateway(flowNodeWrapper, joinRuleFunction, mergeExpression, forkRuleFunction);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setTextAnnotationValue",
		signature = "void setTextAnnotationValue(Object processObjWrapper, String annotationValue)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "annotationValue", type = "String", desc = "Annotation Value")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set's the text annotation value",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static void setTextAnnotationValue(Object annotationElementWrapper, String annotationValue) {
		EObjectWrapper<EClass, EObject> annotationNodeWrapper = null;
		if (annotationElementWrapper instanceof EObjectWrapper) {
			annotationNodeWrapper = (EObjectWrapper<EClass, EObject>) annotationElementWrapper;
		}
		
		FlowNodeHelper.setTextAnnotationValue(annotationNodeWrapper, annotationValue);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setDocumentation",
        signature = "void setDocumentation(Object processObjWrapper, Object flowElementWrapper, String docId, String docText, String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "flowElementWrapper", type = "Object", desc = "Flow Element EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "docId", type = "String", desc = "Documentation Id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "docText", type = "String", desc = "Documentation Text"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Documentation element to flow node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setDocumentation(Object processObjWrapper, Object flowElementWrapper, String docId, String docText, String name) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> flowNodeWrapper = null;
		if (flowElementWrapper instanceof EObjectWrapper) {
			flowNodeWrapper = (EObjectWrapper<EClass, EObject>) flowElementWrapper;
		}
		
		FlowNodeHelper.setDocumentation(processModelWrapper, flowNodeWrapper, docId, docText, name);
	}
}
