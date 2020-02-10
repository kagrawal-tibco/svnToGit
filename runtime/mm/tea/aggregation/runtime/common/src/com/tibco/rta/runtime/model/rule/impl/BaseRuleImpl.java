//package com.tibco.rta.runtime.model.rule.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.tibco.rta.query.QueryDef;
//import com.tibco.rta.runtime.model.MetricNodeEvent;
//import com.tibco.rta.runtime.model.rule.Action;
//import com.tibco.rta.runtime.model.rule.mutable.MutableRule;
//
///**
// * Base rule implementation class.
// */
//public abstract class BaseRuleImpl implements MutableRule {
//
////    protected String name;
//
////    protected QueryDef setCondition;
//
////    protected List<Action> setActionList = new ArrayList<Action>();
//
//
//    public BaseRuleImpl(String name, QueryDef setCondition) {
//        this.name = name;
//        this.setCondition = setCondition;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//
//    //@Override
//    public void addSetAction(Action action) {
//        setActionList.add(action);
//    }
//
//   // @Override
//    public abstract void eval(MetricNodeEvent nodeEvent) throws Exception;
//
////    @Override
////    public QueryDef getSetCondition() {
////        return setCondition;
////    }
////
////    @Override
////    public QueryDef getClearCondition() {
////        return null;
////    }
//
//   // @Override
//    public void addClearAction(Action action) {
//        //no-op
//    }
//
////    protected void performActions(MetricNodeEvent node, List<Action> actionList) {
////        try {
////            for (Action action : actionList) {
////                action.performAction(this, node);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//}
