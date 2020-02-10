/**
 * 
 */
package com.tibco.cep.decision.table.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Arguments;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.ParentResource;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.util.DTConstants;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.core.utils.DecisionTableColumnIdGenerator;

/**
 * A utility class for some utility methods required for 
 * working with Decision tables in the new Designer.
 * @author aathalye
 * @since 4.0
 *
 */
public class LegacyDecisionTableCoreUtil {
	
	
	/**
	 * The new 4.0 implementation
	 * @param templateResource -> The {@link RuleFunction}
	 * @param projectName -> The designer project name
	 * @param argument -> The {@link com.tibco.cep.decisionproject.ontology.Argument} to set as viewer input
	 * @throws Exception
	 */
	
	private static List<String> processedEntitiesList = new ArrayList<String>();
	
	
	public static void buildArgumentModelDesiderata(RuleFunction templateResource,
			                                        Table tableEModel,
			                                        String projectName,
			                                        com.tibco.cep.decisionproject.ontology.Argument argument) throws Exception {
		int vrfArgsCount = templateResource.getArguments().getArgument().size();
		int tableArgsCount = tableEModel.getArgument().size();
		List<Argument> arguments = tableEModel.getArgument();
		if (vrfArgsCount != tableArgsCount) {
			//Repopulate args
			arguments.clear();
			arguments.addAll(templateResource.getArguments().getArgument());
		}
		for (Argument arg : arguments) {
			ArgumentProperty argProperty = arg.getProperty();
			//Get the Extension
			String entityPath = argProperty.getPath();
			String alias = argProperty.getAlias();
			boolean isArray = argProperty.isArray();
			
			Entity entity = null;
			
			DesignerElement designerElement = IndexUtils.getElement(projectName, entityPath);
			if (designerElement instanceof EntityElement) {
				EntityElement entityElement = (EntityElement)designerElement;
				entity = entityElement.getEntity();
			} 
			
			if (entity != null) {
				//Now convert this entity to an Abstract Resource
				ArgumentResource argumentResource = 
					buildArgumentResource(projectName, entity, alias, isArray);
				if (argumentResource != null) {
					argument.getArgumentEntry().add(argumentResource);
				}
			} else {
				PrimitiveHolder primitiveHolder = OntologyFactory.eINSTANCE.createPrimitiveHolder();
				if(isArray) {
					primitiveHolder.setName(alias + "[]");
				} else{
					primitiveHolder.setName(alias);
				}
				
				primitiveHolder.setFolder("");
				if (entityPath.equalsIgnoreCase(DTConstants.PROPERTY_TYPE_STRING)) {
					primitiveHolder.setPrimitiveType(PropertyDefinition.PROPERTY_TYPE_STRING);
				} else if (entityPath.equalsIgnoreCase(DTConstants.PROPERTY_TYPE_LONG)) {
					primitiveHolder.setPrimitiveType(PropertyDefinition.PROPERTY_TYPE_LONG);
				} else if (entityPath.equalsIgnoreCase(DTConstants.PROPERTY_TYPE_DOUBLE)) {
					primitiveHolder.setPrimitiveType(PropertyDefinition.PROPERTY_TYPE_REAL);
				} else if (entityPath.equalsIgnoreCase(DTConstants.PROPERTY_TYPE_BOOLEAN)) {
					primitiveHolder.setPrimitiveType(PropertyDefinition.PROPERTY_TYPE_BOOLEAN);
				} else if (entityPath.equalsIgnoreCase(DTConstants.PROPERTY_TYPE_DATE_TIME)) {
					primitiveHolder.setPrimitiveType(PropertyDefinition.PROPERTY_TYPE_DATETIME);
				} else if (entityPath.equalsIgnoreCase(DTConstants.PROPERTY_TYPE_INT)) {
					primitiveHolder.setPrimitiveType(PropertyDefinition.PROPERTY_TYPE_INTEGER);
				}
				argument.getArgumentEntry().add(primitiveHolder);
			}
		}
	}
	
	/**
	 * Initialize a {@link ColumnIdGenerator} for an existing table
	 * @param columnIdGenerator
	 * @param table
	 * @since 3.0
	 * TODO Implement this
	 */
	@SuppressWarnings("unused")
	public static void initializeColumnIdGenerator(final DecisionTableColumnIdGenerator columnIdGenerator,
			                                       String projectName,
			                                       final Table table) {
			
		int maxDtColumnId = 0;
		int maxEtColumnId = 0;
		
		List<Column> decisionTableColumns = null;
		List<Column> exceptionTableColumns = null;
		
		TableRuleSet decisionTable = table.getDecisionTable();
		TableRuleSet exceptionTable = table.getExceptionTable();
		
		if (decisionTable != null) {
			Columns decisionTableColumnsModel = decisionTable.getColumns();
			if (decisionTableColumnsModel != null) {
				decisionTableColumns = decisionTableColumnsModel.getColumn();
			}
		}
		if (exceptionTable != null) {
			Columns exceptionTableColumnsModel = exceptionTable.getColumns();
			if (exceptionTableColumnsModel != null) {
				decisionTableColumns = exceptionTableColumnsModel.getColumn();
			}
		}
		
		if (decisionTableColumns != null && decisionTableColumns.size() > 0) {
			maxDtColumnId = getMaxColumnId(decisionTableColumns);
		}
		if (exceptionTableColumns != null && exceptionTableColumns.size() > 0) {
			maxEtColumnId = getMaxColumnId(exceptionTableColumns);
		}
		columnIdGenerator.initializeCounter(maxDtColumnId, maxEtColumnId);
	}
	
	/**
	 * Get Maximum column id based on current persisted order
	 * @param modelColumns
	 * @return
	 */
	private static int getMaxColumnId(List<Column> modelColumns) {
		int size = modelColumns.size();
		int maxColumnId = 0;
		Column lastColumn = modelColumns.get(size - 1);
		String id = lastColumn.getId();
		int idInt = Integer.parseInt(id);
		for (int loop = 0; loop < size; loop++) {
			try {
				lastColumn = modelColumns.get(loop);
				//Find out the max between the 2
				maxColumnId = Math.max(idInt, Integer.parseInt(lastColumn.getId()));
				if (maxColumnId > idInt) {
					idInt = maxColumnId;
				}
			} catch (Exception e) {
				maxColumnId = 0;
			}
		}
		return maxColumnId;
	}
	
		
	static class ResourcePathRefs {
		
		String resourcePath;
		
		String parentResourcePath;

		/**
		 * @param resourcePath
		 * @param parentResourcePath
		 */
		ResourcePathRefs(String resourcePath, String parentResourcePath) {
			this.resourcePath = resourcePath;
			this.parentResourcePath = parentResourcePath;
		}

		/**
		 * @return the resourcePath
		 */
		final String getResourcePath() {
			return resourcePath;
		}

		/**
		 * @return the parentResourcePath
		 */
		final String getParentResourcePath() {
			return parentResourcePath;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof ResourcePathRefs)) {
				return false;
			}
			ResourcePathRefs other = (ResourcePathRefs)obj;
			if (!resourcePath.equals(other.getResourcePath())) {
				return false;
			}
			if (!parentResourcePath.equals(other.getParentResourcePath())) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * @since 4.0
	 * @param projectName
	 * @param entity
	 * @param alias
	 * @param isArrayArgument
	 * @param currentArrayLoopIndex
	 * @return
	 */
	private static ArgumentResource buildArgumentResource(String projectName, 
			                                              final Entity entity, 
			                                              String alias,
			                                              boolean isArrayArgument) {
		ArgumentResource argumentResource = null;
		if (entity instanceof com.tibco.cep.designtime.core.model.element.Concept) {
			com.tibco.cep.designtime.core.model.element.Concept newC = 
				(com.tibco.cep.designtime.core.model.element.Concept)entity;
			com.tibco.cep.decisionproject.ontology.Concept oldC = OntologyFactory.eINSTANCE.createConcept();
			oldC.setName(newC.getName());
			oldC.setFolder(newC.getFolder());
			oldC.setOwnerProjectName(newC.getOwnerProjectName());
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> properties = newC.getAllProperties();
			
			if(isArrayArgument){
				oldC.setAlias(alias + "[]");
			} else {
				oldC.setAlias(alias);
			}
			
			oldC.setScoreCard(newC.isScorecard());
			if(!processedEntitiesList.contains(oldC.getAlias())) {
				processedEntitiesList.add(oldC.getAlias());
				buildArgumentResource(projectName, oldC, properties);
			}
			argumentResource = oldC;
		}
		if (entity instanceof com.tibco.cep.designtime.core.model.event.Event) {
			com.tibco.cep.designtime.core.model.event.Event newE = (com.tibco.cep.designtime.core.model.event.Event)entity;
			com.tibco.cep.decisionproject.ontology.Event oldE = OntologyFactory.eINSTANCE.createEvent();
			oldE.setName(newE.getName());
			oldE.setFolder(newE.getFolder());
			oldE.setOwnerProjectName(newE.getOwnerProjectName());
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> properties = newE.getAllUserProperties();
			oldE.setAlias(alias);
			buildArgumentResource(projectName, oldE, properties);
			argumentResource = oldE;
		}
		//TODO Add primitive type
		processedEntitiesList.clear();
		return argumentResource;
	}
	
	public static ArgumentResource buildArgumentResource(String projectName, 
			final com.tibco.cep.decisionproject.ontology.Concept newC, 
			String alias,
			boolean isArrayArgument) {
		
		List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> properties = ((com.tibco.cep.designtime.core.model.element.Concept)IndexUtils.getEntity(newC.getOwnerProjectName(), newC.getPath())).getAllProperties();
		if(!processedEntitiesList.contains(newC.getAlias())) {
			processedEntitiesList.add(newC.getAlias());
			buildArgumentResource(projectName, newC, properties);
		}
		return newC;
	}
	/**
	 * 
	 * @param <P>
	 * @param projectName
	 * @param old
	 * @param pDefs
	 * @param processedEntitiesPaths
	 * 
	 */
	private static <P extends ParentResource> void buildArgumentResource(String projectName, 
			                                                             final P old,
                                                                         List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> pDefs
                                                                         ) {
		
		String parentResourcePath = old.getFolder() + old.getName();
		for (com.tibco.cep.designtime.core.model.element.PropertyDefinition pDef : pDefs) {
			Property prop = OntologyFactory.eINSTANCE.createProperty();
			int dataType = pDef.getType().getValue();
			//This is used only for contained/referenced concept
			if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
					|| dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
				prop.setPropertyResourceType(pDef.getConceptTypePath());
				prop.setMultiple(pDef.isArray());
				String resourcePath = prop.getPropertyResourceType();
				if (resourcePath != null && resourcePath.length() > 0) {
					Concept oldConcept = OntologyFactory.eINSTANCE.createConcept();
					com.tibco.cep.designtime.core.model.element.Concept newConcept = IndexUtils.getConcept(projectName, resourcePath);
					if (newConcept == null) {
						throw new RuntimeException ("Resource " + resourcePath + " does not have matching concept");
					}
					oldConcept.setName(newConcept.getName());
					oldConcept.setFolder(newConcept.getFolder());
					oldConcept.setOwnerProjectName(newConcept.getOwnerProjectName());
					if (old instanceof ArgumentResource) {
						ArgumentResource argsResource = (ArgumentResource)old;
						String alias = null;
						String addendum = (pDef.isArray()) ? "[]" : "";
						alias = argsResource.getAlias() + "." + pDef.getName() + addendum;
						oldConcept.setAlias(alias);
					}
					old.addChild(oldConcept);
					if (isDTCorePluginAvailable())	com.tibco.decision.table.core.DecisionTableCorePlugin.debug(LegacyDecisionTableCoreUtil.class.getName(), "Contained or referenced concept : {0}", oldConcept.getName());
					if (!processedEntitiesList.contains(oldConcept.getAlias())) {
						processedEntitiesList.add(oldConcept.getAlias());
						//Recurse this
						buildArgumentResource(projectName, oldConcept, newConcept.getAllProperties());
					}
				}
			} else {
				boolean hasAccess = true;
				String propertyPath = parentResourcePath + "/" + pDef.getName();
				if (Utils.isStandaloneDecisionManger()) {
					hasAccess = ensureAccess(pDef.getOwnerProjectName(), propertyPath);
				}
				if (hasAccess) {
					String addendum = (pDef.isArray()) ? "[]" : "";
					prop.setName(pDef.getName() + addendum);
					prop.setDataType(dataType);
					prop.setMultiple(pDef.isArray());
					prop.setFolder(pDef.getFolder());
					prop.setHistoryPolicy(pDef.getHistoryPolicy());
					prop.setHistorySize(pDef.getHistorySize());
					prop.setOwnerProjectName(pDef.getOwnerProjectName());
					old.addChild(prop);
				}
			}
		}
	}
	
	/**
	 * Check for read access before showing a property in args explorer.
	 * @param projectName
	 * @param propertyPath
	 * @return
	 */
	private static boolean ensureAccess(String projectName, String propertyPath) {
		List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
		IAction readPropertyAction = ActionsFactory.getAction(ResourceType.PROPERTY, "read");
		Permit permit = SecurityUtil.ensurePermission(projectName,  
				propertyPath, ResourceType.PROPERTY, roles, readPropertyAction, PermissionType.BERESOURCE);
		return Permit.ALLOW == permit;
	}
	
	/**
	 * The new 4.0 implementation
	 * @param abstractResource -> The {@link RuleFunction} object
	 * @param designerProjectName -> The name of the 4.0 designer project in which to create this
	 * @return
	 * @since 4.0
	 */
	public static Map<String, PropertyPathWithDataType> getPropertyPathWithDataTypeMap(Table table, AbstractResource abstractResource, 
			                                                                           String designerProjectName) {
		Map<String, String> pathIdntMap = null;
		Map<String, PropertyPathWithDataType> propPathMap = null;
		
		pathIdntMap = getPropertyIdentifierMap(abstractResource);
		if (abstractResource instanceof RuleFunction) {
			RuleFunction rf = (RuleFunction)abstractResource;
			com.tibco.cep.decisionproject.ontology.Argument argument = OntologyFactory.eINSTANCE
					.createArgument();
			//Build argument model
			try {
				buildArgumentModelDesiderata(rf, table, designerProjectName, argument);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			propPathMap = getPropertyPathWithDataTypeMap(pathIdntMap, argument, designerProjectName);
		}
		return propPathMap;
	}
	
	/**
	 * Property path and data type of the property maintained.
	 * @author aathalye
	 *
	 */
	public static class PropertyPathWithDataType {
		
		/**
		 * This path is of form /Concepts/Concept/propName
		 */
		private String propertyPath;
		
		private int propertyDataType;

		/**
		 * @param propertyPath
		 * @param propertyDataType
		 */
		public PropertyPathWithDataType(String propertyPath,
				int propertyDataType) {
			this.propertyPath = propertyPath;
			this.propertyDataType = propertyDataType;
		}

		/**
		 * @return the propertyPath
		 */
		public final String getPropertyPath() {
			return propertyPath;
		}

		/**
		 * @return the propertyDataType
		 */
		public final int getPropertyDataType() {
			return propertyDataType;
		}
	}
	
	/**
	 * Get property path map
	 * @param pathIdntMap
	 * @param argument
	 * @param designerProjectName
	 * @return
	 * @since 3.0
	 */
	public static Map<String, PropertyPathWithDataType> getPropertyPathWithDataTypeMap(Map<String, String> pathIdntMap,
			com.tibco.cep.decisionproject.ontology.Argument argument, String designerProjectName) {
		Map<String, PropertyPathWithDataType> propertyPathWithDataTypeMap = new HashMap<String, PropertyPathWithDataType>();
		for (AbstractResource abs : argument.getArgumentEntry()) {
			if (abs instanceof Concept || abs instanceof Event) {
				String path = abs.getFolder() + abs.getName();
				for (Iterator<String> itr = pathIdntMap.keySet().iterator(); itr.hasNext();) {
					String identifier = (String) itr.next();
					if (identifier != null) {
						if (!path.equalsIgnoreCase(pathIdntMap.get(identifier)))
							continue;
						ParentResource parentResource = (ParentResource) abs;
						Iterator<? extends AbstractResource> parentIterator = parentResource.getChildren();
						while (parentIterator.hasNext()) {
							AbstractResource next = parentIterator.next();
							if (next instanceof Property) {
								Property prop = (Property) next;
								String name = prop.getName();
								String propPath = null;
								int dataType = prop.getDataType();
								String propName = null;
								if(abs instanceof ArgumentResource){
									propName = ((ArgumentResource)abs).getAlias() + "." + name;
								} else {
									propName = identifier + "." + name;
								}
								if (prop.eContainer() != null) {
									AbstractResource parent = (AbstractResource) prop.eContainer();
									String folder = null;
									if (parent.getFolder() != null) {
										folder = parent.getFolder();
									} else {
										folder = "/";
									}
									propPath = folder + parent.getName();
								}
								if (dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPT 
										|| dataType == PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
									String resourcePath = prop.getPropertyResourceType();
									if (resourcePath != null && resourcePath.length() > 0) {
										// get concept
										/*Concept oldConcept = (Concept) conceptMap.get(resourcePath);
									if (oldConcept == null) {
										com.tibco.cep.designtime.core.model.element.Concept newConcept = 
											IndexUtils.getConcept(designerProjectName, resourcePath);
										if (newConcept == null) {
											throw new RuntimeException ("Resource " + resourcePath + " does not have matching concept");
										}
										oldConcept = OntologyFactory.eINSTANCE.createConcept();
										oldConcept = new ConceptModelTransformer().transform(newConcept, oldConcept);

										//Reset the name of the property
										prop.setAlias(propName);
										//oldConcept.setAlias(propName);
										propPath = propPath + "/" + oldConcept.getName();
										conceptMap.put(resourcePath, oldConcept);
									}
									populateConRefConceptPropPath(designerProjectName, 
											                      propPathMap, 
											                      propPath,
											                      propName,
											                      oldConcept,
											                      conceptMap);*/
									}
									continue;
								} else {
									propPath = propPath + "/" + name;
								}
								PropertyPathWithDataType propertyPathWithDataType = new PropertyPathWithDataType(propPath, dataType);
								propertyPathWithDataTypeMap.put(propName, propertyPathWithDataType);
							} else if (next instanceof Concept) {
								// this is a contained/referenced concept => get all its properties
								Concept c = (Concept)next;
								Map<String, PropertyPathWithDataType> propertyPathWithDataTypes = new HashMap<String, PropertyPathWithDataType>();
								Deque<String> conceptPaths = new ArrayDeque<String>();
								collectConceptPropertiesWithPaths(c, propertyPathWithDataTypes, conceptPaths);
								for (Map.Entry<String, PropertyPathWithDataType> entry : propertyPathWithDataTypes.entrySet()) {
									propertyPathWithDataTypeMap.put(entry.getKey(), entry.getValue());
								}
							}
						}
					}
				}
			} else if (abs instanceof PrimitiveHolder) {  // process a primitive
				
				int dataType = ((PrimitiveHolder) abs).getPrimitiveType();
				String dataPath = "";
				switch (dataType) {
				case PropertyDefinition.PROPERTY_TYPE_STRING :
					dataPath = DTConstants.PROPERTY_TYPE_STRING;
					break;
				case PropertyDefinition.PROPERTY_TYPE_BOOLEAN :
					dataPath = DTConstants.PROPERTY_TYPE_BOOLEAN;
					break;
				case PropertyDefinition.PROPERTY_TYPE_INTEGER :
					dataPath = DTConstants.PROPERTY_TYPE_INTEGER;
					break;
				case PropertyDefinition.PROPERTY_TYPE_DATETIME :
					dataPath = DTConstants.PROPERTY_TYPE_DATE_TIME;
					break;
				case PropertyDefinition.PROPERTY_TYPE_LONG :
					dataPath = DTConstants.PROPERTY_TYPE_LONG;
					break;
				case PropertyDefinition.PROPERTY_TYPE_REAL :
					dataPath = DTConstants.PROPERTY_TYPE_DOUBLE;
					break;
				}
				PropertyPathWithDataType propertyPathWithDataType = new PropertyPathWithDataType(dataPath, dataType);
				
				propertyPathWithDataTypeMap.put(abs.getName(), propertyPathWithDataType);
			}
		}
		return propertyPathWithDataTypeMap;
	}
	
	/**
	 * Collect all concept properties
	 * 
	 * @param parentConcept parent concept
	 * @param propertyPathWithDataTypes the map of properties
	 * @param conceptPaths	the stack
	 */
	private static void collectConceptPropertiesWithPaths(Concept parentConcept, Map<String, PropertyPathWithDataType> propertyPathWithDataTypes, Deque<String> conceptPaths) {
		String conceptPath = parentConcept.getPath();
		if (true == conceptPaths.contains(conceptPath)) {// check if concept is already visited
			return;
		}
		conceptPaths.push(conceptPath);

		Iterator<? extends AbstractResource> parentIterator = parentConcept.getChildren();
		while (parentIterator.hasNext()) {
			AbstractResource next = parentIterator.next();
			if (next instanceof Property) {
				Property prop = (Property) next;
				String name = prop.getName();
				String propPath = null;
				int dataType = prop.getDataType();
				if (prop.eContainer() != null) {
					AbstractResource parent = (AbstractResource) prop.eContainer();
					String folder = null;
					if (parent.getFolder() != null) {
						folder = parent.getFolder();
					} else {
						folder = "/";
					}
					propPath = folder + parent.getName()+"/"+name; // add the complete prop name
				} else {
					propPath = propPath + "/" + name;
				}
				String fullPropertyAlias = parentConcept.getAlias()+"."+name;
				PropertyPathWithDataType propertyPathWithDataType = new PropertyPathWithDataType(propPath, dataType);
				// get full name for the property
				propertyPathWithDataTypes.put(fullPropertyAlias, propertyPathWithDataType);
			} else if (next instanceof Concept) {
				// this is a contained/referenced concept, so get all the contained properties
				collectConceptPropertiesWithPaths((Concept)next, propertyPathWithDataTypes, conceptPaths);
			}
		}
		conceptPaths.pop();
	}
	
	/**
	 * Get Property identifier map
	 * @param abstractResource
	 * @return
	 * @since 3.0
	 */
	public static Map<String, String> getPropertyIdentifierMap(AbstractResource abstractResource) {
		Map<String, String> pathIdntMap = new HashMap<String, String>();
		if (abstractResource instanceof RuleFunction) {
			RuleFunction rf = (RuleFunction) abstractResource;
			Arguments arguments = rf.getArguments();
			if (arguments != null) {
				for (Argument argument : arguments.getArgument()) {
					ArgumentProperty argProperty = argument.getProperty();
					if (argProperty != null) {
						String identifier = argProperty.getAlias();
						String path = argProperty.getPath();
						pathIdntMap.put(identifier, path);
					}
				}
			}
		}
		return pathIdntMap;
	}
	
	/**
	 * @since 4.0
	 * @param projectName the project name
	 * @return the base URI of the project
	 */
	public static String getCurrentWorkspacePath(String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		
		IProject project = workspace.getRoot().getProject(projectName);
		
		String baseURI = null;
		if (project != null) {
			baseURI = project.getLocation().toPortableString();
		}
		
		return baseURI;
	}
	
	/**
	 * Get the 4.0 Designer project name for this relative file path.
	 * @param relativePathFile
	 * @return
	 * @since 4.0
	 */
	public static String getProjectNameForPath(IFile relativePathFile) {
		IPath path = relativePathFile.getFullPath();
		String pname = path.segments()[0];
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject project = workspace.getRoot().getProject(pname);
		if (project != null) {
			//valid project
			return pname;
		}
		return null;
	}
	
	/**
	 * Check if Eclipse Plugin Class is available
	 * @return
	 */
	public static boolean isDTCorePluginAvailable() {
		boolean pluginAvaliable = true;
		try {
			Class.forName("org.eclipse.core.runtime.Plugin", false, LegacyDecisionTableCoreUtil.class.getClassLoader());
		} catch(ClassNotFoundException classNotFoundException) {
			pluginAvaliable = false;
		}
		return pluginAvaliable;
	}
}