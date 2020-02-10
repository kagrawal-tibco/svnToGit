package com.tibco.cep.ws.util.wsdlimport;

import java.util.ArrayList;
import java.util.List;


public class DefaultWsdlImportResult
{

	private final List<String> errors = new ArrayList<String>();
	
	
    private boolean isAbstract = false;

	
	public List<String> getErrors()
	{
		return this.errors;
	}


    public boolean isAbstract()
    {
        return this.isAbstract;
    }


    public void setAbstract(
            boolean isAbstract)
    {
        this.isAbstract = isAbstract;
    }

	
}