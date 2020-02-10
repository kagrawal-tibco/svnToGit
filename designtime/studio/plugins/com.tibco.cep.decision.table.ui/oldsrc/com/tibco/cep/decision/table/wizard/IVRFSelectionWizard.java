/**
 * 
 */
package com.tibco.cep.decision.table.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;

/**
 * An interface to be implemented by all wizards required virtual Rule function
 * selection.
 * @author aathalye
 *
 */
public interface IVRFSelectionWizard {
	
	IFile getVRFFile();
	
	Shell getShell();
}
