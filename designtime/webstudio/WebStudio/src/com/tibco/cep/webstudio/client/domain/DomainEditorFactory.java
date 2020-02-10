package com.tibco.cep.webstudio.client.domain;

import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.getBEDate;
import static com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils.indeterminateProgress;
import static com.tibco.cep.webstudio.client.problems.ProblemMarkerUtils.updateProblemRecords;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.addHandlers;
import static com.tibco.cep.webstudio.client.util.ArtifactUtil.removeHandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableUtils;
import com.tibco.cep.webstudio.client.domain.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.webstudio.client.domain.model.Domain;
import com.tibco.cep.webstudio.client.domain.model.DomainEntry;
import com.tibco.cep.webstudio.client.domain.model.RangeEntry;
import com.tibco.cep.webstudio.client.domain.model.SingleEntry;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.editor.IEditorFactory;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor.TemplateInstanceChangeHandler;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.DomainMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.ArtifactDetail;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.problems.ProblemRecord;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.client.util.RuleTemplateHelper;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.AbstractWebStudioDialog;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.client.widgets.WebStudioToolbar;

/**
 * Singleton factory implementation to create Domain editors
 * @author vdhumal
 *
 */
public class DomainEditorFactory implements IEditorFactory {

	protected static GlobalMessages globalMsg = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected static DomainMessages domainMessages = (DomainMessages)I18nRegistry.getResourceBundle(I18nRegistry.DOMAIN_MESSAGES);

	private static DomainEditorFactory instance;
	
	public static DomainEditorFactory getInstance() {
		if (instance == null) {
			instance = new DomainEditorFactory();
		}
		return instance;
	}
	
	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord) {
		return new DomainEditor(selectedRecord);
	}

	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {		
		return new DomainEditor(selectedRecord, isReadOnly, revisionId);
	}

	public class DomainEditor extends AbstractEditor implements ClickHandler, ChangedHandler {
		
		public static final int TOOL_BAR_HEIGHT = 20;
		public static final String RANGE_VALUE_SEPARATOR = ",";

		private Label emptyMessage = null;
		private TextAreaItem descTextItem = null;
		private SelectItem domainTypeItem = null;
		private TextItem superDomainTextItem = null;
		private ButtonItem domainsBrowseButtonItem = null;
		private ListGrid entriesGrid = null;
		private RadioGroupItem rangeRadioGroupItem = null;
		
		private TextItem simpleTextItem = null;
		private RadioGroupItem booleanRadioGroupItem = null;
		
		private TextItem lowerTextItem = null;
		private CheckboxItem lowerIncludedItem = null;
		private TextItem upperTextItem = null;
		private CheckboxItem upperIncludedItem = null;

		private DateTimeItem simpleDateTimeItem = null;
		private DateTimeItem lowerDateTimeItem = null;
		private CheckboxItem lowerDateIncludedItem = null;
		private DateTimeItem upperDateTimeItem = null;
		private CheckboxItem upperDateIncludedItem = null;

		private ListGridRecord selectedEntry = null;
		
		private VLayout entriesGridLayout = null;
		private Layout rangeRadioGroupLayout = null;
		private Layout simpleDetailsLayout = null;
		private Layout booleanDetailsLayout = null;
		private Layout simpleDateDetailsLayout = null;
		private Layout rangeDateDetailsLayout = null;
		private Layout rangeDetailsLayout = null;
		
		private ToolStrip domainToolStrip;
		protected ToolStripButton domainAddbutton;
		protected ToolStripButton domainRemovebutton;
		protected ToolStripButton domainDupebutton;
		
		private Domain domain = null;
		private String domainPath = null;
				
		public DomainEditor(NavigatorResource record) {
			super(record, true, false, null, true, false);
			this.setShowLoading(false);
			this.initialize();
		}

		public DomainEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
			super(selectedRecord, isReadOnly, revisionId, true);
			this.setShowLoading(false);
			this.initialize();
		}
	
		@Override
		public void onSuccess(HttpSuccessEvent event) {
			boolean isValidEvent = false;
			String message = "";
			if ((event.getUrl().indexOf(ServerEndpoints.RMS_GET_ARTIFACT_CONTENTS.getURL()) != -1
					|| event.getUrl().indexOf(ServerEndpoints.RMS_GET_WORKLIST_ITEM_REVIEW.getURL()) != -1)
					&& event.getUrl().indexOf(RequestParameter.REQUEST_PARAM_FILE_EXTN + "=domain") != -1) {
				Element docElement = event.getData();
				domainPath = docElement.getElementsByTagName("artifactPath").item(0).getFirstChild().getNodeValue();				
				init(docElement);				
				if (isReadOnly()) {
					makeReadOnly();
				}
				isValidEvent = true;
			} else if (event.getUrl().indexOf(ServerEndpoints.RMS_POST_ARTIFACT_SAVE.getURL()) != -1) {
				isValidEvent = true;				
				super.postSave();				
				if (this.isNewArtifact()) {
					this.setNewArtifact(false);
				}
			} else if (event.getUrl().indexOf(ServerEndpoints.RMS_VALIDATE.getURL()) != -1) {
				isValidEvent = true;
				Element docElement = event.getData();
				postProblems(docElement);
				indeterminateProgress("", true);
			}
			if (isValidEvent) {
				removeHandlers(this);
				indeterminateProgress(message, true);
			}			
		}
		
		private void postProblems(Element node) {
			if (node.getElementsByTagName("ownerProjectName").getLength() > 0) {
				String projectName = node.getElementsByTagName("ownerProjectName").item(0).getFirstChild().getNodeValue();
				String artifactPath = "/";
				if (node.getElementsByTagName("folder") != null) {
					String folder = node.getElementsByTagName("folder").item(0).getFirstChild().getNodeValue();
					if (folder != null && !folder.isEmpty()) {
						artifactPath += folder + "/";
					}
				}
				artifactPath += node.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
				
				List<ProblemRecord> errorRecordList = new ArrayList<ProblemRecord>();
				List<ProblemRecord> warningRecordList = new ArrayList<ProblemRecord>();
				
				RuleTemplateHelper.getProblemsList(node, projectName, artifactPath, warningRecordList, errorRecordList);
				
				updateProblemRecords(warningRecordList, errorRecordList, true);
			}
		}

		private void init(Element data) {
			domain = DomainHelper.loadDomain(data);
			this.projectName = domain.getOwnerProjectName();		
			this.descTextItem.setValue(domain.getDescription()); 
			this.domainTypeItem.setValue(domain.getDataType());
			this.superDomainTextItem.setValue(domain.getSuperDomainPath());
			
			entriesGrid = new ListGrid();
			entriesGrid.setWidth100();
			entriesGrid.setHeight100();
			entriesGrid.setShowHeaderContextMenu(false);
			entriesGrid.setShowHeaderMenuButton(false);
			entriesGrid.setMargin(0);
			entriesGrid.setSelectionType(SelectionStyle.SINGLE);
			entriesGrid.setEditByCell(true);
			
			ListGridField descriptionField = new ListGridField("description", globalMsg.text_description(), 330);
			descriptionField.setCanEdit(true);
			TextItem descFieldText = new TextItem();
			descFieldText.addChangedHandler(new ChangedHandler() {				
				@Override
				public void onChanged(ChangedEvent event) {
					String desc = ((TextItem)event.getItem()).getValueAsString();
					desc = (desc == null ? "" : desc);
					DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
					entry.setDescription(desc);
					DomainEditor.this.makeDirty();					
				}
			});
			descriptionField.setEditorType(descFieldText);
			descriptionField.setIcon(WebStudioMenubar.ICON_PREFIX + "description.png");
			descriptionField.setShowDisabledIcon(false);

			ListGridField valueField = new ListGridField("value", domainMessages.text_value(), 310);
			valueField.setCanEdit(false);
			valueField.setIcon(WebStudioMenubar.ICON_PREFIX + DomainHelper.getDataTypeIcon(DOMAIN_DATA_TYPES.get(domain.getDataType())));
			valueField.setShowDisabledIcon(false);
			entriesGrid.setFields(descriptionField, valueField);

			entriesGrid.addSelectionUpdatedHandler(new SelectionUpdatedHandler() {				
				@Override
				public void onSelectionUpdated(SelectionUpdatedEvent event) {
					DomainEditor.this.selectedEntry = entriesGrid.getSelectedRecord();
					if (DomainEditor.this.selectedEntry != null) { 
						DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
						populateDomainEntryDetails(domain, entry);
					} else {
						showDomainDetailsEmptyMessage();
					}
				}
			});
			
			entriesGridLayout.addMember(entriesGrid);

			Iterator<DomainEntry> itr = domain.getDomainEntries().iterator();
			while (itr.hasNext()) {
				DomainEntry entry = itr.next();
				ListGridRecord record = new ListGridRecord();
				record.setAttribute("description", entry.getDescription());			
				if(entry instanceof RangeEntry) {
					RangeEntry range = (RangeEntry) entry;
					String lowerValue = "Undefined".equalsIgnoreCase(range.getLower()) ? "": range.getLower();
					String upperValue = "Undefined".equalsIgnoreCase(range.getUpper()) ? "": range.getUpper();
					String value = (range.isLowerInclusive()?"[":"(") + lowerValue + RANGE_VALUE_SEPARATOR + upperValue + (range.isUpperInclusive()?"]":")");
					record.setAttribute("value", value);
				}else{
					SingleEntry single = (SingleEntry) entry;
					record.setAttribute("value", single.getValue());
				}
				record.setAttribute("entry", entry);
				entriesGrid.addData(record);
			}
			
//			WebStudio.get().getApplicationToolBar().disableButton(WebStudioToolbar.TOOL_STRIP_VALIDATE_ID, true);
			if (entriesGrid.getRecords().length == 0) {
				domainRemovebutton.setDisabled(true);
			} else {
				domainTypeItem.setDisabled(true);
			}
			if (DOMAIN_DATA_TYPES.BOOLEAN.getLiteral().equals(this.domain.getDataType())) {
				domainDupebutton.setDisabled(true);
				if (entriesGrid.getRecords().length == 2) {
					domainAddbutton.setDisabled(true);
				}
			}
			
			if (isReadOnly()) {
				makeReadOnly();
			}
		}
		
		@Override
		protected SectionStackSection[] getSections() {
			return new SectionStackSection[] {this.createConfigurationSection(), this.createDomainEntriesSection()};
		}
		
		private SectionStackSection createConfigurationSection() {
			//Configuration section
			SectionStackSection configSection = new SectionStackSection(globalMsg.text_configuration());
			configSection.setExpanded(true);
			configSection.setCanCollapse(true);
			
			VLayout configLayout = new VLayout(5);
			configLayout.setHeight(120);
			configLayout.setMargin(3);
			configLayout.setOverflow(Overflow.AUTO);

			//Form
			DynamicForm configForm = new DynamicForm();
			configForm.setWidth100();
			configForm.setNumCols(3);
			//Description
			descTextItem = new TextAreaItem("description", globalMsg.text_description());
			descTextItem.setTitleAlign(Alignment.LEFT);
			descTextItem.setTitleVAlign(VerticalAlignment.TOP);
			descTextItem.setWidth(525);
			descTextItem.setHeight(50);
			descTextItem.setAlign(Alignment.LEFT);
			descTextItem.addChangedHandler(this);
			
			//Domain Type
			domainTypeItem = new SelectItem("domainType", domainMessages.text_domainType());
			domainTypeItem.setTitleAlign(Alignment.LEFT);
			domainTypeItem.setTitleVAlign(VerticalAlignment.TOP);
			domainTypeItem.setMultiple(false);
			domainTypeItem.setWidth(525);
			domainTypeItem.addChangedHandler(this);			

			Object[] values = DOMAIN_DATA_TYPES.VALUES.toArray();
			String[] domainDataTypes = new String[values.length];
			int i = 0;
			for (Object obj : values) {
				domainDataTypes[i++] = obj.toString();
			}
			domainTypeItem.setValueMap(domainDataTypes);

			//Super Domain
			superDomainTextItem = new TextItem("superDomain", domainMessages.text_inheritsFrom());
			superDomainTextItem.setTitleAlign(Alignment.LEFT);
			superDomainTextItem.setTitleVAlign(VerticalAlignment.TOP);
			superDomainTextItem.setWidth(525);
			superDomainTextItem.addChangedHandler(this);
			superDomainTextItem.setCanEdit(false);
			
			domainsBrowseButtonItem = new ButtonItem("domainBrowse", domainMessages.button_browse());
			//domainsBrowseButtonItem.setWidth(55);
			domainsBrowseButtonItem.setStartRow(false);
			domainsBrowseButtonItem.addChangedHandler(this);
			domainsBrowseButtonItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
				
				@Override
				public void onClick(
						com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
					new SelectDomainDialog(DomainEditor.this.projectName, domainTypeItem.getValueAsString(), DomainEditor.this).draw();
					
				}
			});

			configForm.setItems(descTextItem, domainTypeItem, superDomainTextItem, domainsBrowseButtonItem);

			configLayout.addMember(configForm);
			configSection.addItem(configLayout);

			return configSection;
		}

		private SectionStackSection createDomainEntriesSection() {
			SectionStackSection entriesSection = new SectionStackSection(domainMessages.text_domainEntries());
			entriesSection.setExpanded(true);
			entriesSection.setCanCollapse(false);

			HLayout containerLayout = new HLayout(3);
			containerLayout.setMargin(3);
			containerLayout.setOverflow(Overflow.AUTO);

			entriesGridLayout = new VLayout(3);
			entriesGridLayout.setMargin(0);
			entriesGridLayout.setOverflow(Overflow.AUTO);
			entriesGridLayout.addMember(getDomainTableToolBar());
			
			containerLayout.addMember(entriesGridLayout);
			containerLayout.addMember(createDomainEntryDetailsSection());
			
			entriesSection.addItem(containerLayout);

			return entriesSection;
		}

		protected ToolStrip getDomainTableToolBar() {
			domainToolStrip = new ToolStrip();   
			domainToolStrip.setWidth100();
			domainToolStrip.setHeight(TOOL_BAR_HEIGHT);
			if(LocaleInfo.getCurrentLocale().isRTL()){
				domainToolStrip.setAlign(Alignment.RIGHT);	
			}
			else{
				domainToolStrip.setAlign(Alignment.LEFT);	
			}
			domainToolStrip.setMargin(0);
			
			domainAddbutton = new ToolStripButton();
			domainAddbutton.setIcon(WebStudioMenubar.ICON_PREFIX + "add.png");
			domainAddbutton.setTitle(globalMsg.button_add());
			domainAddbutton.setTooltip(globalMsg.button_add());
			domainAddbutton.setShowRollOver(false);
			domainAddbutton.addClickHandler(this);
			domainToolStrip.addButton(domainAddbutton);

			domainRemovebutton = new ToolStripButton();
			domainRemovebutton.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
			domainRemovebutton.setTitle(globalMsg.button_remove());
			domainRemovebutton.setTooltip(globalMsg.button_remove());
			domainRemovebutton.setShowRollOver(false);
			domainRemovebutton.addClickHandler(this);
			domainToolStrip.addButton(domainRemovebutton);

			domainDupebutton = new ToolStripButton();
			domainDupebutton.setIcon(WebStudioMenubar.ICON_PREFIX + "domain_duplicate.png");
			domainDupebutton.setShowDisabledIcon(false);
			domainDupebutton.setTitle(domainMessages.button_duplicate());
			domainDupebutton.setTooltip(domainMessages.button_duplicate());
			domainDupebutton.setShowRollOver(false);
			domainDupebutton.addClickHandler(this);
			domainToolStrip.addButton(domainDupebutton);
			
			if (!isReadOnly()) {
				//TODO
			}
							
			return domainToolStrip;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			if (event.getSource() == domainAddbutton) {
				if (DOMAIN_DATA_TYPES.BOOLEAN.getLiteral().equals(this.domain.getDataType())) {
					if (entriesGrid.getRecords().length == 0) {
						ListGridRecord newRecord = new ListGridRecord();
						newRecord.setAttribute("description", "");
						newRecord.setAttribute("value", "true");
						entriesGrid.addData(newRecord);
						entriesGrid.deselectAllRecords();
						entriesGrid.selectRecord(newRecord);
						DomainEditor.this.selectedEntry = newRecord;
						
						SingleEntry singleEntry = new SingleEntry();
						singleEntry.setValue("true");
						singleEntry.setDescription("");
						newRecord.setAttribute("entry", singleEntry);
						populateDomainEntryDetails(this.domain, singleEntry);
						
						DomainEditor.this.makeDirty();
					} else if (entriesGrid.getRecords().length == 1) {
						ListGridRecord record = entriesGrid.getRecord(0);
						ListGridRecord newRecord = new ListGridRecord();
						newRecord.setAttribute("description", "");
						entriesGrid.addData(newRecord);
						entriesGrid.deselectAllRecords();
						entriesGrid.selectRecord(newRecord);
						DomainEditor.this.selectedEntry = newRecord;
						
						SingleEntry singleEntry = new SingleEntry();

						String booleanValue = record.getAttribute("value");
						if ("true".equalsIgnoreCase(booleanValue)) {
							newRecord.setAttribute("value", "false");							 
							singleEntry.setValue("false");
						} else {
							newRecord.setAttribute("value", "true");
							singleEntry.setValue("true");
						}

						singleEntry.setDescription("");
						newRecord.setAttribute("entry", singleEntry);
						populateDomainEntryDetails(this.domain, singleEntry);
						
						domainAddbutton.setDisabled(true);
						DomainEditor.this.makeDirty();
					}
				} else if (DOMAIN_DATA_TYPES.DATE_TIME.getLiteral().equals(this.domain.getDataType())) {
					String dateTime = DecisionTableUtils.getBEDate(new Date());
					ListGridRecord newRecord = new ListGridRecord();
					newRecord.setAttribute("description", "");
					newRecord.setAttribute("value", dateTime);
					entriesGrid.addData(newRecord);
					entriesGrid.deselectAllRecords();
					entriesGrid.selectRecord(newRecord);
					DomainEditor.this.selectedEntry = newRecord;
					
					SingleEntry singleEntry = new SingleEntry();
					singleEntry.setDescription("");
					singleEntry.setValue(dateTime);
					newRecord.setAttribute("entry", singleEntry);
					populateDomainEntryDetails(this.domain, singleEntry);

					DomainEditor.this.makeDirty();					
				} else {
					ListGridRecord newRecord = new ListGridRecord();
					newRecord.setAttribute("description", "");
					newRecord.setAttribute("value", "");
					entriesGrid.addData(newRecord);
					entriesGrid.deselectAllRecords();
					entriesGrid.selectRecord(newRecord);
					DomainEditor.this.selectedEntry = newRecord;
					
					SingleEntry singleEnry = new SingleEntry();
					singleEnry.setDescription("");
					singleEnry.setValue("");
					newRecord.setAttribute("entry", singleEnry);
					populateDomainEntryDetails(this.domain, singleEnry);

					DomainEditor.this.makeDirty();
				}
				domainTypeItem.setDisabled(true);
				domainRemovebutton.setDisabled(false);
			} 
			else if (event.getSource() == domainRemovebutton) {				
				entriesGrid.removeSelectedData();
				showDomainDetailsEmptyMessage();				
				if (entriesGrid.getRecords().length == 0) {
					domainTypeItem.setDisabled(false);
					domainRemovebutton.setDisabled(true);
				}
				if (DOMAIN_DATA_TYPES.BOOLEAN.getLiteral().equals(this.domain.getDataType()) 
						&& entriesGrid.getRecords().length < 2) {
					domainAddbutton.setDisabled(false);
				}
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == domainDupebutton) {
				ListGridRecord dupeRecord = new ListGridRecord();
				String description = DomainEditor.this.selectedEntry.getAttribute("description");
				String value = DomainEditor.this.selectedEntry.getAttribute("value");
				dupeRecord.setAttribute("description", description);
				dupeRecord.setAttribute("value", value);
				entriesGrid.addData(dupeRecord);
				
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof SingleEntry) {
					SingleEntry singleEntry = new SingleEntry();
					singleEntry.setDescription(entry.getDescription());
					singleEntry.setValue(((SingleEntry) entry).getValue());
					dupeRecord.setAttribute("entry", singleEntry);
				} else if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = new RangeEntry();
					rangeEntry.setDescription(entry.getDescription());
					rangeEntry.setLower(((RangeEntry) entry).getLower());
					rangeEntry.setLowerInclusive(((RangeEntry) entry).isLowerInclusive());
					rangeEntry.setUpper(((RangeEntry) entry).getUpper());
					rangeEntry.setUpperInclusive(((RangeEntry) entry).isUpperInclusive());
					dupeRecord.setAttribute("entry", rangeEntry);
				}
				domainRemovebutton.setDisabled(false);
				DomainEditor.this.makeDirty();
			}			
		}
		
		private SectionStack createDomainEntryDetailsSection() {
			SectionStack sectionStack = new SectionStack();
			SectionStackSection detailsSection = new SectionStackSection(domainMessages.text_details());
			detailsSection.setExpanded(true);
			detailsSection.setCanCollapse(true);

			VLayout entryDetailsLayout = new VLayout(3);
			entryDetailsLayout.setMargin(0);
			entryDetailsLayout.setLayoutTopMargin(10);
			entryDetailsLayout.setOverflow(Overflow.AUTO);

			emptyMessage = new Label();
			emptyMessage.setContents(domainMessages.msg_empty_details());
			emptyMessage.setAlign(Alignment.CENTER);
			emptyMessage.setValign(VerticalAlignment.CENTER);
			entryDetailsLayout.addMember(emptyMessage);
			
			rangeRadioGroupLayout = new VLayout(3);
			rangeRadioGroupLayout.setMargin(0);
			rangeRadioGroupLayout.setOverflow(Overflow.AUTO);
			rangeRadioGroupLayout.setHeight(30);
			
			DynamicForm radioGroupForm = new DynamicForm();
			radioGroupForm.setWidth100();
			rangeRadioGroupItem = new RadioGroupItem();  
			rangeRadioGroupItem.setVertical(false);
			rangeRadioGroupItem.setTitle("");  
			rangeRadioGroupItem.setValueMap(domainMessages.radioGroupItem_single(), domainMessages.radioGroupItem_range());
			rangeRadioGroupItem.setValue(domainMessages.radioGroupItem_single());
			if(LocaleInfo.getCurrentLocale().isRTL()){
				rangeRadioGroupItem.setAlign(Alignment.RIGHT);
			}
			else{
				rangeRadioGroupItem.setAlign(Alignment.LEFT);
			}
			rangeRadioGroupItem.addChangedHandler(this);
			radioGroupForm.setItems(rangeRadioGroupItem);
			rangeRadioGroupLayout.addMember(radioGroupForm);
			entryDetailsLayout.addMember(rangeRadioGroupLayout);
			rangeRadioGroupLayout.hide();
			
			simpleDetailsLayout = createSimpleDetails();
			entryDetailsLayout.addMember(simpleDetailsLayout);
			simpleDetailsLayout.hide();
			
			rangeDetailsLayout = createRangeDetails();
			entryDetailsLayout.addMember(rangeDetailsLayout);
			rangeDetailsLayout.hide();

			booleanDetailsLayout = createBooleanDetails();
			entryDetailsLayout.addMember(booleanDetailsLayout);
			booleanDetailsLayout.hide();
			
			simpleDateDetailsLayout = createSimpleDateDetails();
			entryDetailsLayout.addMember(simpleDateDetailsLayout);
			simpleDateDetailsLayout.hide();
			
			rangeDateDetailsLayout = createRangeDateDetails();
			entryDetailsLayout.addMember(rangeDateDetailsLayout);
			rangeDateDetailsLayout.hide();
						
	        detailsSection.addItem(entryDetailsLayout);
	        sectionStack.addSection(detailsSection);

	        return sectionStack;
		}
		
		private Layout createSimpleDetails() {		
			VLayout simpleLayout = new VLayout(3);
			simpleLayout.setMargin(0);
			simpleLayout.setOverflow(Overflow.AUTO);

			//Form
			DynamicForm simpleForm = new DynamicForm();
			simpleForm.setWidth100();
			simpleTextItem = new TextItem("simpleValue");
			simpleTextItem.setWidth(475);
			simpleTextItem.addChangedHandler(this);
			simpleTextItem.setValidators(new CustomValidator() {				
				@Override
				protected boolean condition(Object obj) {
					String value = (obj != null ? obj.toString() : "");
					this.setErrorMessage(domainMessages.msg_invalid_domain_entry(value, domain.getDataType()));
					return DomainValidator.isValidSingleValue(domain, value);
				}
			});
			simpleForm.setItems(simpleTextItem);
			simpleLayout.addMember(simpleForm);
	
			return simpleLayout;
		}

		private Layout createBooleanDetails() {		
			VLayout simpleLayout = new VLayout(3);
			simpleLayout.setMargin(0);
			simpleLayout.setOverflow(Overflow.AUTO);

			//Form
			DynamicForm booleanForm = new DynamicForm();
			booleanForm.setWidth100();
			booleanRadioGroupItem = new RadioGroupItem();
			booleanRadioGroupItem.setValueMap("true", "false");
			booleanRadioGroupItem.setVertical(true);
			booleanRadioGroupItem.setTitle("Boolean");
			booleanRadioGroupItem.setDisabled(true);
			booleanForm.setItems(booleanRadioGroupItem);
			simpleLayout.addMember(booleanForm);
	
			return simpleLayout;
		}

		private Layout createRangeDetails() {		
			VLayout rangeLayout = new VLayout(3);
			rangeLayout.setMargin(0);
			rangeLayout.setOverflow(Overflow.AUTO);
			
			//Form
			DynamicForm rangeForm = new DynamicForm();
			rangeForm.setWidth100();
			rangeForm.setNumCols(4);
			
			lowerTextItem = new TextItem("lowerValue", domainMessages.text_lower());
			lowerTextItem.setWidth(475);
			lowerTextItem.addChangedHandler(this);
			lowerTextItem.setValidators(new CustomValidator() {				
				@Override
				protected boolean condition(Object obj) {
					String lowerValue = (obj != null ? obj.toString() : null);
					String upperValue = upperTextItem.getValueAsString();
					if (lowerValue != null && !lowerValue.isEmpty()
							&& !DomainValidator.isValidSingleValue(DomainEditor.this.domain, lowerValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_domain_entry(lowerValue, domain.getDataType()));
						return false; 						
					} else if ((upperValue != null && !upperValue.isEmpty() && DomainValidator.isValidSingleValue(DomainEditor.this.domain, upperValue))
							&& !DomainValidator.isValidRangeValue(domain, lowerValue, upperValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_range_entry(lowerValue, upperValue, domain.getDataType()));
						return false;
					}
					return true;					
				}
			});

			lowerIncludedItem = new CheckboxItem("lowerIncluded", domainMessages.text_included());
			lowerIncludedItem.setWidth(20);
			lowerIncludedItem.addChangedHandler(this);

			upperTextItem = new TextItem("upperValue", domainMessages.text_upper());
			upperTextItem.setWidth(475);
			upperTextItem.addChangedHandler(this);
			upperTextItem.setValidators(new CustomValidator() {				
				@Override
				protected boolean condition(Object obj) {
					String lowerValue = lowerTextItem.getValueAsString();
					String upperValue = (obj != null ? obj.toString() : null);
					if (upperValue != null && !upperValue.isEmpty()
							&& !DomainValidator.isValidSingleValue(DomainEditor.this.domain, upperValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_domain_entry(upperValue, domain.getDataType()));
						return false; 
					} else if ((lowerValue != null && !lowerValue.isEmpty() && DomainValidator.isValidSingleValue(DomainEditor.this.domain, lowerValue))
							&& !DomainValidator.isValidRangeValue(domain, lowerValue, upperValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_range_entry(lowerValue, upperValue, domain.getDataType()));
						return false;
					}
					return true;					
				}
			});

			upperIncludedItem = new CheckboxItem("upperIncluded", domainMessages.text_included());
			upperIncludedItem.setWidth(20);
			upperIncludedItem.addChangedHandler(this);
			
			rangeForm.setItems(lowerTextItem, lowerIncludedItem, upperTextItem, upperIncludedItem);
			rangeLayout.addMember(rangeForm);
			
			return rangeLayout;
		}
		
		private Layout createSimpleDateDetails() {		
			VLayout simpleDateLayout = new VLayout(3);
			simpleDateLayout.setMargin(0);
			simpleDateLayout.setOverflow(Overflow.AUTO);

			//Form
			DynamicForm simpleDateForm = new DynamicForm();
			simpleDateForm.setWidth100();
			simpleDateTimeItem = new DateTimeItem("simpleValue", domainMessages.text_date());
			simpleDateTimeItem.setEditorValueFormatter(new FormItemValueFormatter() {
				@Override
				public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
					return getBEDate((Date)value);
				}
			});
			simpleDateTimeItem.setWidth(475);
			simpleDateTimeItem.addChangedHandler(this);
			simpleDateTimeItem.setValidators(new CustomValidator() {				
				@Override
				protected boolean condition(Object obj) {
					String value = (obj != null ? obj.toString() : null);
					this.setErrorMessage(domainMessages.msg_invalid_domain_entry(value, domain.getDataType()));
					return DomainValidator.isValidSingleValue(domain, value);
				}
			});
			simpleDateForm.setItems(simpleDateTimeItem);
			simpleDateLayout.addMember(simpleDateForm);
	
			return simpleDateLayout;
		}

		private Layout createRangeDateDetails() {		
			VLayout rangeLayout = new VLayout(3);
			rangeLayout.setMargin(0);
			rangeLayout.setOverflow(Overflow.AUTO);
			
			//Form
			DynamicForm rangeForm = new DynamicForm();
			rangeForm.setWidth100();
			rangeForm.setNumCols(4);
			
			lowerDateTimeItem = new DateTimeItem("lowerValue", domainMessages.text_lower());
			lowerDateTimeItem.setEditorValueFormatter(new FormItemValueFormatter() {
				@Override
				public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
					return getBEDate((Date)value);
				}
			});
			lowerDateTimeItem.setWidth(475);
			lowerDateTimeItem.addChangedHandler(this);
			lowerDateTimeItem.setValidators(new CustomValidator() {				
				@Override
				protected boolean condition(Object obj) {
					String lowerValue = (obj != null ? obj.toString() : null);
					if (!DomainValidator.isValidSingleValue(DomainEditor.this.domain, lowerValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_domain_entry(lowerValue, domain.getDataType()));
						return false;						
					} else if (!DomainValidator.isValidRangeValue(domain, lowerValue, upperDateTimeItem.getValue().toString())) {
						this.setErrorMessage(domainMessages.msg_invalid_range_entry(lowerValue, upperDateTimeItem.getValue().toString(), domain.getDataType()));
						return false;						
					}
					return true;					
				}
			});

			lowerDateIncludedItem = new CheckboxItem("lowerDateIncluded", domainMessages.text_included());
			lowerDateIncludedItem.setWidth(20);
			lowerDateIncludedItem.addChangedHandler(this);

			upperDateTimeItem = new DateTimeItem("upperValue", domainMessages.text_upper());
			upperDateTimeItem.setEditorValueFormatter(new FormItemValueFormatter() {
				@Override
				public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
					return getBEDate((Date)value);
				}
			});
			upperDateTimeItem.setWidth(475);
			upperDateTimeItem.addChangedHandler(this);
			upperDateTimeItem.setValidators(new CustomValidator() {				
				@Override
				protected boolean condition(Object obj) {
					String upperValue = (obj != null ? obj.toString() : null);
					if (!DomainValidator.isValidSingleValue(DomainEditor.this.domain, upperValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_domain_entry(upperValue, domain.getDataType()));
						return false;						
					} else if (!DomainValidator.isValidRangeValue(domain, lowerDateTimeItem.getValue().toString(), upperValue)) {
						this.setErrorMessage(domainMessages.msg_invalid_range_entry(lowerDateTimeItem.getValue().toString(), upperValue, domain.getDataType()));
						return false;						
					}
					return true;
				}
			});

			upperDateIncludedItem = new CheckboxItem("upperDateIncluded", domainMessages.text_included());
			upperDateIncludedItem.setWidth(20);
			upperDateIncludedItem.addChangedHandler(this);
			
			rangeForm.setItems(lowerDateTimeItem, lowerDateIncludedItem, upperDateTimeItem, upperDateIncludedItem);
			rangeLayout.addMember(rangeForm);
			
			return rangeLayout;
		}
		
		private void populateDomainEntryDetails(Domain domain, DomainEntry domainEntry) {		
			if (DOMAIN_DATA_TYPES.BOOLEAN.getLiteral().equalsIgnoreCase(domain.getDataType())) {
				if (domainEntry instanceof SingleEntry){
					SingleEntry single = (SingleEntry) domainEntry;
					booleanRadioGroupItem.setValue(single.getValue());
				}				
				showDomainDetailsBoolean();
			} else if (DOMAIN_DATA_TYPES.DATE_TIME.getLiteral().equalsIgnoreCase(domain.getDataType())) {
				if (domainEntry instanceof SingleEntry){
					rangeRadioGroupItem.setValue(domainMessages.radioGroupItem_single());					
					SingleEntry single = (SingleEntry) domainEntry;
					simpleDateTimeItem.setValue(single.getValue());
					simpleDateTimeItem.validate();
					showDomainDetailsDateSimple();
				} else if (domainEntry instanceof RangeEntry) {
					rangeRadioGroupItem.setValue(domainMessages.radioGroupItem_range());
					RangeEntry range = (RangeEntry) domainEntry;
					lowerDateTimeItem.setValue(range.getLower());
					lowerDateIncludedItem.setValue(range.isLowerInclusive());
					upperDateTimeItem.setValue(range.getUpper());
					upperDateTimeItem.validate();
					upperDateIncludedItem.setValue(range.isUpperInclusive());
					showDomainDetailsDateRange();	
				}
			}	
			else {
				boolean showRangeRadio = !DOMAIN_DATA_TYPES.STRING.getLiteral().equalsIgnoreCase(domain.getDataType());				
				if (domainEntry instanceof SingleEntry){
					rangeRadioGroupItem.setValue(domainMessages.radioGroupItem_single());
					SingleEntry single = (SingleEntry) domainEntry;
					simpleTextItem.setTitle(getSimpleDetailsTitle(DOMAIN_DATA_TYPES.get(domain.getDataType())));
					simpleTextItem.setValue(single.getValue());
					simpleTextItem.validate();
					showDomainDetailsSimple(showRangeRadio);
				} else if (domainEntry instanceof RangeEntry) {
					rangeRadioGroupItem.setValue(domainMessages.radioGroupItem_range());
					RangeEntry range = (RangeEntry) domainEntry;
					String lowerValue = "Undefined".equalsIgnoreCase(range.getLower()) ? "" : range.getLower();
					String upperValue = "Undefined".equalsIgnoreCase(range.getUpper()) ? "" : range.getUpper();
					lowerTextItem.setValue(lowerValue);
					lowerIncludedItem.setValue(range.isLowerInclusive());
					upperTextItem.setValue(upperValue);
					upperTextItem.validate();
					upperIncludedItem.setValue(range.isUpperInclusive());
					showDomainDetailsRange();
				}				
			}			
		}

		private String getSimpleDetailsTitle(DOMAIN_DATA_TYPES dataType) {
			String title = null;
			switch(dataType) {
			case STRING:
				title = domainMessages.text_simpleString();
				break;
			case INTEGER:
				title = domainMessages.text_simpleNumber();
				break;
			case LONG:
				title = domainMessages.text_long();
				break;
			case DOUBLE:
				title = domainMessages.text_double();
				break;				
			case DATE_TIME:
				title = domainMessages.text_date();
				break;
			case BOOLEAN:
				title = domainMessages.text_boolean();
				break;
			default:
				dataType.getLiteral();
			}
			return title;
		}
		
		private void showDomainDetailsEmptyMessage() {
			hideAllDomainDetails();
			clearDomainEntryDetails();
			emptyMessage.show();
		}

		private void showDomainDetailsSimple(boolean showRangeRadio) {
			hideAllDomainDetails();
			if (showRangeRadio)
				rangeRadioGroupLayout.show();
			simpleDetailsLayout.show();
		}

		private void showDomainDetailsBoolean() {
			hideAllDomainDetails();
			booleanDetailsLayout.show();
		}

		private void showDomainDetailsRange() {
			hideAllDomainDetails();
			rangeRadioGroupLayout.show();
			rangeDetailsLayout.show();
		}

		private void showDomainDetailsDateSimple() {
			hideAllDomainDetails();
			rangeRadioGroupLayout.show();
			simpleDateDetailsLayout.show();
		}

		private void showDomainDetailsDateRange() {
			hideAllDomainDetails();
			rangeRadioGroupLayout.show();
			rangeDateDetailsLayout.show();
		}

		private void hideAllDomainDetails() {
			emptyMessage.hide();
			rangeRadioGroupLayout.hide();
			simpleDetailsLayout.hide();
			booleanDetailsLayout.hide();
			rangeDetailsLayout.hide();
			simpleDateDetailsLayout.hide();
			rangeDateDetailsLayout.hide();
		}
		
		private void clearDomainEntryDetails() {
			simpleTextItem.setValue("");
			booleanRadioGroupItem.setValue(false);
			lowerTextItem.setValue("");
			lowerIncludedItem.setValue(false);
			upperTextItem.setValue("");
			upperIncludedItem.setValue(false);
			simpleDateTimeItem.setValue(new Date());
			lowerDateTimeItem.setValue(new Date());
			upperDateTimeItem.setValue(new Date());
			selectedEntry = null;
		}
		
		@Override
		protected Canvas getWidget() {
			return null;
		}

		@Override
		public void setSaveConfirmationProperties() {
			//no impl
		}

		@Override
		public void onDirty() {
			//no impl
		}

		@Override
		public void onSave() {
			String description = descTextItem.getValueAsString();
			description = (description == null ? "" : description); 
			domain.setDescription(description);
			domain.setDataType(domainTypeItem.getValueAsString());
			domain.setSuperDomainPath(superDomainTextItem.getValueAsString());
			
			ListGridRecord[] records = entriesGrid.getRecords();
			domain.removeAllDomainEntries();
			for (int i = 0; i < records.length; i++) {
				DomainEntry domainEntry = (DomainEntry) records[i].getAttributeAsObject("entry");
				if (domainEntry instanceof SingleEntry) {
					SingleEntry singleEntry = (SingleEntry) domainEntry;
					String value = singleEntry.getValue();
					if (value != null && !value.isEmpty()) {
						domain.addDomainEntry(domainEntry);
					}
				} else {
					domain.addDomainEntry(domainEntry);
				}	
			}
						
			WebStudio.get().getEditorPanel().getBottomPane().setVisible(false);
			ArtifactUtil.addHandlers(this);
			DomainHelper.saveDomain(this.domain,
					this.getSelectedResource(),
					this.request);
		}

		@Override
		public boolean onValidate() {
			indeterminateProgress(globalMsg.progressMessage_pleaseWait() + " " + globalMsg.progressMessage_validatingBR(), false);
			DomainHelper.validateDomain(getSelectedResource(), request);
			addHandlers(this);
			return true;
		}

		@Override
		public void makeReadOnly() {
			descTextItem.setDisabled(true);
			domainTypeItem.setDisabled(true);
			superDomainTextItem.setDisabled(true);
			domainsBrowseButtonItem.setDisabled(true);			
			entriesGrid.setDisabled(true);
			domainAddbutton.setDisabled(true);
			domainRemovebutton.setDisabled(true);
			domainDupebutton.setDisabled(true);			
		}

		@Override
		public void onChanged(ChangedEvent event) {
			if (event.getSource() == descTextItem) {
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == domainTypeItem) {
				this.domain.setDataType(domainTypeItem.getValueAsString());
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == superDomainTextItem) {
				String domainPath = superDomainTextItem.getValueAsString();
				if (domainPath != null && !domainPath.isEmpty()) {
					domainTypeItem.setDisabled(true);
				} else {
					domainTypeItem.setDisabled(false);
				}
				DomainEditor.this.makeDirty();
			}		
			else if (event.getSource() == rangeRadioGroupItem) {
				DomainEntry changedEntry = null;
				String value = null;
				if (rangeRadioGroupItem.getValueAsString().equals(domainMessages.radioGroupItem_single())) {
					SingleEntry singleEntry = new SingleEntry();
					changedEntry = singleEntry;
					if (DOMAIN_DATA_TYPES.DATE_TIME.getLiteral().equalsIgnoreCase(domain.getDataType())) {
						value = DecisionTableUtils.getBEDate(new Date());
					} else {
						value = "";
					}
					singleEntry.setValue(value);
				} else {
					RangeEntry rangeEntry = new RangeEntry();
					changedEntry = rangeEntry;
					if (DOMAIN_DATA_TYPES.DATE_TIME.getLiteral().equalsIgnoreCase(domain.getDataType())) {
						value = "(" + DecisionTableUtils.getBEDate(new Date()) + "," + DecisionTableUtils.getBEDate(new Date()) + ")";
						rangeEntry.setLower(DecisionTableUtils.getBEDate(new Date()));
						rangeEntry.setUpper(DecisionTableUtils.getBEDate(new Date()));
					} else {
						value = "(,)";
					}					
				}
				populateDomainEntryDetails(this.domain, changedEntry);
				DomainEditor.this.selectedEntry.setAttribute("value", value);
				DomainEditor.this.selectedEntry.setAttribute("entry", changedEntry);
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();								
			}
			else if (event.getSource() == simpleTextItem) {
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof SingleEntry) {
					SingleEntry singleEntry = (SingleEntry) entry;
					String value = simpleTextItem.getValueAsString();
					if (simpleTextItem.validate()) {
						value = value.trim();
						singleEntry.setValue(value);
						DomainEditor.this.selectedEntry.setAttribute("value", value);
					}
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();				
			}
			else if (event.getSource() == simpleDateTimeItem) {
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof SingleEntry) {
					SingleEntry singleEntry = (SingleEntry) entry;
					String value = simpleDateTimeItem.getValue().toString();
					if (simpleDateTimeItem.validate()) {
						singleEntry.setValue(value);
						DomainEditor.this.selectedEntry.setAttribute("value", value);
					}
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();				
			}			
			else if (event.getSource() == lowerTextItem) {			
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = ((RangeEntry) entry);
					String lowerValue = lowerTextItem.getValueAsString();
					boolean isValidValue = false;
					if (lowerValue == null || lowerValue.isEmpty()) {
						lowerValue = "Undefined";
						isValidValue = true;
					} else {
						lowerValue = lowerValue.trim();
						isValidValue = DomainValidator.isValidSingleValue(domain, lowerValue);
					}

					lowerTextItem.validate(); //To show error icon
					upperTextItem.validate(); //To show error icon					

					if (isValidValue) {
						rangeEntry.setLower(lowerValue);					
						lowerValue = "Undefined".equalsIgnoreCase(lowerValue) ? "": lowerValue;
						String upperValue = "Undefined".equalsIgnoreCase(rangeEntry.getUpper()) ? "": rangeEntry.getUpper();
						String value = (rangeEntry.isLowerInclusive()?"[":"(") + lowerValue + RANGE_VALUE_SEPARATOR + upperValue + (rangeEntry.isUpperInclusive()?"]":")");
						DomainEditor.this.selectedEntry.setAttribute("value", value);
					}
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == upperTextItem) {
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = ((RangeEntry) entry);
					String upperValue = upperTextItem.getValueAsString();
					boolean isValid = false;
					if (upperValue == null || upperValue.isEmpty()) {
						upperValue = "Undefined";
						isValid = true;
					} else {
						upperValue = upperValue.trim();
						isValid = DomainValidator.isValidSingleValue(domain, upperValue);
					}

					upperTextItem.validate(); //To show error icon
					lowerTextItem.validate(); //To show error icon

					if (isValid) {
						rangeEntry.setUpper(upperValue);
						String lowerValue = "Undefined".equalsIgnoreCase(rangeEntry.getLower()) ? "": rangeEntry.getLower();
						upperValue = "Undefined".equalsIgnoreCase(upperValue) ? "": upperValue;
						String value = (rangeEntry.isLowerInclusive()?"[":"(") + lowerValue + RANGE_VALUE_SEPARATOR + upperValue + (rangeEntry.isUpperInclusive()?"]":")");
						DomainEditor.this.selectedEntry.setAttribute("value", value);
					}
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == lowerDateTimeItem) {
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = ((RangeEntry) entry);
					String lowerValue = lowerDateTimeItem.getValue() != null ? lowerDateTimeItem.getValue().toString() : null;
					boolean isValid = false;
					if (lowerValue == null || lowerValue.isEmpty()) {
						lowerValue = DecisionTableUtils.getBEDate(new Date());
						isValid = true;
					} else {
						lowerDateTimeItem.validate(); //To show error icon
						isValid = DomainValidator.isValidSingleValue(domain, lowerValue);
					}
					
					if (isValid) {
						rangeEntry.setLower(lowerValue);						
						String value = (rangeEntry.isLowerInclusive()?"[":"(") + lowerValue + RANGE_VALUE_SEPARATOR + rangeEntry.getUpper() + (rangeEntry.isUpperInclusive()?"]":")");
						DomainEditor.this.selectedEntry.setAttribute("value", value);
					}
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == upperDateTimeItem) {
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = ((RangeEntry) entry);
					String upperValue = upperDateTimeItem.getValue() != null ? upperDateTimeItem.getValue().toString() : null;
					boolean isValid = false;
					if (upperValue == null || upperValue.isEmpty()) {
						upperValue = DecisionTableUtils.getBEDate(new Date());
						isValid = true;
					} else {
						upperDateTimeItem.validate(); //To show error icon
						isValid = DomainValidator.isValidSingleValue(domain, upperValue);
					}
					
					if (isValid) {
						rangeEntry.setUpper(upperValue);					
						String value = (rangeEntry.isLowerInclusive()?"[":"(") + rangeEntry.getLower() + RANGE_VALUE_SEPARATOR + upperValue + (rangeEntry.isUpperInclusive()?"]":")");
						DomainEditor.this.selectedEntry.setAttribute("value", value);
					}
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == lowerIncludedItem || event.getSource() == lowerDateIncludedItem) {
				CheckboxItem checkboxItem = (CheckboxItem) event.getSource();
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = ((RangeEntry) entry);
					rangeEntry.setLowerInclusive(checkboxItem.getValueAsBoolean());
					String value = (rangeEntry.isLowerInclusive()?"[":"(") + rangeEntry.getLower() + RANGE_VALUE_SEPARATOR + rangeEntry.getUpper() + (rangeEntry.isUpperInclusive()?"]":")");
					DomainEditor.this.selectedEntry.setAttribute("value", value);
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();
			}
			else if (event.getSource() == upperIncludedItem || event.getSource() == upperDateIncludedItem) {
				CheckboxItem checkboxItem = (CheckboxItem) event.getSource();
				DomainEntry entry = (DomainEntry) DomainEditor.this.selectedEntry.getAttributeAsObject("entry");
				if (entry instanceof RangeEntry) {
					RangeEntry rangeEntry = ((RangeEntry) entry);
					rangeEntry.setUpperInclusive(checkboxItem.getValueAsBoolean());
					String value = (rangeEntry.isLowerInclusive()?"[":"(") + rangeEntry.getLower() + RANGE_VALUE_SEPARATOR + rangeEntry.getUpper() + (rangeEntry.isUpperInclusive()?"]":")");
					DomainEditor.this.selectedEntry.setAttribute("value", value);
				}
				DomainEditor.this.entriesGrid.markForRedraw();
				DomainEditor.this.makeDirty();
			}			
		}
		
		protected void setSuperDomainPath(String domainPath) {
			String oldDomainPath = this.superDomainTextItem.getValueAsString();
			if (domainPath != null && !domainPath.equalsIgnoreCase(oldDomainPath)) {
				this.superDomainTextItem.setValue(domainPath);
				this.domainTypeItem.setDisabled(true);
				DomainEditor.this.makeDirty();
			}	
		}

		@Override
		public TemplateInstanceChangeHandler getChangeHandler() {
			//no impl
			return null;
		}

		@Override
		public void close() {
			//no impl
		}

		@Override
		public String getEditorUrl() {
			// TODO Auto-generated method stub
			return null;
		}
	}
		
	class SelectDomainDialog extends AbstractWebStudioDialog implements HttpSuccessHandler, HttpFailureHandler {
		
		protected TreeGrid domainTreeGrid;
		protected String projectName;
		protected String domainDataType;
		protected DomainEditor domainEditor;
		
		public SelectDomainDialog(String projectName, String domainDataType, DomainEditor domainEditor) { 
			this.projectName = projectName;
			this.domainDataType = domainDataType;
			this.domainEditor = domainEditor;
			this.setDialogWidth(400);
			this.setDialogHeight(250);
			this.setDialogTitle(domainMessages.text_selectDomain());
			this.initialize();
			setIsModal(true);
			setShowModalMask(true);
			this.setDialogHeaderIcon(Page.getAppImgDir() + "icons/16/domain.png");			
		} 

		@Override
		public void onFailure(HttpFailureEvent event) {
			//no impl
		}

		@Override
		public void onSuccess(HttpSuccessEvent event) {
			//no impl			
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
		
			domainTreeGrid = new DomainTreeGrid();
			domainTreeGrid.addRecordClickHandler(new RecordClickHandler() {			
				@Override
				public void onRecordClick(RecordClickEvent event) {
					NavigatorResource record = (NavigatorResource) event.getRecord();				
					if (ARTIFACT_TYPES.DOMAIN.getValue().equals(record.getType())) {
						okButton.setDisabled(false);
					} else {
						okButton.setDisabled(true);
					}
				}
			});
			container.addMember(domainTreeGrid);
			return container;
		}
		
		@Override
		public void onAction() {
			NavigatorResource record = (NavigatorResource) domainTreeGrid.getSelectedRecord();
			String superDomainPath = record.getId().substring(record.getId().indexOf("$"), record.getId().length()).replace('$', '/');
			domainEditor.setSuperDomainPath(superDomainPath);
			doCancel();
		}

		public class DomainTreeGrid extends TreeGrid implements HttpSuccessHandler, HttpFailureHandler {

			private final String ICON_PATH = Page.getAppImgDir() + "icons/16/";
			private static final String ROOT_NODE = "Root";
			
			private Tree domainTree;
			private TreeNode projectNode;
			
			public DomainTreeGrid() {
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
				setEmptyMessage(domainMessages.msg_empty_selectDomain());
				setLayoutAlign(Alignment.RIGHT);
				setBorder("1px solid lightgray");
				// initialize by setting field data 
				setFields(new TreeGridField("name", "Name"));  
				setOverflow(Overflow.AUTO);
				addSort(new SortSpecifier("name", SortDirection.ASCENDING));
				
				//Setup the tree
				domainTree = new Tree();
				domainTree.setModelType(TreeModelType.PARENT);
				domainTree.setIdField("id");
				domainTree.setParentIdField("parent");
				domainTree.setNameProperty("name");
				domainTree.setReportCollisions(false);
				TreeNode root = new TreeNode ("Root");
				domainTree.setRoot(root);
				
				getDomainResources();						
			}

			/**
			 * Fetch the list of project artifacts from the server
			 */
			private void getDomainResources() {
				ArtifactUtil.addHandlers(this);
				HttpRequest request = new HttpRequest();
				request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));	
				request.addRequestParameters(new RequestParameter("dataType", SelectDomainDialog.this.domainEditor.domainTypeItem.getValueAsString()));
				request.submit(ServerEndpoints.RMS_GET_DOMAINS_BY_DATATYPE.getURL());
			}

			@Override
			public void onSuccess(HttpSuccessEvent event) {
				boolean validEvent = false;
				if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_DOMAINS_BY_DATATYPE.getURL()) != -1) {
					Element docElement = event.getData();

					List<ArtifactDetail> artifactList = new ArrayList<ArtifactDetail>();
					NodeList artifactElements = docElement.getElementsByTagName("artifactDetails");
					for (int i = 0; i < artifactElements.getLength(); i++) {
						NodeList artifactDetails = artifactElements.item(i).getChildNodes();
						
						String artifactPath = null;
						for (int j = 0; j < artifactDetails.getLength(); j++) {
							if (!artifactDetails.item(j).toString().trim().isEmpty()) {
								if (artifactDetails.item(j).getNodeName().equals("artifactPath")) {
									String artifact = artifactDetails.item(j).getFirstChild().getNodeValue();									
									artifactPath = artifact.substring(artifact.indexOf("/"), artifact.length());									
								}
							}
						}						
						
						String baseDomainPath = SelectDomainDialog.this.domainEditor.domainPath;
						if (!baseDomainPath.equals(artifactPath)) {
							ArtifactDetail artifactDetailItem = new ArtifactDetail(artifactPath, null);
							artifactList.add(artifactDetailItem);
						}	
					}
					
					NavigatorResource[] resourceList = ProjectExplorerUtil.createProjectResources(artifactList, projectName);				
					for (int i = 0; i < resourceList.length; i++) {
						buildDomainTree(resourceList[i]);
					}	
					if (projectNode != null) {
						domainTree.openFolder(projectNode);
					}	
					// set up the field data
					this.setData(domainTree);
					validEvent = true;
				}

			 	if (validEvent) {
					removeHandlers(this);
				}
			}	

			@Override
			public void onFailure(HttpFailureEvent event) {
				if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_GROUP_ARTIFACTS.getURL()) != -1) {
					ArtifactUtil.removeHandlers(this);
				}
			}		
			
			private void buildDomainTree(NavigatorResource navResource) {
				TreeNode parentFolder = domainTree.getRoot();			
				TreeNode resourceNode = null;
				String resourcePath = "";

				String[] parts = navResource.getId().split("\\$");
				
				for (int i = 0; i < parts.length; i++) {			
					String prevParent = resourcePath;
					resourcePath += (resourcePath.isEmpty()) ? parts[i] : "$" + parts[i];
					resourceNode = domainTree.findById(resourcePath);
					if (resourceNode == null) {			
						if (prevParent == "") {
							resourceNode = new NavigatorResource(parts[i],
									ROOT_NODE,
									parts[i],
									ARTIFACT_TYPES.PROJECT.getValue(),
									ICON_PATH + "studioproject.gif",
									ARTIFACT_TYPES.PROJECT);
							projectNode = resourceNode;
						} else {
							if (resourcePath.equals(navResource.getId())) {
								resourceNode = new NavigatorResource(
										parts[i], 
										prevParent.replace("/", "$"), 
										(prevParent + "$" + parts[i]).replace("/", "$"), 
										ARTIFACT_TYPES.DOMAIN.getValue(), 
										ICON_PATH + "domain.png",
										ARTIFACT_TYPES.DOMAIN);							
							} else {							
								resourceNode = new NavigatorResource(
										parts[i], 
										prevParent.replace("/", "$"), 
										(prevParent + "$" + parts[i]).replace("/", "$"), 
										ARTIFACT_TYPES.FOLDER.getValue(), 
										ICON_PATH + "folder.png",
										ARTIFACT_TYPES.FOLDER);
							}
						}	
						domainTree.add(resourceNode, parentFolder);
					}				
					parentFolder = resourceNode;
				}
			}

		}	
	}
}
