package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder;
import com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference;
import com.tibco.cep.designtime.core.model.beviewsconfig.View;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class RolePreferenceRefactoringParticipant extends DashboardRefactoringParticipant {

	public RolePreferenceRefactoringParticipant() {
		super(BEViewsElementNames.EXT_ROLE_PREFERENCE, 
				BEViewsElementNames.VIEW, 
				BEViewsElementNames.TEXT_OR_CHART_COMPONENT,
				BEViewsElementNames.CHART_COMPONENT, 
				BEViewsElementNames.TEXT_COMPONENT,
				BEViewsElementNames.PAGE_SELECTOR_COMPONENT);
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		if (object instanceof View) {
			URI refactoredURI = createRefactoredURI(object);
			View referredBEView = (View) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> rolePreferenceElements = coreFactory.getChildren(BEViewsElementNames.ROLE_PREFERENCE);
			for (LocalElement rolePreferenceElement : rolePreferenceElements) {
				RolePreference rolePreference = (RolePreference) rolePreferenceElement.getEObject();
				RolePreference copyElement = (RolePreference) EcoreUtil.copy(rolePreference);
				View View = copyElement.getView();
				if (checkEquals(referredBEView, View)) {
					View proxyObject = (View) createProxyToNewPath(refactoredURI, rolePreference.eResource().getURI(), referredBEView.eClass());
					copyElement.setView(proxyObject);
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyElement), copyElement);
					compositeChange.add(change);
				}
			}
		} else if (object instanceof Component) {
			URI refactoredURI = createRefactoredURI(object);
			Component referredComponent = (Component) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> rolePreferenceElements = coreFactory.getChildren(BEViewsElementNames.ROLE_PREFERENCE);
			for (LocalElement rolePreferenceElement : rolePreferenceElements) {
				RolePreference rolePreference = (RolePreference) rolePreferenceElement.getEObject();
				RolePreference copyElement = (RolePreference) EcoreUtil.copy(rolePreference);
				ComponentGalleryFolder gallery = copyElement.getGallery();
				if (gallery != null) {
					boolean updateReferences = updateReferences(rolePreference, referredComponent, gallery.getComponent(), gallery.getSubFolder(), refactoredURI, copyElement, projectName,
							compositeChange);
					if (updateReferences) {
						Change change = createTextFileChange(IndexUtils.getFile(projectName, copyElement), copyElement);
						compositeChange.add(change);
					}
				}
			}

		}
		return;
	}

	private boolean updateReferences(RolePreference rolePreference, Component referredComponent, EList<Component> components, EList<ComponentGalleryFolder> folders, URI refactoredURI,
			RolePreference copyElement, String projectName, CompositeChange compositeChange) throws CoreException {
		boolean update = false;
		for (int i = 0; i < components.size(); i++) {
			Component component = components.get(i);
			if (checkEquals(referredComponent, component)) {
				Component proxyObject = (Component) createProxyToNewPath(refactoredURI, rolePreference.eResource().getURI(), referredComponent.eClass());
				components.set(i, proxyObject);
				update = true;
			}
		}
		for (ComponentGalleryFolder folder : folders) {
			if (updateReferences(rolePreference, referredComponent, folder.getComponent(), folder.getSubFolder(), refactoredURI, copyElement, projectName, compositeChange)) {
				update = true;
			}
		}
		return update;
	}

	@Override
	protected String changeTitle() {
		return "Role Preference Changes:";
	}

}
