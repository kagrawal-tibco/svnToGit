package com.tibco.rta.bemm.actions;

import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.QueryDefEx;
import com.tibco.rta.query.QueryResultTuple;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.service.query.QueryUtils;

public class StreamingQueryAction extends AbstractActionImpl {

    private static StreamingQueryAction INSTANCE;

    private StreamingQueryActionHandler queryActionHandler;

    public static StreamingQueryAction getInstance(Rule rule, ActionDef actionDef, StreamingQueryActionHandler queryActionHandler) {
        if (INSTANCE == null) {
            INSTANCE = new StreamingQueryAction(rule, actionDef, queryActionHandler);
        }
        return INSTANCE;
    }

	public StreamingQueryAction(Rule rule, ActionDef actionDef, StreamingQueryActionHandler queryActionHandler) {
		super(rule, actionDef);
		this.queryActionHandler = queryActionHandler;
	}

	@Override
	public ActionHandlerContext getActionHandlerContext() {
		return queryActionHandler;
	}

	@Override
	public String getAlertType() {
		return "StreamingQueryAction";
	}

	@Override
	public void performAction(Rule rule, MetricNodeEvent metricNodeEvent) throws Exception {
		QueryDefEx queryDef = (QueryDefEx) rule.getRuleDef().getSetCondition();
        QueryResultTuple queryResultTuple = new QueryResultTuple();
        queryResultTuple.setQueryName(queryDef.getName());
        
        MetricResultTuple resultTuple = QueryUtils.convertMetricNodeToMetricResultTuple(metricNodeEvent);
        queryResultTuple.setMetricResultTuple(resultTuple);

        //ServerSessionRegistry.INSTANCE.invoke(queryResultTuple);
        queryDef.getResultHandler().onData(queryResultTuple);		
	}

	@Override
	public String getAlertDetails() {
		return "";
	}

	@Override
	public String getAlertText() {
		return "";
	}

}
