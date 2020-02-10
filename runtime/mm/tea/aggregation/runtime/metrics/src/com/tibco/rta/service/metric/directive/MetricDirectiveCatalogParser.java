package com.tibco.rta.service.metric.directive;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/4/14
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricDirectiveCatalogParser {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_METRIC.getCategory());

    private static final String ELEM_DIRECTIVE = "directive";

    private static final String ATTR_CLASS = "class";

    private static final String ATTR_SCHEMA = "schema";

    private static SAXParserFactory factory = SAXParserFactory.newInstance();

    private static SAXParser saxParser;

    static {
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public static void parseCatalog(InputSource catalogSource) throws Exception {
        saxParser.parse(catalogSource, new CatalogHandler());
    }

    @SuppressWarnings("unchecked")
    private static class CatalogHandler extends DefaultHandler {
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (ELEM_DIRECTIVE.equals(qName)) {
                String directiveClass = attributes.getValue(ATTR_CLASS);
                String schemaName = attributes.getValue(ATTR_SCHEMA);

                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Directive schema name [%s] and class [%s]", schemaName, directiveClass);
                }
                //Instantiate
                try {
                    MetricProcessingDirectiveFactory.INSTANCE.registerMetricProcessingDirective(schemaName, directiveClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
