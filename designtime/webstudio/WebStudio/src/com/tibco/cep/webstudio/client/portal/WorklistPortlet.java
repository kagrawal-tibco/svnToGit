/**
 * 
 */
package com.tibco.cep.webstudio.client.portal;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.client.widgets.RMSWorklistDialog;

/**
 * Portlet to display favorites and recently opened artifacts.
 * 
 * @author Vikram Patil
 */
public class WorklistPortlet extends WebStudioPortlet {
	
	private static final GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private VLayout projectContainer;
	private String portletTitle;
	private ListGrid worklistGrid;
	private IButton okButton;
	private RMSWorklistDialog worklistDialog;
	
	// default delay of 1 min
	private static final int FETCH_DELAY = 60000;
	
	/**
	 * Base Constructor
	 * 
	 * @param portletTitle
	 * @param serverURL
	 */
	public WorklistPortlet(String portletTitle, ServerEndpoints serverURL) {
		super();
		this.portletTitle = portletTitle;
		initialize();
	}
	
	@Override
	protected void initialize() {
		if (initialized) {
			return;
		}
		
		setTitle(portletTitle);
		
		projectContainer = new VLayout(10);
		projectContainer.setWidth100();
		projectContainer.setHeight100();
		projectContainer.setMembersMargin(0);
        projectContainer.setOverflow(Overflow.AUTO);  
        
    	setModularCanvas(projectContainer);
    	addWorklistGrid();
    	
    	this.addVisibilityChangedHandler(new VisibilityChangedHandler() {		
			@Override
			public void onVisibilityChanged(VisibilityChangedEvent event) {
				if (event.getIsVisible()) {
					if (worklistGrid != null) {
						if (worklistGrid.getResultSet() != null){
							worklistGrid.getResultSet().invalidateCache();
						}
						worklistGrid.fetchData();
					}
				}
			}
		});
    	
    	createWorklistScheduler();
    	
    	initialized = true;
	}
	
	/**
	 * Add Worklist element
	 */
	private void addWorklistGrid() {
		HLayout buttonContainer = new HLayout(10);
		buttonContainer.setWidth100();
		buttonContainer.setHeight(20);
		buttonContainer.setLayoutMargin(5);
		buttonContainer.setAlign(Alignment.RIGHT);
		
		okButton = new IButton(globalMsgBundle.button_ok());
		okButton.setWidth(100);  
		okButton.setShowRollOver(true);  
		okButton.setShowDisabled(true);  
		okButton.setShowDown(true);
		okButton.disable();
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				worklistDialog.onAction();
			}
		});
		buttonContainer.addMember(okButton);
		
		worklistDialog = new RMSWorklistDialog(okButton);
		worklistGrid = worklistDialog.getWorklistGrid();
		
		projectContainer.addMember(worklistGrid);
		projectContainer.addMember(buttonContainer);
		worklistGrid.fetchData();
	}
	
	/**
	 * Scheduler to fetch worklist details
	 */
	private void createWorklistScheduler() {
		// Setup a schedular for fetch worklist contents periodically
    	Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
			public boolean execute() {
				if (/*WebStudio.get().getHeader().isDashboardPageSelected() && */ okButton.isDisabled()) {
					if (worklistGrid != null) {
						if (worklistGrid.getResultSet() != null){
							worklistGrid.getResultSet().invalidateCache();
						}
						worklistGrid.fetchData();
					}
				}
				return true;
			}
		}, FETCH_DELAY);
	}
}
