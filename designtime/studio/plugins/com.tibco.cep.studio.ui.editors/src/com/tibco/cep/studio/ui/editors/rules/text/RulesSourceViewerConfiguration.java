package com.tibco.cep.studio.ui.editors.rules.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;
import com.tibco.cep.studio.core.rules.IRulesSourceTypes;
import com.tibco.cep.studio.ui.editors.rules.ColorManager;
import com.tibco.cep.studio.ui.editors.rules.IColorConstants;
import com.tibco.cep.studio.ui.editors.rules.RulesEditor;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorContentAssistProcessor;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorContentAssistant;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorQuickFixAssistant;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorQuickFixProcessor;


public class RulesSourceViewerConfiguration extends
		TextSourceViewerConfiguration {

	private ITextDoubleClickStrategy fDoubleClickStrategy;
	private ColorManager 		fColorManager;
	private ITextEditor 		fTextEditor;

	private XMLTagScanner 		fTagScanner;
	private RulesCodeScanner 	fCodeScanner;
	private HeaderCodeScanner 	fHeaderScanner;
	private CommentCodeScanner 	fCommentScanner;
	
	protected RulesReconciler fReconciler;

	protected IResolutionContextProvider fResolutionContextProvider;
	private RulesEditorContentAssistant fEntityContentAssistant;

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (fDoubleClickStrategy == null) {
			fDoubleClickStrategy = new DefaultTextDoubleClickStrategy();
		}
		return fDoubleClickStrategy;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		RulesPresentationReconciler reconciler = new RulesPresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, RulesPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, RulesPartitionScanner.XML_TAG);

		dr = new DefaultDamagerRepairer(getScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(getHeaderScanner());
		reconciler.setDamager(dr, RulesPartitionScanner.HEADER);
		reconciler.setRepairer(dr, RulesPartitionScanner.HEADER);
		
		dr = new DefaultDamagerRepairer(getCommentScanner());
		reconciler.setDamager(dr, RulesPartitionScanner.COMMENT);
		reconciler.setRepairer(dr, RulesPartitionScanner.COMMENT);

		dr = new DefaultDamagerRepairer(getScanner());
		reconciler.setDamager(dr, RulesPartitionScanner.STRING);
		reconciler.setRepairer(dr, RulesPartitionScanner.STRING);
		
		return reconciler;
	}

	public RulesSourceViewerConfiguration(ITextEditor editor, IResolutionContextProvider contextProvider) {
		super();
		this.fColorManager = ColorManager.getInstance();
		this.fTextEditor = editor;
		this.fResolutionContextProvider = contextProvider;
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		IHyperlinkDetector[] hyperlinkDetectors = super.getHyperlinkDetectors(sourceViewer);
		IHyperlinkDetector[] detectors = new IHyperlinkDetector[hyperlinkDetectors.length + 2];
		System.arraycopy(hyperlinkDetectors, 0, detectors, 0, hyperlinkDetectors.length);
		detectors[hyperlinkDetectors.length-1] = getXsltHyperlinkDetector(sourceViewer);
		detectors[hyperlinkDetectors.length] = getRuleHyperlinkDetector(sourceViewer);
		return detectors;
	}

	protected IHyperlinkDetector getRuleHyperlinkDetector(
			ISourceViewer sourceViewer) {
		return new RulesHyperlinkDetector(fTextEditor, sourceViewer, getSourceType(), fResolutionContextProvider);
	}

	protected IHyperlinkDetector getXsltHyperlinkDetector(
			ISourceViewer sourceViewer) {
		return new XsltHyperlinkDetector(fTextEditor, sourceViewer, getSourceType(), getProjectName(), fResolutionContextProvider);
	}
	
	protected int getSourceType() {
		return IRulesSourceTypes.FULL_SOURCE;
	}

	protected RulesCodeScanner getScanner() {
		if (fCodeScanner == null) {
			fCodeScanner = RulesCodeScanner.getInstance(fColorManager);
			fCodeScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						fColorManager.getColor(IColorConstants.DEFAULT))));
		}
		return fCodeScanner;
	}
	
	protected XMLTagScanner getXMLTagScanner() {
		if (fTagScanner == null) {
			fTagScanner = new XMLTagScanner(fColorManager);
			fTagScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						fColorManager.getColor(IColorConstants.TAG))));
		}
		return fTagScanner;
	}

	protected CommentCodeScanner getCommentScanner() {
		if (fCommentScanner == null) {
			fCommentScanner = CommentCodeScanner.getInstance(fColorManager);
			fCommentScanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						fColorManager.getColor(IColorConstants.COMMENT))));
		}
		return fCommentScanner;
	}
	
	protected HeaderCodeScanner getHeaderScanner() {
		if (fHeaderScanner == null) {
			fHeaderScanner = HeaderCodeScanner.getInstance(fColorManager);
			fHeaderScanner.setDefaultReturnToken(
					new Token(
							new TextAttribute(
									fColorManager.getColor(IColorConstants.HEADER))));
		}
		return fHeaderScanner;
	}
	
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
				RulesPartitionScanner.STRING,
				RulesPartitionScanner.KEYWORD,
				RulesPartitionScanner.HEADER,
				RulesPartitionScanner.COMMENT,
				IDocument.DEFAULT_CONTENT_TYPE,
				RulesPartitionScanner.XML_TAG };
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return super.getAnnotationHover(sourceViewer);
	}

	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer,
			String contentType) {
		return new RulesTextHover(sourceViewer, getHoverType(), getProjectName(), fResolutionContextProvider);
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		if (fEntityContentAssistant == null) {
			fEntityContentAssistant = new RulesEditorContentAssistant(fResolutionContextProvider);
			fEntityContentAssistant.setContentAssistProcessor(new RulesEditorContentAssistProcessor(fEntityContentAssistant, fResolutionContextProvider, getProjectName(), getSourceType()), IDocument.DEFAULT_CONTENT_TYPE);
			fEntityContentAssistant.setAutoActivationDelay(500);
			fEntityContentAssistant.enableAutoActivation(true);
			
			fEntityContentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		}
		
		return fEntityContentAssistant;
	}
	
	/**
	 * These are the Strings that get inserted in the code
	 * when a ToggleCommentAction is run
	 */
    public String[] getDefaultPrefixes(ISourceViewer sourceViewer,
            String contentType) {
        return new String[] { "//", "" };
    }

	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		RulesReconcilingStrategy strategy = new RulesReconcilingStrategy(fResolutionContextProvider, getProjectName(), sourceViewer, getReconcileType());
		strategy.setProgressMonitor(new NullProgressMonitor());

		if (fTextEditor instanceof IReconcilingListener) {
			strategy.addReconcileListener((IReconcilingListener) fTextEditor);
		}
		if (true) { // TODO : extract as a preference, whether to highlight local/global vars, catalog functions, etc
			installConditionalHighlightingPresenter(sourceViewer, strategy);
			installSelectionHighlightingPresenter(sourceViewer, strategy);
		}
		
		this.fReconciler = new RulesReconciler(fTextEditor, strategy, false);
		fReconciler.setDelay(500);

		return fReconciler;
	}

	public RulesReconciler getReconciler() {
		return (RulesReconciler) this.fReconciler;
	}

	protected void installConditionalHighlightingPresenter(
			ISourceViewer sourceViewer, RulesReconcilingStrategy strategy) {
        ConditionalHighlightingPresenter presenter = new ConditionalHighlightingPresenter();
        ConditionalHighlightingReconciler highlightingReconciler = new ConditionalHighlightingReconciler(presenter, sourceViewer, fResolutionContextProvider);
		presenter.install(sourceViewer, (RulesPresentationReconciler) new RulesSourceViewerConfiguration(null, fResolutionContextProvider).getPresentationReconciler(sourceViewer));
		strategy.addReconcileListener(highlightingReconciler);
	}

	protected void installSelectionHighlightingPresenter(
			ISourceViewer sourceViewer, RulesReconcilingStrategy strategy) {
        ConditionalHighlightingPresenter presenter = new ConditionalHighlightingPresenter();
        SelectionHighlightingPresenter highlightingReconciler = new SelectionHighlightingPresenter(presenter, sourceViewer, fResolutionContextProvider);
		presenter.install(sourceViewer, (RulesPresentationReconciler) new RulesSourceViewerConfiguration(null, fResolutionContextProvider).getPresentationReconciler(sourceViewer));
		sourceViewer.getTextWidget().addCaretListener(highlightingReconciler);
		strategy.addReconcileListener(highlightingReconciler);
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(
			ISourceViewer sourceViewer, String contentType) {
		List<IAutoEditStrategy> strategies = new ArrayList<IAutoEditStrategy>();
		if (IRulesEditorPartitionTypes.HEADER.equals(contentType)) {
			// only add HeaderAutoEditStrategy for the HEADER partition
			strategies.add(new HeaderAutoEditStrategy());
			return strategies.toArray(new IAutoEditStrategy[strategies.size()]);
		}

		IAutoEditStrategy[] autoEditStrategies = super.getAutoEditStrategies(sourceViewer, contentType);
		for (IAutoEditStrategy autoEditStrategy : autoEditStrategies) {
			if (!(autoEditStrategy instanceof DefaultIndentLineAutoEditStrategy)) {
				strategies.add(autoEditStrategy);
			}
		}
		strategies.add(new RulesEditorAutoIndentStrategy());
		return strategies.toArray(new IAutoEditStrategy[strategies.size()]);
	}

	@Override
	public IQuickAssistAssistant getQuickAssistAssistant(
			ISourceViewer sourceViewer) {
		IQuickAssistAssistant assistant= new RulesEditorQuickFixAssistant();
		assistant.setQuickAssistProcessor(new RulesEditorQuickFixProcessor((RulesEditorQuickFixAssistant) assistant));
		assistant.setInformationControlCreator( new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, EditorsPlugin.getAdditionalInfoAffordanceString());
			}
		});
		
		return assistant;
	}

	protected String getProjectName() {
		if (fTextEditor instanceof RulesEditor) {
			return ((RulesEditor)fTextEditor).getProjectName();
		}
		return null;
	}

	protected int getReconcileType() {
		return IRulesSourceTypes.FULL_SOURCE;
	}

	protected int getHoverType() {
		return IRulesSourceTypes.FULL_SOURCE;
	}

	public IResolutionContextProvider getResolutionContextProvider() {
		return fResolutionContextProvider;
	}
	
	public void unconfigure() {
		this.fResolutionContextProvider = null;
		if (this.fEntityContentAssistant != null) {
			this.fEntityContentAssistant.dispose();
			this.fEntityContentAssistant = null;
		}
		this.fReconciler = null;
	}
}
