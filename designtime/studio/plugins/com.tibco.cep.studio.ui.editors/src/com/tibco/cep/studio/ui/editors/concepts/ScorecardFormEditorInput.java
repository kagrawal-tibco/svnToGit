package com.tibco.cep.studio.ui.editors.concepts;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.element.Scorecard;

/**
 * 
 * @author sasahoo
 *
 */
public class ScorecardFormEditorInput extends FileEditorInput{

	@SuppressWarnings("unused")
	private IFile file;
	
	public Scorecard getScorecard() {
		return scorecard;
	}

	public void setScorecard(Scorecard concept) {
		this.scorecard = concept;
	}

	private Scorecard scorecard;
	
	
	public ScorecardFormEditorInput(IFile file, Scorecard scorecard) {
		super(file);
		this.scorecard = scorecard;
		this.file = file;
	}
}