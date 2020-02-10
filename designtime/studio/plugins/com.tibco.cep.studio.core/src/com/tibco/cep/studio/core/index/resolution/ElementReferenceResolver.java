package com.tibco.cep.studio.core.index.resolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.DefaultResolutionContext.ResolutionMode;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class ElementReferenceResolver {

	public static boolean fDebug = false;
	private static final String ATTR_PARENT = "parent";
	private static final String ONTOLOGY_FUNCTIONS = "Ontology Functions";
//	private static enum fReferenceTypes { Concept, Event, TimeEvent, AdvisoryEvent, PropertyDefinition, VariableDefinition, Exception };
	
	private static final String[] fPrimitiveAttributes = new String[] { "id" /* for Concepts, Events, TimeEvents, AdvisoryEvents */,
											"extId" /* for Concepts, Events, AdvisoryEvents */, 
											"length" /* for arrays/multiples */, 
											"ttl" /* for Events, TimeEvents, AdvisoryEvents */, 
											"isSet" /* for boolean */, 
											"stackTrace" /* for exceptions */,
											"cause" /* for exceptions */, 
											"errorType" /* for exceptions */,
											"message" /* for exceptions, AdvisoryEvents */,
											"category" /* for AdvisoryEvents */, 
											"type" /* for AdvisoryEvents */,
											"ruleUri" /* for AdvisoryEvents */,
											"ruleScope" /* for AdvisoryEvents */,
											"closure" /* for TimeEvents */, 
											"interval" /* for TimeEvents */, 
											"payload" /* for Events */,
											"scheduledTime" /* for TimeEvents */};
	
	static {
		Arrays.sort(fPrimitiveAttributes);
	}
	
	public Object resolveElementFromContentAssistant(ElementReference reference, IResolutionContext context, boolean contentAssistMode) {
		context.getResolvedObjects().clear();
		Object o = resolveElement(reference, context, contentAssistMode);
		if (contentAssistMode && context.getResolvedObjects().get(reference) != null) {
			List<Object> list = context.getResolvedObjects().get(reference);
			if (list.size() == 1) {
				o = list.get(0);
			} else {
				o = list;
			}
		}
		String projectName = CommonIndexUtils.getVariableContextProjectName(context.getScope());
		if (o != null && resolveGenericObjectReference(reference, projectName) != null) {
			return resolveCatalogFunction(projectName, reference);
		}
		return o;
		
	}
	
	public static Object resolveElement(ElementReference reference, IResolutionContext context) {
		return resolveElement(reference, context, false);
	}
	
	public static Object resolveElement(ElementReference reference, IResolutionContext context, boolean contentAssistMode) {
		Object element = fullResolveElement(reference, context.getScope(), context, contentAssistMode);
		if (element != null) {
			reference.setBinding(element);
			return element;
		}
		
		/*
		 * if we could not resolve this, and it has a qualifier, then find ALL
		 * definitions of the root qualifier, and try to resolve from there.
		 * This solves the problems where there is, for instance, a root 
		 * FunctionsCategory named "Common" and a ScoreCard named "Common", 
		 * and the user has an unqualified reference to the ScoreCard property,
		 * i.e. Common.id, we can resolve "id" properly.  If this is not done,
		 * the resolution will resolve "Common" as a FunctionsCategory,
		 * and therefore "id" will not be resolved.
		 */
		if (reference.getQualifier() != null) {
			printDebug("Trying to resolve from root: "+reference.getName());
			String projectName = CommonIndexUtils.getVariableContextProjectName(context.getScope());
			element = resolveFromRootQualifier(reference, projectName, contentAssistMode);
			if (element != null) {
				reference.setBinding(element);
				return element;
			}
		}
		return null;
	}
	
	public static Object resolveVariableDefinitionType(
			VariableDefinition element) {
		String type = element.getType();
		String projectName = CommonIndexUtils.getVariableContextProjectName(element);
		String folder = ModelUtils.convertPackageToPath(type);
		Object typeElement = CommonIndexUtils.getElement(projectName, folder);
		if (typeElement == null) {
			typeElement = ElementReferenceResolver.resolveNonQualifiedReference(type, projectName, true, false);
			if (typeElement == null) {
				// query IElementResolutionProviders
				IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
				for (IElementResolutionProvider provider : elementResolutionProviders) {
					typeElement = provider.resolveType(projectName, element.getType());
					if (typeElement != null) {
						return typeElement;
					}
				}
			}
		}
		return typeElement;
	}
	
	public static IResolutionContext createResolutionContext(ScopeBlock scope) {
		EObject element = getRootOfScope(scope);
		if (element instanceof RuleElement) {
			return new RuleElementResolutionContext(scope, ((RuleElement) element));
		} else if (element instanceof CompilableScope) {
			return new CompilableScopeResolutionContext(scope, ((CompilableScope) element));
		}
		return null;
	}
	
	private static Object fullResolveElement(ElementReference reference, ScopeBlock scope, IResolutionContext context, boolean contentAssistMode) {
		Object element = internalResolveElement(reference, scope, context, contentAssistMode);
		if (element != null && !contentAssistMode) {
			reference.setBinding(element);
			return element;
		}
		if (contentAssistMode) {
			if (element instanceof List) {
				return element;
			} else if (element != null) {
				context.addResolvedObject(reference, element);
				return element;
			}
			String projName = CommonIndexUtils.getVariableContextProjectName(scope);
			return backtrack(reference, scope, context, contentAssistMode, element, projName);
		}
		element = fastGlobalResolveElement(reference, scope);
		if (element != null) {
			reference.setBinding(element);
			return element;
		}
		
		return null;
	}

	private static Object fastGlobalResolveElement(ElementReference reference,
			ScopeBlock scope) {
		// if all else fails, and this reference does NOT have a qualifier,
		// find the first element that matches this name
		String projectName = CommonIndexUtils.getVariableContextProjectName(scope);
		if (reference.getQualifier() == null) {

			if (reference.isMethod()) {
				return resolveFunctionCall(null, projectName, reference);
			}

			// 'primitive' object types - Object, Concept, Event, etc
			Object element = resolveGenericObjectReference(reference, projectName);
			if (element != null) {
				return element;
			}
			
			// find first element that matches this name
			return resolveNonQualifiedReference(reference.getName(), projectName, reference.isTypeRef(), reference.isMethod() && reference.getQualifier() == null);
		}
		return null;
	}

	private static Object resolveFromRootQualifier(ElementReference reference,
			String projectName, boolean contentAssistMode) {
		
		Stack<ElementReference> refStack = getRootQualifier(reference);
		ElementReference rootRef = refStack.pop();
		List<DesignerElement> allElements = CommonIndexUtils.getAllElements(projectName, rootRef.getName());
		for (DesignerElement designerElement : allElements) {
			Stack<ElementReference> stack = (Stack<ElementReference>) refStack.clone();
			Object element = internalResolveFromRoot(designerElement, stack, projectName, contentAssistMode);
			if (element != null) {
				reference.setBinding(element);
				return element;
			}
		}

		return null;
	}

	private static Object internalResolveFromRoot(
			Object qualifier, Stack<ElementReference> refStack, String projectName, boolean contentAssistMode) {
		ElementReference reference = refStack.pop();
		Object resolvedElement = processQualifier(reference, qualifier, projectName, contentAssistMode, qualifier instanceof EntityElement);
		reference.setBinding(resolvedElement);
		if (refStack.size() == 0) {
			return resolvedElement;
		}
		return internalResolveFromRoot(resolvedElement, refStack, projectName, contentAssistMode);
	}

	private static Stack<ElementReference> getRootQualifier(ElementReference reference) {
		Stack<ElementReference> refStack = new Stack<ElementReference>();
		refStack.push(reference);
		while (reference.getQualifier() != null) {
			reference = reference.getQualifier();
			refStack.push(reference);
		}
		return refStack;
	}

	private static Object resolveFunctionCall(Object qualifier, String projectName,
			ElementReference reference) {
		if (qualifier instanceof FunctionsCategory) {
			for (Iterator iterator = ((FunctionsCategory) qualifier).all(); iterator.hasNext();) {
				Object type = iterator.next();
				String localName = "";
				if (type instanceof FunctionsCategory) {
					localName = ((FunctionsCategory) type).getName().getLocalName();
				} else if (type instanceof JavaStaticFunction) {
					localName = ((JavaStaticFunction) type).getName().getLocalName();
				} else if (type instanceof EMFModelJavaFunction) {
					localName = ((EMFModelJavaFunction) type).getName().getLocalName();
				} else {
					printDebug("Something else in catalog");
				}
				if (reference.getName().equals(localName)) {
					return type;
				}
			}
		} else if (qualifier instanceof ElementContainer) {
			EList<DesignerElement> entries = ((ElementContainer) qualifier).getEntries();
			for (DesignerElement designerElement : entries) {
				if (designerElement.getName().equals(reference.getName()) && designerElement instanceof RuleElement) {
					// only resolve to rule element
					return designerElement;
				}
			}
		}
		
		// try to use the function lookup
//		FunctionsCatalogLookup lookup = new FunctionsCatalogLookup(projectName);
//		FunctionRec fn = lookup.lookupFunction(getFullName(reference), null);
//		if (fn != null) {
//			return fn;
//		}
		
		if (qualifier instanceof EntityElement) {
			//Only if the element is of type Metric
			//Use ontology function lookup to return correct function return type
			TypeElement element = (TypeElement) qualifier;
			if (element.getElementType() == ELEMENT_TYPES.METRIC) {
				FunctionsCatalogLookup lookup = new FunctionsCatalogLookup(projectName);
				FunctionRec fn = lookup.lookupFunction(getFullName(reference), null);
				if (fn != null) {
					return fn;
				}
			}
			return qualifier;
		} else if (qualifier instanceof Entity) {
			// this is an ontology function, return the qualifier (for now)
			return qualifier;
		}
		
		Object resolvedElement = resolveRuleCall(projectName, reference);
		if (resolvedElement != null) {
			Object catalogFn = reference.getQualifier() == null ? resolveCatalogFunction(projectName, reference) : null;
			if (catalogFn != null) {
				// this is an unqualified reference and resolves to both a catalog function as well as a rule function, report ambiguous error
				List<Object> list = new ArrayList<Object>();
				list.add(resolvedElement);
				list.add(catalogFn);
				return createAmbiguousReference(list);
			}
			return resolvedElement;
		}
		resolvedElement = resolveCatalogFunction(projectName, reference);
		if (resolvedElement != null) {
			return resolvedElement;
		}
		resolvedElement = resolveNonQualifiedReference(reference.getName(), projectName, reference.isTypeRef(), reference.isMethod() && reference.getQualifier() == null);
		if (resolvedElement instanceof EntityElement) {
			// this is an ontology function, return the qualifier (for now)
			return resolvedElement;
		}
		
		return null;
	}

	public static String getFullName(ElementReference reference) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(reference.getName());
		while (reference.getQualifier() instanceof ElementReference) {
			reference = reference.getQualifier();
			buffer.insert(0, '.');
			buffer.insert(0, reference.getName());
		}
		return buffer.toString();
	}

	private static Object resolveRuleCall(String projectName,
			ElementReference reference) {
//		List<DesignerElement> allRules = CommonIndexUtils.getAllElements(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE, ELEMENT_TYPES.RULE_FUNCTION });
		// rules cannot be called directly, this can only resolve to a rule function
		List<DesignerElement> allRules = CommonIndexUtils.getAllElements(projectName, ELEMENT_TYPES.RULE_FUNCTION);
		for (DesignerElement designerElement : allRules) {
			if (designerElement.getName().equals(reference.getName())) {
				return designerElement;
			}
		}
		return null;
	}

	private static Object resolveGenericObjectReference(ElementReference ref,
			String projectName) {
		// Only resolve to generic type if reference is not part of a qualified name
		if (ref.getQualifier() != null) {
			return null;
		}
		if (ref.eContainer() instanceof ElementReference) {
//			 FIXME : Workaround for "mystring"@length attribute access, since "mystring" does not create an ElementReference
//			if ("length".equals(((ElementReference) ref.eContainer()).getName()) && ((ElementReference) ref.eContainer()).isAttRef()) {
//				if (ref.isTypeRef() || "String".equals(ref.getBinding())) {
//					return "String";
//				}
//			}
			// the container is actually the next name in the QN.
			// for instance, 'That' is the container for 'This' in
			// the name This.That
			return null;
		} else {
			// FIXME : Workaround for "mystring"@length attribute access, since "mystring" does not create an ElementReference
//			if ("length".equals(ref.getName()) && (ref.isAttRef())) {
//				return "String";
//			}
		}
		
		String name = ref.getName();
		// 'generic' object types - Object, Concept, Event
		if (RuleGrammarUtils.isGenericType(name)) {
			return name;
		}
		return null;
	}

	private static Object resolveNonQualifiedReference(
			String unqualifiedName, String projectName, boolean typeRef, boolean unqualifiedMethodCall) {
		if (unqualifiedName == null) {
			return null;
		}
		if (unqualifiedMethodCall && unqualifiedName.startsWith("schedule")) {
			unqualifiedName = unqualifiedName.substring("schedule".length());
		}
		if (unqualifiedName.indexOf('/') == -1 && unqualifiedName.indexOf('.') == -1) {
			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(projectName);
			Object entry = findIndexEntry(unqualifiedName, index);
			if (entry != null) {
				if (entry instanceof EntityElement) {
					if (typeRef || unqualifiedMethodCall) {
						return entry;
					}
				} else {
					return entry;
				}
			}
			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
			for (int i = 0; i < referencedProjects.size(); i++) {
				entry = findIndexEntry(unqualifiedName, referencedProjects.get(i));
				if (entry != null) {
					if (entry instanceof EntityElement) {
						if (typeRef) {
							return entry;
						}
					} else {
						return entry;
					}
				}
			}
		}
		return null;
	}
	
	private static Object findIndexEntry(String unqualifiedName, ElementContainer container) {
		EList<DesignerElement> entries = container.getEntries();
		for (DesignerElement designerElement : entries) {
			if (designerElement.getName().equals(unqualifiedName)) {
				if (designerElement instanceof EntityElement) {
					// this type of search only applies to EntityElements
					return designerElement;
				}
			}
			if (designerElement instanceof ElementContainer) {
				Object o = findIndexEntry(unqualifiedName, (ElementContainer) designerElement);
				if (o != null) {
					return o;
				}
			}
		}
		
		if(unqualifiedName.equals("AdvisoryEvent") 
				|| unqualifiedName.equals("Exception")
				|| unqualifiedName.equals("Concept")
				|| unqualifiedName.equals("Event")
				|| unqualifiedName.equals("SimpleEvent")
				|| unqualifiedName.equals("TimeEvent")
				|| unqualifiedName.equals("Process")
				|| unqualifiedName.equals("Entity")){
			return unqualifiedName;//Fix for getting @attributes AdvisoryEvent/Exception/Concept/Event/Entity  
		}
		
		return null;  
	}

	private static Object internalResolveElement(ElementReference reference, ScopeBlock scope, IResolutionContext resolutionContext, boolean contentAssistMode) {
		if (reference.getQualifier() != null) {
			Object qualifier = internalResolveElement(reference.getQualifier(), scope, resolutionContext, contentAssistMode);
			reference.getQualifier().setBinding(qualifier);
			String projName = CommonIndexUtils.getVariableContextProjectName(scope);
			if (qualifier != null) {
				if (qualifier instanceof List) {
					List<Object> qualifiers = (List<Object>) qualifier;
					for (Object object : qualifiers) {
						Object element = processQualifier(reference, object, projName, contentAssistMode, object instanceof EntityElement);
						if (element != null && contentAssistMode) {
							resolutionContext.addResolvedObject(reference, element);
						}
					}
					return resolutionContext.getResolvedObjects().get(reference);
				} else {
					Object element = processQualifier(reference, qualifier, projName, contentAssistMode, qualifier instanceof EntityElement && !reference.getQualifier().isAttRef());
					if (element != null && !contentAssistMode) {
						reference.setBinding(element);
						return element;
					}
					if (element != null && contentAssistMode) {
						resolutionContext.addResolvedObject(reference, element);
						element = null;
					}
				}
				return backtrack(reference, scope, resolutionContext,
						contentAssistMode, null, projName);
			} else {
				String fullPath = getFullReferencePath(reference);
				fullPath = ModelUtils.convertPackageToPath(fullPath);
				DesignerElement element = CommonIndexUtils.getElement(projName, fullPath, !contentAssistMode);
				if (element == null) {
					String elementName = fullPath.substring(1);
					List<DesignerElement> allElements = CommonIndexUtils.getAllElements(projName, elementName);
					if (allElements.size() > 0) {
						element = allElements.get(0);
					}
				}
				return element;
			}
		}
		String projectName = CommonIndexUtils.getVariableContextProjectName(scope);
		if (reference.isMethod()) {
			// return here, as method calls cannot be resolved to local vars/entities/etc
			Object obj = resolveFunctionCall(null, projectName, reference);
			if (contentAssistMode && obj != null) {
				resolutionContext.addResolvedObject(reference, obj);
			} else {
				return obj;
			}
		}
		if (scope == null) {
			return null;
		}
		if (resolutionContext.getMode() <= IResolutionContext.LOCAL_VAR_MODE) {
			resolutionContext.setMode(IResolutionContext.LOCAL_VAR_MODE);
			EList<LocalVariableDef> defs = scope.getDefs();
			for (LocalVariableDef localVariableDef : defs) {
				if (reference.getOffset() > localVariableDef.getOffset() // variable must be declared before it is used
						&& reference.getName().equals(localVariableDef.getName())) {
					printDebug("resolved (local) as "+localVariableDef);
					resolutionContext.pushQualifier(localVariableDef);
					if (contentAssistMode && localVariableDef != null) {
						resolutionContext.addResolvedObject(reference, localVariableDef);
					} else {
						return localVariableDef;
					}
				}
			}
		}
		if (scope.getParentScopeDef() != null) {
//			resolutionContext.setMode(IResolutionContext.INITIAL_MODE);
			Object resolvedElement = internalResolveElement(reference, scope.getParentScopeDef(), resolutionContext, contentAssistMode);
			if (resolvedElement != null) {
				printDebug("resolved (parent) as "+resolvedElement);
				reference.setBinding(resolvedElement);
				if (resolvedElement != null) {
					return resolvedElement;
				}
			}
		}
		if (scope.getParentScopeDef() == null) {
			if (resolutionContext.getMode() < IResolutionContext.GLOBAL_VAR_MODE) {
				resolutionContext.setMode(IResolutionContext.GLOBAL_VAR_MODE);
				List<GlobalVariableDef> globalVariables = null;
				globalVariables = resolutionContext.getGlobalVariables();
				for (GlobalVariableDef globalVariableDef : globalVariables) {
					if (reference.getName().equals(globalVariableDef.getName())) {
						printDebug("resolved (global) as "+globalVariableDef);
						resolutionContext.pushQualifier(globalVariableDef);
						if (contentAssistMode && globalVariableDef != null) {
							resolutionContext.addResolvedObject(reference, globalVariableDef);
						} else {
							return globalVariableDef;
						}
					}
				}
			}

			Object element = null;
			if (resolutionContext.getMode() < IResolutionContext.GENERIC_MODE) {
				resolutionContext.setMode(IResolutionContext.GENERIC_MODE);
				// 'generic' object types - Object, Concept, Event, String
				element = resolveGenericObjectReference(reference, projectName);
				if (element != null) {
					resolutionContext.pushQualifier(element);
					if (contentAssistMode) {
						resolutionContext.addResolvedObject(reference, element);
					} else {
						return element;
					}
				}
			}

			DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(projectName);
			if (resolutionContext.getMode() < IResolutionContext.ELEMENT_MODE) {
				resolutionContext.setMode(IResolutionContext.ELEMENT_MODE);
				// resolve top level containers
				element = searchContainer(reference, resolutionContext, index);
				if (element != null) {
					resolutionContext.pushQualifier(element);
					if (contentAssistMode) {
						resolutionContext.addResolvedObject(reference, element);
					} else {
						return element;
					}
				}
			}
			if (resolutionContext.getMode() <= IResolutionContext.SHARED_ELEMENT_MODE) {
				resolutionContext.setMode(IResolutionContext.SHARED_ELEMENT_MODE);
				// process project libraries as well
				if (index != null) {
					EList<DesignerProject> referencedIndexes = index.getReferencedProjects();
					for (int i = 0; i < referencedIndexes.size(); i++) {
						if (resolutionContext.getSubMode() > i) {
							continue;
						}
						resolutionContext.setSubMode(i+1); // the subMode tracks which referencedIndex we've already searched, for backtracking
						element = searchContainer(reference, resolutionContext, referencedIndexes.get(i));
						if (element != null) {
							resolutionContext.pushQualifier(element);
							if (contentAssistMode) {
								resolutionContext.addResolvedObject(reference, element);
							} else {
								return element;
							}
						}
					}
				}
			}
			
			if (resolutionContext.getMode() < IResolutionContext.FUNCTION_MODE) {
				resolutionContext.setMode(IResolutionContext.FUNCTION_MODE);
				element = resolveCatalogFunction(projectName, reference);
				if (element != null) {
					// this should only resolve to FunctionsCategory, as this is not a method call
					resolutionContext.pushQualifier(element);
					if (contentAssistMode) {
						resolutionContext.addResolvedObject(reference, element);
					} else {
						return element;
					}
				}
			}
			
			
			if (resolutionContext.getMode() < IResolutionContext.GLOBAL_MODE) {
				resolutionContext.setMode(IResolutionContext.GLOBAL_MODE);
				// not able to resolve from the local scope, try a global search
				printDebug("unable to resolve, try global search");
				element = resolveGlobalReference(projectName, reference, contentAssistMode);
				if (element != null) {
					resolutionContext.pushQualifier(element);
					if (contentAssistMode) {
						resolutionContext.addResolvedObject(reference, element);
					} else {
						return element;
					}
				}
			}
			if (element == null && reference.getQualifier() != null && resolutionContext.getQualifierStack().size() > 0) {
				ResolutionMode mode = (ResolutionMode) resolutionContext.getQualifierStack().pop();
				resolutionContext.setMode(mode.mode);
				resolutionContext.setSubMode(mode.sub_mode);
				return internalResolveElement(reference.getQualifier(), scope, resolutionContext, contentAssistMode);
			}
			if (contentAssistMode) {
				return resolutionContext.getResolvedObjects().get(reference);
			} else {
				return element;
			}
		}
		return null;
	}

	private static Object searchContainer(ElementReference reference,
			IResolutionContext resolutionContext, DesignerProject index) {
		if (index == null) {
			return null;
		}
		EList<DesignerElement> entries = index.getEntries();
		for (DesignerElement designerElement : entries) {
			if (designerElement.getName().equals(reference.getName()) && !reference.isArray()) {
				return designerElement;
			}
		}
		// query IElementResolutionProviders
		IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		for (IElementResolutionProvider provider : elementResolutionProviders) {
			Object resolvedObj = provider.searchContainer(reference, resolutionContext, index.getName());
			if (resolvedObj != null) {
				return resolvedObj;
			}
		}

		return null;
	}

	private static Object backtrack(ElementReference reference, ScopeBlock scope,
			IResolutionContext resolutionContext, boolean contentAssistMode,
			Object element, String projectName) {
		if (element == null && resolutionContext.getQualifierStack().size() > 0) {
			// need to backtrack, and continue searching from previous spot to properly find overlapping qualified names
			ResolutionMode mode = (ResolutionMode) resolutionContext.getQualifierStack().pop();
			resolutionContext.setMode(mode.mode);
			resolutionContext.setSubMode(mode.sub_mode);
			Object qualifier = internalResolveElement(reference.getQualifier(), scope, resolutionContext, contentAssistMode);
			if (qualifier != null) {
				element = processQualifier(reference, qualifier, projectName, contentAssistMode, qualifier instanceof EntityElement);
				if (element != null) {
					reference.setBinding(element);
					return element;
				}
			}
			if (resolutionContext.getQualifierStack().size() > 0 && scope != null) {
				if (((ResolutionMode)resolutionContext.getQualifierStack().lastElement()).mode == IResolutionContext.LOCAL_VAR_MODE) {
					return backtrack(reference, scope.getParentScopeDef(), resolutionContext, contentAssistMode, element, projectName);
				}
			}
			return backtrack(reference, scope, resolutionContext, contentAssistMode, element, projectName);
		} 
		return null;
	}

	private static Object processQualifier(ElementReference reference,
			Object qualifier, String projectName, boolean contentAssistMode, boolean isEntityRef) {
		printDebug("qualifier resolved as "+qualifier);
		if (reference.isAttRef()) {
			printDebug("reference is an attribute reference "+reference);
			return resolveAttributeReference(reference, qualifier, projectName);
		}
		if (reference.isMethod()) {
			return resolveFunctionCall(qualifier, projectName, reference);
		}
		if (qualifier instanceof VariableDefinition) {
			// now, check the type of the qualifier and use it as a base
			VariableDefinition varDef = (VariableDefinition) qualifier;
			String type = varDef.getType();
			printDebug("qualifier type is "+type);
			String projName = CommonIndexUtils.getVariableContextProjectName(varDef);
			if (reference.getQualifier() != null) {
				if (varDef.isArray() && !reference.getQualifier().isArray()) {
					return null;
				} else if (!varDef.isArray() && reference.getQualifier().isArray()) {
					return null;
				}
			}
			return processType(reference, type, projName, contentAssistMode);
		} else if (qualifier instanceof RuleElement) {
			String type = ((RuleElement) qualifier).getRule().getReturnType();
			printDebug("qualifier type is "+type);
			String projName = ((RuleElement) qualifier).getIndexName();
			return processType(reference, type, projName, contentAssistMode);
		} else if (qualifier instanceof PropertyDefinition) {
			// see if this is a concept/event reference
			PropertyDefinition property = (PropertyDefinition) qualifier;
			if (reference.getQualifier() != null) {
				if (property.isArray() && !reference.getQualifier().isArray()) {
					return null;
				} else if (!property.isArray() && reference.getQualifier().isArray()) {
					return null;
				}
			}
			if (property.getConceptTypePath() != null) {
				String conceptPath = property.getConceptTypePath();
				Concept qualifierConcept = CommonIndexUtils.getConcept(((Entity)property.eContainer()).getOwnerProjectName(), conceptPath);
				return searchEntity(reference, qualifier, qualifierConcept, false);
			}
		} else if (qualifier instanceof EntityElement) {
			if (((EntityElement) qualifier).getElementType() == ELEMENT_TYPES.SCORECARD && reference.isTypeRef()) {
				return null; // Cannot declare a local/global var whose qualifier is of type ScoreCard
			}
			Entity entity = ((EntityElement)qualifier).getEntity();
			return searchEntity(reference, qualifier, entity, isEntityRef);
		} else if (qualifier instanceof ElementContainer) {
			boolean favorEntityEl = false;
			if (reference.eContainer() instanceof ElementReference) {
				ElementReference next = (ElementReference) reference.eContainer();
				if (next.isMethod() && next.getName().equals(reference.getName())) {
					// Favor entity elements over Folders when both exist in the container (i.e. a Folder named "Person" as well as a Concept named "Person")
					favorEntityEl = true;
				}
			}
			EList<DesignerElement> entries = ((ElementContainer) qualifier).getEntries();
			DesignerElement ee = null;
			for (DesignerElement designerElement : entries) {
				if (designerElement.getName().equals(reference.getName())) {
					if ((contentAssistMode || reference.eContainer() instanceof ElementReference) && designerElement instanceof ElementContainer) {
						if (favorEntityEl && !(designerElement instanceof EntityElement)) {
							ee = designerElement;
						} else {
							return designerElement;
						}
					} else if (!(designerElement instanceof ElementContainer)){
						if (reference.isTypeRef() || reference.eContainer() instanceof ElementReference 
								|| contentAssistMode || designerElement.getElementType() == ELEMENT_TYPES.SCORECARD) {
							if (favorEntityEl && !(designerElement instanceof EntityElement)) {
								ee = designerElement;
							} else {
								return designerElement;
							}
						}
					}
				}
			}
			if (ee != null) {
				return ee;
			}
		} else if (qualifier instanceof FunctionsCategory) {
			for (Iterator iterator = ((FunctionsCategory) qualifier).all(); iterator.hasNext();) {
				Object type = iterator.next();
				String localName = "";
				if (type instanceof FunctionsCategory) {
					localName = ((FunctionsCategory) type).getName().getLocalName();
				} else if (type instanceof JavaStaticFunction) {
					if (reference.isMethod()) {
						localName = ((JavaStaticFunction) type).getName().getLocalName();
					}
				} else {
					printDebug("Something else in catalog");
				}
				if (reference.getName().equals(localName)) {
					return type;
				}
			}
		}
		// query IElementResolutionProviders
		IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		for (IElementResolutionProvider provider : elementResolutionProviders) {
			Object resolvedObj = provider.processQualifier(reference, qualifier, projectName, contentAssistMode, isEntityRef);
			if (resolvedObj != null) {
				return resolvedObj;
			}
		}
		
		return null; // if we fall through, this could lead to improper resolution
	}
	
	private static Object resolveAttributeReference(ElementReference reference,
			Object qualifier, String projectName) {
		String attributeName = reference.getName();
		int binarySearch = Arrays.binarySearch(fPrimitiveAttributes, attributeName);
		if (binarySearch >= 0) {
			printDebug("Primitive attribute reference "+attributeName + " on "+qualifier);
			if (isValidAttribute(attributeName, qualifier, reference.getQualifier(), projectName)) {
				return attributeName; // primitive reference, does not resolve to anything
			}
		} else {
			// query IElementResolutionProviders
			IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
			for (IElementResolutionProvider provider : elementResolutionProviders) {
				Object obj = provider.resolveAttributeReference(attributeName, qualifier, reference.getQualifier(), projectName);
				if (obj != null) {
					return obj;
				}
			}
		}
		qualifier = resolveAttrQualifier(qualifier, projectName);
		if ("length".equals(attributeName)) {
			// this is now also valid for Strings
			if ("String".equals(qualifier)) {
				return attributeName;
			}
		}
		if (ATTR_PARENT.equals(attributeName)) {
			// qualifer must be a concept, try to resolve
			// to the concept's parent
			if (!(qualifier instanceof Concept)) {
				return null;
			}
			Concept concept = (Concept) qualifier;
			String parentPath = concept.getParentConceptPath();
			if (parentPath == null) {
				printDebug("Concept "+concept.getName()+" does not have a parent, cannot resolve @parent");
				return null;
			}
			return CommonIndexUtils.getElement(projectName, parentPath, ELEMENT_TYPES.CONCEPT);
		}
		return null;
	}

	private static Object resolveAttrQualifier(Object qualifier,
			String projectName) {
		if (qualifier instanceof VariableDefinition) {
			String type = ((VariableDefinition) qualifier).getType();
			printDebug("qualifier type is "+type);
			if (RuleGrammarUtils.isGenericType(type)) {
				return type;
			}
			qualifier = resolveVariableDefinitionType((VariableDefinition) qualifier);
//			String folder = ModelUtils.convertPackageToPath(type);
//			qualifier = CommonIndexUtils.getElement(projectName, folder);
			if (qualifier == null) {
				qualifier = resolveNonQualifiedReference(type, projectName, true, false);
			}
			printDebug("fully resolved qualifier is "+qualifier);
		}
		if (qualifier instanceof PropertyDefinition) {
			PropertyDefinition prop = (PropertyDefinition) qualifier;
			if ((prop.getType() == PROPERTY_TYPES.CONCEPT || prop.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) && prop.getConceptTypePath() != null) {
				qualifier = IndexUtils.getConcept(projectName, prop.getConceptTypePath());
			}
		}
		if (qualifier instanceof JavaStaticFunction) {
			qualifier = ((JavaStaticFunction) qualifier).getReturnClass();
		}
		if (qualifier instanceof EntityElement) {
			qualifier = ((EntityElement) qualifier).getEntity();
		}
		return qualifier;
	}

	private static boolean isValidAttribute(String attributeName,
			Object qualifier, ElementReference reference, String projectName) {
		boolean arrayAccessor = false;
		boolean qualifierIsArray = false;
		boolean dereferencedArray = true;
		if (reference != null && reference.isArray()) {
			// there is an array access for this reference, add completions for the qualifier, not for an "array"
			arrayAccessor = true;
		}
		if (qualifier instanceof VariableDefinition) {
			if (((VariableDefinition) qualifier).isArray()) {
				qualifierIsArray = true;
			}
			if (qualifierIsArray && !arrayAccessor) {
				if ("length".equals(attributeName)) {
					return true;
				}
			}
			qualifier = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) qualifier);
		} else if (qualifier instanceof PropertyDefinition) {
			qualifierIsArray = ((PropertyDefinition) qualifier).isArray();
		}
		dereferencedArray = !(qualifierIsArray && !arrayAccessor);
		if (qualifier instanceof EntityElement) {
			qualifier = ((EntityElement) qualifier).getEntity();
		}
		if (qualifier instanceof RuleElement) {
			FunctionRec ruleModelFunction = getRuleModelFunction((RuleElement) qualifier);
			if (ruleModelFunction != null) {
				qualifier = ruleModelFunction.function;
			}
		}
		if (qualifier instanceof PropertyDefinition) {
			PropertyDefinition def = (PropertyDefinition) qualifier;
			if ("isSet".equals(attributeName)) {
				return true;
			}
			if ("length".equals(attributeName)) {
				if ((def.isArray() && !dereferencedArray) || def.getType() == PROPERTY_TYPES.STRING) {
					return true;
				}
			}
			if (def.getType() == PROPERTY_TYPES.CONCEPT || def.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				String conceptTypePath = def.getConceptTypePath();
				qualifier = IndexUtils.getConcept(projectName, conceptTypePath);
			}
		}
		boolean isGeneric = false;
		Class returnClass = null;
		if (qualifier instanceof Predicate) {
			returnClass = ((Predicate) qualifier).getReturnClass();
			if (returnClass.isPrimitive()) {
				return false; // no attributes are valid for primitive types
			} else if (returnClass.isArray()) {
				if (arrayAccessor) {
					qualifier = returnClass.getComponentType();
				} else if ("length".equals(attributeName)) {
					return true;
				}
				return false;
			} 
			if (com.tibco.cep.runtime.model.event.TimeEvent.class.isAssignableFrom(returnClass)) {
				qualifier = "TimeEvent";
			}  else if (com.tibco.cep.runtime.model.event.TimeEvent.class.isAssignableFrom(returnClass)) {
				qualifier = "TimeEvent";
			} else if (com.tibco.cep.designtime.model.event.AdvisoryEvent.class.isAssignableFrom(returnClass)) {
				qualifier = "AdvisoryEvent";
			} else if (AdvisoryEvent.class.isAssignableFrom(returnClass)) {
				qualifier = "AdvisoryEvent";
			} else if (com.tibco.cep.designtime.model.event.Event.class.isAssignableFrom(returnClass)) {
				qualifier = "Event";
			} else if (SimpleEvent.class.isAssignableFrom(returnClass)) {
				qualifier = "Event";
			} else if (com.tibco.cep.runtime.model.element.Concept.class.isAssignableFrom(returnClass)) {
				qualifier = "Concept";
			} else if (com.tibco.cep.designtime.model.element.Concept.class.isAssignableFrom(returnClass)) {
				qualifier = "Concept";
			} else if ("java.lang.String".equals(returnClass.getName())) {
				qualifier = "String";
			} else if ("com.tibco.cep.bpmn.runtime.session.Process".equals(returnClass.getName())) {
				qualifier = "Process";
			}
		}
		if (qualifier instanceof Concept && dereferencedArray) {
			// valid attributes: "id", "extId", potentially "parent"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("extId".equals(attributeName)) {
				return true;
			}
			if (((Concept) qualifier).getParentConceptPath() != null) {
				if ("parent".equals(attributeName)) {
					return true;
				}
			}
		}
		if ((qualifier instanceof TimeEvent || "TimeEvent".equals(qualifier)) && dereferencedArray) {
			// valid attributes: "id", "ttl", "closure", "interval", "scheduledTime"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("ttl".equals(attributeName)) {
				return true;
			}
			if ("closure".equals(attributeName)) {
				return true;
			}
			if ("interval".equals(attributeName)) {
				return true;
			}
			if ("scheduledTime".equals(attributeName)) {
				return true;
			}
		}
		if (qualifier instanceof Event && dereferencedArray) {
			// valid attributes: "id", "extId", "ttl", "payload"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("extId".equals(attributeName)) {
				return true;
			}
			if ("ttl".equals(attributeName)) {
				return true;
			}
			if ("payload".equals(attributeName)) {
				return true;
			}
		}
		if ("Event".equals(qualifier) && dereferencedArray) {
			// valid attributes: "id", "ttl"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("ttl".equals(attributeName)) {
				return true;
			}
		}
		if (("Concept".equals(qualifier) || "Entity".equals(qualifier)) && dereferencedArray) {
			// valid attributes: "id", "extId"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("extId".equals(attributeName)) {
				return true;
			}
		}
		if ("Process".equals(qualifier) && dereferencedArray) {
			// valid attributes: "id", "extId"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("extId".equals(attributeName)) {
				return true;
			}
		}
		if ("AdvisoryEvent".equals(qualifier) && dereferencedArray) {
			// valid attributes: "id", "extId", "message", "category", "type", "ruleUri", "ruleScope"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("extId".equals(attributeName)) {
				return true;
			}
			if ("ttl".equals(attributeName)) {
				return true;
			}
			if ("message".equals(attributeName)) {
				return true;
			}
			if ("category".equals(attributeName)) {
				return true;
			}
			if ("type".equals(attributeName)) {
				return true;
			}
			if ("ruleUri".equals(attributeName)) {
				return true;
			}
			if ("ruleScope".equals(attributeName)) {
				return true;
			}
		}
		if ("SimpleEvent".equals(qualifier) && dereferencedArray) {
			// valid attributes: "id", "extId", "ttl", "payload"
			if ("id".equals(attributeName)) {
				return true;
			}
			if ("extId".equals(attributeName)) {
				return true;
			}
			if ("ttl".equals(attributeName)) {
				return true;
			}
			if ("payload".equals(attributeName)) {
				return true;
			}
		}
		if ("Exception".equals(qualifier) && dereferencedArray) {
			// valid attributes: "stackTrace", "cause", "errorType", "message"
			if ("cause".equals(attributeName)) {
				return true;
			}
			if ("stackTrace".equals(attributeName)) {
				return true;
			}
			if ("errorType".equals(attributeName)) {
				return true;
			}
			if ("message".equals(attributeName)) {
				return true;
			}
		}
		if ("String".equals(qualifier) && dereferencedArray) {
			// this is now also valid for Strings
			if ("length".equals(attributeName)) {
				return true;
			}
		}
		
		// query IElementResolutionProviders
		IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		for (IElementResolutionProvider provider : elementResolutionProviders) {
			Object obj = provider.resolveAttributeReference(attributeName, qualifier, reference.getQualifier(), projectName);
			if (obj != null) {
				return true;
			}
		}


		return false;
	}

	private static Object processType(ElementReference reference, String type,
			String projName, boolean contentAssistMode) {
		String folder = ModelUtils.convertPackageToPath(type);
		DesignerElement element = CommonIndexUtils.getElement(projName, folder, !contentAssistMode);
		printDebug("fully resolved qualifier is "+element);
		
		// from the fully resolved qualifier, find the reference
		if (element instanceof EntityElement) {
			Entity entity = ((EntityElement)element).getEntity();
			PropertyDefinition property = findProperty(entity, reference.getName());
			if (property != null) {
				printDebug("found property "+property);
				return property;
			}
		}
		Object obj = resolveNonQualifiedReference(type, projName, true, reference.isMethod() && reference.getQualifier() == null);
		if (obj == null) {
			IElementResolutionProvider[] elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
			for (IElementResolutionProvider provider : elementResolutionProviders) {
				obj = provider.resolveType(projName, type);
				if (obj != null) {
					break;
				}
			}
		}
		if (obj != null) {
			// use this as qualifier
			obj = processQualifier(reference, obj, projName, contentAssistMode, false);
			if (obj != null) {
				reference.setBinding(obj);
				return obj;
			}
		}
		return null;
	}

	private static Object resolveCatalogFunction(String projectName,
			ElementReference reference) {
		if (reference.isTypeRef()) {
			return null; // do not resolve type references to functions
		}
		Object element = resolveBuiltInFunction(projectName, reference);
		if (element != null) {
			return element;
		}
		
		element = resolveCustomFunction(projectName, reference);
		if (element != null) {
			return element;
		}
		
		return element;
	}

	private static Object resolveBuiltInFunction(String projectName,
			ElementReference reference) {
		
		try {
			FunctionsCatalog catalog = FunctionsCatalogManager.getInstance().getStaticRegistry();
			FunctionsCatalogManager.waitForStaticRegistryUpdates();
			Iterator<String> allCatalogs = catalog.catalogNames();
			while (allCatalogs.hasNext()) {
				String catName = (String) allCatalogs.next();
				
				if (!catName.equalsIgnoreCase(ONTOLOGY_FUNCTIONS)) {
					FunctionsCategory fc = (FunctionsCategory) catalog.getCatalog(catName);
					Object obj = processFunctionsCategory(reference, fc);
					if (obj != null) {
						return obj;
					}
				}
				
			}
			
		} catch (Exception e) {
			StudioCorePlugin.log("Failed to resolve function reference from catalog:["+projectName+"] "+ reference.getName());
		}

		return null;
	}

	private static Object processFunctionsCategory(ElementReference reference,
			FunctionsCategory fc) {
		for (Iterator iterator = fc.all(); iterator
				.hasNext();) {
			Object type = iterator.next();
			String localName = "";
			if (type instanceof FunctionsCategory) {
				if  (reference.isMethod() && reference.getQualifier() == null) {
					// recurse through this functions category until we
					// find a method with this name
					Object fn = processFunctionsCategory(reference, (FunctionsCategory) type);
					if (fn != null) {
						return fn;
					}
				} else {
					localName = ((FunctionsCategory) type).getName().getLocalName();
					if (reference.getName().equals(localName)) {
						return type;
					}
				}
			} else if (type instanceof JavaStaticFunction && reference.isMethod()) {
				localName = ((JavaStaticFunction) type).getName().getLocalName();
				if (reference.getName().equals(localName)) {
					return type;
				}
			} else {
				printDebug("Something else in catalog");
			}
		}
		return null;
	}

	private static Object resolveCustomFunction(String projectName,
			ElementReference reference) {
		try {
			FunctionsCatalog catalog = FunctionsCatalogManager.getInstance().getCustomRegistry(projectName);
			if (catalog == null) {
				return null;
			}
			Iterator catalogs = catalog.catalogs();
			while (catalogs.hasNext()) {
				FunctionsCategory fc = (FunctionsCategory) catalogs.next();
				Object o = processFunctionsCategory(reference, fc);
				if (o != null) {
					return o;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Object resolveGlobalReference(String projectName, ElementReference reference, boolean contentAssistMode) {
		String fullPath = getFullReferencePath(reference);
		fullPath = ModelUtils.convertPackageToPath(fullPath);
		DesignerElement element = CommonIndexUtils.getElement(projectName, fullPath, !contentAssistMode);
		if (element == null) {
			String elementName = fullPath.substring(1);
			List<DesignerElement> allElements = CommonIndexUtils.getAllElements(projectName, elementName);
			if (allElements.size() > 1) {
				return createAmbiguousReference(allElements);
			} else if (allElements.size() == 1) {
				element = allElements.get(0);
			}
		}
		if (element instanceof EntityElement && !reference.isTypeRef()) {
			// this is a direct reference to an Entity, i.e. ApplicationReceived, but it is not valid in this context (!reference.isTypeRef()), so return null
			return null;
		}
		if (element instanceof ElementContainer && reference.isArray()) {
			// cannot use an array accessor on ElementContainers
			return null;
		}
		return element;
	}

	private static AmbiguousReference createAmbiguousReference(
			List<? extends Object> allElements) {
		AmbiguousReference ref = new AmbiguousReference(allElements);
		return ref;
	}

	private static Object searchEntity(ElementReference reference,
			Object qualifier, Entity entity, boolean entityRef) {
		// first, check if this is a property on the qualifier, only if the qualifier is NOT a direct entityRef
		PropertyDefinition property = findProperty(entity, reference.getName());
		if (property != null) {
			printDebug("found property "+property);
			if (!entityRef || entity instanceof Scorecard) {
				return property;
			}
		}
		// next, check if this is a constructor for the qualifier
		if (qualifier instanceof EntityElement && reference.getName().equals(((EntityElement) qualifier).getName())) {
			// TODO : also check whether there are additional segments after this
			// if so, this is not a constructor.
			// this is a constructor
			printDebug("found constructor "+qualifier);
			if (reference.isMethod()) {
				return entity; // only a constructor if it is a method call
			}
		}
		// finally, check whether the entity is a Domain object.  If so, check the domain entries
		if (entity instanceof Domain) {
			EList<DomainEntry> entries = ((Domain) entity).getEntries();
			for (int i = 0; i < entries.size(); i++) {
				DomainEntry entry = entries.get(i);
				if (reference.getName().equals(entry.getDescription())) {
					return entry;
				}
			}
		}
		return null;
	}

	public static EObject getRootOfScope(EObject object) {
		while (object.eContainer() != null) {
			object = object.eContainer();
			if (object instanceof RuleElement
					|| object instanceof CompilableScope) {
				return object;
			}
		}
		return null;
	}

	private static void printDebug(String message) {
		if (fDebug) {
			System.out.println(message);
		}
	}

	private static String getFullReferencePath(ElementReference reference) {
		String fullPath = "";
		if (reference.getQualifier() != null) {
			fullPath += getFullReferencePath(reference.getQualifier()) + "." + reference.getName();
		} else {
			fullPath += reference.getName();
		}
		return fullPath;
	}

	private static PropertyDefinition findProperty(Entity entity, String name) {
		if (entity instanceof Concept) {
			Concept concept = (Concept) entity;
			EList<PropertyDefinition> allProperties = concept.getAllProperties();
			for (PropertyDefinition propertyDefinition : allProperties) {
				if (name.equals(propertyDefinition.getName())) {
					return propertyDefinition;
				}
			}
		}
		if (entity instanceof Event) {
			Event event = (Event) entity;
			EList<PropertyDefinition> allProperties = event.getAllUserProperties();
			for (PropertyDefinition propertyDefinition : allProperties) {
				if (name.equals(propertyDefinition.getName())) {
					return propertyDefinition;
				}
			}
		}
		return null;
	}

	public static FunctionRec getRuleModelFunction(RuleElement element) {
		String projectName = element.getIndexName();
		String ruleName = element.getName();
		String fullPath = element.getFolder()+ruleName;
		String methodCall = ModelUtils.convertPathToPackage(fullPath);
		FunctionRec ruleModelFunction = getModelFunction(projectName, methodCall);
		return ruleModelFunction;
	}

	public static FunctionRec getModelFunction(Entity element, ElementReference reference) {
		String elementName = element.getName();
		String fnName = ModelUtils.convertPathToPackage(element.getFullPath());
		if (element instanceof TimeEvent) {
			fnName += ".schedule"+elementName;
		}
		//INFO Added by Anand to fix CR # 1-APY7IH START 
		else if (element instanceof Metric) {
			if (reference != null) {
				fnName += "."+reference.getName();
			} else {
				fnName += ".compute"; // assume it is compute?  what about lookup?
			}
		}
		//INFO Added by Anand to fix CR # 1-APY7IH END
		else {
			fnName += "."+elementName;
		}
		return getModelFunction(element.getOwnerProjectName(), fnName);
	}

	public static FunctionRec getModelFunction(String ownerProject, String fnName) {
		// TODO : cache these, rather than creating a new one each time
		FunctionsCatalogLookup lookup = new FunctionsCatalogLookup(ownerProject);
		FunctionRec function = lookup.lookupFunction(fnName, null);
		return function;
	}

}
