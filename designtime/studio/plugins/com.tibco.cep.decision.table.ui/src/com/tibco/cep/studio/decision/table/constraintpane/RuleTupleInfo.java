package com.tibco.cep.studio.decision.table.constraintpane;

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
public class RuleTupleInfo extends DecisionTableMutableAttributes.IdAttribute {

   private  DecisionTable dt;
    
    private List<Cell> actions = new ArrayList<Cell>();

    public RuleTupleInfo(DecisionTable dt) {
        this.dt = dt;
    }
    
    public void addAction(Cell action) {
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
    
    public List<Cell> getActions() {
    	return Collections.unmodifiableList(actions);
    }

	public DecisionTable getDecisionTable() {
		return dt;
	}

	public void setDecisionTable(DecisionTable dt) {
		this.dt = dt;
	}

	public void setActions(List<Cell> actions) {
		this.actions = actions;
	}
    
}
