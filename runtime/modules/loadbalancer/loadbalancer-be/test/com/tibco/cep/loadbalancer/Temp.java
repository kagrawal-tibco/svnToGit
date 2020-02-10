package com.tibco.cep.loadbalancer;

/*
* Author: Ashwin Jayaprakash / Date: Apr 6, 2010 / Time: 11:02:35 AM
*/

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Temp {
    public static void main(String[] args) {
        float f = 12.345f;
        float lf = Float.intBitsToFloat(Float.floatToIntBits(f) - 1);
        float rf = Float.intBitsToFloat(Float.floatToIntBits(f) + 1);
        System.out.println(lf + " :: " + f + " :: " + rf);

        System.out.println();

        f = 12345612345123.789654321876543987f;
        System.out.println(f);
        System.out.println(Float.floatToIntBits(f));
        System.out.println(Float.floatToRawIntBits(f));
        System.out.println(Float.intBitsToFloat(Float.floatToIntBits(f)));
        System.out.println(DecimalFormat.getNumberInstance().format(f));

        System.out.println();

        BigDecimal number = new BigDecimal(f);
        number = number.setScale(18, RoundingMode.HALF_UP);
        System.out.println(number);

        System.out.println();

        double d = 12.345;
        double ld = Double.longBitsToDouble(Double.doubleToLongBits(d) - 1);
        double rd = Double.longBitsToDouble(Double.doubleToLongBits(d) + 1);
        System.out.println(ld + " :: " + d + " :: " + rd);
    }

    public static void main34dfg(String[] args) throws Exception {

        Class objectStreamClass = Class.forName("java.io.ObjectStreamClass$FieldReflector");
        Field unsafeField = objectStreamClass.getDeclaredField("unsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        System.out.println("Page size: " + unsafe.pageSize());
        System.out.println("Addr size: " + unsafe.addressSize());
    }

    public static void main234(String[] args) {
        Integer[] a = {100, 34};
        Object[] b = a;

        System.out.println((b instanceof Double[]));
        System.out.println((b instanceof Integer[]));
        System.out.println((b instanceof Object[]));

        System.out.println(a.getClass());
        System.out.println(b.getClass());

        for (Object o : b) {
            System.out.println(o);
        }

        System.out.println("----------------");

        LinkedList[] ll = {new LinkedList(), new LinkedList()};
        List[] l = ll;
        Collection[] c = l;
        Object[] oo = c;

        System.out.println((l instanceof LinkedList[]));
        System.out.println((l instanceof List[]));
        System.out.println((c instanceof List[]));
        System.out.println((oo instanceof List[]));

        System.out.println(ll.getClass());
        System.out.println(l.getClass());
        System.out.println(c.getClass());
        System.out.println(oo.getClass());

        //------------------


        byte[] data = new byte[4];

        int number = 10447;
        System.out.println(Integer.toBinaryString(number) + " : Actual : " + number);

        int n1 = (number >>> 24) & 0xFF;
        int n2 = (number >>> 16) & 0xFF;
        int n3 = (number >>> 8) & 0xFF;
        int n4 = (number >>> 0) & 0xFF;

        data[0] = (byte) n1;
        data[1] = (byte) n2;
        data[2] = (byte) n3;
        data[3] = (byte) n4;

        int x1 = (data[0] << 24);
        int x2 = (data[1] << 16);
        int x3 = (data[2] << 8);
        int x4 = (data[3] << 0);

        int x = x1 + x2 + x3 + x4;
        System.out.println(Integer.toBinaryString(x) + " : Final : " + x);
    }
}
