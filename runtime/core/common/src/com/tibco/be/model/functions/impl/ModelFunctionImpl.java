package com.tibco.be.model.functions.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import static com.tibco.be.model.functions.FunctionDomain.*;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 4, 2004
 * Time: 12:21:44 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ModelFunctionImpl implements ModelFunction {
	
    Entity model;
    StringBuffer template;
    StringBuffer signature;
    String contentTemplate;
    FunctionDomain[] fnDomains = new FunctionDomain[0];

    protected static final Class[] argumentsMap1 = {
          java.lang.String.class
        , int.class
        , long.class
        , double.class
        , boolean.class
        , java.util.Calendar.class
        , com.tibco.cep.runtime.model.element.ContainedConcept.class
        , com.tibco.cep.runtime.model.element.Concept.class
    };
    protected static final Class[] argumentsMap2 = {
          java.lang.String[].class
        , int[].class
        , long[].class
        , double[].class
        , boolean[].class
        , java.util.Calendar[].class
        , com.tibco.cep.runtime.model.element.ContainedConcept[].class
        , com.tibco.cep.runtime.model.element.Concept[].class
    };

    protected static final LinkedHashMap RDF_2_PRIMITIVE_ARG_MAP = new LinkedHashMap();
    static {
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.STRING, String.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.INTEGER, int.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.LONG, long.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.DOUBLE, double.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.BOOLEAN, boolean.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.DATETIME, java.util.Calendar.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.CONCEPT_REFERENCE, com.tibco.cep.runtime.model.element.Concept.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.CONCEPT, com.tibco.cep.runtime.model.element.Concept.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.EVENT, com.tibco.cep.runtime.model.event.SimpleEvent.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.TIME_EVENT, com.tibco.cep.runtime.model.event.TimeEvent.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.BASE_CONCEPT, com.tibco.cep.runtime.model.element.Concept.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.BASE_EVENT, com.tibco.cep.kernel.model.entity.Event.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.ADVISORY_EVENT, com.tibco.cep.runtime.model.event.AdvisoryEvent.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.EXCEPTION, com.tibco.cep.runtime.model.exception.BEException.class);
        RDF_2_PRIMITIVE_ARG_MAP.put(RDFTypes.OBJECT, java.lang.Object.class);
    }


    /**
     *
     * @param model
     */
    ModelFunctionImpl (Entity model) {
        this.model=model;
    }

    abstract String getDescription();
    abstract String[] getParameterNames();
    
    /**
     *
     * @return
     */
    public String getModelClass () {
        return ModelNameUtil.modelPathToGeneratedClassName( model.getFullPath());
    }

    /**
     *
     * @return
     */
    String getModelName() {
        return model.getName();
    }

    public boolean reevaluate() {
        return false;  
    }

    private String printableArgClz(Class clz, int entityIndex) {
        Entity entity = null;
        Entity[] entityArgs = (Entity[]) getEntityArguments();
        if((entityArgs != null) && (entityIndex < entityArgs.length)) {
            entity = entityArgs[entityIndex];
        }
        return printableClz(clz, entity);
    }
    
    private String printableReturnClz() {
        Class clz = getReturnClass();
        Entity entity = (Entity) getEntityReturnType();
        if(clz == null && entity == null) return "void";
        
        if(clz == null) clz = Object.class;
        return printableClz(clz, entity);
    }
    
    private String printableClz(Class clz, Entity entity) {
        String arraySuffix = "";
        while(clz.isArray()) {
            arraySuffix += "[]";
            clz = clz.getComponentType();
        }
        String s1;
        if(entity != null && isEntityType(clz)) {
            s1 = ModelUtils.convertPathToPackage(entity.getFullPath()) + arraySuffix;
        } else {
            s1 = clz.getName() + arraySuffix;
            s1 = s1.substring(s1.lastIndexOf('.')+1);
        }
        return s1;
    }

    protected String getFSName() {
        return getName().getNamespaceURI() + "." + getName().getLocalName();
    }

    private static String formatForHTML(String source) {
        if (null == source) {
            return "";
        }
        return source.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    public String argumentTemplate() {
        if (contentTemplate == null) {
            StringBuffer sb = new StringBuffer();

            Class [] parmTypes = getArguments();
            String [] argNames = getParameterNames();

            int entityIndex = 0;
            for (int i=0; i < parmTypes.length; i++) {
                String argName;
                if (i >= argNames.length) {
                    argName="?";
                } else {
                    argName= argNames[i];
                }

                sb.append(printableArgClz(parmTypes[i], entityIndex));
                sb.append(' ');
                sb.append(argName);
                if (i != (parmTypes.length-1)) {
                    sb.append(", ");
                }
                if(isEntityType(parmTypes[i])) entityIndex++;
            }
            contentTemplate = sb.toString();
        }
        return contentTemplate;
    }

    void prepareStrings() {
        try {
            if (template == null) {
                template = new StringBuffer();

                signature = new StringBuffer("</title>\n<style type=\"text/css\">\n* {border:0;padding:0;margin:0;}\n"
                        + "table.descr {margin:10px 10px 0 10px;}\ntable.params {margin:0 10px 10px 10px}"
                        + "td.name,.function td {font-style:italic; font-size:14px;}\n"
                        + "td {font-size:11px;font-family:serif;}\ntd,th {padding:1px;}\n"
                        + "th,.paramsheaders td {font-size:12px;font-family:sans-serif;letter-spacing:1px;text-align:left;}\n"
                        + ".paramsheaders td {text-decoration:underline;}\n"
                        + "</style></head>\n<body><table class=\"descr\" cellspacing=\"1\" cellpadding=\"2\">"
                        + "<tr class=\"function\"><th width=\"100\" align=\"left\">Function:</th><td><code>")
                    .append(getName().getLocalName())
                    .append("</code></td></tr><tr><th align=\"left\">Synopsis:</th><td>")
                    .append(formatForHTML(getDescription()))
                    .append("</td></tr></table>\n"
                        + "<table class=\"params\" cellspacing=\"1\" cellpadding=\"2\">"
                        + "<tr><th width=\"100\" align=\"left\">Parameters:</th></tr>"
                        + "<tr class=\"paramsheaders\"><td class=\"name\" align=\"left\"><u>Name</u></td>"
                        + "<td class=\"type\" align=\"left\"><u>Type</u></td></tr>");

                template.append(getFSName() + "(");
                //signature.append(getName().getNamespaceURI() + "." + getName().getLocalName() + "(");

                Class [] parameterTypes = this.getArguments();
                String [] parameterNames = getParameterNames();

                int entityIndex = 0;
                for (int i=0; i < parameterTypes.length; i++) {
                    template.append("/*").append(parameterNames[i]).append(" ")
                        .append(printableArgClz(parameterTypes[i], entityIndex)).append(" */");
                    //signature.append(printableClz(parameterTypes[i]) + " " + parameterNames[i] + "<br>");

                    signature.append("<tr><td class=\"name\"><code>")
                        .append(formatForHTML(parameterNames[i]))
                        .append("</code></td><td><code>")
                        .append(formatForHTML(printableArgClz(parameterTypes[i], entityIndex)))
                        .append("</code></td></tr>");
                    //signature.append(printableClz(parameterTypes[i]) + " " + "<i>"+ parameterNames[i] + "<br>");
                    if (i != (parameterTypes.length-1)) {
                        template.append(",");
                        //signature.append(",");
                    }
                    if(isEntityType(parameterTypes[i])) entityIndex++;
                }
                template.append(")");
                // return type

                signature = new StringBuffer("<html><head><title>").append(formatForHTML(template.toString()))
                    .append(signature)
                    .append("<tr><th align=\"left\">Returns:</th><td><code>")
                    .append(formatForHTML(printableReturnClz()))
                    .append("</code></td></tr></table>\n</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    void prepareFormatStrings() {
        try {
            if (template == null) {
                template = new StringBuffer();

                template.append(getFSName() + "(");
                //signature.append(getName().getNamespaceURI() + "." + getName().getLocalName() + "(");

                Class [] parameterTypes = this.getArguments();
                String [] parameterNames = getParameterNames();

                int entityIndex = 0;
                for (int i=0; i < parameterTypes.length; i++) {
                    template.append("/*").append(parameterNames[i]).append(" ")
                        .append(printableArgClz(parameterTypes[i], entityIndex)).append(" */");
                    template.append("{"+i+"}");
                    if (i != (parameterTypes.length-1)) {
                        template.append(",");
                    }
                    if(isEntityType(parameterTypes[i])) entityIndex++;
                }
                template.append(")");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean isEntityType(Class clz) {
        while(clz.isArray()) clz = clz.getComponentType();
        return clz == com.tibco.cep.runtime.model.element.ContainedConcept.class || clz == com.tibco.cep.runtime.model.element.Concept.class;
    }

    /**
     *
     * @return
     */
    public String inline() {
        prepareStrings();
        return signature.toString();
    }

    /**
     *
     * @return
     */
    public String signature() {
        prepareStrings();
        return template.toString();
    }
    
    @Override
    public String signatureFormat() {
    	prepareFormatStrings();
    	return template.toString();
    }

    /**
     *
     * @return
     */
    public String template() {
        prepareStrings();
        return template.toString();
    }

    /**
     *
     * @return
     */
    public String toString() {
        try {
            prepareStrings();
            return template.toString();
            //return functionName.getNamespaceURI() + "." + functionName.getLocalName();
            //return template();
        } catch (Exception e) {
            return "foobar";
        }
    }

    /* (non-Javadoc)
	 * @see com.tibco.be.model.functions.impl.ModelFunction#getModel()
	 */
    public Entity getModel() {
        return model;
    }

    public boolean requiresAssert() { return false; }
    
    public boolean isVarargs() { return false; }
    public boolean isVarargsCodegen() { return isVarargs(); }
    public Class getVarargsCodegenArgType() {
    	Class[] args = getArguments();
    	if(args != null && args.length > 0) return args[args.length - 1];
    	else return null;
    }

	@Override
	public FunctionDomain[] getFunctionDomains() {
		return fnDomains;
	}
	
	public void setFunctionDomains(FunctionDomain[] domains) {
		FunctionDomain[] d = new FunctionDomain[domains.length];
		System.arraycopy(domains, 0, d, 0, domains.length);
		this.fnDomains = d;
	}
	
	public boolean isValidInAction() {
		return Arrays.asList(fnDomains).contains(ACTION);
	}

	public boolean isValidInCondition() {
		return Arrays.asList(fnDomains).contains(CONDITION);
	}

	public boolean isValidInQuery() {
		return Arrays.asList(fnDomains).contains(QUERY);
	}

	public boolean isValidInBUI() {
		return Arrays.asList(fnDomains).contains(ACTION);
	}
    
    
}
