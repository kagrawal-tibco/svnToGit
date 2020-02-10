package com.tibco.be.util.config.topology;

import com.tibco.be.util.XiSupport;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 9/19/11
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class MMIoXml {

    public static String infoXML(String operationName, String msg){
        return buildXML(operationName, msg, MMIoNS.Elements.INFO);
    }

    public static String warningXML(String operationName, String msg){
        return buildXML(operationName, msg, MMIoNS.Elements.WARNING);

    }

    public static String successXML(String operationName, String msg){
        return buildXML(operationName, msg, MMIoNS.Elements.SUCCESS);
    }

    public static String errorXML(String operationName, String errMmsg, String errCode){
        errMmsg = errMmsg == null ? "" : errMmsg;
        errCode = errCode == null ? "" : errCode;
        return serialize(createOperationNode(operationName,createErrorNode(errMmsg, errCode)));
    }

    private static String serialize(XiNode xiNode) {
        return XiSerializer.serialize(xiNode);
    }

    private static String buildXML(String operationName, String msg, ExpandedName expName) {
        return serialize(createOperationNode(operationName, createSimpleNode(msg, expName)));
    }

    private static XiNode createOperationNode(String operName, XiNode child) {
        XiNode operElem = XiSupport.getXiFactory().createElement(MMIoNS.Elements.OPERATION);
        operElem.setAttributeStringValue(MMIoNS.Attributes.NAME,operName);
        operElem.appendChild(child);
        return operElem;
    }

    private static XiNode createSimpleNode(String text, ExpandedName expName) {
        XiNode elem = XiSupport.getXiFactory().createElement(expName);
        elem.appendText(text);
        return elem;
    }

    private static XiNode createErrorNode(String errMsg, String errCode) {
        XiNode errElem = XiSupport.getXiFactory().createElement(MMIoNS.Elements.ERROR);

        XiNode errMsgElem = XiSupport.getXiFactory().createElement(MMIoNS.Elements.ERROR_MSG);
        errMsgElem.appendText(errMsg);

        XiNode errCodeElem = XiSupport.getXiFactory().createElement(MMIoNS.Elements.ERROR_CODE);
        errCodeElem.appendText(errCode);

        errElem.appendChild(errCodeElem);
        errElem.appendChild(errMsgElem);

        return errElem;
    }

}  //MMIoXml
