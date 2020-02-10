/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.MultipleAppearance;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor.TemplateInstanceChangeHandler;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.model.NavigatorResource;

/**
 * This class provides a singleton Factory instance to create Concept editors
 * 
 * @author Vikram Patil
 */
public class ConceptEditorFactory implements IEditorFactory {
	private static ConceptEditorFactory instance;

	/**
	 * Create a singleton to create Concept Editors
	 * 
	 * @return
	 */
	public static ConceptEditorFactory getInstance() {
		if (instance == null) {
			instance = new ConceptEditorFactory();
		}
		return instance;
	}

	private ConceptEditorFactory() {
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
		return new ConceptEditor(selectedRecord, isReadOnly, revisionId);
	}

	/**
	 * Editor class for Concept. Provides various sections implementations.
	 * Additionally supports handling editor events.
	 */
	class ConceptEditor extends AbstractEditor {

		/**
		 * Initialises and setup the concept editor
		 * 
		 * @param selectedRecord
		 */
		public ConceptEditor(NavigatorResource selectedRecord) {
			this(selectedRecord, false, null);
		}
		
		/**
		 * Initialises and setup the concept editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 */
		public ConceptEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
			super(selectedRecord, isReadOnly, revisionId, true);
			this.initialize();
		}

		@Override
		protected SectionStackSection[] getSections() {
			return new SectionStackSection[] { this.createConfigurationSection(), this.createPropertiesSection() };
		}

		/**
		 * Creates a configuration Section
		 * 
		 * @return
		 */
		private SectionStackSection createConfigurationSection() {
			SectionStackSection configurationSection = new SectionStackSection("Configuration");
			configurationSection.setExpanded(true);
			configurationSection.setCanCollapse(true);

			VLayout formContainer = new VLayout(10);
			formContainer.setWidth100();
			formContainer.setLeft(0);
			formContainer.setMargin(10);

			// Description Form
			DynamicForm descForm = new DynamicForm();
			descForm.setWidth100();

			TextAreaItem description = new TextAreaItem("description", "Description");
			description.setWidth("*");
			description.setHeight(50);
			description.addChangedHandler(new ChangedHandler() {
				public void onChanged(ChangedEvent event) {
					ConceptEditor.this.makeDirty();
				}
			});
			descForm.setItems(description);
			formContainer.addMember(descForm);

			// Inherits Form
			HLayout inheritContainer = new HLayout(10);
			inheritContainer.setWidth100();
			inheritContainer.setHeight(25);

			DynamicForm inheritForm = new DynamicForm();
			inheritForm.setWidth100();

			TextItem inherits = new TextItem("inherits", "Inherits From");
			inherits.setWidth("*");

			inheritForm.setItems(inherits);
			inheritContainer.addMember(inheritForm);

			IButton browse = new IButton("Browse");
			inheritContainer.addMember(browse);

			formContainer.addMember(inheritContainer);

			// State Model form
			DynamicForm stateModelForm = new DynamicForm();
			stateModelForm.setWidth100();

			SelectItem stateModels = new SelectItem("stateModels", "State Models");
			stateModels.setMultiple(true);
			stateModels.setMultipleAppearance(MultipleAppearance.GRID);
			stateModels.setWidth("*");

			CheckboxItem autoStartStateModel = new CheckboxItem("autoStart", "Auto Start State Model");
			autoStartStateModel.setTitleOrientation(TitleOrientation.LEFT);
			autoStartStateModel.setLabelAsTitle(true);

			stateModelForm.setItems(stateModels, autoStartStateModel);

			formContainer.addMember(stateModelForm);

			configurationSection.addItem(formContainer);

			return configurationSection;
		}

		/**
		 * Creates a properties Section
		 * 
		 * @return
		 */
		private SectionStackSection createPropertiesSection() {
			SectionStackSection propertiesSection = new SectionStackSection("Properties");
			propertiesSection.setExpanded(true);
			propertiesSection.setCanCollapse(true);

			ListGrid propertiesGrid = new ListGrid();
			propertiesGrid.setWidth100();
			propertiesGrid.setHeight100();
			propertiesGrid.setShowHeaderContextMenu(false);
			propertiesGrid.setShowHeaderMenuButton(false);
			propertiesGrid.setMargin(10);

			ListGridField name = new ListGridField("name", "Name", 275);
			ListGridField type = new ListGridField("type", "Type", 160);
			ListGridField multiple = new ListGridField("multiple", "Multiple", 160);
			multiple.setType(ListGridFieldType.BOOLEAN);
			ListGridField policy = new ListGridField("policy", "Policy", 225);
			ListGridField history = new ListGridField("history", "History", 160);
			ListGridField domain = new ListGridField("domain", "Domain", 200);

			propertiesGrid.setFields(name, type, multiple, policy, history, domain);
			propertiesSection.addItem(propertiesGrid);

			return propertiesSection;
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
		public boolean onValidate() {
			// TODO Auto-generated method stub
			return false;
		}

		public void onSuccess(HttpSuccessEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		protected Canvas getWidget() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TemplateInstanceChangeHandler getChangeHandler() {
			// TODO Auto-generated method stub
			return null;
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
}
