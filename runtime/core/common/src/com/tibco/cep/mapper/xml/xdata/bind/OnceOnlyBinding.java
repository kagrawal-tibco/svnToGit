package com.tibco.cep.mapper.xml.xdata.bind;

import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.InputData;
import com.tibco.cep.mapper.xml.xdata.ValidationUtils;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.channel.infoset.helpers.XmlContentTracer;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiBuilder;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmComponentProvider;
import com.tibco.xml.schema.SmComponentProviderEx;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmGlobalComponentNotFoundException;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.serialization.helpers.OutputConfigTermination;
import com.tibco.xml.transform.trax.TransformerEx;
import com.tibco.xml.trax.XiNodeBucket;
import com.tibco.xml.trax.XmlContentResult;
import com.tibco.xml.validation.factory.XmlTreeNodeValidatorFactory;
import com.tibco.xml.validation.factory.state.StateDrivenXmlTreeNodeValidatorFactory;
import com.tibco.xml.validation.helpers.Validator_XmlContent_Remark_ExternalCache;
import com.tibco.xml.xquery.ExprFocus;
import com.tibco.xml.xquery.helpers.DefaultExprFocus;

/**
 * This implementation returns the XiNode or drives the stream only once.
 */
class OnceOnlyBinding implements InputData {
	private static final XiNode EMPTY_NODE = XiFactoryFactory.newInstance().createDocument();
	private static final XiNodeBucket EMPTY_NODE_BUCKET = new XiNodeBucket(EMPTY_NODE);
	private static final ExprFocus EMPTY_DOCUMENT_FOCUS = new DefaultExprFocus(EMPTY_NODE);


	private TransformerEx transformer;
	private boolean validate;
	private SmParticleTerm expectedOutput;
	private SmComponentProviderEx mCompProvider;
	protected BindingRemarkHandler errorHandler;
	private boolean done;

	/**
     *
	 * @param transformer    must have the input parameters already set up
	 * @param validate
	 * @param expectedOutput
	 * @param nsProv
	 */
	public OnceOnlyBinding(TransformerEx transformer, boolean validate,
                          SmParticleTerm expectedOutput,
                          SmComponentProviderEx nsProv) {
		done = false;
		errorHandler = new BindingRemarkHandler();
		this.transformer = transformer;
		this.validate = validate;
		this.expectedOutput = expectedOutput;
		this.mCompProvider = nsProv;
	}

	public XiNode getXiNode() throws IllegalStateException, SAXException {
		if (done) {
			throw new IllegalStateException("input already consumed");
		}

		XiBuilder builder = new XiBuilder(XiFactoryFactory.newInstance());
		stream(builder);
      XiNode retNode = builder.getNode();

      // we need to reset the builder.. it has a reference into the thing
      // that it built, and the builder sticks around because the validator has a reference to it,
      // and this causes a delay in GC for the document.
      // Having a reset method on the validator would probably be cleaner, but this works.
      builder.reset();
      return retNode;
	}

	// performance... getBoolean calls are expensive so lets make them static, since they don't change.
	static boolean sm_useOldVal = ValidationUtils.isUsingTheOldValidator();
	static boolean sm_useTransResult = Boolean.getBoolean("use.transformer.result");
	static boolean sm_traceVal = Boolean.getBoolean("trace.validator");
	static boolean sm_traceRawTrans = Boolean.getBoolean("trace.raw.transformer");

	public void stream(XmlContentHandler contentHandler) throws IllegalStateException, SAXException {
		if (done) {
			throw new IllegalStateException("input already consumed");
		}
		Result result/* = setupTransformer(transformer, validate, expectedOutput, mnsProvider, errorHandler, errorHandler,
		contentHandler)*/;
		transformer.setErrorListener(errorHandler);
		if (sm_useOldVal) {
			if (validate) {
				Validator_XmlContent_Remark_ExternalCache validator = new Validator_XmlContent_Remark_ExternalCache();
				validator.setXmlContentHandler(contentHandler);
            validator.setComponentProvider(new SmComponentProviderExWrapper(mCompProvider));
				validator.setRootSmParticleTerm(expectedOutput);
				validator.setXmlRemarkHandler(errorHandler);
				result = new XmlContentResult(validator, OutputConfigTermination.getInstance());
			}
			else {
				result = new XmlContentResult(contentHandler, OutputConfigTermination.getInstance());
			}
			try {
				transformer.transform(EMPTY_NODE_BUCKET, result);
			}
			catch (TransformerException e) {
				errorHandler.fatalError(e);
			}
			finally {
				done = true;
			}
		}
		else {
			XmlContentHandler resultHandler = contentHandler;
			if (validate) {

				XmlTreeNodeValidatorFactory factory;
				if (sm_traceVal) {
					factory = new StateDrivenXmlTreeNodeValidatorFactory(new XmlContentTracer(contentHandler), errorHandler);
				}
				else {
					factory = new StateDrivenXmlTreeNodeValidatorFactory(contentHandler, errorHandler);
				}
				XmlContentHandler validator = factory.newXmlContentValidator(
                    new SmComponentProviderExWrapper(mCompProvider),
                    expectedOutput);

				if (sm_traceRawTrans) {
					resultHandler = new XmlContentTracer(validator);
				}
				else {
					resultHandler = validator;
				}
			}
			try {
				if (sm_useTransResult) {
					transformer.transform(EMPTY_NODE_BUCKET,
										  new XmlContentResult(resultHandler, OutputConfigTermination.getInstance()));
				}
				else {
					transformer.transform(EMPTY_DOCUMENT_FOCUS, resultHandler, OutputConfigTermination.getInstance());
				}
			}
			catch (TransformerException e) {
				errorHandler.fatalError(e);
			}
			finally {
				done = true;
			}
		}
	}

	public void assertNoErrors() throws SAXException {
		errorHandler.assertNoErrors();
	}
   class SmComponentProviderExWrapper implements SmComponentProvider {
      SmComponentProviderEx m_compProviderEx;
      public SmComponentProviderExWrapper(SmComponentProviderEx compProviderEx) {
         m_compProviderEx = compProviderEx;
      }
      public SmType getType(ExpandedName name) {
         SmType retVal = null;
         try {
            retVal = m_compProviderEx.getType(name);
         }
         catch (SmGlobalComponentNotFoundException e) {
            // do nothing, just return null...
         }
         return retVal;
      }

      public SmElement getElement(ExpandedName name) {
         SmElement retVal = null;
         try {
            retVal = m_compProviderEx.getElement(name);
         }
         catch (SmGlobalComponentNotFoundException e) {
            // do nothing, just return null...
         }
         return retVal;
      }

      public SmAttribute getAttribute(ExpandedName name) {
         SmAttribute retVal = null;
         try {
            retVal = m_compProviderEx.getAttribute(name);
         }
         catch (SmGlobalComponentNotFoundException e) {
            // do nothing, just return null...
         }
         return retVal;
      }
   }
}
