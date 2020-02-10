package com.tibco.cep.runtime.session.impl.locks;

import com.tibco.cep.runtime.session.impl.locks.BloomFilterImpl;

import java.io.*;

/*
* Author: Ashwin Jayaprakash Date: Dec 17, 2008 Time: 11:19:05 PM
*/
public class BloomFilterPerfTest {
    static final int START_NUM = Integer.MAX_VALUE - 1000;

    static final int END_NUM = Integer.MAX_VALUE;

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        for (int i = 0; i < 1000; i++) {
            test(i);
        }
    }

    static void test(int round) throws IOException, ClassNotFoundException {
        long start = System.nanoTime();

        BloomFilterImpl.init();
        BloomFilterImpl originalFilter = new BloomFilterImpl();

        add(round, originalFilter);
        byte[] bytes = serialize(originalFilter);

        BloomFilterImpl newFilter = deserialize(bytes);
        int numFound = check(round, newFilter);

        double d = (System.nanoTime() - start) / 1000.0;

        String msg = "Round: [%5s], Found#: [%5s], Time micros: [%5s]";
        System.err.println(String.format(msg, round, numFound, d));
    }

    private static void add(int round, BloomFilterImpl f) {
        for (int i = START_NUM + ((END_NUM - START_NUM) / 2); i < END_NUM; i++) {
            f.add(round + ":" + i);
        }
    }

    private static int check(int round, BloomFilterImpl f) {
        int c = 0;

        for (int i = START_NUM; i < END_NUM; i++) {
            if (f.contains(round + ":" + i)) {
                c++;
            }
        }

        return c;
    }

    private static byte[] serialize(BloomFilterImpl f) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(f);

        bos.flush();
        bos.close();

        return bos.toByteArray();
    }

    private static BloomFilterImpl deserialize(byte[] bytes)
            throws ClassNotFoundException, IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);

        BloomFilterImpl f = (BloomFilterImpl) ois.readObject();

        return f;
    }
}
