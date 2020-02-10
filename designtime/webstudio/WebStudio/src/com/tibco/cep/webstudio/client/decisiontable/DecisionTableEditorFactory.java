package com.tibco.cep.webstudio.client.decisiontable;

import java.util.List;

import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;


/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableEditorFactory implements IEditorFactory {
	
	private static DecisionTableEditorFactory instance;

	
	public static DecisionTableEditorFactory getInstance() {
		if (instance == null){
			instance = new DecisionTableEditorFactory();
		}
		return instance;
	}
	
	private DecisionTableEditorFactory() {}
	
	/** 
	 * @see com.tibco.cep.webstudio.client.editor.IEditorFactory#createEditor(com.tibco.cep.webstudio.client.model.NavigatorResource)
	 */
	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord) {
		return createEditor(selectedRecord, false, null, true, false, null);
	}
	
	/**
	 * Initialise Decision Table Editor
	 * 
	 * @param selectedRecord
	 * @param loadDataAtStartUp
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean loadDataAtStartUp) {
		return createEditor(selectedRecord, false, null, loadDataAtStartUp, false, null);
	}
	
	/**
	 * @see IEditorFactory#createEditor(NavigatorResource, boolean, String)
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
		return createEditor(selectedRecord, isReadOnly, revisionId, true, false, null);
	}
	
	
	/**
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param artifactVersionDiff
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean artifactVersionDiff, List<RequestParameter> requestParams) {
		return createEditor(selectedRecord, isReadOnly, revisionId, true, artifactVersionDiff, requestParams);
	}
	
	/**
	 * Initialise Decision Table Editor
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param loadDataAtStartUp
	 * @param artifactVersionDiff
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataAtStartUp, 
																												boolean artifactVersionDiff, List<RequestParameter> requestParams) {
		return createEditor(selectedRecord, isReadOnly, revisionId, loadDataAtStartUp, artifactVersionDiff, false, requestParams);
	}

	/**
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param loadDataAtStartUp
	 * @param artifactVersionMerge
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId, boolean loadDataAtStartUp, 
																					boolean artifactVersionDiff, boolean artifactVersionMerge, List<RequestParameter> requestParams) {
		return new DecisionTableEditor(selectedRecord, isReadOnly, revisionId, loadDataAtStartUp, artifactVersionDiff, artifactVersionMerge, requestParams);
	}
}
