package com.tibco.rta.service.query;

import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/2/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultQueryAction extends AbstractActionImpl {

    private DefaultQueryActionHandler queryActionHandler;

    private DefaultQueryAction(Rule rule, ActionDef actionDef, DefaultQueryActionHandler queryActionHandler) {
    	super(rule, actionDef);
        this.queryActionHandler = queryActionHandler;
    }

    private static DefaultQueryAction INSTANCE;

    public static DefaultQueryAction getInstance(Rule rule, ActionDef actionDef, DefaultQueryActionHandler queryActionHandler) {
        if (INSTANCE == null) {
            INSTANCE = new DefaultQueryAction(rule, actionDef, queryActionHandler);
        }
        return INSTANCE;
    }

    @Override
    public void performAction(Rule rule, MetricNodeEvent metricNodeEvent) throws Exception {
        QueryDef queryDef = rule.getRuleDef().getSetCondition();
        QueryResultTuple queryResultTuple = new QueryResultTuple();
        queryResultTuple.setQueryName(queryDef.getName());
        
        MetricResultTuple resultTuple = QueryUtils.convertMetricNodeToMetricResultTuple(metricNodeEvent);
        queryResultTuple.setMetricResultTuple(resultTuple);

        ServerSessionRegistry.INSTANCE.invoke(queryResultTuple);
    }

    @Override
    public ActionHandlerContext getActionHandlerContext() {
        return queryActionHandler;
    }

	@Override
	public String getAlertText() {
		return "";
	}

	@Override
	public String getAlertDetails() {
		return "";
	}

    @Override
    public String getAlertType() {
        return "DefaultQueryAction";
    }
}
