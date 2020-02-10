package com.tibco.cep.studio.core.util.packaging;

import com.tibco.be.parser.codegen.CodeGenContext;

/**
 * @author pdhar
 *
 */
public interface ISharedArchiveResourcesProvider {

	public static final int BUFFER_SIZE=102400;
	
		

		/**
		 * @param ctx
		 */
		public void addResources(CodeGenContext ctx) throws Exception;
}
