package com.tibco.cep.studio.tester.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.TesterUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class ShowWMDynamicContribution extends CompoundContributionItem {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
	 */
	@Override
	protected IContributionItem[] getContributionItems() {
		List<IContributionItem> list = new ArrayList<IContributionItem>();
		try {
			TesterUIUtils.populateWMSessions(null, list);
		} catch (Exception e) {
			StudioTesterUIPlugin.log(e);
		}
		IContributionItem[] items = new IContributionItem[list.size()];
		list.toArray(items);
		return items;
	}
}
