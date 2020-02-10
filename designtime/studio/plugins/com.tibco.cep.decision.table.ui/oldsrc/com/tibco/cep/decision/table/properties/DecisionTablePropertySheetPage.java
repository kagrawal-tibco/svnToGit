package com.tibco.cep.decision.table.properties;

import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTablePropertySheetPage extends TabbedPropertySheetPage {

	protected DecisionTableEditor editor;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public DecisionTablePropertySheetPage(DecisionTableEditor editor) {
		super(editor);
		this.editor = editor;
	}

	/**
	 * Get the EMF AdapterFactory for this editor.
	 * 
	 * @return the EMF AdapterFactory for this editor.
	 */
	public DecisionTableEditor getEditor() {
		return editor;
	}

}