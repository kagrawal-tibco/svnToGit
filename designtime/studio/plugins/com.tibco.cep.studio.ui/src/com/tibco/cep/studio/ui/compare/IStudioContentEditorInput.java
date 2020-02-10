package com.tibco.cep.studio.ui.compare;

import java.io.IOException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.core.runtime.CoreException;

import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;

public interface IStudioContentEditorInput {

	public static final String 	PROP_PROJ_NAME = "project_name"; //$NON-NLS-1$
	
	//left object revision id
	public static final String 	PROP_COMPARE_REVISION_ID_LEFT	= "compare_revision_id_left"; //$NON-NLS-1$
	
	//right object revision id
	public static final String 	PROP_COMPARE_REVISION_ID_RIGHT	= "compare_revision_id_right"; //$NON-NLS-1$
	
	//left object name
	public static final String 	PROP_COMPARE_NAME_LEFT	= "compare_name_left"; //$NON-NLS-1$
	
	//right object name
	public static final String 	PROP_COMPARE_NAME_RIGHT	= "compare_name_right"; //$NON-NLS-1$
	
	//left object absolute path
	public static final String 	PROP_COMPARE_ABS_PATH_LEFT	= "compare_abs_path_left"; //$NON-NLS-1$
	
	//right object absolute path
	public static final String 	PROP_COMPARE_ABS_PATH_RIGHT	= "compare_abs_path_right"; //$NON-NLS-1$

	//left Object extension
	public static final String 	PROP_COMPARE_EXTN_LEFT		= "compare_extn_left"; //$NON-NLS-1$
	
	//right object extension
	public static final String 	PROP_COMPARE_EXTN_RIGHT		= "compare_extn_right"; //$NON-NLS-1$
	
	//is remote left object
	public static final String 	PROP_COMPARE_IS_LEFT_REMOTE = "compare_is_left_remote";//$NON-NLS-1$
	
	//is remote right object
	public static final String 	PROP_COMPARE_IS_RIGHT_REMOTE = "compare_is_right_remote";//$NON-NLS-1$
	
	/**
	 * Fetch left compare Input
	 * @param configuration
	 * @return
	 * @throws Exception
	 */
	public AbstractResourceNode getLeftInput(CompareConfiguration configuration) throws Exception ;
	
	/**
	 * Fetch right compare input
	 * @param configuration
	 * @return
	 * @throws Exception
	 */
	public AbstractResourceNode getRightInput(CompareConfiguration configuration)throws Exception;
	
	/**
	 * create resource node for the given compare input object
	 * @param obj
	 * @param isLeft
	 * @return
	 */
	public AbstractResourceNode createResourceNode(Object obj, boolean isLeft)  throws IOException, CoreException;
}
