package com.tibco.be.ws.functions;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.ws.functions.util.WebstudioFunctionUtils;
import com.tibco.be.ws.scs.ISCSIntegration;
import com.tibco.be.ws.scs.SCSIntegrationFactory;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelExportProvider;
import com.tibco.cep.decision.table.provider.excel.WebStudioDTExcelImportProvider;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "WS.Decision",
        synopsis = "Functions to work with RuleFunction & Decision Table EMF models.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.WS.Decision", value=true))

public class WebstudioDecisionTableFunctions {
	
    private static String STUDIO_TOOLS_PATH = "/studio/bin/studio-tools";
	
    @com.tibco.be.model.functions.BEFunction(
            name = "createRuleFunctionEMFObject",
            synopsis = "",
            signature = "Object createRuleFunctionEMFObject(String projectName, String rulefunctionContents, String extension, String scsIntegrationType, String repoRootURL)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Name of the Project."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionContents", type = "String", desc = "Rule function contents."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extension", type = "String", desc = "Extension of resource. Valid value is - rulefunction"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "The type of SCS integration"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "Base repo URL for the source control system."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Ear Path.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "EObject representing the RuleFunction."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a RuleFunction EMF model object of the RTI.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static Object createRuleFunctionEMFObject(String projectName, String rulefunctionContents, String extension, String scsIntegrationType, String repoRootURL, String earPath) {
        try {
        	ISCSIntegration scsIntegration = SCSIntegrationFactory.INSTANCE.getSCSIntegrationClass(scsIntegrationType);
        	
        	ByteArrayInputStream bis = new ByteArrayInputStream(rulefunctionContents.getBytes("UTF-8"));
            if (CommonIndexUtils.RULEFUNCTION_EXTENSION.equals(extension)) {
                RulesASTNode astNode = (RulesASTNode) CommonRulesParserManager.parseRuleInputStream(projectName, bis, true);
                RuleCreatorASTVisitor aSTVisitor = new RuleCreatorASTVisitor(true, true, projectName);
                astNode.accept(aSTVisitor);
                //Get compilable object
                Compilable compilable = aSTVisitor.getRule();
                if (compilable instanceof RuleFunction) {
                	RuleFunction rulefunction = (RuleFunction) compilable;
                	Symbols symbols = rulefunction.getSymbols();
                	for (Symbol symbol : symbols.getSymbolList()){
                        String typeExtension =
                                WebstudioFunctionUtils.resolveSymbolTypeExtension(scsIntegration, repoRootURL, projectName, symbol, earPath);
                        if (typeExtension != null)
                        	symbol.setTypeExtension(typeExtension.toUpperCase());
                	}
                }
                return aSTVisitor.getRule();
            } else {
                throw new IllegalArgumentException(String.format("[%s] Not a Rule Function extension. Valid Extension value is 'rulefunction'", extension));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }		
	}
	
    @com.tibco.be.model.functions.BEFunction(
            name = "isVirtualRuleFunction",
            synopsis = "",
            signature = "boolean isVirtualRuleFunction(String rulefunctionEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionEMFObject", type = "Object", desc = "RuleFunction EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the rule function is virtual, false other-wise."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if the rule function is virtual, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static boolean isVirtualRuleFunction(Object rulefunctionEMFObject) {
        if (!(rulefunctionEMFObject instanceof RuleFunction)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a RuleFunction");
        }
        RuleFunction ruleFunction = (RuleFunction) rulefunctionEMFObject;
        return ruleFunction.isVirtual();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionName",
            synopsis = "",
            signature = "String getRuleFunctionName(String rulefunctionEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionEMFObject", type = "Object", desc = "RuleFunction EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Name of the rule function."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the name of the rule function.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionName(Object rulefunctionEMFObject) {
        if (!(rulefunctionEMFObject instanceof RuleFunction)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a RuleFunction");
        }
        RuleFunction ruleFunction = (RuleFunction) rulefunctionEMFObject;
        return ruleFunction.getName();
    }

    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionFolder",
            synopsis = "",
            signature = "String getRuleFunctionFolder(String rulefunctionEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionEMFObject", type = "Object", desc = "RuleFunction EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Folder of the rule function."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the folder of the rule function.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionFolder(Object rulefunctionEMFObject) {
        if (!(rulefunctionEMFObject instanceof RuleFunction)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a RuleFunction");
        }
        RuleFunction ruleFunction = (RuleFunction) rulefunctionEMFObject;
        return ruleFunction.getFolder();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionProject",
            synopsis = "",
            signature = "String getRuleFunctionProject(String rulefunctionEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionEMFObject", type = "Object", desc = "RuleFunction EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Project of the rule function."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the project of the rule function.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionProject(Object rulefunctionEMFObject) {
        if (!(rulefunctionEMFObject instanceof RuleFunction)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a RuleFunction");
        }
        RuleFunction ruleFunction = (RuleFunction) rulefunctionEMFObject;
        return ruleFunction.getOwnerProjectName();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionReturnType",
            synopsis = "",
            signature = "String getRuleFunctionReturnType(String rulefunctionEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionEMFObject", type = "Object", desc = "RuleFunction EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Return-type of the rule function."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the return-type of the rule function.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionReturnType(Object rulefunctionEMFObject) {
        if (!(rulefunctionEMFObject instanceof RuleFunction)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a RuleFunction");
        }
        RuleFunction ruleFunction = (RuleFunction) rulefunctionEMFObject;
        return ruleFunction.getReturnType();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionSymbols",
            synopsis = "",
            signature = "Object[] getRuleFunctionSymbols(String rulefunctionEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "rulefunctionEMFObject", type = "Object", desc = "RuleFunction EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "An array of rule function symbols."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns an array of rule function symbols.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static Object[] getRuleFunctionSymbols(Object rulefunctionEMFObject) {
        if (!(rulefunctionEMFObject instanceof RuleFunction)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a RuleFunction");
        }
        RuleFunction ruleFunction = (RuleFunction) rulefunctionEMFObject;
        EList<Symbol> symbolList = ruleFunction.getSymbols().getSymbolList();
        Object[] symbols = (symbolList != null ? symbolList.toArray() : new Object[0]);
        return symbols;
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionSymbolAlias",
            synopsis = "",
            signature = "String getRuleFunctionSymbolAlias(String symbolEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolEMFObject", type = "Object", desc = "RuleFunction Symbol EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Alias of the rule function symbol."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the alias of the rule function symbol.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionSymbolAlias(Object symbolEMFObject) {
        if (!(symbolEMFObject instanceof Symbol)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a Symbol");
        }
        Symbol symbol = (Symbol) symbolEMFObject;
        return symbol.getIdName();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionSymbolPath",
            synopsis = "",
            signature = "String getRuleFunctionSymbolPath(String symbolEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolEMFObject", type = "Object", desc = "RuleFunction Symbol EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Path of the rule function symbol."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the path of the rule function symbol.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionSymbolPath(Object symbolEMFObject) {
        if (!(symbolEMFObject instanceof Symbol)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a Symbol");
        }
        Symbol symbol = (Symbol) symbolEMFObject;
        return symbol.getType();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getRuleFunctionSymbolType",
            synopsis = "",
            signature = "String getRuleFunctionSymbolType(String symbolEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolEMFObject", type = "Object", desc = "RuleFunction symbol EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Type of the rule function symbol."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the type of the rule function symbol.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static String getRuleFunctionSymbolType(Object symbolEMFObject) {
        if (!(symbolEMFObject instanceof Symbol)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a Symbol");
        }
        Symbol symbol = (Symbol) symbolEMFObject;
        return symbol.getTypeExtension();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "isRuleFunctionSymbolArray",
            synopsis = "",
            signature = "boolean isRuleFunctionSymbolArray(String symbolEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbolEMFObject", type = "Object", desc = "RuleFunction symbol EMF object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "true if the rule function symbol is an array, false other-wise."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns true if the rule function symbol is an array, false other-wise.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static boolean isRuleFunctionSymbolArray(Object symbolEMFObject) {
        if (!(symbolEMFObject instanceof Symbol)) {
            throw new IllegalArgumentException("Invalid argument. Argument should be a Symbol");
        }
        Symbol symbol = (Symbol) symbolEMFObject;
        return symbol.isArray();
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createDecisionTableEMFObject",
            synopsis = "",
            signature = "Object createDecisionTableEMFObject(String tableModelXML)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableModelXML", type = "String", desc = "Table Model XML.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Decision Table EObject."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates Decision Table (TableImpl) EObject from the XML model.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
	public static Object createDecisionTableEMFObject(String tableModelXML) {
        if (tableModelXML == null) {
            throw new IllegalArgumentException("Argument table model XML cannot be null");
        }
		
		EObject eObject;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(tableModelXML.getBytes("UTF-8"));
			XMLResource.XMLMap xmlMap = new XMLMapImpl();
			xmlMap.setNoNamespacePackage(DtmodelPackage.eINSTANCE);
			Map<String, XMLResource.XMLMap> options = new HashMap<String, XMLResource.XMLMap>();
			options.put(XMLResource.OPTION_XML_MAP, xmlMap);
			eObject = CommonIndexUtils.deserializeEObject(bais, options);
			if (eObject instanceof Table) {
				return eObject;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getVRFPath",
            synopsis = "",
            signature = "String getVRFPath(String tableModelXML)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableModelXML", type = "String", desc = "Table Model XML.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "VRF Path."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the VRF Path.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String getVRFPath(String tableModelXML) {
        if (tableModelXML == null) {
            throw new IllegalArgumentException("Argument table model XML cannot be null");
        }
		
		EObject eObject;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(tableModelXML.getBytes("UTF-8"));
			XMLResource.XMLMap xmlMap = new XMLMapImpl();
			xmlMap.setNoNamespacePackage(DtmodelPackage.eINSTANCE);
			Map<String, XMLResource.XMLMap> options = new HashMap<String, XMLResource.XMLMap>();
			options.put(XMLResource.OPTION_XML_MAP, xmlMap);			
			eObject = CommonIndexUtils.deserializeEObject(bais, options);
			if (eObject instanceof Table) {
				return ((Table)eObject).getImplements();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;    	    	
    }
    
    @com.tibco.be.model.functions.BEFunction(
            name = "serializeDecisionTableEMFObject",
            synopsis = "",
            signature = "Object serializeDecisionTableEMFObject(Object tableEObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableEObject", type = "Object", desc = "Decision Table EMF Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Serialized XML byte stream of Decision Table."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Serializes decision Table EObject to XML byte stream.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    @SuppressWarnings("unchecked")
	public static Object serializeDecisionTableEMFObject(Object tableEObject) {
		Object obj;
		if (!(tableEObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}

		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		options.put(XMLResource.OPTION_FORMATTED, Boolean.FALSE);
		
		Table tableModel = (Table) tableEObject;
		XMLHelperImpl xmlHelper = new XMLHelperImpl();
		ArrayList<Table> arrayList = new ArrayList<Table>();
		arrayList.add(tableModel);
		try {
			String xml = XMLHelperImpl.saveString(options, arrayList, "UTF-8", xmlHelper);
			obj = xml.getBytes("UTF-8");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	
		return obj;
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getDecisionTableArguments",
            synopsis = "",
            signature = "String[] getDecisionTableArguments(Object tableEObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableEObject", type = "Object", desc = "Decision Table EMF Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Array of Arguments alias"),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets the decision table arguments alias.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String[] getDecisionTableArguments(Object tableEObject) {
		if (!(tableEObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}

		Table tableModel = (Table) tableEObject;		
		EList<Argument> arguments = tableModel.getArgument();
		String[] args = new String[arguments.size()];
		int index = 0;
		for (Argument arg : arguments) {
			args[index] = arg.getProperty().getAlias();
			index++;
		}
		
		return args;
    }	

    @com.tibco.be.model.functions.BEFunction(
            name = "getDecisionTableArgumentDetails",
            synopsis = "",
            signature = "String[] getDecisionTableArgumentDetails(Object tableEObject, String argumentAlias)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableEObject", type = "Object", desc = "Decision Table EMF Object."),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "argumentAlias", type = "String", desc = "Argumet alias.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Argument details"),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets the decision table argument details.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static String[] getDecisionTableArgumentDetails(Object tableEObject, String argumentAlias) {
		if (!(tableEObject instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}

		Table tableModel = (Table) tableEObject;		
		EList<Argument> arguments = tableModel.getArgument();
		for (Argument arg : arguments) {
			if (arg.getProperty().getAlias().equals(argumentAlias)) {
				String[] properties = new String[4];
				properties[0] = arg.getDirection();
				properties[1] = arg.getProperty().getResourceType().getLiteral();
				properties[2] = arg.getProperty().getPath();
				properties[3] = String.valueOf(arg.getProperty().isArray());
				return properties;
			}	
		}
		
		return null;
    }	
    
    @com.tibco.be.model.functions.BEFunction(
            name = "createEntityEMFObject",
            synopsis = "",
            signature = "Object createEntityEMFObject(String entityModelXML)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityModelXML", type = "String", desc = "Decision Table EMF Model XML.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Decision Table EMF Object"),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Deserializes decision Table XML to EObject.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )        
	public static Object createEntityEMFObject(String entityModelXML) {
        if (entityModelXML == null) {
            throw new IllegalArgumentException("Argument entityModelXML cannot be null");
        }
		
		EObject eObject;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(entityModelXML.getBytes("UTF-8"));
			eObject = CommonIndexUtils.deserializeEObject(bais);
			if ((eObject instanceof Concept) || (eObject instanceof Event)) {
				return eObject;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getSuperEntityPath",
            synopsis = "",
            signature = "String getSuperEntityPath(Object entityEMFObject)",
            params = {
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityEMFObject", type = "Object", desc = "Concept/Event EMF Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Path of the Super entity"),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns the Path to the Super entity.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
	public static String getSuperEntityPath(Object entityEMFObject){

		if (entityEMFObject instanceof Concept) {
			Concept concept = (Concept) entityEMFObject;
			return concept.getSuperConceptPath();
		}
		else if (entityEMFObject instanceof Event) {
			Event event = (Event) entityEMFObject;
			return event.getSuperEventPath();
		} else {
			throw new IllegalArgumentException("Parameter should be an instance of Concept / Event");
		}		
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "createEntityPropertiesList",
            synopsis = "",
            signature = "Object createEntityPropertiesList()",
            params = {},
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "New ArrayList to populate entity properties."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Creates a new ArrayList to populate entity properties.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )     
	public static Object createEntityPropertiesList(){
		return (new ArrayList<PropertyDefinition>()); 	
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "populateEntityProperties",
            synopsis = "",
            signature = "void populateEntityProperties(Object propertiesObj, Object entityEMFObject)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertiesObj", type = "Object", desc = "Object to populate Properties of the Concept/Event."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "entityEMFObject", type = "Object", desc = "Concept/Event EMF Object.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "New ArrayList to populate entity properties."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Populates the the propertiesObj object with the entity properties.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    @SuppressWarnings("unchecked")
	public static void populateEntityProperties(Object propertiesObj, Object entityEMFObject){

		if (!(propertiesObj instanceof List)) {
			throw new IllegalArgumentException("Parameter should be an instance of Concept");
		}

		List<PropertyDefinition> properties = (List<PropertyDefinition>) propertiesObj;
		
		if (entityEMFObject instanceof Concept) {
			Concept concept = (Concept) entityEMFObject;
			properties.addAll(concept.getProperties());
		}
		else if (entityEMFObject instanceof Event) {
			Event event = (Event) entityEMFObject;
			properties.addAll(event.getProperties());
		} else {
			throw new IllegalArgumentException("Parameter should be an instance of Concept / Event");
		}				
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "getDomainPathsForProperty",
            synopsis = "",
            signature = "String[] getDomainPathsForProperty(Object allPropertiesObject, String propertyName, String scsIntegrationType, String repoRootURL, String projectName)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "allPropertiesObject", type = "Object", desc = "All properties of Concept/Event."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "propertyName", type = "String", desc = "Name of the Concept/Event Property."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsIntegrationType", type = "String", desc = "SCS Integration Impl type."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "repoRootURL", type = "String", desc = "SCS Repository URL."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project Name.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "New ArrayList to populate entity properties."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Returns array of Domain Paths for the Concept/Event property.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )
    @SuppressWarnings("unchecked")
	public static String[] getDomainPathsForProperty(Object allPropertiesObject, String propertyName, String scsIntegrationType, String repoRootURL, String projectName) {

		if (allPropertiesObject == null) {
			throw new IllegalArgumentException("Parameter allPropertiesObject should not be NULL");
		}
		if (propertyName == null) {
			throw new IllegalArgumentException("Parameter propertyName should not be NULL");
		}
		
		List<PropertyDefinition> allProperties;
		if (allPropertiesObject instanceof List) {
			allProperties = (List) allPropertiesObject;
		} 
		else {
			throw new IllegalArgumentException("Parameter allPropertiesObject should be an instance of List");
		}				

		try {
			List<String> domainPathList = new ArrayList<String>();
			String[] domainPaths;
			if (allProperties != null) {
				for (PropertyDefinition property :  allProperties) {
					if (propertyName.equalsIgnoreCase(property.getName())) {
						EList<DomainInstance> domainInstances = property.getDomainInstances();
						for (DomainInstance domainInstance : domainInstances) {
							domainPathList.add(domainInstance.getResourcePath());	
						}
						break;
					}	
				}		
			}	
			domainPaths = new String[domainPathList.size()];
			return domainPathList.toArray(domainPaths);
			
		} catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "validateDecisionTable",
            synopsis = "",
            signature = "String validateDecisionTable(String scsRootURL, String projectName, String archivePath, String extendedClasspath, " +
            											" String tempFilePath, String tableModelXML)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "rootURL", type = "String", desc = "SCS Root URL."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project name."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "archivePath", type = "String", desc = "Project archive path."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "extendedClasspath", type = "String", desc = "Extended classpath."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "tempFilePath", type = "String", desc = "DT Temp file."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableModelXML", type = "String", desc = "Table Model XML.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "New ArrayList to populate entity properties."),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Validates Decision Table specified by tempFilePath.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )     
	public static String validateDecisionTable(String scsRootURL, String projectName, String archivePath, String extendedClasspath, String tempFilePath, String tableModelXML) {

    	if (projectName == null) {
            throw new IllegalArgumentException("Argument projectName cannot be null");
        }
		String cmdOutput = null;				
    	String beHome = System.getProperty(SystemProperty.BE_HOME.getPropertyName());
    	if(beHome != null) {    	
    		String studioToolsExecPath = beHome + STUDIO_TOOLS_PATH;
    		String projectPath = scsRootURL + "/" + projectName;
			try {
				List<String> validateDTCommand = new ArrayList<String>();
	
				validateDTCommand.add(studioToolsExecPath);
				validateDTCommand.add("--propFile");
				validateDTCommand.add(studioToolsExecPath + ".tra");
				validateDTCommand.add("-dt");
				validateDTCommand.add("validateWSTable");
				validateDTCommand.add("-r");
				validateDTCommand.add(scsRootURL);
				validateDTCommand.add("-p");
				validateDTCommand.add(projectName); 
				validateDTCommand.add("-t");
				validateDTCommand.add(tempFilePath);
	
				if (archivePath != null) {
					validateDTCommand.add("-e");
					validateDTCommand.add(archivePath);
				}
	
				if (extendedClasspath != null && extendedClasspath.length() > 0) {
					extendedClasspath = extendedClasspath.replaceAll("\\\\", "/");
	
					validateDTCommand.add("-cp");
					validateDTCommand.add(extendedClasspath);
				}
	
				cmdOutput = WebstudioFunctionUtils.executeCommand(validateDTCommand, projectPath, extendedClasspath);
			} catch(Exception e){
				throw new RuntimeException(e);
			}
	
			if (cmdOutput != null) {
				cmdOutput = StringUtils.substringBetween(cmdOutput, "<DTValidationErrors>", "</DTValidationErrors>");
				cmdOutput = "<errors>" + cmdOutput + "</errors>";
			}
	
			deleteDTTempFile(tempFilePath);
    	} else {
    		throw new RuntimeException(String.format("BE_HOME not set"));
    	}
    	
		return cmdOutput;
    }
                  
    @com.tibco.be.model.functions.BEFunction(
            name = "writeDTContentsToTempFile",
            synopsis = "",
            signature = "void writeDTContentsToTempFile(String tempLocation, String tempFileName, String dtContents)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "tempFilePath", type = "String", desc = "Project name."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "dtContents", type = "String", desc = "Decsion Table content")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "", desc = ""),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Writes the Decision Table contents to a Temp file, for CLI validation",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )	
	public static void writeDTContentsToTempFile(String tempFilePath, String dtContents) {
        File tempFile = new File(tempFilePath);

        if (tempFile.exists() && tempFile.isDirectory()) {
            throw new RuntimeException(String.format("Temp File %s is a directory", tempFile));
        }
        if (!tempFile.isAbsolute()) {
            throw new RuntimeException(String.format("File path specified by %s needs to be absolute", tempFile));
        }
        if (!tempFile.getParentFile().exists()) {
        	tempFile.getParentFile().mkdirs();
        }
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempFile);
            fos.write(dtContents.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                	//Ignore	
                }
            }
        }        
	}

    @com.tibco.be.model.functions.BEFunction(
            name = "exportDecisionTableToExcel",
            synopsis = "",
            signature = "void exportDecisionTableToExcel(String projectName, String excelFilePath, Object tableModelObj)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsRootURL", type = "String", desc = "SCS root URL."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project name."),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "EAR file Path"),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "tableModelObj", type = "String", desc = "Table Model EObj")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "byte array of Excel contents"),
            version = "5.1.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Exports the Decision Table contents to a Excel and returns the excel contents.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )    
    public static Object exportDecisionTableToExcel(String scsRootURL, String projectName, String earPath, Object tableModelObj) {
		if (!(tableModelObj instanceof Table)) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", Table.class.getName()));
		}
		Table table = (Table) tableModelObj; 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			WebstudioFunctionUtils.loadProjectIndex(scsRootURL, projectName, earPath);
			ExcelExportProvider provider = new ExcelExportProvider(projectName, null, "DecisionTable", table);
			provider.setUseColumnAlias(true);			
			provider.exportWorkbook(baos);
			
			Collection<ValidationError> excelVldErrorCollection = provider.getExcelVldErrorCollection();
			if(excelVldErrorCollection.size() > 0 ) {
				StringBuffer buf = new StringBuffer();
				buf.append("Invalid Excel file (");
				int size = excelVldErrorCollection.size();
				int ctr = 1;
				buf.append(size);
				buf.append(" errors): ");
				for (ValidationError validationError : provider.getExcelVldErrorCollection()) {
					buf.append(validationError.getErrorMessage());
					if (ctr < size) {
						buf.append(", ");
					} else {
						buf.append('.');
					}
					ctr++;
				}
				throw new RuntimeException(buf.toString()); 
			} else {
				return baos.toByteArray();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					//Ignore
				}
			}			
		}
	}

    @com.tibco.be.model.functions.BEFunction(
        name = "importDecisionTableFromExcel",
        synopsis = "",
        signature = "Object importDecisionTableFromExcel(String scsRootURL, String projectName, String earPath, Object tableModelObj)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "scsRootURL", type = "String", desc = "SCS root URL."),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "Project name."),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "EAR file Path"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "dtName", type = "String", desc = "DT Name"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfPath", type = "String", desc = "VRF Path"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "importData", type = "String", desc = "Excel Data")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Table EModel"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Imports the Decision Table contents from a Excel and returns the DT Model.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )     
    public static Object importDecisionTableFromExcel(String scsRootURL, String projectName, String earPath, String dtName, String vrfPath, Object importData) {
		if (!(importData instanceof byte[])) {
			throw new IllegalArgumentException(String.format("Argument should be of type [%s]", byte[].class.getName()));
		}
		byte[] buff = (byte[]) importData;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	Table tableEModel = null;
    	boolean isVRF = true;
		try {
			WebstudioFunctionUtils.loadProjectIndex(scsRootURL, projectName, earPath);
			Compilable compilable  = CommonIndexUtils.getRule(projectName, vrfPath, ELEMENT_TYPES.RULE_FUNCTION);
			RuleFunction rulefunction = null;
			if (compilable instanceof RuleFunction) {
				rulefunction = (RuleFunction) compilable;
			}
			if (rulefunction != null && rulefunction.isVirtual()) {
				ByteArrayInputStream bais = new ByteArrayInputStream(buff);
				WebStudioDTExcelImportProvider provider = new WebStudioDTExcelImportProvider(projectName, vrfPath, rulefunction, dtName);
				tableEModel = provider.importDT(bais);
				List<String> errors = provider.getValidationErrors();
				if (errors.isEmpty()) {
					return tableEModel;
				} else {
					throw new RuntimeException(StringUtils.join(errors, "; "));
				}
			} else {
				isVRF = false;
				throw new Exception();
			}
		} catch (Exception ex) {
			if (!isVRF) {
				throw new RuntimeException("Selected RuleFunction is not a Virtual RuleFunction.");
			}
			throw new RuntimeException("Error Importing Decision Table from Excel.", ex);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					//Ignore
				}
			}			
		}
	}
    
    @com.tibco.be.model.functions.BEFunction(
            name = "getVRFCodeGenDirectoryName",
            synopsis = "",
            signature = "String getVRFCodeGenDirectoryName(String vrfName, String returnType, Object[] symbols)",
            params = {
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "vrfName", type = "String", desc = "VRF Name"),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "returnType", type = "String", desc = "VRF Return type"),
            	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "symbols", type = "String", desc = "VRF symbols"),
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "VRF Code gen directory name"),
            version = "5.2.0",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets the VRF code gen directory name.",
            cautions = "",
            fndomain = {ACTION},
            example = ""
        )     
	    public static String getVRFCodeGenDirectoryName(String vrfName, String returnType, Object[] symbols) {
			try {
	            StringBuilder sb = new StringBuilder();
	            returnType = (returnType != null && returnType.trim().isEmpty()) ? null : returnType;
	            sb.append(returnType);
	            sb.append(vrfName);
	            for(int i = 0; i < symbols.length; i++) {
	                String type = symbols[i].toString();
	                if(type == null || type.trim().isEmpty()) type = void.class.getName();
	                sb.append(type);
	            }
	            String name = ModelNameUtil.escapeIdentifier(sb.toString()) + "$";
	            if(name.length() > 85) { //See ModelNameUtil.isNameOversize()
	                name = "";
	                String retType = returnType;
	                if(retType == null) {
	                    name += null;
	                } else {
	                    RDFUberType rdfType = RDFTypes.getType(retType);
	                    if(rdfType != null && (RDFTypes.BOOLEAN == rdfType || RDFTypes.DOUBLE == rdfType || RDFTypes.INTEGER == rdfType || RDFTypes.LONG == rdfType)) {
	                        name += rdfType.toString();
	                    }
	                }
	                name += vrfName;
	                name = ModelNameUtil.escapeIdentifier(name) +  "$oversizeName";	            	
	            }
	            return name;
			} catch (Exception ex) {
				throw new RuntimeException("Error getting VRF code gen directory.", ex);
			}    
		}
    
	
    /**
     * @param tempFilePath
     */
    private static void deleteDTTempFile(String tempFilePath) {
        File tempFile = new File(tempFilePath);

        if (!tempFile.exists()) {
        	//Ignore
        }
        if (tempFile.exists() && tempFile.isDirectory()) {
            throw new RuntimeException(String.format("Temp File %s is a directory", tempFile));
        }
        if (!tempFile.isAbsolute()) {
            throw new RuntimeException(String.format("File path specified by %s needs to be absolute", tempFile));
        }
        
       	if (!tempFile.delete()) {
       		System.out.println("File not deleted.");
       	}
	}        
}
