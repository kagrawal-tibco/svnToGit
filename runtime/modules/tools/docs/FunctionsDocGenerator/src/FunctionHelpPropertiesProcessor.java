import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Properties;


public class FunctionHelpPropertiesProcessor implements FunctionCatalogDoclet.CatalogProcessor {

    public interface OptionNames {
        public static final String VERBOSE = "verbose";
    }

    public static final String COMMON_PREFIX_OF_PROPERTIES = "be.help.functions.category.";
    public static final String OUPUT_FILE_NAME = "functions-help.properties";

    protected static final String PROPERTIES_COMMENTS_PREFIX = "Function Properties ";
    protected static final StringTemplate TEMPLATE_FOR_HTML_TOOLTIP;

    static {
        final StringTemplateGroup group = new StringTemplateGroup(FunctionHelpPropertiesDoclet.class.getName());
        TEMPLATE_FOR_HTML_TOOLTIP = group.getInstanceOf("tooltip");
    }

    protected boolean verbose;


    public FunctionHelpPropertiesProcessor() {
       this.verbose = false;
    }


    protected void addCategory(FunctionCategoryDocumentation category, Properties p) {
        final String categoryName = category.getFullName();
        if (this.verbose) {
            System.err.println("    Processing category: " + categoryName);
        }

        this.addProperty(p, categoryName + ".clz", category.getClassName());
        this.addProperty(p, categoryName + ".name", categoryName);
        this.addProperty(p, categoryName + ".description", category.getDescription());

        for (FunctionDocumentation function : category.getFunctions().values()) {
            this.addFunction(function, categoryName, p);
        }

        for (FunctionCategoryDocumentation subCategory : category.getSubCategories().values()) {
            this.addCategory(subCategory, p);
        }
    }


    protected void addFunction(FunctionDocumentation function, String categoryName, Properties p) {
        final String name = function.getName();
        if (this.verbose) {
            System.err.println("        Processing function: " + name);
        }

        final Collection<FunctionParameterDocumentation> args = function.getArguments().values();
        final String cautions = function.getCautions();
        final String description = function.getDescription();
        final String domain = function.getDomain();
        final String example = function.getExample();
        final String fullName = categoryName + "." + function.getName();
        final String mapper = function.getMapper();
        final String methodPrefix = categoryName + ".method." + name;
        final FunctionParameterDocumentation returnParam = function.getReturn();
        final String see = function.getSee();
        final String signature = function.getSignature();
        final String synopsis = function.getSynopsis();
        final String version = function.getVersion();

        TEMPLATE_FOR_HTML_TOOLTIP.reset();
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("cautions", cautions);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("description", description);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("domain", domain);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("example", example);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("fullName", fullName);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("mapper", mapper);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("name", name);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("parameters", args);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("return", returnParam);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("see", see);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("signature", signature);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("synopsis", synopsis);
        TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("version", version);
        final String tooltip = TEMPLATE_FOR_HTML_TOOLTIP.toString();


        this.addProperty(p, methodPrefix + ".cautions", cautions);
        this.addProperty(p, methodPrefix + ".description", description);
        this.addProperty(p, methodPrefix + ".domain", domain);
        this.addProperty(p, methodPrefix + ".example", example);
        this.addProperty(p, methodPrefix + ".fullName", fullName);
        this.addProperty(p, methodPrefix + ".mapper", mapper);
        int index = 0;
        for (FunctionParameterDocumentation arg : args) {
            final String paramPrefix = methodPrefix + ".param." + index++;
            this.addProperty(p, paramPrefix + ".description", arg.getDescription());
            this.addProperty(p, paramPrefix + ".name", arg.getName());
            this.addProperty(p, paramPrefix + ".type", arg.getType());

        }
        if (null != returnParam) {
            this.addProperty(p, methodPrefix + ".return", returnParam.getType());
        }
        this.addProperty(p, methodPrefix + ".see", see);
        this.addProperty(p, methodPrefix + ".signature", signature);
        this.addProperty(p, methodPrefix + ".synopsis", synopsis);
        this.addProperty(p, methodPrefix + ".tooltip", tooltip);
        this.addProperty(p, methodPrefix + ".version", version);
    }


    protected void addProperty(Properties p, String name, String value) {
        if (null == value) {
            value = "";
        }
        p.setProperty(COMMON_PREFIX_OF_PROPERTIES + name, value);
    }


    public void init(String options) {
        for (String option : options.split(";")) {
            if (OptionNames.VERBOSE.equalsIgnoreCase(option)) {
                this.verbose = true;
                break;
            }
        }
    }
    

    public void process(FunctionCatalogDocumentation[] catalogs, File outputDir) {
        final Properties p = new Properties();
        final StringBuilder catalogNames = new StringBuilder();

        for (FunctionCatalogDocumentation catalog : catalogs) {
            final String catalogName = catalog.getName();
            if (this.verbose) {
                System.err.println("Processing catalog: " + catalogName);
            }
            catalogNames.append(" - ").append(catalogName);
            for (FunctionCategoryDocumentation category : catalog.getCategories().values()) {
                this.addCategory(category, p);
            }
        }

        try {
            final File outputFile = new File(outputDir, OUPUT_FILE_NAME);
            if (this.verbose) {
                System.err.println("Writing properties to: " + outputFile.getPath());
            }
            
            final OutputStream out = new FileOutputStream(outputFile);
            try {
                p.store(out, PROPERTIES_COMMENTS_PREFIX + catalogNames);
                out.flush();
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
    public void stop() {        
    }


}
