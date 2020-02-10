package com.tibco.cep.rt.functions;

import com.tibco.be.model.functions.AbstractFunctionCatalogProvider;
import com.tibco.be.model.functions.IFunctionCatalogProvider;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;
import com.tibco.be.model.functions.impl.ModelFunctionsFactory;
import com.tibco.cep.rt.Activator;

public class DefaultFunctionCatalogProvider extends
		AbstractFunctionCatalogProvider implements IFunctionCatalogProvider {

	public DefaultFunctionCatalogProvider() {		
	}
	
	@Override
	public void loadCatalogs(FunctionsCatalog catalog) throws Exception {
		
		ModelFunctionsFactory.getINSTANCE().loadModelFunctions(catalog);
		super.loadCatalogs(catalog, new JavaStaticFunctionsFactory.IFunctionClassLoader(){
			@Override
			public Class<?> getClass(String className)
					throws ClassNotFoundException {
				try {
					return Class.forName(className);
				} catch (ClassNotFoundException e) {
					Activator.log(e);
					throw e;
				}
			}

			@Override
			public ClassLoader getLoader() {
				return DefaultFunctionCatalogProvider.this.getClass().getClassLoader();
			}
			
		});
		
	}

}
