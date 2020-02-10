/**
 * 
 */
package com.tibco.cep.studio.core.util.packaging;

import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;


/**
 * 
 * @author pdhar
 *
 */
public interface BARPackager extends Packager {

	/**
	 * 
	 * @return
	 */
	<C extends SARPackager> C getSARPackager();

	/**
	 * 
	 * @return
	 */

	<R extends RuntimeClassesPackager> R getRuntimeClassesPackager();

	/**
	 * 
	 * @return
	 */

	MutableServiceArchive getServiceArchive();
	
	
	/**
	 * Commit all changes
	 */
	void close() throws Exception;

}
