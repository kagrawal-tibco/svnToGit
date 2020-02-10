package com.tibco.cep.studio.dashboard.ui.wizards.page;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPage;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;

public abstract class PageTemplate {

	protected String name;

	protected String description;

	protected PageTemplateWizardPage wizardPage;

	protected PageTemplate(String name, String description, PageTemplateWizardPage wizardPage) {
		this.name = name;
		this.description = description;
		this.wizardPage = wizardPage;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Image getPreviewImage() {
		return null;
	}

	public PageTemplateWizardPage getWizardPage(){
		return wizardPage;
	}

	public abstract boolean isAcceptable(List<LocalElement> selection);

	public abstract List<LocalEntity> buildPage(IProject project, LocalPage page) throws Exception;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageTemplate other = (PageTemplate) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}