package com.tibco.store.query.model;

import com.tibco.store.query.exec.PredicateTreeVisitor;

public interface Predicate {

    public <V extends PredicateTreeVisitor> void accept(V predicateTreeVisitor);
}
