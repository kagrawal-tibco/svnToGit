package com.tibco.be.functions.object;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomStringImpl;
import com.tibco.xml.data.primitive.ExpandedName;

/*
* Author: Ashwin Jayaprakash Date: Aug 25, 2008 Time: 10:26:34 PM
*/
public class XMLSerializationTest {
    public static void main(String[] args) throws Exception {
        ConceptX x = new ConceptX();
        x.p1.setValue("one");
        x.p2.setValue("two");
        x.p3.setValue("three");
        x.p4.setValue("four");
        x.p5.setValue("five");
        x.p6.setValue("six");
        x.p7.setValue("seven");
        x.p8.setValue("eight");
        x.p9.setValue("nine");
        x.p10.setValue("ten");

        String string1 = ObjectHelper.filterAndSerializeUsingDefaults(x,
                "abababababababababababababababababab|rstuvwxyz1212121212121212kmkmkmkmkmkm");
        System.err.println("-- filterAndSerializeUsingDefaults --");
        System.err.println(string1);

        String string2 = ObjectHelper.serializeUsingDefaults(x);
        System.err.println();
        System.err.println("-- serializeUsingDefaults --");
        System.err.println(string2);

        if (string1.equalsIgnoreCase(string2)) {
            throw new RuntimeException("Regex did not work.");
        }

        //------------

        for (int i = 0; i < 5; i++) {
            testFilterAndSerializeLoop(x);
        }

        for (int i = 0; i < 1024; i++) {
            ObjectHelper.filterAndSerializeUsingDefaults(x, ("a:" + i));
        }

        System.err.println("Cache purged...somewhat");

        for (int i = 0; i < 5; i++) {
            testFilterAndSerializeLoop(x);
        }

        //------------

        System.err.println();

        for (int i = 0; i < 5; i++) {
            testSerializeLoop(x);
        }

        for (int i = 0; i < 1024; i++) {
            ObjectHelper.serializeUsingDefaults(x);
        }

        for (int i = 0; i < 5; i++) {
            testSerializeLoop(x);
        }

    }

    private static void testSerializeLoop(ConceptX x) {
        int outerLoop = 100;
        int innerLoop = 1000;

        long time = 0;
        for (int i = 0; i < outerLoop; i++) {
            time += serialize(x, innerLoop);
        }

        double d = time / outerLoop;
        d = d / innerLoop;

        System.err.println("Serialize time micros: " + (d / 1000));
    }

    private static long serialize(ConceptX x, int loops) {
        long start = System.nanoTime();

        String s = null;
        for (int i = 0; i < loops; i++) {
            s = ObjectHelper.serializeUsingDefaults(x);
        }

        return System.nanoTime() - start;
    }

    private static void testFilterAndSerializeLoop(ConceptX x) {
        int outerLoop = 100;
        int innerLoop = 1000;

        long time = 0;
        for (int i = 0; i < outerLoop; i++) {
            time += filterAndSerialize(x, innerLoop);
        }

        double d = time / outerLoop;
        d = d / innerLoop;

        System.err.println("FilterAndSerialize time micros: " + (d / 1000));
    }

    private static long filterAndSerialize(ConceptX x, int loops) {
        long start = System.nanoTime();

        String s = null;
        for (int i = 0; i < loops; i++) {
            s = ObjectHelper.filterAndSerializeUsingDefaults(x,
                    "abababababababababababababababababab|rstuvwxyz1212121212121212kmkmkmkmkmkm");
        }

        return System.nanoTime() - start;
    }

    private static class ConceptX extends ConceptImpl {
        protected PropertyAtomStringImpl p1 =
                new PropertyAtomStringX(this, "abcdefghijklmn444455556666mmmmmm");

        protected PropertyAtomStringImpl p2 =
                new PropertyAtomStringX(this, "pqrstuvwxyz1234567890abcd ");

        protected PropertyAtomStringImpl p3 =
                new PropertyAtomStringX(this, "abababababababababababababababababab");

        protected PropertyAtomStringImpl p4 =
                new PropertyAtomStringX(this, "rstuvwxyz1212121212121212kmkmkmkmkmkm");

        protected PropertyAtomStringImpl p5 =
                new PropertyAtomStringX(this, "rstuvwxyz4444444444444444kmkmkmkmkmkm");

        protected PropertyAtomStringImpl p6 =
                new PropertyAtomStringX(this, "rstuvwxyz111wwwwwww222222kmkmkmkmkmkm");

        protected PropertyAtomStringImpl p7 =
                new PropertyAtomStringX(this, "werwerwerwerwerwerwerwerwerwerwerwer");

        protected PropertyAtomStringImpl p8 =
                new PropertyAtomStringX(this, "qwertyyuuioplkjmnhbs22343swswwerwee");

        protected PropertyAtomStringImpl p9 =
                new PropertyAtomStringX(this, "hello");

        protected PropertyAtomStringImpl p10 =
                new PropertyAtomStringX(this, "blah123123123hello");


        public com.tibco.cep.runtime.model.element.Property[] getProperties() {
            return new com.tibco.cep.runtime.model.element.Property[]{p1, p2, p3, p4, p5, p6, p7,
                    p8, p9, p10};
        }

        @Override
        public ExpandedName getExpandedName() {
            return new ExpandedName(ConceptX.class.getName());
        }

        public void removeProperties() {
        }
    }

    private static class PropertyAtomStringX extends PropertyAtomStringImpl {
        protected String name;

        protected PropertyAtomStringX(Object parent, String name) {
            super(1, parent);

            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
