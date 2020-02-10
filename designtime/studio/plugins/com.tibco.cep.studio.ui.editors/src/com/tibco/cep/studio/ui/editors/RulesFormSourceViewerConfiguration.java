package com.tibco.cep.studio.ui.editors;

import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.source.ISourceViewer;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.text.ConditionalHighlightingPresenter;
import com.tibco.cep.studio.ui.editors.rules.text.ConditionalHighlightingReconciler;
import com.tibco.cep.studio.ui.editors.rules.text.RulesHyperlinkDetector;
import com.tibco.cep.studio.ui.editors.rules.text.RulesPresentationReconciler;
import com.tibco.cep.studio.ui.editors.rules.text.RulesReconcilingStrategy;
import com.tibco.cep.studio.ui.editors.rules.text.RulesSourceViewerConfiguration;
import com.tibco.cep.studio.ui.editors.rules.text.RulesTextHover;

public class RulesFormSourceViewerConfiguration extends
		RulesSourceViewerConfiguration {

	private int fSourceType;
	private RulesEditor fRulesEditor;
	private ConditionalHighlightingPresenter fPresenter;

	public RulesFormSourceViewerConfiguration(IResolutionContextProvider provider, int reconcileType, RulesEditor rulesEditor) {
		super(null, provider);
		this.fSourceType = reconcileType;
		this.fRulesEditor = rulesEditor;
	}

	@Override
	protected void installConditionalHighlightingPresenter(ISourceViewer sourceViewer, RulesReconcilingStrategy strategy) {
        fPresenter = new ConditionalHighlightingPresenter();
        ConditionalHighlightingReconciler highlightingReconciler = new ConditionalHighlightingReconciler(fPresenter, sourceViewer, fResolutionContextProvider);
        fPresenter.install(sourceViewer, (RulesPresentationReconciler) new RulesSourceViewerConfiguration(null, fResolutionContextProvider).getPresentationReconciler(sourceViewer));
		strategy.addReconcileListener(highlightingReconciler);
//        addReconcileListener(fHighlightingReconciler);
	}
	
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType) {
		return new RulesTextHover(sourceViewer, getHoverType(), getProjectName(), fResolutionContextProvider);
	}

	@Override
	protected IHyperlinkDetector getRuleHyperlinkDetector(
			ISourceViewer sourceViewer) {
		return new RulesHyperlinkDetector(getProjectName(), sourceViewer, getSourceType(), fResolutionContextProvider);
	}

	@Override
	protected String getProjectName() {
		if (fRulesEditor != null) {
			return fRulesEditor.getProjectName();
		}
		return super.getProjectName();
	}

	@Override
	protected int getReconcileType() {
		return getSourceType();
	}

	@Override
	protected int getHoverType() {
		return getSourceType();
	}
	
	@Override
	protected int getSourceType() {
		return fSourceType;
	}

	public void unconfigure() {
		super.unconfigure();
		if (this.fPresenter != null) {
			this.fPresenter.uninstall();
			this.fPresenter = null;
		}
		this.fRulesEditor = null;
	}
	
}