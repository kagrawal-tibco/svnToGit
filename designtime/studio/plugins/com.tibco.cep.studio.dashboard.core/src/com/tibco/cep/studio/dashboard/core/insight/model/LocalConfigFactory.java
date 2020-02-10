package com.tibco.cep.studio.dashboard.core.insight.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.ViewsConfigReader;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

//TODO remove the rawtypes & unchecked suppress warnings
public class LocalConfigFactory {

	private static LocalConfigFactory factory;

	private final static String ConfigPackage = "com.tibco.cep.studio.dashboard.core.insight.model.configs";
	private final static String ClassPrefix = "Local";

	private LocalConfigFactory() {
	}

	public synchronized static LocalConfigFactory getInstance() {
		if (factory == null) {
			factory = new LocalConfigFactory();
		}
		return factory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	//PATCH figure out a better exception handling
	public LocalConfig createConfigInstance(String configType) {
		try {
			String configClassName = getClassName(configType);
			Class configClass = Class.forName(configClassName);
			Constructor configClassConstructor;
			try {
				configClassConstructor = configClass.getConstructor(new Class[]{String.class});
				LocalConfig configInstance = (LocalConfig) configClassConstructor.newInstance(new Object[]{configType});
				return configInstance;
			} catch (NoSuchMethodException nsme) {
				configClassConstructor = configClass.getConstructor();
				LocalConfig configInstance = (LocalConfig) configClassConstructor.newInstance();
				return configInstance;
			}
		} catch (ClassNotFoundException e) {
			return new LocalConfig(configType);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param parentElement Element under which this instance would be created. Mostly it would be BaseLocalConfig type.
	 * @param configType
	 * @param mdElement
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LocalConfig createConfigInstance(LocalElement parentElement, String configType, Entity mdElement) {
		try {
			String configClassName = getClassName(configType);
			Class configClass = Class.forName(configClassName);
			Constructor configClassConstructor;
			try {
				configClassConstructor = configClass.getConstructor(new Class[]{LocalElement.class, String.class, BEViewsElement.class});
				return (LocalConfig) configClassConstructor.newInstance(new Object[]{parentElement, configType, mdElement});
			} catch (NoSuchMethodException e) {
				configClassConstructor = configClass.getConstructor(new Class[]{LocalElement.class, BEViewsElement.class});
				return (LocalConfig) configClassConstructor.newInstance(new Object[]{parentElement, mdElement});
			}
		} catch (ClassNotFoundException e) {
			return new LocalConfig(parentElement, configType, (BEViewsElement) mdElement);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param parentElement
	 * @param configType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LocalConfig createConfigInstance(LocalElement parentElement, String configType) {
		try {
			String configClassName = getClassName(configType);
			Class configClass = Class.forName(configClassName);
			Constructor configClassConstructor = configClass.getConstructor(new Class[]{LocalElement.class, String.class});
			LocalConfig configInstance = (LocalConfig) configClassConstructor.newInstance(new Object[]{parentElement, configType});
			return configInstance;
		} catch (ClassNotFoundException e) {
			return new LocalConfig(parentElement, configType);
		}  catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public LocalConfig createConfigInstance(BEViewsElement mdElement) throws Exception {
		throw new UnsupportedOperationException("Please use the constructors that take a LocalConfigType");
	}

	private String getClassName(String type) throws ClassNotFoundException {
		String configClassName = ConfigPackage + "." + ClassPrefix + type;
		try {
			Class.forName(configClassName);
			return configClassName;
		} catch (ClassNotFoundException e) {
			String superType = ViewsConfigReader.getInstance().getSuperParticleName(type);
			if (superType != null) {
				return getClassName(superType);
			}
			else {
				throw e;
			}
		}
	}

}
