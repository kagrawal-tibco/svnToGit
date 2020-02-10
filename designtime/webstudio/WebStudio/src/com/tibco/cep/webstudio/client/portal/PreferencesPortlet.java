package com.tibco.cep.webstudio.client.portal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.form.validator.IsIntegerValidator;
import com.smartgwt.client.widgets.form.validator.Validator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.UserPreferences;
import com.tibco.cep.webstudio.client.preferences.UserPreferenceHelper;

public class PreferencesPortlet extends WebStudioPortlet {
	private IButton applyButton;
	private List<Validator> validators;
	private List<Validator> dtPageSizeValidators;
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private TextItem scsUserName, scsUserPassword;
	
    public PreferencesPortlet() {
		super();
	}

	protected void initialize() {
		if (initialized) {
			return;
		}
    	this.setTitle(globalMsgBundle.text_preferences());
    	this.setHeight(450);

    	VLayout preferenceLayout = new VLayout(10);
    	preferenceLayout.setWidth100();
    	preferenceLayout.setHeight100();
    	
    	final DynamicForm preferencesForm = new DynamicForm();
    	preferencesForm.setMargin(10);
    	preferencesForm.setWidth100();
    	preferencesForm.setAutoHeight();
    	preferencesForm.setNumCols(2);
    	preferencesForm.setColWidths("30%", "*");
    	
    	final IntegerItem recentlyOpenedSize = new IntegerItem();
    	recentlyOpenedSize.setWidth("100%");
    	recentlyOpenedSize.setTitle(globalMsgBundle.text_numberOfRecentItems());
    	recentlyOpenedSize.setValue(WebStudio.get().getUserPreference().getRecentlyOpenedArtifactLimit());
    	recentlyOpenedSize.setValidators(getIntegerFieldValidators());
    	recentlyOpenedSize.setRequired(true);
    	recentlyOpenedSize.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
//    	final TextItem favoritesSize = new TextItem();
//    	favoritesSize.setWidth("100%");
//    	favoritesSize.setTitle("Number of Favorites");
//    	favoritesSize.setValue(WebStudio.get().getUserPreference().getFavoriteArtifactLimit());
//    	favoritesSize.setRequired(true);
//    	favoritesSize.setValidators(getIntegerFieldValidators());
//    	favoritesSize.addChangedHandler(new ChangedHandler() {
//			public void onChanged(ChangedEvent event) {
//				applyButton.enable();
//			}
//		});
    	
    	final TextItem customUrl = new TextItem();
    	customUrl.setWidth("100%");
    	customUrl.setTitle(globalMsgBundle.text_defaultURLForCustomWebpage());
    	customUrl.setValue(WebStudio.get().getUserPreference().getCustomURL());
    	customUrl.setRequired(true);
    	customUrl.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	scsUserName = new TextItem();
    	scsUserName.setWidth("100%");
    	scsUserName.setTitle(globalMsgBundle.text_scsUserName());
    	scsUserName.setValue(WebStudio.get().getUserPreference().getScsUserName());
    	scsUserName.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	scsUserPassword = new PasswordItem();
    	scsUserPassword.setWidth("100%");
    	scsUserPassword.setTitle(globalMsgBundle.text_scsUserPassword());
    	scsUserPassword.setValue(WebStudio.get().getUserPreference().getScsUserPassword());
    	scsUserPassword.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	//UI for list or tree view
    	final SelectItem viewPref = new SelectItem();
    	viewPref.setTitle(globalMsgBundle.text_itemViewPreference());
    	viewPref.setWidth("100%");
    	LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
    	valueMap.put("List", globalMsgBundle.preferences_project_view_list());
    	valueMap.put("Tree", globalMsgBundle.preferences_project_view_tree());
    	viewPref.setValueMap(valueMap);
    	String itemViewValue = WebStudio.get().getUserPreference().getItemView();
    	if (!itemViewValue.equals("List") && !itemViewValue.equals("Tree")) {
    		itemViewValue = "List";
    	}
    	viewPref.setValue(itemViewValue);
    	viewPref.setRequired(true);
    	viewPref.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});

    	final IntegerItem decisionTablePageSize = new IntegerItem();
    	decisionTablePageSize.setWidth("100%");
    	decisionTablePageSize.setTitle(globalMsgBundle.text_decisionTablePageSize());
    	decisionTablePageSize.setValue(WebStudio.get().getUserPreference().getDecisionTablePageSize());
    	decisionTablePageSize.setValidators(getDecisionTablePageSizeValidators());
    	decisionTablePageSize.setRequired(true);
    	decisionTablePageSize.addChangedHandler(new ChangedHandler() {
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});

    	final CheckboxItem autoUnlockPref = new CheckboxItem();
    	autoUnlockPref.setTitle(globalMsgBundle.text_autoUnLockOnReview());
    	autoUnlockPref.setLabelAsTitle(true);
    	autoUnlockPref.setWidth("100%");
    	autoUnlockPref.setValue(WebStudio.get().getUserPreference().isAutoUnLockOnReview());
    	autoUnlockPref.setRequired(false);
    	autoUnlockPref.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	final CheckboxItem groupRelatedArtifactsPref = new CheckboxItem();
    	groupRelatedArtifactsPref.setTitle(globalMsgBundle.text_groupRelatedArtifacts());
    	groupRelatedArtifactsPref.setLabelAsTitle(true);
    	groupRelatedArtifactsPref.setWidth("100%");
    	groupRelatedArtifactsPref.setValue(WebStudio.get().getUserPreference().isGroupRelatedArtifacts());
    	groupRelatedArtifactsPref.setRequired(false);
    	groupRelatedArtifactsPref.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	final CheckboxItem allowCustomDomainValuesPref = new CheckboxItem();
    	allowCustomDomainValuesPref.setTitle(globalMsgBundle.text_allowCustomDomainValues());
    	allowCustomDomainValuesPref.setLabelAsTitle(true);
    	allowCustomDomainValuesPref.setWidth("100%");
    	allowCustomDomainValuesPref.setValue(WebStudio.get().getUserPreference().isAllowCustomDomainValues());
    	allowCustomDomainValuesPref.setRequired(false);
    	allowCustomDomainValuesPref.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	final CheckboxItem showColumnAliasIfPresentPref = new CheckboxItem();
    	showColumnAliasIfPresentPref.setTitle(globalMsgBundle.text_showColumnAliasIfPresent());
    	showColumnAliasIfPresentPref.setLabelAsTitle(true);
    	showColumnAliasIfPresentPref.setWidth("100%");
    	showColumnAliasIfPresentPref.setValue(WebStudio.get().getUserPreference().isShowColumnAliasIfPresent());
    	showColumnAliasIfPresentPref.setRequired(false);
    	showColumnAliasIfPresentPref.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	//UI for auto fit approches list
    	final SelectItem autoFitPref = new SelectItem();
    	autoFitPref.setTitle(globalMsgBundle.text_autoFitAllColumnsforDecisiontable());
    	autoFitPref.setWidth("100%");
    	LinkedHashMap<String, String> approchMap = new LinkedHashMap<String, String>();
    	approchMap.put("Default", globalMsgBundle.preferences_autofit_default_option());
    	approchMap.put("Both", globalMsgBundle.preferences_autofit_both_option());
    	approchMap.put("Title", globalMsgBundle.preferences_autofit_title_option());
    	approchMap.put("Value", globalMsgBundle.preferences_autofit_value_option());
    	autoFitPref.setValueMap(approchMap);
    	autoFitPref.setValue(WebStudio.get().getUserPreference().getAutoFitColumnsApproch());
    	autoFitPref.setRequired(true);
    	autoFitPref.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});
    	
    	final SelectItem rtiDefaultFilterType = new SelectItem();
    	rtiDefaultFilterType.setTitle(globalMsgBundle.text_itemRtiStartingFilter());
    	rtiDefaultFilterType.setWidth("100%");
    	LinkedHashMap<String, String> valueMapStartingFilter = new LinkedHashMap<String, String>();
    	valueMapStartingFilter.put("Match Any", globalMsgBundle.preferences_rti_startingFilter_MatchAny());
    	valueMapStartingFilter.put("Match All", globalMsgBundle.preferences_rti_startingFilter_MatchAll());
    	valueMapStartingFilter.put("Match None", globalMsgBundle.preferences_rti_startingFilter_MatchNone());
    	rtiDefaultFilterType.setValueMap(valueMapStartingFilter);
    	rtiDefaultFilterType.setValue(WebStudio.get().getUserPreference().getRtiDefaultFilterType());
    	rtiDefaultFilterType.setRequired(true);
    	rtiDefaultFilterType.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				applyButton.enable();
			}
		});

    	preferencesForm.setFields(recentlyOpenedSize, customUrl, scsUserName, scsUserPassword, viewPref, decisionTablePageSize, autoUnlockPref, groupRelatedArtifactsPref, allowCustomDomainValuesPref, showColumnAliasIfPresentPref, autoFitPref, rtiDefaultFilterType);
    	
    	HLayout buttonContainer = new HLayout();
    	buttonContainer.setWidth100();
    	buttonContainer.setHeight("20%");
    	buttonContainer.setAlign(Alignment.RIGHT);
    	buttonContainer.setLayoutRightMargin(10);
    	
    	applyButton = new IButton(globalMsgBundle.button_apply());
    	applyButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (preferencesForm.validate()) {
					UserPreferences userPreference = WebStudio.get().getUserPreference();
					userPreference.setCustomURL(customUrl.getValueAsString());
//					userPreference.setFavoriteArtifactLimit(Integer.parseInt(favoritesSize.getValueAsString()));
					userPreference.setItemView(viewPref.getValueAsString());
					userPreference.setRecentlyOpenedArtifactLimit(recentlyOpenedSize.getValueAsInteger());
					userPreference.setDecisionTablePageSize(decisionTablePageSize.getValueAsInteger());
					
					userPreference.setScsUserName(scsUserName.getValueAsString());
					userPreference.setScsUserPassword(scsUserPassword.getValueAsString());

					userPreference.setAutoUnLockOnReview(autoUnlockPref.getValueAsBoolean());
					
					if(userPreference.isGroupRelatedArtifacts() != groupRelatedArtifactsPref.getValueAsBoolean()){
						userPreference.setIsGroupingPropertyChanged(true);
					}
					userPreference.setGroupRelatedArtifacts(groupRelatedArtifactsPref.getValueAsBoolean());
					userPreference.setAllowCustomDomainValues(allowCustomDomainValuesPref.getValueAsBoolean());
					userPreference.setShowColumnAliasIfPresent(showColumnAliasIfPresentPref.getValueAsBoolean());
					userPreference.setAutoFitColumnsApproch(autoFitPref.getValueAsString());
					userPreference.setRtiDefaultFilterType(rtiDefaultFilterType.getValueAsString());
					UserPreferenceHelper.getInstance().updateUserPreferences(userPreference);
					
					//update dashboard artifacts appropriately
					WebStudio.get().getPortalPage().updateDashboardArtifactPortlet(DASHBOARD_PORTLETS.MY_FAVORITES);
					WebStudio.get().getPortalPage().updateDashboardArtifactPortlet(DASHBOARD_PORTLETS.RECENTLY_OPENED);
					// update the workspace view  and portal view here
					WebStudio.get().getWorkspacePage().getGroupContentsWindow().setShowAsTree(userPreference.getItemView().equals("Tree") ? true : false);
					WebStudio.get().getPortalPage().getGroupContentWindowtFromGroupPortlet().setShowAsTree(userPreference.getItemView().equals("Tree") ? true : false);
					applyButton.disable();
				}
			}
		});
    	applyButton.disable();
    	buttonContainer.addMember(applyButton);
    	
    	preferenceLayout.addMember(preferencesForm);
    	preferenceLayout.addMember(buttonContainer);
    	
    	this.setModularCanvas(preferenceLayout);
    	
    	this.setShowCloseButton(false);
    	
    	initialized = true;
	}
	
	/**
	 * Get validators for integer field
	 * @return
	 */
	private Validator[] getIntegerFieldValidators() {
		if (validators == null) {
			validators = new ArrayList<Validator>();
			validators.add(new IsIntegerValidator());

			IntegerRangeValidator rangeValidator = new IntegerRangeValidator();
			rangeValidator.setMin(1);
			rangeValidator.setMax(25);

			validators.add(rangeValidator);
		}

		return validators.toArray(new Validator[validators.size()]);
	}

	/**
	 * Get validators for Decision table page size
	 * @return
	 */
	private Validator[] getDecisionTablePageSizeValidators() {
		if (dtPageSizeValidators == null) {
			dtPageSizeValidators = new ArrayList<Validator>();
			dtPageSizeValidators.add(new IsIntegerValidator());

			IntegerRangeValidator rangeValidator = new IntegerRangeValidator();
			rangeValidator.setMin(10);
			rangeValidator.setMax(500);

			dtPageSizeValidators.add(rangeValidator);
		}

		return dtPageSizeValidators.toArray(new Validator[dtPageSizeValidators.size()]);
	}
	
	/**
	 * Clear SCS fields, typically in case of errors.
	 */
	public void clearSCSFields() {
		scsUserName.clearValue();
		scsUserPassword.clearValue();
	}
}
