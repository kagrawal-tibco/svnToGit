package com.tibco.cep.studio.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogTray;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.wizards.GlobalVariablesTray;
import com.tibco.cep.studio.ui.wizards.export.JDBCDeployExportWizard;

/*
@author ssailapp
@date Feb 16, 2011
 */

public class GvUiUtil {

	public static final String ICON_GLOBAL_VAR_OFF = "icons/globalVarToggleOff.gif";
	public static final String ICON_GLOBAL_VAR_ON = "icons/globalVarToggleOn.gif";

	private IProject project;

	private TrayDialog trayDialog;

	private GlobalVariablesTray globalVariablesTray;

	public GvUiUtil(IProject project) {
		this.project = project;
	}

	public static GvField createTextGv(Composite parent, String value) {
		final GvField gvField = new GvField(parent, value, GvField.FIELD_TYPE_TEXT);
		return gvField;
	}
	
	public static GvField createTextGv(Composite parent, String value, Label label) {
		final GvField gvField = new GvField(parent, value, GvField.FIELD_TYPE_TEXT, label);
		return gvField;
	}

	public static GvField createPasswordGv(Composite parent, String value) {
		final GvField gvField = new GvField(parent, value, GvField.FIELD_TYPE_PASSWORD);
		return gvField;
	}

	public static GvField createCheckBoxGv(Composite parent, String value) {
		final GvField gvField = new GvField(parent, value, GvField.FIELD_TYPE_CHECKBOX);
		return gvField;
	}

	public static GvField createComboGv(Composite parent, String value) {
		final GvField gvField = new GvField(parent, value, GvField.FIELD_TYPE_COMBO);
		return gvField;
	}

	public static Image getGvImage(String imageLocation) {
		return StudioUIPlugin.getDefault().getImage(imageLocation);
	}

	public void addGvButton(Composite parentComposite) {
		Composite gvComp = new Composite(parentComposite, SWT.NONE);
		gvComp.setLayout(new GridLayout(1, false));
		GridData gd = new GridData(SWT.END, SWT.CENTER, true, false);
		gvComp.setLayoutData(gd);
		Button bGv = PanelUiUtil.createImageButton(gvComp, StudioUIPlugin.getDefault().getImage("icons/global_var.png"), 16, SWT.RIGHT);
		bGv.addListener(SWT.Selection, getGlobalVariablePopoutTray());
		gvComp.pack();
	}

	public ToolItem createGvButton(ToolBar parent) {
		ToolItem gvButton = new ToolItem(parent, SWT.CHECK);
		gvButton.setImage(StudioUIPlugin.getDefault().getImage("icons/global_var.png"));
		gvButton.setToolTipText("Show Global Variable Help"); //$NON-NLS-1$
		gvButton.addListener(SWT.Selection, getGlobalVariablePopoutTray());
		return gvButton;
	}

	private Listener getGlobalVariablePopoutTray() {
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				final Point point = computePopUpLocation(event.widget.getDisplay());
				Shell activeShell = getActiveShell();
				if (GlobalVariablesTray.isAppropriateFor(activeShell)) {
					displayView(activeShell,point.x, point.y);
					return;
				}
			}
		};
		return listener;
	}

	/**
	 * Determines the location for the help popup shell given the widget which
	 * orginated the request for help.
	 *
	 * @param display
	 *            the display where the help will appear
	 */

	private Point computePopUpLocation(Display display) {
		Point point = display.getCursorLocation();
		return new Point(point.x + 15, point.y);
	}

	private Shell getActiveShell() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		return display.getActiveShell();
	}

	private void displayView(Shell activeShell,int x, int y) {
		Control controlInFocus = activeShell.getDisplay().getFocusControl();
		trayDialog = (TrayDialog)activeShell.getData();
		DialogTray tray = trayDialog.getTray();
		if (tray == null) {
			globalVariablesTray = new GlobalVariablesTray("com.tibco.cep.studio.ui.GlobalVariableTrayView", project); //$NON-NLS-1$
			trayDialog.openTray(globalVariablesTray);
		} else { // some else is occupying the tray area
			trayDialog.closeTray();
			tray = null;
			trayDialog.getShell().setFocus();
		}
	}

	private void initialize() {
		//getControl().addKeyListener(getKeyListener());
	}

	private KeyListener getKeyListener() {
		KeyListener listener = new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				logKeyCode(e);
				if(e.keyCode == 103 && ((e.stateMask & SWT.CTRL) != 0)) {
					final Point point = computePopUpLocation(e.widget.getDisplay());
					// check the dialog
					Shell activeShell = getActiveShell();
					if (GlobalVariablesTray.isAppropriateFor(activeShell)) {
						displayView(activeShell,point.x, point.y);
						return;
					}
				}
			}
		};
		return listener;
	}

	private void logKeyCode(KeyEvent e) {
		String string = "";

		//check click together?
		if ((e.stateMask & SWT.ALT) != 0) string += "ALT - keyCode = " + e.keyCode;
		if ((e.stateMask & SWT.CTRL) != 0) string += "CTRL - keyCode = " + e.keyCode;
		if ((e.stateMask & SWT.SHIFT) != 0) string += "SHIFT - keyCode = " + e.keyCode;

		if(e.keyCode == SWT.BS)
		{
			string += "BACKSPACE - keyCode = " + e.keyCode;
		}

		if(e.keyCode == SWT.ESC)
		{
			string += "ESCAPE - keyCode = " + e.keyCode;
		}

		//check characters
		if(e.keyCode >=97 && e.keyCode <=122)
		{
			string += " " + e.character + " - keyCode = " + e.keyCode;
		}

		//check digit
		if(e.keyCode >=48 && e.keyCode <=57)
		{
			string += " " + e.character + " - keyCode = " + e.keyCode;
		}

		if(!string.equals(""))
			System.out.println (string);
	}

	/**
	 * @param wizardContainer
	 * @param wizard
	 * @return
	 */
	public ToolItem addWizardGVToolItem(IWizardContainer wizardContainer, JDBCDeployExportWizard wizard) {

		ToolBar buttonToolbar= wizard.getButtonToolbar();
		if (buttonToolbar == null) {
			buttonToolbar = getButtonToolBar(wizardContainer);
		}
		if(buttonToolbar != null) {
			((GridLayout) buttonToolbar.getParent().getLayout()).numColumns++;
			buttonToolbar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
			ToolItem item = createGvButton(buttonToolbar);
			buttonToolbar.layout();
			WizardDialog wdialog = (WizardDialog) wizardContainer;
			if (!wizard.isSizeChanged()) {
				int X = wdialog.getShell().getSize().x;
				int Y =  wdialog.getShell().getSize().y;
				Point size = wdialog.getShell().computeSize( X , Y + 1);
				wdialog.getShell().setSize( size );
				wizard.setSizeChanged(true);
			}
			return item;

		}
		return null;
	}

	/**
	 * @param wizardContainer
	 * @return
	 */
	private ToolBar getButtonToolBar (IWizardContainer wizardContainer) {
		if(wizardContainer instanceof Dialog) {
			Dialog dialog = (Dialog) wizardContainer;
			Control buttonBar = dialog.buttonBar;

			if(buttonBar instanceof Composite) {
				Control[] children = ((Composite)buttonBar).getChildren();
				for(Control child:children){
					if(child instanceof ToolBar) {
						return (ToolBar) child;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param wizardContainer
	 * @param item
	 * @param wizard
	 * @return
	 */
	public ToolItem removeWizardGVToolItem(IWizardContainer wizardContainer, ToolItem item, JDBCDeployExportWizard wizard) {
		ToolBar buttonToolbar= wizard.getButtonToolbar();
		if (buttonToolbar == null) {
			buttonToolbar = getButtonToolBar(wizardContainer);
		}
		if(buttonToolbar != null) {
			((GridLayout) buttonToolbar.getParent().getLayout()).numColumns--;
			buttonToolbar.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

			item.dispose();

			buttonToolbar.layout();

			if (trayDialog != null && globalVariablesTray != null) {
				trayDialog.closeTray();
			}
			return item;
		}

		return null;
	}
}
