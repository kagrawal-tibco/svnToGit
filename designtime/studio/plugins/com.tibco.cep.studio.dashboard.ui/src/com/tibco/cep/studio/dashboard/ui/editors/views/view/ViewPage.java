package com.tibco.cep.studio.dashboard.ui.editors.views.view;

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDashboardPage;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.AbstractEntityEditorPage;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionTable;
import com.tibco.cep.studio.dashboard.ui.viewers.ElementCheckBoxSelectionViewer;
import com.tibco.cep.studio.dashboard.ui.viewers.TableColumnInfo;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 *
 * @author rgupta
 */
public class ViewPage extends AbstractEntityEditorPage {

	private static final String INTERNAL_SKIN = "<Internal Skin>";

	private ElementCheckBoxSelectionViewer pagesViewer;

	public ViewPage(FormEditor editor, LocalElement localElement) {
		super(editor, localElement);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
	}

	@Override
	protected void createExtendedProperties(IManagedForm mform, Composite propertiesPanel) throws Exception {
		super.createExtendedProperties(mform, propertiesPanel);
		createComboField(mform, propertiesPanel, getLocalElement().getParticle("Skin"));
	}

	@Override
	protected void createFieldSection(IManagedForm mform, Composite parent) throws Exception {
		Section section = createSection(mform, parent, "Pages");
		LocalElement localElement = getLocalElement();
		ElementCheckBoxSelectionTable pagesTable = new ElementCheckBoxSelectionTable(section, TableColumnInfo.get(false, true));
		pagesViewer = new ElementCheckBoxSelectionViewer(section, localElement, "Page", "DefaultPage", pagesTable);
		section.setClient(pagesTable.getControl());
	}

	@Override
	protected void doPopulateControl(LocalElement localElement) throws Exception {
		try {
			getLocalElement().getChildren("Page");
			getLocalElement().getChildren("DefaultPage");
			getLocalElement().getChildren("Skin");

			//update the skin combo
			//updateComboField((CCombo)propertyWidgets.get("Skin"), getLocalElement().getParticle("Skin"));
			super.doPopulateControl(getLocalElement());

			//update the pages viewer
			pagesViewer.setElementChoices(localElement.getRoot().getChildren(BEViewsElementNames.DASHBOARD_PAGE));
			pagesViewer.setSelectedElements(localElement.getChildren("Page"), localElement.getElement("DefaultPage"));

		} catch (Exception e) {
			DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not initialize "+getEditorInput().getName(),e));
		}
	}

	@Override
	protected void handleOutsideElementChange(int change, LocalElement element) {
		try {
			//we will only process skin add & delete changes, re-factoring changes are handled by beview change
			if (element instanceof LocalSkin && change != IResourceDelta.CHANGED){
				if (change == IResourceDelta.REMOVED){
					//if we are removing
					getLocalElement().refresh("Skin");
				}
				updateComboField((CCombo)propertyWidgets.get("Skin"), getLocalElement().getParticle("Skin"));
			}
			//we will only process page add & delete changes, re-factoring changes are handled by beview change
			else if (element instanceof LocalDashboardPage && change != IResourceDelta.CHANGED){
				if (change == IResourceDelta.REMOVED){
					getLocalElement().refresh("Page");
					getLocalElement().refresh("DefaultPage");
				}
				pagesViewer.setElementChoices(getLocalElement().getRoot().getChildren(BEViewsElementNames.DASHBOARD_PAGE));
				pagesViewer.setSelectedElements(getLocalElement().getChildren("Page"), getLocalElement().getElement("DefaultPage"));
			}
			//we will only be getting be-view updates due to a refactoring on skin/page
			else if (change == IResourceDelta.CHANGED && element.getID().equals(getLocalElement().getID()) == true){
				//reset all the properties
				getLocalElement().refresh(element.getEObject());
				//reset all referenced children
				getLocalElement().refresh("Skin");
				getLocalElement().refresh("Page");
				getLocalElement().refresh("DefaultPage");
				populateControl(getLocalElement());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void updateComboField(CCombo combo, LocalParticle particle) throws Exception {
		super.updateComboField(combo, particle);
		//add default skin if we are dealing with 'skin'
		if (BEViewsElementNames.SKIN.equals(particle.getName()) == true) {
			combo.add(INTERNAL_SKIN);
			if (particle.getParent().getEnumerations(particle.getName()).isEmpty() == true || getLocalElement().getChildren(particle.getName()).isEmpty() == true) {
				//we have no skin's in the system
				combo.setText(INTERNAL_SKIN);
			}
		}
	}

	@Override
	protected void processSelection(CCombo combo, LocalParticle particle) {
		if (BEViewsElementNames.SKIN.equals(particle.getName()) == true) {
			if (combo.getText().equals(INTERNAL_SKIN)){
				getLocalElement().removeChildrenByParticleName(particle.getName());
				return;
			}
		}
		super.processSelection(combo, particle);
	}

}