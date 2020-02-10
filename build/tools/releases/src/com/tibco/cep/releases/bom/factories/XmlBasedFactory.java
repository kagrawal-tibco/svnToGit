package com.tibco.cep.releases.bom.factories;

import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: 6/28/11
 * Time: 6:58 PM
 */
public interface XmlBasedFactory<T> {

    T make(XiNode node) throws Exception;

}
