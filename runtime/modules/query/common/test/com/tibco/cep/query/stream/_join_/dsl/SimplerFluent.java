package com.tibco.cep.query.stream._join_.dsl;

import java.util.LinkedList;

/*
* Author: Ashwin Jayaprakash Date: May 1, 2009 Time: 11:58:59 AM
*/
public class SimplerFluent {
    interface Expression {
    }

    interface Junction extends Expression {
        ComplexExpression and();

        Junction and(Expression e);

        ComplexExpression or();

        Junction or(Expression e);
    }

    interface UnaryExpression extends Expression {
        //todo
        Junction custom(Expression e);
    }

    interface BooleanExpression extends Expression {
        Junction eq(int rhs, int lhs);
    }

    interface ComplexExpression extends UnaryExpression, BooleanExpression {
    }

    interface Query extends ComplexExpression {
        ComplexExpression where();
    }

    public static void main(String[] args) {
        Query q = new QueryAdapter() {
            {
                where()
                        .eq(10, 10).and().eq(33, 33)
                        .or()
                        .eq(4, 6).and(eq(3, 3)).and().eq(12, 10);
            }
        };

        System.out.println(q);
    }

    //--------------

    static class Context {
        protected StringBuilder builder;

        protected LastKnownType lastKnownType;

        /**
         * Starts with {@link com.tibco.cep.query.stream._join_.dsl.SimplerFluent.LastKnownType#Junction}.
         */
        Context() {
            this.lastKnownType = LastKnownType.Junction;
            this.builder = new StringBuilder();
        }

        public StringBuilder getBuilder() {
            return builder;
        }

        public LastKnownType getLastKnownType() {
            return lastKnownType;
        }
    }

    static enum LastKnownType {
        Expression,
        Junction;
    }

    static class QueryAdapter implements Query {
        protected LinkedList<Context> stack;

        protected JunctionAdapter junctionAdapter;

        QueryAdapter() {
            this.stack = new LinkedList<Context>();
            this.stack.add(new Context());

            this.junctionAdapter = new JunctionAdapter(this);
        }

        public LinkedList<Context> getStack() {
            return stack;
        }

        public JunctionAdapter getJunctionAdapter() {
            return junctionAdapter;
        }

        public ComplexExpression where() {
            stack.getLast().builder.append("where");

            return this;
        }

        public Junction custom(Expression e) {
            //todo
            stack.getLast().builder.append(e);

            return junctionAdapter;
        }

        public Junction eq(int rhs, int lhs) {
            Context context = stack.getLast();

            if (context.lastKnownType == LastKnownType.Expression) {
                context = new Context();
                stack.add(context);
            }
            else {
                context.lastKnownType = LastKnownType.Expression;
            }

            context.builder.append(' ');
            context.builder.append(rhs);
            context.builder.append(' ');
            context.builder.append('=');
            context.builder.append(' ');
            context.builder.append(lhs);

            return junctionAdapter;
        }

        @Override
        public String toString() {
            return stack.getFirst().builder.toString();
        }
    }

    static class JunctionAdapter implements Junction {
        protected QueryAdapter queryAdapter;

        protected LinkedList<Context> stack;

        JunctionAdapter(QueryAdapter queryAdapter) {
            this.queryAdapter = queryAdapter;
            this.stack = queryAdapter.stack;
        }

        public QueryAdapter getQueryAdapter() {
            return queryAdapter;
        }

        public ComplexExpression and() {
            Context context = stack.getLast();

            context.builder.append(" and");
            context.lastKnownType = LastKnownType.Junction;

            return queryAdapter;
        }

        public Junction and(Expression e) {
            Context poppedContext = stack.removeLast();
            Context lastContext = stack.getLast();

            lastContext.builder.append(" and (");
            lastContext.builder.append(poppedContext.builder);
            lastContext.builder.append(" )");

            return this;
        }

        public ComplexExpression or() {
            Context context = stack.getLast();

            context.builder.append(" or");
            context.lastKnownType = LastKnownType.Junction;

            return queryAdapter;
        }

        public Junction or(Expression e) {
            Context poppedContext = stack.removeLast();
            Context lastContext = stack.getLast();

            lastContext.builder.append(" or (");
            lastContext.builder.append(poppedContext.builder);
            lastContext.builder.append(" )");

            return this;
        }
    }
}
