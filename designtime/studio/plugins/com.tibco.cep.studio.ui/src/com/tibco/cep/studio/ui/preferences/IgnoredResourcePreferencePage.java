package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.TextProcessor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.util.IIgnoreInfo;
import com.tibco.cep.studio.ui.util.Messages;

public class IgnoredResourcePreferencePage 
			extends PreferencePage 
			implements IWorkbenchPreferencePage {
	
	private Table ignoreTable;
	private Button addButton;
	private Button removeButton;
    

	public IgnoredResourcePreferencePage() {
		// TODO Auto-generated constructor stub
	}

	public IgnoredResourcePreferencePage(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public IgnoredResourcePreferencePage(String title, ImageDescriptor image) {
		super(title, image);
		// TODO Auto-generated constructor stub
	}
	
	
    
    /**
     * Creates the page's UI content.
     */
	@Override
	protected Control createContents(Composite ancestor) {
		Composite parent = new Composite(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 2;
		parent.setLayout(layout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		parent.setLayoutData(data);
		
		Label l1 = new Label(parent, SWT.NULL);
		l1.setText(Messages.getString("pref.codegen.ignore.patterns")); 
		data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		data.horizontalSpan = 2;
		l1.setLayoutData(data);
		
		ignoreTable = new Table(parent, SWT.CHECK | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		//gd.widthHint = convertWidthInCharsToPixels(30);
		gd.heightHint = 300;
		ignoreTable.setLayoutData(gd);
		ignoreTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				handleSelection();
			}
		});
		
		Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));//$NON-NLS-1$
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		
		addButton = new Button(buttons, SWT.PUSH);
		addButton.setText(Messages.getString("pref.codegen.add.pattern")); //$NON-NLS-1$
		addButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				addIgnore();
			}
		});
		
		removeButton = new Button(buttons, SWT.PUSH);
		removeButton.setText(Messages.getString("pref.codegen.remove.pattern"));//$NON-NLS-1$ 
		removeButton.setEnabled(false);
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				removeIgnore();
			}
		});
		fillTable(StudioCore.getAllIgnores());
		Dialog.applyDialogFont(ancestor);
		setButtonLayoutData(addButton);
		setButtonLayoutData(removeButton);
        
        // set F1 help
//        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IHelpContextIds.IGNORE_PREFERENCE_PAGE);
        
		return parent;
	}

	@Override
	public void init(IWorkbench workbench) {
		setDescription(Messages.getString("pref.codegen.shared.resource.filter")); 

	}
	
	
	/**
	 * Do anything necessary because the OK button has been pressed.
	 *
	 * @return whether it is okay to close the preference page
	 */
	public boolean performOk() {
		int count = ignoreTable.getItemCount();
		String[] patterns = new String[count];
		boolean[] enabled = new boolean[count];
		TableItem[] items = ignoreTable.getItems();
		for (int i = 0; i < count; i++) {
			patterns[i] = items[i].getText();
			enabled[i] = items[i].getChecked();
		}
		StudioCore.setAllIgnores(patterns, enabled);
//		TeamUIPlugin.broadcastPropertyChange(new PropertyChangeEvent(this, TeamUI.GLOBAL_IGNORES_CHANGED, null, null));
		return true;
	}
	
	protected void performDefaults() {
		super.performDefaults();
		ignoreTable.removeAll();
		IIgnoreInfo[] ignore = StudioCore.getDefaultIgnores();
		fillTable(ignore);
	}
	
	/**
	 * @param ignore
	 */
	private void fillTable(IIgnoreInfo[] ignore) {
		for (int i = 0; i < ignore.length; i++) {
			IIgnoreInfo info = ignore[i];
			TableItem item = new TableItem(ignoreTable, SWT.NONE);
			item.setText(TextProcessor.process(info.getPattern(), ".*")); //$NON-NLS-1$
			item.setChecked(info.getEnabled());
		}		
	}

	private void addIgnore() {
		
		InputDialog dialog = new InputDialog(getShell(), 
				Messages.getString("pref.codegen.enter.pattern.short"), 
				Messages.getString("pref.codegen.enter.pattern.long"), null, null) {
			protected Control createDialogArea(Composite parent) {
				Control control = super.createDialogArea(parent);
//				PlatformUI.getWorkbench().getHelpSystem().setHelp(control, IHelpContextIds.IGNORE_PREFERENCE_PAGE);
				return control;
			}
		};

		dialog.open();
		if (dialog.getReturnCode() != Window.OK) return;
		String pattern = dialog.getValue();
		if (pattern.equals("")) return; //$NON-NLS-1$
		// Check if the item already exists
		TableItem[] items = ignoreTable.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText().equals(pattern)) {
				MessageDialog.openWarning(getShell(), 
						Messages.getString("pref.codegen.pattern.exists.short"), //$NON-NLS-1$ 
						Messages.getString("pref.codegen.pattern.exists.long")); //$NON-NLS-1$
				return;
			}
		}
		TableItem item = new TableItem(ignoreTable, SWT.NONE);
		item.setText(TextProcessor.process(pattern, ".*")); //$NON-NLS-1$
		item.setChecked(true);
	}
	
	private void removeIgnore() {
		int[] selection = ignoreTable.getSelectionIndices();
		ignoreTable.remove(selection);
	}
	private void handleSelection() {
		if (ignoreTable.getSelectionCount() > 0) {
			removeButton.setEnabled(true);
		} else {
			removeButton.setEnabled(false);
		}
	}

	

}
