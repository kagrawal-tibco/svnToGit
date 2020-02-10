package com.tibco.cep.bpmn.core.index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.common.configuration.NamePrefix;


public class BpmnNameGenerator {
	
	EObject index;
	private  List<Integer> uIds	  =new ArrayList<Integer>();
	private  Integer generatedUid =0;
	BpmnNameGenerator(EObject index) {
		this.index = index;
	}
	
	/**
	 * @param type
	 * @param name TODO
	 * @param processName
	 * @return
	 */

	public Identifier getNextIdentifier(EObject elementContainer,EClass type, String name) {
		if(!BpmnModelClass.BASE_ELEMENT.isSuperTypeOf(type)) {
			throw new IllegalArgumentException("Only Base element type can have generated Ids");
		}
		BpmnIndex ontology = new DefaultBpmnIndex(index);
		String projectName = ontology.getName();
		BpmnProcessSettings bpmnConfig = BpmnCorePlugin.getBpmnProjectConfiguration(projectName);
		Map<String, NamePrefix> prefixMap = bpmnConfig.getNamePrefixMap();
		
		if(!BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(elementContainer.eClass())){
			elementContainer = BpmnModelUtils.getFlowElementContainer(elementContainer);
		}
		
		Identifier containerId = BpmnModelUtils.getFlowElementContainerId(elementContainer);
		
		Collection<EObject> elements;
		String identifierPrefix = null;
		if (type.equals(BpmnModelClass.PROCESS)) {
			elements = ontology.getAllProcesses();
			identifierPrefix = containerId.getId();
			List<Integer> noList = new ArrayList<Integer>();
			for (EObject element:elements) {
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
				String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (id !=null) {
					if (id.startsWith(identifierPrefix)) {
						int lastIndex = id.lastIndexOf("_");
						if(lastIndex != -1) {
							String no = id.substring( lastIndex + 1);
							noList.add(getValidNo(no));
						} else {
						}
					}
				}
			}
			if (noList.size() > 0) {
				int no = Collections.max(noList);
				no++;
				identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
				return new Identifier(identifierPrefix+"_"+ no);
			} else {
				identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
				return new Identifier(identifierPrefix);
			}
		} else {
			elements = ontology.getProcessElementsByType(elementContainer, type);
			String typePrefix = null;
			if(name != null) {
				typePrefix = name;
			} else {
				if(type.equals((BpmnModelClass.RULE_FUNCTION_TASK)))           //TODO : Just a temporary fix for issue BE: 19893. Will have to handle it well later
					typePrefix ="Script";
				if(type.equals((BpmnModelClass.JAVA_TASK)))           		   //Fix for issue BE: 22756.
					typePrefix ="Java";
				else
				    typePrefix = prefixMap.containsKey(type.getName())? prefixMap.get(type.getName()).getPrefix():type.getName();
			}
			typePrefix = normaliseIdentifierPrefix(typePrefix);
			identifierPrefix = containerId.getId()+"."+typePrefix;
			List<Integer> noList = new ArrayList<Integer>(); 
			for (EObject element:elements) {
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
				String id = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (id !=null) {
					if (id.startsWith(identifierPrefix)) {
						int lastIndex = id.lastIndexOf("_");
						if(lastIndex != -1) {
							String no = id.substring(id.lastIndexOf("_") + 1);
							noList.add(getValidNo(no));
						}
					}
				}
			}
			if (noList.size() > 0) {
				int no = Collections.max(noList);
				no++;
				identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
				return new Identifier(identifierPrefix +"_"+ no);
			}
		}
		
		identifierPrefix = normaliseIdentifierPrefix(identifierPrefix);
		return new Identifier(identifierPrefix+"_"+0);
	}
	
	private String normaliseIdentifierPrefix(String identifierPrefix){
		if(identifierPrefix != null){
			identifierPrefix = identifierPrefix.trim();
			identifierPrefix = identifierPrefix.replaceAll("\\s+", "_");
		}
		
		
		return identifierPrefix;
	}
	
	/**
	 * @param no
	 * @return
	 */
	public static int getValidNo(String no) {
		int n;
		try{
			n = Integer.parseInt(no); 
		}catch(Exception e) {
			return 0;
		}
		return n;
	}
	
	public int createUid(EObject elt){
		createUidList(elt);
		generatedUid=0;
		return getUniqueid();
	}
	
	public void createUidList(EObject elt){
		uIds=new ArrayList<Integer>();
		try{
		EObjectWrapper<EClass, EObject>  process=BpmnModelUtils.getProcess(elt);
		List<EObject> allFlowNodes = BpmnModelUtils
				.getAllFlowNodes(process.getEInstance());
		List<EObject> allSeqFLowNodes =BpmnModelUtils.getAllSequenceFlows(process.getEInstance());
		for (EObject element:allFlowNodes) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			Integer uId=(Integer)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			if(uId!=0 && uId!=null)
				uIds.add(uId);
//			System.out.println(uIds.size());
		}
		for(EObject element:allSeqFLowNodes){
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			Integer uId=(Integer)elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_UNIQUE_ID);
			if(uId!=0 && uId!=null)
				uIds.add(uId);
		}
		}catch(Exception e){
			System.out.println(e);
		}
				
	}
	
	private int getUniqueid(){	
		if (uIds.contains(++generatedUid))
			return getUniqueid();
		else
			return generatedUid;
	}
	
	public EObject getIndex() {
		return index;
	}

	public List<Integer> getuIds() {
		return uIds;
	}

	public void setuIds(List<Integer> uIds) {
		this.uIds = uIds;
	}

	

}
