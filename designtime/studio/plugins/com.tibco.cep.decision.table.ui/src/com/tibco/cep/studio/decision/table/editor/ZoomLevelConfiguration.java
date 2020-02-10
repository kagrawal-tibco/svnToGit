package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.swt.SWT;

import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

public class ZoomLevelConfiguration extends AbstractUiBindingConfiguration{

	static IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
	private final NatTable natTable;
	private IDecisionTableEditor editor;
    
	public ZoomLevelConfiguration(NatTable natTable, IDecisionTableEditor editor) {
		this.natTable = natTable;
		this.editor = editor;
	}

	@Override
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
		uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1 | SWT.SHIFT, '+'), new ZoomAction(true, editor.getDtDesignViewer()));
		uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, '='), new ZoomAction(true, editor.getDtDesignViewer()));
		uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.MOD1, '-'), new ZoomAction(false, editor.getDtDesignViewer()));
	}
	
}
