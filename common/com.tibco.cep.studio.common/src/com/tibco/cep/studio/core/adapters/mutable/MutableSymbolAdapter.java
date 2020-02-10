package com.tibco.cep.studio.core.adapters.mutable;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;
import com.tibco.cep.studio.core.adapters.SymbolAdapter;

public class MutableSymbolAdapter extends SymbolAdapter implements Symbol {
	
//	private String idName;
//    private String type;
//    private Domain domain;
//    private Symbols parent;
	
	public MutableSymbolAdapter(final com.tibco.cep.designtime.core.model.rule.Symbol adapted) {
		super(adapted);
	}

    public MutableSymbolAdapter(Symbols parent, String idname, String typePath) {
        this(parent, idname, typePath, null);
    }


    public MutableSymbolAdapter(Symbols parent, String idname, String typePath, Domain domain) {
    	super(RuleFactory.eINSTANCE.createSymbol());
    	adapted.setIdName(idname);
    	adapted.setType(typePath);
//        this.parent = parent;
//        this.idName = idname;
//        this.type = typePath;
//        if (null == domain) {
//            this.domain = new CGMutableDomainAdapter(this);
//        } else {
//            this.domain = domain;
//        }        
    }


	public MutableSymbolAdapter(Symbol symbol) {
		super(RuleFactory.eINSTANCE.createSymbol());
		adapted.setType(symbol.getType());
		adapted.setIdName(symbol.getName());
//		this.domain = new CGMutableDomainAdapter(this);
	}
	
	public MutableSymbolAdapter(SymbolAdapter symbol) {
		super(symbol);
		adapted.setType(symbol.getType());
		adapted.setIdName(symbol.getName());
//		this.domain = new CGMutableDomainAdapter(this);		
	}


//	public Domain getDomain() {
//        return this.domain;
//    }



//
//    public String getName() {
//        return this.idName;
//    }


    public Symbols getSymbols() {
//        return this.parent;
    	return null;
    }


    public String getType() {
        if (null == adapted.getType()) { return ""; }
        int indexOfDot = adapted.getType().indexOf(".");
        if (indexOfDot == -1) return adapted.getType();
        return adapted.getType().substring(0, indexOfDot);
    }


    public String getTypeExtension() {
        if (null == adapted.getType()) { return ""; }
        int indexOfDot = adapted.getType().lastIndexOf(".");
        if (indexOfDot == -1) {
            return "";
        }
        return adapted.getType().substring(indexOfDot);
    }


    public boolean setName(String name) {
        if (ModelUtils.IsEmptyString(name) || !ModelNameUtil.isValidIdentifier(name)) {
            return false; // Invalid name.
        }
        if (adapted.getIdName() != null && adapted.getIdName().equals(name)) {
            // Same name, nothing to do.
        } 
//        else if (null == this.parent) {
//            this.idName = name; // No parent => no conflict.
//        } else {
//            final Symbol existingEntry = (Symbol) this.parent.get(name);
//            if ((null != existingEntry) && (this != existingEntry)) {
//                return false; // Another symbol already exists with that name in the parent => conflict.
//            }
////            final Map changes = new HashMap();
////            changes.put("name", this.idName);
//            this.idName = name;            
//        }
        return true;
    }


    public void setType(String entityPath) {
        if (((null == entityPath) && (null == adapted.getType()))
                || ((null != entityPath) && entityPath.equals(adapted.getType()))) {
            return; // Same type, nothing to do.
        }
//        final Map changes = new HashMap();
//        changes.put("type", this.type);
        setTypeInternal(entityPath);        
    }

    public void setTypeInternal(String entityPath) {
    	adapted.setType(entityPath);
    }

    public String getTypeWithExtension() {
        return adapted.getType();
    }

}
