package com.tibco.cep.runtime.service.decision;

import java.util.ArrayList;

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
    ArrayList actions = new ArrayList();

    public RuleTupleInfo(DecisionTable dt) {
        this.dt = dt;
    }
}
