package com.tibco.cep.studio.core.functions.model;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;

import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.impl.ModelFunction;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.kernel.model.entity.Event;

public abstract class EMFOntologyModelFunction implements ModelFunction {

	private Entity fEntity;
    StringBuffer fTemplate;
    StringBuffer fSignature;
	private String fContentTemplate;
	private FunctionDomain[] fndomains = new FunctionDomain[0];
	
	
	public static final String TYPE_STRING = "java.lang.String"; //$NON-NLS-1$
	public static final String TYPE_OBJECT = "java.lang.Object"; //$NON-NLS-1$
	public static final String TYPE_INT = "java.lang.Integer"; //$NON-NLS-1$
	public static final String TYPE_DOUBLE = "java.lang.Double"; //$NON-NLS-1$
	public static final String TYPE_FLOAT = "java.lang.Float"; //$NON-NLS-1$
	public static final String TYPE_LONG = "java.lang.Long"; //$NON-NLS-1$
	public static final String TYPE_CHAR = "java.lang.Character"; //$NON-NLS-1$
	public static final String TYPE_BYTE = "java.lang.Byte"; //$NON-NLS-1$
	public static final String TYPE_BOOLEAN = "java.lang.Boolean"; //$NON-NLS-1$
	public static final String TYPE_CALENDAR= "java.util.Calendar"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_BYTE = "byte"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_LONG = "long"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_INT = "int"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_DOUBLE = "double"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_FLOAT = "float"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_CHAR = "char"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_BOOLEAN = "boolean"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_BOOLEAN_ARRAY = "boolean[]"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_CHAR_ARRAY = "char[]"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_INT_ARRAY = "int[]"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_LONG_ARRAY = "long[]"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_FLOAT_ARRAY = "float[]"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_DOUBLE_ARRAY = "double[]"; //$NON-NLS-1$
	public static final String TYPE_PRIMITIVE_BYTE_ARRAY = "byte[]"; //$NON-NLS-1$
	
	

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
    protected static final LinkedHashMap RDF_2_PRIMITIVE_ARG_MAP2 = new LinkedHashMap();
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
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_OBJECT, java.lang.Object.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_STRING, java.lang.String.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_INT, Integer.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_LONG, Long.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_DOUBLE, Double.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_FLOAT, Float.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_CHAR, Character.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_BYTE, Byte.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_BOOLEAN, Boolean.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_CALENDAR, java.util.Calendar.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_INT, int.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_LONG, long.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_DOUBLE, double.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_FLOAT, float.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_CHAR, char.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_BYTE, byte.class);
    	RDF_2_PRIMITIVE_ARG_MAP.put(TYPE_PRIMITIVE_BOOLEAN, byte.class);
    	
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.STRING, String[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.INTEGER, int[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.LONG, long[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.DOUBLE, double[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.BOOLEAN, boolean[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.DATETIME, java.util.Calendar[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.CONCEPT_REFERENCE, com.tibco.cep.runtime.model.element.Concept[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.CONCEPT, com.tibco.cep.runtime.model.element.Concept[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.EVENT, com.tibco.cep.runtime.model.event.SimpleEvent[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.TIME_EVENT, com.tibco.cep.runtime.model.event.TimeEvent[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.BASE_CONCEPT, com.tibco.cep.runtime.model.element.Concept[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.BASE_EVENT, com.tibco.cep.kernel.model.entity.Event[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.ADVISORY_EVENT, com.tibco.cep.runtime.model.event.AdvisoryEvent[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.EXCEPTION, com.tibco.cep.runtime.model.exception.BEException[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(RDFTypes.OBJECT, java.lang.Object[].class);
    	
    	
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_INT_ARRAY, int[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_LONG_ARRAY, long[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_DOUBLE_ARRAY, double[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_FLOAT_ARRAY, float[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_CHAR_ARRAY, char[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_BYTE_ARRAY, byte[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_PRIMITIVE_BOOLEAN_ARRAY, byte[].class);
    	
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_OBJECT, java.lang.Object[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_STRING, String[].class);
    	RDF_2_PRIMITIVE_ARG_MAP2.put(TYPE_CALENDAR, java.util.Calendar[].class);
    }

    public EMFOntologyModelFunction(Entity entity) {
		this.fEntity = entity;
	}

    abstract public Entity[] getEntityArguments();
    abstract public Entity   getEntityReturnType();
    abstract String getDescription();
    abstract String[] getParameterNames();

    public Entity getModel() {
        return fEntity;
    }
    
    public String getModelClass () {
        return ModelNameUtil.modelPathToGeneratedClassName( fEntity.getFullPath());
    }

    /**
     *
     * @return
     */
    protected String getModelName() {
        return fEntity.getName();
    }

    @Override
	public String inline() {
        prepareStrings();
        if(fSignature != null) {
        	return fSignature.toString();
        } else 
        	return "";
	}

	@Override
	public boolean reevaluate() {
		return false;
	}

	@Override
	public boolean requiresAssert() {
		return false;
	}

	@Override
	public boolean requiresAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String signature() {
        prepareStrings();
        return fTemplate.toString();
	}
	
	public String signatureFormat() {
		prepareFormatString();
		return fTemplate.toString();
	}

	@Override
	public String template() {
        prepareStrings();
        return fTemplate.toString();
	}
	
    public String argumentTemplate() {
        if (fContentTemplate == null) {
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
            fContentTemplate = sb.toString();
        }
        return fContentTemplate;
    }

    @Override
	public String toString() {
        try {
            prepareStrings();
            return fTemplate.toString();
        } catch (Exception e) {
            return "foobar";
        }
	}

    protected String getFSName() {
        return getName().getNamespaceURI() + "." + getName().getLocalName();
    }

    private boolean isEntityType(Class clz) {
        while(clz.isArray()) clz = clz.getComponentType();
        return clz == com.tibco.cep.runtime.model.element.ContainedConcept.class 
        	|| clz == com.tibco.cep.runtime.model.element.Concept.class
        	|| clz == Event.class;
    }

    private String printableArgClz(Class clz, int entityIndex) {
        Entity entity = null;
        Entity[] entityArgs = getEntityArguments();
        if((entityArgs != null) && (entityIndex < entityArgs.length)) {
            entity = entityArgs[entityIndex];
        }
        return printableClz(clz, entity);
    }
    
    private String printableReturnClz() {
        Class clz = getReturnClass();
        Entity entity = getEntityReturnType();
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
        } else if(Calendar.class.isAssignableFrom(clz)){
        	s1 = "DateTime";
        } else {
            s1 = clz.getName() + arraySuffix;
            s1 = s1.substring(s1.lastIndexOf('.')+1);
        }
        return s1;
    }

    private static String formatForHTML(String source) {
        if (null == source) {
            return "";
        }
        return source.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    void prepareStrings() {
        try {
            if (fTemplate == null) {
                fTemplate = new StringBuffer();

                fSignature = new StringBuffer("</title>\n<style type=\"text/css\">\n* {border:0;padding:0;margin:0;}\n"
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

                fTemplate.append(getFSName() + "(");
                //signature.append(getName().getNamespaceURI() + "." + getName().getLocalName() + "(");

                Class [] parameterTypes = this.getArguments();
                String [] parameterNames = getParameterNames();
                int entityIndex = 0;
                for (int i=0; i < parameterTypes.length; i++) {
                    fTemplate.append("/*").append(parameterNames[i]).append(" ")
                        .append(printableArgClz(parameterTypes[i], i)).append(" */");
                    //signature.append(printableClz(parameterTypes[i]) + " " + parameterNames[i] + "<br>");

                    fSignature.append("<tr><td class=\"name\"><code>")
                        .append(formatForHTML(parameterNames[i]))
                        .append("</code></td><td><code>")
                        .append(formatForHTML(printableArgClz(parameterTypes[i], i)))
                        .append("</code></td></tr>");
                    //signature.append(printableClz(parameterTypes[i]) + " " + "<i>"+ parameterNames[i] + "<br>");
                    if (i != (parameterTypes.length-1)) {
                        fTemplate.append(",");
                        //signature.append(",");
                    }
                    if(isEntityType(parameterTypes[i])) entityIndex++;
                }
                fTemplate.append(")");
                // return type

                fSignature = new StringBuffer("<html><head><title>").append(formatForHTML(fTemplate.toString()))
                    .append(fSignature)
                    .append("<tr><th align=\"left\">Returns:</th><td><code>")
                    .append(formatForHTML(printableReturnClz()))
                    .append("</code></td></tr></table>\n</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void prepareFormatString() {
        try {
            if (fTemplate == null) {
                fTemplate = new StringBuffer();

               

                fTemplate.append(getFSName() + "(");
                //signature.append(getName().getNamespaceURI() + "." + getName().getLocalName() + "(");

                Class [] parameterTypes = this.getArguments();
                String [] parameterNames = getParameterNames();
                int entityIndex = 0;
                for (int i=0; i < parameterTypes.length; i++) {
                    fTemplate.append("/*").append(parameterNames[i]).append(" ")
                        .append(printableArgClz(parameterTypes[i], i)).append(" */");
                    fTemplate.append("{"+i+"}");
                    if (i != (parameterTypes.length-1)) {
                        fTemplate.append(",");
                    }
                    if(isEntityType(parameterTypes[i])) entityIndex++;
                }
                fTemplate.append(")");                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public boolean isVarargs() { return false; }
	public boolean isVarargsCodegen() { return isVarargs(); }
    public Class getVarargsCodegenArgType() {
    	Class[] args = getArguments();
    	if(args != null && args.length > 0) return args[args.length - 1];
    	else return null;
    }

	@Override
	public FunctionDomain[] getFunctionDomains() {
		return fndomains;
	}
	
	public void setFunctionDomains(FunctionDomain[] domains) {
		FunctionDomain[] d = new FunctionDomain[domains.length];
		System.arraycopy(domains, 0, d, 0, domains.length);
		this.fndomains = d;
	}
	
	public boolean isValidInAction() {
		return Arrays.asList(fndomains).contains(ACTION);
	}

	public boolean isValidInCondition() {
		return Arrays.asList(fndomains).contains(CONDITION);
	}

	public boolean isValidInQuery() {
		return Arrays.asList(fndomains).contains(QUERY);
	}

	public boolean isValidInBUI() {
		return Arrays.asList(fndomains).contains(ACTION);
	}
	
}
