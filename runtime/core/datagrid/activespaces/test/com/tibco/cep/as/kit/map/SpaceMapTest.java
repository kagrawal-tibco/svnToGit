package com.tibco.cep.as.kit.map;

import com.tibco.as.space.ASException;
import com.tibco.as.space.Member.DistributionRole;
import com.tibco.as.space.MemberDef;
import com.tibco.as.space.Metaspace;
import com.tibco.as.space.SpaceDef;
import com.tibco.as.space.SpaceDef.DistributionPolicy;
import com.tibco.cep.as.kit.collection.DiscardableSet;
import com.tibco.cep.as.kit.tuple.DefaultTupleCodec;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

import java.io.*;
import java.util.ArrayList;

/*
 * Author: Ashwin Jayaprakash Date: Apr 25, 2009 Time: 12:50:11 AM
 */
public class SpaceMapTest {
    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SpaceMapTest.class);

    public static void main(String[] args) throws ASException {
        String metaspaceName = "aj_test_" + SpaceMap.class.getSimpleName();
        String listenUrl = "";
        String discoverUrl = "";
        File dataStoreDir = new File(
                System.getProperty("user.home")
                        + System.getProperty("file.separator")
                        + "as_store"
                        + System.getProperty("file.separator")
                        + SpaceMapTest.class.getSimpleName());
        dataStoreDir.mkdirs();

        MemberDef cd = MemberDef.create()
                .setDiscovery(discoverUrl)
                .setListen(listenUrl);

        Metaspace metaspace = Metaspace.connect(metaspaceName, cd);

        SpaceMapCreator.Parameters<String, Person> parameters =
                new SpaceMapCreator.Parameters<String, Person>()
                        .setSpaceName("test_person_space")
                        .setRole(DistributionRole.SEEDER)
                        .setDistributionPolicy(DistributionPolicy.NON_DISTRIBUTED)
                        .setKeyClass(String.class)
                        .setValueClass(Person.class)
                        .setTupleCodec(new DefaultTupleCodec())
                        .setPersistenceType(SpaceDef.PersistenceType.SHARE_NOTHING)
                        .setPersistencePolicy(SpaceDef.PersistencePolicy.ASYNC)
                        .setLocalStorePath(dataStoreDir.getAbsolutePath());

        LOGGER.log(Level.INFO, "Using [" + dataStoreDir.getAbsolutePath() + "]");

        //-----------

        SpaceMap<String, Person> spaceMap = SpaceMapCreator.create(metaspace, parameters);

        if (args.length == 0) {
            final int total = 10000;
            final int iters = 50;

            for (int i = 0; i < iters; i++) {
                runTest(i, spaceMap, total);
                System.gc();
                LOGGER.log(Level.INFO, "\n\n");
            }

            LOGGER.log(Level.INFO, "Times taken. Iterations=%d. Items per iteration=%d", iters, total);
            LOGGER.log(Level.INFO, "RepeatPut, NewPut, Get, Remove1, Remove2", total);
            for (int i = 0; i < iters; i++) {
                LOGGER.log(Level.INFO, "%d, %d, %d, %d, %d",
                        repeatPutMillis.get(i), newPutMillis.get(i), readMillis.get(i), removeAlt1Millis.get(i),
                        removeAlt2Millis.get(i));
            }
        }
        else {
            LOGGER.log(Level.INFO, "Done nothing...Just recovered");
        }

        //-----------

        spaceMap.discard();
        metaspace.closeAll();
    }

    static ArrayList<Long> repeatPutMillis = new ArrayList<Long>();

    static ArrayList<Long> newPutMillis = new ArrayList<Long>();

    static ArrayList<Long> readMillis = new ArrayList<Long>();

    static ArrayList<Long> removeAlt1Millis = new ArrayList<Long>();

    static ArrayList<Long> removeAlt2Millis = new ArrayList<Long>();

    private static void runTest(final int iter, SpaceMap<String, Person> spaceMap, final int total) {
        LOGGER.log(Level.INFO, "============ Repeat put test ============");

        final String nameTemplate = "user_repeat_";

        long start = System.currentTimeMillis();
        int i = 0;
        for (; i < total; i++) {
            Person p = new Person(nameTemplate + i, i);
            spaceMap.put(p.getName(), p);
        }
        repeatPutMillis.add(System.currentTimeMillis() - start);
        LOGGER.log(Level.INFO, "Repeat put %d keys", i);

        //-----------

        LOGGER.log(Level.INFO, "============ New put test ============");

        final String newNameTemplate = "user_non_repeat_" + iter + "_";

        start = System.currentTimeMillis();
        i = 0;
        for (; i < total; i++) {
            Person p = new Person(newNameTemplate + i, i);
            spaceMap.put(p.getName(), p);
        }
        newPutMillis.add(System.currentTimeMillis() - start);
        LOGGER.log(Level.INFO, "Non repeat put %d keys", i);

        //-----------

        LOGGER.log(Level.INFO, "============ Iterator/Get test ============");

        start = System.currentTimeMillis();
        i = 0;
        DiscardableSet<String> keySet = spaceMap.keySet();
        for (String s : keySet) {
            i++;
        }
        keySet.discard();
        readMillis.add(System.currentTimeMillis() - start);
        LOGGER.log(Level.INFO, "Iterated %d keys", i);

        //-----------

        LOGGER.log(Level.INFO, "============ Remove alternate repeat test ============");

        start = System.currentTimeMillis();
        i = 0;
        int r = 0;
        for (; i < total; i++) {
            if ((i & 1) == 0) {
                spaceMap.remove(nameTemplate + i);
                r++;
            }
        }
        removeAlt1Millis.add(System.currentTimeMillis() - start);
        LOGGER.log(Level.INFO, "Removed %d keys", r);

        //-----------

        LOGGER.log(Level.INFO, "============ Remove remaining repeat test ============");

        start = System.currentTimeMillis();
        i = 0;
        r = 0;
        for (; i < total; i++) {
            if ((i & 1) == 0) {
                spaceMap.remove(nameTemplate + i);
                r++;
            }
        }
        removeAlt2Millis.add(System.currentTimeMillis() - start);
        LOGGER.log(Level.INFO, "Removed %d keys", r);
    }

    public static class Person implements Externalizable {
        protected String name;

        protected int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(name);
            out.writeInt(age);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = in.readUTF();
            age = in.readInt();
        }
    }
}
