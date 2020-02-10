package com.tibco.cep.query.model;

import java.util.Iterator;

import com.tibco.cep.query.model.validation.Validatable;


/**
 * @author pdhar
 *
 */
public interface ProjectionAttributes extends NamedContext, QueryContext, Validatable {
	
    public static NamedContextId CTX_ID = new NamedContextId() {
        public String toString() { return "PROJECTION_ATTRIBUTES"; }
    };
	
	Projection[] getAllProjections();

	Aliased[] getAllNamedAliases();
	
	Aliased[] getAllPseudoAliases();
	
	Iterator getProjectionIterator();
	
	Projection getProjection(String alias);
		
	Iterator getProjectionElementIterator();
		
	int getProjectionElementCount();

}
