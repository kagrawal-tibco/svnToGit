package com.tibco.cep.studio.debug.core.model;

import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.ABSTRACT_PROPERTY_ARRAY_IMPL_CLASS;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.ABSTRACT_PROPERTY_ATOM_IMPL_CLASS;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CALENDAR_CLASS_GET_TIMEZONE_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CALENDAR_CLASS_GET_TIMEZONE_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CALENDAR_CLASS_GET_TIME_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CALENDAR_CLASS_GET_TIME_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CALENDAR_GET_TIME_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CALENDAR_GET_TIME_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CONCEPT_INTERFACE_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CONDITION_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.CONSTRUCTOR_NAME;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DATETIME_FUNCTION_CLASS_FORMAT_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DATETIME_FUNCTION_CLASS_FORMAT_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_PAYLOAD_TO_STRING_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEBUG_PAYLOAD_TO_STRING_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.DEFAULT_CONSTRUCTOR_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.ENTITY_INTERFACE_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.EVENT_INTERFACE_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.NAMED_INSTANCE_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.OBJECT_TO_STRING_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.OBJECT_TO_STRING_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROCESS_VARIABLES_INTERFACE_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ARRAY_INTERFACES2IMPL;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ARRAY_TO_ARRAY_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ARRAY_TO_ARRAY_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ATOM_GET_STRING_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ATOM_GET_STRING_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ATOM_GET_VALUE_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ATOM_GET_VALUE_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_ATOM_INTERFACES2IMPL;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_CONCEPT_GET_CONCEPT_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.PROPERTY_CONCEPT_GET_CONCEPT_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.RULEFUNCTION_INTERFACE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_CLASS;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_CLASS_SET_TIMEZONE_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_CLASS_SET_TIMEZONE_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_CONSTRUCTOR_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_FORMAT_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_FORMAT_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_SET_TIME_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.SIMPLE_DATE_FORMAT_SET_TIME_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_GET_CLOSURE_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_GET_CLOSURE_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_GET_INTERVAL_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_GET_INTERVAL_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_GET_SCHEDULED_TIME_METHOD;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_GET_SCHEDULED_TIME_METHOD_SIGNATURE;
import static com.tibco.cep.studio.debug.core.model.DebuggerConstants.TIME_EVENT_INTERFACE;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jdi.Bootstrap;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.ArrayType;
import com.sun.jdi.BooleanValue;

import com.sun.jdi.ByteValue;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassObjectReference;
import com.sun.jdi.ClassType;
import com.sun.jdi.DoubleValue;
import com.sun.jdi.Field;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.InterfaceType;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.LongValue;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.PrimitiveValue;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.util.JNISignature;
import com.tibco.cep.kernel.core.rete.conflict.AgendaItem;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.provider.SMapContentProvider;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineCompositeState;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.debug.DebugTaskFactory;
import com.tibco.cep.runtime.service.debug.DebuggerService;
import com.tibco.cep.runtime.service.debug.SmartStepInto;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Provides static method which invoked by the Debugger
 * 
 * @author pdhar
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DebuggerSupport {

	private static int[] fJDIVersion = null;

	/**
	 * @param thread
	 * @param debugTaskFactoryType
	 * @param entityURI
	 * @param xmlData
	 * @param ruleSessionURI
	 * @param destinationURI
	 * @param sessionCounter
	 * @throws DebugException
	 */
	public static void assertData(RuleDebugThread thread, ReferenceType debugTaskFactoryType, String entityURI, String xmlData, String ruleSessionURI,
			String destinationURI, int sessionCounter) throws DebugException {
		synchronized (thread) {
			final VirtualMachine vm = thread.getVM();
			final Class clazz = DebugTaskFactory.class;
			if (debugTaskFactoryType == null) {
				debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}
			final String methodName = "newDebugTask";
			final String signature = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V";
			final Method newDebugTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) debugTaskFactoryType, methodName, signature);
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(vm.mirrorOf(entityURI));
			args.add(vm.mirrorOf(xmlData));
			if (ruleSessionURI != null) {
				args.add(vm.mirrorOf(ruleSessionURI));
			} else {
				args.add(vm.mirrorOf((String) null));
			}
			if (destinationURI != null) {
				args.add(vm.mirrorOf(destinationURI));
			} else {
				args.add(null);
			}
			args.add(vm.mirrorOf(sessionCounter));
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.disableCollection();
				}
			}
			thread.invokeMethod(((ClassType) debugTaskFactoryType), null, newDebugTaskMethod, args, false);
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.enableCollection();
				}
			}
		}
	}

	/**
	 * 
	 * @param thread
	 * @param calendar
	 * @param flags
	 * @return
	 */
	public static ObjectReference calendarGetTime(RuleDebugThread thread, ObjectReference calendar, int flags) {
		return (ObjectReference) invokeConcreteMethod(CALENDAR_GET_TIME_METHOD, CALENDAR_GET_TIME_METHOD_SIGNATURE, thread, calendar, Collections.EMPTY_LIST,
				flags);
	}

	public static void clearWMContents(RuleDebugThread eventThread, ReferenceType debugTaskFactoryType, String ruleSessionName) throws DebugException {
		synchronized (eventThread) {
			final VirtualMachine vm = eventThread.getVM();
			final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
			if (debugTaskFactoryType == null) {
				debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}

			final String methodName = "newClearWMContentsTask";
			String signature = "";
			try {
				signature = getMethodSignature(clazz, methodName, String.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}// "(Ljava/lang/String;J[Ljava/lang/Integer;[Ljava/lang/String;)V";//getMethodSignature(clazz,
				// methodName, String.class, Integer.class);
			final Method processTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) debugTaskFactoryType, methodName, signature);
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(vm.mirrorOf(ruleSessionName));

			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.disableCollection();
				}
			}
			eventThread.invokeMethod(((ClassType) debugTaskFactoryType), null, processTaskMethod, args, false);
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.enableCollection();
				}
			}
		}

	}

	/**
	 * @param thread
	 * @param debugTaskFactoryType
	 * @param entityURI
	 * @param xmlData
	 * @param destinationURI
	 * @param ruleSessionURI
	 * @param sessionName
	 * @param sessionCounter
	 * @throws Exception
	 */
	public static void debugTestData(RuleDebugThread thread, ReferenceType debugTaskFactoryType, String[] entityURI, String[] xmlData, String[] destinationURI,
			String ruleSessionName, String testerSessionName, int sessionCounter) throws Exception {
		synchronized (thread) {
			final VirtualMachine vm = thread.getVM();
			final Class clazz = DebugTaskFactory.class;
			if (debugTaskFactoryType == null) {
				debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}
			final String methodName = "newDebugTestDataTask";
			final String signature = "([Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V";
			final Method newDebugTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) debugTaskFactoryType, methodName, signature);

			ArrayList<Value> args = new ArrayList<Value>();

			ReferenceType strArrType_1 = findClassBySignature(vm, "[Ljava/lang/String;");
			ArrayType arrayType_1 = (ArrayType) strArrType_1;
			ArrayReference arrRef_1 = arrayType_1.newInstance(entityURI.length);
			arrRef_1.disableCollection();

			for (int loop = 0; loop < entityURI.length; loop++) {
				arrRef_1.setValue(loop, vm.mirrorOf(entityURI[loop]));
			}
			args.add(arrRef_1);

			ReferenceType strArrType_2 = findClassBySignature(vm, "[Ljava/lang/String;");
			ArrayType arrayType_2 = (ArrayType) strArrType_2;
			ArrayReference arrRef_2 = arrayType_2.newInstance(xmlData.length);
			arrRef_2.disableCollection();

			for (int loop = 0; loop < xmlData.length; loop++) {
				arrRef_2.setValue(loop, vm.mirrorOf(xmlData[loop]));
			}
			args.add(arrRef_2);

			ReferenceType strArrType_3 = findClassBySignature(vm, "[Ljava/lang/String;");
			ArrayType arrayType_3 = (ArrayType) strArrType_3;
			ArrayReference arrRef_3 = arrayType_3.newInstance(destinationURI.length);
			arrRef_3.disableCollection();

			for (int loop = 0; loop < destinationURI.length; loop++) {
				arrRef_3.setValue(loop, vm.mirrorOf(destinationURI[loop]));
			}
			args.add(arrRef_3);

			if (ruleSessionName != null) {
				args.add(vm.mirrorOf(ruleSessionName));
			} else {
				args.add(vm.mirrorOf((String) null));
			}
			if (testerSessionName != null) {
				args.add(vm.mirrorOf(testerSessionName));
			} else {
				args.add(null);
			}
			args.add(vm.mirrorOf(sessionCounter));
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.disableCollection();
				}
			}
			thread.invokeMethod(((ClassType) debugTaskFactoryType), null, newDebugTaskMethod, args, false);
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.enableCollection();
				}
			}
		}
	}

	/**
	 * @param eventThread
	 * @param DebugTaskFactoryType
	 * @param rules
	 * @param deploy
	 * @throws DebugException
	 * @throws InvalidTypeException
	 * @throws ClassNotLoadedException
	 */
	public static void deployRules(RuleDebugThread eventThread, ReferenceType DebugTaskFactoryType, String[] rules, boolean deploy) throws DebugException,
			InvalidTypeException, ClassNotLoadedException {
		synchronized (eventThread) {

			VirtualMachine vm = eventThread.getVM();
			Class clazz = DebugTaskFactory.class;
			if (DebugTaskFactoryType == null) {
				DebugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}
			String methodName = "deployRules";
			String signature = "([Ljava/lang/String;Z)V";
			Method processTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) DebugTaskFactoryType, methodName, signature);
			ArrayList<Value> args = new ArrayList<Value>();
			ReferenceType strArrType = findClassBySignature(vm, "[Ljava/lang/String;");
			// for(ReferenceType r:vm.allClasses()) {
			// if(r instanceof ArrayType) {
			// String sig = ((ArrayType)r).componentSignature();
			// String typeName =((ArrayType)r).componentTypeName();
			// System.out.println("signature:"+sig);
			// System.out.println("typeName:"+typeName);
			// }
			// }
			ArrayType arrayType = (ArrayType) strArrType;
			ArrayReference arrRef = arrayType.newInstance(rules.length);
			arrRef.disableCollection();
			for (int i = 0; i < rules.length; i++) {
				arrRef.setValue(i, vm.mirrorOf(rules[i]));
			}
			args.add(arrRef);
			args.add(vm.mirrorOf(deploy));
			try {
				eventThread.invokeMethod(((ClassType) DebugTaskFactoryType), null, processTaskMethod, args, false);
			} finally {
				arrRef.enableCollection();
			}
		}
	}

	/**
	 * @param clzType
	 * @param clazz
	 * @return
	 */
	public static boolean extendsClass(ClassType clzType, Class clazz) {
		ClassType superClazz = clzType.superclass();
		if (superClazz != null) {
			return superClazz.name().equals(clazz.getName());
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param eventThread
	 * @param debugTaskFactoryType
	 * @param ruleSessionName
	 * @param fetchCount
	 * @throws Exception
	 */
	public static void fetchWMContents(RuleDebugThread eventThread, ReferenceType debugTaskFactoryType, String ruleSessionName, int fetchCount)
			throws Exception {
		synchronized (eventThread) {
			final VirtualMachine vm = eventThread.getVM();
			final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
			if (debugTaskFactoryType == null) {
				debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}

			final String methodName = "newWMContentsTask";
			final String signature = "(Ljava/lang/String;I)V";// getMethodSignature(clazz,
																// methodName,
																// String.class,
																// Integer.class);
			final Method processTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) debugTaskFactoryType, methodName, signature);

			ArrayList<Value> args = new ArrayList<Value>();
			args.add(vm.mirrorOf(ruleSessionName));
			args.add(vm.mirrorOf(fetchCount));

			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.disableCollection();
				}
			}
			eventThread.invokeMethod(((ClassType) debugTaskFactoryType), null, processTaskMethod, args, false);
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.enableCollection();
				}
			}
		}
	}

	public static ReferenceType findClass(VirtualMachine vm, String className) {
		List<ReferenceType> rtypes = vm.classesByName(className);
		if (rtypes.size() > 0) {
			for (ReferenceType rt : rtypes) {
				if (rt.name().equals(className)) {
					return rt;
				}
			}
		}
		return null;
	}

	public static List<ReferenceType> findClassByInterface(VirtualMachine vm, String interfaceName) {
		List<ReferenceType> rtypes = vm.allClasses();
		List<ReferenceType> intfClasses = new ArrayList<ReferenceType>();
		if (rtypes.size() > 0) {
			for (ReferenceType rt : rtypes) {
				if (implementsInterface((ClassType) rt, interfaceName)) {
					intfClasses.add(rt);
				}
			}
		}
		return intfClasses;
	}

	protected static ReferenceType findClassBySignature(VirtualMachine vm, String s) {
		List<ReferenceType> refTypes = vm.allClasses();
		for (ReferenceType r : refTypes) {
			if (r.signature().equals(s)) {
				return r;
			}
		}
		return null;
	}

	public static Method findMethodByName(ReferenceType refType, String methodName) {
		List<Method> classMethods = refType.methodsByName(methodName);
		for (Method method : classMethods) {
			if (method.name().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	public static Method findMethodBySignature(ClassType classType, String methodName, String signature) {
		return classType.concreteMethodByName(methodName, signature);
	}

	public static Method findMethodBySignature(InterfaceType interfaceType, String methodName, String signature) {
		List<Method> methods = interfaceType.methodsByName(methodName, signature);
		if (methods.size() > 0) {
			return methods.get(0);
		}
		return null;
	}

	public static String formatAsTime(long timestamp, String format) {
		Date date = new Date(timestamp);
		// TODO: Get the time format as a preference from user
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String timeStr = formatter.format(date);
		return timeStr;
	}

	/**
	 * @param thread
	 * @param calendar
	 * @param flags
	 * @return
	 * @throws DebugException
	 */
	public static String formatCalendar(RuleDebugThread thread, ObjectReference calendar, int flags) throws DebugException {
		synchronized (thread) {

			VirtualMachine vm = thread.getUnderlyingThread().virtualMachine();
			ArrayList<ObjectReference> args_ = new ArrayList<ObjectReference>();

			// "yyyy-MM-dd HH:mm:ss z"
			StringReference formatString = vm.mirrorOf(Format.STANDARD_DATE_FORMAT);
			formatString.disableCollection();

			args_.add(formatString);

			// new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
			List classes = vm.classesByName(SIMPLE_DATE_FORMAT_CLASS);
			ClassType simpleDateFormatClass = (ClassType) classes.get(0);
			Method constructor = simpleDateFormatClass.concreteMethodByName(CONSTRUCTOR_NAME, SIMPLE_DATE_FORMAT_CONSTRUCTOR_SIGNATURE);
			ObjectReference sdf = thread.newInstance(simpleDateFormatClass, constructor, args_);
			// ObjectReference sdf = simpleDateFormatClass.newInstance(thread,
			// constructor, args_, ObjectReference.INVOKE_SINGLE_THREADED);
			sdf.disableCollection();

			// calendar.getTimeZone();
			ObjectReference timezone = (ObjectReference) invokeConcreteMethod(CALENDAR_CLASS_GET_TIMEZONE_METHOD, CALENDAR_CLASS_GET_TIMEZONE_METHOD_SIGNATURE,
					thread, calendar, Collections.EMPTY_LIST, ObjectReference.INVOKE_SINGLE_THREADED);
			timezone.disableCollection();

			// sdf.setTimeZone(calendar.getTimeZone());
			args_.clear();
			args_.add(timezone);
			invokeConcreteMethod(SIMPLE_DATE_FORMAT_CLASS_SET_TIMEZONE_METHOD, SIMPLE_DATE_FORMAT_CLASS_SET_TIMEZONE_METHOD_SIGNATURE, thread, sdf, args_,
					ObjectReference.INVOKE_SINGLE_THREADED);

			// calendar.getTime();
			ObjectReference time = (ObjectReference) invokeConcreteMethod(CALENDAR_CLASS_GET_TIME_METHOD, CALENDAR_CLASS_GET_TIME_METHOD_SIGNATURE, thread,
					calendar, Collections.EMPTY_LIST, ObjectReference.INVOKE_SINGLE_THREADED);
			time.disableCollection();

			// sdf.setTime(calendar.getTime());
			invokeConcreteMethod(SIMPLE_DATE_FORMAT_SET_TIME_METHOD, SIMPLE_DATE_FORMAT_SET_TIME_METHOD_SIGNATURE, thread, sdf, args_,
					ObjectReference.INVOKE_SINGLE_THREADED);

			ClassType dateTimeClass = (ClassType) classes.get(0);
			Method dateTimeCon = dateTimeClass.concreteMethodByName(CONSTRUCTOR_NAME, DEFAULT_CONSTRUCTOR_SIGNATURE);
			args_.clear();
			args_.add(calendar);
			ObjectReference dateTimeRef = thread.newInstance(dateTimeClass, dateTimeCon, Collections.EMPTY_LIST);
			// ObjectReference dateTimeRef = dateTimeClass.newInstance(thread,
			// dateTimeCon, Collections.EMPTY_LIST, flags);
			dateTimeRef.disableCollection();

			args_.clear();
			args_.add(time);

			StringReference format = null;
			try {
				format = (StringReference) invokeConcreteMethod(DATETIME_FUNCTION_CLASS_FORMAT_METHOD, DATETIME_FUNCTION_CLASS_FORMAT_SIGNATURE, thread,
						dateTimeRef, args_, flags);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// cleanup
			formatString.enableCollection();
			dateTimeRef.enableCollection();
			sdf.enableCollection();
			timezone.enableCollection();
			time.enableCollection();
			args_.clear();

			if (format == null) {
				return null;
			} else {
				return format.value();
			}
		}
	}

	/**
	 * 
	 * @param actionItem
	 * @return
	 */
	public static Method getActionExecuteMethod(ObjectReference actionItem) {
		if (actionItem == null || !implementsInterface(actionItem, "com.tibco.cep.kernel.model.rule.Action")) {
			throw new IllegalArgumentException("The object reference does not implement the " + Action.class.getName() + " interface");
		}
		Method execMethod = findMethodBySignature((ClassType) actionItem.referenceType(), "execute", "([Ljava/lang/Object;)V");
		return execMethod;
	}

	/**
	 * 
	 * @param eventThread
	 * @param resolverMirror
	 * @return
	 * @throws DebugException
	 */
	public static ArrayReference getAgendaItems(RuleDebugThread eventThread, ObjectReference resolverMirror) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(resolverMirror, ConflictResolver.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + ConflictResolver.class.getName() + " interface");
			}
			Method agendaToArrayMethod = findMethodBySignature((ClassType) resolverMirror.referenceType(), "agendaToArray",
					"()[Lcom/tibco/cep/kernel/core/rete/conflict/AgendaItem;");
			ArrayReference agendaItems = (ArrayReference) eventThread.invokeMethod(null, resolverMirror, agendaToArrayMethod, Collections.EMPTY_LIST, false);
			return agendaItems;
		}
	}

	/**
	 * @param eventThread
	 * @param agendaItemVal
	 * @return
	 * @throws DebugException
	 */
	public static Value getAgendaParams(RuleDebugThread eventThread, ObjectReference agendaItemVal) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) agendaItemVal.referenceType()).superclass().name().equals(AgendaItem.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + AgendaItem.class.getName() + " interface");
			}
			Method getCauseMethod = findMethodBySignature((ClassType) agendaItemVal.referenceType(), "getParams", "()Ljava/lang/Object;");
			Value rule = eventThread.invokeMethod(null, agendaItemVal, getCauseMethod, Collections.EMPTY_LIST, false);
			return rule;
		}
	}

	/**
	 * 
	 * @param eventThread
	 * @param agendaItemVal
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getAgendaRule(RuleDebugThread eventThread, ObjectReference agendaItemVal) throws DebugException {
		synchronized (eventThread) {
			if (!((ClassType) agendaItemVal.referenceType()).superclass().name().equals(AgendaItem.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + AgendaItem.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) agendaItemVal.referenceType(), "getCause", "()Ljava/lang/Object;");
			Value rule = eventThread.invokeMethod(null, agendaItemVal, classMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) rule;
		}
	}

	public static Map<String, CacheAgent.Type> getAgentTypeMap(RuleDebugThread rdt) throws DebugException {
		Map<String, CacheAgent.Type> agentTypeMap = new HashMap<String, CacheAgent.Type>();
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference wrapperRspInstance = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			Method getClusterMethod = findMethodBySignature((ClassType) wrapperRspInstance.referenceType(), "getCluster",
					"()Lcom/tibco/cep/runtime/service/cluster/Cluster;");
			ObjectReference clusterInstance = (ObjectReference) rdt.invokeMethod(null, wrapperRspInstance, getClusterMethod, Collections.EMPTY_LIST, false);
			if (clusterInstance != null) {
				Method getAgentManagerMethod = findMethodBySignature((ClassType) clusterInstance.referenceType(), "getAgentManager",
						"()Lcom/tibco/cep/runtime/service/cluster/agent/AgentManager;");
				ObjectReference agentManagerInstance = (ObjectReference) rdt.invokeMethod(null, clusterInstance, getAgentManagerMethod, Collections.EMPTY_LIST,
						false);
				if (agentManagerInstance != null) {
					Method getAgentsMethod = findMethodBySignature((ClassType) agentManagerInstance.referenceType(), "getAgents", "()Ljava/util/Collection;");
					Value agentTuples = rdt.invokeMethod(null, agentManagerInstance, getAgentsMethod, Collections.EMPTY_LIST, false);
					ArrayReference entryArray = getArrayReference(rdt, (ObjectReference) agentTuples);
					try {
						entryArray.disableCollection();
						for (Object v : entryArray.getValues()) {
							if (v instanceof ObjectReference) {
								ObjectReference entryObj = (ObjectReference) v;
								Method getTypeMethod = findMethodBySignature((ClassType) entryObj.referenceType(), "getType",
										"()Lcom/tibco/cep/runtime/service/cluster/CacheAgent$Type;");
								Method getAgentNameMethod = findMethodBySignature((ClassType) entryObj.referenceType(), "getAgentName", "()Ljava/lang/String;");
								Value agentName = rdt.invokeMethod(null, entryObj, getAgentNameMethod, Collections.EMPTY_LIST, false);
								ObjectReference agentType = (ObjectReference) rdt.invokeMethod(null, entryObj, getTypeMethod, Collections.EMPTY_LIST, false);
								Method getNameMethod = findMethodBySignature((ClassType) agentType.referenceType(), "name", "()Ljava/lang/String;");
								ObjectReference typeName = (ObjectReference) rdt.invokeMethod(null, agentType, getNameMethod, Collections.EMPTY_LIST, false);
								agentTypeMap.put(value2String(rdt, agentName), CacheAgent.Type.valueOf(value2String(rdt, typeName)));
							}
						}

					} finally {
						entryArray.enableCollection();
					}
				}
			}
			return agentTypeMap;
		}
	}

	/*
	 * Return a ArrayReference by invoking toArray on collection ObjectReference
	 */
	private static ArrayReference getArrayReference(RuleDebugThread rdt, ObjectReference objInstance) throws DebugException {
		Method toArrayMethod = findMethodBySignature((ClassType) objInstance.referenceType(), "toArray", "()[Ljava/lang/Object;");
		ArrayReference arrayRef = (ArrayReference) rdt.invokeMethod(null, objInstance, toArrayMethod, Collections.EMPTY_LIST, false);
		return arrayRef;
	}

	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws DebugException
	 */
	private static Value getBooleanValue(RuleDebugThread eventThread, ObjectReference jdiValue) throws DebugException {
		synchronized (eventThread) {
			if (!((ObjectReference) jdiValue).referenceType().name().equals(Boolean.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + Boolean.class.getName());
			}
			Method classMethod = findMethodBySignature((ClassType) jdiValue.referenceType(), "booleanValue", "()Z");
			Value conceptIdVal = eventThread.invokeMethod(null, jdiValue, classMethod, Collections.EMPTY_LIST, false);
			return conceptIdVal;
		}

	}

	/**
	 * @param eventThread
	 * @param omRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getCacheAgent(RuleDebugThread eventThread, ObjectReference omRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(omRef, ObjectBasedStore.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + ObjectBasedStore.class.getName() + " interface");
			}
			Method getAgent = findMethodBySignature((ClassType) omRef.referenceType(), "getCacheAgent",
					"()Lcom/tibco/cep/runtime/service/om/coherence/cluster/agents/InferenceAgent;");
			Value agentRef = eventThread.invokeMethod(null, omRef, getAgent, Collections.EMPTY_LIST, false);
			return (ObjectReference) agentRef;
		}
	}

	/**
	 * @param eventThread
	 * @param cref
	 * @return
	 * @throws DebugException
	 */
	public static LongValue getCalendarTimeInMillis(RuleDebugThread eventThread, ObjectReference cref) throws DebugException {
		synchronized (eventThread) {
			if (Calendar.class.getName().equals(cref.referenceType().name())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Calendar.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) cref.referenceType(), "getTimeInMillis", "()J");
			LongValue timeMillis = (LongValue) eventThread.invokeMethod(null, cref, classMethod, Collections.EMPTY_LIST, false);
			return timeMillis;
		}
	}

	/**
	 * Get cluster name if engine running with cache cluster mode
	 * 
	 * @param rdt
	 * @return
	 * @throws DebugException
	 */
	public static String getClusterName(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			String clusterName = null;
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference wrapperRspInstance = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			Method getClusterMethod = findMethodBySignature((ClassType) wrapperRspInstance.referenceType(), "getCluster",
					"()Lcom/tibco/cep/runtime/service/cluster/Cluster;");
			ObjectReference clusterInstance = (ObjectReference) rdt.invokeMethod(null, wrapperRspInstance, getClusterMethod, Collections.EMPTY_LIST, false);
			if (clusterInstance != null) {
				Method getClusterNameMethod = findMethodBySignature((ClassType) clusterInstance.referenceType(), "getClusterName", "()Ljava/lang/String;");
				Value clusterNameValue = rdt.invokeMethod(null, clusterInstance, getClusterNameMethod, Collections.EMPTY_LIST, false);
				if (clusterNameValue != null) {
					clusterName = ((StringReference) clusterNameValue).value();
				}
			}
			return clusterName;
		}
	}

	/**
	 * @param eventThread
	 * @param conceptRef
	 * @return
	 * @throws AbsentInformationException
	 */
	public static String getConceptName(RuleDebugThread eventThread, ObjectReference conceptRef) throws AbsentInformationException {
		synchronized (eventThread) {
			if (!implementsInterface(conceptRef, Concept.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Concept.class.getName() + " interface");
			}
			String name = conceptRef.referenceType().sourceName().replace(".java", "");
			return name;
		}
	}

	/**
	 * @param eventThread
	 * @param conceptRef
	 * @return
	 * @throws DebugException
	 * @throws ClassNotLoadedException
	 */
	public static Map<String, Value> getConceptProperties(RuleDebugThread eventThread, ObjectReference conceptRef) throws DebugException,
			ClassNotLoadedException {
		synchronized (eventThread) {
			if (!implementsInterface(conceptRef, Concept.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Concept.class.getName() + " interface");
			}
			Map<String, Value> propertyMap = new LinkedHashMap<String, Value>();
			Method getPropertiesMethod = findMethodBySignature((ClassType) conceptRef.referenceType(), "getProperties", "()[Lcom/tibco/cep/runtime/model/element/Property;");
			Value propertiesVal = eventThread.invokeMethod(null, conceptRef, getPropertiesMethod, Collections.EMPTY_LIST, false);
			if (propertiesVal instanceof ArrayReference) {
				ArrayReference arf = (ArrayReference) propertiesVal;
				ReferenceType pc = findClass(eventThread.getVM(), Property.class.getName());
				Method getNameMethod = findMethodBySignature((InterfaceType)pc, "getName", "()Ljava/lang/String;");
				for (Object pv : arf.getValues()) {
					Value nameVal = eventThread.invokeMethod(null, (ObjectReference) pv, getNameMethod, Collections.EMPTY_LIST, false);
					propertyMap.put(value2String(eventThread, nameVal), (Value) pv);
				}
			}
					
//			List<Field> flds = conceptRef.referenceType().visibleFields();
//			for (Field f : flds) {
//				if (f.name().startsWith("$2z") && implementsInterface((ClassType) f.type(), Property.class.getName())) {
//					Value v = conceptRef.getValue(f);
//					if (v == null) {
//						Method method = DebuggerSupport.findMethodByName(conceptRef.referenceType(), "get" + f.name());
//						// Method classMethod =
//						// findMethodBySignature((ClassType)
//						// propRef.referenceType(), "getName",
//						// "()Ljava/lang/String;");
//						if (method == null)
//							continue;
//						v = eventThread.invokeMethod(null, conceptRef, method, Collections.EMPTY_LIST, false);
//					}
//					propertyMap.put(f.name(), v);
//				}
//			}
			return propertyMap;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static IntegerValue getConceptPropertyArrayLength(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyArray.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyArray.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "length", "()I");
			Value valueRef = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return (IntegerValue) valueRef;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @param arrayIndex
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getConceptPropertyArrayValueAtom(RuleDebugThread eventThread, ObjectReference propRef, int arrayIndex) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(propRef, PropertyArray.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyArray.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "get", "(I)Lcom/tibco/cep/runtime/model/element/PropertyAtom;");
			ArrayList<Value> args = new ArrayList<Value>();
			IntegerValue arrayIndexRef = eventThread.getVM().mirrorOf(arrayIndex);
			args.add(arrayIndexRef);
			Value valueRef = eventThread.invokeMethod(null, propRef, classMethod, args, false);
			return (ObjectReference) valueRef;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @param histIndex
	 * @return
	 * @throws DebugException
	 */
	public static LongValue getConceptPropertyAtomHistoryTime(RuleDebugThread eventThread, ObjectReference propRef, int histIndex) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtom.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtom.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getTimeAtIdx", "(I)J");
			ArrayList<Value> args = new ArrayList<Value>();
			IntegerValue histIndexRef = eventThread.getVM().mirrorOf(histIndex);
			args.add(histIndexRef);
			Value valueRef = eventThread.invokeMethod(null, propRef, classMethod, args, false);
			return (LongValue) valueRef;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @param histIndex
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getConceptPropertyAtomHistoryValue(RuleDebugThread eventThread, ObjectReference propRef, int histIndex) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(propRef, PropertyAtom.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtom.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getValueAtIdx", "(I)Ljava/lang/Object;");
			ArrayList<Value> args = new ArrayList<Value>();
			IntegerValue histIndexRef = eventThread.getVM().mirrorOf(histIndex);
			args.add(histIndexRef);
			Value valueRef = eventThread.invokeMethod(null, propRef, classMethod, args, false);
			return (ObjectReference) valueRef;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getConceptPropertyAtomValue(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtom.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtom.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getValue", "()Ljava/lang/Object;");
			Value valueRef = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) valueRef;
		}
	}
	
	public static ObjectReference getPropertyAtomConceptValue(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtomConcept.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomConcept.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getConcept", "()Lcom/tibco/cep/runtime/model/element/Concept;");
			Value valueRef = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) valueRef;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static IntegerValue getConceptPropertyHistorySize(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, Property.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Property.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getHistorySize", "()I");
			Value sizeRef = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return (IntegerValue) sizeRef;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static StringReference getConceptPropertyName(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, Property.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Property.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getName", "()Ljava/lang/String;");
			Value nameRef = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return (StringReference) nameRef;
		}
	}

	/**
	 * 
	 * @param eventThread
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getCurrentRuleSession(RuleDebugThread eventThread) throws DebugException {
		synchronized (eventThread) {
			ObjectReference ruleSessionMirror;
			ReferenceType ruleSessionManagerMirror = findClass(eventThread.getUnderlyingThread().virtualMachine(), RuleSessionManager.class.getName());
			Method classMethod = findMethodBySignature((ClassType) ruleSessionManagerMirror, "getCurrentRuleSession",
					"()Lcom/tibco/cep/runtime/session/RuleSession;");
			Value value = eventThread.invokeMethod((ClassType) ruleSessionManagerMirror, null, classMethod, Collections.EMPTY_LIST, false);
			ruleSessionMirror = ((ObjectReference) value);
			return ruleSessionMirror;
		}
	}

	public static String getCurrentRuleSessionName(final RuleDebugThread rdt) throws DebugException {
		ObjectReference currentRuleSession = getCurrentRuleSession(rdt);
		if (currentRuleSession == null) {
			return null;
		}
		Method getNameMethod = findMethodBySignature((ClassType) currentRuleSession.referenceType(), "getName", "()Ljava/lang/String;");
		Value name = rdt.invokeMethod(null, currentRuleSession, getNameMethod, Collections.EMPTY_LIST, false);
		return value2String(rdt, name);
	}

	public static ObjectReference getDebuggerServiceInstance(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ReferenceType refClazz = findClass(rdt.getVM(), DebuggerService.class.getName());
			Method instanceMethod = findMethodBySignature((ClassType) refClazz, "getInstance", "()Lcom/tibco/cep/runtime/service/debug/DebuggerService;");
			Value serviceInstance = rdt.invokeMethod((ClassType) refClazz, null, instanceMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) serviceInstance;
		}
	}

	public static ObjectReference getDeployedProject(RuleDebugThread rdt, ObjectReference rspInstance) throws DebugException {
		synchronized (rdt) {

			if (!implementsInterface((ClassType) rspInstance.referenceType(), RuleServiceProvider.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + RuleServiceProvider.class.getName());
			}

			ReferenceType refClazz = rspInstance.referenceType();
			Method runtimeMethod = findMethodBySignature((ClassType) refClazz, "getProject", "()Lcom/tibco/cep/repo/DeployedProject;");
			Value projectInstance = rdt.invokeMethod(null, rspInstance, runtimeMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) projectInstance;
		}
	}

	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws DebugException
	 */
	public static Value getDoubleValue(RuleDebugThread eventThread, ObjectReference jdiValue) throws DebugException {
		synchronized (eventThread) {
			if (!((ObjectReference) jdiValue).referenceType().name().equals(Double.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + Double.class.getName());
			}
			Method classMethod = findMethodBySignature((ClassType) jdiValue.referenceType(), "doubleValue", "()D");
			Value conceptIdVal = eventThread.invokeMethod(null, jdiValue, classMethod, Collections.EMPTY_LIST, false);
			return conceptIdVal;
		}

	}

	/**
	 * 
	 * @param thread
	 * @param entity
	 * @return
	 * @throws DebugException
	 */
	public static StringReference getEntityExtId(RuleDebugThread thread, ObjectReference entity) throws DebugException {
		synchronized (thread) {

			if (!implementsInterface(entity, Entity.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Entity.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) entity.referenceType(), "getExtId", "()Ljava/lang/String;");
			Value id = thread.invokeMethod(null, entity, classMethod, Collections.EMPTY_LIST, false);
			return ((StringReference) id);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param entity
	 * @return
	 * @throws DebugException
	 */
	public static LongValue getEntityId(RuleDebugThread thread, ObjectReference entity) throws DebugException {
		synchronized (thread) {

			if (!implementsInterface(entity, Entity.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Entity.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) entity.referenceType(), "getId", "()J");
			Value id = thread.invokeMethod(null, entity, classMethod, Collections.EMPTY_LIST, false);
			return ((LongValue) id);
		}
	}

	/**
	 * @param eventThread
	 * @param omRef
	 * @param entityUri
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getEntityProvider(RuleDebugThread eventThread, ObjectReference omRef, StringReference entityUri) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(omRef, ObjectBasedStore.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + ObjectBasedStore.class.getName() + " interface");
			}
			Method getEntityProvider = findMethodBySignature((ClassType) omRef.referenceType(), "getEntityDao",
					"(Ljava/lang/String;)Lcom/tibco/cep/runtime/service/om/coherence/cluster/ClusterEntityProvider;");
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(entityUri);
			Value clusterEntityProviderRef = eventThread.invokeMethod(null, omRef, getEntityProvider, args, false);
			return ((ObjectReference) clusterEntityProviderRef);
		}
	}

	/**
	 * @param eventThread
	 * @param eventRef
	 * @return
	 * @throws DebugException
	 */
	public static ArrayReference getEventPropertyNames(RuleDebugThread eventThread, ObjectReference eventRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(eventRef, SimpleEvent.class.getName())) {
				return null;
			}

			Method classMethod = findMethodBySignature((ClassType) eventRef.referenceType(), "getPropertyNames", "()[Ljava/lang/String;");
			Value propsRef = eventThread.invokeMethod(null, eventRef, classMethod, Collections.EMPTY_LIST, false);
			return (ArrayReference) propsRef;
		}
	}

	/**
	 * @param eventThread
	 * @param typeDescRef
	 * @return
	 * @throws DebugException
	 */
	public static ClassObjectReference getImplClass(RuleDebugThread eventThread, ObjectReference typeDescRef) throws DebugException {
		synchronized (eventThread) {

			Method classMethod = findMethodBySignature((ClassType) typeDescRef.referenceType(), "getImplClass", "()Ljava/lang/Class;");
			Value classRef = eventThread.invokeMethod(null, typeDescRef, classMethod, Collections.EMPTY_LIST, false);
			return (ClassObjectReference) classRef;
		}
	}

	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws DebugException
	 */
	public static Value getIntValue(RuleDebugThread eventThread, ObjectReference jdiValue) throws DebugException {
		synchronized (eventThread) {

			if (!((ObjectReference) jdiValue).referenceType().name().equals(Integer.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + Integer.class.getName());
			}
			Method classMethod = findMethodBySignature((ClassType) jdiValue.referenceType(), "intValue", "()I");
			Value conceptIdVal = eventThread.invokeMethod(null, jdiValue, classMethod, Collections.EMPTY_LIST, false);
			return conceptIdVal;
		}

	}

	public static int[] getJDIVersion() {
		if (fJDIVersion == null) {
			fJDIVersion = new int[2];
			VirtualMachineManager mgr = Bootstrap.virtualMachineManager();
			fJDIVersion[0] = mgr.majorInterfaceVersion();
			fJDIVersion[1] = mgr.minorInterfaceVersion();
		}
		return fJDIVersion;
	}

	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 * @throws DebugException
	 */
	public static Value getLongValue(RuleDebugThread eventThread, ObjectReference jdiValue) throws DebugException {
		synchronized (eventThread) {

			if (!((ObjectReference) jdiValue).referenceType().name().equals(Long.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + Long.class.getName());
			}
			Method classMethod = findMethodBySignature((ClassType) jdiValue.referenceType(), "longValue", "()J");
			Value conceptIdVal = eventThread.invokeMethod(null, jdiValue, classMethod, Collections.EMPTY_LIST, false);
			return conceptIdVal;
		}

	}

	/**
	 * @param clazz
	 * @param methodName
	 * @param ctypes
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static String getMethodSignature(Class clazz, String methodName, Class... ctypes) throws NoSuchMethodException {
		java.lang.reflect.Method m = clazz.getDeclaredMethod(methodName, ctypes);
		return JNISignature.generateMethodSignature(m);
	}

	/**
	 * @param eventThread
	 * @param omRef
	 * @param uriRef
	 * @param classRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getNamedInstance(RuleDebugThread eventThread, ObjectReference omRef, StringReference uriRef, ClassObjectReference classRef)
			throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(omRef, ObjectManager.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + ObjectManager.class.getName() + " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) omRef.referenceType(), "getNamedInstance",
					"(Ljava/lang/String;Ljava/lang/Class;)Lcom/tibco/cep/kernel/model/entity/Element;");
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(uriRef);
			args.add(classRef);
			Value elementRef = eventThread.invokeMethod(null, omRef, classMethod, args, false);
			return (ObjectReference) elementRef;
		}
	}

	public static Integer getNumTasksFromBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType.name().equals("com.tibco.cep.bpmn.runtime.agent.StarterJobImpl")||
					refType.name().equals("com.tibco.cep.bpmn.runtime.agent.JobImpl")) {
				Method mp = findMethodBySignature((ClassType) refType, "getJobContext", "()Lcom/tibco/cep/bpmn/runtime/model/JobContext;");
				Value pv = rdt.invokeMethod(null, thisObject, mp, Collections.EMPTY_LIST, false);
				Method m = findMethodBySignature((ClassType) ((ObjectReference) pv).referenceType(), "getNumTasks", "()I");
				Value value = rdt.invokeMethod(null, ((ObjectReference) pv), m, Collections.EMPTY_LIST, false);
				if (value instanceof IntegerValue) {
					return ((IntegerValue) value).intValue();
				}
			}

		}
		return -1;
	}

	/**
	 * @param eventThread
	 * @param ruleSessionRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getObjectManager(RuleDebugThread eventThread, ObjectReference ruleSessionRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(ruleSessionRef, RuleSession.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + RuleSession.class.getName() + " interface");
			}
			Method getOMMethod = findMethodBySignature((ClassType) ruleSessionRef.referenceType(), "getObjectManager",
					"()Lcom/tibco/cep/kernel/service/ObjectManager;");
			Value omRef = eventThread.invokeMethod(null, ruleSessionRef, getOMMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) omRef;
		}
	}

	public static Object getPrimitiveValue(Value value) {
		if (value == null) {
			return "";
		}
		if (value instanceof IntegerValue) {
			return ((IntegerValue) value).intValue();
		} else if (value instanceof LongValue) {
			return ((LongValue) value).longValue();
		} else if (value instanceof DoubleValue) {
			return ((DoubleValue) value).doubleValue();
		} else if (value instanceof BooleanValue) {
			return ((BooleanValue) value).booleanValue();
		} else {
			return value.toString();
		}
	}

	/*
	 * TODO - ConceptTreeNode public static void
	 * fillConceptProperties(ThreadReference thread, ObjectReference concept,
	 * ConceptTreeNode node) throws ClassNotLoadedException,
	 * InvocationException, IncompatibleThreadStateException,
	 * InvalidTypeException, ClassNotFoundException { ReferenceType conceptType
	 * = concept.referenceType(); List methods = conceptType.allMethods();
	 * Iterator methodIt = methods.iterator(); while (methodIt.hasNext()) {
	 * Method m = (Method) methodIt.next(); String name = m.name(); if
	 * (!name.startsWith(GET_PROPERTY_PREFIX)) { continue; }
	 * 
	 * name = name.substring(GET_PROPERTY_PREFIX.length()); ObjectReference
	 * value = (ObjectReference) concept.invokeMethod(thread, m,
	 * Collections.EMPTY_LIST, ObjectReference.INVOKE_SINGLE_THREADED);
	 * value.disableCollection(); String propClass = getPropertyAtomClass(m); if
	 * (propClass != null) { node.appendPropertyAtomNode(thread, concept, name,
	 * value, propClass); } else { propClass = getPropertyArrayClass(m); if
	 * (propClass != null) { node.appendPropertyArrayNode(thread, concept, name,
	 * value, propClass); } } value.enableCollection(); } }
	 */

	/*
	 * TODO - WorkbenchBundle public static void
	 * fillTimeEventProperties(ThreadReference thread, ObjectReference event,
	 * EventTreeNode node) throws ClassNotLoadedException, InvocationException,
	 * IncompatibleThreadStateException, InvalidTypeException { WorkbenchBundle
	 * bundle = WorkbenchBundle.getBundle(); String closureName =
	 * bundle.getString("BEDebuggerAgendaTree.closure"); String intervalName =
	 * bundle.getString("BEDebuggerAgendaTree.interval"); String
	 * scheduledTimeName =
	 * bundle.getString("BEDebuggerAgendaTree.scheduledTime");
	 * 
	 * StringReference closure = timeEventGetClosure(thread, event,
	 * ObjectReference.INVOKE_SINGLE_THREADED); LongValue interval =
	 * timeEventGetInterval(thread, event,
	 * ObjectReference.INVOKE_SINGLE_THREADED); ObjectReference scheduledTime =
	 * timeEventGetScheduledTime(thread, event,
	 * ObjectReference.INVOKE_SINGLE_THREADED);
	 * 
	 * node.appendProperty(thread, event, closureName, "java.lang.String",
	 * closure); node.appendProperty(thread, event, intervalName, "long",
	 * interval); node.appendProperty(thread, event, scheduledTimeName,
	 * "java.util.Calendar", scheduledTime); }
	 */

	/*
	 * TODO - EventTreeNode public static void
	 * fillEventProperties(ThreadReference thread, ObjectReference event,
	 * EventTreeNode node) throws ClassNotLoadedException, InvocationException,
	 * IncompatibleThreadStateException, InvalidTypeException { ReferenceType
	 * eventType = event.referenceType(); List l = eventType.allFields();
	 * Iterator it = l.iterator(); while (it.hasNext()) { Field f = (Field)
	 * it.next(); String name = f.name(); if
	 * (name.startsWith(ModelNameUtil.MEMBER_PREFIX)) { name =
	 * name.substring(ModelNameUtil.MEMBER_PREFIX.length()); Value value =
	 * event.getValue(f); node.appendProperty(thread, event, name, f.typeName(),
	 * value); } }
	 * 
	 * // handle payload StringReference payload =
	 * simpleEventImplDebugPayloadToString(thread, event,
	 * ObjectReference.INVOKE_SINGLE_THREADED); node.appendPayload(payload); }
	 */

	/**
	 * @param eventThread
	 * @param conceptRef
	 * @return
	 * @throws DebugException
	 * @throws ClassNotLoadedException
	 */
	public static Map<String, Value> getProcessProperties(RuleDebugThread eventThread, ObjectReference conceptRef) throws DebugException,
			ClassNotLoadedException {
		synchronized (eventThread) {

			if (!implementsInterface(conceptRef, Concept.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Concept.class.getName() + " interface");
			}
			Map<String, Value> propertyMap = new LinkedHashMap<String, Value>();
			Method pm = findMethodBySignature((ClassType) conceptRef.referenceType(), "getProperties", "()[Lcom/tibco/cep/runtime/model/element/Property;");
			ArrayReference pval = (ArrayReference) eventThread.invokeMethod(null, conceptRef, pm, Collections.EMPTY_LIST, false);
			Iterator<Value> valueIter = pval.getValues().iterator();
			while (valueIter.hasNext()) {
				ObjectReference objRef = (ObjectReference) valueIter.next();
				ReferenceType obRefClazz = objRef.referenceType();
				Method gn = findMethodBySignature((ClassType) obRefClazz, "getName", "()Ljava/lang/String;");
				Value nm = eventThread.invokeMethod(null, objRef, gn, Collections.EMPTY_LIST, false);
				propertyMap.put(value2String(eventThread, nm), objRef);
			}
			return propertyMap;
		}
	}

	public static String getProcessUriFromBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType.name().equals("com.tibco.cep.bpmn.runtime.agent.StarterJobImpl")||
					refType.name().equals("com.tibco.cep.bpmn.runtime.agent.JobImpl"))  {
				Method mp = findMethodBySignature((ClassType) refType, "getJobContext", "()Lcom/tibco/cep/bpmn/runtime/model/JobContext;");
				Value pv = rdt.invokeMethod(null, thisObject, mp, Collections.EMPTY_LIST, false);
				Method m = findMethodBySignature((ClassType) ((ObjectReference) pv).referenceType(), "getDesignTimeProcessUri", "()Ljava/lang/String;");
				Value uri = rdt.invokeMethod(null, ((ObjectReference) pv), m, Collections.EMPTY_LIST, false);
				return value2String(rdt, uri);
			}

		}
		return null;
	}

	public static ObjectReference getProcessVariables(RuleDebugThread rdt, ObjectReference objRef) throws DebugException {
		synchronized (rdt) {
			ReferenceType refType = objRef.referenceType();
			if (refType.name().equals("com.tibco.cep.bpmn.runtime.agent.Process")) {
				Method mp = findMethodBySignature((ClassType) refType, "getJobContext", "()Lcom/tibco/cep/bpmn/runtime/model/JobContext;");
				Value pv = rdt.invokeMethod(null, objRef, mp, Collections.EMPTY_LIST, false);
				return (ObjectReference) pv;
			}
		}
		return null;
	}

	public static String getPropertyArrayClass(Field f) throws ClassNotLoadedException {
		Type type = f.type();
		return matchSuperClass(type, ABSTRACT_PROPERTY_ARRAY_IMPL_CLASS);
	}

	public static String getPropertyArrayClass(Method m) {
		String returnTypeName = m.returnTypeName();
		return (String) PROPERTY_ARRAY_INTERFACES2IMPL.get(returnTypeName);
	}

	/**
	 * @param threadReference
	 * @param omRef
	 * @return
	 */
	// public static String getObjectManagerType(RuleDebugThread
	// threadReference,
	// ObjectReference omRef) {
	// if (implementsInterface(omRef, ObjectBasedStore.class.getName())) {
	// return OM_TYPE_CACHE;
	// } else if (implementsInterface(omRef, PropertyBasedStore.class
	// .getName())) {
	// return OM_TYPE_DB;
	// }
	// return OM_TYPE_INMEMORY;
	// }

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomBoolean(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(propRef, PropertyAtomBoolean.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomBoolean.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getBoolean", "()Z");
			Value booleanVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return booleanVal;
		}
	}

	/**
	 * @param eventThread
	 * @param agentRef
	 * @return
	 * @throws DebugException
	 */
	// public static ObjectReference getLocalCache(RuleDebugThread eventThread,
	// ObjectReference agentRef) throws DebugException {
	// synchronized (eventThread) {
	// if (!implementsInterface(agentRef, InferenceAgentMBean.class.getName()))
	// {
	// throw new IllegalArgumentException(
	// "The object reference does not implement the "
	// + InferenceAgentMBean.class.getName()
	// + " interface");
	// }
	// Method getCache = findMethodBySignature((ClassType) agentRef
	// .referenceType(), "getLocalCache",
	// "()Lcom/tibco/cep/runtime/service/om/invm/LocalCache;");
	// Value cacheRef = eventThread.invokeMethod(null, agentRef, getCache,
	// Collections.EMPTY_LIST, false);
	// return (ObjectReference) cacheRef;
	// }
	// }

	/**
	 * @param eventThread
	 * @param cacheRef
	 * @param entityId
	 * @return
	 * @throws DebugException
	 */
	// public static int getLocalCacheVersion(RuleDebugThread eventThread,
	// ObjectReference cacheRef, LongValue entityId) throws DebugException {
	// synchronized (eventThread) {
	// if (!implementsInterface(cacheRef, LocalCache.class.getName())) {
	// throw new IllegalArgumentException(
	// "The object reference does not implement the "
	// + LocalCache.class.getName() + " interface");
	// }
	// Method getVersionMethod = findMethodBySignature((ClassType) cacheRef
	// .referenceType(), "getVersion", "(J)I");
	// ArrayList<Value> args = new ArrayList<Value>();
	// args.add(entityId);
	// Value hasEntity = eventThread.invokeMethod(null, cacheRef,
	// getVersionMethod, args, false);
	// return ((IntegerValue) hasEntity).intValue();
	// }
	// }

	/**
	 * @param eventThread
	 * @param cacheRef
	 * @param entityId
	 * @return
	 * @throws DebugException
	 */
	// public static boolean hasEntity(RuleDebugThread eventThread,
	// ObjectReference cacheRef, Value entityId) throws DebugException {
	// synchronized (eventThread) {
	//
	// if (!implementsInterface(cacheRef, LocalCache.class.getName())) {
	// throw new IllegalArgumentException(
	// "The object reference does not implement the "
	// + LocalCache.class.getName() + " interface");
	// }
	// Method hasEntityMethod = findMethodBySignature((ClassType) cacheRef
	// .referenceType(), "hasEntity", "(J)Z");
	// ArrayList<Value> args = new ArrayList<Value>();
	// args.add(entityId);
	// Value hasEntity = eventThread.invokeMethod(null, cacheRef,
	// hasEntityMethod, args, false);
	// return ((BooleanValue) hasEntity).booleanValue();
	// }
	// }

	public static String getPropertyAtomClass(Field f) throws ClassNotLoadedException {
		Type type = f.type();
		return matchSuperClass(type, ABSTRACT_PROPERTY_ATOM_IMPL_CLASS);
	}

	public static String getPropertyAtomClass(Method m) {
		String returnTypeName = m.returnTypeName();
		return (String) PROPERTY_ATOM_INTERFACES2IMPL.get(returnTypeName);
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomConceptId(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtomConcept.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomConcept.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getConceptId", "()J");
			Value conceptIdVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return conceptIdVal;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomDateTime(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(propRef, PropertyAtomDateTime.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomDateTime.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getDateTime", "()Ljava/util/Calendar;");
			Value dateTimeVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);

			return dateTimeVal;
		}
	}

	/**
	 * @param eventThread
	 * @param clusterEntityProviderRef
	 * @param entityId
	 * @return
	 * @throws DebugException
	 */
	// public static BooleanValue containsKey(RuleDebugThread eventThread,
	// ObjectReference clusterEntityProviderRef, Value entityId)
	// throws DebugException {
	// synchronized (eventThread) {
	//
	// if (!implementsInterface(clusterEntityProviderRef,
	// ClusterEntityProvider.class.getName())) {
	// throw new IllegalArgumentException(
	// "The object reference does not implement the "
	// + ClusterEntityProvider.class.getName()
	// + " interface");
	// }
	// /*
	// * ArrayList<Value> args = new ArrayList<Value>(); args.add(entityId);
	// * ClassType longRef = (ClassType)
	// * findClass(threadReference.virtualMachine(), Long.class.getName());
	// * Method valueOfMethod = findMethodBySignature(longRef, "valueOf",
	// * "(J)Ljava/lang/Long;"); ObjectReference entityLongRef =
	// * (ObjectReference) longRef.invokeMethod(threadReference,
	// * valueOfMethod, args, ObjectReference.INVOKE_SINGLE_THREADED);
	// */
	//
	// ArrayList<Value> args1 = new ArrayList<Value>();
	// args1.add(entityId);
	//
	// Method containsKey = findMethodBySignature(
	// (ClassType) clusterEntityProviderRef.referenceType(),
	// "containsKey", "(J)Z");
	// Value contains = eventThread.invokeMethod(null,
	// clusterEntityProviderRef, containsKey, args1, true);
	// return (BooleanValue) contains;
	// }
	// }

	/**
	 * @param eventThread
	 * @param clusterEntityProviderRef
	 * @return
	 * @throws DebugException
	 */
	// public static BooleanValue hasBackingStore(RuleDebugThread eventThread,
	// ObjectReference clusterEntityProviderRef) throws DebugException {
	// synchronized (eventThread) {
	//
	// if (!implementsInterface(
	// clusterEntityProviderRef,
	// com.tibco.cep.runtime.service.om.api.ClusterEntityProvider.class
	// .getName())) {
	// throw new IllegalArgumentException(
	// "The object reference does not implement the "
	// + ClusterEntityProvider.class.getName()
	// + " interface");
	// }
	// Method classMethod = findMethodBySignature(
	// (ClassType) clusterEntityProviderRef.referenceType(),
	// "hasBackingStore", "()Z");
	// Value hasBS = eventThread.invokeMethod(null, clusterEntityProviderRef,
	// classMethod, Collections.EMPTY_LIST, false);
	// return (BooleanValue) hasBS;
	// }
	// }

	/**
	 * @param eventThread
	 * @param entityId
	 * @return
	 * @throws DebugException
	 */
	// public static BooleanValue checkIfInBackingStore(
	// RuleDebugThread eventThread, Value entityId) throws DebugException {
	// synchronized (eventThread) {
	//
	// ReferenceType cacheCluserProviderMirror = findClass(
	// eventThread.getVM(), CacheClusterProvider.class.getName());
	// Method classMethod = findMethodBySignature(
	// (ClassType) cacheCluserProviderMirror, "getINSTANCE",
	// "()Lcom/tibco/cep/runtime/service/om/coherence/cluster/CacheClusterProvider;");
	// ObjectReference clusterInstRef = (ObjectReference) eventThread
	// .invokeMethod(((ClassType) cacheCluserProviderMirror), null,
	// classMethod, Collections.EMPTY_LIST, false);
	//
	// Method getCacheCluster = findMethodBySignature(
	// (ClassType) clusterInstRef, "getCacheCluster",
	// "()Lcom/tibco/cep/runtime/service/om/coherence/cluster/CacheCluster;");
	// ObjectReference cacheClusterRef = (ObjectReference) eventThread
	// .invokeMethod(null, clusterInstRef, getCacheCluster,
	// Collections.EMPTY_LIST, false);
	//
	// Method getObjectTableCache = findMethodBySignature(
	// (ClassType) cacheClusterRef.referenceType(),
	// "getObjectTableCache",
	// "()Lcom/tibco/cep/runtime/service/om/coherence/cluster/ObjectTableCache;");
	// ObjectReference tableCacheRef = (ObjectReference) eventThread
	// .invokeMethod(null, cacheClusterRef, getObjectTableCache,
	// Collections.EMPTY_LIST, false);
	//
	// Method containsKey = findMethodBySignature((ClassType) tableCacheRef,
	// "containsKey", "(Ljava/lang/Object;)Z");
	// ArrayList<Value> args = new ArrayList<Value>();
	// args.add(entityId);
	// Value isInBS = eventThread.invokeMethod(null, tableCacheRef,
	// containsKey, args, false);
	// return (BooleanValue) isInBS;
	// }
	// }

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomDouble(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtomDouble.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomDouble.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getDouble", "()D");
			Value doubleVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return doubleVal;
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomInt(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtomInt.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomInt.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getInt", "()I");
			Value intVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return intVal;
		}
	}

	// public static Value getEventPropertyValueByName(ThreadReference
	// threadReference, ObjectReference eventRef, String propertyName) throws
	// InvalidTypeException, ClassNotLoadedException,
	// IncompatibleThreadStateException, InvocationException {
	// if (!implementsInterface(eventRef, SimpleEvent.class.getName())) {
	// return null;
	// }
	//
	// Method classMethod = findMethodBySignature((ClassType)
	// eventRef.referenceType(), "getProperty",
	// "(Ljava/lang/String;)Ljava/lang/Object;");
	// ArrayList<Value> args = new ArrayList<Value>();
	// StringReference propertyNameRef =
	// threadReference.virtualMachine().mirrorOf(propertyName);
	// args.add(propertyNameRef);
	// Value propValue = eventRef.invokeMethod(threadReference, classMethod,
	// args, ObjectReference.INVOKE_SINGLE_THREADED);
	// if (propValue == null)
	// return null;
	// Value retValue = getPrimitiveValueFromObjectReference(threadReference,
	// (ObjectReference) propValue);
	// return retValue;
	// }

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomLong(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtomLong.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomLong.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getLong", "()J");
			Value longVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return longVal;
		}
	}

	// private static Value getPrimitiveValueFromObjectReference(ThreadReference
	// threadReference, ObjectReference objRef) throws InvalidTypeException,
	// ClassNotLoadedException, IncompatibleThreadStateException,
	// InvocationException {
	// ReferenceType type = objRef.referenceType();
	// Value primValue = null;
	// if (type.name().equals(Long.class.getName())) {
	// Method longMethod = findMethodBySignature((ClassType)
	// (objRef).referenceType(), "longValue", "()J");
	// primValue = (objRef).invokeMethod(threadReference, longMethod,
	// Collections.EMPTY_LIST, ObjectReference.INVOKE_SINGLE_THREADED);
	// }
	// else if (type.name().equals(Integer.class.getName())) {
	// Method intMethod = findMethodBySignature((ClassType)
	// (objRef).referenceType(), "intValue", "()I");
	// primValue = (objRef).invokeMethod(threadReference, intMethod,
	// Collections.EMPTY_LIST, ObjectReference.INVOKE_SINGLE_THREADED);
	// }
	// return primValue;
	// }

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static Value getPropertyAtomString(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {
			if (!implementsInterface(propRef, PropertyAtomString.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + PropertyAtomString.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getString", "()Ljava/lang/String;");
			Value stringVal = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return stringVal;
		}
	}

	/**
	 * @param threadReference
	 * @param type
	 * @return
	 */
	public static String getPropertyTypeString(RuleDebugThread eventThread, int type) {
		synchronized (eventThread) {

			try {
				ReferenceType RDFTypesMirror = findClass(eventThread.getVM(), RDFTypes.class.getName());
				Method classMethod = findMethodBySignature((ClassType) RDFTypesMirror, "getTypeString", "(I)Ljava/lang/String;");
				ArrayList<Value> args = new ArrayList<Value>();
				args.add(eventThread.getVM().mirrorOf(type));
				Value typeStrRef = eventThread.invokeMethod(((ClassType) RDFTypesMirror), null, classMethod, args, false);
				return value2String(eventThread, typeStrRef);
				// Field typeStringsField =
				// RDFTypesMirror.fieldByName("typeStrings");
				// Value types = RDFTypesMirror.getValue(typeStringsField);
				// if(types instanceof ArrayReference){
				// ArrayReference arrRef = (ArrayReference) types;
				// Value typeValue = null;
				// for(int i=0; i < arrRef.length();i++) {
				// if(i == type) {
				// typeValue = arrRef.getValue(i);
				// break;
				// }
				// }
				// return value2String(threadReference, typeValue);
				// }
			} catch (Exception e) {
				return e.getClass().getName() + ":" + e.getMessage();
			}
		}
	}

	/**
	 * 
	 * @param eventThread
	 * @param wmMirror
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getResolver(RuleDebugThread eventThread, ObjectReference wmMirror) throws DebugException {
		synchronized (eventThread) {

			ObjectReference resolverMirror;
			if (!implementsInterface(wmMirror, WorkingMemory.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + WorkingMemory.class.getName() + " interface");
			}
			Method getResolverMethod = findMethodBySignature((ClassType) wmMirror.referenceType(), "getResolver",
					"()Lcom/tibco/cep/kernel/core/rete/conflict/ConflictResolver;");
			Value confResolverValue = eventThread.invokeMethod(null, wmMirror, getResolverMethod, Collections.EMPTY_LIST, false);
			resolverMirror = (ObjectReference) confResolverValue;
			return resolverMirror;
		}
	}

	public static String getResourcePath(IResource resource) {
		if (resource != null && resource.exists()) {
			return resource.getFullPath().removeFileExtension().removeFirstSegments(1).makeAbsolute().toString();
		}
		return null;
	}

	/**
	 * 
	 * @param eventThread
	 * @param ruleMirror
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getRuleAction(RuleDebugThread eventThread, ObjectReference ruleMirror) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(ruleMirror, Rule.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Rule.class.getName() + " interface");
			}
			Method getActionsMethod = findMethodBySignature((ClassType) ruleMirror.referenceType(), "getActions", "()[Lcom/tibco/cep/kernel/model/rule/Action;");
			ArrayReference actions = (ArrayReference) eventThread.invokeMethod(null, ruleMirror, getActionsMethod, Collections.EMPTY_LIST, false);
			try {
				actions.disableCollection();
				List actionItemValues = actions.getValues();
				Iterator it = actionItemValues.iterator();
				while (it.hasNext()) {
					ObjectReference actionItemVal = (ObjectReference) it.next();
					if (actionItemVal != null && implementsInterface(actionItemVal, "com.tibco.cep.kernel.model.rule.Action")) {
						return actionItemVal;
					}
				}
			} finally {
				actions.enableCollection();
			}
			return null;
		}
	}

	/**
	 * 
	 * @param eventThread
	 * @param ruleMirror
	 * @return
	 * @throws DebugException
	 */
	public static String getRuleName(RuleDebugThread eventThread, ObjectReference ruleMirror) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(ruleMirror, Rule.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Rule.class.getName() + " interface");
			}
			Method classMethod = findMethodBySignature((ClassType) ruleMirror.referenceType(), "getName", "()Ljava/lang/String;");
			Value rule = eventThread.invokeMethod(null, ruleMirror, classMethod, Collections.EMPTY_LIST, false);
			return ((StringReference) rule).value();
		}
	}

	public static ObjectReference getRuleRuntime(RuleDebugThread rdt, ObjectReference rspInstance) throws DebugException {
		synchronized (rdt) {

			if (!implementsInterface((ClassType) rspInstance.referenceType(), RuleServiceProvider.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + RuleServiceProvider.class.getName());
			}
			ReferenceType refClazz = rspInstance.referenceType();
			Method runtimeMethod = findMethodBySignature((ClassType) refClazz, "getRuleRuntime", "()Lcom/tibco/cep/runtime/session/RuleRuntime;");
			Value runtimeInstance = rdt.invokeMethod(null, rspInstance, runtimeMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) runtimeInstance;
		}
	}

	/**
	 * Get Rule Service Provider Object Reference
	 * 
	 * @param rdt
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getRuleServiceProvider(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference rsp = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			return rsp;
		}
	}

	/**
	 * @param eventThread
	 * @param ruleSessionRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getRuleServiceProvider(RuleDebugThread eventThread, ObjectReference ruleSessionRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(ruleSessionRef, RuleSession.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + RuleSession.class.getName() + " interface");
			}
			Method getRSP = findMethodBySignature((ClassType) ruleSessionRef.referenceType(), "getRuleServiceProvider",
					"()Lcom/tibco/cep/runtime/session/RuleServiceProvider;");
			Value rspRef = eventThread.invokeMethod(null, ruleSessionRef, getRSP, Collections.EMPTY_LIST, false);
			return (ObjectReference) rspRef;
		}
	}

	public static ObjectReference getRuleServiceProviderFromDebuggerService(RuleDebugThread rdt, ObjectReference debuggerServiceInstance) throws DebugException {
		synchronized (rdt) {

			if (!((ObjectReference) debuggerServiceInstance).referenceType().name().equals(DebuggerService.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + DebuggerService.class.getName());
			}
			ReferenceType refClazz = debuggerServiceInstance.referenceType();
			Method rspMethod = findMethodBySignature((ClassType) refClazz, "getRuleServiceProvider", "()Lcom/tibco/cep/runtime/session/RuleServiceProvider;");
			Value rspInstance = rdt.invokeMethod(null, debuggerServiceInstance, rspMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) rspInstance;
		}
	}

	public static ObjectReference getRuleServiceProviderFromManager(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference rspManagerInstance = getRuleServiceProviderManager(rdt);
			if (!((ObjectReference) rspManagerInstance).referenceType().name().equals(RuleServiceProviderManager.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + RuleServiceProviderManager.class.getName());
			}
			ReferenceType refClazz = rspManagerInstance.referenceType();
			Method rspMethod = findMethodBySignature((ClassType) refClazz, "getFirstProvider", "()Lcom/tibco/cep/runtime/session/RuleServiceProvider;");
			Value rspInstance = rdt.invokeMethod(null, rspManagerInstance, rspMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) rspInstance;
		}
	}

	public static ObjectReference getRuleServiceProviderManager(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {

			ReferenceType rspmType = findClass(rdt.getVM(), RuleServiceProviderManager.class.getName());
			Method getInstanceMethod = findMethodBySignature((ClassType) rspmType, "getInstance",
					"()Lcom/tibco/cep/runtime/session/RuleServiceProviderManager;");
			ObjectReference rspmInstance = (ObjectReference) rdt.invokeMethod((ClassType) rspmType, null, getInstanceMethod, Collections.EMPTY_LIST, false);
			return rspmInstance;
		}
	}

	public static String getRuleSessionName(RuleDebugThread rdt, ObjectReference rsInstance) throws DebugException {
		synchronized (rdt) {
			if (!implementsInterface((ClassType) rsInstance.referenceType(), RuleSession.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + RuleSession.class.getName());
			}
			ReferenceType refClazz = rsInstance.referenceType();
			Method getNameMethod = findMethodBySignature((ClassType) refClazz, "getName", "()Ljava/lang/String;");
			Value nameInstance = rdt.invokeMethod(null, rsInstance, getNameMethod, Collections.EMPTY_LIST, false);
			return ((StringReference) nameInstance).value();
		}
	}

	public static String[] getRuleSessionNames(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference rspInstance = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			ObjectReference runtimeInstance = getRuleRuntime(rdt, rspInstance);
			ReferenceType refClazz = runtimeInstance.referenceType();
			Method ruleSessionsMetod = findMethodBySignature((ClassType) refClazz, "getRuleSessions", "()[Lcom/tibco/cep/runtime/session/RuleSession;");
			ArrayReference ruleSessions = (ArrayReference) rdt.invokeMethod(null, runtimeInstance, ruleSessionsMetod, Collections.EMPTY_LIST, false);
			List<String> sessionNames = new ArrayList<String>();
			// if (isMac()) {
			Iterator<Value> valueIter = ruleSessions.getValues().iterator();
			while (valueIter.hasNext()) {
				sessionNames.add(getRuleSessionName(rdt, (ObjectReference) valueIter.next()));
			}
			// } else {
			// for(Value session:ruleSessions.getValues()) {
			// sessionNames.add(getRuleSessionName(rdt, (ObjectReference)
			// session));
			// }
			// }
			return sessionNames.toArray(new String[sessionNames.size()]);
		}
	}

	/**
	 * @param rdt
	 * @return
	 * @throws DebugException
	 */
	public static Map<String, String> getRuleSessionsClassMap(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference rspInstance = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			ObjectReference runtimeInstance = getRuleRuntime(rdt, rspInstance);
			ReferenceType refClazz = runtimeInstance.referenceType();
			Method ruleSessionsMetod = findMethodBySignature((ClassType) refClazz, "getRuleSessions", "()[Lcom/tibco/cep/runtime/session/RuleSession;");
			ArrayReference ruleSessions = (ArrayReference) rdt.invokeMethod(null, runtimeInstance, ruleSessionsMetod, Collections.EMPTY_LIST, false);
			Map<String, String> sessionMap = new HashMap<String, String>();
			Iterator<Value> valueIter = ruleSessions.getValues().iterator();
			while (valueIter.hasNext()) {
				ObjectReference objRef = (ObjectReference) valueIter.next();
				ReferenceType obRefClazz = objRef.referenceType();
				String rs = getRuleSessionName(rdt, objRef);
				sessionMap.put(rs, obRefClazz.name());
				// if
				// (obRefClazz.signature().equals("Lcom/tibco/cep/bpmn/runtime/agent/ProcessRuleSession;"))
				// {
				// sessionMap.put(rs, true);
				// } else {
				// sessionMap.put(rs, false);
				// }
			}
			return sessionMap;
		}
	}

	private static ObjectReference getSmapContentProvider(RuleDebugThread rdt, ObjectReference projectInstance) throws DebugException {
		synchronized (rdt) {

			if (!implementsInterface((ClassType) projectInstance.referenceType(), DeployedProject.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + DeployedProject.class.getName());
			}
			ReferenceType refClazz = projectInstance.referenceType();
			Method runtimeMethod = findMethodBySignature((ClassType) refClazz, "getSMapContenProvider", "()Lcom/tibco/cep/repo/provider/SMapContentProvider;");
			Value smapContentProviderInstance = rdt.invokeMethod(null, projectInstance, runtimeMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) smapContentProviderInstance;
		}
	}

	private static ArrayReference getSmapData(RuleDebugThread rdt, ObjectReference smapContenProviderInstance, String smapUri) throws DebugException {
		synchronized (rdt) {
			ClassType smapContenProviderClassType = (ClassType) smapContenProviderInstance.referenceType();
			if (!implementsInterface(smapContenProviderClassType, SMapContentProvider.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + SMapContentProvider.class.getName());
			}
			ReferenceType refClazz = smapContenProviderInstance.referenceType();
			Method getResourceAsByteArrayMethod = findMethodBySignature((ClassType) refClazz, "getResourceAsByteArray", "(Ljava/lang/String;)[B");
			List<Value> args = new ArrayList<Value>();
			args.add(rdt.getVM().mirrorOf(smapUri));
			smapContenProviderInstance.disableCollection();
			Value arrayVal = rdt.invokeMethod(null, smapContenProviderInstance, getResourceAsByteArrayMethod, args, false);
			return (ArrayReference) arrayVal;
		}
	}

	public static List<byte[]> getSMapData(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference rspInstance = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			return getSMapData(rdt, rspInstance);
		}
	}

	public static List<byte[]> getSMapData(RuleDebugThread rdt, ObjectReference rspInstance) throws DebugException {
		synchronized (rdt) {

			List<byte[]> smapList = new ArrayList<byte[]>();
			ObjectReference projectInstance = getDeployedProject(rdt, rspInstance);
			ObjectReference smapContenProviderInstance = getSmapContentProvider(rdt, projectInstance);
			try {
				smapContenProviderInstance.disableCollection();
				if (smapContenProviderInstance != null) {
					ArrayReference smapUriArrRef = getSmapURIArray(rdt, smapContenProviderInstance);

					// this blocked changed so it can work on the Mac:
					Iterator<Value> valueIter = smapUriArrRef.getValues().iterator();
					while (valueIter.hasNext()) {
						Value smapURIVal = (Value) valueIter.next();
						String smapUri = ((StringReference) smapURIVal).value();
						ArrayReference mapDataRef = getSmapData(rdt, smapContenProviderInstance, smapUri);
						try {
							mapDataRef.disableCollection();
							if (mapDataRef != null) {
								byte[] data = new byte[mapDataRef.length()];
								for (int i = 0; i < mapDataRef.length(); i++) {
									ByteValue bVal = (ByteValue) mapDataRef.getValue(i);
									data[i] = bVal.value();
								}
								smapList.add(data);
							}
						} finally {
							mapDataRef.enableCollection();
						}
					}

					// Commented out and replaced with block above, because this
					// does not work on the Mac:

					// for(Value smapURIVal:smapUriArrRef.getValues()) {
					// String smapUri = ((StringReference)smapURIVal).value();
					// ArrayReference mapDataRef =
					// getSmapData(rdt,smapContenProviderInstance,smapUri);
					// try {
					// mapDataRef.disableCollection();
					// if(mapDataRef != null) {
					// byte[] data = new byte[mapDataRef.length()];
					// for(int i=0; i < mapDataRef.length(); i++){
					// ByteValue bVal = (ByteValue) mapDataRef.getValue(i);
					// data[i] = bVal.value();
					// }
					// smapList.add(data);
					// }
					// } finally {
					// mapDataRef.enableCollection();
					// }
					// }
				}
			} finally {
				smapContenProviderInstance.enableCollection();
			}
			return smapList;
		}
	}

	private static ArrayReference getSmapURIArray(RuleDebugThread rdt, ObjectReference smapContenProviderInstance) throws DebugException {
		synchronized (rdt) {
			ClassType smapContenProviderClassType = (ClassType) smapContenProviderInstance.referenceType();
			if (!implementsInterface(smapContenProviderClassType, SMapContentProvider.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + SMapContentProvider.class.getName());
			}
			ReferenceType refClazz = smapContenProviderInstance.referenceType();
			Method getSMapContenProviderMethod = findMethodBySignature((ClassType) refClazz, "getAllResourceURI", "()Ljava/util/Collection;");
			Value collectionObjRef = rdt.invokeMethod(null, smapContenProviderInstance, getSMapContenProviderMethod, Collections.EMPTY_LIST, false);
			return getArrayReference(rdt, (ObjectReference) collectionObjRef);
		}
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getStateMachineConcept(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(propRef, Property.PropertyStateMachine.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + Property.PropertyStateMachine.class.getName()
						+ " interface");
			}

			Method classMethod = findMethodBySignature((ClassType) propRef.referenceType(), "getStateMachineConcept",
					"()Lcom/tibco/cep/runtime/model/element/StateMachineConcept;");
			Value nameRef = eventThread.invokeMethod(null, propRef, classMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) nameRef;
		}
	}

	/**
	 * @param eventThread
	 * @param smRef
	 * @return
	 * @throws DebugException
	 * @throws ClassNotLoadedException
	 */
	public static HashMap<Field, Value> getStateMachineStates(RuleDebugThread eventThread, ObjectReference smRef) throws DebugException,
			ClassNotLoadedException {
		synchronized (eventThread) {

			HashMap<Field, Value> propmap = new HashMap<Field, Value>();
			if (!implementsInterface((ClassType) smRef.referenceType(), StateMachineConcept.class.getName()))
				return propmap;

			List<Field> flds = smRef.referenceType().visibleFields();
			for (Field f : flds) {
				if (f.name().startsWith("$2z") && implementsInterface((ClassType) f.type(), Property.class.getName())) {
					Value v = smRef.getValue(f);
					if (v == null) {
						Method method = DebuggerSupport.findMethodByName(smRef.referenceType(), "get" + f.name());
						// Method classMethod =
						// findMethodBySignature((ClassType)
						// propRef.referenceType(), "getName",
						// "()Ljava/lang/String;");
						if (method == null)
							continue;
						v = eventThread.invokeMethod(null, smRef, method, Collections.EMPTY_LIST, false);
					}
					propmap.put(f, v);
				}
			}
			return propmap;
		}
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static String getStateName(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "getName", "()Ljava/lang/String;");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return value2String(eventThread, activeRef);
		}
	}

	public static String getProcessTaskIdFromBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType instanceof ClassType && implementsInterface((ClassType) refType, "com.tibco.cep.bpmn.runtime.activity.Task")) {
				Method m = findMethodBySignature((ClassType) refType, "getName", "()Ljava/lang/String;");
				Value taskId = rdt.invokeMethod(null, thisObject, m, Collections.EMPTY_LIST, false);
				return value2String(rdt, taskId);
			}
			
		}
		return null;
	}
	
	public static int getProcessTaskUniqueIdFromBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType instanceof ClassType && implementsInterface((ClassType) refType, "com.tibco.cep.bpmn.runtime.activity.Task")) {
				Method m = findMethodBySignature((ClassType) refType, "getUniqueId", "()I");
				Value value = rdt.invokeMethod(null, thisObject, m, Collections.EMPTY_LIST, false);
				if (value instanceof IntegerValue) {
					return ((IntegerValue) value).intValue();
				}
			}
			
		}
		return -1;
	}
	
	public static String getRuleFunctionFromTaskBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType instanceof ClassType && refType.name().equals("com.tibco.cep.bpmn.runtime.activity.tasks.RuleFunctionTask")) {
				Method m = findMethodBySignature((ClassType) refType, "getRulefunction", "()Lcom/tibco/cep/designtime/model/rule/RuleFunction;");
				ObjectReference ruleFunctionObj = (ObjectReference) rdt.invokeMethod(null, thisObject, m, Collections.EMPTY_LIST, false);
				Method methodGetFullPath = findMethodBySignature((ClassType) ruleFunctionObj.referenceType(), "getFullPath", "()Ljava/lang/String;");
				Value fullPath = rdt.invokeMethod(null, ruleFunctionObj, methodGetFullPath, Collections.EMPTY_LIST, false);
				return value2String(rdt, fullPath);
			}

		}
		return null;
	}
	
	public static List<ObjectReference> getOutgoingTasksFromBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		List<ObjectReference> taskObjs = new ArrayList<ObjectReference>();
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType instanceof ClassType && implementsInterface((ClassType) refType, "com.tibco.cep.bpmn.runtime.activity.Task")) {
				List<ObjectReference> transitionObjs = getOutgoingTransitionsFromBreakpoint(rdt, thisObject);
				for(ObjectReference transitionObj:transitionObjs) {
					Method m = findMethodBySignature((ClassType) transitionObj.referenceType(), "toTask", "()Lcom/tibco/cep/bpmn/runtime/activity/Task;");
					ObjectReference taskObj = (ObjectReference) rdt.invokeMethod(null, transitionObj, m, Collections.EMPTY_LIST, false);
					taskObjs.add(taskObj);
				}
			}

		}
		return taskObjs;
	}
	
	public static List<ObjectReference> getOutgoingTransitionsFromBreakpoint(RuleDebugThread rdt, ObjectReference thisObject) throws DebugException {
		List<ObjectReference> transitionObjs = new ArrayList<ObjectReference>();
		synchronized (rdt) {
			ReferenceType refType = thisObject.referenceType();
			if (refType instanceof ClassType && implementsInterface((ClassType) refType, "com.tibco.cep.bpmn.runtime.activity.Task")) {
				Method m = findMethodBySignature((ClassType) refType, "getOutgoingTransitions", "()[Lcom/tibco/cep/bpmn/runtime/activity/Transition;");
				ArrayReference transitions = (ArrayReference) rdt.invokeMethod(null, thisObject, m, Collections.EMPTY_LIST, false);
				try {
					transitions.disableCollection();
					for (Object v : transitions.getValues()) {
						if (v instanceof ObjectReference) {
							ObjectReference entryObj = (ObjectReference) v;
							transitionObjs.add(entryObj);
						}
					}

				} finally {
					transitions.enableCollection();
				}
			}

		}
		return transitionObjs;
	}

	public static Map<String, String> getTaskRegistryData(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference rspInstance = getTaskRegistryFromDebuggerService(rdt, dsInstance);
			return getTaskRegistryData(rdt, rspInstance);
		}
	}

	public static Map<String, String> getTaskRegistryData(RuleDebugThread rdt, ObjectReference trInstance) throws DebugException {
		if (trInstance != null) {
			synchronized (rdt) {
				if (!trInstance.referenceType().name().equals("com.tibco.cep.bpmn.runtime.activity.TaskRegistry")) {
					throw new IllegalArgumentException("The value type is not com.tibco.cep.bpmn.runtime.activity.TaskRegistry");
				}
				Map<String, String> trMap = new HashMap<String, String>();
				ReferenceType mapEntryClass = findClass(rdt.getVM(), "java.util.Map$Entry");
				Method methodGetKey = findMethodBySignature((InterfaceType) mapEntryClass, "getKey", "()Ljava/lang/Object;");
				Method methodGetValue = findMethodBySignature((InterfaceType) mapEntryClass, "getValue", "()Ljava/lang/Object;");

				try {
					trInstance.disableCollection();
					Method getTypeRegistryMethod = findMethodBySignature((ClassType) trInstance.referenceType(), "getTypeRegistry", "()Ljava/util/Collection;");
					Value collectionObjRef = rdt.invokeMethod(null, trInstance, getTypeRegistryMethod, Collections.EMPTY_LIST, false);
					ArrayReference entryArray = getArrayReference(rdt, (ObjectReference) collectionObjRef);
					try {
						entryArray.disableCollection();
						for (Object v : entryArray.getValues()) {
							if (v instanceof ObjectReference) {
								ObjectReference entryObj = (ObjectReference) v;
								Value key = rdt.invokeMethod(null, entryObj, methodGetKey, Collections.EMPTY_LIST, false);
								Value val = rdt.invokeMethod(null, entryObj, methodGetValue, Collections.EMPTY_LIST, false);
								trMap.put(value2String(rdt, key), value2String(rdt, val));
							}
						}

					} finally {
						entryArray.enableCollection();
					}
				} finally {
					trInstance.enableCollection();
				}
				return trMap;
			}
		}
		return Collections.EMPTY_MAP;
	}

	public static ObjectReference getTaskRegistryFromDebuggerService(RuleDebugThread rdt, ObjectReference debuggerServiceInstance) throws DebugException {
		synchronized (rdt) {

			if (!((ObjectReference) debuggerServiceInstance).referenceType().name().equals(DebuggerService.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + DebuggerService.class.getName());
			}
			ReferenceType refClazz = debuggerServiceInstance.referenceType();
			Method rspMethod = findMethodBySignature((ClassType) refClazz, "getTaskTypeRegistry", "()Ljava/lang/Object;");
			Value trInstance = rdt.invokeMethod(null, debuggerServiceInstance, rspMethod, Collections.EMPTY_LIST, false);
			return (ObjectReference) trInstance;
		}
	}

	/**
	 * @param vm
	 * @param threadUniqueId
	 * @return
	 */
	public static ThreadReference getThreadReferenceForUniqueID(VirtualMachine vm, long threadUniqueId) {
		List<ThreadReference> allthreads = vm.allThreads();
		for (ThreadReference tr : allthreads) {
			ObjectReference or = tr;
			if (or.uniqueID() == threadUniqueId) {
				return tr;
			}
		}
		return null;
	}

	/**
	 * @param eventThread
	 * @param rspRef
	 * @return
	 * @throws DebugException
	 */
	public static ArrayReference getTypeDescriptorArrayForScorecards(RuleDebugThread eventThread, ObjectReference rspRef) throws DebugException {
		synchronized (eventThread) {

			Method getClassLoaderMethod = findMethodBySignature((ClassType) rspRef.referenceType(), "getClassLoader", "()Ljava/lang/ClassLoader;");
			ObjectReference classloaderRef = (ObjectReference) eventThread.invokeMethod(null, rspRef, getClassLoaderMethod, Collections.EMPTY_LIST, false);

			Method classMethod = findMethodBySignature((ClassType) classloaderRef.referenceType(), "getTypeDescriptorArray",
					"(I)[Lcom/tibco/cep/runtime/model/TypeManager$TypeDescriptor;");
			ArrayList<Value> args = new ArrayList<Value>();
			IntegerValue arrayIndexRef = eventThread.getVM().mirrorOf(TypeManager.TYPE_NAMEDINSTANCE);
			args.add(arrayIndexRef);
			Value valueRef = eventThread.invokeMethod(null, classloaderRef, classMethod, args, false);
			return (ArrayReference) valueRef;
		}
	}

	/**
	 * @param eventThread
	 * @param ruleServiceProviderRef
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getTypeManager(RuleDebugThread eventThread, ObjectReference ruleServiceProviderRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(ruleServiceProviderRef, RuleServiceProvider.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + RuleServiceProvider.class.getName() + " interface");
			}
			Method getTypeMgr = findMethodBySignature((ClassType) ruleServiceProviderRef.referenceType(), "getTypeManager",
					"()Lcom/tibco/cep/runtime/model/TypeManager;");
			Value tmRef = eventThread.invokeMethod(null, ruleServiceProviderRef, getTypeMgr, Collections.EMPTY_LIST, false);
			return (ObjectReference) tmRef;
		}
	}

	/**
	 * @param eventThread
	 * @param typeDescRef
	 * @return
	 * @throws DebugException
	 */
	public static StringReference getUri(RuleDebugThread eventThread, ObjectReference typeDescRef) throws DebugException {
		synchronized (eventThread) {

			Method classMethod = findMethodBySignature((ClassType) typeDescRef.referenceType(), "getURI", "()Ljava/lang/String;");
			Value uriRef = eventThread.invokeMethod(null, typeDescRef, classMethod, Collections.EMPTY_LIST, false);
			return (StringReference) uriRef;
		}
	}

	/**
	 * @param eventThread
	 * @param tmRef
	 * @param resUriRef
	 * @return
	 * @throws DebugException
	 */
	public static StringReference getUri(RuleDebugThread eventThread, ObjectReference tmRef, ReferenceType resUriRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface(tmRef, TypeManager.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + TypeManager.class.getName() + " interface");
			}
			Method getTypeDesc = findMethodBySignature((ClassType) tmRef.referenceType(), "getTypeDescriptor",
					"(Ljava/lang/Class;)Lcom/tibco/cep/runtime/model/TypeManager$TypeDescriptor;");
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(resUriRef.classObject());
			Value typeDescRef = eventThread.invokeMethod(null, tmRef, getTypeDesc, args, false);
			if (typeDescRef == null)
				return null;
			Method getUri = findMethodBySignature((ClassType) ((ObjectReference) typeDescRef).referenceType(), "getURI", "()Ljava/lang/String;");
			Value uriRef = eventThread.invokeMethod(null, ((ObjectReference) typeDescRef), getUri, Collections.EMPTY_LIST, false);
			return (StringReference) uriRef;
		}
	}

	public static Map<String, ObjectReference> getVariables(RuleDebugThread rdt, ObjectReference objRef) throws DebugException {
		Map<String, ObjectReference> vars = new HashMap<String, ObjectReference>();
		synchronized (rdt) {
			ReferenceType refType = objRef.referenceType();
			if (refType.name().equals("com.tibco.cep.bpmn.runtime.utils.Variables")) {
				Method mp = findMethodBySignature((ClassType) refType, "asMap", "()Ljava/util/Map;");
				Value mpVal = rdt.invokeMethod(null, objRef, mp, Collections.EMPTY_LIST, false);
				ReferenceType mapClass = findClass(rdt.getVM(), "java.util.Map");
				Method es = findMethodBySignature((InterfaceType) mapClass, "entrySet", "()Ljava/util/Set;");
				Value esVal = rdt.invokeMethod(null, (ObjectReference) mpVal, es, Collections.EMPTY_LIST, false);
				ReferenceType mapEntryClass = findClass(rdt.getVM(), "java.util.Map$Entry");
				Method methodGetKey = findMethodBySignature((InterfaceType) mapEntryClass, "getKey", "()Ljava/lang/Object;");
				Method methodGetValue = findMethodBySignature((InterfaceType) mapEntryClass, "getValue", "()Ljava/lang/Object;");
				ObjectReference esInstance = (ObjectReference) esVal;
				try {
					esInstance.disableCollection();
					ArrayReference entryArray = getArrayReference(rdt, (ObjectReference) esInstance);
					try {
						entryArray.disableCollection();
						if(entryArray != null & entryArray.length()!= 0){
							for (Object v : entryArray.getValues()) {
								if (v instanceof ObjectReference) {
									ObjectReference entryObj = (ObjectReference) v;
									Value key = rdt.invokeMethod(null, entryObj, methodGetKey, Collections.EMPTY_LIST, false);
									Value val = rdt.invokeMethod(null, entryObj, methodGetValue, Collections.EMPTY_LIST, false);
									vars.put(value2String(rdt, key), (ObjectReference) val);
								}
							}
						}else {
							System.out.println("entryArray for debugger is empty");
						}
					} finally {
						entryArray.enableCollection();
					}
				} finally {
					esInstance.enableCollection();
				}

				return vars;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param eventThread
	 * @param ruleSession
	 * @return
	 * @throws DebugException
	 */
	public static ObjectReference getWorkingMemory(RuleDebugThread eventThread, ObjectReference ruleSession) throws DebugException {
		synchronized (eventThread) {

			ObjectReference wmMirror;
			if (!implementsInterface(ruleSession, RuleSession.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + RuleSession.class.getName() + " interface");
			}
			ObjectReference ruleSessionMirror = ruleSession;
			Method getWMMethod = findMethodBySignature((ClassType) ruleSessionMirror.referenceType(), "getWorkingMemory",
					"()Lcom/tibco/cep/kernel/model/knowledgebase/WorkingMemory;");
			Value defRspValue = eventThread.invokeMethod(null, ruleSessionMirror, getWMMethod, Collections.EMPTY_LIST, false);
			wmMirror = (ObjectReference) defRspValue;
			return wmMirror;
		}
	}

	public static boolean implementsInterface(ClassType type, String interfaceName) {
		List l = type.allInterfaces();
		Iterator it = l.iterator();
		while (it.hasNext()) {
			InterfaceType iType = (InterfaceType) it.next();
			String iTypeName = iType.name();
			if (interfaceName.equals(iTypeName)) {
				return true;
			}
		}

		return false;
	}

	public static boolean implementsInterface(ObjectReference object, String interfaceName) {
		ReferenceType rt = object.referenceType();
		if (!(rt instanceof ClassType)) {
			return false;
		}

		ClassType type = (ClassType) object.referenceType();
		return implementsInterface(type, interfaceName);
	}

	/**
	 * 
	 * @param methodName
	 * @param methodSignature
	 * @param thread
	 * @param object
	 * @param arguments
	 * @param flags
	 * @return
	 */
	public static Value invokeConcreteMethod(String methodName, String methodSignature, RuleDebugThread thread, ObjectReference object, List arguments,
			int flags) {
		synchronized (thread) {
			try {
				ClassType cls = (ClassType) object.type();
				Method method = cls.concreteMethodByName(methodName, methodSignature);
				if (method == null) {
					return null;
				}
				return thread.invokeMethod(null, object, method, arguments, flags == ObjectReference.INVOKE_NONVIRTUAL);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * Checkk if be engine is running with Cache Server mode
	 * 
	 * @param rdt
	 * @return
	 * @throws DebugException
	 */
	public static boolean isCacheServerMode(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ObjectReference dsInstance = getDebuggerServiceInstance(rdt);
			ObjectReference wrapperRspInstance = getRuleServiceProviderFromDebuggerService(rdt, dsInstance);
			Method isCacheServerModeMethod = findMethodBySignature((ClassType) wrapperRspInstance.referenceType(), "isCacheServerMode", "()Z");
			Value isCacheServerInstance = rdt.invokeMethod(null, wrapperRspInstance, isCacheServerModeMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) isCacheServerInstance).value();
		}
	}

	protected static boolean isCalenderType(ObjectReference objectReference) {
		boolean baseCalendar = (objectReference.referenceType().name().equals(Calendar.class.getName()));
		boolean superCalendar = false;
		if (objectReference.referenceType() instanceof ClassType) {
			ClassType ct = (ClassType) objectReference.referenceType();
			superCalendar = ct.superclass().name().equals(Calendar.class.getName());
		}
		return (baseCalendar || superCalendar);
	}

	public static boolean isCodeGenType(ReferenceType refType) {
		if (refType.name().startsWith("be.gen")) {
			return true;
		}
		return false;

	}

	public static boolean isConcept(ObjectReference object) {
		return implementsInterface(object, CONCEPT_INTERFACE_INTERFACE);
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 */
	public static boolean isConceptPropertyAtom(RuleDebugThread eventThread, ObjectReference propRef) {
		if (implementsInterface(propRef, PropertyAtom.class.getName())) {
			return true;
		}
		return false;
	}

	/**
	 * @param eventThread
	 * @param propRef
	 * @return
	 */
	public static boolean isConceptPropertyStateMachine(RuleDebugThread eventThread, ObjectReference propRef) {
		if (implementsInterface(propRef, Property.PropertyStateMachine.class.getName())) {
			return true;
		}
		return false;
	}

	public static boolean isCondition(ClassType cType) {
		return implementsInterface(cType, CONDITION_INTERFACE);
	}

	public static boolean isEntity(ObjectReference object) {
		return implementsInterface(object, ENTITY_INTERFACE_INTERFACE);
	}

	public static boolean isEvent(ObjectReference object) {
		return implementsInterface(object, EVENT_INTERFACE_INTERFACE);
	}

	/**
	 * Returns if the JDI version being used is greater than or equal to the
	 * given version (major, minor).
	 * 
	 * @param version
	 * @return boolean
	 */
	public static boolean isJdiVersionGreaterThanOrEqual(int[] version) {
		int[] runningVersion = getJDIVersion();
		return runningVersion[0] > version[0] || (runningVersion[0] == version[0] && runningVersion[1] >= version[1]);
	}

	public static boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		// Mac
		return (os.indexOf("mac") >= 0);
	}

	public static boolean isProcessContext(ObjectReference object) {
		if (object == null) {
			return false;
		}
		return implementsInterface(object, "com.tibco.cep.bpmn.runtime.agent.Job");
	}

	public static boolean isProcessVariables(ObjectReference object) {
		return object.referenceType().name().equals(PROCESS_VARIABLES_INTERFACE_INTERFACE);
	}

	public static boolean isRuleFunction(ClassType cType) {
		return implementsInterface(cType, RULEFUNCTION_INTERFACE);
	}

	public static boolean isScorecard(ObjectReference concept) {
		return implementsInterface(concept, NAMED_INSTANCE_INTERFACE);
	}

	/**
	 * @param eventRef
	 * @return
	 */
	public static boolean isSimpleEvent(ObjectReference eventRef) {
		if (implementsInterface(eventRef, SimpleEvent.class.getName())) {
			return true;
		}
		return false;
	}

	public static boolean isSmartType(ReferenceType refType) {
		if (isCodeGenType(refType)) {
			ClassType clzType = (ClassType) refType;
			List<InterfaceType> ifTypes = clzType.interfaces();
			for (InterfaceType ifType : ifTypes) {
				if (ifType.name().equals("com.tibco.cep.runtime.service.debug.SmartStepInto")) {
					return true;
				}
			}

		}
		return false;
	}

	public static boolean isSolaris() {
		String os = System.getProperty("os.name").toLowerCase();
		// Solaris
		return (os.indexOf("sunos") >= 0);
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateActive(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "isActive", "()Z");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) activeRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateAmbiguous(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "isAmbiguous", "()Z");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) activeRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateComplete(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "isComplete", "()Z");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) activeRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateExited(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "isExited", "()Z");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) activeRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateMachineClosed(RuleDebugThread eventThread, ObjectReference smRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface((ClassType) smRef.referenceType(), StateMachineConcept.class.getName()))
				return false;

			Method classMethod = findMethodBySignature((ClassType) smRef.referenceType(), "isMachineClosed", "()Z");
			Value closedRef = eventThread.invokeMethod(null, smRef, classMethod, Collections.EMPTY_LIST, true);
			return ((BooleanValue) closedRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateMachineStarted(RuleDebugThread eventThread, ObjectReference smRef) throws DebugException {
		synchronized (eventThread) {

			if (!implementsInterface((ClassType) smRef.referenceType(), StateMachineConcept.class.getName()))
				return false;

			Method classMethod = findMethodBySignature((ClassType) smRef.referenceType(), "isMachineStarted", "()Z");
			Value startedRef = eventThread.invokeMethod(null, smRef, classMethod, Collections.EMPTY_LIST, true);
			return ((BooleanValue) startedRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateReady(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "isReady", "()Z");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) activeRef).booleanValue();
		}
	}

	/**
	 * @param eventThread
	 * @param smStateRef
	 * @return
	 * @throws DebugException
	 */
	public static boolean isStateTimeoutSet(RuleDebugThread eventThread, ObjectReference smStateRef) throws DebugException {
		synchronized (eventThread) {

			if (!((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineCompositeState.class.getName())
					&& !((ClassType) smStateRef.referenceType()).superclass().name().equals(PropertyStateMachineState.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the a State Machine State interface");
			}

			Method classMethod = findMethodBySignature((ClassType) smStateRef.referenceType(), "isTimeoutSet", "()Z");
			Value activeRef = eventThread.invokeMethod(null, smStateRef, classMethod, Collections.EMPTY_LIST, false);
			return ((BooleanValue) activeRef).booleanValue();
		}
	}

	public static boolean isSubProcessContext(ObjectReference object) {
		if (object == null) {
			return false;
		}
		return implementsInterface(object, "com.tibco.cep.bpmn.runtime.agent.SubProcessContext");
	}

	public static boolean isTimeEvent(ObjectReference event) {
		return implementsInterface(event, TIME_EVENT_INTERFACE);
	}

	public static boolean isUnix() {
		String os = System.getProperty("os.name").toLowerCase();
		// linux or unix
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		// windows
		return (os.indexOf("win") >= 0);
	}

	public static void logMessage(RuleDebugThread rdt, Level logLevel, String msg) throws DebugException {
		synchronized (rdt) {

			ObjectReference rspInstance = getRuleServiceProviderFromManager(rdt);
			if (!implementsInterface((ClassType) rspInstance.referenceType(), RuleServiceProvider.class.getName())) {
				throw new IllegalArgumentException("The value type is not " + RuleServiceProvider.class.getName());
			}
			ReferenceType refClazz = rspInstance.referenceType();
			Method loggerMethod = findMethodBySignature((ClassType) refClazz, "getLogger", "(Ljava/lang/String;)Lcom/tibco/cep/kernel/service/logging/Logger;");
			ArrayList<Value> largs = new ArrayList<Value>();
			largs.add(rdt.getVM().mirrorOf("debugger.core"));
			ObjectReference loggerInstance = (ObjectReference) rdt.invokeMethod(null, rspInstance, loggerMethod, largs, false);
			List<Field> levelFields = DebuggerSupport.findClass(rdt.getVM(), Level.class.getName()).allFields();
			ReferenceType levelType = DebuggerSupport.findClass(rdt.getVM(), Level.class.getName());
			Method levelToStringMethod = findMethodBySignature((ClassType) levelType, "toString", "()Ljava/lang/String;");
			ObjectReference logLevelObject = null;
			for (Field field : levelFields) {
				if (field.isStatic()) {
					ObjectReference ref = (ObjectReference) levelType.getValue(field);
					if (ref.referenceType().equals(levelType)) {
						StringReference val = (StringReference) rdt.invokeMethod(null, ref, levelToStringMethod, Collections.EMPTY_LIST, false);
						if (logLevel.toString().equals(val.value())) {
							logLevelObject = ref;
							break;
						}
					}

				}
			}
			if (logLevelObject == null)
				return;
			ReferenceType loggerType = loggerInstance.referenceType();
			Method logMethod = findMethodBySignature((ClassType) loggerType, "log", "(Lcom/tibco/cep/kernel/service/logging/Level;Ljava/lang/String;)V");
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(logLevelObject);
			args.add(rdt.getVM().mirrorOf(msg));
			rdt.invokeMethod(null, loggerInstance, logMethod, args, false);
		}
	}

	public static void mainpulateWM(RuleDebugThread eventThread, ReferenceType debugTaskFactoryType, String ruleSessionName, long id, String[] index,
			String[] properties) throws DebugException {
		synchronized (eventThread) {
			final VirtualMachine vm = eventThread.getVM();
			final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
			if (debugTaskFactoryType == null) {
				debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}

			final String methodName = "newManipulateWMTask";
			String signature = "";
			try {
				signature = getMethodSignature(clazz, methodName, String.class, long.class, String[].class, String[].class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}// "(Ljava/lang/String;J[Ljava/lang/Integer;[Ljava/lang/String;)V";//getMethodSignature(clazz,
				// methodName, String.class, Integer.class);
			final Method processTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) debugTaskFactoryType, methodName, signature);
			ArrayList<Value> args = new ArrayList<Value>();
			args.add(vm.mirrorOf(ruleSessionName));
			args.add(vm.mirrorOf(id));

			ReferenceType strArrType = findClassBySignature(vm, "[Ljava/lang/String;");
			ArrayType arrayType = (ArrayType) strArrType;
			ArrayReference strArrRef = arrayType.newInstance(index.length);
			strArrRef.disableCollection();
			for (int loop = 0; loop < index.length; loop++) {
				try {
					strArrRef.setValue(loop, vm.mirrorOf(index[loop]));
				} catch (InvalidTypeException e) {
					e.printStackTrace();
				} catch (ClassNotLoadedException e) {
					e.printStackTrace();
				}
			}
			args.add(strArrRef);

			strArrType = findClassBySignature(vm, "[Ljava/lang/String;");
			arrayType = (ArrayType) strArrType;
			strArrRef = arrayType.newInstance(properties.length);
			strArrRef.disableCollection();
			for (int loop = 0; loop < properties.length; loop++) {
				try {
					strArrRef.setValue(loop, vm.mirrorOf(properties[loop]));
				} catch (InvalidTypeException e) {
					e.printStackTrace();
				} catch (ClassNotLoadedException e) {
					e.printStackTrace();
				}
			}
			args.add(strArrRef);

			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.disableCollection();
				}
			}
			eventThread.invokeMethod(((ClassType) debugTaskFactoryType), null, processTaskMethod, args, false);
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.enableCollection();
				}
			}
		}

	}

	private static String matchSuperClass(Type t, String superClass) {
		if (!(t instanceof ClassType)) {
			return null;
		}

		ClassType classType = null;
		String className = null;
		ClassType tmp = (ClassType) t;
		do {
			classType = tmp;
			className = classType.name();

			tmp = classType.superclass();
			if (tmp == null) {
				return null;
			}

		} while (!superClass.equals(tmp.name()));

		// if (classType == null) {
		// return null; // Object or primitive
		// }

		return className;
	}

	/**
	 * 
	 * @param thread
	 * @param object
	 * @param flags
	 * @return
	 */
	public static StringReference objectToString(RuleDebugThread thread, ObjectReference object, int flags) {
		synchronized (thread) {

			return (StringReference) invokeConcreteMethod(OBJECT_TO_STRING_METHOD, OBJECT_TO_STRING_METHOD_SIGNATURE, thread, object, Collections.EMPTY_LIST,
					flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param propertyArray
	 * @param flags
	 * @return
	 */
	public static ArrayReference propertyArrayToArray(RuleDebugThread thread, ObjectReference propertyArray, int flags) {
		synchronized (thread) {

			return (ArrayReference) invokeConcreteMethod(PROPERTY_ARRAY_TO_ARRAY_METHOD, PROPERTY_ARRAY_TO_ARRAY_METHOD_SIGNATURE, thread, propertyArray,
					Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param propertyAtomConcept
	 * @param flags
	 * @return
	 */
	public static ObjectReference propertyAtomConceptGetConcept(RuleDebugThread thread, ObjectReference propertyAtomConcept, int flags) {
		synchronized (thread) {

			return (ObjectReference) invokeConcreteMethod(PROPERTY_CONCEPT_GET_CONCEPT_METHOD, PROPERTY_CONCEPT_GET_CONCEPT_METHOD_SIGNATURE, thread,
					propertyAtomConcept, Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param propertyAtom
	 * @param flags
	 * @return
	 */
	public static StringReference propertyAtomGetString(RuleDebugThread thread, ObjectReference propertyAtom, int flags) {
		synchronized (thread) {

			return (StringReference) invokeConcreteMethod(PROPERTY_ATOM_GET_STRING_METHOD, PROPERTY_ATOM_GET_STRING_METHOD_SIGNATURE, thread, propertyAtom,
					Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param propertyAtom
	 * @param flags
	 * @return
	 */
	public static ObjectReference propertyAtomGetValue(RuleDebugThread thread, ObjectReference propertyAtom, int flags) {
		synchronized (thread) {

			return (ObjectReference) invokeConcreteMethod(PROPERTY_ATOM_GET_VALUE_METHOD, PROPERTY_ATOM_GET_VALUE_METHOD_SIGNATURE, thread, propertyAtom,
					Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param simpleDateFormat
	 * @param args
	 * @param flags
	 * @return
	 */
	public static StringReference simpleDateFormatFormat(RuleDebugThread thread, ObjectReference simpleDateFormat, List args, int flags) {
		synchronized (thread) {

			return (StringReference) invokeConcreteMethod(SIMPLE_DATE_FORMAT_FORMAT_METHOD, SIMPLE_DATE_FORMAT_FORMAT_METHOD_SIGNATURE, thread,
					simpleDateFormat, args, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param event
	 * @param flags
	 * @return
	 */
	public static StringReference simpleEventImplDebugPayloadToString(RuleDebugThread thread, ObjectReference event, int flags) {
		synchronized (thread) {

			return (StringReference) invokeConcreteMethod(DEBUG_PAYLOAD_TO_STRING_METHOD, DEBUG_PAYLOAD_TO_STRING_METHOD_SIGNATURE, thread, event,
					Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * Is the class assignable from SmartStepable
	 * 
	 * @param c
	 * @return
	 */
	public static boolean supportsStepInto(Class c) {
		if (c.isAssignableFrom(SmartStepInto.class)) {
			return true;
		}
		return false;
	}

	/**
	 * Does the Object's class supports step into.
	 * 
	 * @param o
	 * @return
	 */
	public static boolean supportsStepInto(Object o) {
		if (o instanceof SmartStepInto)
			return true;
		return false;
	}

	/**
	 * 
	 * @param eventThread
	 * @param debugTaskFactoryType
	 * @param inputData
	 * @param ruleSessionName
	 * @param sessionName
	 * @throws Exception
	 */
	public static void testData(RuleDebugThread eventThread, ReferenceType debugTaskFactoryType, String[] inputData, String ruleSessionName, String sessionName)
			throws Exception {
		synchronized (eventThread) {
			final VirtualMachine vm = eventThread.getVM();
			final Class<DebugTaskFactory> clazz = DebugTaskFactory.class;
			if (debugTaskFactoryType == null) {
				debugTaskFactoryType = DebuggerSupport.findClass(vm, clazz.getName());
			}

			final String methodName = "newTestDataTask";
			final String signature = getMethodSignature(clazz, methodName, String[].class, String.class, String.class);
			final Method processTaskMethod = DebuggerSupport.findMethodBySignature((ClassType) debugTaskFactoryType, methodName, signature);

			ArrayList<Value> args = new ArrayList<Value>();
			ReferenceType strArrType = findClassBySignature(vm, "[Ljava/lang/String;");
			ArrayType arrayType = (ArrayType) strArrType;
			ArrayReference arrRef = arrayType.newInstance(inputData.length);
			arrRef.disableCollection();

			for (int loop = 0; loop < inputData.length; loop++) {
				arrRef.setValue(loop, vm.mirrorOf(inputData[loop]));
			}
			args.add(arrRef);
			args.add(vm.mirrorOf(ruleSessionName));
			args.add(vm.mirrorOf(sessionName));

			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.disableCollection();
				}
			}
			eventThread.invokeMethod(((ClassType) debugTaskFactoryType), null, processTaskMethod, args, false);
			for (Value v : args) {
				if (v instanceof ObjectReference) {
					ObjectReference o = (ObjectReference) v;
					o.enableCollection();
				}
			}
		}
	}

	/**
	 * 
	 * @param thread
	 * @param timeEvent
	 * @param flags
	 * @return
	 */
	public static StringReference timeEventGetClosure(RuleDebugThread thread, ObjectReference timeEvent, int flags) {
		synchronized (thread) {

			return (StringReference) invokeConcreteMethod(TIME_EVENT_GET_CLOSURE_METHOD, TIME_EVENT_GET_CLOSURE_METHOD_SIGNATURE, thread, timeEvent,
					Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param timeEvent
	 * @param flags
	 * @return
	 */
	public static LongValue timeEventGetInterval(RuleDebugThread thread, ObjectReference timeEvent, int flags) {
		synchronized (thread) {
			return (LongValue) invokeConcreteMethod(TIME_EVENT_GET_INTERVAL_METHOD, TIME_EVENT_GET_INTERVAL_METHOD_SIGNATURE, thread, timeEvent,
					Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * 
	 * @param thread
	 * @param timeEvent
	 * @param flags
	 * @return
	 */
	public static ObjectReference timeEventGetScheduledTime(RuleDebugThread thread, ObjectReference timeEvent, int flags) {
		synchronized (thread) {

			return (ObjectReference) invokeConcreteMethod(TIME_EVENT_GET_SCHEDULED_TIME_METHOD, TIME_EVENT_GET_SCHEDULED_TIME_METHOD_SIGNATURE, thread,
					timeEvent, Collections.EMPTY_LIST, flags);
		}
	}

	/**
	 * @param eventThread
	 * @param objRef
	 * @return
	 * @throws DebugException
	 */
	public static String toString(RuleDebugThread eventThread, ObjectReference objRef) throws DebugException {
		synchronized (eventThread) {

			Method method = findMethodBySignature((ClassType) objRef.referenceType(), "toString", "()Ljava/lang/String;");
			StringReference s = (StringReference) eventThread.invokeMethod(null, objRef, method, Collections.EMPTY_LIST, false);
			if (s != null) {
				return s.value();
			}
			return "";
		}
	}

	/**
	 * @param eventThread
	 * @param jdiValue
	 * @return
	 */
	public static String value2String(RuleDebugThread eventThread, Value jdiValue) {
		synchronized (eventThread) {

			final String valFormat = "{0}";
			final String idFormat = "{0} (id={1})";
			try {
				if (jdiValue == null) {
					return "null";
				} else {
					if (jdiValue instanceof PrimitiveValue) {
						Object val = getPrimitiveValue(jdiValue);
						return MessageFormat.format(valFormat, String.valueOf(val));
					} else if (jdiValue instanceof ObjectReference) {
						ObjectReference objRef = (ObjectReference) jdiValue;
						final Long objId = objRef.uniqueID();
						if (isCalenderType((ObjectReference) jdiValue)) {
							LongValue millis = getCalendarTimeInMillis(eventThread, (ObjectReference) jdiValue);
							return MessageFormat.format(valFormat, formatAsTime(millis.longValue(), DebuggerConstants.DEBUGGER_DATE_FORMAT));
						} else if (((ObjectReference) jdiValue).referenceType().name().equals(Integer.class.getName())) {
							Value val = getIntValue(eventThread, (ObjectReference) jdiValue);
							return MessageFormat.format(valFormat, value2String(eventThread, val));
						} else if (((ObjectReference) jdiValue).referenceType().name().equals(Long.class.getName())) {
							Value val = getLongValue(eventThread, (ObjectReference) jdiValue);
							return MessageFormat.format(valFormat, value2String(eventThread, val));
						} else if (((ObjectReference) jdiValue).referenceType().name().equals(Boolean.class.getName())) {
							Value val = getBooleanValue(eventThread, (ObjectReference) jdiValue);
							return MessageFormat.format(valFormat, value2String(eventThread, val));
						} else if (((ObjectReference) jdiValue).referenceType().name().equals(Double.class.getName())) {
							Value val = getDoubleValue(eventThread, (ObjectReference) jdiValue);
							return MessageFormat.format(valFormat, value2String(eventThread, val));
						} else if (jdiValue instanceof ClassObjectReference) {
							return MessageFormat.format(valFormat, ((ClassObjectReference) jdiValue).referenceType().name(), objId);
						} else if (jdiValue instanceof StringReference) {
							return MessageFormat.format(valFormat, ((StringReference) jdiValue).value());
						} else if (jdiValue instanceof ArrayReference) {
							return MessageFormat.format(idFormat, objRef.type().name(), objId);
						} else {
							StringBuilder builder = new StringBuilder();
							List<Method> methods = ((ReferenceType) objRef.type()).methodsByName("toString");
							StringReference s = (StringReference) eventThread.invokeMethod(null, objRef, methods.get(0), Collections.EMPTY_LIST, false);
							builder.append(" [");
							builder.append(s.value());
							builder.append("]");
							return MessageFormat.format(idFormat, objRef.type().name(), objId) + builder.toString();
						}
					}
				}
			} catch (Exception e) {
				return e.getClass().getName() + ":" + e.getMessage();
			}
			return "";
		}
	}

	/**
	 * @param eventThread
	 * @param agendaItemVal
	 * @return
	 * @throws DebugException
	 */
	public int getAgendaParamsSize(RuleDebugThread eventThread, ObjectReference agendaItemVal) throws DebugException {
		int length = -1;
		synchronized (eventThread) {
			if (!((ClassType) agendaItemVal.referenceType()).superclass().name().equals(AgendaItem.class.getName())) {
				throw new IllegalArgumentException("The object reference does not implement the " + AgendaItem.class.getName() + " interface");
			}
			getAgendaParams(eventThread, agendaItemVal);
			Method getCauseMethod = findMethodBySignature((ClassType) agendaItemVal.referenceType(), "getParams", "()Ljava/lang/Object;");
			Value objects = eventThread.invokeMethod(null, agendaItemVal, getCauseMethod, Collections.EMPTY_LIST, false);
			if (objects instanceof ArrayReference) {
				ArrayReference arf = (ArrayReference) objects;
				try {
					arf.enableCollection();
					length = arf.length();
				} finally {
					arf.disableCollection();
				}
			}
		}
		return length;
	}
	
	public static boolean isPropertyAtomConceptValue(RuleDebugThread eventThread, ObjectReference propRef) throws DebugException {
		if (DebuggerSupport.implementsInterface(propRef,
				PropertyAtomConceptReference.class.getName()) || DebuggerSupport.implementsInterface(propRef, PropertyAtomContainedConcept.class.getName())) {
			return true;
		} 
		return false;
	}
	
	public static boolean isProcessJavaTaskContext(RuleDebugThread rdt) throws DebugException {
		synchronized (rdt) {
			ReferenceType jobClass = findClass(rdt.getVM(),"com.tibco.cep.bpmn.runtime.activity.tasks.ProcessJavaTask");
			Method method = findMethodBySignature((ClassType) jobClass, "getCurrentJobContext", "()Lcom/tibco/cep/bpmn/runtime/model/JobContext;");
			Value currJobContext = rdt.invokeMethod((ClassType) jobClass, null, method, Collections.EMPTY_LIST, false);
			return currJobContext != null;
		}
		
	}

}
