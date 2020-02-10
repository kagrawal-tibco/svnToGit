package com.tibco.cep.webstudio.client.decisiontable;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TopOperatorAppearance;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.FilterBuilder;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;
import com.tibco.cep.webstudio.client.editor.TableDataGrid;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionCustomFilterDialog extends Dialog implements ClickHandler {         

	protected DataSource datasource;
	protected TableDataGrid tablegrid;
	protected FilterBuilder filterBuilder;
	protected IButton filterButton;
	protected IButton cancelButton;
	private VStack vStack;

	final int WIDTH = 525;
	final int HEIGHT = 300; 
	private boolean customFilter;

	public DecisionCustomFilterDialog(TableDataGrid tablegrid,
			DataSource datasource, 
			boolean customFilter) { 
		this.datasource = datasource;
		this.customFilter = customFilter;
		this.tablegrid = tablegrid;
		if (tablegrid.getShowFilterEditor()) {
			tablegrid.setShowFilterEditor(false);
		}
		init();
	} 

	public void init() {
		this.setAutoSize(true); 
		this.setShowToolbar(false); 
		this.setCanDragReposition(true);
		this.setTitle("Decision Table Filter Builder"); 
		this.setShowModalMask(false);
		this.setIsModal(true);
		
		filterBuilder = new FilterBuilder();

		if (customFilter) {
			filterBuilder.setTopOperatorAppearance(TopOperatorAppearance.RADIO);  
		}
		filterBuilder.setDataSource(datasource);   

		HStack hstack = new HStack();
		hstack.setAlign(Alignment.CENTER);
		
		filterButton = new IButton("Filter");   
		filterButton.addClickHandler(this);   
		
		cancelButton = new IButton("Cancel");   
		cancelButton.addClickHandler(this);

		vStack = new VStack(10);   
		vStack.addMember(filterBuilder);   
		
		hstack.addMember(filterButton);
		hstack.addMember(cancelButton);
		
		vStack.addMember(hstack);   
		
		this.addItem(vStack);
	} 
	
	/* (non-Javadoc)
	 * @see com.smartgwt.client.widgets.events.ClickHandler#onClick(com.smartgwt.client.widgets.events.ClickEvent)
	 */
	public void onClick(ClickEvent event) {   
		if (event.getSource() == filterButton) {
			tablegrid.filterData(filterBuilder.getCriteria());
		}
		if (event.getSource() == cancelButton) {
			filterBuilder.clearCriteria();
			tablegrid.filterData(filterBuilder.getCriteria());
			removeItem(vStack);            
			markForDestroy();          
			hide();        
		}
	}   
}