package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.rules.ast.RulesASTNodeFinder;
import com.tibco.cep.studio.core.rules.grammar.RulesParser;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.utils.RulesEditorUtils;

public class XsltHyperlinkDetector implements IHyperlinkDetector {

	private ITextEditor fEditor;
	private ISourceViewer fSourceViewer;
	private int fSourceType;
	private String fProjectName;
	private IResolutionContextProvider fResolutionContextProvider;

	public XsltHyperlinkDetector(ITextEditor textEditor,
			ISourceViewer sourceViewer, int sourceType, String projectName, IResolutionContextProvider provider) {
		this.fEditor = textEditor;
		this.fSourceViewer = sourceViewer;
		this.fSourceType = sourceType;
		this.fProjectName = projectName;
		this.fResolutionContextProvider = provider;
	}

//	public XsltHyperlinkDetector(String projectName,
//			ISourceViewer sourceViewer, int sourceType) {
//		this((ITextEditor)null, sourceViewer, sourceType);
//		this.fProjectName = projectName;
//	}
	
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {
		if (!(fEditor instanceof RulesEditor)) {
			return detectNonEditorHyperlinks(textViewer, region);
		}
		RulesEditor editor = (RulesEditor) fEditor;
		RulesASTNodeFinder finder = new RulesASTNodeFinder(region.getOffset());
		RulesASTNode rulesAST = editor.getRulesAST();
		if (rulesAST == null) {
			return null;
		}
		rulesAST.accept(finder);
		RulesASTNode node = finder.getFoundNode();
		
		if (node == null) {
			return null;
		}
		if (node.getType() != RulesParser.StringLiteral) {
			return null;
		}
		String text = node.getText();
		if (!text.startsWith(RulesEditorUtils.XPATH_PREFIX) && !text.startsWith(RulesEditorUtils.XSLT_PREFIX)) {
			return null;
		}
		XsltHyperlink hyperlink = new XsltHyperlink(node, ((RulesEditor)fEditor).getProjectName(), fEditor.getDocumentProvider().getDocument(fEditor.getEditorInput()), fResolutionContextProvider, fEditor.isEditable());
		return new IHyperlink[] { hyperlink };
	}

	private IHyperlink[] detectNonEditorHyperlinks(ITextViewer textViewer,
			IRegion region) {
		RulesASTNodeFinder finder = new RulesASTNodeFinder(region.getOffset());
		RulesASTNode rulesAST = null;
		if (fEditor != null) {
			rulesAST = (RulesASTNode) fEditor.getAdapter(RulesASTNode.class);
		} else {
			rulesAST = RulesParserManager.getTree(textViewer.getDocument(), fSourceType, fProjectName);
		}
		
		if (rulesAST == null) {
			return null;
		}
		rulesAST.accept(finder);
		RulesASTNode node = finder.getFoundNode();
		
		if (node == null || node.getType() != RulesParser.StringLiteral) {
			return null;
		}
		String text = node.getText();
		if (!text.startsWith(RulesEditorUtils.XPATH_PREFIX) && !text.startsWith(RulesEditorUtils.XSLT_PREFIX)) {
			return null;
		}
		XsltHyperlink hyperlink = new XsltHyperlink(node, fProjectName, textViewer.getDocument(), fResolutionContextProvider, textViewer.isEditable());
		return new IHyperlink[] { hyperlink };
	}

	

}
