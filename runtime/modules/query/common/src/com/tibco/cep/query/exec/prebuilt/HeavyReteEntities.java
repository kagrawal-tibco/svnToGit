package com.tibco.cep.query.exec.prebuilt;

import com.tibco.cep.query.stream.impl.rete.HeavyReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;

/*
* Author: Ashwin Jayaprakash Date: Apr 3, 2008 Time: 5:51:20 PM
*/
public final class HeavyReteEntities {
    public final static class HeavyReteEntityInfo extends AbstractTupleInfo
            implements ReteEntityInfo {
        protected final int prebuiltType;

        /**
         * @param columnNames
         * @param columnTypes
         * @param prebuiltType Number 1-n indicating one of the {@link HeavyReteEntity} sub-classes.
         *                     Anything else to get {@link com.tibco.cep.query.stream.impl.rete.HeavyReteEntity}
         *                     itself.
         */
        public HeavyReteEntityInfo(String[] columnNames, Class[] columnTypes, int prebuiltType) {
            super(fetchContainerClass(prebuiltType), columnNames, columnTypes);

            this.prebuiltType = prebuiltType;
        }

        public int getPrebuiltType() {
            return prebuiltType;
        }

        public static Class<? extends HeavyReteEntity> fetchContainerClass(int prebuiltType) {
            switch (prebuiltType) {
                case 1:
                    return HeavyReteEntity001.class;

                case 2:
                    return HeavyReteEntity002.class;

                case 3:
                    return HeavyReteEntity003.class;

                case 4:
                    return HeavyReteEntity004.class;

                case 5:
                    return HeavyReteEntity005.class;

                case 6:
                    return HeavyReteEntity006.class;

                case 7:
                    return HeavyReteEntity007.class;

                case 8:
                    return HeavyReteEntity008.class;

                case 9:
                    return HeavyReteEntity009.class;

                case 10:
                    return HeavyReteEntity010.class;

                case 11:
                    return HeavyReteEntity011.class;

                case 12:
                    return HeavyReteEntity012.class;

                case 13:
                    return HeavyReteEntity013.class;

                case 14:
                    return HeavyReteEntity014.class;

                case 15:
                    return HeavyReteEntity015.class;

                case 16:
                    return HeavyReteEntity016.class;

                case 17:
                    return HeavyReteEntity017.class;

                case 18:
                    return HeavyReteEntity018.class;

                case 19:
                    return HeavyReteEntity019.class;

                case 20:
                    return HeavyReteEntity020.class;

                default:
                    return HeavyReteEntity.class;
            }
        }

        @Override
        public Class<? extends HeavyReteEntity> getContainerClass() {
            return (Class<? extends HeavyReteEntity>) super.getContainerClass();
        }

        public HeavyReteEntity createTuple(Number id) {
            switch (prebuiltType) {
                case 1:
                    return new HeavyReteEntity001(id);

                case 2:
                    return new HeavyReteEntity002(id);

                case 3:
                    return new HeavyReteEntity003(id);

                case 4:
                    return new HeavyReteEntity004(id);

                case 5:
                    return new HeavyReteEntity005(id);

                case 6:
                    return new HeavyReteEntity006(id);

                case 7:
                    return new HeavyReteEntity007(id);

                case 8:
                    return new HeavyReteEntity008(id);

                case 9:
                    return new HeavyReteEntity009(id);

                case 10:
                    return new HeavyReteEntity010(id);

                case 11:
                    return new HeavyReteEntity011(id);

                case 12:
                    return new HeavyReteEntity012(id);

                case 13:
                    return new HeavyReteEntity013(id);

                case 14:
                    return new HeavyReteEntity014(id);

                case 15:
                    return new HeavyReteEntity015(id);

                case 16:
                    return new HeavyReteEntity016(id);

                case 17:
                    return new HeavyReteEntity017(id);

                case 18:
                    return new HeavyReteEntity018(id);

                case 19:
                    return new HeavyReteEntity019(id);

                case 20:
                    return new HeavyReteEntity020(id);

                default:
                    return new HeavyReteEntity(id);
            }

        }
    }

    //----------

    public static final class HeavyReteEntity001 extends HeavyReteEntity {
        public HeavyReteEntity001(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity002 extends HeavyReteEntity {
        public HeavyReteEntity002(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity003 extends HeavyReteEntity {
        public HeavyReteEntity003(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity004 extends HeavyReteEntity {
        public HeavyReteEntity004(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity005 extends HeavyReteEntity {
        public HeavyReteEntity005(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity006 extends HeavyReteEntity {
        public HeavyReteEntity006(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity007 extends HeavyReteEntity {
        public HeavyReteEntity007(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity008 extends HeavyReteEntity {
        public HeavyReteEntity008(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity009 extends HeavyReteEntity {
        public HeavyReteEntity009(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity010 extends HeavyReteEntity {
        public HeavyReteEntity010(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity011 extends HeavyReteEntity {
        public HeavyReteEntity011(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity012 extends HeavyReteEntity {
        public HeavyReteEntity012(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity013 extends HeavyReteEntity {
        public HeavyReteEntity013(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity014 extends HeavyReteEntity {
        public HeavyReteEntity014(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity015 extends HeavyReteEntity {
        public HeavyReteEntity015(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity016 extends HeavyReteEntity {
        public HeavyReteEntity016(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity017 extends HeavyReteEntity {
        public HeavyReteEntity017(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity018 extends HeavyReteEntity {
        public HeavyReteEntity018(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity019 extends HeavyReteEntity {
        public HeavyReteEntity019(Number id) {
            super(id);
        }
    }

    public static final class HeavyReteEntity020 extends HeavyReteEntity {
        public HeavyReteEntity020(Number id) {
            super(id);
        }
    }
}
