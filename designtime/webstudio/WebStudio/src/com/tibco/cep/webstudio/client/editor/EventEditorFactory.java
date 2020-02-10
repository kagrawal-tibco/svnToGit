/**
 * 
 */
package com.tibco.cep.webstudio.client.editor;

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
import com.tibco.cep.webstudio.client.panels.CustomSC;

/**
 * This class provides a singleton Factory instance to create Event editors
 * 
 * @author Vikram Patil
 */
public class EventEditorFactory implements IEditorFactory {
	private static EventEditorFactory instance;

	/**
	 * Create a singleton to create Event Editors
	 * 
	 * @return
	 */
	public static EventEditorFactory getInstance() {
		if (instance == null) {
			instance = new EventEditorFactory();
		}
		return instance;
	}

	private EventEditorFactory() {
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
		return new EventEditor(selectedRecord, isReadOnly, revisionId);
	}

	/**
	 * Editor class for Event. Provides various sections implementations.
	 * Additionally supports handling editor events.
	 */
	class EventEditor extends AbstractEditor {

		/**
		 * Initialises and setup the event editor
		 * 
		 * @param selectedRecord
		 */
		public EventEditor(NavigatorResource selectedRecord) {
			this(selectedRecord, false, null);
		}
		
		/**
		 * Initialises and setup the event editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 */
		public EventEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
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
					EventEditor.this.makeDirty();
				}
			});
			descForm.setItems(description);
			formContainer.addMember(descForm);

			// Inherits Form
			HLayout inheritContainer = new HLayout(5);
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

			// Time to live form
			DynamicForm ttlForm = new DynamicForm();
			ttlForm.setWidth100();
			ttlForm.setNumCols(3);
			ttlForm.setColWidths("10%", "83%", "*");

			TextItem ttl = new TextItem("ttl", "Time to Live");
			ttl.setWidth("*");
			ttl.setValue(0);

			SelectItem timeValue = new SelectItem("timeValue");
			timeValue.setShowTitle(false);
			timeValue.setWidth(105);
			timeValue.setValueMap("Milliseconds", "Seconds", "Minutes", "Hours", "Days");
			timeValue.setValue("Seconds");
			ttlForm.setItems(ttl, timeValue);

			formContainer.addMember(ttlForm);

			// Default Destination
			HLayout destinationContainer = new HLayout(5);
			destinationContainer.setWidth100();
			destinationContainer.setHeight(25);

			DynamicForm destinationForm = new DynamicForm();
			destinationForm.setWidth100();

			TextItem defaultDestination = new TextItem("destination", "Default Destination");
			defaultDestination.setWidth("*");

			destinationForm.setItems(defaultDestination);
			destinationContainer.addMember(destinationForm);

			IButton destBrowse = new IButton("Browse");
			destinationContainer.addMember(destBrowse);

			formContainer.addMember(destinationContainer);

			// State Model form
			DynamicForm retryForm = new DynamicForm();
			retryForm.setWidth100();

			CheckboxItem retryonException = new CheckboxItem("retryonException", "Retry On Exception");
			retryonException.setTitleOrientation(TitleOrientation.LEFT);
			retryonException.setLabelAsTitle(true);

			retryForm.setItems(retryonException);

			formContainer.addMember(retryForm);

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

			ListGridField name = new ListGridField("name", "Name", 392);
			ListGridField type = new ListGridField("type", "Type", 392);
			ListGridField domain = new ListGridField("domain", "Domain", 392);

			propertiesGrid.setFields(name, type, domain);
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
