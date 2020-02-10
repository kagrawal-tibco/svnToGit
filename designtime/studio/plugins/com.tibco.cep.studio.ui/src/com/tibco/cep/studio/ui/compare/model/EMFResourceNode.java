package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.compare.util.CompareUtils;

public class EMFResourceNode extends AbstractResourceNode {

	/**
	 * @param input
	 * @param revision
	 * @param isRemote
	 * @param autoMerge
	 */
	public EMFResourceNode(Object input, 
			               String revision, 
			               boolean isRemote,
			               boolean autoMerge) {
		super(input, autoMerge);
		setRemote(isRemote);
		if (isRemote) {
			setRevision(Integer.parseInt(revision));
		}
	}

	@Override
	protected String getExtension() {
		return CompareUtils.getExtension((EObject)getInput());
	}

	@Override
	protected int getFeatureId() {
		return CompareUtils.getFeatureId((EObject)getInput());
	}

	@Override
	protected String getModelAbsuluteFilePath(EObject object) {
		if (object instanceof Table) {
			return CompareUtils.getTableFilePath((Table)object);
		}
		if (object instanceof Entity) {
			Entity entity = (Entity)object;
			return IndexUtils.getFile(entity.getOwnerProjectName(), entity).getLocation().toString();
		}
		return null;
	}

	@Override
	protected String getModelName() {
		return CompareUtils.getModelName((EObject)getInput());
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.model.AbstractResourceNode#getImage()
	 */
	public Image getImage() {
		return PlatformUI.getWorkbench().
		                getEditorRegistry().
		                getImageDescriptor("filename."+ getExtension()).createImage();
	}
}