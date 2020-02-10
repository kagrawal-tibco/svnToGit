package com.tibco.be.model.functions.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionsCatalogVisitor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 2, 2004
 * Time: 6:24:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionsCategoryImpl implements FunctionsCategory {

    ExpandedName categoryName;
    ArrayList<Object>   m_nodes = new ArrayList<Object>();
    HashMap<String,FunctionsCategory>     m_catIndex = new HashMap<String,FunctionsCategory>();
    HashMap<String,Predicate>     m_predIndex = new HashMap<String,Predicate>();


    boolean allowSubCategories=true, allowPredicates=true;

    /**
     *
     * @param categoryName
     */
    public FunctionsCategoryImpl(ExpandedName categoryName) {
        this.categoryName=categoryName;
    }

    /**
     *
     * @param categoryName
     * @param allowSubCategories
     * @param allowPredicates
     */
    public FunctionsCategoryImpl(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates) {
        this.categoryName=categoryName;
        this.allowSubCategories=allowSubCategories;
        this.allowPredicates=allowPredicates;
    }

    /**
     *
     * @return
     */
    public ExpandedName getName() {
        return categoryName;
    }

    /**
     *
     * @param predicate
     * @throws Exception
     */
    public void registerPredicate(Predicate predicate) throws Exception {
        Object foundOne= m_predIndex.remove(predicate.getName().getLocalName());
        if (foundOne != null) {
            m_nodes.remove(foundOne);
        }
        m_nodes.add(predicate);
        m_predIndex.put(predicate.getName().getLocalName(), predicate);
    }

    /**
     *
     * @param functionCategory
     * @throws Exception
     */
    public void registerSubCategory(FunctionsCategory functionCategory) throws Exception {
        m_nodes.add(functionCategory);
        m_catIndex.put(functionCategory.getName().getLocalName(), functionCategory);
    }

    /**
     *
     * @param predicate
     * @param deep
     * @throws Exception
     */
    public void deregisterPredicate(Predicate predicate, boolean deep) throws Exception {
        Object foundOne= m_predIndex.remove(predicate.getName().getLocalName());
        if (foundOne != null) {
            m_nodes.remove(foundOne);
        }
    }

    /**
     *
     * @param functionCategory
     * @param deep
     * @throws Exception
     */
    public void deregisterSubCategory(FunctionsCategory functionCategory, boolean deep) throws Exception {
        Object foundOne= m_catIndex.remove(functionCategory.getName().getLocalName());
        if (foundOne != null) {
            m_nodes.remove(foundOne);
        }
    }

    /**
     *
     * @param functionsCategoryName
     * @return
     * @throws Exception
     */
    public FunctionsCategory getSubCategory(ExpandedName functionsCategoryName) throws Exception {
        return (FunctionsCategory) m_catIndex.get(functionsCategoryName.getLocalName());
    }

    public Predicate getPredicate(ExpandedName predicateName, boolean deep) throws Exception {
        return (Predicate) m_predIndex.get(predicateName.getLocalName());
    }

    /**
     *
     * @return
     */
    public Iterator<?> all() {
        return m_nodes.iterator();
    }

    public Iterator<FunctionsCategory> allSubCategories() {
        return m_catIndex.values().iterator();
    }

    public Iterator<Predicate> allFunctions() {
        return m_predIndex.values().iterator();
    }

    public int size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void prepare() {
        Collections.sort(m_nodes, new Comparator() {
            public int compare(Object o1, Object o2) {
                if ((o1 instanceof Predicate) && (o2 instanceof Predicate)) {
                    return ((Predicate)o1).getName().getLocalName().compareToIgnoreCase(((Predicate)o2).getName().getLocalName());
                } else if ((o1 instanceof Predicate) && (o2 instanceof FunctionsCategory)) {
                    return 1;
                } else if ((o2 instanceof Predicate) && (o1 instanceof FunctionsCategory)) {
                    return -1;
                } else {
                    return ((FunctionsCategory)o1).getName().getLocalName().compareToIgnoreCase(((FunctionsCategory)o2).getName().getLocalName());
                }
            }
        });
        Iterator allSubs= allSubCategories();
        while (allSubs.hasNext()) {
            FunctionsCategory it = (FunctionsCategory) allSubs.next();
            it.prepare();
        }
    }
    public String toString() {
        if (categoryName != null) {
            if (categoryName.getNamespaceURI() != null) {
                return categoryName.getNamespaceURI() + "." + categoryName.getLocalName();
            }
            else {
                return categoryName.getLocalName();
            }
        } else {
            return "[Root Category]";
        }
    }

    /**
     *
     * @param name
     * @return
     */
    Object lookupEither(String name) {
        Object o= m_predIndex.get(name);
        if (o == null) {
            return m_catIndex.get(name);
        } else {
            return o;
        }
    }
    /**
     *
     * @param functionName
     */
    public Object lookup (String functionName, boolean unqualifiedSearch) {
//        System.out.println("Looking Up Function " + functionName);
        if ((functionName == null) || functionName.trim().length() <= 0) {
            return null;
        }
    
        String names[] = functionName.split("\\(|\\)|\\.");
        if (names.length == 0) {
            return null;
        }
        else if (names.length == 1) {
            if (unqualifiedSearch) {
                List matches= find(functionName, true, true);
                if (matches.size() <= 0) {
                    return null;
                } else {
                    if (matches.size() == 1) {
                        return matches.get(0);
                    } else {
                        return matches;
                    }
                }
            } else {
                return lookupEither(names[0]);
            }

        } else {
            Object cat= m_catIndex.get(names[0]);
            if (cat instanceof FunctionsCategory) {
                StringBuffer sb= new StringBuffer(10);
                sb.append(names[1]);
                for (int i=2;i< names.length; i++) {
                    sb.append("." + names[i]);
                }
                return ((FunctionsCategory) cat).lookup(sb.toString(), unqualifiedSearch);
            } else {
                return null;
            }
        }
    }


    /**
     *
     * @param singleName
     * @param matchAll
     * @return
     */
    List<Predicate> find (String singleName, boolean matchAll, boolean searchMe) {
        List<Predicate> matchedPredicates = new ArrayList<Predicate>();

        if (searchMe) {
            Predicate o=m_predIndex.get(singleName);
            if (o != null) {
                matchedPredicates.add(o);
            }
        }
        Iterator<FunctionsCategory> allSubCategories = allSubCategories();
        while (allSubCategories.hasNext()) {
            FunctionsCategoryImpl fc = (FunctionsCategoryImpl)allSubCategories.next();
            List<Predicate> cl= fc.find(singleName, matchAll, true);
            matchedPredicates.addAll(cl);
        }
        return matchedPredicates;
    }


    /**
     *
     */
    public void clear() {
        m_nodes.clear();
        m_catIndex.clear();
        m_predIndex.clear();
    }
    
    @Override
    public void accept(FunctionsCatalogVisitor visitor) {
    	if(visitor.visit(this)){
			for(Iterator<FunctionsCategory> iter = allSubCategories();iter.hasNext();) {
				FunctionsCategory subCat = iter.next();
				subCat.accept(visitor);				
			}
		} 
    }
}
