package com.tibco.cep.studio.core.converter;

import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;

public abstract class CompilableConverter extends EntityConverter {

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		Compilable newCompilable = (Compilable) newEntity;
		com.tibco.cep.designtime.model.rule.Compilable compilable = (com.tibco.cep.designtime.model.rule.Compilable) entity;
		newCompilable.setActionText(compilable.getActionText());
		newCompilable.setConditionText(compilable.getConditionText());
		newCompilable.setCompilationStatus(compilable.getCompilationStatus());
		newCompilable.setReturnType(compilable.getReturnType());
		
		Symbols arguments = compilable.getScope();
		if (newCompilable.getSymbols() == null) {
			newCompilable.setSymbols(RuleFactory.eINSTANCE.createSymbols());
		}

		if (arguments != null && arguments.getSymbolsList() != null) {
			List symbolsList = arguments.getSymbolsList();
			for (Object object : symbolsList) {
				Symbol symbol = (Symbol) object;
				com.tibco.cep.designtime.core.model.rule.Symbol newSymbol = RuleFactory.eINSTANCE.createSymbol();
				newSymbol.setIdName(symbol.getName());
				newSymbol.setType(symbol.getType());
				newSymbol.setTypeExtension(symbol.getTypeExtension());
				//Domain domain = convertDomain(symbol.getDomain());
				//newSymbol.setDomain(domain);
				newCompilable.getSymbols().getSymbolMap().put(newSymbol.getIdName(),newSymbol);
			}
		}

	}

	/*private Domain convertDomain(
			com.tibco.cep.designtime.model.rule.Domain domain) {
		Domain newDomain = RuleFactory.eINSTANCE.createDomain();
//		newDomain.setSymbol(domain.getParent()); // this is the container (?)
		Object[] array = domain.toArray();
		for (Object object : array) {
			DomainEntry entry = (DomainEntry) object;
			com.tibco.cep.designtime.core.model.rule.DomainEntry newEntry = RuleFactory.eINSTANCE.createDomainEntry();
			newEntry.setName(entry.getName());
//			newEntry.setValue(entry.getValue());
			newDomain.getEntries().add(newEntry);
		}
		return newDomain;
	}*/
	
}
