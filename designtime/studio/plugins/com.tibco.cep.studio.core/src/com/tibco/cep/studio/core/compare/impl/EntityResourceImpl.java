package com.tibco.cep.studio.core.compare.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;

public class EntityResourceImpl extends XMIResourceImpl {

	public EntityResourceImpl(URI uri) {
		super(uri);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		super.load(options);
	}

	@Override
	protected XMLLoad createXMLLoad() {
		return new XMLLoadImpl(createXMLHelper());
	}

	@Override
	public void doSave(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		super.doSave(outputStream, options);
	}

	@Override
	public void doSave(Writer writer, Map<?, ?> options) throws IOException {
		super.doSave(writer, options);
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		super.save(options);
	}
}
