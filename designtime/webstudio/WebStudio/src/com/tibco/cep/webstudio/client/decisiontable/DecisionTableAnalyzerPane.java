package com.tibco.cep.webstudio.client.decisiontable;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.adjustColumnName;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionConstraintUtils.createTypedPane;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Element;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HeaderControl;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.events.RestoreClickEvent;
import com.smartgwt.client.widgets.events.RestoreClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SectionItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableAnalyzeAction;
import com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableAnalyzerComponent;
import com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableAnalyzerResponseHandler;
import com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableCoverageAction;
import com.tibco.cep.webstudio.client.decisiontable.constraint.testdata.coverage.DTTestDataSelectorDialog;
import com.tibco.cep.webstudio.client.decisiontable.model.Table;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableAnalyzerPane extends Window implements ClickHandler, 
                                                                 RestoreClickHandler, 
                                                                 ResizedHandler, 
                                                                 CloseClickHandler,
                                                                 HttpSuccessHandler,
                                                                 HttpFailureHandler {

	private int DEFAULT_WIDTH = 250; 
	private HeaderControl maximize;
	private HeaderControl minimize;
	private HeaderControl close;
	private HeaderControl restore_min;
	private HeaderControl restore_max;
	private DynamicForm container;
	private Canvas parent;
	
	private ToolStripButton refreshbutton;
	private ToolStripButton analyzebutton;
	private ToolStripButton coveragebutton;
	private ToolStripButton testDataCoverageButton;
	
	private DecisionTableAnalyzerComponent tableAnalyzerComponent;
	private Map<String, FormItem[]> fComponents = new HashMap<String, FormItem[]>();
	
	private Map<String, Map<String, Object>> fRangeColumnValues = new HashMap<String, Map<String, Object>>();
	
	private DecisionTableEditor fCurrentEditor;
	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
    
	protected HttpRequest request;
	
	public DecisionTableAnalyzerPane(Canvas parent) {
		super();
		GWT.log("init Web Studio Decision Table Analyzer Pane()...", null);
		this.parent = parent;
        init();
        this.request = new HttpRequest();
	}
	
	public void update(String projectName, String artifactPath) {
		indeterminateProgress(dtMessages.wsshowdtanalyzer(), false);
		String artifactExtension = "rulefunctionimpl";
		ArtifactUtil.addHandlers(this);
		this.request.clearRequestParameters();
		this.request.setMethod(HttpMethod.GET);
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, artifactPath));
		this.request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PARAM_FILE_EXTN, artifactExtension));
		
		String url = ServerEndpoints.RMS_DECISION_TABLE_SHOW_ANALYZER_PANE.getURL();
		request.submit(url);				
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DECISION_TABLE_SHOW_ANALYZER_PANE.getURL()) != -1) {			
			tableAnalyzerComponent = DecisionTableAnalyzerResponseHandler.loadAnalyzerComponentResponse(event.getData());
			clearComponents();
			updateComponents();
			indeterminateProgress("", true);
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		ArtifactUtil.removeHandlers(this);
		Element docElement = event.getData();
		String responseMessage = docElement.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		showError(responseMessage);		
	}
	
	/**
	 * @param table
	 */
	private void updateComponents() {
		fComponents.clear();
		fRangeColumnValues.clear();
		container.setNumCols(1);
		List<DecisionTableAnalyzerComponent.ColumnFilter> columnFilters = tableAnalyzerComponent.getFilters();
		List<FormItem> fields = new ArrayList<FormItem>();
		for (DecisionTableAnalyzerComponent.ColumnFilter columnFilter : columnFilters) {
			String column = columnFilter.getColumnName();
			final SectionItem section = new SectionItem();  
			section.setDefaultValue(adjustColumnName(column, true));
			section.setTitle(adjustColumnName(column, true));
			section.setSectionExpanded(true);
			FormItem[] items = createTypedPane(columnFilter, fRangeColumnValues);
			fComponents.put(column, items);
			if (items.length == 1) {
				section.setItemIds(column);
				fields.add(section);
				fields.add(items[0]);
			} else if(items.length == 4) {
				fields.add(section);
				SectionItem minSectionItem = new SectionItem();
				minSectionItem.setSectionExpanded(true);
				minSectionItem.setCanCollapse(false);
				minSectionItem.setDefaultValue(dtMessages.dt_tableAnalyzer_min());
				minSectionItem.setTitle(dtMessages.dt_tableAnalyzer_min());
				minSectionItem.setName("Section" + column + "_MIN");
				minSectionItem.setHeight(14);
				minSectionItem.setItemIds(column + "_MIN","_MIN_TEXT");

				fields.add(minSectionItem);
				fields.add(items[0]);
				fields.add(items[1]);

				SectionItem maxSctionItem = new SectionItem();
				maxSctionItem.setSectionExpanded(true);
				maxSctionItem.setCanCollapse(false);
				maxSctionItem.setDefaultValue(dtMessages.dt_tableAnalyzer_max());
				maxSctionItem.setHeight(14);
				maxSctionItem.setTitle(dtMessages.dt_tableAnalyzer_max());
				maxSctionItem.setName("Section" + column + "_MAX");
				maxSctionItem.setItemIds(column + "_MAX","_MAX_TEXT");

				section.setItemIds("Section" + column + "_MIN", column + "_MIN", "_MIN_TEXT", "Section" + column + "_MAX", column + "_MAX", "_MAX_TEXT");

				fields.add(maxSctionItem);
				fields.add(items[2]);
				fields.add(items[3]);
			}
		}
		container.setFields(fields.toArray(new FormItem[fields.size()]));
	}

	public void clearComponents() {
		if (container != null) {
			removeItem(container);
		}
		container = new DynamicForm();
		container.setWidth(250);
		addItem(container);
	}
	
	public void refresh(Table table) {
		clearComponents();
		update(table.getProjectName(), table.getFolder() + table.getName());
	}

	@Override
	public void onRestoreClick(RestoreClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public void init() {
		setWidth(DEFAULT_WIDTH);
		setHeight100();
		setAlign(Alignment.RIGHT);
	    minimize = new HeaderControl(HeaderControl.MINIMIZE, this);  
	    maximize = new HeaderControl(HeaderControl.MAXIMIZE, this);
	    close = new HeaderControl(HeaderControl.CLOSE, this);
	    close.setTooltip(globalMsgBundle.button_close());
	    restore_min = new HeaderControl(HeaderControl.CASCADE, this);  
	    restore_max = new HeaderControl(HeaderControl.CASCADE, this);  
	    restore_min.setVisible(false);
	    restore_max.setVisible(false);
	    maximize.setVisible(false);
	    minimize.setVisible(false);
		setAnimateMinimize(true);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, minimize, restore_min, maximize, restore_max, close);
		addCloseClickHandler(this);
		addItem(getAnalyzerToolBar());
	}

	protected ToolStrip getAnalyzerToolBar() {
		ToolStrip analyzerToolStrip = new ToolStrip();   
		analyzerToolStrip.setWidth100();
		analyzerToolStrip.setHeight(AbstractDecisionTableEditor.TOOL_BAR_HEIGHT);
		analyzerToolStrip.setAlign(Alignment.RIGHT); 
		analyzerToolStrip.setID("dt_analyzer_toolstrip");
		
		refreshbutton = new ToolStripButton();
		refreshbutton.setIcon(Page.getAppImgDir() + "icons/16/refresh_16x16.png");
		refreshbutton.setTooltip(globalMsgBundle.text_refresh());
		refreshbutton.addClickHandler(this);
		refreshbutton.setAlign(Alignment.RIGHT);
		analyzerToolStrip.addButton(refreshbutton);

		analyzerToolStrip.addSeparator();
		
		analyzebutton = new ToolStripButton();
		analyzebutton.setIcon(Page.getAppImgDir() + "icons/16/analyze_table16x16.gif");
		analyzebutton.setTooltip(globalMsgBundle.text_analyze());
		analyzebutton.setAlign(Alignment.RIGHT);
		analyzebutton.addClickHandler(this);
		analyzerToolStrip.addButton(analyzebutton);
		
		coveragebutton = new ToolStripButton();
		coveragebutton.setIcon(Page.getAppImgDir() + "icons/16/detail.gif");
		coveragebutton.setTooltip(globalMsgBundle.text_showCoverage());
		coveragebutton.setAlign(Alignment.RIGHT);
//		coveragebutton.setActionType(SelectionType.CHECKBOX);
		coveragebutton.addClickHandler(this);
		analyzerToolStrip.addButton(coveragebutton);

		testDataCoverageButton = new ToolStripButton();
		testDataCoverageButton.setIcon(Page.getAppImgDir() + "icons/16/testdata_coverage.png");
		testDataCoverageButton.setTooltip(globalMsgBundle.text_showTestData_Coverage());
		testDataCoverageButton.setAlign(Alignment.RIGHT);
		testDataCoverageButton.addClickHandler(this);
		analyzerToolStrip.addButton(testDataCoverageButton);
		
		return analyzerToolStrip;
	}  

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() == maximize) {
			this.maximize();
			maximize.setVisible(false);
			restore_max.setVisible(true);
		}
		if (event.getSource() == minimize) {
			this.minimize();
			minimize.setVisible(false);
			restore_min.setVisible(true);
		}
		if (event.getSource() == restore_min) {
			this.restore();
			restore_min.setVisible(false);
			minimize.setVisible(true);
		}
		if (event.getSource() == restore_max) {
			this.restore();
			restore_max.setVisible(false);
			maximize.setVisible(true);
		}
		if (event.getSource() == close) {
//			Don't Destroy.. just hide for future use
//			this.destroy();
			close();
		}
		if (event.getSource() == refreshbutton) {
			if (WebStudio.get().getEditorPanel().getTabs().length > 0 
					&& WebStudio.get().getEditorPanel().getSelectedTab() instanceof DecisionTableEditor) {
				DecisionTableEditor editor = (DecisionTableEditor)WebStudio.get().getEditorPanel().getSelectedTab();
				if (editor.getTable() != null) {
					refresh(editor.getTable());
				}
			}
		} 
		if (event.getSource() == coveragebutton) {
			run(false);				
		} 
		if (event.getSource() == analyzebutton) {
			if (WebStudio.get().getEditorPanel().getSelectedTab() instanceof DecisionTableEditor) {
				fCurrentEditor  = (DecisionTableEditor)WebStudio.get().getEditorPanel().getSelectedTab();
				fCurrentEditor.setAnalyzed(false);
			}
			run(true);
		}
		if (event.getSource() == testDataCoverageButton) {
			fCurrentEditor  = (DecisionTableEditor)WebStudio.get().getEditorPanel().getSelectedTab();
			DTTestDataSelectorDialog dtTestDataSelector = new DTTestDataSelectorDialog(fCurrentEditor.getProjectName(), fCurrentEditor.getTable(), tableAnalyzerComponent.getTestDataArtifacts());
			dtTestDataSelector.show();
		}
	}
	
	public void close() {
		GWT.log("Closing Web Studio Decision Table Analyzer Pane...", null);
		this.setVisible(false);
		WebStudio.get().getEditorPanel().setShowTableAnalyzer(false);
		parent.setVisible(false);
		WebStudio.get().getEditorPanel().reflow();
	}
	
	@Override
	public void onResized(ResizedEvent event) {
		//TODO
	}

	@Override
	public void onCloseClick(CloseClickEvent event) {
	}
	
	public void run(boolean analyze) {
		if (WebStudio.get().getEditorPanel().getSelectedTab() instanceof DecisionTableEditor) {
			fCurrentEditor  = (DecisionTableEditor)WebStudio.get().getEditorPanel().getSelectedTab();
			if (fCurrentEditor.isDirty()) {
				CustomSC.say(dtMessages.dtHasUnsavedChanges_message());
				return;
			}
			fCurrentEditor.removeFilter(fCurrentEditor.getDecisionTableDataGrid());
			if (analyze) {
				new DecisionTableAnalyzeAction(fCurrentEditor).run();
			}  else {
				DecisionTableUtils.removeCoverageHighlights(fCurrentEditor);
				fCurrentEditor.getDecisionTableDataGrid().redraw();
				new DecisionTableCoverageAction(fCurrentEditor, tableAnalyzerComponent, fComponents, fRangeColumnValues).run();	
			}
		}
	}
}