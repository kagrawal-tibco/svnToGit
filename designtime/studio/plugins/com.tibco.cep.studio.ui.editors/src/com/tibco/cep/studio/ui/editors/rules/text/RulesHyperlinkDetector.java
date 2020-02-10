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

public class RulesHyperlinkDetector implements IHyperlinkDetector {

	private ITextEditor fEditor;
	private ISourceViewer fSourceViewer;
	private int fSourceType;
	private String fProjectName;
	private IResolutionContextProvider fContextProvider;

	public RulesHyperlinkDetector(ITextEditor textEditor,
			ISourceViewer sourceViewer, int sourceType, IResolutionContextProvider resolutionContextProvider) {
		this.fEditor = textEditor;
		this.fSourceViewer = sourceViewer;
		this.fSourceType = sourceType;
		this.fContextProvider = resolutionContextProvider;
	}

	public RulesHyperlinkDetector(String projectName,
			ISourceViewer sourceViewer, int sourceType, IResolutionContextProvider resolutionContextProvider) {
		this((ITextEditor)null, sourceViewer, sourceType, resolutionContextProvider);
		this.fProjectName = projectName;
	}
	
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer,
			IRegion region, boolean canShowMultipleHyperlinks) {
		if (!(fEditor instanceof RulesEditor)) {
			return detectNonEditorHyperlinks(textViewer, region);
		}
		RulesEditor editor = (RulesEditor) fEditor;
		RulesASTNodeFinder finder = new RulesASTNodeFinder(region.getOffset());
		RulesASTNode rulesAST = editor.getRulesAST();
		if (rulesAST == null || rulesAST.isDirty()) {
			System.out.println("AST is dirty, hyperlinks not detected");
			return null;
		}
		rulesAST.accept(finder);
		RulesASTNode node = finder.getFoundNode();
		
		if (node == null) {
			return null;
		}
		while (node.getType() != RulesParser.SIMPLE_NAME && node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		
		if (node.getType() != RulesParser.SIMPLE_NAME) {
//			System.out.println("node is not part of a name node");
			return null;
		}
		RulesHyperlink hyperlink = new RulesHyperlink(fSourceViewer, fSourceType, node, ((RulesEditor)fEditor).getProjectName(), fContextProvider);
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
		if (node == null) {
			return null;
		}
		while (node.getType() != RulesParser.SIMPLE_NAME && node.getParent() != null) {
			node = (RulesASTNode) node.getParent();
		}
		
		if (node.getType() != RulesParser.SIMPLE_NAME) {
			return null;
		}
		RulesHyperlink hyperlink = new RulesHyperlink(fSourceViewer, fSourceType, node, fProjectName, fContextProvider);
		return new IHyperlink[] { hyperlink };
	}

}
