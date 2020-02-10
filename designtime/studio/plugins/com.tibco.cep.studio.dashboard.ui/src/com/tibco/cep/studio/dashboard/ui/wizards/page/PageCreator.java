package com.tibco.cep.studio.dashboard.ui.wizards.page;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPage;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;

public abstract class PageCreator {

	protected IProject project;

	protected String name;

	protected String description;

	protected String displayName;

	protected PageTemplate template;

	protected LocalPage localPage;

	protected String folder;

	protected String namespace;

	protected List<LocalEntity> additionalElements;

	protected PageCreator(){
		additionalElements = new LinkedList<LocalEntity>();
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	public IProject getProject() {
		return project;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public PageTemplate getTemplate() {
		return template;
	}

	public void setTemplate(PageTemplate template) {
		this.template = template;
	}

	public LocalPage getPage() {
		return localPage;
	}

	public List<LocalEntity> getAdditionalElements(){
		return additionalElements;
	}

	public synchronized LocalPage createPage() throws Exception {
		localPage = (LocalPage) LocalECoreFactory.getInstance(project).createLocalElement(getType());
		updateCoreProperties(localPage);
		if (template != null){
			additionalElements.addAll(template.buildPage(project,localPage));
		}
		return localPage;
	}

	protected abstract String getType();

	protected void updateCoreProperties(LocalPage page) throws Exception{
		page.getID();
		page.setName(name);
		page.setDisplayName(displayName);
		page.setDescription(description);
		page.setFolder(folder);
		page.setNamespace(namespace);
		page.setOwnerProject(project.getName());
	}

}