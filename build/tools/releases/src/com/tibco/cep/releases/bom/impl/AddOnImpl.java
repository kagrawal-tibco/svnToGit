package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.AddOn;
import com.tibco.cep.releases.bom.Assembly;

import java.util.Map;
import java.util.SortedMap;

/**
 * User: nprade
 * Date: 6/28/11
 * Time: 5:58 PM
 */
public class AddOnImpl
        extends AssembliesImpl
        implements AddOn {


    private final String name;


    public AddOnImpl(
            String name,
            Map<String, Assembly> map) {

        super(map);

        if (null == name) {
            throw new NullPointerException();
        }
        this.name = name;
    }


    public AddOnImpl(
            String name,
            SortedMap<String, Assembly> map) {

        super(map);

        if (null == name) {
            throw new NullPointerException();
        }
        this.name = name;
    }


    @Override
    public int compareTo(
            AddOn o) {

        if (null == o) {
            return 1;
        } else {
            return this.name.compareTo(o.getName());
        }
    }


    @Override
    public boolean equals(
            Object o) {

        return (null != o)
                && this.getClass().equals(o.getClass())
                && this.name.equals(((AddOnImpl) o).name);
    }


    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public int hashCode() {

        long longHash = super.hashCode();
        longHash = longHash * 29 + this.name.hashCode();
        longHash ^= (longHash >>> 32);
        return (int) longHash;
    }


    @Override
    public String toString() {
        return this.getName() + "@" + this.hashCode();
    }


}
