package com.tibco.cep.decision.table.wizard;

import java.util.Iterator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.RuleFunction;

/**
 * 
 * @author sasahoo
 * 
 */
public class NewDecisionTableViewerFilter extends ViewerFilter {
	private boolean visible = false;

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		if (element instanceof RuleFunction) {
			if (DecisionTableUtil.isVirtual((RuleFunction) element)) {
				return true;
			} else
				return false;
		}
		if (element instanceof Implementation) {
			return true;
		}
		if (element instanceof Folder) {
			visible = false;
			return isVisible((Folder) element);
		}
		return false;
	}

	private boolean isVisible(Folder folder) {
		Iterator<? extends AbstractResource> itr = folder.getChildren();
		while (itr.hasNext()) {
			AbstractResource resource = itr.next();
			if (resource instanceof Folder) {
				isVisible((Folder) resource);
			}
			if (resource instanceof RuleFunction) {
				if (DecisionTableUtil.isVirtual((RuleFunction) resource)) {
					visible = true;
				}
			}
		}
		if (visible == true) {
			return true;
		}
		return false;
	}
}
