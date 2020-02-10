package com.tibco.cep.studio.dashboard.ui.chartcomponent.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class PageUtils {

	public static final Section createSection(FormToolkit toolkit, Composite parent){
		Section section = toolkit.createSection(parent, ExpandableComposite.TITLE_BAR);
		section.setLayout(new FillLayout());
		Composite composite = toolkit.createComposite(section, SWT.NONE);
		composite.setLayout(new FillLayout());
		section.setClient(composite);
		return section;
	}
	
}
