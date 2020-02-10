package com.tibco.cep.bpmn.model.designtime.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.xml.data.primitive.ExpandedName;

public class EEnumWrapper<E extends EEnum,I extends EEnumLiteral> {
	private E eEnumType;
	private Map<String,EEnumLiteral> eMap = new HashMap<String,EEnumLiteral>();
	private Map<ExpandedName,EEnumLiteral> exMap = new HashMap<ExpandedName,EEnumLiteral>();
	

	private EEnumWrapper(E type) {
		eEnumType = type;
		for(EEnumLiteral e:eEnumType.getELiterals()) {
			eMap.put(e.getName(),e);
			exMap.put(CommonECoreHelper.getExpandedName(e),e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private EEnumWrapper(I obj) {
		this((E) obj.eClass());
	}
	
	
	public static<E extends EEnum,I extends EEnumLiteral> EEnumWrapper<E,I> useInstance(I obj) {
		return new EEnumWrapper<E,I>(obj);
	}
	
	public static<E extends EEnum,I extends EEnumLiteral> EEnumWrapper<E,I> useInstance(E en) {
		return new EEnumWrapper<E,I>(en);
	}
	
	public static <E extends EEnum,I extends EEnumLiteral> EEnumWrapper<E,I> createInstance(ExpandedName expName) {
		@SuppressWarnings("unchecked")
		E eClass = (E) BpmnMetaModel.INSTANCE.getEnum(expName);
		return createInstance(eClass);
	}
	
//	/**
//	 * @deprecated
//	 * @param <E>
//	 * @param <I>
//	 * @param enumSpec
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static <E extends EEnum,I extends EEnumLiteral> EEnumWrapper<E,I> createInstance(String enumSpec) {
//		E eClass = (E) BpmnMetaModel.INSTANCE.getEnum(enumSpec);
//		return createInstance(eClass);
//	}	
	
	public static <E extends EEnum,I extends EEnumLiteral> EEnumWrapper<E,I> createInstance(E enumType) {
		return new EEnumWrapper<E,I>(enumType);
	}
	
	public EPackage getEPackage() {
		assert eEnumType != null;		
		return eEnumType.getEPackage();
	}
	
	public EEnumLiteral getEnumLiteral(ExpandedName enumName) {
		return exMap.get(enumName);
	}
	
	
	/**
	 * @deprecated
	 * @param enumName
	 * @return
	 */
	public EEnumLiteral getEnumLiteral(String enumName) {
		return eMap.get(enumName);
	}
	
	

}
