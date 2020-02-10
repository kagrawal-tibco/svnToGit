package com.tibco.be.model.functions;

import com.tibco.be.model.functions.impl.FunctionsCatalog;

public interface IFunctionCatalogProvider {
	public static final String PROP_FUNCTION_CATALOG_URLS = "be.function.catalog.urls"; //$NON-NLS-1$

	/**
	 * load function.catalog from the local plugin classloader to the existing catalog
	 * @param catalog
	 * @throws Exception 
	 */
	void loadCatalogs(FunctionsCatalog catalog) throws Exception;
	
}
