package com.tibco.cep.bemm.monitoring.bean;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryType;
import com.tibco.rta.RtaSession;
import com.tibco.rta.query.filter.Filter;

public class BemmRule {
	
	private String ruleName;
	private String ruleScheduleName;
	private String ruleUserName;
	private RtaSession session;
	private String sessionName;
	
	private String setConditionName;
	private String setConditionSchemaName;
	private String setConditionSchemaCube;
	private String setConditionHierarchy;
	private String setConditionRuleMetric;
	private QueryType setConditionQueryType;
	private int setConditionBatchSize=5;
	
	private boolean clearConditionPresent=false;
	private String clearConditionName;
	private String clearConditionSchemaName;
	private String clearConditionSchemaCube;
	private String clearConditionHierarchy;
	private String clearConditionRuleMetric;
	private QueryType clearConditionQueryType;
	private int clearConditionBatchSize=5;
	
	private List<Filter> setConditionFilterList=new ArrayList<Filter>();
	private List<Filter> clearConditionFilterList=new ArrayList<Filter>();
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleScheduleName() {
		return ruleScheduleName;
	}
	public void setRuleScheduleName(String ruleScheduleName) {
		this.ruleScheduleName = ruleScheduleName;
	}
	public String getRuleUserName() {
		return ruleUserName;
	}
	public void setRuleUserName(String ruleUserName) {
		this.ruleUserName = ruleUserName;
	}
	public RtaSession getSession() {
		return session;
	}
	public void setSession(RtaSession session) {
		this.session = session;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getSetConditionName() {
		return setConditionName;
	}
	public void setSetConditionName(String setConditionName) {
		this.setConditionName = setConditionName;
	}
	public String getSetConditionSchemaName() {
		return setConditionSchemaName;
	}
	public void setSetConditionSchemaName(String setConditionSchemaName) {
		this.setConditionSchemaName = setConditionSchemaName;
	}
	public String getSetConditionSchemaCube() {
		return setConditionSchemaCube;
	}
	public void setSetConditionSchemaCube(String setConditionSchemaCube) {
		this.setConditionSchemaCube = setConditionSchemaCube;
	}
	public String getSetConditionHierarchy() {
		return setConditionHierarchy;
	}
	public void setSetConditionHierarchy(String setConditionHierarchy) {
		this.setConditionHierarchy = setConditionHierarchy;
	}
	public String getSetConditionRuleMetric() {
		return setConditionRuleMetric;
	}
	public void setSetConditionRuleMetric(String setConditionRuleMetric) {
		this.setConditionRuleMetric = setConditionRuleMetric;
	}
	public QueryType getSetConditionQueryType() {
		return setConditionQueryType;
	}
	public void setSetConditionQueryType(QueryType setConditionQueryType) {
		this.setConditionQueryType = setConditionQueryType;
	}
	public int getSetConditionBatchSize() {
		return setConditionBatchSize;
	}
	public void setSetConditionBatchSize(int setConditionBatchSize) {
		this.setConditionBatchSize = setConditionBatchSize;
	}
	public boolean isClearConditionPresent() {
		return clearConditionPresent;
	}
	public void setClearConditionPresent(boolean clearConditionPresent) {
		this.clearConditionPresent = clearConditionPresent;
	}
	public String getClearConditionName() {
		return clearConditionName;
	}
	public void setClearConditionName(String clearConditionName) {
		this.clearConditionName = clearConditionName;
	}
	public String getClearConditionSchemaName() {
		return clearConditionSchemaName;
	}
	public void setClearConditionSchemaName(String clearConditionSchemaName) {
		this.clearConditionSchemaName = clearConditionSchemaName;
	}
	public String getClearConditionSchemaCube() {
		return clearConditionSchemaCube;
	}
	public void setClearConditionSchemaCube(String clearConditionSchemaCube) {
		this.clearConditionSchemaCube = clearConditionSchemaCube;
	}
	public String getClearConditionHierarchy() {
		return clearConditionHierarchy;
	}
	public void setClearConditionHierarchy(String clearConditionHierarchy) {
		this.clearConditionHierarchy = clearConditionHierarchy;
	}
	public String getClearConditionRuleMetric() {
		return clearConditionRuleMetric;
	}
	public void setClearConditionRuleMetric(String clearConditionRuleMetric) {
		this.clearConditionRuleMetric = clearConditionRuleMetric;
	}
	public QueryType getClearConditionQueryType() {
		return clearConditionQueryType;
	}
	public void setClearConditionQueryType(QueryType clearConditionQueryType) {
		this.clearConditionQueryType = clearConditionQueryType;
	}
	public int getClearConditionBatchSize() {
		return clearConditionBatchSize;
	}
	public void setClearConditionBatchSize(int clearConditionBatchSize) {
		this.clearConditionBatchSize = clearConditionBatchSize;
	}
	public List<Filter> getSetConditionFilterList() {
		return setConditionFilterList;
	}
	public void setSetConditionFilterList(List<Filter> setConditionFilterList) {
		this.setConditionFilterList = setConditionFilterList;
	}
	public List<Filter> getClearConditionFilterList() {
		return clearConditionFilterList;
	}
	public void setClearConditionFilterList(List<Filter> clearConditionFilterList) {
		this.clearConditionFilterList = clearConditionFilterList;
	}
	
	
	
	
	}
