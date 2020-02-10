package com.tibco.cep.studio.ui.doc;

import java.util.Iterator;

import org.eclipse.epf.richtext.RichText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class ExtendedRichText extends RichText {


	/**
	 * @param parent
	 * @param style
	 */
	public ExtendedRichText(Composite parent, int style) {
		super(parent, style);
	}
	
	/**
	 * @param parent
	 * @param style
	 * @param basePath
	 */
	public ExtendedRichText(Composite parent, int style, String basePath) {
		super(parent, style, basePath);
	}

	/**
	 * @return
	 */
	public Control getEditorControl() {
		return editorControl;
	}
	
	/**
	 * Notifies the modify listeners that the rich text editor content has
	 * changed.
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.epf.richtext.RichText#notifyModifyListeners()
	 */
	@Override
	public void notifyModifyListeners() {
		notifyingModifyListeners = true;

		Event event = new Event();
		event.display = Display.getCurrent();
		event.widget = editor;

		for (Iterator<ModifyListener> i = modifyListeners.iterator(); i.hasNext();) {
			ModifyListener listener = i.next();
			if (debug) {
				printDebugMessage(
						"notifyModifyListeners", "notifying listener, " + listener); //$NON-NLS-1$ //$NON-NLS-2$	
			}
			listener.modifyText(new ModifyEvent(event));
			if (debug) {
				printDebugMessage(
						"notifyModifyListeners", "notified listener, " + listener); //$NON-NLS-1$ //$NON-NLS-2$	
			}
		}

		notifyingModifyListeners = false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epf.richtext.RichText#getControl()
	 */
	public Control getControl() {
		return editor;
	}
	
	@Override
	protected void printDebugMessage(String method, String msg, String text) {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("RichText[").append(editor.getUrl()).append(']') //$NON-NLS-1$
				.append('.').append(method);
		if (msg != null && msg.length() > 0) {
			strBuf.append(": ").append(msg); //$NON-NLS-1$
		}
		if (text != null && text.length() > 0) {
			strBuf.append('\n').append(text);
		}
		StudioUIPlugin.debug(strBuf.toString());
	}
}