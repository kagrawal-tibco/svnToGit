package com.tibco.cep.studio.ui.editors.retegraph;

import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ReteGraphDiagramPropertySheetPage extends TabbedPropertySheetPage {

	protected ReteGraphDiagramEditor editor;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public ReteGraphDiagramPropertySheetPage(ReteGraphDiagramEditor editor) {
		super(editor);
		this.editor = editor;
	}

	/**
	 * Get the EMF AdapterFactory for this editor.
	 * 
	 * @return the EMF AdapterFactory for this editor.
	 */
	public ReteGraphDiagramEditor getEditor() {
		return editor;
	}

}