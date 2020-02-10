package com.tibco.cep.query.model.visitor.filter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 8, 2007
 * Time: 7:54:29 PM
 * To change this template use File | Settings | File Templates.
 */
interface Items {   // a bunch of items (unordered collection)

     //* eval block for each
     void enumerate( UnaryProcedure aBlock ) throws Exception;


     //* increment count for each true
     int count( UnaryPredicate aBlock );


     //* remove item for each block that answer true
     void remove( UnaryPredicate aBlock );


     //* detect first for block answers true
     Object detect( UnaryPredicate aBlock );


     //* inject value into block with each item
     Object inject( Object value, BinaryFunction aBlock );


     //* answer new collection for each true
     Items select( UnaryPredicate aBlock );


     //* answer new collection for each false
     Items reject( UnaryPredicate aBlock );


     //* answer new collection of non-null results
     Items collect( UnaryFunction aBlock );
 }
