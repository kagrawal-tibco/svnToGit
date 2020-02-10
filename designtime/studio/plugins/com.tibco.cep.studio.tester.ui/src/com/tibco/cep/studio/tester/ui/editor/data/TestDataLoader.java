package com.tibco.cep.studio.tester.ui.editor.data;

import org.eclipse.ui.IEditorPart;

import com.tibco.cep.studio.tester.ui.editor.result.ResultDetailsPage;

/**
 * 
 * @author sasahoo
 *
 */
public class TestDataLoader {
	
	private AbstractTestViewer tdViewer;
	private IEditorPart editor;
	
	
	
	// TODO refactor code of loading test data from Testdatapropertiestable to Testdataloader- mgujrath
	
	
	/**
	 * @param editor
	 */
	public TestDataLoader(IEditorPart editor, AbstractTestViewer testViewer) {
		if (editor !=  null && editor instanceof TestDataEditor) {
			TestDataEditor testDataEditor = (TestDataEditor)editor;
			tdViewer = testDataEditor.getTestDataDesignViewer();
		}
		if (testViewer != null && testViewer instanceof ResultDetailsPage){
			tdViewer = testViewer;
		}
		this.editor = editor;
	}
	
	
}