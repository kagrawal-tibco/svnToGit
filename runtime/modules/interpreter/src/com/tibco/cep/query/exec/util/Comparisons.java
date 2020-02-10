package com.tibco.cep.query.exec.util;

import java.util.Calendar;
import java.util.Comparator;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.util.FixedKeys;
import com.tibco.cep.runtime.model.element.PropertyAtom;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 7, 2007
 * Time: 7:41:44 PM
 */


public class Comparisons {


    public static int compare(Comparable o1, Comparable o2) {
        if ((FixedKeys.NULL == o1)
                || (null == o1)
                || ((o1 instanceof PropertyAtom) && !((PropertyAtom) o1).isSet())) {
            if ((FixedKeys.NULL == o2)
                    || (null == o2)
                    || ((o2 instanceof PropertyAtom) && !((PropertyAtom) o2).isSet())) {
                return 0;
            }
            // Nulls first.
            return -1;
        } else if ((FixedKeys.NULL == o2)
                || (null == o2)
                || ((o2 instanceof PropertyAtom) && !((PropertyAtom) o2).isSet())) {
            return 1;
        }

        return o1.compareTo(o2);
    }


    public static int compare(Entity o1, Entity o2) {
        if (null == o1) {
            if (null == o2) {
                return 0;
            }
            // Nulls first.
            return -1;
        } else if (null == o2) {
            return 1;
        }
        return (int) Math.signum(o1.getId() - o2.getId());
    }


    public static int compare(Object o1, Object o2) {
        if ((FixedKeys.NULL == o1)
                || (null == o1)
                || ((o1 instanceof PropertyAtom) && !((PropertyAtom) o1).isSet())) {
            if ((FixedKeys.NULL == o2)
                    || (null == o2)
                    || ((o2 instanceof PropertyAtom) && !((PropertyAtom) o2).isSet())) {
                return 0;
            }
            // Nulls first.
            return -1;
        } else if ((FixedKeys.NULL == o2)
                || (null == o2)
                || ((o2 instanceof PropertyAtom) && !((PropertyAtom) o2).isSet())) {
            return 1;
        }

        // Arbitrary order
        return (int) Math.signum(o1.hashCode() - o2.hashCode());
    }


    public static boolean equalTo(Boolean s1, Object s2) {
        if (null == s1) {
            return null == s2;
        }
        return s1.equals(s2);
    }


    public static boolean equalTo(Calendar s1, Object s2) {
        if (null == s1) {
            return null == s2;
        } else if (null == s2) {
            return false;
        }
        return (s2 instanceof Calendar)
                && (s1.getTimeInMillis() == ((Calendar) s2).getTimeInMillis());
    }


    public static boolean equalTo(Entity s1, Object s2) {
        if (null == s1) {
            return null == s2;
        } else if (null == s2) {
            return false;
        }
        return (s2 instanceof Entity)
                && (s1.getId() == ((Entity) s2).getId());
    }


    public static boolean equalTo(Number s1, Object s2) {
        if (null == s1) {
            return null == s2;
        } else if (null == s2) {
            return false;
        }
        return (s2 instanceof Number)
                && (s1.doubleValue() == ((Number) s2).doubleValue());
    }


    public static boolean equalTo(Object s1, Object s2) {
        if (null == s1) {
            return null == s2;
        } else if (null == s2) {
            return false;
        }
        if (s1 instanceof Calendar) {
            return (s2 instanceof Calendar)
                    && (((Calendar) s1).getTimeInMillis() == ((Calendar) s2).getTimeInMillis());
        }
        if (s1 instanceof Entity) {
            return (s2 instanceof Entity)
                    && (((Entity) s1).getId() == ((Entity) s2).getId());
        }
        if (s1 instanceof Number) {
            return (s2 instanceof Number)
                    && (((Number) s1).doubleValue() == ((Number) s2).doubleValue());
        }
        return s1.equals(s2);
    }


    public static boolean equalTo(String s1, Object s2) {
        if (null == s1) {
            return null == s2;
        }
        return s1.equals(s2);
    }


    public static boolean greaterThan(Calendar s1, Object s2) {
        return (null != s1)
                && s1.after(s2);
    }


    public static boolean greaterThan(Number s1, Object s2) {
        return (null != s1)
                && (s2 instanceof Number)
                && (s1.doubleValue() > ((Number) s2).doubleValue());
    }


    public static boolean greaterThan(String s1, Object s2) {
        return (null != s1)
                && (s2 instanceof String)
                && (s1.compareTo((String) s2) > 0);
    }


    public static boolean greaterThanOrEqualTo(Calendar s1, Object s2) {
        return (null != s1)
                && (s2 instanceof Calendar)
                && (s1.getTimeInMillis() >= ((Calendar) s2).getTimeInMillis());
    }


    public static boolean greaterThanOrEqualTo(Number s1, Object s2) {
        return (null != s1)
                && (s2 instanceof Number)
                && (s1.doubleValue() >= ((Number) s2).doubleValue());
    }


    public static boolean greaterThanOrEqualTo(String s1, Object s2) {
        return (null != s1)
                && (s2 instanceof String)
                && (s1.compareTo((String) s2) >= 0);
    }


    public static boolean isBetween(Calendar expr, Calendar bound1, Calendar bound2) {
        if ((null == expr) && (null == bound1) && (null == bound2)) {
            return true;
        }
        if ((null == expr) || (null == bound1) || (null == bound2)) {
            return false;
        }
        final int bound1CmpExpr = bound1.compareTo(expr);
        final int exprCmpBound2 = expr.compareTo(bound2);
        return (((bound1CmpExpr <= 0) && (exprCmpBound2 <= 0))
                || ((bound1CmpExpr >= 0) && (exprCmpBound2 >= 0)));
    }


    public static boolean isBetween(Number expr, Number bound1, Number bound2) {
        return ((null == expr) && (null == bound1) && (null == bound2))
                || ((null != expr) && (null != bound1) && (null != bound2) && isBetween(expr.doubleValue(), bound1.doubleValue(), bound2.doubleValue()));
    }


    public static boolean isBetween(String expr, String bound1, String bound2) {
        return ((null == expr) && (null == bound1) && (null == bound2))
                || ((null != expr) && (null != bound1) && (null != bound2) && (bound1.compareTo(expr) <= 0) && (bound2.compareTo(expr) >= 0));
    }


    public static boolean isBetween(double expr, double bound1, double bound2) {
        return ((bound1 <= expr) && (expr <= bound2))
                || ((bound1 >= expr) && (expr >= bound2));
    }


    public static boolean lessThan(Calendar s1, Object s2) {
        return (null != s1)
                && s1.before(s2);
    }


    public static boolean lessThan(Number s1, Object s2) {
        return (null != s1)
                && (s2 instanceof Number)
                && (s1.doubleValue() < ((Number) s2).doubleValue());
    }


    public static boolean lessThan(String s1, Object s2) {
        return (null != s1)
                && (s2 instanceof String)
                && (s1.compareTo((String) s2) < 0);
    }


    public static boolean lessThanOrEqualTo(Calendar s1, Object s2) {
        return (null != s1)
                && (s2 instanceof Calendar)
                && (s1.getTimeInMillis() <= ((Calendar) s2).getTimeInMillis());
    }


    public static boolean lessThanOrEqualTo(Number s1, Object s2) {
        return (null != s1)
                && (s2 instanceof Number)
                && (s1.doubleValue() <= ((Number) s2).doubleValue());
    }


    public static boolean lessThanOrEqualTo(String s1, Object s2) {
        return (null != s1)
                && (s2 instanceof String)
                && (s1.compareTo((String) s2) <= 0);
    }


    public static boolean notEqualTo(Boolean s1, Object s2) {
        if (null == s1) {
            return (null != s2);
        }
        return !s1.equals(s2);
    }


    public static boolean notEqualTo(Calendar s1, Object s2) {
        if (null == s1) {
            return (null != s2);
        }
        return (!(s2 instanceof Calendar))
                || (s1.getTimeInMillis() != ((Calendar) s2).getTimeInMillis());
    }


    public static boolean notEqualTo(Entity s1, Object s2) {
        if (null == s1) {
            return (null != s2);
        }
        return (!(s2 instanceof Entity))
                || (s1.getId() != ((Entity) s2).getId());
    }


    public static boolean notEqualTo(Number s1, Object s2) {
        if (null == s1) {
            return (null != s2);
        }
        return (!(s2 instanceof Number))
                || (s1.doubleValue() != ((Number) s2).doubleValue());
    }


    public static boolean notEqualTo(Object s1, Object s2) {
        if (null == s1) {
            return (null != s2);
        } else if (null == s2) {
            return true;
        }
        if (s1 instanceof Calendar) {
            return (!(s2 instanceof Calendar))
                    || (((Calendar) s1).getTimeInMillis() != ((Calendar) s2).getTimeInMillis());
        }
        if (s1 instanceof Entity) {
            return (!(s2 instanceof Entity))
                    || (((Entity) s1).getId() != ((Entity) s2).getId());
        }
        if (s1 instanceof Number) {
            return (!(s2 instanceof Number))
                    || (((Number) s1).doubleValue() != ((Number) s2).doubleValue());
        }
        return !s1.equals(s2);
    }


    public static boolean notEqualTo(String s1, Object s2) {
        if (null == s1) {
            return (null != s2);
        } else if (null == s2) {
            return true;
        }
        return !s1.equals(s2);
    }


    public static class Comparators {

        public static final Comparator<Comparable> COMPARABLES = new Comparator<Comparable>() {
            public int compare(Comparable o1, Comparable o2) {
                return Comparisons.compare(o1, o2);
            }
        };


        public static final Comparator<Object> ENTITIES = new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return Comparisons.compare((Entity) o1, (Entity) o2);
            }
        };


        public static final Comparator<Object> DEFAULT = new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if ((o1 instanceof Comparable) && (o2 instanceof Comparable)) {
                    return Comparisons.compare((Comparable) o1, (Comparable) o2);
                }
                return Comparisons.compare(o1, o2);
            }
        };

    }
}
