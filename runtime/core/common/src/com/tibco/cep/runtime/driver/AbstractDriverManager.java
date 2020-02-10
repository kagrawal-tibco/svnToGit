package com.tibco.cep.runtime.driver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import org.xml.sax.InputSource;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * @author ssinghal
 * 
 * Abstract class for all Drivers(cluster, cache etc)
 *
 */
public abstract class AbstractDriverManager {
	
	protected final ExpandedName DRIVER_FILE_NODE_NAME_TYPE = ExpandedName.makeName("type");
	protected final ExpandedName DRIVER_FILE_NODE_NAME_LABEL = ExpandedName.makeName("label");
    protected final ExpandedName DRIVER_FILE_NODE_NAME_VERSION = ExpandedName.makeName("version");
    protected final ExpandedName DRIVER_FILE_NODE_NAME_CLASS = ExpandedName.makeName("class");
    protected final ExpandedName DRIVER_FILE_NODE_NAME_DESCRIPTION = ExpandedName.makeName("description");
    
    protected static HashMap m_registrations = new HashMap();
    protected static HashMap m_drivers = new HashMap();

	protected static XiParser PARSER = XiParserFactory.newInstance();
	
	public abstract void initialize() throws Exception ;
	protected abstract void register(String type, String label, String version, String className, String description) ;
	
	 
	 protected void loadDrivers(String fileName, ExpandedName rootName, ExpandedName nodeName) throws IOException {
        final Enumeration driverConfigs = Thread.currentThread().getContextClassLoader().getResources(fileName);
        while (driverConfigs.hasMoreElements()) {
            try {
                final URL url = (URL) driverConfigs.nextElement();
                final InputStream is = url.openStream();
                final XiNode document = PARSER.parse(new InputSource(is));
                final XiNode drivers = XiChild.getChild(document, rootName);
                for (Iterator it = XiChild.getIterator(drivers, nodeName); it.hasNext();) {
                    final XiNode driverNode = (XiNode) it.next();
                    loadDriver(driverNode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	 
	protected void loadDriver(XiNode node) {
		final String driverType = XiChild.getString(node, DRIVER_FILE_NODE_NAME_TYPE);
		final String driverLabel = XiChild.getString(node, DRIVER_FILE_NODE_NAME_LABEL);
		final String version = XiChild.getString(node, DRIVER_FILE_NODE_NAME_VERSION);
		final String className = XiChild.getString(node, DRIVER_FILE_NODE_NAME_CLASS);
		final String driverDescription = XiChild.getString(node, DRIVER_FILE_NODE_NAME_DESCRIPTION);
		
		register(driverType, driverLabel, version, className, driverDescription);
	}
	 
	 
	protected static void initializeRegisteredDrivers() {
		for (Iterator it = m_registrations.values().iterator(); it.hasNext(); ) {
		    try {
		        final AbstractDriverManager.AbstractDriverRegistration registration = (AbstractDriverManager.AbstractDriverRegistration) it.next();
		        final String type = registration.getType();
		        if (registration.className != null && registration.getClassName().length() > 0) {
		            final Object driver = registration.makeDriverInstance();
		            m_drivers.put(type, driver);
		        }
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    } catch (IllegalAccessException e) {
		        e.printStackTrace();
		    } catch(InstantiationException e) {
		        e.printStackTrace();
		    }
		}
	}
	 
	 
	 
	public abstract class AbstractDriverRegistration{
		 
		protected String label;
		protected String type;
		protected String version;
		protected String className;
		protected String description;
		protected Object driver;
		
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}

		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}

		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}

		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public AbstractDriverRegistration(String type, String label, String version, String className, String description) {
			this.type = type;
			this.label = label;
			this.version = version;
			this.className = className;
			this.description = description;
		}

		public Object makeDriverInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            if (driver != null) {
                return driver;
            }
            
            final Class c = Class.forName(className);
            driver =  c.newInstance();
            return driver;
        }
		
		public boolean isLessRecentThan(String version) {
            final int[] myVersion = convertVersion(this.version);
            final int[] yourVersion = convertVersion(version);

            if (yourVersion[0] < myVersion[0]) {
                return false;
            }
            if (yourVersion[1] < myVersion[1]) {
                return false;
            }
            if (yourVersion[2] < myVersion[2]) {
                return false;
            }
            if (yourVersion[3] < myVersion[3]) {
                return false;
            }

            return true;
        }


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
        }
	     
		 
	}

}
