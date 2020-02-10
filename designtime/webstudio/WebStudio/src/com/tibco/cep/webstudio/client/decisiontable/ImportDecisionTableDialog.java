package com.tibco.cep.webstudio.client.decisiontable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.NamedFrame;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.datasources.RMSManagedProjectDS;
import com.tibco.cep.webstudio.client.diff.DiffHelper;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.EditorFactory;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ErrorMessageDialog;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;
import com.tibco.cep.webstudio.client.widgets.ProjectResourcesTreeGrid;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * @author sasahoo
 *
 */
public class ImportDecisionTableDialog extends AbstractWebStudioDialog {
	
	final int WIDTH = 400;
	final int HEIGHT = 100;

	private TextItem nameTextItem;
	private TextItem parentFolderTextItem;
	private TreeGrid folderTreeGrid;
	private NavigatorResource selectedResource;
	private DynamicForm fileForm;
	private UploadItem fitem = null;
	private NamedFrame frame = null;
	private HiddenItem callbackItem = null;
	private HiddenItem overwriteItem = null;

	private String projectName = null;
	private String resourceType= null;	
	private String artifactName = null;
	private String folderPath = null;
	private boolean overwrite = true;
	
	protected DTMessages dtMsgs = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private static GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	public ImportDecisionTableDialog(NavigatorResource selectedResource, String resourceType) { 
		this(selectedResource, "", resourceType, CommonIndexUtils.DOT + CommonIndexUtils.RULE_FN_IMPL_EXTENSION);
		this.setTitle(globalMsgBundle.dialog_importartifact_title(globalMsgBundle.createNew_resource_dt()));
		this.setDialogWidth(WIDTH);
		this.setDialogHeight(HEIGHT);
		this.setHeaderIcon(Page.getAppImgDir() + "icons/16/import_DT.png");
	} 
	
	public ImportDecisionTableDialog(NavigatorResource selectedResource, String operation, String resourceType, String artifactExtn) { 
		this.selectedResource = selectedResource;
		this.resourceType = resourceType;
		this.projectName = selectedResource.getId().substring(0, selectedResource.getId().indexOf("$"));
		this.setDialogWidth(400);
		this.setDialogHeight(250);
		this.setDialogTitle(globalMsgBundle.dialog_importartifact_title(dtMessages.decisionTable()));
		this.initialize();
		setIsModal(false);
		setShowModalMask(false);
	} 

	@Override
	public List<Widget> getWidgetList() {
		List<Widget> widgetList = new ArrayList<Widget>();
		widgetList.add(createForm());
		return widgetList;
	}	

	protected VLayout createForm() {
		VLayout container = new VLayout(5);
		container.setWidth100();

		frame = new NamedFrame("uploadTarget");
		frame.setWidth("1px");
		frame.setHeight("1px");
		frame.setVisible(false);
		
		fileForm = new DynamicForm();
		fileForm.setWidth100();
		fileForm.setNumCols(2);
		fileForm.setAlign(Alignment.CENTER);
		fileForm.setEncoding(Encoding.MULTIPART);
		fileForm.setTarget("uploadTarget");
				
		fileForm.setAction(ServerEndpoints.RMS_DT_IMPORT.getURL());
		Map<String, String> map = new HashMap<String, String>();
		map.put("resourceName", globalMsgBundle.createNew_resource_existingResourceErrorMessage(globalMsgBundle.createNew_resource_dt()));
		fileForm.setErrors(map, false);
		
		String projectName = selectedResource.getId().substring(0,selectedResource.getId().indexOf("$"));
		String path = selectedResource.getId().substring(selectedResource.getId().indexOf("$"), selectedResource.getId().length()).replace("$", "/");
		String virtualRuleFunctionPath = path.substring(path.indexOf("/"), path.lastIndexOf('.'));
		String type = path.substring(path.lastIndexOf('.') + 1, path.length());
		
		HiddenItem projectNameItem = new HiddenItem(RequestParameter.REQUEST_PROJECT_NAME);
		projectNameItem.setDefaultValue(projectName);
		
		HiddenItem virtualRuleFunctionPathItem = new HiddenItem(RequestParameter.REQUEST_PARAM_PATH);
		virtualRuleFunctionPathItem.setDefaultValue(virtualRuleFunctionPath);
		
		HiddenItem typeItem = new HiddenItem(RequestParameter.REQUEST_PARAM_TYPE);
		typeItem.setDefaultValue(ARTIFACT_TYPES.valueOf(type.toUpperCase()).toString());
		
		HiddenItem fileExtensionItem = new HiddenItem(RequestParameter.REQUEST_PARAM_FILE_EXTN);
		fileExtensionItem.setDefaultValue(type);

		callbackItem = new HiddenItem("callbackName");

		nameTextItem = new TextItem("resourceName", globalMsgBundle.dialog_importartifact_name());
		nameTextItem.setWrapTitle(false);
		nameTextItem.setDisplayField("resourceName");
		nameTextItem.setOptionDataSource(RMSManagedProjectDS.getInstance());
		nameTextItem.setAlign(Alignment.LEFT);
		nameTextItem.setWidth(300);
		nameTextItem.setErrorIconSrc(Page.getAppImgDir() + "icons/16/error.png");

		nameTextItem.setShowErrorIcon(false);
		nameTextItem.setShowErrorText(false);
		nameTextItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				toggleOK();
			}
		});
		
		fitem = new UploadItem("selectResource", globalMsgBundle.dialog_importartifact_select());
		fitem.setDisplayField("selectResource");
		fitem.setAlign(Alignment.LEFT);
		fitem.setWrapTitle(false);
		fitem.setWidth(300);
		fitem.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				String fileName = fitem.getDisplayValue();
				try {
					fileName = fileName.replaceAll("(.*?)fakepath[\\\\/]", "");
				} catch (Exception ex) {
					//Do nothing
				}
				if (fileName != null) {
					int indx = fileName.indexOf(".");
					if (indx != -1) {
						fileName = fileName.substring(0, indx);
					}
					nameTextItem.setValue(fileName);
				}	
				toggleOK();
			}
		});
		
		String baseArtifactFolderPath = selectedResource.getId().substring(this.selectedResource.getId().indexOf("$"),
																			this.selectedResource.getId().lastIndexOf("$")).replace("$", "/");

		parentFolderTextItem = new TextItem("resourceFolderPath", globalMsgBundle.createNew_resource_parentFolder());
		parentFolderTextItem.setValue(baseArtifactFolderPath);
		parentFolderTextItem.setAlign(Alignment.LEFT);
		parentFolderTextItem.setWrapTitle(false);
		parentFolderTextItem.setWidth(300);
		parentFolderTextItem.setErrorIconSrc(Page.getAppImgDir() + "icons/16/error.png");
		parentFolderTextItem.setShowErrorIcon(false);
		parentFolderTextItem.setShowErrorText(false);
		parentFolderTextItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				toggleOK();

			}
		});
		
		overwriteItem = new HiddenItem("overwrite"); 
				
		fileForm.setFields(projectNameItem, virtualRuleFunctionPathItem, typeItem, callbackItem, fileExtensionItem, nameTextItem, 
																									fitem, parentFolderTextItem, overwriteItem);
		
		container.addMember(fileForm);
		container.addMember(frame);

		folderTreeGrid = new ProjectResourcesTreeGrid(projectName);
		folderTreeGrid.addRecordClickHandler(new RecordClickHandler() {			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				NavigatorResource record = (NavigatorResource) event.getRecord();				
				String folder = null;
				if (ARTIFACT_TYPES.PROJECT.getValue().equals(record.getType())) {
					folder = "/";
				} else {
					folder = record.getId().substring(record.getId().indexOf("$")).replace("$", "/");
				}	
				parentFolderTextItem.setValue(folder);
			}
		});
		container.addMember(folderTreeGrid);

		return container;
	}

	protected void toggleOK() {
		if(fitem.getValue() != null && nameTextItem.getValue() != null && parentFolderTextItem.getValue() != null){
			okButton.enable();
		}
		else{
			okButton.disable();
		}
	}

	@Override
	public void onAction() {
		boolean isNameValid = false; 
		boolean isParentFolderValid = false;
		boolean isFileValid = false; 
		artifactName = nameTextItem.getValueAsString().trim();

		folderPath = parentFolderTextItem.getValueAsString().trim();
		folderPath = folderPath.replace("\\", "/");
		if (!folderPath.startsWith("/")) {
			folderPath = "/" + folderPath;
		}
		if (folderPath.endsWith("/") && folderPath.length() > 1) {
			folderPath = folderPath.substring(0, folderPath.lastIndexOf("/"));
		}

		String resourceId = projectName + folderPath.replace("/", "$") + "$" + artifactName + CommonIndexUtils.DOT 
				+ CommonIndexUtils.RULE_FN_IMPL_EXTENSION;			

		if (artifactName.isEmpty()) {
			ErrorMessageDialog.showError(globalMsgBundle.createNew_resource_emptyResourceErrorMessage(resourceType));
		} else if (!ArtifactUtil.isValidArtifactName(artifactName)) {
			ErrorMessageDialog.showError(globalMsgBundle.createNew_resource_invalidResourceErrorMessage(artifactName, resourceType));
		} else {
			isNameValid = true;
		}

		if (folderPath.isEmpty()) {
			ErrorMessageDialog.showError(globalMsgBundle.createNew_resource_emptyResourceErrorMessage("folder"));
		} else {
			isParentFolderValid = true;
		}

		if (isNameValid) {
			Object valObj = fitem.getValue();
			Object fileName = null;
			if (valObj != null) {
				fileName = valObj;
				if (valObj instanceof Collection<?>) {
					Collection<?> collection = (Collection<?>) valObj;
					Iterator<?> itr = collection.iterator();
					if (itr.hasNext())
						fileName = itr.next();
				}
				if (fileName.toString().endsWith(".xls")) {
					isFileValid = true;
				} else {
					ErrorMessageDialog.showError(globalMsgBundle.dialog_importartifact_invalidFileMessage());
				}
			} else {
				ErrorMessageDialog.showError(globalMsgBundle.dialog_importartifact_noFileMessage());
			}
		}
		
		if (isNameValid && isParentFolderValid && isFileValid) {
			WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
			if (resourceNameExits(navGrid, resourceId)) {
				new ImportWarningDialog().draw();
			} else {
				this.overwrite = true;
				submitForm();
				okButton.disable();
			}	
		}
	}
	
	private boolean resourceNameExits(WebStudioNavigatorGrid navGrid, String resourceId) {
		String resourcePath = resourceId;
		if (resourceId.indexOf(".") != -1) {
			resourcePath = resourceId.substring(0, resourceId.lastIndexOf("."));
		}
		TreeNode[] allNodes = navGrid.getData().getAllNodes();
		for (TreeNode res : allNodes) {
			if (res instanceof NavigatorResource) {
				NavigatorResource navResource = (NavigatorResource) res;
				String nodePath = (navResource.getId().indexOf(".") != -1) ? navResource.getId().substring(0, navResource.getId().indexOf(".")) : navResource.getId();
				if (nodePath.toLowerCase().equals(resourcePath.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void submitForm() {
		overwriteItem.setValue(overwrite);
		com.google.gwt.dom.client.Element formElement = fileForm.getDOM().getFirstChildElement(); 
		submitFormAsync(formElement, ServerEndpoints.RMS_DT_IMPORT.getURL());
	}
	
	private native void submitFormAsync(com.google.gwt.dom.client.Element formElement, String url) /*-{
		var that = this;
		var xhr = new XMLHttpRequest();  	  
	  	var formData = new FormData(formElement);                	  	  
	
		xhr.open("POST", url);				  	  
		xhr.onreadystatechange = function() {
	        if (xhr.readyState == 4 && xhr.status == 200) {
				that.@com.tibco.cep.webstudio.client.decisiontable.ImportDecisionTableDialog::processResponse(Ljava/lang/String;)(xhr.responseText);
	        } else {
	        	@com.tibco.cep.webstudio.client.http.HttpRequest::showErrorMessage();
	        }		
	    };
	    xhr.send(formData); 
	}-*/;	
	
	private void processResponse(String responseMessage) {
		Document document = XMLParser.parse(responseMessage);
		Element responseDocElement = document.getDocumentElement();
		String statusValue = responseDocElement.getElementsByTagName("status").item(0).getFirstChild().getNodeValue();
		boolean isSuccess = (statusValue.equals("0")) ? true : false;
		if (isSuccess) {
			WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
			String parentFolder = projectName + folderPath.replace("/", "$");
			if (overwrite) {
				navGrid.getClickHandler().openDecisionTable(selectedResource, parentFolder, nameTextItem.getValueAsString() 
						+ CommonIndexUtils.DOT + CommonIndexUtils.RULE_FN_IMPL_EXTENSION);
			} else {						
				String dtName = nameTextItem.getValueAsString() + CommonIndexUtils.DOT + CommonIndexUtils.RULE_FN_IMPL_EXTENSION;
				String Id = parentFolder + "$" + dtName;
				NavigatorResource navigatorResource = navGrid.getResourceById(Id);
				if (navigatorResource == null) {
					navigatorResource = new DecisionTableNavigatorResource(dtName, parentFolder, selectedResource.getId(),
						Id,
						CommonIndexUtils.RULE_FN_IMPL_EXTENSION,
						Page.getAppImgDir() + "icons/16/decisiontable.png",
						ARTIFACT_TYPES.RULEFUNCTIONIMPL);
				}
				String tabTitle = nameTextItem.getValueAsString();
				Tab[] openTabs = WebStudio.get().getEditorPanel().getTabs();
				for (Tab tab : openTabs) {
					if (tab instanceof AbstractEditor) {
						if (tabTitle.equals(tab.getTitle())) {
							ProjectExplorerUtil.removeArtifactFromEditor(navigatorResource);
							WebStudio.get().showWorkSpacePage();
						}
					}
				}
				IEditorFactory editorFactory = EditorFactory.getArtifactEditorFactory(navigatorResource);
				if (editorFactory instanceof DecisionTableEditorFactory) {
					DecisionTableEditorFactory dtEditorFactory = (DecisionTableEditorFactory) editorFactory;
					DecisionTableEditor dtEditor = (DecisionTableEditor) dtEditorFactory.createEditor(navigatorResource, true, null, false, true, null);
					if (dtEditor != null) {
						dtEditor.setTitle(tabTitle);
						dtEditor.setMerge(true);
						Node previousVersionDocNode = DiffHelper.getPreviousVersionNode(responseDocElement);
						Node currentVersionDocNode = DiffHelper.getCurrentVersionNode(responseDocElement);
						if (previousVersionDocNode != null && currentVersionDocNode != null) {
							 //DT diff functionality, compute and merge DT diff
							DiffHelper.processDTDiff(previousVersionDocNode, currentVersionDocNode, dtEditor.modifications);
							dtEditor.init(currentVersionDocNode, null, false);
						}							
						dtEditor.makeDirty();
						navGrid.getClickHandler().setSelectedResource(navigatorResource);
						ProjectExplorerUtil.captureRecordSelection(navigatorResource);
						WebStudio.get().showWorkSpacePage();
						WebStudio.get().getEditorPanel().addTab(dtEditor);
						WebStudio.get().getEditorPanel().selectTab(dtEditor); 
					}
					ProjectExplorerUtil.toggleToolbarOptions(navigatorResource, WebStudioToolbar.TOOL_STRIP_VALIDATE_ID);
				}
			}	
		} else {
			ErrorMessageDialog.showError(responseMessage);
		}
		this.destroy();
	}
		
	private class ImportWarningDialog extends Dialog implements ClickHandler {
 
		protected DynamicForm fileForm;
		protected IButton okButton, cancelButton;
		private RadioGroupItem radioGroupItem = null;
		
		private ImportWarningDialog() {
			init();
		}
				
		private void init() {
			this.setAutoSize(true); 
			this.setShowToolbar(false); 
			this.setCanDragReposition(true);
			this.setTitle(globalMsgBundle.dialog_importartifact_title(dtMessages.decisionTable())); 
			this.setShowModalMask(false);
			this.setIsModal(true);

			fileForm = new DynamicForm();
			fileForm.setWidth100();
			fileForm.setNumCols(5);
			fileForm.setAlign(Alignment.CENTER);		

			radioGroupItem = new RadioGroupItem();
			radioGroupItem.setTitle(dtMsgs.wsdtimportwarningtitle());
			radioGroupItem.setTitleOrientation(TitleOrientation.TOP);
			radioGroupItem.setValueMap(dtMsgs.wsdtimportoverwriteoption(), dtMsgs.wsdtimportmergeoption());
			radioGroupItem.setDefaultValue(dtMsgs.wsdtimportoverwriteoption());
			radioGroupItem.setColSpan(5);
			radioGroupItem.addChangedHandler(new ChangedHandler() {
		        public void onChanged(ChangedEvent event) {
		        	if (event.getValue().equals(dtMsgs.wsdtimportoverwriteoption())) {
		        		overwrite = true;
		        	} else {
		        		overwrite = false;
		        	}
		        }
		    });
			fileForm.setFields(radioGroupItem);
			
			okButton = new IButton(globalMsgBundle.button_ok());   
			okButton.addClickHandler(this);

			cancelButton = new IButton(globalMsgBundle.button_cancel());   
			cancelButton.addClickHandler(this);
			
			HStack hstack = new HStack(10);
			hstack.setAlign(Alignment.RIGHT);
			hstack.setAlign(VerticalAlignment.BOTTOM);
			hstack.addMember(okButton);   			
			hstack.addMember(cancelButton);

			VStack vStack = new VStack(10);   
			vStack.addMember(fileForm);
			vStack.addMember(hstack);
			
			this.addItem(vStack);
		} 
		
		/* (non-Javadoc)
		 * @see com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt.client.widgets.events.ClickEvent)
		 */
		public void onClick(ClickEvent event) {   
			if (event.getSource() == okButton) {
				submitForm();
				markForDestroy();          
				hide();        
			}
			if (event.getSource() == cancelButton) {
				markForDestroy();          
				hide();        
			}
		}   
	}
	
}