package com.tibco.cep.studio.ui.actions;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.core.doc.DocumentationDescriptor;
import com.tibco.cep.studio.ui.util.PanelUiUtil;

/*
@author ssailapp
@date Dec 8, 2011
 */
@SuppressWarnings("unused")
public class GenerateDocumentationDialog extends Dialog {

	private Label lProject, lLocation;
	private Text tLocation, tProject;
	private Button bLocationBrowse, bOk, bCancel; 
	private DocumentationDescriptor docDesc;
	private Shell dialog;

	protected GenerateDocumentationDialog(Shell parentShell, IProject project) {
		super(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText("Generate Documentation");
		docDesc = new DocumentationDescriptor();
		docDesc.project = project;
		docDesc.location = project.getLocation().toString() + "/doc";
	}

	public DocumentationDescriptor open() {
		dialog = new Shell(getParent(), getStyle());
		dialog.setText(getText());
		dialog.setSize(150, 150);
		createContents(dialog);
		Point size = dialog.computeSize(-1, -1);
		Point dialogLoc = getBounds(size);
		dialog.setBounds(dialogLoc.x, dialogLoc.y, size.x, size.y);
		dialog.pack();
		dialog.open();
		Display display = getParent().getDisplay();
		while (!dialog.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return docDesc;
	}

	public void createContents(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));

		lProject = PanelUiUtil.createLabel(comp, "Project: ");
		tProject = PanelUiUtil.createText(comp);
		tProject.setText(docDesc.project.getName());
		tProject.setEditable(false);
		tProject.setEnabled(false);

		lLocation = PanelUiUtil.createLabel(comp, "Documentation Destination: ");
		Composite locComp = new Composite(comp, SWT.NONE);
		locComp.setLayout(PanelUiUtil.getCompactWidthGridLayout(2, false));
		tLocation = PanelUiUtil.createText(locComp, 250);
		tLocation.addListener(SWT.Modify, getLocationModifyListener());
		tLocation.setText(docDesc.location);		
		bLocationBrowse = PanelUiUtil.createBrowsePushButton(locComp, tLocation);
		bLocationBrowse.addListener(SWT.Selection, PanelUiUtil.getFolderBrowseListener(null, comp, tLocation));
		locComp.pack();
		locComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		PanelUiUtil.createLabelFiller(comp, 2);

		PanelUiUtil.createLabelFiller(comp);
		Composite buttonComp = new Composite(comp, SWT.NONE);
		buttonComp.setLayout(new GridLayout(3, false));
		PanelUiUtil.createLabelFiller(buttonComp);
		bOk = PanelUiUtil.createPushButton(buttonComp, "OK", 50);
		bOk.addListener(SWT.Selection, getOkListener());
		bCancel = PanelUiUtil.createPushButton(buttonComp, "Cancel", 50);
		bCancel.addListener(SWT.Selection, getCancelListener());
		buttonComp.pack();
		buttonComp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true));

		comp.pack();
		parent.pack();
	}

	private Listener getLocationModifyListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				docDesc.location = tLocation.getText();
			}
		};
		return listener;
	}

	private Listener getOkListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				String location = tLocation.getText();
				File docDir = new File(location);
				if (docDir.exists()) {
					if (!docDir.isDirectory()) {
						PanelUiUtil.showErrorMessage("Documentation Destination is not a valid directory.");
						return;
					}
					if (docDir.exists() && docDir.list().length > 0) {
						boolean status = MessageDialog.openQuestion(dialog, "Generate Documentation",
								"Documentation Destination is not empty. Documentation generation will cause all contents to be lost. Continue ?");
						if (!status) {
							return;
						}
					}
				}
				dialog.dispose();
			}
		};
		return listener;
	}

	private Listener getCancelListener() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				docDesc = null;
				dialog.dispose();
			}
		};
		return listener;
	}

	public Point getCenterPoint() {
		Shell parentShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		Rectangle shellBounds = parentShell.getBounds();
		return new Point(shellBounds.x + shellBounds.width / 2, (shellBounds.y + shellBounds.height) / 2);
	}

	protected Point getBounds(Point initialSize) {
		Point shellCenter = getCenterPoint();
		return new Point(shellCenter.x - initialSize.x / 2, shellCenter.y - initialSize.y / 2);
	}

}
