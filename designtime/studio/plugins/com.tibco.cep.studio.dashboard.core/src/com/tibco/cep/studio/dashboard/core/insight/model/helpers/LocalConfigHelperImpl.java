package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.util.EnumHelper;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

public class LocalConfigHelperImpl implements ILocalConfigHelper {

	private static LocalConfigHelperImpl instance = null;

	public synchronized static ILocalConfigHelper getInstance() {
		if (instance == null) {
			instance = new LocalConfigHelperImpl();
		}
		return instance;
	}

	private IConfigReader configReader;

	protected LocalConfigHelperImpl() {
		configReader = ViewsConfigReader.getInstance();
	}

	@Override
	public String getPropertyValue(LocalConfig localConfig, String property) {
		return getPropertyValue(localConfig, localConfig.getPropertyHelper(property));
	}

	@Override
	public String getPropertyValue(LocalConfig localConfig, LocalPropertyConfig helper) {
		String value = getPropertyValueByPath(localConfig.getEObject(), helper);
		if (value == null) {
			return null;
		}
		Map<String, String> enumPair = helper.enumPair;
		if (enumPair != null) {
			value = EnumHelper.getLocalName(value, enumPair);
		}
		return value;
	}

	private String getPropertyValueByPath(EObject eObject, LocalPropertyConfig propertyHelper) {
		if (propertyHelper.isEmptyPath) {
			return null;
		} else {
			// the read part does not need any more information than a token path.
			EObject child = getEObject(eObject, false, propertyHelper.pathTokens, propertyHelper.typeTokens, propertyHelper.subscripts);
			// If the path is elementRef or nameRef, return the name of the MDElement.
			return getEValue(child, propertyHelper.leaf, propertyHelper.getLeafIndex());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private EObject getEObject(EObject parentEObject, boolean create, String[] pathTokens, String[] typeTokens, Subscript[] subscripts) {
		for (int i = 0; (parentEObject != null) && (i < pathTokens.length - 1); ++i) {
			String token = pathTokens[i];
			EStructuralFeature structuralFeature = parentEObject.eClass().getEStructuralFeature(token);
			if (structuralFeature == null) {
				throw new IllegalArgumentException("Feature by name " + token + " does not exist in class " + parentEObject.eClass().getName());
			}
			EObject childEObject = null;
			if (subscripts[i].isIndex() == true) {
				// we are dealing with regular index based lookups
				if (subscripts[i].getIndex() < 0) {
					childEObject = (EObject) parentEObject.eGet(structuralFeature);

					// when create is true and either childeobject is null or childeobject type is not of what needed

					boolean bCreate = create && (childEObject == null || childEObject.eClass().getName().equals(typeTokens[i]) == false);
					if (bCreate) {
						if (childEObject != null) {
							if (typeTokens[i].equals("IndicatorFieldFormat") || typeTokens[i].equals("TextFieldFormat") || typeTokens[i].equals("ProgressBarFieldFormat")) {
								bCreate = false;
							}
						}
					}
					if (bCreate) {
						childEObject = BEViewsConfigurationFactory.eINSTANCE.create((EClass) BEViewsConfigurationFactory.eINSTANCE.getEPackage().getEClassifier(typeTokens[i]));// create((EClass) structuralFeature.getEType());
						parentEObject.eSet(structuralFeature, childEObject);
					}
				} else {
					int index = subscripts[i].getIndex();
					List list = ((List) parentEObject.eGet(structuralFeature));

					// Check elements in the list are of target type
					for (Iterator itValues = list.iterator(); itValues.hasNext();) {
						EObject eObject = (EObject) itValues.next();
						String childType = eObject.eClass().getName();
						if (!childType.equals(typeTokens[i])) {
							itValues.remove();
						}
					}

					int currentNoOfMDCfgs = list.size();
					if ((index + 1) > currentNoOfMDCfgs && create) {
						for (int j = currentNoOfMDCfgs; j <= index; j++) {
							childEObject = BEViewsConfigurationFactory.eINSTANCE.create((EClass) BEViewsConfigurationFactory.eINSTANCE.getEPackage().getEClassifier(typeTokens[i]));// create((EClass)
							// structuralFeature.getEType());
							list.add(childEObject);
						}
					} else if (index < currentNoOfMDCfgs) {
						childEObject = (EObject) list.get(index);
					} else {
						childEObject = null;
					}
				}
			} else {
				// we are dealing with child type based lookups
				List<?> childObjects = null;
				Object childObject = parentEObject.eGet(structuralFeature);
				if (childObject instanceof EObject) {
					childObjects = Arrays.asList(childObject);
				} else if (childObject instanceof List) {
					childObjects = (List<?>) childObject;
				}
				for (Object object : childObjects) {
					EObject tempChildEObject = (EObject) object;
					EStructuralFeature childFeature = tempChildEObject.eClass().getEStructuralFeature(subscripts[i].getChildName());
					if (childFeature == null) {
						throw new IllegalArgumentException("Feature by name " + subscripts[i].getChildName() + " does not exist in class " + tempChildEObject.eClass().getName());
					}
					if (childFeature.isMany() == true) {
						throw new IllegalArgumentException("Feature by name " + subscripts[i].getChildName() + " in class " + tempChildEObject.eClass().getName() + " is multi-oriented, expecting singular-oriented");
					}
					EObject grandChild = (EObject) tempChildEObject.eGet(childFeature);
					if (grandChild != null && grandChild.eClass().getName().equals(subscripts[i].getChildType()) == true) {
						childEObject = tempChildEObject;
						break;
					}
				}
				if (childEObject == null && create == true) {
					childEObject = BEViewsConfigurationFactory.eINSTANCE.create((EClass) BEViewsConfigurationFactory.eINSTANCE.getEPackage().getEClassifier(typeTokens[i]));// create((EClass) structuralFeature.getEType());
					if (structuralFeature.isMany() == true) {
						EList<EObject> existingChildObjects = (EList<EObject>) parentEObject.eGet(structuralFeature);
						// we need to create a clone on the original list
						existingChildObjects = new BasicEList<EObject>(existingChildObjects);
						// if (existingChildObjects == null){
						// existingChildObjects = new BasicEList<EObject>();
						// }
						// else {
						// existingChildObjects = existingChildObjects
						// }
						existingChildObjects.add(childEObject);
						parentEObject.eSet(structuralFeature, existingChildObjects);
					} else {
						parentEObject.eSet(structuralFeature, childEObject);
					}
				}
			}
			parentEObject = childEObject;
		}
		return parentEObject;
	}

	public EObject getEObject(LocalConfig startLocalObject, EObject eObject, LocalPropertyConfig propertyHelper, boolean create) {
		return getEObject(eObject, create, propertyHelper.pathTokens, propertyHelper.typeTokens, propertyHelper.subscripts);
	}

	protected String getEValue(EObject startEObject, String token, int index) {
		if (startEObject == null) {
			return null;
		}
		EStructuralFeature eSFeature = startEObject.eClass().getEStructuralFeature(token);
		if (eSFeature != null) {
			if (eSFeature.isUnsettable() == true && startEObject.eIsSet(eSFeature) == false) {
				return null;
			}
			// Check datatype here
			Object value = startEObject.eGet(eSFeature);
			if (index == -1) {
				if (value != null) {
					return String.valueOf(value);
				}
			} else {
				if (value != null) {
					EList<?> list = (EList<?>) startEObject.eGet(eSFeature);
					return String.valueOf(list.get(index));
				}
			}
		}
		return null;
	}

	public void setPropertyValue(LocalConfig localConfig, EObject eObject, String propertyName, Object value) {
		LocalPropertyConfig helper = localConfig.getPropertyHelper(propertyName);
		setPropertyValue(localConfig, eObject, helper, value);
	}

	@Override
	public void setPropertyValue(LocalConfig localConfig, EObject eObject, LocalPropertyConfig helper, Object value) {
		boolean isEmpty = value == null;
		if (value instanceof String && isEmpty == false) {
			isEmpty = StringUtil.isEmpty((String) value);
		}
		if (isEmpty == true && helper.property instanceof SynOptionalProperty) {
			removePropertyValue(localConfig, eObject, helper);
			return;
		}
		Map<String, String> enumPair = helper.enumPair;
		if (enumPair != null && value != null) {
			value = EnumHelper.getMdName((String) value, enumPair);
			if (StringUtil.isEmpty((String) value)) {
				removePropertyValue(localConfig, eObject, helper);
				return;
			}
		}
		setPropertyValueByPath(eObject, helper, value);
	}

	/**
	 * Set the value of a leaf property given its path.
	 *
	 * @param startMdObject
	 * @param path
	 * @param value
	 * @throws Exception
	 */
	private void setPropertyValueByPath(EObject eObject, LocalPropertyConfig propertyHelper, Object value) {
		// LocalPropertyConfig propertyHelper = configReader.getPropertyHelper(eObject.eClass().getName(), propertyName);
		if (propertyHelper.isEmptyPath) {
			return;
		} else {
			if (propertyHelper.isElementRef == false && propertyHelper.isNameRef == false && propertyHelper.isMDConfigType == false) {
				// don't handle this case until we know what we are doing here.
				return;
			}
			boolean isEmpty = isValueEmpty(value);
			EObject eObject2 = getEObject(eObject, !isEmpty, propertyHelper.pathTokens, propertyHelper.typeTokens, propertyHelper.subscripts);
			if (eObject2 != null) {
				setEValue(eObject2, propertyHelper, value, isEmpty);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setEValue(EObject startEObject, LocalPropertyConfig propertyHelper, Object value, boolean blankValue) {
		String token = propertyHelper.leaf;
		int index = propertyHelper.getLeafIndex();
		EStructuralFeature eSFeature = startEObject.eClass().getEStructuralFeature(token);
		if (eSFeature == null) {
			return;
		}

		EClassifier featureType = eSFeature.getEType();
		if (featureType instanceof EDataType) {
			EDataType valueDataType = (EDataType) featureType;
			// EcoreUtil.EGenericTypeConverter.INSTANCE.toJavaInstanceTypeName(arg0)
			if (valueDataType instanceof EEnum) {
				if (value != null && blankValue == false) {
					value = BEViewsConfigurationFactory.eINSTANCE.createFromString(valueDataType, String.valueOf(value));
				}
			} else {
				if (value != null && blankValue == false && valueDataType.getName().equals("Int")) {
					value = Integer.parseInt((String) value);
				} else if (value != null && blankValue == false && valueDataType.getName().equals("Boolean")) {
					value = Boolean.parseBoolean((String) value);
				} else if (value != null && blankValue == false && valueDataType.getName().equals("Double")) {
					value = Double.parseDouble((String) value);
				}
			}
		}
		if (index == -1) {
			if (value == null) {
				startEObject.eUnset(eSFeature);
			} else {
				startEObject.eSet(eSFeature, value);
			}
		} else {
			EList list = (EList) startEObject.eGet(eSFeature);
			for (int i = 0; i <= index; i++) {
				if (i > list.size()) {
					list.add(null);
				}
				if (i == index) {
					list.set(i, value);
					break;
				}
			}
		}
	}

	private boolean isValueEmpty(Object value) {
		boolean isEmpty = true;
		if (value != null) {
			if (value instanceof String) {
				isEmpty = StringUtil.isEmpty((String) value);
			} else {
				isEmpty = false;
			}
		}
		return isEmpty;
	}

	public void removePropertyValue(LocalConfig localConfig, EObject eObject, String propertyName) {
		LocalPropertyConfig helper = localConfig.getPropertyHelper(propertyName);
		removePropertyValue(localConfig, eObject, helper);
	}

	public void removePropertyValue(LocalConfig localConfig, EObject eObject, LocalPropertyConfig helper) {
		removePropertyValueByPath(localConfig, eObject, helper);
	}

	private void removePropertyValueByPath(LocalConfig startLocalObject, EObject eObject, LocalPropertyConfig propertyHelper) {
		// if (propertyHelper.isEmptyPath)
		// return;
		// else {
		// setEValue(eObject, propertyHelper, null);
		// }

		// LocalPropertyConfig propertyHelper = configReader.getPropertyHelper(eObject.eClass().getName(), propertyName);
		if (propertyHelper.isEmptyPath) {
			return;
		} else {
			if (propertyHelper.isElementRef == false && propertyHelper.isNameRef == false && propertyHelper.isMDConfigType == false) {
				// don't handle this case until we know what we are doing here.
				return;
			}
			EObject eObject2 = getEObject(eObject, false, propertyHelper.pathTokens, propertyHelper.typeTokens, propertyHelper.subscripts);
			if (eObject2 != null) {
				setEValue(eObject2, propertyHelper, null, true);
			}
		}
	}

	public EObject[] getChildren(LocalConfig localConfig, LocalParticleConfig lph) {
		EObject startEObject = localConfig.getEObject();
		// LocalParticleConfig lph = configReader.getParticleHelper(startEObject.eClass().getName(), particleName);
		if (lph == null) {
			return null;
		}

		EObject childEObject = getEObjectByPath(startEObject, lph.pathToken);
		if (childEObject == null) {
			return null;
		}

		String leaf = lph.pathToken[lph.pathToken.length - 1];
		EStructuralFeature eSFeature = childEObject.eClass().getEStructuralFeature(leaf);
		if (eSFeature != null) {
			long max = lph.particle.getMaxOccurs();
			if (max == 1) {
				EObject child = (EObject) childEObject.eGet(eSFeature);
				if (child != null) {
					return new EObject[] { child };
				}
			} else {
				EList<?> list = (EList<?>) childEObject.eGet(eSFeature);
				return list.toArray(new EObject[] {});
			}
		}
		return null;
	}

	/**
	 * Get an object given its path. This method is called to retrieve MdObject for a particle. For simplicity, just pass in pathTokens.
	 *
	 * It assumes there are no subscripted structures. It works with ConfigStructure.
	 *
	 * It does not create if not present and returns null, saying no exist.
	 *
	 * @param startMdObject
	 * @param pathTokens
	 * @return
	 * @throws Exception
	 */
	private static EObject getEObjectByPath(EObject startEObject, String[] pathTokens) {
		EObject parentEObject = startEObject;
		for (int i = 0; (parentEObject != null) && (i < pathTokens.length - 1); ++i) {
			String token = pathTokens[i];
			EStructuralFeature structuralFeature = parentEObject.eClass().getEStructuralFeature(token);
			if (structuralFeature == null) {
				throw new IllegalArgumentException("Feature by name " + token + " does not exist in class " + parentEObject.eClass().getName());
			}
			parentEObject = (EObject) parentEObject.eGet(structuralFeature);
		}
		return parentEObject;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EObject createChild(LocalConfig localConfig, LocalParticleConfig lph, String childName) {
		EObject startEObject = localConfig.getEObject();
		EObject childEObject = BEViewsConfigurationFactory.eINSTANCE.create((EClass) BEViewsConfigurationFactory.eINSTANCE.getEPackage().getEClassifier(lph.getType()));
		if ((childEObject instanceof BEViewsElement)) {
			((BEViewsElement) childEObject).setName(childName);
		}
		long multiplicity = lph.particle.getMaxOccurs();
		String leaf = lph.pathToken[lph.pathToken.length - 1];
		EStructuralFeature eSFeature = startEObject.eClass().getEStructuralFeature(leaf);
		if (multiplicity < 0 || multiplicity > 1) {
			if (eSFeature != null) {
				EList list = (EList) startEObject.eGet(eSFeature);
				list.add(childEObject);
			}
		} else {
			startEObject.eSet(eSFeature, childEObject);
		}

		return childEObject;
	}

	public void deleteChild(LocalConfig localConfig, LocalParticleConfig lph, String childName) {
		EObject startEObject = localConfig.getEObject();
		long multiplicity = lph.particle.getMaxOccurs();
		String leaf = lph.pathToken[lph.pathToken.length - 1];
		EStructuralFeature eSFeature = startEObject.eClass().getEStructuralFeature(leaf);
		if (multiplicity < 0 || multiplicity > 1) {
			if (eSFeature != null) {
				EList<?> list = (EList<?>) startEObject.eGet(eSFeature);
				// find the child by name
				for (int i = 0; i < list.size(); i++) {
					BEViewsElement childElement = (BEViewsElement) list.get(i);
					if (childElement.getName().equals(childName)) {
						list.remove(i);
					}
				}
			}
		} else {
			startEObject.eSet(eSFeature, null);
		}
	}

	@Override
	public void setEReferenceChild(LocalConfig parent, EObject eObject, LocalParticle childParticle, List<LocalElement> elements) {
		String token = childParticle.getPath();
		long multiplicity = childParticle.getMaxOccurs();

		setConfigReferenceChild(parent, eObject, token, multiplicity, elements);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setConfigReferenceChild(LocalConfig parent, EObject eObject, String token, long multiplicity, List<LocalElement> elements) {
		EStructuralFeature eSFeature = eObject.eClass().getEStructuralFeature(token);
		if (multiplicity < 0 || multiplicity > 1) {
			if (eSFeature != null) {
				EList list = (EList) eObject.eGet(eSFeature);
				// remove them
				list.clear();
				// add them back
				for (int i = 0; i < elements.size(); i++) {
					LocalElement newElement = elements.get(i);
					list.add(newElement.getEObject());
				}
			}
		} else {
			EObject value = null;
			if (elements.size() > 0) {
				LocalElement newElement = (LocalElement) elements.get(0);
				if (newElement != null) {
					value = newElement.getEObject();
				}
			}
			eObject.eSet(eSFeature, value);
		}
	}

	@Override
	public void setParticleChildrenOrder(LocalConfig config, LocalParticle particle) {
		List<LocalElement> localChildren = particle.getElements(true);
		LocalParticleConfig particleHelper = configReader.getParticleHelper(config.getElementType(), particle.getName());
		EList<EObject> persistedChildren = new BasicEList<EObject>(localChildren.size());
		for (LocalElement child : localChildren) {
			persistedChildren.add(child.getEObject());
		}
		setChildren(config, particleHelper, persistedChildren);
	}

	private void setChildren(LocalConfig localConfig, LocalParticleConfig lph, EList<EObject> children) {
		if (localConfig == null || lph == null || children == null || children.isEmpty() == true){
			return;
		}
		EObject childEObject = getEObjectByPath(localConfig.getEObject(), lph.pathToken);
		if (childEObject == null) {
			return;
		}
		String leaf = lph.pathToken[lph.pathToken.length - 1];
		EStructuralFeature eSFeature = childEObject.eClass().getEStructuralFeature(leaf);
		if (eSFeature != null) {
			long max = lph.particle.getMaxOccurs();
			if (max == 1) {
				childEObject.eSet(eSFeature,children.get(0));
			} else {
				childEObject.eSet(eSFeature, children);
			}
		}
	}
}
