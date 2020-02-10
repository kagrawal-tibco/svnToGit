/**
 * 
 */
package com.tibco.be.ws.process;

import static com.tibco.be.ws.process.ProcessPropertyConstants.PROCESS_TYPE_PRIVATE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROCESS_TYPE_PUBLIC;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DOC_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_DOC_TEXT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ID;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_ISARRAY;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_NAME;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_PATH;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TYPE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TYPE_CONCEPT;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TYPE_CONCEPT_REFERENCE;
import static com.tibco.be.ws.process.ProcessPropertyConstants.PROP_TYPE_ONTOLOGY_PREFIX;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.ws.scs.SCSException;
import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.ProcessIndexResourceProvider;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * This class caters to all API's specific to fetching and setting data from and into Process EMF Model
 * 
 * @author vpatil
 */
public class ProcessHelper {

	// structure to maintain the mapping for EMF project to TNS cache
	private static Map<String, EMFTnsCache> projectToTnsCache = new HashMap<String, EMFTnsCache>();
	
	private static final String TEMP_PROCESS_NAME_PREFIX = "tempProcess_";
	
	/**
	 * Loads the BPMN project and creates and sets the project index
	 * 
	 * @param projectPath
	 * @throws Exception
	 */
	public static void loadBPMNProject(String projectPath) throws Exception {
		EMFProject project = new EMFProject(projectPath);
		project.load();
		
		ProcessIndexResourceProvider bpmnIndexProvider = (ProcessIndexResourceProvider) project.getIndexResourceProviderMap().get(AddOnType.PROCESS);
		BpmnModelCache.getInstance().putIndex(project.getName(), bpmnIndexProvider.getIndex());
		
		addProjectCache(project.getName(), project.getTnsCache());
	}
	
	/**
	 * Retrieves the process Id
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static String getId(EObjectWrapper<EClass, EObject> processObjWrapper) {
		 String id = processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		 if (id != null && !id.isEmpty()) {
			 return id;
		 } else {
			 return null;
		 }
	}
	
	/**
	 * Retrieves the process type
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static String getType(EObjectWrapper<EClass, EObject> processObjWrapper) {
		EEnumLiteral processType = (EEnumLiteral) processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
		
		String pType = (processType.equals(BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC)) ? PROCESS_TYPE_PUBLIC : PROCESS_TYPE_PRIVATE;
		return pType;
	}
	
	/**
	 * Retrieves the process Author
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static String getAuthor(EObjectWrapper<EClass, EObject> processObjWrapper) {
		if (ExtensionHelper.isValidDataExtensionAttribute(processObjWrapper, BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR)) {
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(processObjWrapper);
			if (valueWrapper != null) {
				String author = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR);
				if (author != null && !author.isEmpty()) {
					return author;
				}
			}
		}
		return null;
	}
	
	/**
	 * Retrieves the process version
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static int getVersion(EObjectWrapper<EClass, EObject> processObjWrapper) {
		if (ExtensionHelper.isValidDataExtensionAttribute(processObjWrapper, BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR)) {
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(processObjWrapper);
			if (valueWrapper != null) {
				int version = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
				return version;
			}
		}
		return 0;
	}
	
	/**
	 * Retrieves the process Label
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static String getLabel(EObjectWrapper<EClass, EObject> processObjWrapper) {
		if (ExtensionHelper.isValidDataExtensionAttribute(processObjWrapper, BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR)) {
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(processObjWrapper);
			if (valueWrapper != null) {
				String label = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				if (label != null && !label.isEmpty()) {
					return label;
				}
			}
		}
		return null;
	}
	
	/**
	 * Retrieves the Process creation date
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static String getCreationDate(EObjectWrapper<EClass, EObject> processObjWrapper) {
		Date creationDate = processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CREATION_DATE);
		 if (creationDate != null) {
			 return creationDate.toString();
		 } else {
			 return null;
		 }
	}
	
	/**
	 * Retrieves the Process last modified date
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static String getLastModificationDate(EObjectWrapper<EClass, EObject> processObjWrapper) {
		Date lastModifiedDate = processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED);
		 if (lastModifiedDate != null) {
			 return lastModifiedDate.toString();
		 } else {
			 return null;
		 }
	}
	
	/**
	 * Retrieves the process Properties
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static EObject[] getProperties(EObjectWrapper<EClass, EObject> processObjWrapper) {
		EList<EObject> properties = processObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
		if (properties != null && properties.size() > 0) {
			return properties.toArray(new EObject[properties.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieves the process property details
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static Map<String, Object> getPropertyDetails(EObject property) {
		Map<String, Object> propertyDetails = new HashMap<String,Object>();
		
		EObjectWrapper<EClass, EObject> propWrapper = EObjectWrapper.wrap(property);
		String pname = propWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		propertyDetails.put(PROP_NAME, pname);
		
		EObjectWrapper<EClass, EObject> itemDef = EObjectWrapper.wrap((EObject)propWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
		boolean isArray = (Boolean)itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION);
		propertyDetails.put(PROP_ISARRAY,isArray);
		
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(property);
		EEnumLiteral propType = addDataExtensionValueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE);
		propertyDetails.put(PROP_TYPE, propType.getName());

		if (itemDef != null && (PROP_TYPE_CONCEPT.equals(propType.getName()) || PROP_TYPE_CONCEPT_REFERENCE.equals(propType.getName()))) {
			String fullPath = getPropertyFullPath(itemDef);
			propertyDetails.put(PROP_PATH, fullPath);
		}
		
		return propertyDetails;
	}
	
	/**
	 * Retrieves the full path of the property (type = concept)
	 * 
	 * @param itemDef
	 * @return
	 */
	private static String getPropertyFullPath(EObjectWrapper<EClass, EObject> itemDef) {
		String id = itemDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		if(id == null)
			return null;
		id = id.replace("[]", "");
		ExpandedName itemDefinitionType = ExpandedName.parse(id);

		if(itemDefinitionType != null && itemDefinitionType.getExpandedForm() != null) {
			String strUriPath = itemDefinitionType.getExpandedForm().substring(1,(itemDefinitionType.getExpandedForm().lastIndexOf('}')));
			String strConceptPath = strUriPath.substring(PROP_TYPE_ONTOLOGY_PREFIX.length(), strUriPath.length());
			return strConceptPath;
		}
		
		return null;
	}
	
	/**
	 * Retrieves the process Lane sets
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static EObject[] getLaneSets(EObjectWrapper<EClass, EObject> processObjWrapper) {
		EList<EObject> laneSets = processObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		if (laneSets != null && laneSets.size() > 0) {
			return laneSets.toArray(new EObject[laneSets.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieve the laneset Id
	 * 
	 * @param laneSet
	 * @return
	 */
	public static String getLaneSetId(EObject laneSet) {
		EObjectWrapper<EClass, EObject> laneSetWrapper = EObjectWrapper.wrap(laneSet);
		String laneSetId = laneSetWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		return laneSetId;
	}
	
	/**
	 * Retrieves the process Lanes
	 * 
	 * @param laneSet
	 * @return
	 */
	public static EObject[] getLanes(EObject laneSet) {
		EObjectWrapper<EClass, EObject> laneSetWrapper = EObjectWrapper.wrap(laneSet);
		EList<EObject> lanes = laneSetWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		if (lanes != null && lanes.size() > 0) {
			return lanes.toArray(new EObject[lanes.size()]);
		}
		return null;
	}
	
	/**
	 * Add project to EMFTns Cache mapping
	 * 
	 * @param project
	 * @param cache
	 */
	public static void addProjectCache(String project, EMFTnsCache cache) {
		if (!projectToTnsCache.containsKey(project)) {
			projectToTnsCache.put(project, cache);
		}
	}
	
	/**
	 * Fetch EMF Tns Cache for the associated project
	 * 
	 * @param project
	 * @return
	 */
	public static EMFTnsCache getProjectCache(String project) {
		return projectToTnsCache.get(project);
	}
	
	/**
	 * Retrieves the process Lane details
	 * 
	 * @param lane
	 * @return
	 */
	public static Map<String, String> getLaneDetails(EObject lane) {
		Map<String, String> laneDetails = new HashMap<String, String>();
		
		EObjectWrapper<EClass, EObject> laneObject = EObjectWrapper.wrap(lane);
		String id = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		laneDetails.put(PROP_ID, id);
		String name = laneObject.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		laneDetails.put(PROP_NAME, name);
		
		return laneDetails;
	}
	
	/**
	 * Retrieve the child Lane set for a lane.
	 * 
	 * @param lane
	 * @return
	 */
	public static EObject getChildLaneSet(EObject lane) {
		EObjectWrapper<EClass, EObject> laneObject = EObjectWrapper.wrap(lane);
		
		if (laneObject.containsAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET)) {
			EObjectWrapper<EClass, EObject> childLaneSet = laneObject
					.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);

			return childLaneSet.getEInstance();
		}
		return null;
	}
	
	/**
	 * Retrieves the process child lanes
	 * 
	 * @param childLaneSet
	 * @return
	 */
	public static EObject[] getChildLanes(EObject childLaneSet) {
		EObjectWrapper<EClass, EObject> childLaneSetWrapper = EObjectWrapper.wrap(childLaneSet);
		EList<EObject> childLanes = childLaneSetWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		if (childLanes != null && childLanes.size() > 0) {
			return childLanes.toArray(new EObject[childLanes.size()]);
		}
		return null;
	}
	
	/**
	 * Retrieves Documentation details
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static Object getDocumentationDetails(EObjectWrapper<EClass, EObject> processObjWrapper) {
		Map<String, Object> documentationDetails = new HashMap<String, Object>();
		
		if (processObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION)) {
			EList<EObject> documentationList = processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			for (EObject documentation : documentationList) {
				EObjectWrapper<EClass, EObject> documentationWrapperObject = EObjectWrapper.wrap(documentation);

				String docId = documentationWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				documentationDetails.put(PROP_DOC_ID, docId);

				String docText = documentationWrapperObject.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
				documentationDetails.put(PROP_DOC_TEXT, docText);
			}
		}
		
		return documentationDetails;
	}
	
	/**
	 * Sets the documentation details
	 * 
	 * @param processObjWrapper
	 * @param docId
	 * @param docText
	 * @param name
	 */
	public static void setDocumentation(EObjectWrapper<EClass, EObject> processObjWrapper, String docId, String docText, String name) {
		if (docId == null) {
			Identifier flowElementIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), null, processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), name);
			docId = flowElementIdentifier.getId();
		}
		
		EObjectWrapper<EClass, EObject> docWrapper = EObjectWrapper.createInstance(BpmnMetaModel.DOCUMENTATION);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, docId);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEXT, docText);
		processObjWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION, docWrapper);
	}
	
	/**
	 * Create a new EMF object for BE Process
	 * 
	 * @param processName
	 * @param projectName
	 * @param folder
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public static EObjectWrapper<EClass, EObject> createProcessEMFObject(String projectBasePath, String processName, String projectName, String folder, String id) throws Exception {
		if (getProjectCache(projectName) == null) {
//			String projectPath = projectBasePath + "/" + projectName;
			loadBPMNProject(projectBasePath);
		}
		EObjectWrapper<EClass, EObject> processObjWrapper = EObjectWrapper.createInstance(BpmnModelClass.PROCESS);
		
		processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER, folder);
		processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT, projectName);
		processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id);
		processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, processName);
		
		return processObjWrapper;
	}
	
	/**
	 * Set all the base attributes for a process
	 * 
	 * @param processObjWrapper
	 * @param type
	 * @param label
	 * @param author
	 * @param version
	 * @param zoomLevel
	 */
	public static void setProcessBaseAttributes(EObjectWrapper<EClass, EObject> processObjWrapper, String type, String label, String author, int version, double zoomLevel) {
		if (type.equals(PROCESS_TYPE_PUBLIC)) {
			processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE, BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC);
		} else {
			processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE, BpmnModelClass.ENUM_PROCESS_TYPE_EXECUTABLE);
		}
		
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(processObjWrapper);
		if (valueWrapper != null) {
			valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_AUTHOR, author);
			valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION, version);
			valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ZOOM_LEVEL, zoomLevel);
			valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL, label);
		}
	}
	
	/**
	 * Add a new Property to the process
	 * 
	 * @param processObjWrapper
	 * @param name
	 * @param type
	 * @param isMultiple
	 * @param path
	 */
	public static void addProperty(EObjectWrapper<EClass, EObject> processObjWrapper, String name, String type, boolean isMultiple, String path) {
		String projectName = processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		
		EList<EObject> properties = processObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
		
		if (properties != null) {
			EObjectWrapper<EClass, EObject> property = EObjectWrapper.createInstance(BpmnModelClass.PROPERTY);
			property.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
			
			EObjectWrapper<EClass, EObject> eObjectWrapper = null;
			if (path != null && !path.isEmpty()) {
				List<EObjectWrapper<EClass, EObject>> eObjectWrapperList = LocalECoreHelper.getItemDefinitionUsingLocation(projectName, path, getProjectCache(projectName));
				if (eObjectWrapperList != null && eObjectWrapperList.size() > 0) {
						eObjectWrapper = (isMultiple) ? eObjectWrapperList.get(1) : eObjectWrapperList.get(0);
				}
			} else {
				eObjectWrapper = LocalECoreHelper.getItemDefinitionUsingNameSpace(projectName, SupportedProcessTypes.getExpandedName(type).toString(), getProjectCache(projectName));
			}
			EObject itemDefObject = eObjectWrapper.getEInstance();			
			EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper.wrap(itemDefObject);
			itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, isMultiple);
			String id = itemDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			
			property.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF, itemDefObject);
			properties.add(property.getEInstance());
			
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(property);
			if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE)){
				addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE, SupportedProcessTypes.getEmfType(type));
			}
		}
	}
	
	/**
	 * Add a new laneset of the Process Object
	 * 
	 * @param processObjWrapper
	 * @param laneSetId
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> addLaneSet(EObjectWrapper<EClass, EObject> processObjWrapper, String laneSetId) {
		@SuppressWarnings("unchecked")
		EList<EObject> laneSets = (EList<EObject>) processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		EObjectWrapper<EClass, EObject> laneSet = createLaneSet(processObjWrapper, null, laneSetId);
		laneSets.add(laneSet.getEInstance());
		
		return laneSet;
	}
	
	/**
	 * Create a new lane set
	 * 
	 * @param processObjWrapper
	 * @param parentLane
	 * @param laneSetId
	 * @return
	 */
	private static EObjectWrapper<EClass, EObject> createLaneSet(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> parentLane, String laneSetId) {
		EObjectWrapper<EClass, EObject> laneSet = EObjectWrapper.createInstance(BpmnModelClass.LANE_SET);

		if (laneSetId == null) {
			Identifier laneSetIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), laneSet.getEClassType(), processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), null);
			laneSetId = laneSetIdentifier.getId();
		}
		laneSet.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, laneSetId);
		
		if(parentLane != null) {
			laneSet.setAttribute(BpmnMetaModelConstants.E_ATTR_PARENT_LANE,parentLane.getEInstance());
		}
		return laneSet;
	}
	
	/**
	 * Add a new lane to Laneset
	 * 
	 * @param laneLabel
	 * @param laneId
	 * @param parentLaneSet
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> addLane(EObjectWrapper<EClass, EObject> processObjWrapper, String laneLabel, EObjectWrapper<EClass, EObject> parentLaneSet, String laneId) {
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper.createInstance(BpmnModelClass.LANE);
		
		if (laneId == null) {
			Identifier laneIdentifier = CommonProcessUtil.getNextIdentifier(processObjWrapper.getEInstance(), lane.getEClassType(), processObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT).toString(), laneLabel);
			laneId = laneIdentifier.getId();
		}
		lane.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, laneId);
		lane.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, laneLabel);

		@SuppressWarnings("unchecked")
		EList<EObject> lanes = (EList<EObject>) parentLaneSet.getAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		lanes.add(lane.getEInstance());
		
		ExtensionHelper.getAddDataExtensionValueWrapper(lane);
		
		return lane;
	}
	
	/**
	 * Add a new Child Lane set
	 * 
	 * @param processObjWrapper
	 * @param lane
	 * @param childLaneSetId
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> addChildLaneSet(EObjectWrapper<EClass, EObject> processObjWrapper, EObjectWrapper<EClass, EObject> lane, String childLaneSetId) {
		EObject childLaneSet = (EObject) lane.getAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
	    if(childLaneSet == null) {
	    	EObjectWrapper<EClass, EObject> claneSet = createLaneSet(processObjWrapper, lane, childLaneSetId);
	    	lane.setAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET, claneSet.getEInstance());
	    	childLaneSet = claneSet.getEInstance();
	    }
		
		return EObjectWrapper.wrap(childLaneSet);
	}
	
	/**
	 * Retrieve the Lane Id
	 * 
	 * @param lane
	 * @return
	 */
	public static String getLaneId(EObjectWrapper<EClass, EObject> lane) {
		if (lane != null) {
			return lane.getAttribute(BpmnMetaModelConstants.E_ATTR_ID).toString();
		}
		
		return null;
	}
	
	/**
	 * Get the underlying EMF Instance
	 * 
	 * @param wrapper
	 * @return
	 */
	public static EObject getEMFInstance(EObjectWrapper<EClass, EObject> wrapper) {
		return wrapper.getEInstance();
	}
	
	/**
	 * Set the Process creation date
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static void setCreationDate(EObjectWrapper<EClass, EObject> processObjWrapper, String creationDate) {
		Date creationDt = null;

		if (creationDate != null) {
			try {
				creationDt = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH).parse(creationDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			creationDt = Calendar.getInstance().getTime();
		}
		processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_CREATION_DATE, creationDt);
	}
	
	/**
	 * Set the Process last modified date
	 * 
	 * @param processObjWrapper
	 * @return
	 */
	public static void setLastModificationDate(EObjectWrapper<EClass, EObject> processObjWrapper, String lastModifiedDate) {
		Date lastModifiedDt = null; 
		
		if (lastModifiedDate != null) {
			try {
				lastModifiedDt = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH).parse(lastModifiedDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 } else {
			 lastModifiedDt = Calendar.getInstance().getTime();
		 }
		 processObjWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LAST_MODIFIED, lastModifiedDt);
	}
	
	/**
	 * Serialize the the process EMF model
	 *  
	 * @param processEMFModel
	 * @return
	 */
	public static byte[] serializeProcessEMFModel(EObject processEMFModel) {
		String tempProcessName = TEMP_PROCESS_NAME_PREFIX + System.nanoTime() + ".beprocess";
		
		String absoluteFilePath = new File(".").getAbsolutePath();
		absoluteFilePath = absoluteFilePath.substring(0, absoluteFilePath.length() - 1) + tempProcessName;
		
		URI filePathURI = URI.createFileURI(absoluteFilePath);
		try {
			CommonECoreHelper.serializeMetaModelXMI(filePathURI, processEMFModel);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Path processFilePath = Paths.get(absoluteFilePath);
		byte[] artifactContents = null;
		try {
			artifactContents = Files.readAllBytes(processFilePath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		File processFile = new File(absoluteFilePath);
		processFile.delete();
		
		return artifactContents;
	}
	
	public static void main(String[] args) throws Exception {
		EObjectWrapper<EClass, EObject> processWrapper = createProcessEMFObject("C:/tibco/BE5.1/511_HF1_V38/be/5.1/examples/process/SimpleProcess/SimpleProcess", "MyProcess", "SimpleProcess", "/Processes", "MyProcess");
		setProcessBaseAttributes(processWrapper, "Public", "Cool Process", "Vikram", 1, 3.561277914307084);
		
		addProperty(processWrapper, "data", "String", false, null);
		addProperty(processWrapper, "customer", "Concept", false, "/Concepts/Customer");
		
		EObjectWrapper<EClass, EObject> laneSet = addLaneSet(processWrapper, null);
		EObjectWrapper<EClass, EObject> lane = addLane(processWrapper, "Lane 1", laneSet, null);
		addChildLaneSet(processWrapper, lane, null);
		
		String laneId = lane.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		
		EObjectWrapper<EClass, EObject> flowNodeStartTask = FlowNodeHelper.createFlowNode(processWrapper, BpmnModelClass.START_EVENT.getName(), new String[]{laneId});
		FlowNodeHelper.setFlowElementBaseAttributes(processWrapper, flowNodeStartTask, 32, "My Start Task", "/Events/DebugOut", null);
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(flowNodeStartTask, 2, "My Start Task Label", false, "event.start.message");
		FlowNodeHelper.setFlowNodePoint(flowNodeStartTask, 31.129263793873037, 12.227418915086552, true);
		FlowNodeHelper.setFlowNodePoint(flowNodeStartTask, 31.129263793872923, -21.739247751524736, false);
		FlowNodeHelper.setEventDefinitionType(processWrapper, flowNodeStartTask, "MessageEventDefinition", null, "My Start Task");
		
		String outputXSLT = "xslt://{{/Processes/DebugOut}}&lt;?xml version=\\&quot;1.0\\&quot; encoding=\\&quot;UTF-8\\&quot;?>\\n&lt;xsl:stylesheet xmlns:xsd=\\&quot;http://www.w3.org/2001/XMLSchema\\&quot; xmlns:xsl=\\&quot;http://www.w3.org/1999/XSL/Transform\\&quot; xmlns:ns=\\&quot;www.tibco.com/be/ontology/Processes/DebugOut\\&quot; version=\\&quot;1.0\\&quot; exclude-result-prefixes=\\&quot;xsl xsd\\&quot;>\\n    &lt;xsl:output method=\\&quot;xml\\&quot;/>\\n    &lt;xsl:param name=\\&quot;DebugOut\\&quot;/>\\n    &lt;xsl:template match=\\&quot;/\\&quot;>\\n        &lt;job>\\n            &lt;xsl:if test=\\&quot;$DebugOut/debugOut\\&quot;>\\n                &lt;ns:debugOut>\\n                    &lt;xsl:value-of select=\\&quot;$DebugOut/debugOut\\&quot;/>\\n                &lt;/ns:debugOut>\\n            &lt;/xsl:if>\\n        &lt;/job>\\n    &lt;/xsl:template>\\n&lt;/xsl:stylesheet>";
		FlowNodeHelper.setFlowNodeOutputMapperXslt(flowNodeStartTask, outputXSLT);
		
		EObjectWrapper<EClass, EObject> flowNodeRF = FlowNodeHelper.createFlowNode(processWrapper, BpmnModelClass.RULE_FUNCTION_TASK.getName(), new String[]{laneId});
		FlowNodeHelper.setFlowElementBaseAttributes(processWrapper, flowNodeRF, 32, "My RF Task", "/RuleFunctions/CreateCustomerAccount", null);
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(flowNodeRF, 2, "My RF Task Label", false, "activity.ruleFunction");
		FlowNodeHelper.setFlowNodePoint(flowNodeRF, 31.129263793873037, 12.227418915086552, true);
		FlowNodeHelper.setFlowNodePoint(flowNodeRF, 31.129263793872923, -21.739247751524736, false);
		
		String lpCardinality = "xpath://&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n&lt;xpath&gt;\n    &lt;expr&gt;$job/customer/@Id&lt;/expr&gt;\n    &lt;namespaces&gt;\n        &lt;namespace URI=\"http://www.w3.org/1999/XSL/Transform\" pfx=\"xsl\"/&gt;\n        &lt;namespace URI=\"http://www.w3.org/2001/XMLSchema\" pfx=\"xsd\"/&gt;\n    &lt;/namespaces&gt;\n    &lt;variables&gt;\n        &lt;variable&gt;job&lt;/variable&gt;\n    &lt;/variables&gt;\n&lt;/xpath&gt;";
		String lpCompletionCondition = "xpath://&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n&lt;xpath&gt;\n    &lt;expr&gt;number($job/customer/account/@Id) = number($job/customer/account/@Id)&lt;/expr&gt;\n    &lt;namespaces&gt;\n        &lt;namespace URI=\"http://www.w3.org/1999/XSL/Transform\" pfx=\"xsl\"/&gt;\n        &lt;namespace URI=\"http://www.w3.org/2001/XMLSchema\" pfx=\"xsd\"/&gt;\n    &lt;/namespaces&gt;\n    &lt;variables&gt;\n        &lt;variable&gt;job&lt;/variable&gt;\n        &lt;variable&gt;job&lt;/variable&gt;\n    &lt;/variables&gt;\n&lt;/xpath&gt;";
		FlowNodeHelper.setLoopCharacteristics(flowNodeRF, BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS.getName(), false, null, "0", true, lpCardinality, "Complex", lpCompletionCondition);
		String exp = "xpath://&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?>\n&lt;xpath>\n    &lt;expr>$job/customer/@Id&lt;/expr>\n    &lt;namespaces>\n        &lt;namespace URI=\"http://www.w3.org/1999/XSL/Transform\" pfx=\"xsl\"/>\n        &lt;namespace URI=\"http://www.w3.org/2001/XMLSchema\" pfx=\"xsd\"/>\n    &lt;/namespaces>\n    &lt;variables>\n        &lt;variable>job&lt;/variable>\n    &lt;/variables>\n&lt;/xpath>";
		FlowNodeHelper.setTimeout(flowNodeRF, exp, "/Events/RecvTaskTimeout", "Seconds");
		
		String inputXSLT = "xslt://{{/RuleFunctions/CreateCustomerAccount}}&lt;?xml version=\\&quot;1.0\\&quot; encoding=\\&quot;UTF-8\\&quot;?>\\n&lt;xsl:stylesheet xmlns:xsd=\\&quot;http://www.w3.org/2001/XMLSchema\\&quot; xmlns:xsl=\\&quot;http://www.w3.org/1999/XSL/Transform\\&quot; xmlns:ns=\\&quot;www.tibco.com/be/ontology/RuleFunctions/CreateCustomerAccount\\&quot; version=\\&quot;1.0\\&quot; exclude-result-prefixes=\\&quot;xsl xsd\\&quot;>\\n    &lt;xsl:output method=\\&quot;xml\\&quot;/>\\n    &lt;xsl:param name=\\&quot;job\\&quot;/>\\n    &lt;xsl:template match=\\&quot;/\\&quot;>\\n        &lt;ns:CreateCustomerAccount>\\n            &lt;args>\\n                &lt;ns:c>\\n                    &lt;xsl:value-of select=\\&quot;$job/customer\\&quot;/>\\n                &lt;/ns:c>\\n            &lt;/args>\\n        &lt;/ns:CreateCustomerAccount>\\n    &lt;/xsl:template>\\n&lt;/xsl:stylesheet>";
		FlowNodeHelper.setFlowNodeInputMapperXslt(flowNodeRF, inputXSLT);
		
		EObjectWrapper<EClass, EObject> flowNodeReceiveTask = FlowNodeHelper.createFlowNode(processWrapper, BpmnModelClass.END_EVENT.getName(), new String[]{laneId});
		FlowNodeHelper.setFlowElementBaseAttributes(processWrapper, flowNodeReceiveTask, 32, "My End Task", "/Events/DebugOut", null);
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(flowNodeReceiveTask, 2, "My End Task Label", false, null);
		FlowNodeHelper.setFlowNodePoint(flowNodeReceiveTask, 31.129263793873037, 12.227418915086552, true);
		FlowNodeHelper.setFlowNodePoint(flowNodeReceiveTask, 31.129263793872923, -21.739247751524736, false);
		
		FlowNodeHelper.setMessageStarters(flowNodeReceiveTask, "/Rules/MyDT", false, true);
		FlowNodeHelper.setMessageStarters(flowNodeReceiveTask, "/Rules/MyDT2", true, false);
		
		EObjectWrapper<EClass, EObject> sequenceFlow = SequenceFlowHelper.createSequenceFlow(processWrapper, new String[]{laneId}, "MyProcess.My_RF_Task_0", "MyProcess.My_End_Task_0");
		SequenceFlowHelper.setBaseSequenceFlowAttributes(processWrapper, sequenceFlow, 45, "SequenceFlow", true, null,null);
		
		EObjectWrapper<EClass, EObject> exclusiveGateway = FlowNodeHelper.createFlowNode(processWrapper, BpmnModelClass.EXCLUSIVE_GATEWAY.getName(), new String[]{laneId});
		FlowNodeHelper.setFlowElementBaseAttributes(processWrapper, exclusiveGateway, 35, "Exclusive", null, null);
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(exclusiveGateway, 2, null, false, "gateway.exclusive");
		FlowNodeHelper.setFlowNodePoint(exclusiveGateway, 200.04749547354072, 78.7989730201299, true);
		FlowNodeHelper.setFlowNodePoint(exclusiveGateway, 200.04749547354072, 43.79897302012989, false);
		FlowNodeHelper.setGatewayDirection(exclusiveGateway, "DIVERGING");
		
		EObjectWrapper<EClass, EObject> sequenceFlow2 = SequenceFlowHelper.createSequenceFlow(processWrapper, new String[]{laneId}, "MyProcess.My_Start_Task_0", "MyProcess.Exclusive_0");
		SequenceFlowHelper.setBaseSequenceFlowAttributes(processWrapper, sequenceFlow2, 46, "SequenceFlow", true, null,null);
		
		String transformation = "xslt://{{/Processes/test}}&lt;?xml version=\\&quot;1.0\\&quot; encoding=\\&quot;UTF-8\\&quot;?>&lt;xsl:stylesheet xmlns:xsd=\\&quot;http://www.w3.org/2001/XMLSchema\\&quot; xmlns:xsl=\\&quot;http://www.w3.org/1999/XSL/Transform\\&quot; version=\\&quot;1.0\\&quot; exclude-result-prefixes=\\&quot;xsl xsd\\&quot;> &lt;xsl:output method=\\&quot;xml\\&quot;/>&lt;xsl:param name=\\&quot;job\\&quot;/>&lt;xsl:template match=\\&quot;/\\&quot;>&lt;job>&lt;xsl:copy-of select=\\&quot;$job/ancestor-or-self::*/namespace::node()\\&quot;/>&lt;xsl:copy-of select=\\&quot;$job/@*\\&quot;/>&lt;xsl:copy-of select=\\&quot;$job/node()\\&quot;/>&lt;/job>&lt;/xsl:template>&lt;/xsl:stylesheet>";
		FlowNodeHelper.setExclusiveGateway(processWrapper, exclusiveGateway, "MyProcess.SequenceFlow_0", transformation, "MyProcess.SequenceFlow_1");
		
		EObjectWrapper<EClass, EObject> parallelGateway = FlowNodeHelper.createFlowNode(processWrapper, BpmnModelClass.PARALLEL_GATEWAY.getName(), new String[]{laneId});
		FlowNodeHelper.setFlowElementBaseAttributes(processWrapper, parallelGateway, 36, "Parallel", null, null);
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(parallelGateway, 2, null, false, "parallel.exclusive");
		FlowNodeHelper.setFlowNodePoint(parallelGateway, 200.04749547354072, 78.7989730201299, true);
		FlowNodeHelper.setFlowNodePoint(parallelGateway, 200.04749547354072, 43.79897302012989, false);
		FlowNodeHelper.setGatewayDirection(parallelGateway, "CONVERGING");
		
		EObjectWrapper<EClass, EObject> sequenceFlow3 = SequenceFlowHelper.createSequenceFlow(processWrapper, new String[]{laneId}, "MyProcess.Exclusive_0", "MyProcess.Parallel_0");
		SequenceFlowHelper.setBaseSequenceFlowAttributes(processWrapper, sequenceFlow3, 47, "SequenceFlow", true, null,null);
		
		String mergeExpression = "xpath://&lt;?xml version=\\&quot;1.0\\&quot; encoding=\\&quot;UTF-8\\&quot;?>\n&lt;xpath>\n    &lt;expr>$job/@state&lt;/expr>\n    &lt;namespaces>\n        &lt;namespace URI=\\&quot;http://www.w3.org/1999/XSL/Transform\\&quot; pfx=\\&quot;xsl\\&quot;/>\n        &lt;namespace URI=\\&quot;http://www.w3.org/2001/XMLSchema\\&quot; pfx=\\&quot;xsd\\&quot;/>\n    &lt;/namespaces>\n    &lt;variables>\n        &lt;variable>job&lt;/variable>\n    &lt;/variables>\n&lt;/xpath>";
		FlowNodeHelper.setParallelGateway(parallelGateway, "/RuleFunctions/GatewayRF", mergeExpression, null);
		
		EObjectWrapper<EClass, EObject> sequenceFlow4 = SequenceFlowHelper.createSequenceFlow(processWrapper, new String[]{laneId}, "MyProcess.Parallel_0", "MyProcess.My_End_Task_0");
		SequenceFlowHelper.setBaseSequenceFlowAttributes(processWrapper, sequenceFlow4, 48, "SequenceFlow", true, null,null);
		
		EObjectWrapper<EClass, EObject> annotationTask = FlowNodeHelper.createTextAnnotationNode(processWrapper, BpmnModelClass.TEXT_ANNOTATION.getName());
		FlowNodeHelper.setFlowElementBaseAttributes(processWrapper, annotationTask, 36, null, null, null);
		FlowNodeHelper.setFlowElementBaseExtentionAttributes(annotationTask, 0, "Script Notes", false, null);
		FlowNodeHelper.setTextAnnotationValue(annotationTask, "Some Info");
		
		EObjectWrapper<EClass, EObject> association = SequenceFlowHelper.createAssociationNode(processWrapper, "MyProcess.My_RF_Task_0", "MyProcess.TextAnnotation_0", "My Association", null);
		
		
//		URI filePath = URI.createFileURI("D:/MyProcess.beprocess");
//		CommonECoreHelper.serializeMetaModelXMI(filePath, processWrapper.getEInstance());
		byte[] artifactContents = serializeProcessEMFModel(processWrapper.getEInstance());
		
		File artifactFile = new File("D:/MyProcess.beprocess");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(artifactFile);
			fos.write(artifactContents);
		} catch (IOException e) {
			throw new SCSException(e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
		}
	}

}
