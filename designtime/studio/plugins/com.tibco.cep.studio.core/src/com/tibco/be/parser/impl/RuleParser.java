package com.tibco.be.parser.impl;

import java.util.ArrayList;

import com.tibco.be.parser.CompileErrors;
import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.RuleFileParserClient;
import com.tibco.be.parser.RuleGrammarConstants;
import com.tibco.be.parser.RuleInfo;
import com.tibco.be.parser.Token;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.cep.designtime.model.ModelUtils;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Sep 30, 2006
 * Time: 4:31:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RuleParser implements RuleFileParserClient {
    protected RuleInfo rInfo = null;
    protected ArrayList rInfoList = new ArrayList();

    //OK to keep incompletly parsed rules?
    public void newRule() {
        rInfo = new RuleInfo();
        rInfoList.add(rInfo);
    }

    public void setName(String name) {
        rInfo.setPath(ModelUtils.convertPackageToPath(name));
    }

    public void addAttribute(Token attribute, String value) {
    	if(attribute.kind == RuleGrammarConstants.FORWARD_CHAIN) {
    		rInfo.setForwardChain(!("false".equalsIgnoreCase(value)));
    	} else if(attribute.kind == RuleGrammarConstants.RANK_FUNCTION) { 
    		if(!"null".equalsIgnoreCase(value)){
    			rInfo.setRank(ModelUtils.convertPackageToPath(value));
    		}
    	}else {
    		rInfo.addAttribute(attribute, value);
    	}
    }

    public void addImport(NameNode importType) {
        rInfo.addImport(importType);
    }

    public void addDeclaration(String declType, String declName) {
        rInfo.addDeclaration(ModelUtils.convertPackageToPath(declType), declName);
    }

    public void addWhenTree(RootNode whenTree) {
        rInfo.addWhenTree(whenTree);
    }

    public void addThenTree(RootNode thenTree) {
        rInfo.addThenTree(thenTree);
    }

    public void addError(ParseException error) {
        addError(error, CompileErrors.syntaxError(error));
    }

    public void addError(ParseException error, String message) {
        if(rInfo == null) {
            rInfo = new RuleInfo();
            rInfoList.add(rInfo);
        }
        rInfo.addError(RuleError.makeSyntaxError(error, message));
    }
}
