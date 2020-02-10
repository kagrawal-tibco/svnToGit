package com.tibco.cep.query.stream.core;

import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultGlobalContext;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.framework.AbstractTest;
import com.tibco.cep.query.stream.framework.SimpleIdGenerator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.framework.TrackedTuple;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.io.WithdrawableSourceImpl;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.truncate.TruncatedStream;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.WrappedCustomCollection;
import org.testng.annotations.Test;

import java.util.*;

/*
 * Author: Ashwin Jayaprakash Date: Nov 7, 2007 Time: 2:58:56 PM
 */

public class TruncatedStreamTest extends AbstractTest {
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

            WithdrawableSource source = new WithdrawableSourceImpl(new ResourceId("source"),
                    dataInfo);
            sources.put("source", source);

            TruncatedStream truncatedStream = new TruncatedStream(source.getInternalStream(),
                    new ResourceId("truncatedStream"), 5, 3);

            sink = new StreamedSink(truncatedStream, new ResourceId("sink"));

            // ----------

            Context context = new Context(new DefaultGlobalContext(),
                    new DefaultQueryContext(master.getAgentService().getName(), "Filter"));

            StockQuote quote = null;

            WrappedCustomCollection<Tuple> tuples =
                    new WrappedCustomCollection(new ArrayList<Tuple>());
            for (int i = 0; i < 20; i++) {
                quote = new StockQuote();
                quote.setSymbol("GOOG_" + i);
                quote.setPrice(540.12);
                Tuple data = new InputTuple(SimpleIdGenerator.generateNewId(), new Object[]{quote});
                tuples.add(data);
            }

            source.getInternalStream().getLocalContext().setNewTuples(tuples);
            source.getInternalStream().process(context);

            // ----------

            ArrayList<Tuple> actualSource = (ArrayList<Tuple>) tuples.getActualSource();

            Set<Tuple> observedTuples = new HashSet<Tuple>();
            observedTuples.add(actualSource.get(3));
            observedTuples.add(actualSource.get(4));
            observedTuples.add(actualSource.get(5));
            observedTuples.add(actualSource.get(6));
            observedTuples.add(actualSource.get(7));

            List<Object[]> expectedResults = new LinkedList<Object[]>();
            for (Tuple t : observedTuples) {
                expectedResults.add(t.getRawColumns());
            }

            LinkedIdentityHashMap<Tuple, Object[]> received =
                    collectAndMatchStreamedSink(expectedResults, 3);
            verifyCollection(expectedResults, received);

            // ----------

            source.getInternalStream().getLocalContext().setNewTuples(null);
            source.getInternalStream().getLocalContext().setDeadTuples(tuples);
            source.getInternalStream().process(context);

            expectedResults = new LinkedList<Object[]>();
            received = collectAndMatchStreamedSink(expectedResults, 3);

            // -----------

            commonTests();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
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
}
