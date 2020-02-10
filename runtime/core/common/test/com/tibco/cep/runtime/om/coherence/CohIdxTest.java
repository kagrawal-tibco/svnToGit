package com.tibco.cep.runtime.om.coherence;
/*
* Author: Ashwin Jayaprakash / Date: Jul 8, 2010 / Time: 11:10:37 AM
*/

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.ValueExtractor;
import com.tangosol.util.filter.GreaterFilter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class CohIdxTest {
    public static class Populate {
        public static void main(String[] args) throws InterruptedException {
            NamedCache namedCache = CacheFactory.getCache("person");

            namedCache.addIndex(new VE(), true, null);

            for (int i = 0; i < 100; i++) {
                Person p = new Person();
                p.setName("blah" + i);
                p.setAge(i + 1);

                namedCache.put(i, p);
            }

            System.out.println("Put total: " + namedCache.size());

            Thread.sleep(Integer.MAX_VALUE);
        }
    }

    public static class Read {
        public static void main(String[] args) {
            NamedCache namedCache = CacheFactory.getCache("person");

            Set keys = namedCache.keySet(new AgeFilter(new VE(), 97));
            System.out.println("Keys: " + keys);
        }
    }

    public static class Person implements Serializable {
        String name;

        int age;

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
    }

    public static class VE implements ValueExtractor, Serializable {
        public VE() {
        }

        @Override
        public Object extract(Object o) {
            Person p = (Person) o;

            return p.getAge();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof VE);
        }

        @Override
        public int hashCode() {
            return getClass().hashCode();
        }
    }

    public static class AgeFilter extends GreaterFilter {
        public AgeFilter() {
        }

        public AgeFilter(ValueExtractor extractor, Comparable oValue) {
            super(extractor, oValue);
        }

        @Override
        public Filter applyIndex(Map mapIndexes, Set setKeys) {
            System.out.println("Inside filter");

            return super.applyIndex(mapIndexes, setKeys);
        }
    }
}
