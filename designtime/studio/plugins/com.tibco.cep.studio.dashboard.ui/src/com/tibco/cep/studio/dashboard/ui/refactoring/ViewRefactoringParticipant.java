package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.Page;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.View;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ViewRefactoringParticipant extends DashboardRefactoringParticipant {

	public ViewRefactoringParticipant() {
		super(BEViewsElementNames.EXT_VIEW, BEViewsElementNames.DASHBOARD_PAGE, BEViewsElementNames.SKIN);
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		if (object instanceof Page) {
			URI refactoredURI = createRefactoredURI(object);
			Page referredPageSet = (Page) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> beViewElements = coreFactory.getChildren(BEViewsElementNames.VIEW);
			for (LocalElement beViewElement : beViewElements) {
				View View = (View) beViewElement.getEObject();
				View copyElement = (View) EcoreUtil.copy(View);
				EList<Page> pagesets = copyElement.getAccessiblePage();
				for (int i = 0; i < pagesets.size(); i++) {
					Page page = pagesets.get(i);
					if (checkEquals(referredPageSet, page)) {
						Page proxyObject = (Page) createProxyToNewPath(refactoredURI, View.eResource().getURI(), referredPageSet.eClass());
						pagesets.set(i, proxyObject);
						if (checkEquals(referredPageSet, copyElement.getDefaultPage())) {
							// Update the default pageset reference also
							copyElement.setDefaultPage(proxyObject);
						}
						Change change = createTextFileChange(IndexUtils.getFile(projectName, copyElement), copyElement);
						compositeChange.add(change);
					}
				}
			}
		} else if (object instanceof Skin) {
			URI refactoredURI = createRefactoredURI(object);
			Skin referredBEViewSkin = (Skin) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> beViewElements = coreFactory.getChildren(BEViewsElementNames.VIEW);
			for (LocalElement beViewElement : beViewElements) {
				View View = (View) beViewElement.getEObject();
				View copyElement = (View) EcoreUtil.copy(View);
				Skin skin = copyElement.getSkin();
				if (checkEquals(referredBEViewSkin, skin)) {
					Skin proxyObject = (Skin) createProxyToNewPath(refactoredURI, View.eResource().getURI(), referredBEViewSkin.eClass());
					copyElement.setSkin(proxyObject);
					Change change = createTextFileChange(IndexUtils.getFile(projectName, copyElement), copyElement);
					compositeChange.add(change);
				}
			}
		}
		return;
	}

	@Override
	protected String changeTitle() {
		return "View Changes:";
	}

	@Override
	protected boolean isExtensionOfInterest(String extension) {
		//FIXME extract the "system" extension to BEViewsElementNames
		return "system".equals(extension);
	}
}
