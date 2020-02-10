package com.tibco.cep.releases.bom;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:27 PM
 */
public interface Bom
    extends NamedComponent, AddOns {

    AddOns getAddOns();

    Assemblies getAssemblies();

    FileSets getFileSets();

}
