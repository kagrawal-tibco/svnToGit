package com.tibco.cep.studio.ui.editors.clusterconfig;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModelMgr;
import com.tibco.cep.studio.ui.util.StudioImages;

public class LoadBalancerPage extends FormPage {
	private LoadBalancerBlock block;
	private ClusterConfigEditor editor;
	
	public LoadBalancerPage(ClusterConfigEditor editor, ClusterConfigModelMgr modelmgr) {
		super(editor, "loadbalancer", getName());
		this.editor = editor;
		block = new LoadBalancerBlock(this, modelmgr);
	}

	protected void createFormContent(final IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		form.setBackgroundImage(StudioImages.getImage(StudioImages.IMG_FORM_BG));
		form.setText(editor.getTitle());
		form.setImage(editor.getTitleImage());
		block.createContent(managedForm);
	}

	public static String getName() {
		return ("Load Balancer");
	}
	
	public void update() {
		block.updateBlock();
	}
}

