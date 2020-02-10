package com.tibco.cep.webstudio.client.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.SliderItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.PortalLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.preferences.UserPreferenceHelper;

public class DashboardAppearancePortlet extends WebStudioPortlet {

//    protected static final int DASHBOARD_PORTLETS = 0;
//    protected static final int SETTINGS_PORTLETS = 1;
    
    private ListGrid homePortletsGrid;
    private static GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private IButton removeSelectedDashboardPortletsButton;
    
	public DashboardAppearancePortlet(PortalLayout portalLayout) {
		super();
	}

	protected void initialize() {
		if (initialized) {
			return;
		}
		this.setTitle(globalMsgBundle.text_dashboardAppearance());
		Canvas canvas = createConfigurationCanvas();  
    	this.setModularCanvas(canvas);
    	
    	this.addVisibilityChangedHandler(new VisibilityChangedHandler() {
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				updatePortletList();				
			}
		});
    	this.setShowCloseButton(false);
    	
    	initialized = true;
	}

	private Canvas createConfigurationCanvas() {
		VLayout stack = new VLayout();
		stack.setWidth100();
		stack.setHeight100();
        final DynamicForm form = new DynamicForm();  
        form.setMargin(10);
        form.setAutoWidth();  
        form.setNumCols(2);  
  
        final SliderItem numColItem = new SliderItem();  
        numColItem.setMinValue(1);
        numColItem.setMaxValue(5);
        numColItem.setWidth(250);
        numColItem.setNumValues(5);
        
        numColItem.setTitle(globalMsgBundle.text_numberOfColumns());
        numColItem.setWrapTitle(false);
        int numColumns = WebStudio.get().getUserPreference().getPortalColumns();
        numColItem.setValue(numColumns);
  
        numColItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Integer value = (Integer) event.getValue();
				if (value == null) {
					return;
				}
				if (!maximized) {
					try {
						int numColumns = value;
						WebStudioPortalPage portalPage = WebStudio.get().getPortalPage();
						portalPage.setNumColumns(numColumns);
						
						WebStudio.get().getUserPreference().setPortalColumns(numColumns);
						UserPreferenceHelper.getInstance().updateUserPreferences(WebStudio.get().getUserPreference());
					} catch (NumberFormatException e) {
					}
				}
			}
		});
        
        HLayout gridsLayout = new HLayout();
        gridsLayout.setMargin(10);
        gridsLayout.setHeight100();
        gridsLayout.setWidth100();
        stack.addMember(gridsLayout);

        createDashboardPortletsGrid(gridsLayout);
 
        form.setItems(numColItem);
        stack.addMember(form);

		return stack;
	}

	private void createDashboardPortletsGrid(HLayout parent) {
		VLayout stack = new VLayout();
		stack.setMembersMargin(10);
		stack.setWidth100();
		parent.addMember(stack);
		
		homePortletsGrid = new ListGrid();
        homePortletsGrid.setWidth100();
        homePortletsGrid.setEmptyMessage(globalMsgBundle.message_browseButton_emptyMessage());
        homePortletsGrid.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
                ListGridRecord[] selectedRecords = homePortletsGrid.getSelectedRecords();  
				if (selectedRecords != null && selectedRecords.length > 0) {
					removeSelectedDashboardPortletsButton.enable();
				} else {
					removeSelectedDashboardPortletsButton.disable();
				}
			}
		});
        
        
        ListGridField portletField = new ListGridField(globalMsgBundle.text_dashboardPortlets());
        homePortletsGrid.setFields(portletField);
        
        List<ListGridRecord> records = getCurrentPortlets();
        homePortletsGrid.setRecords((ListGridRecord[]) records.toArray(new ListGridRecord[records.size()]));
        
        removeSelectedDashboardPortletsButton = new IButton(globalMsgBundle.removeSelectedDashboardPortlets_button());  
    	removeSelectedDashboardPortletsButton.disable();
        removeSelectedDashboardPortletsButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                ListGridRecord[] selectedRecords = homePortletsGrid.getSelectedRecords();  
                if(selectedRecords != null) { 
                	for (ListGridRecord record : selectedRecords) {
                		String portletName = record.getAttributeAsString(globalMsgBundle.text_dashboardPortlets());
                		homePortletsGrid.removeData(record);  
                		WebStudio.get().getPortalPage().removeDashboardPortal(portletName);
                		
                		WebStudio.get().getUserPreference().removeDashboardPortlets(portletName);
                	}
                	UserPreferenceHelper.getInstance().updateUserPreferences(WebStudio.get().getUserPreference());
                	removeSelectedDashboardPortletsButton.disable();
                } else {
                	CustomSC.say(globalMsgBundle.selectRecordBeforeAction_message());  
                }
            }
              
        });
        removeSelectedDashboardPortletsButton.setAutoFit(true);
        
        final IButton addHomePortlet = new IButton(globalMsgBundle.addDashboardPortlet_button() + "...");  
        addHomePortlet.setAutoFit(true);  
  
        addHomePortlet.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	handleAddClicked(homePortletsGrid, WebStudio.get().getPortalPage().getHomePortlets());
            }
        });  

        final IButton addWebPagePortlet = new IButton(globalMsgBundle.addWebPagePortlet_button() + "...");  
        addWebPagePortlet.setAutoFit(true);  
        addWebPagePortlet.setTooltip(globalMsgBundle.addWebPagePortlet_tooltip());
        
        addWebPagePortlet.addClickHandler(new ClickHandler() {  
        	public void onClick(ClickEvent event) {  
        		CustomSC.askforValue(globalMsgBundle.addWebPagePortler_enterURL_message(), new ValueCallback() {
					
					@Override
					public void execute(String value) {
						if (value != null) {
							validateAndAddURL(value);
						}
					}

					private void validateAndAddURL(String value) {
						if (value == null) {
							return;
						}
						if (!value.contains(":")) {
							value = "http://"+value;
						}
						final String url = value;
						WebPagePortlet.validateURL(url, new URLValidationRPCCallback() {

							@Override
							protected void validateEvent(boolean isValid) {
								if (!isValid) {
									CustomSC.say("Invalid URL", "The specified URL does not allow embedding inside of a portlet");
									return;
								}
								String portletID = getUniqueUrlID(url);
								WebPagePortlet newPortlet = new WebPagePortlet(url, false/* no need to revalidate URL */); 
								WebStudio.get().getPortalPage().addCustomHomePortlet(newPortlet, portletID);
								WebStudio.get().getUserPreference().addDashboardPortlets(portletID);
								UserPreferenceHelper.getInstance().updateUserPreferences(WebStudio.get().getUserPreference());
								updatePortletList();
							}
						});
					}

					private String getUniqueUrlID(String value) {
						String id = WebPagePortlet.CUSTOM_PAGE_PREFIX + value;
						WebStudioPortlet portlet = WebStudio.get().getPortalPage().getHomePortlets().get(id);
						int ctr = 0;
						while (portlet != null) {
							// web page portlet already defined, get new id and allow duplicate portlets with same URL
							id = WebPagePortlet.CUSTOM_PAGE_PREFIX + value + ctr++;
							portlet = WebStudio.get().getPortalPage().getHomePortlets().get(id);
						}
						return id;
					}
				});
        	}
        });  
        
        stack.addMember(homePortletsGrid);
        
        HLayout buttonLayout = new HLayout(10);
        buttonLayout.setWidth100();
        buttonLayout.setAutoHeight();
        buttonLayout.addMember(removeSelectedDashboardPortletsButton);
        buttonLayout.addMember(addHomePortlet);
        buttonLayout.addMember(addWebPagePortlet);
        stack.addMember(buttonLayout);
        
	}
	
	protected void handleAddClicked(final ListGrid listGrid,
			final Map<String, WebStudioPortlet> currentPortlets) {
		
		Set<String> keySet = currentPortlets.keySet();
		List<String> hiddenPortlets = new ArrayList<String>();
		for (String key : keySet) {
			WebStudioPortlet webStudioPortlet = currentPortlets.get(key);
			if (webStudioPortlet.isHiddenInPortal()) {
				hiddenPortlets.add(key);
			}
		}
		final Dialog dialog = new Dialog();
		dialog.setMargin(0);
		dialog.setVPolicy(LayoutPolicy.FILL);  
		dialog.setHPolicy(LayoutPolicy.FILL);  
		dialog.setOverflow(Overflow.AUTO);  
		dialog.setCanDragResize(true);
		dialog.setWidth(480);
		dialog.setHeight(250);
		dialog.setTitle(globalMsgBundle.portlet_availablePortlets_title());
//		dialog.setStyleName("ws-portletborder");
//		dialog.setShowEdges(false);
//		dialog.setBodyColor("white");
//		dialog.setBackgroundColor("white");
		dialog.setIsModal(true);
		
		DynamicForm form = new DynamicForm();
		form.setLeft(0);
		form.setAlign(Alignment.LEFT);
		form.setNumCols(4);
		form.setWidth100();
		form.setHeight100();
		
		final Button okButton = new Button(globalMsgBundle.button_ok());
		final CheckboxItem[] items = new CheckboxItem[hiddenPortlets.size()];
		for (int i = 0; i < hiddenPortlets.size(); i++) {
			String name = hiddenPortlets.get(i);
			final CheckboxItem item = new CheckboxItem();
			item.setTitle(name.toString());
			items[i] = item;
			item.addChangedHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					if (item.getValueAsBoolean()) {
						okButton.enable();
					} else {
						for (CheckboxItem item : items) {
							if (item.getValueAsBoolean()) {
								return;
							}
						}
						okButton.disable();
					}
				}
			});
				
		}
		int btnWidth = 120;
		Button selectAllButton = new Button(globalMsgBundle.button_select_all());
		selectAllButton.setTooltip(globalMsgBundle.button_select_all());
		selectAllButton.setWrap(false);
		selectAllButton.setWidth(btnWidth);
		if (items.length == 0) {
			selectAllButton.disable();
		}
		selectAllButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				for (CheckboxItem item : items) {
					item.setValue(true);
				}
				okButton.enable();
			}
			
		});
		Button deselectAllButton = new Button(globalMsgBundle.button_deselect_all());
		deselectAllButton.setTooltip(globalMsgBundle.button_deselect_all());
		deselectAllButton.setWrap(false);
		deselectAllButton.setWidth(btnWidth);
		if (items.length == 0) {
			deselectAllButton.disable();
		}
		deselectAllButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for (CheckboxItem item : items) {
					item.setValue(false);
				}
				okButton.disable();
			}
			
		});
		okButton.setWidth(70);
		okButton.disable();
		okButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				for (CheckboxItem item : items) {
					if (item.getValueAsBoolean()) {
						WebStudio.get().getPortalPage().showDashboardPortal(item.getTitle());
		    			WebStudio.get().getUserPreference().addDashboardPortlets(item.getTitle());
		    			
						ListGridRecord record = new ListGridRecord();
		    			record.setAttribute(globalMsgBundle.text_dashboardPortlets(), item.getTitle());
		    			listGrid.addData(record);
					}
				}
				UserPreferenceHelper.getInstance().updateUserPreferences(WebStudio.get().getUserPreference());
				dialog.destroy();
			}
		});
		Button cancelButton = new Button(globalMsgBundle.button_cancel());
		cancelButton.setWidth(70);
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				dialog.destroy();
			}
		});
		dialog.setButtons(selectAllButton, deselectAllButton, okButton, cancelButton);
		dialog.addItem(form);
		form.setItems(items);
		dialog.draw();
	}
	
	@Override
	public int getDefaultHeight() {
		return 500;
	}
	
	/**
	 * Get currently displayed portlets
	 * @return
	 */
	private List<ListGridRecord> getCurrentPortlets() {
		List<ListGridRecord> records = new ArrayList<ListGridRecord>();
        Map<String, WebStudioPortlet> homePortlets = WebStudio.get().getPortalPage().getHomePortlets();
        
        Set<String> keySet = homePortlets.keySet();
        for (String dashboardPortlet : keySet) {
        	WebStudioPortlet portlet = homePortlets.get(dashboardPortlet);
        	if (!portlet.isHiddenInPortal()) {
        		ListGridRecord record = new ListGridRecord();
        		record.setAttribute(globalMsgBundle.text_dashboardPortlets(), dashboardPortlet);
        		records.add(record);
        	}
		}
        
        return records;
	}
	
	/**
	 * Update the list of currently visible portlets
	 */
	public void updatePortletList() {
		List<ListGridRecord> records = getCurrentPortlets();
		if (homePortletsGrid != null) {
			homePortletsGrid.selectAllRecords();
			homePortletsGrid.removeSelectedData();
			homePortletsGrid.setRecords((ListGridRecord[]) records.toArray(new ListGridRecord[records.size()]));
		}
	}
}
