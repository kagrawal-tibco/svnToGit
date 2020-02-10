package com.tibco.cep.releases.bom.impl;

import com.tibco.cep.releases.bom.AddOns;
import com.tibco.cep.releases.bom.Assemblies;
import com.tibco.cep.releases.bom.Bom;
import com.tibco.cep.releases.bom.FileSets;

/**
 * User: nprade
 * Date: 6/27/11
 * Time: 6:56 PM
 */
public class BomImpl
        extends AddOnsImpl
        implements Bom {

    private final AddOns addOns;
    private final Assemblies assemblies;
    private final FileSets fileSets;
    private final String name;


    public BomImpl(
            String name,
            AddOns addOns,
            Assemblies assemblies,
            FileSets fileSets) {
        super(addOns);
        this.addOns = addOns;
        this.assemblies = assemblies;
        this.fileSets = fileSets;
        this.name = name;
    }


    @Override
    public AddOns getAddOns() {
        return this.addOns;
    }


    @Override
    public Assemblies getAssemblies() {
        return this.assemblies;
    }


    @Override
    public FileSets getFileSets() {
        return this.fileSets;
    }


    @Override
    public String getName() {
        return this.name;
    }
}
