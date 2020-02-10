package com.tibco.cep.webstudio.client.editor;

import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.tibco.cep.webstudio.client.decisiontable.AbstractDecisionTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.AbstractTableEditor;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableEditor;

/**
 * 
 * @author sasahoo
 *
 */
public class TableDataGrid extends ListGrid {
	
	private AbstractTableEditor editor;
	
	/**
	 * @param editor
	 * @param removeRecords
	 * @param autoFetch
	 */
	public TableDataGrid(AbstractTableEditor editor, boolean removeRecords, boolean autoFetch) {
		this.editor = editor;
		this.setCanEdit(true);  
		this.setCanRemoveRecords(removeRecords);
		this.setAutoFetchData(autoFetch);  
		this.setUseAllDataSourceFields(true);  
		this.setDrawAheadRatio(2); // load 2 pages initially
		this.setShowResizeBar(true);
		this.setCanReorderFields(false);
		this.setEditEvent(ListGridEditEvent.CLICK);   
        this.setEditByCell(true);   
        this.setFilterByCell(true);
        this.setFilterOnKeypress(true);
//        this.setDataFetchMode(FetchMode.LOCAL);
        this.setCanAcceptDroppedRecords(true);
		this.setAllowFilterExpressions(true);
	}
	
	public AbstractEditor getEditor() {
		return editor;
	}

	/**
	 * @param editor
	 */
	public void setEditor(AbstractDecisionTableEditor editor) {
		this.editor = editor;
	}
	/**
	 * This method is used to add the ClickHandler for "Clear Sort" menu in
	 * DecisionTableEditor.
	 */
	@Override
	protected MenuItem[] getHeaderContextMenuItems(final Integer fieldNum) {

		MenuItem[] menuItems = super.getHeaderContextMenuItems(fieldNum);
		if (null != editor && editor instanceof DecisionTableEditor) {
			if (null != menuItems && menuItems.length > 0) {
				for (MenuItem menuItem : menuItems) {
					if (null != menuItem) {
						
						if (getClearSortFieldText().equals(menuItem.getTitle())) {
							menuItem.addClickHandler(new ClickHandler() {
								/*
								 * After clicking on the "Clear Sort" menu, clear
								 * the existing sort and then sortit in ascending
								 * order on 0th(ID) column.
								 */
								@Override
								public void onClick(MenuItemClickEvent event) {
									clearSort();
									sort(0, SortDirection.ASCENDING);
								}
							});
						}
					}
				}
			}
		}
		return menuItems;
	}
}