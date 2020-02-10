package com.tibco.be.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmAttributeGroup;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSupport;
import com.tibco.xml.schema.SmType;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 20, 2005
 * Time: 12:57:11 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Class to Cache all the Expanded Name with SmType ...
 * Copied from BW
 *
 */
public class SmModel2ExpandedNameCache {

    static HashMap cachedElements = new HashMap();
    static final String PAYLOAD_PROPERTY = "_payload_";

    public static synchronized SmModel2ExpandedName buildCache(SmElement element, boolean isPayLoadElement) {

        SmModel2ExpandedName sme = (SmModel2ExpandedName)cachedElements.get(element);
        if (sme == null) {
            SmParticleTerm term = element;
            if (isPayLoadElement) {
                term = getPayloadElement(element);
            }
           sme = new SmModel2ExpandedName(element);
           initializeModelGroupMap (term, sme.mapModelGroup2Name_ExpandedName, sme.mapModelGroup2Name_ExpandedName_ForAttributes);
            cachedElements.put(element, sme);
        }
        return sme;

    }

    public static SmElement getPayloadElement(SmElement type) {

        SmElement ele = SmSupport.getElementInContext(type.getType(), null, PAYLOAD_PROPERTY);
        if (ele != null)    return ele;
        return type;

    }


    protected static void initializeModelGroupMap (final SmParticleTerm outputTypeElement, Map mapModelGroup2Name_ExpandedName, Map mapModelGroup2Name_ExpandedName_ForAttributes)
    {
        if (outputTypeElement == null)
        {
            return;
        }

        SmModelGroup modelGroup = null;
        SmAttributeGroup attribteGroup = null;
        boolean bIsChoice = false;
        if (outputTypeElement instanceof SmElement)
        {
            SmType smType = ((SmElement)outputTypeElement).getType();
            if (SmSupport.isTextOnlyContent(smType))
            {
                String strNameForElement = ((SmElement)outputTypeElement).getName();
                String strNamespaceForElement = ((SmElement)outputTypeElement).getNamespace();
                HashMap name2ExpandedName = new HashMap();
                name2ExpandedName.put (
                    strNameForElement,
                    ExpandedName.makeName(strNamespaceForElement, strNameForElement)
                );
                mapModelGroup2Name_ExpandedName.put(outputTypeElement, name2ExpandedName);
                return;
            }
            else
            {
                modelGroup = smType.getContentModel();

                if (modelGroup == null)
                {
                    return;
                }
                attribteGroup = smType.getAttributeModel();
            }
        }
        else if (outputTypeElement instanceof SmModelGroup)
        {
            modelGroup = (SmModelGroup)outputTypeElement;
        }

        if (mapModelGroup2Name_ExpandedName.containsKey(modelGroup))
        {
            if (outputTypeElement instanceof SmElement)
            {
                    initializeModelGroupMapForAttributes((SmElement)outputTypeElement, mapModelGroup2Name_ExpandedName_ForAttributes);
            }
            return;
        }

        HashMap name_ExpandedName = new HashMap ();
        mapModelGroup2Name_ExpandedName.put(modelGroup, name_ExpandedName);

        Iterator itParticles = modelGroup.getParticles();
        for (; itParticles.hasNext();)
        {
            SmParticle particle = (SmParticle) itParticles.next();
            SmParticleTerm particleTerm = particle.getTerm();
            if (particleTerm instanceof SmElement)
            {
                ExpandedName expandedName = particleTerm.getExpandedName();
                name_ExpandedName.put (expandedName.getLocalName(), expandedName);

                SmType smTypeChild = ((SmElement)(particleTerm)).getType();
                if (SmSupport.isTextOnlyContent(smTypeChild))
                {
                }
                else
                {
                    // recursive-loop
                    initializeModelGroupMap((SmElement)(particleTerm), mapModelGroup2Name_ExpandedName, mapModelGroup2Name_ExpandedName_ForAttributes);
                }
                //initializeModelGroupMapForAttributes((SmElement)(particleTerm));
            }
        }


        if (outputTypeElement instanceof SmElement)
        {
            initializeModelGroupMapForAttributes((SmElement)outputTypeElement, mapModelGroup2Name_ExpandedName_ForAttributes);
        }
    }

    protected static void initializeModelGroupMapForAttributes (final SmElement outputTypeElement, Map mapModelGroup2Name_ExpandedName_ForAttributes)
    {
        if (outputTypeElement == null)
        {
            return;
        }
        SmType smType = outputTypeElement.getType();
        SmAttributeGroup attributeModelGroup = smType.getAttributeModel();
        if ((attributeModelGroup == null) || (mapModelGroup2Name_ExpandedName_ForAttributes.containsKey(attributeModelGroup)))
        {
            return;
        }

        HashMap name_ExpandedName = new HashMap ();
        mapModelGroup2Name_ExpandedName_ForAttributes.put(attributeModelGroup, name_ExpandedName);

        Iterator itParticles = attributeModelGroup.getParticles();
        for (; itParticles.hasNext();)
        {
            SmParticle particle = (SmParticle) itParticles.next();
            SmParticleTerm particleTerm = particle.getTerm();
            if (particleTerm instanceof SmAttribute)
            {
                ExpandedName expandedName = particleTerm.getExpandedName();
                name_ExpandedName.put (expandedName.getLocalName(), expandedName);
            }
        }
    }

}
