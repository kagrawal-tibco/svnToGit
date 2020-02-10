/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Symbols</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.SymbolsImpl#getSymbolList <em>Symbol List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymbolsImpl extends EObjectImpl implements Symbols {
	/**
	 * The cached value of the '{@link #getSymbolList() <em>Symbol List</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolList()
	 * @generated
	 * @ordered
	 */
	protected EList<Symbol> symbolList;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbolsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.SYMBOLS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Symbol> getSymbolList() {
		if (symbolList == null) {
			symbolList = new EObjectContainmentEList<Symbol>(Symbol.class, this, RulePackage.SYMBOLS__SYMBOL_LIST);
		}
		return symbolList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<String, Symbol> getSymbolMap() {
		Map<String,Symbol> symbolMap = new Map<String,Symbol>() {

			@Override
			public void clear() {
				getSymbolList().clear();
				
			}

			@Override
			public boolean containsKey(Object key) {
				for(Symbol s: getSymbolList()) {
					if(s.getIdName().equals(key)){
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean containsValue(Object value) {
				if(value instanceof Symbol) {
					for(Symbol s:getSymbolList()) {
						if(s.equals(value)) {
							return true;
						}
					}
				} 
				return false;
			}

			@Override
			public Set<Map.Entry<String, Symbol>> entrySet() {
				LinkedHashSet set = new LinkedHashSet();
				for(Symbol s:getSymbolList()) {
					final Symbol sym = s;
					set.add(new Map.Entry<String,Symbol>() {

						@Override
						public String getKey() {
							return sym.getIdName();
						}

						@Override
						public Symbol getValue() {
							return sym;
						}

						@Override
						public Symbol setValue(Symbol value) {
							throw new UnsupportedOperationException();
						}
						
					});
				}
				return set;
			}

			@Override
			public Symbol get(Object key) {
				if(key instanceof String) {
					for(Symbol s: getSymbolList()) {
						if(s.getIdName().equals((String)key)) {
							return s;
						}
					}
				}
				return null;
			}

			@Override
			public boolean isEmpty() {				
				return getSymbolList().size() == 0;
			}

			@Override
			public Set keySet() {
				LinkedHashSet set = new LinkedHashSet();
				for(Symbol s:getSymbolList()) {
					set.add(s.getIdName());
				}
				return set;
			}

			@Override
			public int size() {
				return getSymbolList().size();
			}

			@Override
			public Collection<Symbol> values() {
				return getSymbolList();
			}

			@Override
			public Symbol put(String key, Symbol value) {
				Symbol added = null;
				if(getSymbolList().add(value)) {
					added = value;
				}
				return added;
			}

			@Override
			public void putAll(Map<? extends String, ? extends Symbol> t) {
				for(Symbol s: t.values()) {
					getSymbolList().add(s);
				}				
			}

			@Override
			public Symbol remove(Object key) {
				Symbol removed=null;
				if(key instanceof String) {
					for(Symbol s: getSymbolList()) {
						if(s.getIdName().equals(key)){
							if(getSymbolList().remove(s)) {
								removed = s;
							}
						}
					}
				}
				return removed;
			}
			
		};
		return symbolMap;
	}

	


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulePackage.SYMBOLS__SYMBOL_LIST:
				return ((InternalEList<?>)getSymbolList()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulePackage.SYMBOLS__SYMBOL_LIST:
				return getSymbolList();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RulePackage.SYMBOLS__SYMBOL_LIST:
				getSymbolList().clear();
				getSymbolList().addAll((Collection<? extends Symbol>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RulePackage.SYMBOLS__SYMBOL_LIST:
				getSymbolList().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RulePackage.SYMBOLS__SYMBOL_LIST:
				return symbolList != null && !symbolList.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SymbolsImpl
