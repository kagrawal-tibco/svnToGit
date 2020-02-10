import com.tibco.cep.cep_commonVersion;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class FunctionHtmlDocProcessor implements FunctionCatalogDoclet.CatalogProcessor {

    public interface OptionNames {
        public static final String VERBOSE = "verbose";
    }

    protected static final String CATALOG_FILE_NAME = "index.html";
    protected static final String CATEGORY_SUMMARY_FILE_NAME = "category-summary.html";
    protected static final String PRODUCT_COPYRIGHT = cep_commonVersion.copyright;
    protected static final String PRODUCT_NAME = cep_commonVersion.getComponent()
            .replaceAll("BusinessEvents(?!&trade;)(?:&[Tt][Rr][Aa][Dd][Ee];|(.|$))", "BusinessEvents&trade;$1");
    protected static final String PRODUCT_VERSION = cep_commonVersion.version;
    protected static final StringTemplateGroup TEMPLATE_GROUP =
            new StringTemplateGroup(FunctionHtmlDocProcessor.class.getName());

    protected final StringTemplate categoryTemplate;
    protected final StringTemplate functionTemplate;

    protected boolean verbose;


    public FunctionHtmlDocProcessor() {
        this.categoryTemplate = TEMPLATE_GROUP.getInstanceOf("category");
        this.functionTemplate = TEMPLATE_GROUP.getInstanceOf("function");
        this.verbose = false;
    }


    public void init(String options) {
        if (null != options) {
            for (String option : options.split(";")) {
                if (OptionNames.VERBOSE.equalsIgnoreCase(option)) {
                    this.verbose = true;
                    break;
                }
            }
        }
    }


    public void process(FunctionCatalogDocumentation[] catalogs, File outputDir) {
        try {
            this.writeProductPage(catalogs, outputDir);

            for (FunctionCatalogDocumentation catalog : catalogs) {
                final String catalogName = catalog.getName();
                if (this.verbose) {
                    System.err.println("Processing catalog: " + catalogName);
                }
                final File catalogDir = new File(outputDir, catalogName);
                catalogDir.mkdirs();
                this.writeCatalogPage(catalog, catalogDir);
                for (FunctionCategoryDocumentation category : catalog.getCategories().values()) {
                    this.process(category, catalogDir, catalogName);
                }//for
            }//for
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void process(
            FunctionCategoryDocumentation category,
            File outputDir,
            String catalogName
    ) throws IOException {

        outputDir = new File(outputDir, category.getName());
        outputDir.mkdirs();

        this.writeCategoryPage(category, outputDir, catalogName);

        for (FunctionCategoryDocumentation subcategory : category.getSubCategories().values()) {
            this.process(subcategory, outputDir, catalogName);
        }
        for (FunctionDocumentation function : category.getFunctions().values()) {
            this.process(function, outputDir, category, catalogName);
        }
    }


    protected void process(
            FunctionDocumentation function,
            File outputDir,
            FunctionCategoryDocumentation category,
            String catalogName
    ) throws IOException {

        this.writeFunctionPage(function, outputDir, category, catalogName);
    }


    public void stop() {
    }


    protected void writeCatalogPage(
            FunctionCatalogDocumentation catalog,
            File outputDir
    ) throws IOException {

        final File outputFile = new File(outputDir, CATALOG_FILE_NAME);
        if (this.verbose) {
            System.err.println("Writing catalog page to: " + outputFile.getPath());
        }

        final String title = catalog.getName() + " Functions Catalog";

        this.categoryTemplate.reset();
        this.categoryTemplate.setAttribute("catalogFileName", CATALOG_FILE_NAME);
        this.categoryTemplate.setAttribute("catalogName", catalog.getName());
        this.categoryTemplate.setAttribute("categories", catalog.getCategories().values().toArray());
        this.categoryTemplate.setAttribute("categorySummaryFileName", CATEGORY_SUMMARY_FILE_NAME);
        this.categoryTemplate.setAttribute("copyright", PRODUCT_COPYRIGHT);
        this.categoryTemplate.setAttribute("isCatalog", true);
        this.categoryTemplate.setAttribute("pathToCatalogRoot", ".");
        this.categoryTemplate.setAttribute("productName", PRODUCT_NAME);
        this.categoryTemplate.setAttribute("title", title);

        this.writeToFile(this.categoryTemplate.toString(), outputFile);
    }


    protected void writeCategoryPage(
            FunctionCategoryDocumentation category,
            File outputDir,
            String catalogName
    ) throws IOException {

        final File outputFile = new File(outputDir, CATEGORY_SUMMARY_FILE_NAME);
        final String categoryFullName = category.getFullName();
        if (this.verbose) {
            System.err.println("Writing " + categoryFullName + " to: " + outputFile.getPath());
        }

        final StringBuilder pathToRoot = new StringBuilder("..");
        final List<AncestorInfo> ancestors = new LinkedList<AncestorInfo>();
        for (FunctionCategoryDocumentation p = category.getParent(); null != p; p = p.getParent()) {
            ancestors.add(0, new AncestorInfo(p.getName(), pathToRoot.toString() + "/" + CATEGORY_SUMMARY_FILE_NAME, false));
            pathToRoot.append("/..");
        }
        ancestors.add(new AncestorInfo(category.getName(), CATEGORY_SUMMARY_FILE_NAME, true));

        this.categoryTemplate.reset();
        this.categoryTemplate.setAttribute("ancestors", ancestors.toArray());
        this.categoryTemplate.setAttribute("catalogName", catalogName);
        this.categoryTemplate.setAttribute("catalogFileName", CATALOG_FILE_NAME);
        this.categoryTemplate.setAttribute("categories", category.getSubCategories().values().toArray());
        this.categoryTemplate.setAttribute("categorySummaryFileName", CATEGORY_SUMMARY_FILE_NAME);
        this.categoryTemplate.setAttribute("copyright", PRODUCT_COPYRIGHT);
        this.categoryTemplate.setAttribute("description", category.getDescription());
        this.categoryTemplate.setAttribute("functions", category.getFunctions().values().toArray());
        this.categoryTemplate.setAttribute("isCategory", true);
        this.categoryTemplate.setAttribute("pathToCatalogRoot", pathToRoot);
        this.categoryTemplate.setAttribute("productName", PRODUCT_NAME);
        this.categoryTemplate.setAttribute("title", categoryFullName);

        this.writeToFile(this.categoryTemplate.toString(), outputFile);
    }


    protected void writeFunctionPage(
            FunctionDocumentation function,
            File outputDir,
            FunctionCategoryDocumentation category,
            String catalogName
    ) throws IOException {

        final String name = function.getName();
        final String categoryFullName = category.getFullName();
        final String fullName = categoryFullName + "." + name;
        final File outputFile = new File(outputDir, name + ".html");
        if (this.verbose) {
            System.err.println("Writing " + fullName + "() to: " + outputFile.getPath());
        }

        final StringBuilder pathToRoot = new StringBuilder("..");
        final List<AncestorInfo> ancestors = new LinkedList<AncestorInfo>();
        for (FunctionCategoryDocumentation p = category.getParent(); null != p; p = p.getParent()) {
            ancestors.add(0, new AncestorInfo(p.getName(), pathToRoot.toString() + "/" + CATEGORY_SUMMARY_FILE_NAME, false));
            pathToRoot.append("/..");
        }
        ancestors.add(new AncestorInfo(category.getName(), CATEGORY_SUMMARY_FILE_NAME, true));

        this.functionTemplate.reset();
        this.functionTemplate.setAttribute("ancestors", ancestors.toArray());
        this.functionTemplate.setAttribute("catalogFileName", CATALOG_FILE_NAME);
        this.functionTemplate.setAttribute("catalogName", catalogName);
        this.functionTemplate.setAttribute("categoryFullName", categoryFullName);
        this.functionTemplate.setAttribute("categorySummaryFileName", CATEGORY_SUMMARY_FILE_NAME);
        this.functionTemplate.setAttribute("copyright", PRODUCT_COPYRIGHT);
        this.functionTemplate.setAttribute("function", function);
        this.functionTemplate.setAttribute("parameters", function.getArguments().values().toArray());
        this.functionTemplate.setAttribute("pathToCatalogRoot", pathToRoot);
        this.functionTemplate.setAttribute("productName", PRODUCT_NAME);
        this.functionTemplate.setAttribute("return", function.getReturn());

        this.writeToFile(this.functionTemplate.toString(), outputFile);
    }


    protected void writeProductPage(
            FunctionCatalogDocumentation[] catalogs,
            File outputDir
    ) throws IOException {

        final File outputFile = new File(outputDir, CATALOG_FILE_NAME);
        if (this.verbose) {
            System.err.println("Writing product page to: " + outputFile.getPath());
        }

        final String title = PRODUCT_NAME + " " + PRODUCT_VERSION;

        this.categoryTemplate.reset();
        this.categoryTemplate.setAttribute("catalogFileName", CATALOG_FILE_NAME);
        this.categoryTemplate.setAttribute("catalogs", catalogs);
        this.categoryTemplate.setAttribute("categorySummaryFileName", CATEGORY_SUMMARY_FILE_NAME);
        this.categoryTemplate.setAttribute("copyright", PRODUCT_COPYRIGHT);
        this.categoryTemplate.setAttribute("isProduct", true);
        this.categoryTemplate.setAttribute("pathToCatalogRoot", ".");
        this.categoryTemplate.setAttribute("productName", PRODUCT_NAME);
        this.categoryTemplate.setAttribute("title", title);

        this.writeToFile(this.categoryTemplate.toString(), outputFile);
    }


    private void writeToFile(String data, File outputFile) throws IOException {
        final FileWriter writer = new FileWriter(outputFile);
        try {
            writer.write(data);
            writer.flush();
        } finally {
            writer.close();
        }
    }


    private static class AncestorInfo {
        private boolean isCurrent;
        private String link;
        private String name;

        private AncestorInfo(String name, String link, boolean isCurrent) {
            this.isCurrent = isCurrent;
            this.link = link;
            this.name = name;
        }

        public String getLink() {
            return this.link;
        }

        public String getName() {
            return this.name;
        }

        public boolean isCurrent() {
            return this.isCurrent;
        }
    }
}