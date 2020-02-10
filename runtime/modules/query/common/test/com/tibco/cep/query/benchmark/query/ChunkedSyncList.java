package com.tibco.cep.query.benchmark.query;

import com.tibco.cep.query.stream.util.AppendOnlyQueue;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
* Author: Ashwin Jayaprakash Date: Jun 19, 2008 Time: 10:47:23 AM
*/

/**
 * Works for 1 producer Thread ({@link #add(Object)} and {@link #forceFlushCurrentChunk()}) and 1 or
 * more consumer Threads via{{@link #getChunksForConsumption()}}.
 */
public class ChunkedSyncList<E> {
    protected int chunkSize;

    protected ChunkImpl<E> currProducerChunk;

    protected LinkedBlockingQueue<Chunk<E>> chunksForConsumption;

    public ChunkedSyncList(int chunkSize) {
        this.chunkSize = chunkSize;
        this.currProducerChunk = new ChunkImpl<E>(chunkSize / 4);
        this.chunksForConsumption = new LinkedBlockingQueue<Chunk<E>>(Integer.MAX_VALUE);
    }

    /**
     * Safe for single producer Thread!
     *
     * @param e
     */
    public void add(E e) {
        if (currProducerChunk.getSize() >= chunkSize) {
            forceFlushCurrentChunk();
        }

        currProducerChunk.add(e);
    }

    /**
     * Has to be the same Thread as the one which calls {@link #add(Object)}.
     */
    public void forceFlushCurrentChunk() {
        chunksForConsumption.add(currProducerChunk);

        currProducerChunk = new ChunkImpl<E>(chunkSize);
    }

    public int getChunkSize() {
        return chunkSize;
    }

    public BlockingQueue<Chunk<E>> getChunksForConsumption() {
        return chunksForConsumption;
    }

    //-------------

    public static class ChunkImpl<E> implements Chunk<E> {
        protected AppendOnlyQueue<E> storage;

        public ChunkImpl(int chunkSize) {
            this.storage = new AppendOnlyQueue<E>(chunkSize);
        }

        public int getSize() {
            return storage.size();
        }

        public Collection<E> getElements() {
            return storage;
        }

        public void add(E e) {
            storage.add(e);
        }

        public void discard() {
            storage.discard();
        }
    }
}
