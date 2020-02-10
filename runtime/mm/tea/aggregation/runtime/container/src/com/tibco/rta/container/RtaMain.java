package com.tibco.rta.container;

/**
 * @author bgokhale
 * 
 * Java Main class to kick off RtaEngine. RtaEngine is a class on its own such that it can be instantiated
 * elsewhere in "API" mode if required...
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.SortedSet;

import com.tibco.rta.JarVersionInspector;
import com.tibco.rta.engine.RtaEngine;
import com.tibco.rta.engine.RtaEngineFactory;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;


public class RtaMain {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_RUNTIME_CONTAINER.getCategory());

	private String propFileName;
	
	private String[] args;
	
	private Properties configuration;
	
	
	public RtaMain(String [] args) {
		this.args = args;
	}
	
	public static void main(String[] args) {
		
		try {
			RtaMain rtaMain = new RtaMain(args);
			rtaMain.parseArgs();
			rtaMain.printComponentInfo();
			Properties configuration = rtaMain.getConfiguration();
            LogManagerFactory.init(null);
			RtaEngine engine = RtaEngineFactory.getInstance().getEngine(configuration);
			engine.init(configuration);
			engine.start();
			Thread.sleep(Long.MAX_VALUE);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An exception occurred, while starting engine, exiting...", e);
		} catch (Error e) {
            LOGGER.log(Level.FATAL, "An error occurred, while starting engine, exiting...", e);
            System.exit(1);
        }
	}

	
	public void parseArgs() {
		int i = 0;

		if (args.length == 0) {
        	usage();
        }        

		while (i < args.length) {
			if (args[i].compareTo("-propFile") == 0) {
				if ((i + 1) >= args.length) {
					usage();
				}
				propFileName = args[i + 1];
				i += 2;
			} else {
				usage();
				break;
			}
		}
	}


    
    public Properties getConfiguration() throws Exception {
    	if (configuration == null) {
    		configuration = new Properties();
    		configuration.load(new FileInputStream(new File(propFileName)));
    	}
		return configuration;
    }
	
    private void usage() {
    	System.err.println("RtaEngine -propFile <filename>");
    	System.exit(0);
    }
    
    private void printComponentInfo() {
    	if (LOGGER.isEnabledFor(Level.INFO)) {
//            LOGGER.log(Level.INFO, ContainerComponentVersion.printVersionBanner());
        }
		
		JarVersionInspector jvInspector = new JarVersionInspector();
        final SortedSet<String> jarNameToClassName = jvInspector.getJarVersions(null);
        for (String versionInfo : jarNameToClassName) {
            if (LOGGER.isEnabledFor(Level.INFO)) {
                LOGGER.log(Level.INFO, versionInfo);
            }
        }

        System.out.println("");
    }
    

}
