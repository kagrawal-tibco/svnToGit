package com.tibco.cep.studio.mapper.ui.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.MapperXSDUtils;
import com.tibco.cep.studio.mapper.core.BEMapperControlArgs;
import com.tibco.cep.studio.mapper.ui.BEMapperInputOutputAdapter;
import com.tibco.xml.mappermodel.emfapi.EMapperUtilities;
import com.tibco.xml.mappermodel.primary.IMapperModelForUI;
import com.tibco.xml.mapperui.emfapi.EMapperFactory;
import com.tibco.xml.mapperui.emfapi.IEMapperControl;
import com.tibco.xml.mapperui.exports.XPathBuilderExp;
import com.tibco.xml.xmodel.xpath.Coercion;

public class SWTMapperUtils {
	
	// A bit of a hack to get the proper resource container for the mapper schema selection dialog (coercions, substitutions, etc).
	public static MapperInvocationContext currentContext;
	private static List<Map<String, Object>> customFuncs;

	private static void initArgs(BEMapperControlArgs mEArgs, MapperInvocationContext context) {
		currentContext = context;
		mEArgs.setSourceElements(MapperXSDUtils.getSourceElements(context));
		mEArgs.setTargetElement(MapperXSDUtils.getTargetEntity(context));
		MapperXSDUtils.updateCustomFunctions(context.getProjectName());
	}
	
	public static void initCustomFunctions() {
		// this only needs to be done once, but it will internally check to ensure it is not done multiple times
		EMapperUtilities.setCustomFunctions(getCustomFuncs());
	}

	public static synchronized List<Map<String, Object>> getCustomFuncs() {
		if (customFuncs == null) {
			try {
				Class<?> custFuncClass = Class.forName("com.tibco.xml.cxf.functionlist.CustomXPathFunctionsList");
				Method method = custFuncClass.getMethod("getCustomFuncs");
				customFuncs = (List<Map<String, Object>>) method.invoke(custFuncClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (customFuncs == null) {
			customFuncs = new ArrayList<Map<String,Object>>();
		}
		return customFuncs;
	}

	public static BEMapperInputOutputAdapter createMapperControl(Composite parent,
			MapperInvocationContext context) {
		BEMapperControlArgs mEArgs = new BEMapperControlArgs();
//		mEArgs.setExpandToShowAllMappingsOnStart(true);
		mEArgs.setAutoExpandSourceTreeLevel(1);
		mEArgs.setAutoExpandTargetTreeLevel(4);
		mEArgs.setInvocationContext(context);
		initArgs(mEArgs, context);
		IEMapperControl mapperControl = EMapperFactory.createMapperControl(parent, mEArgs);
		BEMapperInputOutputAdapter input = new BEMapperInputOutputAdapter(mapperControl, mEArgs);
		mapperControl.setInput(input);
		return input;
	}
	
	public static BEMapperInputOutputAdapter createXPathBuilderControl(Composite parent,
			MapperInvocationContext context) {
		BEMapperControlArgs mEArgs = new BEMapperControlArgs();
		mEArgs.setAutoExpandSourceTreeLevel(1);
		mEArgs.setAutoExpandTargetTreeLevel(4);
		mEArgs.setInvocationContext(context);
		mEArgs.setDisplayEvalTo(true);
		initArgs(mEArgs, context);
		IEMapperControl mapperControl = EMapperFactory.createMapperXPathBuilder(parent, mEArgs);
		BEMapperInputOutputAdapter input = new BEMapperInputOutputAdapter(mapperControl, mEArgs);
		mapperControl.setInput(input);
		processCoercions((XPathBuilderExp) mapperControl, context);
		return input;
	}
	
	private static void processCoercions(XPathBuilderExp mapperControl,
			MapperInvocationContext context) {
		String xslt = context.getXslt();
		List<Coercion> coercions = MapperXSDUtils.getCoercionsFromXPath(xslt);
		if (coercions.size() == 0) {
			return;
		}
		try {
			// the getModel and doRefresh methods are not public API, and so must be called reflectively
			IMapperModelForUI model = (IMapperModelForUI) mapperControl.getClass().getMethod("getModel").invoke(mapperControl);
			model.addCoercionList(coercions);
			/*for (Coercion coercion : coercions) {
				model.addCoercion(coercion);
			}*/
			mapperControl.getClass().getMethod("doRefresh", boolean.class, boolean.class).invoke(mapperControl, true, false);
		} catch (IllegalAccessException e) {
			StudioCorePlugin.log(e);
		} catch (IllegalArgumentException e) {
			StudioCorePlugin.log(e);
		} catch (InvocationTargetException e) {
			StudioCorePlugin.log(e);
		} catch (NoSuchMethodException e) {
			StudioCorePlugin.log(e);
		} catch (SecurityException e) {
			StudioCorePlugin.log(e);
		}
		
	}

	public static BEMapperInputOutputAdapter createMapperControl(Composite parent,
			BEMapperControlArgs mEArgs) {
		IEMapperControl mapperControl = EMapperFactory.createMapperControl(parent, mEArgs);
		BEMapperInputOutputAdapter input = new BEMapperInputOutputAdapter(mapperControl, mEArgs);
		mapperControl.setInput(input);
		return input;
	}

}
