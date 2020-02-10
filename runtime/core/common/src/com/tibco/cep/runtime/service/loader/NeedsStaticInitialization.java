package com.tibco.cep.runtime.service.loader;

/**
 Classes that implement this interface will, when loaded or reloaded by BEClassLoader
 Have their public static void NeedsStaticInitialization_init() method called.
 */
public interface NeedsStaticInitialization {
    public static final String INIT_METHOD_NAME = "NeedsStaticInitialization_init";
}
