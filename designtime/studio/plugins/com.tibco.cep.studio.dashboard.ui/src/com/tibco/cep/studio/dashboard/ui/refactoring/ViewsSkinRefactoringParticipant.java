package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ViewsSkinRefactoringParticipant extends DashboardRefactoringParticipant {

	public ViewsSkinRefactoringParticipant() {
		super(BEViewsElementNames.EXT_SKIN, BEViewsElementNames.CHART_COLOR_SET, BEViewsElementNames.TEXT_COLOR_SET);
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		Map<String,Skin> copiedSkins = new HashMap<String, Skin>();
		Set<String> changedSkins = new HashSet<String>();
		List<EObject> family = getFamily(object);
		for (EObject member : family) {
			if (member instanceof ComponentColorSet) {
				URI refactoredURI = createRefactoredURI(member);
				ComponentColorSet referredColorSet = (ComponentColorSet) member;
				LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
				List<LocalElement> skinElements = coreFactory.getChildren(BEViewsElementNames.SKIN);
				for (LocalElement skinElement : skinElements) {
					if (((LocalSkin)skinElement).isSystem() == true){
						//skip system element skin
						continue;
					}
					Skin skin = (Skin) skinElement.getEObject();
					Skin copyElement = copiedSkins.get(skin.getGUID());
					if (copyElement == null) {
						copyElement = (Skin) EcoreUtil.copy(skin);
						copiedSkins.put(copyElement.getGUID(),copyElement);
					}
					List<ComponentColorSet> componentColorSets = copyElement.getComponentColorSet();
					List<ComponentColorSet> defaultComponentColorSets = copyElement.getDefaultComponentColorSet();
					for (int i = 0; i < componentColorSets.size(); i++) {
						ComponentColorSet colorSet = componentColorSets.get(i);
						if (checkEquals(referredColorSet, colorSet)) {
							ComponentColorSet proxyObject = (ComponentColorSet) createProxyToNewPath(refactoredURI, skin.eResource().getURI(), referredColorSet.eClass());
							componentColorSets.set(i, proxyObject);

							for (int j = 0; j < defaultComponentColorSets.size(); j++) {
								ComponentColorSet defaultColorSet = defaultComponentColorSets.get(j);
								if (checkEquals(referredColorSet, defaultColorSet)) {
									// This means this colorset is set as
									// default too
									// Update the default element too..
									defaultComponentColorSets.set(j, proxyObject);
									break;
								}
							}
							changedSkins.add(copyElement.getGUID());
						}
					}

				}
			}
		}
		if (changedSkins.isEmpty() == false){
			for (String guid : changedSkins) {
				Skin skin = copiedSkins.get(guid);
				Change change = createTextFileChange(IndexUtils.getFile(projectName, skin), skin);
				compositeChange.add(change);						
			}
		}
	}

	@Override
	protected String changeTitle() {
		return "Skin Changes:";
	}

	@Override
	protected boolean isExtensionOfInterest(String extension) {
		// FIXME extract the "system" extension to BEViewsElementNames
		return extension.equals("system");
	}
}