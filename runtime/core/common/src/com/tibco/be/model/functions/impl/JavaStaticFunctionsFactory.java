package com.tibco.be.model.functions.impl;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.utils.FunctionHelpBundle;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.*;
import com.tibco.xml.datamodel.helpers.XiAttribute;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.impl.DefaultComponentFactory;

import org.xml.sax.InputSource;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;

/*
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 6:17:19 PM
 */
public class JavaStaticFunctionsFactory {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    protected static XiParser parser;
    protected static MutableComponentFactoryTNS cf;
    protected static XiFactory factory;
    private static FunctionHelpBundle help;
    private static boolean initialized = false;
    static Logger logger = LogManagerFactory.getLogManager().getLogger(JavaStaticFunctionsFactory.class);
    
    public interface IFunctionClassLoader {
    	Class<?> getClass(String className) throws ClassNotFoundException;
    	ClassLoader getLoader();
    }

    protected static void init() throws Exception {
        if (!initialized) {
            parser = XiParserFactory.newInstance();
            cf = DefaultComponentFactory.getTnsAwareInstance();
            factory = XiFactoryFactory.newInstance();
            help = FunctionHelpBundle.getBundle();
            initialized = true;
        }
    }

    public static void loadFunctionCategories(FunctionsCatalog catalog) throws Exception{
        init();
        ArrayList<URL> urlList = new ArrayList<URL>();
        String fnCatURLStr = System.getProperty(SystemProperty.FUNCTION_CATALOG_URLS.getPropertyName(),null);
        if(fnCatURLStr == null) {
        	Enumeration<URL> e = FunctionsCatalog.class.getClassLoader().getSystemResources("functions.catalog");
        	while (e.hasMoreElements()) {
        		urlList.add(e.nextElement());
        	}
        	e = FunctionsCatalog.class.getClassLoader().getResources("functions.catalog");
        	while (e.hasMoreElements()) {
        		urlList.add(e.nextElement());
        	}
        } else {
        	String []fnCatURLs = fnCatURLStr.split(",");
        	for(String urlString: fnCatURLs) {
        		URL url = FunctionsCatalog.class.getClassLoader().getSystemResource(urlString.trim());
        		if(url != null) {
        			urlList.add(url);
        			continue;
        		} else {
        			url = FunctionsCatalog.class.getClassLoader().getResource(urlString.trim());
        			urlList.add(url);
        			continue;
        		}
        	}
        }
        
        loadFunctionCategoryURLs(catalog, urlList,new IFunctionClassLoader() {
        	@Override
        	public Class<?> getClass(String className) throws ClassNotFoundException {
        		return Class.forName(className);
        	}

			@Override
			public ClassLoader getLoader() {
				return Class.class.getClassLoader();
			}
        	
        	
        });
    }

	/**
	 * This is the main entry point for designtime and runtime function catalog
	 * @param catalog
	 * @param urlList
	 * @param functionClassLoader
	 * @throws Exception
	 */
	public static void loadFunctionCategoryURLs(FunctionsCatalog catalog,
			List<URL> urlList, IFunctionClassLoader functionClassLoader) throws Exception {
		init();
		final Set<String> loadedFiles = new HashSet<String>();
        for (Iterator<URL> iter = urlList.iterator(); iter.hasNext();) {
            final URL url = iter.next();
            InputStream is = url.openStream();
            // System.out.println("Loading function catalog from :" + url);
            XiNode document = parser.parse(new InputSource(is));

            final String catalogName = XiChild.getChild(document, ExpandedName.makeName("catalog")).getAttributeStringValue(ExpandedName.makeName("name"));
            if (loadedFiles.contains(catalogName)) {
                logger.log(Level.DEBUG, "Ignoring :" + url.getFile() + " already loaded from a different location");
            } else {
                loadedFiles.add(catalogName);
                try {
                    loadFunctionCategoriesFromDocument(catalog, document,functionClassLoader);
                } catch (Exception ex) {
                	if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
                        logger.log(Level.DEBUG, ex, "Exception while loading function catalog '%s'.", catalogName);
                        logger.log(Level.WARN,
                                "Failed to fully load function category '%s', moving on to next catalog.", catalogName);
                	} else {
                		throw ex;
                	}
                } catch (LinkageError ex) {
                	if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
                        logger.log(Level.DEBUG, ex, "Exception while loading function catalog '%s'.", catalogName);
                        logger.log(Level.WARN,
                                "Failed to fully load function category '%s', moving on to next catalog.", catalogName);
                	} else  {
                		throw ex;	
                	}
                }
            }
        }
	}

    public static void loadFunctionCategoriesFromDocument(FunctionsCatalog catalog, XiNode document, IFunctionClassLoader functionClassLoader) throws Exception {
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
                loadCatalog(javaCatalog, catalogNode,functionClassLoader);
                javaCatalog.prepare();
            }
    }

    /**
     *
     * @param node
     * @param functionClassLoader 
     * @throws Exception
     */

    protected static void loadCatalog(FunctionsCategoryImpl catalog, XiNode node, IFunctionClassLoader functionClassLoader) throws Exception {
        // Iterate over all the top level categories
        Iterator itr = XiChild.getIterator(node, ExpandedName.makeName("category"));
        while (itr.hasNext()) {
            XiNode n = (XiNode) itr.next();
            FunctionsCategory category = null;
            try {
            	category = loadCategory(null, n,functionClassLoader);
        	} catch (LinkageError ex) {
                String categoryName= XiChild.getChild(n,ExpandedName.makeName("name")).getStringValue().trim();
            	if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
            		logger.log(Level.DEBUG, ex, "Exception while loading function category '%s'.", categoryName);
                    logger.log(Level.WARN,
                            "Failed to fully load function category '%s', moving on to next category.", categoryName);
            	}
			}
            if(category != null) {
                catalog.registerSubCategory(category);
            }
        }
    }


    protected static ExpandedName makeName (ExpandedName parentName, String name) {
        if (parentName != null) {
            if (parentName.getNamespaceURI() != null)
                return ExpandedName.makeName(parentName.getNamespaceURI() + "." + parentName.getLocalName(),name);
            else
                return ExpandedName.makeName(parentName.getLocalName(),name);
        } else {
            return ExpandedName.makeName(name);
        }
    }
    /**
     *
     * @param parentName
     * @param node
     * @param functionClassLoader 

     * @throws Exception
     */
    protected static FunctionsCategory loadCategory(ExpandedName parentName, XiNode node, IFunctionClassLoader functionClassLoader) throws Exception {
        String categoryName= XiChild.getChild(node,ExpandedName.makeName("name")).getStringValue().trim();
        XiNode enabledNode = XiChild.getChild(node, ExpandedName.makeName("enabled"));
        if (enabledNode != null) {
            if (!getIsEnabled(enabledNode)) return null;
        }
        FunctionsCategoryImpl fc = new FunctionsCategoryImpl(makeName(parentName, categoryName));
        Iterator children= node.getChildren();
        while (children.hasNext()) {
            XiNode child = (XiNode) children.next();
            if (child.getName() == null) {
                continue;
            }
            if (child.getName().getLocalName().equalsIgnoreCase("category")) {
                FunctionsCategory childCategory = loadCategory(fc.getName(),child,functionClassLoader);
                if(childCategory != null) {
                    fc.registerSubCategory(childCategory);
                }
            } else if (child.getName().getLocalName().equalsIgnoreCase("function")) {
                // load function
//            	try {
            		loadFunction(fc, child,functionClassLoader);
//				} catch (LinkageError ex) {
//                	if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
//                		logger.log(Level.ERROR, "Failed to fully load function category '" + categoryName
//                				+ "', moving on to next category.");
//                		ex.printStackTrace();
//                	} else {
//                		logger.log(Level.ERROR, "Failed to fully load function category '" + categoryName
//                				+ "', moving on to next category.");
//                	}
//				}
            }
        }
        return fc;
    }

    protected static boolean getIsEnabled(XiNode enabledNode) {
        String nv = enabledNode.getStringValue().trim();
        String propertyName = XiAttribute.getStringValue(enabledNode, "system-property");
        if (propertyName != null) {
            String pv = System.getProperty(propertyName.trim(), nv);
            return Boolean.valueOf(pv).booleanValue();
        }
        else {
            return Boolean.valueOf(nv).booleanValue();
        }


    }


    protected static void loadFunction(FunctionsCategoryImpl parentCategory, XiNode node, IFunctionClassLoader functionClassLoader) throws Exception {
        XiNode namedNode = XiChild.getChild(node, ExpandedName.makeName("name"));
        XiNode classNode = XiChild.getChild(node, ExpandedName.makeName("class"));
        XiNode methodNode = XiChild.getChild(node, ExpandedName.makeName("method"));
        XiNode enabledNode = XiChild.getChild(node,ExpandedName.makeName("enabled"));

        String javaMethodName=null;

        if (enabledNode != null) {
            if (!getIsEnabled(enabledNode)) return;
        }

        if (methodNode != null) {
            javaMethodName=methodNode.getStringValue().trim();
        } else {
            javaMethodName=namedNode.getStringValue().trim();
        }

        if ((classNode == null)) return;
        if (namedNode == null) return;

        //Class cls = Class.forName(classNode.getStringValue());
        Class cls = functionClassLoader.getClass(classNode.getStringValue().trim());

        Method method = matchMethod(cls,javaMethodName);
        if (method != null) {
            // Check for mapper
            XiNode mapperNode = XiChild.getChild(node, ExpandedName.makeName("mapper"));
            if (mapperNode == null) {
                JavaStaticFunction function= JavaStaticFunction.loadJavaStaticFunction(makeName(parentCategory.getName(), namedNode.getStringValue().trim()), node, method);
                parentCategory.registerPredicate(function);
            } else {
                MutableSchema schema = cf.createSchema();
                schema.setNamespace(cls.getName() + "/" + method.getName());
                JavaStaticFunctionWithXSLT function = JavaStaticFunctionWithXSLT.loadJavaStaticFunctionWithXSLT(factory,schema,makeName(parentCategory.getName(),namedNode.getStringValue()), node, method);
                parentCategory.registerPredicate(function);
            }

        }

    }
    
    protected static Class<?> getClass(String className) throws ClassNotFoundException {
    	return Class.forName(className);
    }

    protected static Method matchMethod(Class clz, String methodName) throws Exception {
    	if (clz == null) {
    		return null;
    	}
        Method ms[] = clz.getDeclaredMethods();
        int incr=0;

        for (int i=0; i < ms.length; i++) {
            Method m = ms[i];
            int mod = m.getModifiers();
            boolean isPublic = Modifier.isPublic(mod);
            boolean isStatic = Modifier.isStatic(mod);

            if (!(isPublic && isStatic)) continue;
            String name = m.getName();

            if (name.equals(methodName.trim())) {
                return m;
                //addFunction(new Function(this,cls, m, name, n, schema));
                //++incr;
            }
        }
        return null;
    }

    /**
     *
     * @param function
     * @return
     */
    public final static String tooltip(JavaStaticFunction function) {
        String s = function.getToolTip();
        if (null==s) return help.tooltip(function);
        return s;
    }
    /**
     *
     * @param function
     * @return
     */
    public final static String signature(JavaStaticFunction function) {
        String s = function.getSignature();
        if (null==s) return "";
        //if(null == s) help.tooltip(function);
        return s;
    }
    
    /**
    *
    * @param function
    * @return
    */
   public final static String signatureFormat(JavaStaticFunction function) {
       String s = function.getSignatureFormat();
       if (null==s) return "";
       //if(null == s) help.tooltip(function);
       return s;
   }

    /**
     *
     * @param args
     */
    public static void main (String args[]) {

        try {
            FunctionsCatalog catalog = FunctionsCatalog.getINSTANCE();
            JavaStaticFunctionsFactory.loadFunctionCategories(catalog);
            System.out.println("Lookup Result=" + catalog.lookup(args[0], true));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class CatalogRoot extends FunctionsCategoryImpl {
        /**
         * @param categoryName
         */
        public CatalogRoot(ExpandedName categoryName) {
            super(categoryName);
        }


        /**
         * @param categoryName
         * @param allowSubCategories
         * @param allowPredicates
         */
        public CatalogRoot(
                ExpandedName categoryName,
                boolean allowSubCategories,
                boolean allowPredicates) {
            super(categoryName, allowSubCategories, allowPredicates);
        }
    }


}
