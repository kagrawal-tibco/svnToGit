package com.tibco.cep.studio.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class RefactoringUtils {

	private static final String REFACTORING_EXT_PT_ID = "refactoringParticipant";
	private static RefactoringParticipant[] fRefactoringParticpants;

	public synchronized static RefactoringParticipant[] getRefactoringParticipants() {
		// do not cache this array.  For multiple selections, a new RefactoringParticipant must be created for each IResource 
		// (for instance, deleting multiple files)
//		if (fRefactoringParticpants == null) {
			List<RefactoringParticipant> participants = new ArrayList<RefactoringParticipant>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, REFACTORING_EXT_PT_ID);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("participant");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("participant");
						if (executableExtension instanceof RefactoringParticipant) {
							participants.add((RefactoringParticipant) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fRefactoringParticpants = new RefactoringParticipant[participants.size()];
			return participants.toArray(fRefactoringParticpants);
//		}
//		return fRefactoringParticpants;
	}
	
}
