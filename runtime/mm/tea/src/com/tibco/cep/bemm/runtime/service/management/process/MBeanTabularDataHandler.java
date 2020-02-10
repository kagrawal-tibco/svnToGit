/**
 * 
 */
package com.tibco.cep.bemm.runtime.service.management.process;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.runtime.service.management.TabularDataDescriptor;

/**
 * @author dijadhav
 *
 */
public class MBeanTabularDataHandler {

    private Logger logger;
    //column and item have the same meaning here. Hence each item represents a table column
    private OpenType [] itemTypes;
    private String[] itemNames;
    private String[] indexNames;
    private String[] itemDescriptions;
    private int numItems;

    // Each 'tableRow' has type CompositeType ('rowType'). 'tabularData' has type TabularType ('tabularType').
    // Each row of the table 'tabularData' is a 'tableRow'. A 'tabularData' (of type TabularType) object is hence
    // a collection of tableRows' (of type CompositeType)
    private CompositeType rowType = null;
    private CompositeData tableRow = null;
    private TabularType tabularType = null;
    private TabularDataSupport tabularData = null;

    //constructor
    public MBeanTabularDataHandler(Logger logger) {
        this.logger = logger;
    }

    public void setTabularData (String invokedMethod) {
        //get columns (items) descriptor for the invoked method
        TabularDataDescriptor.ItemsDescriptor itemsDesc = TabularDataDescriptor.getInstance().getItemsDescriptor(invokedMethod);

        itemTypes = itemsDesc.itemTypes;
        itemNames = itemsDesc.itemNames;
        indexNames = itemsDesc.indexNames;
        itemDescriptions = itemsDesc.itemDescriptions;
        numItems = itemNames.length;
        setRowType(itemsDesc.rowTypeName, itemsDesc.rowTypeDescription);
        setTabularType(itemsDesc.tabularTypeName, itemsDesc.tabularTypeDescription);

        tabularData = new TabularDataSupport(tabularType);
    } //setTabularData

    private void setRowType(String rowTypeName, String rowTypeDescription) {
        try {
            rowType = new CompositeType(rowTypeName, rowTypeDescription, itemNames, itemDescriptions, itemTypes);
        } catch (OpenDataException e) {
            try {
				logger.log(Level.ERROR,BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CREATING_COMPOSITETYPE_ERROR, rowTypeName));
			} catch (ObjectCreationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
    } //setRowType

    private void setTabularType(String tabularTypeName, String tabularTypeDescription) {
        try {
            tabularType = new TabularType(tabularTypeName,tabularTypeDescription,rowType,indexNames);
        } catch (OpenDataException e) {
            try {
				logger.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CREATING_TABULARTYPE_ERROR, tabularTypeName));
	            e.printStackTrace();
            } catch (ObjectCreationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    } //setTabularType

    //ads current row to the table
    public void put(Object[] itemValues) {
        setTableRow(itemValues);
        tabularData.put(tableRow);
    } //put

    private void setTableRow(Object[] itemValues) {
        try {
            tableRow = new CompositeDataSupport(rowType, itemNames, itemValues);
        } catch (OpenDataException e) {
            try {
				logger.log(Level.ERROR,BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CREATING_COMPOSITEDATASUPPORT_ERROR));
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
    } //setTableRow

    //returns the actual table, after rows have been filled in.
    public TabularDataSupport getTabularData(String invokedMethod){
        if (tabularData == null)
            setTabularData(invokedMethod);

        return tabularData;
    }

    public OpenType[] getItemTypes() {
        return itemTypes;
    }

    public String[] getItemNames() {
        return itemNames;
    }

    public String[] getIndexNames() {
        return indexNames;
    }

    public String[] getItemDescriptions() {
        return itemDescriptions;
    }

    public int getNumItems() {
        return numItems;
    }

}
