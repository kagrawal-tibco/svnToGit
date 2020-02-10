package com.tibco.cep.webstudio.client.palette;

import static com.tibco.cep.webstudio.client.process.ProcessConstants.moduleName;
import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.types.VisibilityMode;
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
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.process.ProcessEditor;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * This class is used to show the process palette pane
 * @author sasahoo
 * 
 */
public class PalettePane extends Window implements ClickHandler,
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

	private Canvas parent;
	private SectionStack sectionStack;
	private Map<String, ToolStripButton> toolStripMap = new LinkedHashMap<String, ToolStripButton>();
	private Map<String, ActivateToolCallback> callBackMap = new LinkedHashMap<String, ActivateToolCallback>();

	private ToolStripButton refreshbutton;

	private String iconPath = Page.getAppImgDir() + "process/";

	private ProcessEditor fCurrentEditor;

	protected ProcessMessages processMessages = (ProcessMessages)I18nRegistry.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	protected HttpRequest request;

	public PalettePane(Canvas parent) {
		super();
		GWT.log("init Web Studio Palette Pane()...", null);
		this.parent = parent;
		// parent.setWidth(DEFAULT_WIDTH);
		init();
		setTitle(Canvas.imgHTML(iconPath + "palette.gif") + " " + processMessages.palette_title());
		this.request = new HttpRequest();
	}
	
	public void update(final String projectName) {
		PaletteToolbarService.Toolbar.getInstance().getPalette(
				new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						if (null == result || result.isEmpty()) {
							// Check whether palette has been already populated
							if (toolStripMap.size() == 0) {
								ArtifactUtil.addHandlers(PalettePane.this);

								if (PalettePane.this.request == null) {
									PalettePane.this.request = new HttpRequest();
								}
								PalettePane.this.request
										.clearRequestParameters();
								PalettePane.this.request
										.setMethod(HttpMethod.GET);
								PalettePane.this.request
										.addRequestParameters(new RequestParameter(
												RequestParameter.REQUEST_PROJECT_NAME,
												projectName));
								String url = ServerEndpoints.RMS_PROCESS_POPULATE_PALETTE_PANE
										.getURL();
								request.submit(url);
							}
						}else{
							PaletteToolbarService.Toolbar.getInstance().getToolbar(result, new PaletteToolbarCallback(PalettePane.this));
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}
				});

	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		if (event.getUrl().indexOf(ServerEndpoints.RMS_PROCESS_POPULATE_PALETTE_PANE.getURL()) != -1) {	
			Node paletteNode = event.getData().getElementsByTagName("palette").item(0);
			String paletteData = paletteNode.toString();
			PaletteToolbarService.Toolbar.getInstance().getToolbar(paletteData, new PaletteToolbarCallback(this));
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
		if (WebStudio.get().getEditorPanel().getSelectedTab() instanceof ProcessEditor) {
			fCurrentEditor  = (ProcessEditor)WebStudio.get().getEditorPanel().getSelectedTab();
		}
		createToolBar();
		update(fCurrentEditor.getProjectName());
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
			// Don't Destroy.. just hide for future use
			// this.destroy();
			close();
		}
		if (event.getSource() == refreshbutton) {

		}
	}

	public void close() {
		GWT.log("Closing Web Studio Decision Table Analyzer Pane...", null);
		this.setVisible(false);
		parent.setVisible(false);
		WebStudio.get().getEditorPanel().reflow();
	}

	@Override
	public void onResized(ResizedEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCloseClick(CloseClickEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRestoreClick(RestoreClickEvent event) {
		// TODO Auto-generated method stub
	}

	protected void createToolBar() {
		sectionStack = new SectionStack();
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setWidth100();
		sectionStack.setHeight100();
		sectionStack.setAlign(Alignment.LEFT);
		addItem(sectionStack);
	}

	public void populateToolbar(Palette result) {
		for (PaletteToolGroup grpitem : result.getPaletteToolGroup()) {
			SectionStackSection section = new SectionStackSection();
			String imgHTML = Canvas.imgHTML(iconPath + grpitem.getIconPath());
			section.setTitle(imgHTML + "<b>&nbsp;&nbsp;"
					+ getGroupTitle(grpitem.getGroupId()) + "</b>");
			sectionStack.addSection(section);
			populateToolbarItems(grpitem, section);
		}
	}

	private void populateToolbarItems(PaletteToolGroup  grpitem, SectionStackSection section) {
		ToolStrip toolStrip = new ToolStrip();
		toolStrip.setVertical(true);
		toolStrip.setAlign(Alignment.LEFT);
		toolStrip.setHeight100();
		toolStrip.setWidth(DEFAULT_WIDTH);
		toolStrip.setBackgroundColor("white");
		for (PaletteTool item : grpitem.getPaletteTools()) {

			ToolStripButton toolItem = new ToolStripButton();
			setItemTitleAndToolTip(item, toolItem);
			if (LocaleInfo.getCurrentLocale().isRTL()) {
				toolItem.setAlign(Alignment.RIGHT);
			}
			else{
				toolItem.setAlign(Alignment.LEFT);
			}
			toolItem.setWidth100();
			toolItem.setIcon(iconPath + item.getIconPath());
			// toolItem.setTitle(item.getElementType());
			toolItem.setActionType(SelectionType.RADIO);
			toolItem.setRadioGroup(moduleName);
			// toolItem.setTooltip(item.getTooltip());
			ActivateToolCallback callback = new ActivateToolCallback(
					item.getToolName(), item.getToolType(),
					item.getElementType(), item.getEmfType(),
					item.getExtendedType());
			callback.setItemId(item.getId());
			toolItem.addClickHandler(callback);
			toolStrip.addButton(toolItem);
			toolStripMap.put(item.getElementType(), toolItem);
			callBackMap.put(item.getElementType(), callback);

		}
		section.addItem(toolStrip);
	}

	public void resetSelectTool() {
		clearSelection();
		toolStripMap.get("Select Tool").setSelected(true);
	}

	public String getSelectedTool() {
		for (ToolStripButton button : toolStripMap.values()) {
			if (button.isSelected()) {
				return button.getTitle();
			}
		}
		return null;
	}

	public void setSelectedTool(String elementType) {
		clearSelection();
		toolStripMap.get(elementType).setSelected(true);
	}

	public void clearSelection() {
		for (ToolStripButton button : toolStripMap.values()) {
			button.setSelected(false);
		}
	}

	/**
	 * Set Palette Group Title
	 */

	private String getGroupTitle(String groupId) {
		String groupTitle = "";
		if ("general".equals(groupId)) {
			groupTitle = processMessages.paletteGeneralGroup();
		} else if ("activity".equals(groupId)) {
			groupTitle = processMessages.paletteTaskGroup();
		} else if ("gateway".equals(groupId)) {
			groupTitle = processMessages.paletteGatewaysGroup();
		} else if ("start.event".equals(groupId)) {
			groupTitle = processMessages.paletteStartEventsGroup();
		} else if ("end.event".equals(groupId)) {
			groupTitle = processMessages.paletteEndEventsGroup();
		}

		return groupTitle;
	}

	/**
	 * Set Palette item title and tooltip
	 */
	private void setItemTitleAndToolTip(PaletteTool item,
			ToolStripButton toolItem) {
		if(toolItem !=null) { 
			if(LocaleInfo.getCurrentLocale().isRTL()){
				toolItem.setAlign(Alignment.RIGHT);
			}
			else{
				toolItem.setAlign(Alignment.LEFT);
			}
		}
		if (null != item) {
			if ("general.note".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteAnnotation());
				toolItem.setTooltip(processMessages.paletteAnnotationToolTip());
			} else if ("general.association".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteAssociation());
				toolItem.setTooltip(processMessages.paletteAssociationToolTip());
			} else if ("general.sequence".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteSequence());
				toolItem.setTooltip(processMessages.paletteSequenceToolTip());
			} else if ("activity.ruleFunction".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteScript());
				toolItem.setTooltip(processMessages.paletteScriptToolTip());
			} else if ("activity.javatask".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteJavaTask());
				toolItem.setTooltip(processMessages.paletteJavaTaskToolTip());
			} else if ("activity.businessrule".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteBusinessTask());
				toolItem.setTooltip(processMessages
						.paletteBusinessTaskToolTip());
			} else if ("activity.sendEvent".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteSendMessage());
				toolItem.setTooltip(processMessages.paletteSendMessageToolTip());
			} else if ("activity.receiveEvent".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteReceiveMessage());
				toolItem.setTooltip(processMessages
						.paletteReceiveMessageToolTip());
			} else if ("activity.manual".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteManualTask());
				toolItem.setTooltip(processMessages.paletteManualTaskToolTip());
			} else if ("activity.webService".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteWebService());
				toolItem.setTooltip(processMessages.paletteWebServiceToolTip());
			} else if ("activity.inference".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteInferenceTask());
				toolItem.setTooltip(processMessages
						.paletteInferenceTaskToolTip());
			} else if ("activity.subProcess".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteSubProcess());
				toolItem.setTooltip(processMessages.paletteSubProcessToolTip());
			} else if ("activity.callActivity".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteCallAcitivity());
				toolItem.setTooltip(processMessages
						.paletteCallAcitivityToolTip());
			} else if ("gateway.exclusive".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteExclusiveGateway());
				toolItem.setTooltip(processMessages
						.paletteExclusiveGatewayToolTip());
			} else if ("gateway.parallel".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteParallelGateway());
				toolItem.setTooltip(processMessages
						.paletteParallelGatewayToolTip());
			} else if ("event.start.none".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteStartEvent());
				toolItem.setTooltip(processMessages.paletteStartEventToolTip());
			} else if ("event.start.message".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteMessageStartEvent());
				toolItem.setTooltip(processMessages
						.paletteMessageStartEventToolTip());
			} else if ("event.start.timer".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteTimerStartEvent());
				toolItem.setTooltip(processMessages
						.paletteTimerStartEventToolTip());
			} else if ("event.start.signal".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteSignalStartEvent());
				toolItem.setTooltip(processMessages
						.paletteSignalStartEventToolTip());
			} else if ("event.end.none".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteEndEvent());
				toolItem.setTooltip(processMessages.paletteEndEventToolTip());
			} else if ("event.end.message".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteMessageEndEvent());
				toolItem.setTooltip(processMessages
						.paletteMessageEndEventToolTip());
			} else if ("event.end.error".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteErrorEndEvent());
				toolItem.setTooltip(processMessages
						.paletteErrorEndEventToolTip());
			} else if ("event.end.signal".equals(item.getId())) {
				toolItem.setTitle(processMessages.paletteSignalEndEvent());
				toolItem.setTooltip(processMessages
						.paletteSignalEndEventToolTip());
			}
		}
	}
}
