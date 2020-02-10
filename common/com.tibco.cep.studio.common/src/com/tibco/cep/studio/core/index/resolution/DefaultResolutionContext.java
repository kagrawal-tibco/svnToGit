package com.tibco.cep.studio.core.index.resolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public abstract class DefaultResolutionContext implements IResolutionContext {

	protected class ResolutionMode {
		
		public Object qualifier;
		public int mode;
		public int sub_mode;

		public ResolutionMode() {
			super();
		}
		
		public ResolutionMode(Object qualifier, int mode, int subMode) {
			this.qualifier = qualifier;
			this.mode = mode;
			this.sub_mode = subMode;
		}

	}
	
	private ScopeBlock fScopeBlock;
	private Stack<Object> fQualifierStack = new Stack<Object>();
	private int fMode = INITIAL_MODE;
	private int fSubMode = 0;
	private HashMap<ElementReference, List<Object>> fResolvedObjects = new HashMap<ElementReference, List<Object>>();
	
	public DefaultResolutionContext(ScopeBlock scopeBlock) {
		this.fScopeBlock = scopeBlock;
	}

	@Override
	public ScopeBlock getScope() {
		return fScopeBlock;
	}

	@Override
	public void setScope(ScopeBlock scope) {
		fScopeBlock = scope;
	}
	
	@Override
	public Stack<Object> getQualifierStack() {
		return fQualifierStack;
	}

	@Override
	public void pushQualifier(Object qualifier) {
		fQualifierStack.push(new ResolutionMode(qualifier, fMode, fSubMode));	
		this.fMode = INITIAL_MODE;
		this.fSubMode = 0;
	}

	@Override
	public int getMode() {
		return fMode;
	}

	@Override
	public void setMode(int mode) {
		this.fMode = mode;
	}

	@Override
	public int getSubMode() {
		return fSubMode;
	}
	
	@Override
	public void setSubMode(int mode) {
		this.fSubMode = mode;
	}
	
	@Override
	public HashMap<ElementReference, List<Object>> getResolvedObjects() {
		return fResolvedObjects;
	}

	@Override
	public void addResolvedObject(ElementReference ref, Object obj) {
		List<Object> list = fResolvedObjects.get(ref);
		if (list == null) {
			list = new ArrayList<Object>();
			fResolvedObjects.put(ref, list);
		}
		if (!list.contains(obj)) {
			list.add(obj);
		}
	}
	
}
