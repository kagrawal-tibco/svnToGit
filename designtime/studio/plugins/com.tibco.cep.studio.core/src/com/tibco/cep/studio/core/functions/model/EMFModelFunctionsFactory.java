/**
 *
 */
package com.tibco.cep.studio.core.functions.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.functions.impl.FunctionsCatalog;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.semantic.FunctionsCatalogManager;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.modules.CEPModule;
import com.tibco.cep.modules.ModuleRegistry;
import com.tibco.cep.rt.CoreLibPathProvider;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.functions.annotations.AnnotationHandler;
import com.tibco.cep.studio.core.functions.annotations.AnnotationInfoCollector;
import com.tibco.cep.studio.core.functions.annotations.AnnotationInfoCollector.JavaFileInfo;
import com.tibco.cep.studio.core.functions.annotations.CatalogInfo;
import com.tibco.cep.studio.core.functions.annotations.CategoryInfo;
import com.tibco.cep.studio.core.functions.annotations.FunctionCatalogHandler;
import com.tibco.cep.studio.core.functions.annotations.FunctionInfo;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * @author rmishra
 *
 */
public class EMFModelFunctionsFactory {

    private EMFFunctionsCategoryImpl fCategory;
    private FunctionsCatalog fCatalog;
	private String fProjectName;
	private List<DesignerProject> fLoadedProjectReferences = new ArrayList<DesignerProject>();

    /**
     *
     */
    public EMFModelFunctionsFactory(FunctionsCatalog catalog, String projectName) {
    	this.fCategory = new EMFFunctionsCategoryImpl(ExpandedName.makeName(projectName+".Ontology"));
    	this.fCatalog = catalog;
    	this.fProjectName = projectName;
        catalog.register(FunctionsCatalogManager.ONTOLOGY_FUNCTIONS+projectName, fCategory);
    }

    /**
     * load a function category for a specific project
     * @param catalog
     * @throws Exception
     */
    public FunctionsCategory loadModelFunctions() throws Exception {
        loadOntology(fProjectName);
		fCatalog.register(FunctionsCatalogManager.ONTOLOGY_FUNCTIONS+fProjectName, fCategory);
        return fCategory;
    }

    /**
     * Load Model functions with Catalog
     * @param catalog
     * @param ontology
     * @throws Exception
     */
    private void loadOntology(String projectName) throws Exception {
    	DesignerProject index = StudioCorePlugin.getDesignerModelManager().getIndex(projectName);
    	loadOntology(index);
    	fCategory.prepare();
    }

    private void loadOntology(DesignerProject ontology) throws Exception {
    	IndexUtils.waitForUpdate();
    	if (ontology == null) {
    		return;
    	}
    	// load referenced projects first, so that any duplicate entities are used
    	// from the local project
    	EList<DesignerProject> referencedProjects = ontology.getReferencedProjects();
    	for (int i = 0; i < referencedProjects.size(); i++) {
			loadOntology(referencedProjects.get(i));
			fLoadedProjectReferences.add(referencedProjects.get(i));
		}
    	EList<EntityElement> entities = ontology.getEntityElements();
    	for (int i = 0; i < entities.size(); i++) {
			EntityElement ee = entities.get(i);
    		Entity e = null;
    		if (ee instanceof SharedEntityElement) {
    			e = ((SharedEntityElement) ee).getSharedEntity();
    		} else if(ee instanceof ProcessElement) {
    			// Processes are instantiated using process template catalog functions therefore
    			// no catalog function constructors are needed.
    			continue;

    		}else {
    			e = ee.getEntity();
    		}
    		processEntity(e);
		}
    	EList<RuleElement> ruleElements = ontology.getRuleElements();
    	for (int i = 0; i < ruleElements.size(); i++) {
			RuleElement ruleElement = ruleElements.get(i);
    		if (ruleElement.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
    			processRuleElement(ruleElement);
    		}
		}
    	fCategory.prepare();
    }

    public void updateProjectReferences(DesignerProject project) {
    	IndexUtils.waitForUpdateAll();
    	EList<DesignerProject> referencedProjects = project.getReferencedProjects();
    	for (int i = 0; i < referencedProjects.size(); i++) {
			DesignerProject designerProject = referencedProjects.get(i);
			if (loaded(fLoadedProjectReferences, designerProject)) {
				// already loaded
				continue;
			}
			try {
				loadOntology(designerProject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			fLoadedProjectReferences.add(designerProject);
		}
    	// now remove any references that no longer exist
    	List<DesignerProject> unloadedProjects = new ArrayList<DesignerProject>();
    	for (int i = 0; i < fLoadedProjectReferences.size(); i++) {
    		DesignerProject designerProject = fLoadedProjectReferences.get(i);
    		if (!loaded(referencedProjects,designerProject)) {
    			unloadOntology(designerProject);
    			unloadedProjects.add(designerProject);
    		}
		}
    	for (DesignerProject designerProject : unloadedProjects) {
			fLoadedProjectReferences.remove(designerProject);
		}
    }

	private boolean loaded(List<DesignerProject> projects, DesignerProject designerProject) {
		for (int i = 0; i < projects.size(); i++) {
			if (projects.get(i).getName().equals(designerProject.getName())) {
				return true;
			}
		}
		return false;
	}

	private void unloadOntology(DesignerProject ontology) {
    	EList<EntityElement> entities = ontology.getEntityElements();
    	for (int i = 0; i < entities.size(); i++) {
			EntityElement ee = entities.get(i);
    		Entity e = null;
    		if (ee instanceof SharedEntityElement) {
    			e = ((SharedEntityElement) ee).getSharedEntity();
    		} else {
    			e = ee.getEntity();
    		}
    		try {
				removeEntity(e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
    	EList<RuleElement> ruleElements = ontology.getRuleElements();
    	for (RuleElement ruleElement : ruleElements) {
    		if (ruleElement.getElementType() == ELEMENT_TYPES.RULE_FUNCTION) {
    			String path = ModelUtils.convertPathToPackage(ruleElement.getFolder());
    			removeElement(ruleElement.getName(), true, false, path);
    		}
    	}
	}

	public void processRuleElement(RuleElement ruleElement) throws Exception {
		registerRuleFunction((RuleFunction)ruleElement.getRule());
	}

	public void processEntity(Entity e) throws Exception {
		if (isModuleEntity(e)) {
			declareModuleMethod(e);
		}
		else if (e instanceof Concept) {
			registerConcept(e);
		}
		else if (e instanceof Event) {
			registerEvent(e);
		}
		else if (e instanceof RuleFunction) {
			registerRuleFunction((RuleFunction) e);
		}
		else if (e instanceof Channel) {
			registerChannel(e);
		} else if( e instanceof JavaSource) {
			registerJava(e);
		}
	}

	public void removeEntity(Entity e) throws Exception {
		if (isModuleEntity(e)
				|| e instanceof Event
				|| e instanceof RuleFunction
				/*|| e instanceof Channel*/ // not currently supporting channels
				|| e instanceof Concept) {

    		boolean timeEvent = e instanceof TimeEvent;
    		String fullPath = ModelUtils.convertPathToPackage(e.getFullPath());
    		removeElement(e.getName(), false, timeEvent, fullPath);

		}
	}

    /**
     * @param concept
     */
    public void conceptAdded(Concept concept) throws Exception {

        //remove leading slash and replace slashes with dots
        String fullPath = concept.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());

        String generatedJavaName;

        ExpandedName conceptName = ExpandedName.makeName(fullPath, concept.getName());

        EMFConceptModelFunction function;
//        if(concept.isAScorecard()) {
//            generatedJavaName = "get" + concept.getName();
//            function = new ScorecardModelFunction(concept, conceptName, generatedJavaName);
//        }
//        else {
        generatedJavaName = "new" + concept.getName();
        function = new EMFConceptModelFunction(concept, conceptName, generatedJavaName);
//        }

        FunctionsCategory conceptCategory = addPredicate(function, fullPath);

        //declareModuleMethods(concept, fullPath);

        declarePOJOMethods(concept, fullPath);

//        if (concept instanceof DBConcept) {
//            DBConcept dbconcept = (DBConcept) concept;
//            generatedJavaName = "querySingle" + dbconcept.getName();
//            function = new DBConceptModelFunction(dbconcept, ExpandedName.makeName(fullPath, generatedJavaName), generatedJavaName);
//            addPredicate(function, fullPath);
//
//            generatedJavaName = "queryMultiple" + dbconcept.getName();
//            function = new MultipleDBConceptModelFunction(dbconcept, ExpandedName.makeName(fullPath, generatedJavaName), generatedJavaName);
//            addPredicate(function, fullPath);
//        }

        /** TODO ISS- Not supporting State Machine stuff in rules in V 1.0 */
        if (0 == 0) {
            return;
        }

        /** Add StateMachine category, and state names */
        StateMachine sm = concept.getRootStateMachine();
        if (sm == null) {
            return;
        }

        String smPath = fullPath + ModelNameUtil.RULE_SEPARATOR_CHAR + sm.getName();
        ExpandedName smName = ExpandedName.makeName(fullPath, sm.getName());
        FunctionsCategory smCategory = new EMFStateModelFunctionCategory(smName, sm);
        conceptCategory.registerSubCategory(smCategory);

        /** Add each state name as a predicate */
        registerStatePredicates(smPath, smCategory, sm);
    }

    private boolean isModuleEntity(Entity entity) {
    	if (entity == null) {
    		return false;
    	}
    	PropertyMap propertyMap = entity.getTransientProperties();
        String typeName = getTransientProperty(propertyMap, "typeName");
        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
        return (module != null) ;
    }

    private void declareModuleMethod(Entity entity) throws Exception {
    	PropertyMap propertyMap = entity.getTransientProperties();
        String typeName = getTransientProperty(propertyMap, "typeName");
        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
        if (module != null) {
//            DTFactory dtFactory = module.getDesigntimeFactory();
//            dtFactory.addOntologyMethods(entity); // TODO : what is the corresponding method?
        }

    }

    private String getTransientProperty(PropertyMap propertyMap, String propertyName) {
    	if (propertyMap == null) {
    		return null;
    	}
    	EList<Entity> properties = propertyMap.getProperties();
    	for (Entity entityProp : properties) {
			if (entityProp.getName().equals(propertyName)) {
				if (entityProp instanceof SimpleProperty) {
					return ((SimpleProperty) entityProp).getValue();
				} else if (entityProp instanceof PropertyMap) {
					String propVal = getTransientProperty((PropertyMap) entityProp, propertyName);
					if (propVal != null) {
						return propVal;
					}
				}
			}
		}
    	return null;
    }

    private void declarePOJOMethods(Concept concept, String fullPath) throws Exception {
        boolean usePojoStyle = Boolean.valueOf(System.getProperty("TIBCO.BE.MODEL.USEPOJOFORCONCEPTS", "false")).booleanValue();
        if (usePojoStyle) {
            Iterator<PropertyDefinition> r  = concept.getProperties().iterator();
            while (r.hasNext()) {
                PropertyDefinition pd = (PropertyDefinition) r.next();
                char c = pd.getName().charAt(0);
                StringBuffer buf = new StringBuffer().append(Character.toUpperCase(c)).append(pd.getName().substring(1));

                String setPropertyName =  "set" + buf.toString();
                ExpandedName pdName = ExpandedName.makeName(fullPath, setPropertyName);
                addPredicate (new EMFConceptPropertyModelFunction(pd, pdName, setPropertyName, true), fullPath);

                String getPropertyName = "get" + buf.toString();
                pdName = ExpandedName.makeName(fullPath, getPropertyName);
                addPredicate (new EMFConceptPropertyModelFunction(pd, pdName, getPropertyName, false), fullPath);
            }

            ExpandedName methodName = ExpandedName.makeName(fullPath, "new"+concept.getName());
            addPredicate(new EMFConceptMethodModelFunction(concept, methodName, "new"+concept.getName(), new String[0] , new Class[0] , java.lang.Object.class), fullPath);

            methodName = ExpandedName.makeName(fullPath, "Initialize");
            addPredicate(new EMFConceptMethodModelFunction(concept, methodName, "Initialize", new String[0] , new Class[0] , void.class), fullPath);


        }
    }

    private void registerStatePredicates(String path, FunctionsCategory parentCategory, StateComposite root) throws Exception {
        List<StateEntity> entities = root.getStateEntities();
        if (entities == null) {
            return;
        }

        Iterator<StateEntity> it = entities.iterator();
        while (it.hasNext()) {
            State substate = (State) it.next();
            String subpath = path + ModelNameUtil.RULE_SEPARATOR_CHAR + substate.getName();
            ExpandedName subname = ExpandedName.makeName(path, substate.getName());

            if (substate instanceof StateComposite) {
                StateComposite comp = (StateComposite) substate;
                EMFModelFunctionCategory compCategory = new EMFStateModelFunctionCategory(subname, substate);
                parentCategory.registerSubCategory(compCategory);

                if (comp.isConcurrentState()) {
                    List<StateComposite> regions = comp.getRegions();
                    if (regions == null) {
                        continue;
                    }

                    Iterator<StateComposite> regionIt = regions.iterator();
                    while (regionIt.hasNext()) {
                        StateComposite region = (StateComposite) regionIt.next();
                        String regionPath = subpath + ModelNameUtil.RULE_SEPARATOR_CHAR + region.getName();
                        ExpandedName regionName = ExpandedName.makeName(subpath, region.getName());

                        EMFModelFunctionCategory regionCategory = new EMFStateModelFunctionCategory(regionName, region);
                        compCategory.registerSubCategory(regionCategory);
                        registerStatePredicates(regionPath, regionCategory, region);
                    }
                } else {
                    registerStatePredicates(subpath, compCategory, comp);
                }
            } else {
                registerStatePredicate(path, parentCategory, substate);
            }
        }

    }

    private void registerStatePredicate(String parentPath, FunctionsCategory parentCat, State state) throws Exception {
        ExpandedName statename = ExpandedName.makeName(parentPath, state.getName());
        EMFStatePredicate sp = new EMFStatePredicate(state, statename);
        parentCat.registerPredicate(sp);
    }

    /**
     * @param timeEvent
     * @throws Exception
     */
    private void timeEventAdded(Event timeEvent) throws Exception {

        String fullPath = timeEvent.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        EMFTimeEventFunction function = new EMFTimeEventFunction(timeEvent, ExpandedName.makeName(fullPath, "schedule" + timeEvent.getName()));
        addPredicate(function, fullPath);
    }

    private void simpleEventAdded(Event simpleEvent) throws Exception {

        String fullPath = simpleEvent.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        EMFEventModelFunction_Create function = new EMFEventModelFunction_Create(simpleEvent, ExpandedName.makeName(fullPath, simpleEvent.getName()), "new" + simpleEvent.getName());
        addPredicate(function, fullPath);
    }

    private void channelAdded(Channel channel) throws Exception {
        String fullPath = channel.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());

//        String folderPath = channel.getFolderPath();
//        ExpandedName folderName = ExpandedName.makeName(folderPath);

//        FunctionsCategory folderCat = getINSTANCE().m_category;
//        if(channel.getFolder().getParent() != null) {
//            folderCat = getINSTANCE().m_category.getSubCategory(folderName);
//            if(folderCat == null) {
//                folderCat = new FunctionsCategoryImpl(folderName, true, true);
//                getINSTANCE().m_category.registerSubCategory(folderCat);
//            }
//        }

        //TODO ISS not supporting Channels/Destinations
        if (0 == 0) {
            return;
        }

        ExpandedName catName = ExpandedName.makeName(fullPath);
        EMFChannelModelFunctionCategory channelFnCat = new EMFChannelModelFunctionCategory(catName, channel);
//        folderCat.registerSubCategory(channelFnCat);

        registerDestinations(channelFnCat, channel, fullPath);
    }

    private void registerDestinations(FunctionsCategory cat, Channel channel, String fullPath) throws Exception {
        DriverConfig driver = channel.getDriver();
        if (driver == null) {
            return;
        }

        Iterator<Destination> destIt = driver.getDestinations().iterator();
        while (destIt.hasNext()) {
            Destination destination = (Destination) destIt.next();
            ExpandedName destCatName = ExpandedName.makeName(fullPath, destination.getName());

            String destinationPath = fullPath + "." + destination.getName();

            EMFDestinationModelFunctionCategory dmfc = new EMFDestinationModelFunctionCategory(destCatName, false, true, destination);
//            cat.registerSubCategory(dmfc);

            /** Add the properties for the destination */
            PropertyMap instance = destination.getProperties();
//            List<EObject> c = instance.getProperties();

//            Iterator allPropIt = instance.iterator();
            EList<Entity> properties = instance.getProperties();
            for (Entity entity : properties) {
    			String propertyName = (String) entity.getName();
//                Collection props = (Collection) allPropIt.next();
//                Iterator propIt = props.iterator();
//                while (propIt.hasNext()) {
//                    PropertyInstance pi = (PropertyInstance) propIt.next();
                    ExpandedName propName = ExpandedName.makeName(destinationPath, propertyName);
                    EMFDestinationPropertyPredicate dpp = new EMFDestinationPropertyPredicate(destination, propName);
                    addPredicate(dpp, destinationPath);
//                }
            }

            /** Add one for the event URI */

        }
    }

    public FunctionsCategory addPredicate(Predicate predicate, String fullPath) throws Exception {

        String names[] = fullPath.split("\\(|\\)|\\.");
        FunctionsCategory foundOne = fCategory;
        String ns = null;

        if (names.length >= 0) {
            for (int i = 0; i < names.length; i++) {
                FunctionsCategory fc = foundOne.getSubCategory(ExpandedName.makeName(names[i]));
                if (fc == null) {
                    if (predicate instanceof EMFDestinationPropertyPredicate && (i == names.length - 1)) {
                        EMFDestinationPropertyPredicate dpp = (EMFDestinationPropertyPredicate) predicate;
                        Destination dest = dpp.getDestination();

                        if (ns != null) {
                            fc = new EMFDestinationModelFunctionCategory(ExpandedName.makeName(ns, names[i]), dest);
                        } else {
                            fc = new EMFDestinationModelFunctionCategory(ExpandedName.makeName(names[i]), dest);
                        }
                    } else if (predicate instanceof EMFDestinationPropertyPredicate && (i == names.length - 2)) {
                        EMFDestinationPropertyPredicate dpp = (EMFDestinationPropertyPredicate) predicate;
                        Destination dest = dpp.getDestination();
                        DriverConfig driverConfig = dest.getDriverConfig();
                        Channel channel = driverConfig.getChannel();

                        if (ns != null) {
                            fc = new EMFChannelModelFunctionCategory(ExpandedName.makeName(ns, names[i]), channel);
                        } else {
                            fc = new EMFChannelModelFunctionCategory(ExpandedName.makeName(names[i]), channel);
                        }
                    } else if (predicate instanceof EMFOntologyModelFunction && i == names.length - 1) {
                        // last part of path to entity

                        if (predicate instanceof EMFModelRuleFunction) {
                            fc = foundOne;
                            continue;
                        }

                        if (ns != null) {
                            fc = new EMFModelFunctionCategory(ExpandedName.makeName(ns, names[i]), ((EMFOntologyModelFunction) predicate).getModel());
                        } else {
                            fc = new EMFModelFunctionCategory(ExpandedName.makeName(names[i]), ((EMFOntologyModelFunction) predicate).getModel());
                        }
                    } else {
                        if (ns != null) {
                            fc = new EMFFunctionsCategoryImpl(ExpandedName.makeName(ns, names[i]));
                        } else {
                            fc = new EMFFunctionsCategoryImpl(ExpandedName.makeName(names[i]));
                        }
                    }

                    foundOne.registerSubCategory(fc);
                    if (ns != null) {
                        ns += fc.getName().getLocalName();
                    } else {
                        ns = fc.getName().getLocalName();
                    }
                }
                foundOne = fc;
            }
            foundOne.registerPredicate(predicate);
        }

        return foundOne;
    }

//    void force(Ontology ontology) throws Exception {
////        m_ontology = null;
//        loadOntology(ontology);
//
//    }
    
    public void registerJava(Entity e) throws Exception {
//    	javaFunctionAdded((JavaSource) e);
    }

    public void registerChannel(Entity e) throws Exception {
        final Channel channel = (Channel) e;
        channelAdded(channel);
//        channel.addEntityChangeListener(INSTANCE);
    }

    public void registerRuleFunction(RuleFunction rf) throws Exception {
        ruleFunctionAdded(rf);
//        rf.addEntityChangeListener(INSTANCE);
    }

    public void registerEvent(Entity e) throws Exception {
        final Event event = (Event) e;
        if (event instanceof TimeEvent) {
            if (((TimeEvent)event).getScheduleType() == EVENT_SCHEDULE_TYPE.RULE_BASED) {
                timeEventAdded(event);
            } else {
            	IFile file = IndexUtils.getFile(e.getOwnerProjectName(), e);
            	if (file != null) {
            		removeElement(file);
            	}
            }
        } else {
            simpleEventAdded(event);
        }
//        event.addEntityChangeListener(INSTANCE);
    }

    public void registerConcept(Entity e) throws Exception {
        Concept concept = (Concept) e;
        if (concept.isScorecard()) {
            return;
        }
        //Added By Anand To Support Metric - 11/23/2009 - START
        if (concept instanceof Metric){
        	metricAdded((Metric)concept);
        	return;
        }
        //Added By Anand To Support Metric - 11/23/2009 - END
        conceptAdded(concept);
//        concept.addEntityChangeListener(INSTANCE);
    }
    
   
    
    /**
     * @param javaSource static function annotated by {@link BEFunction}
     * @throws Exception
     */
    void javaFunctionAdded(JavaSource javaSource) throws Exception {
      // Get Annotation Info
      org.eclipse.core.resources.IProject proj = org.eclipse.core.resources.ResourcesPlugin.getWorkspace().getRoot().getProject(fProjectName);
      IFile file = IndexUtils.getFile(proj,javaSource);
      
      String classPath = CoreLibPathProvider.getCoreLibPathsAsString();
      AnnotationHandler<?>[] handlers = new AnnotationHandler[]{ new FunctionCatalogHandler()};
      AnnotationInfoCollector collector = AnnotationInfoCollector.newInstance(classPath,handlers);
      JavaFileInfo info = AnnotationInfoCollector.JavaFileInfo.createJavaFileInfo(javaSource);
      collector.addFile(info);
      Map<Class<?>, Object> result = collector.collect();
      if(result.isEmpty())
    	  return;
      
      CatalogInfo catalogInfo = (CatalogInfo) result.get(CatalogInfo.class);
       

      String generatedJavaName = javaSource.getName();
      for(CategoryInfo categoryInfo:catalogInfo.getCategories()){
    	  for(FunctionInfo fnInfo: categoryInfo.getFunctions()){
//    		  String fullPath = (categoryInfo.getCategory().length() > 0) ? categoryInfo.getCategory() + ModelNameUtil.RULE_SEPARATOR_CHAR + fnInfo.getName() : fnInfo.getName();
    		  String fullPath = categoryInfo.getCategory();
    		  ExpandedName rfName = ExpandedName.makeName(categoryInfo.getCategory(), fnInfo.getName());
    		  EMFModelJavaFunction fn = new EMFModelJavaFunction(javaSource, rfName, fnInfo.getName(),categoryInfo,fnInfo);
    		  addPredicate(fn, fullPath);
    	  }
    	  
      }
  }
    
   

    void ruleFunctionAdded(RuleFunction rf) throws Exception {

//        String fullPath= rf.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        String folderPath = rf.getFolder();
        if (folderPath.length() > 0) {
        	folderPath = folderPath.substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        }

        if (folderPath.length() > 0 && folderPath.charAt(folderPath.length() - 1) == ModelNameUtil.RULE_SEPARATOR_CHAR)
        {
            folderPath = folderPath.substring(0, folderPath.length() - 1);
        }

        String generatedJavaName = rf.getName();

        ExpandedName rfName = ExpandedName.makeName(folderPath, rf.getName());
        EMFModelRuleFunction fn = new EMFModelRuleFunction(rf, rfName, generatedJavaName);

        String fullPath = (folderPath.length() > 0) ? folderPath + ModelNameUtil.RULE_SEPARATOR_CHAR + rf.getName() : rf.getName();

        addPredicate(fn, fullPath);
    }

	public void removeElement(IFile file) {
		IPath fullPath = file.getFullPath();
		fullPath = fullPath.removeFileExtension();
		fullPath = fullPath.removeFirstSegments(1); // remove project name
		String fileName = fullPath.lastSegment();
		boolean ruleType = IndexUtils.isRuleType(file);
		if (ruleType) {
			// categories are kept slightly differently between Entities and Rules/RuleFunctions, need to remove last segment here
			fullPath = fullPath.removeLastSegments(1);
		}
		boolean isTimeEvent = "time".equalsIgnoreCase(file.getFileExtension());
		//Added by Anand to fix 1-AY6KUS - 06/25/2010
		boolean isMetric = "metric".equalsIgnoreCase(file.getFileExtension());
		String path = fullPath.toString().replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
		if (isMetric == true){
			metricRemoved(fileName,path);
		} else {
			removeElement(fileName, ruleType, isTimeEvent, path);
		}
	}

	private void removeElement(String fileName, boolean isRuleType,
			boolean isTimeEvent, String path) {
		try {
			FunctionsCategory category = null;
			if (path == null || path.length() == 0) {
				category = fCategory;
			} else {
				category = getFunctionsCategory(path);
			}
			if (category == null) {
				StudioCorePlugin.log(new Status(Status.INFO, StudioCorePlugin.PLUGIN_ID, "Category is null for "+path));
				return;
			}
			ExpandedName predName = null;
			if (isTimeEvent) {
				predName = ExpandedName.makeName("schedule"+fileName);
			} else {
				if(fileName != null)
					predName = ExpandedName.makeName(fileName);
			}
			if(predName ==  null)return;
			Predicate predicate = category.getPredicate(predName, true);
			category.deregisterPredicate(predicate, true);
			if (!isRuleType) {
				int index = path.lastIndexOf('.');
				if (index > 0) {
					String parentPath = path.substring(0, index);
					FunctionsCategory parentCategory = getFunctionsCategory(parentPath);
					parentCategory.deregisterSubCategory(category, true);
					checkForEmptyCategory(parentCategory);
				} else {
					fCategory.deregisterSubCategory(category, true);
				}
			} else {
				checkForEmptyCategory(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkForEmptyCategory(FunctionsCategory category) {
		try {
			if (!category.all().hasNext()) {
				if (category instanceof EMFFunctionsCategoryImpl) {
					FunctionsCategory parentCategory = ((EMFFunctionsCategoryImpl) category).getParentCategory();
					if (parentCategory != null) {
						parentCategory.deregisterSubCategory(category, true);
						checkForEmptyCategory(parentCategory);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public FunctionsCategory getFunctionsCategory(String fullPath) throws Exception {
        String names[] = fullPath.split("\\(|\\)|\\.");
        FunctionsCategory foundOne = fCategory;
        for (String subCatName : names) {
        	if (foundOne == null) {
        		return null;
        	}
        	foundOne = foundOne.getSubCategory(ExpandedName.makeName(subCatName));
		}

        return foundOne;
	}

	public void conceptRemoved(String fullPath, String name) {
        //remove leading slash and replace slashes with dots
//        String fullPath = concept.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());

        String generatedJavaName;

        ExpandedName conceptName = ExpandedName.makeName(fullPath, name);

        EMFConceptModelFunction function;
//        if(concept.isAScorecard()) {
//            generatedJavaName = "get" + concept.getName();
//            function = new ScorecardModelFunction(concept, conceptName, generatedJavaName);
//        }
//        else {
        generatedJavaName = "new" + name;
        function = new EMFConceptModelFunction(null, conceptName, generatedJavaName);

	}

	public void removeFolder(IFolder file) {
		IPath fullPath = file.getFullPath();
		fullPath = fullPath.removeFirstSegments(1); // remove project name
		String path = fullPath.toString().replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
		removeFolder(path);

	}

	private void removeFolder(String path) {
		try {
			FunctionsCategory category = getFunctionsCategory(path);
			if (category == null) {
				System.out.println("Category is null for "+path);
				return;
			}
			int index = path.lastIndexOf('.');
			if (index > 0) {
				String parentPath = path.substring(0, index);
				FunctionsCategory parentCategory = getFunctionsCategory(parentPath);
				parentCategory.deregisterSubCategory(category, true);
				checkForEmptyCategory(parentCategory);
			} else {
				fCategory.deregisterSubCategory(category, true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//Added to support metric - Anand - 11/23/2009
    private void metricAdded(Metric metric) throws Exception {
    	//conceptAdded(metric);
		if (metric.getProperties().isEmpty() == true) {
			return;
		}
		String fullPath = metric.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);
		// add compute method
		Map<String, PropertyDefinition> groupByFields = new LinkedHashMap<String, PropertyDefinition>();
		Map<String, PropertyDefinition> aggrFields = new LinkedHashMap<String, PropertyDefinition>();
		Map<String, PropertyDefinition> sysFields = new HashMap<String, PropertyDefinition>();
		Map<String, List<String>> dependentFieldMapping = new HashMap<String, List<String>>();

		for (PropertyDefinition field : metric.getProperties()) {
			String name = field.getName();
			if (field.isGroupByField() == true) {
				// add group by fields
				groupByFields.put(name, field);
			} else {
				boolean isSysField = false;
				List<String> dependentFields = new LinkedList<String>();
				// check if the field is dependent field
				PropertyMap extendedProperties = field.getExtendedProperties();
				if (extendedProperties != null) {
					EList<Entity> extendedPropertiesList = extendedProperties.getProperties();
					if (extendedPropertiesList != null && extendedPropertiesList.isEmpty() == false) {
						for (Entity entity : extendedPropertiesList) {
							if (entity instanceof SimpleProperty) {
								SimpleProperty property = (SimpleProperty) entity;
								if (property.getName().equals("system") == true) {
									isSysField = Boolean.valueOf(property.getValue());
								} else if (property.getName().startsWith("dependentfield.") == true) {
									dependentFields.add(property.getValue());
								}
							}
						}
					}
				}
				if (isSysField == true) {
					sysFields.put(name, field);
				} else {
					aggrFields.put(name, field);
				}
				dependentFieldMapping.put(name, dependentFields);
			}
		}
		List<String> argNamesList = new LinkedList<String>();
		List<Class<?>> argTypesList = new LinkedList<Class<?>>();
		// add group by fields to the args list
		for (PropertyDefinition groupByField : groupByFields.values()) {
			argNamesList.add(groupByField.getName());
			argTypesList.add(EMFMetricMethodModelFunction.argumentsMap1[groupByField.getType().getValue()]);
		}

		// add getMetricExtId method
		ExpandedName keyMethodName = ExpandedName.makeName(fullPath, EMFMetricMethodModelFunction.GET_METRIC_EXT_ID_FUNCTION_NAME);
		String[] keyArgNames = argNamesList.toArray(new String[argNamesList.size()]);
		Class<?>[] keyArgTypes = argTypesList.toArray(new Class<?>[argTypesList.size()]);
		EMFMetricMethodModelFunction keyMethodFunction = new EMFMetricMethodModelFunction(metric, keyMethodName, EMFMetricMethodModelFunction.GET_METRIC_EXT_ID_FUNCTION_NAME, keyArgNames, keyArgTypes, String.class);
		// add it to the predicate
		addPredicate(keyMethodFunction, fullPath);

		// add load method
		ExpandedName loadMethodName = ExpandedName.makeName(fullPath, EMFMetricMethodModelFunction.LOOKUP_FUNCTION_NAME);
		String[] loadArgNames = argNamesList.toArray(new String[argNamesList.size()]);
		Class<?>[] loadArgTypes = argTypesList.toArray(new Class<?>[argTypesList.size()]);
		EMFMetricMethodModelFunction loadMethodFunction = new EMFMetricMethodModelFunction(metric, loadMethodName, EMFMetricMethodModelFunction.LOOKUP_FUNCTION_NAME, loadArgNames, loadArgTypes, Metric.class);
		// add it to the predicate
		addPredicate(loadMethodFunction, fullPath);

		// add delete method
		ExpandedName deleteMethodName = ExpandedName.makeName(fullPath, EMFMetricMethodModelFunction.DELETE_FUNCTION_NAME);
		EMFMetricMethodModelFunction deleteMethodFunction = new EMFMetricMethodModelFunction(metric, deleteMethodName, EMFMetricMethodModelFunction.DELETE_FUNCTION_NAME, loadArgNames, loadArgTypes, Metric.class);
		// add it to the predicate
		addPredicate(deleteMethodFunction, fullPath);

		// add compute method
		// add all the metric fields filtering system fields and fields which are dependent on other user defined fields
		argNamesList.clear();
		argTypesList.clear();
		for (PropertyDefinition field : metric.getProperties()) {
			String name = field.getName();
			// process only non system fields
			if (sysFields.containsKey(name) == false) {
				// add the field if it is a group by field
				if (groupByFields.containsKey(name) == true) {
					argNamesList.add(field.getName());
					argTypesList.add(EMFMetricMethodModelFunction.argumentsMap1[field.getType().getValue()]);
				} else {
					// add the field if it is a non-system aggregate field with no user defined dependent fields
					List<String> dependentFields = dependentFieldMapping.get(name);
					if (dependentFields.isEmpty() == true || sysFields.keySet().containsAll(dependentFields) == true) {
						argNamesList.add(name);
						argTypesList.add(EMFMetricMethodModelFunction.argumentsMap1[field.getType().getValue()]);
					}
				}
			}
		}
		// for (PropertyDefinition aggregateField : aggrFields.values()) {
		// List<String> dependentFields = dependentFieldMapping.get(aggregateField.getName());
		// //if we have no dependent fields or all dependent fields are system fields
		// if (dependentFields.isEmpty() == true || sysFields.keySet().containsAll(dependentFields) == true) {
		// argNamesList.add(aggregateField.getName());
		// argTypesList.add(EMFMetricMethodModelFunction.argumentsMap1[aggregateField.getType().getValue()]);
		// }
		// }
		// add all the user define fields
		for (PropertyDefinition userDefinedField : metric.getUserDefinedFields()) {
			argNamesList.add(userDefinedField.getName());
			argTypesList.add(EMFMetricMethodModelFunction.argumentsMap1[userDefinedField.getType().getValue()]);
		}
		// create the method signature
		ExpandedName computeMethodName = ExpandedName.makeName(fullPath, EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME);
		// create the function
		String[] argNames = argNamesList.toArray(new String[argNamesList.size()]);
		Class<?>[] argTypes = argTypesList.toArray(new Class<?>[argTypesList.size()]);
		EMFMetricMethodModelFunction computeMethodFunction = new EMFMetricMethodModelFunction(metric, computeMethodName, EMFMetricMethodModelFunction.COMPUTE_FUNCTION_NAME, argNames, argTypes, Metric.class);
		// add it to the predicate
		addPredicate(computeMethodFunction, fullPath);

    }

    //Added by Anand to fix 1-AY6KUS on 06/25/2010
    private void metricRemoved(String metricName, String path){
		try {
			FunctionsCategory category = null;
			if (path == null || path.length() == 0) {
				category = fCategory;
			} else {
				category = getFunctionsCategory(path);
			}
			if (category == null) {
				System.out.println("Category is null for "+path);
				return;
			}
			for (String metricMethod : EMFMetricMethodModelFunction.getAvailableFunctions()) {
				ExpandedName predName = ExpandedName.makeName(metricMethod);
				Predicate predicate = category.getPredicate(predName, true);
				if (predicate != null){
					category.deregisterPredicate(predicate, true);
				}
			}
			int index = path.lastIndexOf('.');
			if (index > 0) {
				String parentPath = path.substring(0, index);
				FunctionsCategory parentCategory = getFunctionsCategory(parentPath);
				parentCategory.deregisterSubCategory(category, true);
				checkForEmptyCategory(parentCategory);
			} else {
				fCategory.deregisterSubCategory(category, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
