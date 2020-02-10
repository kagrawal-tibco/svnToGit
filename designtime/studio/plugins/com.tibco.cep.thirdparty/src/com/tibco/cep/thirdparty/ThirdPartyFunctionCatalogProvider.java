package com.tibco.cep.thirdparty;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
// uncomment in luna
//import org.eclipse.osgi.container.ModuleLoader;
//import org.eclipse.osgi.internal.framework.EquinoxBundle;
import org.osgi.framework.Bundle;

import com.tibco.be.model.functions.IFunctionCatalogProvider;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;
import com.tibco.cep.rt.Activator;

public class ThirdPartyFunctionCatalogProvider  implements IFunctionCatalogProvider {
	public ThirdPartyFunctionCatalogProvider() {		
	}
	
	@Override
	public void loadCatalogs(FunctionsCatalog catalog) throws Exception {
		final Bundle tpdelegate = DelegatePlugin.getDefault().getDelegateBundle();
		if(catalog == null) 
			return;
		if(tpdelegate == null){
			IProduct product = Platform.getProduct();
			if (product != null && !product.getId().contains("streambase")) {
				// don't log this error in a streambase shared context
				DelegatePlugin.logErrorMessage("Failed to initialize third-party delegate plugin.");
			}
			return;
		}
		ArrayList<URL> urlList = new ArrayList<URL>();
		String fnCatURLStr = System.getProperty(PROP_FUNCTION_CATALOG_URLS,null);
		if(fnCatURLStr == null) {
			Enumeration<URL> e = tpdelegate.getResources("functions.catalog");
			while (e != null && e.hasMoreElements()) {
				urlList.add(e.nextElement());
			}	        	
		} else {
			String []fnCatURLs = fnCatURLStr.split(",");
			for(String urlString: fnCatURLs) {
				if(!urlString.isEmpty()) {
					URL url = tpdelegate.getResource(urlString);
					if(url != null) {
						urlList.add(url);
						continue;
					} 
				}
			}
		}
		if(urlList.isEmpty())
			return;
		JavaStaticFunctionsFactory.loadFunctionCategoryURLs(catalog, urlList,new JavaStaticFunctionsFactory.IFunctionClassLoader(){
			@Override
			public Class<?> getClass(String className)
					throws ClassNotFoundException {
				try {
					return tpdelegate.loadClass(className.trim());
					//					return Class.forName(className);
				} catch (ClassNotFoundException e) {
					Activator.log(e);
					throw e;
				}
			}

			@Override
			public ClassLoader getLoader() {
				//uncomment in luna
//				ModuleLoader mldr = ((EquinoxBundle)tpdelegate).getModule().getCurrentRevision().getWiring().getModuleLoader();
//				try {
//					Method mthd = ModuleLoader.class.getMethod("getModuleClassLoader", null);
//					if(mthd != null) {
//						return (ClassLoader) mthd.invoke(mldr, null);
//					}
//				} catch (Exception e) {
//					Activator.log(e);
//				} 
//				return null;
				return tpdelegate.getClass().getClassLoader();
			}
			
		});
	}
}
