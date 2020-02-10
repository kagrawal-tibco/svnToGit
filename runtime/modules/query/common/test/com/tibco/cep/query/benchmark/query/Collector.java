package com.tibco.cep.query.benchmark.query;

/*
* Author: Ashwin Jayaprakash Date: Jun 19, 2008 Time: 10:44:43 AM
*/
public class Collector<I> {
    protected ChunkedSyncList[] lanes;

    public Collector(int numLanes, int laneChunkSize) {
        this.lanes = new ChunkedSyncList[numLanes];

        for (int i = 0; i < lanes.length; i++) {
            lanes[i] = new ChunkedSyncList<I>(laneChunkSize);
        }
    }

    public int getNumLanes() {
        return lanes.length;
    }

    @SuppressWarnings({"unchecked"})
    public void offer(int laneNum, I i) {
        lanes[laneNum].add(i);
    }

    public ChunkedSyncList[] getLanes() {
        return lanes;
    }

    public void flushLane(int laneNum) {
        lanes[laneNum].forceFlushCurrentChunk();
    }

    public void flushAllLanes() {
        for (ChunkedSyncList lane : lanes) {
            lane.forceFlushCurrentChunk();
        }
    }
}
