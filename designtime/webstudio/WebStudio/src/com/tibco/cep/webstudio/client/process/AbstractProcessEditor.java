package com.tibco.cep.webstudio.client.process;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.drawingViewName;
//import static com.tibco.cep.webstudio.client.process.ProcessConstants.modelID;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectID;
import static com.tibco.cep.webstudio.client.process.ProcessConstants.projectLocation;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.process.properties.ProcessPropertyTab;
import com.tibco.cep.webstudio.client.process.properties.Property;
import com.tibco.cep.webstudio.client.process.util.ProcessCommandUtil;
import com.tibco.cep.webstudio.client.process.validataion.ProcessValidationUtils;
import com.tibco.cep.webstudio.client.util.LoadingMask;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tomsawyer.licensing.gwtclient.TSLicensingModule;
import com.tomsawyer.util.gwtclient.TSSession;
import com.tomsawyer.util.gwtclient.ui.error.TSErrorDialogBox;
import com.tomsawyer.util.shared.TSCallback;
import com.tomsawyer.web.client.TSWebProject;
import com.tomsawyer.web.client.TSWebProjectException;
import com.tomsawyer.web.client.project.TSProjectData;
import com.tomsawyer.web.client.project.TSWebProjectCommandResult;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinator;
import com.tomsawyer.web.client.view.TSWebModelViewCoordinators;
import com.tomsawyer.web.client.view.TSWebViewClient;
import com.tomsawyer.web.client.view.behavior.TSToolbarWidget;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewWidget;

/**
 * Abstract Process Editor
 * 
 * @author sasahoo,dijadhav
 */
public abstract class AbstractProcessEditor extends AbstractEditor {
	protected Canvas parentCanvas;

	protected String selectedPalette;

	protected String processArtifactPath;
	protected String artifactExtention;
	protected String process;

	protected String processName;
	private boolean showPropertyPane;
	private boolean showPalettePane;
	private static final String TS_CONTAINER_PREFIX = "TSGWTContainer_";
	private static final String TS_USER = "studiodevuser";
	private static final String TS_VIEW_PREFIX = "view#";
	private static final String TS_MODEL_PREFIX = "model#";
	private boolean isInitialized = false;
	private String drawingViewID;
	private String modelId;

	protected ProcessMessages processMessages = (ProcessMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);
	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private ProcessPropertyTab processPropertyTab;
	private boolean canUndo;
	private boolean canRedo;
	private boolean isLocalizedTitleLoaded;
	private static final String TITLE_PROPERTY = "process.toolbar.";

	public AbstractProcessEditor(NavigatorResource record) {
		super(record);
	}

	public AbstractProcessEditor(NavigatorResource record, boolean isReadOnly,
			String revisionId, boolean loadDataAtStartup) {
		super(record, isReadOnly, revisionId, loadDataAtStartup);
	}

	public AbstractProcessEditor(NavigatorResource record, boolean hasSections,
			boolean isReadOnly, String revisionId, boolean loadDataAtStartup,
			boolean versionDiffContent) {
		super(record, hasSections, isReadOnly, revisionId, loadDataAtStartup,
				versionDiffContent);
	}

	public AbstractProcessEditor(NavigatorResource record,
			boolean loadDataAtStartup) {
		super(record, loadDataAtStartup);
	}

	@Override
	public void setSaveConfirmationProperties() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDirty() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSave() {
		saveProcess();
	}

	@Override
	public boolean onValidate() {
		validate();
		return true;
	}

	@Override
	public void makeReadOnly() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		super.close();
		if (getProcessPropertyTab() != null
				&& getProcessPropertyTab().isVisible()) {
			getProcessPropertyTab().destroy();
			processPropertyTab = null;
			showPropertyPane = false;
			WebStudio.get().getEditorPanel().getPropertiesPane()
					.fillProperties(null);
		}
		showPalettePane = false;
		WebStudio.get().getEditorPanel().setPalettePane(null);
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		WebStudio.get().getEditorPanel().getLeftPane().setVisible(false);
		ProcessCommandUtil.clearHistory(this);
	}

	@Override
	protected Canvas getWidget() {
		this.parentCanvas = new Canvas(TS_CONTAINER_PREFIX
				+ System.currentTimeMillis());
		parentCanvas.setWidth100();
		parentCanvas.setHeight100();
		return parentCanvas;
	}

	protected void populate(Canvas parentCanvas) {
		if (TSSession.getInstance().isLicensed()) {
			loadProcessViewer();
			return;
		} else {
			this.checkLicensing();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void checkLicensing() {
		// Process Logging
		if (WebStudio.isProcessLoggingEnabled()
				&& !WebStudio.get().isAlreadyProcessDebugLevelEnabled()) {
			WebStudio.get().setProcessDebugLevelEnabled(true);
			ProcessLogServiceAsync logService = (ProcessLogServiceAsync) GWT
					.create(ProcessLogService.class);
			logService.setLogLevel("", new AsyncCallback() {
				@Override
				public void onSuccess(Object result) {
					System.out.println("");
				}

				public void onFailure(Throwable caught) {
					System.out.println("");
				}
			});
		}
		if (TSLicensingModule.getInstance() != null) {
			TSLicensingModule.getInstance().checkLicense(TS_USER,
					new TSCallback<Void>() {
						public void onSuccess(Void v) {
							loadProcessViewer();
						}

						public void onFailure(Throwable th) {
							// Failed to check licensing.
							new TSErrorDialogBox(th).center();
						}
					});
		} else {
			// Check this again, the module should have been loaded by now.
			Timer timer = new Timer() {
				public void run() {
					checkLicensing();
				}
			};
			timer.schedule(5);
		}
	}

	@Override
	protected SectionStackSection[] getSections() {
		return null;
	}

	public void loadProcessViewer() {
		isInitialized = true;
		drawingViewID = TS_VIEW_PREFIX + processName;
		modelId = TS_MODEL_PREFIX + processName;
		final TSWebProject project = new TSWebProject(projectID,
				projectLocation);
		TSCallback<TSProjectData> loadProjectCallback = new TSCallback<TSProjectData>() {
			@Override
			public void onSuccess(TSProjectData returnData) {
				try {
					project.newDefaultModel(moduleName, modelId);
					final TSDrawingViewWidget drawingViewWidget = project
							.newDrawingView(moduleName, modelId, drawingViewID,
									drawingViewName);
					TSWebModelViewCoordinator tsWebModelViewCoordinator = TSWebModelViewCoordinators
							.get(modelId);

					final TSWebViewClient view = tsWebModelViewCoordinator
							.getView(drawingViewID);

					final Widget w = view.getWidget();
					w.setHeight("100%");
					w.setWidth("100%");
					w.getElement().getStyle().setOverflow(Overflow.HIDDEN);
					project.create(new TSCallback<TSWebProjectCommandResult>() {
						public void onSuccess(
								TSWebProjectCommandResult returnData) {
							populateViewer();
							if (!isLocalizedTitleLoaded) {
								localizeTitle(drawingViewWidget);
							}
						}					

						public void onFailure(Throwable th) {
							GWT.log("Failed to load project", th);
						}

					});

					parentCanvas.addChild(w);
					// Event.addNativePreviewHandler(new NativeEventHandler());
				} catch (TSWebProjectException e) {
					TSErrorDialogBox errorDialog = new TSErrorDialogBox(e);
					errorDialog.center();
				}
			}

			@Override
			public void onFailure(Throwable th) {
				new TSErrorDialogBox(th.getMessage()).center();
			}
		};

		project.loadProject(loadProjectCallback);
	}
	
	/**
	 * populate the viewer
	 */
	protected void populateViewer() {
		ProcessCommandUtil.load(this, processArtifactPath, artifactExtention,
				process);
	}

	/**
	 * Call this onSave()
	 */
	protected void saveProcess() {
		ProcessCommandUtil.save(this, processArtifactPath, artifactExtention);
	}

	/**
	 * Show the palette
	 */
	public void showPalette() {
		showPalettePane = true;
		WebStudio.get().getEditorPanel().getLeftPane()
				.setVisible(showPalettePane);
		WebStudio.get().getEditorPanel().getLeftPane()
				.addChild(ProcessEditorFactory.getPalette());
		WebStudio.get().getEditorPanel()
				.setPalettePane(ProcessEditorFactory.getPalette());
		WebStudio.get().getEditorPanel().getLeftPane()
				.setAlign(Alignment.RIGHT);
		WebStudio.get().getEditorPanel().getLeftPane().setVisible(true);
	}

	public String getSelectedPalette() {
		return selectedPalette;
	}

	public void setSelectedPalette(String selectedPalette) {
		this.selectedPalette = selectedPalette;
	}

	public String getProjectName() {
		return projectName;
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}

	@Override
	public String getEditorUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method is used to show the property data.
	 * 
	 * @param data
	 */
	public void showProperties(Property data) {
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		if (null == processPropertyTab) {
			processPropertyTab = new ProcessPropertyTab();
		}
		if (!showPropertyPane
				&& !WebStudio.get().getEditorPanel().getBottomPane()
						.isVisible()) {
			showPropertyPane = true;
		}
		processPropertyTab.refresh(this, data);
		WebStudio.get().getEditorPanel().getPropertiesPane()
				.fillProperties(getProcessPropertyTab());
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		WebStudio.get().getEditorPanel().getBottomContainer().setSelectedTab(0);
		LoadingMask.clearMask();
		this.getPane().setCanFocus(true);
		this.getPane().focus();
	}

	/**
	 * Fetch the properties of given type for given id.
	 * 
	 * @param id
	 * @param propertyType
	 */
	public void fetchProperties(String id, String propertyType) {
		ProcessCommandUtil.fetchProperty(this, id, propertyType);
	}

	/**
	 * Redo the process data
	 */
	private void redo() {
		ProcessCommandUtil.redo(this);
	}

	/**
	 * Delete the selected nodes and edges
	 */
	public void delete() {
		ProcessCommandUtil.delete(this);
	}

	public void copy() {
		// TODO Auto-generated method stub
	}

	public void paste() {
	}

	public void cut() {
		// TODO Auto-generated method stub
	}

	/**
	 * Undo the process data
	 */
	public void undo() {
		ProcessCommandUtil.undo(this);
	}

	/**
	 * This method is used to update the property and palette pane associated
	 * with the current view.
	 */
	public void updatePropertyAndPalettePane() {
		AbstractEditor abstractEditor = (AbstractEditor) WebStudio.get()
				.getEditorPanel().getSelectedTab();
		if (abstractEditor instanceof AbstractProcessEditor) {
			String id = ProcessConstants.PROCESS_ID;
			((AbstractProcessEditor) abstractEditor).fetchProperties(id,
					ProcessConstants.GENERAL_PROPERTY);

		} else {
			WebStudio.get().getEditorPanel().getLeftPane().setVisible(false);
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
		}
	}

	/**
	 * @return the drawingViewID
	 */
	public String getDrawingViewID() {
		return drawingViewID;
	}

	/**
	 * @return the showPropertyPane
	 */
	public boolean isShowPropertyPane() {
		return showPropertyPane;
	}

	/**
	 * @param showPropertyPane
	 *            the showPropertyPane to set
	 */
	public void setShowPropertyPane(boolean showPropertyPane) {
		this.showPropertyPane = showPropertyPane;
	}

	/**
	 * @return the showPalettePane
	 */
	public boolean isShowPalettePane() {
		return showPalettePane;
	}

	/**
	 * @param showPalettePane
	 *            the showPalettePane to set
	 */
	public void setShowPalettePane(boolean showPalettePane) {
		this.showPalettePane = showPalettePane;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.smartgwt.client.widgets.tab.Tab#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		try {
			super.setTitle(title);
		} catch (Exception exception) {

		}

	}

	/**
	 * This method is used to publish the java script method.
	 * 
	 * @param processEditor
	 */
	public static native void publish(AbstractProcessEditor processEditor)/*-{

		//Delete process element call back
		$wnd.deleteProcessElement = $entry(function() {
			return processEditor.@com.tibco.cep.webstudio.client.process.AbstractProcessEditor::delete()();
		});

		//Undo process elemnt call back
		$wnd.undoProcessElement = $entry(function() {
			return processEditor.@com.tibco.cep.webstudio.client.process.AbstractProcessEditor::undo()();
		});

		//Redo Process Element call back
		$wnd.redoProcessElement = $entry(function() {
			return processEditor.@com.tibco.cep.webstudio.client.process.AbstractProcessEditor::redo()();
		});

	}-*/;

	public void createNewProcess() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the isInitialized
	 */
	public boolean isInitialized() {
		return isInitialized;
	}

	/**
	 * @param isInitialized
	 *            the isInitialized to set
	 */
	public void setInitialized(boolean isInitialized) {
		this.isInitialized = isInitialized;
	}

	/**
	 * Validate the process
	 */
	private void validate() {
		indeterminateProgress(processMessages.validationWait(), false);
		addHandlers(this);
		ProcessCommandUtil.validate(this);
	}

	/**
	 * This method is used to show the validation error message
	 * 
	 * @param validationResult
	 */
	public void showProblems(List<Serializable> validationResult) {
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);

		List<ProblemRecord> existingErrors = WebStudio.get().getEditorPanel()
				.getProblemsPane().getErrorRecords();
		if (null != existingErrors && !existingErrors.isEmpty()) {
			List<ProblemRecord> processRecords = new LinkedList<ProblemRecord>();
			for (ProblemRecord problemRecord : existingErrors) {
				if (null != problemRecord) {
					String extension = problemRecord.getAttribute("Extension");
					if (null != extension && !extension.trim().isEmpty()
							&& ".beprocess".equals(extension)) {
						processRecords.add(problemRecord);
					}
				}
			}
			existingErrors.removeAll(processRecords);
		}
		List<ProblemRecord> errorRecords = ProcessValidationUtils
				.getProblemRecord(projectName, processName,
						processArtifactPath, artifactExtention,
						validationResult, processMessages);
		existingErrors.addAll(errorRecords);
		WebStudio.get().getEditorPanel().getProblemsPane().getErrorRecords()
				.addAll(0, existingErrors);
		WebStudio.get().getEditorPanel().getProblemsPane()
				.refreshProblemsGrid();
		WebStudio.get().getEditorPanel().showProblemPane();

	}

	/**
	 * This method is sued to show the operation in progress message.
	 * 
	 * @param message
	 * @param done
	 */
	public static void indeterminateProgress(String message, boolean done) {
		if (done) {
			SC.clearPrompt();
		} else {
			SC.showPrompt(Canvas.imgHTML(Page.getAppImgDir()
					+ "icons/16/wait.gif")
					+ message);
		}
	}

	/**
	 * @return the processPropertyTab
	 */
	public ProcessPropertyTab getProcessPropertyTab() {
		return processPropertyTab;
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		if (event.isCtrlKeyDown() && event.getKeyName().equals("Z")) {
			event.cancel();
			undo();
		} else if (event.isCtrlKeyDown() && event.getKeyName().equals("R")) {
			event.cancel();
			redo();
		} else if (event.getKeyName().equals("Delete")) {
			event.cancel();
			delete();
		} else {
			super.onKeyPress(event);
		}
	}

	/**
	 * This method is used to send save process data.
	 * 
	 * @param object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sendSaveRequest(Object object) {
		addHandlers(AbstractProcessEditor.this);
		process = object.toString();
		process = process.substring(process.indexOf("process") - 1,
				process.length());
		ProcessDataItem dataItem = new ProcessDataItem(process,
				processArtifactPath);
		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_PROJECT_NAME,
				projectName);
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM,
				dataItem);
		String xmlData = new SerializeArtifact().generateXML(
				ServerEndpoints.RMS_POST_ARTIFACT_SAVE, requestParameters);
		if (null == request) {
			request = new HttpRequest();
		}
		request.clearRequestParameters();
		request.setMethod(HttpMethod.POST);
		request.setPostData(xmlData);
		String url = ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL();
		request.submit(url);
	}

	public String getProcessArtifactPath() {
		return processArtifactPath;
	}

	public String getModelId() {
		return modelId;
	}

	/**
	 * This method is used to set localized data for tom swayer toolbar item
	 * tooltip
	 * 
	 * @param drawingViewWidget
	 */
	private void localizeTitle(final TSDrawingViewWidget drawingViewWidget) {
		final TSToolbarWidget toolbarWidget = drawingViewWidget.getNorthToolbar();
		Event.sinkEvents(toolbarWidget.getElement(), Event.ONMOUSEOVER);
		Event.setEventListener(toolbarWidget.getElement(), new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				if (Event.ONMOUSEOVER == event.getTypeInt()) {
					List<UIObject> uiObjects = toolbarWidget.getAllItems();
					for (UIObject uiObject : uiObjects) {
						String propertyName = TITLE_PROPERTY + uiObject.getTitle().trim().toLowerCase().replaceAll(" ", ".");
						String title = processMessages.getPropertyValue(propertyName);
						if (null != title && !title.trim().isEmpty()) {
							uiObject.setTitle(title);
						}
					}
				}
			}
		});
	}
}
