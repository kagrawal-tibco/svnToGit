package com.tibco.cep.sharedresource.refactor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;

/*
@author Huabin Zhang (huzhang@tibco-support.com)
@date Dec 23, 2012 10:19:32 PM
*/

public class ASConnectionRefactoringParticipant extends AbstractSharedResourceRefactoring {

	@Override
	public String getExtension() {
		return ("sharedascon");
	}

	@Override
	public String getName() {
		return ("AS Connection refactoring participant");
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return null;
	}

}
