package com.tibco.cep.webstudio.client.process.properties.tabs;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabDeselectedEvent;
import com.smartgwt.client.widgets.tab.events.TabDeselectedHandler;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.ProcessMessages;
import com.tibco.cep.webstudio.client.process.AbstractProcessEditor;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.handler.DocumentationChangedHandler;
import com.tibco.cep.webstudio.client.process.handler.GeneralPropertyChangedHandler;
import com.tibco.cep.webstudio.client.process.properties.DocumentationProperty;
import com.tibco.cep.webstudio.client.process.properties.Property;

/**
 * This is the base class for all tabs in related to property.
 * 
 * @author dijadhav
 * 
 */
public abstract class PropertyTabSet extends TabSet implements
		TabSelectedHandler, TabDeselectedHandler {
	protected Property property;
	protected ProcessMessages processMessages = (ProcessMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.PROCESS_MESSAGES);
	/**
	 * Path for images
	 */
	protected String iconPath = Page.getAppImgDir() + "icons/16/";
	/**
	 * General property change handler.
	 */
	protected GeneralPropertyChangedHandler generalPropertyChangedHandler = new GeneralPropertyChangedHandler();
	/**
	 * Documentation property change handler.
	 */
	protected DocumentationChangedHandler documentationChangeHandler = new DocumentationChangedHandler();

	private Tab generaltab;
	private Tab documentationTab;

	public PropertyTabSet(Property property) {
		this();
		this.property = property;
	}

	public PropertyTabSet() {
		super();
		if(LocaleInfo.getCurrentLocale().isRTL()){
			setTabBarPosition(Side.RIGHT);
			setTabBarAlign(Side.RIGHT);
		}
		else{
			setTabBarPosition(Side.LEFT);
			setTabBarAlign(Side.LEFT);
		}
		setWidth100();
		setHeight100();
		setTabBarThickness(110);
		addTabSelectedHandler(this);
		addTabDeselectedHandler(this);
		setShowTabScroller(true);
		setShowTabPicker(false);

	}

	public void hideTab(Tab tab) {
		removeTab(tab);
	}

	public void showTab(Tab tab) {
		addTab(tab);
	}

	/**
	 * @return the generaltab
	 */
	public Tab getGeneraltab() {
		return generaltab;
	}

	/**
	 * @param generaltab
	 *            the generaltab to set
	 */
	public void setGeneraltab(Tab generaltab) {
		this.generaltab = generaltab;
	}

	/**
	 * @return the documentationTab
	 */
	public Tab getDocumentationTab() {
		return documentationTab;
	}

	/**
	 * @param documentationTab
	 *            the documentationTab to set
	 */
	public void setDocumentationTab(Tab documentationTab) {
		this.documentationTab = documentationTab;
	}

	public void createTabSet() {
		generaltab = new Tab(processMessages.processPropertyTabTitleGeneral());
		generaltab.setAttribute("height", "19px");
		documentationTab = new Tab(
				processMessages.processPropertyTabTitleDocumentation());
		documentationTab.setAttribute("height", "19px");
		this.setTabs(generaltab, documentationTab);
	}

	protected DynamicForm createGeneralPropertyForm() {
		DynamicForm generalForm = new DynamicForm();
		generalForm.setLeft("0px");
		generalForm.setWidth(510);
		generalForm.setHeight100();
		generalForm.setIsGroup(false);
		return generalForm;
	}

	/**
	 * This method is sued to create the documentation form for process.
	 * 
	 * @return
	 */
	protected DynamicForm createDocumentationPropertyForm() {
		DynamicForm docForm = new DynamicForm();
		docForm.setLeft("0px");
		docForm.setWidth(650);
		docForm.setHeight(200);
		docForm.setIsGroup(false);
		return docForm;
	}

	/**
	 * This method is used to create the name item with given value.
	 * 
	 * @param value
	 * @return
	 */
	protected TextItem getNameItem(String value) {
		TextItem nameItem = new TextItem();
		nameItem.setName(ProcessConstants.NAME);
		nameItem.setTitle(processMessages.processPropertyTabGeneralName());
		nameItem.setCanEdit(false);
		nameItem.setWidth(400);
		nameItem.setVisible(true);
		nameItem.setValue(value);
		nameItem.setAttribute("style", "background-color:lightgray");
		return nameItem;
	}

	/**
	 * This method is used to get the label item with given value.
	 * 
	 * @param value
	 * @return
	 */
	protected TextItem getLabelItem(String value) {
		TextItem labelItem = new TextItem();
		labelItem.setName(ProcessConstants.LABEL);
		labelItem.setTitle(processMessages.processPropertyTabGeneralLabel());
		labelItem.setCanEdit(true);
		labelItem.setWidth(400);
		labelItem.setVisible(true);
		labelItem.setValue(value);
		labelItem.addBlurHandler(generalPropertyChangedHandler);
		labelItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
						.get().getEditorPanel().getSelectedTab();
				if (!processEditor.isDirty()) {
					processEditor.makeDirty();
				}
			}
		});
		return labelItem;
	}

	@Override
	public void onTabDeselected(TabDeselectedEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(TabSelectedEvent event) {
		if (processMessages.processPropertyTabTitleGeneral().equals(
				event.getTab().getTitle())) {
			this.setSelectedTab(0);
		} else if (processMessages.processPropertyTabTitleDocumentation()
				.equals(event.getTab().getTitle())) {
			this.setSelectedTab(1);
			if (null == this.getTab(1).getPane()) {
				this.fetchProperty(ProcessConstants.DOCUMENTATION_PROPERTY);
			}
		}
	}

	/**
	 * This method is used to fetch the property of given type.
	 * 
	 * @param propertyType
	 */
	protected void fetchProperty(String propertyType) {
		Canvas canvas = generaltab.getPane();
		String selectedItemId = "";
		DynamicForm dynamicForm = null;
		if (canvas instanceof DynamicForm) {
			dynamicForm = (DynamicForm) canvas;
		} else if (canvas instanceof HLayout) {
			HLayout hLayout = (HLayout) canvas;
			dynamicForm = (DynamicForm) hLayout.getMember(0);
		}
		if (null != dynamicForm) {
			FormItem nameField = dynamicForm.getField(ProcessConstants.NAME);
			if (null != nameField) {
				selectedItemId = (String) nameField.getValue();
				AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
						.get().getEditorPanel().getSelectedTab();
				processEditor.fetchProperties(selectedItemId, propertyType);
			}
		}
	}

	/**
	 * @return the property
	 */
	public Property getProperty() {
		return property;
	}

	/**
	 * @param property
	 *            the property to set
	 */
	public void setProperty(Property property) {
		this.property = property;
	}

	/**
	 * Create the documentation property form.
	 * 
	 * @param documentationProperty
	 */
	protected void createDocumentationPropertyForm(
			DocumentationProperty documentationProperty) {
		DynamicForm docForm = createDocumentationPropertyForm();
		TextAreaItem richTextItem = new TextAreaItem("doc");
		richTextItem.setShowTitle(false);
		richTextItem.setCanEdit(true);
		richTextItem.setDisabled(false);
		richTextItem.setShowDisabled(false);
		richTextItem.setCanFocus(true);
		richTextItem.setShowFocused(true);
		richTextItem.setWidth(600);
		
		richTextItem.setValue(documentationProperty.getText());
		richTextItem.addBlurHandler(documentationChangeHandler);
		documentationChangeHandler.setType(documentationProperty
				.getElementType());
		richTextItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				AbstractProcessEditor processEditor = (AbstractProcessEditor) WebStudio
						.get().getEditorPanel().getSelectedTab();
				if (!processEditor.isDirty()) {
					processEditor.makeDirty();
				}
			}
		});
		docForm.setItems(richTextItem);

		/*
		 * final RichTextEditor richTextEditor = new RichTextEditor();
		 * richTextEditor.setHeight(155);
		 * richTextEditor.setOverflow(Overflow.HIDDEN);
		 * richTextEditor.setCanDragResize(true);
		 * richTextEditor.setShowEdges(true); richTextEditor.setWidth(600);
		 * HLayout layout = new HLayout(); layout.setWidth(600);
		 * 
		 * richTextEditor.setValue(documentationProperty.getText());
		 * layout.addMember(richTextEditor); CanvasItem canvasItem = new
		 * CanvasItem(); canvasItem.setCanEdit(true);
		 * canvasItem.setDisabled(false); canvasItem.setWidth(600);
		 * canvasItem.setShowTitle(false); docForm.setDisabled(false);
		 * docForm.setItems(canvasItem);
		 */
		getDocumentationTab().setPane(docForm);
	}
}
