package com.tibco.cep.studio.ui.statemachine.tabbed.properties;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getImage;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getText;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * This is the property sheet page for the State model editor.
 * @author sasahoo
 */
@SuppressWarnings("restriction")
public class StateMachinePropertySheetPage
	extends TabbedPropertySheetPage {

	/**
	 * the State Machine model editor.
	 */
	protected StateMachineEditor editor;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public StateMachinePropertySheetPage(StateMachineEditor editor) {
		super(editor);
		this.editor = editor;
	}

	/**
	 * Get the EMF AdapterFactory for this editor.
	 * 
	 * @return the EMF AdapterFactory for this editor.
	 */
	public StateMachineEditor getEditor() {
		return editor;
	}

//	/**
//	 * Get the EMF AdapterFactory for this editor.
//	 * 
//	 * @return the EMF AdapterFactory for this editor.
//	 */
//	public AdapterFactory getAdapterFactory() {
//		return editor.getAdapterFactory();
//	}
	
	/**
	 * Update the title bar.
	 */
	public void doRefreshTitleBar(TSEObject object) {
		TabbedPropertyComposite tabbedPropertyComposite = (TabbedPropertyComposite)getControl();
		TabbedPropertyTitle title = tabbedPropertyComposite.getTitle();
		if (getCurrentTab() == null) {
			/**
			 * No tabs are shown so hide the title bar, otherwise you see
			 * "No properties available" and a title bar for the selection.
			 */
			title.setTitle(null, null);
		} else {
			String text = getText(object);
			Image image = getImage(object);
			title.setTitle(text, image);
		}
	}

	public void removeEditor() {
		if (this.editor != null) {
			this.editor = null;
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		editor.propertySheetDisposed();
	}
	
}
