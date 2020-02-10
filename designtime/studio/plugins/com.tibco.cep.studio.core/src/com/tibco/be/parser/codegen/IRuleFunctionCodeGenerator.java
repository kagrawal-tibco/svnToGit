package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.rule.RuleFunction;

public interface IRuleFunctionCodeGenerator {

	void generateRuleFunction(RuleFunction fn, CodeGenContext ctx) throws Exception;
	void generateRuleFunctionStream(RuleFunction fn, CodeGenContext ctx) throws Exception;

}
