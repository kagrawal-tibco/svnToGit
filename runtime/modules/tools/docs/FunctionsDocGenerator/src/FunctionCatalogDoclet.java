/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: May 19, 2008
* Time: 4:09:20 PM
*/

import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ArrayList;

public class FunctionCatalogDoclet extends Doclet {

    public interface CatalogProcessor {

        void init(String options);

        void process(FunctionCatalogDocumentation[] catalog, File output);

        void stop();
    }

    public static class OptionNames {
        public static final String CATALOG = "-catalog";
        public static final String PROCESSOR = "-processor";
        public static final String PROCESSOR_OPTIONS = "-options";
        public static final String OUTPUT_DIR = "-d";
    }

    private static List<File> catalogXmlFiles;
    private static CatalogProcessor processor;
    private static String processorOptions;
    private static File outputDir;


    /**
     * Return the version of the Java Programming Language supported
     * by this doclet.
     * <p/>
     * This method is required by any doclet supporting a language version
     * newer than 1.1.
     *
     * @return the language version supported by this doclet.
     * @since 1.5
     */
    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }


    /**
     * Checks for doclet-added options. Returns the number of
     * arguments you must specify on the command line for the
     * given option.
     *
     * @param option String option.
     * @return number of arguments on the command line for an option
     *         including the option name itself.  Zero return means
     *         option not known.  Negative value means error occurred.
     */
    public static int optionLength(String option) {
        if (null != option) {
            if (option.equals(OptionNames.CATALOG)
                    || option.startsWith(OptionNames.CATALOG + " ")) {
                return 2;
            }
            if (option.equals(OptionNames.PROCESSOR)
                    || option.startsWith(OptionNames.PROCESSOR + " ")) {
                return 2;
            }
            if (option.equals(OptionNames.PROCESSOR_OPTIONS)
                    || option.startsWith(OptionNames.PROCESSOR_OPTIONS + " ")) {
                return 2;
            }
            if (option.equals(OptionNames.OUTPUT_DIR)
                    || option.startsWith(OptionNames.OUTPUT_DIR + " ")) {
                return 2;
            }
        }
        return 0;
    }

    /**
     * @param root RootDoc which represents the root of the program structure information for one run of javadoc.
     * @return true on success.
     */
    public static boolean start(RootDoc root) {

        processor.init(processorOptions);

        try {
            final List<FunctionCatalogDocumentation> catalogs
                    = new ArrayList<FunctionCatalogDocumentation>(catalogXmlFiles.size());

            for (File catalogFile: catalogXmlFiles) {
                try {
                    final FunctionCatalogDocumentationLoader loader = new FunctionCatalogDocumentationLoader(catalogFile, root);
                    catalogs.add(loader.getCatalog());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            processor.process(catalogs.toArray(new FunctionCatalogDocumentation[catalogs.size()]), outputDir);
        } finally {
            processor.stop();
        }

        return true;
    }


    /**
     * Checks that the expected options are provided correctly.
     *
     * @param options  String[][] all options provided.
     * @param reporter DocErrorReporter used to report errors.
     * @return true if the options are valid.
     */
    public static boolean validOptions(String[][] options,
                                       DocErrorReporter reporter) {

        catalogXmlFiles = new ArrayList<File>();

        if (null != options) {
            for (String[] option : options) {
                if ((null != option) && (option.length > 0)) {
                    final String optionName = option[0];

                    if (OptionNames.CATALOG.equals(optionName) && (option.length > 1)) {
                        final File catalog = new File(option[1]);
                        if (catalog.exists() && !catalog.isDirectory()) {
                            catalogXmlFiles.add(catalog);
                        } else {
                            reporter.printError("Invalid option value: "
                                    + OptionNames.CATALOG + " " + option[1]);
                             return false;
                        }

                    } else if (OptionNames.PROCESSOR.equals(optionName) && (option.length > 1)) {
                        processor = null;
                        try {
                            final Class converterClass = Class.forName(option[1]);
                            final Constructor constructor = converterClass.getConstructor();
                            processor = (CatalogProcessor) constructor.newInstance();
                        } catch (LinkageError e) {
                            reporter.printError("Invalid option value: "
                                    + OptionNames.PROCESSOR + " " + option[1]);
                            e.printStackTrace();
                            return false;
                        } catch (Exception e) {
                            reporter.printError("Invalid option value: "
                                    + OptionNames.PROCESSOR + " " + option[1]);
                            e.printStackTrace();
                            return false;
                        }

                    } else if (OptionNames.PROCESSOR_OPTIONS.equals(optionName) && (option.length > 1)) {
                        processorOptions = option[1];

                    } else if (OptionNames.OUTPUT_DIR.equals(optionName) && (option.length > 1)) {
                        outputDir = new File(option[1]);
                        if (!outputDir.isDirectory()) {
                            reporter.printError("Invalid option value: "
                                    + OptionNames.OUTPUT_DIR + " " + option[1]);
                            return false;
                        }
                    }

                }//if
            }//for
        }//if

        if (catalogXmlFiles.size() == 0) {
            reporter.printError("Missing option value for: "
                    + OptionNames.CATALOG + " <catalog name>");
            return false;
        }

        if (null == processor) {
            reporter.printError("Missing option value for: "
                    + OptionNames.PROCESSOR + " <processor class name>");
            return false;
        }
        if (null == outputDir) {
            reporter.printError("Missing option value for: "
                    + OptionNames.OUTPUT_DIR + " <output directory path>");
            return false;
        }
        
        return true;
    }


}
