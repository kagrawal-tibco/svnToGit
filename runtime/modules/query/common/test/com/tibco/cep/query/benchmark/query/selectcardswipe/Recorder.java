package com.tibco.cep.query.benchmark.query.selectcardswipe;

import com.tibco.cep.query.benchmark.query.Collector;
import com.tibco.cep.query.benchmark.query.Processor;

import java.util.Calendar;

/*
* Author: Ashwin Jayaprakash Date: Jun 18, 2008 Time: 4:15:54 PM
*/
public class Recorder {
    static Collector<Object[]> collector;

    static Processor.CustomChunkProcessor<Object[]> chunkProcessor;

    static Processor<Object[]> processor;

    public static void init(int numQueries, int recordChunkSize) {
        collector = new Collector<Object[]>(numQueries, recordChunkSize);

        chunkProcessor = new ChunkProcessorImpl();

        processor = new Processor<Object[]>(collector, chunkProcessor);
        processor.start();
    }

    public static void start(int queryNumber) {
    }

    public static void record(int queryNumber, long eventId, Calendar createdTime,
                              Calendar receivedTime) {
        Object[] row = new Object[]{eventId, createdTime.getTimeInMillis(),
                receivedTime.getTimeInMillis()};

        collector.offer(queryNumber, row);
    }

    public static void stop(int queryNumber) {
        collector.flushLane(queryNumber);
    }

    public static void stopAll() {
        processor.stop();
    }
}
