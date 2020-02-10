package com.tibco.rta;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tibco.rta.impl.RtaCommandImpl;

/**
 * 
 * An asynchronous notification object that sends server notifications like exceptions and certain life cycle events
 *
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RtaCommandImpl.class, name = "cmd")})
public interface RtaCommand extends RtaAsyncMessage {
	
	/**
	 * 
	 * An enumeration of various commands sent by the server.
	 *
	 */
	public enum Command {
		START_COMPONENT, STOP_COMPONENT, SUSPEND_COMPONENT, RESUME_COMPONENT, START_SCRIPT, CUSTOM_COMMAND
	}
	
	/**
	 * Get the command associated with this message.
	 * @return the command associated with this message.
	 */
	Command getCommand();
}