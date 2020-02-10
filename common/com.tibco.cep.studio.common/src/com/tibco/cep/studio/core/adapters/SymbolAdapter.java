/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import com.tibco.cep.designtime.model.rule.Domain;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;

/**
 * @author aathalye
 *
 */
public class SymbolAdapter implements Symbol, ICacheableAdapter {
	
	protected com.tibco.cep.designtime.core.model.rule.Symbol adapted;
	
	public SymbolAdapter(final com.tibco.cep.designtime.core.model.rule.Symbol adapted) {
		this.adapted = adapted;
	}
	
	public SymbolAdapter(final SymbolAdapter adapter) {
		this.adapted = adapter.getAdapted();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbol#getDomain()
	 */
	
	public Domain getDomain() {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbol#getName()
	 */
	
	public String getName() {
		return adapted.getIdName();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbol#getSymbols()
	 */
	
	public Symbols getSymbols() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbol#getType()
	 */
	
	public String getType() {
		if (null == adapted.getType()) { return ""; }
        int indexOfDot = adapted.getType().indexOf(".");
        if (indexOfDot == -1) return adapted.getType();
        return adapted.getType().substring(0, indexOfDot);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbol#getTypeExtension()
	 */
	
	public String getTypeExtension() {
		if (null == adapted.getType()) { return ""; }
        int indexOfDot = adapted.getType().lastIndexOf(".");
        if (indexOfDot == -1) {
            return "";
        }
        return adapted.getType().substring(indexOfDot);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.rule.Symbol#getTypeWithExtension()
	 */
	
	public String getTypeWithExtension() {
		return "." + getTypeExtension();
	}
	
	public String toString() {
		return adapted.toString();
	}
	
	protected com.tibco.cep.designtime.core.model.rule.Symbol getAdapted() {
		return adapted;
	}

	@Override
	public boolean isArray() {
		return adapted.isArray();
	}
}
