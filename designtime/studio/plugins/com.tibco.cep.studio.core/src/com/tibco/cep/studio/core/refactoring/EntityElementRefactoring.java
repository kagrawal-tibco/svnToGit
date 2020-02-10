package com.tibco.cep.studio.core.refactoring;

import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;

/** 
 * Stubbed out class for performing formal rename for entities.
 * Currently renames are done via the EntityRenameParticipant,
 * but we'll want to use this to invoke renaming in places
 * other than the Project Explorer
 * @author rhollom
 *
 */
public class EntityElementRefactoring extends ProcessorBasedRefactoring {

	public EntityElementRefactoring(RefactoringProcessor processor) {
		super(processor);
	}

}
