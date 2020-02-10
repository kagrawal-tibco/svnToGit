package com.tibco.cep.studio.core.rdf;

import java.util.Iterator;

import com.tibco.util.localization.LocaleIndependentMessage;
import com.tibco.xml.tns.TargetNamespaceProvider;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsError;
import com.tibco.xml.tns.parse.TnsErrorSeverity;
import com.tibco.xml.tns.parse.TnsFragment;
import com.tibco.xml.tns.parse.helpers.TnsErrorReporter;
import com.tibco.xml.ws.wsdl.WsWsdl;
import com.tibco.xml.ws.wsdl.impl.Wsdl;

public class WsdlWrapperDocument implements TnsDocument, Cloneable {

	private WsWsdl fDocument;
	private String fTargetNamespace;
	private String fWsdlNamespace;

	public WsdlWrapperDocument(String namespace, Wsdl wsdl) {
		this.fTargetNamespace = namespace;
		this.fWsdlNamespace = wsdl.getTargetNamespace();
		wsdl.setTargetNamespace(namespace);
		this.fDocument = wsdl;
	}

	@Override
	public Iterator getFragments() {
//		return fDocument.getFragments();
		return getSingleFragment().getFragments();
	}

	public String getTargetNamespace() {
		return fTargetNamespace;
	}

	@Override
	public void addFragment(TnsFragment fragment) {
		fDocument.addFragment(fragment);		
	}

	@Override
	public String getLocation() {
		return fDocument.getLocation();
	}

	@Override
	public TnsFragment getSingleFragment() {
		return fDocument.getSingleFragment();
	}

	@Override
	public void setLocation(String location) {
		fDocument.setLocation(location);		
	}

	@Override
	public TnsError addError(TnsErrorSeverity severity, String code,
			String message, int line, int col) {
		return fDocument.addError(severity, code, message, line, col);
	}

	@Override
	public TnsError addError(TnsErrorSeverity severity, String rsrcFileName,
			String rsrcKey, Object[] args, int line, int col) {
		return fDocument.addError(severity, rsrcFileName, rsrcKey, args, line, col);
	}

	@Override
	public TnsError addError(TnsErrorSeverity severity, String rsrcFileName,
			String rsrcKey, LocaleIndependentMessage lim, int line, int col) {
		return fDocument.addError(severity, rsrcFileName, rsrcKey, lim, line, col);
	}

	@Override
	public Iterator getErrors(TnsErrorSeverity severityThreshold,
			boolean transitive) {
		return fDocument.getErrors(severityThreshold, transitive);
	}

	@Override
	public String getIdentifier() {
		return fDocument.getIdentifier();
	}

	@Override
	public boolean hasErrors(TnsErrorSeverity severityThreshold,
			boolean transitive) {
		return fDocument.hasErrors(severityThreshold, transitive);
	}

	@Override
	public void reportErrors(TnsErrorReporter reporter,
			TnsErrorSeverity inclMinSeverity, boolean transitive) {
		fDocument.reportErrors(reporter, inclMinSeverity, transitive);		
	}

	@Override
	public boolean isResolved() {
		return fDocument.isResolved();
	}

	@Override
	public void resolveAndCheck(TargetNamespaceProvider resolver) {
		((Wsdl)fDocument).setTargetNamespace(fWsdlNamespace);
		fDocument.resolveAndCheck(resolver);		
		((Wsdl)fDocument).setTargetNamespace(fTargetNamespace);
	}

	@Override
	public void unresolve() {
		fDocument.unresolve();		
	}

	public Object clone() {
		return fDocument.clone();
	}

}
