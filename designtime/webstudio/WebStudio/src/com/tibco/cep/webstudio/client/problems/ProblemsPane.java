package com.tibco.cep.webstudio.client.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.types.GroupStartOpen;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableConstants;
import com.tibco.cep.webstudio.client.editor.AbstractEditor;
import com.tibco.cep.webstudio.client.i18n.CustomizedSmartgwtMessages;
import com.tibco.cep.webstudio.client.i18n.DTMessages;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.panels.CustomSC;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.client.widgets.WebStudioNavigatorGrid;

/**
 * 
 * @author sasahoo
 *
 */
public class ProblemsPane extends Tab {
	
	private List<ProblemRecord> errorRecords = new ArrayList<ProblemRecord>();
	private List<ProblemRecord> warningRecords = new ArrayList<ProblemRecord>();
	private ListGrid problemsGrid;
	
	private Map<String, List<ProblemMarker>> uriMarkerMap = new HashMap<String, List<ProblemMarker>>();
	private Map<ProblemMarker, ProblemRecord> markerRecordMap = new HashMap<ProblemMarker, ProblemRecord>();
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	private DTMessages dtMsgBundle= (DTMessages) I18nRegistry.getResourceBundle(I18nRegistry.DT_MESSAGES);
	private CustomizedSmartgwtMessages customSgwtMsgBundle= (CustomizedSmartgwtMessages) I18nRegistry.getResourceBundle(I18nRegistry.CUSTOM_SGWT_MESSAGES);

	public ProblemsPane() {
		setTitle(Canvas.imgHTML(Page.getAppImgDir() + "icons/16/error_warning.png") + globalMsgBundle.text_problems());
		init();
	}
	
	private void init() {
		VLayout vLayout = new VLayout();
		vLayout.setWidth100();
		vLayout.setHeight100();
				
		problemsGrid = new ListGrid() {			
			 @Override
			 protected MenuItem[] getHeaderContextMenuItems(final Integer fieldNum) {
				 if (fieldNum == null) {
					 return new MenuItem[]{};
				 }
				 if (fieldNum == 0) {
					 return new MenuItem[]{};
				 }
				 return getGeneralHeaderContextMenuItems(super.getHeaderContextMenuItems(fieldNum));
			 }
		};  
		problemsGrid.setCanEdit(false);    
		problemsGrid.setWidth100();  
		problemsGrid.setHeight100();  
		problemsGrid.setShowAllRecords(true);  
		problemsGrid.setCellHeight(22);
		problemsGrid.setCanFreezeFields(false);
		problemsGrid.setShowFilterEditor(true);
		problemsGrid.setFilterOnKeypress(true);
		problemsGrid.setCanReorderFields(false);		  
		problemsGrid.setAutoFetchData(false);
		problemsGrid.setEmptyMessage(globalMsgBundle.message_noData());
  
        problemsGrid.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				openAssociatedMarkerResource(((ProblemRecord)event.getRecord()).getMarker());
			}
		});
        	                
		// Add the Row Context Menu only if DT has errors or warnings
		boolean hasErrors = null != errorRecords && errorRecords.isEmpty() ? true : false;
		boolean hassWarnings = null != warningRecords && warningRecords.isEmpty() ? true : false;
		if (hassWarnings || hasErrors) {
			problemsGrid
					.addRowContextClickHandler(new ProblemPaneRowContextMenuHandler());
		}
        
        ListGridField nofield = new ListGridField("#");		
        nofield.setType(ListGridFieldType.IMAGE);
        nofield.setImageURLPrefix(Page.getAppImgDir() + "icons/16/");
        nofield.setWidth("3%");
                
        ListGridField descfield = new ListGridField("Description");		
        descfield.setWidth("25%");
        
        ListGridField resourcefield = new ListGridField("Resource");		
        resourcefield.setWidth("10%");
        
        ListGridField projectfield = new ListGridField("Project");
        projectfield.setWidth("10%");
        
		ListGridField pathfield = new ListGridField("Path");        
		pathfield.setWidth("20%");
		
		ListGridField locfield = new ListGridField("Location");        
		locfield.setWidth("17%");
		
		ListGridField typefield = new ListGridField("Type");      
		typefield.setAttribute("width", "8%");

        ListGridField problemTypefield = new ListGridField("ProblemType");
        problemTypefield.setWidth("7%");

        problemsGrid.setFields(nofield, descfield, resourcefield, projectfield, pathfield, locfield, typefield, problemTypefield);
        
        refreshProblemsGrid();
        
        vLayout.addMember(problemsGrid);
        
        setPane(vLayout);
	}
	
	/**
	 * Refreshes the Problems grid
	 */
	public void refreshProblemsGrid() {
        if (problemsGrid.getDataSource() != null) {
        	problemsGrid.getDataSource().destroy();
        }
        List<ProblemRecord> allRecords = new ArrayList<ProblemRecord>();
        allRecords.addAll(warningRecords);
        allRecords.addAll(errorRecords);
        ProblemsDataSource datasource = new ProblemsDataSource();
        problemsGrid.setDataSource(datasource);
        datasource.setCacheData(allRecords.toArray(new ProblemRecord[allRecords.size()]));
        problemsGrid.fetchData();
		problemsGrid.setCanGroupBy(true);
        problemsGrid.setGroupStartOpen(GroupStartOpen.ALL);
		problemsGrid.setShowGroupSummary(true);
		//problemsGrid.setShowGroupSummaryInHeader(true);
		problemsGrid.groupBy("ProblemType");
	}
	
	public List<ProblemRecord> getErrorRecords() {
		return errorRecords;
	}

	public List<ProblemRecord> getWarningRecords() {
		return warningRecords;
	}
		
	private MenuItem[] getGeneralHeaderContextMenuItems(MenuItem[] items) {
		List<MenuItem> menuItemList =  new ArrayList<MenuItem>();
		List<String> menuTitles = new ArrayList<String>();
		menuTitles.add(customSgwtMsgBundle.listGrid_clearSortFieldText());
		menuTitles.add(customSgwtMsgBundle.listGrid_configureSortText());
		menuTitles.add(customSgwtMsgBundle.listGrid_fieldVisibilitySubmenuTitle());
		
		for (MenuItem mitem : items) {
			if (mitem.getTitle() == null 
					|| menuTitles.contains(mitem.getTitle().trim())) {
				continue;
			}
			menuItemList.add(mitem);
		}
		return menuItemList.toArray(new MenuItem[menuItemList.size()]);
	}
	
	public Map<String, List<ProblemMarker>> getUriMarkerMap() {
		return uriMarkerMap;
	}

	public Map<ProblemMarker, ProblemRecord> getMarkerRecordsMap() {
		return markerRecordMap;
	}
	
	public ListGrid getProblemsGrid() {
		return problemsGrid;
	}
	
	public void openAssociatedMarkerResource(ProblemMarker marker) {
		AbstractEditor editor = null;
		for (Tab tab : WebStudio.get().getEditorPanel().getTabs()) {
			if (tab instanceof AbstractEditor) {
				if (((AbstractEditor)tab).getURI() != null 
						&& ((AbstractEditor)tab).getURI().equals( marker.getURI())) {
					editor = (AbstractEditor)tab;
					WebStudio.get().getEditorPanel().selectTab(editor);
					editor.gotoMarker(marker);
					WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
					break;
				}
			}
		}
		//Open editor here if not there
		if (editor == null) {
			if (marker.getExtension().equalsIgnoreCase(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue())) {
				WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
				String artifact = marker.getURI() + CommonIndexUtils.DOT +  CommonIndexUtils.RULE_FN_IMPL_EXTENSION;
				artifact = artifact.replace("/", DecisionTableConstants.CONDITION_COLUMN_DELIMITER);
				NavigatorResource resource = navGrid.getResourceById(artifact);
				navGrid.getClickHandler().createPage(resource, marker);
			} else if (marker.getExtension().equalsIgnoreCase(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue())) {
				WebStudioNavigatorGrid navGrid = WebStudio.get().getWorkspacePage().getGroupContentsWindow().getArtifactTreeGrid();
				String artifact = marker.getURI() + CommonIndexUtils.DOT +  CommonIndexUtils.RULE_TEMPLATE_INSTANCE_EXTENSION;
				artifact = artifact.replace("/", DecisionTableConstants.CONDITION_COLUMN_DELIMITER);
				NavigatorResource resource = navGrid.getResourceById(artifact);
				navGrid.getClickHandler().createPage(resource, marker);
			}
		} 
		WebStudio.get().getEditorPanel().getBottomPane().setVisible(true);
	}

	/**
	 * This is the Handler class for Context Menu of row in ProblemPane.
	 * 
	 * @author dijadhav
	 * 
	 */
	private final class ProblemPaneRowContextMenuHandler implements RowContextClickHandler {
		/**
		 * This class has the functionality to remove the selected records from
		 * errors or warnings list.After deletion of record refresh the
		 * ProblemPane.
		 * 
		 * @author dijadhav
		 * 
		 */
		private final class DeleteConfirmationCallback implements BooleanCallback {
			/*
			 * This method is executed when user clicks on the buttons from
			 * confirmation dialog box.
			 */
			public void execute(Boolean value) {
				if (value != null && value) {
					ListGridRecord[] listGridRecords = problemsGrid
							.getSelectedRecords();
					if (null != listGridRecords && listGridRecords.length > 0) {
						for (ListGridRecord listGridRecord : listGridRecords) {
							if (null != listGridRecord) {
								if (errorRecords.contains(listGridRecord)) {
									errorRecords.remove(listGridRecord);
								} else if (warningRecords.contains(listGridRecord)) {
									warningRecords.remove(listGridRecord);
								}
							}
						}
						refreshProblemsGrid();
					}
				}
			}
		}

		@Override
		/**
		 * This method is invoked to show context menu when user right clicks on the record from ProblemPane.
		 */
		public void onRowContextClick(RowContextClickEvent event) {
			Menu menu = new Menu();
			MenuItem deleteMenuItem = new MenuItem(dtMsgBundle.dtProblemPaneRecordDelMenuTitle());
			
			deleteMenuItem.addClickHandler(new ClickHandler() {
				
				@Override
				/**
				 * Executed when this menu item is clicked by the user. 
				 */
				public void onClick(MenuItemClickEvent event) {
					CustomSC.confirm(dtMsgBundle.dtProblemPaneRecordDelConfDialogTitle(), dtMsgBundle.dtProblemPaneRecordDelConfMsg(), new DeleteConfirmationCallback());
				}
			});
			menu.addItem(deleteMenuItem);
			problemsGrid.setContextMenu(menu);
		}
	}
}