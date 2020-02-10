/**
 * 
 */
package com.tibco.cep.studio.core.domain.importHandler;

import java.util.List;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.studio.core.domain.importSource.IDomainImportSource;
import com.tibco.cep.studio.core.validation.ValidationError;

/**
 * All {@link IDomainModelImportHandler}s should extend this class
 * @author aathalye
 *
 */
public abstract class AbstractDomainModelImportHandler<O extends Object, D extends IDomainImportSource<O>> implements IDomainModelImportHandler<O, D> {
	
	protected D domainImportDataSource;
	
	protected List<ValidationError> validationErrors;
	
	/**
	 * The model object
	 */
	protected Domain domain;
	
	/**
	 * The data type for the domain model
	 */
	protected DOMAIN_DATA_TYPES dataType;
	
	/**
	 * 
	 * @param domainImportDataSource
	 * @param validationErrors
	 */
	public AbstractDomainModelImportHandler(D domainImportDataSource,
			                                List<ValidationError> validationErrors) {
		this.domainImportDataSource = domainImportDataSource;
		this.validationErrors = validationErrors;
	}
	
	/**
	 * This is to avoid duplicate entries
	 * @param entries
	 * @param domainEntry
	 * @return
	 */
	protected boolean contains(List<DomainEntry> entries,
			                   DomainEntry domainEntry) {
		for (DomainEntry entry : entries) {
			if (entry.equals(domainEntry)) {
				return true;
			}
		}
		return false;
	}
	
	protected void createDomainModel() {
		if (domain == null) {
			domain = DomainFactory.eINSTANCE.createDomain();
		}
		//Check what type of domain entry it is
	}
	
	public Domain getImportedDomain() {
		return domain;
	}
}
