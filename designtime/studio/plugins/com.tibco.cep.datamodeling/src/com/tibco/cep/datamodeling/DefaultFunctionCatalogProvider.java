package com.tibco.cep.datamodeling;

import com.tibco.be.model.functions.AbstractFunctionCatalogProvider;
import com.tibco.be.model.functions.IFunctionCatalogProvider;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;

public class DefaultFunctionCatalogProvider extends
		AbstractFunctionCatalogProvider implements IFunctionCatalogProvider {

	public DefaultFunctionCatalogProvider() {
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
				return DefaultFunctionCatalogProvider.this.getClass().getClassLoader();
			}
			
		});
		
	}

}
