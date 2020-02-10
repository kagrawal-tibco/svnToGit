package com.tibco.cep.eventstreamprocessing;

import com.tibco.be.model.functions.AbstractFunctionCatalogProvider;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;

/*
@author ssailapp
@date Apr 1, 2010 4:12:04 PM
 */

public class ESPFunctionCatalogProvider extends AbstractFunctionCatalogProvider {

	public ESPFunctionCatalogProvider() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void loadCatalogs(FunctionsCatalog catalog) throws Exception {
		super.loadCatalogs(catalog, new JavaStaticFunctionsFactory.IFunctionClassLoader(){
			@Override
			public Class<?> getClass(String className)
					throws ClassNotFoundException {
				return Class.forName(className);
			}

			@Override
			public ClassLoader getLoader() {
				return ESPFunctionCatalogProvider.this.getClass().getClassLoader();
			}
			
		});
		
	}
}
