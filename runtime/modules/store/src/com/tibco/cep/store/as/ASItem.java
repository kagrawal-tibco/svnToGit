/**
 * 
 */
package com.tibco.cep.store.as;

import com.tibco.cep.store.Item;
import com.tibco.cep.store.as.serializer.ASRowCodec;
import com.tibco.datagrid.Row;

/**
 * @author vpatil
 *
 */
public class ASItem extends Item {
	private Row row;
	
	@Override
	public void setItemCodec() {
		this.itemCodec = new ASRowCodec();
	}

	@Override
	public void createItem() throws Exception {
		row = ((ASContainer) getContainer()).getTable().createRow();
	}

	@Override
	public void destroy() throws Exception {
		if (row != null) {
			row.destroy();
		}
	}
	
	public void setRow(Row row) {
		this.row = row;
	}
	
	public Row getRow() {
		return this.row;
	}
	
	@Override
	public void clear() throws Exception {
		if (row != null) {
			row.clear();
		}
	}
	
	@Override
	public long getExpiration() throws Exception {
		long rowExpiration = this.row.getExpiration();
		long epochTimeInSec = (System.currentTimeMillis()/1000);
		if(rowExpiration < epochTimeInSec) return 0;
		return this.row.getExpiration() - epochTimeInSec;
	}
	
	@Override
	public void setTTL(long ttl) throws Exception {
		this.row.setTTL(ttl);
	}
}
