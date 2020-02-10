package com.tibco.cep.studio.core.util.mapper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JRootPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.parser.codegen.CommonMapperCoreUtils;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.be.util.shared.ModelConstants;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.AVTUtilities;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultStylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.ReadFromXSLT;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.xpath.Coercion;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.Lexer;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.TypeChecker;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.XPathCheckArguments;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.mapper.MapperMetricUtils.FieldInfo;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.mapper.ui.data.xpath.XTypeChecker;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CodeErrorMessage;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.CompletionProposal;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.DefaultCodeErrorMessage;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.DefaultCompletionProposal;
import com.tibco.cep.studio.mapper.ui.jedit.errcheck.ErrCheckErrorList;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.build.MutableModelGroup;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.helpers.SmComponentProviderExOnSmNamespaceProvider;
import com.tibco.xml.schema.impl.SmNamespaceProviderImpl;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.TargetNamespaceProvider;
import com.tibco.xml.tns.impl.TargetNamespaceCache;

/**
 * 
 * @author sasahoo
 *
 */
public class MapperCoreUtils extends CommonMapperCoreUtils {

    private static final int OPTIONAL_FIELD = 0;
	private static final int REQUIRED_FIELD = 1;
	private static final String CREATE_UPDATE_METRIC_NAME = "createOrUpdateMetric";

	public static HashMap getPrefix2Namespaces(NamespaceContextRegistry nsmapper)  {
        HashMap map = new HashMap();
        try {
        Iterator itr = nsmapper.getPrefixes();
        while (itr.hasNext()) {
            String pfx = (String)itr.next();
            String ns = nsmapper.getNamespaceURIForPrefix(pfx);
            map.put(pfx, ns);
        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static XTypeChecker makeTypeChecker(final SmSequenceType expectedType) {
        return new XTypeChecker()
        {
            public ErrorMessageList check(SmSequenceType gotType, TextRange errorMessageTextRange) {
                if (expectedType==null) {
                    return ErrorMessageList.EMPTY_LIST;
                }
                XPathCheckArguments args = new XPathCheckArguments();
                return TypeChecker.checkSerialized(gotType,expectedType,errorMessageTextRange,args);
            }

            public SmSequenceType getBasicType() {
                return expectedType;
            }
        };
    }
    
    public static VariableDefinitionList makeInputVariableDefinitions(EMFTnsCache cache, MapperInvocationContext context) {
		
		VariableDefinitionList vdlist = new VariableDefinitionList();
		if (context == null) {
			return vdlist;
		}

		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(context.getProjectName());
		SmElement gsmElement = provider.toSmElement(false);
		if (gsmElement != null) {
			SmSchema schema = gsmElement.getSchema();
			SmSequenceType create = SmSequenceTypeFactory.create(SmSupport.getElement(schema, "GlobalVariables"));
			vdlist.add(new VariableDefinition(ExpandedName.makeName(GlobalVariablesProvider.NAME), create));
		}
		
		for (com.tibco.cep.studio.core.index.model.VariableDefinition variableDefinition : context.getDefinitions()) {
			VariableDefinition varDef = getVariableDefinitionFromTypeName(context.getProjectName(), variableDefinition);
			if (varDef != null) {
				vdlist.add(varDef);
				continue;
			}
			Object objType = ElementReferenceResolver.resolveVariableDefinitionType(variableDefinition);
			if (objType instanceof EntityElement) {
				Entity entity = ((EntityElement) objType).getEntity();
				try {
					SmElement smElement = getSmElementFromPath(context.getProjectName(), entity.getFullPath());
					if (smElement == null) {
						continue;
					}
					SmSchema schema = smElement.getSchema();
					SmSequenceType create = SmSequenceTypeFactory.create(SmSupport.getElement(schema, entity.getName()));
					vdlist.add(new VariableDefinition(ExpandedName.makeName(variableDefinition.getName()), create));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// TODO : resolve primitive types to XType
				vdlist.add(new VariableDefinition(ExpandedName.makeName(variableDefinition.getName()), SmSequenceTypeFactory.createSimple(getSmType(variableDefinition.getType()))));
			}
		}
		return vdlist;
	}

	public static TemplateEditorConfiguration createTemplateEditorConfiguration(
			MapperInvocationContext context, EMFTnsCache cache) {
		VariableDefinitionList vdl = MapperCoreUtils.makeInputVariableDefinitions(cache, context);
		TemplateEditorConfiguration tec = new TemplateEditorConfiguration();

		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		ArrayList coercionSet = new ArrayList();
		TemplateBinding template = getBinding(context.getXslt(), coercionSet);
		List receivingParams = XSTemplateSerializer.getReceivingParms(context.getXslt());
		if (context.getFunction() != null) {
			if (receivingParams.size() == 0) {
				SmElement inputType = ((PredicateWithXSLT)context.getFunction()).getInputType();
				tec.setExpectedOutput(SmSequenceTypeFactory.create(inputType));
			} else {
				String entityPath = (String) receivingParams.get(0);
				MutableElement element = buildElement(context, entityPath);
				tec.setExpectedOutput(SmSequenceTypeFactory.create(element));
			}
		}

		StylesheetBinding sb = (StylesheetBinding)template.getParent();
		
		if (sb != null) {
			BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
			for (int i=0; i<nd.length; i++) {
				nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
			}
		}

		ExprContext ec = new ExprContext(vdl, StudioCorePlugin.getUIAgent(context.getProjectName()).getFunctionResolver()).createWithNamespaceMapper(nsm);
		TargetNamespaceProvider targetNamespaceProvider =
			((TargetNamespaceCache) StudioCorePlugin.getCache(context.getProjectName())).getNamespaceProvider();
		SmNamespaceProvider smNamespaceProvider =
			new SmNamespaceProviderImpl(targetNamespaceProvider);
//		ec = ec.createWithInputAndOutputSchemaProvider(smNamespaceProvider);
		ec = ec.createWithInputAndOutputSchemaAndComponentProvider(smNamespaceProvider, new SmComponentProviderExOnSmNamespaceProvider(smNamespaceProvider));
		tec.setExprContext(ec);
		tec.setImportRegistry(new DefaultImportRegistry());
		tec.setStylesheetResolver(new DefaultStylesheetResolver());
		tec.setBinding(template);
//		CoercionSet cs = new CoercionSet();
		tec.setCoercionSet(deserializeCoercionSet(coercionSet));
		return tec;
	}
	
	
	public static TemplateEditorConfiguration createTemplateEditorConfiguration(
			MapperInvocationContext context, VariableDefinitionList vdl, EMFTnsCache cache, SmSequenceType outPutType) {
		TemplateEditorConfiguration tec = new TemplateEditorConfiguration();

		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		ArrayList coercionSet = new ArrayList();
		TemplateBinding template = getBinding(context.getXslt(), coercionSet);
		List receivingParams = XSTemplateSerializer.getReceivingParms(context.getXslt());
		tec.setExpectedOutput(outPutType);

		StylesheetBinding sb = (StylesheetBinding)template.getParent();
		
		if (sb != null) {
			BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
			for (int i=0; i<nd.length; i++) {
				nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
			}
		}

		ExprContext ec = new ExprContext(vdl, StudioCorePlugin.getUIAgent(context.getProjectName()).getFunctionResolver()).createWithNamespaceMapper(nsm);
		TargetNamespaceProvider targetNamespaceProvider =
			((TargetNamespaceCache) StudioCorePlugin.getCache(context.getProjectName())).getNamespaceProvider();
		SmNamespaceProvider smNamespaceProvider =
			new SmNamespaceProviderImpl(targetNamespaceProvider);
//		ec = ec.createWithInputAndOutputSchemaProvider(smNamespaceProvider);
		ec = ec.createWithInputAndOutputSchemaAndComponentProvider(smNamespaceProvider, new SmComponentProviderExOnSmNamespaceProvider(smNamespaceProvider));
		tec.setExprContext(ec);
		tec.setImportRegistry(new DefaultImportRegistry());
		tec.setStylesheetResolver(new DefaultStylesheetResolver());
		tec.setBinding(template);
//		CoercionSet cs = new CoercionSet();
		tec.setCoercionSet(deserializeCoercionSet(coercionSet));
		return tec;
	}

    public static ArrayList serializeCoercionSet(CoercionSet cs) {
        if(cs != null && cs.getCount() > 0){
            ArrayList clist = new ArrayList();
            ArrayList allCoercions = cs.getCoercionList();
            for (int i=0; i < allCoercions.size(); i++){
                Coercion coe = (Coercion) allCoercions.get(i);
                String coeStr = String.valueOf(coe.getType()) + ',' + coe.getTypeOrElementName() + ',' +  coe.getXPath();
                if (coe.getOccurrence() != null) {
                	coeStr+= "," + coe.getOccurrence().getMinOccurs()+":"+coe.getOccurrence().getMaxOccurs();
                }
                clist.add(coeStr);
            }
            return clist;
        }
        return null;
    }

    private static CoercionSet deserializeCoercionSet(ArrayList coercionStrList) {
        CoercionSet cs = new CoercionSet();
        for(int i=0; i < coercionStrList.size(); i++) {
            String[] coerionProp = ((String)coercionStrList.get(i)).split(",");
            Coercion c = null;
            if (coerionProp.length == 4) {
            	String[] card = coerionProp[3].split(":");
            	int min = Integer.parseInt(card[0]);
            	int max = Integer.parseInt(card[1]);
            	c = new Coercion(coerionProp[2], coerionProp[1], Integer.parseInt(coerionProp[0]), SmCardinality.create(min, max));
            } else {
            	c = new Coercion(coerionProp[2], coerionProp[1], Integer.parseInt(coerionProp[0]));
            }
            cs.add(c);
        }
        return cs;
    }

	public static MutableElement buildElement(MapperInvocationContext context, String entityPath) {
    	PredicateWithXSLT function = (PredicateWithXSLT) context.getFunction();
    	if (entityPath != null && entityPath.startsWith("$")) {
    		// lookup entity path from context
    		String varName = entityPath.substring(1);
    		List<com.tibco.cep.studio.core.index.model.VariableDefinition> definitions = context.getDefinitions();
    		for (com.tibco.cep.studio.core.index.model.VariableDefinition varDef : definitions) {
				if (varName.equals(varDef.getName())) {
					entityPath = ModelUtils.convertPackageToPath(varDef.getType());
					break;
				}
			}
    	}
        SmElement refEle = MapperCoreUtils.getSmElementFromPath(context.getProjectName(), entityPath);
        if (refEle == null) {
        	// TODO : report error
        	System.out.println("Could not find element "+entityPath+" in cache.");
        	return null;
        }
        MutableSchema schema = SmFactory.newInstance().createMutableSchema();
        MutableType type = MutableSupport.createType(schema, "fn");

//        type.setDerivationMethod(SmComponent.EXTENSION);
        SmType fnType = function.getInputType().getType();
        copyType(fnType, type);

        //for views metric only, create a new type
        MutableType metricType = MutableSupport.createType(schema, refEle.getName());
        if (CREATE_UPDATE_METRIC_NAME.equals(function.getInputType().getName())) {
        	createMetricType(context, entityPath, metricType);
        }
        
        MutableElement fElement = MutableSupport.createElement(schema, function.getInputType().getName(), type);
        if ("createEvent".equals(fElement.getName())) {
        	// don't add object, attributes go under 'event' element
        	MutableSupport.addLocalElement(type, "event", refEle.getType(),0,1 );
        } else {
            if (CREATE_UPDATE_METRIC_NAME.equals(function.getInputType().getName())) {
            	//only if it is views metric, we display properties which user is allowed to update
            	MutableSupport.addLocalElement(type, "object", metricType,0,1 );
            } else {
            	MutableSupport.addLocalElement(type, "object", refEle.getType(),0,1 );
            }
        }
        return fElement;
    }

	private static void createMetricType(MapperInvocationContext context, String entityPath, MutableType metricType) {
		Concept cept = null;
		try {
			cept = (Concept) new com.tibco.cep.designtime.CommonOntologyAdapter(context.getProjectName()).getEntity(entityPath);
		} catch (Exception e) {
			StudioCorePlugin.log(e);
			return;
		}
        List<FieldInfo> computeOrderList = new ArrayList<FieldInfo>();
        Map<String, FieldInfo> fieldMap = MapperMetricUtils.generateMetricFieldInfo(cept, computeOrderList);
        for (Iterator<FieldInfo> itr = fieldMap.values().iterator(); itr.hasNext();) {
            FieldInfo fi = itr.next();
            if (fi.isSystem()) {
        		continue;
            } else if (fi.getDependingField() != null) {
                FieldInfo tmp = fieldMap.get(fi.getDependingField()[0].getPropertyName());
                if (tmp.isSystem() == false) {
                    continue;
                }
            }
    		addMetricProperty(metricType, fi.propertyName, fi.getType(), fi.isGroupBy() ? REQUIRED_FIELD:OPTIONAL_FIELD);
        }
	}

	private static void addMetricProperty(SmType type, String fieldName, int fieldType, int isRequired) {
		MutableModelGroup grp = (MutableModelGroup ) type.getContentModel();
		if (grp == null) {
			return;
		}

		Iterator<?> itr = grp.getParticles();
		while (itr.hasNext()) {
		    SmParticle particle = (SmParticle) itr.next();
		    SmParticleTerm term=particle.getTerm();
		    if (fieldName.equalsIgnoreCase(term.getName())) {
		    	return;
		    } 
		}
		MutableSchema schema = SmFactory.newInstance().createMutableSchema();
		SmElement element = MutableSupport.createElement(schema, fieldName, getSmTypeFromMetricType(fieldType));
		MutableSupport.addParticleTerm(grp, element, isRequired, 1);
	}

    private static SmType getSmTypeFromMetricType(int fieldTypeId) {
    	SmType type = null;
        RDFUberType rdfType = RDFTypes.types[fieldTypeId];
        if(rdfType instanceof RDFPrimitiveTerm) {
            if(rdfType == RDFTypes.EXCEPTION) {
                type = RDFTnsFlavor.getBaseExceptionType();
            } else {
                type = rdfType.getXSDLTerm();
            }
        }
        return type;
	}

	protected static void copyType(SmType src, MutableType dest) {
        SmModelGroup grp = src.getContentModel();
        if (grp == null) return;
        Iterator itr = grp.getParticles();
        while (itr.hasNext()) {
            SmParticle particle = (SmParticle) itr.next();
            SmParticleTerm term=particle.getTerm();
            if (!"object".equalsIgnoreCase(term.getName()) && !"event".equalsIgnoreCase(term.getName())) {
            	MutableSupport.addParticleTerm(dest, term, particle.getMinOccurrence(), particle.getMaxOccurrence());
            }

        }

    }

	public static NamespaceContextRegistry getNamespaceMapper() {
		NamespaceMapper nsmapper = new DefaultNamespaceMapper("xsd");
		((DefaultNamespaceMapper)nsmapper).addXSDNamespace();
		// CR 1-AFQC1N -- add xsl namespace explicitly
		((DefaultNamespaceMapper)nsmapper).addNamespaceURI("xsl", ReadFromXSLT.XSLT_URI);
		return nsmapper;
	}

	public static SmElement getSmElementFromVarDef(String projectName, com.tibco.cep.studio.core.index.model.VariableDefinition varDef) {
		Object objType = ElementReferenceResolver.resolveVariableDefinitionType(varDef);
		if (objType instanceof EntityElement) {
			Entity entity = ((EntityElement) objType).getEntity();
			return getSmElementFromPath(projectName, entity.getFullPath());
		}
		
		return null;
	}
	
	public static SmElement getSmElementFromPath(String projectName, String entity) {
        SmNamespaceProvider snp = StudioCorePlugin.getCache(projectName).getSmNamespaceProvider();
        SmNamespace sm =  snp.getNamespace(EMFRDFTnsFlavor.BE_NAMESPACE +  entity);
        String[] terms = entity.split("/");
        String elname = terms[terms.length - 1];
        //SmComponent c = StudioCorePlugin.getCache("FraudDetectionDashboard").getSmNamespaceProvider().getNamespace("www.tibco.com/be/ontology/Dashboards/M_DummyAccounts").getComponent(1,"M_DummyAccounts");
        
        if(sm != null) {
            return (SmElement)sm.getComponent(SmComponent.ELEMENT_TYPE, elname);
        } else {
            return null;
        }
    }

    private static VariableDefinition getVariableDefinitionFromTypeName(String projectName, com.tibco.cep.studio.core.index.model.VariableDefinition varDef) {
    	String typeName = varDef.getType();
    	String varName = varDef.getName();
    	
    	boolean isArray = varDef.isArray();
    	
//        int numBrackets = numTrailinglBracketPairs(typeName);
    	if (typeName.indexOf('.') != -1) {
    		typeName = ModelUtils.convertPackageToPath(typeName);
    	}
        
        if(!isArray) {
        	SmType smType = getSmTypeFromTypeName(projectName, typeName);
        	if (smType == null) {
        		SmElement smElement = getSmElementFromVarDef(projectName, varDef);
        		if (smElement != null) {
        			smType = smElement.getType();
        		}
        	}
        	if (smType == null) {
        		return null;
        	}
        	SmSequenceType xtype = SmSequenceTypeFactory.createElement(smType.getExpandedName(), smType, false);
            return new VariableDefinition(ExpandedName.makeName(varName), xtype);
        } else {
            //create a new type with the name of the array variable
            MutableSchema schema = SmFactory.newInstance().createMutableSchema();
            MutableType type = MutableSupport.createType(schema, varName);
//            type.setDerivationMethod(SmComponent.EXTENSION);

            //add a single element to the new type with the schema of the local variable's type
        	SmType smType = getSmTypeFromTypeName(projectName, typeName);
        	if (smType == null) {
        		SmElement smElement = getSmElementFromVarDef(projectName, varDef);
        		if (smElement != null) {
        			smType = smElement.getType();
        		}
        	}
            MutableSupport.addLocalElement(type, ModelConstants.ARRAY_ELEMENT_NAME, smType, 0, Integer.MAX_VALUE);
        
            return new VariableDefinition(ExpandedName.makeName(varName), SmSequenceTypeFactory.createElement(type));
        }
    }

    private static SmType getSmTypeFromTypeName(String projectName, String typeName) {
//        typeName = stripTrailingBracketPairs(typeName);
        SmType type = null;
        
        RDFUberType rdfType = RDFTypes.getType(typeName);
        if(rdfType == null) rdfType = RDFTypes.getType(typeName + "_Wrapper");
        
        if(rdfType instanceof RDFPrimitiveTerm) {
            if(rdfType == RDFTypes.EXCEPTION) {
                type = RDFTnsFlavor.getBaseExceptionType();
            } else {
                type = rdfType.getXSDLTerm();
            }
        } 
        else if(rdfType == RDFTypes.BASE_ENTITY) {
            type = RDFTnsFlavor.getBaseEntityType();
        }
        else if(rdfType == RDFTypes.BASE_PROCESS) {
        	type = RDFTnsFlavor.getBaseProcessType();
        }
        else if(rdfType == RDFTypes.BASE_CONCEPT) {
            type = RDFTnsFlavor.getBaseConceptType();
        }
        else if(rdfType == RDFTypes.BASE_EVENT) {
            type = RDFTnsFlavor.getBaseEventType();
        }
        else if(rdfType == RDFTypes.CONCEPT) {
            type = RDFTnsFlavor.getBaseContainedConceptType();
        }
        else if(rdfType == RDFTypes.EVENT) {
            type = RDFTnsFlavor.getBaseSimpleEventType();
        }
        else if(rdfType == RDFTypes.TIME_EVENT) {
            type = RDFTnsFlavor.getBaseTimeEventType();
        }
        else if(rdfType == RDFTypes.ADVISORY_EVENT) {
            type = RDFTnsFlavor.getBaseAdvisoryEventType();
        }
        else if(rdfType == RDFTypes.OBJECT) {
            type = rdfType.getXSDLTerm();
        }

        if(type == null) {
            SmElement ele = getSmElementFromPath(projectName, typeName);
            if(ele != null) {
                type = ele.getType();
            }
            assert(ele != null);
        }
        
        return type;
    }
    
    //returns the number of times "[]" appears at the end of the string
//    private static int numTrailinglBracketPairs(String typeName) {
//        int bracketPairCount = 0;
//        for(int ii = typeName.length() - 1; ii > 0; ii-=2){
//            if(typeName.charAt(ii - 1) == '[' && typeName.charAt(ii) ==']') {
//                bracketPairCount++;
//            } else {
//                break;
//            }
//        }
//        return bracketPairCount;
//    }
    
//    private static String stripTrailingBracketPairs(String typeName) {
//        return typeName.substring(0, typeName.length() - 2*numTrailinglBracketPairs(typeName));
//    }

    private static SmType getSmType(String type) {
    	if ("String".equals(type)) {
    		return XSDL.STRING;
    	} else if ("int".equals(type)) {
    		return XSDL.INT;
    	} else if ("boolean".equals(type)) {
    		return XSDL.BOOLEAN;
    	} 
    	
		return XSDL.STRING; // default case (?)
	}

	@SuppressWarnings("serial")
	protected static Container getSwingContainer(java.awt.Frame frame) {
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}

	/**
	 * This code was taken from XPathTextArea$MyErrorFinder::getErrors.  
	 * This method is used instead to avoid need for XPathTextArea creation
	 * to simply detect errors
	 * @param xpath -- the xpath string
	 * @param exprContext
	 * @param namespaceImporter
	 * @param typeChecker
	 * @param isAVTMode
	 * @return
	 * @throws IOException
	 */
    public static ErrCheckErrorList getErrors(String xpath, ExprContext exprContext, final NamespaceContextRegistry namespaceImporter, XTypeChecker typeChecker, boolean isAVTMode) throws IOException
    {
        ErrorMessageList syntaxErrors;
        Expr expr;
        if (!isAVTMode)
        {
            Parser ret = new Parser(Lexer.lex(xpath));

            syntaxErrors = ret.getErrorMessageList();
            expr = ret.getExpression();
        }
        else
        {
            syntaxErrors = ErrorMessageList.EMPTY_LIST; //WCETODO get syntax errors out.
            expr = AVTUtilities.parseAsExpr(xpath);
        }
        XPathTypeReport retTypeReport;
        if (xpath.length()==0) {
            retTypeReport = new XPathTypeReport();
        } else {
            // now do semantic check:
            if (exprContext!=null) { // if null, don't do any semantic checking.
                ExprContext context = exprContext;
                retTypeReport = expr.evalType(context);
                if (syntaxErrors.size()>0)
                {
                    // combine the syntax & type errors:
                    retTypeReport = new XPathTypeReport(retTypeReport.xtype,syntaxErrors.addMessages(retTypeReport.errors));
                }
                if (retTypeReport.errors.size()==0) {
                    // now do a return type check:
                    XTypeChecker checker = typeChecker;
                    if (checker!=null) // If we have a type checker...
                    {
                        ErrorMessageList typeError = checker.check(retTypeReport.xtype,new TextRange(0,xpath.length()));
                        if (typeError!=null && typeError.size()>0) {
                            // update type report:
                            retTypeReport = new XPathTypeReport(
                                retTypeReport.xtype,
                                typeError);
                        }
                    }
                }
            } else {
                // no syntax errors, nothing to check.
                retTypeReport = new XPathTypeReport();
            }
        }
        final XPathTypeReport ftypeReport = retTypeReport;

        // Copy the errors:
        ErrCheckErrorList el = new ErrCheckErrorList();
        ErrorMessage[] em = ftypeReport.errors.getMessages();
        for (int i=0;i<em.length;i++) {
            final ErrorMessage msg = em[i];
            int t = msg.getType();
            if (t==ErrorMessage.TYPE_MARKER || t==ErrorMessage.TYPE_AUTOCAST) {
                // don't show these.
                continue;
            }
            int uitype = ErrorMessage.TYPE_ERROR;
            switch (t) {
                case ErrorMessage.TYPE_ERROR:
                    uitype=CodeErrorMessage.TYPE_ERROR;
                    break;
                case ErrorMessage.TYPE_UNCHECKED:
                    uitype=CodeErrorMessage.TYPE_UNCHECKED;
                    break;
                case ErrorMessage.TYPE_WARNING:
                    uitype=CodeErrorMessage.TYPE_WARNING;
                    break;
            }
            int code = msg.getCode();
            CompletionProposal[] proposals;
            if (code==ErrorMessage.CODE_MISSING_PREFIX && exprContext!=null)
            {
                DefaultCompletionProposal proposal = new DefaultCompletionProposal("Add namespace prefix  (Alt+Ent)",msg.getTextRange(),"") {
                    public void apply(Document document) {
                        String ns = msg.getSupplementalText();
                        String suggestedPrefix = msg.getSupplementalText2();
                        String pfx = namespaceImporter.getOrAddPrefixForNamespaceURI(ns,suggestedPrefix);
                        try {
                            String original = document.getText(mTextRange.getStartPosition(),mTextRange.getLength());
                            String ln;
                            if (original.indexOf("(")>=0)
                            {
                                ln = original;
                            }
                            else
                            {
                                QName oqname = new QName(original);
                                ln = oqname.getLocalName();
                            }
                            String replacementText = pfx + ":" + ln;
                            document.remove(mTextRange.getStartPosition(),mTextRange.getLength());
                            document.insertString(mTextRange.getStartPosition(),replacementText,document.getDefaultRootElement().getAttributes());
                        } catch (BadLocationException ble) {
                            // ignore this.
                        }
                    }
                };
                proposals = new CompletionProposal[] {proposal};
            } else {
                proposals = new CompletionProposal[0];
            }

            DefaultCodeErrorMessage uimsg = new DefaultCodeErrorMessage(
                    msg.getMessage(),
                    msg.getTextRange(),
                    uitype,
                    proposals);
            el.addMessage(uimsg);
        }
        return el;
    }

}
