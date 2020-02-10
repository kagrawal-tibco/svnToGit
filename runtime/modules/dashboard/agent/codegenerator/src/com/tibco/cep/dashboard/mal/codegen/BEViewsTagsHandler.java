package com.tibco.cep.dashboard.mal.codegen;

import java.util.Properties;

import xdoclet.XDocletException;
import xdoclet.XDocletMessages;
import xdoclet.tagshandler.TypeTagsHandler;
import xdoclet.template.TemplateException;
import xdoclet.util.Translator;
import xjavadoc.XClass;

/**
 * @xdoclet.taghandler namespace="Syn"
 */
public class BEViewsTagsHandler extends TypeTagsHandler {

    /**
     * Outputs the instance name of a class (ffirst char to lower case).
     * @param template              The body of the block tag
     * @exception XDocletException  if something goes wrong
     * @doc.tag                     type="block"
     */
    public void className(String template) throws XDocletException {
        try {
            String fullClassName = getEngine().outputOf(template);
            String name = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            if (isArray(name)) {
                name = name.substring(0, name.length()-2);
            }

            getEngine().print(name.substring(0, 1).toUpperCase() + name.substring(1));
        }
        catch (TemplateException ex) {
            throw new XDocletException(ex, Translator.getString(XDocletMessages.class, XDocletMessages.METHOD_FAILED, new String[]{"SynderaTagsHandler.instanceName"}));
        }
    }

    /**
     * Outputs the model name of a class (removes MAL).
     * @param template              The body of the block tag
     * @exception XDocletException  if something goes wrong
     * @doc.tag                     type="block"
     */
    public void modelName(String template) throws XDocletException {
        try {
            String fullClassName = getEngine().outputOf(template);
            /*String name = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            if (isArray(name)) {
                name = name.substring(0, name.length()-2);
            }

            getEngine().print(name.substring(0, 1).toUpperCase() + name.substring(1));
            */
            getEngine().print(fullClassName.substring(3));
        }
        catch (TemplateException ex) {
            throw new XDocletException(ex, Translator.getString(XDocletMessages.class, XDocletMessages.METHOD_FAILED, new String[]{"SynderaTagsHandler.instanceName"}));
        }
    }

    /**
     * Outputs the model name of a class (removes MAL).
     * @param template              The body of the block tag
     * @exception XDocletException  if something goes wrong
     * @doc.tag                     type="block"
     */
    public void ucfirst(String template) throws XDocletException {
        try {
            String name = getEngine().outputOf(template);
            /*String name = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            if (isArray(name)) {
                name = name.substring(0, name.length()-2);
            }

            getEngine().print(name.substring(0, 1).toUpperCase() + name.substring(1));
            */
            getEngine().print(name.substring(0, 1).toUpperCase() + name.substring(1));
        }
        catch (TemplateException ex) {
            throw new XDocletException(ex, Translator.getString(XDocletMessages.class, XDocletMessages.METHOD_FAILED, new String[]{"SynderaTagsHandler.instanceName"}));
        }
    }

    /**
     * Outputs the model name of a class (removes MAL).
     * @param template              The body of the block tag
     * @exception XDocletException  if something goes wrong
     * @doc.tag                     type="block"
     */
    public void modelInstanceName(String template) throws XDocletException {
        try {
            String fullClassName = getEngine().outputOf(template);
            /*String name = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            if (isArray(name)) {
                name = name.substring(0, name.length()-2);
            }

            getEngine().print(name.substring(0, 1).toUpperCase() + name.substring(1));
            */
            String modelName = fullClassName.substring(3);
            getEngine().print(modelName.substring(0, 1).toLowerCase() + modelName.substring(1));
        }
        catch (TemplateException ex) {
            throw new XDocletException(ex, Translator.getString(XDocletMessages.class, XDocletMessages.METHOD_FAILED, new String[]{"SynderaTagsHandler.instanceName"}));
        }
    }

    /**
     * Outputs the instance name of a class (ffirst char to lower case).
     * @param template              The body of the block tag
     * @exception XDocletException  if something goes wrong
     * @doc.tag                     type="block"
     */
    public void instanceName(String template) throws XDocletException {
        try {
            String fullClassName = getEngine().outputOf(template);
            String name = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            String result = name.substring(0, 1).toLowerCase() + name.substring(1);
            if (isArray(result)) {
                result = result.substring(0, result.length()-2);
            }

            getEngine().print(result);
        }
        catch (TemplateException ex) {
            throw new XDocletException(ex, Translator.getString(XDocletMessages.class, XDocletMessages.METHOD_FAILED, new String[]{"SynderaTagsHandler.instanceName"}));
        }
    }

    /**
     * Gets the PrimitiveOrString attribute of the TypeTagsHandler class
     * 
     * @param value
     *            Describe what the parameter does
     * @return The PrimitiveOrString value
     */
    private static boolean isPrimitiveOrString(String value) {
        return isPrimitiveType(value) || value.equals("java.lang.String")
                || value.equals("String");
    }

    /**
     * Tests if name is an array (ends with [])
     * 
     * @param name
     *            The name of the type.
     * @return true if name is an array.
     */
    private static boolean isArray(String name) {
        return (name.indexOf('[') == name.length() - 2)
                && (name.indexOf(']') == name.length() - 1);
    }

    /**
     * Tests if name is a primitive type and is an array (ends with [])
     * 
     * @param name
     *            The name of the type.
     * @return true if name is an array of a primitive type.
     * @see #isPrimitiveType
     */
    public static boolean isPrimitiveOrStringArray(String name) {
        return (name.indexOf('[') == name.length() - 2)
                && (name.indexOf(']') == name.length() - 1)
                && (isPrimitiveOrString(name.substring(0, name.length()-2)));
    }

    /**
     * Evaluate the body block if current class has a super class (not java.lang.Object).
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void ifHasSuperClass(String template) throws XDocletException
    {
        XClass superClass = getCurrentClass().getSuperclass();
        if (superClass != null && !superClass.getName().endsWith("MALElement")) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if current class has a super class (not java.lang.Object).
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void ifHasNotSuperClass(String template) throws XDocletException
    {
        XClass superClass = getCurrentClass().getSuperclass();
        if (superClass != null && superClass.getName().endsWith("MALElement")) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the test is true.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the attribute name."
     */
    public void ifCompare(String template,
            Properties attributes) throws XDocletException {
        String lhs = attributes.getProperty("lhs");
        String rhs = attributes.getProperty("rhs");
        String op = attributes.getProperty("op");
        if ("eq".equals(op) && lhs.equals(rhs)) {
            generate(template);
        } else if ("neq".equals(op) && !lhs.equals(rhs)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is "name".
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the attribute name."
     */
    public void ifIsNameAttribute(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if ("name".equals(value)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is "description".
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the attribute name."
     */
    public void ifIsDescriptionAttribute(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if ("description".equals(value)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is "name" or "description".
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the attribute name."
     */
    public void ifIsNotNameOrDescriptionAttribute(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (!"name".equals(value) && !"description".equals(value)) {
            generate(template);
        }
    }
    
    /**
     * Evaluate the body block if the value is "name".
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the attribute name."
     */
    public void ifIsNotNameAttribute(String template, Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (!"name".equals(value)) {
            generate(template);
        }
    }    

    /**
     * Evaluate the body block if the value is of an array type.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @see #ifIsNotPrimitiveArray(java.lang.String,java.util.Properties)
     * @see #isPrimitiveArray(java.lang.String)
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the type name."
     */
    public void ifIsArray(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (isArray(value)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is not of an array type.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @see #ifIsNotPrimitiveArray(java.lang.String,java.util.Properties)
     * @see #isPrimitiveArray(java.lang.String)
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the type name."
     */
    public void ifIsNotArray(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (!isArray(value)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is of a primitive array type.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @see #ifIsNotPrimitiveArray(java.lang.String,java.util.Properties)
     * @see #isPrimitiveArray(java.lang.String)
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the type name."
     */
    public void ifIsPrimitiveOrStringArray(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (isPrimitiveOrStringArray(value)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is not of a primitive/String array
     * type.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @see #ifIsPrimitiveArray(java.lang.String,java.util.Properties)
     * @see #isPrimitiveArray(java.lang.String)
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the type name."
     */
    public void ifIsNotPrimitiveOrStringArray(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (!isPrimitiveOrStringArray(value)) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is of an enum type.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the type name."
     */
    public void ifIsEnum(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (value.endsWith("Enum")) {
            generate(template);
        }
    }

    /**
     * Evaluate the body block if the value is not of a primitivr or enum type.
     * 
     * @param template
     *            The body of the block tag
     * @param attributes
     *            The attributes of the template tag
     * @exception XDocletException
     *                Description of Exception
     * @doc.tag type="block"
     * @doc.param name="value" optional="false" description="A string
     *            containsing the type name."
     */
    public void ifIsStructure(String template,
            Properties attributes) throws XDocletException {
        String value = attributes.getProperty("value");
        if (!isPrimitiveOrString(value) && !value.endsWith("Enum")) {
            generate(template);
        }
    }
    
    /**
     * Returns the model class name for the current MAL class.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void modelClass(Properties attributes) throws XDocletException{
        String className = getCurrentClass().getName();
        getEngine().print(className.substring(3));
    }
    
    /**
     * Returns the model instance name for the current MAL class.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void modelInstance(Properties attributes) throws XDocletException{
        String modelName = getCurrentClass().getName();
        if (modelName.startsWith("MAL")){
        	modelName = modelName.substring(3);
        }
        getEngine().print(modelName.substring(0, 1).toLowerCase() + modelName.substring(1));
    }
    
    /**
     * Returns the model instance name for the current MAL class.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void paramModel(Properties attributes) throws XDocletException{
        String paramName = getCurrentMethod().getPropertyName();
        getEngine().print(paramName.substring(0, 1).toUpperCase() + paramName.substring(1));
    }
    
    /**
     * Returns the model instance name for the current MAL class.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void propertyName(Properties attributes) throws XDocletException{
        String paramName = getCurrentMethod().getPropertyName();
        getEngine().print(paramName.substring(0, 1).toUpperCase() + paramName.substring(1));
    }
    
    /**
     * Returns the model instance name for the current MAL class.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void propertyType(Properties attributes) throws XDocletException{
        String paramType = getCurrentMethod().getPropertyType().getType().getName();
        if (paramType.startsWith("MAL")) {
        	getEngine().print(paramType.substring(3));	
        }
        else {
        	getEngine().print(paramType);
        }
    }
    
    /**
     * Returns the model instance name for the current MAL class.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void enumType(Properties attributes) throws XDocletException{
        String paramType = getCurrentMethod().getPropertyType().getType().getName();
        getEngine().print(paramType);
    }
    
    /**
     * Returns the get method name for the current method.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void methodGet(Properties attributes) throws XDocletException{
        String methodName = getCurrentMethod().getPropertyName();
        String propertyType = getCurrentMethod().getPropertyType().getType().getName();
        if (propertyType.equals("boolean")){
            getEngine().print("is" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
        }
        else {
            getEngine().print("get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
        }
    }

    /**
     * Returns the set method name for the current method.
     *
     * @param template              The body of the block tag
     * @exception XDocletException  Description of Exception
     * @doc.tag                     type="block"
     */
    public void methodSet(Properties attributes) throws XDocletException{
        String methodName = getCurrentMethod().getPropertyName();
        getEngine().print("set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1));
    }
}
