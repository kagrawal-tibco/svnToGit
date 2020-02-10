package com.tibco.cep.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * User: ssubrama
 * Creation Date: May 7, 2008
 * Creation Time: 5:11:20 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */

/**
 * BitMapHelper class provides a bunch of method to encode integer array into a bitmap vector.
 * The base could be any arbitary integer that is used while encoding and decoding.
 * The code assumes that the base is very closely associated to the nos itself.
 *
 * The prime user of this code is TypeIdAssigner
 */
public class BitMapHelper {

    int base;

    public BitMapHelper(int base) {
        this.base = base;
    }

    public int[] encode2BitMapVector(Collection<Integer> data)
    {
        //Collections.sort(data); //Avoid System array copy
        int[] bmv = new int[1]; //atleast 1
        for (int item : data) {
            int quot = ((item - base) >> 0x5);     //div 32
            if (quot >= bmv.length) {
                int[] nbmv = new int[quot+1];
                System.arraycopy(bmv,0,nbmv,0,bmv.length);
                bmv = nbmv;
            }
            int remainder = (item - base) & 0x1F; // Mod of 32 - equivalent of a % 32
            bmv[quot] |= (1 << remainder);  //0..31 bits in an int
        }


        return bmv;
    }

    public  List<Integer> decodeBitMapVector2List(int[] bmv)
    {
        ArrayList<Integer> values = new ArrayList<Integer>();
        int offset = base;
        for (int val: bmv) {
            for (int i=0; i<32; i++) {
                int mask = 0x1 << i;
                if ((val & mask) == mask) {
                    values.add(offset + i);
                }
            }
            offset = offset + 32;
        }
        return values;

    }

    public int numElements(int[] bmv) {
        int totalbits = 0;

        for (int val : bmv) {
            totalbits += Integer.bitCount(val);
        }
        return totalbits;
    }


    public boolean exists(int[] bmv, int value )
    {
        return exists(bmv, value, this.base);
    }

    public boolean exists(int[] bmv, int value, int base )
    {

        int v = value - base;
        int q = v >> 0x5;     //div 32
        int r = v & 0x1F; // Mod of 32 - equivalent of a % 32
        int m = 1 << r; //mask
        if (q >= bmv.length) return false;
        return ((bmv[q] & m) == m);


    }

    public boolean matches(int [] topic, int [] interest) {
        int len = topic.length < interest.length ? topic.length : interest.length;
        for (int i=0; i < len; i++) {
            if ((topic[i] & interest[i]) != 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check if there is any element in bmv1 present in bmv2
     * @param bmv1
     * @param bmv2
     * @return
     */
    public boolean hasIntersection(int[] bmv1, int[] bmv2) {
         for (int bm : bmv1) {
             if (exists(bmv2, bm, 0)) return true;
         }
        return false;
    }


    public static void main(String[] args) {

        System.out.println("Mod of 68 % 32 is " + (68 & 0x1F));
        System.out.println("Div of 68 / 32 is " + (68 >> 0x5));

        List<Integer> testData = Arrays.asList(1012, 1011, 1013);

        List<Integer> sub = Arrays.asList(1012);

        BitMapHelper bmhelper = new BitMapHelper(1000);
        int[] bmv = bmhelper.encode2BitMapVector(testData);
        int [] bmv2 = bmhelper.encode2BitMapVector(sub);
        System.out.println("INTERSECTION VALUE = " + bmhelper.matches(bmv2, bmv));
        List<Integer> retData = bmhelper.decodeBitMapVector2List(bmv);
        System.out.println("retdata = " + retData);
        System.out.println("Exist 1032 = " + bmhelper.exists(bmv, 1032));
        System.out.println("Exist 5000 = " + bmhelper.exists(bmv, 5000));
    }

}
