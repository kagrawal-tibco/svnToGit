package com.tibco.cep.runtime.model.process;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.service.cluster.om.CacheHandle;

public class ObjectBeanHandle extends BaseHandle  implements CacheHandle {
	ObjectBean tuple;
	public ObjectBeanHandle(TypeInfo tinfo, ObjectBean tuple) {
		super(tinfo);
		this.tuple = tuple;
	}
	@Override
	public Object getObject() {
		return tuple;
	}
	@Override
	public boolean hasRef() {
		return tuple != null;
	}
	@Override
	public Object getRef() {
		return tuple;
	}
	@Override
	public void removeRef() {
		tuple = null;
		
	}
	@Override
	public void touch(WorkingMemory wm) {
		if (wm != null) {
            if(WorkingMemoryImpl.executingInside(wm)) {
                ((WorkingMemoryImpl) wm).recordTouchHandle(this);
            }
        }
		
	}
	@Override
	public long getId() {
		return tuple.getKey().hashCode();
	}
	
}