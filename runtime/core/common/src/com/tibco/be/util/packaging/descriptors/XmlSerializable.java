package com.tibco.be.util.packaging.descriptors;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:38:57 PM
 */

public interface XmlSerializable {

    ExpandedName getTypeXName();

    XiNode toXiNode(XiFactory xiFactory);

    void fromXiNode(XiNode xiNode) throws XmlAtomicValueCastException;

}
