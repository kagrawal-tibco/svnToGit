package com.tibco.cep.query.stream.impl.rete.service;

/*
* Author: Ashwin Jayaprakash Date: Jul 15, 2008 Time: 6:11:10 PM
*/
public interface EntityLoader {
    Class getEntityClass();

    /**
     * @return Returns the same result object everytime but with updated results.
     * @throws Exception
     */
    BatchResult resumeOrStartBatchLoad() throws Exception;

    void end();

    public static class BatchResult {
        protected int numSent;

        /**
         * <code>true</code> if there are any remaining items to be processed.
         */
        protected boolean hasMore;

        public int getNumSent() {
            return numSent;
        }

        public void setNumSent(int numSent) {
            this.numSent = numSent;
        }

        public boolean hasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }
    }
}
