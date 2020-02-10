package com.tibco.cep.studio.ui.editors.rules.assist;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

import com.tibco.cep.studio.core.index.resolution.IResolutionContextProvider;

public class RulesEditorContentAssistant extends ContentAssistant {

	private IResolutionContextProvider fResolutionContextProvider;

	public RulesEditorContentAssistant(IResolutionContextProvider fProvider) {
		this.fResolutionContextProvider = fProvider;
	}

	@Override
	public IContentAssistProcessor getContentAssistProcessor(String contentType) {
		return super.getContentAssistProcessor(contentType);
	}

	@Override
	protected void install() {
		super.install();
		IContentAssistProcessor processor = getContentAssistProcessor(IDocument.DEFAULT_CONTENT_TYPE);
		if (processor instanceof RulesEditorContentAssistProcessor) {
			((RulesEditorContentAssistProcessor) processor).install(fResolutionContextProvider);
		}
	}

	@Override
	public void uninstall() {
		IContentAssistProcessor processor = getContentAssistProcessor(IDocument.DEFAULT_CONTENT_TYPE);
		if (processor instanceof RulesEditorContentAssistProcessor) {
			((RulesEditorContentAssistProcessor) processor).uninstall();
		}
		super.uninstall();
	}

	public void dispose() {
		this.fResolutionContextProvider = null;
	}
	
}
