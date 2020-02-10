package com.tibco.cep.bpmn.core.refactoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingRunner;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;

/**
 * 
 * @author majha
 *
 */
public class BpmnProcessRefactiongForXsltHelper {

	public BpmnProcessRefactiongForXsltHelper(){
		
	}
	
	public static boolean removeProcessXslt(EObject process) {
		boolean change = false;
		List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(process);
		for (EObject eObject : allFlowNodes) {
			BpmnModelUtils.setInputMapperXslt(eObject, "");
			BpmnModelUtils.setOutputMapperXslt(eObject, "");
		}

		return change;
	}
	
	public static boolean removeFlownodeXslt(EObject flowNode) {
		boolean change = false;
		BpmnModelUtils.setInputMapperXslt(flowNode, "");
		BpmnModelUtils.setOutputMapperXslt(flowNode, "");

		return change;
	}
	
	
	public static boolean handleProcessRefactor(EObject processToBeRefactor, String oldPath, String newPath, Map<String, String> nameSpaceMapper ){
		boolean change = false;
		List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(processToBeRefactor);
		for (EObject eObject : allFlowNodes) {
			change = handleXsltChangeForProcessRefactor(eObject, oldPath, newPath, nameSpaceMapper);
		}
		
		return change;
	}
	
	public static boolean handleXsltChangeForProcessRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper ){
		boolean change = false;
		change = handleOutputXsltChangeForProcessRefactor(flowNode, oldPath, newPath, nameSpaceMapper);
		change = handleInputXsltChangeForProcessRefactor(flowNode, oldPath, newPath, nameSpaceMapper);
		
		return change;
	}
	
	private static boolean handleInputXsltChangeForProcessRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper ){
		boolean change = false;
		
		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
		if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
			change = processXSLTFunctionForProcessRefactor(flowNode, newPath, oldPath, inputMapperXslt, true, nameSpaceMapper);
		}
		
		return change;
	}
	
	private static boolean handleOutputXsltChangeForProcessRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper  ){
		boolean change = false;
		String outputMapperXslt = BpmnModelUtils.getOutputMapperXslt(flowNode);
		if(outputMapperXslt != null && !outputMapperXslt.isEmpty()){
			change = processXSLTFunctionForProcessRefactor(flowNode, newPath, oldPath, outputMapperXslt, false, nameSpaceMapper);
		}
		
		return change;
	}
	
	public static boolean processXSLTFunctionForProcessPaste(EObject flowNode,
			String newPath,String oldPath,  Map<String, String> nameSpaceMapper){
		boolean change= false;
		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
		String outputMapperXslt = BpmnModelUtils.getOutputMapperXslt(flowNode);
		if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
			change = processXSLTFunctionForProcessRefactor(flowNode, newPath, oldPath, inputMapperXslt, true, nameSpaceMapper);
		}
		
		if(outputMapperXslt != null && !outputMapperXslt.isEmpty()){
			change = processXSLTFunctionForProcessRefactor(flowNode, newPath, oldPath, outputMapperXslt, false, nameSpaceMapper);
		}
		return change;
		
	}
	private static boolean  processXSLTFunctionForProcessRefactor(EObject flowNode,
			String newPath,String oldPath, String xslt, boolean inputMap, Map<String, String> nameSpaceMapper) {
		boolean change = false;
		TemplateBinding binding = MapperCoreUtils.getBinding(xslt, new ArrayList());
		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding)binding.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
		for (int i=0; i<nd.length; i++) {
			nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
		}
		ArrayList<?> receivingParams = XSTemplateSerializer.getReceivingParms(xslt);
		
		if (receivingParams.size() > 0) {
			// make sure that the receiving param matches the previous name/location before updating
			String param = (String) receivingParams.get(0);
			if (param.equals(oldPath)) {
				String xsltTemplate = BindingRunner.getXsltFor(binding, nsm );
				ArrayList<String> params = new ArrayList<String>();
				params.add(newPath);
				String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, new ArrayList<Object>());
				Set<String> keySet = nameSpaceMapper.keySet();
				for (String string : keySet) {
					newXslt = newXslt.replace(string, nameSpaceMapper.get(string));
				}
				if(inputMap)
					BpmnModelUtils.setInputMapperXslt(flowNode, newXslt);
				else
					BpmnModelUtils.setOutputMapperXslt(flowNode, newXslt);
				
				change = true;
			}
		}
		return change;
	}
	
	
	public static boolean handleXsltChangeForEventRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper,boolean isRenameRefactor){
		boolean change = false;
		change = handleOutputXsltChangeForEventRefactor(flowNode, oldPath, newPath, nameSpaceMapper, isRenameRefactor);
		change = handleInputXsltChangeForEventRefactor(flowNode, oldPath, newPath, nameSpaceMapper, isRenameRefactor);
		
		return change;
	}
	

	
	public static boolean processXSLTFunctionForProcessVariableRefactor(EObject flowNode,String newPath,String oldPath, String xslt,
																			boolean inputMap, Map<String, String> nameSpaceMapper, boolean isRenameRefactor){
		boolean change = false ;
			// case of output mapping
			if (isRenameRefactor) {
				String oName = " name=\\\"" + oldPath + "\\\"";
				String nName = " name=\\\"" + newPath + "\\\"";
				String newXslt = xslt.replace(oName, nName);
				newXslt = newXslt.replace("$" + oldPath, "$" + newPath);
				if (!newXslt.equals(xslt))
					BpmnModelUtils.setInputMapperXslt(flowNode, newXslt);
				return true ;
		}
		
		return change ;
	}
	
	public static boolean processOpXSLTFunctionForProcessVarrefactor( EObject flowNode,String newPath,String oldPath, String xslt ){
		String newXslt = null ;
		newXslt = xslt.replace( oldPath,  newPath);
		if (!newXslt.equals(xslt)) {
			BpmnModelUtils.setOutputMapperXslt(flowNode, newXslt);
		    return true ;
		}
		return false; 
	}
	public static boolean handleXsltChangeForEventPropRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper,boolean isRenameRefactor){
		boolean change = false;
		change = handleOutputXsltChangeForEventPropRefactor(flowNode, oldPath, newPath, nameSpaceMapper, isRenameRefactor);
//		change = handleInputXsltChangeForEventPropRefactor(flowNode, oldPath, newPath, nameSpaceMapper, isRenameRefactor);
		
		return change;
	}
	
	
//	private static boolean handleInputXsltChangeForEventPropRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper, boolean isRenameRefactor){
//		boolean change = false;
//		
//		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
//		if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
//			change = processXSLTFunctionForEventPropRefactor(flowNode, newPath, oldPath, inputMapperXslt, true, nameSpaceMapper, isRenameRefactor);
//		}
//		
//		return change;
//	}
	
	
	private static boolean handleOutputXsltChangeForEventPropRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper, boolean isRenameRefactor){
		boolean change = false;
		String outputMapperXslt = BpmnModelUtils.getOutputMapperXslt(flowNode);
		if(outputMapperXslt != null && !outputMapperXslt.isEmpty()){
			change = processXSLTFunctionForEventPropRefactor(flowNode, newPath, oldPath, outputMapperXslt, false, nameSpaceMapper, isRenameRefactor);
		}
	return change ;
	}
	
	private static boolean handleInputXsltChangeForEventRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper, boolean isRenameRefactor){
		boolean change = false;
		
		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
		if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
			change = processXSLTFunctionForEventRefactor(flowNode, newPath, oldPath, inputMapperXslt, true, nameSpaceMapper, isRenameRefactor);
		}
		
		return change;
	}
	
	
	private static boolean handleOutputXsltChangeForEventRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper, boolean isRenameRefactor){
		boolean change = false;
		String outputMapperXslt = BpmnModelUtils.getOutputMapperXslt(flowNode);
		if(outputMapperXslt != null && !outputMapperXslt.isEmpty()){
			change = processXSLTFunctionForEventRefactor(flowNode, newPath, oldPath, outputMapperXslt, false, nameSpaceMapper, isRenameRefactor);
		}
		
		return change;
	}
	
	private static boolean processXSLTFunctionForEventPropRefactor(EObject flowNode,
			String newPath,String oldPath, String xslt, boolean inputMap, Map<String, String> nameSpaceMapper, boolean isRenameRefactor) {
		if (!inputMap) {
			// case of output mapping
			if (isRenameRefactor) {
				String oName = " name=\\\"" + oldPath + "\\\"";
				String nName = " name=\\\"" + newPath + "\\\"";
				String newXslt = xslt.replace(oName, nName);
				newXslt = newXslt.replace("$" + oldPath, "$" + newPath);
				if (!newXslt.equals(xslt))
					BpmnModelUtils.setOutputMapperXslt(flowNode, newXslt);
				return true ;
			}
		}
		
		return false ;
		
	}
	private static boolean  processXSLTFunctionForEventRefactor(EObject flowNode,
			String newPath,String oldPath, String xslt, boolean inputMap, Map<String, String> nameSpaceMapper, boolean isRenameRefactor) {
		boolean change = false;
		TemplateBinding binding = MapperCoreUtils.getBinding(xslt, new ArrayList());
		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding)binding.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
		for (int i=0; i<nd.length; i++) {
			nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
		}
		ArrayList<?> receivingParams = XSTemplateSerializer.getReceivingParms(xslt);
		
		String newName = null;
		String oldName = null;
		if(isRenameRefactor){
			String[] split = oldPath.split("/");
			oldName = split[split.length-1];
			split = newPath.split("/");
			newName = split[split.length-1];
		}
		if (receivingParams.size() > 0) {
			// make sure that the receiving param matches the previous name/location before updating
			String param = (String) receivingParams.get(0);
			if (param.equals(oldPath)) {
				String xsltTemplate = BindingRunner.getXsltFor(binding, nsm );
				ArrayList<String> params = new ArrayList<String>();
				params.add(newPath);
				String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, new ArrayList<Object>());
				Set<String> keySet = nameSpaceMapper.keySet();
				for (String string : keySet) {
					newXslt = newXslt.replace(string, nameSpaceMapper.get(string));
					if(isRenameRefactor){
						String prefix = nsm.getOrAddPrefixForNamespaceURI(string);
						String oName = prefix+":"+oldName;
						String nName = prefix+":"+newName;
						newXslt = newXslt.replace(oName, nName);
					}
				}
				
				if(inputMap)
					BpmnModelUtils.setInputMapperXslt(flowNode, newXslt);
				else
					BpmnModelUtils.setOutputMapperXslt(flowNode, newXslt);
				
				change = true;
			}
		}
		if (!inputMap) {
			// case of output mapping
			if (isRenameRefactor) {
				String oName = " name=\\\"" + oldName + "\\\"";
				String nName = " name=\\\"" + newName + "\\\"";
				String newXslt = xslt.replace(oName, nName);
				newXslt = newXslt.replace("$" + oldName, "$" + newName);
				if (!newXslt.equals(xslt))
					BpmnModelUtils.setOutputMapperXslt(flowNode, newXslt);
			}
		}
		
		
		return change;
	}
	
	
	public static boolean handleXsltChangeForRuleFunctionRefactor(EObject flowNode, String oldPath, String newPath, Map<String, String> nameSpaceMapper,boolean isRenameRefactor){
		boolean change = false;
		String inputMapperXslt = BpmnModelUtils.getInputMapperXslt(flowNode);
		if(inputMapperXslt != null && !inputMapperXslt.isEmpty()){
			change = processXSLTFunctionForRuleFunctionRefactor(flowNode, newPath, oldPath, inputMapperXslt, nameSpaceMapper, isRenameRefactor);
		}
		
		return change;
	}
	
	
	
	private static boolean  processXSLTFunctionForRuleFunctionRefactor(EObject flowNode,
			String newPath,String oldPath, String xslt, Map<String, String> nameSpaceMapper, boolean isRenameRefactor) {
		boolean change = false;
		TemplateBinding binding = MapperCoreUtils.getBinding(xslt, new ArrayList());
		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding)binding.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
		for (int i=0; i<nd.length; i++) {
			nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
		}
		ArrayList<?> receivingParams = XSTemplateSerializer.getReceivingParms(xslt);
		
		String newName = null;
		String oldName = null;
		if(isRenameRefactor){
			String[] split = oldPath.split("/");
			oldName = split[split.length-1];
			split = newPath.split("/");
			newName = split[split.length-1];
		}
		if (receivingParams.size() > 0) {
			// make sure that the receiving param matches the previous name/location before updating
			String param = (String) receivingParams.get(0);
			if (param.equals(oldPath)) {
				String xsltTemplate = BindingRunner.getXsltFor(binding, nsm );
				ArrayList<String> params = new ArrayList<String>();
				params.add(newPath);
				String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, new ArrayList<Object>());
				Set<String> keySet = nameSpaceMapper.keySet();
				for (String string : keySet) {
					newXslt = newXslt.replace(string, nameSpaceMapper.get(string));
					if(isRenameRefactor){
						String prefix = nsm.getOrAddPrefixForNamespaceURI(string);
						String oName = prefix+":"+oldName;
						String nName = prefix+":"+newName;
						newXslt = newXslt.replace(oName, nName);
					}
				}
				
				BpmnModelUtils.setInputMapperXslt(flowNode, newXslt);
				
				change = true;
			}
		}
		
		
		return change;
	}
	

	
	

}
