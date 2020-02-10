package com.tibco.cep.studio.ui.editors.events;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.ISourceViewer;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.ui.editors.rules.text.RulesHyperlinkDetector;
import com.tibco.cep.studio.ui.editors.rules.text.RulesReconciler;
import com.tibco.cep.studio.ui.editors.rules.text.RulesSourceViewerConfiguration;
import com.tibco.cep.studio.ui.editors.rules.text.RulesTextHover;

public class EventExpiryRuleSourceViewerConfiguration extends RulesSourceViewerConfiguration {

	private int fSourceType;
	private IProject project;
	private IReconciler fReconciler;

	/**
	 * @param contextProvider
	 * @param reconcileType
	 * @param project
	 */
	public EventExpiryRuleSourceViewerConfiguration(IResolutionContextProvider contextProvider, int reconcileType, IProject project) {
		super(null, contextProvider);
		this.fSourceType = reconcileType;
		this.project = project;
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
		if (project != null) {
			return project.getName();
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
	
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		this.fReconciler = super.getReconciler(sourceViewer);
		return this.fReconciler;
	}
	
	public RulesReconciler getReconciler() {
		return (RulesReconciler) this.fReconciler;
	}
}