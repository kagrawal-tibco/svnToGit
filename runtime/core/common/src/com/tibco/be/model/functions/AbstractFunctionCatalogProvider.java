package com.tibco.be.model.functions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;

public abstract class AbstractFunctionCatalogProvider implements IFunctionCatalogProvider  {
	public void loadCatalogs(FunctionsCatalog catalog,JavaStaticFunctionsFactory.IFunctionClassLoader functionClassLoader) throws Exception {
		if(catalog == null) 
			return;
		ArrayList<URL> urlList = new ArrayList<URL>();
		String fnCatURLStr = System.getProperty(PROP_FUNCTION_CATALOG_URLS,null);
		if(fnCatURLStr == null || fnCatURLStr.isEmpty()) {
			Enumeration<URL> e = getClass().getClassLoader().getSystemResources("functions.catalog");
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			e = getClass().getClassLoader().getResources("functions.catalog");
			while (e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}
			
		}
        JavaStaticFunctionsFactory.loadFunctionCategoryURLs(catalog, urlList,functionClassLoader);

	}

}
