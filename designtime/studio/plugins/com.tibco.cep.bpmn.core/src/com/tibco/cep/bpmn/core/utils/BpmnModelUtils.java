package com.tibco.cep.bpmn.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.be.model.functions.FunctionsCatalogVisitor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.BpmnNameGenerator;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolEntryImpl;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.core.functions.model.EMFModelFunctionsFactory;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;

public class BpmnModelUtils extends BpmnCommonModelUtils {

	/**
	 * @param ctx TODO
	 * @param processName
	 * @return the generated Process name
	 */
	public static String generatedProcessName(CodeGenContext ctx, String processName) {
		String name = CommonECoreHelper.toTitleCase(processName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getProcessPrefix() + BPMN_PREFIX_SEPARATOR + name;
	}
	
	/**
	 * @param ctx TODO
	 * @param ruleName
	 * @return the generated Rule name
	 */
	public static String generatedRuleName(CodeGenContext ctx, String ruleName) {
		String name = CommonECoreHelper.toTitleCase(ruleName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getRulePrefix() + BPMN_PREFIX_SEPARATOR + name;
	}
	
	/**
	 * @param ctx TODO
	 * @param rulefunctionName
	 * @return the generated Rulefunction name
	 */
	public static String generatedRulefunctionName(CodeGenContext ctx, String rulefunctionName) {
		String name = CommonECoreHelper.toTitleCase(rulefunctionName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getRuleFunctionPrefix() + BPMN_PREFIX_SEPARATOR + name;
	}

	/**
	 * @param flowElement
	 * @return
	 */
	public static List<SymbolEntryImpl> getMethodArguments(EObject flowElement) {
		List<SymbolEntryImpl> list =  new ArrayList<SymbolEntryImpl>(); 
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_METHOD_ARGUMENTS;
		EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowElement);
		if(valueWrapper != null && valueWrapper.containsAttribute(attrName)) {
			EList<EObject> attributes = valueWrapper.getListAttribute(attrName);
			for (EObject object: attributes) {
				if(object != null) {
					SymbolEntryImpl symbolEntry = new SymbolEntryImpl(object);
					list.add(symbolEntry);
				}
			}
		}
		return list;
	}

	/**
	 * @param flowElement
	 * @return
	 */
	public static SymbolEntryImpl getMethodReturnType(EObject flowElement) {
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(flowElement);
		String attrName = BpmnMetaModelExtensionConstants.E_ATTR_METHOD_RETURN_TYPE;

		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				EObject object = valueWrapper.getAttribute(attrName);
				if(object != null) {
					SymbolEntryImpl simpl = new SymbolEntryImpl(object);
					return simpl;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * @param ctx TODO
	 * @param conceptName
	 * @return the generated Concept name
	 */
	public static String generatedConceptName(CodeGenContext ctx, String conceptName) {
		String name = CommonECoreHelper.toTitleCase(conceptName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getConceptPrefix() + BPMN_PREFIX_SEPARATOR + name;
	}
	
	/**
	 * @param ctx TODO
	 * @param eventName
	 * @return the generated Event name
	 */
	public static String generatedEventName(CodeGenContext ctx, String eventName) {
		String name = CommonECoreHelper.toTitleCase(eventName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getEventPrefix() + BPMN_PREFIX_SEPARATOR + name;
	}
	
	/**
	 * @param ctx TODO
	 * @param timeEventName
	 * @return the generated Timeevent name
	 */
	public static String generatedTimeEventName(CodeGenContext ctx, String timeEventName) {
		String name = CommonECoreHelper.toTitleCase(timeEventName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getTimeEventPrefix() + BPMN_PREFIX_SEPARATOR + name;
	}
	
	/**
	 * @param ctx TODO
	 * @param scorecardName
	 * @return the generated scorecard name
	 */
	public static String generatedScorecardName(CodeGenContext ctx, String scorecardName) {
		String name = CommonECoreHelper.toTitleCase(scorecardName).replace(' ', '_');
		BpmnProcessSettings  config =  (BpmnProcessSettings) ctx.get(BpmnCoreConstants.BPMN_CONFIG);
		return config.getScorecardPrefix() + BPMN_PREFIX_SEPARATOR + name;
	}
	
	public static String generatedFlowElementName(EObject flowElement) {
		EObjectWrapper<EClass, EObject> ewrapper = EObjectWrapper.wrap(flowElement);
		String ename = ewrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		if(ename == null) {
			ename = ewrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(ename.indexOf(".") != -1) {
				ename.replace(".", "_");
			}
		}
		String name = CommonECoreHelper.toTitleCase(ename).replace(' ', '_');
		return name;
	}


	/**
	 * @param project
	 * @param entity
	 * @return
	 */
	public static URI getEntityFileURI(IProject project, Entity entity) {
		IFile file = IndexUtils.getFile(project,entity);
		return URI.createPlatformResourceURI(file.getFullPath().toPortableString(), false);
	}
	
	
    public static ModelFunction findModelFunction(Entity e) throws Exception {
    	String projectName = e.getOwnerProjectName();
    	FunctionsCategory fcat = FunctionsCatalogManager.getInstance().getOntologyCategory(projectName);
    	ModelfunctionLookupVisitorImpl visitor = new ModelfunctionLookupVisitorImpl(e);
		fcat.accept(visitor);
		ModelFunction mf =  visitor.getModelFunction();
		if(mf == null) {
			// the resource change event for this entity has not arrived yet
			EMFModelFunctionsFactory mff = FunctionsCatalogManager.getInstance().getModelFunctionsFactory(projectName);
			mff.processEntity(e);
			fcat.accept(visitor);
			mf =  visitor.getModelFunction();
		}
		return mf;
    }
    
    
    public static class ModelfunctionLookupVisitorImpl implements FunctionsCatalogVisitor {
		private ModelFunction mf = null;
		private Entity entity;
		
		public ModelfunctionLookupVisitorImpl(Entity e) {
			this.entity = e;
		}
		public ModelFunction getModelFunction() { 
			return mf; 
		}
		@Override
		public boolean visit(FunctionsCategory category) {
			if(mf != null){
				return false;
			}
			Iterator<Predicate> iter = category.allFunctions();
			while(iter.hasNext()){
				Predicate p = iter.next();
				if(p instanceof ModelFunction) {
					
					Object modelObj = ((ModelFunction)p).getModel();
					if(modelObj.equals(entity)) {
						mf = (ModelFunction) p;
						return false;
					}
				}
			}
			return true;
		}
	}
    
    public static Predicate findFunction(String projectName,String functionName) throws Exception {
    	FunctionsCategory fcat = FunctionsCatalogManager.getInstance().getOntologyCategory(projectName);
    	Object object = fcat.lookup(functionName, false);
    	if(object instanceof Predicate) {
    		return (Predicate) object;
    	}
    	return null;
    }
    
    

	/**
	 * Builds the scoped symbol hierarchy tree
	 * @param projectName
	 * @return
	 */
//	public static RootSymbolMap initRootSymbolMap(String projectName) {
//		BpmnIndex ontology =  BpmnCorePlugin.getDefault()
//		.getBpmnModelManager().getBpmnOntology(projectName);
//		RootSymbolMap rootSymbolMap = BpmnCorePlugin.getDefault()
//									.getBpmnModelManager()
//									.getRootSymbolMap(projectName);
//		Collection<EObject> processes = ontology.getAllProcesses();
//		for(EObject proc: processes) {
//			ProcessSymbolMap processSymbolMap = rootSymbolMap.getProcessSymbolMap(proc);	
//			Collection<EObject> flowElements = ontology.getFlowElements(proc);
//			for(EObject flowElement:flowElements) {
//				processSymbolMap.initElementSymbolMap(flowElement);
//			}
//		}
//		return rootSymbolMap;
//	}
	
	/**
	 * Builds the scoped symbol hierarchy tree
	 * @param projectName
	 * @return
	 */
//	public static RootSymbolMap refreshRootSymbolMap(String projectName) {
//		BpmnIndex ontology =  BpmnCorePlugin.getDefault()
//		.getBpmnModelManager().getBpmnOntology(projectName);
//		RootSymbolMap rootSymbolMap = BpmnCorePlugin.getDefault()
//									.getBpmnModelManager()
//									.getRootSymbolMap(projectName);
//		Collection<EObject> processes = ontology.getAllProcesses();
//		for(EObject proc: processes) {
//			ProcessSymbolMap processSymbolMap = rootSymbolMap.getProcessSymbolMap(proc);
//			Collection<EObject> flowNodes = ontology.getFlowNodes(proc);
//			for(EObject node:flowNodes) {
//				processSymbolMap.initElementSymbolMap(node);
//			}
//			Collection<EObject> seqFlows = ontology.getSequenceFlows(proc);
//			for(EObject seqFlow:seqFlows) {
//				SymbolMap seqSymbolMap = processSymbolMap.initElementSymbolMap(seqFlow);
//				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap(seqFlow);
//				EObject source = seqFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
//				EObject target = seqFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
//				FlowElementSymbolMap sourceSymbolMap = processSymbolMap.getSymbolMapRegistry().get(source);
//				FlowElementSymbolMap targetSymbolMap = processSymbolMap.getSymbolMapRegistry().get(target);
//				sourceSymbolMap.getOutgoingMaps().add(seqSymbolMap);
//				targetSymbolMap.getIncomingMaps().add(seqSymbolMap);
//				seqSymbolMap.getIncomingMaps().add(sourceSymbolMap);
//				seqSymbolMap.getOutgoingMaps().add(targetSymbolMap);
//			}
//			Collection<EObject> startNodes = ontology.getFlowNodes(proc, false, true, BpmnModelClass.FLOW_NODE);
//			for(EObject startNode: startNodes) {
//				FlowElementSymbolMap nodeSymbolMap = processSymbolMap.getSymbolMapRegistry().get(startNode);
//				processSymbolMap.getOutgoingMaps().add(nodeSymbolMap);
//			}
//			
//			Collection<EObject> endNodes = ontology.getFlowNodes(proc, true, false, BpmnModelClass.FLOW_NODE);
//			for(EObject endNode: endNodes) {
//				FlowElementSymbolMap nodeSymbolMap = processSymbolMap.getSymbolMapRegistry().get(endNode);
//				processSymbolMap.getIncomingMaps().add(nodeSymbolMap);
//			}			
//			
//		}
//		return rootSymbolMap;
//	}
	

	/**
	 * convert to BPMN symbol
	 * @param s
	 * @return
	 */
	public static EObject convertFromBESymbol(Symbol s) {
		return createBpmnSymbol(s.getIdName(), s.getType());
	}

	/**
	 * @param element
	 * @return
	 */
	public static String getFlowElementId(EObject element) {
		if(!BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(element.eClass())) {
			throw new IllegalArgumentException("Invalid flow element object.");
		}
		EObject container = element.eContainer();
		Identifier cname = getFlowElementContainerId(container);
		return cname.getName()+BPMN_DOT_SEPARATOR+generatedFlowElementName(element).toUpperCase();
	}
	
	/**
	 * @param flowElement
	 * @return
	 */
	public static Identifier getFlowElementContainerId(EObject flowElement) {
		if(!BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(flowElement.eClass())) {
			throw new IllegalArgumentException("Invalid flow element object.");
		}
		String s = null;
		EObjectWrapper<EClass, EObject> feWrapper = EObjectWrapper.wrap(flowElement);
		Identifier cname = new Identifier((String)feWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		EObject parentContainer = flowElement.eContainer();
		if(parentContainer == null || 
				!BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(parentContainer.eClass())) {
			s = cname.getName();
		} else {
			s = getFlowElementContainerId(parentContainer).getId()+BPMN_DOT_SEPARATOR+cname.getName();
		}
		return new Identifier(s);
	}
	
	
	public static Identifier nextIdentifier(
			EObjectWrapper<EClass, EObject> elementContainer,
			EClass type, String name) {
		EObjectWrapper<EClass, EObject> process = getProcess(elementContainer);
		String projectName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(projectName);
		return nameGenerator.getNextIdentifier(elementContainer.getEInstance(), type, name);
	}
	
	/**
	 * @param objWrapper
	 * @param name TODO
	 * @param projectName
	 * @param process
	 * @return
	 */
	public static Identifier nextIdentifier(
			EObjectWrapper<EClass, EObject> elementContainer,
			EObjectWrapper<EClass, EObject> objWrapper, String name) {
		EObjectWrapper<EClass, EObject> process = getProcess(elementContainer);
		String projectName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		return nextIdentifier(projectName, elementContainer, objWrapper, name);
	}
	
	
	
	public static Identifier nextIdentifier(String projectName,
			EObjectWrapper<EClass, EObject> elementContainer,
			EObjectWrapper<EClass, EObject> objWrapper, String name) {
		BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(projectName);
		return nameGenerator.getNextIdentifier(elementContainer.getEInstance(), objWrapper.getEClassType(), name);
	}
	
	/**
	 * @param eObj
	 * @param name TODO
	 * @param process
	 * @return
	 */
	public static Identifier nextIdentifier(
			EObjectWrapper<EClass, EObject> elementContainer,
			EObject eObj, String name) {
		EObjectWrapper<EClass, EObject> process = getProcess(elementContainer);
		String projectName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(projectName);
		return nameGenerator.getNextIdentifier(elementContainer.getEInstance(), eObj.eClass(), name);
	}
	
	/**
	 * @param projectName
	 * @param type
	 * @param name TODO
	 * @param processName
	 * @return
	 */
	public static Identifier nextIdentifier(String projectName,String elementContainerId,EClass type, String name) {
		EObject index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(projectName);
		if(index == null)
			return null;
		BpmnIndex ontology = new DefaultBpmnIndex(index);
		EObject elementContainer = ontology.getElementById(elementContainerId);
		BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(projectName);
		return nameGenerator.getNextIdentifier(elementContainer, type, name);
	}
	
	/**
	 * @param folder
	 * @param fileName
	 * @param duplicateFile
	 * @return
	 */
	public static boolean isDuplicateProcessResource(IResource folder, String fileName, StringBuilder duplicateFile) {
		Object[] object = CommonUtil.getResources((IContainer)folder);
		for(Object obj : object){
			if(obj instanceof IFile){
				EObject element = BpmnIndexUtils.getElement((IFile)obj);
				if(element != null){
					EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
					if(element != null){
						if(((String) elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)).equalsIgnoreCase(fileName)){
							duplicateFile.append(((IFile)obj).getName());
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private static int createUniqueId(String name,EObjectWrapper<EClass, EObject> flowElementContainer){
		BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(name);
		int uid=nameGenerator.createUid(flowElementContainer.getEInstance());
		return uid;
	}
	
	public static void addUniqueId(String name,EObjectWrapper<EClass, EObject> flowElementContainer,EObjectWrapper<EClass, EObject> eventWrapper){
		if(eventWrapper.isInstanceOf(BpmnModelClass.FLOW_ELEMENT)||eventWrapper.isInstanceOf(BpmnModelClass.ARTIFACT)){
			try{
				int uid=createUniqueId(name,flowElementContainer);
				eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID,uid);
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public static boolean checkForUniqueId(EObjectWrapper<EClass, EObject> process){
		boolean flag=false;
		try{
			List<EObject> allFlowNodes = BpmnModelUtils
					.getAllFlowNodes(process.getEInstance());
			List<EObject> allSeqFLowNodes =BpmnModelUtils.getAllSequenceFlows(process.getEInstance());
			for (EObject element:allFlowNodes) {
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
				Integer uId=(Integer)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				if(uId==0 || uId==null){
					flag=true;
					addUniqueId( getProjName(element),process,elementWrapper);
				}
					
//				System.out.println(uIds.size());
			}
			for(EObject element:allSeqFLowNodes){
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
				Integer uId=(Integer)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
				if(uId==0 || uId==null){
					flag=true;
					addUniqueId( getProjName(element),process,elementWrapper);
				}
			}
			}catch(Exception e){
				System.out.println(e);
			}
		return flag;
	}
	public static String getProjName(EObject proc){
		EObjectWrapper<EClass, EObject> process = BpmnModelUtils.getProcess(proc);
		String projectName = process.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		return projectName;
	}
	
	public static EObjectWrapper<EClass, EObject> createEObjectCopy(EObject originalObject, EObjectWrapper<EClass, EObject> process, String projName, String name){
		EObject newObject = EcoreUtil.copy(originalObject);
		EObjectWrapper<EClass, EObject> newWrapper = EObjectWrapper
				.wrap(newObject);
		Identifier id = BpmnModelUtils.nextIdentifier(projName, process,newWrapper, name);
		addUniqueId(projName,process,newWrapper);
		newWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		if(newWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME))
			newWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, id.getName());
		else if (ExtensionHelper.isValidDataExtensionAttribute(
				newWrapper,
				BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
			EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
			.getAddDataExtensionValueWrapper(newWrapper);
			if (valWrapper != null)
				valWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL, id.getName());
		}
		EList<EObject> eContents = newObject.eContents();
		for (EObject eObject : eContents) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
			if (wrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_ID)) {
				 id = BpmnModelUtils.nextIdentifier(projName,
						process, wrap, null);
				wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
			}
		}
		
		return newWrapper;
	}
	
	public static String getInputMapperXslt(EObject userObject) {
		String xslt = "";
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			} else if (BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			}
		}
		return xslt;
	}
	
	public static void setInputMapperXslt(EObject userObject, String xslt) {
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					EObjectWrapper<EClass, EObject> transformWrap = EObjectWrapper
							.wrap(transform);
					transformWrap
							.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, xslt);
				}
			} else if (BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataInAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION);
				if (!dataInAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataInAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					EObjectWrapper<EClass, EObject> transformWrap = EObjectWrapper
							.wrap(transform);
					transformWrap
							.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, xslt);
				}
			}
		}
		
	}
	
	public static String getOutputMapperXslt(EObject userObject) {
		String xslt = "";
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			} else if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					ROEObjectWrapper<EClass, EObject> transformWrap = ROEObjectWrapper
							.wrap(transform);
					xslt = (String) transformWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_BODY);
				}
			}
		}
		return xslt;
	}
	
	public static void setOutputMapperXslt(EObject userObject, String xslt) {
		if (userObject != null) {
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
					.wrap(userObject);
			if (BpmnModelClass.ACTIVITY.isSuperTypeOf(userObject.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					EObjectWrapper<EClass, EObject> transformWrap = EObjectWrapper
							.wrap(transform);
					transformWrap
							.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, xslt);
				}
			} else if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(userObject
					.eClass())) {
				List<EObject> dataOutAssocs = userObjWrapper
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION);
				if (!dataOutAssocs.isEmpty()) {
					ROEObjectWrapper<EClass, EObject> doAssocWrap = ROEObjectWrapper
							.wrap((EObject) dataOutAssocs.get(0));

					EObject transform = (EObject) doAssocWrap
							.getAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION);
					EObjectWrapper<EClass, EObject> transformWrap = EObjectWrapper
							.wrap(transform);
					transformWrap
							.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, xslt);
				}
			}
		}
	}
	
	public static boolean isSWTMapper() {
		
		return false;
//		if (projectName != null && StudioProjectConfigurationManager.getInstance()
//				.getProjectConfiguration(projectName) != null) {
//			XPATH_VERSION xpathVersion = StudioProjectConfigurationManager.getInstance()
//					.getProjectConfiguration(projectName).getXpathVersion();
//			if (xpathVersion == XPATH_VERSION.XPATH_10) {
//				return false;
//			} else if (xpathVersion == XPATH_VERSION.XPATH_20) {
//				return true;
//			}
//		}
//		
////		return MessageDialog.openQuestion(new Shell(), "", "SWT?");
//		return "true".equals(System.getProperty("com.tibco.mapper.swt", "true"));
	}
	
	public static String getType(boolean primitive, String type) {
		if (primitive) {
			if (type.equals("Integer")) {
				type = "int";
			}
			if (type.equals("Double")) {
				type = "double";
			}
			if (type.equals("Long")) {
				type = "long";
			}
			if (type.equals("Boolean")) {
				type = "boolean";
			}
		}
		if (type.equalsIgnoreCase("Process")) {
			type = "Concept";
		}
		if (type.equalsIgnoreCase("DateTime")) {
			type = "Calendar";
		}
		return type;
	}
	
	public static String getType(String type) {
		if (type.equals("Integer")) {
			type = "int";
		}
		if (type.equals("Double")) {
			type = "double";
		}
		if (type.equals("Long")) {
			type = "long";
		}
		if (type.equals("Boolean")) {
			type = "boolean";
		}
		return type;
	}
	
	
	public static String getAttributeValue(EObjectWrapper<EClass, EObject> userObjWrapper, String attrName){
		if(userObjWrapper.containsAttribute(attrName)){
			Object attr = userObjWrapper.getAttribute(attrName);
			if (attr != null)
				return attr.toString();
		}

		return "";
	}

}
