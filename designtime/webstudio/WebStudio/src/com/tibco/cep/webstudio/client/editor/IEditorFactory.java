/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import com.tibco.cep.webstudio.client.model.NavigatorResource;

/**
 * All Editor factories will implement this interface to provide specific
 * concrete editors.
 * 
 * @author Vikram Patil
 */
public interface IEditorFactory {
	/**
	 * Creates a specific editor based on the editor type
	 * 
	 * @param selectedRecord
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord);
	
	/**
	 * Creates a specific editor based on the editor type and revision ID.
	 * This method is largely used for creating readonly editors
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId);
}
