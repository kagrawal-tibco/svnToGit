import com.sun.javadoc.*;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 */
public class FunctionHelpPropertiesDoclet extends Doclet {


    public static final String COMMON_PREFIX_OF_PROPERTIES = "be.help.functions.category.";
    public static final String FUNCTIONS_HELP_PROPERTIES_FILE_NAME = "functions-help.properties";
    public static final String DESTINATION_DIR_OPTION_NAME = "-d";
    public static final String TAG_NAME_FOR_CATEGORY = ".category";
    public static final String TAG_NAME_FOR_CAUTIONS = ".cautions";
    public static final String TAG_NAME_FOR_DESCRIPTION = ".description";
    public static final String TAG_NAME_FOR_DOMAIN = ".domain";
    public static final String TAG_NAME_FOR_EXAMPLE = ".example";
    public static final String TAG_NAME_FOR_MAPPER = ".mapper";
    public static final String TAG_NAME_FOR_NAME = ".name";
    public static final String TAG_NAME_FOR_RETURN = "return";
    public static final String TAG_NAME_FOR_SEE = ".see";
    public static final String TAG_NAME_FOR_SIGNATURE = ".signature";
    public static final String TAG_NAME_FOR_SYNOPSIS = ".synopsis";
    public static final String TAG_NAME_FOR_VERSION = ".version";

    protected static final Pattern PATTERN_FOR_TYPE_PLUS_COMMENT = Pattern.compile("^\\s*(\\w+(?:\\[])*)(?:\\s+(.*))?$", Pattern.DOTALL);
    protected static final String PROPERTIES_COMMENTS = "Function Properties";
    protected static final StringTemplate TEMPLATE_FOR_HTML_TOOLTIP;

    private static File outputFile;

    static {
        final StringTemplateGroup group = new StringTemplateGroup(FunctionHelpPropertiesDoclet.class.getName());
        TEMPLATE_FOR_HTML_TOOLTIP = group.getInstanceOf("tooltip");
    }

    private Properties properties;


    public FunctionHelpPropertiesDoclet(RootDoc root) {
        this.properties = new Properties();
        for (ClassDoc classDoc : root.classes()) {
            this.addProperties(classDoc);
        }
    }


    protected void addProperties(ClassDoc classDoc) {
        final Tag[] categoryTags = classDoc.tags(TAG_NAME_FOR_CATEGORY);
        if ((null != categoryTags) && (categoryTags.length > 0)) {
            final String categoryName = categoryTags[0].text().trim();
            this.addProperty(categoryName + ".clz", classDoc.typeName());
            this.addProperty(categoryName + ".name", categoryName);
            this.addProperty(categoryName + ".description", this.getTagValue(classDoc, TAG_NAME_FOR_SYNOPSIS));

            for (MethodDoc methodDoc : classDoc.methods()) {
                this.addProperties(categoryName, methodDoc);
            }
        }
    }


    protected void addProperties(String categoryName, MethodDoc methodDoc) {
        final Tag[] nameTags = methodDoc.tags(TAG_NAME_FOR_NAME);
        if ((null != nameTags) && (nameTags.length > 0)) {
            final String methodPrefix = categoryName + ".method." + nameTags[0].text();

            final String cautions = this.getTagValue(methodDoc, TAG_NAME_FOR_CAUTIONS);
            final String description = this.getTagValue(methodDoc, TAG_NAME_FOR_DESCRIPTION);
            final String domain = this.getTagValue(methodDoc, TAG_NAME_FOR_DOMAIN);
            final String example = this.getTagValue(methodDoc, TAG_NAME_FOR_EXAMPLE);
            final String mapper = this.getTagValue(methodDoc, TAG_NAME_FOR_MAPPER);
            final String name = this.getTagValue(methodDoc, TAG_NAME_FOR_NAME);
            final String fullName = categoryName + "." + name;
            final String see = this.getTagValue(methodDoc, TAG_NAME_FOR_SEE);
            final String signature = this.getTagValue(methodDoc, TAG_NAME_FOR_SIGNATURE);
            final String synopsis = this.getTagValue(methodDoc, TAG_NAME_FOR_SYNOPSIS);
            final String version = this.getTagValue(methodDoc, TAG_NAME_FOR_VERSION);

            final ParamDescriptor[] paramDescriptors = this.getParamDescriptors(methodDoc);

            final Tag[] returnTags = methodDoc.tags(TAG_NAME_FOR_RETURN);
            final String returnString;
            final ParamDescriptor returnDescriptor;
            if ((null != returnTags) && (returnTags.length > 0)) {
                returnDescriptor = this.getParamDescriptor("return", returnTags[0].text());
                returnString = returnDescriptor.getType();
            } else {
                returnDescriptor = null;
                returnString = null;
            }

            TEMPLATE_FOR_HTML_TOOLTIP.reset();
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("cautions", cautions);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("description", description);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("domain", domain);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("example", example);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("fullName", fullName);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("mapper", mapper);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("name", name);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("parameters", paramDescriptors);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("return", returnDescriptor);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("see", see);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("signature", signature);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("synopsis", synopsis);
            TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("version", version);
            final String tooltip = TEMPLATE_FOR_HTML_TOOLTIP.toString();

            addProperty(methodPrefix + ".cautions", cautions);
            addProperty(methodPrefix + ".description", description);
            addProperty(methodPrefix + ".domain", domain);
            addProperty(methodPrefix + ".example", example);
            addProperty(methodPrefix + ".fullName", fullName);
            addProperty(methodPrefix + ".mapper", mapper);
            int index = 0;
            for (ParamDescriptor paramDescriptor : paramDescriptors) {
                final String paramPrefix = methodPrefix + ".param." + index++;
                addProperty(paramPrefix + ".description", paramDescriptor.getDescription());
                addProperty(paramPrefix + ".name", paramDescriptor.getName());
                addProperty(paramPrefix + ".type", paramDescriptor.getType());

            }
            addProperty(methodPrefix + ".return", returnString);
            addProperty(methodPrefix + ".see", see);
            addProperty(methodPrefix + ".signature", signature);
            addProperty(methodPrefix + ".synopsis", synopsis);
            addProperty(methodPrefix + ".tooltip", tooltip);
            addProperty(methodPrefix + ".version", version);
        }
    }


    protected void addProperty(String name, String value) {
        if (null == value) {
            value = "";
        }
        this.properties.setProperty(COMMON_PREFIX_OF_PROPERTIES + name, value);
    }


    protected ParamDescriptor getParamDescriptor(ParamTag paramtag) {
        return this.getParamDescriptor(paramtag.parameterName(), paramtag.parameterComment());
    }


    protected ParamDescriptor getParamDescriptor(String name, String comment) {
        final Matcher matcher = PATTERN_FOR_TYPE_PLUS_COMMENT.matcher(comment);
        String description, type;
        if (matcher.matches()) {
            type = matcher.group(1);
            description = matcher.group(2);
        } else {
            type = "";
            description = "";
        }
        return new ParamDescriptor(name, type, description);
    }


    protected ParamDescriptor[] getParamDescriptors(MethodDoc methodDoc) {
        final ParamTag[] paramTags = methodDoc.paramTags();
        final ParamDescriptor[] descriptors = new ParamDescriptor[paramTags.length];
        int i = 0;
        for (ParamTag paramTag : methodDoc.paramTags()) {
            descriptors[i++] = this.getParamDescriptor(paramTag);
        }
        return descriptors;
    }


    public Properties getProperties() {
        return this.properties;
    }


    protected String getTagValue(Doc doc, String tagName) {
        final Tag[] tags = doc.tags(tagName);
        if ((null == tags) || (tags.length == 0)) {
            return null;
        } else {
            final String text = tags[0].text();
            if (null == text) {
                return "";
            } else {
                return text;
            }
        }
    }


    private class ParamDescriptor {

        private String name;
        private String type;
        private String description;

        private ParamDescriptor(String name, String type, String description) {
            this.description = description;
            this.name = name;
            this.type = type;
        }

        public String getDescription() {
            return this.description;
        }

        public String getName() {
            return this.name;
        }

        public String getType() {
            return this.type;
        }

    }


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


    public static void main(String[] args) {
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
        if ((null != option)
                && (option.equals(DESTINATION_DIR_OPTION_NAME)
                || option.startsWith(DESTINATION_DIR_OPTION_NAME + " "))) {
            return 2;
        }
        return 0;
    }

    /**
     * @param root RootDoc which represents the root of the program structure information for one run of javadoc.
     * @return true on success.
     */
    public static boolean start(RootDoc root) {
        final FunctionHelpPropertiesDoclet doclet = new FunctionHelpPropertiesDoclet(root);
        final Properties props = doclet.getProperties();

        try {
            final OutputStream out = new FileOutputStream(outputFile);
            props.store(out, PROPERTIES_COMMENTS);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        if (null != options) {
            for (String[] option : options) {
                if ((null != option)
                        && (option.length > 0)
                        && (DESTINATION_DIR_OPTION_NAME.equals(option[0]))) {
                    final File outputDir = new File(option[1]);
                    if (outputDir.isDirectory()) {
                        outputFile = new File(outputDir, FUNCTIONS_HELP_PROPERTIES_FILE_NAME);
                        return true;
                    }//if
                    break;
                }//if
            }//for
        }//if

        reporter.printError("The " + FunctionHelpPropertiesDoclet.class.getName()
                + " doclet requires " + DESTINATION_DIR_OPTION_NAME + " <directory>");
        return false;
    }

}
