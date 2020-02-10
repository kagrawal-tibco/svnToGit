package com.tibco.cep.bpmn.ui;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.invokeOnDisplayThread;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Panel;

import javax.swing.JRootPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

//import com.jidesoft.plaf.LookAndFeelFactory;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.diagramming.utils.SyncXErrorHandler;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.XPathTypeReport;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.xpath.XPathEditWindow;
import com.tibco.xml.schema.SmSequenceType;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessXPathWizardPage extends WizardPage implements ChangeListener {
	
    protected IProject fProject;
	protected XPathEditWindow xpeWindow;
	protected UIAgent uiAgent;
	protected java.awt.Frame frame;
	private EObjectWrapper<EClass, EObject> process;
	private String xpath;
	private ProcessXPathPanelBuilder builder;
	private  IProcessXPathValidate processXPathValidate;
	private String defaultMessage = "";
	private String[] loopArgs;
	
	/**
	 * @param pageTitle
	 * @param project
	 * @param processXPathValidate
	 * @param process
	 * @param xpath
	 */
	protected ProcessXPathWizardPage(String pageTitle, 
			                  IProject project, 
			                  IProcessXPathValidate processXPathValidate,
			                  EObjectWrapper<EClass, EObject> process, 
			                  String xpath, String...args) {
		super(pageTitle);
		this.fProject = project;
		this.process = process;
		this.xpath = xpath;
		this.processXPathValidate = processXPathValidate;
		this.loopArgs = args;
	}

	/**
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.EMBEDDED);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = convertWidthInCharsToPixels(100);
		data.heightHint = 400;
		composite.setLayoutData(data);
		
//		LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
		System.setProperty("sun.awt.noerasebackground", "true");
		
		Container panel = getSwingContainer(composite);
		// panel.setBackground(Color.RED);
		builder = new ProcessXPathPanelBuilder(frame, panel, fProject, process, xpath, loopArgs);
		builder.getXpeWindow().addChangeListener(this);
		setControl(composite);
		setDefaultMessage();
	}
	
	public void setDefaultMessage () {
		if (processXPathValidate.getExpectedType() != null) {
			defaultMessage = Messages.getString("expected.process.sm.type", processXPathValidate.getExpectedType());
			setMessage(defaultMessage, DialogPage.INFORMATION);
		}
	}
	
	public String getXPath() {
		return builder.getXPath();
	}
	
	@SuppressWarnings("serial")
	protected Container getSwingContainer(Composite parent) {
		frame = SWT_AWT.new_Frame(parent);
		new SyncXErrorHandler().installHandler();
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		return root.getContentPane();
	}
	
	/**
	 * @param uiAgent
	 *            the uiAgent to set
	 */
	public void setUIAgent(UIAgent uiAgent) {
		this.uiAgent = uiAgent;
	}

	public String getErrors() {
		XPathTypeReport typeReport = builder.getXpeWindow().getTypeReport();
		if (!typeReport.errors.hasZeroErrorCount()) {
			final StringBuffer buffer = new StringBuffer();
			for (ErrorMessage message : typeReport.errors.getErrorMessages()) {
				buffer.append(message.getMessage() + "\n");
			}
			if (buffer.length() > 0 ) {
				return buffer.toString();
			}
		}
		return null;
	}
	
	public String getUserMessage() {
		return builder.getXpeWindow().getTypeReport().xtype.getUserMessage();
	}
	

	public boolean isValid() {
		XPathTypeReport typeReport = builder.getXpeWindow().getTypeReport();
		SmSequenceType xtype = typeReport.xtype;
		if (xtype != null && !processXPathValidate.isValid(xtype)) {
			return false;
		}else if(xtype == null)
			return false;
		return true;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		invokeOnDisplayThread(new Runnable() {
			@Override
			public void run() {
				setErrorMessage(null);
				setDefaultMessage();
			}
		}, false);
	}
}