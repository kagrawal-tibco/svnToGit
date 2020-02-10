package com.tibco.cep.studio.ui.editors.wizards;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.persistExtendedProperties;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.forms.extendedPropTreeViewer.ExtendedPropTreeViewer;

/**
 * 
 * @author sasahoo
 *
 */
public class NewMetaDataDialog extends Dialog{

	protected String title; 
	protected PropertyDefinition propertyDefinition;
//	protected ExtendedPropertiesPanel exPanel;
	protected ExtendedPropTreeViewer extPropTreeviewer;
	private AbstractSaveableEntityEditorPart editor;
	
	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param editor
	 * @param propertyDefinition
	 */
	public NewMetaDataDialog(Shell parentShell, 
			                    String dialogTitle, 
			                    AbstractSaveableEntityEditorPart editor, 
                                PropertyDefinition propertyDefinition) {
		super(parentShell);
		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.editor = editor;
		this.propertyDefinition = propertyDefinition;
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
		if(!editor.isEnabled()){
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
		// Apply to the parent so that the message gets it too.
		applyDialogFont(parent);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		extPropTreeviewer= new ExtendedPropTreeViewer(editor){
			@Override
			public List<Object> getInputFromModel(){
				
				if(propertyDefinition.getExtendedProperties()==null)
					propertyDefinition.setExtendedProperties(ModelFactory.eINSTANCE.createPropertyMap());
				EditorUtils.getExtendedProperties(propertyDefinition.getExtendedProperties(),getMap());
				prList=getChldElementList(getMap(),null);
				return prList;
			}
			
			@Override
		    protected void setDirty(boolean tf) {
		    	isDirty = tf;
		    	if(tf){
		    		if(editor != null){
		    			persistExtendedProperties(propertyDefinition, getExtendedPropertiesMap());
		    		}
		    	}
		    }
			
		};
		extPropTreeviewer.createHierarchicalTree(composite);
	
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(100);
		data.heightHint = 200;
		data.grabExcessHorizontalSpace=true;
		data.grabExcessVerticalSpace=true;
		composite.setLayoutData(data);
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.ui.wizards.RMSArtifactsUpdateDialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if(isDirty()){
			persistExtendedProperties(propertyDefinition, getExtendedPropertiesMap());
			editor.modified();
		}
		super.okPressed();
	}
	
	/**
	 * @return
	 */
	public Map getExtendedPropertiesMap(){
		
		return extPropTreeviewer.getMap();
	}
	/*
	 * to be moved to other file
	 */
	
	
	/**
	 * @return
	 */
	public boolean isDirty(){
		return extPropTreeviewer.isDirty();
	}
	
	/**
	 * Get the number of columns in the layout of the Shell of the dialog.
	 */
	int getColumnCount() {
		return 1;
	}
}