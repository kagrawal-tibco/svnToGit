package com.tibco.cep.mapper.xml.xdata.xpath;

/* jtb 12/08/04 - mod for migration */
//import com.tibco.bw.store.XmluiAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.XsltStylesheetProvider;
import com.tibco.xml.schema.SmSequenceType;

/**
 * This class acts as a runtime/designtime resolver for Templates.
 */
public class XsltTemplateResolver
{
    private Map mTemplatesByName;       // String (template name) -> Template
    private Map mTemplatesByOutput;     // XType (output type) -> List<Template>
    private List mTemplates;            // list of XsltTemplateDataModel

    private boolean mLocked;
    private XsltStylesheetProvider mStylesheetCache;

    public XsltTemplateResolver()
    {
    }

    public void setXsltTemplateCache(XsltStylesheetProvider templateCache)
    {
        if (mStylesheetCache != null)
        {
            throw new RuntimeException("The template cache can not be changed after its been set!");
        }
        mStylesheetCache = templateCache;
        reset();
    }

    public void reset()
    {
        if (mStylesheetCache == null)
        {
            return;
        }
        mLocked = false;
        mTemplatesByName = new HashMap();
        mTemplatesByOutput = new HashMap();
        mTemplates = new ArrayList();
        addTemplates();
    }

    protected void addTemplates()
    {
        if (mStylesheetCache == null) {
            return;
        }
        Iterator iter = mStylesheetCache.getStylesheets();
        while(iter.hasNext())
        {
            XsltStylesheetDataModel stylesheet = (XsltStylesheetDataModel) iter.next();
            addXsltStylesheetTemplates(stylesheet);
        }
    }

    protected void addXsltStylesheetTemplates(XsltStylesheetDataModel stylesheet)
    {
        throw new RuntimeException();/*XsltTemplateDataModel[] templates = stylesheet.getXsltTemplates();
        for (int i = 0; i < templates.length; i++)
        {
            addXsltTemplateDataModel(templates[i]);
        }*/
    }

    /*
    protected void addXsltTemplateDataModel(XsltTemplateDataModel template)
    {
        if (template == null)
        {
            return;
        }

        String name = template.getName();
        if (name != null)
        {
            mTemplatesByName.put(name, template); // LAMb: does not deal w/ overlapping template names.
        }

        XType outputType = template.getOutputXType();
        if (outputType != null)
        {
            addOutputXType(template);
        }

        mTemplates.add(template);
    }

    // LAMb: need to handle uniqueness issue.
    public XsltTemplateDataModel getTemplateByName(String name)
    {
        return (XsltTemplateDataModel) mTemplatesByName.get(name);
    }

    public XsltTemplateDataModel[] getTemplateByBindingTerm(XType type)
    {
        List templates = getTemplateList(type);
        if (templates == null)
        {
            return XsltTemplateDataModel.EMPTY_TEMPLATES_ARRAY;
        }
        XsltTemplateDataModel[] models = new XsltTemplateDataModel[templates.size()];
        templates.toArray(models);
        return models;
    }*

    protected void addOutputXType(XsltTemplateDataModel template)
    {
        XType outputType = template.getOutputXType();

        if (mTemplatesByOutput.containsKey(outputType))
        {
            List templates = getTemplateList(outputType);
            templates.add(template); // LAMb: what about templates which are already there w/ the same name?
        }
        else
        {
            List templates = new LinkedList();
            templates.add(template);
            mTemplatesByOutput.put(outputType, templates);
        }
    }

    private final List getTemplateList(XType outputType)
    {
        List templates = (List) mTemplatesByOutput.get(outputType); // LAMb: how does equals work for XType?
        return templates;
    }

    public void lock()
    {
        mLocked = true;
    }

    /**
     *  Parses an xslt string into a set of template data models.
     * LAMb: Should this be here?
     * @param xsltString
     * @param ns
     * @param repoAgent
     * @param functions
     * @return XsltTemplateDataModel[]
     *
    public static XsltTemplateDataModel[] parseTemplates(String xsltString, NamespaceMapper ns, XmluiAgent repoAgent, FunctionResolver functions)
    {
        // Is the stylesheet string empty?
        if (xsltString == null || xsltString.length() == 0)
        {
            return XsltTemplateDataModel.EMPTY_TEMPLATES_ARRAY;
        }

        // Parse out the Xslt.
        StylesheetBinding stylesheet;
        try
        {
            stylesheet = ReadFromXSLT.read(xsltString);
            //NamespaceMapper sheetNamespace = stylesheet.getNamespaceMap();
            //ns.mergeNamespaces(sheetNamespace);
        }
        catch (RuntimeException ex)
        {
            return XsltTemplateDataModel.EMPTY_TEMPLATES_ARRAY;
        }

        // Create the template list from the Binding.
        // Filter out non-named templates.
        List templateList = new LinkedList();
        Iterator iter = stylesheet.getChildIterator();
        while (iter.hasNext())
        {
            Binding binding = (Binding) iter.next();
            if (binding instanceof TemplateBinding)
            {
                XsltTemplateDataModel template = new XsltTemplateDataModel((TemplateBinding) binding, ns, repoAgent, functions);
                String name = template.getName();
                templateList.add(template);
            }
        }

        // Create the array from the list and return the result.
        XsltTemplateDataModel[] templateArray = new XsltTemplateDataModel[templateList.size()];
        templateArray = (XsltTemplateDataModel[]) templateList.toArray(templateArray);
        return templateArray;
    }*/

    /**
     * Gets a type from the qname. Where should this method live? This seems an odd place.
     */
/* jtb 12/08/04 - mod for migration
    public static XType getTypeFromQName(String qname, NamespaceMapper ns, XmluiAgent repoAgent)
*/
    public static SmSequenceType getTypeFromQName(String qname, NamespaceMapper ns)
    {
        throw new RuntimeException();/*
        if (repoAgent == null)
        {
            return SimpleXTypes.ITEM; // LAMb: ack...this is bad!
        }

        int index = qname.indexOf(':');
        String namespace;
        String relname;
        if (index<0)
        {
            namespace = null;
            relname = qname;
        }
        else
        {
            namespace = ns.getNamespaceURI(qname.substring(0,index));
            relname = qname.substring(index+1);
        }

        if (namespace == null || namespace.equals("http://www.w3.org/2001/XMLSchema"))
        {
            if (relname.equals("boolean"))
            {
                return SimpleXTypes.BOOLEAN;
            }
            else if (relname.equals("decimal"))
            {
                return SimpleXTypes.DOUBLE;
            }
            else if (relname.equals("string"))
            {
                return SimpleXTypes.STRING;
            }
        }

        SchemaProvider schemas = repoAgent.getSchemaProvider();
        if (schemas == null)
        {
            return SimpleXTypes.ITEM;
        }

        SmSchema schema = schemas.getSchema(namespace);
        if (schema == null)
        {
            return SimpleXTypes.ITEM;
        }

        SmElement el = SmSupport.getElement(schema, relname);
        if (el == null)
        {
            return SimpleXTypes.ITEM;
        }

        XType t = new DocumentRootXType(el).getDocumentElement();
        if (t == null)
        {
            return SimpleXTypes.ITEM;
        }
        return t;*/
    }

    /**
     * Gets a type from the string.
     */
//    public XType getTypeFromQName(String qname, NamespaceMapper ns)
//    {
//        XmluiAgent repoAgent = mStylesheetCache.getRepoAgent();
//        return getTypeFromQName(qname, ns, repoAgent);
//    }
}

