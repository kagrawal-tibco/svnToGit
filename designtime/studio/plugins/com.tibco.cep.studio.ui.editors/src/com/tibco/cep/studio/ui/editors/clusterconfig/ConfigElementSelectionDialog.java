package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 15, 2009 5:15:26 PM
 */

public abstract class ConfigElementSelectionDialog extends Dialog  {

	protected Shell shell, dialog;
	protected Table table;
	protected Button bOK, bCancel;
	
	public ConfigElementSelectionDialog(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	public void initDialog(String title, final boolean multiSelect) {
		shell = getParent();
		dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | SWT.TITLE | SWT.BORDER);
		dialog.setText(title);
		dialog.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY_GROUP));
		dialog.setLayout(new GridLayout(1, false));
		
		table = new Table(dialog, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		table.setSize(300, 400);
		
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				bOK.setEnabled(false);
				
				for (TableItem item: table.getItems()) {
					if (item.getChecked()) {
						bOK.setEnabled(true);
						break;
					}
				}
				if (!multiSelect) {
					boolean isChecked = ((TableItem)(event.item)).getChecked();
					for (TableItem item: table.getItems()) {
						item.setChecked(false);
					}
					if (isChecked)
						((TableItem)(event.item)).setChecked(true);
				}
			}
		});
		
		table.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite butComp = new Composite(dialog, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		butComp.setLayoutData(gd);
		butComp.setLayout(new GridLayout(3, false));
		
		Label lFiller = PanelUiUtil.createLabelFiller(butComp);
		
		bOK = new Button(butComp, SWT.PUSH);
		bOK.setText("OK");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bOK.setLayoutData(gd);
		bOK.setEnabled(false);
		
		bCancel = new Button(butComp, SWT.PUSH);
		bCancel.setText("Cancel");
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.widthHint = 80;
		bCancel.setLayoutData(gd);
		butComp.pack();
	}

	public abstract void open(ArrayList<?> items, ArrayList<?> filter);
	
	public void openDialog() {
		openDialog("");
	}
	
	public void openDialog(String tooltip) {
		table.setToolTipText(tooltip);

		dialog.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.TRAVERSE_ESCAPE) {
					dialog.dispose();
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
				}
			}
		});
		
		bCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				dialog.dispose();
			}
		});
		dialog.pack();
		
		dialog.setSize(300, 400);
		Rectangle shellBounds = shell.getBounds();
        Point dialogSize = dialog.getSize();
        dialog.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2, shellBounds.y + (shellBounds.height - dialogSize.y) / 4);

        dialog.open();
        
		Display display = shell.getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
}
