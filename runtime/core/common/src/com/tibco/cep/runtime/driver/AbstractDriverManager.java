package com.tibco.cep.runtime.driver;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author ssinghal
 * 
 * Abstract class for all Drivers(cluster, cache etc)
 *
 */
public abstract class AbstractDriverManager {
	
    protected static List<AbstractDriverRegistration> d_registrations = new ArrayList<AbstractDriverRegistration>();

	public abstract void initialize() throws Exception ;
	protected abstract void register(URL url) ;
	protected abstract List<DriverPojo> getAllDrivers() throws Exception ;
	
	protected List<URL> getDriverUrls() {
		List<URL> urls = new ArrayList<URL>();
		for(AbstractDriverRegistration regis : d_registrations) {
			urls.add(regis.getUrl());
		}
		return urls;
	}
	
	 
	protected void loadDrivers(String fileName, ExpandedName rootName) throws IOException {
        final Enumeration driverConfigs = Thread.currentThread().getContextClassLoader().getResources(fileName);
        while (driverConfigs.hasMoreElements()) {
            try {
                final URL url = (URL) driverConfigs.nextElement();
                loadDriver(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	 
	protected void loadDriver(URL url) {
		register(url);
	}
	 
	public abstract class AbstractDriverRegistration{
		 
		protected URL url;
		protected String driverCategory;
		
		
		public URL getUrl() {
			return url;
		}
		public void setUrl(URL url) {
			this.url = url;
		}
		
		public String getDriverCategory() {
			return driverCategory;
		}
		public void setDriverCategory(String driverCategory) {
			this.driverCategory = driverCategory;
		}
		
		public AbstractDriverRegistration(URL url, String driverCategory) {
			this.url = url;
			this.driverCategory = driverCategory;
		}

	/*	public boolean isLessRecentThan(String version) {
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
	  */
		 
	}

}
