package com.tibco.cep.studio.dashboard.ui.forms;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum;

public final class FontStyleWidget extends Composite {

	private Button btn_FontBold;
	private Button btn_FontItalic;
	private FontStyleEnum fontStyleEnum;

	private PropertyChangeSupport propertyChangeSupport;

	public FontStyleWidget(FormToolkit toolKit, Composite parent, int style) {
		super(parent, style);
		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		super.setLayout(layout);
		// create bold button
		if (toolKit != null) {
			btn_FontBold = toolKit.createButton(this, "B", SWT.TOGGLE);
		} else {
			btn_FontBold = new Button(this, SWT.TOGGLE);
			btn_FontBold.setText("B");
		}

		// create italic button
		if (toolKit != null) {
			btn_FontItalic = toolKit.createButton(this, "I", SWT.TOGGLE);
		} else {
			btn_FontItalic = new Button(this, SWT.TOGGLE);
			btn_FontItalic.setText("I");
		}

		// set layout data
		GridData btnGridData = new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false);
		btnGridData.widthHint = btnGridData.heightHint = 20;
		btn_FontBold.setLayoutData(btnGridData);
		btn_FontItalic.setLayoutData(btnGridData);

		// hook up listeners
		btn_FontBold.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FontStyleEnum currFontStyleEnum = getFontStyle();
				fontStyleChanged();
				// fire property change only when user triggers the change
				propertyChangeSupport.firePropertyChange("FontStyle", currFontStyleEnum, getFontStyle());
			}

		});

		btn_FontItalic.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FontStyleEnum currFontStyleEnum = getFontStyle();
				fontStyleChanged();
				// fire property change only when user triggers the change
				propertyChangeSupport.firePropertyChange("FontStyle", currFontStyleEnum, getFontStyle());				
			}

		});

		propertyChangeSupport = new PropertyChangeSupport(this);
		
//		setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
	}

	@Override
	public void setLayout(Layout layout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEnabled(boolean enabled) {
		btn_FontBold.setEnabled(enabled);
		btn_FontItalic.setEnabled(enabled);
	}

	public void setFontStyle(FontStyleEnum fontStyleEnum) {
		this.fontStyleEnum = fontStyleEnum;
		switch (this.fontStyleEnum.getValue()) {
			default:
			case FontStyleEnum.NORMAL_VALUE:
				btn_FontBold.setSelection(false);
				btn_FontBold.setToolTipText("Click to set bold styling");
				btn_FontItalic.setSelection(false);
				btn_FontItalic.setToolTipText("Click to set italic styling");
				break;
			case FontStyleEnum.BOLD_VALUE:
				btn_FontBold.setSelection(true);
				btn_FontBold.setToolTipText("Click to unset bold styling");
				btn_FontItalic.setSelection(false);
				btn_FontItalic.setToolTipText("Click to set italic styling");
				break;
			case FontStyleEnum.ITALIC_VALUE:
				btn_FontBold.setSelection(false);
				btn_FontBold.setToolTipText("Click to set bold styling");
				btn_FontItalic.setSelection(true);
				btn_FontItalic.setToolTipText("Click to unset italic styling");
				break;
			case FontStyleEnum.BOLD_ITALIC_VALUE:
				btn_FontBold.setSelection(true);
				btn_FontBold.setToolTipText("Click to unset bold styling");
				btn_FontItalic.setSelection(true);
				btn_FontItalic.setToolTipText("Click to unset italic styling");
				break;
		}
	}

	public FontStyleEnum getFontStyle() {
		return fontStyleEnum;
	}

	protected void fontStyleChanged() {
		FontStyleEnum style = FontStyleEnum.NORMAL;
		boolean bold = btn_FontBold.getSelection();
		boolean italic = btn_FontItalic.getSelection();
		if (bold == true && italic == true) {
			style = FontStyleEnum.BOLD_ITALIC;
		} else if (bold == true) {
			style = FontStyleEnum.BOLD;
		} else if (italic == true) {
			style = FontStyleEnum.ITALIC;
		}
		setFontStyle(style);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
