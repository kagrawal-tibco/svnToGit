import com.sun.javadoc.*;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.data.primitive.ExpandedName;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;

import org.xml.sax.InputSource;

public class FunctionCatalogDocumentationLoader {

    protected static final XiParser PARSER = XiParserFactory.newInstance();
    protected static final Pattern PATTERN_FOR_TYPE_PLUS_COMMENT = Pattern.compile("^\\s*(\\w+(?:\\[\\])*)(?:\\s+(.*))?$", Pattern.DOTALL);
    protected static final String REGEX_FOR_SUBCATEGORY_SEPARATOR = Pattern.quote(".");

    private static final ExpandedName XNAME_CATALOG = ExpandedName.makeName("catalog");
    private static final ExpandedName XNAME_CATEGORY = ExpandedName.makeName("category");
    private static final ExpandedName XNAME_CLASS = ExpandedName.makeName("class");
    private static final ExpandedName XNAME_ENABLED = ExpandedName.makeName("enabled");
    private static final ExpandedName XNAME_FUNCTION = ExpandedName.makeName("function");
    private static final ExpandedName XNAME_NAME = ExpandedName.makeName("name");

    private RootDoc rootDoc;
    private String catalogName;
    private Map<String, List<String>> classNameToFunctionNames = new HashMap<String, List<String>>();


    public FunctionCatalogDocumentationLoader(File catalogFile, RootDoc root) throws Exception {
        final FileInputStream fis = new FileInputStream(catalogFile);
        try {
            this.readCatalogXml(PARSER.parse(new InputSource(fis)));
        } finally {
            fis.close();
        }
        this.rootDoc = root;
    }


    protected FunctionCatalogDocumentation getCatalog() {
        final FunctionCatalogDocumentation catalog = new FunctionCatalogDocumentation(catalogName);
        for (ClassDoc classDoc : this.rootDoc.classes()) {
            this.readClass(classDoc, catalog);
        }
        return catalog;
    }


    protected String getTagValue(Doc doc, String tagName) {
        final Tag[] tags = doc.tags(tagName);
        if ((null == tags) || (tags.length == 0)) {
            return null;
        } else {
            final String text = tags[0].text();
            if ((null == text) || "".equals(text.trim())) {
                return null;
            } else {
                return text;
            }
        }
    }


    protected void readClass(ClassDoc classDoc, FunctionCatalogDocumentation catalog) {

        final String className = classDoc.qualifiedName();
        final List<String> functionNames = this.classNameToFunctionNames.get(className);
        if ((null == functionNames) || (functionNames.size() == 0)) {
//            System.out.println("Ignored class: " + className);
            return;
//        } else {
//            System.out.println("Class: " + className + " ok for functions: " + functionNames);
        }

        final Tag[] categoryTags = classDoc.tags(FunctionJavadocTagNames.CATEGORY);
        if ((null != categoryTags) && (categoryTags.length > 0)) {

            // Gets the category, creating it if needed, and creating any parent category that did not already exist.
            FunctionCategoryDocumentation category = null;
            final String categoryName = categoryTags[0].text().trim();
            for (String name : categoryName.split(REGEX_FOR_SUBCATEGORY_SEPARATOR)) {
                FunctionCategoryDocumentation subCategory;
                if (null == category) {
                    subCategory = catalog.getCategories().get(name);
                } else {
                    subCategory = category.getSubCategories().get(name);
                }
                if (null == subCategory) {
                    subCategory = new FunctionCategoryDocumentation(name, null, null, category);
                    if (null == category) {
                        catalog.getCategories().put(name, subCategory);
                    } else {
                        category.getSubCategories().put(name, subCategory);
                    }
                }
                category = subCategory;
            }

            // Updates the category.
            if (category != null) {
                category.setClassName(classDoc.qualifiedName());
                category.setDescription(this.getTagValue(classDoc, FunctionJavadocTagNames.SYNOPSIS));

                // Loads the functions into the category.
                final SortedMap<String, FunctionDocumentation> functions = category.getFunctions();
                for (MethodDoc methodDoc : classDoc.methods()) {
                    final FunctionDocumentation f = this.makeFunction(methodDoc);
                    if (null != f) {
                        final String functionName = f.getName();
                        if (functionNames.contains(categoryName + "." + functionName)) {
                            functions.put(functionName, f);
//                        } else {
//                            System.out.println("Ignored function: " + categoryName + "." + functionName);
                        }
                    }
                }
            }
        }
    }


    protected FunctionDocumentation makeFunction(MethodDoc methodDoc) {
        final Tag[] nameTags = methodDoc.tags(FunctionJavadocTagNames.NAME);
        if ((null != nameTags) && (nameTags.length > 0)) {

            final String name = this.getTagValue(methodDoc, FunctionJavadocTagNames.NAME);
//            System.out.println("  Function: " + name);

            final Tag[] returnTags = methodDoc.tags(FunctionJavadocTagNames.RETURN);
            final FunctionParameterDocumentation returnType;
            if ((null != returnTags) && (returnTags.length > 0)) {
                returnType = this.makeParameter("return", returnTags[0].text());
            } else {
                returnType = null;
            }

            final FunctionDocumentation function = new FunctionDocumentation(name,
                    returnType,
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.SIGNATURE),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.SYNOPSIS),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.DESCRIPTION),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.DOMAIN),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.MAPPER),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.SEE),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.CAUTIONS),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.EXAMPLE),
                    this.getTagValue(methodDoc, FunctionJavadocTagNames.VERSION));

            final LinkedHashMap<String, FunctionParameterDocumentation> args = function.getArguments();
            for (ParamTag paramTag : methodDoc.paramTags()) {
                final FunctionParameterDocumentation arg = this.makeParameter(paramTag);
                args.put(arg.getName(), arg);
            }

            return function;
        }
        return null;
    }


    protected FunctionParameterDocumentation makeParameter(ParamTag paramtag) {
        final String name = paramtag.parameterName();
        return this.makeParameter(name, paramtag.parameterComment());
    }


    protected FunctionParameterDocumentation makeParameter(String name, String comment) {
        final Matcher matcher = PATTERN_FOR_TYPE_PLUS_COMMENT.matcher(comment);
        String description, type;
        if (matcher.matches()) {
            type = matcher.group(1);
            description = matcher.group(2);
        } else {
            type = "";
            description = "";
        }
        return new FunctionParameterDocumentation(name, type, description);
    }


    private void readCatalogXml(XiNode xml) {
        this.classNameToFunctionNames = new HashMap<String, List<String>>();
        final XiNode catalogNode = XiChild.getChild(xml, XNAME_CATALOG);
        if (XiChild.getBoolean(catalogNode, XNAME_ENABLED, true)) {
            this.catalogName = catalogNode.getAttributeStringValue(XNAME_NAME);
            for (Iterator it = XiChild.getIterator(catalogNode, XNAME_CATEGORY); it.hasNext(); ) {
                this.readCategoryXml((XiNode) it.next(), "");
            }
        }
    }


    private void readCategoryXml(XiNode xml, String baseName) {
        if (XiChild.getBoolean(xml, XNAME_ENABLED, true)) {
            baseName = baseName + XiChild.getString(xml, XNAME_NAME) + ".";

            for (Iterator it = XiChild.getIterator(xml, XNAME_CATEGORY); it.hasNext(); ) {
                this.readCategoryXml((XiNode) it.next(), baseName);
            }
            for (Iterator it = XiChild.getIterator(xml, XNAME_FUNCTION); it.hasNext(); ) {
                this.readFunctionXml((XiNode) it.next(), baseName);
            }
        }
    }

    
    private void readFunctionXml(XiNode xml, String baseName) {
        if (XiChild.getBoolean(xml, XNAME_ENABLED, true)) {
            final String className = XiChild.getString(xml, XNAME_CLASS);
            final String functionName = XiChild.getString(xml, XNAME_NAME);
            if ((null != className) && (null != functionName)) {
                List<String> functionNames = this.classNameToFunctionNames.get(className);
                if (null == functionNames) {
                    functionNames = new ArrayList<String>();
                    this.classNameToFunctionNames.put(className, functionNames);
                }
                functionNames.add(baseName + functionName);
//                System.out.println("Adding to " + className + ": " + baseName + functionName);
            }
        }
    }


}
