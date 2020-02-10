package com.tibco.cep.webstudio.client.portal;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickEvent;
import com.smartgwt.client.widgets.layout.events.SectionHeaderClickHandler;
import com.tibco.cep.webstudio.client.PortalLayout;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.RTIFieldType;
import com.tibco.cep.webstudio.client.preferences.ApplicationPreferenceHelper;
import com.tibco.cep.webstudio.client.widgets.OperatorDisplayNameUtil;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Class for application preferences portlet
 * 
 * @author apsharma
 */
public class ApplicationPreferencePortlet extends WebStudioPortlet {

	private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private static Label selectedFieldTypeLabel = null;
	private static String selectedFieldType = null;
	private static DynamicForm filterOperatorForm = null;
	private static DynamicForm commandOperatorForm = null;
	private static Label selectedArrow;
	private static IButton applyButton;
	private SectionStackSection section3 = null;

	public ApplicationPreferencePortlet(PortalLayout portalLayout) {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.webstudio.client.portal.WebStudioPortlet#initialize()
	 */
	protected void initialize() {
		if (initialized) {
			return;
		}
		
		final Map<String, String> expanded = new HashMap<String, String>();
		this.setTitle(globalMsgBundle.application_preferences());
		this.setHeight(570);
		final SectionStack sectionStack = new SectionStack();  
        sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
        sectionStack.setWidth100();
        sectionStack.setHeight("98%");
        
        final SectionStackSection section1 = new SectionStackSection(globalMsgBundle.operatorSectionString());  
        section1.setExpanded(false);
        section1.setCanCollapse(true);
        section1.setResizeable(false);
        Canvas canvas = createConfigurationCanvas();
        section1.addItem(canvas);
        sectionStack.addSection(section1);
		
        final SectionStackSection section2 = new SectionStackSection(globalMsgBundle.notificationSectionString());  
        section2.setExpanded(false);
        section2.setCanCollapse(true);
        NotificationPortlet np = new NotificationPortlet();
        section2.addItem(np.getCanvas());  
        sectionStack.addSection(section2);
        
        section3 = new SectionStackSection(globalMsgBundle.aclSectionString());  
        section3.setExpanded(true);
        section3.setCanCollapse(true);
        section3.setResizeable(false);
        RoleConfigurationPortlet Rp = new RoleConfigurationPortlet();
        section3.addItem(Rp.getCanvas());
        sectionStack.addSection(section3);
        
		sectionStack.addSectionHeaderClickHandler(new SectionHeaderClickHandler() {
			
			@Override
			public void onSectionHeaderClick(SectionHeaderClickEvent event) {
				if(event.getSection().getTitle().equalsIgnoreCase(globalMsgBundle.operatorSectionString())){
					for(Canvas c : section3.getItems()){
					  c.destroy();	
					}
					section2.setExpanded(false);
					section3.setExpanded(false);
				}else if(event.getSection().getTitle().equalsIgnoreCase(globalMsgBundle.notificationSectionString())){
					for(Canvas c : section3.getItems()){
						  c.destroy();	
						}
					section1.setExpanded(false);
					section3.setExpanded(false);
				}else if(event.getSection().getTitle().equalsIgnoreCase(globalMsgBundle.aclSectionString())){
					if(section3.getItems().length == 0){
						RoleConfigurationPortlet Rp = new RoleConfigurationPortlet();
				        section3.addItem(Rp.getCanvas());
					}
					section1.setExpanded(false);
					section2.setExpanded(false);
				}
				
				
			}
		});
        
		this.setModularCanvas(sectionStack);
		this.setShowCloseButton(false);

		initialized = true;
	}

	/**
	 * Method to create the portlet canvas.
	 * 
	 * @return
	 */
	private Canvas createConfigurationCanvas() {
		VLayout stack = new VLayout();
		stack.setWidth100();
		stack.setHeight100();
		stack.setStyleName("ws-app-pref-portlet");

		HLayout gridsLayout = new HLayout();
		gridsLayout.setHeight100();
		gridsLayout.setWidth100();
		gridsLayout.setStyleName("ws-app-pref-op-outerLayout");
		gridsLayout.setCanHover(true);
		gridsLayout.setHoverWidth(300);
		gridsLayout.setShowHover(true);

		createFieldTypeLayout(gridsLayout);
		createOperatorLayout(gridsLayout);

		HLayout buttonContainer = new HLayout();
		buttonContainer.setStyleName("ws-app-pref-button");
		buttonContainer.setWidth100();
		buttonContainer.setHeight("20%");
		buttonContainer.setAlign(Alignment.RIGHT);

		applyButton = new IButton(globalMsgBundle.button_apply());
		buttonContainer.addMember(applyButton);
		applyButton.disable();
		applyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ApplicationPreferenceHelper.getInstance()
						.updateApplicationPreferences();
			}
		});

		stack.addMember(gridsLayout);
		stack.addMember(buttonContainer);

		return stack;
	}

	/**
	 * Method to create operator Layout.
	 * 
	 * @param parent
	 */
	private void createOperatorLayout(HLayout parent) {
		HLayout gridsLayout = new HLayout();
		gridsLayout.setHeight100();
		gridsLayout.setWidth("70%");
		createFilterOperatorLayout(gridsLayout);
		createCommandOperatorLayout(gridsLayout);
		parent.addMember(gridsLayout);
	}

	/**
	 * Method to create Filter Operators Layout.
	 * 
	 * @param parent
	 */
	private void createFilterOperatorLayout(HLayout parent) {

		VLayout stack = new VLayout();
		parent.addMember(stack);
		stack.setBorder("1px solid #ABABAB");
		stack.setStyleName("ws-app-pref-commandop");

		Label filterOperatorLabel = new Label();
		filterOperatorLabel.setContents(globalMsgBundle.filter_operators());
		filterOperatorLabel.setAlign(Alignment.CENTER);
		filterOperatorLabel.setBackgroundColor("#DEDEDE");
		filterOperatorLabel.setAutoHeight();
		filterOperatorLabel.setPadding(10);

		filterOperatorForm = new DynamicForm();
		filterOperatorForm.setNumCols(1);
		filterOperatorForm.setWidth100();
		filterOperatorForm.setOverflow(Overflow.AUTO);

		Map<IBuilderOperator, Boolean> operatorsMap = ApplicationPreferenceHelper
				.getInstance()
				.getOperatorsMap(ApplicationPreferenceHelper.FILTER_OPERATOR,
						selectedFieldTypeLabel.getContents(), selectedFieldType);
		setOperatorsForm(filterOperatorForm,
				ApplicationPreferenceHelper.FILTER_OPERATOR, operatorsMap);
		filterOperatorForm.setTitle("");
		stack.addMember(filterOperatorLabel);
		stack.addMember(filterOperatorForm);
	}

	/**
	 * Method to create command operators layout.
	 * 
	 * @param parent
	 */
	private void createCommandOperatorLayout(HLayout parent) {

		VLayout stack = new VLayout();
		stack.setStyleName("ws-app-pref-commandop");
		parent.addMember(stack);
		stack.setBorder("1px solid #ABABAB");

		Label cmdOperatorLabel = new Label();
		cmdOperatorLabel.setContents(globalMsgBundle.command_operators());
		cmdOperatorLabel.setAlign(Alignment.CENTER);
		cmdOperatorLabel.setBackgroundColor("#DEDEDE");
		cmdOperatorLabel.setAutoHeight();
		cmdOperatorLabel.setPadding(10);

		commandOperatorForm = new DynamicForm();
		commandOperatorForm.setNumCols(1);
		commandOperatorForm.setWidth100();
		commandOperatorForm.setOverflow(Overflow.AUTO);

		Map<IBuilderOperator, Boolean> operatorsMap = ApplicationPreferenceHelper
				.getInstance()
				.getOperatorsMap(ApplicationPreferenceHelper.COMMAND_OPERATOR,
						selectedFieldTypeLabel.getContents(), selectedFieldType);
		setOperatorsForm(commandOperatorForm,
				ApplicationPreferenceHelper.COMMAND_OPERATOR, operatorsMap);
		commandOperatorForm.setTitle("");
		stack.addMember(cmdOperatorLabel);
		stack.addMember(commandOperatorForm);
	}

	/**
	 * Method to create Field Type layout.
	 * 
	 * @param parent
	 */
	private void createFieldTypeLayout(HLayout parent) {
		VLayout stack = new VLayout();
		parent.addMember(stack);

		stack.setStyleName("ws-app-pref-fieldTypeStack");
		stack.setHeight100();
		stack.setWidth100();

		Label fieldTypeLabel = new Label();
		fieldTypeLabel.setContents(globalMsgBundle.field_type());
		fieldTypeLabel.setAlign(Alignment.CENTER);
		fieldTypeLabel.setBackgroundColor("#DEDEDE");
		fieldTypeLabel.setAutoHeight();
		fieldTypeLabel.setPadding(10);
		fieldTypeLabel.setWidth100();
		stack.addMember(fieldTypeLabel);

		RTIFieldType[] fieldTypes = RTIFieldType.values();
		int count = 0;
		for (final RTIFieldType fieldType : fieldTypes) {
			final HLayout fieldLayout = new HLayout();
			fieldLayout.setWidth100();

			final Img fieldIcon = new Img(ApplicationPreferenceHelper
					.getInstance().getFieldTypeValueIconMap()
					.get(fieldType.getDisplayText()));
			fieldIcon.setHeight(16);
			fieldIcon.setWidth(16);
			fieldIcon.setPadding(6);
			fieldIcon.show();

			final Label fieldLabel = new Label();
			fieldLabel.setAutoHeight();
			fieldLabel.setContents(fieldType.getDisplayText());
			fieldLabel.setPadding(6);
			fieldLabel.setAlign(Alignment.LEFT);

			final HLayout arrowLayout = new HLayout();
			arrowLayout.setAlign(Alignment.RIGHT);
			arrowLayout.setWidth100();
			final Label arrowLabel = new Label();
			arrowLabel.setAutoHeight();
			arrowLabel.setAutoWidth();
			arrowLabel.setContents("&#9654;");
			arrowLabel.hide();
			arrowLabel.setStyleName("ws-app-pref-arrow");
			arrowLabel.setAlign(Alignment.RIGHT);
			arrowLayout.addMember(arrowLabel);

			fieldLayout.addMember(fieldIcon);
			fieldLayout.addMember(fieldLabel);
			fieldLayout.addMember(arrowLayout);

			if (count == 0) {
				fieldLabel.setStyleName("ws-app-pref-fieldtype-onclick");
				selectedFieldType = fieldType.getValue();
				selectedArrow = arrowLabel;
				selectedArrow.show();
				selectedFieldTypeLabel = fieldLabel;
			} else {
				fieldLabel.setStyleName("ws-app-pref-fieldtype");
			}
			fieldLabel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (selectedFieldTypeLabel != null) {
						selectedFieldTypeLabel
								.setStyleName("ws-app-pref-fieldtype");
					}
					arrowLabel.show();
					if (!fieldLabel.equals(selectedFieldTypeLabel)) {
						selectedArrow.hide();
					}
					selectedArrow = arrowLabel;
					fieldLabel.setStyleName("ws-app-pref-fieldtype-onclick");
					selectedFieldTypeLabel = fieldLabel;
					selectedFieldType = fieldType.getValue();
					setOperators();
				}
			});
			fieldLabel.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if (!fieldLabel.equals(selectedFieldTypeLabel)) {
						fieldLabel
								.setStyleName("ws-app-pref-fieldtype-onHover");
					}
				}
			});

			fieldLabel.addMouseOutHandler(new MouseOutHandler() {

				@Override
				public void onMouseOut(MouseOutEvent event) {
					if (!fieldLabel.equals(selectedFieldTypeLabel)) {
						fieldLabel.setStyleName("ws-app-pref-fieldtype");
					}
				}
			});

			stack.addMember(fieldLayout);
			count++;
		}
	}

	/**
	 * Method to set filter and command operators on change of Field Type.
	 */
	private static void setOperators() {
		Map<IBuilderOperator, Boolean> filterOperatorsMap = ApplicationPreferenceHelper
				.getInstance()
				.getOperatorsMap(ApplicationPreferenceHelper.FILTER_OPERATOR,
						selectedFieldTypeLabel.getContents(), selectedFieldType);
		Map<IBuilderOperator, Boolean> commandOperatorsMap = ApplicationPreferenceHelper
				.getInstance()
				.getOperatorsMap(ApplicationPreferenceHelper.COMMAND_OPERATOR,
						selectedFieldTypeLabel.getContents(), selectedFieldType);
		setOperatorsForm(filterOperatorForm,
				ApplicationPreferenceHelper.FILTER_OPERATOR, filterOperatorsMap);
		setOperatorsForm(commandOperatorForm,
				ApplicationPreferenceHelper.COMMAND_OPERATOR,
				commandOperatorsMap);
	}

	/**
	 * Method to set the operator of the given operator type in the given form .
	 * 
	 * @param operatorsForm
	 * @param operatorType
	 * @param operatorsMap
	 */
	private static void setOperatorsForm(DynamicForm operatorsForm,
			final String operatorType,
			Map<IBuilderOperator, Boolean> operatorsMap) {
		final CheckboxItem[] items = new CheckboxItem[operatorsMap.size()];
		int count = 0;
		for (Entry<IBuilderOperator, Boolean> entry : operatorsMap.entrySet()) {
			String operator = entry.getKey().getValue();
			final CheckboxItem item = new CheckboxItem();
			item.setTitle(OperatorDisplayNameUtil.getOperator(operator));
			item.setShowTitle(false);
			item.setTextBoxStyle("ws-app-pref-operators");
			item.setValue(entry.getValue());
			item.setAttribute("op_value", operator);
			items[count++] = item;
			item.addChangedHandler(new ChangedHandler() {
				@Override
				public void onChanged(ChangedEvent event) {
					ApplicationPreferenceHelper.getInstance()
							.updateOperatorPreference(
									selectedFieldTypeLabel.getContents(),
									item.getAttribute("op_value"),
									operatorType, item.getValueAsBoolean());
					applyButton.enable();
				}
			});
		}
		operatorsForm.setItems(items);
	}

	public static void disableApplyButton() {
		applyButton.disable();
	}
}
