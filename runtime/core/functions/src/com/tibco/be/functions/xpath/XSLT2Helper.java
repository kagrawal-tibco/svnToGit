package com.tibco.be.functions.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;

import org.genxdm.processor.xpath.v20.XQExecuteToolkit;
import org.genxdm.processor.xpath.v20.shared.util.UnaryIterable;
import org.genxdm.typed.TypedContext;
import org.genxdm.typed.variant.ItemIterable;
import org.genxdm.xpath.v20.err.ExprException;
import org.genxdm.xs.SchemaComponentCache;
import org.genxdm.xslt.v20.SchemaResolver;
import org.genxdm.xslt.v20.Transform;
import org.genxdm.xslt.v20.Transformer;

import com.tibco.be.model.functions.FunctionsCatalogVisitor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Variable;
import com.tibco.be.model.functions.VariableList;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.rdf.XiNodeBuilder;
import com.tibco.be.util.TemplatesArgumentPair;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.genxdm.processor.xslt.XSLTransformBuilder;
import com.tibco.genxdm.processor.xslt.XSLTransformBuilderErrorThrower;
import com.tibco.xml.cxf.ootb.runtime.OOTBCustomFunctionsImpl;
import com.tibco.xml.cxf.runtime.impl.CustomXPathFunctionGroup;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.datamodel.XiNode;

public class XSLT2Helper {

	static {
		// with BW 6.5.1, null variables get converted to an empty string (from XiTypedModel::getValue)
		// setting this property forces the previous functionality to maintain a null
		// NOTE: This is set in both XSLT2Helper and XPath2Helper to ensure it is set before any mappings
        System.setProperty("bw.xml.typedmodel.oldgetvalue", "true");
    }

    // a language toolkit: xqexecutetoolkit
    private static XQExecuteToolkit toolkit = new XQExecuteToolkit();
    private static ConcurrentHashMap<String, Transform> transformCache = new ConcurrentHashMap<>();
    
	public static void doTransform(TemplatesArgumentPair tap, XiNode[] nodes, String xslt, VariableList varlist,
			AbstractSequenceHandler sh, Entity entity) throws ExprException, IOException {

        // start by initializing the bits that we need:
        // a typed context
        TypedContext<XiNode, XmlAtomicValue> context = BESchemaComponentCacheManager.getInstance().getTypedContext();
        
        // schema parser // needs to be initialized?
                        // probably needs a schemacatalog/catalogresolver pair,
                       //  even though all the schemas will be handed over. eager resolution, remember?
//        schemaParser = new W3cXmlSchemaParser(); 
        // validator factory
//        ValidatorFactory<XiNode, XmlAtomicValue> valFactory = new ValidatorFactory<XiNode, XmlAtomicValue>(context);
        Transform transform = getTransform(/*tap,*/ xslt, context, entity);

        Map<QName,ItemIterable<XiNode,XmlAtomicValue>> paramMap = new HashMap<QName, ItemIterable<XiNode,XmlAtomicValue>>();
        XiNode paramN = null;
		
		try {
			setupParams(tap, nodes, xslt, varlist, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Transformer transformer = transform.newTransformer();
//		try { // BE-23395 : do not catch this error, allow it to propagate up so that it is properly logged/handled
			transformer.transform(paramN, paramMap, context, sh, context);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	private static class BESchemaResolver implements SchemaResolver {

		private static BESchemaResolver fInstance;
		public static synchronized BESchemaResolver getInstance() {
			if (fInstance == null) {
				fInstance = new BESchemaResolver();
			}
			return fInstance;
		}
		
		@Override
		public SchemaComponentCache resolveSchema(String arg0, String arg1,
				String arg2) {
			return BESchemaComponentCacheManager.getInstance().getCache();
		}
		
	}
	
	private static Transform getTransform(/*TemplatesArgumentPair tap, */String xslt, TypedContext<XiNode,XmlAtomicValue> context, Entity entity) throws ExprException, IOException {
		Transform transform = transformCache.get(xslt);
		if (transform == null) {
	        SchemaResolver resolver = BESchemaResolver.getInstance();
			// a transformbuilder, and the error catcher.
	        XSLTransformBuilder<XiNode, XmlAtomicValue> xsltFactory = new XSLTransformBuilder<XiNode, XmlAtomicValue>(context, resolver); // the resolver seems necessary (see BE-22041)
//	        XSLTransformBuilder<XiNode, XmlAtomicValue> xsltFactory = new XSLTransformBuilder<XiNode, XmlAtomicValue>(context); // might need to use overload that takes SchemaResolver?
			
	        addCustomFunctions(xsltFactory);
			
	        xsltFactory.setErrorHandler(XSLTransformBuilderErrorThrower.getInstance());
	        ArrayList<String> parms = new ArrayList<String>();
	        String xsltTemplate = XSTemplateSerializer.deSerialize(xslt, parms, null);
//	        String xsltTemplate = tap.getXSLT(); // get the template from the already created TAP, as deserialize takes time
	        ByteArrayInputStream bis = new ByteArrayInputStream(xsltTemplate.getBytes("UTF-8"));
	        transform = xsltFactory.prepareTransform(bis, URI.create(entity.getType()), toolkit);
	        transformCache.putIfAbsent(xslt, transform);
		}
		return transform;
	}

	private static void addCustomFunctions(final XSLTransformBuilder<XiNode,XmlAtomicValue> xsltFactory) {
		final BECustomFunctionImpls beCustomFunctionImpls = new BECustomFunctionImpls();
		xsltFactory.setFunctionSigns(XPath2Helper.BE_CUSTOM_NAMESPACE, beCustomFunctionImpls);
		xsltFactory.setFunctionImpls(XPath2Helper.BE_CUSTOM_NAMESPACE, beCustomFunctionImpls);
		
		final CustomXPathFunctionGroup tibGroup = new CustomXPathFunctionGroup(XPath2Helper.TIB_CUSTOM_NAMESPACE, new OOTBCustomFunctionsImpl());
		xsltFactory.setFunctionSigns(XPath2Helper.TIB_CUSTOM_NAMESPACE, tibGroup);
		xsltFactory.setFunctionImpls(XPath2Helper.TIB_CUSTOM_NAMESPACE, tibGroup);
		
		try {
			Iterator<String> catalogNames = FunctionsCatalog.getINSTANCE().catalogNames();
			while (catalogNames.hasNext()) {
				String catName = (String) catalogNames.next();
				FunctionsCategory catalog = FunctionsCatalog.getINSTANCE().getCatalog(catName);
				catalog.accept(new FunctionsCatalogVisitor() {
					
					@Override
					public boolean visit(FunctionsCategory category) {
						ExpandedName name = category.getName();
						String str = name.namespaceURI == null ? name.localName : name.namespaceURI+"."+name.localName;
						str = str.replaceAll("\\.", "-");
						xsltFactory.setFunctionSigns(XPath2Helper.BE_CUSTOM_NAMESPACE+str, beCustomFunctionImpls);
						xsltFactory.setFunctionImpls(XPath2Helper.BE_CUSTOM_NAMESPACE+str, beCustomFunctionImpls);
						return true;
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setupParams(TemplatesArgumentPair tap, XiNode[] inputNodes, String xslt, VariableList varlist,
			Map<QName, ItemIterable<XiNode, XmlAtomicValue>> paramMap)
			throws Exception {
        if (inputNodes != null) {
            for (int i=0; i < inputNodes.length; i++) {
                XiNode inputNode = inputNodes[i];
                if(inputNodes[i] == null)
                    continue;
                ExpandedName name = inputNode.getName();
        		final ItemIterable<XiNode, XmlAtomicValue> varItemIt = new ItemIterable.NodeIterable<XiNode, XmlAtomicValue>(new UnaryIterable<XiNode>(inputNode));
				paramMap.put(new QName(name.localName), varItemIt);
            }
            return;
        }
		List<String> vars = null;
		if (tap == null) {
			ArrayList<String> params = new ArrayList<String>();
			String xsltTemplate = XSTemplateSerializer.deSerialize(xslt, params, null);
			Source templateSource = TraxSupport.makeTransformerSource(xsltTemplate);
			vars = TraxSupport.getParamNamesForTemplateSource(templateSource);
		} else {
			vars = tap.getParamNames();
		}
        Iterator<String> itr = vars.iterator();
        while (itr.hasNext()) {
        	String variable = itr.next();
        	Variable var = varlist.getVariable(variable);
            if (var != null) {
                XiNode xiNode = XiNodeBuilder.makeXiNode(var);
        		final ItemIterable<XiNode, XmlAtomicValue> varItemIt = new ItemIterable.NodeIterable<XiNode, XmlAtomicValue>(new UnaryIterable<XiNode>(xiNode));
				paramMap.put(new QName(var.getName()), varItemIt);
            }
        }
	}

}
