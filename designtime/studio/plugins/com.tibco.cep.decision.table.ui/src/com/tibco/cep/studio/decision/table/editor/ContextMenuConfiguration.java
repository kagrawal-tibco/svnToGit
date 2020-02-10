package com.tibco.cep.studio.decision.table.editor;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.menu.PopupMenuBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ContextMenuConfiguration extends AbstractUiBindingConfiguration{

	private Menu bodyMenu;
	private final NatTable natTable;
	private IDecisionTableEditor editor;
	private IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	
    
	public ContextMenuConfiguration(NatTable natTable, IDecisionTableEditor editor) {
		this.natTable = natTable;
		this.editor = editor;
		createContextMenu();
	}

	private void createContextMenu() {
		this.bodyMenu = new Menu(window.getShell(), SWT.POP_UP);
		natTable.addDisposeListener(new DisposeListener() {
            
            @Override
			public void widgetDisposed(DisposeEvent e) {
				bodyMenu.dispose();
			}
    });
		new PopupMenuBuilder(natTable,bodyMenu).withAutoResizeSelectedColumnsMenuItem();
	}

	@Override
	public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
		uiBindingRegistry.registerMouseDownBinding(
                new MouseEventMatcher(SWT.NONE, GridRegion.BODY, 3),
                new CellSelectPopupMenuAction(bodyMenu, editor, natTable));
	}

}
