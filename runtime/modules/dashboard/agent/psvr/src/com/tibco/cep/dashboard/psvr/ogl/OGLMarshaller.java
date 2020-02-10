package com.tibco.cep.dashboard.psvr.ogl;

import java.io.IOException;
import java.io.StringWriter;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.ServiceDependent;
import com.tibco.cep.dashboard.security.SecurityToken;

/**
 * @author apatil
 * 
 */
public final class OGLMarshaller extends ServiceDependent {

	private static OGLMarshaller instance;

	public static final synchronized OGLMarshaller getInstance() {
		if (instance == null) {
			instance = new OGLMarshaller();
		}
		return instance;
	}

	private boolean debug;

	private OGLMarshaller() {
		super("oglmarshaller","OGL Marshaller");
		debug = false;
	}

	public final String marshall(SecurityToken token, Object obj) throws OGLException {
		try {
			StringWriter sw = new StringWriter();
			Marshaller marshaller = new Marshaller(sw);
			marshaller.setSuppressNamespaces(true);
			marshaller.setSuppressXSIType(true);
			marshaller.setSupressXMLDeclaration(true);
			marshaller.setValidation(debug);
			marshaller.setDebug(debug);
			marshaller.marshal(obj);
			return sw.toString();
		} catch (MarshalException e) {
			String message = messageGenerator.getMessage("oglmarshaller.marshalling.failure", getMessageGeneratorArgs(token, e, obj));
			throw new OGLException(message, e);
		} catch (ValidationException e) {
			String message = messageGenerator.getMessage("oglmarshaller.validation.failure", getMessageGeneratorArgs(token, e, obj));
			throw new OGLException(message, e);
		} catch (IOException e) {
			String message = messageGenerator.getMessage("oglmarshaller.io.failure", getMessageGeneratorArgs(token, e, obj));
			throw new OGLException(message, e);
		}
	}

	private MessageGeneratorArgs getMessageGeneratorArgs(SecurityToken token, Exception e, Object... additionalArgs) {
		if (token == null) {
			return new MessageGeneratorArgs(e, additionalArgs);
		}
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), e, additionalArgs);
	}

	/**
	 * @return Returns the debug.
	 */
	boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug
	 *            The debug to set.
	 */
	void setDebug(boolean debug) {
		this.debug = debug;
	}
}
