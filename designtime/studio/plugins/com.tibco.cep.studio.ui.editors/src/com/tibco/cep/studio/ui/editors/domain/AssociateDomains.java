package com.tibco.cep.studio.ui.editors.domain;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class AssociateDomains {
	
	protected PropertyDefinition propertyDefinition;
	protected boolean editorOpen = false;
	protected AbstractSaveableEntityEditorPart editor = null;
    protected int index = 0;
    
	public PropertyDefinition getPropertyDefinition() {
		return propertyDefinition;
	}
	public void setPropertyDefinition(PropertyDefinition propertyDefinition) {
		this.propertyDefinition = propertyDefinition;
	}
	public boolean isEditorOpen() {
		return editorOpen;
	}
	public void setEditorOpen(boolean editorOpen) {
		this.editorOpen = editorOpen;
	}
	public AbstractSaveableEntityEditorPart getEditor() {
		return editor;
	}
	public void setEditor(AbstractSaveableEntityEditorPart editor) {
		this.editor = editor;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
    
}
