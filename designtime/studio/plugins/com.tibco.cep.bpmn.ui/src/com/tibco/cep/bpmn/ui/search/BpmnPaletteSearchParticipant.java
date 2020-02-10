package com.tibco.cep.bpmn.ui.search;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.common.palette.PalettePackage;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;

/**
 * 
 * @author majha
 * 
 */
public class BpmnPaletteSearchParticipant implements ISearchParticipant {

	public BpmnPaletteSearchParticipant() {
		super();
	}

	public SearchResult search(Object resolvedElement, String projectName,
			String nameToFind, IProgressMonitor monitor) {

		SearchResult result = new SearchResult();
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		Map<IFile, BpmnPaletteModel> allPalettes = BpmnPaletteResourceUtil
				.getAllPalettes(project);
		Set<IFile> keySet = allPalettes.keySet();
		for (IFile f : keySet) {
			BpmnPaletteModel model = allPalettes.get(f);
			if(model == null)
				continue;
			List<BpmnPaletteGroup> bpmnPaletteGroups = model
					.getBpmnPaletteGroups();
			for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
				List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup
						.getPaletteItems();
				for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
					String attachedResource = bpmnPaletteGroupItem
							.getAttachedResource();
					if (attachedResource == null
							|| attachedResource.trim().isEmpty())
						continue;

					if (resolvedElement instanceof TypeElement) {
						TypeElement element = (TypeElement) resolvedElement;
						String path = element.getFolder() + element.getName();
						BpmnCommonPaletteGroupItemType itemType = bpmnPaletteGroupItem.getItemType();
						if(itemType instanceof BpmnCommonPaletteGroupEmfItemType){
							BpmnCommonPaletteGroupEmfItemType emfType = (BpmnCommonPaletteGroupEmfItemType)itemType;
							EClass modelType = BpmnMetaModel.getInstance().getEClass( emfType.getEmfType());
							if(modelType.equals(BpmnModelClass.INFERENCE_TASK)){
								if(attachedResource.contains(path)){
									ElementMatch createElementMatch = createElementMatch(PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE, bpmnPaletteGroupItem.getItem().eClass(), model.getModel());
									result.addExactMatch(createElementMatch);
									continue;
								}
							}
						}
						if (attachedResource.equals(path)) {
							ElementMatch createElementMatch = createElementMatch(PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE, bpmnPaletteGroupItem.getItem().eClass(), model.getModel());
							result.addExactMatch(createElementMatch);
							continue;
						}
					} else if (resolvedElement instanceof EObject) {
						EObject resoElement = (EObject) resolvedElement;
						if (BpmnModelClass.PROCESS.isSuperTypeOf(resoElement
								.eClass())) {
							IFile file = BpmnIndexUtils.getFile(projectName,
									resoElement);
							if(file!= null && file.exists()){
								URI fileURI = URI.createPlatformResourceURI(file
										.getFullPath().toPortableString(), false);
								if (attachedResource.equals(fileURI.path())) {
									ElementMatch createElementMatch = createElementMatch(PalettePackage.PALETTE_ITEM__ATTACHED_RESOURCE, bpmnPaletteGroupItem.getItem().eClass(), model.getModel());
									result.addExactMatch(createElementMatch);
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
	
	protected ElementMatch createElementMatch(int featureId, EClass class1,
			EObject matchedElement) {
		ElementMatch match = SearchFactory.eINSTANCE.createElementMatch();

		EStructuralFeature feature = class1.getEStructuralFeature(featureId);
		match.setFeature(feature);
		match.setMatchedElement(matchedElement);
		return match;
	}

}
