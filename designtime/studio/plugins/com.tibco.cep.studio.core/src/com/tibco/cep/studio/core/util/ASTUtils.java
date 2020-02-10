package com.tibco.cep.studio.core.util;

import org.eclipse.emf.ecore.EObject;

import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class ASTUtils {

	public static final int[] PRIMITIVE_TYPES = new int[] { RulesParser.StringLiteral, RulesParser.CharacterLiteral, RulesParser.DecimalLiteral, RulesParser.FloatingPointLiteral, RulesParser.HexLiteral, RulesParser.NullLiteral };
	public static final String[] PRIMITIVE_TYPES_LITERALS = new String[] { "String", "char", "decimal", "float", "hex", "null", "octal" };
	
	/**
	 * Returns the type of the argument, i.e. String, int, etc
	 * @param projectName - required if the argNode is a method call
	 * @param argNode
	 * @return
	 */
	public static String getArgumentType(String projectName, RulesASTNode argNode) {
		int type = argNode.getType();
		int resultType = -1;
		for (int i=0; i<PRIMITIVE_TYPES.length; i++) {
			if (PRIMITIVE_TYPES[i] == type) {
				resultType = i;
				break;
			}
		}
		if (resultType >= 0) {
//			int primType = PRIMITIVE_TYPES[resultType];
			return getPrimTypeAsString(resultType);
		}
		if (type == RulesParser.Identifier) {
			return getIdentifierType(projectName, argNode);
		}
		if (type == RulesParser.SIMPLE_NAME) {
			return getIdentifierType(projectName, argNode);
		}
		if (type == RulesParser.QUALIFIED_NAME) {
			return getIdentifierType(projectName, argNode);
		}
		
		if (type == RulesParser.METHOD_CALL) {
			return getMethodCallReturnType(projectName, argNode);
		}
		return null;
	}

	private static String getMethodCallReturnType(String projectName,
			RulesASTNode argNode) {
		RulesASTNode name = argNode.getChildByFeatureId(RulesParser.NAME);
		String methodName = RuleGrammarUtils.getNameAsString(name, RuleGrammarUtils.NAME_FORMAT);
		FunctionRec function = new FunctionsCatalogLookup(projectName).lookupFunction(methodName, null);
		if (function == null) {
			return null;
		}
		return function.function.getReturnClass().getName();
	}

	private static String getIdentifierType(String projectName,
			RulesASTNode argNode) {
		EObject reference = RuleGrammarUtils.getElementReference(argNode);
		if (reference == null) {
			return null;
		}
		if (reference instanceof VariableDefinition) {
			return ((VariableDefinition) reference).getType();
		}
		if (reference instanceof ElementReference) {
			Object resolvedElement = ElementReferenceResolver.resolveElement((ElementReference) reference, ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope((ElementReference) reference)));
			if (resolvedElement == null) {
				return null;
			}
			return getResolvedElementType((ElementReference) reference, resolvedElement);
		}
		return null;
	}

	private static String getResolvedElementType(ElementReference reference, Object resolvedElement) {
		if (resolvedElement instanceof FunctionRec) {
			return ((FunctionRec) resolvedElement).function.getReturnClass().getName();
		}
		if (resolvedElement instanceof VariableDefinition) {
			return ((VariableDefinition) resolvedElement).getType();
		} else if (resolvedElement instanceof RuleElement) {
			String fullName = ((RuleElement) resolvedElement).getFolder() + ((RuleElement) resolvedElement).getName();
			ModelUtils.convertPathToPackage(fullName);
			return fullName;
		} 
//		else if (qualifier instanceof PropertyDefinition) {
//			// see if this is a concept/event reference
//			PropertyDefinition property = (PropertyDefinition) qualifier;
//			if (property.getConceptTypePath() != null) {
//				String conceptPath = property.getConceptTypePath();
//				Concept qualifierConcept = CommonIndexUtils.getConcept(((Entity)property.eContainer()).getOwnerProjectName(), conceptPath);
//				return searchEntity(reference, qualifier, qualifierConcept);
//			}
//		} else if (qualifier instanceof EntityElement) {
//			Entity entity = ((EntityElement)qualifier).getEntity();
//			return searchEntity(reference, qualifier, entity);
//		} else if (qualifier instanceof ElementContainer) {
//			EList<DesignerElement> entries = ((ElementContainer) qualifier).getEntries();
//			for (DesignerElement designerElement : entries) {
//				if (designerElement.getName().equals(reference.getName())) {
//					if (!(designerElement instanceof RuleElement)) {
//						// don't resolve non-method calls to rule elements
//						return designerElement;
//					}
//				}
//			}
//		} else if (qualifier instanceof FunctionsCategory) {
//			for (Iterator iterator = ((FunctionsCategory) qualifier).all(); iterator.hasNext();) {
//				Object type = iterator.next();
//				String localName = "";
//				if (type instanceof FunctionsCategory) {
//					localName = ((FunctionsCategory) type).getName().getLocalName();
//				} else if (type instanceof JavaStaticFunction) {
//					localName = ((JavaStaticFunction) type).getName().getLocalName();
//				} else {
//					printDebug("Something else in catalog");
//				}
//				if (reference.getName().equals(localName)) {
//					return type;
//				}
//			}
//		}
		return null; // if we fall through, this could lead to improper resolution
	}

	private static String getPrimTypeAsString(int primType) {
		if (primType >= 0 && primType < PRIMITIVE_TYPES_LITERALS.length) {
			return PRIMITIVE_TYPES_LITERALS[primType];
		}
		return null;
	}

}
