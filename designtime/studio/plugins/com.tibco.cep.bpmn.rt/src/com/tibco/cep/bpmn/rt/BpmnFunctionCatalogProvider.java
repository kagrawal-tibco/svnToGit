package com.tibco.cep.bpmn.rt;

import com.tibco.be.model.functions.AbstractFunctionCatalogProvider;
import com.tibco.be.model.functions.IFunctionCatalogProvider;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;

public class BpmnFunctionCatalogProvider extends AbstractFunctionCatalogProvider implements IFunctionCatalogProvider {

	public BpmnFunctionCatalogProvider() {
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
				return BpmnFunctionCatalogProvider.this.getClass().getClassLoader();
			}
			
		});

	}
}
