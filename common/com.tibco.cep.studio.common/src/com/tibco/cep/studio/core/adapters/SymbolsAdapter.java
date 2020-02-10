/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.model.Ontology;



/**
 * @author aathalye
 *
 */
public class SymbolsAdapter implements com.tibco.cep.designtime.model.rule.Symbols, ICacheableAdapter {
	
	protected Symbols adapted;
	protected Ontology emfOntology;
	
	public SymbolsAdapter(Ontology ontology) {
		this.emfOntology = ontology;
		adapted = RuleFactory.eINSTANCE.createSymbols();
	}
	
	public SymbolsAdapter(com.tibco.cep.designtime.model.rule.Symbols original,Ontology ontology) {
		this(ontology);
        for (Object o: original.getSymbolsList()) {
        	com.tibco.cep.designtime.model.rule.Symbol s = (com.tibco.cep.designtime.model.rule.Symbol) o;
        	put(s);            
        }
	}
	
	protected Symbols getAdapted() {
		return adapted;
	}
	
	public SymbolsAdapter(Symbols original,Ontology ontology) {
		this.emfOntology = ontology;
		this.adapted = original;
	}
	
	public SymbolsAdapter(List<Symbol> symbols,Ontology ontology) {
		this(ontology);
        for (Symbol s: symbols) {
        	Symbol sym = null;
        	if(s instanceof RuleFunctionSymbol) {
        		sym = RuleFactory.eINSTANCE.createRuleFunctionSymbol();
        	} else {
        		sym = RuleFactory.eINSTANCE.createSymbol();
        	}
        	sym.setIdName(s.getIdName());
        	sym.setType(s.getType());
        	sym.setTypeExtension(s.getTypeExtension());
        	adapted.getSymbolList().add(sym);
//        	adapted.getSymbolMap().put(sym.getIdName(), sym);
        }

	}
	
	
	public com.tibco.cep.designtime.model.rule.Symbol put(com.tibco.cep.designtime.model.rule.Symbol symbol) {
        if (null == symbol) {
            return null;
        }
        final String name = symbol.getName();
        if (null == name) {
            return null;
        }
        Symbol s = RuleFactory.eINSTANCE.createSymbol();
        s.setIdName(symbol.getName());
        s.setType(symbol.getType());
        s.setTypeExtension(symbol.getTypeExtension());
        // TODO: add domain if needed
//        adapted.getSymbolMap().put(name, s);
        adapted.getSymbolList().add(s);
        return new SymbolAdapter(s);
    }
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbols#containsType(java.lang.String)
	 */
	public boolean containsType(String typePath) {
		if (null == typePath) {
            return false;
        }
        for (Symbol symbol: adapted.getSymbolList()) {
//            final Symbol symbol = (Symbol) it.next();
            if (typePath.equals(symbol.getType())) {
                return true;
            }
        }
        return false;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbols#getAvailableIdentifier(java.lang.String)
	 */
	public String getAvailableIdentifier(String baseName) {
		String name = baseName;
        for (long i = 0; adapted.getSymbolMap().containsKey(name); i++) {
            name = baseName + "_" + i;
        }
        return name;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbols#getSymbol(java.lang.String)
	 */
	public com.tibco.cep.designtime.model.rule.Symbol getSymbol(String idName) {
		Symbol symbol = adapted.getSymbolMap().get(idName);
		if (symbol != null) {
			return new SymbolAdapter(symbol);		
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbols#getSymbolsList()
	 */
	public List<com.tibco.cep.designtime.model.rule.Symbol> getSymbolsList() {
		List<com.tibco.cep.designtime.model.rule.Symbol> symbolsList = new ArrayList<com.tibco.cep.designtime.model.rule.Symbol>(adapted.getSymbolMap().size());
		for (Symbol symbol : adapted.getSymbolList()) {
			symbolsList.add(new SymbolAdapter(symbol));
		}
		return symbolsList;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbols#getType(java.lang.String)
	 */
	
	public String getType(String identifier) {
		//This identifier is the alias for this Symbol which will be unique in the RF/Rule
		if(adapted.getSymbolMap().containsKey(identifier)) {
			return adapted.getSymbolMap().get(identifier).getType();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbols#getTypes()
	 */
	
	public LinkedHashSet getTypes() {
		//Why should this return an implementation?
		final LinkedHashSet set = new LinkedHashSet(adapted.getSymbolList().size());
		for (Symbol symbol : adapted.getSymbolList()) {
			set.add(symbol.getType());
		}
		return set;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	
	public void clear() {
		adapted.getSymbolMap().clear();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	
	public boolean containsKey(Object key) {
		return adapted.getSymbolMap().containsKey(key);
	}

	/* (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	
	public boolean containsValue(Object value) {
//		return adapted.getSymbolMap().containsValue(value);
		return false;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	
	public Set<Map.Entry<String, com.tibco.cep.designtime.model.rule.Symbol>> entrySet() {
		Map<String, com.tibco.cep.designtime.model.rule.Symbol> map = new LinkedHashMap<String, com.tibco.cep.designtime.model.rule.Symbol>();
		for (Symbol symbol : adapted.getSymbolList()) {
			map.put(symbol.getIdName(), new SymbolAdapter(symbol));
		}
		return map.entrySet();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	
	public Object get(Object key) {
		// TODO Auto-generated method stub
		if(adapted.getSymbolMap().containsKey(key)) {
			return new SymbolAdapter(adapted.getSymbolMap().get(key));
		} 
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	
	public boolean isEmpty() {
		return adapted.getSymbolList().isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	
	public Set<?> keySet() {
		Set<String> symbols = new LinkedHashSet<String>();
		for (Symbol symbol : adapted.getSymbolList()) {
			symbols.add(symbol.getIdName());
		}
		return symbols;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	
	public Object put(Object key, Object value) {
		if(!(key instanceof String) || !(value instanceof String)) {
			throw new IllegalArgumentException();
		}
		Symbol s = RuleFactory.eINSTANCE.createSymbol();
		s.setIdName((String) key);
		s.setType((String) value);
//		s.setTypeExtension(sym.getTypeExtension());
		//TODO: set Domain here if needed
		if(adapted.getSymbolList().add(s))
			return new SymbolAdapter(s);
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void putAll(Map map) {
		for (Iterator it = map.values().iterator(); it.hasNext();) {
            final Object value = it.next();
            if ((null != value) && (value instanceof com.tibco.cep.designtime.model.rule.Symbol)) {
                this.put((com.tibco.cep.designtime.model.rule.Symbol) value);
            }//if
        }//for
//		throw new UnsupportedOperationException("No mutators allowed");
	}

	/* (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	
	public Object remove(Object object) {
        if (object instanceof String) {
            adapted.getSymbolMap().remove(object);
        }
        if (object instanceof com.tibco.cep.designtime.model.rule.Symbol) {
            adapted.getSymbolMap().remove(((com.tibco.cep.designtime.model.rule.Symbol) object).getName());
        }
        return object;
	}

	/* (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	
	public int size() {
		return adapted.getSymbolList().size();
	}

	/* (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	
	public Collection<com.tibco.cep.designtime.model.rule.Symbol> values() {
		ArrayList<com.tibco.cep.designtime.model.rule.Symbol> list = new ArrayList<com.tibco.cep.designtime.model.rule.Symbol>();
		for(Symbol s: adapted.getSymbolList()) {
			list.add(new SymbolAdapter(s));
		}
		return list;
	}
}
