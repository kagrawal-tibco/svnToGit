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

/**
 * This class provides a singleton Factory instance to create Rule function
 * editors
 * 
 * @author Vikram Patil
 */
public class RuleFunctionEditorFactory implements IEditorFactory {
	private static RuleFunctionEditorFactory instance;

	/**
	 * Create a singleton to create Rule Function Editors
	 * 
	 * @return
	 */
	public static RuleFunctionEditorFactory getInstance() {
		if (instance == null) {
			instance = new RuleFunctionEditorFactory();
		}
		return instance;
	}

	private RuleFunctionEditorFactory() {
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
		return new RuleFunctionEditor(selectedRecord, isReadOnly, revisionId);
	}

	/**
	 * Editor class for Rule Function. Provides various sections
	 * implementations. Additionally supports handling editor events.
	 */
	class RuleFunctionEditor extends AbstractEditor {

		/**
		 * Initialises and sets up the rule function editor
		 * 
		 * @param selectedRecord
		 */
		public RuleFunctionEditor(NavigatorResource selectedRecord) {
			this(selectedRecord, false, null);
		}
		
		/**
		 * Initialises and sets up the rule function editor
		 * 
		 * @param selectedRecord
		 * @param isReadOnly
		 * @param revisionId
		 */
		public RuleFunctionEditor(NavigatorResource selectedRecord, boolean isReadOnly, String revisionId) {
			super(selectedRecord, isReadOnly, revisionId, true);
			this.initialize();
		}

		@Override
		protected SectionStackSection[] getSections() {
			return new SectionStackSection[] { this.createConfigurationSection(), this.createScopeSection(),
					this.createBodySection() };
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
					RuleFunctionEditor.this.makeDirty();
				}
			});

			TextItem alias = new TextItem("alias", "Alias");
			alias.setWidth("*");

			CheckboxItem virtual = new CheckboxItem("virtual", "Virtual");
			virtual.setTitleOrientation(TitleOrientation.LEFT);
			virtual.setLabelAsTitle(true);

			SelectItem validity = new SelectItem("priority", "Priority");
			validity.setWidth("*");
			validity.setValueMap("Action", "Action,Condition", "Action,Condition,Query");
			validity.setDefaultToFirstOption(true);

			descForm.setItems(description, alias, virtual, validity);
			formContainer.addMember(descForm);

			// Return Type Form
			HLayout returnTypeContainer = new HLayout(5);
			returnTypeContainer.setWidth100();
			returnTypeContainer.setHeight(25);

			DynamicForm returnTypeForm = new DynamicForm();
			returnTypeForm.setWidth100();

			TextItem returnType = new TextItem("rank", "Rank");
			returnType.setWidth("*");

			returnTypeForm.setItems(returnType);
			returnTypeContainer.addMember(returnTypeForm);

			IButton browse = new IButton("Browse");
			returnTypeContainer.addMember(browse);

			formContainer.addMember(returnTypeContainer);

			configurationSection.addItem(formContainer);

			return configurationSection;
		}

		/**
		 * Creates a Scope Section
		 * 
		 * @return
		 */
		private SectionStackSection createScopeSection() {
			SectionStackSection scopeSection = new SectionStackSection("Scope");
			scopeSection.setExpanded(true);
			scopeSection.setCanCollapse(true);

			ListGrid propertiesGrid = new ListGrid();
			propertiesGrid.setWidth100();
			propertiesGrid.setHeight(300);
			propertiesGrid.setShowHeaderContextMenu(false);
			propertiesGrid.setShowHeaderMenuButton(false);
			propertiesGrid.setMargin(10);

			ListGridField term = new ListGridField("term", "Term");
			term.setWidth("50%");
			ListGridField alias = new ListGridField("alias", "Alias");
			alias.setWidth("50%");

			propertiesGrid.setFields(term, alias);
			scopeSection.addItem(propertiesGrid);

			return scopeSection;
		}

		/**
		 * Creates an section for various actions
		 * 
		 * @return
		 */
		private SectionStackSection createBodySection() {
			SectionStackSection bodySection = new SectionStackSection("Body");
			bodySection.setExpanded(true);
			bodySection.setCanCollapse(true);

			DynamicForm whenForm = new DynamicForm();
			whenForm.setWidth100();
			whenForm.setPadding(10);
			whenForm.setNumCols(2);
			whenForm.setColWidths("50%", "50%");

			TextAreaItem body = new TextAreaItem("body");
			body.setShowTitle(false);
			body.setWidth("*");
			body.setHeight(50);
			body.setColSpan(2);

			whenForm.setItems(body);

			bodySection.addItem(whenForm);

			return bodySection;
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
