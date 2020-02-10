package com.tibco.cep.studio.ui.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.widgets.DomainResourceSelector;

public class RuleTemplateTextAndDialogCellEditor extends DialogCellEditor {
	

	public static String ICON_TOOLBAR_LIST_BROWSE = "/icons/browse_file_system.gif";
	protected Button result;
	private  String completePath;
	
	protected Label textField;
	protected String dialogMessage;
	protected String dialogTitle;
	protected Composite parent;
	protected String columnName;
	protected AbstractSaveableEntityEditorPart editor;
	protected IWorkbenchPage page;
	
	public RuleTemplateTextAndDialogCellEditor(Composite comp,String columnName){
		super(comp);
		this.parent=comp;
		this.columnName=columnName;
	}
	
	  public Control createControl(Composite parent) {
		  return super.createControl(parent) ;
	  }
	  
	protected Button createButton(Composite parent) {
        result = new Button(parent, SWT.DOWN);
        result.setImage(StudioUIPlugin.getDefault().getImage(ICON_TOOLBAR_LIST_BROWSE));
        //result.setText("..."); //$NON-NLS-1$
        return result;
    }
    
	public Button getButton(){
		return this.result;
	}
	
	public void setDialogMessage(String dialogMessage) {
		this.dialogMessage = dialogMessage;
	}


	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	protected Control createContents(Composite cell) {
		textField = new Label(cell, SWT.LEFT);
		textField.setFont(cell.getFont());
		textField.setBackground(cell.getBackground());
	   	textField.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent event) {
					 setValueToModel();
				}
			});
		textField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent event) {
					keyReleaseOccured(event);
				}
			});
		return textField;
	}
    
	protected void keyReleaseOccured(KeyEvent keyEvent) {
		if (keyEvent.keyCode == SWT.CR || keyEvent.keyCode == SWT.KEYPAD_CR) { // Enter key
			setValueToModel();
		}
		super.keyReleaseOccured(keyEvent);
	}

	protected void setValueToModel() {
	 	String newValue = textField.getText();
	    boolean newValidState = isCorrect(newValue);
        if (newValidState) {
            markDirty();
            doSetValue(newValue);
        } else {
            // try to insert the current value into the error message.
            setErrorMessage(MessageFormat.format(getErrorMessage(), new Object[] { newValue.toString() }));
        }
	
	
	}
	
	protected void updateContents(Object value) {
		if (textField == null) {
			return;
		}
		if ( value == null || value.toString().isEmpty() ){
			textField.setText("");
			return ;
		}
		
        String text = "";
        if (value != null) {
			text = value.toString();
		}
        textField.setText(text);
	}
	
	protected void doSetFocus() {
		// Overridden to set focus to the Text widget instead of the Button.
		textField.setFocus();
	}
	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		
		Table table=(Table)cellEditorWindow.getParent();
		TableItem[] selectedProperty=table.getSelection();selectedProperty[0].getText();
		Binding prop = (Binding)selectedProperty[0].getData();
		if(columnName.equals("Domain Model") || columnName.equals("domain")){
			try{
					String val= prop.getType().toString();
					return setValuesFromDomainResourcesPicker(prop, val);
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		return null;
	}

	private String setValuesFromDomainResourcesPicker(Binding prop , String oldValue){
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    	editor=(AbstractSaveableEntityEditorPart) page.getActivePart();
	    runDomainResourceSelector(prop, page, editor.getProject().getName(), true, true, oldValue);
		String domainValues = completePath;
		return domainValues;
	}
	
	protected String getDialogInitialValue() {
		Object value = getValue();
		if (value == null) {
			return null;
		} else {
			return value.toString();
		}
	}
	
	
	public  boolean runDomainResourceSelector(Binding binding, IWorkbenchPage page,
			String projectName, boolean invokeFromEditor, boolean editorOpen, String type) {
		EList  eList =  new BasicEList();
		List<IFile> existingResourcesList= new ArrayList<IFile>();
		Entity entity = IndexUtils.getEntity(projectName, 
				binding.getDomainModelPath(), ELEMENT_TYPES.DOMAIN);
		if ( entity instanceof Domain ) {
			Domain domain = (Domain) entity ;
			IFile file = IndexUtils.getFile(projectName, domain);
			if (file != null && !file.exists()) {
			IFile fileLoc = IndexUtils.getLinkedResource(projectName,
					binding.getDomainModelPath());//.getResourcePath());
			if (fileLoc != null && fileLoc.exists())
				file = fileLoc;
		}
			existingResourcesList.add(file);
			eList.add( domain);
		}

		try {
			DomainResourceSelector picker = new DomainResourceSelector(page.getWorkbenchWindow().getShell(),
					projectName,eList,PROPERTY_TYPES.getByName(type),existingResourcesList,true);
			if (picker.open() == Dialog.OK) {
				if (picker.getFirstResult() != null) {
					Set<Object> domainObjects = (Set<Object>) picker.getFirstResult();
					if (domainObjects.size() == 0) {
						completePath = "";
					}
			
					for (Object dm : domainObjects) {
						String fullPath = null;
						if (dm instanceof IFile) {
							IFile domainFile = (IFile) dm;
							@SuppressWarnings("unused")
							Domain domain = (Domain) IndexUtils.getEntity(projectName,
									IndexUtils.getFullPath(domainFile), ELEMENT_TYPES.DOMAIN);
							fullPath = IndexUtils.getFullPath(domainFile);
						}
						if (dm instanceof SharedEntityElement) {
							SharedEntityElement sharedEntityElement = (SharedEntityElement) dm;
							if (sharedEntityElement.getEntity() instanceof Domain) {
								fullPath = sharedEntityElement.getEntity().getFullPath();
							}
						}
						if (fullPath != null) {
								completePath = fullPath;
						}
					}

				} else {
					completePath = "";
				}
				return true;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		completePath = "";
		return false;
	}

	
	public static void addDomainInstances(PropertyDefinition propertyDefinition, String path) {
		if (!doesDomainExist(propertyDefinition.getDomainInstances(), path)) {
			DomainInstance dmInstance = DomainFactory.eINSTANCE.createDomainInstance();
			dmInstance.setResourcePath(path);
			dmInstance.setOwnerProperty(propertyDefinition);
			// dmInstance.setGUID(GUIDGenerator.getGUID());
			propertyDefinition.getDomainInstances().add(dmInstance);
		}
	}
	
	public static boolean doesDomainExist(EList<DomainInstance> instances, String path) {

		for (DomainInstance instance : instances) {
			if (instance.getResourcePath().equals(path)) {
				return true;
			}
		}
		return false;
	}
	
}	
