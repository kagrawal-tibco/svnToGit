/**
 * 
 */
package com.tibco.cep.decisionproject.acl;

import com.tibco.be.parser.semantic.RLSymbolTable;
import com.tibco.cep.decision.table.language.DTValidator;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.Compilable;

/**
 * @author aathalye
 *
 */
public class AuthzCompiler extends DTValidator {

	private AuthzCompiler(final Compilable comp, 
			              final Ontology o) {
		super(comp, o);
	}
	
	public static <I extends Implementation> AuthzCompiler newAuthzCompiler(final Ontology o, final I table) {
		if(o == null || table == null) return null;
		Compilable c = compilableForImpl(table, o);
		if(c == null) return null;
		return new AuthzCompiler(c, o);
	}
	
	public RLSymbolTable getSymbolTable() {
		return this.symbolTable;
	}
}
