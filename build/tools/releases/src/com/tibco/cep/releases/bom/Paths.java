package com.tibco.cep.releases.bom;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 4:17 PM
 */
public interface Paths
        extends SortedSet<String> {

    boolean containsPath(String path);


}
