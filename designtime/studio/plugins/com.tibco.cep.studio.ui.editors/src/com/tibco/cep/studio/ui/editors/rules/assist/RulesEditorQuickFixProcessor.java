package com.tibco.cep.studio.ui.editors.rules.assist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.quickassist.IQuickFixableAnnotation;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.SimpleResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.IProblemTypes;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.ui.editors.rules.text.IProblemContext;
import com.tibco.cep.studio.ui.editors.rules.text.IRulesAnnotation;
import com.tibco.cep.studio.ui.editors.rules.text.ResolutionProblemContext;

public class RulesEditorQuickFixProcessor implements IQuickAssistProcessor {
	
	private RulesEditorQuickFixAssistant fQuickFixAssistant;

	public RulesEditorQuickFixProcessor(RulesEditorQuickFixAssistant assistant) {
		this.fQuickFixAssistant = assistant;
	}

	public static boolean hasCorrections(Annotation annotation) {
		if (!(annotation instanceof IQuickFixableAnnotation)) {
			return false;
		}
		if (annotation instanceof IRulesAnnotation) {
			if (((IRulesAnnotation) annotation).getProblemCode() == IProblemTypes.PROBLEM_RESOLUTION) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canAssist(IQuickAssistInvocationContext invocationContext) {
		return true;
	}

	@Override
	public boolean canFix(Annotation annotation) {
		return hasCorrections(annotation);
	}

	@Override
	public ICompletionProposal[] computeQuickAssistProposals(
			IQuickAssistInvocationContext invocationContext) {
		Annotation annotation = fQuickFixAssistant.getCurrentAnnotation();
		if (annotation == null) {
			return null;
		}
		IAnnotationModel annotationModel = invocationContext.getSourceViewer().getAnnotationModel();
		Position position = annotationModel.getPosition(annotation);
		if (position == null) {
			return null;
		}
		List<ICompletionProposal> quickAssistProposals = new ArrayList<ICompletionProposal>();
		if (annotation instanceof IRulesAnnotation) {
			IRulesAnnotation rulesAnn = (IRulesAnnotation) annotation;
			processRulesAnnotation(rulesAnn, quickAssistProposals);
		}
		if (quickAssistProposals.size() == 0) {
			quickAssistProposals.add(new CompletionProposal("", invocationContext.getOffset(), 0, invocationContext.getOffset(), null, "No quick fixes currently available", null, null));
		}
		return (ICompletionProposal[]) quickAssistProposals
				.toArray(new ICompletionProposal[quickAssistProposals.size()]);
	}

	private void processRulesAnnotation(IRulesAnnotation rulesAnn,
			List<ICompletionProposal> quickAssistProposals) {
		if (rulesAnn.getProblemCode() == IProblemTypes.PROBLEM_RESOLUTION) {
			IProblemContext problemContext = rulesAnn.getProblemContext();
			if (problemContext instanceof ResolutionProblemContext) {
				ElementReference reference = ((ResolutionProblemContext) problemContext).getReference();
				if (reference.getQualifier() != null) {
					// resolve qualifer and potentially use that as the containing folder, 
					// or the owner concept/event for the property definition
					Object qualifier = null;
					ElementReference qualifierRef = reference.getQualifier();
					if (qualifierRef.getBinding() != null) {
						qualifier = qualifierRef.getBinding();
					} else {
						ScopeBlock scope = RuleGrammarUtils.getScope(qualifierRef);
						SimpleResolutionContext context = new SimpleResolutionContext(scope);
						qualifier = ElementReferenceResolver.resolveElement(qualifierRef, context);
					}
					if (qualifier != null) {
						processQualifiedReference(qualifier, reference, quickAssistProposals);
					}
				} else {
					processUnqualifiedReference(reference, quickAssistProposals);
				}
			}
		}
	}

	private void processUnqualifiedReference(ElementReference reference,
			List<ICompletionProposal> quickAssistProposals) {
		IProject project = getProject(reference);
		IndexUtils.getRootContainer(reference);
		if (reference.isMethod()) {
			NewEntityCompletionProposal proposal = new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.RULE_FUNCTION);
			quickAssistProposals.add(proposal);
		} else {
			NewEntityCompletionProposal proposal = new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.CONCEPT);
			quickAssistProposals.add(proposal);
			proposal = new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.SIMPLE_EVENT);
			quickAssistProposals.add(proposal);
			proposal = new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.TIME_EVENT);
			quickAssistProposals.add(proposal);
		}
	}

	private void processQualifiedReference(Object qualifier,
			ElementReference reference,
			List<ICompletionProposal> quickAssistProposals) {
		IProject project = getProject(reference);
		if (qualifier instanceof VariableDefinition) {
			qualifier = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) qualifier);
		}
		if (qualifier instanceof EntityElement) {
			qualifier = ((EntityElement) qualifier).getEntity();
		}
		if (qualifier instanceof Folder) {
			if (reference.isMethod()) {
				String folderPath = getFolderPath((Folder)qualifier);
				NewEntityCompletionProposal proposal = 
					new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.RULE_FUNCTION, 
							folderPath, true);
				quickAssistProposals.add(proposal);
			} else {
				// create event/concept in container folder
				String folderPath = getFolderPath((Folder)qualifier);
				NewEntityCompletionProposal proposal = 
					new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.CONCEPT, 
							folderPath, true);
				quickAssistProposals.add(proposal);
				proposal = 
					new NewEntityCompletionProposal(project, reference.getName(), ELEMENT_TYPES.SIMPLE_EVENT, 
							folderPath, true);
				quickAssistProposals.add(proposal);
			}
		} else if (qualifier instanceof Concept || qualifier instanceof Event) {
			if (!reference.isMethod()) {
				// create concept property
				NewPropertyDefinitionCompletionProposal proposal = new NewPropertyDefinitionCompletionProposal(project, reference.getName(), (Entity)qualifier);
				quickAssistProposals.add(proposal);
			}
		}
	}

	private String getFolderPath(Folder qualifier) {
		StringBuffer buf = new StringBuffer();
		buf.append(qualifier.getName());
		buf.append('/');
		while (qualifier.eContainer() instanceof Folder) {
			qualifier = (Folder) qualifier.eContainer();
			buf.insert(0, qualifier.getName()+"/");
		}
		buf.insert(0, '/');
		return buf.toString();
	}

	private IProject getProject(EObject object) {
		if (object instanceof ElementReference) {
			object = IndexUtils.getRootContainer(object);
		}
		if (object instanceof DesignerProject) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(((DesignerProject) object).getName());
		}
		if (object instanceof EntityElement) {
			object = ((EntityElement) object).getEntity();
		}
		if (object instanceof Entity) {
			Entity entity = (Entity) object;
			return ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
		}
		if (object instanceof RuleElement) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(((RuleElement) object).getIndexName());
		}
		return null;
	}
	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return "Error!";
	}

}
