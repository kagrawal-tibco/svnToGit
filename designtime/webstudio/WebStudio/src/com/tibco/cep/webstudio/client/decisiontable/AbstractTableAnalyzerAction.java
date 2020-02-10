package com.tibco.cep.webstudio.client.decisiontable;

import java.util.Map;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.tibco.cep.webstudio.client.http.HttpRequest;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractTableAnalyzerAction {
	
	protected DecisionTableEditor fCurrentEditor;
	protected Map<String, FormItem[]> fComponents;
	protected Map<String, Map<String, Object>> fRangeColumnValues;
	protected HttpRequest request;
	
	/**
	 * @param fCurrentEditor
	 * @param fCurrentOptimizedTable
	 * @param fComponents
	 * @param fRangeColumnValues2
	 */
	public AbstractTableAnalyzerAction(DecisionTableEditor fCurrentEditor, 
            Map<String, FormItem[]> fComponents, 
            Map<String, Map<String, Object>> fRangeColumnValues2) {
		this.fCurrentEditor = fCurrentEditor;
		this.fComponents = fComponents;
		this.fRangeColumnValues = fRangeColumnValues2;
		request = new HttpRequest();
	}
	
	public abstract void run();

}
