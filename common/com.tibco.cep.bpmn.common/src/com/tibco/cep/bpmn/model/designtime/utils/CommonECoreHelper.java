package com.tibco.cep.bpmn.model.designtime.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionFactory;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.flavor.XSDLConstants;
import com.tibco.xml.schema.impl.DefaultFlavor;
import com.tibco.xml.schema.parse.xsd.XSDLSchema;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.util.XSDWriter;

public class CommonECoreHelper {
	
	protected static class ImportSchemaLocator implements XSDWriter.DeclareImportsTns {
			String namespace;
			String[] myns = new String[0];
	
			public ImportSchemaLocator(String namespace) {
				if (!namespace.equals("/")) {
					if (namespace.startsWith("/")) {
						namespace = namespace.substring(1);
					}
					this.namespace = namespace;
					myns = namespace.split("/");
				}
			}
	
			public String getLocation(String ns, String loc) {
				if (BE_BASE_CONCEPT_NS.equals(ns) || BE_BASE_EVENT_NS.equals(ns)) {
					return null;
				}
				String relative_location = getRelativeLocation(ns);
				return relative_location;
			}
	
			public String getLocation(String location) {
				return location;
			}
	
			public boolean declareImport(String s) {
				return true;
			}
	
			private String getRelativeLocation(String ns) {
	
				ns = ns.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length() + 1);
				String[] st = ns.split("/");
	
				String relNs = "";
				if (myns.length <= st.length - 1) {
	
					int len_limit = myns.length;
	
					int j = 0;
					for (j = 0; j < len_limit; j++) {
						if (!myns[j].equals(st[j])) {
							break;
						}
					}
					int k = 0;
					for (k = len_limit - 1; k >= j; k--) {
						relNs += "../";
					}
	
					for (k = j; k < st.length - 1; k++) {
						relNs = relNs + st[k] + "/";
					}
					relNs += st[st.length - 1];
				} else {
	
					for (int i = myns.length; i > st.length - 1; i--) {
						relNs += "../";
					}
	
					int len_limit = st.length - 1;
					int j = 0;
					for (j = 0; j < len_limit; j++) {
						if (!myns[j].equals(st[j])) {
							break;
						}
					}
					int k = 0;
					for (k = len_limit - 1; k >= j; k--) {
						relNs += "../";
					}
	
					for (k = j; k < st.length - 1; k++) {
						relNs = relNs + st[k] + "/";
					}
					relNs += st[st.length - 1];
	
				}
				return relNs + ".xsd";
			}
		}

	private static final String DEFAULT_ENCODING = "UTF-8"; //$NON-NLS-1$
	public static final String DEFAULT_PACKAGE_SEPARATOR = "."; //$NON-NLS-1$
	public static final String BE_BASE_CONCEPT_NS = "www.tibco.com/be/ontology/_BaseConcept";
	public static final String BE_BASE_EVENT_NS = "www.tibco.com/be/ontology/_BaseEvent";
	public static final String BE_BASE_SOAP_EVENT_NS = "www.tibco.com/be/ontology/_BaseSOAPEvent";
	public static final String BE_BASE_CONTAINED_CONCEPT_NS = "www.tibco.com/be/ontology/_BaseContainedConcept";
	public static final String BE_BASE_TIME_EVENT_NS = "www.tibco.com/be/ontology/_BaseTimeEvent";
	public static final String BE_BASE_ENTITY_NS = "www.tibco.com/be/ontology/_BaseEntity";
	public static final String BE_BASE_EXCEPTION_NS = "www.tibco.com/be/ontology/_BaseException";
	public static final String BE_BASE_ADVISORY_EVENT_NS = "www.tibco.com/be/ontology/_BaseAdvisoryEvent";
	public static final String DEFAULT_PATH_SEPARATOR = "/"; //$NON-NLS-1$
	private static MutableSchema S_SCHEMA = new XSDLSchema();
	private static DefaultFlavor S_FLAVOR = new DefaultFlavor(
				XSDLConstants.NAMESPACE, XSDL.FAMILY, "1.0", "xsd");
	
	
	
//	/**
//	 * @deprecated
//	 * @param classSpec
//	 * @return
//	 */
//	public static String getClassNameFromQualifiedName(String classSpec) {
//		String className = null;
//		int pos = classSpec.lastIndexOf(DEFAULT_PACKAGE_SEPARATOR);
//		if(pos != -1) {
//			className = classSpec.substring(pos + 1);
//		}
//		return className;
//	}
	
	
//	/**
//	 * @deprecated
//	 * @param classname
//	 * @return
//	 */
//	public static String getQualifiedPackageName(String classname) {
//		String packageName = null;
//        int pos = classname.lastIndexOf(DEFAULT_PACKAGE_SEPARATOR);
//        if (pos != -1) {
//        	packageName = classname.substring(0, pos);
//        }
//        return packageName;
//	}
	
//	/**
//	 * @deprecated
//	 * @param ePackage
//	 * @return
//	 */
//	public static String getQualifiedPackageName(EPackage ePackage) {
//		String packageSpec = ePackage.getName();
//		while(ePackage.getESuperPackage() != null){
//			packageSpec = ePackage.getESuperPackage().getName()+CommonECoreHelper.DEFAULT_PACKAGE_SEPARATOR+packageSpec;
//			ePackage = ePackage.getESuperPackage();
//		}
//		return packageSpec;
//	}
	
	/**
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeMetaModelXMI(URI uri) throws Exception {
		return deserializeXMI(BpmnMetaModel.INSTANCE.getResourceSet(),uri, true);		
	}
	
	/**
	 * @param uri
	 * @param resolve TODO
	 * @return
	 * @throws Exception
	 */
	public static EList<EObject> deserializeModelXMI(ResourceSet rset,URI uri,Map<Object,Object> opts, boolean resolve) throws Exception {
//		ResourceSet rset = getModelResourceSet();
		Map<Object, Object> options = new HashMap<Object, Object>();
//		options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF,
//				XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		// options override
		options.putAll(opts);
		return deserializeXMI(rset, uri,options, resolve);
	}

	/**
	 * @param uri
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeXMIResource(Resource res,URI uri) throws Exception {
		Map<Object, Object> options = new HashMap<Object, Object>();
		res.load(options);		
		EList<EObject> contents = res.getContents();
		if (contents != null && contents.size() > 0) {
			return contents;
		}
		return null;
	}
	

	/**
	 * @param uri
	 * @param resolve TODO
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeXMI(ResourceSet resourceSet,URI uri, boolean resolve) throws Exception {
//		Resource resource = null;
		Map<Object, Object> options = new HashMap<Object, Object>();
//		options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF,
//				XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		return deserializeXMI(resourceSet,uri,options, resolve);
	}
	
	/**
	 * @param uri
	 * @param resolve TODO
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeXMI(ResourceSet resourceSet,URI uri,Map<Object,Object> options, boolean resolve) throws Exception {
		synchronized (resourceSet) {
			XMLResource resource = null;
			synchronized (resourceSet) {
				EList<Resource> resources = resourceSet.getResources();
				for (Iterator<Resource> iterator = resources.iterator(); iterator.hasNext();) {
					Resource r = iterator.next();
					if (!(r instanceof XMLResource)) {
						continue;
					}
					if(r != null && r.getURI() != null && r.getURI().equals(uri)) {
						iterator.remove();
						break;
					}
				}

				if(resource == null){
					resource = (XMLResource) resourceSet.createResource(uri,DEFAULT_ENCODING);
				}
				if(resource.isLoaded()) {
					resourceSet.getResources().remove(resource);
					resource = (XMLResource) resourceSet.createResource(uri,DEFAULT_ENCODING);
				}
			}
			
			if(resource != null) {
				resource.setEncoding("UTF-8");
				resource.load(options);	
				if(resolve) {
					EcoreUtil.resolveAll(resource);
				}
				EList<EObject> contents = resource.getContents();
				if (contents != null && contents.size() > 0) {
					return contents;
				}
			}
		}
	
		return null;
	}
	
	
	
	
	/**
	 * @param rset
	 * @param uri
	 * @param is
	 * @param opt
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeXMI(ResourceSet rset,URI uri, InputStream is,Map<Object,Object> opt) throws IOException {
		XMLResource resource = null;
		if(rset  == null) {
			rset = BpmnMetaModel.INSTANCE.getResourceSet();
		}
		Map<Object, Object> options = new HashMap<Object, Object>();
		if(opt != null) {
			options.putAll(opt);
		}
		options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF,
				XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		
		if(is != null) {
			synchronized (rset) {
				resource = (XMLResource) rset.createResource(uri,DEFAULT_ENCODING);
				if( resource != null ){
					resource.setEncoding(DEFAULT_ENCODING);
				}
			}
		}
		return deserializeXMI(resource, is, options);
	}
	
	
	
	/**
	 * @param resource
	 * @param is
	 * @param opt
	 * @return
	 * @throws IOException
	 */
	public static EList<EObject> deserializeXMI(XMLResource resource, InputStream is,Map<Object,Object> opt) throws IOException {
		Map<Object, Object> options = new HashMap<Object, Object>();
		if(opt != null) {
			options.putAll(opt);
		}
		options.put(XMIResource.OPTION_PROCESS_DANGLING_HREF,
				XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		if(is != null) {
			resource.load(is, options);
		}
		EList<EObject> contents = resource.getContents();
		if (contents != null && contents.size() > 0) {
			return contents;
		}
		return null;
	}
	
	
	/**
	 * @param uri
	 * @param eObject
	 * @throws IOException
	 */
	public static void serializeMetaModelXMI(URI uri, EObject eObject) throws IOException {
		ResourceSet resourceSet = BpmnMetaModel.INSTANCE.getResourceSet();
		resourceSet.getURIConverter().getURIMap().clear();
//		String fileName = uri.devicePath().substring(uri.devicePath().lastIndexOf(DEFAULT_PATH_SEPARATOR)+1);
//		String project = fileName.substring(0,fileName.lastIndexOf(DEFAULT_PACKAGE_SEPARATOR));

		XMLResource resource = null;
		synchronized (resourceSet) {
			resource = (XMLResource) resourceSet.createResource(uri);	
			resource.setEncoding("UTF-8");
			resource.getContents().add(eObject);
		}
		if (resource != null)
			CommonECoreHelper.serializeXMI(resource);
		
	}
	
	
	
	
	/**
	 * @param uri
	 * @param eObject
	 * @throws IOException
	 */
	public static void serializeModelXMI(ResourceSet rset,URI uri, EObject eObject,Map<Object,Object> opts) throws IOException {
		synchronized (rset) {
			EList<Resource> resources = rset.getResources();
			for (Iterator<Resource> iterator = resources.iterator(); iterator.hasNext();) {
				Resource next = iterator.next();
				if (!(next instanceof XMLResource)) {
					continue;
				}
				XMLResource r = (XMLResource) next;
				if(r != null && r.getURI() != null && r.getURI().equals(uri)) {
					iterator.remove();
					break;
				}
			}
			XMLResource resource = (XMLResource) rset.createResource(uri);
			resource.setEncoding(DEFAULT_ENCODING);
			resource.getContents().add(eObject);
			
			Map<Object,Object> options = ModelUtils.getPersistenceOptions();
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
			options.put(XMLResource.OPTION_ENCODING, DEFAULT_ENCODING);
			//options override
			options.putAll(opts);
			CommonECoreHelper.serializeXMI(resource,options);
		}
	}
	
	

	

	
	
	/**
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public static Resource serializeXMI(Resource resource) throws IOException {
		Map<Object,Object> options = ModelUtils.getPersistenceOptions();
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		options.put(XMLResource.OPTION_ENCODING, DEFAULT_ENCODING);
		serializeXMI(resource,options);
		return resource;
	}
	

	
	
	/**
	 * @param options2 
	 * @param monitor 
	 * @param eObj
	 * @param fileURI
	 * @throws IOException
	 */
	public static Resource serializeXMI(Resource resource, Map<Object, Object> options) throws IOException{
		resource.save(options);
		return resource;
	}
	
	

	
	/**
	 * @param indexWrapper
	 */
	public static void loadExtensions(EObjectWrapper<EClass, EObject> indexWrapper) {
		Collection<EObject> extDefs = BpmnMetaModel.INSTANCE
				.getExtensionDefinitions();
		synchronized (extDefs) {
			for (EObject extDef : extDefs) {
				EObject temp = EcoreUtil.copy(extDef);
				EObjectWrapper<EClass, EObject> extn = ExtensionFactory
						.createExtension(EObjectWrapper.wrap(temp));
				indexWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_EXTENSIONS,
						extn.getEInstance());
			}
		}
	}

	public static AdapterFactoryEditingDomain getEditingDomain(String fileURI) throws Exception {
		ResourceSet xmiRes = new ResourceSetImpl();		
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(
				factories);

		CommandStack commandStack = new BasicCommandStack();
		AdapterFactoryEditingDomain domain = new AdapterFactoryEditingDomain(
				adapterFactory, commandStack, xmiRes);
		// register the packages
		for (EPackage p : BpmnMetaModel.getAllPackages()) {
			if (p.getNsURI() != null) {
				domain.getResourceSet().getPackageRegistry().put(p.getNsURI(),
						p);
			}
		}
		File file = new File(fileURI);
		if(file.exists()) {
			deserializeXMI(domain.getResourceSet(), URI.createFileURI(fileURI), true);
			
		}
		return domain;
	}
	
//	/**
//	 * @deprecated
//	 * @param element
//	 * @return
//	 */
//	public static String getClassSpec(EClass eClass) {
//		return getPackageSpec(eClass.getEPackage())+"."+eClass.getName();
//	}
	
	
//	/**
//	 * @deprecated
//	 * @param element
//	 * @return
//	 */
//	public static String getPackageSpec(EPackage ePackage) {
//		String packageSpec = null;
//		while(ePackage != null) {
//			if(packageSpec == null) {
//				packageSpec = ePackage.getName();
//			} else {
//				packageSpec = ePackage.getName() +"."+ packageSpec;
//			}
//			ePackage = ePackage.getESuperPackage();
//		}
//		if(packageSpec.endsWith("."))
//			return packageSpec.substring(0,packageSpec.lastIndexOf(".")-1);
//		else
//			return packageSpec;
//	}
	
	/**
	 * @param eClass
	 * @return
	 */
	public static ExpandedName getExpandedName(EClassifier eClass) {
		return ExpandedName.makeName(eClass.getEPackage().getNsURI(), eClass.getName());
	}
	
	/**
	 * @param enLiteral
	 * @return
	 */
	public static ExpandedName getExpandedName(EEnumLiteral enLiteral) {
		String nsURI = enLiteral.getEEnum().getEPackage().getNsURI() + DEFAULT_PATH_SEPARATOR+enLiteral.getEEnum().getName();
		return ExpandedName.makeName(nsURI, enLiteral.getName());
	}
	
	/**
	 * @param ePackage
	 * @return
	 */
	public static ExpandedName getExpandedName(EPackage ePackage) {
		// A package name is unique within its parent's namespace i.e
		// if package A contains package B which contains class B
		// then ExName of package  = B {/A}B and class B = {/A/B}B
		if(ePackage.getESuperPackage() == null) {
			return ExpandedName.makeName(ePackage.eResource().getURI().toString(), ePackage.getName());
		}
		return ExpandedName.makeName(ePackage.getESuperPackage().getNsURI(), ePackage.getName());
	}
	
	
	
	/**
     * Converts the string with a camel case or with underscores and replace it 
     * with spaces between each word, and underscores replaced by spaces.
     * For example "javaProgramming" and "JAVA_PROGRAMMING"
     * would both become Java Programming".
     * @param str The String to convert
     * @return The converted String
     */
    public static String toTitleCase( String str )
    {
        if( str == null || str.length() == 0 )
        {
            return str;
        }
 
        StringBuffer result = new StringBuffer();
 
        /*
         * Pretend space before first character
         */
        char prevChar = ' ';
 
        /*
         * Change underscore to space, insert space before capitals
         */
        for( int i = 0; i < str.length(); i++ )
        {
            char c = str.charAt( i );
            if( c == '_' )
            {
                result.append( ' ' );
            }
            else if( prevChar == ' ' || prevChar == '_' )
            {
                result.append( Character.toUpperCase( c ) );
            }
            else if( Character.isUpperCase( c ) && !Character.isUpperCase( prevChar ) )
            {
                /*
                 * Insert space before start of word if camel case
                 */
                result.append( ' ' );
//                result.append( Character.toUpperCase( c ) );
                result.append( c );
            }
            else
            {
//            	result.append( c );
                result.append( Character.toLowerCase(c) );
            }
 
            prevChar = c;
        }
 
        return result.toString();
    }
    
    
    /**
     * Utitility function to generate the expanded name entries for BpmnMetaModelConstants
     * @param pw TODO
     * @param ePackage
     * @param clazz
     */
    public static Map<String,String> generateMetaModelConstantsInterface(PrintWriter pw, EPackage ePackage, EClassifier clazz) {
    	Map<String,String> attrMap = new HashMap<String,String>(); 
		if(clazz instanceof EClass) {
			String uCaseName = CommonECoreHelper.toTitleCase(clazz.getName()).toUpperCase().replace(' ', '_');
			
			pw.println(
					MessageFormat.format("\tpublic static ExpandedName {0} = ExpandedName.makeName(\"{1}\",\"{2}\");\t//$NON-NLS-1$",
							uCaseName,ePackage.getNsURI(),clazz.getName()));
			EList<EStructuralFeature> sfeatures = ((EClass) clazz).getEAllStructuralFeatures();
			for(EStructuralFeature feature:sfeatures) {
				String fName = feature.getName();
				String tName = "E_ATTR_"+CommonECoreHelper.toTitleCase(feature.getName()).toUpperCase().replace(' ', '_');
				attrMap.put(fName,tName);
			}
		} else if(clazz instanceof EEnum) {
			String uCaseName = "ENUM_"+CommonECoreHelper.toTitleCase(clazz.getName()).toUpperCase().replace(' ', '_');
			pw.println(
					MessageFormat.format("\tpublic static ExpandedName {0} = ExpandedName.makeName(\"{1}\",\"{2}\");\t//$NON-NLS-1$",
							uCaseName,ePackage.getNsURI(),clazz.getName()));
			EEnum e = (EEnum) clazz;
			for(EEnumLiteral el :e.getELiterals()) {
				String litName = uCaseName+"_"+el.getName();
				pw.println(
						MessageFormat.format("\tpublic static ExpandedName {0} = ExpandedName.makeName(\"{1}\",\"{2}\");\t//$NON-NLS-1$",
								litName,ePackage.getNsURI()+DEFAULT_PATH_SEPARATOR+e.getName(),el.getName()));
			}

		}
		pw.flush();
		return attrMap;
	}
    
    
    /**
     * Utitility function to generate the expanded name entries for BpmnMetaModelClass
     * @param pw TODO
     * @param ePackage
     * @param clazz
     */
    public static void generateMetaModelClassInterface(PrintWriter pw, EPackage ePackage, EClassifier clazz) {
		if(clazz instanceof EClass) {
			String uCaseName = CommonECoreHelper.toTitleCase(clazz.getName()).toUpperCase().replace(' ', '_');
			
			pw.println(
					MessageFormat.format("\tpublic static EClass {0} = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.{1});",
							uCaseName,uCaseName));
		} else if(clazz instanceof EEnum) {
			String uCaseName = "ENUM_"+CommonECoreHelper.toTitleCase(clazz.getName()).toUpperCase().replace(' ', '_');
			pw.println(
					MessageFormat.format("\tpublic static EEnum {0} = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.{1});",
							uCaseName,uCaseName));
			EEnum e = (EEnum) clazz;
			for(EEnumLiteral el :e.getELiterals()) {
				String litName = uCaseName+"_"+el.getName();
				pw.println(
						MessageFormat.format("\tpublic static EEnumLiteral {0} = EEnumWrapper.useInstance({1}).getEnumLiteral(BpmnMetaModel.{2});",
								litName,uCaseName,litName));
			}

		}
		pw.flush();
	}
    
    
    /**
     * @param indexWrapper
     * @return
     */
    public static Collection<String> getRootElementIds(EObjectWrapper<EClass, EObject> indexWrapper) {
		Set<String> set = new HashSet<String>();
		if(indexWrapper == null || !indexWrapper.isSet()){
			return Collections.emptySet();
		}
		List<EObject> rElements = BpmnCommonIndexUtils.getRootElements(indexWrapper);
		for(int i=0; i < rElements.size();i++) {
			EObjectWrapper<EClass, EObject> rElementWrapper = EObjectWrapper.wrap(rElements.get(i));
			String rid = rElementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			set.add(rid);
		}
		return set;
	}
    
    public static void loadImports(EObjectWrapper<EClass, EObject> indexWrapper, EMFTnsCache cache, String projName) {
    	loadImports(indexWrapper, projName);
    	Map<ExpandedName, TnsComponent> cachedElements = BpmnCommonModelUtils.getCachedElements(cache);
    	addMissingItemDef(cachedElements, indexWrapper, projName);
    }
    
    private static void loadImports(EObjectWrapper<EClass, EObject> indexWrapper, String projName) {
    	Map<String, ROEObjectWrapper<EClass, EObject>> importTypeList = new HashMap<String, ROEObjectWrapper<EClass,EObject>>();
    	Set<String> itemDefIdList = new HashSet<String>();
    	
    	List<EObject> itemDefinitionsList = BpmnCommonIndexUtils.getAllItemDefinitions(projName);
    	for (EObject eObject : itemDefinitionsList) {
    		ROEObjectWrapper<EClass, EObject> w = ROEObjectWrapper.wrap(eObject);
    		itemDefIdList.add((String)w.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		}
    	
    	List<EObject> allImports = BpmnCommonIndexUtils.getAllImports(projName);
    	for (EObject eObject : allImports) {
    		ROEObjectWrapper<EClass, EObject> w = ROEObjectWrapper.wrap(eObject);
    		importTypeList.put((String)w.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT_TYPE), w);
		}
		
    	//Model RDFTypes
    	
		for (RDFUberType t : RDFTypes.types) {
			SmType st = t.getXSDLTerm();
			ExpandedName en = st.getExpandedName();
			final String location = st.getContainingFragment().getDocument().getLocation();
			if(!(t instanceof RDFPrimitiveTerm)) {
				en = ExpandedName.makeName(RDFTnsFlavor.BE_NAMESPACE,"_Base"+t.getName());
			} 
			if (!importTypeList.keySet().contains(en.toString())) {
				EObjectWrapper<EClass, EObject> importWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.IMPORT);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORT_TYPE,
						en.toString());
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_LOCATION, location);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_NAMESPACE,
						en.getNamespaceURI());
				indexWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORTS,
						importWrapper.getEInstance());
				importTypeList.put(en.toString(), importWrapper);
			}
			
			if(!itemDefIdList.contains(en.toString())){
				EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.ITEM_DEFINITION);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,en.toString());
				ROEObjectWrapper<EClass, EObject> roeObjectWrapper = importTypeList.get(en.toString());
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT,roeObjectWrapper.getEInstance());
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_KIND,
						BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE, st);
				BpmnCommonIndexUtils.addRootElement(indexWrapper, itemDefWrapper.getEInstance());
			}
	
			
	
			// arrays
			en = mkName(en.getNamespaceURI(), en.getLocalName() + "[]");
			if (!importTypeList.keySet().contains(en.toString())) {
				EObjectWrapper<EClass, EObject> importWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.IMPORT);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORT_TYPE, en.toString());
				importWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION,location);
				importWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAMESPACE,	en.getNamespaceURI());
				indexWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_IMPORTS,
						importWrapper.getEInstance());
				importTypeList.put(en.toString(), importWrapper);
			}
//			st = makeListType(en, st);
	
			
			if(!itemDefIdList.contains(en.toString())){
				EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper.createInstance(BpmnModelClass.ITEM_DEFINITION);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,en.toString());
				ROEObjectWrapper<EClass, EObject> roeObjectWrapper = importTypeList.get(en.toString());
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT,roeObjectWrapper.getEInstance());
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_KIND,
						BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE, st);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, true);
				BpmnCommonIndexUtils.addRootElement(indexWrapper, itemDefWrapper.getEInstance());
			}
		}
	}
    
    protected static List<EObjectWrapper<EClass, EObject>> getItemDefinitionUsingLocation(EMFTnsCache cache, EObjectWrapper<EClass, EObject> indexWrapper, String projName, String entityPath){
		List<EObjectWrapper<EClass, EObject>> allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsUsingLoc(projName, entityPath);
		if(allItemDefinitions.size()==0){
			addItemdefinitionForLocation(cache, entityPath, indexWrapper, projName);
		}
//		else{
//			boolean addAgain = false;
//			for (EObjectWrapper<EClass, EObject> eObjectWrapper : allItemDefinitions) {
//				if (eObjectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT) == null){
//					addAgain = true;
//					break;
//				}
//			}
//			
//			if(addAgain){
//				removeItemDefinitionsForLoc(projName, entityPath);
//				addItemdefinitionForLocation(cache, entityPath, indexWrapper, projName);
//			}
//		}
		
		allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsUsingLoc(projName, entityPath);
		
		return allItemDefinitions;
    }
    
    public static void removeItemDefinitionsForLoc(String projName, String loc){
    	List<EObjectWrapper<EClass, EObject>> allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsUsingLoc(projName, loc);
    	EObject index = BpmnCommonIndexUtils.getIndex(projName);
    	if(index != null){
    		EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
    		for (EObjectWrapper<EClass, EObject> eObjectWrapper : allItemDefinitions) {
    			EObject imp = eObjectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
    			indexWrapper.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_IMPORTS, imp);
    			BpmnCommonIndexUtils.removeRootElement(indexWrapper, eObjectWrapper.getEInstance());
			}
    		
    	}
    	
    	
    }

    protected static EObjectWrapper<EClass, EObject> getItemDefinitionUsingNameSpace(EMFTnsCache cache, EObjectWrapper<EClass, EObject> indexWrapper, String projName, String nameSpace){
		Map<String, EObjectWrapper<EClass, EObject>> allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsMap(projName);
		EObjectWrapper<EClass, EObject> eObjectWrapper = allItemDefinitions.get(nameSpace);
		if(eObjectWrapper == null){
			String nmSpace = nameSpace.endsWith("[]")? nameSpace.replace("[]", ""): nameSpace;
			ExpandedName parse = ExpandedName.parse(nmSpace);
			try {
				SmElement element = cache.getSmComponentProvider().getElement(parse);
				Map<ExpandedName, SmElement> smEleMap = new HashMap<ExpandedName, SmElement>();
				smEleMap.put(parse, element);
				addAllItemdefs(smEleMap, indexWrapper, projName);
			} catch (SmGlobalComponentNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
		allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsMap(projName);
		eObjectWrapper = allItemDefinitions.get(nameSpace);
		
		return eObjectWrapper;
    }
    

	private static void addItemdefinitionForLocation(EMFTnsCache cache, String loc, EObjectWrapper<EClass, EObject> indexWrapper, String projName) {
		Map<ExpandedName, TnsComponent> cachedElements = BpmnCommonModelUtils.getCachedElementsForLocation(cache, loc);
		addMissingItemDef(cachedElements, indexWrapper, projName);
	}
	
	private static void addMissingItemDef(
			Map<ExpandedName, TnsComponent> cachedElements,
			EObjectWrapper<EClass, EObject> indexWrapper, String projName) {
		Map<ExpandedName, SmElement> smEleMap = new HashMap<ExpandedName, SmElement>();
		for (Entry<ExpandedName, TnsComponent> element : cachedElements
				.entrySet()) {
			ExpandedName exName = element.getKey();
			TnsComponent tnsComponent = element.getValue();
			int type = tnsComponent.getComponentType();
			TnsComponent comp = tnsComponent.getContainingFragment()
					.getComponent(exName.localName, type);
			SmElement smt = null;
			if (comp instanceof SmElement) {
				smt = (SmElement) comp;
				smEleMap.put(exName, smt);

			}
		}
		addAllItemdefs(smEleMap, indexWrapper, projName);
	}
	
	private static void addAllItemdefs(Map<ExpandedName, SmElement> smelementMap,
			EObjectWrapper<EClass, EObject> indexWrapper, String projName) {
		Map<String, EObjectWrapper<EClass, EObject>> importTypeList = new HashMap<String, EObjectWrapper<EClass, EObject>>();
		Map<String, EObjectWrapper<EClass, EObject>> itemDefIdMap = new HashMap<String, EObjectWrapper<EClass, EObject>>();

		List<EObject> itemDefinitionsList = BpmnCommonIndexUtils
				.getAllItemDefinitions(projName);
		for (EObject eObject : itemDefinitionsList) {
			EObjectWrapper<EClass, EObject> w = EObjectWrapper
					.wrap(eObject);
			itemDefIdMap.put((String) w
					.getAttribute(BpmnMetaModelConstants.E_ATTR_ID), w);
		}

		List<EObject> allImports = BpmnCommonIndexUtils.getAllImports(projName);
		for (EObject eObject : allImports) {
			EObjectWrapper<EClass, EObject> w = EObjectWrapper
					.wrap(eObject);
			importTypeList
					.put((String) w
							.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT_TYPE),
							w);
		}

		for (Entry<ExpandedName, SmElement> element : smelementMap.entrySet()) {
			ExpandedName exName = element.getKey();
			SmElement smt = element.getValue();

			String location = null;
			if (exName.getNamespaceURI() != null
					&& !exName.getNamespaceURI().isEmpty()) {
				location = exName.namespaceURI.replace(
						RDFTnsFlavor.BE_NAMESPACE, "");
			}

			if (!importTypeList.containsKey(exName.toString())) {
				EObjectWrapper<EClass, EObject> importWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.IMPORT);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORT_TYPE,
						exName.toString());
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_LOCATION, location);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_NAMESPACE,
						exName.getNamespaceURI());
				indexWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORTS,
						importWrapper.getEInstance());
				importTypeList.put(exName.toString(), importWrapper);
			}

			if (!itemDefIdMap.containsKey(exName.toString())) {
				EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.ITEM_DEFINITION);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,
						exName.toString());
				ROEObjectWrapper<EClass, EObject> roeObjectWrapper = importTypeList
						.get(exName.toString());
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORT,
						roeObjectWrapper.getEInstance());
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_ITEM_KIND,
						BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_STRUCTURE, smt.getType());
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, false);
				BpmnCommonIndexUtils.addRootElement(indexWrapper, itemDefWrapper.getEInstance());

			}else{
				//check for import in itemdef
				EObjectWrapper<EClass, EObject> itemDefWrapper = itemDefIdMap.get(exName.toString());
				EObject imp = (EObject)itemDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
				if(imp == null){
					EObjectWrapper<EClass, EObject> impWrap = importTypeList
							.get(exName.toString());
					itemDefWrapper.setAttribute(
							BpmnMetaModelConstants.E_ATTR_IMPORT,
							impWrap.getEInstance());
				}
			}

			// array
			ExpandedName expArrName = ExpandedName.makeName(
					exName.getNamespaceURI(), exName.getLocalName() + "[]");
			if (!importTypeList.containsKey(expArrName.toString())) {
				EObjectWrapper<EClass, EObject> importWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.IMPORT);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORT_TYPE,
						expArrName.toString());
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_LOCATION, location);
				importWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_NAMESPACE,
						expArrName.getNamespaceURI());
				indexWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORTS,
						importWrapper.getEInstance());
				importTypeList.put(expArrName.toString(), importWrapper);
			}

			if (!itemDefIdMap.containsKey(expArrName.toString())) {
				EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper
						.createInstance(BpmnModelClass.ITEM_DEFINITION);
				itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,
						expArrName.toString());
				ROEObjectWrapper<EClass, EObject> roeObjectWrapper = importTypeList
						.get(expArrName.toString());
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IMPORT,
						roeObjectWrapper.getEInstance());
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_ITEM_KIND,
						BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_STRUCTURE, smt.getType());
				itemDefWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, true);
				BpmnCommonIndexUtils.addRootElement(indexWrapper, itemDefWrapper.getEInstance());
			}else{
				//check for import in itemdef
				EObjectWrapper<EClass, EObject> itemDefWrapper = itemDefIdMap.get(expArrName.toString());
				EObject imp = (EObject)itemDefWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
				if(imp == null){
					EObjectWrapper<EClass, EObject> impWrap = importTypeList
							.get(expArrName.toString());
					itemDefWrapper.setAttribute(
							BpmnMetaModelConstants.E_ATTR_IMPORT,
							impWrap.getEInstance());
				}
			}
		}
	}
    
//    public static void refreshWsdlPortTypes(EObjectWrapper<EClass, EObject> indexWrapper){
//    	Map<String, EObjectWrapper<EClass, EObject>> allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsMap((String)indexWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
//    	List<EObject> rootElements = new ArrayList<EObject>(indexWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_ROOT_ELEMENTS));
//    	for(Iterator<EObject> it = rootElements.iterator();it.hasNext();) {
//    		ROEObjectWrapper<EClass, EObject> w = ROEObjectWrapper.wrap(it.next());
//    		if(w.isInstanceOf(BpmnModelClass.INTERFACE)){
//    			EList<EObject> listAttribute = w.getListAttribute(BpmnMetaModelConstants.E_ATTR_OPERATIONS);
//    			for (EObject eObject : listAttribute) {
//    				EObjectWrapper<EClass, EObject> operation = EObjectWrapper.wrap(eObject);
//    				EObject inMsg = operation.getAttribute(BpmnMetaModelConstants.E_ATTR_IN_MESSAGE_REF);
//    				if(inMsg != null){
//    					 EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(inMsg);
//    					 EObject attribute = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF);
//    					 if(attribute != null){
//    						 EObjectWrapper<EClass, EObject> itemref = EObjectWrapper.wrap(attribute);
//    						 String id = itemref.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
//    						 EObjectWrapper<EClass, EObject> eObjectWrapper = allItemDefinitions.get(id);
//    						 if( eObjectWrapper != null)
//    							 wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF, eObjectWrapper);
//    					 }
//    				}
//    				EObject outMsg = operation.getAttribute(BpmnMetaModelConstants.E_ATTR_OUT_MESSAGE_REF);
//    				if(outMsg != null){
//   					 EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(inMsg);
//   					 EObject attribute = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF);
//   					 if(attribute != null){
//   						 EObjectWrapper<EClass, EObject> itemref = EObjectWrapper.wrap(attribute);
//   						 String id = itemref.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
//   						 EObjectWrapper<EClass, EObject> eObjectWrapper = allItemDefinitions.get(id);
//   						 if( eObjectWrapper != null)
//   							 wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF, eObjectWrapper);
//   					 }
//   				}
//				}
//    		}
//    	}
//    }
    
//    public static void loadWsdlPortTypes(WsWsdl wsdl, String projName, String wsdlName){
//    	Map<String, EObjectWrapper<EClass, EObject>> allItemDefinitions = BpmnCommonIndexUtils.getAllItemDefinitionsMap(projName);
//    	EObject index = BpmnCommonIndexUtils.getIndex(projName);
//    	if(index == null)
//    		return;
//    	EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
//    	List<EObject> interfaces = BpmnCommonIndexUtils.getAllInterfaces(projName);
//		for (Iterator<EObject> it = interfaces.iterator(); it.hasNext();) {
//			ROEObjectWrapper<EClass, EObject> w = ROEObjectWrapper.wrap(it
//					.next());
//			if (w.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).equals(
//					wsdlName))
//				it.remove();
//		}
//    	EObjectWrapper<EClass, EObject> interfaceWrapper = null;
//    	Iterator<?> portTypes = wsdl.getPortTypes();
//		while (portTypes.hasNext()) {
//			if (interfaceWrapper == null){
//				interfaceWrapper = EObjectWrapper
//						.createInstance(BpmnModelClass.INTERFACE);
//				interfaceWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, wsdlName);
//				interfaceWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, wsdlName);
//			}
//			
//			WsPortType portType = (WsPortType) portTypes.next();
//			createOperations(interfaceWrapper, portType, allItemDefinitions);
//		}
//		
//		if(interfaceWrapper != null){
//			BpmnCommonIndexUtils.addRootElement(indexWrapper, interfaceWrapper.getEInstance());
//		}
//    }
//    
//    public static void removeWsdlPortTypes(String projName, String wsdlName){
//    	EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(BpmnCommonIndexUtils.getIndex(projName));
//    	List<EObject> rootElements = BpmnCommonIndexUtils.getRootElements(indexWrapper);
//    	for(Iterator<EObject> it = rootElements.iterator();it.hasNext();) {
//    		ROEObjectWrapper<EClass, EObject> w = ROEObjectWrapper.wrap(it.next());
//    		if(w.isInstanceOf(BpmnModelClass.INTERFACE)){
//    			if(w.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME).equals(wsdlName))
//    				BpmnCommonIndexUtils.removeRootElement(indexWrapper, w.getEInstance());
//    		}
//    	}
//    }
//    
//    private static void createOperations(EObjectWrapper<EClass, EObject> interfaceWrapper ,WsPortType portType, Map<String, EObjectWrapper<EClass, EObject>> allItemDefinitions  ){
//    	Iterator<?> operations = portType.getOperations();
//    	while (operations.hasNext()) {
//			WsOperation opn = (WsOperation) operations.next();
//			String opName = opn.getName().getLocalName();
//			EObjectWrapper<EClass, EObject> operation = EObjectWrapper.createInstance(BpmnModelClass.OPERATION);
//			operation.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, opName);
//			operation.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, opName);
//			
//			EObjectWrapper<EClass, EObject> createMessage = createMessage(opn, WsMessageKind.INPUT, allItemDefinitions);
//			if(createMessage != null)
//				operation.setAttribute(BpmnMetaModelConstants.E_ATTR_IN_MESSAGE_REF, createMessage);
//			
//			createMessage = createMessage(opn, WsMessageKind.OUTPUT, allItemDefinitions);
//			if(createMessage != null)
//				operation.setAttribute(BpmnMetaModelConstants.E_ATTR_OUT_MESSAGE_REF, createMessage);
//			interfaceWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_OPERATIONS, operation.getEInstance());
//		}
//    }
//    
//	private static EObjectWrapper<EClass, EObject> createMessage(
//			WsOperation opn, WsMessageKind kind,
//			Map<String, EObjectWrapper<EClass, EObject>> allItemDefinitions) {
//
//		WsOperationMessage[] opMsgs = opn.getMessages(kind);
//		if (opMsgs.length > 0) {
//			WsOperationMessage wsOperationMessage = opMsgs[0];
//			Iterator<?> iter = wsOperationMessage.getMessage()
//					.getMessageParts();
//			if (iter.hasNext()) {
//				WsMessagePart wsMsgPart = (WsMessagePart) iter.next();
//				String name = null;
//				String namespace = null;
//				if(wsMsgPart.getElement() != null){
//					name = wsMsgPart.getElement().getName();
//					namespace = wsMsgPart.getElement().getNamespace();
//				}
//				
//				EObjectWrapper<EClass, EObject> itemDef = null;
//				if(name != null && namespace != null){
//					String string = ExpandedName.makeName(namespace, name).toString();
//					itemDef = allItemDefinitions
//							.get(string);
//				}
//				
//				if (itemDef != null) {
//					EObjectWrapper<EClass, EObject> message = EObjectWrapper
//							.createInstance(BpmnModelClass.MESSAGE);
//					message.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,
//							wsOperationMessage.getName().getLocalName());
//					message.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME,
//							wsOperationMessage.getName().getLocalName());
//
//					message.setAttribute(
//							BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF,
//							itemDef.getEInstance());
//
//					return message;
//				}
//			}
//		}
//
//		return null;
//	}

//	public static String generateSchema(SmSchema schema, String xsdNamespace, String xsdName)
//			throws Exception {
//			
//				StringWriter stringWriter = new StringWriter();
//				XSDWriter xw = new XSDWriter();
//				// System.out.println("NS gen = " + xsdNamespace + " xsd = " + xsdName);
//				ImportSchemaLocator importTns = new ImportSchemaLocator(xsdNamespace);
//			
//				xw.setProperty(XSDWriter.IMPORT, importTns);
//			
//				List<String> imports = new LinkedList<String>();
//				imports.add(BE_BASE_CONCEPT_NS);
//			
//				xw.write(schema, stringWriter, imports.listIterator());
//				String xsd = stringWriter.toString();
//			
//				xsd = normalizeXsd(xsd);
//				return xsd;
//			}
//
//	private static String normalizeXsd(String xsd) throws Exception {
//		XiNode rootNode;
//		XiParser parser;
//	
//		parser = XiParserFactory.newInstance();
//	
//		rootNode = parser.parse(new InputSource(new ByteArrayInputStream(xsd.getBytes())));
//		XiNode schemaNode = XiChild.getChild(rootNode, ExpandedName
//				.makeName(XSDL.NAMESPACE, "schema"));
//		
//		int countComplexTypeNodes = XiChild.getChildCount(schemaNode,
//				ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
//		if(countComplexTypeNodes > 1){
//			Iterator<?> ComplexTypeIter = XiChild.getIterator(schemaNode,
//					ExpandedName.makeName(XSDL.NAMESPACE, "complexType"));
//			while (true) {
//				XiNode complexTypeNode = (XiNode) ComplexTypeIter.next();
//				if (!ComplexTypeIter.hasNext()) {
//					schemaNode.removeChild(complexTypeNode);
//					break;
//				}
//			}
//		}
//		
//		StringWriter w = new StringWriter();
//		XiSerializer.serialize(rootNode, w);
//		w.flush();
//		xsd = w.toString();
//		return xsd;
//	}

	public static Map<String, EObject> getRootElementMap(
			EObjectWrapper<EClass, EObject> indexWrapper) {
		Map<String, EObject> map = new HashMap<String, EObject>();
		if (indexWrapper != null && indexWrapper.isSet()) {
			List<EObject> rElements = BpmnCommonIndexUtils.getRootElements(indexWrapper);
			for (int i = 0; i < rElements.size(); i++) {
				EObjectWrapper<EClass, EObject> rElementWrapper = EObjectWrapper
						.wrap(rElements.get(i));
				String rid = rElementWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				map.put(rid, rElements.get(i));
			}
		}
		return map;
	}

	/**
	 * Makes a derived simple type and adds it to the given schema.
	 */
	protected static MutableType makeListType(final ExpandedName name, SmType member) {
		MutableType type = makeType(name);
		type.addMemberType(member);
		type.setValueType(SmType.VALUE_TYPE_LIST);
		type.setAtomicType(null);
		return type;
	}

	/**
	 * Makes a simple type and adds it to the given schema.
	 */
	private static MutableType makeType(final ExpandedName expandedName) {
		MutableType type = S_SCHEMA.getComponentFactory().createType();
		type.setSchema(S_SCHEMA);
		type.setExpandedName(expandedName);
		S_FLAVOR.addNativeType(type);
		return type;
	}

	private static ExpandedName mkName(final String ns, final String localName) {
		return ExpandedName.makeName(ns, localName);
	}
    
    
	
	

}
