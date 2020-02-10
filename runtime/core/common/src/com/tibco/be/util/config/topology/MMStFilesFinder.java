package com.tibco.be.util.config.topology;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.util.SystemProperty;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 11/17/11
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class MMStFilesFinder {
    // Constants
    private static final String ST_TEMPLATE_FILE_NAME = "site_topology_template.st";
    private static final String ST_FILE_EXTENSION = ".st";
    private static final String MM_CONFIG_REL_PATH = File.separator+ "mm" + File.separator + "config" + File.separator;

    public static final String TPLGY_FILE_PROP_BE = "be.mm.topology.file";
    public static final String TPLGY_FILE_PROP_ASG = "asg.mm.topology.file";

    public static File ST_FILES_DIR = new File(System.getProperty(SystemProperty.BE_HOME.getPropertyName())
                                               + MM_CONFIG_REL_PATH);

    private static ArrayList<File> stFiles;   //File objects for all the st files (files with .st extension) under BE_HOME/mm/config
    private static Logger logger;

    // Initialize this class //
    static {
        logger = LogManagerFactory.getLogManager().getLogger(MMStFilesFinder.class);
        findStFiles();
    }

    private static void findStFiles() {
        final String tfp = System.getProperty(TPLGY_FILE_PROP_BE) != null
                     ? System.getProperty(TPLGY_FILE_PROP_BE)
                     : System.getProperty(TPLGY_FILE_PROP_ASG);

        final File stFileFromProp =
                    tfp == null || tfp.trim().isEmpty() ? null
                    : new File(tfp);

        //Includes all the files with .st extension under BE_HOME/mm/config, excluding the case when
        //a file with the same name was specified using the TPLGY_FILE_PROP
        stFiles= new ArrayList<File>();
        if (ST_FILES_DIR.exists() == true) {
            stFiles.addAll(Arrays.asList(ST_FILES_DIR.listFiles(new StFileFilter(stFileFromProp))));
        }

        if (stFileFromProp != null)
            stFiles.add(stFileFromProp);

        if (stFiles == null || stFiles.isEmpty()) {
            final String msg = String.format("No site topology file found! " +
                "At least one site topology file must exist in the directory '%s'",
                ST_FILES_DIR);

            logger.log(Level.ERROR, msg);
            throw new RuntimeException("No site topology file found!");
        }
    }

    public static List<File> getStFiles() {
        return Collections.unmodifiableList(stFiles);
    }

    //Class used to filer which ST files (.st) are to be parsed. Read comments inside class.
    private static class StFileFilter implements FileFilter {
        private String stFileNameFromProp;

        public boolean accept(File pathname) {
            return ( pathname.getName().endsWith(ST_FILE_EXTENSION) &&     //Include only files with .st extension
                    !pathname.getName().equals(ST_TEMPLATE_FILE_NAME) &&   //Exclude ST Template File
                    !pathname.getName().equals(stFileNameFromProp) );      //Exclude a file if there is a file with the
                                                                           //same name specified using the TPLGY_FILE_PROP property
        }

        private StFileFilter(File stFileFromProp) {
            stFileNameFromProp = stFileFromProp == null ? null
                    : stFileFromProp.getName();
        }
    }
}
