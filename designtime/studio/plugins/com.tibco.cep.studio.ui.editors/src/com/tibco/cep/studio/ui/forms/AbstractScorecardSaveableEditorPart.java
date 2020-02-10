package com.tibco.cep.studio.ui.forms;

import org.eclipse.core.resources.IFile;

import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.concepts.ScorecardFormEditorInput;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractScorecardSaveableEditorPart extends AbstractSaveableEntityEditorPart{

    protected Scorecard scorecard;
    protected IFile file = null;
    protected ScorecardFormEditorInput scorecardFormEditorInput;

	
    public Scorecard getScorecard() {
		return (Scorecard)getEntity();
	}
    
    /**
	 * This is how the framework determines which interfaces we implement.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(Scorecard.class))
			return getScorecard();
		return super.getAdapter(key);
	}
	
	public void setScorecard(Scorecard concept) {
		this.scorecard = concept;
	}
}
