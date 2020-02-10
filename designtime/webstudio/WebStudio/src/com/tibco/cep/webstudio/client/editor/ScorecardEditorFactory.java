/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory.RuleTemplateInstanceEditor.TemplateInstanceChangeHandler;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;

/**
 * This class provides a singleton Factory instance to create Scorecard editors
 * 
 * @author Vikram Patil
 */
public class ScorecardEditorFactory implements IEditorFactory {
	private static ScorecardEditorFactory instance;

	/**
	 * Create a singleton to create Scorecard Editors
	 * 
	 * @return
	 */
	public static ScorecardEditorFactory getInstance() {
		if (instance == null) {
			instance = new ScorecardEditorFactory();
		}
		return instance;
	}

	private ScorecardEditorFactory() {
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
		return new ScorecardEditor(selectedRecord, isReadOnly, revisionId);
	}

	/**
	 * Editor class for Scorecard. Provides various sections implementations.
	 * Additionally supports handling editor events.
	 */
	class ScorecardEditor extends AbstractEditor {
		private TextAreaItem description;

		/**
		 * Initialises and setsup the scorecard editor
		 * 
		 * @param selectedRecord
		 */
		public ScorecardEditor(NavigatorResource selectedRecord) {
			this(selectedRecord, false, null);
		}
		
		/**
		 * Initialises and setsup the scorecard editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 */
		public ScorecardEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
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
			configurationSection.setCanCollapse(false);

			HLayout formContainer = new HLayout();
			formContainer.setWidth100();
			formContainer.setLeft(0);

			DynamicForm configForm = new DynamicForm();
			configForm.setWidth100();
			configForm.setMargin(10);

			this.description = new TextAreaItem("description", "Description");
			this.description.setWidth("*");
			this.description.setHeight(50);
			this.description.addChangedHandler(new ChangedHandler() {
				public void onChanged(ChangedEvent event) {
					ScorecardEditor.this.makeDirty();
				}
			});

			configForm.setItems(this.description);
			formContainer.addMember(configForm);

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
			CustomSC.say("Dirty Event generated.");
		}

		@Override
		public void onSave() {
			CustomSC.say("Save Event generated.");
		}
		
		@Override
		public boolean onValidate() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onSuccess(HttpSuccessEvent event) {
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
