package com.tibco.cep.decision.table.codegen;

import java.util.List;

import com.tibco.be.parser.RuleError;
import com.tibco.cep.decision.table.language.MergedTable;
import com.tibco.cep.decision.table.model.dtmodel.Table;

public class DTCodegenTableContext {
	
	
	public DTCodegenTableContext(Table table, 
			                     String projectName,
			                     MergedTable mt, 
			                     List<RuleError> errorList) {
		this.table = table;
		this.errorList = errorList;
		this.projectName = projectName;
		this.mt = mt;
	}
	public Table table = null;
	public List<RuleError> errorList = null;
	public String projectName = null;
	public MergedTable mt = null;
}
