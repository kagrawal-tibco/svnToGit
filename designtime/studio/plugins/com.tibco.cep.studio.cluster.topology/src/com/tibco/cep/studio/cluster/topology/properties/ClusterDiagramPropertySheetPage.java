package com.tibco.cep.studio.cluster.topology.properties;

import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;

public class ClusterDiagramPropertySheetPage extends TabbedPropertySheetPage {

	protected ClusterTopologyEditor editor;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public ClusterDiagramPropertySheetPage(ClusterTopologyEditor editor) {
		super(editor);
		this.editor = editor;
	}

	/**
	 * Get the EMF AdapterFactory for this editor.
	 * 
	 * @return the EMF AdapterFactory for this editor.
	 */
	public ClusterTopologyEditor getEditor() {
		return editor;
	}

}