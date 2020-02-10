package com.tibco.cep.studio.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.functions.IFunctionCatalogProvider;
import com.tibco.be.model.functions.utils.FunctionHelpBundle;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;

public class FunctionsHelpBundleMananger {

	private HashMap<String, FunctionHelpBundle> fProjectHelpBundles = new HashMap<String, FunctionHelpBundle>();
	private FunctionHelpBundle staticHelpBundle;
	private static final String FUNCTIONS_HELP_FILE = "functions-help.properties";
	private static FunctionsHelpBundleMananger fInstance;
	
	public static synchronized FunctionsHelpBundleMananger getInstance() {
		if (fInstance == null) {
			fInstance = new FunctionsHelpBundleMananger();
		}
		return fInstance;
	}
	
	public void reloadHelpBundle(String projectName) {
		loadHelpBundle(projectName);
	}
	
	public FunctionHelpBundle getHelpBundle(String projectName) {
		if (staticHelpBundle == null) {
			createStaticHelpBundle();
		}
		if (projectName == null) {
			return staticHelpBundle;
		}
		if (staticHelpBundle != null) {
			FunctionHelpBundle helpBundle = fProjectHelpBundles.get(projectName);
			if (helpBundle == null) {
				helpBundle = loadHelpBundle(projectName);
			}
			if(helpBundle != null) {
				return helpBundle;
			}
		}
		return staticHelpBundle;
	}

	private FunctionHelpBundle loadHelpBundle(String projectName) {
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		FunctionHelpBundle helpBundle = null;
		StudioProjectConfiguration configuration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(projectName);
		if (configuration == null) {
			return null;
		}
		EList<CustomFunctionLibEntry> entries = configuration.getCustomFunctionLibEntries();
		Properties p = new Properties();
		for (int i = 0; i < entries.size(); i++) {
			CustomFunctionLibEntry entry = entries.get(i);
			loadFunctionJarProperties(p, entry);
		}
		try {
		    baos = new ByteArrayOutputStream();
		    p.store(baos, ""); //todo Comment?
		    bais = new ByteArrayInputStream(baos.toByteArray());
			helpBundle = new FunctionsInfoHelper(bais);
			fProjectHelpBundles.put(projectName, helpBundle);
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		} finally {

			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
				}
			}

		}
		return helpBundle;
	}

	private void loadFunctionJarProperties(Properties p,
			CustomFunctionLibEntry entry) {
		String path = entry.getPath();
		InputStream stream = null;
		try {
			stream = getHelpPropertiesInputStream(path);
			if(stream != null) p.load(stream);
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					StudioCorePlugin.log(e);
				}
			}
		}
	}

	private InputStream getHelpPropertiesInputStream(String jarPath) {
		File file = new File(jarPath);
		if(file.exists()){
			try {
				JarFile jFile = new JarFile(file);
				Enumeration<JarEntry> en = jFile.entries();
				while(en.hasMoreElements()) {
					JarEntry jEntry = en.nextElement();
					if(jEntry.getName().endsWith("functions-help.properties")){
						return jFile.getInputStream(jEntry);
					}
				}
			} catch (IOException e) {						
				return null;
			}
		}
		return null;
	}

	private void createStaticHelpBundle() {
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = null;
		try {
			Enumeration<URL> resources = FunctionHelpBundle.class.getClassLoader().getResources(FUNCTIONS_HELP_FILE); 
			List<URL> processedURLs = new ArrayList<URL>();
			Properties p = new Properties();
			Stack<URL> urls = new Stack<URL>();
			// BE-5244 : process urls in reverse order, so that hotfix jars are processed after
			// non-hf jars, and thus replace the non-hf properties
			while (resources.hasMoreElements()) {
		        final URL url = resources.nextElement();
		        urls.push(url);
			}
			while (urls.size() > 0) {
				final URL url = urls.pop();
		        if (processedURLs.contains(url)) {
		        	continue;
		        }
		        InputStream is = url.openStream();
		        p.load(is);
		        processedURLs.add(url);
			}

			// add all of the jars registered via the extension point as well
			IFunctionCatalogProvider[] providers = FunctionsCatalogManager.getInstance().getFunctionCatalogProviders();
			for (int i = 0; i < providers.length; i++) {
				resources = providers[i].getClass().getClassLoader().getResources(FUNCTIONS_HELP_FILE); 
				while (resources.hasMoreElements()) {
		            final URL url = resources.nextElement();
		            if (processedURLs.contains(url)) {
		            	continue;
		            }
		            InputStream is = url.openStream();
		            p.load(is);
		            processedURLs.add(url);
				}
			}

		    baos = new ByteArrayOutputStream();
		    p.store(baos, ""); //todo Comment?
		    bais = new ByteArrayInputStream(baos.toByteArray());
			staticHelpBundle = new FunctionsInfoHelper(bais);

		} catch (Exception e) {
			StudioCorePlugin.log(e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
				}
			}
		}
	}

	protected class FunctionsInfoHelper extends FunctionHelpBundle {
		
		protected FunctionsInfoHelper(InputStream stream) throws IOException {
			super(stream);
		}

		@Override
		public String getString(String key) {
			if (this == staticHelpBundle) {
				// avoid infinite loop
				return super.getString(key);
			}
			String s = staticHelpBundle.getString(key);
			if (s == null || s == key) {
				return super.getString(key);
			}
			return s;
		}
		
	}

}
