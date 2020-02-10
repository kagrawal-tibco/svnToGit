package com.tibco.cep.util;

import java.util.LinkedList;
import java.util.List;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/*
* Author: Ashwin Jayaprakash Date: Oct 14, 2009 Time: 5:45:15 PM
*/
public class InternalObjectName {
    protected String domain;

    protected List<String> kvPairs;

    public InternalObjectName(String domain, List<String> kvPairs) {
        this.domain = domain;
        this.kvPairs = kvPairs;
    }

    public InternalObjectName(String domain, String... kvPairs) {
        this.domain = domain;

        this.kvPairs = new LinkedList<String>();
        for (String name : kvPairs) {
            this.kvPairs.add(name);
        }
    }

    public InternalObjectName(InternalObjectName parent, String kvPair) {
        this(parent.getDomain(), new LinkedList<String>(parent.getKvPairs()));

        this.kvPairs.add(kvPair);
    }

    public String getDomain() {
        return domain;
    }

    public List<String> getKvPairs() {
        return kvPairs;
    }

    public ObjectName toObjectName() throws MalformedObjectNameException {
        return toObjectName(this);
    }

    public static ObjectName toObjectName(InternalObjectName ion)
            throws MalformedObjectNameException {
        StringBuilder b = new StringBuilder(ion.getDomain());

        b.append(':');

        boolean start = true;
        for (String namePart : ion.getKvPairs()) {
            if (start == false) {
                b.append(',');
            }
            else {
                start = false;
            }

            b.append(namePart);
        }

        String s = b.toString();

        return new ObjectName(s);
    }
}
