package com.tibco.cep.bpmn.model.designtime.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.xml.data.primitive.ExpandedName;

public class EObjectWrapper<C extends EClass,I extends EObject> extends ROEObjectWrapper<C, I> implements Notifier {
	
//	private C eClassType;
//	private I eInstance;
	
	@SuppressWarnings("unchecked")
	private EObjectWrapper(C type) {
		super(type);
		eClassType = type;
		eInstance = (I) ((EPackage)getEPackage()).getEFactoryInstance().create(eClassType);
	}
	
	@SuppressWarnings("unchecked")
	private EObjectWrapper(I obj) {
		super(obj);
		eClassType = (C) obj.eClass();
		eInstance = obj;
	}
	
	
	
	public static <T extends EClass,O extends EObject> EObjectWrapper<T,O> wrap(O obj) {
		return new EObjectWrapper<T,O>(obj);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EClass,O extends EObject> EObjectWrapper<T,O> createInstance(ExpandedName exName) {
		T eClass = (T) BpmnMetaModel.INSTANCE.getEClass(exName);
		return createInstance(eClass);
	}
	

//	@SuppressWarnings("unchecked")
//	public static <T extends EClass,O extends EObject> EObjectWrapper<T,O> createInstance(String classSpec) {
//		T eClass = (T) BpmnMetaModel.INSTANCE.getEClass(classSpec);
//		return createInstance(eClass);
//	}	
	
	public static <T extends EClass,O extends EObject> EObjectWrapper<T,O> createInstance(T classType) {
		return new EObjectWrapper<T,O>(classType);
	}
	
	
	
	public void unsetAttribute(String name) {
		if(isSet()) {
			EStructuralFeature sf = eClassType.getEStructuralFeature(name);
			if(sf != null) {
				eInstance.eUnset(sf);
			} else {
				throw new NullPointerException("Attribute:"+name+" not found in Class:" +eClassType.getName());
			}
		}
	}
	
	
	/**
	 * @param name
	 * @param eObj
	 */
	public void setAttribute(String name,EObjectWrapper<EClass, EObject> eObj) {
		if(eObj != null){
			setAttribute(name, eObj.getEInstance());
		} else {
			setAttribute(name,(Object) null);
		}
	}

	/**
	 * @param name
	 * @param newValue
	 */
	public void setAttribute(String name,Object newValue){
		if(isSet()) {
			eInstance.eSet(eClassType.getEStructuralFeature(name), newValue);
		}
	}
	
	
	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public String getStringAttribute(String name) {
//		if(isSet()) {
//			return (String) eInstance.eGet(eClassType.getEStructuralFeature(name),true);
//		}
//		return null;
//	}
//	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public int getIntAttribute(String name) {
//		if(isSet()) {
//			return (Integer) eInstance.eGet(eClassType.getEStructuralFeature(name),true);
//		}
//		return -1;
//	}
//	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public long getLongAttribute(String name) {
//		if(isSet()) {
//			return (Long) eInstance.eGet(eClassType.getEStructuralFeature(name),true);
//		}
//		return -1L;
//	}
//	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public double getDoubleAttribute(String name) {
//		if(isSet()) {
//			return (Double) eInstance.eGet(eClassType.getEStructuralFeature(name),true);
//		}
//		return -1L;
//	}
//	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public float getFloatAttribute(String name) {
//		if(isSet()) {
//			return (Float) eInstance.eGet(eClassType.getEStructuralFeature(name),true);
//		}
//		return -1L;
//	}
//	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public Date getDateAttribute(String name) {
//		if(isSet()) {
//			return (Date) eInstance.eGet(eClassType.getEStructuralFeature(name),true);
//		}
//		return null;
//	}
//	
//	
//	/**
//	 * @param name
//	 * @return
//	 */
//	public EObject getEObjectAttribute(String name) {
//		return (EObject) getAttribute(name);
//	}
	
	
	
	
	
	/**
	 * @param <T>
	 * @param <O>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends EClass,O extends EObject> EObjectWrapper<T,O> getWrappedEObjectAttribute(String name) {
		return new EObjectWrapper<T,O>((O) getAttribute(name));
	}
	

	/**
	 * @param <T>
	 * @param <O>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends EClass,O extends EObject,X extends ROEObjectWrapper<T,O>> List<X> getWrappedEObjectList(String name) {
		List<X> list = new ArrayList<X>();
		for(EObject eObj:getListAttribute(name)) {
			list.add((X) new EObjectWrapper<T,O>((O) eObj));
		}
		return list;
	}
	
	
	
	/**
	 * @param name
	 * @param obj
	 * @return
	 */
	public boolean addToListAttribute(String name,EObjectWrapper<EClass, EObject> obj) {
		if(isSet()) {
			return getListAttribute(name).add(obj.getEInstance());
		}
		return false;
	}
	
	/**
	 * @param name
	 * @param obj
	 * @return
	 */
	public boolean addToListAttribute(String name,EObject obj){
		if(isSet()) {
			return getListAttribute(name).add(obj);
		}
		return false;
	}
	
	

	

	

	
	
	
	public void accept(EObjectVisitor visitor) {
		if(isSet() && visitor.visit(this.eInstance)){
			EList<EObject> list = this.eInstance.eContents();
			for( EObject eObj : list) {
				EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(eObj);
				wrapper.accept(visitor);				
			}
		} else {
			System.err.println("Skipping  :"+this.eInstance);
		}
	}
	
	/**
	 * @param visitor
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void accept(WrappedObjectVisitor visitor) {
		if(isSet() && visitor.visit(this)){
			EList<EObject> list = eInstance.eContents();
			for( EObject eObj : list) {
				EObjectWrapper<EClass, EObject> eObjWrapper = EObjectWrapper.wrap(eObj);
				eObjWrapper.accept(visitor);				
			}
		} 
	}
	
//	/**
//	 * @deprecated
//	 * @param classSpec
//	 * @return
//	 */
//	public boolean isInstanceOf(String classSpec) {
//		EClass eClass = BpmnMetaModel.INSTANCE.getEClass(classSpec);
//		return isInstanceOf(eClass);
//	}
	
	
	
	
	public void adapt(Adapter adapter) {
		if(isSet()) {
			eInstance.eAdapters().add(adapter);
		}
	}

	@Override
	public EList<Adapter> eAdapters() {
		if(isSet()) {
			return eInstance.eAdapters();
		}
		return new BasicEList<Adapter>();
	}

	@Override
	public boolean eDeliver() {
		if(isSet()) {
			return eInstance.eDeliver();
		}
		return false;
	}

	@Override
	public void eNotify(Notification notification) {
		if(isSet()){
			eInstance.eNotify(notification);		
		}
	}

	@Override
	public void eSetDeliver(boolean deliver) {
		if(isSet()) {
			eInstance.eSetDeliver(deliver);		
		}
	}

	public void removeFromListAttribute(String name, Object object) {
		getListAttribute(name).remove(object);		
	}
	
	public void removeFromListAttribute(String name,EObjectWrapper<EClass, EObject> obj) {
		getListAttribute(name).remove(obj.getEInstance());		
	}
	
	public void clearListAttribute(String name) {
		getListAttribute(name).clear();		
	}

	public EObject copy() {
		if(isSet()) {
			return EcoreUtil.copy(eInstance);
		}
		return null;
	}
	
	public EObjectWrapper<EClass, EObject> copyWrapper() {
		if(isSet()) {
			return EObjectWrapper.wrap(copy());
		}
		return null;
	}
	
	

}
