package com.tibco.cep.studio.dashboard.ui.editors.metric;

import org.eclipse.ui.IEditorPart;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

public abstract class AbstractMetricSaveableEditorPart extends AbstractSaveableEntityEditorPart {

	protected LocalElement localElement;
	protected String selectedUDPropertyDefinitionName;

	public String getSelectedUDPropertyDefinitionName() {
		return selectedUDPropertyDefinitionName;
	}

	public void setSelectedUDPropertyDefinitionName(String selectedUDPropertyDefinitionName) {
		this.selectedUDPropertyDefinitionName = selectedUDPropertyDefinitionName;
	}

	public LocalElement getLocalElement() {
		return localElement;
	}

	protected void handleActivate() {

	}

	public void doSave() {
		try {
			saving = true;
			saveResource();
			postSaveResource();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saving = false;
		}
	}

	/**
	 * @throws Exception
	 */
	protected void saveResource() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	protected void postSaveResource() throws Exception {
		if (isDirty()) {
			isModified = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	public void firePropertyChange() {
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

}