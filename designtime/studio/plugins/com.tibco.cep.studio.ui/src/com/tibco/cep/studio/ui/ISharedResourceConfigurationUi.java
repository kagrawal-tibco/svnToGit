package com.tibco.cep.studio.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Composite;

/*
@author ssailapp
@date Aug 16, 2011
 */

public interface ISharedResourceConfigurationUi {
	
	public void createConfigSectionContents(Composite parentUi);
	
	public void setInput(IFile resource);

	public Object getData();
	
}
