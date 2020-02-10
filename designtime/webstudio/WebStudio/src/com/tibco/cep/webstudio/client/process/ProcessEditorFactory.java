package com.tibco.cep.webstudio.client.process;

import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.palette.PalettePane;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessEditorFactory implements IEditorFactory {

	private static ProcessEditorFactory instance;
	private static PalettePane palettePane;

	public static ProcessEditorFactory getInstance() {
		if (instance == null) {
			instance = new ProcessEditorFactory();
		}
		return instance;
	}

	public static PalettePane getPalette() {
		if (palettePane == null) {
			palettePane = new PalettePane(WebStudio.get().getEditorPanel()
					.getLeftPane());
		}
		return palettePane;
	}

	private ProcessEditorFactory() {
	}

	/**
	 * @see com.tibco.cep.webstudio.client.editor.IEditorFactory#createEditor(com.tibco.cep.webstudio.client.model.NavigatorResource)
	 */
	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord) {
		return createEditor(selectedRecord, false, null, true, false);
	}

	/**
	 * Initialise BPMN Process Editor
	 * 
	 * @param selectedRecord
	 * @param loadDataAtStartUp
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord,
			boolean loadDataAtStartUp) {
		return createEditor(selectedRecord, false, null, loadDataAtStartUp,
				false);
	}

	/**
	 * @see IEditorFactory#createEditor(NavigatorResource, boolean, String)
	 */
	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord,
			boolean isReadOnly, String revisionId) {
		return createEditor(selectedRecord, isReadOnly, revisionId, true, false);
	}

	/**
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param artifactVersionDiff
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord,
			boolean isReadOnly, String revisionId, boolean artifactVersionDiff) {
		return createEditor(selectedRecord, isReadOnly, revisionId, true,
				artifactVersionDiff);
	}

	/**
	 * Initialise BPMN Process Editor
	 * 
	 * @param selectedRecord
	 * @param isReadOnly
	 * @param revisionId
	 * @param loadDataAtStartUp
	 * @param artifactVersionDiff
	 * @return
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord,
			boolean isReadOnly, String revisionId, boolean loadDataAtStartUp,
			boolean artifactVersionDiff) {
		return new ProcessEditor(selectedRecord, false, isReadOnly, revisionId,
				loadDataAtStartUp, artifactVersionDiff);
	}

}
