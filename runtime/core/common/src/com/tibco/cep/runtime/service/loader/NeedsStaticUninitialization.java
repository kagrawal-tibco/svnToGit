package com.tibco.cep.runtime.service.loader;

/**
 Classes that implement this interface will, when loaded or reloaded by BEClassLoader
 Have their public static void NeedsStaticInitialization_init() method called.
 */
public interface NeedsStaticUninitialization {
    public static final String UNINIT_METHOD_NAME = "NeedsStaticUninitialization_uninit";
}