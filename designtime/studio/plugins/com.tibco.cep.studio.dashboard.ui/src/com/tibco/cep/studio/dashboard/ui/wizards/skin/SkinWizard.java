package com.tibco.cep.studio.dashboard.ui.wizards.skin;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.wizard.IWizardPage;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.ui.wizards.BaseMultiPageViewsWizard;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.ui.wizards.EntityFileCreationWizard;

public class SkinWizard extends BaseMultiPageViewsWizard {

	private EntityFileCreationWizard skinCreationPage;
	private SkinWizardColorSetPage chartColorSetPage;
	private SkinWizardColorSetPage textColorSetPage;

	private LocalSkin skin;

	public SkinWizard() {
		super(BEViewsElementNames.SKIN, BEViewsElementNames.SKIN, "New Skin Wizard", "SkinPage", "New Skin", "Create a new Skin");
		setDefaultPageImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("skin_wizard.png"));
	}

	@Override
	public void addPages() {
		super.addPages();
		chartColorSetPage = new SkinWizardColorSetPage(BEViewsElementNames.CHART_COLOR_SET);
		chartColorSetPage.setTitle("New Skin");
		chartColorSetPage.setDescription("Select Chart Color Sets...");
		addPage(chartColorSetPage);
		textColorSetPage = new SkinWizardColorSetPage(BEViewsElementNames.TEXT_COLOR_SET);
		textColorSetPage.setTitle("New Skin");
		textColorSetPage.setDescription("Select Text Color Sets...");
		addPage(textColorSetPage);
	}

	@Override
	protected EntityFileCreationWizard createPage() {
		skinCreationPage = super.createPage();
		return skinCreationPage;
	}

	@Override
	protected void pageIsAboutToBeHidden(IWizardPage page) {
		if (page == skinCreationPage) {
			if (skin == null) {
				skin = createSkin(skinCreationPage.getFileName(), skinCreationPage.getTypeDesc());
			}
		}
	}

	@Override
	protected void pageIsAboutToBeShown(IWizardPage page) {
		if (page == chartColorSetPage) {
			chartColorSetPage.setLocalSkin(skin);
		} else if (page == textColorSetPage) {
			textColorSetPage.setLocalSkin(skin);
		}
	}

	@Override
	protected void persistFirstPage(String elementName, String elementDesc, String baseURI, IProgressMonitor monitor) throws Exception {
		if (skin == null) {
			skin = createSkin(skinCreationPage.getFileName(), skinCreationPage.getTypeDesc());
		}
		// when user comes back by hitting BACK button, and change the data source name or description.
		skin.setName(entityCreatingWizardPage.getFileName());
		skin.setDescription(entityCreatingWizardPage.getTypeDesc());
		skin.synchronize();
		// Persist the element
		DashboardResourceUtils.persistEntity(skin.getEObject(), baseURI, entityCreatingWizardPage.getProject(), monitor);
	}

	private LocalSkin createSkin(String elementName, String elementDesc) {
		IProject project = skinCreationPage.getProject();

		LocalSkin skin = (LocalSkin) LocalECoreFactory.getInstance(project).createLocalElement(elementType);
		skin.getID();
		skin.setName(elementName);
		skin.setDescription(elementDesc);
		skin.setFolder(DashboardResourceUtils.getFolder(getModelFile()));
		skin.setNamespace(DashboardResourceUtils.getFolder(getModelFile()));
		skin.setOwnerProject(project.getName());

		// add chart color set
		List<LocalElement> chartColorSets = getColorSetsToUse(skin, BEViewsElementNames.CHART_COLOR_SET);
		if (chartColorSets.isEmpty() == false) {
			for (LocalElement chartColorSet : chartColorSets) {
				skin.addElement(BEViewsElementNames.COMPONENT_COLOR_SET, chartColorSet);
			}
			skin.addElement(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET, chartColorSets.get(0));
		}

		// add text color set
		List<LocalElement> textColorSets = getColorSetsToUse(skin, BEViewsElementNames.TEXT_COLOR_SET);
		if (textColorSets.isEmpty() == false) {
			for (LocalElement textColorSet : textColorSets) {
				skin.addElement(BEViewsElementNames.COMPONENT_COLOR_SET, textColorSet);
			}
			skin.addElement(BEViewsElementNames.DEFAULT_COMPONENT_COLOR_SET, textColorSets.get(0));
		}

		return skin;
	}

	@SuppressWarnings("rawtypes")
	private List<LocalElement> getColorSetsToUse(LocalSkin skin, String elementType) {
		List<LocalElement> colorSets = new LinkedList<LocalElement>();
		if (_selection != null) {
			Iterator iterator = _selection.iterator();
			while (iterator.hasNext()) {
				Object object = (Object) iterator.next();
				if (object instanceof IResource) {
					IResource resource = (IResource) object;
					// is the selection within the current project
					if (resource.getProject().equals(entityCreatingWizardPage.getProject()) == true) {
						// yes, it is, is it a series color
						DesignerElement element = IndexUtils.getElement(resource);
						if (element != null && element instanceof EntityElement) {
							EntityElement entityElement = (EntityElement) element;
							ELEMENT_TYPES entityType = entityElement.getElementType();
							if (entityType.compareTo(ELEMENT_TYPES.CHART_COMPONENT_COLOR_SET) == 0 || entityType.compareTo(ELEMENT_TYPES.TEXT_COMPONENT_COLOR_SET) == 0) {
								ELEMENT_TYPES type = null;
								if (BEViewsElementNames.CHART_COLOR_SET.equals(elementType) == true) {
									type = ELEMENT_TYPES.CHART_COMPONENT_COLOR_SET;
								} else if (BEViewsElementNames.TEXT_COLOR_SET.equals(elementType) == true) {
									type = ELEMENT_TYPES.TEXT_COMPONENT_COLOR_SET;
								}
								if (element.getElementType().compareTo(type) == 0) {
									colorSets.add(LocalECoreFactory.toLocalElement(LocalECoreFactory.getInstance(entityCreatingWizardPage.getProject()), entityElement.getEntity()));
								}
							}
//							else {
//								colorSets.clear();
//								break;
//							}
						}
					}
				}
			}
		}
		if (colorSets.isEmpty() == true) {
			List<LocalElement> availableColorSets = skin.getRoot().getChildren(elementType);
			if (availableColorSets.isEmpty() == false) {
				colorSets.add(availableColorSets.get(0));
			}
		}
		return colorSets;
	}

}