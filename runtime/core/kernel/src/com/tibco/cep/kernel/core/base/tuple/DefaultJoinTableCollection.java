package com.tibco.cep.kernel.core.base.tuple;

/*
* Author: Suresh Subramani / Date: 8/22/12 / Time: 6:27 PM
*/


import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.service.ResourceManager;

import java.util.LinkedList;

public class DefaultJoinTableCollection implements JoinTableCollection {


    volatile JoinTable[] allTables = new JoinTable[0];
    LinkedList<Short> recycleIds = new LinkedList<Short>();

    @Override
    synchronized public short addTable(JoinTable _table) throws SetupException {
            if (recycleIds.size() > 0) {
                short id = recycleIds.removeFirst();
                if (allTables[id] != null)
                    throw new SetupException("Table Id [" + id + "] already assigned");
                allTables[id] = _table;
                return id;
            } else {
                int id = allTables.length;
                if (id == Short.MAX_VALUE) {
                    throw new SetupException(ResourceManager.getString("joinTable.rearchMaxId"));
                }
                JoinTable[] newTable = new JoinTable[id + 1];
                System.arraycopy(allTables, 0, newTable, 0, id);
                newTable[id] = _table;
                allTables = newTable;
                return (short) id;
            }
    }

    @Override
    public JoinTable getJoinTable(short tableid) {
        JoinTable result = _getJoinTable(tableid);
        //maybe the table is being added
        if(result == null) {
        	synchronized(this) {
        		result = _getJoinTable(tableid);
        	}
        }
        return result;
    }
    
    protected JoinTable _getJoinTable(short tableid) {
    	JoinTable[] tables = allTables;
    	return ((tableid >= tables.length) || (tableid < 0)) ? null : tables[tableid];
    }
    
    @Override
    synchronized public void removeTable(JoinTable joinTable) {
            short tId = joinTable.getTableId();

            JoinTable table = getJoinTable(tId);
            if (table == null) return;

            allTables[tId] = null;
            recycleIds.add(tId);
    }

    @Override
    public JoinTable[] toArray() {
        return allTables;
    }

    @Override
    public void clear() {
        JoinTable[] allTables = new JoinTable[0];
        LinkedList<Short> recycleIds = new LinkedList<Short>();
    }
}
