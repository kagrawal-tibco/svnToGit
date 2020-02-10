package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.cep.bpmn.runtime.agent.Job;
import com.tibco.cep.bpmn.runtime.utils.Variables;


public interface LoopTask<T extends AbstractTask> {
	
	/**
	 * @return
	 */
	T getLoopTask();
	
	LoopCharacteristics getLoopCharacteristics();
	
//	boolean hasCheckpoint();
	
	boolean hasWait();
	
	 public static interface LoopCharacteristics {
	    	
	    	boolean testBefore();
	    	
	    	String loopConditionExpr();
	    	
	    	String loopCountVarExpr();
	    	
	    }
	    
	    public static interface MultiInstanceLoopCharacteristics extends LoopCharacteristics {
	    	
	    	public enum Behavior {
	    		NONE, // no event is throws for each instance completion
	    		ONE, // one event is thrown upon for first instance completion
	    		ALL, // no event is thrown, but a token is produced after completion of all instances
	    		COMPLEX; // event is thrown after evaluating an expression.
	    		public static Behavior fromString(String s) {
	    			if(s.equalsIgnoreCase("NONE")){
	    				return NONE;
	    			}
	    			if(s.equalsIgnoreCase("ONE")){
	    				return ONE;
	    			}
	    			if(s.equalsIgnoreCase("ALL")){
	    				return ALL;
	    			}
	    			if(s.equalsIgnoreCase("COMPLEX")){
	    				return COMPLEX;
	    			}
	    			return NONE;
	    		}
	    	}
	    	
	    	boolean isSequential();
	    	
	    	String loopCardinality();
	    	
	    	Behavior getBehavior();
	    	
	    	LoopVarType getLoopVarType();
	    	
	    }
	    
	    public static interface LoopVarType{
	    	String getType();
	    	boolean isConcept();
	    }

	    boolean isComplete(Job job) throws Exception;
	    
	    void setComplete(Job job) throws Exception;
	    
	    void incrementAndSaveCounter(Job job, Variables vars) throws  Exception;

		String getName();

}
