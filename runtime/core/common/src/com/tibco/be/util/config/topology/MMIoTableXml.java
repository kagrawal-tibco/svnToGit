package com.tibco.be.util.config.topology;

import com.tibco.be.util.XiSupport;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 9/26/11
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class MMIoTableXml {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

        private static XiNode table;

        public MMIoTableXml() {
            table = XiSupport.getXiFactory().createElement(MMIoNS.Elements.TABLE);
        }

        public Row addRow() {
            final Row row = new Row();
            table.appendChild(row.getRow());
            return row;
        }

        public String serialize(String operName) {
            XiNode operElem = XiSupport.getXiFactory().createElement(MMIoNS.Elements.OPERATION);
            operElem.setAttributeStringValue(MMIoNS.Attributes.NAME,operName);

            XiNode dataNode = XiSupport.getXiFactory().createElement(MMIoNS.Elements.DATA);
            dataNode.appendChild(table);

            operElem.appendChild(dataNode);

            return XiSerializer.serialize(operElem);
    }

        public static class Row {
            private XiNode row;

            private Row() {
                row= XiSupport.getXiFactory().createElement(MMIoNS.Elements.ROW);
            }

            private XiNode getRow() {
                return row;
            }

             public void addColumn(String name, String valueDataType, String value) {
                 XiNode column = XiSupport.getXiFactory().createElement(MMIoNS.Elements.COLUMN);

                column.setAttributeStringValue(MMIoNS.Attributes.NAME, name);
                column.setAttributeStringValue(MMIoNS.Attributes.TYPE, valueDataType);
                column.setAttributeStringValue(MMIoNS.Attributes.VALUE, value);

                row.appendChild(column);
            }

            public void addColumn(String name, String value) {
                addColumn(name, String.class.getName(), value);
            }

            public void addColumns(String[] names, String[] values) {
                for(int i=0; i<names.length; i++) {
                    addColumn(names[i], String.class.getName(), values[i]);
                }
            }

            public void addColumns(String[] names, String[] valuesDataTypes, String[] values) {
                for(int i=0; i<names.length; i++) {
                    addColumn(names[i], valuesDataTypes[i], values[i]);
                }
            }
        }  //Row

    public static void main(String[] args) {
        MMIoTableXml table = new MMIoTableXml();
        Row row = table.addRow();
        String[] colNames = {"COL_NAME","COL_NAME_2"};
        String[] colVals = {"COL_VALUE","COL_VALUE_2"};
        row.addColumns(colNames,colVals);

//        row.addColumn("COL_NAME", "COL_VALUE");
//        row.addColumn("COL_NAME_2", "COL_VALUE_2");

        System.out.println(table.serialize("Test"));

        System.out.println("Stop");
    }

}
