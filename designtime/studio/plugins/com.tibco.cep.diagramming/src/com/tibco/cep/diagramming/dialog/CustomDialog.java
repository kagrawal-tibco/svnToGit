package com.tibco.cep.diagramming.dialog;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.diagramming.utils.ICommandIds;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tomsawyer.application.swing.export.TSEPrintPreviewWindow;
import com.tomsawyer.application.swing.export.TSEPrintSetupDialog;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

/**
 * 
 * @author ggrigore
 *
 */
public class CustomDialog extends Dialog {

	private Composite dialogAreaComposite;
	private TSSwingCanvas swingCanvas;
	private String action;
	
	public CustomDialog(Shell parentShell, TSSwingCanvas swingCanvas, String action) {
		super(parentShell);
		this.swingCanvas = swingCanvas;
		this.action = action;
		parentShell.setText("TIBCO BusinessEvents");
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("TIBCO BusinessEvents");
	}

	protected Control createDialogArea(Composite container) {

		Composite parent = (Composite) super.createDialogArea(container);
		dialogAreaComposite = new Composite(parent, SWT.EMBEDDED);
		dialogAreaComposite.setBackground(Display.getDefault().getSystemColor(1));
		GridLayout elayout = new GridLayout();
		elayout.numColumns = 1;
		elayout.makeColumnsEqualWidth = false;
		dialogAreaComposite.setLayout(elayout);

		java.awt.Frame frame = SWT_AWT.new_Frame(dialogAreaComposite);
		new SyncXErrorHandler().installHandler();
		
		if (action.equals(ICommandIds.CMD_PAGE_SETUP)) {
			TSEPrintSetupDialog printSetupDialog = new TSEPrintSetupDialog(
				frame, "Print Setup", swingCanvas);
			//printSetupDialog.setSize(600, 400);
			printSetupDialog.setModal(false);
			printSetupDialog.setVisible(true);
		}

		else if (action.equals(ICommandIds.CMD_PRINT_PREVIEW)){
			JDialog printPreviewWindow = new TSEPrintPreviewWindow(frame,
					"Print Preview", swingCanvas);
			printPreviewWindow.setSize(600, 400);
			printPreviewWindow.setModal(false);
			printPreviewWindow.setVisible(true);
		}

		else if (action.equals("com.tibco.cep.diagramming.saveasimage")) {
			SaveAsImageDialog saveAsImageDialog = new SaveAsImageDialog(frame,
					"Export Drawing To Image", swingCanvas);
			saveAsImageDialog.setSize(550, 500);
			saveAsImageDialog.setModal(false);
			saveAsImageDialog.setVisible(true);
		}

		applyDialogFont(parent);
		return parent;

	}

	static void addTab(JTabbedPane tabbedPane, String text) {
		JLabel label = new JLabel(text);
		JButton button = new JButton(text);
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(button);
		tabbedPane.addTab(text, panel);
	}


	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.dialogAreaComposite.setFocus();
	}
	

}