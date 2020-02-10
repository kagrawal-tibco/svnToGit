package com.tibco.cep.query.stream.cache;

import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Mar 18, 2008 Time: 7:29:04 PM
 */

public interface CacheBuilder extends Component {
    Cache build(String name, ResourceId parent, Type type);

    BuilderInput getBuilderInput();

    //---------------

    public static enum Type {
        PRIMARY, DEADPOOL
    }

    //---------------

    public static class BuilderInput {
        /**
         * {@value}
         */
        public static final String KEY_INPUT = BuilderInput.class.getName();

        protected final int primaryMaxItems;

        protected final long primaryExpiryTimeMillis;

        protected final int deadpoolMaxItems;

        protected final long deadpoolExpiryTimeMillis;

        /**
         * @param primaryMaxItems
         * @param primaryExpiryTimeMillis
         * @param deadpoolMaxItems
         * @param deadpoolExpiryTimeMillis
         */
        public BuilderInput(int primaryMaxItems, long primaryExpiryTimeMillis, int deadpoolMaxItems,
                            long deadpoolExpiryTimeMillis) {
            this.primaryMaxItems = primaryMaxItems;
            this.primaryExpiryTimeMillis = primaryExpiryTimeMillis;

            this.deadpoolMaxItems = deadpoolMaxItems;
            this.deadpoolExpiryTimeMillis = deadpoolExpiryTimeMillis;
        }

        public int getPrimaryMaxItems() {
            return primaryMaxItems;
        }

        public long getPrimaryExpiryTimeMillis() {
            return primaryExpiryTimeMillis;
        }

        public int getDeadpoolMaxItems() {
            return deadpoolMaxItems;
        }

        public long getDeadpoolExpiryTimeMillis() {
            return deadpoolExpiryTimeMillis;
        }
    }
}
