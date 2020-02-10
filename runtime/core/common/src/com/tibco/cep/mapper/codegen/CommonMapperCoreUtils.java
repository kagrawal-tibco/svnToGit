package com.tibco.cep.mapper.codegen;

import java.util.ArrayList;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.BindingManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.bind.ReadFromXSLT;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;

public class CommonMapperCoreUtils {

	public static TemplateBinding getBinding(String template, ArrayList coercionSet) {
		String xslt = XSTemplateSerializer.deSerialize(template, new ArrayList<Object>(), coercionSet);
		TemplateBinding tb = null;
		if ((xslt != null) && (xslt.length() != 0))
		{
			StylesheetBinding ssb = null;
			try {
				ssb = ReadFromXSLT.read(xslt);
			} catch (RuntimeException e) {
				throw new XMLReadException(e); // throw error to allow callers to handle
			}
			if (ssb == null) {
				tb = new TemplateBinding(BindingElementInfo.EMPTY_INFO,null,"/*");
			} else {
				tb = BindingManipulationUtils.getNthTemplate(ssb, 0);
			}
		}
		else {
			tb = new TemplateBinding(BindingElementInfo.EMPTY_INFO,null,"/*");
		}
		return tb;
	}

}
