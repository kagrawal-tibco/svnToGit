/**
 * 
 */
package com.tibco.cep.decision.table.provider.csv;

/**
 * To be implemented by everyone interested in getting notification when 
 * a word is found in the CSV
 * @author aathalye
 *
 */
public interface CSVListener {
	
	/**
	 * An event of type {@link CSVWordEvent} will be generated
	 * whenever a non-tab word/character is found.
	 * @param wordEvent
	 */
	void wordEvent(CSVWordEvent wordEvent);
}
