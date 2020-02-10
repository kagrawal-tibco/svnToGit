import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.impl.FunctionsCatalog;

import java.io.File;
import java.util.*;


/**
 * User: nprade
 * Date: 4/26/12
 * Time: 8:01 PM
 */
public class FunctionsDocGenerator {

    protected static final boolean VERBOSE = false;
    protected static final Set<String> IGNORED_CATALOG_NAMES = new TreeSet<String>();
    static {
        IGNORED_CATALOG_NAMES.add("Ontology");
        IGNORED_CATALOG_NAMES.add("Custom");
        IGNORED_CATALOG_NAMES.add("BRMS"); //Ignore RMS functions
    }

    public static void main(
            String[] args)
            throws Exception {

        final File outputDir = new File(args[0]).getCanonicalFile();

        final FunctionsDocGenerator fdg = new FunctionsDocGenerator();
        fdg.generate(outputDir);
    }


    public FunctionsDocGenerator() {
    }


    public void generate(
            File outputDir)
            throws Exception {

//        if (!outputDir.isDirectory()) {
//            throw new IllegalArgumentException("Not a directory:\n" + outputDir.getCanonicalPath());
//        }
//
//        if (!outputDir.canWrite()) {
//            throw new IllegalArgumentException("Cannot write in:\n" + outputDir.getCanonicalPath());
//        }

        final List<FunctionCatalogDocumentation> catalogs = readCatalogs();

        final FunctionHtmlDocProcessor processor = new FunctionHtmlDocProcessor();
        final String processorOptions = VERBOSE ? FunctionHtmlDocProcessor.OptionNames.VERBOSE : "";
        processor.init(processorOptions);
        processor.process(catalogs.toArray(new FunctionCatalogDocumentation[catalogs.size()]), outputDir);
    }


    private List<FunctionCatalogDocumentation> readCatalogs()
            throws Exception {

        final SortedMap<String, FunctionsCategory> nameToCatalog = new TreeMap<String, FunctionsCategory>();

        for (final Iterator i = FunctionsCatalog.getINSTANCE().catalogs(); i.hasNext(); ) {
            final FunctionsCategory catalog = (FunctionsCategory) i.next();
            final String name = catalog.getName().localName;
            if (!IGNORED_CATALOG_NAMES.contains(name)) {
                nameToCatalog.put(name, catalog);
            }
        }

        final List<FunctionCatalogDocumentation> result =
                new ArrayList<FunctionCatalogDocumentation>(nameToCatalog.size());

        final FunctionCatalogConverter converter = new FunctionCatalogConverter();
        for (final FunctionsCategory catalog : nameToCatalog.values()) {
            result.add(converter.convertCatalog(catalog));
        }

        return result;
    }

}
