package com.tibco.cep.studio.core.migration.helper;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * Massage column names of DTables imported from 4.0 projects
 * to the new format in 5.0.
 * 
 * @author aathalye
 *
 */
public class ArrayColumnMigrationHelper {
	
	/**
	 * Cache already loaded entities so that they are not read again.
	 * TODO Need to figure out how to clean this map after every migration cycle.
	 */
	private static Map</** Resource Path **/String, Entity> loadedEntityCache = new HashMap<String, Entity>();
	
	/**
	 * Massage column name for array style properties from 4.0 to 5.0 format.
	 * <p>
	 * 4.0 Format : a.b.prop
	 * </p>
	 * <p>
	 * Examples of 5.0 Format : 
	 * <li>
	 * a.b.prop[]
	 * </li>
	 * <li>
	 * a.b[].prop[]
	 * </li>
	 * <li>
	 * a[].b[].prop[]
	 * </li>
	 * </p>
	 * <p>
	 * This only applies to array style column names. For others the format is same.
	 * </p>
	 * @param projectRootDirPath
	 * @param allArgumentPaths
	 * @param allArgumentAliases
	 * @param allResourceTypes
	 * @param columnName
	 * @return
	 */
	public static String getMassagedColumnName(String projectRootDirPath,
			                                   String[] allArgumentPaths, 
			                                   String[] allArgumentAliases,
			                                   String[] allResourceTypes,
			                                   String columnName) {
		File projectRootDir = new File(projectRootDirPath);
		return processColumnName(projectRootDir,   
				                 allArgumentPaths,
				                 allArgumentAliases,
				                 allResourceTypes,
				                 columnName);
	}
	
	/**
	 * 
	 * @param buffer
	 * @return
	 */
	private static String[] tokenize(String buffer, String delimiter) {
		StringTokenizer stringTokenizer = new StringTokenizer(buffer, delimiter);
		String[] tokens = new String[stringTokenizer.countTokens()];
		int tokenCounter = 0;
		while (stringTokenizer.hasMoreTokens()) {
			tokens[tokenCounter++] = stringTokenizer.nextToken();
		}
		return tokens;
	}
	
	/**
	 * Process each old column name and return massaged one.
	 * @param projectRootDir
	 * @param argPaths
	 * @param argAliases
	 * @param resourceTypes
	 * @param columnName
	 * @return
	 */
	private static String processColumnName(File projectRootDir,
			                                String[] argPaths, 
										    String[] argAliases,
										    String[] resourceTypes,
										    String columnName) {
		Queue<String> columnSplitQueue = getColumnSplitQueue(columnName);
		Map<String, Object> argsResourceMap = new HashMap<String, Object>();
		//The paths and respective aliases will be at 
		//same index in their respective arrays.
		for (int loop = 0; loop < argPaths.length; loop++) {
			//Get path and resourcetype
			String argPath = argPaths[loop];
			String argAlias = argAliases[loop];
			String resourceTypeString = resourceTypes[loop];
			ResourceType resourceType = ResourceType.get(resourceTypeString);
			//Check whether it is an entity
			if (resourceType != null) {
				switch (resourceType) {
					case CONCEPT :
						Concept argumentConcept = readConceptArgument(projectRootDir, argPath);
						argsResourceMap.put(argAlias, argumentConcept);
						break;
						
					case EVENT :
						Event argumentEvent = readEventArgument(projectRootDir, argPath);
						argsResourceMap.put(argAlias, argumentEvent);
						break;
				}
			}
		}
		return massageColumnName(projectRootDir, argsResourceMap, columnSplitQueue);
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param conceptArgPath
	 * @return
	 */
	private static Concept readConceptArgument(File projectRootDir, String conceptArgPath) {
		Concept concept = null;
		//See if this exists in map
		if (loadedEntityCache.containsKey(conceptArgPath)) {
			concept = (Concept)loadedEntityCache.get(conceptArgPath);
		} else {
			EObject argumentObject = readEObjectArgument(projectRootDir, conceptArgPath, ".concept");
			if (argumentObject instanceof Concept) {
				concept = (Concept)argumentObject;
				loadedEntityCache.put(conceptArgPath, concept);
			}
		}
		return concept;
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param eventArgPath
	 * @return
	 */
	private static Event readEventArgument(File projectRootDir, String eventArgPath) {
		Event event = null;
		//See if this exists in map
		if (loadedEntityCache.containsKey(eventArgPath)) {
			event = (Event)loadedEntityCache.get(eventArgPath);
		} else {
			EObject argumentObject = readEObjectArgument(projectRootDir, eventArgPath, ".event");
			if (argumentObject instanceof Event) {
				event = (Event)argumentObject;
				loadedEntityCache.put(eventArgPath, event);
			}
		}
		return event;
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param argumentPath
	 * @param extension
	 * @return
	 */
	private static EObject readEObjectArgument(File projectRootDir, String argumentPath, String extension) {
		File argumentFullPath = new File(projectRootDir.getAbsolutePath(), argumentPath + extension);
		EObject argument = null;
		if (argumentFullPath.exists()) {
			try {
				argument = CommonIndexUtils.deserializeEObject(new FileInputStream(argumentFullPath));
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
		return argument;
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param argsResourceMap
	 * @param columnSplitQueue
	 * @return
	 */
	private static String massageColumnName(File projectRootDir,
			                                Map</** Alias, Entity || primitive path **/String, Object> argsResourceMap,
			                                Queue<String> columnSplitQueue) {
		//Get the first element in the queue and match it with any alias
		String head = columnSplitQueue.poll();
		StringBuilder stringBuilder = new StringBuilder();
		if (head != null) {
			stringBuilder.append(head);
			//Get value for this head
			Object resource = argsResourceMap.get(head);
			if (resource instanceof Concept) {
				//Found one. 
				Concept conceptResource = (Concept)resource;
				massageColumnNameForConceptArg(projectRootDir,	
						                       columnSplitQueue,
						                       conceptResource, 
						                       stringBuilder);
			} else if (resource instanceof Event) {
				//Found one. 
				Event eventResource = (Event)resource;
				massageColumnNameForEventArg(projectRootDir,
						                     columnSplitQueue,
						                     eventResource,
						                     stringBuilder);
			} else if (argsResourceMap.containsKey(head) && resource == null) {
				//Resource could not be loaded. Reason - could be from a referenced project library
				//Column name cannot be evaluated, return as is.
				String property = columnSplitQueue.poll();
				if (property != null) {
					stringBuilder.append(".");
					stringBuilder.append(property);
				}	
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param columnSplitQueue
	 * @param eventResource
	 * @param massagedColumnBuffer
	 */
	private static void massageColumnNameForEventArg(File projectRootDir,
			                                         Queue<String> columnSplitQueue, 
			                                         Event eventResource,
			                                         StringBuilder massagedColumnBuffer) {
		while (!columnSplitQueue.isEmpty()) {
			String split = columnSplitQueue.poll();
			//Match against any property
			PropertyDefinition matchingPropertyDefinition = eventResource.getPropertyDefinition(split, true);
			if (matchingPropertyDefinition != null) {
				//Check if it is array type
				boolean isArray = matchingPropertyDefinition.isArray();
				String massaged = (isArray) ? split + "[]" : split;
				massagedColumnBuffer.append(".");
				massagedColumnBuffer.append(massaged);
			} else {
				//Look for super concept if it has one
				if (eventResource.getSuperEventPath() != null && eventResource.getSuperEventPath().trim().length() > 0) {
					EObject superObject = readEObjectArgument(projectRootDir, eventResource.getSuperEventPath(), ".event");
					if (superObject == null) {
						// super event could not be found.  Possibly due to a referenced project library that is no accessible during migration.  
						// Just send back the previous value
						StudioCorePlugin.log(new Status(IStatus.WARNING, StudioCorePlugin.PLUGIN_ID, "Super Event " + eventResource.getSuperEventPath() + " not found during table migration.  Unable to migrate Decision Table column"));
						massagedColumnBuffer.append(".");
						massagedColumnBuffer.append(split);
					}
					Event superEvent = (Event)superObject;
					massageColumnNameForEventArg(projectRootDir,
							                     columnSplitQueue, 
							                     superEvent, 
							                     massagedColumnBuffer);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param projectRootDir
	 * @param columnSplitQueue
	 * @param conceptResource
	 * @param massagedColumnBuffer
	 */
	private static void massageColumnNameForConceptArg(File projectRootDir,	
			                                           Queue<String> columnSplitQueue, 
			                                           Concept conceptResource,
			                                           StringBuilder massagedColumnBuffer) {
		
		while (!columnSplitQueue.isEmpty()) {
			String split = columnSplitQueue.peek();
			//Match against any property
			
			if (split.contains("[]")) {
				split = split.replace("[]", "");
			}
			PropertyDefinition matchingPropertyDefinition = conceptResource.getPropertyDefinition(split, true);
			
			if (matchingPropertyDefinition != null) {
				//Remove from queue
				columnSplitQueue.poll();
				//Check if it is array type
				boolean isArray = matchingPropertyDefinition.isArray();
				String massaged = (isArray) ? split + "[]" : split;
				massagedColumnBuffer.append(".");
				massagedColumnBuffer.append(massaged);
				PROPERTY_TYPES dataType = matchingPropertyDefinition.getType();
				
				if (dataType == PROPERTY_TYPES.CONCEPT_REFERENCE || dataType == PROPERTY_TYPES.CONCEPT) {
					//Get the concept matching this
					String conceptTypePath = matchingPropertyDefinition.getConceptTypePath();
					EObject childConceptObject = readEObjectArgument(projectRootDir, conceptTypePath, ".concept");
					Concept childConceptResource = (Concept)childConceptObject;
					//Recurse
					massageColumnNameForConceptArg(projectRootDir, columnSplitQueue, childConceptResource, massagedColumnBuffer);
				}
			} else {
				//Look for super concept if it has one
				if (conceptResource.getSuperConceptPath() != null  && conceptResource.getSuperConceptPath().trim().length() > 0 ) {
					EObject superObject = readEObjectArgument(projectRootDir, conceptResource.getSuperConceptPath(), ".concept");
					Concept superConcept = (Concept)superObject;
					massageColumnNameForConceptArg(projectRootDir,
							                       columnSplitQueue, 
							                       superConcept, 
							                       massagedColumnBuffer);
				} else {
					// append original column text
					massagedColumnBuffer.append(".");
					massagedColumnBuffer.append(split);
					// invalid column header, log error and move on
					StudioCorePlugin.log(new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, "Invalid column property '"+split+"' in table column '"+massagedColumnBuffer+"'. Unable to migrate table column"));
					//Remove from queue
					columnSplitQueue.poll();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param columnName
	 * @return
	 */
	private static Queue<String> getColumnSplitQueue(String columnName) {
		String[] columnNameTokens = tokenize(columnName, ".");
		Queue<String> columnSplitQueue = new LinkedList<String>();
		for (String columnNameToken : columnNameTokens) {
			columnSplitQueue.offer(columnNameToken);
		}
		return columnSplitQueue;
	}
}
