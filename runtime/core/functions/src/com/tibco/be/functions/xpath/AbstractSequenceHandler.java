package com.tibco.be.functions.xpath;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.xml.namespace.QName;

import org.genxdm.exceptions.GenXDMException;
import org.genxdm.io.DtdAttributeKind;
import org.genxdm.typed.io.SequenceHandler;

import com.tibco.xml.data.primitive.XmlAtomicValue;

public abstract class AbstractSequenceHandler implements SequenceHandler<XmlAtomicValue> {

	@Override
	public void attribute(String arg0, String arg1, String arg2, String arg3,
			DtdAttributeKind arg4) throws GenXDMException {
	}

	@Override
	public void comment(String arg0) throws GenXDMException {
	}

	@Override
	public void endDocument() throws GenXDMException {
	}

	@Override
	public void endElement() throws GenXDMException {
	}

	@Override
	public void namespace(String arg0, String arg1) throws GenXDMException {
	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws GenXDMException {
	}

	@Override
	public void startDocument(URI arg0, String arg1) throws GenXDMException {
	}

	@Override
	public void startElement(String ns, String elementName, String prefix)
			throws GenXDMException {
	}

	@Override
	public void text(String arg0) throws GenXDMException {
	}

	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void attribute(String arg0, String arg1, String arg2,
			List<? extends XmlAtomicValue> arg3, QName arg4)
			throws GenXDMException {
	}

	@Override
	public void startElement(String ns, String elementName,
			String prefix, QName paramQName)
			throws GenXDMException {
	}

	@Override
	public void text(List<? extends XmlAtomicValue> arg0)
			throws GenXDMException {
	}

}
