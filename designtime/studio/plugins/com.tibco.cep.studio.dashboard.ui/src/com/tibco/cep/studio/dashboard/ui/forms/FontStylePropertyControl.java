package com.tibco.cep.studio.dashboard.ui.forms;

import java.beans.PropertyChangeListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

public class FontStylePropertyControl extends PropertyControl {

	private FontStyleWidget fontStyleWidget;

	public FontStylePropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		super(parentForm, displayName, property);
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		fontStyleWidget = new FontStyleWidget(null, parent, SWT.NONE);
		fontStyleWidget.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				setValue(fontStyleWidget.getFontStyle().toString());
			}

			
		});		
		return fontStyleWidget;
	}
	
	@Override
	protected void refreshEnumerations() {
	}

	@Override
	protected void refreshSelection() {
		String fontStyle = getValue().toLowerCase();
		FontStyleEnum fontStyleNum = FontStyleEnum.NORMAL;
		for (FontStyleEnum styleEnum : FontStyleEnum.VALUES) {
			if (styleEnum.getLiteral().equals(fontStyle) == true){
				fontStyleNum = styleEnum;
				break;
			}
		}
		fontStyleWidget.setFontStyle(fontStyleNum);		
	}

}
