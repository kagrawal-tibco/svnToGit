package com.tibco.cep.studio.ui.editors.rules.assist;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getPropertyImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension3;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.functions.impl.JavaStaticFunction;
import com.tibco.be.model.functions.impl.JavaStaticFunctionWithXSLT;
import com.tibco.be.model.functions.impl.ModelFunctionImpl;
import com.tibco.be.model.functions.impl.ModelJavaFunction;
import com.tibco.be.parser.semantic.FunctionRec;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.runtime.model.event.AdvisoryEvent;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.studio.core.functions.model.EMFOntologyModelFunction;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.resolution.ElementReferenceResolver;
import com.tibco.cep.studio.core.index.resolution.GlobalVariableExtension;
import com.tibco.cep.studio.core.index.resolution.IElementResolutionProvider;
import com.tibco.cep.studio.core.index.resolution.IResolutionContext;
import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.index.resolution.ResolutionUtils;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.rules.BaseRulesParser;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesTreeAdaptor;
import com.tibco.cep.studio.core.rules.grammar.RulesLexer;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.core.rules.grammar.RulesParser.actionContextStatements_return;
import com.tibco.cep.studio.core.rules.grammar.RulesParser.preconditionStatements_return;
import com.tibco.cep.studio.core.rules.grammar.RulesParser.startRule_return;
import com.tibco.cep.studio.core.rules.grammar.RulesParser.thenStatements_return;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.RuleFormViewer;
import com.tibco.cep.studio.ui.editors.rules.text.BrowserInformationControl;
import com.tibco.cep.studio.ui.editors.rules.text.MethodParameterContextValidator;
import com.tibco.cep.studio.ui.editors.rules.text.RulesTextContentProvider;
import com.tibco.cep.studio.ui.editors.rules.text.RulesTextLabelDecorator;
import com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils;
import com.tibco.xml.data.primitive.ExpandedName;

public class RulesEditorContentAssistProcessor implements IContentAssistProcessor {

	private class HTMLCompletionProposal implements ICompletionProposal, ICompletionProposalExtension3 {

		private CompletionProposal fProposal;
		
		public HTMLCompletionProposal(CompletionProposal proposal) {
			this.fProposal = proposal;
		}

		@Override
		public void apply(IDocument document) {
			fProposal.apply(document);			
		}

		@Override
		public String getAdditionalProposalInfo() {
			return fProposal.getAdditionalProposalInfo();
		}

		@Override
		public IContextInformation getContextInformation() {
			return fProposal.getContextInformation();
		}

		@Override
		public String getDisplayString() {
			return fProposal.getDisplayString();
		}

		@Override
		public Image getImage() {
			return fProposal.getImage();
		}

		@Override
		public Point getSelection(IDocument document) {
			return fProposal.getSelection(document);
		}

		@Override
		public IInformationControlCreator getInformationControlCreator() {
			return new IInformationControlCreator() {
			
				@Override
				public IInformationControl createInformationControl(Shell parent) {
					return new BrowserInformationControl(parent, EditorsUI.getTooltipAffordanceString());
				}
			};
		}

		@Override
		public int getPrefixCompletionStart(IDocument document,
				int completionOffset) {
			return completionOffset;
		}

		@Override
		public CharSequence getPrefixCompletionText(IDocument document,
				int completionOffset) {
			return null;
		}
		
	}
	
	private class RuleCompletionProposalContext implements IContextInformation {

		private Object fProposalObj;

		public RuleCompletionProposalContext(Object proposalObj) {
			this.fProposalObj = proposalObj;
		}

		public Object getProposalObj() {
			return fProposalObj;
		}

		@Override
		public String getContextDisplayString() {
			return null;
		}

		@Override
		public Image getImage() {
			return null;
		}

		@Override
		public String getInformationDisplayString() {
			String template = null;
			Object obj = fProposalObj;
			if (obj instanceof JavaStaticFunction) {
				template = ((JavaStaticFunction) obj).argumentTemplate();
			} else if (obj instanceof ModelFunctionImpl) {
				template = ((ModelFunctionImpl) obj).argumentTemplate();
			} else if (obj instanceof EMFOntologyModelFunction) {
				template = ((EMFOntologyModelFunction) obj).argumentTemplate();
			}
			if (template != null && template.length() > 0) {
				return template;
			}
			return "";
		}
		
	}
	
	private class ProposalSorter implements Comparator<ICompletionProposal> {

		@Override
		public int compare(ICompletionProposal o1, ICompletionProposal o2) {
			IContextInformation c1 = o1.getContextInformation();
			IContextInformation c2 = o2.getContextInformation();
			if (c1 instanceof RuleCompletionProposalContext && c2 instanceof RuleCompletionProposalContext) {
				Object obj1 = ((RuleCompletionProposalContext) c1).getProposalObj();
				Object obj2 = ((RuleCompletionProposalContext) c2).getProposalObj();
				int comp = compareCompletionObjects(obj1, obj2);
				if (comp != 0) {
					return comp;
				}
			}
			return o1.getDisplayString().compareTo(o2.getDisplayString());
		}

		private int compareCompletionObjects(Object obj1, Object obj2) {
			// categorizes proposals so that similar proposals are grouped together
			/*
			 * 1. Local and Global variables
			 * 2. Element containers and Functions categories
			 * 3. Entities and methods
			 */
			if (obj1 == null && obj2 != null) {
				return -1;
			}
			if (obj1 != null && obj2 == null) {
				return 1;
			}
			int cat1;
			if (obj1 instanceof VariableDefinition) { cat1 = 0; }
			else if (obj1 instanceof ElementContainer) { cat1 = 1; }
			else if (obj1 instanceof FunctionsCategory) { cat1 = 1; }
			else { cat1 = 2; }
			
			int cat2;
			if (obj2 instanceof VariableDefinition) { cat2 = 0; }
			else if (obj2 instanceof ElementContainer) { cat2 = 1; }
			else if (obj2 instanceof FunctionsCategory) { cat2 = 1; }
			else { cat2 = 2; }
			
			if (cat1 != cat2) {
				return cat1 > cat2 ? 1 : -1;
			}
			return 0;
		}
		
	}
	
	private class TokenReference {
		
		public CommonToken token;
		public boolean arrayRef;
		public boolean methodRef;
		public boolean attrRef;

		public TokenReference(CommonToken token, boolean arrayRef, boolean methodRef) {
			this(token, arrayRef, methodRef, false);
		}
		
		public TokenReference(CommonToken token, boolean arrayRef, boolean methodRef, boolean attributeRef) {
			this.token = token;
			this.arrayRef = arrayRef;
			this.methodRef = methodRef;
			this.attrRef = attributeRef;
		}
		
	}
	
	private boolean fDebug = true;
	private String fLastCharTyped;
	private String fProjectName;
	private String fProposalFilter;
	private int fSourceType;
	private ProposalSorter fProposalSorter = new ProposalSorter();
	
	private IResolutionContextProvider fContextProvider;
	private FunctionsCatalogLookup fLookup;
	private MethodParameterContextValidator fInfoValidator;
	private int fScopeType = -1;
	private ElementReference fLastMethodCallReference;
	private ContentAssistant fContentAssistant;
	
	private static RulesTextLabelDecorator fLabelProvider = new RulesTextLabelDecorator();
	private static HashMap<String, String> fNonFilterTokens = new HashMap<String, String>();

	private static ElementReferenceResolver fResolver = new ElementReferenceResolver();
	
	private IElementResolutionProvider[] elementResolutionProviders = null;
	
	static {
		fNonFilterTokens.put("SEMICOLON", ";");
		fNonFilterTokens.put("LBRACE", "{");
		fNonFilterTokens.put("RBRACE", "}");
		fNonFilterTokens.put("LPAREN", "(");
		fNonFilterTokens.put("RPAREN", ")");
		fNonFilterTokens.put("LBRACK", "[");
		fNonFilterTokens.put("RBRACK", "]");
		fNonFilterTokens.put("EQ", "==");
		fNonFilterTokens.put("NE", "!=");
		fNonFilterTokens.put("LT", "<");
		fNonFilterTokens.put("GT", ">");
		fNonFilterTokens.put("LE", "<=");
		fNonFilterTokens.put("GE", ">=");
		fNonFilterTokens.put("ASSIGN", "=");
		fNonFilterTokens.put("ANNOTATE", "@");
		fNonFilterTokens.put("DOT", ".");
		
		fNonFilterTokens.put("plus", "+");
		fNonFilterTokens.put("minus", "-");
		fNonFilterTokens.put("star", "*");
		fNonFilterTokens.put("slash", "/");
		fNonFilterTokens.put("or", "||");
		fNonFilterTokens.put("and", "&&");
		fNonFilterTokens.put("uor", "|");
		fNonFilterTokens.put("uand", "&");
		fNonFilterTokens.put("quote", "\"");
		fNonFilterTokens.put("comma", ",");
	}
	
	public RulesEditorContentAssistProcessor(ContentAssistant contentAssistant, IResolutionContextProvider contextProvider, String projectName, int sourceType) {
		super();
		this.fContentAssistant = contentAssistant;
		this.fContextProvider = contextProvider;
		this.fProjectName = projectName;
		this.fSourceType = sourceType;
		this.fLookup = new FunctionsCatalogLookup(fProjectName);
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		printDebug("Offset: "+offset);
		fContentAssistant.enableAutoInsert(false);

		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		
		RulesParser parser = initializeRulesParser(viewer.getDocument(), offset);
		fProposalFilter = getProposalFilter(offset, viewer.getDocument(), parser).toLowerCase();
		printDebug("proposal filter: "+fProposalFilter);
		ScopeBlock scope = parser.getLocalScope();
		if (scope != null && parser.getLastPoppedScope() != null && parser.getLastPoppedScope().getOffset() > scope.getOffset()) {
			// check whether the last popped scope is actually the proper scope to process
			// by checking whether the offset/length is closer to the offset of the completion proposal offset
			ScopeBlock lastPoppedScope = parser.getLastPoppedScope();
			TokenStream tokenStream = parser.getTokenStream();
			int index = parser.getCurrentToken().getTokenIndex();
	        for (int i = index; i >= 0; i--) {
	        	Token tkn = tokenStream.get(i);
	        	if (tkn instanceof CommonToken) {
	        		if (((CommonToken)tkn).getStopIndex() < lastPoppedScope.getOffset()) {
	        			break;
	        		}
	        		if (tkn.getType() == RulesParser.LBRACE) {
	        			// we are in an unclosed block
	        			scope = lastPoppedScope;
	        			break;
	        		} else if (tkn.getType() == RulesParser.RBRACE) {
	        			break;
	        		}
	        	}
	        }
		}
		if (scope == null) {
			scope = parser.getLastPoppedScope();
		}
		if (scope == null) {
			scope = parser.getScope();
		}
		if (scope != null) {
			fScopeType  = scope.getType();
		}
		printScope(scope);
		ElementReference reference = getLastElementReference(parser, scope);
		if (reference != null) {
			printDebug("Last reference: "+reference.getName());
			IResolutionContext context = fContextProvider.getResolutionContext(reference, scope);
			Object resolvedElement = fResolver.resolveElementFromContentAssistant(reference, context, true);
			if (resolvedElement instanceof List) {
				filterList((List)resolvedElement);
				List<Object> elements = (List<Object>) resolvedElement;
				for (Object object : elements) {
					processResolvedElement(offset, proposals, object, reference);
				}
			} else {
				processResolvedElement(offset, proposals, resolvedElement, reference);
			}
		} else if (fLastMethodCallReference != null) {
			processLastMethodCallReference(offset, scope, proposals);
		} else {
			collectLocalVariableCompletions(offset, scope, proposals);
			collectGlobalCompletions(offset, proposals);
		}
		Collections.sort(proposals, fProposalSorter);
		
		
		
		//BE-22810 - Removing duplicate entries from Proposal List
		proposals=removeDuplicateEntriesInProposals(proposals);
		ICompletionProposal[] props = new ICompletionProposal[proposals.size()];
		proposals.toArray(props);
		
		return props;
		
	}

	private List<ICompletionProposal> removeDuplicateEntriesInProposals(
			List<ICompletionProposal> props) {
		
        List<ICompletionProposal> filteredList=new ArrayList<ICompletionProposal>();
		boolean found=false;
		for(int i=0;i<props.size();i++)
		{
			for(int j=i+1;j<props.size();j++)
			{
				if(isEqualProposalObjects(props.get(j),props.get(i)))
				{
					found=true;
				}
			}
			if(!found){	
				filteredList.add(props.get(i));
			}
			found=false;
				
		}
		return filteredList;
	}
	private boolean isEqualProposalObjects(
			ICompletionProposal prop1,
			ICompletionProposal prop2) {

		try
		{

			if(prop1!=null && prop2!=null)
			{	
				if(((RuleCompletionProposalContext) prop1.getContextInformation()).getProposalObj() instanceof PropertyDefinition && ((RuleCompletionProposalContext) prop2.getContextInformation()).getProposalObj() instanceof PropertyDefinition)
				{
					PropertyDefinition propDef1=(PropertyDefinition) ((RuleCompletionProposalContext) prop1.getContextInformation()).getProposalObj();

					PropertyDefinition propDef2=(PropertyDefinition) ((RuleCompletionProposalContext) prop2.getContextInformation()).getProposalObj();
					if(propDef1!=null && propDef2!=null)
					{	
						if((prop1.getImage().equals(prop2.getImage())) &&  
								(prop1.getDisplayString().equals((prop2.getDisplayString())))  &&
								propDef1.equals(propDef2))
						{
							return true;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			//do nothing and return false
		}
		return false;
	}

	private void filterList(List resolvedElement) {
		List<Object> filteredList = new ArrayList<Object>();
		for (int i=0; i<resolvedElement.size(); i++) {
			Object object = resolvedElement.get(i);
			if (object instanceof VariableDefinition) {
				object = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) object);
				for (int j = 0; j < resolvedElement.size(); j++) {
					if (j == i) {
						continue;
					}
					Object o = resolvedElement.get(j);
					if (o.equals(object)) {
						filteredList.add(object);
					}
				}
			}
		}
		for (Object object : filteredList) {
			resolvedElement.remove(object);
		}
	}

	private void processLastMethodCallReference(int offset, ScopeBlock scope,
			List<ICompletionProposal> proposals) {
		IResolutionContext context = fContextProvider.getResolutionContext(fLastMethodCallReference, scope);
		Object resolvedElement = fResolver.resolveElementFromContentAssistant(fLastMethodCallReference, context, true);
		if (resolvedElement instanceof JavaStaticFunctionWithXSLT) {
			fContentAssistant.enableAutoInsert(true);
			processFunctionCallCompletion(offset, proposals, resolvedElement, null);
			if (((JavaStaticFunctionWithXSLT) resolvedElement).isXsltFunction()) {
				
			} else if (((JavaStaticFunctionWithXSLT) resolvedElement).isXPathFunction()) {
				
			}
		} else {
			collectLocalVariableCompletions(offset, scope, proposals);
			collectGlobalCompletions(offset, proposals);
		}
	}

	private void collectGlobalCompletions(int offset,
			List<ICompletionProposal> proposals) {
		collectRootEntries(offset, proposals);
		collectCatalogFunctions(offset, proposals);
	}

	private void collectCatalogFunctions(int offset,
			List<ICompletionProposal> proposals) {

		// add all root level catalog functions/function categories
		try {
			FunctionsCatalog catalog = FunctionsCatalogManager.getInstance().getStaticRegistry();
			Iterator<String> allCatalogs = catalog.catalogNames();
			while (allCatalogs.hasNext()) {
				String catName = (String) allCatalogs.next();
				printDebug("Adding completions for catalog: "+catName);
				FunctionsCategory fc = (FunctionsCategory) catalog.getCatalog(catName);
				processFunctionsCategory(offset, proposals, fc);
			}
		} catch (Exception e) {
		}
		collectCustomFunctions(offset, proposals);
		// these are added from the file structure of the project (for now)
//		collectOntologyFunctions(offset, proposals);
	}

	private void collectCustomFunctions(int offset,
			List<ICompletionProposal> proposals) {
		try {
			FunctionsCatalog registry = FunctionsCatalogManager.getInstance().getCustomRegistry(fProjectName);
	    	if (registry == null) {
	    		return;
	    	}
			Iterator catalogs = registry.catalogs();
			while (catalogs.hasNext()) {
				FunctionsCategory fc = (FunctionsCategory) catalogs.next();
				processFunctionsCategory(offset, proposals, fc);
			}
		} catch (Exception e) {
		}
	}

	private void collectOntologyFunctions(int offset,
			List<ICompletionProposal> proposals) {
		try {
			processFunctionsCategory(offset, proposals, FunctionsCatalogManager.getInstance().getOntologyCategory(fProjectName));
		} catch (Exception e) {
		}
	}
	
	private void processFunctionsCategory(int offset,
			List<ICompletionProposal> proposals, FunctionsCategory fc) {
		Object[] children = RulesTextContentProvider.getChildren(fc);
		for (Object object : children) {
			if (object instanceof JavaStaticFunction) {
				addFunctionCompletionProposal(offset, proposals, (JavaStaticFunction) object);
			} else {
				addCompletionProposal(offset, proposals, object);
			}
		}
	}

	private void collectRootEntries(int offset,
			List<ICompletionProposal> proposals) {
		
		DesignerProject index = IndexUtils.getIndex(fProjectName);
		processResolvedElement(offset, proposals, index, null);
		
	}

	private ElementReference getLastElementReference(RulesParser parser,
			ScopeBlock scope) {
		if (fLastCharTyped == null) {
			return null;
		}
		char lastChar = fLastCharTyped.charAt(0);
		Stack<TokenReference> referenceTokenStack = getReferenceTokenStack(parser);
		if (!Character.isWhitespace(lastChar) 
				&& lastChar != '.'
				&& lastChar != '@'
				&& referenceTokenStack.size() > 0) {
			// remove most recent token, as this is part of the name to
			// complete.  Get all children of the qualifier, and then
			// let the fProposalFilter filter relevant results
			TokenReference ref = referenceTokenStack.remove(0);
			if (ref.attrRef) {
				fLastCharTyped = "@";
			}
		}
		ElementReference reference = createElementReference(null, referenceTokenStack);
		return reference;
	}

	private ElementReference createElementReference(ElementReference qualifier,
			Stack<TokenReference> refTokenStack) {
		if (refTokenStack.size() == 0) {
			return null;
		}
		ElementReference ref = IndexFactory.eINSTANCE.createElementReference();
		TokenReference tknRef = refTokenStack.pop();
		// assumption is that token is an Identifier
		ref.setName(tknRef.token.getText());
		if (qualifier != null) {
			ref.setQualifier(qualifier);
		}
		ref.setOffset(tknRef.token.getStartIndex());
		// no need to set length, only offset
//		ref.setLength(tknRef.token.getStopIndex()-ref.getOffset());
		ref.setArray(tknRef.arrayRef);
		ref.setMethod(tknRef.methodRef);
		ref.setAttRef(tknRef.attrRef);
		if (refTokenStack.size() > 0) {
			return createElementReference(ref, refTokenStack);
		}
		return ref;
	}

	/*
	 * This method collects all identifier tokens that should be used
	 * to qualify the content assist proposal.  It also determines
	 * whether the identifier is a method call or array access for
	 * more accurate resolution
	 */
	private Stack<TokenReference> getReferenceTokenStack(RulesParser parser) {
		Stack<TokenReference> tokenStack = new Stack<TokenReference>();
		TokenStream tokenStream = parser.getTokenStream();
		int size = tokenStream.size();
		computeTokenReferenceStack(tokenStack, tokenStream, size-1);
		
		return tokenStack;
	}

	private void computeTokenReferenceStack(Stack<TokenReference> tokenStack,
			TokenStream tokenStream, int startingIndex) {
		boolean arrayAccess = false;
		boolean methodCall = false;
		boolean prevArrayAccess = false;
		boolean prevMethodCall = false;
		boolean foundWhitespace = false;
		boolean foundDot = false;
		int arrDepth = 0;
		int parenDepth = 0;
		for (int i = startingIndex; i >= 0; i--) {
			Token tkn = tokenStream.get(i);
			if (tkn.getChannel() != 99) {
				if (tkn.getType() == RulesParser.ANNOTATE) {
					if (tokenStack.size() > 0) {
						tokenStack.lastElement().attrRef = true;
					}
					continue;
				}
				if (foundWhitespace && tkn.getType() != RulesParser.DOT && !foundDot) {
					break;
				}
				if (tkn.getType() == RulesParser.DOT) {
					foundDot = true;
					continue;
				}
				if (tkn.getType() == RulesParser.Identifier && !arrayAccess && !methodCall) {
					tokenStack.push(new TokenReference((CommonToken) tkn, prevArrayAccess, prevMethodCall));
					foundDot = false;
					prevArrayAccess = false;
					prevMethodCall = false;
				} else if (tkn.getType() == RulesParser.RBRACK) {
					arrayAccess = true;
					arrDepth++;
				} else if (tkn.getType() == RulesParser.RPAREN) {
					methodCall = true;
					parenDepth++;
				} else if (tkn.getType() == RulesParser.LBRACK) {
					if (arrDepth == 0) {
						arrayAccess = false;
						prevArrayAccess = false;
						break;
					}
					arrDepth--;
					if (arrDepth == 0) {
						arrayAccess = false;
						prevArrayAccess = true;
					}
				} else if (tkn.getType() == RulesParser.LPAREN) {
					if (parenDepth == 0) {
						methodCall = false;
						prevMethodCall = false;
						setPreviousMethodCall(i, tokenStream);
						break;
					}
					parenDepth--;
					if (parenDepth == 0) {
						methodCall = false;
						prevMethodCall = true;
					}
				} else if (tkn.getType() != RulesParser.DOT && !arrayAccess && !methodCall) {
					break;
				}
			} else if (!methodCall) {
				// this is a whitespace token.  Only continue if the previous 
				// non-whitespace token is a '.'
				foundWhitespace = true;
			}
		}
	}
	
	private void setPreviousMethodCall(int index, TokenStream tokenStream) {
		Stack<TokenReference> tokenStack = new Stack<TokenReference>();
		computeTokenReferenceStack(tokenStack, tokenStream, index-1);
		if (tokenStack.size() > 0) {
			TokenReference lastElement = tokenStack.lastElement();
			System.out.println(lastElement.token);
		}
		ElementReference reference = createElementReference(null, tokenStack);
		fLastMethodCallReference = reference;
	}

	private void processResolvedElement(int offset,
			List<ICompletionProposal> proposals, Object resolvedElement, ElementReference reference) {
		if (fLastCharTyped == null) {
			
		}
		char lastChar = fLastCharTyped.charAt(0);
		if (lastChar == '@') {
			processAttributeCompletion(offset, proposals, resolvedElement, reference);
		} else if (lastChar == '(') {
			processFunctionCallCompletion(offset, proposals, resolvedElement, reference);
		} else {
			//Handling Entity Element(Concept/Event) Function Call 
			if(resolvedElement instanceof EntityElement && reference != null && !reference.isAttRef()){
				EntityElement element = (EntityElement)resolvedElement;
				if(!(element.getEntity() instanceof Scorecard) 
						&& !(element.getEntity() instanceof Channel)
						&& !(element.getElementType() == ELEMENT_TYPES.STATE_MACHINE)
						&& !(element.getElementType() == ELEMENT_TYPES.METRIC)){
					String name = element.getEntity().getName();
					Image image = StudioUIPlugin.getDefault().getImage("icons/function.png");
					FunctionRec modelFunction = ElementReferenceResolver.getModelFunction(element.getEntity(), null);
					if (modelFunction != null) {
						String argSuffix = getCompletionSuffix(modelFunction.function);
						String fnName = modelFunction.function.getName().localName;
						addCompletionProposal(offset, proposals, fnName+argSuffix, RulesTextLabelDecorator.getMethodInfo(modelFunction), image, fnName, modelFunction.function, true);
					} else {
						addCompletionProposal(offset, proposals, name+"(", RulesTextLabelDecorator.getInfo(element.getEntity(), fProjectName), image, name, element, true);
					}
				} else if (element.getElementType() == ELEMENT_TYPES.METRIC) {
					Image image = StudioUIPlugin.getDefault().getImage("icons/function.png");
					FunctionRec[] modelFunctions = RulesTextLabelDecorator.getModelFunctions(element.getEntity(), null);
					for (FunctionRec modelFunction : modelFunctions) {
						if (modelFunction == null) {
							continue;
						}
						String argSuffix = getCompletionSuffix(modelFunction.function);
						String fnName = modelFunction.function.getName().localName;
						addCompletionProposal(offset, proposals, fnName+argSuffix, RulesTextLabelDecorator.getMethodInfo(modelFunction), image, fnName, modelFunction.function, true);
					}
				}
				else if (element.getEntity() instanceof Scorecard) {
					Object[] children = RulesTextContentProvider.getChildren(resolvedElement);
					for (Object object : children) {
						addCompletionProposal(offset, proposals, object);
					}
				}
			} else {
				Object[] children = RulesTextContentProvider.getChildren(resolvedElement);
				List<Object> result = new ArrayList<Object>(); 
				result.addAll(Arrays.asList(children)); 
				Object[] newChildren = new Object[result.size()];
				children = result.toArray(newChildren);
				for (Object object : children) {
					if (object instanceof JavaStaticFunction) {
						addFunctionCompletionProposal(offset, proposals, (JavaStaticFunction) object);
					} else if(object instanceof ModelJavaFunction){
						addModelJavaFunctionCompletionProposal(offset, proposals, (ModelJavaFunction) object);
					} else {
						addCompletionProposal(offset, proposals, object);
					}
				}
			}
		}
	}

	private void processFunctionCallCompletion(int offset,
			List<ICompletionProposal> proposals, Object qualifier, ElementReference reference) {
		if (!(qualifier instanceof JavaStaticFunctionWithXSLT)) {
			return;
		}
		JavaStaticFunctionWithXSLT function = (JavaStaticFunctionWithXSLT) qualifier;
		String completion = "";
		String displayString = "";
		String info = "";
		if (function.isXsltFunction()) {
			completion = RulesEditorUtils.XSLT_PREFIX+"\")";
			displayString = "<xslt>";
			info = "insert the xslt string for the mapper";
		} else if (function.isXPathFunction()) {
			completion = RulesEditorUtils.XPATH_PREFIX+"\")";
			displayString = "<xpath>";
			info = "insert the xpath string for the xpath builder";
		}
		addCompletionProposal(offset, proposals, completion, info, null, displayString, function, false);
	}

	/*
	 *  Depending on the qualifer, the '@' completions are
	 *  different.
	 */
	private void processAttributeCompletion(int offset,
			List<ICompletionProposal> proposals, Object qualifier, ElementReference reference) {
		boolean arrayAccessor = false;
		if (reference != null && reference.isArray()) {
			// there is an array access for this reference, add completions for the qualifier, not for an "array"
			arrayAccessor = true;
//			addCompletionProposal(offset, proposals, "length", "Attribute 'length'", getPropertyImage(PROPERTY_TYPES.INTEGER), null);
//			return;
		}
		if (qualifier instanceof VariableDefinition) {
			if (((VariableDefinition) qualifier).isArray() && !arrayAccessor) {
				addCompletionProposal(offset, proposals, "length", "Attribute 'length'",  getPropertyImage(PROPERTY_TYPES.INTEGER), null);
				return;
			}
			qualifier = ElementReferenceResolver.resolveVariableDefinitionType((VariableDefinition) qualifier);
		}
		if (qualifier instanceof EntityElement) {
			qualifier = ((EntityElement) qualifier).getEntity();
		}
		if (qualifier instanceof RuleElement) {
			FunctionRec ruleModelFunction = ElementReferenceResolver.getRuleModelFunction((RuleElement) qualifier);
			if (ruleModelFunction != null) {
				qualifier = ruleModelFunction.function;
			}
		}
		if (qualifier instanceof PropertyDefinition) {
			PropertyDefinition def = (PropertyDefinition) qualifier;
//			if (def.getType() == PROPERTY_TYPES.BOOLEAN) {
				addCompletionProposal(offset, proposals, "isSet", "Attribute 'isSet'",  getPropertyImage(PROPERTY_TYPES.BOOLEAN), null);
//			}
			if (def.isArray() && !arrayAccessor) {
				addCompletionProposal(offset, proposals, "length", "Attribute 'length'", getPropertyImage(PROPERTY_TYPES.INTEGER), null);
			}
			if (def.getType() == PROPERTY_TYPES.CONCEPT || def.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE) {
				String conceptTypePath = def.getConceptTypePath();
				qualifier = IndexUtils.getConcept(fProjectName, conceptTypePath);
			}
		}
		boolean isGeneric = false;
		Class returnClass = null;
		if (qualifier instanceof Predicate) {
			returnClass = ((Predicate) qualifier).getReturnClass();
			if (returnClass.isPrimitive()) {
				return; // no attributes are valid for primitive types
			} else if (returnClass.isArray()) {
				if (arrayAccessor) {
					qualifier = returnClass.getComponentType();
				} else {
					addCompletionProposal(offset, proposals, "length", "Attribute 'length'", getPropertyImage(PROPERTY_TYPES.INTEGER), null);
				}
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
			}
		}
		if (qualifier instanceof Concept) {
			// valid attributes: "id", "extId", potentially "parent"
			addCompletionProposal(offset, proposals, "id", "Attribute 'id'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "extId", "Attribute 'extId'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			if (((Concept) qualifier).getParentConceptPath() != null) {
				addCompletionProposal(offset, proposals, "parent", "Attribute 'parent'\n("+((Concept) qualifier).getParentConceptPath()+")", 
						EditorsUIPlugin.getDefault().getImage("icons/iconConceptRef16.gif"), null);
			}
			return;
		}
		if (qualifier instanceof TimeEvent) {
			// valid attributes: "id", "ttl", "closure", "interval", "scheduledTime"
			addCompletionProposal(offset, proposals, "id", "Attribute 'id'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "ttl", "Attribute 'ttl'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "closure", "Attribute 'closure'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "interval", "Attribute 'interval'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "scheduledTime", "Attribute 'scheduledTime'", getPropertyImage(PROPERTY_TYPES.DATE_TIME), null);
			return;
		}
		if (qualifier instanceof Event) {
			// valid attributes: "id", "extId", "ttl", "payload"
			addCompletionProposal(offset, proposals, "id", "Attribute 'id'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "extId", "Attribute 'extId'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "ttl", "Attribute 'ttl'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "payload", "Attribute 'payload'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			return;
		}
		if ("Event".equals(qualifier)) {
			// valid attributes: "id", "ttl"
			addCompletionProposal(offset, proposals, "id", "Attribute 'id'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "ttl", "Attribute 'ttl'", getPropertyImage(PROPERTY_TYPES.LONG), null);
		}
		if ("Concept".equals(qualifier) || "Entity".equals(qualifier)) {
			// valid attributes: "id", "extId"
			addCompletionProposal(offset, proposals, "id", "Attribute 'id'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "extId", "Attribute 'extId'", getPropertyImage(PROPERTY_TYPES.STRING), null);
		}
		if ("AdvisoryEvent".equals(qualifier)) {
			// valid attributes: "id", "extId", "message", "category", "type"
			addCompletionProposal(offset, proposals, "id", "Attribute 'id'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "extId", "Attribute 'extId'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "ttl", "Attribute 'ttl'", getPropertyImage(PROPERTY_TYPES.LONG), null);
			addCompletionProposal(offset, proposals, "message", "Attribute 'message'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "category", "Attribute 'category'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "type", "Attribute 'type'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "ruleUri", "Attribute 'ruleUri'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "ruleScope", "Attribute 'ruleScope'", null, null);
			return;
		}
		if ("Exception".equals(qualifier)) {
			// valid attributes: "stackTrace", "cause", "errorType", "message"
			addCompletionProposal(offset, proposals, "cause", "Attribute 'cause'",EditorsUIPlugin.getDefault().getImage("icons/iconException16.png"), null);
			addCompletionProposal(offset, proposals, "stackTrace", "Attribute 'stackTrace'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "errorType", "Attribute 'errorType'", getPropertyImage(PROPERTY_TYPES.STRING), null);
			addCompletionProposal(offset, proposals, "message", "Attribute 'message'",  getPropertyImage(PROPERTY_TYPES.STRING), null);
			return;
		}
		elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
		for (IElementResolutionProvider provider : elementResolutionProviders) {
			List<Map<String, String>> list = provider.getElementAttributes(qualifier);
			for(Map<String, String> map : list) {
				addCompletionProposal(offset, proposals, map.get("Name"), map.get("Info"), getPropertyImage(PROPERTY_TYPES.get(map.get("Type"))), null);	
			}
		}
		
	}

	private void addCompletionProposal(int offset,
			List<ICompletionProposal> proposals, Object object) {
		if (object instanceof ArchiveElement 
				|| object instanceof DomainInstance 
				|| object instanceof DecisionTableElement
				|| object instanceof StateMachine) {
			return; // do not show archive, SM, DT, domain completions
		}
		if (object instanceof EntityElement) {
			if ((((EntityElement) object).getElementType() == ELEMENT_TYPES.DOMAIN && !isDomainCompletionSupported())
					|| ((EntityElement) object).getElementType() == ELEMENT_TYPES.STATE_MACHINE
					|| ((EntityElement) object).getElementType() == ELEMENT_TYPES.DECISION_TABLE) {
				return;
			}
		}
		String name = fLabelProvider.getText(object);
		if (name.isEmpty()) {
			elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
			for (IElementResolutionProvider provider : elementResolutionProviders) {
				name = provider.getElementText(object);
				if (!name.isEmpty()) {
					break;
				}
			}
		}
		addCompletion(offset, name, object, proposals, false);
	}

	private boolean isDomainCompletionSupported() {
		return fScopeType == BaseRulesParser.BINDINGS_SCOPE; 
	}

	private void addFunctionCompletionProposal(int offset,
			List<ICompletionProposal> proposals, JavaStaticFunction function) {
		
		String name = fLabelProvider.getText(function);
		String fnName = getMapperFunctionName(function);
		if (fnName != null) {
			String info = RulesTextLabelDecorator.getInfo(function, fProjectName);
			Image image = fLabelProvider.getImage(function);
			addCompletionProposal(offset, proposals, fnName, info, image, function.getName().localName, name, true);
		} else {
			addFunctionTemplateCompletion(offset, name, function, proposals);
		}
	}
	
	private void addModelJavaFunctionCompletionProposal(int offset,
			List<ICompletionProposal> proposals, ModelJavaFunction function) {
		
		String name = fLabelProvider.getText(function);
		String fnName = getMapperFunctionName(function);
		if (fnName != null) {
			String info = RulesTextLabelDecorator.getInfo(function, fProjectName);
			Image image = fLabelProvider.getImage(function);
			addCompletionProposal(offset, proposals, fnName, info, image, function.getName().localName, name, true);
		} else {
			addFunctionTemplateCompletion(offset, name, function, proposals);
		}
	}
	
	private String getMapperFunctionName(Predicate function) {
		if (!(function instanceof JavaStaticFunctionWithXSLT)) {
			return null;
		}
		JavaStaticFunctionWithXSLT fn = (JavaStaticFunctionWithXSLT) function;
		ExpandedName name = fn.getName();
		String fullName = name.toString();
		if (RulesEditorUtils.XPATH_TYPE.equals(fn.getMapperType())) {
			return name.localName+"("+RulesEditorUtils.XPATH_PREFIX+"\")";
		} else if (RulesEditorUtils.XSLT_TYPE.equals(fn.getMapperType())) {
			return name.localName+"("+RulesEditorUtils.XSLT_PREFIX+"\")";
		}
		if (RulesEditorUtils.CREATE_EVENT_FN_QN.equals(fullName)) {
			return RulesEditorUtils.CREATE_EVENT_FN;
		} else if (RulesEditorUtils.CREATE_INSTANCE_FN_QN.equals(fullName)) {
			return RulesEditorUtils.CREATE_INSTANCE_FN;
		} else if (fullName.startsWith(RulesEditorUtils.XPATH_FUNCTION_PREFIX)) {
			return name.localName+"("+RulesEditorUtils.XPATH_PREFIX+"\")";
		}
		return null;
	}

	private void addFunctionTemplateCompletion(int offset, String name,
			Predicate function, List<ICompletionProposal> proposals) {
		// TODO Add template completion proposals for function calls to outline arguments, etc
		addCompletion(offset, name, function, proposals, true);
	}

	private void addCompletion(int offset, String name,
			Object element, List<ICompletionProposal> proposals, boolean isHtml) {
		String info = RulesTextLabelDecorator.getInfo(element, fProjectName);
		if (info.startsWith("<html>")) {
			isHtml = true;
		}
		Image image = fLabelProvider.getImage(element);
		if (element instanceof RuleElement) {
			if (fScopeType != BaseRulesParser.ATTRIBUTE_SCOPE) {
				FunctionRec ruleModelFunction = ElementReferenceResolver.getRuleModelFunction((RuleElement) element);
				if (ruleModelFunction != null) {
					element = ruleModelFunction.function;
				}
			}
		}
		if (element instanceof Predicate) {
			String argSuffix = getCompletionSuffix((Predicate) element);
			addCompletionProposal(offset, proposals, name+argSuffix, info, image, name, element, isHtml);
		} else {
			if (image == null) {
				elementResolutionProviders = ResolutionUtils.getElementResolutionProviders();
				for (IElementResolutionProvider provider : elementResolutionProviders) {
					String type = provider.getElementImageType(element);
					if (type.equals(CommonIndexUtils.PROCESS_EXTENSION)) {
						image =  StudioUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
						break;
					}
				}
			}
			if (info.isEmpty()) {
				for (IElementResolutionProvider provider : elementResolutionProviders) {
					info = provider.getElementInfo(element, null);
					if (!info.isEmpty()) {
						break;
					}
				}
			}
			addCompletionProposal(offset, proposals, name, info, image, null, element, isHtml);
		}
	}

	private String getCompletionSuffix(Predicate element) {
		if (element == null) {
			return "(";
		}
		int length = element.getArguments().length;
		String argSuffix = "(";
		if (length == 0) {
			argSuffix += ")";
		}
		return argSuffix;
	}
	
	private void collectLocalVariableCompletions(int offset, ScopeBlock scope,
			List<ICompletionProposal> proposals) {
		if (scope == null) {
			return;
		}
		if (scope.getType() == BaseRulesParser.FOR_SCOPE) {
			// TODO : traverse all child scope defs for local variables declared prior to the offset of the content assistant
			EList<ScopeBlock> childScopeDefs = scope.getChildScopeDefs();
			if (childScopeDefs.size() == 1) {
				scope = childScopeDefs.get(0);
			}
		}
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			String name = localVariableDef.getName();
			String info = RulesTextLabelDecorator.getInfo(localVariableDef, fProjectName);
			addCompletionProposal(offset, proposals, name, info, fLabelProvider.getImage(localVariableDef), localVariableDef);
		}
		EObject parent = scope.eContainer();
		while (parent != null) {
			if (parent instanceof ScopeBlock) {
				ScopeBlock block = (ScopeBlock) parent;
				for (LocalVariableDef localVariableDef : block.getDefs()) {
					String name = localVariableDef.getName();
					String info = RulesTextLabelDecorator.getInfo(localVariableDef, fProjectName);
					addCompletionProposal(offset, proposals, name, info, fLabelProvider.getImage(localVariableDef), localVariableDef);
				}
			} else if (parent instanceof RuleElement) {
				RuleElement element = (RuleElement) parent;
				for (GlobalVariableDef globalVariableDef : element.getGlobalVariables()) {
					String name = globalVariableDef.getName();
					String info = RulesTextLabelDecorator.getInfo(globalVariableDef, fProjectName);
					addCompletionProposal(offset, proposals, name, info, fLabelProvider.getImage(globalVariableDef), globalVariableDef);
				}
			}
			parent = parent.eContainer();
		}
	}

	private void addCompletionProposal(int offset,
			List<ICompletionProposal> proposals, String name, String info, Image image, Object element) {
		addCompletionProposal(offset, proposals, name, info, image, null, element, false);
	}
	
	private void addCompletionProposal(int offset, List<ICompletionProposal> proposals, String name, 
			String info, Image image, String displayString, Object element, boolean isHtml) {
		RuleCompletionProposalContext context = new RuleCompletionProposalContext(element);
		if (fProposalFilter.length() == 0 || name.toLowerCase().startsWith(fProposalFilter)) {
			CompletionProposal completionProposal = new CompletionProposal(name, offset - fProposalFilter.length(), fProposalFilter.length(),
					name.length(), image, displayString, context, info);
			if (isHtml) {
				proposals.add(new HTMLCompletionProposal(completionProposal));
			} else {
				proposals.add(completionProposal);
			}
		}
	}

	private void printScope(ScopeBlock scope) {
		if (scope == null) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		EList<LocalVariableDef> defs = scope.getDefs();
		for (LocalVariableDef localVariableDef : defs) {
			builder.insert(0, "Def: "+localVariableDef.getName());
		}
		EObject parent = scope.eContainer();
		while (parent != null) {
			if (parent instanceof ScopeBlock) {
				ScopeBlock block = (ScopeBlock) parent;
				for (LocalVariableDef localVariableDef : block.getDefs()) {
					builder.insert(0, "Def: "+localVariableDef.getName()+"\n");
				}
			} else if (parent instanceof RuleElement) {
				RuleElement element = (RuleElement) parent;
				for (GlobalVariableDef globalVariableDef : element.getGlobalVariables()) {
					builder.insert(0, "Def: "+globalVariableDef.getName()+"\n");
				}
			}
			parent = parent.eContainer();
		}
		printDebug("Scope:\n"+builder.toString());
	}

	private RulesParser initializeRulesParser(IDocument document, int offset) {
		fLastMethodCallReference = null;
		RulesLexer lexer = new RulesLexer(new ANTLRStringStream(document.get().substring(0, offset)));
		CommonTokenStream tokens = new CommonTokenStream(lexer, Token.DEFAULT_CHANNEL);
		RulesParser parser = new RulesParser(tokens);
		parser.setTreeAdaptor(new RulesTreeAdaptor());
		RulesASTNode node = parseDocument(parser);
		if (node != null) {
			node.setData("tokens", tokens);
			node.setData("element", parser.getRuleElement());
		} else {
			printDebug("Return value is null");
		}
		if (parser.getRuleElement() != null) {
			parser.getRuleElement().setIndexName(fProjectName);
		}

		return parser;
	}

	private RulesASTNode parseDocument(RulesParser parser) {
		RulesASTNode rulesAST = null;
		switch (fSourceType) {
		case IRulesSourceTypes.FULL_SOURCE:
			rulesAST = parseFullRule(parser);
			return rulesAST;

		case IRulesSourceTypes.CONDITION_SOURCE:
			rulesAST = parseConditions(parser);
			break;
			
		case IRulesSourceTypes.ACTION_SOURCE:
			rulesAST = parseActions(parser);
			break;
			
		case IRulesSourceTypes.ACTION_CONTEXT_SOURCE:
			rulesAST = parseActionContext(parser);
			break;
			
		case IRulesSourceTypes.PRE_CONDITION_SOURCE:
			rulesAST = parsePreConditions(parser);
			break;
			
		default:
			break;
		}
		// For all non-full source cases, we need to add the global variables, as they were not yet parsed
		if (fContextProvider instanceof RuleFormViewer) {
			Compilable compilable = ((RuleFormViewer) fContextProvider).getCommonCompilable();
			if (compilable != null && parser.getRuleElement() != null) {
				RuleElement ruleElement = parser.getRuleElement();
				for (Symbol symbol: compilable.getSymbols().getSymbolList()) {
					LocalVariableDef definition = IndexFactory.eINSTANCE.createLocalVariableDef();
					definition.setName(symbol.getIdName());
					definition.setType(symbol.getType());
					definition.setArray(symbol.isArray());
					ruleElement.getScope().getDefs().add(definition);
				}
			}
		}
		return rulesAST;
	}

	private RulesASTNode parseFullRule(RulesParser parser) {
		startRule_return startRule = null;
		try {
			startRule = parser.startRule();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		if (startRule != null && startRule.getTree() instanceof RulesASTNode) {
			return (RulesASTNode) startRule.getTree();
		}
		return null;
	}

	private RulesASTNode parseConditions(RulesParser parser) {
		ParserRuleReturnScope whenReturn = null;
		try {
			whenReturn = parser.predicateStatements();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		if (whenReturn != null && whenReturn.getTree() instanceof RulesASTNode) {
			return (RulesASTNode) whenReturn.getTree();
		}
		return null;
	}
	
	private RulesASTNode parsePreConditions(RulesParser parser) {
		preconditionStatements_return whenReturn = null;
		try {
			whenReturn = parser.preconditionStatements();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		if (whenReturn != null && whenReturn.getTree() instanceof RulesASTNode) {
			return (RulesASTNode) whenReturn.getTree();
		}
		return null;
	}
	
	private RulesASTNode parseActions(RulesParser parser) {
		thenStatements_return thenReturn = null;
		try {
			thenReturn = parser.thenStatements();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		if (thenReturn != null && thenReturn.getTree() instanceof RulesASTNode) {
			return (RulesASTNode) thenReturn.getTree();
		}
		return null;
	}
	
	private RulesASTNode parseActionContext(RulesParser parser) {
		actionContextStatements_return thenReturn = null;
		try {
			thenReturn = parser.actionContextStatements();
		} catch (RecognitionException e) {
			e.printStackTrace();
		}
		if (thenReturn != null && thenReturn.getTree() instanceof RulesASTNode) {
			return (RulesASTNode) thenReturn.getTree();
		}
		return null;
	}
	
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		
		return new IContextInformation[] { new ContextInformation("mydispstring", "myinfodispstring") };
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '.', '@', '(' /* for mapper functions */};
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return new char[] { '(' };
	}

	public IContextInformationValidator getContextInformationValidator() {
		if (fInfoValidator == null) {
			fInfoValidator = new MethodParameterContextValidator();
		}
		return fInfoValidator;
	}

	public String getErrorMessage() {
		return null;
	}
	
    private String getProposalFilter(int offset, IDocument doc, RulesParser parser) {
        Token lastToken = getLastToken(parser);
        int lastTokenOffset = getLastTokenOffset(parser);
        String filter = "";
        try {
            filter = doc.get(lastTokenOffset+1, offset-lastTokenOffset-1);
            filter = stripLeadingWhitespace(filter);
            fLastCharTyped = doc.get(offset-1, 1);
            printDebug("Last char typed: '"+fLastCharTyped+"'");
            if (filter == null && (doc.getLineOfOffset(offset) == (lastToken.getLine()-1))) { // ANTLR's lines are 1 based, IDocument is 0 based
            	if (lastToken.getChannel() != 99) {
            		filter = lastToken.getText();
            	} else {
            		filter = "";
            	}
            } else if (filter == null) {
                filter = "";
            }
            if (filter.length() == 0) {
                return filter;
            }
            if (fDebug ) {
                System.err.println("proposal filter: '"+filter+"'");
            }
            for (Iterator<String> iter = fNonFilterTokens.values().iterator(); iter.hasNext();) {
                String nonFilterChar = iter.next();
                // this only works for single char non-filter tokens...
                if (filter.charAt(0) == nonFilterChar.toCharArray()[0]) {
                    filter = filter.substring(1);
                    break;
                }
            }
        } catch (BadLocationException e1) {
        	fLastCharTyped = " ";
        }
        return filter;
    }

    private Token getLastToken(RulesParser parser) {
    	TokenStream tokenStream = parser.getTokenStream();
        int size = tokenStream.size();
        for (int i = size-1; i >= 0; i--) {
        	Token tkn = tokenStream.get(i);
        	return tkn;
        }
        return null;
    }

    private int getLastTokenOffset(RulesParser parser) {
    	TokenStream tokenStream = parser.getTokenStream();
        int size = tokenStream.size();
        int delta = 0;
        for (int i = size-1; i >= 0; i--) {
            Token tkn = tokenStream.get(i);
            if (tkn instanceof CommonToken) {
                return ((CommonToken)tkn).getStopIndex() + delta;
            }
            delta += tkn.getText().length();
        }
        return 0;
    }

    private String stripLeadingWhitespace(String unconsumed) {
        int lastNewline = unconsumed.lastIndexOf('\n');
        if (lastNewline != -1) {
            unconsumed = unconsumed.substring(lastNewline);
        }
        
        char arr[] = unconsumed.toCharArray();
        boolean nonEmpty = arr.length > 0;
        for (int i = 0; i < arr.length; i++) {
            if (!Character.isWhitespace(arr[i])) {
                String newString = unconsumed.substring(i);
                return newString;
            }
            nonEmpty = true;
        }
        if (nonEmpty) {
            return "";
        }
        return null;
    }

	private void printDebug(String message) {
		if (fDebug) {
			System.out.println(message);
		}
	}

	public void uninstall() {
		fContextProvider = null;
	}

	public void install(IResolutionContextProvider fResolutionContextProvider) {
		fContextProvider = fResolutionContextProvider;
	}

}
