package com.tibco.cep.studio.debug.ui.views;

import org.eclipse.debug.internal.ui.views.launch.LaunchView;
import org.eclipse.swt.widgets.Composite;
@SuppressWarnings({"restriction"})
public class RuleStackView extends LaunchView {
	
	public static final String RULE_STACK_VIEW_ID = "com.tibco.cep.studio.debug.ui.views.rulestackview";

	public RuleStackView() {
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		setPartName("Rule Debug Stack");

	}
	
	@Override
	protected void createActions() {
		super.createActions();
	}

}
