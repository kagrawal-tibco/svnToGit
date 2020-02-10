/**
 * 
 */
package com.tibco.cep.webstudio.client.widgets;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RequestParameter;

/**
 * Base class for all Web Studio dialogs, setting up a basic dialog template with all the generic properties.
 * Specific properties are left up to the concrete instances to set along with the widget list.
 * 
 * @author Vikram Patil
 */
public abstract class AbstractWebStudioDialog extends Window implements
		CloseClickHandler {
	
	private boolean showButtonContainer;
	private boolean showOkButton;
	private boolean showCancelButton;
	private int dialogHeight;
	private int dialogWidth;
	private String dialogTitle;
	private String dialogHeaderIcon;
	protected IButton okButton, cancelButton;
	protected HLayout buttonContainer;
	
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected DTMessages dtMessages = (DTMessages)I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	
	private String cancelButtonText = globalMsgBundle.button_cancel();
	
	/**
	 * Default Constructor
	 */
	public AbstractWebStudioDialog() {
		super();
		this.showButtonContainer = true;
		this.showOkButton = true;
		this.showCancelButton = true;
	}
	
	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.events.CloseClickHandler#onCloseClick(com.smartgwt.client.widgets.events.CloseClientEvent)
	 */
	public void onCloseClick(CloseClickEvent event) {
		this.preDestroy();
		this.destroy();
		this.postDestory();
	}
	
	/**
	 * Any tasks that need to be performed before the dialog gets destroyed
	 */
	public void preDestroy(){}
	
	/**
	 * Any tasks that need to be performed just after the dialog gets destroyed
	 */
	public void postDestory() {}
	
	/**
	 * Configure and setup the Dialog properties
	 */
	public void initialize(){
		setWidth(dialogWidth);
		setHeight(dialogHeight);
		setTitle(dialogTitle);		
		addCloseClickHandler(this);
		setAutoCenter(true);
		setAutoSize(true);
		setIsModal(true);
		setShowModalMask(true);
		setHeaderIcon(dialogHeaderIcon);
		
		VLayout layout = new VLayout(10);
		layout.setAutoHeight();
		layout.setWidth100();
		layout.setLayoutBottomMargin(10);
		layout.setLayoutLeftMargin(10);
		layout.setLayoutRightMargin(10);
		layout.setLayoutTopMargin(12);
		
		for (Widget widget : getWidgetList()){
			layout.addMember(widget);
		}
		
		if (showButtonContainer){
			layout.addMember(createActionButtons());
		}

		addItem(layout);
	}
	/**
	 * To have button alignment on left side in RTL locale, set it true. 
	 * @param buttonOnLeftSideInRTL 
	 * */
	public void initialize(boolean buttonOnLeftSideInRTL){
		setWidth(dialogWidth);
		setHeight(dialogHeight);
		setTitle(dialogTitle);		
		addCloseClickHandler(this);
		setAutoCenter(true);
		setAutoSize(true);
		setIsModal(true);
		setShowModalMask(true);
		setHeaderIcon(dialogHeaderIcon);
		
		VLayout layout = new VLayout(10);
		layout.setAutoHeight();
		layout.setWidth100();
		layout.setLayoutBottomMargin(10);
		layout.setLayoutLeftMargin(10);
		layout.setLayoutRightMargin(10);
		layout.setLayoutTopMargin(12);
		
		for (Widget widget : getWidgetList()){
			layout.addMember(widget);
		}
		
		if (showButtonContainer){
			layout.addMember(createActionButtons(buttonOnLeftSideInRTL));
		}

		addItem(layout);
	}
	
	
	/**
	 * Create action buttons to save/cancel an action
	 * @return
	 */
	private HLayout createActionButtons(boolean buttonOnLeftSideInRTL){
		HLayout buttonContainer = new HLayout(10);
		buttonContainer.setWidth100();
		buttonContainer.setHeight100();
		
		if (buttonOnLeftSideInRTL) {
			if (LocaleInfo.getCurrentLocale().isRTL()) {
				buttonContainer.setAlign(Alignment.RIGHT);
			} else {
				buttonContainer.setAlign(Alignment.LEFT);
			} 
		}
		else{
			if (LocaleInfo.getCurrentLocale().isRTL()) {
				buttonContainer.setAlign(Alignment.LEFT);
			} else {
				buttonContainer.setAlign(Alignment.RIGHT);
			} 
		}
		addCustomWidget(buttonContainer);

		if (showOkButton){
			okButton = new IButton(globalMsgBundle.button_ok());
			okButton.setWidth(100);  
			okButton.setShowRollOver(true);  
			okButton.setShowDisabled(true);  
			okButton.setShowDown(true);
			okButton.disable();
			okButton.setAlign(Alignment.CENTER);
			okButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					onAction();
				}
			});
			buttonContainer.addMember(okButton);
		}

		if (showCancelButton){
			cancelButton = new IButton(cancelButtonText);
			cancelButton.setWidth(100);  
			cancelButton.setShowRollOver(true);  
			cancelButton.setShowDisabled(true);  
			cancelButton.setShowDown(true);
			cancelButton.setAlign(Alignment.CENTER);
			cancelButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					doCancel();
				}
			});
			buttonContainer.addMember(cancelButton);
		}		
		return buttonContainer;		
	}
	private HLayout createActionButtons() {
		buttonContainer = new HLayout(10);
		buttonContainer.setWidth100();
		buttonContainer.setHeight100();
		
		if(LocaleInfo.getCurrentLocale().isRTL()){
			buttonContainer.setAlign(Alignment.LEFT);	
		}
		else{
			buttonContainer.setAlign(Alignment.RIGHT);	
		}
		
		addCustomWidget(buttonContainer);

		if (showOkButton){
			okButton = new IButton(globalMsgBundle.button_ok());
			okButton.setWidth(100);  
			okButton.setShowRollOver(true);  
			okButton.setShowDisabled(true);  
			okButton.setShowDown(true);
			okButton.disable();
			okButton.setAlign(Alignment.CENTER);
			okButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					onAction();
				}
			});
			buttonContainer.addMember(okButton);
		}

		if (showCancelButton){
			cancelButton = new IButton(cancelButtonText);
			cancelButton.setWidth(100);  
			cancelButton.setShowRollOver(true);  
			cancelButton.setShowDisabled(true);  
			cancelButton.setShowDown(true);
			cancelButton.setAlign(Alignment.CENTER);
			cancelButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					doCancel();
				}
			});
			buttonContainer.addMember(cancelButton);
		}
		
		return buttonContainer;		
	}
	
	/**
	 * To add any custom widget within the container.
	 * 
	 * @param container
	 */
	protected void addCustomWidget(HLayout container) {
		// Do Nothing
	}
	
	/**
	 * List of widgets to be displayed on the target Dialog
	 * @return
	 */
	public abstract List<Widget> getWidgetList();
	
	/**
	 * Action to be performed on Ok button click
	 */
	public abstract void onAction();
	
	/**
	 * Destory's the current dialog window. Override for custom behavior
	 */
	protected void doCancel() {
		this.preDestroy();
		destroy();
		this.postDestory();
	}

	/**
	 * Cleaning up the cached data to fetch more/next data set
	 */
	protected void cleanupCache(ListGrid artifactGrid) {
		if (artifactGrid.getResultSet() != null){
			artifactGrid.getResultSet().invalidateCache();
		}
	}
	
	/**
	 * Creates a list of selected artifacts
	 * 
	 * @param artifactList
	 * @param addChangeType
	 * @return
	 */
	protected List<RequestParameter> getSelectedArtifactList(ListGridRecord[] artifactList, boolean addChangeType){
		List<RequestParameter> selectedArtifacts = new ArrayList<RequestParameter>(artifactList.length);
		
		RequestParameter parameter = null;
		for (ListGridRecord record : artifactList) {
			parameter = new RequestParameter(RequestParameter.REQUEST_PARAM_PATH, record.getAttribute(RequestParameter.REQUEST_PARAM_PATH));
			if (record.getAttribute(RequestParameter.REQUEST_PARAM_TYPE) != null) {
				parameter.add(RequestParameter.REQUEST_PARAM_TYPE, record.getAttribute("artifactType"));
			}
			if (record.getAttribute("fileExtension") != null) {
				parameter.add(RequestParameter.REQUEST_PARAM_FILE_EXTN,  record.getAttribute("fileExtension"));
			}
			if (record.getAttribute("changeType") != null && addChangeType) {
				parameter.add(RequestParameter.REQUEST_PARAM_CHANGE_TYPE, record.getAttribute("changeType"));
			}
			if (record.getAttribute("baseArtifactPath") != null) {
				parameter.add(RequestParameter.REQUEST_PARAM_BASE_ARTIFACT_PATH, record.getAttribute("baseArtifactPath"));
			}
			
			selectedArtifacts.add(parameter);
		}
		
		return selectedArtifacts;
	}
	
	/**
	 * Return cell color based on the artifact change type
	 * 
	 * @param changeType
	 * @return
	 */
	protected String getCellColorByChangeType(String changeType) {
		String cellColor = null;
		if (changeType.trim().toLowerCase().equals("modify") || changeType.trim().toLowerCase().equals("modified")) {
			cellColor = "color:darkblue;";
		} else if (changeType.trim().toLowerCase().equals("create") || changeType.trim().toLowerCase().equals("added") 
				|| changeType.trim().toLowerCase().equals("add")) {
			cellColor = "color:purple;";
		} else if (changeType.trim().toLowerCase().equals("delete")
				|| changeType.trim().toLowerCase().equals("deleted")) {
			cellColor = "color:maroon;";
		}
		return cellColor;
	}
	
	public boolean isShowButtonContainer() {
		return showButtonContainer;
	}

	public void setShowButtonContainer(boolean showButtonContainer) {
		this.showButtonContainer = showButtonContainer;
	}

	public boolean isShowOkButton() {
		return showOkButton;
	}

	public void setShowOkButton(boolean showOkButton) {
		this.showOkButton = showOkButton;
	}

	public boolean isShowCancelButton() {
		return showCancelButton;
	}

	public void setShowCancelButton(boolean showCancelButton) {
		this.showCancelButton = showCancelButton;
	}

	public int getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(int dailogHeight) {
		this.dialogHeight = dailogHeight;
	}

	public int getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(int dailogWidth) {
		this.dialogWidth = dailogWidth;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dailogTitle) {
		this.dialogTitle = dailogTitle;
	}

	public String getDialogHeaderIcon() {
		return dialogHeaderIcon;
	}

	public void setDialogHeaderIcon(String dialogHeaderIcon) {
		this.dialogHeaderIcon = dialogHeaderIcon;
	}
	
	public void setCancelButtonText(String text) {
		this.cancelButtonText = text;
	}
}
