package com.tibco.cep.query.stream._join_.dsl;

import java.awt.*;

/*
* Author: Ashwin Jayaprakash Date: Dec 5, 2008 Time: 9:13:37 PM
*/
public class Fluent {
    public interface BooleanExpressionDef {
    }

    public interface ChainedBooleanExpressionDef {
        ChainedBooleanExpressionDef and(BooleanExpressionDef e);
    }

    public interface ExpressionCreatorDef {
        <T extends Comparable> BinaryExpressionDef<T> $(T arg);
    }

    public interface BinaryExpressionDef<T> {
        BooleanExpressionDef ge(BinaryExpressionDef<T> rhs);

        BooleanExpressionDef gt(BinaryExpressionDef<T> rhs);

        BooleanExpressionDef ne(BinaryExpressionDef<T> rhs);

        BooleanExpressionDef eq(BinaryExpressionDef<T> rhs);

        BooleanExpressionDef lt(BinaryExpressionDef<T> rhs);

        BooleanExpressionDef le(BinaryExpressionDef<T> rhs);
    }

    public interface SourceDef {
    }

    public interface FromDef {
        JoinDef innerJoin(SourceDef s);

        JoinDef outerJoin(SourceDef s);

        JoinDef leftOuterJoin(SourceDef s);

        JoinDef rightOuterJoin(SourceDef s);

        SimpleWhereDef where(BooleanExpressionDef e);
    }

    public interface JoinDef {
        JoinWhereDef on(BooleanExpressionDef e);
    }

    public interface JoinWhereDef extends ChainedBooleanExpressionDef, FromDef {
        JoinWhereDef and(BooleanExpressionDef e);

        SimpleWhereDef where(BooleanExpressionDef e);
    }

    public interface SimpleWhereDef extends ChainedBooleanExpressionDef {
        SimpleWhereDef and(BooleanExpressionDef e);
    }

    public interface StarterDef {
        FromDef from(SourceDef s);
    }

    //-----------

    static class QueryHelper implements StarterDef, ExpressionCreatorDef {
        public FromDef from(SourceDef s) {
            return null;
        }

        public <T extends Comparable> BinaryExpressionDef<T> $(T arg) {
            return null;
        }

        void test() {
            from(null)
                    .where($(System.currentTimeMillis()).gt($(30l)))
                    .and($("hello").eq($("hello")));

            Point p = new Point(12, 12);

            from(null)
                    .innerJoin(null)
                    .on($(10.9).eq($(45.7)))
                    .and($("abc").ge($("xyz")))

                    .innerJoin(null)
                    .on(null)

                    .where($(10).gt($(30)))
                    .and($("hello").eq($("hello")))
                    .and($(p.getX()).eq($(45.0)));
        }
    }
}
