package com.tibco.cep.studio.debug.core.model.var;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;

import com.sun.jdi.Value;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.RuleDebugStackFrame;
import com.tibco.cep.studio.debug.core.model.RuleDebugThread;

public class VersionVariable extends RuleDebugVariable implements IVariable {
	
	public final static int VERSION_TYPE_SIMPLE = 0;
	public final static int VERSION_TYPE_CACHE = 1;
	public final static String FIELD_FILTER = "m_version";
	private int type;

	
	/**
	 * 
	 * @param debugTarget
	 * @param tinfo
	 * @param name
	 * @param v
	 * @param versionType
	 */
	public VersionVariable(RuleDebugStackFrame frame, 
			RuleDebugThread tinfo,
			String name, 
			Value jdiValue, 
			int versionType) {
		super(frame,tinfo,name,jdiValue);
		this.type = versionType;	
		init();
	}
	
	@Override
	public void init() {
		if (type == VERSION_TYPE_SIMPLE)
    		setName("Version");
    	else if (type == VERSION_TYPE_CACHE)
    		setName("Cache Version");
		StudioDebugCorePlugin.debug("************ Initialized using Version Row *************");
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public String getValueString() throws DebugException {
		// TODO Auto-generated method stub
		return super.getValueString();
	}

}
