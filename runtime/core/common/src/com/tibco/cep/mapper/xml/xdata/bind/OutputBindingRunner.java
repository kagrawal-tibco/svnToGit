package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.XMLNameUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.Variable;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableList;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Takes an activity output & binds it into the process data.
 */
public class OutputBindingRunner {
    private final ExpandedName mSlotName; // switch to slot #.
    private final SmParticleTerm mOutputElement;
    private final SmNamespaceProvider mSchemas;
    private final boolean m_wantsValidation;

    /**
     * Will change when output bindings get more sophisticated...
     */
    public OutputBindingRunner(String activityName, SmNamespaceProvider scp, SmParticleTerm addedElement, boolean wantsValidation) {
        mSlotName = ExpandedName.makeName(NoNamespace.URI,
                                          mangleActivityName(activityName));//switch to index.
        mOutputElement = addedElement;
        mSchemas = scp;
        m_wantsValidation = wantsValidation;
    }

    /**
     * The standard way to mangle Activity names into slot names
     */
    public static String mangleActivityName(String name) {
        String obviousReplacement = name.trim().replace(' ','-'); // this is nicer.
        return XMLNameUtilities.makeValidName(obviousReplacement);
    }

    /**
     * Validates output (if required)
     * @param node
     * @throws SAXException
     */
    public XiNode validate(XiNode node) throws SAXException {
        XiNode retNode = node;
        if (m_wantsValidation)
        {
            retNode = BindingRunner.validate(node,mSchemas,mOutputElement);
        }
        return retNode;
    }

    public void run(XiNode added, VariableList processData) {
        processData.setVariable(mSlotName,new Variable(added));
        /*
        if (mIsPrimitive) { // it's a primitive type.
            processData.set(mSlotIndex,added.get(0)); // get text() content out.
            return;
        }
        // Essentially we need to rename the top node here:
        XMetaData metaData = processData.getMetaData();
        XData temp = XData.create(mSlotElement);

        // copy attribute content, ugh:
        int slotCount = temp.getMetaData().getSlotCount();
        for (int i=0;i<slotCount;i++) {
            Object attr = added.get(i);
            temp.set(i,attr);
        }
        processData.set(mSlotIndex,temp);*/
    }
}

