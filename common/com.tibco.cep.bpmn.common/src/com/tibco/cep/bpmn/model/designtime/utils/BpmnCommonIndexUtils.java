package com.tibco.cep.bpmn.model.designtime.utils;

import com.tibco.cep.bpmn.model.designtime.BpmnModelCache;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants.*;
import static com.tibco.cep.studio.core.utils.ModelUtils.getPersistenceOptions;

public class BpmnCommonIndexUtils {
	
	
	public static final String BPMN_PROCESS_EXTENSION = "beprocess";
	public static final String BPMN_INDEX_EXTENSION = "bdx";
	public static final String BPMN_INDEX_SCHEME = "bdx";
	
	public static final String ANNOTATION_BPMN_JAVA_CLASS_METHOD_TASK = "JavaTaskMethod";//$NON-NLS-1$
	public static final String JAVA_TASK_RETURN_TYPE_NAME = "RETURN";//$NON-NLS-1$
	public static final String JAVA_TASK_TYPE_ANNOTATION = "ModelTypeMap";//$NON-NLS-1$
	public static final String JAVA_TASK_MODEL_TYPE_MAP = "ModelTypeMap";//$NON-NLS-1$
	public static final String JAVA_TASK_MODEL_TYPE = "ModelType";//$NON-NLS-1$
	
	public static final String PATH_SEPARATOR = new String(new char[]{Path.SEPARATOR});
	public static final String DOT = ".";
	public static final Map<String,String> extensionMap = new HashMap<String,String>();
	private static boolean sunParserAvailable = true;
	private static boolean sunParserCheckComplete = false;
	static {
		extensionMap.put("Definitions", BPMN_PROCESS_EXTENSION);
		extensionMap.put("Process", BPMN_PROCESS_EXTENSION);
	}

	public static String[] tnsExtensions = new String[] { BPMN_PROCESS_EXTENSION, };

	/**
	 * @param element
	 * @return
	 */
	public static EObject getSerializableParent(EObject element) {
		EObject e = element;
		while(e  != null ){
			if(isSerializable(e)) {
				return e;
			}
			e = e.eContainer();
		}
		return null;
	}
	
	/**
	 * @param element
	 * @return
	 */
	public static boolean isSerializable(EObject element) {
		if(element == null) {
			return false;
		}
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(element);
		return eWrapper.isInstanceOf(BpmnModelClass.SERIALIZABLE_ELEMENT);
	}
	
	/**
	 * @param element
	 * @return
	 */
	public static boolean isRootElement(EObject element) {
		if(element == null) {
			return false;
		}
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(element);
		return eWrapper.isInstanceOf(ROOT_ELEMENT);
	}
	
	public static boolean isBaseElement(EObject element) {
		if(element == null) {
			return false;
		}
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(element);
		return eWrapper.isInstanceOf(BASE_ELEMENT);
	}
	
	
	public static boolean isBpmnProcess(EObject element) {
		if(element == null) {
			return false;
		}
		EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(element);
		return eWrapper.isInstanceOf(PROCESS);
	}
	
	public static String getElementPath(EObject element) {
		EObject serializable = getSerializableParent(element);
		if(serializable != null) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(element);
            final String attribute = (String) wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
            if (null != attribute) {
                Path path = new Path(attribute);
                path = path.append(
                        wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME) + "." + getFileExtension(element));
                return path.toPortableString();
            }
		}
		return null;
	}
	
	public static String removeExtension(String s) {

	    String separator = System.getProperty("file.separator");
	    String filename;

	    // Remove the path upto the filename.
	    int lastSeparatorIndex = s.lastIndexOf(separator);
	    if (lastSeparatorIndex == -1) {
	        filename = s;
	    } else {
	        filename = s.substring(lastSeparatorIndex + 1);
	    }

	    // Remove the extension.
	    int extensionIndex = filename.lastIndexOf(".");
	    if (extensionIndex == -1)
	        return filename;

	    return filename.substring(0, extensionIndex);
	}

	
	/**
	 * @param element
	 * @return
	 */
	public static String getFileExtension(EObject element) {
		EObject serializable = getSerializableParent(element);
		if(serializable != null) {
			String elementType = getElementType(serializable);
			return extensionMap.get(elementType);
		}
		return null;
	}

	/**
	 * @param element
	 * @return
	 */
	private static String getElementType(EObject element) {
		return element.eClass().getName();
	}

	
	
	
	public static EObject getElement(String projectName, String fullElementPath) {
		if (fullElementPath == null) {
			return null;
		}
		Path path = new Path(fullElementPath);		
		EObject index = getIndex(projectName);
		if(index != null){
			BpmnIndex ontology = new DefaultBpmnIndex(index);
			Collection<EObject> selements = ontology.getAllElementsByType(BpmnModelClass.SERIALIZABLE_ELEMENT);
			for(EObject selement:selements) {
				if(path.equals(new Path(getElementPath(selement)))) {
					return selement;
				}
			}
		}

		
		return null;
	}
	
	public static EObject getIndex(String indexName) {
		// need a way to wait for loading to complete here, without referencing the job manager
		return BpmnModelCache.getInstance().getIndex(indexName);
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	public static List<EObject> getAllProcesses(String projectName) {
		return getAllBpmnEntities(projectName,new EClass[] {BpmnModelClass.PROCESS});
	}
	
	public static List<EObject> getAllInterfaces(String projectName) {
		return getAllBpmnEntities(projectName,new EClass[] {BpmnModelClass.INTERFACE});
	}
	
	public static List<EObject> getAllItemDefinitions(String projectName) {
		return getAllBpmnEntities(projectName,new EClass[] {BpmnModelClass.ITEM_DEFINITION});
	}
	
	public static List<EObjectWrapper<EClass, EObject>> getAllItemDefinitionsUsingLoc(String projectName, String location) {
		List<EObjectWrapper<EClass, EObject>> itemDefs = new ArrayList<EObjectWrapper<EClass,EObject>>();
		List<EObject> allBpmnEntities = getAllBpmnEntities(projectName,new EClass[] {BpmnModelClass.ITEM_DEFINITION});
		for (EObject eObject : allBpmnEntities) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
			EObject imp = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
			if(imp != null){
				EObjectWrapper<EClass, EObject> impWrapper = EObjectWrapper.wrap(imp);
				String loc = impWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
				if(loc != null && loc.equals(location))
					itemDefs.add(wrap);
			}
		}
		return itemDefs;
	}
	
	
	public static List<EObject> getAllImports(String projectName) {
		return getAllBpmnEntities(projectName,new EClass[] {BpmnModelClass.IMPORT});
	}
	
	
	public static Map<String, EObjectWrapper<EClass, EObject>> getAllItemDefinitionsMap(
			String projectName) {
		Map<String, EObjectWrapper<EClass, EObject>> itemDefMap = new HashMap<String, EObjectWrapper<EClass, EObject>>();
		List<EObject> allBpmnEntities = getAllItemDefinitions(projectName);
		for (EObject eObject : allBpmnEntities) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
			String attribute = wrap
					.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			itemDefMap.put(attribute, wrap);
		}

		return itemDefMap;
	}
	

	
	public static EObjectWrapper<EClass, EObject> getOperationUsingName(
			String projectName,String interfaceName, String operationName) {
		
		List<EObject> allBpmnEntities = getAllBpmnEntities(projectName,
				new EClass[] { BpmnModelClass.INTERFACE });
		for (EObject eObject : allBpmnEntities) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
			if(wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).equals(interfaceName)){
				EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OPERATIONS);
				for (EObject eObject2 : listAttribute) {
					EObjectWrapper<EClass, EObject> operation = EObjectWrapper.wrap(eObject2);
					
					if(operation.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).equals(operationName))
						return operation;
				}
			}
			
		}

		return null;
	}
	
	
	public static List<EObject> getMessageList(
			String projectName) {
		
		List<EObject> allBpmnEntities = getAllBpmnEntities(projectName,
				new EClass[] { BpmnModelClass.MESSAGE });

		return allBpmnEntities;
	}
	
	
	
	
	/**
	 * @param projectName
	 * @param types
	 * @return
	 */
	public static  List<EObject> getAllBpmnEntities(String projectName, EClass[] types) {
		return getAllBpmnEntities(projectName, types, true);
	}
	
	/**
	 * @param projectName
	 * @param types
	 * @param waitForUpdate
	 * @return
	 */
	public static  List<EObject> getAllBpmnEntities(String projectName, EClass[] types , boolean waitForUpdate) {
		if (waitForUpdate){
			waitForUpdate();
		}
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}
		
		try {
			EObject index = getIndex(projectName);
			List<EObject> filteredEntities = new ArrayList<EObject>();
			
			
			if (index != null) {
				for (EClass type : types) {
					filteredEntities.addAll((Collection<EObject>) internalGetAllBpmnEntities(type, index));
				}
			}
			return filteredEntities;
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
	}
	
	/**
	 * @param type
	 * @param index
	 * @return
	 */
	private static List<EObject> internalGetAllBpmnEntities(EClass type,
			EObject index) {
		List<EObject> filteredEntities = new ArrayList<EObject>();
		if(index != null){
			BpmnIndex ontology = new DefaultBpmnIndex(index);
			Collection<EObject> entities = ontology.getAllElementsByType(type);
			
			for (EObject entityEntry:entities) {
				if (entityEntry.eClass() == type ) {
					filteredEntities.add(entityEntry);
				}				
			}
			//TODO: Manish add ref project support
//			EList<DesignerProject> referencedProjects = getReferencedProjects();
//			for (int i=0; i<referencedProjects.size(); i++) {
//				DesignerProject designerProject = referencedProjects.get(i);
//				filteredEntities.addAll(internalGetAllBpmnEntities(type, index));
//			}
		}
		return filteredEntities;
	}
	
	
	/**
	 * @return
	 */
	public synchronized static boolean sunClassAvailable() {
		if(System.getProperty("os.name").equals("AIX") || System.getProperty("os.arch").equals("s390x")){
			//BE-19680: Sun parsers are provided by AIX since JDK 1.7 but causes ClassCastException, so avoid loading sun classes on AIX 
			sunParserCheckComplete = true;
			sunParserAvailable = false;
			return sunParserAvailable;
		}
		if (sunParserCheckComplete) {
			if (sunParserAvailable) {
				// just set it here so that it doesn't need to be set in all places that perform this check
    			System.setProperty("javax.xml.parsers.SAXParserFactory",
    				"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
    			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
    				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
			}
			return sunParserAvailable;
		}
		try {
			sunParserAvailable = Class.forName("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl") != null;
		} catch (ClassNotFoundException e) {
			sunParserAvailable = false;
		}
		sunParserCheckComplete = true;
		if (sunParserAvailable) {
			// just set it here so that it doesn't need to be set in all places that perform this check
			System.setProperty("javax.xml.parsers.SAXParserFactory",
				"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		}
		return sunParserAvailable;
	}
	private static void waitForUpdate() {
		// does nothing in this context, updates not possible
		// if callers need to wait for index updates, they should
		// call BpmnIndexUtils equivalent methods
	}
	
	/**
	 * @param var
	 * @return
	 */
	public static EObject getRootContainer(EObject var) {
		if(BpmnModelClass.PROCESS.isSuperTypeOf(var.eClass())){
			return var;
		}
		while (var.eContainer() != null && !BpmnModelClass.PROCESS.isSuperTypeOf(var.eClass())) {
			var = var.eContainer();
		}
		return var;
	}
	
	/**
	 * get xmi resource content bytes
	 * @param eObject
	 * @param projectIndexUriMap TODO
	 * @since 4.0
	 * @return
	 * @throws IOException
	 */
	public static byte[] getEObjectContents(final String containerPath, final EObject eObject,URIHandler uriHandler)
	throws IOException {
//		final URI baseURI = containerPath == null ? null : URI.createFileURI(containerPath);
//		System.out.println("CommonIndexUtils.getEObjectContents():Using base URI as "+baseURI);
		Resource resource = null;
		Resource oldResource = eObject.eResource();
		if (eObject.eResource() != null && eObject.eResource().getURI() != null) {
			// Factory factory =
			// eObject.eResource().getResourceSet().getResourceFactoryRegistry().getFactory(eObject.eResource().getURI());
			Factory factory = Resource.Factory.Registry.INSTANCE
			.getFactory(eObject.eResource().getURI());
			resource = factory.createResource(eObject.eResource().getURI());
		}
		if (resource == null) {
			resource = new XMIResourceImpl(URI.createURI(""));
		}
		resource.getContents().add(eObject);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			HashMap<Object, Object> persistenceOptions = new HashMap<Object, Object>(getPersistenceOptions());
			persistenceOptions.put(XMIResource.OPTION_URI_HANDLER, uriHandler);
			resource.save(os, persistenceOptions);
			return os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				if (oldResource != null) {
					oldResource.getContents().add(eObject);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * @param ext
	 * @return
	 */
	public static boolean isBpmnType(String ext) {
		return BPMN_PROCESS_EXTENSION.equals(ext);
	}


	/**
	 * @param fileExtension
	 * @return
	 */
	public static boolean isTnsCacheResource(String fileExtension) {
		// needs to be case insensitive...
		if (Arrays.binarySearch(tnsExtensions, fileExtension) >= 0) {
			return true;
		}
		// do a case insensitive search. Slow, but necessary
		for (String ex : tnsExtensions) {
			if (ex.equalsIgnoreCase(fileExtension)) {
				return true;
			}
		}
		return false;
	}

	
	
	public static EObject loadEObject(ResourceSet resourceSet,java.net.URI fileURI, boolean showErrorMessage) {
		URI uri = URI.createURI(fileURI.toString());
		try {
    		Thread thread = Thread.currentThread();
    		ClassLoader loader = thread.getContextClassLoader();
    		if (!sunClassAvailable()) {
    			System.setProperty("javax.xml.parsers.SAXParserFactory",
    				"org.apache.xerces.jaxp.SAXParserFactoryImpl");	               
    			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
    		}
    		Resource resource = null;
    		try {
    			resource = resourceSet.getResource(uri,true);
    		} finally {
    			thread.setContextClassLoader(loader);
    			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
    			CommonIndexUtils.sunClassAvailable();
    		}

			EList<EObject> contents = resource.getContents();
			if (contents != null && contents.size() > 0) {
				//TODO this is to take of the BEViews System Elements which contains more then one element
				return (EObject) contents.get(contents.size()-1);
			}
		} catch (Exception e) {
			if (showErrorMessage) {
				System.err.println("Could not load "+fileURI.toString());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void addRootElement(EObjectWrapper<EClass, EObject> indexWrapper, EObject rootElelement){
		EList<EObject> listAttribute = indexWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_ROOT_ELEMENTS);
		synchronized (listAttribute) {
			listAttribute.add(rootElelement);
		}
	}
	
	public static void removeRootElement(EObjectWrapper<EClass, EObject> indexWrapper, EObject rootElelement){
		EList<EObject> listAttribute = indexWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_ROOT_ELEMENTS);
		synchronized (listAttribute) {
			listAttribute.remove(rootElelement);
		}
	}
	
	public static List<EObject> getRootElements(EObjectWrapper<EClass, EObject> indexWrapper){
		EList<EObject> listAttribute = indexWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_ROOT_ELEMENTS);
		List<EObject> elements = new ArrayList<EObject>();
		synchronized (listAttribute) {
			elements = new ArrayList<EObject>(listAttribute);
		}
		return elements;
	}

}
