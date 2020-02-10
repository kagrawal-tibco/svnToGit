package com.tibco.cep.decision.table.constraintpane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: ssubrama
 * Creation Date: Aug 2, 2008
 * Creation Time: 8:24:49 AM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
class RuleTupleInfo extends DecisionTableMutableAttributes.IdAttribute {

    DecisionTable dt;
    
    private List<Cell> actions = new ArrayList<Cell>();

    public RuleTupleInfo(DecisionTable dt) {
        this.dt = dt;
    }
    
    void addAction(Cell action) {
    	if (!actions.contains(action)) {
    		actions.add(action);
    	}
    }
    
    void removeAction(Cell action) {
    	actions.remove(action);
    }
    
    void removeAllActions(List<Cell> cellsToRemove) {
    	actions.removeAll(cellsToRemove);
    }
    
    List<Cell> getActions() {
    	return Collections.unmodifiableList(actions);
    }
}
