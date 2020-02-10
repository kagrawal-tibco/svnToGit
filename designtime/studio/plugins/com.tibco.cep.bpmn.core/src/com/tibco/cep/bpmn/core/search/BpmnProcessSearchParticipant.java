package com.tibco.cep.bpmn.core.search;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;

/**
 * 
 * @author majha
 *
 */
public class BpmnProcessSearchParticipant implements ISearchParticipant {

	public BpmnProcessSearchParticipant() {
		super();
	}

	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {

		SearchResult result = new SearchResult();
		List<EObject> allProcesses = BpmnIndexUtils
				.getAllProcesses(projectName);
		for (EObject process : allProcesses) {
			List<EObject> allFlowNodes = BpmnModelUtils
					.getAllFlowNodes(process);
			allFlowNodes.add(0, process);
			for (EObject flowNode : allFlowNodes) {
				Object resource = BpmnModelUtils.getAttachedResource(flowNode);
				if (resource == null)
					continue;
				if (resolvedElement instanceof TypeElement) {
					TypeElement element = (TypeElement) resolvedElement;
					String path = element.getFolder() + element.getName();
					if (resource instanceof String) {
						String val = (String) resource;
						if (val.equals(path)) {
							result.addExactMatch(process);
							continue;
						}
					}
				} else if (resolvedElement instanceof EObject
						&& ((EObject) flowNode).eClass().equals(
								BpmnModelClass.CALL_ACTIVITY)) {
					if (resource instanceof EObject) {
						EObject resoElement = (EObject) resolvedElement;
						EObject val = (EObject) resource;
						if (BpmnModelClass.PROCESS.isSuperTypeOf(val.eClass())
								&& BpmnModelClass.PROCESS
										.isSuperTypeOf(resoElement.eClass())) {
							if (val.eIsProxy()) {
								URI proxyURI = ((org.eclipse.emf.ecore.InternalEObject) val)
										.eProxyURI();
								IFile file = BpmnIndexUtils.getFile(
										projectName, resoElement);
								if(file != null && file.exists()){
									URI fileURI = URI.createPlatformResourceURI(
											file.getFullPath().toPortableString(),
											false);
									if (proxyURI.path().equals(fileURI.path())) {
										result.addExactMatch(process);
										continue;
									}
								}
							} else {
								String valId = EObjectWrapper
										.wrap(val)
										.getAttribute(
												BpmnMetaModelConstants.E_ATTR_ID);
								String resolvedElementId = EObjectWrapper
										.wrap(resoElement)
										.getAttribute(
												BpmnMetaModelConstants.E_ATTR_ID);
								if (valId.equals(resolvedElementId)) {
									result.addExactMatch(process);
									continue;
								}
							}
						}
					}
				}

			}
		}

		return result;

	}

}
