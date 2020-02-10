package com.tibco.cep.decision.table.language;

import java.util.Map;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.util.DTDomainUtil;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;

public class DTCellCompilationContext {
	public TableRuleVariable trv = null;
	public Column col = null;
	public Map<String, DomainEntry> domainEntryMap = null;

	public DTCellCompilationContext(TableRuleVariable trv, Column col,
			Map<String, DomainEntry> domainEntryMap) {
		super();
		this.trv = trv;
		this.col = col;
		this.domainEntryMap = domainEntryMap;
	}
	
	public String getExpr() {
		if (trv == null) return null;
		return trv.getExpr();
	}
	
	public void initDomainEntryMap(String projectName, Map<String, Map<String, DomainEntry>> dmCache) {
		domainEntryMap = dmCache.get(col.getPropertyPath());
		if (domainEntryMap == null) {
			domainEntryMap = DTDomainUtil.getDomainEntries(projectName, col);
			dmCache.put(col.getPropertyPath(), domainEntryMap);
		}
	}
}
