package com.tibco.cep.query.exec.prebuilt;

import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.HeavyTuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 3, 2008 Time: 5:51:20 PM
*/
public final class HeavyTuples {
    public final static class HeavyTupleInfo extends AbstractTupleInfo {
        protected final int prebuiltType;

        /**
         * @param columnNames
         * @param columnTypes
         * @param prebuiltType Number 1-n indicating one of the {@link com.tibco.cep.query.stream.tuple.HeavyTuple}
         *                     sub-classes. Anything else to get {@link com.tibco.cep.query.stream.tuple.HeavyTuple}
         *                     itself.
         */
        public HeavyTupleInfo(String[] columnNames, Class[] columnTypes, int prebuiltType) {
            super(fetchContainerClass(prebuiltType), columnNames, columnTypes);

            this.prebuiltType = prebuiltType;
        }

        public int getPrebuiltType() {
            return prebuiltType;
        }

        public static Class<? extends HeavyTuple> fetchContainerClass(int prebuiltType) {
            switch (prebuiltType) {
                case 1:
                    return HeavyTuple001.class;

                case 2:
                    return HeavyTuple002.class;

                case 3:
                    return HeavyTuple003.class;

                case 4:
                    return HeavyTuple004.class;

                case 5:
                    return HeavyTuple005.class;

                case 6:
                    return HeavyTuple006.class;

                case 7:
                    return HeavyTuple007.class;

                case 8:
                    return HeavyTuple008.class;

                case 9:
                    return HeavyTuple009.class;

                case 10:
                    return HeavyTuple010.class;

                case 11:
                    return HeavyTuple011.class;

                case 12:
                    return HeavyTuple012.class;

                case 13:
                    return HeavyTuple013.class;

                case 14:
                    return HeavyTuple014.class;

                case 15:
                    return HeavyTuple015.class;

                case 16:
                    return HeavyTuple016.class;

                case 17:
                    return HeavyTuple017.class;

                case 18:
                    return HeavyTuple018.class;

                case 19:
                    return HeavyTuple019.class;

                case 20:
                    return HeavyTuple020.class;

                default:
                    return HeavyTuple.class;
            }
        }

        public HeavyTuple createTuple(Number id) {
            switch (prebuiltType) {
                case 1:
                    return new HeavyTuple001(id);

                case 2:
                    return new HeavyTuple002(id);

                case 3:
                    return new HeavyTuple003(id);

                case 4:
                    return new HeavyTuple004(id);

                case 5:
                    return new HeavyTuple005(id);

                case 6:
                    return new HeavyTuple006(id);

                case 7:
                    return new HeavyTuple007(id);

                case 8:
                    return new HeavyTuple008(id);

                case 9:
                    return new HeavyTuple009(id);

                case 10:
                    return new HeavyTuple010(id);

                case 11:
                    return new HeavyTuple011(id);

                case 12:
                    return new HeavyTuple012(id);

                case 13:
                    return new HeavyTuple013(id);

                case 14:
                    return new HeavyTuple014(id);

                case 15:
                    return new HeavyTuple015(id);

                case 16:
                    return new HeavyTuple016(id);

                case 17:
                    return new HeavyTuple017(id);

                case 18:
                    return new HeavyTuple018(id);

                case 19:
                    return new HeavyTuple019(id);

                case 20:
                    return new HeavyTuple020(id);

                default:
                    return new HeavyTuple(id);
            }
        }
    }

    //----------

    public static final class HeavyTuple001 extends HeavyTuple {
        public HeavyTuple001(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple002 extends HeavyTuple {
        public HeavyTuple002(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple003 extends HeavyTuple {
        public HeavyTuple003(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple004 extends HeavyTuple {
        public HeavyTuple004(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple005 extends HeavyTuple {
        public HeavyTuple005(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple006 extends HeavyTuple {
        public HeavyTuple006(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple007 extends HeavyTuple {
        public HeavyTuple007(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple008 extends HeavyTuple {
        public HeavyTuple008(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple009 extends HeavyTuple {
        public HeavyTuple009(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple010 extends HeavyTuple {
        public HeavyTuple010(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple011 extends HeavyTuple {
        public HeavyTuple011(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple012 extends HeavyTuple {
        public HeavyTuple012(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple013 extends HeavyTuple {
        public HeavyTuple013(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple014 extends HeavyTuple {
        public HeavyTuple014(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple015 extends HeavyTuple {
        public HeavyTuple015(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple016 extends HeavyTuple {
        public HeavyTuple016(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple017 extends HeavyTuple {
        public HeavyTuple017(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple018 extends HeavyTuple {
        public HeavyTuple018(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple019 extends HeavyTuple {
        public HeavyTuple019(Number id) {
            super(id);
        }
    }

    public static final class HeavyTuple020 extends HeavyTuple {
        public HeavyTuple020(Number id) {
            super(id);
        }
    }
}