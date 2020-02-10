package com.tibco.cep.studio.dashboard.ui.editors.views.header;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.viewers.attribute.AttributeViewer;

/**
 *
 * @author rgupta
 */
public class HeaderPage extends AbstractEntityEditorPage {

	/**
	 * Viewer for fields.
	 */
	private AttributeViewer _fViewer;

    public HeaderPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		try {
			getLocalElement().getChildren(LocalHeader.ELEMENT_KEY_HEADER_LINK);
			getLocalElement().setExisting();
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not initialize "+input.getName(),e));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected AttributeViewer createFieldViewer(IManagedForm mform, Composite client) throws Exception {
		LocalElement localElement = getLocalElement();
		AttributeViewer fieldViewer = new LocalHeaderLinkViewer(mform.getToolkit(), client, ((FileEditorInput)getEditorInput()).getFile().getProject());
		fieldViewer.setLocalElement(localElement);
		return fieldViewer;
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		Section section = createSection(mform, parent, "Header Links");

		_fViewer = createFieldViewer(mform, section);
		getSite().setSelectionProvider(_fViewer.getTableViewer());
		// _fViewer.setLocalElement(getLocalElement());

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 100;
		_fViewer.setLayoutData(gd);

		mform.getToolkit().paintBordersFor(_fViewer.getContent());
		section.setClient(_fViewer.getContent());
	}

    @Override
    protected void handleOutsideElementChange(int change, LocalElement element) {
    	try {
    		//we will only worry about changes to header due image re-feactor
			if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
				//reset all the properties
				getLocalElement().refresh(element.getEObject());
				//reset all referenced children
				getLocalElement().refresh(LocalHeader.ELEMENT_KEY_HEADER_LINK);
				//update all the controls
				populateControl(getLocalElement());
				//update the header links viewer
				if (_fViewer != null) {
					_fViewer.setLocalElement(getLocalElement());
				}
			}
		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "could not refresh " + getEditorInput().getName(), e));
		}
    }
}
