package com.tibco.cep.studio.ui.forms.components;

import org.eclipse.swt.widgets.Shell;

/**
 * A concept selector dialog which shows only DBConcepts.
 * @author moshaikh
 *
 */
public class DBConceptSelector extends ConceptSelector {

	public DBConceptSelector(Shell shell, String projectName, String conceptPath) {
		super(shell, projectName, conceptPath);
		addFilter(new DBConceptsOnlyViewerFilter(projectName));
	}
}