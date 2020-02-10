package com.tibco.cep.studio.tester.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;

/**
 * 
 * @author sasahoo
 *
 */
public class RuleSessionDynamicContributionItem extends CompoundContributionItem {

	private List<ActionContributionItem> items = new ArrayList<ActionContributionItem>();
	
	/**
	 * @param items
	 */
	public RuleSessionDynamicContributionItem(List<ActionContributionItem> items) {
		this.items.addAll(items);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.CompoundContributionItem#getContributionItems()
	 */
	@Override
	protected IContributionItem[] getContributionItems() {
		List<IContributionItem> list = new ArrayList<IContributionItem>();
		for (ActionContributionItem item : items) {
			list.add(item);
		}
		IContributionItem[] items = new IContributionItem[list.size()];
		list.toArray(items);
		return items;
	}
}