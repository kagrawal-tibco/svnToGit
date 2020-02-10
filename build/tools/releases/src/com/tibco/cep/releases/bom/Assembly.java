package com.tibco.cep.releases.bom;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/27/11
 * Time: 6:46 PM
 */
public interface Assembly
        extends NamedComponent, FileSets, Comparable<Assembly> {


    boolean isPortSpecific();


}
