package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenter;
import org.eclipse.jface.text.DefaultInformationControl.IInformationPresenterExtension;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.widgets.Display;

public class StudioInformationPresenterExtension implements
		IInformationPresenterExtension, IInformationPresenter {

	@Override
	public String updatePresentation(Drawable drawable, String hoverInfo,
			TextPresentation presentation, int maxWidth, int maxHeight) {
		if (drawable instanceof Browser) {
			if (hoverInfo.indexOf("<html>") == -1) {
				// need to wrap info with pre-formatted text tag for Browser
				hoverInfo = "<pre>"+hoverInfo+"</pre>";
			}
		} 
		return hoverInfo;
	}

	@Override
	public String updatePresentation(Display display, String hoverInfo,
			TextPresentation presentation, int maxWidth, int maxHeight) {
		return hoverInfo;
	}

}
