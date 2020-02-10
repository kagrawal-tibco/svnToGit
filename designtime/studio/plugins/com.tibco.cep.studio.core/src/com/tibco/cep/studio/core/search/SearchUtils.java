package com.tibco.cep.studio.core.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.RuleSourceMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.index.model.search.StringLiteralMatch;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class SearchUtils {

	private static ISearchParticipant[] fSearchParticpants;

	public static SearchResult searchIndex(Object resolvedElement, String projectName, String nameToFind) {
		return searchIndex(resolvedElement, projectName, nameToFind, false);
	}
	
	public static SearchResult searchIndex(Object resolvedElement, String projectName, String nameToFind, boolean sourceOnly) {
		return searchIndex(resolvedElement, projectName, nameToFind, new NullProgressMonitor(), sourceOnly);
	}

	public static SearchResult searchIndex(Object resolvedElement,
			String projectName, String nameToFind,
			IProgressMonitor monitor) {
		return searchIndex(resolvedElement, projectName, nameToFind, false);
	}
	
	public static SearchResult searchIndex(Object resolvedElement,
			String projectName, String nameToFind,
			IProgressMonitor monitor, boolean sourceOnly) {
		if (projectName == null || projectName.length() == 0
				|| nameToFind == null || nameToFind.length() == 0) {
			return new SearchResult();
		}
		if (resolvedElement instanceof VariableDefinition) {
			// SPECIAL CASE:
			// no need to search across all rule elements, this var can only
			// be referenced locally in this scope
			RuleVariableSearchParticipant participant = new RuleVariableSearchParticipant();
			return participant.search((VariableDefinition)resolvedElement, projectName, nameToFind, monitor);
		}
		ISearchParticipant[] searchParticipants = getSearchParticipants();
		SearchResult result = new SearchResult();
		for (ISearchParticipant searchParticipant : searchParticipants) {
			SearchResult searchResult = null;
			if (sourceOnly) {
				if (searchParticipant instanceof ISourceSearchParticipant) {
					searchResult = ((ISourceSearchParticipant) searchParticipant).searchSource(resolvedElement, projectName, nameToFind, monitor);
				}
			} else {
				searchResult = searchParticipant.search(resolvedElement, projectName, nameToFind, monitor);
			}
			result.merge(searchResult);
		}
		
		return result;
	}

	public static String getElementMatchLabel(Object object) {
		StringBuilder builder = new StringBuilder();
		if (!(object instanceof ElementMatch)) {
			appendInfo(object, builder);
			return builder.toString();
		} 

		ElementMatch match = (ElementMatch) object;

		if (match.getLabel() != null) {
			builder.append(match.getLabel());
		} else if (match.getFeature() instanceof EStructuralFeature) {
			if (!(match.getMatchedElement() instanceof PropertyDefinition)) {
				builder.append("Attribute '");
				builder.append(((EStructuralFeature) match.getFeature()).getName());
				builder.append("' from ");
			}
			if (match.getMatchedElement() instanceof Entity) {
				appendInfo(match.getMatchedElement(), builder);
//				builder.append(((Entity) match.getMatchedElement()).getName());
			} else {
				appendInfo(match.getMatchedElement(), builder);
//				builder.append(match.getMatchedElement());
			}
		} else if (match.getFeature() == match.getMatchedElement()) {
			builder.append("Element definition: ");
			appendInfo(match.getMatchedElement(), builder);
		}
		return builder.toString();
	}
	
	private static void appendInfo(Object object, StringBuilder builder) {
		if (object instanceof RuleSourceMatch) {
			object = ((RuleSourceMatch) object).getContainingRule();
		}
		if (object instanceof EntityElement) {
			object = ((EntityElement)object).getEntity();
		}
		if (object instanceof DecisionTableElement) {
			object = ((DecisionTableElement) object).getImplementation();
		}
		if (object instanceof PropertyDefinition) {
			PropertyDefinition def = (PropertyDefinition) object;
			builder.append("'");
			builder.append(def.getName());
			builder.append("' ");
			builder.append(def.getType());
			builder.append(" property in ");
			object = def.eContainer();
		}
		if (object instanceof Entity) {
			Entity entity = (Entity) object;
			builder.append(entity.getName());
//			builder.append(" type ");
//			builder.append(entity.getClass().getName());
		}
		if (object instanceof ElementReference) {
			ElementReference element = (ElementReference) object;
			builder.append(element.getName());
			builder.append(" [");
			builder.append(element.getOffset());
			builder.append(", ");
			builder.append(element.getLength());
			builder.append("] -- reference");
		}
		if (object instanceof VariableDefinition) {
			VariableDefinition element = (VariableDefinition) object;
			builder.append(element.getName());
			builder.append(" [");
			builder.append(element.getOffset());
			builder.append(", ");
			builder.append(element.getLength());
			builder.append("] -- definition");
		}
		if (object instanceof RuleElement) {
			RuleElement element = (RuleElement) object;
			builder.append(element.getName());
//			builder.append(" type ");
//			builder.append(element.getClass().getName());
		}
		if (object instanceof Table) {
			builder.append("decision table '");
			builder.append(((Table) object).getName());
			builder.append('\'');
		}
	}

	public static void addArgumentMatch(JavaStaticFunction element,
			SearchResult result, EObject ruleElement,
			RulesASTNode rulesASTNode) {
		MethodArgumentMatch argMatch = SearchFactory.eINSTANCE.createMethodArgumentMatch();
		argMatch.setFunction(element);
		argMatch.setArgNode(rulesASTNode);
		argMatch.setContainingRule(ruleElement);
		argMatch.setOffset(rulesASTNode.getOffset());
		argMatch.setLength(rulesASTNode.getLength());
		ElementMatch match = SearchFactory.eINSTANCE.createElementMatch();
		match.setLabel("Function argument for function "+element.getName().localName);
		match.setMatchedElement(argMatch);
		result.addExactMatch(match);
	}

	public static void addLiteralMatch(SearchResult result, EObject ruleElement, RulesASTNode rulesASTNode) {
		StringLiteralMatch literalMatch = SearchFactory.eINSTANCE.createStringLiteralMatch();
		literalMatch.setContainingRule(ruleElement);
		literalMatch.setOffset(rulesASTNode.getOffset());
		literalMatch.setLength(rulesASTNode.getLength());
		ElementMatch match = SearchFactory.eINSTANCE.createElementMatch();
		match.setLabel("String literal reference");
		match.setMatchedElement(literalMatch);
		result.addExactMatch(match);
	}

	public static IFile getFile(EObject object) {
		if (object instanceof ElementMatch) {
			object = ((ElementMatch) object).getMatchedElement();
		}
		if (object instanceof NonEntityMatch) {
			NonEntityMatch nem = (NonEntityMatch) object;
			String projectName = nem.getProjectName();
			String filePath = nem.getFilePath();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			
			IFile file = project.getFile(filePath);
			return file.isAccessible() ? file : null;
		}
		if (object instanceof TypeElement) {
			EObject rootContainer = IndexUtils.getRootContainer(object);
			String projName = null;
			if (rootContainer instanceof DesignerProject) {
				projName = ((DesignerProject) rootContainer).getName();
			}
			if (projName != null) {
				IFile file = IndexUtils.getFile(projName, (TypeElement) object);
				return file != null && file.isAccessible() ? file : null;
			}
		}
		if (object instanceof RuleSourceMatch) {
			object = ((RuleSourceMatch) object).getContainingRule();
		}
		if (object instanceof EntityElement) {
			EntityElement entity = (EntityElement) object;
			IFile file = IndexUtils.getFile(entity.getEntity().getOwnerProjectName(), entity);
			return file;
		}
		if (object instanceof PropertyDefinition) {
			PropertyDefinition def = (PropertyDefinition) object;
			object = def.eContainer();
		}
		if (object instanceof Compilable) {
			object = getCompilableContainer((Compilable)object);
		}
		if(object instanceof Destination){
			Destination destination = (Destination)object;
			object = destination.getDriverConfig().getChannel();
		}
		if (object instanceof Entity) {
			Entity entity = (Entity) object;
			IFile file = IndexUtils.getFile(entity.getOwnerProjectName(), entity);
			return file;
		}
		if (object instanceof ElementReference) {
			ElementReference elementRef = (ElementReference) object;
			DesignerElement element = IndexUtils.getVariableContext(elementRef);
			if (element instanceof RuleElement) {
				return IndexUtils.getFile(((RuleElement) element).getIndexName(), (TypeElement) element);
			} else if (element instanceof StateMachineElement) {
				return IndexUtils.getFile(((StateMachineElement) element).getIndexName(), ((StateMachineElement) element).getEntity());
			} else if (element instanceof EntityElement) {
				return IndexUtils.getFile(((EntityElement) element).getEntity().getOwnerProjectName(), ((EntityElement) element).getEntity());
			}
		}
		if (object instanceof VariableDefinition) {
			VariableDefinition varDef = (VariableDefinition) object;
			DesignerElement element = IndexUtils.getVariableContext(varDef);
			if (element instanceof RuleElement) {
				return IndexUtils.getFile(((RuleElement) element).getIndexName(), (TypeElement) element);
			} else if (element instanceof StateMachineElement) {
				return IndexUtils.getFile(((StateMachineElement) element).getIndexName(), ((StateMachineElement) element).getEntity());
			} else if (element instanceof EntityElement) {
				return IndexUtils.getFile(((EntityElement) element).getEntity().getOwnerProjectName(), ((EntityElement) element).getEntity());
			}
		}
		if (object instanceof RuleElement) {
			RuleElement ruleElement = (RuleElement) object;
			return IndexUtils.getFile(ruleElement.getIndexName(), ruleElement);
		}
		return null;
	}

	private static EObject getCompilableContainer(EObject object) {
		while (object.eContainer() != null) {
			object = object.eContainer();
			if (object instanceof StateMachine) {
				return object;
			}
		}
		return null;
	}

	private synchronized static ISearchParticipant[] getSearchParticipants() {
		if (fSearchParticpants == null) {
			List<ISearchParticipant> participants = new ArrayList<ISearchParticipant>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, "searchParticipant");
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("participant");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("participant");
						if (executableExtension instanceof ISearchParticipant) {
							participants.add((ISearchParticipant) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fSearchParticpants = new ISearchParticipant[participants.size()];
			return participants.toArray(fSearchParticpants);
		}
		return fSearchParticpants;
	}

}
