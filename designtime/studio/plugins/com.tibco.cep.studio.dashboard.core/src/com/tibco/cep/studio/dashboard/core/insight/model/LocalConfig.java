package com.tibco.cep.studio.dashboard.core.insight.model;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.IConfigReader;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ILocalConfigHelper;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalConfigHelperImpl;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalParticleConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalPropertyConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynPrimitiveType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalInsightElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

public class LocalConfig extends LocalInsightElement {

	public static final String PROP_KEY_DISPLAY_NAME = "Display Name";

	// private static final String ELEM_KEY_PREFIX = "";

	// public static final String ELEMENT_KEY_FIELD = ELEM_KEY_PREFIX + "Field";

	protected ILocalConfigHelper configHelper = LocalConfigHelperImpl.getInstance();

	protected IConfigReader configReader = ViewsConfigReader.getInstance();

	protected String insightType = null;

	// private static List configTypeList;

	public LocalConfig(String insightType) {
		super(false);
		try {
			this.setInsightType(insightType);
			this.setupDefaultProperties();
			this.setupProperties();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public LocalConfig(LocalElement parentElement, String insightType, BEViewsElement BEViewsElement) {
		super(false);
		try {
			this.setInsightType(insightType);
			this.setElementLocatorKey(BEViewsElement);
			this.setParent(parentElement);
			this.setupDefaultProperties();
			this.setupProperties();
			this.persistedElement = BEViewsElement;
			this.refresh();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public LocalConfig(LocalElement parentElement, String insightType, String name) {
		super(false);
		try {
			this.setInsightType(insightType);
			this.setParent(parentElement);
			this.setupDefaultProperties();
			this.setupProperties();
			this.setName(name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public LocalConfig(LocalElement parentElement, String insightType) {
		super(false);
		try {
			this.setInsightType(insightType);
			this.setParent(parentElement);
			this.setupDefaultProperties();
			this.setupProperties();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object cloneThis() throws Exception {
		return LocalConfigFactory.getInstance().createConfigInstance(getInsightType());
	}

	public String getInsightType() {
		return insightType;
	}

	private void setInsightType(String insightType) {
		this.insightType = insightType;
	}

	@Override
	public void setupProperties() {
		for (Iterator<SynProperty> iter = getPropertyList().iterator(); iter.hasNext();) {
			SynProperty propTemplate = iter.next();
			SynProperty propClone = (SynProperty) propTemplate.clone();
			SynPrimitiveType type = (SynPrimitiveType) propClone.getTypeDefinition();
			if (type.getEnumerations().isEmpty())
				type.setEnumerations(this);
			addProperty(this, propClone);
			String defaultValue = propClone.getDefault();
			propClone.setValue(defaultValue);
		}

		for (Iterator<LocalParticle> iter = getParticleList().iterator(); iter.hasNext();) {
			LocalParticle particleTemplate = iter.next();
			LocalParticle particle = new LocalConfigParticle(particleTemplate.getName(), particleTemplate.getMinOccurs(), particleTemplate.getMaxOccurs());
			addParticle(particle, particleTemplate.isReference());
			particle.setPath(particleTemplate.getPath());
			particle.setTypeName(particleTemplate.getTypeName());
			particle.setMDConfigType(particleTemplate.isMDConfigType());
		}
	}

	@Override
	public void parseMDProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			super.parseMDProperty(propertyName);
			return;
		}

		SynProperty prop = (SynProperty) getProperty(propertyName);
		String value = configHelper.getPropertyValue(this, propertyName);
		// ASSUMPTION: When the MDConfig returns the value as empty, then
		// replace it with default
		// This is an assumption that required properties defined with default
		// value
		// cannot be saved as empty which is correct as per current
		// implementation of the
		// SynProperty.isValid() API
		// If the default is NONE, set the default.
		if (value == null || StringUtil.isEmpty(value)) {
			String defaultValue = prop.getDefault();
			if (prop instanceof SynRequiredProperty) {
				value = defaultValue;
			} else if (prop instanceof SynOptionalProperty) {
				if (defaultValue.equals("None")) {
					value = defaultValue;
				}
			}
		}
		setPropertyValue(prop.getName(), value);
	}

	@Override
	protected boolean isSuperProperty(String propertyName) {
		if (propertyName.equals(PROP_KEY_DISPLAY_NAME)) {
			return true;
		}
		return super.isSuperProperty(propertyName);
	}

	@Override
	public void loadChildren(String childrenType) {
		BEViewsElement emfElement = getEObject();
		if (emfElement == null) {
			// throw new IllegalArgumentException("mdElement can't be null. Check callee");
			return;
		}
		loadChildrenFrom(getInsightType(), childrenType, emfElement);
	}

	protected void loadChildrenFrom(String elementType, String childrenType, BEViewsElement persistedElement) {
		LocalParticleConfig particleHelper = configReader.getParticleHelper(elementType, childrenType);
		EObject[] childElements = configHelper.getChildren(this, particleHelper);
		if (childElements != null) {
			for (int i = 0; i < childElements.length; ++i) {
				EObject emfChild = childElements[i];
				// Resolve proxy
				if (emfChild.eIsProxy()) {
					emfChild = EcoreUtil.resolve(emfChild, persistedElement);
				}
				// TODO if failed to resolve, leave it, check better way to handle this.
				if (emfChild.eIsProxy()) {
					continue;
				}
				if (particleHelper.particle.isReference() == false) {
					// load containments by exact type match
					if (emfChild.eClass().getName().equals(particleHelper.getType()) == true) {
						LocalElement childElement = convertToLocalElement(emfChild, childrenType, particleHelper.getType());
						if (childElement != null) {
							addElement(childrenType, childElement);
						}
					}
				} else {
					// load references by type hierarchy match
					if (getTypeHierarchy(emfChild).contains(particleHelper.getType()) == true) {
						LocalElement childElement = convertToLocalElement(emfChild, childrenType, particleHelper.getType());
						if (childElement != null) {
							addElement(childrenType, childElement);
						}
					}
				}
			}
		}
	}

	private List<String> getTypeHierarchy(EObject eObject) {
		List<String> typeHierarchy = new LinkedList<String>();
		for (EGenericType type : eObject.eClass().getEAllGenericSuperTypes()) {
			typeHierarchy.add(type.getEClassifier().getName());
		}
		typeHierarchy.add(eObject.eClass().getName());
		return typeHierarchy;
	}

	protected LocalElement convertToLocalElement(EObject emfElement, String particleName, String expectedConfigType) {
		if (emfElement instanceof Entity) {
			Entity entity = (Entity) emfElement;
			LocalParticle particle = getParticle(particleName);
			if (particle.isMDConfigType() == true) {
				if (particle.isReference()) {
					return getTopLevelConfigByName(entity.eClass().getName(), entity.getName(), entity.getFolder());
				}
				return convertMdConfigToLocalConfig(entity, expectedConfigType);
			} else {
				return convertMdElementToLocalElement(entity, particleName);
			}
		}
		throw new IllegalArgumentException("Invalid class type for element passed " + emfElement.getClass());
	}

	private LocalElement getTopLevelConfigByName(String elementType, String elementName, String elementFolder) {
		// if (elementType.equals(BEViewsElementNames.CHART_COMPONENT) || elementType.equals(BEViewsElementNames.TEXT_COMPONENT)) {
		// return getRoot().getElement(BEViewsElementNames.TEXT_CHART_COMPONENT, elementName, elementFolder);
		// }
		return getRoot().getElement(elementType, elementName, elementFolder);
	}

	protected LocalElement convertMdConfigToLocalConfig(Entity emfElement, String expectedType) {
		return LocalConfigFactory.getInstance().createConfigInstance(this, expectedType, emfElement);
	}

	/**
	 * Overridding classes returns the LocalElement equivales to the MDELement passed in.
	 *
	 * @param mdElement
	 * @param elementType
	 */
	protected LocalElement convertMdElementToLocalElement(Entity mdElement, String elementType) {
		// To be overridden by subclasses to handle specific child MDElements
		return null;
	}

	@Override
	public LocalElement createLocalElement(String childrenType) {
		LocalParticleConfig lph = configReader.getParticleHelper(getInsightType(), childrenType);
		LocalConfig newConfig = LocalConfigFactory.getInstance().createConfigInstance(this, lph.getType());
		newConfig.setName(getNewName(childrenType));
		newConfig.setNamespace(getNamespace());
		newConfig.setFolder(getFolder());
		newConfig.setOwnerProject(getOwnerProject());
		addElement(childrenType, newConfig);
		return newConfig;
	}

	@Override
	public BEViewsElement createMDChild(LocalElement child) {
		LocalParticleConfig lph = configReader.getParticleHelper(getInsightType(), child.getParentParticle().getName());
		BEViewsElement mdChild = (BEViewsElement) configHelper.createChild(this, lph, child.getName());
		// Special logic to subscribe to newly create MDXXXX
		// Normally, the subscription is in createMDChild().
		// mdChild.addAdapter(child);
		return mdChild;
	}

	@Override
	public void deleteMDChild(LocalElement child) {
		LocalParticleConfig lph = configReader.getParticleHelper(insightType, child.getParentParticle().getName());
		configHelper.deleteChild(this, lph, child.getName());
	}

	@Override
	public void synchronizeElement(EObject mdElement) {
		super.synchronizeElement(mdElement);
		preSynchronizeElement((BEViewsElement) mdElement);
		for (Iterator<SynProperty> iter = getPropertyList().iterator(); iter.hasNext();) {
			// prop is only the template from LocalConfigHelper. The value
			// is not the right one.
			SynProperty propertyTemplate = iter.next();
			String name = propertyTemplate.getName();
			// if (getProperty(name) == null) {
			// configHelper.removePropertyValue(this, mdElement, name);
			// } else {
			// synchronizeProperty((BEViewsElement) mdElement,
			// propertyTemplate);
			// }
			if (getProperty(name) != null) {
				synchronizeProperty((BEViewsElement) mdElement, propertyTemplate);
			}
		}
		postSynchronizeElement((BEViewsElement) mdElement);
		synchronizeReferenceChildren((BEViewsElement) mdElement);
	}

	/**
	 * Synchronizes/saves the reference children from local to MDS.
	 *
	 * @param mdElement
	 */
	protected void synchronizeReferenceChildren(BEViewsElement mdElement) {
		for (Iterator<LocalParticle> iter = getParticles(true).iterator(); iter.hasNext();) {
			LocalParticle particle = iter.next();
			// deal with reference particles only, children are handled by synchronizeChildren
			if (particle.isReference() == false) {
				continue;
			}
			if (particle.isMDConfigType() == false) {
				synchronizeMDReferenceChildren(mdElement, particle);
			} else {
				synchronizeConfigReferenceChildren(mdElement, particle);
			}
		}
	}

	/**
	 * Used to synchrnize non-MDConfig reference children. Based on MDReference.
	 *
	 * @param mdElement
	 * @param particle
	 */
	protected void synchronizeMDReferenceChildren(BEViewsElement mdElement, LocalParticle particle) {
		// In case we have any elements in the particle (active or inactive)
		// then only we need to set/update references
		// else it is not needed and we leave the MDS structure as it is.
		List<LocalElement> elements = particle.getElements(true);
		configHelper.setEReferenceChild(this, mdElement, particle, elements);
	}

	/**
	 * Used to synchrnize MDConfig reference children. Based on ConfigInstanceRef.
	 *
	 * @param mdElement
	 * @param particle
	 */
	protected void synchronizeConfigReferenceChildren(BEViewsElement mdElement, LocalParticle particle) {
		// In case we have any elements in the particle (active or inactive)
		// then only we need to set/update references
		// else it is not needed and we leave the MDS structure as it is.
		List<LocalElement> elements = particle.getElements(true);
		configHelper.setConfigReferenceChild(this, mdElement, particle.getPath(), particle.getMaxOccurs(), elements);
	}

	protected void preSynchronizeElement(BEViewsElement mdElement) {
		// To be overridden by the sub classes
	}

	protected void postSynchronizeElement(BEViewsElement mdElement) {
		// To be overridden by the sub classes
	}

	protected void synchronizeProperty(BEViewsElement BEViewsElement, SynProperty propertyTemplate) {
		String value = getPropertyValue(propertyTemplate.getName());
		configHelper.setPropertyValue(this, BEViewsElement, propertyTemplate.getName(), value);
	}

	/*
	 * (non-Javadoc) Will be overridden by sub classes if they want to modify the properties.
	 */
	public LocalPropertyConfig getPropertyHelper(String propertyName) {
		return configReader.getPropertyHelper(getInsightType(), propertyName);
	}

	/*
	 * (non-Javadoc) Will be overridden by sub classes if they want to modify the properties.
	 */
	public LocalPropertyConfig getPropertyHelper(SynProperty prop) {
		return configReader.getPropertyHelper(getInsightType(), prop.getName());
	}

	/**
	 * Will be overridden by sub classes if they want to modify the particles.
	 *
	 * @return
	 */
	public List<LocalParticle> getParticleList() {
		return configReader.getParticleList(getInsightType());
	}

	public List<SynProperty> getPropertyList() {
		return configReader.getPropertyList(getInsightType());
	}

	public LocalElement getFirstChild(String childType) {
		List<LocalElement> children = getChildren(childType);
		if (children != null && !children.isEmpty()) {
			return children.get(0);
		}
		return null;
	}

	public String[] getPropertyEnums(String property) {
		LocalPropertyConfig propertyConfig = configReader.getPropertyHelper(getInsightType(), property);
		return propertyConfig.getPropertyEnumValues();

	}

	@Override
	public void loadChild(String childrenType, String childName) {
	}

	@Override
	public void loadChildByID(String childrenType, String childID) {
	}

	@Override
	public String getElementType() {
		return getInsightType();
	}

	public String getDisplayName() {
		if (getProperty(PROP_KEY_DISPLAY_NAME) != null) {
			return getPropertyValue(PROP_KEY_DISPLAY_NAME);
		}
		return null;
	}

	public void setDisplayName(String displayName) {
		if (getProperty(PROP_KEY_DISPLAY_NAME) != null) {
			setPropertyValue(PROP_KEY_DISPLAY_NAME, displayName);
		}
		return;
	}

	/**
	 * If called, will trigger the notification when value is same too.
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void setPropertyValueForce(String key, String value) {
		super.setPropertyValue(key, value);
	}

	@Override
	public Object clone() {
		LocalConfig newConfig = (LocalConfig) super.clone();
		newConfig.setFolder(getFolder());
		newConfig.setNamespace(getNamespace());
		newConfig.setOwnerProject(getOwnerProject());
		return newConfig;
	}

	@Override
	protected boolean validateReferences(LocalParticle particle) throws Exception {
		EObject[] danglingReferences = getDanglingReferences(particle);
		if (danglingReferences == null) {
			return true;
		}
		for (EObject object : danglingReferences) {
			EObjectImpl impl = (EObjectImpl) object;
			URI proxyURI = impl.eProxyURI();
			LocalECoreFactory coreFactory = (LocalECoreFactory) getRoot();
			IPath projectRootPath = coreFactory.getProject().getLocation();
			URI deresolve = proxyURI.deresolve(URI.createFileURI(projectRootPath.toString()));
			String path = deresolve.toFileString();
			addValidationErrorMessage("Element referred at " + path + " does not exist");
		}
		return danglingReferences.length != 0;
	}

	private EObject[] getDanglingReferences(LocalParticle particle) {
		BEViewsElement emfElement = getEObject();
		if (emfElement == null) {
			// throw new IllegalArgumentException("persisted element can't be null. Check callee");
			return null;
		}
		List<EObject> danglingReferences = new LinkedList<EObject>();
		String childrenType = particle.getName();
		LocalParticleConfig particleHelper = configReader.getParticleHelper(getInsightType(), childrenType);
		EObject[] childObjects = configHelper.getChildren(this, particleHelper);
		if (childObjects != null) {
			for (int i = 0; i < childObjects.length; i++) {
				EObject childObject = childObjects[i];
				boolean isOK = true;
				if (childObject.eIsProxy()) {
					// Being proxy try to resolve it..
					EObject resolved = EcoreUtil.resolve(childObject, this.getEObject());
					if (resolved.eIsProxy() == false) {
						// Resolved...
						// Also check if it is still there on disk as at times
						// EMF resolves element from cache
						Resource resource = resolved.eResource();
						File file = new File(resource.getURI().toFileString());
						if (file.exists() == false) {
							// Resolved, but from cache, actually element does
							// not exist..
							isOK = false;
							// delete the element from cache too, so does not
							// come up again..
							EcoreUtil.remove(resolved);
						} else {
							// Resolved, and element does exist..
							isOK = true;
						}
					} else {
						// Not resolved...
						isOK = false;
					}
				}
				if (isOK == false) {
					danglingReferences.add(childObject);
				}
			}
		}
		return danglingReferences.toArray(new EObject[danglingReferences.size()]);
	}

	public void markElementForRecreate() {
		this.persistedElement = null;
	}

	protected void purgeDanglingReferences() {

	}

	// @Override
	// public boolean isModified() {
	// List<LocalParticle> particles = getReferenceParticles();
	// for (LocalParticle particle : particles) {
	// try {
	// EObject[] danglingReferences = getDanglingReferences(particle);
	// if (danglingReferences.length != 0){
	// return true;
	// }
	// } catch (Exception e) {
	// }
	// }
	// particles = getParticles();
	// for (LocalParticle particle : particles) {
	// List<LocalElement> elements = particle.getElements(true);
	// for (LocalElement localElement : elements) {
	// if (localElement.isModified() == true){
	// return true;
	// }
	// }
	// }
	// return super.isModified();
	// }

	public void setEObject(EObject object) {
		this.persistedElement = object;
	}

	@Override
	public String getDisplayableName() {
		try {
			String displayName = getDisplayName();
			if (displayName != null && displayName.trim().length() != 0) {
				return displayName;
			}
		} catch (Exception e) {
		}
		return super.getDisplayableName();
	}
}
