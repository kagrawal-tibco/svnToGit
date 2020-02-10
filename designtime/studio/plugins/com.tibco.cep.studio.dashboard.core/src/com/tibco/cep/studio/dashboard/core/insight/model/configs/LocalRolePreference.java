package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalRolePreference extends LocalConfig {

	public static final String PROP_KEY_ROLE = "Role";

	public static final String PROP_KEY_VIEW = "View";

	private static final String GALLERY = "Gallery";
	private static final String THIS_TYPE = BEViewsElementNames.ROLE_PREFERENCE;

	public LocalRolePreference() {
		super(THIS_TYPE);
	}

	public LocalRolePreference(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalRolePreference(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	public LocalComponentGalleryFolder getComponentGallery() throws Exception {
		return (LocalComponentGalleryFolder) getElement(GALLERY);
	}

	public LocalComponentGalleryFolder createComponentGallery(String name) throws Exception {
		LocalComponentGalleryFolder gallery = new LocalComponentGalleryFolder(this, name);
		gallery.setFolder(this.getFolder());
		gallery.setNamespace(this.getNamespace());
		gallery.setOwnerProject(this.getOwnerProject());
		addElement(GALLERY, gallery);
		return gallery;
	}

	public void removeComponentGallery(LocalComponentGalleryFolder gallery) throws Exception {
		removeElement(GALLERY, gallery.getName(), FOLDER_NOT_APPLICABLE);
	}

	@Override
	public List<Object> getEnumerations(String propName) {
		if (propName.equals(PROP_KEY_VIEW)) {
			try {
				List<LocalElement> viewConfigs = getRoot().getChildren(BEViewsElementNames.VIEW);
				List<Object> enums = new ArrayList<Object>();
				for (LocalElement viewConfig : viewConfigs) {
					enums.add(viewConfig);
				}
				return enums;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return super.getEnumerations(propName);
	}

}
