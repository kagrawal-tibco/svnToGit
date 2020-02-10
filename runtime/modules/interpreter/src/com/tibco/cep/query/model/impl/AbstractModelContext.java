package com.tibco.cep.query.model.impl;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.antlr.runtime.tree.BaseTree;

import com.tibco.cep.kernel.service.logging.LogManager;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.model.Aliased;
import com.tibco.cep.query.model.AliasedIdentifier;
import com.tibco.cep.query.model.BinaryExpression;
import com.tibco.cep.query.model.DateTimeLiteral;
import com.tibco.cep.query.model.DeleteContext;
import com.tibco.cep.query.model.Function;
import com.tibco.cep.query.model.FunctionArg;
import com.tibco.cep.query.model.GroupPolicy;
import com.tibco.cep.query.model.Identifier;
import com.tibco.cep.query.model.Literal;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.NamedContext;
import com.tibco.cep.query.model.ProjectContext;
import com.tibco.cep.query.model.QueryContext;
import com.tibco.cep.query.model.RootContext;
import com.tibco.cep.query.model.ScopedIdentifier;
import com.tibco.cep.query.model.SelectContext;
import com.tibco.cep.query.model.SlidingWindow;
import com.tibco.cep.query.model.SortCriterion;
import com.tibco.cep.query.model.TimeWindow;
import com.tibco.cep.query.model.TumblingWindow;
import com.tibco.cep.query.model.UnaryExpression;
import com.tibco.cep.query.model.visitor.FilteredHierarchicalContextVisitor;
import com.tibco.cep.query.model.visitor.HierarchicalContextVisitor;
import com.tibco.cep.query.utils.ObjectTreeNode;
import com.tibco.cep.util.ResourceManager;

/**
 * @author pdhar
 *
 */
public abstract class AbstractModelContext implements ModelContext {
    public static final String MSG_PROPERTIES = "com.tibco.cep.query.messages";
    protected static final HashMap<Integer,String> contextStr= new HashMap<Integer,String>();
    static {
            contextStr.put(CTX_TYPE_ROOT , "CTX_TYPE_ROOT");
            contextStr.put(CTX_TYPE_SELECT , "CTX_TYPE_SELECT");
            contextStr.put(CTX_TYPE_DELETE, "CTX_TYPE_DELETE");
            contextStr.put(CTX_TYPE_PROJECTION_ATTRIBUTES , "CTX_TYPE_PROJECTION_ATTRIBUTES");
            contextStr.put(CTX_TYPE_FROM , "CTX_TYPE_FROM");
            contextStr.put(CTX_TYPE_WHERE , "CTX_TYPE_WHERE");
            contextStr.put(CTX_TYPE_ENTITY , "CTX_TYPE_ENTITY");
            contextStr.put(CTX_TYPE_FROM_SCOPE_PROJECTION , "CTX_TYPE_FROM_SCOPE_PROJECTION");
            contextStr.put(CTX_TYPE_ENTITY_SCOPE_PROJECTION , "CTX_TYPE_ENTITY_SCOPE_PROJECTION");
            contextStr.put(CTX_TYPE_EXPRESSION_SCOPE_PROJECTION , "CTX_TYPE_EXPRESSION_SCOPE_PROJECTION");
            contextStr.put(CTX_TYPE_PROJECTION_ELEMENT , "CTX_TYPE_PROJECTION_ELEMENT");
            contextStr.put(CTX_TYPE_IDENTIFIER , "CTX_TYPE_IDENTIFIER");
            contextStr.put(CTX_TYPE_SCOPE_IDENTIFIER , "CTX_TYPE_SCOPE_IDENTIFIER");
            contextStr.put(CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER , "CTX_TYPE_AGGREGATE_FUNCTION_IDENTIFIER");
            contextStr.put(CTX_TYPE_PATH_FUNCTION_IDENTIFIER , "CTX_TYPE_PATH_FUNCTION_IDENTIFIER");
            contextStr.put(CTX_TYPE_BINARY_EXPRESSION , "CTX_TYPE_BINARY_EXPRESSION");
            contextStr.put(CTX_TYPE_PROJECTION , "CTX_TYPE_PROJECTION");
            contextStr.put(CTX_TYPE_OPERATOR , "CTX_TYPE_OPERATOR");
            contextStr.put(CTX_TYPE_ENTITY_PROPERTY , "CTX_TYPE_ENTITY_PROPERTY");
            contextStr.put(CTX_TYPE_ENTITY_ATTRIBUTE , "CTX_TYPE_ENTITY_ATTRIBUTE");
            contextStr.put(CTX_TYPE_CATALOG_FUNCTION , "CTX_TYPE_CATALOG_FUNCTION");
            contextStr.put(CTX_TYPE_RULE_FUNCTION , "CTX_TYPE_RULE_FUNCTION");
            contextStr.put(CTX_TYPE_AGGREGATE_FUNCTION , "CTX_TYPE_AGGREGATE_FUNCTION");
            contextStr.put(CTX_TYPE_FUNCTION_REGISTRY , "CTX_TYPE_FUNCTION_REGISTRY");
            contextStr.put(CTX_TYPE_FUNCTION_ARG , "CTX_TYPE_FUNCTION_ARG");
            contextStr.put(CTX_TYPE_PROJECT , "CTX_TYPE_PROJECT");
            contextStr.put(CTX_TYPE_STREAM , "CTX_TYPE_STREAM");
            contextStr.put(CTX_TYPE_STREAM_POLICY , "CTX_TYPE_STREAM_POLICY");
            contextStr.put(CTX_TYPE_BY_CLAUSE , "CTX_TYPE_BY_CLAUSE");
            contextStr.put(CTX_TYPE_TUMBLING_WINDOW , "CTX_TYPE_TUMBLING_WINDOW");
            contextStr.put(CTX_TYPE_SLIDING_WINDOW , "CTX_TYPE_SLIDING_WINDOW");
            contextStr.put(CTX_TYPE_TIME_WINDOW , "CTX_TYPE_TIME_WINDOW");
            contextStr.put(CTX_TYPE_PURGE_CLAUSE , "CTX_TYPE_PURGE_CLAUSE");
            contextStr.put(CTX_TYPE_CHAR_LITERAL , "CTX_TYPE_CHAR_LITERAL");
            contextStr.put(CTX_TYPE_BOOLEAN_LITERAL , "CTX_TYPE_BOOLEAN_LITERAL");
            contextStr.put(CTX_TYPE_STRING_LITERAL , "CTX_TYPE_STRING_LITERAL");
            contextStr.put(CTX_TYPE_NUMBER_LITERAL , "CTX_TYPE_NUMBER_LITERAL");
            contextStr.put(CTX_TYPE_DATETIME_LITERAL , "CTX_TYPE_DATETIME_LITERAL");
            contextStr.put(CTX_TYPE_NULL_LITERAL , "CTX_TYPE_NULL_LITERAL");
            contextStr.put(CTX_TYPE_UNARY_EXPRESSION , "CTX_TYPE_UNARY_EXPRESSION");
            contextStr.put(CTX_TYPE_FIELD_LIST , "CTX_TYPE_FIELD_LIST");
            contextStr.put(CTX_TYPE_BIND_VARIABLE,"CTX_TYPE_BIND_VARIABLE");
        contextStr.put(CTX_TYPE_GROUP_CLAUSE,"CTX_TYPE_GROUP_CLAUSE");
        contextStr.put(CTX_TYPE_GROUP_POLICY,"CTX_TYPE_GROUP_POLICY");
        contextStr.put(CTX_TYPE_HAVING_CLAUSE,"CTX_TYPE_HAVING_CLAUSE");
        contextStr.put(CTX_TYPE_ORDER_CLAUSE,"CTX_TYPE_ORDER_CLAUSE");
    }



    protected RootContext rootContext = null;
    protected List<ModelContext> childContext = new LinkedList<ModelContext>();
    protected ModelContext parentContext = null;
    protected ResourceManager resourcemanager = ResourceManager.getInstance();
    protected Logger logger;


    /**
     * ctor
     * @param context
     */
    protected AbstractModelContext(ModelContext context) {
        if (context != null) {
            if (context instanceof RootContext) {
                rootContext = (RootContext) context;
            } else {
                rootContext = context.getRootContext();
            }
            parentContext = context;
            parentContext.addChildContext(this);
        } else {
            rootContext = (RootContext) this;
            parentContext = this;
        }
        LogManager logManager = LogManagerFactory.getLogManager();
        this.logger = logManager.getLogger(this.getClass());
        resourcemanager.addResourceBundle(MSG_PROPERTIES, Locale.getDefault());
    }



    /**
     * @return the rootContext
     */
    public RootContext getRootContext() {
        return rootContext;
    }

    /**
     * @return SelectContext that contains this ModelContext, or null if there is none.
     */
    public QueryContext getOwnerContext() {
        if ((null == this.parentContext) || (this == parentContext)) {
            return null;
        }
        if (this.parentContext instanceof SelectContext || this.parentContext instanceof DeleteContext) {
            return (QueryContext)this.parentContext;
        }
        return this.parentContext.getOwnerContext();
    }

    /**
     * Returns the common parent of this context and the specified context
     * @param ctx
     * @return ModelContext
     */
    public ModelContext getCommonParent(ModelContext ctx) {
        for( ModelContext context = this; context != ctx.getRootContext(); context = context.getParentContext()) {
            if(ctx.getParentContext() == context.getParentContext()) {
                return ctx.getParentContext();
            }
        }
        return null;
    }

    /**
     * Returns true if this context is the root context
     * @return boolean
     */
    public boolean isRootContext() {
        return (this == getRootContext());
    }


    /**
     * Default implementation which returns this.getRootContext().getProjectContext();
     * @return ProjectContext for this AbstractModelContext
     */
    public ProjectContext getProjectContext() {
        return this.getRootContext().getProjectContext();
    }



    public ModelContext getParentContext() {
        return parentContext;
    }

    /**
     * @param parentContext the parentContext to set
     */
    protected void setParentContext(ModelContext parentContext) {
        this.parentContext = parentContext;
    }


    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#addChildContext(com.tibco.cep.query.ModelContext)
      */
    public void addChildContext(ModelContext context) {
        if (context instanceof NamedContext) {
            NamedContext nc = (NamedContext) context;
            this.getContextMap().put(nc.getContextId(), context);
        }
        childContext.add(context);
    }

    public ModelContext getNamedContext(String name) {
        return this.getContextMap().get(name);
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#getContextById(int)
      */
    public ModelContext[] getDescendantContextsByType(int type) {
        return getDescendantContextsByType(getRootContext(),type);
    }

    public List<ModelContext> getDirectDescendantContextsByType(int type) {
        final List<ModelContext> result = new ArrayList<ModelContext>();
        for (Iterator it = this.getChildrenIterator(); it.hasNext(); ) {
            final Object child = it.next();
            if ((child instanceof ModelContext) && ((ModelContext) child).getContextType() == type) {
                result.add((ModelContext) child);
            }
        }
        return result;
    }


    /* (non-Javadoc)
    * @see com.tibco.cep.query.ModelContext#getContextById(int)
    */
   public ModelContext[] getDescendantContextsByType(ModelContext context, int type) {
       ArrayList c = new ArrayList();
       lookupChildren(context,type,c);
       return (ModelContext[]) c.toArray(new ModelContext[c.size()]);
   }

    /**
     * recursive search from a given context
     * @param qc
     * @param type
     * @param list
     */
    private void lookupChildren(ModelContext qc, int type, ArrayList list) {
        if (qc.getContextType() == type) {
            list.add(qc);
        }
        for (Iterator iter = qc.getChildrenIterator(); iter.hasNext();) {
            ModelContext element = (ModelContext) iter.next();
            lookupChildren(element, type, list);
        }
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#getChildrenIterator()
      */
    public Iterator getChildrenIterator() {
        return childContext.iterator();
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#childrenCount()
      */
    public int childrenCount() {
        return childContext.size();
    }
    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#getChildren()
      */
    public ModelContext[] getChildren() {
        return (ModelContext[]) childContext.toArray(new ModelContext[childContext.size()]);
    }

    /* (non-Javadoc)
      * @see com.tibco.cep.query.ModelContext#hasChildren()
      */
    public boolean hasChildren() {
        return (childContext.size() > 0);
    }




    /**
     * @return com.tibco.cep.util.ResourceManager
     */
    public ResourceManager getResourceManager() {
        return this.resourcemanager;
    }



//    /* (non-Javadoc)
//      * @see com.tibco.cep.query.ast.ModelContext#isResolved()
//      */
//    public boolean isResolved() {
//        return this.isResolved;
//    }
//    public boolean isResolved() throws Exception{
//        boolean childrenResolved = true;
//        if(this.getProjectContext().getOntology() == null) {
//            throw new ResolveException(this,"Null.Ontology");
//        }
//        for (Iterator iter = getChildrenIterator(); iter.hasNext();) {
//            ModelContext element = (ModelContext) iter.next();
//                if (!element.isResolved()) {
//                    childrenResolved = false;
//                    break;
//                }
//        }
//        return this.isResolved && childrenResolved;
//    }



//    protected boolean resolveChildren() throws Exception {
//        boolean childrenResolved = true;
//        try {
//            for (Iterator iter = getChildrenIterator(); iter.hasNext();) {
//                ModelContext element = (ModelContext) iter.next();
//                if (!element.isResolved()) {
//                    if(element.resolve()) {
//                        element.setResolved(true);
//                    } else  {
//                        element.setResolved(false);
//                        childrenResolved = false;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            throw new ResolveException(this, e);
//        }
//        return childrenResolved;
//    }


//    /* (non-Javadoc)
//      * @see com.tibco.cep.query.ast.ModelContext#setResolved(boolean)
//      */
//    public void setResolved(boolean status) {
//        this.isResolved = status;
//    }


//    public boolean resolve() throws Exception {
//        if(this.getProjectContext().getOntology() == null) {
//            throw new ResolveException(this,"Null.Ontology");
//        }
//        return resolveChildren() && resolveContext();
//    }

    /**
     * abstract function needs to be implmented by all context
     * if there is nothing to resolve against the BE Namespace
     *  return true
     * @return
     * @throws Exception
     */
    //public abstract boolean resolveContext() throws Exception;

    /**
     * visitor pattern accept
     *
     * @param v
     * @return boolean
     */
    public boolean accept(HierarchicalContextVisitor v) throws Exception {
        if (v instanceof FilteredHierarchicalContextVisitor) {
            FilteredHierarchicalContextVisitor fv = (FilteredHierarchicalContextVisitor) v;
            if (fv.shouldVisitChildrenFirst()) {
               if (this.childrenCount() > 0 && fv.shouldVisitChildren()) {
                    Iterator at = childContext.iterator();
                    while (at.hasNext()) {
                        ((ModelContext) at.next()).accept(fv);
                    }
                }
            }
            // conditional visit implemented by the filter and operator
            boolean result  = fv.visit(this);
            if (!fv.shouldVisitChildrenFirst()) {
               if (this.childrenCount() > 0 && fv.shouldVisitChildren()) {
                    Iterator at = childContext.iterator();
                    while (at.hasNext()) {
                        ((ModelContext) at.next()).accept(fv);
                    }
                }
            }
            return result;
        } else {
            if (v.visitEnter(this)) {  // enter this node?
                if (!v.shouldVisitChildrenFirst()) {
                    v.visit(this);
                }
                if (this.childrenCount() > 0 && v.shouldVisitChildren()) {
                    Iterator at = childContext.iterator();
                    while (at.hasNext()) {
                        if (! ((ModelContext) at.next()).accept(v))
                            break;
                    }
                }
                if (v.shouldVisitChildrenFirst()) {
                    v.visit(this);
                }
            }
            return v.visitLeave(this);
        }
    }

    /**
     * Add t as a child to this node.  If t is null, do nothing.  If t
     * is nil, add all children of t to this' children.
     *
     * @param t
     */
    public void addChild(ObjectTreeNode t) {

    }

    public ObjectTreeNode dupNode() {
        return null;
    }

    public ObjectTreeNode dupTree() {
        return null;
    }


    public ObjectTreeNode getChild(int i) {
        return this.childContext.get(i);
    }

    public int getChildCount() {
        return this.childContext.size();
    }

    public String getText() {
        return this.toString();
    }

    /**
     * Return a token type; needed for tree parsing
     */
    public int getType() {
        return this.getContextType();
    }

    /**
     * Indicates the node is a nil node but may still have children, meaning
     * the tree is a flat list.
     */
    public boolean isNil() {
        return false;
    }

    public List getNodeChildren() {
        return this.childContext;
    }

    public void setText(String text) {
        // do nothing
    }

    public void setType(int type) {
        // do nothing
    }

    public String toStringTree() {
        if ( this.childContext==null || this.childContext.size()==0 ) {
            return this.toString();
        }
        StringBuffer buf = new StringBuffer();
        if ( !isNil() ) {
            buf.append("(");
            buf.append(this.toString());
            buf.append(' ');
        }
        for (int i = 0; this.childContext!=null && i < this.childContext.size(); i++) {
            BaseTree t = (BaseTree) this.childContext.get(i);
            if ( i>0 ) {
                buf.append(' ');
            }
            buf.append(t.toStringTree());
        }
        if ( !isNil() ) {
            buf.append(")");
        }
        return buf.toString();
    }

    /**
     * Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The <code>toString</code> method for class <code>Object</code>
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `<code>@</code>', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    public String toString() {
        if (this instanceof DateTimeLiteral) {
            return ((GregorianCalendar)((DateTimeLiteral) this).getValue()).getTime().toString();
        } else if (this instanceof Function) {
            return ((Function) this).getName();
        } else if (this instanceof FunctionArg) {
            return ((FunctionArg) this).getName();
        } if (this instanceof Literal) {
            return ((Literal) this).getValue().toString();
        } else if (this instanceof Identifier) {
            return ((Identifier) this).getName();
        } else if (this instanceof ScopedIdentifier) {
            return ((ScopedIdentifier) this).getName();
        } else if (this instanceof BinaryExpression) {
            return ((BinaryExpression) this).getOperator().toString();
        } else if (this instanceof UnaryExpression) {
            return ((UnaryExpression) this).getOperator().toString();
        } else if (this instanceof Aliased) {
            String alias = ((Aliased) this).getAlias();
            if(null != alias) {
               return contextStr.get(getContextType()) + ": "+ alias;
            } else {
                contextStr.get(getContextType());
            }
        } else if (this instanceof NamedContext) {
            return ((NamedContext) this).getContextId().toString();
        } else if (this instanceof GroupPolicy) {
            final GroupPolicy policy = (GroupPolicy) this;
            return "policy: capture "+ policy.getCaptureType() + ", emit " + policy.getEmitType();
        } else if (this instanceof SortCriterion) {
            final SortCriterion sort = (SortCriterion) this;
            return "sort: direction"+ sort.getDirection()
                    + ", limit first " + sort.getLimitFirst()
                    + ", limit offset " + sort.getLimitOffset();
        } else if (this instanceof SlidingWindow) {
            return "SlidingWindow :"+ ((SlidingWindow) this).getMaxSize();
        } else if (this instanceof TumblingWindow) {
            return "TumblingWindow :"+ ((TumblingWindow) this).getMaxSize();
        }  else if (this instanceof TimeWindow) {
            return "TimeWindow :"+ ((TimeWindow) this).getMaxTime();
        } if (this instanceof AliasedIdentifier) {
            return  ((AliasedIdentifier) this).getAlias();
        }
        return contextStr.get(getContextType());

    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModelContext)) {
            return false;
        }
        if (this.getClass().isAssignableFrom(o.getClass()) && !this.getClass().equals(o.getClass())) {
            return o.equals(this); // Delegates to most specific class.
        }

        final ModelContext that = (ModelContext) o;
        final Iterator itThis = this.childContext.iterator();
        for (Iterator itThat = that.getChildrenIterator(); itThat.hasNext(); ) {
            if (!(itThis.hasNext() && itThis.next().equals(itThat.next()))) {
                return false;
            }
        }
        return !itThis.hasNext();
    }


    public int hashCode() {
        return this.childContext.hashCode();
    }

}
