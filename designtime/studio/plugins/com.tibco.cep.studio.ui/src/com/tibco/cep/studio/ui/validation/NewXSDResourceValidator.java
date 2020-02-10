package com.tibco.cep.studio.ui.validation;

import java.io.File;
import java.io.FileInputStream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.sax.SOXResolver;
import com.tibco.xml.schema.parse.SmParseSupport;

/**
 * XSD Validation for basic reference checks and such.
 * @author aathalye
 *
 */
public class NewXSDResourceValidator extends DefaultResourceValidator {
	
	@Override
	public boolean canContinue() {
		// can continue if any xsd resource got any validation problem
		return true;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.validation.DefaultResourceValidator#validate(com.tibco.cep.studio.core.validation.ValidationContext)
	 */
	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();	
		if (resource == null) return true;
		super.validate(validationContext);
		deleteMarkers(resource);
		try {
			String fullPath = resource.getLocation().toString();
			//Read contents
			File file = new File(fullPath);
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				InputSource inputSource = new InputSource(fis);
				DefaultErrorHandler errorHandler = new DefaultErrorHandler(resource);
				validate(inputSource, errorHandler);
				return !errorHandler.hasValidationErrors();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
	private void validate(InputSource inputSource, ErrorHandler errorHandler) throws Exception {
		EntityResolver resolver = new SOXResolver(System .getProperty(
                com.tibco.xml.validation .XMLValidator.SOX_SCHEMA_PATH),
                null);
		SmParseSupport.parseSchema(inputSource, resolver, errorHandler, SmParseSupport.NO_EXTENDED_CHECKS);
	}
	
	private class DefaultErrorHandler implements ErrorHandler {
		/**
		 * Resource to validate
		 */
		private IResource resource;
		
		private int errorCount;
		
		/**
		 * @param resource
		 */
		public DefaultErrorHandler(IResource resource) {
			this.resource = resource;
		}

		public void error(SAXParseException arg0) throws SAXException {
			errorCount = errorCount++;
			reportProblem(resource, arg0.getMessage(), IMarker.SEVERITY_WARNING);
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
		 */
		
		public void fatalError(SAXParseException arg0) throws SAXException {
			errorCount = errorCount++;
			reportProblem(resource, arg0.getMessage(), IMarker.SEVERITY_ERROR);
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
		 */
		
		public void warning(SAXParseException arg0) throws SAXException {
			reportProblem(resource, arg0.getMessage(), IMarker.SEVERITY_WARNING);
		}
		
		boolean hasValidationErrors() {
			return errorCount > 0;
		}
	}
}
