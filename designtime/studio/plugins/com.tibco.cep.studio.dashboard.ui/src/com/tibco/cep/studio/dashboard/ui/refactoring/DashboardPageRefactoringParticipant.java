package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Panel;
import com.tibco.cep.designtime.core.model.beviewsconfig.Partition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class DashboardPageRefactoringParticipant extends DashboardRefactoringParticipant {

	public DashboardPageRefactoringParticipant() {
		super(BEViewsElementNames.EXT_DASHBOARD_PAGE, 
				BEViewsElementNames.TEXT_OR_CHART_COMPONENT,
				BEViewsElementNames.CHART_COMPONENT, 
				BEViewsElementNames.TEXT_COMPONENT, 
				BEViewsElementNames.PAGE_SELECTOR_COMPONENT, 
				BEViewsElementNames.STATE_MACHINE_COMPONENT);
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange)
			throws Exception {
		if (object instanceof Component){
			URI refactoredURI = createRefactoredURI(object);
			Component referredComponent = (Component) object;
			LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
			List<LocalElement> dashboardPages = coreFactory.getChildren(BEViewsElementNames.DASHBOARD_PAGE);
			for (LocalElement dashboardPageElement : dashboardPages) {
				DashboardPage dashboardPage = (DashboardPage) dashboardPageElement.getEObject();
				DashboardPage dashboardPageCopy = (DashboardPage) EcoreUtil.copy(dashboardPage);
				EList<Partition> partitions = dashboardPageCopy.getPartition();
				for (Partition partition : partitions) {
					EList<Panel> panels = partition.getPanel();
					for (Panel panel : panels) {
						EList<Component> components = panel.getComponent();
						for (int i = 0; i < components.size(); i++) {
							Component component = components.get(i);
							if (checkEquals(referredComponent, component)){
								Component proxyObject = (Component)createProxyToNewPath(refactoredURI, dashboardPage.eResource().getURI(), referredComponent.eClass());
								components.set(i, proxyObject);
								Change change = createTextFileChange(IndexUtils.getFile(projectName, dashboardPageCopy), dashboardPageCopy);
								compositeChange.add(change);
							}
						}
					}
				}
			}
		}
		return;
	}

	@Override
	protected String changeTitle() {
		return "Dashboard Page Changes:";
	}

}