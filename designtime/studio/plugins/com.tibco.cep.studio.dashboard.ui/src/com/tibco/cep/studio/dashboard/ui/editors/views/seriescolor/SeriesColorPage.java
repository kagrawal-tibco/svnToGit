package com.tibco.cep.studio.dashboard.ui.editors.views.seriescolor;

import org.eclipse.ui.forms.editor.FormEditor;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;

/**
 *
 * @author rgupta
 */
public class SeriesColorPage extends AbstractEntityEditorPage {

	public SeriesColorPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	//Overridden by Anand on 09/12/2011 to fix BE-12900
	@Override
	protected String getElementTypeName() {
		return "Series Color";
	}
}
