package com.tibco.rta.service.metric.directive;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MetricProcessingDirectiveFactory {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

    public static final MetricProcessingDirectiveFactory INSTANCE = new MetricProcessingDirectiveFactory();

    private Map<String, String> schemaToDirectiveMap = new HashMap<String, String>();

    //cache instances for reuse.
    private Map<String, MetricProcessingDirective> schemaToInstanceMap = new HashMap<String, MetricProcessingDirective>();

    static {
        try {
            bootstrap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bootstrap() throws Exception {
        //Load all classes starting with parent classloader
        Enumeration<URL> directiveResources = MetricProcessingDirectiveFactory.class.getClassLoader().getResources("metric.directive.catalog");

        while ((directiveResources.hasMoreElements())) {
            URL nextElement = directiveResources.nextElement();
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, "Directive catalog file found at location [%s]", nextElement);
            }
            //Read inputstream
            InputStream stream = nextElement.openStream();
            MetricDirectiveCatalogParser.parseCatalog(new InputSource(stream));
        }
    }

    public void registerMetricProcessingDirective(String schemaName, String directiveClass) {
        if (schemaName == null || schemaName.isEmpty()) {
            throw new IllegalArgumentException("Schema name attribute is mandatory");
        }
        if (directiveClass == null || directiveClass.isEmpty()) {
            throw new IllegalArgumentException("Metric processing directive class attribute is mandatory");
        }
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Schema name [%s] already registered with a directive", schemaName);
        }
        schemaToDirectiveMap.put(schemaName, directiveClass);
    }

    /**
     * Returns an instance of {@link com.tibco.rta.service.metric.directive.MetricProcessingDirective}
     * if there is a mapping with a schema else null.
     */
    public MetricProcessingDirective getMetricProcessingDirectives(String schemaName) throws Exception {
        MetricProcessingDirective metricProcessingDirective;
        String directiveClass = schemaToDirectiveMap.get(schemaName);

        if (directiveClass != null) {
            Class<?> directiveClazz = Class.forName(directiveClass);
            if (!MetricProcessingDirective.class.isAssignableFrom(directiveClazz)) {
                throw new IllegalArgumentException(String.format("Directive class [%s] does not implement MetricProcessingDirective interface", directiveClass));
            }
            if (schemaToInstanceMap.containsKey(schemaName)) {
                if (LOGGER.isEnabledFor(Level.DEBUG)) {
                    LOGGER.log(Level.DEBUG, "Returning cached directive instance for schema [%s]", schemaName);
                }
                metricProcessingDirective = schemaToInstanceMap.get(schemaName);
            } else {
                metricProcessingDirective = (MetricProcessingDirective) directiveClazz.newInstance();
                schemaToInstanceMap.put(schemaName, metricProcessingDirective);
            }
        } else {
            metricProcessingDirective = DefaultProcessingDirective.INSTANCE;
        }
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Metric processing directive class used [%s]", metricProcessingDirective.getClass().getName());
        }
        return metricProcessingDirective;
    }
}
