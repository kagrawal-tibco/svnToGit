package com.tibco.be.util;

import java.util.List;

import javax.xml.transform.Templates;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 1:27:39 PM
 * To change this template use Options | File Templates.
 */
public class TemplatesArgumentPair {
    private final Templates templates;
    private final List<String> paramNames;
    private final Class clz;
    private final Class dvmClz;
    private final String xsltTemplate;
	private String instanceVarName;
    
    protected TemplatesArgumentPair(Templates templates, List<String> paramNames, Class recvParameterClass, Class dvmClz, String xsltTemplate) {
    	this(templates, paramNames, recvParameterClass, dvmClz, xsltTemplate, null);
    }
    
    protected TemplatesArgumentPair(Templates templates, List<String> paramNames, Class recvParameterClass, Class dvmClz, String xsltTemplate, String instanceVarName) {
        this.templates = templates;
        this.paramNames = paramNames;
        clz = recvParameterClass;
        this.dvmClz = dvmClz;
        this.xsltTemplate = xsltTemplate;
        this.instanceVarName = instanceVarName;
    }

    public TemplatesArgumentPair(Templates templates, List<String> paramNames, Class recvParameterClass, Class dvmClz) {
    	this(templates, paramNames, recvParameterClass, dvmClz, null);
    }

	public Templates getTemplates() {
        return templates;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

//    public void setRecvParameterClass(Class clz) {
//        this.clz = clz;
//    }

    public Class getRecvParameterClass() {
        return clz;
    }
	public Class getDvmClz() {
		return dvmClz;
	}
	public String getXSLT() {
		return xsltTemplate;
	}
	public String getInstanceVarName() {
		return instanceVarName;
	}
//	public void setDvmClz(Class dvmClz) {
//		this.dvmClz = dvmClz;
//	}

}
