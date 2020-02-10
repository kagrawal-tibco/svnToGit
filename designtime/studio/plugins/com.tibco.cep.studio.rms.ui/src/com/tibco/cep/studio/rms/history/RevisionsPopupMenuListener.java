/**
 * 
 */
package com.tibco.cep.studio.rms.history;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;


/**
 * @author aathalye
 *
 */
public class RevisionsPopupMenuListener implements IMenuListener {

	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void menuAboutToShow(IMenuManager manager) {
		IContributionItem[] contributionItems = manager.getItems();
		for (IContributionItem contributionItem : contributionItems) {
			if (contributionItem instanceof ActionContributionItem) {
				ActionContributionItem actionContributionItem = (ActionContributionItem)contributionItem;
				IAction action = actionContributionItem.getAction();
				action.isEnabled();
			}
		}
	}
}
