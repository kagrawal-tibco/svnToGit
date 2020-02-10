package com.tibco.cep.studio.debug.core.launch;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

/*
@author ssailapp
@date Jul 22, 2009 1:19:31 PM
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ApplicationSourceLookupDirector extends AbstractSourceLookupDirector {
	
private static Set fFilteredTypes;
	
	static {
		fFilteredTypes = new HashSet();
		//fFilteredTypes.add(ProjectSourceContainer.TYPE_ID);
		//fFilteredTypes.add(WorkspaceSourceContainer.TYPE_ID);
		fFilteredTypes.add(StudioProjectSourceContainer.TYPE_ID);
		fFilteredTypes.add(JarEntrySourceContainer.TYPE_ID);
//		fFilteredTypes.add(ArchiveSourceContainer.TYPE_ID);
//		fFilteredTypes.add(ExternalArchiveSourceContainer.TYPE_ID);
		// can't reference UI constant
		fFilteredTypes.add("org.eclipse.debug.ui.containerType.workingSet");
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.internal.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
	 */
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[]{new ApplicationSourceLookupParticipant()});
	}
	
	public boolean supportsSourceContainerType(ISourceContainerType type) {
		return !fFilteredTypes.contains(type.getId());
	}
}
