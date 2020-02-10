package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartColorSetRefactoringParticipant extends DashboardRefactoringParticipant {

	public ChartColorSetRefactoringParticipant() {
		super(BEViewsElementNames.EXT_CHART_COLOR_SET, BEViewsElementNames.SERIES_COLOR);
	}

	@Override
	protected String changeTitle() {
		return "Chart Color Set Changes:";
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		List<EObject> family = getFamily(object);
		
		List<EObject> refactoredSeriesColors = new LinkedList<EObject>();
		for (EObject member : family) {
			if (member instanceof SeriesColor) {
				refactoredSeriesColors.add(member);
			}
		}

		LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(getResource().getProject());
		List<LocalElement> chartColorSets = coreFactory.getChildren(BEViewsElementNames.CHART_COLOR_SET);
		for (LocalElement chartColorSetElement : chartColorSets) {
			boolean changed = false;
			ChartComponentColorSet chartColorSet = (ChartComponentColorSet) chartColorSetElement.getEObject();
			ChartComponentColorSet copyElement = (ChartComponentColorSet) EcoreUtil.copy(chartColorSet);
			EList<SeriesColor> seriesColors = copyElement.getSeriesColor();
			for (int i = 0; i < seriesColors.size(); i++) {
				SeriesColor seriesColor = seriesColors.get(i);
				for (EObject refactoredSeriesColor : refactoredSeriesColors) {
					if (checkEquals(refactoredSeriesColor, seriesColor)) {
						SeriesColor proxyObject = (SeriesColor) createProxyToNewPath(createRefactoredURI(refactoredSeriesColor), chartColorSet.eResource().getURI(), refactoredSeriesColor.eClass());
						seriesColors.set(i, proxyObject);
						changed = true;
					}
				}
			}
			if (changed == true) {
				Change change = createTextFileChange(IndexUtils.getFile(projectName, copyElement), copyElement);
				compositeChange.add(change);
			}
		}
	}

	@Override
	protected boolean isExtensionOfInterest(String extension) {
		// FIXME extract the "system" extension to BEViewsElementNames
		return "system".equals(extension);
	}
}