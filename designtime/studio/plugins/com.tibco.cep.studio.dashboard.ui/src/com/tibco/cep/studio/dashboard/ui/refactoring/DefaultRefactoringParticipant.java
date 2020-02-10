package com.tibco.cep.studio.dashboard.ui.refactoring;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DefaultRefactoringParticipant extends DashboardRefactoringParticipant {

	public DefaultRefactoringParticipant() {
		super(null, "");
		supportedExtensions = new String[] {
				BEViewsElementNames.EXT_TEXT_COLOR_SET,
				BEViewsElementNames.EXT_SERIES_COLOR,
				BEViewsElementNames.EXT_PAGE_SELECTOR_COMPONENT,
		};
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		return;
	}

	@Override
	protected String changeTitle() {
		return "Independent Element Changes:";
	}

	@Override
	protected boolean isElementOfInterest(String elementType) {
		return false;
	}

}
