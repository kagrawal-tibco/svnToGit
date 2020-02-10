package com.tibco.cep.studio.tester.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

import com.tibco.cep.studio.tester.ui.utils.TesterUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ShowResultsDynamicContribution extends CompoundContributionItem {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
	 */
	@Override
	protected IContributionItem[] getContributionItems() {
		List<IContributionItem> list = new ArrayList<IContributionItem>();
		TesterUIUtils.populateTesterRuns(null, list);
		IContributionItem[] items = new IContributionItem[list.size()];
		list.toArray(items);
		return items;
	}
}
