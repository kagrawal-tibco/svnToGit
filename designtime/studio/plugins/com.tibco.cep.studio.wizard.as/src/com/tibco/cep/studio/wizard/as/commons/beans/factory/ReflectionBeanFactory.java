package com.tibco.cep.studio.wizard.as.commons.beans.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.wizard.as.commons.beans.factory.exception.CanNotCreateBeanException;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

public class ReflectionBeanFactory implements IBeanFactory {

	private Class<?> beanClazz;

	public ReflectionBeanFactory(Class<?> beanClazz) {
		this.beanClazz = beanClazz;
	}

	@Override
	public Object create() throws CanNotCreateBeanException {
		try {
			return beanClazz.newInstance();
		}
		catch (Exception ex) {
			throw new CanNotCreateBeanException(Messages.getString("ReflectionBeanFactory.failed_create_instance") + beanClazz.getCanonicalName(), ex); //$NON-NLS-1$
		}
	}

	@Override
	public Object create(Object[] args) throws CanNotCreateBeanException {
		// analyze the classes of arguments.
		List<Class<?>> argClazzList = new ArrayList<Class<?>>();
		for (Object arg : args) {
			Class<?> clazz = arg.getClass();
			argClazzList.add(clazz);
		}
		Class<?>[] argClazzez = argClazzList.toArray(new Class<?>[argClazzList.size()]);
		Constructor<?>[] constructors = beanClazz.getConstructors();

		// find bean constructor
		Constructor<?> beanConstructor = null;
		for (Constructor<?> constructor : constructors) {
			Class<?>[] paramClazzez = constructor.getParameterTypes();
			if (argClazzez.length == paramClazzez.length) {
				boolean matched = true;
				for (int i=0; matched && i<argClazzez.length; i++) {
					matched &= paramClazzez[i].isAssignableFrom(argClazzez[i]);
				}
				if (matched) {
					beanConstructor = constructor;
					break;
				}
			}
		}

		// create bean if constructor is existing.
		Object bean = null;
		if (null != beanConstructor) {
			try {
	            bean = beanConstructor.newInstance(args);
            }
            catch (InvocationTargetException itEx) {
            	throw new CanNotCreateBeanException(Messages.getString("ReflectionBeanFactory.failed_create_instance", beanClazz.getCanonicalName(), beanConstructor), itEx.getTargetException()); //$NON-NLS-1$ //$NON-NLS-2$
            } catch (Exception ex) {
	            // TODO Auto-generated catch block
	            ex.printStackTrace();
            }
		} else {
			throw new CanNotCreateBeanException(Messages.getString("ReflectionBeanFactory.failed_create_instance2", beanClazz.getCanonicalName())); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return bean;
	}

}
