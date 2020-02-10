package com.tibco.cep.repo.provider;

import com.tibco.cep.repo.ArchiveResourceProvider;

/*
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 1, 2006
 * Time: 12:35:56 PM
 */


public interface SharedArchiveResourceProvider extends ArchiveResourceProvider {

	public byte[] getResourceAsByteArray(String uri);
	
}
