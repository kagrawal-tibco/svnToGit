package com.tibco.cep.driver.http.server.impl.filter;

import com.tibco.cep.driver.http.server.FilterConfiguration;
import com.tibco.cep.repo.GlobalVariables;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: nicolas
 * Date: 9/4/13
 * Time: 2:09 PM
 */
public class FilterConfigurationLoader {


    private static final Pattern PATTERN = Pattern.compile(
            "be\\.http\\.filter\\.([^\\.]+)(?:"
                    + "(\\.class)"
                    + "|(?:\\.urlpattern\\.(.+))"
                    + "|(?:\\.param\\.(.+))"
                    + ")");


    public List<FilterConfiguration> load(
            Properties properties,
            GlobalVariables globalVariables) {

        final List<FilterConfiguration> configs = new ArrayList<FilterConfiguration>();
        for (final MutableConfigData configData : this.parseAllProperties(properties, globalVariables)) {
            if ((null != configData)
                    && (null != configData.className)
                    && !configData.urlPatterns.isEmpty()) {
                configs.add(new FilterConfigurationImpl(
                        configData.name,
                        configData.className,
                        configData.params,
                        configData.urlPatterns));
            }
        }
        return configs;
    }


    private Collection<MutableConfigData> parseAllProperties(
            Properties properties,
            GlobalVariables gvs) {
        final Map<String, MutableConfigData> filterNameToConfigData = new TreeMap<String, MutableConfigData>();
        for (final Map.Entry<Object, Object> e : properties.entrySet()) {
            final Object key = e.getKey();
            if (null != key) {
                final Object value = e.getValue();
                if (null != value) {
                    final Matcher matcher = PATTERN.matcher(key.toString());
                    if (matcher.matches()) {
                        final String filterName = matcher.group(1);
                        MutableConfigData configData = filterNameToConfigData.get(filterName);
                        if (null == configData) {
                            configData = new MutableConfigData(filterName);
                            filterNameToConfigData.put(filterName, configData);
                        }
                        if (null != matcher.group(2)) {
                            configData.className = gvs.substituteVariables((String) value).toString();
                        } else if (null != matcher.group(3)) {
                            configData.urlPatterns.add(gvs.substituteVariables((String) value).toString());
                        } else {
                            final String paramName = matcher.group(4);
                            if ((null != paramName) && !paramName.isEmpty()) {
                                configData.params.put(paramName, gvs.substituteVariables((String) value).toString());
                            }
                        }
                    }
                }
            }
        }
        return filterNameToConfigData.values();
    }


    private class MutableConfigData {
        public final String name;
        public String className;
        public final Set<String> urlPatterns = new HashSet<String>();
        public final Map<String, String> params = new HashMap<String, String>();

        public MutableConfigData(String name) {
            this.name = name;
        }
    }


    private class FilterConfigurationImpl
            implements FilterConfiguration {

        private final String name;
        private final String className;
        private final Set<String> urlPatterns;
        private final Map<String, String> params;

        private FilterConfigurationImpl(
                String name,
                String className,
                Map<String, String> params,
                Set<String> urlPatterns) {
            this.name = name;
            this.className = className;
            this.urlPatterns = Collections.unmodifiableSet(urlPatterns);
            this.params = Collections.unmodifiableMap(params);
        }

        @Override
        public String getClassName() {
            return this.className;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public Map<String, String> getParameters() {
            return this.params;
        }

        @Override
        public Set<String> getUrlPatterns() {
            return this.urlPatterns;
        }
    }

}
