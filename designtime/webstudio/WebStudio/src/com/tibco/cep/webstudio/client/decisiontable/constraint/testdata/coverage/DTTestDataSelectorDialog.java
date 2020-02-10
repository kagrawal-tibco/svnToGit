package com.tibco.cep.webstudio.client.decisiontable.constraint.testdata.coverage;

import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.RuleFunctionNavigatorResource;
import com.tibco.cep.webstudio.client.decisiontable.constraint.DecisionTableAnalyzerComponent;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.PROPERTY_TYPES;
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
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;

/**
 * Dialog to create new Business Rule / Decision Table.
 * @author vdhumal
 *
 */
public class DTTestDataSelectorDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {

	private String currentProjectName;
	private Table decisionTable;
	private List<ArtifactDetail> testDataArtifacts;
	
	private DynamicForm fileForm;
	private SelectItem argumentResourceSelector;
	private TestDataResourcesTreeGrid resourceTreeGrid;
	protected NavigatorResource selectedTestDataResource;
	
	protected GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected DTMessages dtMsgs = (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);

	public DTTestDataSelectorDialog(String projectName, Table decisionTable, List<ArtifactDetail> testDataArtifacts) {
		super();
		setTitle("Show TestData Coverage");
		this.currentProjectName = projectName;
		this.decisionTable = decisionTable;
		this.testDataArtifacts = testDataArtifacts;
		this.setDialogWidth(400);
		this.setDialogHeight(250);
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
	
		resourceTreeGrid = new TestDataResourcesTreeGrid(currentProjectName);
		
		fileForm = new DynamicForm();
		fileForm.setWidth100();
		fileForm.setNumCols(2);
		fileForm.setAlign(Alignment.CENTER);

		String[] arguments = getArgumentEntityPath(decisionTable);
		
		argumentResourceSelector = new SelectItem("argumentResource", dtMsgs.argument());
		argumentResourceSelector.setMultiple(false);
		argumentResourceSelector.setAlign(Alignment.LEFT);
		argumentResourceSelector.setWrapTitle(false);
		argumentResourceSelector.setWidth(300);
		argumentResourceSelector.setErrorIconSrc(Page.getAppImgDir() + "icons/16/error.png");
		argumentResourceSelector.setValueMap(arguments);
		argumentResourceSelector.setValue(arguments.length > 0 ? arguments[0] : "");
		argumentResourceSelector.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				resourceTreeGrid.refreshTree(argumentResourceSelector.getValue().toString(), testDataArtifacts);				
			}
		});
		resourceTreeGrid.refreshTree(argumentResourceSelector.getValue().toString(), testDataArtifacts);
		
		fileForm.setFields(argumentResourceSelector);
		container.addMember(fileForm);
		
		resourceTreeGrid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				NavigatorResource record = (NavigatorResource) event.getRecord();
				if (ARTIFACT_TYPES.CONCEPTTESTDATA.getValue().equals(record.getType())
						|| ARTIFACT_TYPES.EVENTTESTDATA.getValue().equals(record.getType())) {
					selectedTestDataResource = (NavigatorResource) event.getRecord();
				} else {
					selectedTestDataResource = null;
				}
				toggleOK();
			}
		});

		container.addMember(resourceTreeGrid);		
		return container;
	}
	
	private String[] getArgumentEntityPath(Table decisionTable) {
		List<ArgumentData> arguments = decisionTable.getArguments();
		List<String> argumentsPath = new ArrayList<String>();
		for (ArgumentData argumentResource : arguments) {
			if (PROPERTY_TYPES.CONCEPT_ENTITY.getLiteral().equalsIgnoreCase(argumentResource.getResourceType())
					|| PROPERTY_TYPES.EVENT_ENTITY.getLiteral().equalsIgnoreCase(argumentResource.getResourceType())) {
				argumentsPath.add(argumentResource.getPropertyPath());
			}	
		}		
		return argumentsPath.toArray(new String[argumentsPath.size()]);
	}
	
	@Override
	public void onAction() {
		String path = selectedTestDataResource.getId().substring(selectedTestDataResource.getId().indexOf("$"), selectedTestDataResource.getId().length()).replace("$", "/");
		String testDataResourcePath = path.substring(path.indexOf("/"));
			
		String decisionTablePath = decisionTable.getFolder() + decisionTable.getName();
		DecisionTableAnalyzerComponent testDataCoverageRequest = new DecisionTableAnalyzerComponent(currentProjectName, decisionTablePath);
		ArtifactDetail testDataArtifact = null;
		for (ArtifactDetail testDataArtifact2 : testDataArtifacts) {
			if (testDataResourcePath.equals(testDataArtifact2.getArtifactPath())) {
				testDataArtifact = testDataArtifact2;
			}
		}
		testDataCoverageRequest.addTestDataArtifact(testDataArtifact);

		Document rootDocument = XMLParser.createDocument();
		testDataCoverageRequest.serialize(rootDocument, rootDocument);		
		String xmlData = rootDocument.toString();

		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.POST);

		request.submit(ServerEndpoints.RMS_DECISION_TABLE_SHOW_COVERAGE.getURL());		
	}
	
	protected void toggleOK() {
		if(selectedTestDataResource != null) {
			okButton.enable();
		}
		else {
			okButton.disable();
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DECISION_TABLE_SHOW_COVERAGE.getURL()) != -1) {
			validEvent = true;
			DTTestDataCoveragePane testDataCoveragePane = new DTTestDataCoveragePane(event.getData());
			WebStudio.get().getEditorPanel().getBottomContainer().addTab(testDataCoveragePane);
			Node artifactDetailsNode = event.getData().getElementsByTagName("artifactDetails").item(0);
			testDataCoveragePane.loadTestDataCoverageResults(artifactDetailsNode);
			WebStudio.get().getEditorPanel().getBottomContainer().selectTab(testDataCoveragePane);
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
		}
		
		if (validEvent) {
			removeHandlers(this);
			this.destroy();
		}	
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;		
		if (event.getUrl().indexOf(ServerEndpoints.RMS_DECISION_TABLE_SHOW_COVERAGE.getURL()) != -1) {
			validEvent = true;
		}
		if (validEvent) {
//			String responseMessage = event.getData().getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//			showError(responseMessage);		
			removeHandlers(this);		
		}		
	}	
}

class TestDataResourcesTreeGrid extends TreeGrid {
	
	protected final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
	protected final String ROOT_NODE = "Root";
	protected String projectName = null;
	protected Tree resourceTree;
	protected TreeNode projectNode;
	
	TestDataResourcesTreeGrid(String projectName) {
		this.projectName = projectName;
		setWidth(300);
		setHeight(200);
		setShowAllRecords(false);  
		setShowHeader(false);
		setShowOpenIcons(false);
		setShowDropIcons(false);
		setClosedIconSuffix("");
		setFixedFieldWidths(false);
		setIndentSize(23);
		setSelectionType(SelectionStyle.SINGLE);
		setLayoutAlign(Alignment.RIGHT);
		setBorder("1px solid lightgray");
		// initialize by setting field data 
		setFields(new TreeGridField("name", "Name"));  
		setOverflow(Overflow.AUTO);
		addSort(new SortSpecifier("name", SortDirection.ASCENDING));
		
		//Setup the tree
		resourceTree = new Tree();
		resourceTree.setModelType(TreeModelType.PARENT);
		resourceTree.setIdField("id");
		resourceTree.setParentIdField("parent");
		resourceTree.setNameProperty("name");
		resourceTree.setReportCollisions(false);
		TreeNode root = new TreeNode ("Root");
		resourceTree.setRoot(root);		
	}
	
	protected void refreshTree(String entityPath, List<ArtifactDetail> artifactList) {
		clearResourceTree();
		List<ArtifactDetail> artifactFilterList = new ArrayList<ArtifactDetail>(); 
		for (ArtifactDetail artifactDetail : artifactList) {			
			if (entityPath.equalsIgnoreCase(artifactDetail.getBaseArtifactPath())) {
				artifactFilterList.add(artifactDetail);
			}	
		}
		NavigatorResource[] resourceList = ProjectExplorerUtil.createProjectResources(artifactFilterList, projectName);				
		for (int i = 0; i < resourceList.length; i++) {
			buildResourceTree(resourceList[i]);
		}
		if (projectNode == null) {
			buildEmptyProjectTree();
		}
		resourceTree.openFolder(projectNode);					
		// set up the field data
		this.setData(resourceTree);	
	}
			
	protected void buildResourceTree(NavigatorResource navResource) {
		TreeNode parentFolder = resourceTree.getRoot();
		TreeNode resourceNode = null;
		String resourcePath = "";
		String[] parts = navResource.getId().split("\\$");
	
		for (int i = 0; i < parts.length; i++) {
			String prevParent = resourcePath;
			resourcePath += (resourcePath.isEmpty()) ? parts[i] : "$"
					+ parts[i];
			resourceNode = resourceTree.findById(resourcePath);
			if (resourceNode == null) {
				if (prevParent == "") {
					resourceNode = new NavigatorResource(parts[i], ROOT_NODE,
							parts[i], ARTIFACT_TYPES.PROJECT.getValue(),
							ICON_PATH + "studioproject.gif",
							ARTIFACT_TYPES.PROJECT);
					projectNode = resourceNode;
				} else {
					if (resourcePath.equals(navResource.getId())) {
						if (parts[i].contains("concepttestdata")) {
							resourceNode = new NavigatorResource(
									parts[i], prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/",
											"$"),
									ARTIFACT_TYPES.CONCEPTTESTDATA.getValue(),
									ICON_PATH + "conceptTestData.png",
									ARTIFACT_TYPES.CONCEPTTESTDATA);
						} else if (parts[i].contains("eventtestdata")) {
							resourceNode = new NavigatorResource(parts[i],
									prevParent.replace("/", "$"),
									(prevParent + "$" + parts[i]).replace("/", "$"),
									ARTIFACT_TYPES.EVENTTESTDATA.getValue(),
									ICON_PATH + "eventTestData.png", 
									ARTIFACT_TYPES.EVENTTESTDATA);
						}
					} else {
						resourceNode = new NavigatorResource(
								parts[i],
								prevParent.replace("/", "$"),
								(prevParent + "$" + parts[i]).replace("/", "$"),
								ARTIFACT_TYPES.FOLDER.getValue(), ICON_PATH
										+ "folder.png", ARTIFACT_TYPES.FOLDER);
					}
				}	
				resourceTree.add(resourceNode, parentFolder);
			}
			parentFolder = resourceNode;
		}
	}
		
	protected void buildEmptyProjectTree() {
		TreeNode parentFolder = resourceTree.getRoot();
		TreeNode resourceNode = new NavigatorResource(projectName, ROOT_NODE,
				projectName, ARTIFACT_TYPES.PROJECT.getValue(),
				ICON_PATH + "studioproject.gif",
				ARTIFACT_TYPES.PROJECT);
		projectNode = resourceNode;
		resourceTree.add(resourceNode, parentFolder);		
	}
	
	public void clearResourceTree() {
		TreeNode[] allNodes = resourceTree.getAllNodes();
		for (TreeNode treeNode : allNodes) {
			resourceTree.remove(treeNode);
		}
	}
	
}	
