package com.tibco.cep.runtime.jmx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

public class AnnotationAwareStandardMBean extends StandardMBean {

	private static final Map<String, Class<?>> PRIMITIVE_CLASS_TYPES = new HashMap<String, Class<?>>();

	static {
		//int
		PRIMITIVE_CLASS_TYPES.put("int", Integer.TYPE);
		//long
		PRIMITIVE_CLASS_TYPES.put("long", Long.TYPE);
		//double
		PRIMITIVE_CLASS_TYPES.put("double", Double.TYPE);
		//float
		PRIMITIVE_CLASS_TYPES.put("float", Float.TYPE);
		//boolean
		PRIMITIVE_CLASS_TYPES.put("boolean", Boolean.TYPE);
	}

	public <T> AnnotationAwareStandardMBean(T implementation, Class<T> mbeanInterface, boolean isMXBean) {
		super(implementation, mbeanInterface, isMXBean);
	}

	public <T> AnnotationAwareStandardMBean(T implementation, Class<T> mbeanInterface) throws NotCompliantMBeanException {
		super(implementation, mbeanInterface);
	}

	@Override
	protected String getDescription(MBeanInfo info) {
		Description annotation = this.getMBeanInterface().getAnnotation(Description.class);
		if (annotation != null) {
			return annotation.value();
		}
		return super.getDescription(info);
	}

	@Override
	protected String getDescription(MBeanAttributeInfo info) {
		StringBuilder methodName = new StringBuilder();
		String[] paramTypes = null;
		if (info.isIs() == true) {
			methodName.append("is");
		} else if (info.isReadable() == true) {
			methodName.append("get");
		} else if (info.isWritable() == true) {
			methodName.append("set");
			paramTypes = new String[] { info.getType() };
		}
		methodName.append(info.getName());
		Method method = searchMethod(methodName.toString(), paramTypes);
		if (method != null && method.isAnnotationPresent(Description.class) == true) {
			return method.getAnnotation(Description.class).value();
		}
		return super.getDescription(info);
	}

	@Override
	protected String getDescription(MBeanOperationInfo info) {
		Method method = searchMethod(info);
		if (method != null && method.isAnnotationPresent(Description.class) == true) {
			return method.getAnnotation(Description.class).value();
		}
		return super.getDescription(info);
	}

	@Override
	protected String getDescription(MBeanOperationInfo op, MBeanParameterInfo param, int sequence) {
		Method method = searchMethod(op);
		if (method != null){
			Annotation[] annotations = method.getParameterAnnotations()[sequence];
			for (Annotation annotation : annotations) {
				if (annotation instanceof Description){
					return ((Description) annotation).value();
				}
			}
		}
		return super.getDescription(op, param, sequence);
	}

	@Override
	protected String getParameterName(MBeanOperationInfo op, MBeanParameterInfo param, int sequence) {
		Method method = searchMethod(op);
		if (method != null){
			Annotation[] annotations = method.getParameterAnnotations()[sequence];
			for (Annotation annotation : annotations) {
				if (annotation instanceof Parameter){
					return ((Parameter) annotation).value();
				}
			}
		}
		return super.getParameterName(op, param, sequence);
	}

	@Override
	protected int getImpact(MBeanOperationInfo info) {
		Method method = searchMethod(info);
		if (method != null && method.isAnnotationPresent(Impact.class) == true) {
			return method.getAnnotation(Impact.class).value();
		}
		return super.getImpact(info);
	}

	protected Method searchMethod(MBeanOperationInfo info) {
		String[] paramTypes = null;
		MBeanParameterInfo[] parameterInfos = info.getSignature();
		if (parameterInfos != null) {
			paramTypes = new String[parameterInfos.length];
			for (int i = 0; i < parameterInfos.length; i++) {
				paramTypes[i] = parameterInfos[i].getType();
			}
		}
		return searchMethod(info.getName(), paramTypes);
	}

	protected Method searchMethod(String name, String[] paramTypes) {
		try {
			Class<?> implementationClass = this.getMBeanInterface();
			ClassLoader classLoader = implementationClass.getClassLoader();
			Class<?>[] paramClasses = null;
			if (paramTypes != null) {
				paramClasses = new Class<?>[paramTypes.length];
				for (int i = 0; i < paramTypes.length; i++) {
					paramClasses[i] = PRIMITIVE_CLASS_TYPES.get(paramTypes[i]);
					if (paramClasses[i] == null) {
						paramClasses[i] = Class.forName(paramTypes[i], false, classLoader);
					}
				}
			}
			implementationClass.getMethods();
			return implementationClass.getMethod(name, paramClasses);
		} catch (SecurityException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

}
