package com.tibco.cep.query.model;

/**
 * This interface is used in the Function Registry to hold the
 * function information for resolving purposes
 * @author pdhar
 *
 */
public interface Function extends RegistryContext, TypedContext {
    public static final char PATH_SEPARATOR_CHAR = '/';
    public static final String PATH_SEPARATOR = "/";
    public static final String ID_GEN_PREFIX = "$FN";

    public static enum FunctionType {
    FUNCTION_TYPE_AGGREGATE,
    FUNCTION_TYPE_RULE,
    FUNCTION_TYPE_CATALOG };


    public static String AGGREGATE_FUNCTION_PENDING_COUNT = TypeNames.PENDING_COUNT;
    public static String AGGREGATE_FUNCTION_COUNT = TypeNames.COUNT;
    public static String AGGREGATE_FUNCTION_SUM = TypeNames.SUM;
    public static String AGGREGATE_FUNCTION_AVG = TypeNames.AVG;
    public static String AGGREGATE_FUNCTION_MIN = TypeNames.MIN;
    public static String AGGREGATE_FUNCTION_MAX = TypeNames.MAX;

    static String[] AGGREGATE_FUNCTION_NAMES = {
        TypeNames.PENDING_COUNT,
        TypeNames.COUNT,
        TypeNames.SUM,
        TypeNames.AVG,
        TypeNames.MIN,
        TypeNames.MAX,
    };


    /**
     * Return the bare function name
     * @return String
     */
    public String getName();

    /**
     * Sets the function name
     * @param name
     */
    public void setName(String name);

    /**
     * Returns the Function Path
     * @return String
     */
    public String getPath();

    /**
     * Sets the function path
     * @param path
     */
    public void setPath(String path);

    /**
     * Return true if the function type matches the specified type
     * @return boolean
     */
    public FunctionType getFunctionType();

    /**
     * Set the function type
     * @param type
     */
    public void setFunctionType(Function.FunctionType type);


    /**
     * Returns true if the function has a return value, false if void
     * @return boolean
     */
    public boolean hasReturnType();

    /**
     * Return the implementing class
     * @return Class
     */
    public Class getImplClass() throws Exception;


    /**
     * Sets the implemented class
     * @param clazz
     */
    public void setImplClass(Class clazz);

    /**
     * Return the implementing class name
     * @return String
     */
    public String getImplClassName();

    /**
     * Return the array of Exception thrown by the function
     * @return Class[]
     */
    public Class[] getExceptions();

    /**
     * Return an array of Exception Class Names
     * @return String[]
     */
    public String[] getExceptionClassNames();

    /**
     * Add function exception thrown by the function
     * @param exception
     */
    public void addExceptions(Class exception);

    /**
     * Return the array of function arguments
     * @return FunctionArg[]
     */
    public FunctionArg[] getArguments();

    /**
     * Returns the number of arguments
     * @return int
     */
    public int getArgumentCount();



    /**
     * Add a function argument
     * @param name
     * @param type
     * @throws Exception
     * @return FunctionArg
     */
    public FunctionArg addFunctionArgument(String name, TypeInfo type) throws Exception;



    /**
     * Add a function argument
     * @param name
     * @param clazz
     * @throws Exception
     * @return FunctionArg
     */
    public FunctionArg addFunctionArgument(String name, Class clazz) throws Exception;



}
