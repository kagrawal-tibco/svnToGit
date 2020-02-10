package com.tibco.cep.studio.tester.ui.tools;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.tester.core.provider.TesterEngine;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

/**
 * 
 * @author sasahoo
 *
 */
public class ShutdownSessionDialog extends Dialog implements SelectionListener{

	protected String title; 
	protected Table sessionsTable;
    protected int fWidth = 60;
    private Button select;
    protected Set<TableItem> checkedSessions =  new HashSet<TableItem>();
    protected Button cancelButton;
	protected Button okButton;
	
	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param editor
	 * @param propertyDefinition
	 */
	public ShutdownSessionDialog(Shell parentShell, 
			               String dialogTitle) {
		super(parentShell);
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle() | SWT.RESIZE);
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
		dialogArea = createDialogArea(parent);
		buttonBar = createButtonBar(parent);
		applyDialogFont(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		createTableArea(parent);
		return null;
	}
	
	protected Control createSelectButton(Composite parent) {
		select = new Button(parent, SWT.CHECK);
		select.setText("Select / deselect all");
		select.setSelection(false);
		select.addSelectionListener(this);
		return parent;
	}
	
	protected Control createTableArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(parent);
		sessionsTable = new Table(parent, SWT.CHECK | SWT.BORDER | SWT.SINGLE);
		sessionsTable.setLayout(new GridLayout());
		sessionsTable.addSelectionListener(this);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = convertWidthInCharsToPixels(fWidth);
		data.heightHint = 138;
		sessionsTable.setLayoutData(data);
		sessionsTable.setLinesVisible(true);
		sessionsTable.setHeaderVisible(true);
		addColumns();
		createSelectButton(parent);
		return parent;
	}
	
	protected void addColumns() {
		TableColumn resPathColumn = new TableColumn(sessionsTable, SWT.NULL);
		resPathColumn.setText("Running Sessions");
		autoTableLayout();
		for(String session : TesterEngine.INSTANCE.getRunningSessions()){
			createSessionItem(session);
		}
	}
	
	protected void autoTableLayout() {
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(sessionsTable);
		for (@SuppressWarnings("unused") TableColumn column : sessionsTable.getColumns()) {
			autoTableLayout.addColumnData(new ColumnWeightData(1));
		}
		sessionsTable.setLayout(autoTableLayout);
	}
	
	/**
	 * @param session
	 */
	protected void createSessionItem(String session) {
		TableItem item = new TableItem(sessionsTable, SWT.CENTER | SWT.CHECK);
		item.setImage(StudioTesterUIPlugin.getDefault().getImage("icons/launch_rule.gif"));
		item.setText(0, session);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
//		for(TableItem item : checkedSessions){
//			try {
//				TesterEngine.INSTANCE.stopEngine(item.getText());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		super.okPressed();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		cancelButton = createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true);
	}
	
	/**
	 * Get the number of columns in the layout of the Shell of the dialog.
	 */
	private int getColumnCount() {
		return 1;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == select) {
			for (TableItem item : sessionsTable.getItems()) {
				item.setChecked(select.getSelection());
				if(select.getSelection()){
					checkedSessions.add(item);
				} else if (!(checkedSessions.add(item))){
					checkedSessions.remove(item);
				}
			}
		}
		if (e.item instanceof TableItem) {
			TableItem item = (TableItem)e.item;
			if (item.getChecked()) {
				if (!contains(checkedSessions, item)) {
					checkedSessions.add(item);
				}
			} else {
				if (contains(checkedSessions, item)) {
					checkedSessions.remove(item);
				}
			}
		}
		if (checkedSessions.size() == 0) {
			okButton.setEnabled(false);
		} else if (checkedSessions.size() > 0) {
			okButton.setEnabled(true);
		}
	}
	
	/**
	 * @param checkedItems
	 * @param itemToSearch
	 * @return
	 */
	protected boolean contains(Set<TableItem> checkedItems, TableItem itemToSearch) {
		for (TableItem checkedItem : checkedItems) {
			if (checkedItem.getText(0).equals(itemToSearch.getText(0))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}