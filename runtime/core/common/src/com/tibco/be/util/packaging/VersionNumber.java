package com.tibco.be.util.packaging;
/*
 * User: nprade
 * Date: Apr 15, 2010
 * Time: 2:53:49 PM
 */


public final class VersionNumber {


    public VersionNumber() {
    }


    public static int getElementVersionNumber(long l, int position) {
        if ((position < 0) || (position > 3)) {
            throw new IllegalArgumentException("Element number must be between 0 and 3 inclusive");
        }
        for (int i = 0; i < 3 - position; i++) {
            l >>= 16;
        }
        return (new Long(l % 65536L)).intValue();
    }


    public static String getVersionNumber(long l) {
        return getElementVersionNumber(l, 0)
                + "." + getElementVersionNumber(l, 1)
                + "." + getElementVersionNumber(l, 2)
                + "." + getElementVersionNumber(l, 3);
    }


    public static long getVersionNumber(String s) {
        if ((s == null) || s.isEmpty()) {
            s = "0.0.0.0";
        }
        long l = 0L;
        final int firstDot = s.indexOf('.');
        if (firstDot == -1) {
            l = getElementVersionNumber(l, 0, new Integer(s));
        } else {
            l = getElementVersionNumber(l, 0, new Integer(s.substring(0, firstDot)));
            int secondDot = s.indexOf('.', firstDot + 1);
            if (secondDot == -1) {
                l = getElementVersionNumber(l, 1, new Integer(s.substring(firstDot + 1)));
            } else {
                l = getElementVersionNumber(l, 1, new Integer(s.substring(firstDot + 1, secondDot)));
                int thirdDot = s.indexOf('.', secondDot + 1);
                if (thirdDot == -1) {
                    l = getElementVersionNumber(l, 2, new Integer(s.substring(secondDot + 1)));
                } else {
                    l = getElementVersionNumber(l, 2, new Integer(s.substring(secondDot + 1, thirdDot)));
                    int fourthDot = s.indexOf('.', thirdDot + 1);
                    if (fourthDot == -1) {
                        l = getElementVersionNumber(l, 3, new Integer(s.substring(thirdDot + 1)));
                    } else {
                        l = getElementVersionNumber(l, 3, new Integer(s.substring(thirdDot + 1, fourthDot)));
                    }
                }
            }
        }
        return l;
    }


    public static long getElementVersionNumber(long l, int position, int element) {
        if ((position < 0) || (position > 3)) {
            throw new IllegalArgumentException("Element number must be between 0 and 3 inclusive");
        }
        int k = getElementVersionNumber(l, position);
        long l1 = 1L;
        for (int i1 = 0; i1 < 3 - position; i1++) {
            l1 <<= 16;
        }

        long l2 = (long) k * l1;
        long l3 = l - l2;
        return l3 + (long) element * l1;
    }
}
