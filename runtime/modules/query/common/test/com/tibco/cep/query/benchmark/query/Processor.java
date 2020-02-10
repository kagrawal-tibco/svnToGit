package com.tibco.cep.query.benchmark.query;

import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.ReusableIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/*
* Author: Ashwin Jayaprakash Date: Jun 19, 2008 Time: 11:45:43 AM
*/
public class Processor<I> {
    protected Collector<I> collector;

    protected CustomChunkProcessor<I> chunkProcessor;

    protected DaemonThread thread;

    public Processor(Collector<I> collector, CustomChunkProcessor<I> chunkProcessor) {
        this.collector = collector;
        this.chunkProcessor = chunkProcessor;
        this.thread = new DaemonThread();
    }

    public void start() {
        chunkProcessor.init(collector.getNumLanes());

        thread.start();
    }

    public void stop() {
        thread.end();

        chunkProcessor.end();
    }

    //-----------

    protected class DaemonThread extends Thread {
        protected AppendOnlyQueue<BlockingQueue<Chunk<I>>> queues;

        private ReusableIterator<BlockingQueue<Chunk<I>>> queuesIter;

        protected AtomicBoolean stopFlag;

        public DaemonThread() {
            setDaemon(true);
            setPriority(Thread.MIN_PRIORITY);

            this.queues = new AppendOnlyQueue<BlockingQueue<Chunk<I>>>();
            this.queuesIter = this.queues.iterator();

            this.stopFlag = new AtomicBoolean();
        }

        public void end() {
            stopFlag.set(true);

            while (isAlive()) {
                try {
                    join();
                }
                catch (InterruptedException e) {
                    //Ignore.
                }

                if (isAlive()) {
                    interrupt();
                }
            }
        }

        @Override
        public void run() {
            ChunkedSyncList[] lanes = Processor.this.collector.getLanes();
            for (ChunkedSyncList tempList : lanes) {
                ChunkedSyncList<I> chunkedList = (ChunkedSyncList<I>) tempList;

                BlockingQueue<Chunk<I>> queue = chunkedList.getChunksForConsumption();

                queues.add(queue);
            }

            //--------------

            final ReusableIterator<BlockingQueue<Chunk<I>>> cachedQueuesIter = queuesIter;
            final CustomChunkProcessor<I> cachedChunkProcessor = Processor.this.chunkProcessor;
            final int sleepTimeMillis = 5000;
            final ArrayList<Chunk<I>> drainQ = new ArrayList<Chunk<I>>();

            boolean allLanesFlushed = false;
            while (true) {
                int hitCount = 0;

                int laneNum = 0;
                for (cachedQueuesIter.reset(); cachedQueuesIter.hasNext(); laneNum++) {
                    BlockingQueue<Chunk<I>> queue = cachedQueuesIter.next();

                    drainQ.clear();
                    queue.drainTo(drainQ);

                    if (drainQ.size() == 0) {
                        continue;
                    }

                    //-------------

                    cachedChunkProcessor.process(laneNum, drainQ);

                    drainQ.clear();

                    hitCount++;

                    //-------------

                    Thread.yield();
                }

                //-------------

                if (stopFlag.get()) {
                    if (allLanesFlushed) {
                        if (hitCount > 0) {
                            //Keep trying just to make sure everything has been flushed.
                            continue;
                        }

                        break;
                    }

                    //------------

                    Processor.this.collector.flushAllLanes();

                    allLanesFlushed = true;
                }

                if (hitCount == 0) {
                    try {
                        Thread.sleep(sleepTimeMillis);
                    }
                    catch (InterruptedException e) {
                        //Ignore.
                    }
                }
            }
        }
    }

    //------------

    public interface CustomChunkProcessor<I> {
        void init(int numLanes);

        /**
         * @param lane
         * @param chunks Consume all of the elements here. This list is shared. Not for private
         *               use!
         */
        void process(int lane, List<? extends Chunk<I>> chunks);

        void end();
    }
}
