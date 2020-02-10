package com.tibco.cep.bpmn.runtime.utils;

import java.util.Calendar;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.emf.ecore.EEnumLiteral;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.util.calendar.Impl.BECalendar;
import com.tibco.be.util.calendar.Impl.CalendarImpl;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.runtime.activity.tasks.ModelType;
import com.tibco.cep.runtime.model.element.PropertyAtom;

public class ModelTypeHelper {
	public static Object getTypedValue(Variables vars, TaskFunctionModel.TypeSymbol s, Class<?> parameterType) {
		Object variable = vars.getVariable(s.getName());
		return castAsType(variable, s,parameterType);
	}
	private static Object getPrimitiveDefaultVal(Class<?> parameterType){
		if(parameterType == null){
			return null;
		}
		else if(parameterType.isAssignableFrom(boolean.class)){
			return false;
		}else if(parameterType.isAssignableFrom(int.class)){
			return 0;
		}else if(parameterType.isAssignableFrom(long.class)){
			return 0L;
		}else if(parameterType.isAssignableFrom(double.class)){
			return 0.0d;
		}else
			return null;
	}
	
	public static Object castAsType(Object variable, TaskFunctionModel.TypeSymbol symbol, Class<?> parameterType) {
		TaskFunctionModel.PROPERTY_TYPE type = symbol.getPropertyType();
		boolean array = symbol.isArray();
		if (variable == null) {
			return getPrimitiveDefaultVal(parameterType);
//			return null;
		}
		RDFUberType rdfType = RDFTypes.getType(type.getValue().getName());
		if (array) {
			Object[] castArr = null;
			if (variable instanceof Object[]) {
				Object[] varArr = (Object[]) variable;
				castArr = castAsEmptyArray(varArr.length, rdfType);
				for (int i = 0; i < varArr.length; i++) {
					castArr[i] = castAsType(varArr[i], rdfType,parameterType);
				}
			} else {
				castArr = castAsEmptyArray(1, rdfType);
				castArr[0] = variable;
			}
			// need to convert object array to concrete expected type
			Class classType = parameterType;
			if (classType.getComponentType().isPrimitive()) {
				return convertWrapperArrayToPrimitive(castArr, classType);
			}
			return java.util.Arrays.copyOf(castArr, castArr.length, classType);
		}
		return castAsType(variable, rdfType,parameterType);
	}

	public static Object convertWrapperArrayToPrimitive(Object[] castArr, Class classType) {
		if (int[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Integer[]) castArr);
		}
		if (double[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Double[]) castArr);
		}
		if (boolean[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Boolean[]) castArr);
		}
		if (long[].class.equals(classType)) {
			return ArrayUtils.toPrimitive((Long[]) castArr);
		}
		return castArr;
	}

	public static Object[] castAsEmptyArray(int length, RDFUberType rdfType) {
		if (RDFTypes.STRING.equals(rdfType)) {
			return new String[length];
		} else if (RDFTypes.INTEGER.equals(rdfType)) {
			return new Integer[length];
		} else if (RDFTypes.DOUBLE.equals(rdfType)) {
			return new Double[length];
		} else if (RDFTypes.BOOLEAN.equals(rdfType)) {
			return new Boolean[length];
		} else if (RDFTypes.LONG.equals(rdfType)) {
			return new Long[length];
		}
		return new Object[length];
	}

	public static Object castAsType(Object variable, RDFUberType rdfType,Object ... args) {
		Class<?> obj = null ;
		if(args != null && args.length>0){
			obj = (Class<?>) args[0];
		}
		if (variable instanceof PropertyAtom) {
			// do this here or during code generation?
			variable = ((PropertyAtom) variable).getValue();
		}
		if (RDFTypes.STRING.equals(rdfType)) {
			return String.valueOf(variable);
		} else if (RDFTypes.INTEGER.equals(rdfType)) {
			return Integer.valueOf(variable.toString());
		} else if (RDFTypes.DOUBLE.equals(rdfType)) {
			return Double.valueOf(variable.toString());
		} else if (RDFTypes.BOOLEAN.equals(rdfType)) {
			return Boolean.valueOf(variable.toString());
		} else if (RDFTypes.LONG.equals(rdfType)) {
			return Long.valueOf(variable.toString());
		}else if (RDFTypes.DATETIME.equals(rdfType) && (obj != null && com.tibco.be.util.calendar.Calendar.class.isAssignableFrom(obj))){
			final Calendar cal = (Calendar)variable;
			CalendarImpl calObj = new BECalendar(false,cal);
			return calObj;
		}
		return variable;
	}
	
	
	public static TaskFunctionModel.PROPERTY_TYPE getPropertyType(EEnumLiteral typeLit,boolean isPrimitive) {
		if(isPrimitive) {
			if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Integer)){
				return TaskFunctionModel.PROPERTY_TYPE.INTEGER;
			} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Long)){
				return TaskFunctionModel.PROPERTY_TYPE.LONG;
			} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Double)){
				return TaskFunctionModel.PROPERTY_TYPE.DOUBLE;
			} else  if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Boolean)){
				return TaskFunctionModel.PROPERTY_TYPE.BOOLEAN;
			}
		} else {
			if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Integer)){
				return TaskFunctionModel.PROPERTY_TYPE.INTEGER_WRAP;
			} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Long)){
				return TaskFunctionModel.PROPERTY_TYPE.LONG_WRAP;
			} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Double)){
				return TaskFunctionModel.PROPERTY_TYPE.DOUBLE_WRAP;
			} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Boolean)){
				return TaskFunctionModel.PROPERTY_TYPE.BOOLEAN_WRAP;
			}
		}
		if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Concept)){
			return TaskFunctionModel.PROPERTY_TYPE.CONCEPT;
		} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference)){
			return TaskFunctionModel.PROPERTY_TYPE.CONCEPT_REFERENCE;
		} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_String)){
			return TaskFunctionModel.PROPERTY_TYPE.STRING;
		} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_DateTime)){
			return TaskFunctionModel.PROPERTY_TYPE.DATE_TIME;
		} else if(typeLit.equals(BpmnModelClass.ENUM_PROPERTY_TYPES_Process)){
			return TaskFunctionModel.PROPERTY_TYPE.BASE_PROCESS;
		}
		return null;
	}
	
	
	public static TaskFunctionModel.PROPERTY_TYPE getPropertyType(ModelType mt,boolean isPrimitive) {
		switch(mt) {
		case INT:
			return isPrimitive ? TaskFunctionModel.PROPERTY_TYPE.INTEGER: TaskFunctionModel.PROPERTY_TYPE.INTEGER_WRAP;
		case LONG:
			return isPrimitive ? TaskFunctionModel.PROPERTY_TYPE.LONG: TaskFunctionModel.PROPERTY_TYPE.LONG_WRAP;
		case DOUBLE:
			return isPrimitive ? TaskFunctionModel.PROPERTY_TYPE.DOUBLE: TaskFunctionModel.PROPERTY_TYPE.DOUBLE_WRAP;
		case BOOLEAN:
			return isPrimitive ? TaskFunctionModel.PROPERTY_TYPE.BOOLEAN: TaskFunctionModel.PROPERTY_TYPE.BOOLEAN_WRAP;
		case STRING:
			return TaskFunctionModel.PROPERTY_TYPE.STRING;
		case DATETIME:
			return TaskFunctionModel.PROPERTY_TYPE.DATE_TIME;
		case CONTAINED_CONCEPT:
			return TaskFunctionModel.PROPERTY_TYPE.CONCEPT;
		case CONCEPT_REFERENCE:
			return TaskFunctionModel.PROPERTY_TYPE.CONCEPT_REFERENCE;
		case PROCESS:
			return TaskFunctionModel.PROPERTY_TYPE.BASE_PROCESS;
			
		}
		return null;
	}
	
	public static EEnumLiteral getModelPropertyType(TaskFunctionModel.PROPERTY_TYPE pt) {
		switch(pt) {
			case STRING:
					return BpmnModelClass.ENUM_PROPERTY_TYPES_String;
			case INTEGER:
			case INTEGER_WRAP:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_Integer;
			case LONG:
			case LONG_WRAP:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_Long;
			case DOUBLE:
			case DOUBLE_WRAP:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_Double;
			case BOOLEAN:
			case BOOLEAN_WRAP:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_Boolean;
			case DATE_TIME:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_DateTime;
			case CONCEPT:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_Concept;
			case CONCEPT_REFERENCE:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_ConceptReference;
			case BASE_PROCESS:
				return BpmnModelClass.ENUM_PROPERTY_TYPES_Process;
		}
		return null;
	}
}
