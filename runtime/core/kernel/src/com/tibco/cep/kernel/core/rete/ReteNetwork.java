package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.JoinTableCollectionProvider;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.helper.MatchedList;
import com.tibco.cep.kernel.model.entity.*;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.SetupException;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.impl.EquivalentCondition;
import com.tibco.cep.kernel.model.rule.impl.ParentChildCondition;
import com.tibco.cep.kernel.service.ResourceManager;
import org.w3c.dom.Comment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 17, 2006
 * Time: 4:53:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReteNetwork {
    HashMap           classNodes;
    Set               rules;             //all the rule
    HashMap           ruleNodesMap;      //all the Nodes of a rule
    HashMap           ruleLinksMap;      //all the classNodeLink(only, not NodeLink) of a rule
    Map               ruleMatchLinkMap;
    WorkingMemory     workingMemory;
    final int         workingMemoryId;


    public ReteNetwork(WorkingMemory wm) {
        classNodes        = new HashMap();
        rules             = new HashSet();
        ruleNodesMap      = new HashMap();
        ruleLinksMap      = new HashMap();
        ruleMatchLinkMap  = Collections.synchronizedMap(new HashMap());
        workingMemory     = wm;
        workingMemoryId   = wm.getId();
    }

    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public void assertObject(Handle handle) {
        ((ClassNode)(handle.getTypeInfo())).assertObject(handle);
    }

    public void retractObject(Handle handle) {
        ((ClassNode)(handle.getTypeInfo())).removeFromObjectTable(handle);
        ((BaseHandle)handle).removeFromTables();
    }

    public void modifyObject(Handle handle, int[] overrideDirtyBitMap) {
        ((ClassNode)(handle.getTypeInfo())).modifyObject(handle, overrideDirtyBitMap);
    }

    public void stateChangeObject(Handle handle, int index) {
        StateMachineElement obj= (StateMachineElement) handle.getObject();
        Element parent=obj.getOwnerElement();
        Handle elementHandle=((BaseObjectManager) workingMemory.getObjectManager()).getHandle(parent);
        ((StateClassNode)(handle.getTypeInfo())).stateChanged(obj, index, elementHandle);
    }

    public boolean stateChangeHasRules(Handle handle, int index) {
        StateMachineElement obj= (StateMachineElement) handle.getObject();
        return ((StateClassNode)(handle.getTypeInfo())).stateChangedHasRules(obj, index);
    }

    public MatchedList findMatch(Rule rule, Object[] arguments) {
        Object useThis = null;
        int position = -1;
        for(int i=0; i<arguments.length; i++) {
            if(arguments[i] == null) continue;
            if(useThis == null) {
                useThis = arguments[i];
                position = i;
            }
            else
                throw new RuntimeException("argument = " + Format.objsToStr(arguments) + ", can't pass more than one argument");
        }
        if(useThis == null)
            throw new RuntimeException("No argument passed");

        ClassNodeLink[] links = (ClassNodeLink[]) ruleMatchLinkMap.get(rule);
        if(links == null) {
            Set ruleClassNodeLinks = (Set) ruleLinksMap.get(rule);
            if (ruleClassNodeLinks == null)
                throw new RuntimeException("Rule = " + rule.getName() + " is not deployed");
            links = new ClassNodeLink[rule.getIdentifiers().length];
            Iterator ite = ruleClassNodeLinks.iterator();
            while(ite.hasNext()) {
                ClassNodeLink oLink = (ClassNodeLink) ite.next();
                int index = IdentifierUtil.getIndex(rule.getIdentifiers(), oLink.m_identifier);
                links[index] = oLink;
            }
            ruleMatchLinkMap.put(rule, links);
        }
        if(!useThis.getClass().isAssignableFrom(links[position].m_identifier.getType()))
            throw new RuntimeException("argument[" + position + "] = " + useThis + " , is not an instance of " + links[position].m_identifier.getType().getName()
                                       + ".  Rule = " + rule.getName() + ", Identifiers = " + IdentifierUtil.toString(rule.getIdentifiers()));

        return links[position].findMatch(useThis);
    }

    public ClassNode getClassNode(Class cl) {
        ClassNode cn = (ClassNode) classNodes.get(cl);
        if(cn == null) {
            cn = createClassNode(this, cl, classNodes);
            optimizeClass(cl, false);
        }
        return cn;
    }

    //safe - dynamic rule add
    static private ClassNode createClassNode(ReteNetwork rete, Class cl, Map classNodesMap) {
        ClassNode clNode = (ClassNode) classNodesMap.get(cl);
        if(clNode != null) return clNode;
        if (StateMachineElement.class.isAssignableFrom(cl)) {
            clNode = new StateClassNode(rete, cl);
        } else {
            clNode = new ClassNode(rete, cl);
        }
        classNodesMap.put(cl, clNode);
        Class superClass = cl.getSuperclass();
        if(!( superClass == null || superClass.equals(Object.class))) {
            ClassNode superNode = createClassNode(rete, superClass, classNodesMap);
            clNode.setSuper(superNode);
        }
        return clNode;
    }

    public Rule removeRule(Rule rule) throws SetupException {
        rules.remove(rule);
        ruleMatchLinkMap.remove(rule);
        LinkedHashSet ruleNodes = (LinkedHashSet) ruleNodesMap.remove(rule);
        LinkedHashSet ruleLinks = (LinkedHashSet) ruleLinksMap.remove(rule);
        if(ruleNodes == null || ruleLinks == null) {
            //print something?
            System.out.println("Nothing to remove from rete for Rule");
            return null;
        }
        Iterator ite = ruleLinks.iterator();
        while(ite.hasNext()) {
            ClassNodeLink cl = (ClassNodeLink) ite.next();
            cl.m_classNode.removeLink(cl);
            cl.recycleAllTables();
        }
        //optimize Class Hierarchy
        Identifier[] idrs = rule.getIdentifiers();
        for(int i = 0; i < idrs.length; i++) {
            ClassNode clNode = (ClassNode) classNodes.get(idrs[i].getType());
            clNode.removeRule(rule);
            optimizeClass(idrs[i].getType(), true);
        }
        return rule;
    }

    public void fastReset() {

        for (Object classNode : classNodes.values()) {
            ((ClassNode)classNode).fastReset();
        }
        ///classNodes        = new HashMap();
        rules             = new HashSet();
        ruleNodesMap      = new HashMap();
        ruleLinksMap      = new HashMap();
        ruleMatchLinkMap  = Collections.synchronizedMap(new HashMap());


    }

    //this function build temp classNodes for hotdeploy to assert object into the rete graph
    public void buildTempClassMaps(Rule rule, boolean applyAllObjects, Map eventClassMap, Map elementClassMap, Map entityClassMap, Map objectClassMap) {
        //need when this rule contains join or is to evaluate for all objects
        if(applyAllObjects || rule.getIdentifiers().length > 1) {
            Set ruleClassNodeLinks = (Set) ruleLinksMap.get(rule);
            Iterator ite = ruleClassNodeLinks.iterator();
            while(ite.hasNext()) {
                ClassNodeLink oLink = (ClassNodeLink) ite.next();
                ClassNode oCnode = oLink.m_classNode;
                Class cl = oCnode.getType();

                ClassNode cn = null;
                if(Event.class.isAssignableFrom(cl)) {
                    cn = (ClassNode) eventClassMap.get(cl);
                    if(cn == null) {
                        cn =  createClassNode(this, cl, eventClassMap);
                        addSubNode(cl, eventClassMap);
                    }
                }
                else if(Element.class.isAssignableFrom(cl)) {
                    cn = (ClassNode) elementClassMap.get(cl);
                    if(cn == null) {
                        cn =  createClassNode(this, cl, elementClassMap);
                        addSubNode(cl, elementClassMap);
                    }
                }
                else if(Entity.class.isAssignableFrom(cl)) {
                    cn = (ClassNode) entityClassMap.get(cl);
                    if(cn == null) {
                        cn =  createClassNode(this, cl, entityClassMap);
                        addSubNode(cl, entityClassMap);
                    }
                }
                else {
                    cn = (ClassNode) objectClassMap.get(cl);
                    if(cn == null) {
                        cn =  createClassNode(this, cl, objectClassMap);
                        addSubNode(cl, objectClassMap);
                    }
                }
                cn.addLink(oLink);
            }
        }
    }

    private void addSubNode(Class base, Map classMap) {
        ClassNode realReteClassNode = (ClassNode) classNodes.get(base);
        ArrayList subNodes = realReteClassNode.subNodes;
        for(int i = 0; i < subNodes.size(); i++) {
            ClassNode realReteSubClassNode = (ClassNode) subNodes.get(i);
            createClassNode(this, realReteSubClassNode.getType(), classMap);
            addSubNode(realReteSubClassNode.getType(), classMap);
        }
    }


    public void addRule(Rule rule, Set nonEqJoin, Set multiEqJoin) throws SetupException {
        rule.deactivate();
        Set ruleNodes = new LinkedHashSet();
        Set ruleLinks = new LinkedHashSet();

//        rules.add(rule);                          //moved to the end
//        ruleNodesMap.put(rule, ruleNodes);        //moved to the end
//        ruleLinksMap.put(rule, ruleLinks);        //moved to the end
        NodeSet nodeSet = new NodeSet();
        ConditionSet conditionSet            = new ConditionSet();
        LinkedList   conditionNoIdr          = new LinkedList();
        LinkedList   conditionNeedReevaluate = new LinkedList();
        int numReevaluateCondition = 0;
        if(rule.getConditions().length == 0) {
            //rule with no condition
            Identifier idr = pickIdentifier(rule.getIdentifiers());
            createClassFilterNode(rule, ruleNodes, ruleLinks, idr, null, nodeSet);
        }
        else {
            for(int i = 0; i < rule.getConditions().length; i++) {
                if((rule.getConditions()[i].getIdentifiers() == null) ||
                        (rule.getConditions()[i].getIdentifiers().length == 0) ) {
                    //condition with no identifiers
                    conditionNoIdr.add(rule.getConditions()[i]);
                    numReevaluateCondition++;
                }
                else if(rule.getConditions()[i].alwaysEvaluate()) {
                    conditionNeedReevaluate.add(rule.getConditions()[i]);
                    numReevaluateCondition++;
                }
                else if((rule.getConditions()[i].getIdentifiers().length == 1) &&
                        !contains(nodeSet, rule.getConditions()[i].getIdentifiers()[0])) {
                    Condition  cond  = rule.getConditions()[i];
                    createClassFilterNode(rule, ruleNodes, ruleLinks, cond.getIdentifiers()[0], cond, nodeSet);
                }
                else {
                    conditionSet.add(rule.getConditions()[i]);
                }
            }
        }
        if(rule.getConditions().length != 0 && rule.getConditions().length == numReevaluateCondition) {
            Identifier idr = pickIdentifier(rule.getIdentifiers());
            createClassFilterNode(rule, ruleNodes, ruleLinks, idr, null, nodeSet);
        }
        buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, conditionNoIdr, conditionNeedReevaluate);
        optimizeNetwork(ruleNodes);
        optimizeLoadObjectFlag(ruleLinks, ruleNodes);
        setupClassNodeAndClassLink(ruleLinks, ruleNodes);

        //if( started ) {
        //optimize Class Hierarchy
        Identifier[] idrs = rule.getIdentifiers();
        for(int i = 0; i < idrs.length; i++) {
            ClassNode clNode = (ClassNode) classNodes.get(idrs[i].getType());
            clNode.addRule(rule);
            optimizeClass(idrs[i].getType(), true);
        }
        //}

//        if (rule instanceof StateRule) {
//            Iterator allClassNodes=classNodes.values().iterator();
//            while (allClassNodes.hasNext()) {
//                ClassNode c1Node= (ClassNode) allClassNodes.next();
//                if (c1Node instanceof StateClassNode) {
//                    c1Node.addRule(rule);
//                }
//            }
//        }

        checkForRuleProblem(rule, ruleLinks, nonEqJoin, multiEqJoin);
        updateLastRuleIdForClassNodes(rule.getId());
        rules.add(rule);
        ruleNodesMap.put(rule, ruleNodes);
        ruleLinksMap.put(rule, ruleLinks);
    }

    private void updateLastRuleIdForClassNodes(int id) {
        Iterator ite = classNodes.values().iterator();
        while(ite.hasNext()) {
            ClassNode node = (ClassNode) ite.next();
            node.updateLastRuleId(id);
        }
    }

    private boolean nonEquivalentJoinDetected(Node startNode) {
        if(startNode instanceof RuleNode) {
            return false;
        }
        if(startNode instanceof JoinNode) {
            if(startNode instanceof EquivalentJoinNode) {
                return nonEquivalentJoinDetected(startNode.m_nextNodeLink.getLinkToNode());
            }
            else {
                //non equivalent join, check if join from timeevent or ScoreCard (scord card only now)
                JoinNode joinNode = (JoinNode)startNode;
                if(joinNode.getLeftIdentifiers().length == 1 &&
                   NamedInstance.class.isAssignableFrom(joinNode.getLeftIdentifiers()[0].getType())){
                    return nonEquivalentJoinDetected(startNode.m_nextNodeLink.getLinkToNode());
                }
                if(joinNode.getRightIdentifiers().length == 1 &&
                   NamedInstance.class.isAssignableFrom(joinNode.getRightIdentifiers()[0].getType())) {
                    return nonEquivalentJoinDetected(startNode.m_nextNodeLink.getLinkToNode());
                }
                //don't warn when the node has no condition
                if(joinNode.getCondition() == null) {
                    return nonEquivalentJoinDetected(startNode.m_nextNodeLink.getLinkToNode());
                }
                
                return true;
            }
        }
        else {
            return nonEquivalentJoinDetected(startNode.m_nextNodeLink.getLinkToNode());
        }
    }

    private void checkForRuleProblem(Rule rule, Set classLinks, Set nonEqJoin, Set multiEqJoin) {
        if(nonEqJoin == null || multiEqJoin == null) return;
        //check for non equivalent
        Iterator linkIte = classLinks.iterator();
        while(linkIte.hasNext()) {
            ClassNodeLink link = (ClassNodeLink) linkIte.next();
            if(nonEquivalentJoinDetected(link.getLinkToNode())) {
                nonEqJoin.add(rule.getDescription() + " " + IdentifierUtil.toString(rule.getIdentifiers()));
                break;
            }
        }
        //check for multiple equivalent
        Condition[] conditions = rule.getConditions();
        for(int i=0; i < conditions.length -1; i++) {
            for(int j=i+1; j < conditions.length; j++) {
                if( (conditions[i] instanceof EquivalentCondition) &&
                    (conditions[j] instanceof EquivalentCondition) &&
                    IdentifierUtil.same(conditions[i].getIdentifiers(),conditions[j].getIdentifiers())) {
                    multiEqJoin.add(rule.getDescription() + " " + IdentifierUtil.toString(conditions[i].getIdentifiers()));
                }
            }
        }
    }

    //safe - dynamic rule add
    private Identifier pickIdentifier(Identifier[] idrs) {
        Identifier idr = idrs[0];
        for(int i = 1; i < idrs.length; i++) {
            Identifier chkIdr = idrs[i];
//            if(TimeEvent.class.isAssignableFrom(chkIdr.getType())) {      //todo -time event??
//                don't change
//            }
            if(Event.class.isAssignableFrom(chkIdr.getType())) {
                idr = chkIdr;
            }
            else if (!Event.class.isAssignableFrom(idr.getType()) &&
                      Element.class.isAssignableFrom(chkIdr.getType())) {
                idr = chkIdr;
            } else {
                //don't know, not change
            }
        }
        return idr;
    }

    private void optimizeLoadObjectFlag(Set classNodeLinks, Set ruleNodes) {
        Iterator linkIte = classNodeLinks.iterator();
        while(linkIte.hasNext()) {
            ClassNodeLink link = (ClassNodeLink) linkIte.next();
            setLoadObjectFlag(link);
        }
        Iterator nodeIte = ruleNodes.iterator();
        while(nodeIte.hasNext()) {
            Object reteNode = nodeIte.next();
            if(reteNode instanceof Node)
                setLoadObjectFlag_((Node) reteNode);
//            else
//                System.out.println("node type = " + reteNode);
        }
    }

    private boolean setLoadObjectFlag(ClassNodeLink classLink) {
        if(classLink.getLinkToNode() instanceof RuleNode) {
            classLink.loadStopHere = true;
            return true;
        }
        else if(classLink.getLinkToNode() instanceof RuleNode) {
            classLink.loadStopHere = true;
            return true;
        }
        else if(classLink.getLinkToNode() instanceof JoinNode) {
            classLink.loadStopHere = false;
            return false;
        }
        else if(setLoadObjectFlag_(classLink.getLinkToNode())){
            classLink.loadStopHere = true;
            return true;
        }
        else {
            classLink.loadStopHere = false;
            return false;
        }
    }

    //set the flag and return true if this node can reach the RuleNode without going thru a JoinNode
    private boolean setLoadObjectFlag_(Node reteNode) {
        if(reteNode instanceof RuleNode)
            return true;
        else if(reteNode.m_nextNodeLink.m_child instanceof RuleNode) {
            reteNode.loadStopHere = true;
            return true;
        }
        else if(reteNode.m_nextNodeLink.m_child instanceof JoinNode) {
            reteNode.loadStopHere = false;
            return false;
        }
        else if(setLoadObjectFlag_(reteNode.m_nextNodeLink.m_child)) {
            reteNode.loadStopHere = true;
            return true;
        }
        else {
            reteNode.loadStopHere = false;
            return false;
        }
    }


    //safe - dynamic rule add
    private void setupClassNodeAndClassLink(Set linkSet, Set nodeSet) {
        Iterator linkIte = linkSet.iterator();
        while(linkIte.hasNext()) {
            ClassNodeLink link = (ClassNodeLink) linkIte.next();
            Iterator nodeIte = nodeSet.iterator();
            while(nodeIte.hasNext()) {
                Object obj = nodeIte.next();
                if(obj instanceof JoinNode) {
                    JoinTable t = ((JoinNode)obj).leftTable;
                    if (IdentifierUtil.contains(t.getIdentifiers(), new Identifier[] { link.m_identifier })) {
                        //add to ClassNodeLink
                        link.addJoinTable(t);
                        //add to ClassNode
                        ClassNode cNode = getClassNode(link.m_identifier.getType());
                        cNode.addJoinTable(t);
                    }
                    t = ((JoinNode)obj).rightTable;
                    if (IdentifierUtil.contains(t.getIdentifiers(), new Identifier[] { link.m_identifier })) {
                        //add to ClassNodeLink
                        link.addJoinTable(t);
                        //add to ClassNode
                        ClassNode cNode = getClassNode(link.m_identifier.getType());
                        cNode.addJoinTable(t);
                    }
                }
            }
        }
    }

    public void reset() {
        Collection allClassNodes = classNodes.values();
        Iterator ite = allClassNodes.iterator();
        while(ite.hasNext()) {
            ClassNode classNode = (ClassNode) ite.next();
            for(int i =0; i < classNode.m_objectTables.length; i++) {
                classNode.m_objectTables[i].reset();
            }
            for(int i =0; i < classNode.m_tupleTables.length; i++) {
                classNode.m_tupleTables[i].reset();
            }
        }
    }

    private void optimizeClass(ClassNode cn) {
        if(cn.m_super != null)
            optimizeClass(cn.m_super);
        if(cn.isNoAssociatedRule()) {
            cn.hasRule = false;
        }
        else {
            cn.hasRule = true;
        }
    }

    private void optimizeClass(Class cl, boolean optimizeSubClass) {
        ClassNode cnode = (ClassNode) classNodes.get(cl);
        optimizeClass(cnode);
        if(optimizeSubClass) {
            for(int i = 0; i < cnode.subNodes.size(); i++) {
                ClassNode subNode = (ClassNode) cnode.subNodes.get(i);
                optimizeClass(subNode.getType(), true);
            }
        }
    }

/////////////////////////////////////////////////////////////

    //safe - dynamic rule add
    private void optimizeNetwork(Set nodes) {
        //remove unnecessary links
        Iterator ite = classNodes.values().iterator();
        while(ite.hasNext()) {
            ClassNode classNode = ((ClassNode)ite.next());
            for(int i = 0; i < classNode.classLinks.length; i++) {
                NodeLink link = classNode.classLinks[i];
                if(link.m_child instanceof FilterNode) {
                    FilterNode fNode = (FilterNode) link.m_child;
                    if(fNode.m_condition == null) {
                        link.m_child = fNode.m_nextNodeLink.m_child;
                        link.m_childRight = fNode.m_nextNodeLink.m_childRight;
                        nodes.remove(fNode);
                    }
                }
            }
        }
    }

    //safe - dynamic rule add
    private void buildNetwork(Rule rule, Set ruleNodes, Set ruleLinks, ConditionSet conditionSet, NodeSet nodeSet, List noIdentifierSet, List needReevaluateSet) throws SetupException {
        if(conditionSet.size() == 0) {
            if(nodeSet.size() == 1) {  //only 1 node remains
                Node node = nodeSet.get(0);
                if (IdentifierUtil.contains(node.getIdentifiers(), rule.getIdentifiers())) {  //contains all identifiers
                    Node lastNode_ = node;
                    //check condition needReevaulate
                    Iterator ite = needReevaluateSet.iterator();
                    while(ite.hasNext()) {
                        Condition condition = (Condition) ite.next();
                        FilterNode fNode = new FilterNode(rule, this, node.getIdentifiers(), condition);
                        ruleNodes.add(fNode);
                        new NodeLink(lastNode_, fNode);
                        lastNode_ = fNode;
                    }
                    //check conditions with no identifier
                    ite = noIdentifierSet.iterator();
                    while(ite.hasNext()) {
                        Condition condition = (Condition) ite.next();
                        FilterNode fNode = new FilterNode(rule, this, node.getIdentifiers(), condition);
                        ruleNodes.add(fNode);
                        new NodeLink(lastNode_, fNode);
                        lastNode_ = fNode;
                    }
                    RuleNode ruleNode = new RuleNode(this, rule);
                    new NodeLink(lastNode_, ruleNode);
                    ruleNodes.add(ruleNode);
                }
                else {  //missing some identifiers
                    Identifier[] idrs      = IdentifierUtil.notHave(node.getIdentifiers(), rule.getIdentifiers());
                    FilterNode fNode = createClassFilterNode(rule, ruleNodes, ruleLinks, idrs[0], null, nodeSet);
                    createJoinNode(rule, ruleNodes, node, fNode, null, conditionSet, nodeSet);
                    buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, noIdentifierSet, needReevaluateSet);
                }
            }
            else {   //more than 1 node
                Node[] nodes = nodeSet.findSimilarNodes();
                createJoinNode(rule, ruleNodes, nodes[0], nodes[1], null, conditionSet, nodeSet);
                buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, noIdentifierSet, needReevaluateSet);
            }
        }
        else {  // some condition
            //filter node
            if (createFilterNode(rule, ruleNodes, conditionSet, nodeSet)) {
                buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, noIdentifierSet, needReevaluateSet);
            }
            //extension node using existing nodes in nodeSet
            else if(createJoinNodeFromExisting(rule, ruleNodes, conditionSet, nodeSet)) {
                buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, noIdentifierSet, needReevaluateSet);
            }
            //extension node but partly uses existing nodes in nodeSet
            else if (createJoinNodeFromSome(rule, ruleNodes, ruleLinks, conditionSet, nodeSet)) {
                //matched most and least needed
                buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, noIdentifierSet, needReevaluateSet);
            }
            //none of the node matches
            else {
                Condition condition   = conditionSet.findLeastIdentifiers()[0];  //XXX: should find the best condition
                createClassFilterNode(rule, ruleNodes, ruleLinks, condition.getIdentifiers()[0], null, nodeSet);
                buildNetwork(rule, ruleNodes, ruleLinks, conditionSet, nodeSet, noIdentifierSet, needReevaluateSet);
            }
        }
    }

    //safe - dynamic rule add
    private FilterNode createClassFilterNode(Rule rule, Set ruleNodes, Set ruleLinks, Identifier identifier, Condition condition, NodeSet nodeSet) throws SetupException {
        Identifier[] identifiers = new Identifier[] { identifier };
        ClassNode     cNode = getClassNode(identifier.getType());
        FilterNode    fNode = new FilterNode(rule, this, identifiers, condition);
        ClassNodeLink nLink = new ClassNodeLink(cNode, fNode, rule, identifier);
        cNode.addLink(nLink);
        nodeSet.add(fNode);
        ruleNodes.add(cNode);
        ruleNodes.add(fNode);
        ruleLinks.add(nLink);
        return fNode;
    }

    private JoinNode createParentChildJoinNode(Rule rule, Set ruleNodes,
                                               Node nodeContainsParent, Node nodeContainsChild,
                                               Condition joinCondition, ConditionSet conditionSet,
                                               NodeSet nodeSet) throws SetupException {
        JoinNode jNode = new ParentChildJoinNode(rule, this, nodeContainsParent.getIdentifiers(),
                nodeContainsChild.getIdentifiers(),
                joinCondition, workingMemory.isConcurrent());
        conditionSet.remove(joinCondition);

        new NodeLink(nodeContainsParent, jNode, false);  //left link
        new NodeLink(nodeContainsChild, jNode, true);    //right link
        nodeSet.remove(nodeContainsParent);
        nodeSet.remove(nodeContainsChild);
        nodeSet.add(jNode);
        ruleNodes.add(jNode);
        return jNode;
    }

    //safe - dynamic rule add
    private JoinNode createEquivalentJoinNode(Rule rule, Set ruleNodes,
                                              Node leftNode, Node rightNode,
                                              Condition joinCondition, ConditionSet conditionSet,
                                              NodeSet nodeSet) throws SetupException {
        JoinNode jNode = new EquivalentJoinNode(rule, this, leftNode.getIdentifiers(),
                rightNode.getIdentifiers(),
                joinCondition,workingMemory.isConcurrent());
        conditionSet.remove(joinCondition);

        new NodeLink(leftNode, jNode, false);   //left link
        new NodeLink(rightNode, jNode, true);   //right link
        nodeSet.remove(leftNode);
        nodeSet.remove(rightNode);
        nodeSet.add(jNode);
        ruleNodes.add(jNode);
        return jNode;
    }

    //safe - dynamic rule add
    private JoinNode createJoinNode(Rule rule, Set ruleNodes,
                                    Node nodeA, Node nodeB, Condition joinCondition,
                                    ConditionSet conditionSet, NodeSet nodeSet) throws SetupException {
        Node leftNode  = nodeA;
        Node rightNode = nodeB;
        JoinNode jNode = null;
        if (joinCondition != null) {
            if(joinCondition instanceof EquivalentCondition) {
                EquivalentCondition cond = (EquivalentCondition)joinCondition;
                if(IdentifierUtil.contains(nodeA.getIdentifiers(), cond.getLeftIdentifiers()) &&
                        IdentifierUtil.contains(nodeB.getIdentifiers(), cond.getRightIdentifiers())) {
                    return createEquivalentJoinNode(rule, ruleNodes, nodeA, nodeB, joinCondition, conditionSet, nodeSet);
                }
                else if(IdentifierUtil.contains(nodeB.getIdentifiers(), cond.getLeftIdentifiers()) &&
                        IdentifierUtil.contains(nodeA.getIdentifiers(), cond.getRightIdentifiers())) {
                    return createEquivalentJoinNode(rule, ruleNodes, nodeB, nodeA, joinCondition, conditionSet, nodeSet);
                }
            }
            Identifier[] conditionIdrs = joinCondition.getIdentifiers();
            Identifier[] joinBA = IdentifierUtil.append(nodeB.getIdentifiers(), nodeA.getIdentifiers());
            if(IdentifierUtil.matchOrder(conditionIdrs, joinBA)) {
                leftNode  = nodeB;
                rightNode = nodeA;
                jNode = new JoinNode(rule, this, leftNode.getIdentifiers(), rightNode.getIdentifiers(), joinCondition,workingMemory.isConcurrent());
            }
            else {
                Identifier[] joinAB = IdentifierUtil.append(nodeA.getIdentifiers(), nodeB.getIdentifiers());
                if (IdentifierUtil.matchOrder(conditionIdrs, joinAB)) {
                    leftNode  = nodeA;
                    rightNode = nodeB;
                    jNode = new JoinNode(rule, this, leftNode.getIdentifiers(), rightNode.getIdentifiers(), joinCondition,workingMemory.isConcurrent());
                }
                else {  //not match
                    jNode = new JoinNode(rule, this, leftNode.getIdentifiers(), rightNode.getIdentifiers(), joinCondition,workingMemory.isConcurrent());
                }
            }
            conditionSet.remove(joinCondition);
        }
        else {
            jNode = new JoinNode(rule, this, leftNode.getIdentifiers(), rightNode.getIdentifiers(), joinCondition,workingMemory.isConcurrent());
        }
        new NodeLink(leftNode, jNode, false);   //left link
        new NodeLink(rightNode, jNode, true);   //right link
        nodeSet.remove(nodeA);
        nodeSet.remove(nodeB);
        nodeSet.add(jNode);
        ruleNodes.add(jNode);
        return jNode;
    }

    //safe - dynamic rule add
    private boolean createJoinNodeFromSome(Rule rule, Set ruleNodes, Set ruleLinks, ConditionSet conditionSet, NodeSet nodeSet) throws SetupException {
        int leastNeeded = Integer.MAX_VALUE;
        int maxIdentifier = -1;
        Iterator condIte = conditionSet.iterator();
        Node targetNode = null;
        Condition targetCondition = null;
        while(condIte.hasNext()) {
            Condition condition = (Condition) condIte.next();
            for(int i = 0; i < nodeSet.size(); i++) {
                Identifier[] nodeIdentifier = nodeSet.get(i).getIdentifiers();
                int need = IdentifierUtil.notHave(nodeIdentifier, condition.getIdentifiers()).length;
                if(need < leastNeeded) {
                    leastNeeded = need;
                    maxIdentifier = nodeIdentifier.length;
                    targetNode = nodeSet.get(i);
                    targetCondition = condition;
                }
                else if (need == leastNeeded) {
                    //todo: optimize this later
                    if(targetCondition instanceof EquivalentCondition) {
                        continue;
                    }
                    else if(targetCondition instanceof ParentChildCondition) {
                        continue;
                    }
                    else if (nodeIdentifier.length == maxIdentifier) {
                        if(condition instanceof EquivalentCondition) {
                            maxIdentifier = nodeIdentifier.length;
                            targetNode = nodeSet.get(i);
                            targetCondition = condition;
                        }
                        else if(condition instanceof ParentChildCondition) {
                            maxIdentifier = nodeIdentifier.length;
                            targetNode = nodeSet.get(i);
                            targetCondition = condition;
                        }
                    }
                    else if (nodeIdentifier.length > maxIdentifier) {
                        maxIdentifier = nodeIdentifier.length;
                        targetNode = nodeSet.get(i);
                        targetCondition = condition;
                    }
                }
            }
        }
        if (maxIdentifier == -1) {
            return false;
        }
        Identifier[] nodeIdentifiers = IdentifierUtil.notHave(targetNode.getIdentifiers(), targetCondition.getIdentifiers());
        if (leastNeeded == 1) {  //create the extension here
            FilterNode fNode = createClassFilterNode(rule, ruleNodes, ruleLinks, nodeIdentifiers[0], null, nodeSet);
            createJoinNode(rule, ruleNodes, targetNode, fNode, targetCondition, conditionSet, nodeSet);
        }
        // just create a filterNode {
        else {
            //find what is missing
            Node useThis = findBestNode(nodeSet, nodeIdentifiers, targetNode);
            if(useThis == null) {
                createClassFilterNode(rule, ruleNodes, ruleLinks, nodeIdentifiers[0], null, nodeSet);
            }
            else {
                createJoinNode(rule, ruleNodes, targetNode, useThis, null, conditionSet, nodeSet);
            }
        }
        return true;
    }

    //safe - dynamic rule add
    private Node findBestNode(NodeSet nodeSet, Identifier[] matchIdentifiers, Node notThis) {
        Iterator ite = nodeSet.iterator();
        Node foundNode = null;
        int  foundIdr = 0;
        while(ite.hasNext()) {
            Node node = (Node) ite.next();
            if(node.equals(notThis)) {
                continue;
            }
//            int foundMatch = IdentifierImpl.notHave(node.getIdentifiers(), matchIdentifiers).length;
            int foundMatch = IdentifierUtil.numCommonIdentifier(node.getIdentifiers(), matchIdentifiers);
            if(foundMatch > foundIdr) {
                foundIdr = foundMatch;
                foundNode = node;
            }
        }
        if(foundNode != null) {
            return foundNode;
        }
        else {
            return null;
        }
    }

    //safe - dynamic rule add
    private boolean createJoinNodeFromExisting(Rule rule, Set ruleNodes,
                                               ConditionSet conditionSet, NodeSet nodeSet) throws SetupException {
        int maxCommonIdr = -1;
        int numOfIdentifiers = 0;
        Iterator condIte = conditionSet.iterator();
        Node[] joinThese = new Node[2];
        Condition targetCondition = null;
        while(condIte.hasNext()) {
            Condition condition = (Condition) condIte.next();
            for(int i = 0; i < nodeSet.size()-1; i++) {
                Node leftNode = nodeSet.get(i);
                for(int j = i+1; j < nodeSet.size(); j++) {
                    Node rightNode = nodeSet.get(j);
                    if(IdentifierUtil.containsAll(condition.getIdentifiers(),
                            leftNode.getIdentifiers(),
                            rightNode.getIdentifiers())) {
                        int commonIdr =  IdentifierUtil.numCommonIdentifier(leftNode.getIdentifiers(), rightNode.getIdentifiers());
                        if(maxCommonIdr < commonIdr) {
                            maxCommonIdr = commonIdr;
                            joinThese[0] = leftNode;
                            joinThese[1] = rightNode;
                            numOfIdentifiers = IdentifierUtil.joinIdentifiers(leftNode.getIdentifiers(), rightNode.getIdentifiers()).length;
                            targetCondition = condition;
                        }
                        else if (maxCommonIdr == commonIdr) {
                            int numIdrs = IdentifierUtil.joinIdentifiers(leftNode.getIdentifiers(), rightNode.getIdentifiers()).length;
                            if(numIdrs == numOfIdentifiers) {
                                if(targetCondition instanceof EquivalentCondition) {
                                    continue;
                                }
                                if(targetCondition instanceof ParentChildCondition) {
                                    continue;
                                }
                                if(condition instanceof EquivalentCondition) {
                                    joinThese[0] = leftNode;
                                    joinThese[1] = rightNode;
                                    targetCondition = condition;
                                }
                            }
                            else if(numIdrs < numOfIdentifiers) {
                                joinThese[0] = leftNode;
                                joinThese[1] = rightNode;
                                numOfIdentifiers = numIdrs;
                                targetCondition = condition;
                            }
                        }
                    }
                }
            }
        }
        if (maxCommonIdr != -1) {
            createJoinNode(rule, ruleNodes, joinThese[0], joinThese[1], targetCondition, conditionSet, nodeSet);
            return true;
        }
        else {
            return false;
        }
    }

    //safe - dynamic rule add
    private boolean createFilterNode(Rule rule, Set ruleNodes, ConditionSet conditionSet, NodeSet nodeSet) throws SetupException {
        Iterator condIte = conditionSet.iterator();
        while(condIte.hasNext()) {
            Condition condition = (Condition) condIte.next();
            for(int i = 0; i < nodeSet.size(); i++) {
                Node node = nodeSet.get(i);
                if(IdentifierUtil.contains(node.getIdentifiers(), condition.getIdentifiers())) {
                    //create filter
                    FilterNode fNode = new FilterNode(rule, this, node.getIdentifiers(), condition);
                    new NodeLink(node, fNode);
                    conditionSet.remove(condition);
                    nodeSet.remove(node);
                    nodeSet.add(fNode);
                    ruleNodes.add(fNode);
                    return true;
                }
            }
        }
        return false;
    }

    //safe - dynamic rule add
    private String printClassNode(ClassNode node, Set set) {
        String links = new String();
        Iterator ite = set.iterator();
        while(ite.hasNext()) {
            ClassNodeLink link = (ClassNodeLink) ite.next();
            if(link.m_identifier.getType().equals(node.getType()))
                links += Format.BRK +"\t\t" + link.toString();
        }
        return "\t[ClassNode Class(" + node.getType().getName() + ")" + links + "]" + Format.BRK;
    }

    //safe - dynamic rule add
    private String printRule(Rule rule) {
        String ret = new String();
        ret += "[Rule (" + rule.getClass().getName() + ") Id(" + rule.getId() + ") ]" + Format.BRK;
        Set nodes = (Set)ruleNodesMap.get(rule);
        Iterator nodesIte = nodes.iterator();
        while(nodesIte.hasNext()) {
            Object obj =  nodesIte.next();
            if(obj instanceof ClassNode) {
                ret += printClassNode((ClassNode)obj, (Set)ruleLinksMap.get(rule));
            }
            else {
                ret +=  obj + Format.BRK;
            }
        }
        return ret;
    }

    //safe - dynamic rule add
    public String printNetwork() {
        String ret = Format.BRK + ">>>> Class View <<<<" + Format.BRK;

        Iterator ite = classNodes.values().iterator();
        while(ite.hasNext()) {
            ClassNode cNode = (ClassNode) ite.next();
            if(cNode.hasRule)
                ret += cNode + Format.BRK;
        }
        ret += Format.BRK + ">>>> Rule View <<<<" + Format.BRK;
        ite = this.ruleNodesMap.keySet().iterator();
        while(ite.hasNext()) {
            Rule   rule  = (Rule) ite.next();
            ret += printRule(rule);
        }
        return ret;
    }
    
    //////////////////////// XML RETE NETWORK BEGIN ///////////////////////////////
    
    private int id=0;
    private HashMap xmlnodeMap = new HashMap();
    final private int ROOT_NODE = 0;
    final private int RULE_NODE = 1;
    final private int CLASS_NODE = 2;
    final private int FILTER_NODE = 3;
    final private int JOIN_NODE = 4;
    final private int OTHER_NODE = 5;    
    
    public String printNetworkXML() {
    	this.id = 0;
        StringWriter outString = new StringWriter();
    	
    	try {
	    	org.w3c.dom.Element root;
	    	org.w3c.dom.Document doc;
	    	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	
	    	// Create blank DOM Document
	    	doc = docBuilder.newDocument();
	    	// create the root element
	    	root = doc.createElement("TSX");
	    	root.setAttribute("product", "TIBCO BusinessEvents");
	    	doc.appendChild(root);
	    	Comment comment = doc.createComment("Rete Network Graph");
	    	// insert top level comment at the top
	    	doc.insertBefore(comment, root);  
	
	    	org.w3c.dom.Element graphMgrRoot = doc.createElement("graphManager");
	    	graphMgrRoot.setAttribute("id", String.valueOf(id++));
	    	root.appendChild(graphMgrRoot);
	    	org.w3c.dom.Element instancesRoot = doc.createElement("instances");
	    	org.w3c.dom.Element graphsRoot = doc.createElement("graphs");
	    	org.w3c.dom.Element mainGraphRoot = doc.createElement("graph");
	    	mainGraphRoot.setAttribute("id", String.valueOf(id++));
	
	    	graphMgrRoot.appendChild(instancesRoot);
	    	instancesRoot.appendChild(graphsRoot);
	    	graphsRoot.appendChild(mainGraphRoot);
	
	    	org.w3c.dom.Element nodesRoot = doc.createElement("nodes");
	    	mainGraphRoot.appendChild(nodesRoot);
	    	int rootNodeID = this.writeNodes(doc, nodesRoot);
	
	    	org.w3c.dom.Element edgesRoot = doc.createElement("edges");
	    	mainGraphRoot.appendChild(edgesRoot);
	    	this.writeEdges(doc, edgesRoot, rootNodeID);
	
	    	// Prepare the DOM document for writing
	    	Source source = new DOMSource(doc);
	    	Result result;
	    	// Prepare the output file
	    	// BufferedWriter out = new BufferedWriter(new FileWriter("sample.xml"));
	    	result = new StreamResult(outString);
	
	    	// Write the DOM document to the file
	    	Transformer xformer = TransformerFactory.newInstance().newTransformer();
	    	xformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    	xformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    	xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
	    	xformer.transform(source, result);	
	
	    	outString.flush();
	    	outString.close();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return outString.toString();
    }

    private int writeNodes(org.w3c.dom.Document doc, org.w3c.dom.Element root) {
    	
    	// write the root node first
    	int rootNodeID = this.writeNode(doc, root, null, ROOT_NODE);
    	
    	// iterate through all the nodes
    	Rule rule;
    	Set nodes;
    	Iterator nodesIter;
    	Object nodeObj;
        Iterator ite = this.ruleNodesMap.keySet().iterator();
        while(ite.hasNext()) {
            rule = (Rule) ite.next();
            nodes = (Set)ruleNodesMap.get(rule);
            nodesIter = nodes.iterator();
            while(nodesIter.hasNext()) {
                nodeObj =  nodesIter.next();
                if(nodeObj instanceof ClassNode) {
                	if (this.xmlnodeMap.get(nodeObj) == null) {
                		int classNodeID = this.writeNode(doc, root, ((ClassNode) nodeObj).getType().getName(), CLASS_NODE);
                		this.xmlnodeMap.put(nodeObj, classNodeID);
                		this.writeEdge(doc, root, rootNodeID, classNodeID);
                	}
                }
                else if(nodeObj instanceof FilterNode) {
                	if (this.xmlnodeMap.get(nodeObj) == null) {
                		this.xmlnodeMap.put(nodeObj,
                			this.writeNode(doc, root, ((FilterNode) nodeObj).getClass().getName(), FILTER_NODE));
                	}
                } 
                else if(nodeObj instanceof JoinNode) {
                	if (this.xmlnodeMap.get(nodeObj) == null) {
                		this.xmlnodeMap.put(nodeObj,
                			this.writeNode(doc, root, ((JoinNode) nodeObj).getClass().getName(), JOIN_NODE));
                	}
                }
                else if(nodeObj instanceof RuleNode) {
                	if (this.xmlnodeMap.get(nodeObj) == null) {
                		this.xmlnodeMap.put(nodeObj,
                			this.writeNode(doc, root, rule.getClass().getName(), RULE_NODE));
                		// System.err.println("Storing rule node with id: " + ((RuleNode)obj).m_nodeId);
                	}
                }
                else {
                	// some other type of a node, it should not happen
                	if (this.xmlnodeMap.get(nodeObj) == null) {
                		this.xmlnodeMap.put(nodeObj,
                			this.writeNode(doc, root, ((Node) nodeObj).getClass().getName(), OTHER_NODE));
                	}
                }
            }
        }
        
        return rootNodeID;
    }

    private int writeNode(org.w3c.dom.Document doc, org.w3c.dom.Element root, String label, int type) {
    	int nodeID = id++;
    	org.w3c.dom.Element childElement = doc.createElement("node");
		childElement.setAttribute("id", String.valueOf(nodeID));
		root.appendChild(childElement);
		org.w3c.dom.Element grandChildElement = doc.createElement("name");
		String typeName;
		switch (type) {
		case ROOT_NODE:
			typeName = "root";
			break;
		case RULE_NODE:
			typeName = "rule";
			break;
		case CLASS_NODE:
			typeName = "class";
			break;
		case FILTER_NODE:
			typeName = "filter";
			break;
		case JOIN_NODE:
			typeName = "join";
			break;				
		default:
			typeName = "other";
		}
		grandChildElement.setAttribute("value", typeName);
		childElement.appendChild(grandChildElement);        

		// we use labels to annotate nodes with the rete node names
		if (label != null) {
			org.w3c.dom.Element labels = doc.createElement("nodeLabels");
			childElement.appendChild(labels);
	
			org.w3c.dom.Element labelElement = doc.createElement("nodeLabel");
			labelElement.setAttribute("id", String.valueOf(id++));
	
			org.w3c.dom.Element labelText = doc.createElement("name");
			// strip out "be.gen."
			if (label.startsWith("be.gen.")) {
				label = label.substring(7);
			}
			labelText.setAttribute("value", label);
			labels.appendChild(labelElement);
			labelElement.appendChild(labelText);
		}
		
		return nodeID;
    }  
    
    private void writeEdges(org.w3c.dom.Document doc, org.w3c.dom.Element root, int rootNodeID) {
    	// first add implicit edges between root node and class nodes
    	Iterator classIter = this.classNodes.values().iterator();
    	Object cNodeObj;
    	while (classIter.hasNext()) {
    		cNodeObj = this.xmlnodeMap.get(classIter.next());
    		if (cNodeObj != null) {
    			this.writeEdge(doc, root, rootNodeID, ((Integer) cNodeObj).intValue());
    		}
    	}
    	
    	// iterate through all the rete links
        Iterator ite = this.ruleNodesMap.keySet().iterator();
        while(ite.hasNext()) {
            Rule rule = (Rule) ite.next();       
	        Set ruleClassNodeLinks = (Set) ruleLinksMap.get(rule);
	        if (ruleClassNodeLinks != null) {
	            Iterator iter = ruleClassNodeLinks.iterator();
	            while(iter.hasNext()) {
	                ClassNodeLink cLink = (ClassNodeLink) iter.next();
	                ClassNode src = cLink.m_classNode;
	                Node tgt = cLink.m_child;
	                
	                int srcID;
	                int tgtID;
	                Object srcObj = this.xmlnodeMap.get(src);
	                Object tgtObj = this.xmlnodeMap.get(tgt);
	                if (srcObj != null && tgtObj != null) {
	                	srcID = ((Integer) srcObj).intValue();
	                	tgtID = ((Integer) tgtObj).intValue();
	                	this.writeEdge(doc, root, srcID, tgtID);	 
	                }
	                
	                Object srcobj = this.xmlnodeMap.get(cLink.m_parent);
	                if (srcobj != null) {
	                	srcID = ((Integer) srcobj).intValue();
	                }
	                else {
	                	continue;
	                }
	                Object tgtobj = this.xmlnodeMap.get(cLink.m_child);
	                if (tgtobj != null) {
	                	tgtID = ((Integer) tgtobj).intValue();
	                }
	                else {
	                	continue;
	                }
	            }
	        }  
        }    	
    }  
    
    private void writeEdge(org.w3c.dom.Document doc, org.w3c.dom.Element root, int srcID, int tgtID) {
    	org.w3c.dom.Element childElement = doc.createElement("edge");
    	childElement.setAttribute("id", String.valueOf(id++));
    	childElement.setAttribute("source", String.valueOf(srcID));
    	childElement.setAttribute("target", String.valueOf(tgtID));
    	root.appendChild(childElement);
    }  
    

    //////////////////////// XML RETE NETWORK END ///////////////////////////////
    
    
    //safe - dynamic rule add
    public String allJoinTableInfo(boolean hashForm) {
        String ret = "";
        JoinTable[] allTables = JoinTableCollectionProvider.getInstance().getJoinTableCollection().toArray();
        //TODO: classify by rule
        for(int i=0; i<allTables.length; i++) {
            JoinTable t = allTables[i];
            if(t == null) continue;
            if (hashForm) {
                ret += t.contentHashForm();
            }
            else {
                ret += t.contentListForm();
            }
        }
        return ret;
    }

    //return true if any node in the nodeSet contains identifier
    private boolean contains(NodeSet nodeSet, Identifier idr) {
        for(int k = 0; k < nodeSet.size(); k++) {
            Node node = nodeSet.get(k);
            Identifier[] subSet = { idr };
            if(IdentifierUtil.contains(node.getIdentifiers(), subSet)) {
                return true;
            }
        }
        return false;
    }

    class ConditionSet {
        LinkedList m_set;

        ConditionSet() {
            m_set = new LinkedList();
        }

        public int size() {
            return m_set.size();
        }

        public Condition get(int index) {
            return (Condition) m_set.get(index);
        }

        public void add(Condition condition) {
            if(!m_set.contains(condition)) {
                m_set.add(condition);
            }
        }

        public boolean remove(Condition condition) {
            return m_set.remove(condition);
        }

        public Iterator iterator() {
            return m_set.iterator();
        }

        public Condition[] findLeastIdentifiers() {
            int least = Integer.MAX_VALUE;
            Iterator ite = m_set.iterator();
            while(ite.hasNext()) {
                Condition cond = (Condition) ite.next();
                if(cond.getIdentifiers().length < least) {
                    least = cond.getIdentifiers().length;
                }
            }
            if (least == Integer.MAX_VALUE) {
                return new Condition[0];
            }
            Vector v = new Vector();
            ite = m_set.iterator();
            while(ite.hasNext()) {
                Condition cond = (Condition) ite.next();
                if(cond.getIdentifiers().length == least) {
                    v.add(cond);
                }
            }
            Condition[] ret = new Condition[v.size()];
            for(int i = 0; i < ret.length; i++) {
                ret[i] = (Condition) v.get(i);
            }
            return ret;
        }
    }

    class NodeSet {
        LinkedList m_set;

        NodeSet() {
            m_set = new LinkedList();
        }

        public int size() {
            return m_set.size();
        }

        public Node get(int index) {
            return (Node) m_set.get(index);
        }

        public boolean contains(Node node) {
            return m_set.contains(node);
        }

        public void add(Node node) {
            if (!m_set.contains(node)) {
                m_set.add(node);
            }
        }

        public boolean remove(Node node) {
            return m_set.remove(node);
        }

        public Iterator iterator() {
            return m_set.iterator();
        }

        public Node[] findSimilarNodes() throws SetupException {
            if (m_set.size() < 2) {
                throw new SetupException(ResourceManager.getString("rete.nodeSet.lessThan2Node"));
            }
            Node[] ret = new Node[2];
            int maxCommon = 0;
            for(int i = 0; i < m_set.size()-1; i++) {
                Node node1 = (Node) m_set.get(i);
                for(int j = i+1; j < m_set.size(); j++) {
                    Node node2 = (Node) m_set.get(j);
                    int common = IdentifierUtil.numCommonIdentifier(node1.getIdentifiers(), node2.getIdentifiers());
                    if ( common > maxCommon) {
                        maxCommon = common;
                        ret[0] = node1;
                        ret[1] = node2;
                    }
                }
            }
            if (maxCommon == 0) {
                ret[0] = (Node) m_set.get(0);
                ret[1] = (Node) m_set.get(1);
            }
            return ret;
        }

        public Node findNode(Identifier[] identifiers) {
            Vector v = new Vector();
            int max = -1;
            Iterator ite = m_set.iterator();
            while(ite.hasNext()) {
                Node node = (Node) ite.next();
                if(IdentifierUtil.contains(node.getIdentifiers(), identifiers)) {
                    v.add(node);
                    max = Math.max(max, node.getIdentifiers().length);
                }
            }
            ite = v.iterator();
            while(ite.hasNext()) {
                Node node = (Node) ite.next();
                if(node.getIdentifiers().length == max) {
                    return node;
                }
            }
            return null;
        }
    }

    class NodeIdentifier {
        ArrayList m_identifiers;
        Node      m_node;

        NodeIdentifier(Identifier[] identifiers, Node node) {
            m_identifiers = new ArrayList();
            for(int i = 0; identifiers != null && i < identifiers.length; i++) {
                m_identifiers.add(identifiers[i]);
            }
            m_node = node;
        }

        public Node getNode() {
            return m_node;
        }

        public void addIdentifiers(Identifier[] identifiers){
            for(int i = 0; identifiers != null && i < identifiers.length; i++) {
                m_identifiers.add(identifiers[i]);
            }
        }

        public void addIdentifiers(Identifier identifier){
            if(identifier != null) {
                m_identifiers.add(identifier);
            }
        }

        public void addIdentifiers(NodeIdentifier nodeIdentifiers){
            if(nodeIdentifiers != null && nodeIdentifiers.m_identifiers !=null) {
                for(int i=0; i<nodeIdentifiers.m_identifiers.size(); i++) {
                    m_identifiers.add(nodeIdentifiers.m_identifiers.get(i));
                }
            }
        }

        public boolean contains(Identifier[] identifiers) {
            for(int i = 0; identifiers != null && i < identifiers.length; i++) {
                if(!m_identifiers.contains(identifiers[i])) {
                    return false;
                }
            }
            return true;
        }

        public boolean contains(Identifier identifier) {
            if(identifier == null) {
                return true;
            }
            else if(m_identifiers.contains(identifier)) {
                return true;
            }
            return false;
        }

        public boolean contains(NodeIdentifier nodeIdentifiers) {
            if (nodeIdentifiers !=null && nodeIdentifiers.m_identifiers !=null) {
                for(int i = 0; i < nodeIdentifiers.m_identifiers.size(); i++) {
                    if(!m_identifiers.contains(nodeIdentifiers.m_identifiers.get(i))) {
                        return false;
                    }
                }
            }
            return true;
        }

        public Identifier[] notHave(Identifier[] identifiers) {
            Vector v = new Vector();
            for(int i = 0; i < identifiers.length; i++) {
                if(!(m_identifiers.contains(identifiers[i]))) {
                    v.add(identifiers[i]);
                }
            }
            Identifier[] arr = new Identifier[v.size()];
            for(int i = 0; i < v.size(); i++) {
                arr[i] = (Identifier) v.get(i);
            }
            return arr;
        }

        public Identifier[] notHave(NodeIdentifier nodeIdentifiers) {
            return notHave(nodeIdentifiers.toArray());
        }

        public Identifier[] notIn(Identifier[] identifiers) {
            return notIn(new NodeIdentifier(identifiers, null));
        }

        public Identifier[] notIn(NodeIdentifier nodeIdentifiers) {
            Vector v = new Vector();
            for(int i = 0; i < m_identifiers.size(); i++) {
                if(!(nodeIdentifiers.m_identifiers.contains(m_identifiers.get(i)))) {
                    v.add(m_identifiers.get(i));
                }
            }
            Identifier[] arr = new Identifier[v.size()];
            for(int i = 0; i < v.size(); i++) {
                arr[i] = (Identifier) v.get(i);
            }
            return arr;
        }

        public Identifier[] toArray() {
            Identifier[] arr = new Identifier[m_identifiers.size()];
            for(int i = 0; i < m_identifiers.size(); i++) {
                arr[i] = (Identifier) m_identifiers.get(i);
            }
            return arr;
        }

        public boolean equals(Object obj) {
            if(obj instanceof NodeIdentifier) {
                if(notHave((NodeIdentifier)obj).length == 0 &&
                        notIn((NodeIdentifier)obj).length == 0) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            int ret = 0;
            for(int i = 0; i < m_identifiers.size(); i++) {
                ret += m_identifiers.get(i).hashCode();
            }
            return ret;
        }

    }
}
