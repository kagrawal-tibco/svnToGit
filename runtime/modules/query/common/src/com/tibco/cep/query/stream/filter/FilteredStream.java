package com.tibco.cep.query.stream.filter;

import com.tibco.cep.query.stream.core.AbstractStream;
import com.tibco.cep.query.stream.core.Stream;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 4:14:40 PM
 */

public abstract class FilteredStream extends AbstractStream {
    /**
     * Alias for tuples in the expression - {@value}
     */
    public static final String DEFAULT_STREAM_ALIAS = "$_row_$";

    protected final ExpressionEvaluator filterExpression;

    protected final String alias;

    /**
     * The output is the same as that of the source's.
     *
     * @param source
     * @param id
     * @param filterExpression
     */
    public FilteredStream(Stream source, ResourceId id, ExpressionEvaluator filterExpression) {
        this(DEFAULT_STREAM_ALIAS, source, id, filterExpression);
    }

    /**
     * The output is the same as that of the source's.
     *
     * @param alias
     * @param source
     * @param id
     * @param filterExpression
     */
    public FilteredStream(String alias, Stream source, ResourceId id,
                          ExpressionEvaluator filterExpression) {
        super(source, id, source.getOutputInfo());

        this.alias = alias;
        this.filterExpression = filterExpression;
    }

    public String getAlias() {
        return alias;
    }

    public ExpressionEvaluator getFilterExpression() {
        return filterExpression;
    }
}
