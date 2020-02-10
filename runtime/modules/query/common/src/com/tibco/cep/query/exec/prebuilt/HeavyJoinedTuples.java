package com.tibco.cep.query.exec.prebuilt;

import com.tibco.cep.query.stream.join.HeavyJoinedTuple;
import com.tibco.cep.query.stream.join.JoinedTupleInfo;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;

/*
* Author: Ashwin Jayaprakash Date: Apr 3, 2008 Time: 5:51:20 PM
*/
public final class HeavyJoinedTuples {
    public final static class HeavyJoinedTupleInfo extends AbstractTupleInfo
            implements JoinedTupleInfo {
        protected int prebuiltType;

        /**
         * @param columnNames
         * @param columnTypes
         * @param prebuiltType Number 1-n indicating one of the {@link HeavyJoinedTuple}
         *                     sub-classes. Anything else to get {@link HeavyJoinedTuple} itself.
         */
        public HeavyJoinedTupleInfo(String[] columnNames, Class[] columnTypes, int prebuiltType) {
            super(fetchContainerClass(prebuiltType), columnNames, columnTypes);

            this.prebuiltType = prebuiltType;
        }

        public int getPrebuiltType() {
            return prebuiltType;
        }

        public static Class<? extends HeavyJoinedTuple> fetchContainerClass(int prebuiltType) {
            switch (prebuiltType) {
                case 1:
                    return HeavyJoinedTuple001.class;

                case 2:
                    return HeavyJoinedTuple002.class;

                case 3:
                    return HeavyJoinedTuple003.class;

                case 4:
                    return HeavyJoinedTuple004.class;

                case 5:
                    return HeavyJoinedTuple005.class;

                default:
                    return HeavyJoinedTuple.class;
            }
        }

        @Override
        public Class<? extends HeavyJoinedTuple> getContainerClass() {
            return (Class<? extends HeavyJoinedTuple>) super.getContainerClass();
        }

        public HeavyJoinedTuple createTuple(Number id) {
            switch (prebuiltType) {
                case 1:
                    return new HeavyJoinedTuple001(id);

                case 2:
                    return new HeavyJoinedTuple002(id);

                case 3:
                    return new HeavyJoinedTuple003(id);

                case 4:
                    return new HeavyJoinedTuple004(id);

                case 5:
                    return new HeavyJoinedTuple005(id);

                default:
                    return new HeavyJoinedTuple(id);
            }
        }
    }

    //----------

    public static final class HeavyJoinedTuple001 extends HeavyJoinedTuple {
        public HeavyJoinedTuple001(Number id) {
            super(id);
        }
    }

    public static final class HeavyJoinedTuple002 extends HeavyJoinedTuple {
        public HeavyJoinedTuple002(Number id) {
            super(id);
        }
    }

    public static final class HeavyJoinedTuple003 extends HeavyJoinedTuple {
        public HeavyJoinedTuple003(Number id) {
            super(id);
        }
    }

    public static final class HeavyJoinedTuple004 extends HeavyJoinedTuple {
        public HeavyJoinedTuple004(Number id) {
            super(id);
        }
    }

    public static final class HeavyJoinedTuple005 extends HeavyJoinedTuple {
        public HeavyJoinedTuple005(Number id) {
            super(id);
        }
    }
}