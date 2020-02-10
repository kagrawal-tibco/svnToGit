package com.tibco.be.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.be.parser.codegen.RuleBlockLineBuffer;
import com.tibco.be.parser.semantic.SmapStratum;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.RootNode;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.mutable.MutableSymbolAdapter;
import com.tibco.cep.studio.core.adapters.mutable.MutableSymbolsAdapter;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 29, 2004
 * Time: 5:19:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleInfo {
    protected String path = "";
    
    //protected String packageName = "";
    
    //AttrRecord
    protected ArrayList attrList = new ArrayList();

    //NamedNode
    protected ArrayList importList = new ArrayList();
    
    //key is String of declaration name, value is String of model path of type of declaration
    protected MutableSymbolsAdapter declMap = new MutableSymbolsAdapter(null);

    //this string should follow the format used by RuleFunction for return types
    protected String returnTypeName = null;
    
    //One tree per Predicate()
    protected List whenTreeList = new ArrayList();

    //One tree per Statement()
    protected List thenTreeList = new ArrayList();

    //RuleError
    protected ArrayList<RuleError> errorList = new ArrayList<RuleError>();

    protected boolean m_usesForwardChain=true;
    
    protected String m_rank = null;


    private Map<String, SmapStratum> conditionStratumMap = new HashMap<String, SmapStratum>();

    private Map<String, SmapStratum> actionStratumMap = new HashMap<String, SmapStratum>();
    private RuleBlockLineBuffer ruleBlockBuffer;

    public Map<String, SmapStratum> getConditionStratumMap() {
        return conditionStratumMap;
    }

    public Map<String, SmapStratum> getActionStratumMap() {
        return actionStratumMap;
    }

    public void setRuleBlockBuffer(RuleBlockLineBuffer ruleBlockBuffer) {
        this.ruleBlockBuffer = ruleBlockBuffer;
    }

    public RuleBlockLineBuffer getRuleBlockBuffer() {
        return ruleBlockBuffer;
    }

    public class AttrRecord {
        public Token attr;
        public String value;
        
        public AttrRecord(Token attr, String value) {
            this.attr = attr;
            this.value = value;
        }
    }
    
    /**
     * @param path the model path of Rule object corresponding to this RuleInfo (in the format of MutableRule.getFullPath())
     */ 
    public void setPath(String path) {
        this.path = path;
    }
    
    /*
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    */
    
    public void addImport(NameNode importType) {
        importList.add(importType);
    }
    
    public void addAttribute(Token attribute, String value) {
        attrList.add(new AttrRecord(attribute, value));
    }
    
    public void setForwardChain(boolean forwardChain) {
        m_usesForwardChain=forwardChain;
    }

    public boolean usesForwardChain() {
        return this.m_usesForwardChain;
    }
    
    

    /**
	 * @return the m_rank
	 */
	public String getRank() {
		return m_rank;
	}

	/**
	 * @param m_rank the m_rank to set
	 */
	public void setRank(String m_rank) {
		this.m_rank = m_rank;
	}

	public void setPriority(int value) {
        String tokenImage = RuleGrammarConstants.tokenImage[RuleGrammarConstants.PRIORITY];
        //the token images in the the RuleGrammarConstants.tokenImage array are 
        // usually surrounded by quote marks which must be removed
        if(tokenImage.startsWith("\"")) tokenImage = tokenImage.substring(1);
        if(tokenImage.endsWith("\"")) tokenImage = tokenImage.substring(0, tokenImage.length() - 1);
        
        Token t = Token.newToken(RuleGrammarConstants.PRIORITY, tokenImage);
        attrList.add(new AttrRecord(t, String.valueOf(value)));
    }
    
    public void addDeclaration(String declType, String declName) {
        declMap.put(declName, declType);
    }
    public void setDeclarations(Symbols declarations) {
    	if(declarations instanceof MutableSymbolsAdapter) {
        declMap = (MutableSymbolsAdapter) declarations;
    		
    	} else {
    		for(Iterator it =declarations.values().iterator(); it.hasNext();) {
    			Symbol s = (Symbol) it.next();
    			declMap.put(new MutableSymbolAdapter(s));
    		}
    	}
    }
    public void setReturnTypeName(String returnTypeName) {
        this.returnTypeName = returnTypeName;
    }
    
    public void addWhenTree(RootNode tree) {
        whenTreeList.add(tree);
    }
    public void setWhenTrees(List trees) {
        whenTreeList = trees;
    }
    public void addThenTree(RootNode tree) {
        thenTreeList.add(tree);
    }
    public void setThenTrees(List trees) {
        thenTreeList = trees;
    }
    
    public void addError(RuleError error) {
        errorList.add(error);
    }
    
    public void addAllErrors(Collection errorList) {
        this.errorList.addAll(errorList);
    }
    
    /**
     * returns the full path of this rule, including the rule's name
     * @return a String
     */ 
    public String getFullName() {
        return path;
    }
    
    /*
    public String getPackageName() {
        return packageName;
    }
    */
    
    public Iterator getAttributes() {
        return attrList.iterator();
    }
    
    public Iterator getImports() {
        return importList.iterator();
    }
    
    public Symbols getDeclarations() {
        return declMap;
    }
    
    public String getReturnTypeName() {
        return returnTypeName;
    }
    
    public Iterator getWhenTrees() {
        return whenTreeList.iterator();
    }
    
    public Iterator getThenTrees() {
        return thenTreeList.iterator();
    }
    
    public List<RuleError> getErrors() {
        return errorList;
    }
}
