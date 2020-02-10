package com.tibco.cep.studio.ui;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.ui.forms.editor.FormEditor;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractFormEditor extends FormEditor implements IResourceChangeListener {

	public abstract void setEnabled(boolean enabled);
}
