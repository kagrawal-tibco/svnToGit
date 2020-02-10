package com.tibco.cep.decision.table.codegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.MethodRecWriter;
import com.tibco.be.parser.codegen.RuleFunctionCodeGeneratorSmap;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;

public class DecisionTableCodeGenerator extends RuleFunctionCodeGeneratorSmap {
	@SuppressWarnings("rawtypes")
    public static MethodRecWriter generateStaticMethodFromRuleFn(JavaClassWriter cc,RuleFunction fn, List<RuleError> errorList, 
                    Properties oversizeStringConstants, Map ruleFnUsages, boolean hasOversizeClassName, Ontology o
                    , Map<String, Map<String, int[]>> propInfoCache)
    {
        return RuleFunctionCodeGeneratorSmap.generateStaticMethodFromRuleFn(cc,fn, errorList, oversizeStringConstants, ruleFnUsages
        		, hasOversizeClassName, false, true, o, propInfoCache);
    }
}