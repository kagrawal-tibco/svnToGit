package com.tibco.cep.runtime.session.impl.locks;

import com.tibco.cep.runtime.session.impl.locks.BloomFilterImpl;

/*
* Author: Ashwin Jayaprakash Date: Jan 8, 2009 Time: 5:30:56 PM
*/
public class BloomFilterTest {
    public static void main(String[] args) {
        BloomFilterImpl.init();

        BloomFilterImpl f = new BloomFilterImpl();

        for (int i = 12459; i < 12459 + 1000; i++) {
            f.add("777:" + i);
        }

        check(f, "12");
        check(f, "-1");
        check(f, "777:12459");
        check(f, "778:12459");
        check(f, "777:12478");
        check(f, "(12459 + 36)");
        check(f, "1086");
    }

    private static void check(BloomFilterImpl f, String x) {
        System.err.println("Contains " + x + ": " + f.contains(x));
    }
}
