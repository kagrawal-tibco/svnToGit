package com.tibco.cep.deployment.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jidesoft.grid.AbstractExpandableRow;
import com.jidesoft.grid.CellSpan;
import com.jidesoft.grid.ExpandableRow;
import com.jidesoft.grid.Row;
import com.jidesoft.grid.SpanTableModel;
import com.jidesoft.grid.TreeTableModel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlNodeTest;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.navigation.NodeKindNodeTest;
import com.tibco.xml.schema.SmAttributeGroup;
import com.tibco.xml.schema.SmType;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 17, 2009
 * Time: 12:30:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class XmlTableModel extends TreeTableModel implements  SpanTableModel {


    public XmlTableModel(List<ExpandableRow> children) {
        super(children);        
    }

    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int column) {
        return null;
    }

    public CellSpan getCellSpanAt(int rowIndex, int columnIndex) {

        if (columnIndex == 0) return null;

        XiNodeRow row = (XiNodeRow) this.getRowAt(rowIndex);
        SmType type = row.node.getType();
        int numAttr = getNumberOfAttributes(type);

        switch (columnIndex) {
            case 0:
                return null;
            case 1:
                if (numAttr <= 1) return new CellSpan(rowIndex, numAttr+1, 1, 5);
                return null;
            case 2:
                if (numAttr <= 2) return new CellSpan(rowIndex, numAttr+1, 1, 5);
                return null;

            case 3:
                if (numAttr <= 3) return new CellSpan(rowIndex, numAttr+1, 1, 5);
                return null;
            default:
                return null;
        }


    }

    private int getNumberOfAttributes(SmType type) {
        if (type == null) return 0;

        SmAttributeGroup grp = type.getAttributeModel();
        if (grp == null) return 0;

        Iterator itr = grp.getParticles();
        int numAttr = 0;
        while (itr.hasNext()) {
            ++numAttr;
            itr.next();
        }
        return numAttr;
    }

    public boolean isCellSpanOn() {
        return true;
    }

    public static class XiNodeRow extends AbstractExpandableRow {

        XiNode node;
        List<XiNodeRow> children = null;
        protected static final XmlNodeTest NODE_TEST_ELEMENT  = NodeKindNodeTest.getInstance(XmlNodeKind.ELEMENT);
        static final ExpandedName NAME = ExpandedName.makeName("name");
        static final ExpandedName VALUE = ExpandedName.makeName("value");

        public XiNodeRow(XiNode node) {
            this.node = node;
        }

        public List<?> getChildren() {

            if (children != null) {
                return children;
            }
            //TODO - only SmElement ContentModel allowed. Attributes will be treated as columns
            
            Iterator itr = node.getChildren(NODE_TEST_ELEMENT);
            List<XiNodeRow> tempChildren = new ArrayList<XiNodeRow>();
            while (itr.hasNext()) {
                XiNode n = (XiNode) itr.next();
                tempChildren.add(new XiNodeRow(n));
            }
            setChildren(tempChildren);
            return children;
        }

        public void setChildren(List<?> objects) {
            this.children = (List<XiNodeRow>) objects;
            if (this.children != null) {
                for (Object row : this.children) {
                    if (row instanceof Row) {
                        ((Row) row).setParent(this);
                    }
                }
            }
        }

        public Object getValueAt(int i) {
            switch (i) {
                case 0:
                    String name = node.getAttributeStringValue(NAME);
                    if ((null == name) || (name.trim().length() == 0))
                        return node.getName().getLocalName();
                    else
                        return name;
                case 1:
                    String value = node.getAttributeStringValue(VALUE);
                    return value;
                    //return node.getStringValue();
                default:
                    break; //TODO - get String value from the attribute content model - This is needed for Destination, and some Ontology model

            }
            return null;
        }
    }
}
