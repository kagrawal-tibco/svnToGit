/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RecordDropEvent;
import com.smartgwt.client.widgets.grid.events.RecordDropHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor.TemplateInstanceChangeHandler;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.widgets.WebStudioMenubar;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;

/**
 * This class provides a singleton Factory instance to create Rule editors
 * 
 * @author Vikram Patil
 */
public class RuleEditorFactory implements IEditorFactory {
	private static RuleEditorFactory instance;
	private GlobalMessages globalMsgBundle = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	/**
	 * Create a singleton to create Rule Editors
	 * 
	 * @return
	 */
	public static RuleEditorFactory getInstance() {
		if (instance == null) {
			instance = new RuleEditorFactory();
		}
		return instance;
	}

	private RuleEditorFactory() {
	}

	/**
	 * @see com.tibco.cep.webstudio.client.editor.IEditorFactory#createEditor(com.tibco.cep.webstudio.client.model.NavigatorResource)
	 */
	@Override
	public AbstractEditor createEditor(NavigatorResource selectedRecord) {
		return createEditor(selectedRecord, false, null);
	}
	
	/**
	 * @see IEditorFactory#createEditor(NavigatorResource, boolean, String)
	 */
	public AbstractEditor createEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
		return new RuleEditor(selectedRecord, isReadOnly, revisionId);
	}

	/**
	 * Editor class for Rule. Provides various sections implementations.
	 * Additionally supports handling editor events.
	 */
	class RuleEditor extends AbstractEditor {
		private TextAreaItem then;
		private TextAreaItem description;
		private SelectItem priority;
		private TextItem rank;
		private CheckboxItem forwardChain;

		private ListGrid propertiesGrid;
		private ListGridField term;
		private ListGridField alias;
		private Map<String, String> declarationMapping;
		private VLayout conditionContainer;

		/**
		 * Initialises and sets up the rule editor
		 * 
		 * @param selectedRecord
		 */
		public RuleEditor(NavigatorResource selectedRecord) {
			this(selectedRecord, false, null);
		}
		
		/**
		 * Initialises the rule editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 */
		public RuleEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
			super(selectedRecord, isReadOnly, revisionId, true);
			this.initialize();
			this.declarationMapping = new LinkedHashMap<String, String>();
		}

		@Override
		protected SectionStackSection[] getSections() {
			return new SectionStackSection[] { this.createConfigurationSection(), this.createDeclarationSection(),
					this.createWhenSection(), this.createThenSection() };
		}

		/**
		 * Creates a configuration Section
		 * 
		 * @return
		 */
		private SectionStackSection createConfigurationSection() {
			SectionStackSection configurationSection = new SectionStackSection(globalMsgBundle.text_configuration());
			configurationSection.setExpanded(true);
			configurationSection.setCanCollapse(true);

			VLayout formContainer = new VLayout(5);
			formContainer.setWidth100();
			formContainer.setLeft(0);
			formContainer.setMargin(10);

			// Description Form
			DynamicForm descForm = new DynamicForm();
			descForm.setWidth100();

			this.description = new TextAreaItem("description", globalMsgBundle.text_description());
			this.description.setWidth("*");
			this.description.setHeight(50);
			this.description.addChangedHandler(new ChangedHandler() {
				public void onChanged(ChangedEvent event) {
					RuleEditor.this.makeDirty();
				}
			});

			this.priority = new SelectItem("priority", globalMsgBundle.text_priority());
			this.priority.setWidth("*");

			LinkedHashMap<String, String> priorityMap = new LinkedHashMap<String, String>();
			priorityMap.put("1", "1 (" + globalMsgBundle.text_highest() + ")");
			priorityMap.put("2", "2");
			priorityMap.put("3", "3");
			priorityMap.put("4", "4");
			priorityMap.put("5", "5");
			priorityMap.put("6", "6");
			priorityMap.put("7", "7");
			priorityMap.put("8", "8");
			priorityMap.put("9", "9");
			priorityMap.put("10", "10 (" + globalMsgBundle.text_lowest() +")");

			this.priority.setValueMap(priorityMap);
			this.priority.setDefaultToFirstOption(true);

			descForm.setItems(this.description, this.priority);
			formContainer.addMember(descForm);

			// Rank Form
			HLayout rankContainer = new HLayout(5);
			rankContainer.setWidth100();
			rankContainer.setHeight(25);

			DynamicForm rankForm = new DynamicForm();
			rankForm.setWidth100();

			this.rank = new TextItem("rank", "Rank");
			this.rank.setWidth("*");

			rankForm.setItems(this.rank);
			rankContainer.addMember(rankForm);

			IButton browse = new IButton("Browse");
			rankContainer.addMember(browse);

			formContainer.addMember(rankContainer);

			// Forward Chain form
			DynamicForm forwardChainForm = new DynamicForm();
			forwardChainForm.setWidth100();

			this.forwardChain = new CheckboxItem("retryonexception", "Retry On Exception");
			this.forwardChain.setTitleOrientation(TitleOrientation.LEFT);
			this.forwardChain.setLabelAsTitle(true);

			forwardChainForm.setItems(this.forwardChain);

			formContainer.addMember(forwardChainForm);

			configurationSection.addItem(formContainer);

			return configurationSection;
		}

		/**
		 * Creates a Declaration Section
		 * 
		 * @return
		 */
		private SectionStackSection createDeclarationSection() {
			SectionStackSection declarationSection = new SectionStackSection(globalMsgBundle.text_declaration());
			declarationSection.setExpanded(true);
			declarationSection.setCanCollapse(true);

			VLayout container = new VLayout(10);
			container.setWidth100();
			container.setMargin(10);
			container.setHeight(135);

			this.propertiesGrid = new ListGrid();
			this.propertiesGrid.setWidth100();
			this.propertiesGrid.setHeight100();
			this.propertiesGrid.setShowHeaderContextMenu(false);
			this.propertiesGrid.setShowHeaderMenuButton(false);
			this.propertiesGrid.setLeaveScrollbarGap(false);
			this.propertiesGrid.setCanAcceptDroppedRecords(true);
			this.propertiesGrid.setCanReorderRecords(true);
			this.propertiesGrid.setCanEdit(true);
			this.propertiesGrid.addRecordDropHandler(new RecordDropHandler() {
				public void onRecordDrop(RecordDropEvent event) {
					WebStudioNavigatorGrid navigatorGrid = (WebStudioNavigatorGrid) EventHandler.getDragTarget();
					NavigatorResource selectedNode = (NavigatorResource) navigatorGrid.getSelectedRecord();

					String droppedTermValue = selectedNode.getId().replace("$", "/")
							.substring(selectedNode.getId().indexOf("$"), selectedNode.getId().length());
					droppedTermValue = droppedTermValue.substring(0, droppedTermValue.indexOf("."));

					ListGridRecord[] newDroppedRecord = event.getDropRecords();
					newDroppedRecord[0].setAttribute(RuleEditor.this.term.getName(), droppedTermValue);
				}
			});

			this.propertiesGrid.addEditCompleteHandler(new EditCompleteHandler() {
				public void onEditComplete(EditCompleteEvent event) {
					RuleEditor.this.declarationMapping.clear();
					for (ListGridRecord record : RuleEditor.this.propertiesGrid.getRecords()) {
						RuleEditor.this.declarationMapping.put(record.getAttribute("term"),
								record.getAttribute("alias"));
					}

					for (Canvas widget : RuleEditor.this.conditionContainer.getMembers()) {
						if (widget instanceof RuleCondition) {
							((RuleCondition) widget).setAliasList(RuleEditor.this.declarationMapping);
						}
					}
				}
			});
			this.term = new ListGridField("term", "Term");
			this.term.setWidth("50%");
			this.alias = new ListGridField("alias", "Alias");
			this.alias.setWidth("50%");

			this.propertiesGrid.setFields(this.term, this.alias);

			HLayout buttonContainer = new HLayout(5);

			IButton addProperty = new IButton();
			addProperty.setIcon(WebStudioMenubar.ICON_PREFIX + "add.png");
			addProperty.setWidth(24);
			addProperty.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					RuleEditor.this.propertiesGrid.startEditingNew();
				}
			});
			buttonContainer.addMember(addProperty);

			IButton removeProperty = new IButton();
			removeProperty.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
			removeProperty.setWidth(24);
			removeProperty.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					RuleEditor.this.propertiesGrid.removeSelectedData();
				}
			});
			buttonContainer.addMember(removeProperty);

			container.addMember(buttonContainer);
			container.addMember(this.propertiesGrid);

			declarationSection.addItem(container);

			return declarationSection;
		}

		/**
		 * Creates an section for various conditions
		 * 
		 * @return
		 */
		private SectionStackSection createWhenSection() {
			SectionStackSection whenSection = new SectionStackSection("When");
			whenSection.setExpanded(true);
			whenSection.setCanCollapse(true);

			this.conditionContainer = new VLayout(10);
			this.conditionContainer.setWidth100();
			this.conditionContainer.setMargin(10);
			this.conditionContainer.setHeight(100);

			IButton addProperty = new IButton();
			addProperty.setIcon(WebStudioMenubar.ICON_PREFIX + "add.png");
			addProperty.setWidth(24);
			addProperty.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					RuleEditor.this.conditionContainer.addMember(new RuleCondition(RuleEditor.this.declarationMapping));
				}
			});

			this.conditionContainer.addMember(addProperty);

			whenSection.addItem(this.conditionContainer);

			return whenSection;
		}

		/**
		 * Creates an section for various actions
		 * 
		 * @return
		 */
		private SectionStackSection createThenSection() {
			SectionStackSection thenSection = new SectionStackSection("Then");
			thenSection.setExpanded(true);
			thenSection.setCanCollapse(true);

			DynamicForm thenForm = new DynamicForm();
			thenForm.setWidth100();
			thenForm.setPadding(10);
			thenForm.setNumCols(2);
			thenForm.setColWidths("50%", "50%");

			this.then = new TextAreaItem("then");
			this.then.setShowTitle(false);
			this.then.setWidth("*");
			this.then.setHeight(50);
			this.then.setColSpan(2);

			thenForm.setItems(this.then);

			thenSection.addItem(thenForm);

			return thenSection;
		}

		@Override
		public void onDirty() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSave() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(HttpSuccessEvent event) {
			Element docElement = event.getData();

			this.description.setValue(docElement.getElementsByTagName(this.description.getName()).item(0)
					.getFirstChild().getNodeValue());
			this.priority.setValue(docElement.getElementsByTagName(this.priority.getName()).item(0).getFirstChild()
					.getNodeValue());
			this.rank.setValue(docElement.getElementsByTagName(this.rank.getName()).item(0).getFirstChild()
					.getNodeValue());
			this.forwardChain.setValue(docElement.getElementsByTagName(this.forwardChain.getName()).item(0)
					.getFirstChild().getNodeValue());
			this.then.setValue(docElement.getElementsByTagName(this.then.getName()).item(0).getFirstChild()
					.getNodeValue());

			NodeList declarationList = docElement.getElementsByTagName("declaration").item(0).getChildNodes();
			List<ListGridRecord> propertiesData = new ArrayList<ListGridRecord>();
			for (int i = 0; i < declarationList.getLength(); i++) {
				ListGridRecord property = new ListGridRecord();
				String termValue = declarationList.item(i).getFirstChild().getFirstChild().getNodeValue();
				String aliasValue = declarationList.item(i).getLastChild().getFirstChild().getNodeValue();
				property.setAttribute(this.term.getName(), termValue);
				property.setAttribute(this.alias.getName(), aliasValue);

				propertiesData.add(property);
				this.declarationMapping.put(termValue, aliasValue);
			}
			this.propertiesGrid.setData(propertiesData.toArray(new ListGridRecord[propertiesData.size()]));

			NodeList conditionList = docElement.getElementsByTagName("when").item(0).getChildNodes();
			for (int i = 0; i < conditionList.getLength(); i++) {
				NodeList conditionElements = conditionList.item(i).getChildNodes();
				this.conditionContainer.addMember(new RuleCondition(this.declarationMapping,
						conditionElements.item(0).getFirstChild().getNodeValue(),
						conditionElements.item(1).getFirstChild().getNodeValue(),
						conditionElements.item(2).getFirstChild().getNodeValue()));
			}
		}

		@Override
		protected Canvas getWidget() {
			// No Custom Widget needed
			return null;
		}

		@Override
		public TemplateInstanceChangeHandler getChangeHandler() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean onValidate() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void close() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void makeReadOnly() {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void setSaveConfirmationProperties() {
			setConfirmSaveTitle("");
			setConfirmSaveDescription("");
		}
		
		@Override
		public String getEditorUrl() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Rule Condition Widget
	 */
	class RuleCondition extends Canvas {
		private SelectItem leftOperandAlias;
		private SelectItem operator;
		private TextItem rightOperand;
		private TextItem leftOperandProperty;
		private SelectItem rightOperandAlias;
		private RadioGroupItem rightOperandType;

		public RuleCondition(Map<String, String> declarationMapping) {
			this(declarationMapping, null, null, null);
		}

		public RuleCondition(	Map<String, String> declarationMapping,
								String leftOperand,
								String operator,
								String rightOperand) {
			this.init();
			this.setData(declarationMapping, leftOperand, operator, rightOperand);
		}

		private void init() {
			HLayout layout = new HLayout(5);
			layout.setHeight(25);

			DynamicForm form = new DynamicForm();
			form.setNumCols(6);

			this.leftOperandAlias = new SelectItem();
			this.leftOperandAlias.setShowTitle(false);

			this.leftOperandProperty = new TextItem();
			this.leftOperandProperty.setShowTitle(false);

			this.operator = new SelectItem();
			this.operator.setDefaultToFirstOption(true);
			this.operator.setShowTitle(false);

			this.rightOperandType = new RadioGroupItem();
			this.rightOperandType.setValueMap("Expression", "Property");
			this.rightOperandType.setShowTitle(false);
			this.rightOperandType.addChangedHandler(new ChangedHandler() {
				public void onChanged(ChangedEvent event) {
					RuleCondition.this.addRightOperandFields(null);
				}
			});

			this.rightOperandAlias = new SelectItem();
			this.rightOperandAlias.setShowTitle(false);

			this.rightOperand = new TextItem();
			this.rightOperand.setShowTitle(false);

			form.setItems(this.leftOperandAlias,
					this.leftOperandProperty,
					this.operator,
					this.rightOperandType,
					this.rightOperandAlias,
					this.rightOperand);

			HLayout buttonContainer = new HLayout();
			buttonContainer.setLayoutTopMargin(15);

			IButton removeProperty = new IButton();
			removeProperty.setIcon(WebStudioMenubar.ICON_PREFIX + "remove.png");
			removeProperty.setWidth(24);
			removeProperty.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					RuleCondition.this.removeCondition();
				}
			});
			buttonContainer.addMember(removeProperty);

			layout.addMember(form);
			layout.addMember(buttonContainer);

			this.addChild(layout);
		}

		private void removeCondition() {
			this.destroy();
		}

		private void setData(	Map<String, String> declarationMapping,
								String leftOperand,
								String operator,
								String rightOperand) {
			if (declarationMapping != null) {
				this.leftOperandAlias.setValueMap((LinkedHashMap<String, String>) declarationMapping);
				this.rightOperandAlias.setValueMap((LinkedHashMap<String, String>) declarationMapping);
			}

			if (this.operator != null) {
				this.operator.setValueMap(this.getConditionList());
				this.operator.setValue(operator);
			}

			if (leftOperand != null) {
				this.leftOperandAlias.setValue(leftOperand.substring(0, leftOperand.indexOf(".")));
				this.leftOperandProperty.setValue(leftOperand.substring(leftOperand.indexOf(".") + 1,
						leftOperand.length()));
			}

			if (rightOperand != null) {
				if (rightOperand.indexOf(".") != -1) {
					this.rightOperandType.setValue("Property");
				} else {
					this.rightOperandType.setValue("Expression");
				}
			} else {
				this.rightOperandType.setValue("Expression");
			}
			this.addRightOperandFields(rightOperand);
		}

		private LinkedHashMap<String, String> getConditionList() {
			LinkedHashMap<String, String> conditionList = new LinkedHashMap<String, String>();
			conditionList.put("==", "is equals to");
			conditionList.put(">", "is greater than");
			conditionList.put("<", "is less than");
			conditionList.put(">=", "is greater than equal to");
			conditionList.put("<=", "is less than equal to");
			conditionList.put("!=", "is not equal to");

			return conditionList;
		}

		public String getValue() {
			StringBuilder value = new StringBuilder();
			value.append(this.leftOperandAlias.getValueAsString() + ".");
			value.append(this.leftOperandProperty.getValueAsString());
			value.append(this.operator.getValueAsString());
			if (this.rightOperandType.getValueAsString().equals("Property")) {
				value.append(this.rightOperandAlias.getValueAsString() + ".");
			}
			value.append(this.rightOperand.getValueAsString() + ";");

			return value.toString();
		}

		public void setAliasList(Map<String, String> declarationMapping) {
			this.leftOperandAlias.setValueMap((LinkedHashMap<String, String>) declarationMapping);
			this.rightOperandAlias.setValueMap((LinkedHashMap<String, String>) declarationMapping);
		}

		private void addRightOperandFields(String rightOperandData) {
			if (this.rightOperandType.getValueAsString().equals("Expression")) {
				this.rightOperandAlias.hide();
				this.rightOperand.show();
				if (rightOperandData != null) {
					this.rightOperand.setValue(rightOperandData);
				}
			} else {
				this.rightOperandAlias.show();
				if (rightOperandData != null) {
					this.rightOperandAlias.setValue(rightOperandData.substring(0, rightOperandData.indexOf(".")));
				}
				this.rightOperand.show();
				if (rightOperandData != null) {
					this.rightOperand.setValue(rightOperandData.substring(rightOperandData.indexOf(".") + 1,
							rightOperandData.length()));
				}
			}
		}
	}
}
