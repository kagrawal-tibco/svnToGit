package com.tibco.be.parser.semantic;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.FunctionsCategoryImpl;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory;
import com.tibco.cep.rt.CoreLibPathProvider;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.build.MutableSchema;

public class JavaCustomFunctionsFactory extends JavaStaticFunctionsFactory {

    public static void loadFunctionCategoriesFromDocument(FunctionsCatalog catalog, XiNode document, JarFile archiveFile, StudioProjectConfiguration configuration,ClassLoader cldr) throws Exception {
    	init();
    	ClassLoader customClassloader = cldr;
    	if(customClassloader == null) {
    		List<URL> jarURLS = new ArrayList<>();
    		URL[] paths = CoreLibPathProvider.getCoreLibPaths(null);
    		jarURLS.addAll(Arrays.asList(paths));
    		EList<ThirdPartyLibEntry> entries = configuration.getThirdpartyLibEntries();
    		for (ThirdPartyLibEntry bpe : entries) {		

    			String filePath = bpe.getPath(bpe.isVar());
    			File file = new File(filePath);
    			if (file.exists()) {
    				jarURLS.add(file.toURI().toURL());
    			}
    			
    		}
    		customClassloader = new URLClassLoader(jarURLS.toArray(new URL[0]));
    	}
    	XiNode catalogNode =  XiChild.getChild(document, ExpandedName.makeName("catalog"));
    	String catalogName = catalogNode.getAttributeStringValue(ExpandedName.makeName("name"));
    	XiNode enabledNode = XiChild.getChild(catalogNode, ExpandedName.makeName("enabled"));
    	if (enabledNode != null) {
    		if (!getIsEnabled(enabledNode)) return;
    	}
    	String treeName = catalogName;// + " Functions";
    	if (catalog.getCatalog(catalogName) == null) {
    		final CatalogRoot javaCatalog = new CatalogRoot(ExpandedName.makeName(catalogName));
    		catalog.register(treeName, javaCatalog);
    		loadCatalog(javaCatalog, catalogNode, catalogName, archiveFile, configuration,customClassloader);
    		javaCatalog.prepare();
    		StudioCorePlugin.getDefault().fireCatalogChangedEvent(javaCatalog);
    	}
    }

    /**
     *
     * @param node
     * @param systemId
     * @param config TODO
     * @param cldr 
     * @throws Exception
     */

    protected static void loadCatalog(FunctionsCategoryImpl parent, XiNode node, String systemId, JarFile archiveFile, StudioProjectConfiguration config, ClassLoader cldr) throws Exception {
    	// Iterate over all the top level categories
    	Iterator itr = XiChild.getIterator(node, ExpandedName.makeName("category"));
    	while (itr.hasNext()) {
    		XiNode n = (XiNode) itr.next();
    		loadCategory(parent, n,systemId, archiveFile, config,cldr);
    	}
    }


    /**
     *
     * @param parentCategory
     * @param node
     * @param systemId
     * @param config TODO
     * @param cldr 
     * @throws Exception
     */
    protected static void loadCategory(FunctionsCategoryImpl parentCategory, XiNode node, String systemId, JarFile archiveFile, StudioProjectConfiguration config, ClassLoader cldr) throws Exception {
    	String categoryName= XiChild.getChild(node,ExpandedName.makeName("name")).getStringValue().trim();
    	XiNode enabledNode = XiChild.getChild(node, ExpandedName.makeName("enabled"));
    	if (enabledNode != null) {
    		if (!getIsEnabled(enabledNode)) return;
    	}
    	ExpandedName expName = null;
    	if (!(parentCategory instanceof CatalogRoot)) {
    		expName = parentCategory.getName();
    	}
    	FunctionsCategoryImpl fc = new FunctionsCategoryImpl(makeName(expName, categoryName));
    	parentCategory.registerSubCategory(fc);
    	Iterator children= node.getChildren();
    	while (children.hasNext()) {
    		XiNode child = (XiNode) children.next();
    		if (child.getName() == null) {
    			continue;
    		}
    		if (child.getName().getLocalName().equalsIgnoreCase("category")) {
    			loadCategory(fc,child,systemId,archiveFile, config,cldr);
    		} else if (child.getName().getLocalName().equalsIgnoreCase("function")) {
    			// load function
    			loadFunction(fc, child, systemId, archiveFile, config,cldr);
    		}
    	}
    }

    public static void loadFunction(FunctionsCategoryImpl parentCategory, XiNode node, String systemId, JarFile archiveFile, StudioProjectConfiguration config, ClassLoader cldr) throws Exception {
        XiNode namedNode = XiChild.getChild(node, ExpandedName.makeName("name"));
        XiNode classNode = XiChild.getChild(node, ExpandedName.makeName("class"));
        XiNode methodNode = XiChild.getChild(node, ExpandedName.makeName("method"));
        XiNode enabledNode = XiChild.getChild(node,ExpandedName.makeName("enabled"));

        String javaMethodName=null;

        if (enabledNode != null) {
            if (!getIsEnabled(enabledNode)) return;
        }

        if (methodNode != null) {
            javaMethodName=methodNode.getStringValue();
        } else {
            javaMethodName=namedNode.getStringValue();
        }

        if ((classNode == null)) return;
        if (namedNode == null) return;

        Class cls = getClass(classNode.getStringValue(), archiveFile, config,cldr);

        Method method = matchMethod(cls,javaMethodName);
        if (method != null) {
            // Check for mapper
            XiNode mapperNode = XiChild.getChild(node, ExpandedName.makeName("mapper"));
            ExpandedName expName = null;
        	if (!(parentCategory instanceof CatalogRoot)) {
        		expName = parentCategory.getName();
        	}
            if (mapperNode == null) {
                JavaStaticFunction function= JavaStaticFunction.loadJavaStaticFunction(makeName(expName, namedNode.getStringValue().trim()), node, method);
                parentCategory.registerPredicate(function);
            } else {
                MutableSchema schema = cf.createSchema();
                schema.setNamespace(cls.getName() + "/" + method.getName());
                JavaStaticFunctionWithXSLT function = JavaStaticFunctionWithXSLT.loadJavaStaticFunctionWithXSLT(factory,schema,makeName(expName,namedNode.getStringValue()), node, method);
                parentCategory.registerPredicate(function);
            }

        }

    }

	private static Class<?> getClass(String className, JarFile archiveFile, StudioProjectConfiguration config, ClassLoader cldr) throws ClassNotFoundException {
		try {
			Class<?> clazz = cldr.loadClass(className);
			if(clazz == null) {
				Class.forName(className);
			}
			if (clazz != null) {
				return clazz;
			}
		} catch (ClassNotFoundException e) {
		}
		return loadClassFromJar(className, archiveFile, config);
	}

	private static Class<?> loadClassFromJar(String className,
			JarFile archiveFile, StudioProjectConfiguration config) throws ClassNotFoundException {
		URLClassLoader clazzLoader ;
		Class<?> clazz = null;
		try {
			List<URL> classpath = StudioProjectConfigurationManager.getInstance().getProjectLibURLs(config, true);
			List<URL> libURLs = new ArrayList<URL>();
			URL url = new File(archiveFile.getName()).toURI().toURL();
			String filePath = "jar:file:" + url.getPath() + "!/";
			URL url2 = new URI(filePath).toURL();
			if(!classpath.contains(url2)) {
				libURLs.add(url2);
			}
			ClassLoader parentClassLoader = StudioProjectConfigurationManager
				.getInstance().getProjectConfigClassloader(config,true);			
			clazzLoader = new URLClassLoader(libURLs.toArray(new URL[libURLs.size()]),parentClassLoader);
			clazz = clazzLoader.loadClass(className);
		} catch (MalformedURLException e) {
			StudioCorePlugin.log(e);
		} catch (URISyntaxException e) {
			StudioCorePlugin.log(e);
		} 
		return clazz;
	}

	

}
