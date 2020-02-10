package com.tibco.cep.query.exec.codegen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 6, 2007
 * Time: 6:03:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryTemplateRegistry {

    private static final Pattern PATTERN_FOR_GROUP_NAME = Pattern.compile("\\s*group\\s+(\\w+)\\s*;\\s*.*", Pattern.MULTILINE);

    private static QueryTemplateRegistry  instance;
    private static Map templateMap;
    private static Map templateCache;


    private QueryTemplateRegistry() {
        templateMap = Collections.synchronizedMap(new HashMap());
        templateCache = Collections.synchronizedMap(new TemplateCache());
    }

    public static QueryTemplateRegistry getInstance() {
       if(null == instance) {
           instance = new QueryTemplateRegistry();
       }
       return instance;
    }

    public void loadTemplates(String templateFileName) throws Exception {
        final Set loadedFiles = new HashSet();
        Set urls = new HashSet();
        Enumeration e = getClass().getClassLoader().getResources(templateFileName);
        while (e.hasMoreElements()) {
            urls.add(e.nextElement());
        }
        e = getClass().getClassLoader().getSystemResources(templateFileName);
        while (e.hasMoreElements()) {
            urls.add(e.nextElement());
        }

        for (Iterator iter = urls.iterator(); iter.hasNext();) {
            final URL url = (URL)iter.next();
            InputStream is = url.openStream();
            System.out.println("Loading template group from :" + url);
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String firstLine = rd.readLine();
            String groupName = getValidGroupName(firstLine);
            is.close();
            if ((null == groupName) || templateMap.containsKey(groupName)) {
                continue;
            }
            is = url.openStream();
            StringTemplateGroup template = loadStringTemplateGroup(is);
            templateMap.put(groupName,template);
        }
    }

    private StringTemplateGroup loadStringTemplateGroup(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        StringTemplateGroup templateGroup = new StringTemplateGroup(reader);
        //StringTemplate st = templateGroup.getInstanceOf(name);
        return templateGroup;
    }


    public StringTemplate lookupCachedTemplate(String groupName,String templateName) throws Exception  {
        if( null == templateName) {
            throw new IllegalArgumentException("Null values for groupName or TemplateName");
        }
        StringTemplate t = (StringTemplate) templateCache.get(groupName+"."+templateName);
        if(null == t) {
            StringTemplateGroup group = (StringTemplateGroup) templateMap.get(groupName);
            if(null == group) {
                throw new IllegalArgumentException("Unknown template group specified");
            }
            if(!group.isDefined(templateName)) {
                return null;
            } else {
                t = group.getInstanceOf(templateName);
                if(null == t) {
                    throw new IllegalArgumentException("Unknown template name specified");
                }
                templateCache.put(t.getGroup().getName()+"."+t.getName(),t);
            }
        }
        return t;
    }

    public StringTemplate lookupTemplate(String groupName, String templateName) throws Exception {
        if (null == templateName) {
            throw new IllegalArgumentException("Null values for groupName or TemplateName");
        }
        StringTemplateGroup group = (StringTemplateGroup) templateMap.get(groupName);
        if (null == group) {
            throw new IllegalArgumentException("Unknown template group specified");
        }
        if (!group.isDefined(templateName)) {
            return null;
        }
        final StringTemplate t = group.getInstanceOf(templateName);
        if (null == t) {
            throw new IllegalArgumentException("Unknown template name specified");
        }
        return t;
    }


    private String getValidGroupName(String firstLine) {
        final Matcher matcher = PATTERN_FOR_GROUP_NAME.matcher(firstLine);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    static class TemplateCache extends LinkedHashMap {
        private static final int MAX_ENTRIES = Integer.parseInt(System.getProperty("be.query.template.cachesize","500"));

        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > MAX_ENTRIES;
        }
    }


}
