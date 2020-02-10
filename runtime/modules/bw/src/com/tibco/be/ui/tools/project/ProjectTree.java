/* Copyright (c) Tibco Software Inc. 2004
* All rights reserved.
* author: Roger Trigg & Ishaan Shastri.
*/

package com.tibco.be.ui.tools.project;

import java.awt.Component;
import java.awt.dnd.DragGestureEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.tibco.be.util.Filter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleSet;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.repo.Project;


/**
 * This class creates a JTree populated from the ontology passed in such that it can be used
 * to display Concept or Instance (etc.) pickers.  Usually used together with JSelectEntityDialog,
 * which displays the JTree and allow selection.
 */
public class ProjectTree extends JTree {

    public static final int CONCEPT_FLAG = 0x1;
    public static final int INSTANCE_FLAG = 0x2;
    public static final int EVENT_FLAG = 0x4;
    public static final int RULE_SET_FLAG = 0x8;
    public static final int CHANNEL_FLAG = 0x10;
    public static final int ALL_TYPES = CONCEPT_FLAG | INSTANCE_FLAG | EVENT_FLAG | RULE_SET_FLAG | CHANNEL_FLAG;

    protected static final Comparator<? super Entity> ENTITY_PATH_COMPARATOR = new Comparator<Entity>() {
        public int compare(Entity o1, Entity o2) {
            if (null == o1) {
                return (null == o2) ? 0 : -1;
            }
            if (null == o2) {
                return 1;
            }
            return o1.getFullPath().compareTo(o2.getFullPath());
        }
    };

    protected Project project = null;
    protected boolean m_showsEmptyFolders = false;
    protected Set m_pathSet = null;
    protected Filter filter;

    /**
     * Construct a new Project Tree from project.
     *
     * @param project
     */
    public ProjectTree(Project project) {
        this(project, new HashSet(), true);
    }

    public ProjectTree(Project project, Filter filter) {
        this(project, new HashSet(), true);
        if (filter!= null) {
            this.filter = filter;
            m_showsEmptyFolders = false;
        }
    }

    /**
     * Constructor
     *
     * @param project          The project from which to populate.
     * @param pathsToSkip      Paths of Entities that will excluded, regardless of typesToPopulate.
     * @param showEmptyFolders Flag indicating whether empty folders should be added to tree.
     */

    public ProjectTree(Project project, Set pathsToSkip, boolean showEmptyFolders) {
        super();
        setCellRenderer(new ProjectTreeCellRenderer());
        this.project = project;
        m_pathSet = pathsToSkip;
        m_showsEmptyFolders = showEmptyFolders;
        rebuildTree();
    }

    /**
     * Returns true if and only if the Entity path set contains the passed String.
     *
     * @param entityPath
     * @return a boolean
     */
    public boolean containedInPathSet(String entityPath) {
        if (m_pathSet == null) return false;
        return m_pathSet.contains(entityPath);
    }

    /**
     * Returns the Set of Entity paths used.
     *
     * @return the Set of Entity paths used.
     */
    public Set getPathSet() {
        return m_pathSet;
    }

    /**
     * Sets the Set of Entity paths, and rebuilds the tree.
     *
     * @param pathSet A Set containing Entity paths.
     */
    public void setPathSet(Set pathSet) {
        m_pathSet = pathSet;
        rebuildTree();
    }

    /**
     * Returns whether or not this tree displays empty folders as nodes.
     *
     * @return Whether or not this tree displays empty folders as nodes.
     */
    public boolean showsEmptyFolders() {
        return m_showsEmptyFolders;
    }


    /**
     * Build the entire model tree (mirrors the Designer project tree).
     */
    private void rebuildTree() {
        if (project == null) {
            setModel(new DefaultTreeModel(null));
            return;
        }
        Ontology ontology = project.getOntology();
        Folder rootFolder = ontology.getRootFolder();
        ProjectTreeMutableTreeNode rootNode = new ProjectTreeMutableTreeNode(rootFolder);
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        appendFolderNodes(rootFolder, rootNode);
        //TODO - Sridhar - Add other archives too.
        setModel(model);
        expandTree();
    }

    public void setProject(Project project) {
        this.project = project;
        rebuildTree();
    }

    public void closeProject() {
        setModel(new DefaultTreeModel(null));
        return;
    }


    /**
     * Appends the filtered Folder content to the given tree node.
     *
     * @param startFolder Folder to traverse.
     * @param startNode   DefaultMutableTreeNode to append to.
     */
    protected boolean appendFolderNodes(Folder startFolder, DefaultMutableTreeNode startNode) {
        boolean didAppend = this.appendEntityNodes(startNode, startFolder);

        final List<Folder> subFolders = new LinkedList(startFolder.getSubFolders());
        Collections.sort(subFolders, ENTITY_PATH_COMPARATOR);
        for (Folder subFolder : subFolders) {
            final ProjectTreeMutableTreeNode subFolderNode = new ProjectTreeMutableTreeNode(subFolder);
            if (this.appendFolderNodes(subFolder, subFolderNode) || this.m_showsEmptyFolders) {
                startNode.add(subFolderNode);
                didAppend = true;
            }
        }
        
        return didAppend;
    }


    /*
     * returns true if the child was appended
     */

    private boolean appendChildWithFiltering(Entity entity, DefaultMutableTreeNode parent, DefaultMutableTreeNode child) {
        // Do not add the child if we are filtering types, and there are no children
        if ((null != this.filter) && !this.filter.accept(entity)) {
            return false;
        }
        parent.add(child);
        return true;
    }

    /**
     * Append all entity nodes in the passed folder.
     *
     * @param folderNode
     * @param folder
     */
    protected boolean appendEntityNodes(DefaultMutableTreeNode folderNode, Folder folder) {
        boolean didAppendAnything = false;
        final List<Entity> entities = folder.getEntities(false);
        Collections.sort(entities, ENTITY_PATH_COMPARATOR);
        for (Entity entity : entities) {
            String entityPath = entity.getFullPath();
            boolean didAppend = false;

            // Appends the Entity as a node if applicable
            if (!(entity instanceof Folder) || this.containedInPathSet(entityPath)) {
                ProjectTreeMutableTreeNode entityNode = new ProjectTreeMutableTreeNode(entity);
                entityNode.setUserObject(entity);
                if (entity instanceof Concept) {
                    didAppend = appendStateMachines(entityNode, (Concept) entity);
//                } else if (entity instanceof RuleSet) {
//                    didAppend = appendRules(entityNode, (RuleSet) entity);
                } else if (entity instanceof Channel) {
                    didAppend = appendDestination(entityNode, (Channel) entity);
                } else {
                    entityNode.setAllowsChildren(false);
                }
                if (didAppend) {
                    folderNode.add(entityNode);
                } else {
                    didAppend = appendChildWithFiltering(entity, folderNode, entityNode);
                }
                didAppendAnything = didAppendAnything || didAppend;
            }
        }
        return didAppendAnything;
    }

    private boolean appendDestination(ProjectTreeMutableTreeNode channelNode, Channel channel) {
        Iterator itr = channel.getDriver().getDestinations();
        boolean didAppend = false;
        while (itr.hasNext()) {
            Destination dest = (Destination) itr.next();
            ProjectTreeMutableTreeNode destNode = new ProjectTreeMutableTreeNode(dest);
            destNode.setUserObject(dest);
            didAppend |= appendChildWithFiltering(dest, channelNode, destNode);
            //channelNode.add(destNode);
            destNode.setAllowsChildren(false);
        }
        return didAppend;
    }

    private boolean appendStateMachines(ProjectTreeMutableTreeNode conceptNode, Concept entity) {
        boolean didAppend = false;
        if (entity.getStateMachines() == null)
            return didAppend;
        for (Object o : entity.getStateMachines()) {
            StateMachine sm = (StateMachine) o;
            if (!(sm instanceof StandaloneStateMachine)) {
                ProjectTreeMutableTreeNode smNode = new ProjectTreeMutableTreeNode(sm);
                smNode.setUserObject(sm);
                didAppend |= appendChildWithFiltering(sm, conceptNode, smNode);
                //conceptNode.add(smNode);
                smNode.setAllowsChildren(false);
            }
        }
        return didAppend;
    }

    private boolean appendRules(ProjectTreeMutableTreeNode ruleSetNode, RuleSet ruleSet) {
        boolean didAppend = false;
        if (ruleSet.getRules() == null)
            return didAppend;
        for (Object o : ruleSet.getRules()) {
            Rule r = (Rule) o;
            ProjectTreeMutableTreeNode ruleNode = new ProjectTreeMutableTreeNode(r);
            ruleNode.setUserObject(r);
            didAppend |= appendChildWithFiltering(r, ruleSetNode, ruleNode);
            //ruleSetNode.add(ruleNode);
            ruleNode.setAllowsChildren(false);
        }
        return didAppend;
    }


    /**
     * Return the currently selected object.
     */
    public Object getSelectedObject() {
        Entity entity = getSelectedEntity();
        if (entity == null) return null;
        return entity.getFullPath();
    }

    /**
     * Returns the selected Entity
     *
     * @return an Entity
     */
    public Entity getSelectedEntity() {
        TreePath selectionPath = getSelectionPath();
        if (selectionPath == null) return null;

        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
        return (Entity) treeNode.getUserObject();
    }

    /**
     * Drag and Drop methods
     */
    public void dragGestureRecognized(DragGestureEvent dge) {
//        Entity selectedEntity = getSelectedEntity();
//        if(selectedEntity == null || selectedEntity instanceof Folder) return;
//
//        EntitySelection es = new EntitySelection(selectedEntity);
//        dge.startDrag(DragSource.DefaultCopyDrop, es, this);
    }

    public void expandTree() {
        int rowCount = 0;
        do {
            rowCount = this.getRowCount();
            for (int rowIndex = rowCount; rowIndex >= 0; rowIndex--) {
                this.expandRow(rowIndex);
            }
        } while (rowCount != this.getRowCount());
    }

    /**
     * End Drag and DropMethods
     */
    public Entity getEntity(TreePath path) {
        if (path == null) return null;
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        return (Entity) treeNode.getUserObject();
    }


    public Filter getFilter() {
        return this.filter;
    }


    public boolean isLeafNode(TreePath path) {
        if (path == null)
            return false;
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (treeNode == null)
            return false;
        return (treeNode.getChildCount() == 0);
    }


    @Override
    public void setSelectionPath(TreePath path) {
        if (this.filter.accept(this.getEntity(path))) {
            super.setSelectionPath(path);
        }
    }


    class ProjectTreeCellRenderer extends DefaultTreeCellRenderer {

        public ProjectTreeCellRenderer() {
            super();
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                      boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {
            Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if ((value instanceof ProjectTreeMutableTreeNode) && (component instanceof JLabel)) {
                ProjectTreeMutableTreeNode node = (ProjectTreeMutableTreeNode) value;
                JLabel label = (JLabel) component;

                Icon icon = node.getIcon();
                if (icon != null) {
                    label.setIcon(node.getIcon());
                }
                label.setToolTipText(node.getToolTipText());

            }
            return component;
        }
    }
}