package com.tibco.cep.webstudio.client.decisiontable.constraint;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.webstudio.client.decisiontable.AbstractTableAnalyzerAction;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils;
import com.tibco.cep.webstudio.client.decisiontable.TableRuleVariableRecord;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.logging.WebStudioClientLogger;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableCoverageAction extends AbstractTableAnalyzerAction implements HttpSuccessHandler, HttpFailureHandler {

	private HashMap<String, String> fRowCacheMap;
	private DecisionTableAnalyzerComponent tableAnalyzerComponent;
	
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private WebStudioClientLogger logger = WebStudioClientLogger.getLogger(DecisionTableCoverageAction.class.getName());
	
	/**
	 * @param fCurrentEditor
	 * @param fCurrentOptimizedTable
	 * @param fComponents
	 * @param fRangeColumnValues
	 */
	public DecisionTableCoverageAction(DecisionTableEditor fCurrentEditor,
									   DecisionTableAnalyzerComponent tableAnalyzerComponent,
			                           Map<String, FormItem[]> fComponents, 
			                           Map<String, Map<String, Object>> fRangeColumnValues) {
		super(fCurrentEditor, fComponents, fRangeColumnValues);
		this.tableAnalyzerComponent = tableAnalyzerComponent;		
	}

	@Override
	public void run() {
		DecisionTableUtils.indeterminateProgress(globalMsgBundle.progressMessage_pleaseWait() + " " + globalMsgBundle.progressMessage_coverageDT(), false);
		fRowCacheMap = null;
		showCoverage(fCurrentEditor.getProjectName(), fCurrentEditor.getTable().getFolder() + fCurrentEditor.getTable().getName());
	}
	
	public void showCoverage(String projectName, String artifactPath) {
		DecisionTableAnalyzerComponent componentSelections = processComponents();
		Document rootDocument = XMLParser.createDocument();
		componentSelections.serialize(rootDocument, rootDocument);		
		String xmlData = rootDocument.toString();

		ArtifactUtil.addHandlers(this);
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.POST);

		request.submit(ServerEndpoints.RMS_DECISION_TABLE_SHOW_COVERAGE.getURL());				
	}
	
	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DECISION_TABLE_SHOW_COVERAGE.getURL()) != -1) {			
			Map<Integer, List<String>> rulesToHighlight = DecisionTableAnalyzerResponseHandler.loadCoverageResponse(event.getData());			
			DecisionTableUtils.removeCoverageHighlights(fCurrentEditor);
			fCurrentEditor.setRulesToHighlight(rulesToHighlight);
			List<String> rulesToHighlightOnPage = rulesToHighlight.get(fCurrentEditor.getCurrentPage());
			if (rulesToHighlightOnPage != null) {
				logger.debug("Number of rows to highlight on current page" + rulesToHighlightOnPage.size());
				fCurrentEditor.setRowsToHighlight(rulesToHighlightOnPage);				
				DecisionTableUtils.highlightCoverageRecords(fCurrentEditor, "ws-dt-coverage-row-style");			
			}	
			removeHandlers(this);
			DecisionTableUtils.indeterminateProgress("Coverage of Decision Table has been done", true);
		}	
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		ArtifactUtil.removeHandlers(this);
		LoadingMask.clearMask();
		Element docElement = event.getData();
		String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		showError(responseMessage);		
	}

	private DecisionTableAnalyzerComponent processComponents() {
		DecisionTableAnalyzerComponent componentSelections = 
						new DecisionTableAnalyzerComponent(tableAnalyzerComponent.getProjectName(), tableAnalyzerComponent.getArtifactPath());
		for (String columnName : fComponents.keySet()) {
			FormItem[] formItem = fComponents.get(columnName);
			processComponent(columnName, formItem, componentSelections);
		}		
		return componentSelections;
	}

	/**
	 * @param columnName
	 * @param comp
	 * @param cellsToHighlight
	 */
	private void processComponent(String columnName, FormItem[] formItem, DecisionTableAnalyzerComponent componentSelections) {				
		DecisionTableAnalyzerComponent.ColumnFilter selectionFilter = componentSelections.new ColumnFilter(columnName);
		if (formItem[0] instanceof SelectItem) {
			Object selectedItem = ((SelectItem)formItem[0]).getValue();
			String[] stringArray = null;
			if (selectedItem != null) {
				if (selectedItem instanceof List) {
					List<String> selectedItemsList = (List<String>)selectedItem;
					stringArray = selectedItemsList.toArray(new String[selectedItemsList.size()]);
				} else if (selectedItem instanceof String[]) {
					stringArray = (String[]) selectedItem;
				} else {
					stringArray = selectedItem.toString().split(",");
				}
			}

			if (stringArray != null) {
				selectionFilter.addItems(Arrays.asList(stringArray));
			} else {
				// no filter was selected, need to add all Cells for this column
				DecisionTableAnalyzerComponent.ColumnFilter componentFilter = tableAnalyzerComponent.getColumnFilter(columnName);
				selectionFilter.addItems(componentFilter.getItems());
			}
			componentSelections.addFilter(selectionFilter);
		} else if (formItem instanceof FormItem[]) {
			if (formItem[0] instanceof SliderItem
					&& formItem[0] instanceof SliderItem) {
				long lowValue =Long.MIN_VALUE;
				long highValue =Long.MAX_VALUE;
				
				if(formItem[0].getValue() instanceof Integer && formItem[2].getValue() instanceof Integer){
					lowValue = (Integer) ((SliderItem) formItem[0]).getValue();
					highValue = (Integer) ((SliderItem) formItem[2]).getValue();
				}
				if(formItem[0].getValue() instanceof Long && formItem[2].getValue() instanceof Long){
					lowValue = (Long) ((SliderItem) formItem[0]).getValue();
					highValue = (Long) ((SliderItem) formItem[2]).getValue();
				}
				selectionFilter.setRange(lowValue, highValue);
				logger.debug("[Range for " + "\"" + columnName + "," + lowValue
						+ "," + highValue + "]");
				componentSelections.addFilter(selectionFilter);
			}if (formItem[0] instanceof TextItem) {
				Expression expression = new Expression((String)formItem[0].getValue());
				try {
					expression.parse();

					Object lowValue = expression.getOperands()[0];
					Object highValue = expression.getOperands()[1];
					if(null==highValue){
						highValue =lowValue;
					}
					selectionFilter.setRange(lowValue, highValue);
					logger.debug("[Range for " + "\"" + columnName + ","
							+ lowValue + "," + highValue + "]");
					componentSelections.addFilter(selectionFilter);
				} catch (Exception e) {
				}				
			}
		}
	} 
	
	private String getRecordNumber(String id) {
		if (fRowCacheMap == null) {
			initRecordCache();
		}
		return fRowCacheMap.get(id);
	}
	
	private void initRecordCache() {
		fRowCacheMap = new HashMap<String, String>();
		int i = 0;
		for(ListGridRecord record : fCurrentEditor.getDecisionTableDataGrid().getRecords()) {
			fRowCacheMap.put(((TableRuleVariableRecord)record).getRule().getId(), Integer.toString(i));
			i++;
		}
	}
	
}