package com.tibco.be.parser.codegen;

import java.util.Map;

import com.tibco.cep.designtime.model.Entity;

public interface IModuleCodeGenerator {
	
	public void generateModule(Entity entity, Map<?,?> archiveProperties) throws Exception;
	public void generateModuleStream(Entity entity, Map<?,?> archiveProperties) throws Exception;
	
}
