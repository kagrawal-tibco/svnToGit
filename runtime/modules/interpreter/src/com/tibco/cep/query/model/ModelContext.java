package com.tibco.cep.query.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.query.model.visitor.HierarchicalContextVisitor;
import com.tibco.cep.query.utils.ObjectTreeNode;


public interface ModelContext extends ObjectTreeNode {

    int CTX_TYPE_ROOT = -1;
    int CTX_TYPE_SELECT = 0;
    int CTX_TYPE_PROJECTION_ATTRIBUTES = 1;
    int CTX_TYPE_FROM = 2;
    int CTX_TYPE_WHERE = 3;
    int CTX_TYPE_ENTITY = 4;
    int CTX_TYPE_FROM_SCOPE_PROJECTION = 5;
    int CTX_TYPE_ENTITY_SCOPE_PROJECTION = 6;
    int CTX_TYPE_EXPRESSION_SCOPE_PROJECTION = 7;
    int CTX_TYPE_PROJECTION_ELEMENT = 8;
    int CTX_TYPE_IDENTIFIER = 9;
    int CTX_TYPE_SCOPE_IDENTIFIER = 10;
    int CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER = 11;
    int CTX_TYPE_PATH_FUNCTION_IDENTIFIER = 12;
    int CTX_TYPE_BINARY_EXPRESSION = 13;
    int CTX_TYPE_PROJECTION = 14;
    int CTX_TYPE_OPERATOR = 15;
    int CTX_TYPE_ENTITY_PROPERTY = 16;
    int CTX_TYPE_ENTITY_ATTRIBUTE = 17;
    int CTX_TYPE_CATALOG_FUNCTION = 18;
    int CTX_TYPE_RULE_FUNCTION = 19;
    int CTX_TYPE_AGGREGATE_FUNCTION = 20;
    int CTX_TYPE_FUNCTION_REGISTRY = 21;
    int CTX_TYPE_FUNCTION_ARG = 22;
    int CTX_TYPE_PROJECT = 23;
    int CTX_TYPE_STREAM = 24;
    int CTX_TYPE_STREAM_POLICY = 25;
    int CTX_TYPE_BY_CLAUSE = 26;
    int CTX_TYPE_TUMBLING_WINDOW = 27;
    int CTX_TYPE_SLIDING_WINDOW = 28;
    int CTX_TYPE_TIME_WINDOW = 29;
    int CTX_TYPE_PURGE_CLAUSE = 30;
    int CTX_TYPE_CHAR_LITERAL = 31;
    int CTX_TYPE_BOOLEAN_LITERAL = 32;
    int CTX_TYPE_STRING_LITERAL = 33;
    int CTX_TYPE_NUMBER_LITERAL = 34;
    int CTX_TYPE_DATETIME_LITERAL = 35;
    int CTX_TYPE_NULL_LITERAL = 36;
    int CTX_TYPE_UNARY_EXPRESSION = 37;
    int CTX_TYPE_FIELD_LIST = 38;
    int CTX_TYPE_BIND_VARIABLE = 39;
    int CTX_TYPE_RUNTIME_EXPRESSION = 40;
    int CTX_TYPE_GROUP_CLAUSE = 41;
    int CTX_TYPE_GROUP_POLICY = 42;
    int CTX_TYPE_HAVING_CLAUSE = 43;
    int CTX_TYPE_ORDER_CLAUSE = 44;
    int CTX_TYPE_SORT_CRITERION = 45;
    int CTX_TYPE_ENTITY_REGISTRY = 46;
    int CTX_TYPE_ENTITY_ATTRIBUTE_PROXY = 47;
    int CTX_TYPE_ENTITY_PROPERTY_PROXY = 48;
    int CTX_TYPE_ARRAY_LENGTH_ATTRIBUTE = 49;
    int CTX_TYPE_ARRAY_EXPRESSION = 50;
    int CTX_TYPE_DISTINCT_CLAUSE = 51;
    int CTX_TYPE_IS_SET_ATTRIBUTE = 52;
    int CTX_TYPE_TYPE = 53;
    int CTX_TYPE_DELETE = 54;


    boolean isRootContext();


    /**
     * @param context the child context
     */
    void addChildContext(ModelContext context);


    /**
     * @return Map of name to ModelContext valid in this SelectContext
     */
    Map<Object,ModelContext> getContextMap();


    /**
     * Returns true if this context is the root context
     * @return boolean
     */
    RootContext getRootContext();

    /**
     * @return QueryContext that contains this ModelContext, or null if there is none.
     */
    QueryContext getOwnerContext();


    /**
     * Returns the common parent of this context and the specified context
     * @param ctx
     * @return ModelContext
     */
    ModelContext getCommonParent(ModelContext ctx);


    /**
     * @return the parent context
     */
    ModelContext getParentContext();


    /**
     * @param type of the context
     */
    ModelContext[] getDescendantContextsByType(int type);


    /**
     * @param type of the context
     */
    List<ModelContext> getDirectDescendantContextsByType(int type);


    /**
     * @return true if children present false otherwise
     */
    boolean hasChildren();


    /**
     * @return the children iterator
     */
    Iterator getChildrenIterator();


    /**
     * @return the number of children
     */
    int childrenCount();


    /**
     * @return the array of children
     */
    ModelContext[] getChildren();


    /**
     * @return the context type
     */
    int getContextType();   


    /**
     * @return com.tibco.cep.util.ResourceManager
     */
    com.tibco.cep.util.ResourceManager getResourceManager();


    /**
     * @return ProjectContext
     */
    ProjectContext getProjectContext();


    /**
     * visitor pattern accept
     *
     * @param v
     * @return boolean
     */
    boolean accept(HierarchicalContextVisitor v) throws Exception;


}
