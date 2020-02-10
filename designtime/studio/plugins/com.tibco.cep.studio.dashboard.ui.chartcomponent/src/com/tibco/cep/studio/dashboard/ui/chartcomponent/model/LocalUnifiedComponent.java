package com.tibco.cep.studio.dashboard.ui.chartcomponent.model;

import java.util.List;
import java.util.ListIterator;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPreviewableComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalVisualization;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalUnifiedComponent extends LocalComponent {

	public static final String TYPE = LocalUnifiedComponent.class.getSimpleName();

	public static final String PROP_KEY_TYPE = "Type";

	public static final String PROP_KEY_SUBTYPES = "SubTypes";

	private static final String[] TYPES_TO_BE_UNIFIED = new String[] { BEViewsElementNames.CHART_COMPONENT, BEViewsElementNames.TEXT_COMPONENT };

	private boolean editModeOn;

	private String nativeType;

	private List<String> nativeProperties;

	private List<String> nativeParticles;

	private LocalUnifiedComponent() {
		super(null, TYPE, "");
	}

	public LocalUnifiedComponent(LocalElement parentElement, String name) {
		super(parentElement, TYPE, name);
	}

	public LocalUnifiedComponent(LocalElement parentElement, LocalPreviewableComponent component) {
		super(parentElement, TYPE, component.getName());
		try {
			populate(component);
		} finally {
			setInternalStatus(InternalStatusEnum.StatusExisting, true);
		}
	}

	@Override
	public void setupProperties() {
		editModeOn = false;
		for (String type : TYPES_TO_BE_UNIFIED) {
			// add properties
			List<String> existingNames = getPropertyNames();
			List<SynProperty> propertyList = ViewsConfigReader.getInstance().getPropertyList(type);
			for (SynProperty synProperty : propertyList) {
				if (existingNames.contains(synProperty.getName()) == false) {
					// add iff it does not already exist
					addProperty(this, (ISynXSDAttributeDeclaration) synProperty.clone());
				}
			}
		}
		// add chart type property
		addProperty(this, new SynOptionalProperty(PROP_KEY_TYPE, new SynStringType()));
		// add chart sub type as particle
		SynOptionalProperty subType = new SynOptionalProperty(PROP_KEY_SUBTYPES, new SynStringType());
		subType.setArray(true);
		addProperty(this, subType);
		// add unified visualization as a particle
		addParticle(new LocalParticle(LocalUnifiedVisualization.TYPE, 0, 1));
		// add related component as a particle
		LocalParticle particle = configReader.getParticleHelper(BEViewsElementNames.CHART_COMPONENT, BEViewsElementNames.RELATED_COMPONENT).particle;
		addParticle((LocalParticle) particle.clone(), true);
	}

	private void populate(LocalPreviewableComponent component) {
		// PATCH set the eobject since search needs it
		setEObject(component.getEObject());
		// set the native type , we will use it for validation
		nativeType = component.getInsightType();
		nativeProperties = component.getPropertyNames();
		nativeParticles = component.getParticleNames(true);
		// transfer properties
		List<String> names = component.getPropertyNames();
		for (String name : names) {
			SynProperty property = (SynProperty) component.getProperty(name);
			if (property.isArray() == true) {
				// trigger property loading which happens iff you call
				// getPropertyValue
				component.getPropertyValue(name);
//				System.err.println("LocalUnifiedComponent.populate()::Transfering " + component + "/" + name + "{" + property.getValues() + "} to " + this);
				((SynProperty) getProperty(name)).setValues(property.getValues());
			} else {
//				System.err.println("LocalUnifiedComponent.populate()::Transfering " + component + "/" + name + "{" + component.getPropertyValue(name) + "} to " + this);
				setPropertyValue(name, component.getPropertyValue(name));
			}
		}
		// create the singular unified visualization
		LocalUnifiedVisualization unifiedVisualization = new LocalUnifiedVisualization(this, "visualization");
		addElement(LocalUnifiedVisualization.TYPE, unifiedVisualization);
		getParticle(LocalUnifiedVisualization.TYPE).setInitialized(true);
		// transfer all particles
		names = component.getParticleNames(true);
		for (String componentParticleName : names) {
			LocalParticle componentParticle = component.getParticle(componentParticleName);
			if (componentParticle.isReference() == false) {
				List<LocalElement> children = component.getChildren(componentParticleName);
				// we are dealing with visualizations
				for (LocalElement child : children) {
//					System.err.println("LocalUnifiedComponent.populate()::Creating " + component + "/" + componentParticleName + "{" + child + "} in " + this);
					unifiedVisualization.populate((LocalVisualization) child);
				}
			} else {
				// do not load reference component, can cause stack over flow
				// when chart A related to chart B related to Chart A

				List<LocalElement> children = component.getChildren(componentParticleName);
				for (LocalElement child : children) {
//					System.err.println("LocalUnifiedComponent.populate()::Adding " + component + "/" + componentParticleName + "{" + child + "} to " + this);
					addElement(componentParticleName, child);
				}
				getParticle(componentParticleName).setInitialized(true);
			}
		}
	}

	@Override
	public void parseMDProperty(String propertyName) {
		//PATCH need to make sure that setting each property to default is fine
		SynProperty property = (SynProperty) getProperty(propertyName);
		String defaultValue = property.getDefault();
		setPropertyValue(propertyName, defaultValue);
	}

	@Override
	public Object cloneThis() {
		return new LocalUnifiedComponent();
	}

	@Override
	public LocalElement createLocalElement(String elementType) {
		LocalParticle particle = getParticle(elementType);
		if (elementType.equals(LocalUnifiedVisualization.TYPE) == true) {
			String name = getNewName(LocalUnifiedVisualization.TYPE,BEViewsElementNames.VISUALIZATION);
			LocalUnifiedVisualization unifiedVisualization = new LocalUnifiedVisualization(this, name);
			unifiedVisualization.setFolder(getFolder());
			unifiedVisualization.setNamespace(getNamespace());
			unifiedVisualization.setOwnerProject(getOwnerProject());
			particle.addElement(unifiedVisualization);
			particle.setInitialized(true);
			return unifiedVisualization;
		}
		return super.createLocalElement(elementType);
	}

	@Override
	public BEViewsElement createMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("createMDChild");
	}

	@Override
	public void deleteMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("deleteMDChild");
	}

	@Override
	public void loadChild(String childrenType, String childName) {
		throw new UnsupportedOperationException("loadChild");
	}

	@Override
	public void loadChildByID(String childrenType, String childID) {
		throw new UnsupportedOperationException("loadChildByID");
	}

	@Override
	public void loadChildren(String childrenType) {
		loadChildrenFrom(BEViewsElementNames.CHART_COMPONENT, childrenType, getEObject());
	}

	@Override
	public String getElementLocatorKey() {
		return getID();
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		if (nativeProperties.contains(prop.getName()) == true) {
			super.validateProperty(prop);
		}
	}

	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		if (nativeParticles.contains(particle.getName()) == true) {
			super.validateParticle(particle);
		}
		if (particle.getName().equals(LocalUnifiedVisualization.TYPE) == true) {
			super.validateParticle(particle);
		}
	}

	@Override
	protected boolean validateReferences(LocalParticle particle) throws Exception {
		if (nativeParticles.contains(particle.getName()) == true) {
			return super.validateReferences(particle);
		}
		return true;
	}

	@Override
	public String getInsightType() {
		//PATCH is this safe ?
//		if (editModeOn == true) {
//			return PROP_KEY_TYPE;
//		}
//		if (nativeType == null) {
//			//throw new IllegalStateException(PROP_KEY_TYPE+"[id"+getID()+",name="+getName()+"] does not have native type");
//			return PROP_KEY_TYPE;
//		}
//		return nativeType;
		return TYPE;
	}

	public String getNativeType() {
		return nativeType;
	}

	public boolean isEditModeOn() {
		return editModeOn;
	}

	public void setEditModeOn(boolean editModeOn) {
		this.editModeOn = editModeOn;
	}

	public void sync(LocalUnifiedComponent component) {
		//sync properties
		for (String propertyName : getPropertyNames()) {
			SynProperty property = (SynProperty) getProperty(propertyName);
			if (property.getInternalStatus().equals(InternalStatusEnum.StatusModified) == false){
				//the property has not been modified, sync with the incoming
				if (property.isArray() == false) {
					setPropertyValue(propertyName, component.getPropertyValue(propertyName));
				}
				else {
					setPropertyValues(propertyName, ((SynProperty)component.getProperty(propertyName)).getValues());
				}
			}
		}
		//sync visualization
		LocalUnifiedVisualization localUnifiedViz = (LocalUnifiedVisualization) getElement(LocalUnifiedVisualization.TYPE);
		LocalUnifiedVisualization compLocalUnifiedViz = (LocalUnifiedVisualization) component.getElement(LocalUnifiedVisualization.TYPE);
		localUnifiedViz.sync(compLocalUnifiedViz);
		//sync related components, keeping only the ones existing
		List<LocalElement> relatedComponents = getChildren(BEViewsElementNames.RELATED_COMPONENT);
		List<LocalElement> allComponents = getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
		ListIterator<LocalElement> relatedComponentsListIterator = relatedComponents.listIterator();
		while (relatedComponentsListIterator.hasNext()) {
			LocalElement relatedComponent = relatedComponentsListIterator.next();
			if (allComponents.contains(relatedComponent) == false) {
				relatedComponentsListIterator.remove();
			}
		}
	}

}