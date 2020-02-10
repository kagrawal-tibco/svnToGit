package com.tibco.be.ws.scs.impl.filter;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/5/12
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultFilterContext implements IFilterContext {

    /**
     * Denotes absolute path of  file|dir to be used as container.
     */
    private File containerPathFile;

    public File getContainerPathFile() {
        return containerPathFile;
    }

    public void setContainerPathFile(File containerPathFile) {
        this.containerPathFile = containerPathFile;
    }
}
