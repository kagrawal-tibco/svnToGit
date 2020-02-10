package com.tibco.cep.bpmn.model.designtime.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Imutable version of eobject wrapper
 * @author majha
 *
 * @param <C>
 * @param <I>
 */
public class ROEObjectWrapper<C extends EClass,I extends EObject>{
	
	protected C eClassType;
	protected I eInstance;
	
	@SuppressWarnings("unchecked")
	protected ROEObjectWrapper(C type) {
//		enclosedWrapper = EObjectWrapper.createInstance(type);
		eClassType = type;
		eInstance = (I) ((EPackage)getEPackage()).getEFactoryInstance().create(eClassType);
	}
	
	@SuppressWarnings("unchecked")
	protected ROEObjectWrapper(I obj) {
//		enclosedWrapper = EObjectWrapper.useInstance(obj);
		eClassType = (C) obj.eClass();
		eInstance = obj;
	}
	
	
	
	public static <T extends EClass,O extends EObject> ROEObjectWrapper<T,O> wrap(O obj) {
		return new ROEObjectWrapper<T,O>(obj);
	}
	
	
	
	
	public EPackage getEPackage() {
		assert eClassType != null;		
		return eClassType.getEPackage();
	}
	
		
	/**
	 * @return the eClassType
	 */
	public C getEClassType() {
		return eClassType;
	}



	/**
	 * @return the eInstance
	 */
	public I getEInstance() {
		return eInstance;
	}


	/**
	 * @return
	 */
	public boolean isSet() {
		if(eInstance != null){
			return true;
		} 
		throw new NullPointerException("EObjectWrapper eInstance is not set");
	}
	
	/**
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String name) {
		if(isSet()) {
			EStructuralFeature sf = eClassType.getEStructuralFeature(name);
			if(sf != null) {
				return (T) eInstance.eGet(sf,true);
			} else {
				throw new NullPointerException("Attribute:"+name+" not found in Class:" +eClassType.getName());
			}
		} 
		return null;
	}
	

	
	
	/**
	 * @param name
	 * @return
	 */
	public boolean containsAttribute(String name) {
		return eClassType.getEStructuralFeature(name) != null;
	}

	
	/**
	 * @param <T>
	 * @param <O>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends EClass,O extends EObject> ROEObjectWrapper<T,O> getWrappedEObjectAttribute(String name) {
		return new ROEObjectWrapper<T,O>((O) getAttribute(name));
	}
	/**
	 * @param <T>
	 * @param <O>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends EClass,O extends EObject,  X extends ROEObjectWrapper<T,O>> List<X> getWrappedEObjectList(String name) {
		List<X> list = new ArrayList<X>();
		for(EObject eObj:getListAttribute(name)) {
			list.add((X) new ROEObjectWrapper<T,O>((O) eObj));
		}
		return list;
	}
	
	/**
	 * returns List attribute
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EList<EObject> getListAttribute(String name) {
		if(isSet()) {
			return (EList<EObject>) getAttribute(name);
		}
		return new BasicEList<EObject>();
	}
	
	

	/**
	 * Returns enum listeral
	 * @param name
	 * @return
	 */
	public EEnumLiteral getEnumAttribute(String name) {
		if(isSet()) {
			return (EEnumLiteral) getAttribute(name);
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eClassType == null) ? 0 : eClassType.hashCode());
		result = prime * result
				+ ((eInstance == null) ? 0 : eInstance.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		ROEObjectWrapper<EClass, EObject> other = (ROEObjectWrapper<EClass, EObject>) obj;
		if (getEClassType() == null) {
			if (other.getEClassType() != null)
				return false;
		} else if (!getEClassType().equals(other.getEClassType()))
			return false;
		if (getEInstance() == null) {
			if (other.getEInstance() != null)
				return false;
		} else if (!getEInstance().equals(other.getEInstance()))
			return false;
		return true;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return eInstance.toString();
	}
	

	/**
	 * @param classSpec
	 * @return
	 */
	public boolean isInstanceOf(ExpandedName classSpec) {
		EClass eClass = BpmnMetaModel.INSTANCE.getEClass(classSpec);
		return isInstanceOf(eClass);
	}

	/**
	 * @param eClass
	 * @return
	 */
	public boolean isInstanceOf(EClass eClass) {
		if(!isSet()) {
			return false;
		}
		if(eClass != null) {
			 BpmnMetaModel.getMetaModelNsRegistry().get(CommonECoreHelper.getExpandedName(eInstance.eClass()));
			return eClass.isSuperTypeOf(eInstance.eClass());
		}
//		if(eClass != null) {
//			EClass iC = BpmnMetaModel.getClassMap().get(CommonECoreHelper.getClassSpec(eInstance.eClass()));
//			return eClass.isSuperTypeOf(eInstance.eClass());
//		}
		return false;
		
	}

	
	
	

}
