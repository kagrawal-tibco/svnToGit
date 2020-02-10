package com.tibco.cep.studio.ui.editors.project;

import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class ProjectDiagramPropertySheetPage extends TabbedPropertySheetPage {

	protected ProjectDiagramEditor editor;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public ProjectDiagramPropertySheetPage(ProjectDiagramEditor editor) {
		super(editor);
		this.editor = editor;
	}

	/**
	 * Get the EMF AdapterFactory for this editor.
	 * 
	 * @return the EMF AdapterFactory for this editor.
	 */
	public ProjectDiagramEditor getEditor() {
		return editor;
	}

}