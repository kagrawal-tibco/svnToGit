/**
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.validation;

import com.tibco.be.util.config.sharedresources.id.Identity;

/**
 * A sample validator interface for {@link com.tibco.be.util.config.sharedresources.id.Repository}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface RepositoryValidator {
	boolean validate();

	boolean validateIdentity(Identity value);
}
