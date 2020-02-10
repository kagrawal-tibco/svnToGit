package com.tibco.cep.deployment.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.jidesoft.grid.ExpandableRow;
import com.jidesoft.grid.QuickTableFilterField;
import com.jidesoft.grid.TreeTable;
import com.jidesoft.icons.IconsFactory;
import com.jidesoft.plaf.basic.ThemePainter;
import com.jidesoft.swing.AutoRepeatButtonUtils;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialSide;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmElement;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 17, 2009
 * Time: 12:33:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class SchemaAwareXmlEditor extends JPanel implements ActionListener {

    XmlTableModel tableModel;
    static ExpandedName CLUSTER_CONFIG_NM = ExpandedName.makeName("http://www.tibco.com/be/deployment", "cluster-config");
    XiNode clusterDoc;
    XiFactory xiFactory = XiFactoryFactory.newInstance();



    public SchemaAwareXmlEditor(SmElement rootElement, XiNode clusterDoc) {

        super(new BorderLayout());
        this.clusterDoc = clusterDoc;


        java.util.List<ExpandableRow> rootList = new ArrayList<ExpandableRow>();
        rootList.add(new XmlTableModel.XiNodeRow(clusterDoc));

        tableModel = new XmlTableModel(rootList);

        add(createTablePanel());

    }

    JPanel createTablePanel() {


        JPanel btnAndSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        createButtonPanel(btnAndSearchPanel);

        QuickTableFilterField filterField = new QuickTableFilterField(tableModel);
        btnAndSearchPanel.add(filterField);


        JPanel tablePanel = new JPanel(new BorderLayout(2, 2));
        

        final TreeTable treeTable = new TreeTable(tableModel);

        treeTable.setClearSelectionOnTableDataChanges(false);
        treeTable.setShowTreeLines(true);
        treeTable.expandAll();
        treeTable.setPreferredScrollableViewportSize(new Dimension(700, 400));
        treeTable.setRowHeight(20);

        treeTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        treeTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        treeTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        filterField.setTable(treeTable);
        filterField.setColumnIndices(new int[]{0}); //SS - Ensure search works
        JScrollPane scrollPane = new JScrollPane(treeTable);

        tablePanel.add(scrollPane,BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.add(btnAndSearchPanel, BorderLayout.BEFORE_FIRST_LINE);
        panel.add(tablePanel);
        return panel;
    }

    private JPanel createButtonPanel(JPanel compositePanel) {

        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.EAST));

        AbstractButton _buttons[] = new AbstractButton[6];
        _buttons[0] = createJideButton("Add",   IconsFactory.getImageIcon(this.getClass(), "resources/iconAdd.gif"), this);
        _buttons[1] = createJideButton("Del",   IconsFactory.getImageIcon(this.getClass(),"resources/iconDelete.gif"), this);
        _buttons[2] = createJideButton("Right", IconsFactory.getImageIcon(this.getClass(),"resources/iconMoveRight.gif"), this);
        _buttons[3] = createJideButton("Left",  IconsFactory.getImageIcon(this.getClass(),"resources/iconMoveLeft.gif"), this);
        _buttons[4] = createJideButton("Up",    IconsFactory.getImageIcon(this.getClass(),"resources/iconMoveUp.gif"), this);
        _buttons[5] = createJideButton("Down",  IconsFactory.getImageIcon(this.getClass(),"resources/iconMoveDown.gif"), this);

        for (AbstractButton button : _buttons) {
            panel.add(button);
        }

        compositePanel.add(panel);
        //return panel;
        return compositePanel;
    }

    static JideButton createJideButton(String name, Icon icon, ActionListener al) {
        final JideButton button = new JideButton(icon);
        button.setActionCommand(name);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setForegroundOfState(ThemePainter.STATE_DEFAULT, Color.BLACK);
        AutoRepeatButtonUtils.install(button);
        button.addActionListener(al);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
