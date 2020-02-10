/**
 *
 */
package com.tibco.cep.studio.core.util.packaging;

import java.io.File;


/**
 * @author rmishra
 */
public interface EARPackager extends Packager {

    File getEarFile();
    
}
