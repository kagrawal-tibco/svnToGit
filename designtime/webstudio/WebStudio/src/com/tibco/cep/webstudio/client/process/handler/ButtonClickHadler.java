package com.tibco.cep.webstudio.client.process.handler;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;

/**
 * This class is used to handle the up and down button click handler.
 * 
 * @author dijadhav
 * 
 */
public final class ButtonClickHadler implements ClickHandler {
	private final ListGrid listGrid;
	private final IButton upButton;
	private final IButton downButton;

	public ButtonClickHadler(ListGrid listGrid, IButton vrfImplDownButton,
			IButton upButton) {
		this.listGrid = listGrid;
		this.downButton = vrfImplDownButton;
		this.upButton = downButton;
	}

	@Override
	public void onClick(ClickEvent event) {
		IButton button = (IButton) event.getSource();
		String id = button.getID();
		ListGridRecord[] records =listGrid.getRecords();
		// Get the index of the selected item

		ListGridRecord selectedRecord = listGrid.getSelectedRecord();
		int itemSelected = listGrid.getRecordIndex(selectedRecord);

		// Get the string value of the item that has been
		// selected
		ListGridRecord selRcd=null;
		String itemStringSelected = selectedRecord.getAttribute("ruleImlURI");
		if ("UP_BTN".equals(id)) {

			if (itemSelected > 0) {
				ListGridRecord previousRecord = listGrid
						.getRecord(itemSelected - 1);
				String previousRecordValue = previousRecord
						.getAttribute("ruleImlURI");
				selectedRecord.setAttribute("ruleImlURI", previousRecordValue);		
				selectedRecord.setAttribute("checked", false);
				previousRecord.setAttribute("ruleImlURI", itemStringSelected);
				previousRecord.setAttribute("checked", true);
				
				records[itemSelected] = selectedRecord;
				records[itemSelected - 1] = previousRecord;
				selRcd=previousRecord;
				listGrid.updateRecordComponent(selectedRecord, 0, listGrid.getRecordComponent(itemSelected, 0), false);
				listGrid.updateRecordComponent(previousRecord, 0, listGrid.getRecordComponent(itemSelected - 1, 0), false);
			}

		} else if ("DOWN_BTN".equals(id)) {

			if (itemSelected < listGrid.getRecords().length) {
				ListGridRecord nextRecord = listGrid
						.getRecord(itemSelected + 1);
				String nextItem = nextRecord.getAttribute("ruleImlURI");
				selectedRecord.setAttribute("ruleImlURI", nextItem);
				selectedRecord.setAttribute("checked", false);
				nextRecord.setAttribute("ruleImlURI", itemStringSelected);
				nextRecord.setAttribute("checked", true);
				selRcd=nextRecord;
				records[itemSelected] = selectedRecord;
				records[itemSelected + 1] = nextRecord;
				listGrid.updateRecordComponent(selectedRecord, 0, listGrid.getRecordComponent(itemSelected, 0), false);
				listGrid.updateRecordComponent(nextRecord, 0, listGrid.getRecordComponent(itemSelected + 1, 0), false);
			}
		}
		listGrid.setRecords(records);	
		if(null!=selRcd){
			listGrid.selectRecord(selRcd);
		}
	}
}