package com.tibco.be.model.functions.impl;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.Predicate;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.EntityChangeListener;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.mutable.MutableConcept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.mutable.MutableEvent;
import com.tibco.cep.designtime.model.mutable.MutableEntity;
import com.tibco.cep.designtime.model.mutable.MutableOntology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.mutable.MutableRuleFunction;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;
import com.tibco.xml.data.primitive.ExpandedName;

/*
 * User: apuneet
 * Date: Oct 4, 2004
 * Time: 12:59:08 PM
 */


public class ModelFunctionsFactory implements EntityChangeListener {


    FunctionsCategoryImpl m_category;
    final static String beClassPrefix = ModelNameUtil.GENERATED_PACKAGE_PREFIX + ".";
    FunctionsCatalog m_catalog;


    /**
     *
     */
    public ModelFunctionsFactory() {
        m_category = new FunctionsCategoryImpl(ExpandedName.makeName("Ontology"));
    }


    /**
     * @param catalog
     * @throws Exception
     */
    public void loadModelFunctions(FunctionsCatalog catalog) throws Exception {
        catalog.register("Ontology Functions", m_category);
        m_catalog = catalog;
    }


    public void clear() {
        m_category.clear();
    }


    /**
     * @param entity
     */
    public void entityChanged(MutableEntity entity) {
        try {
            if (m_catalog != null) {
                force((MutableOntology) entity.getOntology());
                m_catalog.catalogChanged(m_category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void entityAdded(MutableEntity entity) {
        entityChanged(entity);
    }


    public void entityRemoved(MutableEntity entity) {
        entityChanged(entity);
    }


    public void entityRenamed(MutableEntity entity, String oldName) {
        entityChanged(entity);
    }


    public void entityMoved(MutableEntity entity, String oldPath) {
        entityChanged(entity);
    }


    /**
     * @param concept
     */
    public static void conceptAdded(Concept concept) throws Exception {


        //remove leading slash and replace slashes with dots
        String fullPath = concept.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());

        String generatedJavaName;

        ExpandedName conceptName = ExpandedName.makeName(fullPath, concept.getName());

        ConceptModelFunctionImpl function;
//        if(concept.isAScorecard()) {
//            generatedJavaName = "get" + concept.getName();
//            function = new ScorecardModelFunction(concept, conceptName, generatedJavaName);
//        }
//        else {
        generatedJavaName = "new" + concept.getName();
        function = new ConceptModelFunctionImpl(concept, conceptName, generatedJavaName);
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
        StateMachine sm = concept.getMainStateMachine();
        if (sm == null) {
            return;
        }

        String smPath = fullPath + ModelNameUtil.RULE_SEPARATOR_CHAR + sm.getName();
        ExpandedName smName = ExpandedName.makeName(fullPath, sm.getName());
        FunctionsCategory smCategory = new StateModelFunctionCategory(smName, sm);
        conceptCategory.registerSubCategory(smCategory);

        /** Add each state name as a predicate */
        registerStatePredicates(smPath, smCategory, sm.getMachineRoot());
    }

//    private static boolean isModuleEntity(Entity entity) {
//        String typeName = (String) entity.getTransientProperty("typeName");
//        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
//        return (module != null) ;
//    }
//
//    private static void declareModuleMethod(Entity entity) throws Exception {
//        String typeName = (String) entity.getTransientProperty("typeName");
//        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
//        if (module != null) {
//            DTFactory dtFactory = module.getDesigntimeFactory();
//            dtFactory.addOntologyMethods(entity);
//        }
//
//    }

    private static void declarePOJOMethods(Concept concept, String fullPath) throws Exception {
        boolean usePojoStyle = Boolean.valueOf(System.getProperty("TIBCO.BE.MODEL.USEPOJOFORCONCEPTS", "false")).booleanValue();
        if (usePojoStyle) {
            Iterator r  = concept.getPropertyDefinitions(true).iterator();
            while (r.hasNext()) {
                PropertyDefinition pd = (PropertyDefinition) r.next();
                char c = pd.getName().charAt(0);
                StringBuffer buf = new StringBuffer().append(Character.toUpperCase(c)).append(pd.getName().substring(1));

                String setPropertyName =  "set" + buf.toString();
                ExpandedName pdName = ExpandedName.makeName(fullPath, setPropertyName);
                addPredicate (new ConceptPropertyModelFunction(pd, pdName, setPropertyName, true), fullPath);

                String getPropertyName = "get" + buf.toString();
                pdName = ExpandedName.makeName(fullPath, getPropertyName);
                addPredicate (new ConceptPropertyModelFunction(pd, pdName, getPropertyName, false), fullPath);
            }

            ExpandedName methodName = ExpandedName.makeName(fullPath, "new"+concept.getName());
            addPredicate(new ConceptMethodModelFunction(concept, methodName, "new"+concept.getName(), new String[0] , new Class[0] , java.lang.Object.class), fullPath);

            methodName = ExpandedName.makeName(fullPath, "Initialize");
            addPredicate(new ConceptMethodModelFunction(concept, methodName, "Initialize", new String[0] , new Class[0] , void.class), fullPath);


        }
    }




    static void registerStatePredicates(String path, FunctionsCategory parentCategory, StateComposite root) throws Exception {
        List entities = root.getEntities();
        if (entities == null) {
            return;
        }

        Iterator it = entities.iterator();
        while (it.hasNext()) {
            State substate = (State) it.next();
            String subpath = path + ModelNameUtil.RULE_SEPARATOR_CHAR + substate.getName();
            ExpandedName subname = ExpandedName.makeName(path, substate.getName());

            if (substate instanceof StateComposite) {
                StateComposite comp = (StateComposite) substate;
                ModelFunctionCategory compCategory = new StateModelFunctionCategory(subname, substate);
                parentCategory.registerSubCategory(compCategory);

                if (comp.isConcurrentState()) {
                    List regions = comp.getRegions();
                    if (regions == null) {
                        continue;
                    }

                    Iterator regionIt = regions.iterator();
                    while (regionIt.hasNext()) {
                        StateComposite region = (StateComposite) regionIt.next();
                        String regionPath = subpath + ModelNameUtil.RULE_SEPARATOR_CHAR + region.getName();
                        ExpandedName regionName = ExpandedName.makeName(subpath, region.getName());

                        ModelFunctionCategory regionCategory = new StateModelFunctionCategory(regionName, region);
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


    static void registerStatePredicate(String parentPath, FunctionsCategory parentCat, State state) throws Exception {
        ExpandedName statename = ExpandedName.makeName(parentPath, state.getName());
        StatePredicate sp = new StatePredicate(state, statename);
        parentCat.registerPredicate(sp);
    }


    /**
     * @param timeEvent
     * @throws Exception
     */
    static void timeEventAdded(Event timeEvent) throws Exception {

        String fullPath = timeEvent.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        TimeEventFunctionImpl function = new TimeEventFunctionImpl(timeEvent, ExpandedName.makeName(fullPath, "schedule" + timeEvent.getName()));
        addPredicate(function, fullPath);
    }


    static void simpleEventAdded(Event simpleEvent) throws Exception {

        String fullPath = simpleEvent.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        EventModelFunction_Create function = new EventModelFunction_Create(simpleEvent, ExpandedName.makeName(fullPath, simpleEvent.getName()), "new" + simpleEvent.getName());
        addPredicate(function, fullPath);
    }


    static void channelAdded(Channel channel) throws Exception {

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
        ChannelModelFunctionCategory channelFnCat = new ChannelModelFunctionCategory(catName, channel);
//        folderCat.registerSubCategory(channelFnCat);

        registerDestinations(channelFnCat, channel, fullPath);
    }


    private static void registerDestinations(FunctionsCategory cat, Channel channel, String fullPath) throws Exception {
        DriverConfig driver = channel.getDriver();
        if (driver == null) {
            return;
        }

        Iterator destIt = driver.getDestinations();
        while (destIt.hasNext()) {
            Destination destination = (Destination) destIt.next();
            ExpandedName destCatName = ExpandedName.makeName(fullPath, destination.getName());

            String destinationPath = fullPath + "." + destination.getName();

            DestinationModelFunctionCategory dmfc = new DestinationModelFunctionCategory(destCatName, false, true, destination);
//            cat.registerSubCategory(dmfc);

            // Adds the properties of the destination
            for (Object nameAsObject: destination.getProperties().keySet()) {
                final ExpandedName propName = ExpandedName.makeName(destinationPath, (String) nameAsObject);

                final DestinationPropertyPredicate dpp = new DestinationPropertyPredicate(destination, propName);
                addPredicate(dpp, destinationPath);                
            }

            /** Add one for the event URI */

        }
    }


    public static FunctionsCategory addPredicate(Predicate predicate, String fullPath) throws Exception {

        String names[] = fullPath.split("\\(|\\)|\\.");
        FunctionsCategory foundOne = getINSTANCE().m_category;
        String ns = null;

        if (names.length >= 0) {
            for (int i = 0; i < names.length; i++) {
                FunctionsCategory fc = foundOne.getSubCategory(ExpandedName.makeName(names[i]));
                if (fc == null) {
                    if (predicate instanceof DestinationPropertyPredicate && (i == names.length - 1)) {
                        DestinationPropertyPredicate dpp = (DestinationPropertyPredicate) predicate;
                        Destination dest = dpp.getDestination();

                        if (ns != null) {
                            fc = new DestinationModelFunctionCategory(ExpandedName.makeName(ns, names[i]), dest);
                        } else {
                            fc = new DestinationModelFunctionCategory(ExpandedName.makeName(names[i]), dest);
                        }
                    } else if (predicate instanceof DestinationPropertyPredicate && (i == names.length - 2)) {
                        DestinationPropertyPredicate dpp = (DestinationPropertyPredicate) predicate;
                        Destination dest = dpp.getDestination();
                        DriverConfig driverConfig = dest.getDriverConfig();
                        Channel channel = driverConfig.getChannel();

                        if (ns != null) {
                            fc = new ChannelModelFunctionCategory(ExpandedName.makeName(ns, names[i]), channel);
                        } else {
                            fc = new ChannelModelFunctionCategory(ExpandedName.makeName(names[i]), channel);
                        }
                    } else if (predicate instanceof ModelFunction && i == names.length - 1) {
                        // last part of path to entity

                        if (predicate instanceof ModelRuleFunction) {
                            fc = foundOne;
                            continue;
                        }

                        if (ns != null) {
                            fc = new ModelFunctionCategory(ExpandedName.makeName(ns, names[i]), (Entity) ((ModelFunction) predicate).getModel());
                        } else {
                            fc = new ModelFunctionCategory(ExpandedName.makeName(names[i]), (Entity) ((ModelFunction) predicate).getModel());
                        }
                    } else {
                        if (ns != null) {
                            fc = new FunctionsCategoryImpl(ExpandedName.makeName(ns, names[i]));
                        } else {
                            fc = new FunctionsCategoryImpl(ExpandedName.makeName(names[i]));
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


    static void force(MutableOntology ontology) throws Exception {
        m_ontology = null;
        loadOntology(ontology);

    }


    public static void loadOntology(MutableOntology ontology) throws Exception {
        if ((m_ontology == null) || (m_ontology != ontology)) {
            INSTANCE.clear();
            m_ontology = ontology;

            Collection entities = m_ontology.getEntities();
            Iterator itr = entities.iterator();
            while (itr.hasNext()) {
                Entity e = (Entity) itr.next();
//                if (isModuleEntity(e)) {
//                    declareModuleMethod(e);
//                } else 
                if (e instanceof Concept) {
                    registerConcept(e);
                }
                else if (e instanceof Event) {
                    registerEvent(e);
                }
                else if (e instanceof RuleFunction) {
                    registerRuleFunction(e);
                }
                else if (e instanceof Channel) {
                    registerChannel(e);
                }
            }


            m_ontology.addEntityChangeListener(INSTANCE);
            INSTANCE.m_category.prepare();
        }
    }

    public static void registerChannel(Entity e) throws Exception {
        final MutableChannel channel = (MutableChannel) e;
        channelAdded(channel);
        channel.addEntityChangeListener(INSTANCE);
    }

    public static void registerRuleFunction(Entity e) throws Exception {
        final MutableRuleFunction rf = (MutableRuleFunction) e;
        ruleFunctionAdded(rf);
        rf.addEntityChangeListener(INSTANCE);
    }

    public static void registerEvent(Entity e) throws Exception {
        final MutableEvent event = (MutableEvent) e;
        if (event.getType() == Event.TIME_EVENT) {
            if (event.getSchedule() == Event.RULE_BASED) {
                timeEventAdded(event);
            }
        } else {
            simpleEventAdded(event);
        }
        event.addEntityChangeListener(INSTANCE);
    }

    public static void registerConcept(Entity e) throws Exception {
        MutableConcept concept = (MutableConcept) e;
        if (concept.isAScorecard()) {
            return;
        }
        conceptAdded(concept);
        concept.addEntityChangeListener(INSTANCE);
    }




    static void ruleFunctionAdded(MutableRuleFunction rf) throws Exception {

//        String fullPath= rf.getFullPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());
        String folderPath = rf.getFolderPath().substring(1).replace(Folder.FOLDER_SEPARATOR_CHAR, ModelNameUtil.RULE_SEPARATOR_CHAR);//clzName.substring(beClassPrefix.length());

        if (folderPath.length() > 0 && folderPath.charAt(folderPath.length() - 1) == ModelNameUtil.RULE_SEPARATOR_CHAR)
        {
            folderPath = folderPath.substring(0, folderPath.length() - 1);
        }

        String generatedJavaName = rf.getName();

        ExpandedName rfName = ExpandedName.makeName(folderPath, rf.getName());
        ModelRuleFunctionImpl fn = new ModelRuleFunctionImpl(rf, rfName, generatedJavaName);

        String fullPath = (folderPath.length() > 0) ? folderPath + ModelNameUtil.RULE_SEPARATOR_CHAR + rf.getName() : rf.getName();

        addPredicate(fn, fullPath);
    }





    /**
     * @return a ModelFunctionsFactory
     */
    public static synchronized ModelFunctionsFactory getINSTANCE() {
        return INSTANCE;
    }


    /**
     *
     */
    final static ModelFunctionsFactory INSTANCE = new ModelFunctionsFactory();
    static MutableOntology m_ontology = null;
}
