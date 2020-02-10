package com.tibco.cep.studio.tester.utilities;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.ui.editors.utils.Messages;



/**
 * @author aasingh
 *
 */
public class DateTimeDialog extends Dialog implements SelectionListener, ModifyListener {
	Shell shell;
	protected Text payloadStringText;
	TableViewer tableViewer;
	private String currentDateTime;
	String [] domainItems = null;
	Combo domainCombo;

	public DateTimeDialog(Shell shell, ArrayList domainEntries){
		super(shell);
		this.shell = shell;

		if (domainEntries != null) {
			if (!domainEntries.isEmpty()) {
				domainItems = new String[domainEntries.size()];
				for (int i = 0; i < domainEntries.size(); i++) {
					domainItems[i] = (String) domainEntries.get(i);
				}
			}
		}

	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(250, 475);
		shell.setText("Calendar");
		shell.setLocation(750, 300);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
	
	/*
	 * @see Dialog.createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// initialize the dialog units
		initializeDialogUnits(parent);
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
		applyDialogFont(parent);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */

	protected Control createDialogArea(Composite parent) {

		GridLayout gridLayout = new GridLayout(1,false);
		GridData datap = new GridData(GridData.FILL_BOTH);
		datap.heightHint = 290;
		datap.widthHint = 390;
		parent.setLayout(gridLayout);
		parent.setLayoutData(datap);

		final Composite dialog = new Composite(parent,0);
		GridLayout tempGridLayout = new GridLayout(2,false);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 290;
		data.widthHint = 390;
		dialog.setLayout(tempGridLayout);
		dialog.setLayoutData (data);
		final DateTime calendar = new DateTime (dialog, SWT.CALENDAR | SWT.BORDER);

		domainCombo = new Combo(dialog, SWT.BORDER | SWT.READ_ONLY);
		if(domainItems != null){
			domainCombo.setItems(domainItems); 
			domainCombo.setText(domainItems[0].toString());
		}
		domainCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Object obj = e.getSource() ;
				if ( obj == domainCombo ) {
					Combo tempCombo = ( Combo )obj ;
					System.out.println(tempCombo.getText());
				}

			}
		});
		domainCombo.setEnabled(false);

		final Composite timeDialog = new Composite(dialog,0);
		GridLayout timeGridLayout = new GridLayout(1,false);
		GridData timeGridData = new GridData(GridData.FILL_BOTH);
		timeDialog.setLayout(timeGridLayout);
		timeDialog.setLayoutData(timeGridData);
		final DateTime time = new DateTime ( timeDialog , SWT.TIME | SWT.BORDER);

		final Composite checkBoxdialog = new Composite(dialog,0);
		checkBoxdialog.setLayout(tempGridLayout);
		GridData data1 = new GridData(GridData.FILL_HORIZONTAL);
		data1.horizontalAlignment=SWT.LEFT;
		checkBoxdialog.setLayoutData(data1);

		Button calendarButton = new Button(dialog , SWT.RADIO );
		calendarButton.setText( "Calendar" ) ;
		Button domainButton = new Button(dialog , SWT.RADIO );
		domainButton.setText( "Domain Values" ) ;
		calendarButton.setFocus();

		final Composite buttonbar = new Composite(dialog,0);
		buttonbar.setLayout(tempGridLayout);
		data1.horizontalAlignment=SWT.LEFT;
		buttonbar.setLayoutData(data1);

		Button ok = new Button (buttonbar, SWT.PUSH);
		ok.setText ("OK");
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				if( domainCombo.isEnabled()){
					currentDateTime = domainCombo.getText().toString();
				}
				else{
					
					int MONTH=calendar.getMonth()+1;
					String month=(MONTH+"").length()==1?"0"+MONTH:MONTH+"";
					String day=(calendar.getDay()+"").length()==1?"0"+calendar.getDay():calendar.getDay()+"";
					String hours=(time.getHours()+"").length()==1?"0"+time.getHours():time.getHours()+"";
					String minutes=(time.getMinutes()+"").length()==1?"0"+time.getMinutes():time.getMinutes()+"";
					String seconds=(time.getSeconds()+"").length()==1?"0"+time.getSeconds():time.getSeconds()+"";
					
					currentDateTime = calendar.getYear() + "-" + (month) + "-" + day + "T" + hours+ ":" + minutes + ":" + seconds;
				}

				dialog.getShell().close();
			}
		});
		Button cancel = new Button (buttonbar, SWT.PUSH);
		cancel.setText ("Cancel");
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected (SelectionEvent e) {
				dialog.getShell().close();
			}
		});
		buttonbar.getShell().setDefaultButton (ok);
		buttonbar.pack();
		dialog.pack();

		calendarButton.addMouseListener(new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent e) {
				calendar.setEnabled(true);
				time.setEnabled(true);
				domainCombo.setEnabled(false);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			}

		});
		domainButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (domainItems != null){
					domainCombo.setEnabled(true);
					calendar.setEnabled(false);
					time.setEnabled(false);
				}
				else{
					MessageDialog.openInformation(dialog.getShell(), Messages.getString( "dateTimeDialog.DomainError.Title" ) ,  Messages.getString( "dateTimeDialog.DomainError.Message" ) ) ;
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		buttonbar.getShell().pack();
		buttonbar.getShell().open();
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		super.okPressed();
	}


	@Override
	public void modifyText(ModifyEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == payloadStringText) {
			if (payloadStringText.getText().trim().length() > 0 ) {
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			} else {
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			}
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}

	public String ReturnCurrentDate(){
		return currentDateTime;
	}

}