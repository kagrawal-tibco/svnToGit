package com.tibco.cep.runtime.channel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.xml.sax.InputSource;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Manages all the Channel drivers available from the classpath.
 * <p>Drivers are available when declared in a drivers.xml file
 * which is located at the root of a jar in the classpath
 * and which conforms the <code>drivers.xsd</code> schema.</p>
 *
 * @version 2.0
 * @since 2.0
 */
public class DriverManager {

    protected static final ExpandedName DRIVER_FILE_NODE_NAME_ROOT = ExpandedName.makeName("drivers");
    protected static final ExpandedName DRIVER_FILE_NODE_NAME_DRIVER = ExpandedName.makeName("driver");
    protected static final ExpandedName DRIVER_FILE_NODE_NAME_TYPE = ExpandedName.makeName("type");
    protected static final ExpandedName DRIVER_FILE_NODE_NAME_VERSION = ExpandedName.makeName("version");
    protected static final ExpandedName DRIVER_FILE_NODE_NAME_CLASS = ExpandedName.makeName("class");

    protected static XiParser PARSER = XiParserFactory.newInstance();

    protected static HashMap m_registrations = new HashMap();
    protected static HashMap m_drivers = new HashMap();


    /**
     * Finds, loads and initializes all the available drivers.
     * @throws Exception
     * @since 2.0
     */
    public static void initialize() throws Exception {
    	DriverManager.loadDrivers();
    	DriverManager.initializeRegisteredDrivers();
    }


    /**
     * Gets the driver registered under the given name.
     * @param name the String name of a driver.
     * @return the ChannelDriver with the given name if it was found, else null.
     * @throws Exception
     * @since 2.0
     */
    public static ChannelDriver driverForName(String name) throws Exception {
        return (ChannelDriver) DriverManager.m_drivers.get(name);
    }


    /**
     * Gets the names of all the drivers known to this DriverManager.
     * @return a Set of String names.
     * @since 2.0
     */
    public static Set getAllNames() {
        return DriverManager.m_registrations.keySet();
    }


    /**
     * Binds a driver class and a driver version to a driver name.
     * @param type String name under which the driver is registered.
     * @param version String version of the driver.
     * @param className full String of the driver class.
     * @since 2.0
     */
    protected static void register(String type, String version, String className) {
    	final DriverManager.DriverRegistration registration = new DriverManager.DriverRegistration(type, version, className);
    	if (DriverManager.m_registrations.get(type) == null) {
    		DriverManager.m_registrations.put(type, registration);
    	} else {
    		DriverManager.DriverRegistration registered = (DriverRegistration) DriverManager.m_registrations.get(type);
    		if (registered != null && registered.getDriverClass() != null
    				&& !registered.getDriverClass().equals(className)) {
    			//If the driver type is already registered with a different class, log a warning.
    			LogManagerFactory.getLogManager().getLogger(DriverManager.class).log(Level.WARN,
    					"Driver '" + type + "' is already registered using class - " + className);
    		}
    	}
    }



    protected static void loadDrivers() throws IOException {
        final Enumeration driverConfigs = Thread.currentThread().getContextClassLoader().getResources("drivers.xml");
        while (driverConfigs.hasMoreElements()) {
            try {
                final URL url = (URL) driverConfigs.nextElement();
                final InputStream is = url.openStream();
                final XiNode document = DriverManager.PARSER.parse(new InputSource(is));
                final XiNode drivers = XiChild.getChild(document, DriverManager.DRIVER_FILE_NODE_NAME_ROOT);
                for (Iterator it = XiChild.getIterator(drivers, DriverManager.DRIVER_FILE_NODE_NAME_DRIVER); it.hasNext();) {
                    final XiNode driverNode = (XiNode) it.next();
                    DriverManager.loadDriver(driverNode);
                }//for
            } catch (Exception e) {
                e.printStackTrace();
            }//catch
        }//while
    }//loadDrivers


    protected static void loadDriver(XiNode node) {
        final String driverType = XiChild.getString(node, DriverManager.DRIVER_FILE_NODE_NAME_TYPE);
        final String version = XiChild.getString(node, DriverManager.DRIVER_FILE_NODE_NAME_VERSION);
        final String className = XiChild.getString(node, DriverManager.DRIVER_FILE_NODE_NAME_CLASS);

        DriverManager.register(driverType, version, className);
    }//loadDriver


    private static void initializeRegisteredDrivers() {
        for (Iterator it = DriverManager.m_registrations.values().iterator(); it.hasNext(); ) {
            try {
                final DriverManager.DriverRegistration registration = (DriverManager.DriverRegistration) it.next();
                final String type = registration.getType();
                if (registration.m_className != null && registration.m_className.length() > 0) {
                    final ChannelDriver driver = registration.makeDriverInstance();
                    DriverManager.m_drivers.put(type, driver);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch(InstantiationException e) {
                e.printStackTrace();
            }//catch
        }//for
    }//initializeRegisteredDrivers






    static class DriverRegistration {

        private String m_type;
        private String m_version;
        private String m_className;
        private ChannelDriver m_driver;

        public DriverRegistration(String type, String version, ChannelDriver driver) {
            m_type = type;
            m_version = version;
            m_className = driver.getClass().getName();
            m_driver = driver;
        }//constr

        public DriverRegistration(String type, String version, String className) {
            m_type = type;
            m_version = version;
            m_className = className;
            m_driver = null;
        }//constr

        public String getType() {
            return m_type;
        }

        public String getVersion() {
            return m_version;
        }

        public String getDriverClass() {
            return m_className;
        }

        public boolean isLessRecentThan(String version) {
            final int[] myVersion = convertVersion(m_version);
            final int[] yourVersion = convertVersion(version);

            if (yourVersion[0] < myVersion[0]) {
                return false;
            }//if
            if (yourVersion[1] < myVersion[1]) {
                return false;
            }//if
            if (yourVersion[2] < myVersion[2]) {
                return false;
            }//if
            if (yourVersion[3] < myVersion[3]) {
                return false;
            }//if

            return true;
        }//isLessRecentThan


        private int[] convertVersion(String version) {
            final int[] array = new int[4];
            int start = 0;
            int stop = version.indexOf('.');
            String number = version.substring(start, stop);
            array[0] = Integer.parseInt(number);
            start = stop + 1;
            stop = version.indexOf('.', start);
            number = version.substring(start, stop);
            array[1] = Integer.parseInt(number);
            start = stop + 1;
            stop = version.indexOf('.', start);
            number = version.substring(start, stop);
            array[2] = Integer.parseInt(number);
            start = stop + 1;
            number = version.substring(start);
            array[3] = Integer.parseInt(number);
            return array;
        }//convertVersion


        public ChannelDriver makeDriverInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            if (m_driver != null) {
                return m_driver;
            }//if
            final Class c = Class.forName(m_className);
            m_driver = (ChannelDriver) c.newInstance();
            return m_driver;
        }//makeDriverInstance
    }//class DriverRegistration


}
