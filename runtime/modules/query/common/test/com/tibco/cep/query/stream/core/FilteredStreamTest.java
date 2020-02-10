package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.framework.*;
import com.tibco.cep.query.stream.impl.filter.FilteredStreamImpl;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 6:40:08 PM
 */

public class FilteredStreamTest extends AbstractTest {
    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        try {
            final TupleInfo dataInfo =
                    new AbstractTupleInfo(InputTuple.class, new String[]{"stock"},
                            new Class[]{StockQuote.class}) {
                        public Tuple createTuple(Number id) {
                            return new InputTuple(id);
                        }
                    };

            WithdrawableSource source = new WithdrawableSourceImpl(null, dataInfo);
            sources.put("$source", source);

            String filterString =
                    com.tibco.cep.query.stream.filter.FilteredStream.DEFAULT_STREAM_ALIAS
                            + ".getColumn(0).symbol == \"TIBX\"";

            Map<String, Class<? extends Tuple>> aliases =
                    new HashMap<String, Class<? extends Tuple>>();
            aliases.put(com.tibco.cep.query.stream.filter.FilteredStream.DEFAULT_STREAM_ALIAS,
                    Tuple.class);
            FilteredStreamImpl filteredStream = new FilteredStreamImpl(source.getInternalStream(),
                    null, new SimpleExpression(aliases, filterString));

            sink = new StreamedSink(filteredStream, null);

            // ----------

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), "Filter"));

            List<Tuple> tuples = new LinkedList<Tuple>();

            StockQuote tibxQuote = new StockQuote();
            tibxQuote.setSymbol("TIBX");
            tibxQuote.setPrice(10.32);
            Tuple data = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{tibxQuote});
            source.sendNewTuple(context, data);
            tuples.add(data);

            StockQuote googQuote = new StockQuote();
            googQuote.setSymbol("GOOG");
            googQuote.setPrice(540.12);
            data = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{googQuote});
            source.sendNewTuple(context, data);
            tuples.add(data);

            // ----------

            List<Object[]> expectedResults = new ArrayList<Object[]>();
            expectedResults.add(new Object[]{tibxQuote});

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 5);
            verifyCollection(expectedResults, received);

            for (Tuple t : tuples) {
                source.sendDeadTuple(context, t);
            }

            commonTests();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class StockQuote {
        protected String symbol;

        protected Double price;

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return "symbol: " + getSymbol() + ", price: " + getPrice();
        }
    }

    public static class InputTuple extends TrackedTuple {
        public InputTuple(Number id) {
            super(id);
        }

        public InputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }
}
