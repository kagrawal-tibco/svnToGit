package com.tibco.cep.studio.core.rdf;

import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.impl.TnsImportImpl;
import com.tibco.xml.ws.wsdl.impl.Wsdl;

public class WsdlTnsImport extends TnsImportImpl {

	private TnsDocument fCachedDocument;
	
	public WsdlTnsImport(String original, String xmlBase, String nsURI,
			Class kind) {
		super(original, xmlBase, nsURI, kind);
	}

	@Override
	public TnsDocument getDocument() {
		if (fCachedDocument == null) {
			TnsDocument doc = super.getDocument();
			if (doc instanceof Wsdl) {
				fCachedDocument = new WsdlWrapperDocument(m_nsURI, (Wsdl) doc);
			}
		}
		return fCachedDocument;
	}

	@Override
	public String getNamespaceURI() {
//		return ((WsdlWrapperDocument)getDocument()).getTargetNamespace();
		return super.getNamespaceURI();
	}

}
