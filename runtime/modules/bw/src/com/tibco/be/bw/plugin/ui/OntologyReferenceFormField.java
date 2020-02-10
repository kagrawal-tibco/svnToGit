package com.tibco.be.bw.plugin.ui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.swing.JideBoxLayout;
import com.tibco.ae.designerapi.AEResource;
import com.tibco.ae.designerapi.AEResourceUtils;
import com.tibco.ae.designerapi.DesignerDocument;
import com.tibco.ae.designerapi.forms.ConfigFormField;
import com.tibco.ae.designerapi.forms.TextFormField;
import com.tibco.ae.designerapi.util.GlobalVariableUtilities;
import com.tibco.ae.tools.designer.WindowTracker;
import com.tibco.be.ui.tools.project.ProjectExplorer;
import com.tibco.be.util.Filter;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.Destination;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.util.DesignerGridConstraints;
import com.tibco.util.DesignerGridLayout;
import com.tibco.util.ResourceManager;

/*
 * User: nprade
 * Date: Dec 7, 2009
 * Time: 3:55:44 PM
 */

public class OntologyReferenceFormField
        extends TextFormField
        implements ActionListener, Runnable {


    private Filter filter;
    private boolean pickOnly;
    private JButton browse;
    private JButton clear;

    protected EMFProject project;
    private String browsePath;


    public OntologyReferenceFormField(String property) {
        this(property, AEResourceUtils.getDisplayNameForProperty(property));
    }


    public OntologyReferenceFormField(String property, String labelName) {
        super(property, labelName);

        final ResourceManager bundle = ResourceManager.manager;

        final DesignerGridConstraints c = new DesignerGridConstraints();

        final DesignerGridLayout l = new DesignerGridLayout(new int[]{0, 5, 21, 4, 21, 4, 21}, new int[]{0});
        l.setColumnWeight(1, 1);

        final JPanel contents = this.getContentArea();
        contents.setLayout(l);

        final JTextField textField = this.getTextField();
        textField.setName("tf_" + property);
        contents.add(textField, c.xy(1, 1, "lr"));

        javax.swing.Icon icon;

        icon = bundle.getIcon("ae.resource.reference.form.field.browse.icon");
        this.browse = new JButton(icon);
        contents.add(browse, c.xy(3, 1));
        this.browse.addActionListener(this);
        this.browse.setActionCommand("browse");
        this.browse.setToolTipText(bundle.getString("ae.resource.reference.form.field.browse.tooltip"));
        this.browse.setName("b_" + property + "_browse");

        icon = bundle.getIcon("ae.resource.reference.form.field.clear.icon");
        this.clear = new JButton(icon);
        contents.add(this.clear, c.xy(7, 1));
        this.clear.setPreferredSize(new Dimension(21, 21));
        this.clear.setMinimumSize(new Dimension(21, 21));
        this.clear.setActionCommand("clear");
        this.clear.addActionListener(this);
        this.clear.setToolTipText(bundle.getString("ae.resource.reference.form.field.clear.tooltip"));
        this.clear.setName("b_" + property + "_clear");
    }


    public void actionPerformed(ActionEvent e) {

        DesignerDocument doc = this.getResource().getDesignerDocument();
        if (doc == null) {
            doc = WindowTracker.getFrontDocWindow().getDesignerDocument();
        }

        final Object source = e.getSource();
        final JTextField field = getTextField();
        if (source == field) {
            super.actionPerformed(e);

        } else if ("browse".equals(e.getActionCommand())) {
            this.onBrowse();

        } else if ("clear".equals(e.getActionCommand())) {
            field.setText(null);
        }

    }


    public void dispose() {
        super.dispose();
    }


    protected void fieldHasChanged() {
        super.fieldHasChanged();
        this.updateStatusIcon();
    }


    private boolean hasBrokenReference() {
        final AEResource res = getResource();
        if (null == res) {
            return false;
        }

        String v = (String) this.getValue();

        final DesignerDocument doc = res.getDesignerDocument();
        if ((null != doc) && (null != v)) {
            try {
                v = GlobalVariableUtilities.expand(doc, v).trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!v.isEmpty()) {
                if (null == this.project) {
                    return true;
                }
                final Ontology ontology = this.project.getOntology();
                Object o = ontology.getEntity(v);
                if (null != o) {
                    return (null != this.filter) && !this.filter.accept(o);
                }
                final int lastSlash = v.lastIndexOf("/");
                o = ontology.getEntity(v.substring(0, lastSlash));
                if (! (o instanceof Channel)) {
                    return true;
                }
                final Destination d = ((Channel) o).getDriver().getDestination(v.substring(lastSlash + 1));
                return (null == d)
                        || ((null != this.filter) && !this.filter.accept(d));
            }
        }
        return false;
    }


    void initTextField(JTextField field) {
        field.addActionListener(this);
//        field.setDropTarget(new DropTarget(field, 1, new ReferenceDropTargetListener(this)));
    }

//    public void enableGoToButton(boolean enable) {
//        this.goTo.setEnabled(enable);
//    }


//    public boolean isReturnURIInsteadOfPath() {
//        return this.returnURIInsteadOfPath;
//    }


//    public void setReturnURIInsteadOfPath(boolean returnURIInsteadOfPath) {
//        this.returnURIInsteadOfPath = returnURIInsteadOfPath;
//    }


    public boolean isPickOnly() {
        return this.pickOnly;
    }


    protected void onBrowse() {

        if (null == this.project) {
            return;
        }
        
        Container parent;
        for (parent = this.getParent(); !(parent instanceof JFrame); parent = parent.getParent()) {}
        final JDialog popup = new JDialog((JFrame) parent, this.getLabelText());

        final JTextField jTextField = this.getTextField();
        final String oldValue = this.getTextField().getText();

        final JButton okBtn = new JButton(ButtonPanel.OK);
        okBtn.setAction(new AbstractAction(ButtonPanel.OK) {
            public void actionPerformed(ActionEvent e) {
                popup.setVisible(false);
                popup.dispose();
            }
        });

        final JButton cancelBtn = new JButton(ButtonPanel.CANCEL);
        cancelBtn.setAction(new AbstractAction(ButtonPanel.CANCEL) {
            public void actionPerformed(ActionEvent e) {
                jTextField.setText(oldValue);
                popup.setVisible(false);
                popup.dispose();
            }
        });
        final JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 5));

        final ProjectExplorer pe = new ProjectExplorer(this.filter);
        pe.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getNewLeadSelectionPath();
                if (pe.isLeafNode(path)) {
                    final Entity entity = pe.getEntity(path);
                    jTextField.setText(entity.getFullPath());
                    okBtn.setEnabled(true);
                } else {
                    okBtn.setEnabled(false);
                }
            }
        });
        pe.setProject(project);
        if (null != oldValue) {
            pe.setSelectionPath(project.getOntology().getEntity(oldValue));
        }
        panel.add(pe, JideBoxLayout.VARY);

        final ButtonPanel btnPanel = new ButtonPanel();
        btnPanel.addButton(okBtn, ButtonPanel.AFFIRMATIVE_BUTTON);
        btnPanel.addButton(cancelBtn, ButtonPanel.CANCEL_BUTTON);
        panel.add(btnPanel, JideBoxLayout.FLEXIBLE);
        
        popup.getContentPane().add(panel);
        //popup.setDefaultFocusComponent(pe);
        //popup.setMovable(false);
        popup.setResizable(true);
        //popup.setGripperLocation(SwingConstants.SOUTH_EAST);
        popup.getRootPane().setDefaultButton(cancelBtn);
        //popup.showPopup(jTextField);
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popup.pack();

        final Rectangle pb = parent.getBounds();
        final Point loc = new Point(pb.x + (pb.width/2), pb.y + (pb.height/2));
        popup.setLocation(loc.x - popup.getWidth()/2, loc.y - popup.getHeight()/2);

        popup.setModal(true);
        popup.setAlwaysOnTop(true);
        popup.setVisible(true);
    }


    public void setFilter(Filter filter) {
        this.filter = filter;
    }


    public void run() {
        if (!this.inUpdate) {
            if (this.hasBrokenReference()) {
                this.statusLabel.setIcon(ConfigFormField.BROKEN_ICON);
            } else {
                super.updateStatusIcon();
            }
        }
    }


    public void setBrowseTarget(String path) {
        this.browsePath = path;
    }


    public void setPickOnly(boolean pickOnly) {
        this.pickOnly = pickOnly;
        this.updateFormFieldUI();
    }


    public void setProject(EMFProject p) {
        this.project = p;
    }


    protected boolean updateFormFieldUI() {
        final boolean isEnabled = super.updateFormFieldUI();

        this.browse.setEnabled(isEnabled);
        this.clear.setEnabled(isEnabled);
//        this.goTo.setEnabled(true);

        final JTextField c = this.getTextField();
        c.setEnabled(isEnabled);

        if (isEnabled && this.isPickOnly()) {
            c.setEditable(false);
        }

        return isEnabled;
    }


    protected void updateStatusIcon() {
        EventQueue.invokeLater(this);
    }


//    public void browse(DesignerDocument document, ArrayList<TreeFilter> filters, boolean mustMatchAllFilters) {
//        final JTextField field = getTextField();
//
//        final OntologyResourceChooser chooser = new OntologyResourceChooser(document, false);
//
//        final TreeFilter filterList[] = (null == filters) ? new TreeFilter[0]
//                : filters.toArray(new TreeFilter[filters.size()]);
//
//        chooser.setFilters(filterList, mustMatchAllFilters);
//
//        final String path = field.getText();
//        chooser.setSelectedResourcePath((null != this.browsePath) ? this.browsePath : path);
//        chooser.setVisible(true);
//
//        final String selectedPath = chooser.getSelectedResourcePath();
//        if ((null != selectedPath) && !selectedPath.equals(path)) {
//            field.setText(this.updateBrowsePath(selectedPath));
//        }
//
//        chooser.dispose();
//    }


}