/**
 * 
 */
package com.tibco.be.ws.functions.process;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.ws.process.CommonProcessUtil;
import com.tibco.be.ws.process.FlowNodeHelper;
import com.tibco.be.ws.process.ProcessHelper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

/**
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Process",
        synopsis = "Functions to work with Base Process EMF model.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Process", value=true))

public class WebStudioProcessFunctions {
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getProcessEMFObject",
        signature = "Object getProcessEMFObject(String projectPath, String projectName, String artifactPath)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "Project Path."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project Name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "Artifact Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "EObject representing the Process."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process EMF model object.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getProcessEMFObject(String projectPath, String projectName, String artifactPath) {
		EObjectWrapper<EClass, EObject> modelObjWrapper = null;
		try {
			ProcessHelper.loadBPMNProject(projectPath);
			EObject eObject = BpmnCommonIndexUtils.getElement(projectName, artifactPath);
			modelObjWrapper = EObjectWrapper.wrap(eObject);
            
			if (modelObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
//				System.out.println(processData(modelObjWrapper));
				return modelObjWrapper;
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return modelObjWrapper;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getId",
        signature = "String getId(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Process Id."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Id.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getId(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getId(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getType",
        signature = "String getType(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Process Type."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Type.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getType(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getType(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getAuthor",
        signature = "String getAuthor(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Process Author."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Author.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getAuthor(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getAuthor(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getVersion",
        signature = "String getVersion(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Process version."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Version.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static int getVersion(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getVersion(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLabel",
        signature = "String getLabel(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Process Label."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Label.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getLabel(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getLabel(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getProperties",
        signature = "Object[] getProperties(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Process Properties."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Properties.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getProperties(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getProperties(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getPropertyDetails",
        signature = "Object getPropertyDetails(Object property)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "property", type = "Object", desc = "Property EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Property Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Properties details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getPropertyDetails(Object property) {
		if (!(property instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Property EObject");
		} else {
			EObject eProperty = (EObject) property;
			return ProcessHelper.getPropertyDetails(eProperty);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLaneSets",
        signature = "Object[] getLaneSets(Object processWrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Process Lanesets."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process lanesets.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getLaneSets(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getLaneSets(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLanes",
        signature = "Object[] getLanes(Object laneset)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "laneset", type = "Object", desc = "Property EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Property Lanes."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Lanes.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getLanes(Object laneset) {
		if (!(laneset instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Laneset EObject");
		} else {
			EObject eLaneset = (EObject) laneset;
			return ProcessHelper.getLanes(eLaneset);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLaneDetails",
        signature = "Object getLaneDetails(Object lane)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lane", type = "Object", desc = "Lane EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Lane Details."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Lane details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getLaneDetails(Object lane) {
		if (!(lane instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Lane EObject");
		} else {
			EObject eLane= (EObject) lane;
			return ProcessHelper.getLaneDetails(eLane);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getChildLaneSet",
        signature = "Object getChildLaneSet(Object lane)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lane", type = "Object", desc = "Lane EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Child Lane Set."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a child Lane set.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getChildLaneSet(Object lane) {
		if (!(lane instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Lane EObject");
		} else {
			EObject eLane= (EObject) lane;
			return ProcessHelper.getChildLaneSet(eLane);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getChildLanes",
        signature = "Object[] getChildLanes(Object lane)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lane", type = "Object", desc = "Lane EObject")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "Child Lanes."),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves a Process Child Lanes.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object[] getChildLanes(Object lane) {
		if (!(lane instanceof EObject)) {
			throw new IllegalArgumentException("Invalid argument. Argument should be of type Lane EObject");
		} else {
			EObject eLane = (EObject) lane;
			return ProcessHelper.getChildLanes(eLane);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getDocumentation",
        signature = "Object getDocumentation(Object processWrapper)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "processWrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Documentation Details"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Retrieves documentation details for process.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getDocumentation(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getDocumentationDetails(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "createProcessEMFObject",
        signature = "Object createProcessEMFObject(String projectBasePath, String processName, String projectName, String folder)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectBasePath", type = "String", desc = "Project Base Path"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processName", type = "String", desc = "Process Name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project Name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "folder", type = "String", desc = "Folder Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Process EObject"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a new Process EMF Object",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object createProcessEMFObject(String projectBasePath, String processName, String projectName, String folder) {
		EObjectWrapper<EClass, EObject> processEObjectWrapper = null;
		try {
			processEObjectWrapper = ProcessHelper.createProcessEMFObject(projectBasePath, processName, projectName, folder, processName);
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		return processEObjectWrapper;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setProcessBaseAttributes",
        signature = "void setProcessBaseAttributes(Object processObjWrapper, String type, String label, String author, int version, double zoomLevel)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "Process Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "label", type = "String", desc = "Label"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "author", type = "String", desc = "Author"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "version", type = "int", desc = "Version"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "zoomLevel", type = "double", desc = "Zoom Level")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the base process attributes",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setProcessBaseAttributes(Object processObjWrapper, String type, String label, String author, int version, double zoomLevel) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		ProcessHelper.setProcessBaseAttributes(processModelWrapper, type, label, author, version, zoomLevel);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "addProperty",
        signature = "void addProperty(Object processObjWrapper, String name, String type, boolean isMultiple, String path)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Process Name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "type", type = "String", desc = "Type"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "isMultiple", type = "String", desc = "Is Multiple"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "int", desc = "Path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add's a new Property to process",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void addProperty(Object processObjWrapper, String name, String type, boolean isMultiple, String path) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		ProcessHelper.addProperty(processModelWrapper, name, type, isMultiple, path);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "addLaneSet",
        signature = "Object addLaneSet(Object processObjWrapper, String laneSetId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "laneSetId", type = "String", desc = "Lane Set Id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "LaneSet", type = "Object", desc = "LaneSet EMF Wrapper"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add's a new Lane Set to process",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object addLaneSet(Object processObjWrapper, String laneSetId) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		EObjectWrapper<EClass, EObject> laneset = ProcessHelper.addLaneSet(processModelWrapper, laneSetId);
		
		return laneset;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "addLane",
        signature = "Object addLane(Object processObjWrapper, String laneLabel, Object parentLaneSet, String laneId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "laneLabel", type = "String", desc = "Lane Label"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "parentLaneSet", type = "Object", desc = "Lane set EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "laneId", type = "String", desc = "Lane Id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "Lane", type = "Object", desc = "Lane EMF Wrapper"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add's a new Lane to a laneset",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object addLane(Object processObjWrapper, String laneLabel, Object parentLaneSet, String laneId) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> lanesetWrapper = null;
		if (parentLaneSet instanceof EObjectWrapper) {
			lanesetWrapper = (EObjectWrapper<EClass, EObject>) parentLaneSet;
		}
		EObjectWrapper<EClass, EObject> lane = ProcessHelper.addLane(processModelWrapper, laneLabel, lanesetWrapper, laneId);
		
		return lane;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "addChildLaneSet",
        signature = "Object addChildLaneSet(Object processObjWrapper, Object lane, String childLaneSetId)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lane", type = "Object", desc = "Lane EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "childLaneSetId", type = "String", desc = "Child Lane Set Id")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "Laneset", type = "Object", desc = "Laneset EMF Wrapper"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Add's a new child Laneset to a lane",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object addChildLaneSet(Object processObjWrapper, Object lane, String childLaneSetId) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		EObjectWrapper<EClass, EObject> laneWrapper = null;
		if (lane instanceof EObjectWrapper) {
			laneWrapper = (EObjectWrapper<EClass, EObject>) lane;
		}
		EObjectWrapper<EClass, EObject> childLaneset = ProcessHelper.addChildLaneSet(processModelWrapper, laneWrapper, childLaneSetId);
		
		return childLaneset;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLaneId",
        signature = "String getLaneId(Object lane)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lane", type = "Object", desc = "Lane EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "Lane Id", type = "String", desc = "Lane Id"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the Lane Id",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getLaneId(Object lane) {
		EObjectWrapper<EClass, EObject> laneWrapper = null;
		if (lane instanceof EObjectWrapper) {
			laneWrapper = (EObjectWrapper<EClass, EObject>) lane;
		}
		return ProcessHelper.getLaneId(laneWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getEMFInstance",
        signature = "Object getEMFInstance(Object wrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "wrapper", type = "Object", desc = "Object EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "EMF Instance"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the EMF instance",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object getEMFInstance(Object wrapper) {
		EObjectWrapper<EClass, EObject> objectWrapper = null;
		if (wrapper instanceof EObjectWrapper) {
			objectWrapper = (EObjectWrapper<EClass, EObject>) wrapper;
		}
		return ProcessHelper.getEMFInstance(objectWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getCreationDate",
        signature = "String getCreationDate(Object wrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "wrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Creation Date"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the Process Creation Date",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getCreationDate(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getCreationDate(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "getLastModificationDate",
        signature = "String getLastModificationDate(Object wrapper)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "wrapper", type = "Object", desc = "Process EMF Wrapper")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Last Modification Date"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get the Process Last Modification Date",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static String getLastModificationDate(Object processWrapper) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		return ProcessHelper.getLastModificationDate(processModelWrapper);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setCreationDate",
        signature = "void setCreationDate(Object wrapper, String creationDate)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "wrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "creationDate", type = "String", desc = "Creation Date")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the Process Creation Date",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setCreationDate(Object processWrapper, String creationDate) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		ProcessHelper.setCreationDate(processModelWrapper, creationDate);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setLastModificationDate",
        signature = "void setLastModificationDate(Object wrapper, String lastModifiedDate)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "wrapper", type = "Object", desc = "Process EMF Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "lastModifiedDate", type = "String", desc = "Last Modification Date")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the Process Last Modification Date",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setLastModificationDate(Object processWrapper, String lastModifiedDate) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processWrapper);
		ProcessHelper.setLastModificationDate(processModelWrapper, lastModifiedDate);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setDocumentation",
        signature = "void setDocumentation(Object processObjWrapper, String docId, String docText, String name)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processObjWrapper", type = "Object", desc = "Process EMF Object Wrapper"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "docId", type = "String", desc = "Documentation Id"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "docText", type = "String", desc = "Documentation Text"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "name", type = "String", desc = "Name")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set's Documentation element to Process",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static void setDocumentation(Object processObjWrapper, String docId, String docText, String name) {
		EObjectWrapper<EClass, EObject> processModelWrapper = CommonProcessUtil.getProcessWrapper(processObjWrapper);
		
		ProcessHelper.setDocumentation(processModelWrapper, docId, docText, name);
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "serializeProcessEMFModel",
        signature = "Object serializeProcessEMFModel(Object processEMFModel)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "processEMFModel", type = "Object", desc = "Process EMF Model")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Process model byte[]"),
        version = "5.2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Serialize the Process model to byte[]",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object serializeProcessEMFModel(Object processEMFModel) {
		EObject processEObject = null;
		if (processEMFModel instanceof EObject) {
			processEObject = (EObject) processEMFModel;
		}
		return ProcessHelper.serializeProcessEMFModel(processEObject);
	}
	
	private static String processData(Object oWrapper) throws Exception {
		EObjectWrapper<EClass, EObject> modelObjWrapper = null;
		StringBuffer processedData = new StringBuffer();
		
		if (oWrapper instanceof EObjectWrapper) {
			modelObjWrapper = (EObjectWrapper<EClass, EObject>) oWrapper;
			
			String ID = modelObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			processedData.append(BpmnMetaModelConstants.E_ATTR_ID.toString() + " - " + ID + "\n");
			EEnumLiteral processType = (EEnumLiteral) modelObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
			String pType = null;
			if (processType.equals(BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC)) {
				pType = "Public";
			} else {
				pType = "Private";
			}
			processedData.append(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE.toString() + " - " + pType + "\n");
			
			if (ExtensionHelper.isValidDataExtensionAttribute(modelObjWrapper, BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(modelObjWrapper);
				if (valueWrapper != null) {
					String author = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR);
					processedData.append(BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR.toString() + " - " + author + "\n");
					int version = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
					processedData.append(BpmnMetaModelExtensionConstants.E_ATTR_VERSION.toString() + " - " + version + "\n");
					String label = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
					processedData.append(BpmnMetaModelExtensionConstants.E_ATTR_LABEL.toString() + " - " + label + "\n");
				}
			}
			
			EList<EObject> properties = modelObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
			for (EObject propDef : properties) {
				Map<String, Object> propMap = ProcessHelper.getPropertyDetails(propDef);
				processedData.append(propMap.toString());
				processedData.append("\n");
			}
			
			EList<EObject>laneSets = modelObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
			for(EObject laneSetObj : laneSets) {
				EObjectWrapper<EClass, EObject> laneSet = EObjectWrapper.wrap(laneSetObj);
				EList<EObject> lanes = laneSet.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
				for(EObject lane : lanes) {
					EObjectWrapper<EClass, EObject> laneObject = EObjectWrapper.wrap(lane);
					String id = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					String name = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					processedData.append("Lane Id - " + id + "\t" + "Name - " + name + "\n");
					
					final EObjectWrapper<EClass, EObject> childLaneSet = laneObject
							.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
					final EList<EObject> childLanes = childLaneSet.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
					for (EObject childLane : childLanes) {
						EObjectWrapper<EClass, EObject> childLaneObject = EObjectWrapper.wrap(childLane);
						String childLaneId = childLaneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						processedData.append("Child Lane Id - " + childLaneId + "\n");
						//TODO this should be a recursive method
					}
				}
			}
			
			
			// process sequence flows
			processedData.append("Sequence Flows -----------\n");
			List<EObject> allSequenceFlows = BpmnCommonModelUtils.getAllSequenceFlows(modelObjWrapper.getEInstance());
			for (EObject eObject : allSequenceFlows) {
				EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(eObject);
				String name = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				processedData.append("Name - " + name + "\t");
				
				String locationID = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				processedData.append("Id - " + locationID + "\t");
				int unique_id = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				processedData.append("Unique Id - " + unique_id + "\t");
				EList<EObject> lanes = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
				for(EObject lane : lanes) {
					EObjectWrapper<EClass, EObject> laneObject = EObjectWrapper.wrap(lane);
					String id = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					String lname = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					processedData.append("Lane Id - " + id + "\t" + "Lane Name - " + lname + ",\t");
				}
				boolean isImmediate = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_IMMEDIATE);
				processedData.append("IsImmediate - " +  isImmediate + "\t");
				
				EObjectWrapper<EClass, EObject> sequenceExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(sequenceWrapperObject);
				EObject startPoint = sequenceExtensionValueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_START_POINT));
				if (startPoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(startPoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					
					processedData.append("Start Point (X - " + x + ", Y - " + y + ")\t");
				}
				
				EObject endPoint = sequenceExtensionValueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_END_POINT));
				if (endPoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(endPoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					
					processedData.append("End Point (X - " + x + ", Y - " + y + ")\t");
				}
				
				EList<EObject> listAttribute = sequenceExtensionValueWrapper.getListAttribute((BpmnMetaModelExtensionConstants.E_ATTR_BEND_POINTS));
				for (EObject eobject : listAttribute) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eobject);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					
					processedData.append("Bend Point (X - " + x + ", Y - " + y + ")\t");
				}
				
				EObject targetReference = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
				if (targetReference != null) {
					EObjectWrapper<EClass, EObject> targetReferenceWrapper = EObjectWrapper.wrap(targetReference);
					String id = targetReferenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					String lname = targetReferenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					processedData.append("Target Reference, Id - " + id + "\t" + "Name - " + lname + ",\n");
				}
				
				EObject sourceReference = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
				if (sourceReference != null) {
					EObjectWrapper<EClass, EObject> sourceReferenceWrapper = EObjectWrapper.wrap(sourceReference);
					String id = sourceReferenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					String lname = sourceReferenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					processedData.append("Source Reference, Id - " + id + "\t" + "Name - " + lname + ",\n");
				}
			}
			
			
			processedData.append("Flow Nodes -----------\n");
			List<EObject> allFlowNodes = BpmnCommonModelUtils.getAllFlowNodes(modelObjWrapper.getEInstance());
			for (EObject flowNode : allFlowNodes) {
				EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
				processedData.append("---------------------------------------------------------------------------------------\n");
				processedData.append("Type - " + FlowNodeHelper.getFlowNodeType(flowNode) + "\t");
				String name = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				processedData.append("Name - " + name + "\t");
				String locationID = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				processedData.append("Location Id - " + locationID + "\t");
				int unique_id = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				processedData.append("Unique Id - " + unique_id + "\t");
				List<EObject> incomingSequences = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
				processedData.append("Incoming Sequences, ");
				for (EObject eObject : incomingSequences) {
					EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(eObject);
					String seqName = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					String seqLocationID = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					processedData.append("Name - " + seqName + ", Id - " + seqLocationID + "\t");
				}
				List<EObject> outgoingSequences = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
				processedData.append("Outgoing Sequences, ");
				for (EObject eObject : outgoingSequences) {
					EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(eObject);
					String seqName = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					String seqLocationID = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					processedData.append("Name - " + seqName + ", Id - " + seqLocationID + "\t");
				}
				EList<EObject> lanes = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
				for(EObject lane : lanes) {
					EObjectWrapper<EClass, EObject> laneObject = EObjectWrapper.wrap(lane);
					String id = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					String lname = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
					processedData.append("Lane Id - " + id + "\t" + "Lane Name - " + lname + ",\t");
				}
				
				EObjectWrapper<EClass, EObject> flowNodeExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
				String label = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				processedData.append("Label - " + label + "\t");
				if (ExtensionHelper.isValidDataExtensionAttribute(flowNodeWrapper, BpmnMetaModelExtensionConstants.E_ATTR_VERSION)) {
					int version = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
					processedData.append("Version - " + version + "\t");
				}
				
				if (ExtensionHelper.isValidDataExtensionAttribute(flowNodeWrapper, BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT)) {
					boolean checkPoint = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_CHECK_POINT);
					processedData.append("Checkpoint - " + checkPoint + "\t");
				}
				
				if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED)) {
					Boolean timerEnabled = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_ENABLED);
					processedData.append("timerEnabled - " + timerEnabled + "\n");
					
					if (timerEnabled) {
						EObject timeoutData = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMER_DATA);
	        			if(timeoutData != null){
	        				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(timeoutData);
	        				String xpathExpr = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION);
	        				processedData.append("xpathExpr - " + xpathExpr + "\t");
	        				String timeoutEvent = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
	        				processedData.append("timeoutEvent - " + timeoutEvent + "\t");
	        				EEnumLiteral unitEnum = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_UNIT);
	        				String timeoutUnit = unitEnum.getLiteral();
	        				processedData.append("timeoutUnit - " + timeoutUnit + "\n");
	        			}
					}
				}
				
				if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS)) {
					EObject lcObj = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
					if (lcObj != null) {
						EObjectWrapper<EClass, EObject> lcw = EObjectWrapper.wrap(lcObj);
						if (lcw.isInstanceOf(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS)) {
							final boolean testBefore = (Boolean)lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_TEST_BEFORE);
							final String loopCondition = (String)lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CONDITION);
							final int loopCount = (Integer)lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_MAXIMUM);
						} else if (lcw.isInstanceOf(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS)) {
							final boolean sequential = (Boolean)lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_SEQUENTIAL);
							Object cardinality = lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CARDINALITY);
							if (cardinality != null) {
								EObjectWrapper<EClass, EObject> cardinalityWrapper = EObjectWrapper
										.wrap((EObject) cardinality);
								String body = (String)cardinalityWrapper
										.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
							}
							EEnumLiteral bv = lcw.getEnumAttribute(BpmnMetaModelConstants.E_ATTR_BEHAVIOR);
							String behavior = bv.getLiteral();
							Object completionCondition = lcw.getAttribute(BpmnMetaModelConstants.E_ATTR_COMPLETION_CONDITION);
							if (completionCondition != null) {
								if (cardinality != null) {
									EObjectWrapper<EClass, EObject> completionWrapper = EObjectWrapper
											.wrap((EObject) completionCondition);
									String body = (String)completionWrapper
											.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
								}
							}

						}
					}
				}
				
				EObject nodePoint = flowNodeExtensionValueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT));
				if (nodePoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(nodePoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					
					processedData.append("Node Point (X - " + x + ", Y - " + y + ")\t");
				}
				
				EObject labelPoint = flowNodeExtensionValueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_LABEL_POINT));
				if (labelPoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(labelPoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					
					processedData.append("Label Point (X - " + x + ", Y - " + y + ")\n");
				}
				
				Object resReferenced = BpmnCommonModelUtils.getAttachedResource(flowNode);
				if (resReferenced instanceof List) {
					List<String> resPathList = (List<String>) resReferenced;
					for (String resPath : resPathList) {
						processedData.append("[Is List]Selected Resource - " + resPath + "\n");
					}
				} else {
					processedData.append("Selected Resource - " + resReferenced + "\n");
				}
				
				String inputXslt = CommonProcessUtil.getInputMapperXslt(flowNode);
				processedData.append("Input XSLT - " + inputXslt + "\n");
				
				String outputXslt = CommonProcessUtil.getOutputMapperXslt(flowNode);
				processedData.append("Output XSLT - " + outputXslt + "\n");
				
				
				List<String> impls = new ArrayList<String>();
				if (flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
							.wrap(flowNode);
					if (ExtensionHelper.isValidDataExtensionAttribute(wrap,
							BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS)) {
						EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
								.getAddDataExtensionValueWrapper(wrap);
						if (valueWrapper != null) {
							ArrayList<EObject> arrayList = new ArrayList<EObject>(
									valueWrapper
											.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS));
							for (EObject eObject : arrayList) {
								EObjectWrapper<EClass, EObject> implementation = EObjectWrapper
										.wrap(eObject);
								String uri = implementation
										.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
								if (implementation.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED)) {
									boolean isDeployed = implementation.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED);
								}
								if (uri != null && !uri.trim().isEmpty()) {
									impls.add(uri);
								}
							}
						}
					}
				}
				
				
				Map<String, String> props = new HashMap<String, String>();
				if(flowNodeWrapper.getEClassType().equals(BpmnModelClass.SERVICE_TASK)){
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(flowNodeWrapper);
					if(valueWrapper != null){
						String service = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SERVICE);
						if(service != null && !service.trim().isEmpty())
							props.put("Service", service);
						String port = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PORT);
						if(port != null && !port.trim().isEmpty())
							props.put("Port", port);
						String operation = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_OPERATION);
						if(operation != null && !operation.trim().isEmpty())
							props.put("Operation", operation);
						Long timeout = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT);
						if(timeout != null)
							props.put("Timeout", timeout.toString());
						
						EEnumLiteral propType = valueWrapper
								.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE);
						if (propType == null || propType.equals(BpmnModelClass.ENUM_WS_BINDING_HTTP)){
							String endPointUrl = valueWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL);
							if(endPointUrl != null && !endPointUrl.trim().isEmpty())
								props.put("End Point Url", endPointUrl);
						}else{
							String providerUrl = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JNDI_PROVIDER_URL);
							if(providerUrl != null && !providerUrl.trim().isEmpty())
								props.put("JNDI Context URL", providerUrl);
						}
					}
				}
				
				if(flowNodeWrapper.isInstanceOf(BpmnModelClass.GATEWAY)) {
					if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_GATEWAY_TYPE)){
						EEnumLiteral gateWayType = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_GATEWAY_TYPE);
						processedData.append("GATEWAY Type - " + gateWayType.getLiteral() + "\t");
					}
					
					String gatewayType = null;
					if (BpmnModelClass.EXCLUSIVE_GATEWAY.isSuperTypeOf(flowNodeWrapper.getEClassType())) {
						gatewayType = BpmnModelClass.EXCLUSIVE_GATEWAY.getName();
					} else if (BpmnModelClass.PARALLEL_GATEWAY.isSuperTypeOf(flowNodeWrapper.getEClassType())) {
						gatewayType = BpmnModelClass.PARALLEL_GATEWAY.getName();
					}
					processedData.append("Gateway Type - " + gatewayType + "\t");
					
					EEnumLiteral gateWayDirection = flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION);
					processedData.append("GATEWAY Direction - " + gateWayDirection.getLiteral() + "\t");
					
					if (flowNodeWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT)){
						EObject defaultSeq = (EObject)flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
						EObjectWrapper<EClass, EObject> sequenceWrapperObject = EObjectWrapper.wrap(defaultSeq);
						String locationId = sequenceWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						processedData.append("Default SequenceId - " + locationId);
					}
					
					if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSIONS)) {
						EList<EObject> gatewayExpressionList = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSIONS);
						for (EObject eObject : gatewayExpressionList) {
							EObjectWrapper<EClass, EObject> gatewayExpressionWrapperObject = EObjectWrapper.wrap(eObject);
							String transformation = gatewayExpressionWrapperObject.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION);
							processedData.append("Transformation - " + transformation + "\t");
							String sequenceId = gatewayExpressionWrapperObject.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SEQUENCE_FLOW_ID);
							processedData.append("SequenceId - " + sequenceId + "\t");
						}
					}
					
					if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION)) {
						String joinRulefunction = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_JOIN_RULEFUNCTION);
						processedData.append("Join Rule Function - " + joinRulefunction + "\t");
						
						String mergeExpression = (String) flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_MERGE_EXPRESSION);
						processedData.append("Merge Expression - " + mergeExpression + "\t");
						
					}
					
					if (flowNodeExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION)) {
						String forkRulefunction = flowNodeExtensionValueWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FORK_RULEFUNCTION);
						processedData.append("Fork Rule Function - " + forkRulefunction + "\t");						
					}
					
					processedData.append("\n");
				}
				
				processedData.append("---------------------------------------------------------------------------------------\n");
			}
			processedData.append("Text Annotation ----------------------------------\n");
			Collection<EObject> textAnnotations = BpmnCommonModelUtils.getArtifactNodes(modelObjWrapper);
			for (EObject textAnnot : textAnnotations) {
				EObjectWrapper<EClass, EObject> textAnnotWrapper = EObjectWrapper.wrap(textAnnot);
				processedData.append("---------------------------------------------------------------------------------------\n");
				String locationID = textAnnotWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				processedData.append("Id - " + locationID + "\t");
				processedData.append("Type - " + FlowNodeHelper.getFlowNodeType(textAnnot) + "\t");
				int unique_id = textAnnotWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				processedData.append("Unique Id - " + unique_id + "\t");
				String text = textAnnotWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
				processedData.append("Text - " + text + "\t");
				
				EObjectWrapper<EClass, EObject> textAnnotExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(textAnnotWrapper);
				String label = textAnnotExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				processedData.append("Label - " + label + "\t");
				
				EObject nodePoint = textAnnotExtensionValueWrapper.getAttribute((BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT));
				if (nodePoint != null) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(nodePoint);
					Double x = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X);
					Double y = wrap.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y);
					
					processedData.append("Node Point (X - " + x + ", Y - " + y + ")\t");
				}
			}
			processedData.append("\n");
			
			processedData.append("Associations ----------------------------------\n");
			Collection<EObject> associations = BpmnCommonModelUtils.getAssociations(modelObjWrapper);
			for (EObject asso : associations) {
				EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.wrap(asso);
				processedData.append("---------------------------------------------------------------------------------------\n");
				
				String locationID = associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				processedData.append("Id - " + locationID + "\t");
				int unique_id = associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				processedData.append("Unique Id - " + unique_id + "\t");
				
				EObjectWrapper<EClass, EObject> associationExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(associationWrapper);
				String label = associationExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				processedData.append("Label - " + label + "\t");
				
				EObject targetReference = associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
				if (targetReference != null) {
					EObjectWrapper<EClass, EObject> targetReferenceWrapper = EObjectWrapper.wrap(targetReference);
					String id = targetReferenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					processedData.append("Target Reference, Id - " + id + "\n");
				}
				
				EObject sourceReference = associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
				if (sourceReference != null) {
					EObjectWrapper<EClass, EObject> sourceReferenceWrapper = EObjectWrapper.wrap(sourceReference);
					String id = sourceReferenceWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					processedData.append("Source Reference, Id - " + id + "\n");
				}
			}
			processedData.append("\n");
			
		}
		return processedData.toString();
	}
	
	public static void main(String[] args) {
		getProcessEMFObject("C:/tibco/BE5.2/V26/be/5.2/examples/standard/WebStudio/SimpleProcess", "SimpleProcess", "/Processes/DebugOut.beprocess");
	}

}
