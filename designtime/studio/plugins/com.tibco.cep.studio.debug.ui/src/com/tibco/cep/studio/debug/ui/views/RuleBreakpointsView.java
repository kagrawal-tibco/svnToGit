package com.tibco.cep.studio.debug.ui.views;

import org.eclipse.debug.internal.ui.actions.breakpoints.ShowSupportedBreakpointsAction;
import org.eclipse.debug.internal.ui.views.breakpoints.BreakpointsView;
@SuppressWarnings("restriction")
public class RuleBreakpointsView extends BreakpointsView {
	
	public static final String RULE_BREAKPOINT_VIEW_ID = "com.tibco.cep.studio.debug.ui.views.rulebreakpointview";
	
	@Override
	protected void createActions() {
		super.createActions();
		setAction("ShowBreakpointsForModel", new ShowSupportedBreakpointsAction(getStructuredViewer(),this)); //$NON-NLS-1$
	}

}
