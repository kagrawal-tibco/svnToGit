package com.tibco.cep.studio.core.adapters.mutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.core.adapters.RuleFunctionAdapter;
import com.tibco.cep.studio.core.adapters.SymbolAdapter;

public class MutableRuleFunctionAdapter extends RuleFunctionAdapter<RuleFunction> implements com.tibco.cep.designtime.model.rule.RuleFunction {

//	protected Validity validity;
//    protected String m_body;
//    protected boolean isVirtual = false;
//
//    protected int m_compilationStatus;
//    protected String m_returnType;
//    
//
//    protected Symbols m_decls;

    

    public MutableRuleFunctionAdapter(
			com.tibco.cep.designtime.core.model.rule.RuleFunction adapted,
			Ontology emfOntology) {
		super(adapted, emfOntology);
		adapted.setSymbols(RuleFactory.eINSTANCE.createSymbols());
		// TODO Auto-generated constructor stub
	}


	public MutableRuleFunctionAdapter(Ontology ontology, Folder folder, String name) {
		this(RuleFactory.eINSTANCE.createRuleFunction(),ontology);
		adapted.setName(name);
		adapted.setFolder(folder.getFullPath());
		adapted.setActionText("");
		adapted.setReturnType(null);
		adapted.setValidity(com.tibco.cep.designtime.core.model.rule.Validity.ACTION);
		
//        super(ontology, folder, name);
//        this.m_decls = new CGMutableSymbolsAdapter();
//        m_body = "";
//    	m_returnType = null;
//    	validity = Validity.ACTION;
//        this.scopeMutationObserver = new ScopeMutationObserver(this);
//        this.scopeMutationObserver.startObserving(this.m_decls);
    }


    public MutableRuleFunctionAdapter(com.tibco.cep.designtime.model.rule.RuleFunction rf) {
		this(rf.getOntology(),rf.getFolder(),rf.getName());
		adapted.setValidity(com.tibco.cep.designtime.core.model.rule.Validity.get(rf.getValidity().getValue()));
		adapted.setActionText(rf.getActionText());
		//adapted.setBindingString(rf.getBindingString());
		adapted.setActionText(rf.getBody());
		adapted.setConditionText(rf.getConditionText());
		adapted.setDescription(rf.getDescription());
		Map props = rf.getExtendedProperties();
		for (Iterator<Map.Entry> it =  props.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = it.next();
			String key = (String) entry.getKey();
			Object val = entry.getValue();
			Entity entity = createProperty(key, val);
			adapted.getExtendedProperties().getProperties().add(entity);			
		}
		adapted.setGUID(rf.getGUID());
		adapted.setReturnType(rf.getReturnType());
		com.tibco.cep.designtime.model.rule.Symbols symbols = rf.getScope();
		for (Iterator<com.tibco.cep.designtime.model.rule.Symbol> it = symbols.values().iterator(); it.hasNext();) {
			com.tibco.cep.designtime.model.rule.Symbol s = it.next();
			Symbol adaptedSymbol = RuleFactory.eINSTANCE.createSymbol();
			adaptedSymbol.setIdName(s.getName());
			adaptedSymbol.setArray(s.isArray());
			adaptedSymbol.setType(s.getType());
			adaptedSymbol.setTypeExtension(s.getTypeExtension());
			adapted.getSymbols().getSymbolMap().put(adaptedSymbol.getIdName(), adaptedSymbol);
		}
		adapted.setVirtual(rf.isVirtual());
	}


    public void setName(String name, boolean renameOnConflict) {
    	adapted.setName(name);
//        String oldPath = getFullPath();
//        super.setName(name, renameOnConflict);
//        String newPath = getFullPath();
//
//        pathChanged(oldPath, newPath);
    }


    public void setFolder(Folder folder) {
    	adapted.setFolder(folder.getFullPath());
//    	String oldPath = adapted.getFullPath();
////        m_ontology.setEntityFolder(this, folder);
////        String newPath = getFullPath();
////
//        pathChanged(oldPath, newPath);
    }
   
    public void setValidity(com.tibco.cep.designtime.model.rule.RuleFunction.Validity actionOnly) {
        adapted.setValidity(com.tibco.cep.designtime.core.model.rule.Validity.get(actionOnly.getValue()));
    }
    
    public void setValidity(com.tibco.cep.designtime.core.model.rule.Validity actionOnly) {
        adapted.setValidity(actionOnly);
    }



    public void setVirtual(boolean isVirtual) {
        adapted.setVirtual(isVirtual);
        if (isVirtual) {
            adapted.setReturnType(null);
        }
    }



    public void setArgumentType(String identifier, String type) {
    	Symbol s = RuleFactory.eINSTANCE.createSymbol();
    	s.setIdName(identifier);
    	s.setType(type);
    	adapted.getSymbols().getSymbolMap().put(s.getIdName(),s);
    }


    public boolean deleteIdentifier(String identifier) {
    	Symbol removed = adapted.getSymbols().getSymbolMap().remove(identifier);
    	return (removed != null);
//        return (rem != null? true: false);
    }


    
    public void setBody(String body) {
        if (body == null) {
        	adapted.setActionText("");
        } else {
            adapted.setActionText(body);
        }
    }


 
    public void setConditionText(String text) {
    }


    
    public String getReturnType() {
        String type = adapted.getReturnType();
        if (type == null) return null;
        int indexOfDot = type.indexOf(".");
        if (indexOfDot == -1) return type;
        return type.substring(0, indexOfDot);
    }

    public void setReturnType(String retType) {
    	adapted.setReturnType(retType);
    	adapted.setCompilationStatus(-1);
    }


  


    public void setCompilationStatus(int status) {
        adapted.setCompilationStatus(status);
    }
    
    public Collection getSymbolsList() {
    	List<com.tibco.cep.designtime.model.rule.Symbol> list = new ArrayList<com.tibco.cep.designtime.model.rule.Symbol>();
    	List<Symbol> slist = adapted.getSymbols().getSymbolList();
    	for(int i=0; i < slist.size();i++) {
    		list.add(new SymbolAdapter(slist.get(i)));
    	}
    	return list;
    }


    public void setScope(com.tibco.cep.designtime.model.rule.Symbols declarations) {
        setScopeInternal(declarations);


    }
    
    protected void setScopeInternal(com.tibco.cep.designtime.model.rule.Symbols declarations) {
//        this.m_decls = new CGMutableSymbolsAdapter(declarations,emfOntology);
        for(Iterator it = declarations.values().iterator();it.hasNext();) {
			com.tibco.cep.designtime.model.rule.Symbol s = (com.tibco.cep.designtime.model.rule.Symbol) it.next();
			Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
			symbol.setIdName(s.getName());
			symbol.setType(s.getType());
			symbol.setTypeExtension(s.getTypeExtension());
			adapted.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
		}
    }

    public com.tibco.cep.designtime.model.rule.Symbols getScope() {
    	Symbols symbols = adapted.getSymbols();
    	return new MutableSymbolsAdapter(symbols,emfOntology);
//        return m_decls;
    }
    
//    public void participantPathChanged(String oldPath, String newPath) {
//        if (null != this.m_decls) {
//            for (Iterator it = this.m_decls.values().iterator(); it.hasNext();) {
//                final CGMutableSymbolAdapter symbol = (CGMutableSymbolAdapter) it.next();
//                if (oldPath.equals(symbol.getType())) {
//                    symbol.setType(newPath + symbol.getTypeExtension());
//                }
//            }//for
//        }//if
//    }
}
