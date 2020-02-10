package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IEditorStatusLine;

public class GraphEditorStatusLine implements IEditorStatusLine {
	
	private class GraphStatusLineClearer implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			
		}
		
	}
	
	/** The status line manager. */
	private final IStatusLineManager fStatusLineManager;

	/** The selection provider. */
	private final ISelectionProvider fSelectionProvider;

	/** The status line clearer, <code>null</code> if not installed. */
	private GraphStatusLineClearer fStatusLineClearer;
	
	public GraphEditorStatusLine(IStatusLineManager statusLineManager, ISelectionProvider selectionProvider) {
		Assert.isNotNull(statusLineManager);
		Assert.isNotNull(selectionProvider);

		fStatusLineManager= statusLineManager;
		fSelectionProvider= selectionProvider;
	}
	
	public IStatusLineManager getStatusLineManager() {
		return fStatusLineManager;
	}
	
	public ISelectionProvider getSelectionProvider() {
		return fSelectionProvider;
	}
	
	public GraphStatusLineClearer getStatusLineClearer() {
		return fStatusLineClearer;
	}

	@Override
	public void setMessage(boolean error, String message, Image image) {
		if (error)
			fStatusLineManager.setErrorMessage(image, message);
		else {
			// Clear error message
			fStatusLineManager.setErrorMessage(null, null);

			fStatusLineManager.setMessage(image, message);
		}

		if (isMessageEmpty(message))
			uninstallStatusLineClearer();
		else
			installStatusLineClearer();

	}
	
	/**
	 * Returns whether this given string is empty.
	 *
	 * @param message a string
	 * @return <code>true</code> if the string is <code>null</code>, has 0 length or only white space characters.
	 */
	private static boolean isMessageEmpty(String message) {
		return message == null || message.trim().length() == 0;
	}

	/**
	 * Uninstalls the status line clearer.
	 */
	private void uninstallStatusLineClearer() {
		if (fStatusLineClearer == null)
			return;

		fSelectionProvider.removeSelectionChangedListener(fStatusLineClearer);
		fStatusLineClearer= null;
	}

	/**
	 * Installs the status line clearer.
	 */
	private void installStatusLineClearer() {
		if (fStatusLineClearer != null)
			return;

		GraphStatusLineClearer statusLineClearer= new GraphStatusLineClearer();
		fSelectionProvider.addSelectionChangedListener(statusLineClearer);
		fStatusLineClearer= statusLineClearer;
	}

}
