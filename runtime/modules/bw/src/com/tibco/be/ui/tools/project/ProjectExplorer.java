package com.tibco.be.ui.tools.project;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.jidesoft.icons.IconsFactory;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.JideToggleButton;
import com.tibco.be.util.Filter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.repo.Workspace;
import com.tibco.cep.studio.core.repo.emf.EMFProject;

/**
 * User: ssubrama
 * Creation Date: May 17, 2008
 * Creation Time: 3:35:18 PM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
public class ProjectExplorer extends JPanel {

    public static String TREE_ICON_NAME =   "/com/tibco/be/ui/resources/tree.png";
    public static String SORT_ICON_NAME =   "/com/tibco/be/ui/resources/sort.png";
    public final static String PREFERENCE_DIR = ".TIBCO";

    ProjectTree projectTree;
    private int _order; // 0 or 1
    private EMFProject project;
    private String repoUrl;
    static protected Workspace space;

    static {
        try {
            space = (Workspace) Class.forName("com.tibco.cep.studio.core.repo.emf.EMFWorkspace")
                    .getMethod("getInstance").invoke(null);
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }


    public ProjectExplorer() {
    	this(null);
    }

    public ProjectExplorer(Filter filter) {
        super(new BorderLayout(2,2));

        projectTree = new ProjectTree(null, filter);
        this.setBorder(BorderFactory.createEmptyBorder(0, 3, 2, 3));
        projectTree.setBorder(BorderFactory.createEmptyBorder(0, 3, 2, 2));

//        this.add(createToolBar(), BorderLayout.BEFORE_FIRST_LINE);

        JideScrollPane scrollPane = new JideScrollPane(projectTree);
        scrollPane.setRequestFocusEnabled(false);
        scrollPane.setFocusable(false);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void loadProject (String repoUrl) throws Exception {
//        Project dummyProject = new BEProject().createProject(repoUrl); //creates the VFileFactory
//        if (!dummyProject.isValidDesignerProject()) {
//            String msg = "The directory you selected doesn't appear to contain valid designer files.";
//            throw new RuntimeException(msg);
//        }

        //        ArrayList providers = new ArrayList();
        //        providers.add(beArchiveProvider);
        //        providers.add(sMapResourceProvider);
        //        providers.add(parResourceProvider );
        //        project = space.reloadProject(providers, repoUrl);
        //        projectTree.setProject(project);
        //        this.repoUrl = repoUrl;

        project = new EMFProject(repoUrl);
        project.load();
        projectTree.setProject(project);
        this.repoUrl = repoUrl;
    }

    public void setProject(EMFProject proj) {
        this.project = proj;
        projectTree.setProject(proj);
    }

    public void closeProject() {
        project.close();
        space.deleteProject(repoUrl);
    	projectTree.closeProject();
    }

    protected JComponent createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        JButton categoricViewButton = createToggleButton(new AbstractAction("", IconsFactory.getImageIcon(ProjectExplorer.class, TREE_ICON_NAME)) {
            public void actionPerformed(ActionEvent e) {
                _order = 0;
            }
        });
        categoricViewButton.setToolTipText("Categorizing by Type");
        toolBar.add(categoricViewButton);

        JButton alpheticViewButton = createToggleButton(new AbstractAction("", IconsFactory.getImageIcon(ProjectExplorer.class, SORT_ICON_NAME)) {
            public void actionPerformed(ActionEvent e) {
                _order = 1;
            }
        });
        alpheticViewButton.setToolTipText("Categorizing by Path");
        toolBar.add(alpheticViewButton);

        if (_order == 0) {
            categoricViewButton.setSelected(true);
        }
        else {
            alpheticViewButton.setSelected(true);
        }

        ButtonGroup group = new ButtonGroup();
        group.add(categoricViewButton);
        group.add(alpheticViewButton);

        return toolBar;
    }

    protected JideButton createToggleButton(Action action) {
        JideButton button = new JideToggleButton(action);
        button.setFocusable(false);
        button.setRequestFocusEnabled(false);
        return button;
    }

    public void addTreeSelectionListener(TreeSelectionListener tsl) {
        projectTree.addTreeSelectionListener(tsl);
    }

    public void removeTreeSelectionListener(TreeSelectionListener tsl) {
        projectTree.removeTreeSelectionListener(tsl);
    }

    public Entity getEntity(TreePath path) {
        return projectTree.getEntity(path);
    }

    public boolean isLeafNode(TreePath path) {
    	return projectTree.isLeafNode(path);
    }

    public static String convert2EntityPath(TreePath path) {
        Object[] elements = path.getPath();
        StringBuffer buf = new StringBuffer();
        //buf.append("/");
        int i = 0;
        for (Object o : elements) {
            buf.append(o);
            if (i > 0 && i <=elements.length - 2) {
                buf.append("/");
            }
            ++i;
        }

        return buf.toString();
    }

    public EMFProject getProject() {
        return project;
    }

    public Ontology getOntology() {
        return project.getOntology();
    }

    public String getRepoUrl() {
        return this.repoUrl;
    }


    public void setSelectionPath(Entity entity) {
        final TreePath path;
        if ((null == entity) || !this.projectTree.getFilter().accept(entity)) {
            path = null;
        } else {
            path = this.findTreePath(entity.getFullPath());
        }
        this.projectTree.setSelectionPath(path);
    }


    public void setSelectionPaths(Entity[] entities) {
        final TreePath[] paths;
        if (null == entities) {
            paths = new TreePath[0];
        } else {
            paths = new TreePath[entities.length];
            for (int i=0; i<entities.length; i++) {
                if (this.projectTree.getFilter().accept(entities[i])) {
                    paths[i] = this.findTreePath(entities[i].getFullPath());
                }
            }
        }
        this.projectTree.setSelectionPaths(paths);
    }



    protected TreePath findTreePath(String path) {
        if ((null == path) || path.isEmpty()) {
            return null;
        }
        final TreePath rootPath = new TreePath(this.projectTree.getModel().getRoot());
        final String fullPath = rootPath.getLastPathComponent().toString() + path;
        final java.util.List<String> nodeNames = new LinkedList<String>(Arrays.asList(fullPath.split("/")));
        if (nodeNames.size() == 1) {
            return null;
        }
        return this.findTreePath(rootPath, nodeNames);
    }


    private TreePath findTreePath(TreePath currentPath, java.util.List<String> nodeNames) {
        TreeNode node = (TreeNode) currentPath.getLastPathComponent();

        final int nodeNamesSize = nodeNames.size();
        if ((nodeNamesSize > 0) && node.toString().equals(nodeNames.get(0))) {
            if (nodeNamesSize == 1) {
                return currentPath;

            } else if (node.getChildCount() >= 0) {
                for (Enumeration e = node.children(); e.hasMoreElements();) {
                    final TreePath path = currentPath.pathByAddingChild(e.nextElement());
                    final TreePath found = this.findTreePath(path, nodeNames.subList(1, nodeNamesSize));
                    if (null != found) {
                        return found;
                    }
                }//for
            }//else
        }//if

        return null;
    }


}