package com.tibco.cep.query.model.visitor;

import com.tibco.cep.query.model.ModelContext;

public interface  HierarchicalContextVisitor {
    public static final int  CHILDREN_FIRST = 0x1;
    public static final int  VISIT_CHILDREN = 0x2;


    public int getTraversalMode();
    boolean shouldVisitChildrenFirst();
    boolean shouldVisitChildren();


    public boolean visitEnter(ModelContext node) throws Exception;
    public boolean visit(ModelContext node) throws Exception;
    public boolean visitLeave(ModelContext node) throws Exception;

    public static class DefaultHierarchicalContextVisitor implements HierarchicalContextVisitor
	{
        private int traversalMode;

        public DefaultHierarchicalContextVisitor(int traversalMode) {
          this.traversalMode = traversalMode;            
        }

        public int getTraversalMode() {
            return this.traversalMode;
        }

        public boolean shouldVisitChildrenFirst() {
            return ((traversalMode & HierarchicalContextVisitor.CHILDREN_FIRST ) != 0);
        }

        public boolean shouldVisitChildren() {
            return ((traversalMode & HierarchicalContextVisitor.VISIT_CHILDREN ) != 0);
        }

        public boolean visitEnter( ModelContext node ) throws Exception {
        return true;
        }
		public boolean visitLeave( ModelContext node ) throws Exception {
        return true;
        }
		public boolean visit( ModelContext node ) throws Exception {
        return true;
        }
	}
}
