package com.tibco.cep.studio.dashboard.rt;

import com.tibco.be.model.functions.AbstractFunctionCatalogProvider;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;

public class ViewsFunctionCatalogProvider extends AbstractFunctionCatalogProvider {

	public ViewsFunctionCatalogProvider() {
	}

	@Override
	public void loadCatalogs(FunctionsCatalog catalog) throws Exception {
		super.loadCatalogs(catalog, new JavaStaticFunctionsFactory.IFunctionClassLoader() {
			
			@Override
			public Class<?> getClass(String className) throws ClassNotFoundException {
				return Class.forName(className);
			}

			@Override
			public ClassLoader getLoader() {
				// TODO Auto-generated method stub
				return ViewsFunctionCatalogProvider.this.getClass().getClassLoader();
			}
			
			
		});
	}

}
