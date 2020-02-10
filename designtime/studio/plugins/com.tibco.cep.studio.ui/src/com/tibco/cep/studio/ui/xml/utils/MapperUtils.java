package com.tibco.cep.studio.ui.xml.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.MalformedTreeException;

import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.be.util.TraxSupport;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.be.util.calendar.Calendar;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.util.mapper.MapperCoreUtils;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.mapper.ui.XPathBuilderDialog;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditDialog;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.xml.wizards.XMLMapperWizard;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.mappermodel.func.FunctionsAndConstants;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MapperUtils extends MapperCoreUtils {

	public static void invokeMapper(final MapperInvocationContext context, final Shell shell){
		try{
			Display.getCurrent().asyncExec(new Runnable(){
				public void run() {
					try{
						refreshFunctions();
						XMLMapperWizard wizard = new XMLMapperWizard(context);
						WizardDialog dialog = new WizardDialog(shell, wizard) {
							@Override
							protected void createButtonsForButtonBar(Composite parent) {
								super.createButtonsForButtonBar(parent);
								Button finishButton = getButton(IDialogConstants.FINISH_ID);
								finishButton.setText(IDialogConstants.OK_LABEL);
							}
						};
						dialog.setMinimumPageSize(700, 500);
						try {
							dialog.create();
							// Workaround for Linux Platforms
							// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
							// user needs to close/re-open the editors
							refreshMapperShell(dialog.getShell());
						} catch (RuntimeException e) {
							if (e.getCause() instanceof InterruptedException) {
								return;
							}
						}
						dialog.open();
					}
					catch(Exception e){
						StudioUIPlugin.log(StudioUIPlugin.newErrorStatus("Error while invoking the mapper", e));
						e.printStackTrace();
					}
				}});
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void refreshMapperShell(Shell shell) {
		// Workaround for Linux Platforms
		// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
		// user needs to close/re-open the editors
		boolean isLinux = Platform.OS_LINUX.equals(Platform.getOS());
		if (isLinux) {
			Point size = shell.computeSize( 510, 540 );
			shell.setSize( size );
			size = shell.computeSize( 700, 500 );
			shell.setSize( size );
		}
	}
	
	public static boolean isSWTMapper(boolean debugger) {
		if (debugger) {
			return true;
		}
		return isSWTMapper(null);
	}
	
	public static boolean isSWTMapper(String projectName) {
		//		return MessageDialog.openQuestion(new Shell(), "", "SWT?");
		if (projectName != null && StudioProjectConfigurationManager.getInstance()
				.getProjectConfiguration(projectName) != null) {
			XPATH_VERSION xpathVersion = StudioProjectConfigurationManager.getInstance()
					.getProjectConfiguration(projectName).getXpathVersion();
			if (xpathVersion == XPATH_VERSION.XPATH_10) {
				return false;
			} else if (xpathVersion == XPATH_VERSION.XPATH_20) {
				return true;
			}
		}
		boolean propTrue = "true".equals(System.getProperty("com.tibco.mapper.swt", "false"));
		return projectName == null ? propTrue : 
			TraxSupport.isXPath2DesignTime(projectName) || propTrue;
	}
	public static void invokeXPathBuilder(final MapperInvocationContext context, final Shell shell){
		if (isSWTMapper(context.getProjectName())) {
			invokeSWTXPathBuilder(context, shell);
			return;
		}
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		final java.awt.Frame frame = SWT_AWT.new_Frame(composite);
		new SyncXErrorHandler().installHandler();
		frame.setAlwaysOnTop(true);
		try {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

		            VariableDefinitionList vdl = makeInputVariableDefinitions(StudioCorePlugin.getCache(context.getProjectName()), context);
					
			    	PredicateWithXSLT function = (PredicateWithXSLT) context.getFunction();
			    	Class c = function.getReturnClass();
			    	SmSequenceType type = getExpectedType(c);
		            XiNode xpath = null;
					try {
			            xpath = XSTemplateSerializer.deSerializeXPathString(context.getXslt());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					HashMap map = XSTemplateSerializer.getNSPrefixesinXPath(xpath);
			        Iterator itr = map.keySet().iterator();
			        NamespaceContextRegistry nsmapper = getNamespaceMapper();
			        while (itr.hasNext()) {
			            String pfx = (String)itr.next();
			            String uri = (String)map.get(pfx);
			            nsmapper.getOrAddPrefixForNamespaceURI(uri, pfx);
			        }
//			    	String xslt = XSTemplateSerializer.serializeXPathString(context.getXslt(), new HashMap(), new ArrayList<Object>());
					ExprContext ec = new ExprContext(vdl, StudioCorePlugin.getUIAgent(context.getProjectName()).getFunctionResolver()).createWithNamespaceMapper(nsmapper);
					String newXPathText = XPathEditDialog.showDialog(StudioCorePlugin.getUIAgent(context.getProjectName()), 
							frame,
							ec,
							ec,
							nsmapper,
							null,
							makeTypeChecker(type),
							null,
							XSTemplateSerializer.getXPathExpressionAsStringValue(xpath));
					
					if (newXPathText == null || newXPathText.length() == 0) {
						return;
					}
					replaceMapperString(context, newXPathText, nsmapper);
				}

				private void replaceMapperString(
						final MapperInvocationContext context,
						final String xpathString, NamespaceContextRegistry nsmapper) {
					if (xpathString == null) {
						return;
					}
					final List vars = XSTemplateSerializer.searchForVariableNamesinExpression(xpathString);
					HashMap pfxs = getPrefix2Namespaces(nsmapper);
					final String newXslt = XSTemplateSerializer.serializeXPathString(xpathString, pfxs, vars);

					if (newXslt == null) {
						return;
					}
					// take the new xslt string, and use the refactoring framework
					// to replace the existing text
					Display.getDefault().asyncExec(new Runnable() {
					
						@Override
						public void run() {
							ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(context.getDocument());
							analyzer.analyzeASTNodeReplace(context.getNode(), "\""+newXslt+"\"");
							try {
								analyzer.getCurrentEdit().apply(context.getDocument());
							} catch (MalformedTreeException e) {
								e.printStackTrace();
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
						}
					});
				}

				private SmSequenceType getExpectedType(Class c) {
					SmSequenceType type = SMDT.STRING;

		            if (c.isAssignableFrom(boolean.class) ) {
		                type = SMDT.BOOLEAN;
		            }
		            else if (c.isAssignableFrom(String.class)) {
		                type = SMDT.STRING;
		            }
		            else if (c.isAssignableFrom(long.class)) { //or Integer
		                type = SMDT.INTEGER;
		            }
		            else if (c.isAssignableFrom(Calendar.class)) {
		                type = SMDT.STRING;
		            }
		            else if (c.isAssignableFrom(double.class)) {
		                type = SMDT.DOUBLE;
		            }
					return type;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
		return;
	}

	private static void invokeSWTXPathBuilder(MapperInvocationContext context,
			Shell shell) {
		refreshFunctions();
		XPathBuilderDialog dialog = new XPathBuilderDialog(shell, context);
		int ret = dialog.open();
		if (ret == Dialog.OK) {
			String newXPath = context.getXslt();
			ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(context.getDocument());
			analyzer.analyzeASTNodeReplace(context.getNode(), "\""+newXPath+"\"");
			try {
				analyzer.getCurrentEdit().apply(context.getDocument());
			} catch (MalformedTreeException e) {
				e.printStackTrace();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	public static void refreshFunctions() {
		FunctionsAndConstants instance = FunctionsAndConstants.getInstance();
		Field[] fields = instance.getClass().getDeclaredFields();
		for (Field field : fields) {
			// BE-21870 : Hack since there is no "refresh" method on FunctionsAndConstants.  
			// we can't look up the field by name, as it's obfuscated.  Just reset all array types (there should only be two)
			if (field.getType().isArray()) {
				try {
					field.setAccessible(true);
					field.set(instance, null);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
	}

	/**
	 * Gets the border size for Input/Output binding view.
	 * @return
	 */
	static Border createSpaceBorder()
	{
		//WCETODO need to get this into a constant w/ designer so it matches up with FormFields, etc. without guesswork.
		return BorderFactory.createEmptyBorder(12,12,10,12); // gigantic border!
	}

	/**
	 * Creates a designer config-form border (move this call into designer/find existing one)
	 * @return
	 */
	public static Border createFullBorder()
	{
		return BorderFactory.createCompoundBorder(createSpaceBorder(),BorderFactory.createEtchedBorder());
	}
	
	public static SmType getSimpleTypeForReturnType(String returnType) {
		SmType smType = null;
		String intType = "INTEGER";
		if (returnType.equalsIgnoreCase(PROPERTY_TYPES.BOOLEAN.getName()))
			smType = XSDL.BOOLEAN;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.INTEGER
				.getName()) || returnType.equalsIgnoreCase(intType))
			smType = XSDL.INTEGER;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.DOUBLE
				.getName()))
			smType = XSDL.DOUBLE;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.STRING
				.getName()))
			smType = XSDL.STRING;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.DATE_TIME
				.getName()))
			smType = XSDL.DATETIME;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.LONG
				.getName()))
			smType = XSDL.LONG;
		else if (returnType.equalsIgnoreCase("object"))
			smType = XSDL.ANY_TYPE;
		else 
			smType = XSDL.ANY_TYPE;
		
		return smType;
	}
}
