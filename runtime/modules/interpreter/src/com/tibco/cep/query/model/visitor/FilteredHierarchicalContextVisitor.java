package com.tibco.cep.query.model.visitor;

import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.model.visitor.filter.CollectionItems;
import com.tibco.cep.query.model.visitor.filter.UnaryPredicate;
import com.tibco.cep.query.model.visitor.filter.UnaryProcedure;


/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 8, 2007
 * Time: 3:53:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilteredHierarchicalContextVisitor
        extends HierarchicalContextVisitor.DefaultHierarchicalContextVisitor
        implements HierarchicalContextVisitor {

    private CollectionItems<ContextFilter> m_filters = new CollectionItems<ContextFilter>();
    private CollectionItems<ContextOperator> m_operators = new CollectionItems<ContextOperator>();

    /**
     * Constructor
     */
    public FilteredHierarchicalContextVisitor() {
        this(HierarchicalContextVisitor.CHILDREN_FIRST | HierarchicalContextVisitor.VISIT_CHILDREN );
    }

    /**
     * Constructor
     */
    public FilteredHierarchicalContextVisitor(int flags) {
        super(flags);
    }

    /**
     * add context filters
     * @param filter
     */
    public void addFilter(ContextFilter filter) {
       m_filters.add(filter);
    }

    /**
     * Add context operators
     * @param operator
     */
    public void addOperator(ContextOperator operator) {
        m_operators.add(operator);
    }

    /**
     * Return first that rejects this node
     * @param node
     * @return  boolean
     */
    public boolean visitEnter(final ModelContext node) throws Exception{
        // Return first that rejects this node
        Object rejected =
                m_filters.detect(new UnaryPredicate() {
                    public boolean is(Object each) {
                        return !((ContextFilter)each).canVisit(node);
                    }
                });
        return (rejected == null);
        //return super.visitEnter(node);
    }

    /**
     * Visit non-rejected nodes
     * @param node
     * @return  boolean
     */
    public boolean visitLeave(final ModelContext node) throws Exception {
        m_operators.enumerate(new UnaryProcedure() {
            public void run(Object each) throws Exception {
                ((ContextOperator)each).visit(node);
            }
        });
        return true;
    }

    /**
     * Check reject state for each condition, process if not rejected
     * @param node
     * @return boolean
     */
    public boolean visit(final ModelContext node) throws Exception{
        // Return first that rejects this leaf
        Object rejected =
                m_filters.detect(new UnaryPredicate() {
                    public boolean is(Object each) {
                        return !((ContextFilter)each).canVisit(node);
                    }
                });


        if (rejected == null) { // no one rejected
            m_operators.enumerate(new UnaryProcedure() {
                public void run(Object each) throws Exception{
                    ((ContextOperator)each).visit(node);
                }
            });
        }
        return true;

    }


}
