package com.tibco.cep.runtime.session;

import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/*
* Author: Suresh Subramani / Date: 2/10/12 / Time: 12:56 PM
*/
public interface RuleSessionEx extends RuleSession {

    WorkingMemory getWorkingMemory();

    void registerType(Class type);

    RtcOperationList getRtcOperationList();
}
