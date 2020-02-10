package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalComponentGalleryFolder extends LocalConfig {

	private static final String SUB_FOLDER = "SubFolder";

	private static final String COMPONENT = "Component";

	private static final String THIS_TYPE = BEViewsElementNames.COMPONENT_GALLERY_FOLDER;

	public LocalComponentGalleryFolder() {
		super(THIS_TYPE);
	}

	public LocalComponentGalleryFolder(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalComponentGalleryFolder(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	public LocalConfig getComponentByName(String name, String elementFolder) throws Exception {
		return (LocalConfig) getElement(COMPONENT, name, elementFolder);
	}

	public void removeComponent(LocalConfig component) throws Exception {
		removeElement(COMPONENT, component.getName(), component.getFolder());
	}

	public void addComponent(LocalConfig component) throws Exception {
		addElement(COMPONENT, component);
	}

	public List<LocalElement> getComponents() throws Exception {
		return getChildren(COMPONENT);
	}

	public void refreshComponents() throws Exception {
		refresh(COMPONENT);
	}

	public List<LocalElement> getSubFolders() throws Exception {
		return getChildren(SUB_FOLDER);
	}

	public LocalComponentGalleryFolder createSubFolder(String name) throws Exception {
		LocalComponentGalleryFolder subFolder = new LocalComponentGalleryFolder(this, name);
		subFolder.setFolder(this.getFolder());
		subFolder.setNamespace(this.getNamespace());
		subFolder.setOwnerProject(this.getOwnerProject());
		addElement(SUB_FOLDER, subFolder);
		return subFolder;
	}

	public void removeSubFolder(LocalComponentGalleryFolder subFolder) throws Exception {
		removeElement(SUB_FOLDER, subFolder.getName(), FOLDER_NOT_APPLICABLE);
	}

	@Override
	protected void synchronizeChildren(EObject parent) {
		super.synchronizeChildren(parent);
		configHelper.setParticleChildrenOrder(this, getParticle(SUB_FOLDER));
	}

}
