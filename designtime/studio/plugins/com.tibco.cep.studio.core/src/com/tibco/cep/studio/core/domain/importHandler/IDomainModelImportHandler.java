package com.tibco.cep.studio.core.domain.importHandler;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;

/**
 * Every domain model import handler should implement this interface.
 * @author aathalye
 *
 * @param <D>
 * @param <O>
 */
public interface IDomainModelImportHandler<O extends Object, D extends IDomainImportSource<O>> {
	
	/**
	 * Import {@link Domain} from external data sources and return the 
	 * generated domain model.
	 * 
	 * @param domainConfiguration
	 * @throws DomainModelImportException if any checked/unchecked exception was encountered during import.
	 */
	void importDomain(DomainConfiguration domainConfiguration) throws DomainModelImportException;
	
	/**
	 * Keep the Domain as an instance in the concrete implementation.
	 * @return {@link Domain} -> New domain model, or the existing one with added dataset.
	 */
	Domain getImportedDomain();
}
