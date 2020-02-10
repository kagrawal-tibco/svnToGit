package com.tibco.cep.decisionproject.persistence;

import com.tibco.cep.decisionprojectmodel.DecisionProject;

public interface DecisionProjectLoadedListener {
	void projectLoaded(DecisionProject dp);
}
