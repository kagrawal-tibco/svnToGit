package com.tibco.cep.studio.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.util.ToolBarProvider;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractConceptFormViewer extends AbstractEntityFormViewer{
	
	protected Concept concept;
	protected ToolItem removeSMAssociationButton;
	
	protected void createPropertiesColumnList() {
		columnNames.add("Name");
		columnNames.add("Type");
		columnNames.add(Messages.getString("concept.property.Multiple"));
		columnNames.add(Messages.getString("concept.property.Policy"));
		columnNames.add(Messages.getString("concept.property.History"));
		columnNames.add(Messages.getString("concept.property.Domain"));
	}

	@Override
	public void dispose() {
		super.dispose();
	}
	
	/**
	 * @param removeRowAction
	 * @return
	 */
	protected ToolBar createAssociationToolbar(Composite parent) {

		ToolBar toolBar = new ToolBar(parent, SWT.HORIZONTAL | SWT.RIGHT | SWT.FLAT);
		toolBar.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		toolBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		removeSMAssociationButton = new ToolItem(toolBar, SWT.PUSH);
		Image delImg = StudioUIPlugin.getDefault().getImage(ToolBarProvider.ICON_TOOLBAR_LIST_DELETE);
		removeSMAssociationButton.setImage(delImg);
		removeSMAssociationButton.setToolTipText("Delete");
		removeSMAssociationButton.setText("Delete");
		removeSMAssociationButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				removeAsociation();
			}
		});
		toolBar.pack();
		return toolBar;

	}
	
	protected abstract void removeAsociation();
	
	public ToolItem getRemoveSMAssociationButton() {
		return removeSMAssociationButton;
	}
}
