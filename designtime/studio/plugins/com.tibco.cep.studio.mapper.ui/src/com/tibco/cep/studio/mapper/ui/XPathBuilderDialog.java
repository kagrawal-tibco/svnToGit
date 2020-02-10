package com.tibco.cep.studio.mapper.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.mapper.ui.util.SWTMapperUtils;

public class XPathBuilderDialog extends Dialog {

	private MapperInvocationContext context;

	public XPathBuilderDialog(Shell parentShell, MapperInvocationContext context) {
		super(parentShell);
		this.context = context;
	}

	@Override
	protected Control createDialogArea(Composite parentComp) {
		Composite composite = (Composite) super.createDialogArea(parentComp);
		Composite parent = new Composite(composite, SWT.BORDER);
//		parent.setBackground(new Color(null, 255, 0, 0));
		GridLayout layout = new GridLayout();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.minimumHeight = 400;
		data.minimumWidth = 700;
		parent.setLayout(layout);
		parent.setLayoutData(data);
		composite.getShell().setText("XPath Formula Builder");
		composite.getShell().setImage(MapperActivator.getDefault().getImage("icons/tibco16-32.gif"));
		SWTMapperUtils.createXPathBuilderControl(parent, context);
		return composite;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

}
