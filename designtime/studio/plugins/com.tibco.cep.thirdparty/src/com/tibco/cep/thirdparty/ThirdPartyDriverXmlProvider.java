package com.tibco.cep.thirdparty;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.tibco.cep.tpcl.IDriverXmlProvider;

public class ThirdPartyDriverXmlProvider implements IDriverXmlProvider {

	public ThirdPartyDriverXmlProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<URL> getDriverXML() throws IOException {
		ArrayList<URL> urlList = new ArrayList<URL>();
 		final Bundle tpdelegate = DelegatePlugin.getDefault().getDelegateBundle();
		if(tpdelegate == null){
			IProduct product = Platform.getProduct();
			if (product != null && !product.getId().contains("streambase")) {
				// don't log this error in a streambase shared context
				DelegatePlugin.logErrorMessage("Failed to initialize third-party delegate plugin.");
			}
			return urlList;
		}
		
		Enumeration<URL> e = tpdelegate.getResources(DRIVERS_XML);
		while (e != null && e.hasMoreElements()) {
			urlList.add(e.nextElement());
		}
		return urlList;
	}

}
