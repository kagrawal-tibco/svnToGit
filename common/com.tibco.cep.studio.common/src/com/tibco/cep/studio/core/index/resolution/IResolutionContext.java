package com.tibco.cep.studio.core.index.resolution;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;

public interface IResolutionContext {
	
	public static final int INITIAL_MODE 		= 0;
	public static final int LOCAL_VAR_MODE 		= INITIAL_MODE + 1;
	public static final int GLOBAL_VAR_MODE 	= INITIAL_MODE + 2;
	public static final int GENERIC_MODE 		= INITIAL_MODE + 3;
	public static final int ELEMENT_MODE 		= INITIAL_MODE + 4;
	public static final int SHARED_ELEMENT_MODE	= INITIAL_MODE + 5;
	public static final int FUNCTION_MODE 		= INITIAL_MODE + 6;
	public static final int GLOBAL_MODE 		= INITIAL_MODE + 7;
	
	public List<GlobalVariableDef> getGlobalVariables();

	public ScopeBlock getScope();
	public void setScope(ScopeBlock scope);
	public void pushQualifier(Object qualifier);
	public Stack<Object> getQualifierStack();
	public void setMode(int mode);
	public int getMode();
	public int getSubMode();
	public void setSubMode(int mode);

	// for content assist, we want to aggregate all possible resolutions to provide proper proposals 
	// (i.e. resolve the 'System' catalog function as well as the 'System' folder)
	void addResolvedObject(ElementReference obj, Object element);
	HashMap<ElementReference, List<Object>> getResolvedObjects();
	
}
