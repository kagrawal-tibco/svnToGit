package com.tibco.cep.studio.core.util.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xsd.XSDComplexTypeContent;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDCompositor;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDParticleContent;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.impl.XSDSchemaImpl;

import com.tibco.be.parser.CompileErrors;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.be.util.BECustomFunctionHelper;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.manager.GlobalVariablesMananger;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.xmodel.schema.INativeTypeConsts;
import com.tibco.xml.xmodel.xpath.Coercion;
import com.tibco.xml.xmodel.xpath.type.XType;
import com.tibco.xml.xmodel.xpath.type.XTypeFactory;

public class MapperXSDUtils {
	private static BECustomFunctionHelper customFunctionHelper = new BECustomFunctionHelper();
	private static final String[] PRIMITIVE_NAMES = { "int", "long", "double", "boolean", "String", "DateTime", "null", CompileErrors.unknownTypeName(), "void", "Object" };
	static private final String ELEMENT = "element"; //$NON-NLS-1$
	static private final String TYPE = "type"; //$NON-NLS-1$
	static private final String MODEL_GROUP = "modelGroup"; //$NON-NLS-1$

	public static List<EObject> getSourceElements(MapperInvocationContext context) {
		List<EObject> sourceElements = new ArrayList<EObject>();

		GlobalVariablesProvider provider = GlobalVariablesMananger.getInstance().getProvider(context.getProjectName());
		XSDTerm gvarsTerm = convertGlobalVariables(provider, false);
		if (gvarsTerm != null) {
			sourceElements.add(gvarsTerm);
		}
		List<VariableDefinition> definitions = context.getDefinitions();
		sourceElements.addAll(definitions);
		return sourceElements;
	}
	
	public static XSDTerm convertGlobalVariables(
			GlobalVariablesProvider provider, boolean includeDebuggerVars) {
		XSDFactory factory = XSDFactory.eINSTANCE;
		XSDSchema schema = factory.createXSDSchema();
		XSDElementDeclaration term = factory.createXSDElementDeclaration();
		schema.getContents().add(term);
		term.setName("globalVariables");
		
		XSDComplexTypeDefinition gvarTypeDefinition = factory.createXSDComplexTypeDefinition();
		gvarTypeDefinition.setName("GlobalVars");
		term.setTypeDefinition(gvarTypeDefinition);
		
	    XSDModelGroup modelGroup = factory.createXSDModelGroup();
	    modelGroup.setCompositor(XSDCompositor.SEQUENCE_LITERAL);
	    XSDParticle particle = factory.createXSDParticle();
	    particle.setMinOccurs(1);
	    particle.setMaxOccurs(1);
	    particle.setContent(modelGroup);
	    gvarTypeDefinition.setContent(particle);

		Map<String, XSDModelGroup> gvarContainers = new HashMap<String, XSDModelGroup>();
		if (provider == null) {
			return term;
		}
		Collection<GlobalVariableDescriptor> variables = provider.getVariables();
		for (GlobalVariableDescriptor gvar : variables) {
			XSDElementDeclaration gvarTerm = factory.createXSDElementDeclaration();
			String name = gvar.getName();
			gvarTerm.setName(name);
			String type = gvar.getType();
			if ("Password".equals(type)) {
				continue;
			}
			XSDTypeDefinition typeDef = getXSDType(type);
			gvarTerm.setTypeDefinition(typeDef);
			
			XSDParticle gvarParticle = factory.createXSDParticle();
			gvarParticle.setContent(gvarTerm);

			String path = gvar.getPath();
			int idx = path.lastIndexOf('/');
			if (idx != -1 && path.length() > 1) {
				String parentPath = path.substring(0, idx);
				XSDModelGroup parentGroup = getParentGroup(gvarContainers, parentPath, modelGroup);
				parentGroup.getParticles().add(gvarParticle);
			} else {
				modelGroup.getParticles().add(gvarParticle);
			}
		}
		
		if (includeDebuggerVars) {
			XSDModelGroup debuggerGroup = createModelGroup(modelGroup, "Debugger");
			
		    addRequiredElement(debuggerGroup, "sessionCounter", "Integer");
		    
		    XSDModelGroup profileGroup = createModelGroup(debuggerGroup, "profile");
		    addRequiredElement(profileGroup, "name", "String");
		    addRequiredElement(profileGroup, "beHome", "String");
		    addRequiredElement(profileGroup, "hostName", "String");
		    addRequiredElement(profileGroup, "portNumber", "String");
		    addRequiredElement(profileGroup, "workingDir", "String");
		    addRequiredElement(profileGroup, "repoUrl", "String");
		    
		}

		return term;
	}

	private static void addRequiredElement(XSDModelGroup modelGroup,
			String elName, String type) {
		XSDFactory factory = XSDFactory.eINSTANCE;
	    XSDParticle elParticle = factory.createXSDParticle();
	    elParticle.setMinOccurs(1);
	    elParticle.setMaxOccurs(1);
		modelGroup.getParticles().add(elParticle);
		
		XSDElementDeclaration elDeclaration = factory.createXSDElementDeclaration();
		elDeclaration.setName(elName);
		XSDTypeDefinition elTypeDefinition = getXSDType(type);
		elDeclaration.setTypeDefinition(elTypeDefinition);
		elParticle.setContent(elDeclaration);
	}

	private static XSDModelGroup createModelGroup(XSDModelGroup parentGroup, String groupName) {
		XSDFactory factory = XSDFactory.eINSTANCE;
		XSDElementDeclaration debuggerEl = factory.createXSDElementDeclaration();
		debuggerEl.setName(groupName);
		XSDComplexTypeDefinition debuggerType = factory.createXSDComplexTypeDefinition();
		debuggerType.setName(groupName);
		debuggerEl.setTypeDefinition(debuggerType);
		XSDModelGroup debuggerGroup = factory.createXSDModelGroup();
	    XSDParticle debugParticle = factory.createXSDParticle();
	    debugParticle.setMinOccurs(1);
	    debugParticle.setMaxOccurs(1);
	    debugParticle.setContent(debuggerGroup);
	    debuggerType.setContent(debugParticle);
	    if (parentGroup != null) {
	    	XSDParticle grpPart = factory.createXSDParticle();
	    	grpPart.setContent(debuggerEl);
	    	parentGroup.getParticles().add(grpPart);
	    }
	    return debuggerGroup;
	}

	private static XSDModelGroup getParentGroup(
			Map<String, XSDModelGroup> gvarContainers, String parentPath, XSDModelGroup baseGroup) {
		XSDModelGroup parentGroup = gvarContainers.get(parentPath);
		if (parentGroup == null) {
			XSDFactory factory = XSDFactory.eINSTANCE;
			String segment = new Path(parentPath).lastSegment();
			XSDElementDeclaration parentEl = factory.createXSDElementDeclaration();
			parentEl.setName(segment);
			
			XSDComplexTypeDefinition pTypeDef = factory.createXSDComplexTypeDefinition();
			pTypeDef.setName(segment);
			parentEl.setTypeDefinition(pTypeDef);

			XSDParticle parentGroupPart = factory.createXSDParticle();
			parentGroupPart.setMinOccurs(1);
			parentGroupPart.setMaxOccurs(1);
			pTypeDef.setContent(parentGroupPart);
			
		    parentGroup = factory.createXSDModelGroup();
		    parentGroup.setCompositor(XSDCompositor.SEQUENCE_LITERAL);
			parentGroupPart.setContent(parentGroup);
		    gvarContainers.put(parentPath, parentGroup);

			int idx = parentPath.lastIndexOf('/');
			if (idx > 0) {
				String newParPath = parentPath.substring(0, idx);
				XSDModelGroup newParGroup = getParentGroup(gvarContainers, newParPath, baseGroup);
				XSDParticle grpParticle = factory.createXSDParticle();
				grpParticle.setContent(parentEl);
				newParGroup.getParticles().add(grpParticle);
			} else {
				XSDParticle grpParticle = factory.createXSDParticle();
				grpParticle.setContent(parentEl);
				baseGroup.getParticles().add(grpParticle);
			}
		}
		return parentGroup;
	}

	public static Entity getTargetEntity(MapperInvocationContext context) {
		List receivingParams = XSTemplateSerializer.getReceivingParms(context.getXslt());
		if (receivingParams.size() == 0) {
			return null;
		}

		List<VariableDefinition> definitions = context.getDefinitions();
		String entityPath = (String) receivingParams.get(0);
		if (entityPath.length() > 0 && entityPath.charAt(0) == '$') {
			String varName = entityPath.substring(1);
			for (VariableDefinition variableDefinition : definitions) {
				if (varName.equals(variableDefinition.getName())) {
					entityPath = ModelUtils.convertPackageToPath(variableDefinition.getType());
					break;
				}
			}
		}
		Entity entity = IndexUtils.getEntity(context.getProjectName(), entityPath);
		return entity;
	}

	public static XSDTerm convertVariableDefinitionToXSDTerm(
			VariableDefinition varDef) {
		XSDSchema schema = XSDFactory.eINSTANCE.createXSDSchema();
		XSDElementDeclaration term = XSDFactory.eINSTANCE.createXSDElementDeclaration();
		schema.getContents().add(term);
		term.setName(varDef.getName());
		XSDTypeDefinition typeDef = getXSDType(varDef);
		term.setTypeDefinition(typeDef);
		if (varDef.isArray()) {
			XSDModelGroup group = XSDFactory.eINSTANCE.createXSDModelGroup();
			XSDParticle particle = XSDFactory.eINSTANCE.createXSDParticle();
			particle.setMinOccurs(0);
			particle.setMaxOccurs(Integer.MAX_VALUE);
			term.setName("elements");
			particle.setTerm(term);
			group.getParticles().add(particle);
			return group;
		}
		return term;
	}
	
	private static XSDTypeDefinition getXSDType(VariableDefinition varDef) {
		String type = varDef.getType();
		return getXSDType(type);
	}
	
	private static XSDTypeDefinition getXSDType(String type) {
		XSDSchema schemaForSchema = XSDSchemaImpl.getSchemaForSchema("http://www.w3.org/2001/XMLSchema");
		XSDTypeDefinition typeDefinition = schemaForSchema.resolveTypeDefinition(getSchemaType(type));
		return typeDefinition;
	}
	
	public static String getSchemaType(String type) {
		if ("String".equals(type)) {
			return "string";
		}
		if ("DateTime".equals(type)) {
			return "dateTime";
		}
		if ("Object".equals(type)) {
//			return "anyType";
		}
		return type.toLowerCase();
	}

	public static void printXSDSchema(XSDSchema schema) {
		EList<XSDElementDeclaration> elementDeclarations = schema.getElementDeclarations();
		for (XSDElementDeclaration xsdElementDeclaration : elementDeclarations) {
			int tabs = 0;
			printXSDElementDecl(xsdElementDeclaration, tabs);
		}
		System.out.println();
	}
	
	private static void printXSDElementDecl(
			XSDElementDeclaration xsdElementDeclaration, int tabs) {
		printTabs(tabs);
		println("(XSDElementDeclaration): "+xsdElementDeclaration.getName());
		printTabs(++tabs);
		print(">Type: ");
		XSDTypeDefinition typeDefinition = xsdElementDeclaration.getTypeDefinition();
		printXSDTypeDefinition(typeDefinition, tabs+1);
	}

	private static void printXSDTypeDefinition(
			XSDTypeDefinition typeDefinition, int tabs) {
//		printTabs(tabs);
		if (typeDefinition instanceof XSDComplexTypeDefinition) {
			XSDComplexTypeDefinition ct = (XSDComplexTypeDefinition) typeDefinition;
			XSDComplexTypeContent content = ct.getContent();
			println("Complex type::");
			printTabs(tabs);
			print(">Complex Content::");
			printXSDParticle((XSDParticle)content, tabs+1);
		} else if (typeDefinition instanceof XSDSimpleTypeDefinition) {
			XSDSimpleTypeDefinition st = (XSDSimpleTypeDefinition) typeDefinition;
			XSDSimpleTypeDefinition primitiveTypeDefinition = st.getPrimitiveTypeDefinition();
			if (primitiveTypeDefinition == null) {
				print("null primitive type def");
			} else {
				print(st.getClass().getName()+"::"+primitiveTypeDefinition.getName());
			}
		} else {
			println("Some other xsd type def");
		}
		if (typeDefinition.getBaseType() != null && typeDefinition.getBaseType() != typeDefinition) {
			printTabs(++tabs);
			print("Base type def:");
			printXSDTypeDefinition(typeDefinition.getBaseType(), tabs+1);
		}
	}

	private static void printXSDParticle(XSDParticle particle, int tabs) {
		if (particle == null) {
			println("Null particle");
			return;
		}
		XSDParticleContent content = particle.getContent();
		println("Particle: "+":: (min)"+particle.getMinOccurs()+" (max)"+particle.getMaxOccurs());
		printTabs(tabs);
		print(">Particle content:: ");
		printXSDTerm((XSDTerm)content, tabs+1);
	}

	public static void printXSDTerm(XSDTerm content, int tabs) {
		if (content instanceof XSDElementDeclaration) {
			printXSDElementDeclaration((XSDElementDeclaration)content, tabs);
		} else if (content instanceof XSDModelGroup) {
			printXSDModelGroup((XSDModelGroup)content, tabs);
		}
	}

	private static void printXSDModelGroup(XSDModelGroup content, int tabs) {
		println(content.getClass().getName()+"::");
		printTabs(tabs);
		print(">particles: ");
		EList<XSDParticle> particles = content.getParticles();
		for (XSDParticle xsdParticle : particles) {
//			printTabs(tabs);
			printXSDParticle(xsdParticle, tabs+1);
		}
	}

	private static void printXSDElementDeclaration(
			XSDElementDeclaration content, int tabs) {
		println(content.getClass().getName()+"::"+content.getName());
		printTabs(++tabs);
		print(">element type: ");
		printXSDTypeDefinition(content.getTypeDefinition(), tabs+1);
	}

	private static void printTabs(int tabs) {
		for (int i=0; i<tabs; i++) {
			System.out.print("\t");
		}
	}
	
	private static void print(String msg) {
		System.out.print(msg);
	}
	
	private static void println(String msg) {
		System.out.println(msg);
	}
	
	public static boolean isPrimitiveType(String symbolType) {
		for (int i=0; i<PRIMITIVE_NAMES.length; i++) {
			if (symbolType.equals(PRIMITIVE_NAMES[i])) {
				return true;
			}
		}
		return false;
	}
	
	public static Class<?> primitiveToClass(java.lang.String typeName) {
		if ("int".equals(typeName)) {
			return int.class;
		} else if ("boolean".equals(typeName)) {
			return boolean.class;
		} else if ("double".equals(typeName)) {
			return double.class;
		} else if ("String".equals(typeName)) {
			return String.class;
		} else if ("Object".equals(typeName)) {
			return Object.class;
		} else if ("DateTime".equals(typeName)) {
			return Calendar.class;
		}
		return Void.class;
	}

    public static XType convertClassToXType(Class clazz)
    {
        if (String.class.isAssignableFrom(clazz))
        {
            return XTypeFactory.createAtomic(INativeTypeConsts.NativeString);
        }
        else if (Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz))
        {
            return XTypeFactory.createAtomic(INativeTypeConsts.NativeBoolean);
        }
        else if (Number.class.isAssignableFrom(clazz) ||
            int.class.isAssignableFrom(clazz) ||
            byte.class.isAssignableFrom(clazz) ||
            long.class.isAssignableFrom(clazz) ||
            short.class.isAssignableFrom(clazz) ||
            float.class.isAssignableFrom(clazz) ||
            double.class.isAssignableFrom(clazz) ||
            char.class.isAssignableFrom(clazz))
        {
        	return XTypeFactory.createAtomic(INativeTypeConsts.NativeDouble);
        }
/*        else if (NodeIterator.class.isAssignableFrom(clazz))
        {
            return SimpleXTypes.ANY_NODE;
        }*/
        else if (Void.class.isAssignableFrom(clazz) || void.class.isAssignableFrom(clazz))
        {
            return null;//?SMDT.VOID;
        }
//        else if (clazz.isAssignableFrom(Node.class))
//        {
//            throw new RuntimeException("LAMb: FINISH ME!");
//        }

        throw new RuntimeException("Unable to convert class " + clazz.getName() + " to an XType.");
    }

	public static void updateCustomFunctions(String projectName) {
		try {
			customFunctionHelper.updateCustomFunctions(FunctionsCatalogManager.getInstance().getCustomRegistry(projectName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String[] parseComment(String comment){
    	String[] result = new String[3];
    	int resultIndex = 0;
        int start = 0;
        for (int index = 0; index < comment.length(); index++){
        	if (comment.charAt(index) == ',') {
            	result[resultIndex++] = comment.substring(start, index).trim();
                start = index + 1;
            }
        }
       	result[resultIndex] = comment.substring(start).trim();
    	return result;
	}
	
	public static List<Coercion> getCoercionsFromXPath(String xslt) {
		List<Coercion> coercions = new ArrayList<Coercion>();
		if (!xslt.contains("<!--START COERCIONS-->")) {
			return coercions;
		}
		int idxStart = xslt.indexOf("<!--START COERCIONS-->");
		int idxEnd = xslt.indexOf("<!--END COERCIONS-->");
		String coercionsStr = xslt.substring(idxStart+"<!--START COERCIONS-->".length(), idxEnd);
		String[] split = coercionsStr.split("-->");
		for (String coer : split) {
			String[] result = parseComment(coer);
			int code = getComponentCode(result[1]);
			String qname = result[2];
			String  xpathStr = result[0];
			if (xpathStr.startsWith("<!--")) {
				xpathStr = xpathStr.substring(5);
			}
			Coercion coercion = new Coercion(xpathStr, result[2], code);
			coercions.add(coercion);
		}
		return coercions;
	}

	/**
	 * Convert the strings Element, Type, ModelGroup to the code for such
	 */
	private static int getComponentCode(String componentType) {
		int code = 1;
		if (componentType.equals(ELEMENT))
			code = Coercion.COERCION_ELEMENT;
		else if (componentType.equals(TYPE))
			code = Coercion.COERCION_TYPE;
		else if (componentType.equals(MODEL_GROUP))
			code = Coercion.COERCION_GROUP;
		else
			throw new RuntimeException();
		return code;
	}
	
	public static String getCoercionTypeString(int coercionType) {
		if (coercionType == Coercion.COERCION_ELEMENT)
			return ELEMENT;
		else if (coercionType == Coercion.COERCION_TYPE)
			return TYPE;
		else
			return MODEL_GROUP;
	}

}
