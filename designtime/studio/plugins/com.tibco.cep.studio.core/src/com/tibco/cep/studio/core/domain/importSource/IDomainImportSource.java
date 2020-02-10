package com.tibco.cep.studio.core.domain.importSource;

/**
 * Represent a source from where to import data
 * @author aathalye
 *
 */
public interface IDomainImportSource<O extends Object> {
	
	/**
	 * The actual source of data.
	 * <p>
	 * <b>
	 * e.g: A DB Instance/data file/XL file/XML file etc.
	 * </b>
	 * </p>
	 * @return
	 */
	O getSource();
	
	/**
	 * Optional parameters describing additional information
	 * or configuration details for the source.
	 * <p>
	 * <b>
	 * e.g: DB connection parameters.
	 * </b>
	 * </p>
	 * @return
	 */
	Object[] getParams();
}
