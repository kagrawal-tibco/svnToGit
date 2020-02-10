package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public abstract class PropertyControl {
	
	private SimplePropertyForm parentForm;
	
	protected String displayName;
	
	private SynProperty property;
	
	private Control control;
	
	private LocalElement localElement;
	
	private boolean enable;
	
	private boolean listen;
	
	private boolean readOnly;
	
	protected PropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		this.parentForm = parentForm;
		this.displayName = displayName;
		this.property = property;
		enable = true;
		listen = true;
		readOnly = false;
	}
	
	public final Control createControl(Composite parent){
		control = doCreateControl(parentForm,parent);
		parentForm.addControl(control);
		control.setEnabled(enable);
		return control;
	}
	
	protected abstract Control doCreateControl(SimplePropertyForm parentForm, Composite parent);
	
	public void setLocalElement(LocalElement localElement) {
		this.localElement = localElement;
	}

	protected abstract void refreshEnumerations();
	
	protected abstract void refreshSelection();
	
	public void enable() {
		enable = true;
		if (control != null){
			control.setEnabled(true);
		}
	}
	
	public void disable() {
		enable = false;
		if (control != null){
			control.setEnabled(false);
		}
	}
	
	public void listen() {
		listen = true;
	}
	
	public void dontListen(){
		listen = false;
	}
	
	protected void setValue(String value){
		if (listen == false){
			return;
		}
		try {
			localElement.setPropertyValue(property.getName(), value);
		} catch (Exception e) {
			parentForm.logAndAlert(new Status(IStatus.ERROR,parentForm.getPluginId(),"could not update "+property.getName()+" in "+localElement+" for "+parentForm.getClass(),e));
		}
	}
	
	protected String getValue(){
		try {
			return localElement.getPropertyValue(property.getName());
		} catch (Exception e) {
			parentForm.log(new Status(IStatus.ERROR,parentForm.getPluginId(),"could not read "+property.getName()+" from "+localElement+" for "+parentForm.getClass(),e));
			disable();
			return "";
		}
	}
	
	protected SynProperty getProperty(){
		try {
			if (localElement != null) {
				return (SynProperty) localElement.getProperty(property.getName());
			}
			return property;
		} catch (Exception e) {
			return property;
		}
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public boolean isReadOnly() {
		return readOnly;
	}

}
