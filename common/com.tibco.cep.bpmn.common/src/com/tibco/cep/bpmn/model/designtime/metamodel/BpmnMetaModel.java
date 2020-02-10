package com.tibco.cep.bpmn.model.designtime.metamodel;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.DualHashBidiMap;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionDefintionFactory;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelURIHelper.URIType;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.xml.data.primitive.ExpandedName;


public class BpmnMetaModel implements BpmnMetaModelConstants{

	private ResourceSet resourceSet = null;
	
	private EPackage metaModelPackage;

	private EPackage metaModelIndexPackage;
	
	private EPackage metaModelExtnPackage;
	
	private static BidiMap<ExpandedName,ENamedElement> metaModelNsRegistry = new DualHashBidiMap<ExpandedName,ENamedElement>();
	
	private static Map<ExpandedName,EObject> extensionDefinitionRegistry = Collections.synchronizedMap(new HashMap<ExpandedName,EObject>());
	
	Resource indexRes = null;
	Resource modelRes = null;
	Resource extnRes = null;
	
	static {
		getInstance();
	}
	
	public static BpmnMetaModel  INSTANCE = getInstance();
	
	public static BpmnMetaModel getInstance() {
		if(INSTANCE == null) {
			try {
				INSTANCE = new BpmnMetaModel();
			} catch (Exception e) {
				e.printStackTrace();
				INSTANCE = null;
			}
		}
		return INSTANCE;
	}
	
	public static BpmnMetaModel loadPackages(ResourceSet rset) throws Exception {
		return new BpmnMetaModel(rset);
		
	}
	
	protected BpmnMetaModel() throws Exception {
		this(new ResourceSetImpl());
	}
	
	protected BpmnMetaModel(ResourceSet rset) throws Exception  {
//		ResourceSet rset = new ResourceSetImpl();
		
		if(PlatformUtil.INSTANCE.isStudioPlatform()) {
			try {
				Map<URIType, URI> uriMap = BpmnMetaModelURIHelper.getUriForEclipse();
				loadUri(rset, uriMap);
				indexRes = rset.createResource(uriMap.get(URIType.INDEX));
				modelRes = rset.createResource(uriMap.get(URIType.MODEL));
				extnRes = rset.createResource(uriMap.get(URIType.EXTN));
			} catch (MalformedURLException e) {
				// this code will get execute only if there is any bug . i,e For web application isWebStudioPlatform() returns false  and isStudioPlatform returns true
				// or isStudioPlatform returns true for runtime env
				// this simple safety code, later should be remove the bug is fixed. 
				Map<URIType, URI> uriMap = BpmnMetaModelURIHelper.getUriForRuntime();
				rset = new ResourceSetImpl();
				indexRes = rset.createResource(uriMap.get(URIType.INDEX));
				modelRes = rset.createResource(uriMap.get(URIType.MODEL));
				extnRes = rset.createResource(uriMap.get(URIType.EXTN));
				loadUri(rset, uriMap);
			}

		}else if(PlatformUtil.INSTANCE.isWebStudioPlatform()) {
			Map<URIType, URI> uriMap = BpmnMetaModelURIHelper.getUriForWebStudio();
			loadUri(rset, uriMap);
		} else {
			Map<URIType, URI> uriMap = BpmnMetaModelURIHelper.getUriForRuntime();
			indexRes = rset.createResource(uriMap.get(URIType.INDEX));
			modelRes = rset.createResource(uriMap.get(URIType.MODEL));
			extnRes = rset.createResource(uriMap.get(URIType.EXTN));
			
			loadUri(rset, uriMap);
		}

		rset.getPackageRegistry().put(metaModelIndexPackage.getNsURI(),metaModelIndexPackage);
		rset.getPackageRegistry().put(metaModelPackage.getNsURI(),metaModelPackage);
		rset.getPackageRegistry().put(metaModelExtnPackage.getNsURI(),metaModelExtnPackage);
		

//		xmiOut = CommonECoreHelper.deserializeXMIResource(indexRes,BpmnMetaModelURIHelper.INDEX_URI);
//		metaModelIndexPackage = (EPackage) xmiOut.get(0);
//		xmiOut = CommonECoreHelper.deserializeXMIResource(modelRes,BpmnMetaModelURIHelper.MODEL_URI);
//		metaModelPackage = (EPackage) xmiOut.get(0);
//		xmiOut = CommonECoreHelper.deserializeXMIResource(extnRes,BpmnMetaModelURIHelper.EXTN_URI);
//		metaModelExtnPackage = (EPackage) xmiOut.get(0);

		initializePackageRegistry(metaModelIndexPackage);
		initializePackageRegistry(metaModelPackage);
		initializePackageRegistry(metaModelExtnPackage);
		
		((EPackageImpl)metaModelIndexPackage).freeze();
		((EPackageImpl)metaModelPackage).freeze();
		((EPackageImpl)metaModelExtnPackage).freeze();
		
		if(rset != null){
			synchronized (rset) {
				List<Resource> resources = new ArrayList<Resource>(rset.getResources());
				for (Resource resource : resources) {
					if (resource != null)
						EcoreUtil.resolveAll(resource);
				}
			}
		}
		
		setResourceSet(rset);
		
		if(Boolean.valueOf(System.getProperty("com.tibco.cep.bpmn.gen.interface","false"))) {
			generateModelClassInterfaces(metaModelIndexPackage);
			generateModelClassInterfaces(metaModelPackage);
			generateModelClassInterfaces(metaModelExtnPackage);
		}
//		if(Boolean.valueOf(System.getProperty("com.tibco.cep.bpmn.gen.constants","false"))) {
//			generateModelConstantInterfaces(metaModelIndexPackage);
//			generateModelConstantInterfaces(metaModelPackage);
//			generateModelConstantInterfaces(metaModelExtnPackage);
//		}
	}
	
	private void loadUri(ResourceSet rset, Map<URIType, URI> uriMap) throws Exception{
		EList<EObject> xmiOut = CommonECoreHelper.deserializeXMI(rset,uriMap.get(URIType.INDEX),false);
		metaModelIndexPackage = (EPackage) xmiOut.get(0);
		xmiOut = CommonECoreHelper.deserializeXMI(rset,uriMap.get(URIType.MODEL),false);
		metaModelPackage = (EPackage) xmiOut.get(0);
		xmiOut = CommonECoreHelper.deserializeXMI(rset,uriMap.get(URIType.EXTN),false);
		metaModelExtnPackage = (EPackage) xmiOut.get(0);
		rset.getURIConverter().getURIMap().put(URI.createURI(metaModelIndexPackage.getNsURI()), uriMap.get(URIType.INDEX));
		rset.getURIConverter().getURIMap().put(URI.createURI(metaModelPackage.getNsURI()), uriMap.get(URIType.MODEL));
		rset.getURIConverter().getURIMap().put(URI.createURI(metaModelExtnPackage.getNsURI()), uriMap.get(URIType.EXTN));
	}

	public static ResourceSet getModelResourceSet() throws Exception {
		BpmnMetaModel mm = new BpmnMetaModel();
		return mm.getResourceSet();
	}
	
	/**
	 * initialize model extensions
	 */
	private void initializeModelExtensions() {
		for(EClass eClass: getAllClasses()) {	
			if(ExtensionDefintionFactory.hasDataExtensionDefintion(eClass)) {
				EObject extDef = ExtensionDefintionFactory.getDataExtensionDefintion(eClass);
				if(extDef != null) {
					ExpandedName name = CommonECoreHelper.getExpandedName(eClass);
					extensionDefinitionRegistry.put(name, extDef);
				}
			}
		}
		
	}




	/**
	 * @param ePackage
	 */
	private void initializePackageRegistry(EPackage ePackage) {
		final EPackage.Registry i = EPackage.Registry.INSTANCE;
		i.put(ePackage.getNsURI(),ePackage);
		Map<String, Object> packageRegistry = Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap();
		packageRegistry.put(ePackage.getNsURI(),ePackage);
//		packageMap.put(CommonECoreHelper.getQualifiedPackageName(ePackage), ePackage);
		metaModelNsRegistry.put(CommonECoreHelper.getExpandedName(ePackage), ePackage);
		
		for(Iterator<EClassifier> iter = ePackage.getEClassifiers().iterator();iter.hasNext();) {
			EClassifier clazz = iter.next();
			if(clazz instanceof EClass) {
//				classMap.put(CommonECoreHelper.getQualifiedPackageName(ePackage)+"."+clazz.getName(), (EClass) clazz);				
				metaModelNsRegistry.put(CommonECoreHelper.getExpandedName(clazz), clazz);
			}
			
			if(clazz instanceof EEnum) {
//				enumMap.put(CommonECoreHelper.getQualifiedPackageName(ePackage)+"."+clazz.getName(), (EEnum) clazz);
				metaModelNsRegistry.put(CommonECoreHelper.getExpandedName(clazz), clazz);
			}
		}
		
		for(EPackage esp:ePackage.getESubpackages()) {
			initializePackageRegistry(esp);		
		}
		
	}



	/**
	 * @param ePackage
	 */
	private void generateModelClassInterfaces(EPackage ePackage) {		
		for(Iterator<EClassifier> iter = ePackage.getEClassifiers().iterator();iter.hasNext();) {
			EClassifier clazz = iter.next();
			CommonECoreHelper.generateMetaModelClassInterface(new PrintWriter(System.out), ePackage, clazz);			
		}
		
		for(EPackage esp:ePackage.getESubpackages()) {
			generateModelClassInterfaces(esp);		
		}
		
	}

//	/**
//	 * @param ePackage
//	 */
//	private void generateModelConstantInterfaces(EPackage ePackage) {		
//		for(Iterator<EClassifier> iter = ePackage.getEClassifiers().iterator();iter.hasNext();) {
//			EClassifier clazz = iter.next();
//			CommonECoreHelper.generateMetaModelConstantsInterface(new PrintWriter(System.out), ePackage, clazz);			
//		}
//		
//		for(EPackage esp:ePackage.getESubpackages()) {
//			generateModelConstantInterfaces(esp);		
//		}
//		
//	}
	
	
	
	
	
	
	
	public ResourceSet getResourceSet() {
		return resourceSet;
	}




	public void setResourceSet(ResourceSet resourceSet) {
		this.resourceSet = resourceSet;
	}

	public static BidiMap<ExpandedName, ENamedElement> getMetaModelNsRegistry() {
		return metaModelNsRegistry;
	}


	/**
	 * @return collection of all metamodel packages
	 */
	public static Collection<EPackage> getAllPackages() {
		return EcoreUtil.getObjectsByType(metaModelNsRegistry.values(), EcorePackage.Literals.EPACKAGE);
	}


	/**
	 * @return collection of all metamodel classes
	 */
	public static Collection<EClass> getAllClasses() {
		return EcoreUtil.getObjectsByType(metaModelNsRegistry.values(), EcorePackage.Literals.ECLASS);
	}
	

	/**
	 * @return collection of all metamodel enums
	 */
	public static Collection<EEnum> getAllEnums() {
		return EcoreUtil.getObjectsByType(metaModelNsRegistry.values(), EcorePackage.Literals.EENUM);
	}

	/**
	 * @return the metamodel package
	 */
	public EPackage getMetaModelPackage() {
		return metaModelPackage;
	}
	
	/**
	 * @return the metamodel index package
	 */
	public EPackage getMetaModelIndexPackage() {
		return metaModelIndexPackage;
	}
	
	
	/**
	 * @param eClass
	 * @return
	 */
	public EObject getExtensionDefinition(EClass eClass) {
		synchronized (extensionDefinitionRegistry) {
			if(extensionDefinitionRegistry.isEmpty()) {
				initializeModelExtensions();
			}
			return extensionDefinitionRegistry.get(CommonECoreHelper.getExpandedName(eClass));
		}
	}
	
	/**
	 * @param eClass
	 * @return
	 */
	public boolean hasExtensionDefinition(EClass eClass) {
		synchronized (extensionDefinitionRegistry) {
			if(extensionDefinitionRegistry.isEmpty()) {
				initializeModelExtensions();
			}
			return extensionDefinitionRegistry.containsKey(CommonECoreHelper.getExpandedName(eClass));
		}
	}
	
	/**
	 * @return
	 */
	public Collection<EObject> getExtensionDefinitions() {
		synchronized (extensionDefinitionRegistry) {
			if(extensionDefinitionRegistry.isEmpty()) {
				initializeModelExtensions();
			}
			return Collections.synchronizedCollection(extensionDefinitionRegistry.values());
		}
	}
	
//	/**
//	 * @deprecated
//	 * @param packageSpec
//	 * @return
//	 */
//	public  EPackage getEPackage(String packageSpec) {
//		return packageMap.get(packageSpec);
//	}
	
//	/**
//	 * @deprecated
//	 * @param ePackage
//	 * @return
//	 */
//	public String getPackageSpec(EPackage ePackage) {
//		if(ePackage == null)  return null;
//		return packageMap.inverseMap().get(ePackage);
//	}
	
	
	/**
	 * @param namedElement
	 * @return
	 */
	public ExpandedName getExpandedName(ENamedElement namedElement) {
		return metaModelNsRegistry.inverseMap().get(namedElement);
	}
	
	
	/**
	 * @param exName
	 * @return a package for the given expanded name
	 */
	public EPackage getEPackage(ExpandedName exName) {
		return (EPackage) getNamedElement(exName);
	}
	
	/**
	 * @param exName
	 * @return a EClass for the given expanded name
	 */
	public EClass getEClass(ExpandedName exName) {
		return (EClass) getNamedElement(exName);
	}
	
	/**
	 * @param exName
	 * @return a EEnum for the given expanded name
	 */
	public EEnum getEEnum(ExpandedName exName){
		return (EEnum) getNamedElement(exName);
	}
	
	/**
	 * @param exName
	 * @return
	 */
	private ENamedElement getNamedElement(ExpandedName exName) {
		return metaModelNsRegistry.get(exName);
	}
	
//	/**
//	 * @deprecated
//	 * @param classSpec
//	 * @return
//	 */
//	public  EClass getEClass(String classSpec) {
//		if(classSpec == null)  return null;
//		
//		if(classMap.containsKey(classSpec)){
//			return classMap.get(classSpec);
//		}
//		EPackage ePackage = getEPackage(CommonECoreHelper.getQualifiedPackageName(classSpec));
//		if(ePackage != null) {
//			EClass eClazz = (EClass) ePackage.getEClassifier(CommonECoreHelper.getClassNameFromQualifiedName(classSpec));
//			classMap.put(eClazz.getName(), eClazz);
//			return eClazz;
//		}
//		return null;
//	}
	
//	/**
//	 * @deprecated
//	 * @param eClass
//	 * @return
//	 */
//	public String getClassSpec(EClass eClass) {
//		if(eClass == null)  return null;
//		return classMap.inverseMap().get(eClass);
//	}
	
	/**
	 * @param eClass
	 * @param featureName
	 * @return
	 */
	public static EStructuralFeature getStructuralFeature(EClass eClass,String featureName){
		return eClass.getEStructuralFeature(featureName);
	}
	
	/**
	 * @param eClass
	 * @param featureID
	 * @return
	 */
	public static EStructuralFeature getStructuralFeature(EClass eClass,int featureID){
		return eClass.getEStructuralFeature(featureID);
	}
	
	/**
	 * @param eClass
	 * @param featureName
	 * @return
	 */
	public static int getFeatureID(EClass eClass, String featureName) {
		return eClass.getFeatureID(getStructuralFeature(eClass, featureName));		
	}
	
	
	public EEnum getEnum(ExpandedName exName) {
		if(metaModelNsRegistry.containsKey(exName)) {
			return (EEnum) metaModelNsRegistry.get(exName);
		}
		EPackage ePackage = getEPackage(exName);
		if(ePackage != null) {
			EEnum eEnum = (EEnum) ePackage.getEClassifier(exName.getLocalName());
			metaModelNsRegistry.put(exName, eEnum);
			return eEnum;
		}
		return null;
	}
	
//	/**
//	 * @deprecated
//	 * @param enumSpec
//	 * @return
//	 */
//	public EEnum getEnum(String enumSpec) {
//		if(enumMap.containsKey(enumSpec)) {
//			return enumMap.get(enumSpec);
//		}
//		
//		EPackage ePackage = getEPackage(CommonECoreHelper.getQualifiedPackageName(enumSpec));
//		if(ePackage != null) {
//			EEnum eEnum = (EEnum) ePackage.getEClassifier(CommonECoreHelper.getClassNameFromQualifiedName(enumSpec));
//			enumMap.put(eEnum.getName(), eEnum);
//			return eEnum;
//		}
//		return null;
//		
//	}
	
//	/**
//	 * @deprecated
//	 * @param eEnum
//	 * @return
//	 */
//	public String getEnumSpec(EEnum eEnum) {
//		if(eEnum == null)  return null;
//		return enumMap.inverseMap().get(eEnum);
//	}
	
	private String getEcoreFileName(Resource r) {
		Path p = new Path(r.getURI().path());
		return p.lastSegment();
	}
	
	public Map<String, URI> getModelURIMap() {
		Map<String,URI> uriMap = new HashMap<String,URI>();
		uriMap.put(getEcoreFileName(this.indexRes), this.indexRes.getURI());
		uriMap.put(getEcoreFileName(this.modelRes), this.modelRes.getURI());
		uriMap.put(getEcoreFileName(this.extnRes), this.extnRes.getURI());
		return uriMap;
	}

	public static class ModelUriHandler extends URIHandlerImpl implements URIHandler {

//		private URI baseURI;
		Map<String,URI> uriMap;

		public ModelUriHandler(Map<String, URI> uriMap) {
			this.uriMap = uriMap;
		}

		@Override
		public URI deresolve(URI uri) {			
			return super.deresolve(uri);
		}

		@Override
		public URI resolve(URI uri) {
			if(uri.path() != null) {
				Path p = new Path(uri.path());
				if(uriMap.containsKey(p.lastSegment())) {
					URI u = uriMap.get(p.lastSegment());
					u = u.appendFragment(uri.fragment());
					return u;
				}
			}
			return super.resolve(uri);
		}

		@Override
		public void setBaseURI(URI arg0) {
			// TODO Auto-generated method stub
			this.baseURI = arg0;
		}
		
	}

	

}
