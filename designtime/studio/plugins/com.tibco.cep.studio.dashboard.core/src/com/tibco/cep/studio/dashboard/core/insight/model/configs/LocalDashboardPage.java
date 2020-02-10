package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalDashboardPage extends LocalPage {

	private static final String THIS_TYPE = BEViewsElementNames.DASHBOARD_PAGE;

	public LocalDashboardPage() {
		super(THIS_TYPE);
	}

	public LocalDashboardPage(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalDashboardPage(LocalElement parentConfig, String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		super.validateParticle(particle);
		if (BEViewsElementNames.PARTITION.equals(particle.getName()) == true) {
			List<LocalElement> partitions = getChildren(BEViewsElementNames.PARTITION);
			double totalSpan = 0;
			for (LocalElement partition : partitions) {
				totalSpan = totalSpan + ((LocalPartition) partition).getSpan();
			}
			if (totalSpan > 100) {
				addValidationErrorMessage("'Span' of all partitions exceeds 100%");
			}
		}
	}
}
