package com.tibco.cep.studio.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;

import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class FunctionHoverUtil {

	public static final String DOMAIN = "domain"; //$NON-NLS-1$
	public static final String EXAMPLE = "example";//$NON-NLS-1$
	public static final String FULL_NAME = "fullName";//$NON-NLS-1$
	public static final String MAPPER = "mapper";//$NON-NLS-1$
	public static final String NAME = "name";//$NON-NLS-1$
	public static final String PARAMETERS = "parameters";//$NON-NLS-1$
	public static final String RETURN = "return";//$NON-NLS-1$
	public static final String SEE = "see";//$NON-NLS-1$
	public static final String SIGNATURE = "signature";//$NON-NLS-1$
	public static final String SYNOPSIS = "synopsis";//$NON-NLS-1$
	public static final String VERSION = "version";//$NON-NLS-1$
	public static final String TYPE = "type";//$NON-NLS-1$
	public static final String DESC = "desc";//$NON-NLS-1$

	protected static final StringTemplate TEMPLATE_FOR_HTML_TOOLTIP;
	static {
		InputStream is = BEFunction.class.getClassLoader().getResourceAsStream("com/tibco/be/model/functions/tooltip.st");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[2048];
		try {
			for (int i = is.read(buf); i != -1; i = is.read(buf)) {
				baos.write(buf, 0, i);
			}

			is.close();
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TEMPLATE_FOR_HTML_TOOLTIP = new StringTemplate(new String(baos.toByteArray()));
	}

	public static String getDynamicToolTip(String categoryName, BEFunction annotation) {
		TEMPLATE_FOR_HTML_TOOLTIP.reset();
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("cautions", annotation.cautions());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("description", annotation.description());
		String domain = "";
		for (String s : annotation.domain()) {
			if (domain.isEmpty()) {
				domain += s;
			} else {
				domain = domain + "," + s;
			}
		}
		
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(DOMAIN, Arrays.toString(annotation.fndomain()));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(EXAMPLE, annotation.example());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(FULL_NAME, categoryName + "." + annotation.name());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(MAPPER, annotation.mapper().enabled());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(NAME, annotation.name());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(PARAMETERS, getParams(annotation.params()));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(RETURN, getParam(annotation.freturn()));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(SEE, annotation.see());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(SIGNATURE, annotation.signature());
//		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(SYNOPSIS, annotation.synopsis());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(VERSION, annotation.version());
		final String tooltip = TEMPLATE_FOR_HTML_TOOLTIP.toString();
		return tooltip;
	}
	public static String getDynamicToolTip(String categoryName, Annotation annotation) {
		TEMPLATE_FOR_HTML_TOOLTIP.reset();
		try {
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("cautions", getAnnotationValue(annotation,"cautions"));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute("description", getAnnotationValue(annotation,"description"));
		String domain = "";
		for (String s : (String[])getAnnotationValueArray(annotation,"domain")) {
			if (domain.isEmpty()) {
				domain += s;
			} else {
				domain = domain + "," + s;
			}
		}
		
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(DOMAIN, Arrays.toString(getAnnotationValueArray(annotation,"fndomain")));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(EXAMPLE, getAnnotationValue(annotation,"example"));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(FULL_NAME, categoryName + "." + getAnnotationValue(annotation,"name"));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(MAPPER, getAnnotationValue((Annotation) getAnnotationValue(annotation,"mapper"),"enabled"));//enabled
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(NAME, getAnnotationValue(annotation,"name"));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(PARAMETERS, getParams((Object[])getAnnotationValueArray(annotation,"params")));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(RETURN, getParam((Object)getAnnotationValue(annotation,"freturn")));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(SEE, getAnnotationValue(annotation,"see"));
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(SIGNATURE, getAnnotationValue(annotation,"signature"));
//		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(SYNOPSIS, annotation.synopsis());
		TEMPLATE_FOR_HTML_TOOLTIP.setAttribute(VERSION, getAnnotationValue(annotation,"version"));
		final String tooltip = TEMPLATE_FOR_HTML_TOOLTIP.toString();
		return tooltip;
		} catch (Exception e) {
			StudioCorePlugin.log(e);
			return "";
		}
	}

	private static ParamDescriptor getParam(Object annotationValue) throws Exception {
		final String desc = (String) getAnnotationValue((Annotation) annotationValue, "desc");
		final String name = (String) getAnnotationValue((Annotation) annotationValue, "name");
		final String type = (String) getAnnotationValue((Annotation) annotationValue, "type");
		return new ParamDescriptor() {
			@Override
			public String getDescription() {
				return desc;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getType() {
				return type;
			}
		};
	}
	private static ParamDescriptor[] getParams(Object[] annotationValueArray) throws Exception {
		List<ParamDescriptor> pdlist = new ArrayList<ParamDescriptor>();
		for(Object o:annotationValueArray) {
			pdlist.add(getParam(o));
		}
		return pdlist.toArray(new ParamDescriptor[pdlist.size()]);
	}
	private static Object getAnnotationValue(Annotation annotation, String mparam) throws Exception {
		Class anClass = annotation.annotationType();
		Method m = anClass.getMethod(mparam, null);
		return m.invoke(annotation);
	}
	
	private static Object[] getAnnotationValueArray(Annotation annotation, String mparam) throws Exception {
		Class anClass = annotation.annotationType();
		Method m = anClass.getMethod(mparam, null);
		return (Object[]) m.invoke(annotation);
	}
	
	private static ParamDescriptor[] getParams(FunctionParamDescriptor[] params) {
		List<ParamDescriptor> pdlist = new ArrayList<ParamDescriptor>();
		for (FunctionParamDescriptor p : params) {
			pdlist.add(getParam(p));
		}
		return pdlist.toArray(new ParamDescriptor[pdlist.size()]);
	}

	private static ParamDescriptor getParam(final FunctionParamDescriptor p) {
		return new ParamDescriptor() {
			@Override
			public String getDescription() {
				return p.desc();
			}

			@Override
			public String getName() {
				return p.name();
			}

			@Override
			public String getType() {
				return p.type();
			}
		};
	}

	private interface ParamDescriptor {

		public String getDescription();

		public String getName();

		public String getType();

	}
	
}
