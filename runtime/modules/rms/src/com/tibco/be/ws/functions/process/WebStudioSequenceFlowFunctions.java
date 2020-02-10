/**
 * 
 */
package com.tibco.be.ws.functions.process;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.ws.process.CommonProcessUtil;
import com.tibco.be.ws.process.SequenceFlowHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

/**
 * Catalog Functions to interact with Sequence Flows under Process EMF models
 * 
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Process.SequenceFlow",
        synopsis = "Functions to work with Sequence Flows under Process EMF models.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Process.SequenceFlow", value=true))

public class WebStudioSequenceFlowFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getAllSequenceFlows",
		signature = "Object[] getAllSequenceFlows(Object processWrapper)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Sequence Flows."),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrieves all Sequences Flows.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAllSequenceFlows(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return SequenceFlowHelper.getAllSequenceFlows(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getAllAssociations",
		signature = "Object[] getAllAssociations(Object processWrapper)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Association Flows."),
		version = "5.2.0",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Retrieves all Association Flows.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getAllAssociations(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return SequenceFlowHelper.getAllAssociations(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getSequenceDetails",
        signature = "Object getSequenceDetails(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Flow EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Sequence Flow Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Sequence Flow details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getSequenceDetails(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getSequenceDetails(sequenceObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getStartPoints",
        signature = "Object getStartPoints(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Flow EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Sequence Flow Start point details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Sequence Flow start point details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getStartPoints(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getSequencePoints(sequenceObject, true);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getEndPoints",
        signature = "Object getEndPoints(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Flow EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Sequence Flow End point details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Sequence Flow End point details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getEndPoints(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getSequencePoints(sequenceObject, false);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getBendPointList",
        signature = "Object[] getBendPointList(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Flow EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Bend Point List."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieve the list of bend points associated with the sequence.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getBendPointList(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getBendPointList(sequenceObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getBendPoints",
        signature = "Object getBendPoints(Object bendPoint)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Bend Point EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Bend point details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Bend point details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getBendPoints(Object bendPoint) {
		if (!(bendPoint instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Bend Point EObject");
		} else {
			EObject bendPointObject = (EObject) bendPoint;
			return SequenceFlowHelper.getBendPoints(bendPointObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getSourceReferences",
        signature = "Object getSourceReferences(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Flow EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Sequence source reference details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Sequence Source reference details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getSourceReferences(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getSequenceReferences(sequenceObject, true);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getTargetReferences",
        signature = "Object getTargetReferences(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Flow EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Sequence target reference details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Sequence Target reference details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getTargetReferences(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getSequenceReferences(sequenceObject, false);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getDocumentation",
        signature = "Object getDocumentation(Object sequence)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequence", type = "Object", desc = "Sequence Node EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Documentation details"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves documentation related to the sequence node",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getDocumentation(Object sequence) {
		if (!(sequence instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Sequence Flow EObject");
		} else {
			EObject sequenceObject = (EObject) sequence;
			return SequenceFlowHelper.getDocumentationDetails(sequenceObject);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "createSequenceFlow",
        signature = "Object createSequenceFlow(Object processObjWrapper, String laneIds, String srcRef, String targetRef)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process Wrapper Object"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "laneIds", type = "String", desc = "List of lane Ids"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "srcRef", type = "String", desc = "Source Reference"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "targetRef", type = "String", desc = "Target Reference")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Sequence Flow Wrapper Object."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a sequence flow object",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object createSequenceFlow(Object processObjWrapper, String laneIds, String srcRef, String targetRef) {
		EObjectWrapper<EClass, EObject> processtWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		String[] laneIdList = laneIds.split(",");
		
		EObjectWrapper<EClass, EObject> sequenceWrapper = SequenceFlowHelper.createSequenceFlow(processtWrapper, laneIdList, srcRef, targetRef);
		return sequenceWrapper;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setBaseSequenceFlowAttributes",
        signature = "void setBaseSequenceFlowAttributes(Object processObjWrapper, Object sequenceFlowWrapper, int uniqueId, String name, boolean isImmediate)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process Wrapper Object"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceFlowWrapper", type = "Object", desc = "Sequence Flow Wrapper"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "uniqueId", type = "int", desc = "Unique Id"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Name"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "isImmediate", type = "boolean", desc = "Is Immediate"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceId", type = "String", desc = "Sequence Id"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "expression", type = "String", desc = "Expression")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's the base Sequence Flow Attributes",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setBaseSequenceFlowAttributes(Object processObjWrapper, Object sequenceFlowWrapper, int uniqueId, String name, boolean isImmediate, String sequenceId,String expression) {
		EObjectWrapper<EClass, EObject> processtWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> sequenceeWrapper = null;
		if (sequenceFlowWrapper instanceof EObjectWrapper) {
			sequenceeWrapper = (EObjectWrapper<EClass, EObject>) sequenceFlowWrapper;
		}
		
		SequenceFlowHelper.setBaseSequenceFlowAttributes(processtWrapper, sequenceeWrapper, uniqueId, name, isImmediate, sequenceId,expression);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowNodeStartPoint",
        signature = "void setFlowNodeStartPoint(Object sequenceFlowWrapper, double x, double y)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceFlowWrapper", type = "Object", desc = "Sequence Flow Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "x", type = "double", desc = "X"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "y", type = "double", desc = "Y")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the Flow node start point",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowNodeStartPoint(Object sequenceFlowWrapper, double x, double y) {
		EObjectWrapper<EClass, EObject> sequenceeWrapper = null;
		if (sequenceFlowWrapper instanceof EObjectWrapper) {
			sequenceeWrapper = (EObjectWrapper<EClass, EObject>) sequenceFlowWrapper;
		}
		
		SequenceFlowHelper.setFlowNodePoint(sequenceeWrapper, x, y, true);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setFlowNodeEndPoint",
        signature = "void setFlowNodeEndPoint(Object sequenceFlowWrapper, double x, double y)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceFlowWrapper", type = "Object", desc = "Sequence Flow Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "x", type = "double", desc = "X"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "y", type = "double", desc = "Y")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the Flow node end point",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setFlowNodeEndPoint(Object sequenceFlowWrapper, double x, double y) {
		EObjectWrapper<EClass, EObject> sequenceeWrapper = null;
		if (sequenceFlowWrapper instanceof EObjectWrapper) {
			sequenceeWrapper = (EObjectWrapper<EClass, EObject>) sequenceFlowWrapper;
		}
		
		SequenceFlowHelper.setFlowNodePoint(sequenceeWrapper, x, y, false);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setBendPoint",
        signature = "void setBendPoint(Object sequenceFlowWrapper, double x, double y)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceFlowWrapper", type = "Object", desc = "Sequence Flow Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "x", type = "double", desc = "X"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "y", type = "double", desc = "Y")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set bend point",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setBendPoint(Object sequenceFlowWrapper, double x, double y) {
		EObjectWrapper<EClass, EObject> sequenceWrapper = null;
		if (sequenceFlowWrapper instanceof EObjectWrapper) {
			sequenceWrapper = (EObjectWrapper<EClass, EObject>) sequenceFlowWrapper;
		}
		
		SequenceFlowHelper.setBendPoint(sequenceWrapper, x, y);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setDocumentation",
        signature = "void setDocumentation(Object processObjWrapper, Object sequenceFlowWrapper, String docId, String docText, String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sequenceFlowWrapper", type = "Object", desc = "Sequence Flow EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "docId", type = "String", desc = "Documentation Id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "docText", type = "String", desc = "Documentation Text"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Documentation element to Sequence Flow Element",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setDocumentation(Object processObjWrapper, Object sequenceFlowWrapper, String docId, String docText, String name) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> sequenceNodeWrapper = null;
		if (sequenceFlowWrapper instanceof EObjectWrapper) {
			sequenceNodeWrapper = (EObjectWrapper<EClass, EObject>) sequenceFlowWrapper;
		}
		
		SequenceFlowHelper.setDocumentation(processModelWrapper, sequenceNodeWrapper, docId, docText, name);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "createAssociationNode",
        signature = "Object createAssociationNode(Object sequenceFlowWrapper, String srcRef, String targetRef, String label, String id)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process Flow Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "srcRef", type = "String", desc = "Source Reference"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "targetRef", type = "String", desc = "Target Reference"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "label", type = "String", desc = "Label"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "id", type = "String", desc = "Id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Association Node"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set bend point",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object createAssociationNode(Object processObjWrapper, String srcRef, String targetRef, String label, String id) {
		EObjectWrapper<EClass, EObject> processWrapper = null;
		if (processObjWrapper instanceof EObjectWrapper) {
			processWrapper = (EObjectWrapper<EClass, EObject>) processObjWrapper;
		}
		
		return SequenceFlowHelper.createAssociationNode(processWrapper, srcRef, targetRef, label, id);
	}
}
