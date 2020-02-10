package com.tibco.cep.studio.tester.ui.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.swing.JTable;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.xml.datamodel.XiParserFactory;

/**
 * 
 * @author sasahoo
 *
 */
public class PayloadEntryDialog extends Dialog implements SelectionListener, ModifyListener {

	protected String title; 
	private Text payloadFilePathText;
	private Text payloadStringText;
	private Button browseButton;
	private int row;
	private int col;
	private JTable table;
	private String value;
	
	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param table
	 * @param row
	 * @param column
	 */
	public PayloadEntryDialog(Shell parentShell, 
			                  String dialogTitle, 
			                  JTable table, 
			                  String value,
			                  int row, 
			                  int column) {
		super(parentShell);
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle()/* | SWT.RESIZE*/);
		this.row = row;
		this.col = column;
		this.table = table;
		this.value = value;
	}
	
	/*
	 * (non-Javadoc) Method declared in Window.
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}
	
	/*
	 * @see Dialog.createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// initialize the dialog units
		initializeDialogUnits(parent);
		Point defaultSpacing = LayoutConstants.getSpacing();
		GridLayoutFactory.fillDefaults().margins(LayoutConstants.getMargins())
				.spacing(defaultSpacing.x * 2,
				defaultSpacing.y).numColumns(getColumnCount()).applyTo(parent);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);
		createDialogAndButtonArea(parent);
		return parent;
	}
	
	/*
	 * @see IconAndMessageDialog#createDialogAndButtonArea(Composite)
	 */
	protected void createDialogAndButtonArea(Composite parent) {
		// create the dialog area and button bar
		dialogArea = createDialogArea(parent);
		buttonBar = createButtonBar(parent);
		
		if (value != null && !value.isEmpty()) {
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
		
		// Apply to the parent so that the message gets it too.
		applyDialogFont(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(80);
		data.heightHint = 200;
		group.setLayoutData(data);
		
		createLabel(group, "Select Payload");
		
		Composite childContainer = new Composite(group, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		childContainer.setLayout(layout);
		childContainer.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		payloadFilePathText = createText(childContainer);
		payloadFilePathText.setLayoutData( new GridData(GridData.FILL_HORIZONTAL));
		
		browseButton = new Button(childContainer, SWT.NONE);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.addSelectionListener(this);
		
		createLabel(group, "Payload String");
		
		payloadStringText = new Text(group, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
//		gd.heightHint = 100;
		payloadStringText.setLayoutData(gd);
		payloadStringText.setText(value == null ? "" : value);
		payloadStringText.addModifyListener(this);
		
		return null;
	}
	

	/**
	 * @param container
	 * @param label
	 * @return
	 */
	protected Label createLabel(Composite container, String label) {
		return createLabel(container, label, 0);
	}
	
	/**
	 * @param container
	 * @param labelstr
	 * @param indent
	 * @return
	 */
	protected Label createLabel(Composite container, String labelstr, int indent) {
		Label label = new Label(container, SWT.NONE);
		GridData gData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gData.horizontalIndent = indent;
		label.setLayoutData(gData);
		label.setText(labelstr);
		return label;
	}
	
	/**
	 * @param container
	 * @return
	 */
	protected Text createText(Composite container) {
		Text text = new Text(container, SWT.BORDER);
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.widthHint = 150;
		text.setLayoutData(gData);
		return text;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		try {
			XiParserFactory.newInstance().parse(new InputSource(new StringReader(payloadStringText.getText())));
		} catch (SAXException e) {
			MessageDialog.openError(getShell(), "Payload Error", "Invalid payload entry");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		table.setValueAt(payloadStringText.getText(), row, col);
		super.okPressed();
	}
	
	/**
	 * Get the number of columns in the layout of the Shell of the dialog.
	 */
	private int getColumnCount() {
		return 1;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == browseButton) {
			FileDialog fd = new FileDialog(getShell());
			fd.setText("Open");
			//		        fd.setFilterPath("C:/");
			String[] filterExt = { "*.*" };
			fd.setFilterExtensions(filterExt);
			String selected = fd.open();
			if (selected != null) {
				payloadFilePathText.setText(selected);
				try {
					String content = readFile(selected);
					payloadStringText.setText(content);
				} catch (IOException e1) {
					StudioUIPlugin.debug(this.getClass().getName(), e1.getMessage(), e1);
				}
			}
		}
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	private static String readFile(String filename) throws IOException {
//		   String lineSep = System.getProperty("line.separator");
		   BufferedReader br = new BufferedReader(new FileReader(filename));
		   String nextLine = "";
		   StringBuffer sb = new StringBuffer();
		   while ((nextLine = br.readLine()) != null) {
		     sb.append(nextLine.trim());
//		     sb.append(lineSep);
		   }
		   return sb.toString();
		}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		if (e.getSource() == payloadStringText) {
			if (payloadStringText.getText().trim().length() > 0 ) {
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			} else {
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			}
		}
		
	}


}