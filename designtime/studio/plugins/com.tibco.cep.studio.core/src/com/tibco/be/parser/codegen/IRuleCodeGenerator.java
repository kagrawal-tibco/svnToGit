package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.rule.Rule;

public interface IRuleCodeGenerator {

	void generateRule(Rule rul, CodeGenContext ctx) throws Exception;
	void generateRuleStream(Rule rul, CodeGenContext ctx) throws Exception;

}
