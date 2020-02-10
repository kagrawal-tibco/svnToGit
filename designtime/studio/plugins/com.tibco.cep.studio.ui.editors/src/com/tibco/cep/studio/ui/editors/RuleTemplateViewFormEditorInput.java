package com.tibco.cep.studio.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;

/**
 */
public class RuleTemplateViewFormEditorInput extends FileEditorInput {

	@SuppressWarnings("unused")
	private IFile file;
	private RuleTemplateView ruleTemplateView;
	
	public RuleTemplateView getTemplateView() {
		return ruleTemplateView;
	}

	public void setRuleTemplateView(RuleTemplateView ruleTemplateView) {
		this.ruleTemplateView = ruleTemplateView;
	}
	
	public RuleTemplateViewFormEditorInput(IFile file, RuleTemplateView ruleTemplateView) {
		super(file);
		this.ruleTemplateView = ruleTemplateView;
		this.file = file;
	}
}
