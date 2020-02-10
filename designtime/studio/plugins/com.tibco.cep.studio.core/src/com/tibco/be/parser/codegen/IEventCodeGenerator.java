package com.tibco.be.parser.codegen;

import com.tibco.cep.designtime.model.event.Event;

public interface IEventCodeGenerator {
	
	public void generateEvent( Event event, CodeGenContext context) throws Exception;
	 public void generateEventStream( Event event, CodeGenContext context) throws Exception;

}
