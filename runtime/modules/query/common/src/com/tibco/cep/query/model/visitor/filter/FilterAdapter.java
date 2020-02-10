package com.tibco.cep.query.model.visitor.filter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 8, 2007
 * Time: 7:57:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FilterAdapter extends Procedure, UnaryProcedure, BinaryProcedure,
        Function, UnaryFunction, BinaryFunction,
        Predicate, UnaryPredicate, BinaryPredicate {


    public static class DefaultFilterAdapter implements FilterAdapter {

        //..The Procedures

        /* [JavaDoc Header]
        * Default implementation of a <code>Procedure</code> interface. This
        * implementation calls the <code>Function</code> interface member
        * <code>eval</code> and discards its return value.
        */


        public void run() {
            eval();
        }


        public void run(Object arg1) throws Exception {
            eval(arg1);
        }


        public void run(Object arg1, Object arg2) {
            eval(arg1, arg2);
        }

        //..The Functions

        /* [JavaDoc Header]
        * Default implementation of a <code>Function</code> interface. This
        * implementation calls the <code>Predicate</code> interface member
        * <code>is</code> and returns its <code>boolean</code> value as an
        * instance of <code>java.lang.Boolean</code>.
        */


        public Object eval() {
            return is() ? Boolean.TRUE : Boolean.FALSE;
        }


        public Object eval(Object arg1) {
            return is(arg1) ? Boolean.TRUE : Boolean.FALSE;
        }


        public Object eval(Object arg1, Object arg2) {
            return is(arg1, arg2) ? Boolean.TRUE : Boolean.FALSE;
        }

        //..The Predicates

        /* [JavaDoc Header]
        * Default implementation of a <code>Predicate</code> interface.
        * This implementation calls the <code>eval</code> member of the
        * <code>Function</code>, casts its result as a
        * <code>java.lang.Boolean</code>, and returns the result of
        * calling <code>booleanValue</code>.
        */

        // NOTE: No error checking!!


        public boolean is() {
            return ((Boolean) eval()).booleanValue();
        }


        public boolean is(Object arg1) {
            return ((Boolean) eval(arg1)).booleanValue();
        }


        public boolean is(Object arg1, Object arg2) {
            return ((Boolean) eval(arg1, arg2)).booleanValue();
        }

    }

}
