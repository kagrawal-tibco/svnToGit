package com.tibco.cep.releases.bom;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 7/5/11
 * Time: 2:36 PM
 */
@SuppressWarnings({"UnusedDeclaration"})
public class BomProvider {


    final Map<String, Bom> nameToBom = new HashMap<String, Bom>();


    public Bom getBom(
            String name) {

        return this.nameToBom.get(name);
    }


    public Collection<Bom> getBoms() {

        return Collections.unmodifiableCollection(this.nameToBom.values());
    }


    public Set<String> getBomNames() {

        return Collections.unmodifiableSet(this.nameToBom.keySet());
    }


    public void register(
            Bom bom) {

        this.nameToBom.put(bom.getName(), bom);
    }


}
