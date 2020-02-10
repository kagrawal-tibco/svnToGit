package com.tibco.cep.repo;

import java.io.InputStream;

import com.tibco.xml.tns.impl.TargetNamespaceCache;

public abstract class BETargetNamespaceCache extends TargetNamespaceCache {
	
	 abstract public void resourceChanged(String sysId, InputStream is);
	 abstract public String getRootURI();
}
