package com.tibco.cep.studio.ui.editors.rules.text;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.source.ISourceViewer;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.ui.editors.rules.actions.OpenNodeAction;

public class RulesHyperlink implements IHyperlink {

	
	private RulesASTNode fNode;
	private String fProjectName;
	private IResolutionContextProvider fContextProvider;
	private ISourceViewer fSourceViewer;
	private int fSourceType;

	public RulesHyperlink(ISourceViewer sourceViewer, int sourceType, RulesASTNode node, String projectName, IResolutionContextProvider contextProvider) {
		this.fSourceViewer = sourceViewer;
		this.fSourceType = sourceType;
		this.fNode = node;
		this.fProjectName = projectName;
		this.fContextProvider = contextProvider;
	}

	public IRegion getHyperlinkRegion() {
		return new Region(fNode.getOffset(), fNode.getLength());
	}

	public String getHyperlinkText() {
		return null;
	}

	public String getTypeLabel() {
		return null;
	}

	public void open() {
		try {
			OpenNodeAction openAction = new OpenNodeAction(fSourceViewer, fSourceType, fNode, fProjectName, fContextProvider);
			openAction.run();
		} catch (Exception e) {
		}
	}

}
