package com.tibco.cep.studio.core.refactoring;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.mapper.xml.xdata.bind.Binding;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingRunner;
import com.tibco.cep.mapper.xml.xdata.bind.ElementBinding;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.studio.core.functions.model.EMFMetricMethodModelFunction;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch;
import com.tibco.cep.studio.core.index.model.search.StringLiteralMatch;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.RuleGrammarUtils;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.text.RulesASTRewriter;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Element;

public class RuleRefactoringHelper {

	private boolean fMoveRefactor;
	private String fOldName;
	private String fOldPath;

	public RuleRefactoringHelper(boolean moveRefactor, String oldName, String oldPath) {
		this.fMoveRefactor = moveRefactor;
		this.fOldName = oldName;
		this.fOldPath = oldPath;
	}

	public String getText(RulesASTNode node) {
		RulesASTRewriter writer = new RulesASTRewriter();
		node.accept(writer);
		String name = writer.getText();
		if (name == null) {
			// need to look directly...
			System.out.println("name is null");
		}
		return name;
	}

	private boolean hasEdit(MultiTextEdit edit, int offset, int length) {
		TextEdit[] children = edit.getChildren();
		for (TextEdit textEdit : children) {
			if (offset > textEdit.getOffset() && offset < textEdit.getInclusiveEnd()) {
				return true;
			}
			if (offset+length > textEdit.getOffset() && offset+length < textEdit.getInclusiveEnd()) {
				return true;
			}
		}
		return false;
	}

	public boolean processReferences(List<ElementReference> foundReferences,
			MultiTextEdit edit, RuleElement ruleElement, String newFolder, String newName, Object refactoredElement) {
		boolean changed = false;
		for (ElementReference elementReference : foundReferences) {
			boolean isTimeEventRef = false;
			if (elementReference.isMethod() && (refactoredElement instanceof TimeEvent ||
					(refactoredElement instanceof EntityElement
							&& ((EntityElement)refactoredElement).getElementType() == ELEMENT_TYPES.TIME_EVENT))) {
				isTimeEventRef = true;
			}

			// if the reference has a qualifier, and newFolder != null, then replace the full element reference
			if (newFolder != null && elementReference.getQualifier() != null) {
				ElementReference rootQualifier = elementReference.getQualifier();
				while (rootQualifier.getQualifier() != null) {
					rootQualifier = rootQualifier.getQualifier();
				}
				String newText = ModelUtils.convertPathToPackage(newFolder + newName);
				if (elementReference.isMethod() && !(refactoredElement instanceof RuleElement)) {
					if (isTimeEventRef) {
						newText += ".schedule" + newName;
					} else {
						newText += "." + newName;
					}
				}
				int offset = rootQualifier.getOffset();
				int length = elementReference.getOffset() + elementReference.getLength() - offset;
				if (hasEdit(edit, offset, length)) {
					continue;
				}
				ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, newText);
				edit.addChild(replaceEdit);
				changed = true;
			} else {
				if (!newName.equals(elementReference.getName()) || fMoveRefactor) {
					String newText = newName;
					if (refactoredElement instanceof EntityElement && ((EntityElement)refactoredElement).getElementType() == ELEMENT_TYPES.METRIC) {
						if (Arrays.asList(EMFMetricMethodModelFunction.getAvailableFunctions()).contains(elementReference.getName()) == true) {
							// these stay the same, just continue
							continue;
						}
					}
					if (isTimeEventRef) {
						newText = "schedule"+newName; // ? not sure why we were doing this.  Hopefully doesn't break anything by commenting
					}
					if (newFolder != null && !elementReference.isMethod()) {
						// do not add new folder to name if it is an unqualified method call.  This can break ontology function calls.
						newText = ModelUtils.convertPathToPackage(newFolder+newName);
					}
					if (elementReference.isMethod() && !(refactoredElement instanceof RuleElement)) {
//						newText += "."+newName; // ? not sure why we were doing this.  I think this should only be done for moves.  Hopefully doesn't break anything by commenting
					}
					ReplaceEdit replaceEdit = new ReplaceEdit(elementReference.getOffset(), elementReference.getLength(), newText);
					edit.addChild(replaceEdit);
					changed = true;
				}
			}
		}
		return changed;
	}

	public boolean processDefinitions(List<EObject> foundDefinitions,
			MultiTextEdit edit, RuleElement ruleElement, String newFolder, String newName) {
		boolean changed = false;
		for (EObject object : foundDefinitions) {
			System.out.println("found definition "+object.toString());
		}
		return changed;
	}

	public boolean processSearchResult(SearchResult result,
			MultiTextEdit edit, RuleElement ruleElement, String newFolder,
			String newName, Object elementToRefactor) {
		List<EObject> exactMatches = result.getExactMatches();
		boolean changed = false;
		for (EObject object : exactMatches) {
			if (object instanceof ElementMatch) {
				object = ((ElementMatch) object).getMatchedElement();
				if (processMatch(object, edit, ruleElement, newFolder, newName, elementToRefactor)) changed = true;
			}
		}
		return changed;
	}

	private boolean processMatch(EObject object, MultiTextEdit edit,
			RuleElement ruleElement, String newFolder, String newName, Object elementToRefactor) {
		if (object instanceof MethodArgumentMatch) {
			MethodArgumentMatch argMatch = (MethodArgumentMatch) object;
			return processArgumentMatch(argMatch, edit, ruleElement, newFolder, newName, elementToRefactor);
		} else if (object instanceof StringLiteralMatch) {
			return processStringLiteralMatch((StringLiteralMatch)object, edit, ruleElement, newFolder, newName);
		}
		return false;
	}

	private boolean processStringLiteralMatch(StringLiteralMatch match,
			MultiTextEdit edit, RuleElement ruleElement, String newFolder,
			String newName) {
		int offset = match.getOffset()+1; // skip the initial '"'
		int length = match.getLength()-2; // skip the ending '"'
		String newFullPath = "";
		newFullPath = getNewFullPath(newName, newFolder);
		ReplaceEdit repEdit = new ReplaceEdit(offset, length, newFullPath);
		edit.addChild(repEdit);
		return true;
	}

	private boolean processArgumentMatch(MethodArgumentMatch match,
			MultiTextEdit edit, RuleElement ruleElement, String newFolder,
			String newName, Object elementToRefactor) {
		int offset = match.getOffset()+1; // skip the initial '"'
		int length = match.getLength()-2; // skip the ending '"'
		String newFullPath = "";
		newFullPath = getNewFullPath(newName, newFolder);
		ReplaceEdit repEdit = null;

		Predicate predicate = (Predicate) match.getFunction();
		if (predicate instanceof JavaStaticFunctionWithXSLT) {
			RulesASTNode argNode = (RulesASTNode) match.getArgNode();
			String xslt = getText(argNode);
			xslt = xslt.substring(1, xslt.length()-1);

			JavaStaticFunctionWithXSLT xsltFn = (JavaStaticFunctionWithXSLT) predicate;
			if (xsltFn.isXsltFunction()) {
				ArrayList<String> params = new ArrayList<String>();
				params.add(newFullPath);
				repEdit = processXSLTFunction(ruleElement, newName,
						elementToRefactor, offset, length, argNode,
						xslt, params);
			} else if (xsltFn.isXPathFunction()) {
				repEdit = processXPathFunction(ruleElement, newName,
						elementToRefactor, offset, length, argNode,
						xslt);
			}
		}
		if (repEdit != null) {
			edit.addChild(repEdit);
			return true;
		}
		return false;
	}

	private ReplaceEdit processXPathFunction(RuleElement ruleElement,
			String newName, Object elementToRefactor, int offset, int length,
			RulesASTNode argNode, String xslt) {
		ReplaceEdit repEdit = null;
		EObject elementReference = RuleGrammarUtils.getElementReference((RulesASTNode) argNode.getParent().getParent());
		if (elementReference instanceof ElementReference) {
			IResolutionContext resolutionContext = ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope((ElementReference) elementReference));
			try {
				XiNode xpath = null;
				try {
					xpath = XSTemplateSerializer.deSerializeXPathString(xslt);
					HashMap origMap = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
					if (processXPathNode(resolutionContext, ruleElement, newName, (PropertyDefinition) elementToRefactor, argNode, xpath)) {
						String newXpath = XSTemplateSerializer.getXPathExpressionAsStringValue(xpath);
						final List vars = XSTemplateSerializer.searchForVariableNamesinExpression(newXpath);
						final String newXslt = XSTemplateSerializer.serializeXPathString(newXpath, origMap, vars);
						repEdit = new ReplaceEdit(offset, length, newXslt);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
			}
		}
		return repEdit;
	}

	private boolean processXPathNode(IResolutionContext resolutionContext, RuleElement element, String newName, PropertyDefinition elementToRefactor,
			RulesASTNode argNode, XiNode xpath) {
		boolean changed = false;
		if (xpath instanceof Element) {
			Element el = (Element) xpath;
			if ("expr".equals(el.getName().localName)) {
				String formula = el.getStringValue();
				Document doc = new Document(formula);
				changed = processFormula(resolutionContext, (RuleElement) element, el,
						elementToRefactor, newName, argNode, changed, formula, doc);
			}
		}
		Iterator children = xpath.getChildren();
		while (children.hasNext()) {
			Object object = (Object) children.next();
			if (object instanceof XiNode) {
				XiNode node = (XiNode) object;
				if (processXPathNode(resolutionContext, element, newName, elementToRefactor, argNode, node)) {
					changed = true;
				}
			}
		}
		return changed;
	}

	private ReplaceEdit processXSLTFunction(RuleElement ruleElement,
			String newName, Object elementToRefactor, int offset, int length,
			RulesASTNode argNode, String xslt,
			ArrayList<String> params) {
		ReplaceEdit repEdit = null;
		TemplateBinding binding = MapperCoreUtils.getBinding(xslt, new ArrayList());
		NamespaceContextRegistry nsm = MapperCoreUtils.getNamespaceMapper();
		StylesheetBinding sb = (StylesheetBinding)binding.getParent();
		BindingElementInfo.NamespaceDeclaration[] nd = sb.getElementInfo().getNamespaceDeclarations();
		for (int i=0; i<nd.length; i++) {
			nsm.getOrAddPrefixForNamespaceURI(nd[i].getNamespace(), nd[i].getPrefix());
		}
		ArrayList receivingParams = XSTemplateSerializer.getReceivingParms(xslt);
		String oldPath = fOldPath + fOldName;
		EObject elementReference = RuleGrammarUtils.getElementReference((RulesASTNode) argNode.getParent().getParent());
		if (elementReference instanceof ElementReference) {
			IResolutionContext resolutionContext = ElementReferenceResolver.createResolutionContext(RuleGrammarUtils.getScope((ElementReference) elementReference));
			if (processBindings(resolutionContext, ruleElement.getIndexName(), (String) receivingParams.get(0), binding, elementToRefactor, newName, argNode)) {
				String xsltTemplate = BindingRunner.getXsltFor(binding, nsm );
				String newXslt = XSTemplateSerializer.serialize(xsltTemplate, receivingParams, new ArrayList<Object>());
				repEdit = new ReplaceEdit(offset, length, newXslt);
			}
		}
		if (receivingParams.size() > 0) {
			// make sure that the receiving param matches the previous name/location before updating
			String param = (String) receivingParams.get(0);
			if (param.equals(oldPath)) {
				String xsltTemplate = BindingRunner.getXsltFor(binding, nsm );
				String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params, new ArrayList<Object>());
				repEdit = new ReplaceEdit(offset, length, newXslt);
			}
		}
		return repEdit;
	}

	private boolean processBindings(IResolutionContext resolutionContext, String projName, String param, TemplateBinding binding, Object elementToRefactor, String newName, RulesASTNode argNode) {
		if (!(elementToRefactor instanceof PropertyDefinition)) {
			// can anything else besides a property definition in the xslt get changed during refactoring?
			// TODO : eventually, local and global variables should be included when refactoring supports renaming them
			return false;
		}
		PropertyDefinition propDef = (PropertyDefinition) elementToRefactor;
		DesignerElement element = IndexUtils.getElement(projName, param);
		if (element == null) {
			// we don't know what the output type is, can't accurately match output variables
			// still continue, as we can still potentially match local variables
		}
		boolean changed = false;
		Binding[] children = binding.getChildren();
		for (Binding childBinding : children) {
			if (processBinding(resolutionContext, element, childBinding, propDef, newName, argNode)) {
				changed = true;
			}
		}
		return changed;
	}

	private boolean processBinding(IResolutionContext resolutionContext, DesignerElement element, Binding binding, PropertyDefinition elementToRefactor, String newName, RulesASTNode argNode) {
		boolean changed = false;
		String formula = binding.getFormula();
		IDocument doc = new Document(formula);
		if (formula == null && binding instanceof ElementBinding) {
			ElementBinding elBinding = (ElementBinding) binding;
			ExpandedName name = elBinding.getName();
			String locName = name.localName;
			// locName is the property name
			if (element instanceof EntityElement) {
				Entity ent = ((EntityElement) element).getEntity();
				if (ent.getFullPath().equals(elementToRefactor.getOwnerPath())) {
					// the output entity type matches the owner of this property, check to see if this element binding
					// needs to be updated
					if (locName.equals(fOldName)) {
						elBinding.setLiteralName(new ExpandedName(name.namespaceURI, newName));
						changed = true;
					}
				}
			}
		} else if (formula != null) {
			changed = processFormula(resolutionContext, element, binding,
					elementToRefactor, newName, argNode, changed, formula, doc);
		}
		if (binding.getChildCount() > 0) {
			Binding[] children = binding.getChildren();
			for (Binding childBinding : children) {
				if (processBinding(resolutionContext, element, childBinding, elementToRefactor, newName, argNode)) {
					changed = true;
				}
			}
		}
		return changed;
	}

	private boolean processFormula(IResolutionContext resolutionContext,
			DesignerElement element, Object formulaSource,
			PropertyDefinition elementToRefactor, String newName,
			RulesASTNode argNode, boolean changed, String formula,
			IDocument doc) {
		MultiTextEdit edit = new MultiTextEdit();
		try {
			byte[] bytes = formula.getBytes(com.tibco.cep.studio.core.utils.ModelUtils.DEFAULT_ENCODING);
			int start = -1;
			int end = -1;
			boolean foundStart = false;
			for (int i=0; i<bytes.length; i++) {
				byte b = bytes[i];
				if (foundStart) {
					if (b == '/') {
						end = i;
						String variable = formula.substring(start, i);
						processVariable(variable, formula, start, end, bytes, resolutionContext, element, edit, elementToRefactor, newName, argNode);
						foundStart = false;
						start = -1;
					} else if (!Character.isLetterOrDigit(b)) {
						// never found a '/', no properties to be found
					}
				}
				if (b == '$') {
					start = i+1;
					foundStart = true;
				}
			}
			if (foundStart) {
				// never found a '/', no properties to be found
			}
			try {
				if (edit.getChildren() != null && edit.getChildrenSize() > 0) {
					edit.apply(doc);
					changed = true;
					setSourceFormula(formulaSource, doc.get());
				}
			} catch (MalformedTreeException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return changed;
	}

	private void setSourceFormula(Object source, String newFormula) {
		if (source instanceof Binding) {
			((Binding) source).setFormula(newFormula);
		} else if (source instanceof Element) {
			((Element) source).setStringValue(newFormula);
		}
	}

	private void processVariable(String variable, String formula, int start,
			int end, byte[] bytes, IResolutionContext resolutionContext, DesignerElement element, MultiTextEdit edit,
			PropertyDefinition elementToRefactor, String newName, RulesASTNode argNode) {
		ElementReference ref = IndexFactory.eINSTANCE.createElementReference();
		ref.setName(variable);
		ref.setOffset(argNode.getOffset());
		ref.setLength(argNode.getLength());
		Object el = ElementReferenceResolver.resolveElement(ref, resolutionContext);
		Object type = el;
		if (el instanceof VariableDefinition) {
			type = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) el);
		}

		// get the rest of the xslt to see if this is a reference we care about
		StringBuffer buf = new StringBuffer();
		start = end;
		for (int i=end; i<bytes.length; i++) {
			byte b= bytes[i];
			if (Character.isLetterOrDigit((char)b) || b == '/' || b == '@' || b == '_') {
				buf.append((char)b);
			} else {
				end = i;
				break;
			}
		}
		if (end <= start) {
			end = bytes.length;
		}
		if (buf.charAt(0) == '/') {
			buf.deleteCharAt(0);
			start++;
		}
		String propXslt = buf.toString();
		if (propXslt.length() > 0) {
			int idx = propXslt.indexOf('/');
			if (idx > -1) {
				propXslt = propXslt.substring(0, idx);
				end = idx+start;
			}
			// resolve property against target entity
			if (type instanceof EntityElement) {
				String entityPath =((EntityElement)type).getEntity().getFullPath()+"/"+propXslt;
				if (entityPath.equals(elementToRefactor.getFullPath())) {
					ReplaceEdit repEdit = new ReplaceEdit(start, end-start, newName);
					edit.addChild(repEdit);
				}
			}
		}
	}

	private String getNewFullPath(String newName, String newPath) {
		if (fMoveRefactor) {
			return newPath + fOldName;
		}
		return fOldPath + newName;
	}

}
