package com.tibco.be.model.functions.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.FunctionsCategoryChangeListener;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 3, 2004
 * Time: 2:40:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionsCatalog {
    HashMap<String,FunctionsCategory> catalogs = new LinkedHashMap<String,FunctionsCategory>();
    HashMap<FunctionsCategory,List<FunctionsCategoryChangeListener>> catalogListeners 
    						= new HashMap<FunctionsCategory,List<FunctionsCategoryChangeListener>>();

    /**
     *
     */
    public FunctionsCatalog () {
    }

    /**
     *
     * @param top
     */
    public void register(String catalogName, FunctionsCategory top) {
    	synchronized (catalogs) {
    		catalogs.put(catalogName, top);
		}
    }

    public boolean deregister(String catalogName) {
    	synchronized (catalogs) {
    		return catalogs.remove(catalogName) != null;
		}
    }
    

    /**
     *
     * @return
     */
    public Iterator<String> catalogNames () {
    	synchronized (catalogs) {
    		return catalogs.keySet().iterator();
		}
    }

    /**
     *
     * @return
     */
    public Iterator<FunctionsCategory> catalogs () {
    	synchronized (catalogs) {
    		return catalogs.values().iterator();
		}
    }

    /**
     *
     * @param key
     * @return
     */

    public FunctionsCategory getCatalog(String key) {
    	synchronized (catalogs){
    		return (FunctionsCategory) catalogs.get(key);
    	}
    }
    /**
     *
     * @param functionName
     * @return
     */
    public Object lookup (String functionName, boolean doUnQualifiedSearch) {
        List<Object> ret= new ArrayList<Object>();
        synchronized (catalogs) {
        	Iterator<FunctionsCategory> all = catalogs.values().iterator();
        	
        	while (all.hasNext()) {
        		
        		FunctionsCategory cat= all.next();
        		Object retCat=cat.lookup(functionName, doUnQualifiedSearch);
        		if (retCat != null) {
        			if (!doUnQualifiedSearch) {
        				return retCat;
        			} else {
        				if (retCat instanceof Collection) {
        					ret.addAll((Collection) retCat);
        				} else {
        					ret.add(retCat);
        				}
        			}
        		}
        	}
        	if (ret.size() == 0) {
        		return null;
        	}
        	if (ret.size() == 1) {
        		return ret.get(0);
        	}
        	return ret;
		}
    }

    public int size() {
        return catalogs.size();
    }

    /**
     *
     * @param category
     * @param listener
     */
    public void addCatalogChangeListener(FunctionsCategory category, FunctionsCategoryChangeListener listener) {
    	synchronized (catalogListeners) {
    		List<FunctionsCategoryChangeListener> lstnrs= catalogListeners.get(category);
    		if (lstnrs == null) {
    			lstnrs = new ArrayList<FunctionsCategoryChangeListener>();
    			catalogListeners.put(category, lstnrs);
    		}
    		lstnrs.add(listener);
		}

    }

    /**
     *
     * @param category
     */
    public void catalogChanged(FunctionsCategory category) {
    	synchronized (catalogListeners) {
    		List<FunctionsCategoryChangeListener> lstnrs = catalogListeners.get(category);
    		if (lstnrs != null) {
    			for(FunctionsCategoryChangeListener lstnr:lstnrs){
    				lstnr.onCategoryChange(category);
    			}
    			
    		}
		}
    }
    /**
     *
     * @return
     * @throws Exception
     */
    public synchronized static FunctionsCatalog getINSTANCE () throws Exception{
        if (INSTANCE == null) {
        	// prevent concurrent initialization
        	synchronized(FunctionsCatalog.class){
                if (INSTANCE == null) {
                    final FunctionsCatalog catalog = new FunctionsCatalog();
                    JavaStaticFunctionsFactory.loadFunctionCategories(catalog);
                    JavaAnnotationLookup.lookupCatalog(catalog, false);
                    ModelFunctionsFactory.getINSTANCE().loadModelFunctions(catalog);
                    INSTANCE = catalog;
                }
        	}

        }
        return INSTANCE;
    }
    /**
     *
     */
    static FunctionsCatalog INSTANCE;
}
