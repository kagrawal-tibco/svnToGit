package com.tibco.cep.decision.tree.ui.editor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tomsawyer.graphicaldrawing.TSEObject;

/*
@author ssailapp
@date Sep 14, 2011
 */

@SuppressWarnings("restriction")
public class DecisionTreePropertySheetPage extends TabbedPropertySheetPage {

	protected DecisionTreeEditor editor;

	/**
	 * Contructor for this property sheet page.
	 * 
	 * @param tabbedPropertySheetPageContributor
	 *            the editor contributor of the property sheet page.
	 */
	public DecisionTreePropertySheetPage(DecisionTreeEditor editor) {
		super(editor);
		this.editor = editor;
	}

	public DecisionTreeEditor getEditor() {
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
			String text = DecisionTreeUiUtil.getText(object);
			Image image = DecisionTreeUiUtil.getImage(object);
			title.setTitle(text, image);
		}
	}

	public void removeEditor() {
		if (this.editor != null) {
			this.editor = null;
		}
	}
	
}

