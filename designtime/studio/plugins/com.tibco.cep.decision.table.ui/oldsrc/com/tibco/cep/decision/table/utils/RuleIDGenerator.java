/**
 * 
 */
package com.tibco.cep.decision.table.utils;

import com.jidesoft.decision.AbstractIDGenerator;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;


/**
 * @author aathalye
 *
 */
public class RuleIDGenerator extends AbstractIDGenerator<String> {
	
	/**
	 * Start with this index
	 */
	private String _nextID = "1";
	
	private IDMemento savedIdMemento;
	
		 
	public String getNextID() {
		String id = _nextID;
        reserveID(id);
        int integerId = Integer.parseInt(id);
        _nextID = Integer.toString(integerId + 1);
        DecisionTableUIPlugin.debug(this.getClass().getName(), "Saving id {0}", id);
        savedIdMemento = new IDMemento(id);
        return id;
	}
	
	public String getCurrentID() {
		return _nextID;
	}
	

	/* (non-Javadoc)
	 * @see com.jidesoft.decision.IDGenerator#setID(java.lang.Object)
	 */
	public void setID(String id) {
		_nextID = id;
		savedIdMemento = new IDMemento(id);
	}

	/* (non-Javadoc)
	 * @see com.jidesoft.decision.IDGenerator#reset()
	 */
	public void reset() {
		_nextID = "1";
	}
	
	class IDMemento {
		
		private String savedId;
		
		IDMemento(String savedId) {
			this.savedId = savedId;
		}
		
		String getID() {
			return savedId;
		}
	}

	/* (non-Javadoc)
	 * @see com.jidesoft.decision.IDGenerator#getPreviousID()
	 */
	public void restorePreviousID() {
		String previousID = savedIdMemento.getID();
		DecisionTableUIPlugin.debug(this.getClass().getName(), "Previous id {0}", previousID);
		_nextID = previousID;
		Integer integer = Integer.parseInt(previousID) - 1;
		savedIdMemento = new IDMemento(integer.toString());
	}
}
