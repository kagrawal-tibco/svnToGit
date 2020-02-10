package com.tibco.cep.runtime.service.loader;

import java.util.Collection;

import javax.tools.JavaFileObject;

public interface DynamicLoader {

	public abstract void addJavaFile(String qualifiedName, JavaFileObject file);

	public abstract Collection<? extends JavaFileObject> files();

}
