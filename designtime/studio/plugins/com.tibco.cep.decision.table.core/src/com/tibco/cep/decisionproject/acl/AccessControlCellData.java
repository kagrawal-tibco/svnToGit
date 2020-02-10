/**
 * 
 */
package com.tibco.cep.decisionproject.acl;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;

/**
 * @author aathalye
 *
 */
public class AccessControlCellData {
	
	private Column associatedColumn;
	
	private TableRuleVariable trv;
	
	public AccessControlCellData(final Column column, 
			                     final TableRuleVariable trv) {
		this.associatedColumn = column;
		this.trv = trv;
	}

	/**
	 * @return the associatedColumn
	 */
	public final Column getAssociatedColumn() {
		return associatedColumn;
	}

	
	/**
	 * @return the trv
	 */
	public final TableRuleVariable getTrv() {
		return trv;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof AccessControlCellData)) {
			return false;
		}
		AccessControlCellData acd = (AccessControlCellData)obj;
		//Get column
		if (!acd.getAssociatedColumn().getId().equals(associatedColumn.getId())) {
			return false;
		}
		if (!acd.getTrv().equals(trv)) {
			return false;
		}
		return true;
	}
}
