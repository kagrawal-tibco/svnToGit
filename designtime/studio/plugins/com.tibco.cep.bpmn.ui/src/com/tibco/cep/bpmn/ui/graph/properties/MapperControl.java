package com.tibco.cep.bpmn.ui.graph.properties;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.tree.TreePath;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

import com.tibco.be.model.functions.PredicateWithXSLT;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.wsdl.WsdlWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.properties.MapperPropertySection.EntityMapperContext;
import com.tibco.cep.mapper.xml.xdata.DefaultImportRegistry;
import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.bind.BindingElementInfo;
import com.tibco.cep.mapper.xml.xdata.bind.DefaultStylesheetResolver;
import com.tibco.cep.mapper.xml.xdata.bind.StylesheetBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateBinding;
import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.mapper.xml.xdata.xpath.CoercionSet;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.StudioUIAgent;
import com.tibco.cep.studio.mapper.ui.agent.UIAgent;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingTree;
import com.tibco.cep.studio.mapper.ui.data.param.BinaryCategory;
import com.tibco.cep.studio.mapper.ui.data.param.BooleanCategory;
import com.tibco.cep.studio.mapper.ui.data.param.DateTimeCategory;
import com.tibco.cep.studio.mapper.ui.data.param.DecimalCategory;
import com.tibco.cep.studio.mapper.ui.data.param.IntegerCategory;
import com.tibco.cep.studio.mapper.ui.data.param.StringCategory;
import com.tibco.cep.studio.mapper.ui.data.param.TypeCategory;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.build.MutableElement;
import com.tibco.xml.schema.helpers.SmComponentProviderExOnSmNamespaceProvider;
import com.tibco.xml.schema.impl.SmNamespaceProviderImpl;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.tns.TargetNamespaceProvider;
import com.tibco.xml.tns.impl.TargetNamespaceCache;

public class MapperControl extends AbstractMapperControl{

	private Container mapperPanel;
	private Frame awtframe;
	public IProject fproject;
	WsdlWrapper wsdlWrapper;

	/**
	 * @param agent
	 */
	public MapperControl(EntityMapperContext context, Composite parent) {
		super(null , context ,parent);
		this.mapperContext = context;
		this.parent = parent;
		if(this.parent != null){
			initFrame(parent);
			initAgent();
			createControl();
		}	
	}



	/**
	 * @return the awtframe
	 */
	public Frame getFrame() {
		return awtframe;
	}

	/**
	 * @param awtframe
	 *            the Frame to set
	 */
	public void setFrame(Frame awtframe) {
		this.awtframe = awtframe;
	}



	/**
	 * @return the mapperPanel
	 */
	public Container getMapperPanel() {
		return mapperPanel;
	}

	/**
	 * @param mapperPanel
	 *            the mapperPanel to set
	 */
	public void setMapperPanel(Container mapperPanel) {
		this.mapperPanel = mapperPanel;
	}

	@Override
	public boolean isVisible() {
		return getFrame().isVisible();
	}



	@Override
	public void setVisible(boolean visible) {
		if(getFrame().isDisplayable()) {
			getFrame().setVisible(visible);	
		}
		
	}


	public void initAgent() {
		if (getMapperContext() != null) {
			UIAgent agent = StudioCorePlugin.getUIAgent(getMapperContext()
					.getProject().getName());
			setUIAgent(new DelegatingUIAgent(agent));
		} else {
			String projName = null;
			if (fproject != null) {
				projName = fproject.getName();
			}
			UIAgent agent = new StudioUIAgent(getFrame(), projName);
			setUIAgent(new DelegatingUIAgent(agent));
		}
	}

	public void createControl() {
		mapperPanel = getSwingContainer(getFrame());
		bindingEditorPanel = new BindingEditorPanel(getUIAgent());

		bindingEditor = bindingEditorPanel.getEditor();

		bindingEditor.setPreferredSize(new Dimension(500, 300));

		bindingEditor.setInputLabel(BpmnMessages.getString("mapperControl_bindingEditor_input_label"));
		bindingEditor.setInputLabelTooltip(BpmnMessages.getString("mapperControl_bindingEditor_tooltip_label"));
		bindingEditor.setOutputLabel(BpmnMessages.getString("mapperControl_bindingEditor_output_label"));
//		bindingEditor.setOutputRootDisplayName("Entity");
		bindingEditor.getBindingTree().setRootVisible(false);
		
//		Icon dispIcon = JIconsFactory.getImageIcon(JIconsFactory.J32);
//		bindingEditor.setInputRootDisplayIcon(dispIcon);

		bindingEditor.setSubstitutionDialogCallback(new TypeCategory[] {
				StringCategory.INSTANCE, IntegerCategory.INSTANCE,
				DecimalCategory.INSTANCE, BooleanCategory.INSTANCE,
				DateTimeCategory.INSTANCE, BinaryCategory.INSTANCE, },
				new TypeCategory[] {});
		bindingEditorPanel.setPreferredSize(new Dimension(600, 400));
		bindingEditorPanel.setBorder(BorderFactory.createEmptyBorder());
		mapperPanel.add(bindingEditorPanel, BorderLayout.CENTER);
		updateMapperPanel(updateSchema(null));
		getParent().layout();
	}

	/**
	 * @param xslt
	 * @param element
	 * @param agent
	 */
	@SuppressWarnings("rawtypes")
	public void updateMapperPanel(final String xslt) {
		if(this.parent == null)
			return;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TemplateEditorConfiguration tec = generateXslt(xslt);
				
				BindingTree tree = bindingEditor.getBindingTree();

				tree.setSelectionPath(null);
				tree.setEditable(true);

				try {
					bindingEditor.setTemplateEditorConfiguration(tec);
					bindingEditorPanel.setConfiguration(tec, bindingEditorPanel
							.getEditorState());
					bindingEditorPanel.resetUndoManager();
					TreePath pathForRow = bindingEditor.getBindingTree().getPathForRow(0);
					bindingEditor.getBindingTree().expandPath(pathForRow);


				} catch (ArrayIndexOutOfBoundsException e) {
					//
				} catch (Exception e) {
					BpmnUIPlugin.log(e);
				}

			}
		});
	}
	
	@SuppressWarnings("serial")
	protected Container getSwingContainer(Frame parent) {
		JPanel panel = new JPanel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				paint(g);
			}
		};
		panel.setName(Messages.getString("mapperControl.input"));
		panel.setLayout(new BorderLayout());
		panel.setBorder(createFullBorder());
		parent.add(panel);
		parent.setFocusable(true);
		JApplet applet = new JApplet();
		applet.setLayout( new BorderLayout() );
		parent.add(applet);
		applet.add(panel, BorderLayout.CENTER);

		return panel;
	}

	protected void initFrame(Composite parent) {
		setFrame(SWT_AWT.new_Frame(parent));
	}

	static Border createSpaceBorder() {
		return BorderFactory.createEmptyBorder(2, 2, 2, 2); // gigantic border!
	}

	public static Border createFullBorder() {
		return BorderFactory.createCompoundBorder(createSpaceBorder(),
				BorderFactory.createEtchedBorder());
	}

	
}
