package com.tibco.cep.studio.ui.editors.utils;

import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.isDouble;
import static com.tibco.cep.studio.ui.editors.domain.DomainUtils.isLong;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.invokeOnDisplayThread;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setTraverseSupport;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.streambase.sb.StreamBaseException;
import com.streambase.sb.StreamProperties;
import com.streambase.sb.client.StreamBaseClient;
import com.streambase.sb.client.StreamBaseURI;
//import com.jidesoft.grid.TableModelWrapperUtils;
import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelFactory;
import com.tibco.cep.designtime.core.model.service.channel.Choice;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.drawing.IDiagramManager;
import com.tibco.cep.diagramming.preferences.DiagramPreferenceConstants;
//import com.tibco.cep.driver.kafka.KafkaProperties;
import com.tibco.cep.driver.sb.SBConstants;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.resolution.GlobalVariableExtension;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.GvUtil;
import com.tibco.cep.studio.core.util.PasswordUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.core.validation.DefaultResourceValidator;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.RuleFormEditorInput;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormEditor;
import com.tibco.cep.studio.ui.editors.channels.ChannelFormFeederDelegate;
import com.tibco.cep.studio.ui.editors.channels.contoller.PropertyConfiguration;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptFormEditorInput;
import com.tibco.cep.studio.ui.editors.concepts.ScorecardFormEditorInput;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditor;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditorInput;
import com.tibco.cep.studio.ui.editors.domain.AssociateDomains;
import com.tibco.cep.studio.ui.editors.domain.DomainFormEditor;
import com.tibco.cep.studio.ui.editors.domain.DomainFormEditorInput;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.events.EventFormEditorInput;
import com.tibco.cep.studio.ui.editors.project.ProjectDiagramEditor;
import com.tibco.cep.studio.ui.editors.rules.EntitySharedTextColors;
import com.tibco.cep.studio.ui.editors.rules.assist.RulesEditorContentAssistant;
import com.tibco.cep.studio.ui.editors.rules.text.RulesSourceViewerConfiguration;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditor;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditorInput;
import com.tibco.cep.studio.ui.forms.components.CustomPropertyComboBox;
import com.tibco.cep.studio.ui.forms.components.PropertyComboboxCellEditor;
import com.tibco.cep.studio.ui.navigator.view.ProjectExplorer;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;
import com.tibco.cep.studio.ui.util.GvField;
import com.tibco.cep.studio.ui.util.GvUiUtil;
import com.tibco.cep.studio.ui.util.IStudioRuleSourceCommon;
import com.tibco.cep.studio.ui.util.ImageIconsFactory;
import com.tibco.cep.studio.ui.util.PanelUiUtil;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tibco.cep.studio.ui.widgets.DomainResourceSelector;
import com.tibco.cep.studio.ui.wizards.RuleFunctionSelector;
import com.tibco.security.ObfuscationEngine;

/**
 *
 * @author sasahoo
 *
 */
@SuppressWarnings("restriction")
public class EditorUtils {

	public static final String DELIMITER = ";";
	public static final String HTTP = "HTTP";

	/**
	 *
	 * @param property_types
	 * @param resourceType
	 * @return
	 */
	public static ImageIcon getImageIcon(PROPERTY_TYPES property_types, String resourceType) {
		switch (property_types) {
		case INTEGER:
			return createImageIcon("iconInteger16.gif", "int");

		case DOUBLE:
			return createImageIcon("iconReal16.gif", "double");

		case LONG:
			return createImageIcon("iconLong16.gif", "long");

		case BOOLEAN:
			return createImageIcon("iconBoolean16.gif", "boolean");

		case DATE_TIME:
			return createImageIcon("iconDate16.gif", "DateTime");

		case STRING:
			return createImageIcon("iconString16.gif", "String");

		case CONCEPT:
			return createImageIcon("iconConcept16.gif", resourceType);

		case CONCEPT_REFERENCE:
			return createImageIcon("iconConceptRef16.gif", resourceType);

		default:
			break;
		}
		return null;
	}

	/**
	 *
	 * @param property_types
	 * @return
	 */
	public static ImageIcon getImageIcon(PROPERTY_TYPES property_types) {
		switch (property_types) {
		case INTEGER:
			return createImageIcon("iconInteger16.gif", "int");

		case DOUBLE:
			return createImageIcon("iconReal16.gif", "double");

		case LONG:
			return createImageIcon("iconLong16.gif", "long");

		case BOOLEAN:
			return createImageIcon("iconBoolean16.gif", "boolean");

		case DATE_TIME:
			return createImageIcon("iconDate16.gif", "DateTime");

		case STRING:
			return createImageIcon("iconString16.gif", "String");

		default:
			break;
		}
		return null;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	// public static ImageIcon createImageIcon(String path, String description)
	// {
	// java.net.URL imgURL = EditorsUIPlugin.class.getResource(path);
	// if (imgURL != null) {
	// return new ImageIcon(imgURL, description);
	// } else {
	// System.err.println("Couldn't find file: " + path);
	// return null;
	// }
	// }

	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(String file, String description) {
		ImageIcon image = ImageIconsFactory.createImageIcon(file, EditorsUIPlugin.class.getClassLoader());
		image.setDescription(description);
		return image;
	}

	/**
	 *
	 * @param table
	 * @param column
	 */
	public static void setPropertyComboBoxCellEditor(JTable table, TableColumn column) {
		PROPERTY_TYPES[] propTypesArray = PROPERTY_TYPES.values();
		ImageIcon[] imageList = new ImageIcon[6];
		int i = 0;
		for (PROPERTY_TYPES property_types : propTypesArray) {
			ImageIcon image = getImageIcon(property_types);
			if (image != null) {
				imageList[i] = image;
				i++;
			}
		}
		CustomPropertyComboBox combo = new CustomPropertyComboBox(imageList, table);
		column.setCellEditor(new PropertyComboboxCellEditor(combo));
	}

	/**
	 *
	 * @param isNonBrowseFileSystem
	 * @return
	 */
//	@SuppressWarnings("serial")
//	public static DefaultTableCellRenderer getCellRenderer(final boolean isNonBrowseFileSystem) {
//		return new DefaultTableCellRenderer() {
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//					boolean hasFocus, int row, int col) {
//				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
//						col);
//				label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
//				DefaultTableModel model = (DefaultTableModel) TableModelWrapperUtils.getActualTableModel(table
//						.getModel());
//				if (value instanceof ImageIcon) {
//					label.setText(((ImageIcon) value).getDescription());
//					label.setIcon(((ImageIcon) value));
//				} else {
//					if (!isNonBrowseFileSystem) {
//						PROPERTY_TYPES prop_types = (PROPERTY_TYPES) model.getValueAt(row, 6);
//						if (prop_types.getName().equalsIgnoreCase(PROPERTY_TYPES.CONCEPT.getName())
//								|| prop_types.getName().equalsIgnoreCase(PROPERTY_TYPES.CONCEPT_REFERENCE.getName())) {
//							label.setIcon(getImageIcon((PROPERTY_TYPES) model.getValueAt(row, 6), value.toString()));
//						} else {
//							label.setIcon(getImageIcon((PROPERTY_TYPES) model.getValueAt(row, 6)));
//						}
//					}
//				}
//				return label;
//			}
//		};
//	}

	@SuppressWarnings("serial")
	public static DefaultTableCellRenderer getNumericTextCellRenderer() {
		return new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int col) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						col);
				label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
				label.setText(value == null ? "" : value.toString());
				return label;
			}
		};
	}

	/**
	 *
	 * @param property_types
	 * @param resourceType
	 * @return
	 */
	public static void createImage(PROPERTY_TYPES property_types, PropertyTypeCombo imageCombo) {
		switch (property_types) {
		case INTEGER:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconInteger16.gif"),
					property_types.getName());
			break;

		case DOUBLE:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconReal16.gif"),
					property_types.getName());
			break;

		case LONG:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconLong16.gif"),
					property_types.getName());
			break;

		case BOOLEAN:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconBoolean16.gif"),
					property_types.getName());
			break;

		case DATE_TIME:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconDate16.gif"),
					property_types.getName());
			break;

		case STRING:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconString16.gif"),
					property_types.getName());
			break;

		case CONCEPT:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconConcept16.gif"),
					property_types.getName());
			break;

		case CONCEPT_REFERENCE:
			imageCombo.add(EditorsUIPlugin.getDefault().getImage("icons/iconConceptRef16.gif"),
					property_types.getName());
			break;

		default:
			break;
		}
	}

	/**
	 *
	 * @param property_types
	 * @param resourceType
	 * @return
	 */
	public static Image getPropertyImage(PROPERTY_TYPES property_types) {
		if (property_types == null) {
			return null;
		}
		switch (property_types) {
		case INTEGER:
			return EditorsUIPlugin.getDefault().getImage("icons/iconInteger16.gif");
		case DOUBLE:
			return EditorsUIPlugin.getDefault().getImage("icons/iconReal16.gif");
		case LONG:
			return EditorsUIPlugin.getDefault().getImage("icons/iconLong16.gif");
		case BOOLEAN:
			return EditorsUIPlugin.getDefault().getImage("icons/iconBoolean16.gif");
		case DATE_TIME:
			return EditorsUIPlugin.getDefault().getImage("icons/iconDate16.gif");
		case STRING:
			return EditorsUIPlugin.getDefault().getImage("icons/iconString16.gif");
		case CONCEPT:
			return EditorsUIPlugin.getDefault().getImage("icons/iconConcept16.gif");
		case CONCEPT_REFERENCE:
			return EditorsUIPlugin.getDefault().getImage("icons/iconConceptRef16.gif");
		default:
			break;
		}
		return null;
	}

	/**
	 *
	 * @param property_types
	 * @param resourceType
	 * @return
	 */
	public static Image getImage(DOMAIN_DATA_TYPES data_types) {
		switch (data_types) {
		case INTEGER:
			return EditorsUIPlugin.getDefault().getImage("icons/iconInteger16.gif");
		case DOUBLE:
			return EditorsUIPlugin.getDefault().getImage("icons/iconReal16.gif");
		case LONG:
			return EditorsUIPlugin.getDefault().getImage("icons/iconLong16.gif");
		case BOOLEAN:
			return EditorsUIPlugin.getDefault().getImage("icons/iconBoolean16.gif");
		case DATE_TIME:
			return EditorsUIPlugin.getDefault().getImage("icons/iconDate16.gif");
		case STRING:
			return EditorsUIPlugin.getDefault().getImage("icons/iconString16.gif");
		default:
			break;
		}
		return null;
	}

	/**
	 * @param list
	 * @param name
	 * @return
	 */
	public static PropertyDefinition getPropertyDefinition(List<PropertyDefinition> list, String name) {
		for (PropertyDefinition propertyDefinition : list) {
			if (propertyDefinition.getName().equalsIgnoreCase(name)) {
				return propertyDefinition;
			}
		}
		return null;
	}

	/**
	 *
	 * @param table
	 */
	public static void stopTableCellEditing(JTable table) {
		if (table.getCellEditor() != null) {
			table.getCellEditor().stopCellEditing();
		}
	}

	public static void cancelTableCellEditing(JTable table) {
		if (table.getCellEditor() != null) {
			table.getCellEditor().cancelCellEditing();
		}
	}

	/**
	 *
	 * @param table
	 */
	public static void refresh(JTable table) {
		table.repaint();
		table.updateUI();
		table.revalidate();
	}

	/**
	 *
	 * @param model
	 * @return
	 */
	public static String getPropertyName(String entityName, DefaultTableModel model) {
		String name = entityName + "_property_";
		List<Integer> noList = new ArrayList<Integer>();
		for (int row = 0; row < model.getRowCount(); row++) {
			if (model.getValueAt(row, 0).toString().startsWith(name)) {
				String subname = model.getValueAt(row, 0).toString().substring(entityName.length() + 10);
				if (StudioUIUtils.isNumeric(subname)) {
					noList.add(Integer.parseInt(subname));
				}
			}
		}
		try {
			if (noList.size() > 0) {
				java.util.Arrays.sort(noList.toArray());
				int no = noList.get(noList.size() - 1).intValue();
				no++;
				return name + no;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name + "0";

	}

	/**
	 *
	 * @param page
	 * @param ID
	 * @param input
	 */
	public static void openEditor(final IWorkbenchPage page, final IEditorInput input, final String ID) {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				try {
					page.openEditor(input, ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}, false);
	}

	/**
	 *
	 * @param page
	 * @param file
	 */
	public static void openEditor(final IWorkbenchPage page, final IFile file) {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				try {
					if(file!=null)
					IDE.openEditor(page, file);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}, false);
	}

	/**
	 * @param title
	 * @param message
	 */
	public static void openError(final String title, final String message) {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openError(shell, title, message);
			}
		}, false);
	}

	/**
	 * @param toolkit
	 * @param parent
	 * @param propInstance
	 * @param displyedValueList
	 * @param propertyConfiguration
	 * @param controls
	 * @param editor
	 */
	public Object renderComponents(FormToolkit toolkit, Composite parent, final String driverType,
			final PropertyConfiguration propertyConfiguration, final HashMap<Object, Object> controls,
			final ChannelFormEditor editor) {
		
		if (propertyConfiguration.getPropertyConfigType().equalsIgnoreCase("Boolean")) {

			if (propertyConfiguration.isGvToggle() || propertyConfiguration.getPropertyName().equalsIgnoreCase("Queue")
					|| propertyConfiguration.getPropertyName().equalsIgnoreCase("EnableSecurity")
					|| propertyConfiguration.getPropertyName().equalsIgnoreCase("useSsl")) {
				// final GvField gvField=GvUiUtil.createCheckBoxGv(parent,
				// propertyConfiguration.getDefaultValue());
				final GvField gvField = createGvCheckboxField(parent, "", propertyConfiguration.getDefaultValue(),
						editor, propertyConfiguration, driverType, controls);
				controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);
				/////////////////////////////////
				gvField.setGvListener(new Listener() {
					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						// TODO Auto-generated method stub
						Object fieldData = gvField.getGvText().getData();
						if (fieldData instanceof SimpleProperty) {
							SimpleProperty property = (SimpleProperty) fieldData;
							if (GvUtil.isGlobalVar(property.getValue())) {
								String gvVal = GvUtil.getGvDefinedValue(editor.getProject(), property.getValue());
								if (!gvVal.isEmpty() && (propertyConfiguration.getPropertyName()
										.equalsIgnoreCase("EnableSecurity")
										|| propertyConfiguration.getPropertyName().equalsIgnoreCase("useSsl"))) {
									if (propertyConfiguration.getPropertyName().equalsIgnoreCase("EnableSecurity")) {
										updateASChannelExtPropsSection(controls, editor);
									} else {
										updateAS3Security(controls, editor);
									}
									if (property.getValue() == null) {
										Command command = new SetCommand(editor.getEditingDomain(), property,
												ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText());
										EditorUtils.executeCommand(editor, command);
										return;
									}
									editorModifiedOnReset(property, editor);
									if (!property.getValue().equalsIgnoreCase(gvField.getGvText().getText())) {
										Command command = new SetCommand(editor.getEditingDomain(), property,
												ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText().getText());
										EditorUtils.executeCommand(editor, command);
									}
									
								}
							}
							
						}
						validateField((Text)gvField.getGvText(), propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getPropertyName(),editor.getProject().getName());
					}
				});
				/////////////////////////////////
				final Button buttonGv=(Button)gvField.getField();
				return buttonGv;
				
			} else {
				final Button button=toolkit.createButton(parent, "", SWT.CHECK);
				Boolean value = new Boolean(propertyConfiguration.getDefaultValue()).booleanValue();
				button.setSelection(value);
				controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), button);
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						SimpleProperty property = (SimpleProperty) button.getData();
						if (property == null) return;

						// This is hard coded check, to fix one CR 1-831VO3
						// {Support:TIBCO} Enhancement: defining a destination
						// against a JMS channel
						if (!property.getName().isEmpty()) {
							if (property.getName().equals("Queue")) {
								if (controls.get(driverType + CommonIndexUtils.DOT + "DurableSuscriberName") != null) {
									GvField cntrol = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "DurableSuscriberName");
									if (button.getSelection()) {
										cntrol.setEnabled(false);
									} else {
										cntrol.setEnabled(true);
									}
								}
								if (controls.get(driverType + CommonIndexUtils.DOT + "SharedSubscriptionName") != null) {
									GvField cntrol = (GvField)controls.get(driverType + CommonIndexUtils.DOT + "SharedSubscriptionName");
									if (button.getSelection()) {
										cntrol.setEnabled(false);
									} else {
										cntrol.setEnabled(true);
									}
								}
							} else if (property.getName().equals("Receive")) {
								Control matchControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "Match");
								if (button.getSelection()) {
									matchControl.setEnabled(true);
								} else {
									matchControl.setEnabled(false);
								}
							}
//							}else if (property.getName().equals("be.http.PageFlow")) {
//							
//								Control contextPathControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "be.http.contextPath");
//								Control pageFlowFunctionControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "be.http.pageFlowFunction");
//								Control serializerDeserializerControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer"));
//								Control defaultEventControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.DefaultEvent"));
//								Control isJSONPayload = (Control) controls.get(driverType + CommonIndexUtils.DOT + "be.http.jsonPayload");
//								
//								GvField includeEventTypeControl = null;
//								if (driverType.equalsIgnoreCase(HTTP)) {
//									includeEventTypeControl = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "be.http.IncludeEventType");
//								}
//								
//								Control browseControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("Browse"));
//								Control browseControlProperty = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("Browse")+Messages.getString("Property"));
//								if (button.getSelection()) {
//									contextPathControl.setEnabled(true);
//									pageFlowFunctionControl.setEnabled(true);
//									serializerDeserializerControl.setEnabled(false);
//									defaultEventControl.setEnabled(false);
//									browseControl.setEnabled(false);
//									browseControlProperty.setEnabled(true);
//									if (includeEventTypeControl != null) {
//										includeEventTypeControl.getField().setEnabled(false);
//									}
//									isJSONPayload.setEnabled(false);
//								
//								} else {
//									contextPathControl.setEnabled(false);
//									pageFlowFunctionControl.setEnabled(false);
//									serializerDeserializerControl.setEnabled(true);
//									defaultEventControl.setEnabled(true);
//									browseControl.setEnabled(true);
//									browseControlProperty.setEnabled(false);
//									if (includeEventTypeControl != null) {
//										includeEventTypeControl.getField().setEnabled(true);
//									}
//									
//									String serializerDeserializerType = serializerDeserializerControl.toString();
//									if (serializerDeserializerType != null && !serializerDeserializerType.isEmpty() && serializerDeserializerType.contains("SOAPMessageSerializer")) {
//										if (isJSONPayload != null) {
//											isJSONPayload.setEnabled(false);
//										}
//									} else {
//										isJSONPayload.setEnabled(true);
//									}
//								}
//							}

							// AS3 Channel security
							else if (property.getName().equals("useSsl")) {
								updateAS3Security(controls, editor);
							}
							// hard coded for AS channel driver properties
							else if (!property.getName().isEmpty() && property.getName().equals("EnableSecurity")) {
							    updateASChannelExtPropsSection(controls,editor);
							}
							else if (!property.getName().isEmpty() && property.getName().equals("SecurityRole")) {
							    updateASChannelExtPropsSection(controls,editor);
							}
							else if (!property.getName().isEmpty() && property.getName().equals("Credential")) {
							    updateASChannelExtPropsSection(controls,editor);
							}
							else if (!property.getName().isEmpty() &&  property.getName().equals("EnableBuffering")) {
								Control bufferSizeControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "BufferSize");
								Control flushIntervalControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "FlushInterval");

								if (button.getSelection()) {
									bufferSizeControl.setEnabled(true);
									flushIntervalControl.setEnabled(true);
								} else {
									bufferSizeControl.setEnabled(false);
									flushIntervalControl.setEnabled(false);
								}
							}
							else if (!property.getName().isEmpty() &&  property.getName().equals("Receive")){
								Control matchControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "Match");
								if (button.getSelection()) {
									matchControl.setEnabled(true);
								} else {
									matchControl.setEnabled(false);
								}
								
							}
						}
						String value = Boolean.toString(button.getSelection());
						if (property.getValue() == null) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), value);
							EditorUtils.executeCommand(editor, command);
							return;
						}
						editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(value)) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), value);
							EditorUtils.executeCommand(editor, command);
						}
					}
				});
				return button;
			}
			
			// The following default settings for Channel Configuration
			// fieldCheckButton.setSelection(new
			// Boolean(propertyConfiguration.getDefaultValue()).booleanValue());

		} else if (propertyConfiguration.getDisplayedValues() != null
				&& propertyConfiguration.getDisplayedValues().size() > 0) {

				Channel channel = (Channel) editor.getEntity();
				EList<Destination> dests = channel.getDriver().getDestinations();
				for (Destination dest : dests) {
					PropertyMap pmap = dest.getProperties();
					if (pmap == null) continue;
					SimpleProperty property = (SimpleProperty) pmap.getProperties().get(0);
					if(driverType.equals("HTTP") && pmap.getProperties().get(0).getName().equals("IncludeEventType") && !property.isMandatory()) {
						pmap.getProperties().remove(0);
						dest.setProperties(pmap);
					}
				}

			if (propertyConfiguration.isGvToggle() ||
					((driverType.equals("JMS") || driverType.equals("HTTP") || driverType.equals("RendezVous")) &&
					   (propertyConfiguration.getPropertyName().equals("DeliveryMode") ||
					   propertyConfiguration.getPropertyName().equals("AckMode") ||
					   propertyConfiguration.getPropertyName().equals("IncludeEventType") ||
					   propertyConfiguration.getPropertyName().equals("be.http.IncludeEventType")))) {
				
				final List<String> displyedValueList = propertyConfiguration.getDisplayedValues();
				final List<String> values = propertyConfiguration.getValues();

				final GvField gvField = GvUiUtil.createComboGv(parent, propertyConfiguration.getDefaultValue());
				gvField.setBackgroundColor(new Color(gvField.getMasterComposite().getDisplay(), 255, 255, 255));

				final Combo combo = (Combo) gvField.getField();
				combo.setItems(displyedValueList.toArray(new String[displyedValueList.size()]));
				combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				if (propertyConfiguration.getDefaultValue() != null) {
					combo.setText(propertyConfiguration.getDefaultValue());
				}
				controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);

				gvField.setFieldListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						SimpleProperty property = (SimpleProperty) combo.getData();
						if (property == null) {
							return;
						}

						String val = getRenderedValue(displyedValueList, values, combo.getText(), false);
						if (property.getValue() == null) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), val);
							EditorUtils.executeCommand(editor, command);
							return;
						}

						editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(val)) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), val);
							EditorUtils.executeCommand(editor, command);
						}

					}
				});
				gvField.setGvListener(new Listener() {
					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						// TODO Auto-generated method stub
						Object fieldData = gvField.getGvText().getData();
						if (fieldData instanceof SimpleProperty) {
							SimpleProperty property = (SimpleProperty) fieldData;
							if (property.getValue() == null) {
								Command command = new SetCommand(editor.getEditingDomain(), property,
										ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText());
								EditorUtils.executeCommand(editor, command);
								return;
							}
							editorModifiedOnReset(property, editor);
							if (!property.getValue().equalsIgnoreCase(gvField.getGvText().getText())) {
								Command command = new SetCommand(editor.getEditingDomain(), property,
										ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText().getText());
								EditorUtils.executeCommand(editor, command);
							}
						}

						validateField((Text)gvField.getGvText(), propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getDisplayName(),editor.getProject().getName());
					}
				});
				return gvField.getMasterComposite();

			} else if ((driverType.equals("ActiveSpaces") || driverType.equals("ActiveSpaces 3.x"))
					&& (propertyConfiguration.getPropertyName().equals("SecurityRole")
							|| propertyConfiguration.getPropertyName().equals("Credential")
							|| propertyConfiguration.getPropertyName().equals("Trust_Type")
							)) {
				final List<String> displyedValueList = propertyConfiguration.getDisplayedValues();
				final List<String> values = propertyConfiguration.getValues();

				final GvField gvField = createGvComboField(parent, propertyConfiguration.getDefaultValue(),editor,propertyConfiguration,driverType,controls);
				gvField.setBackgroundColor(new Color(gvField.getMasterComposite().getDisplay(), 255, 255, 255));

				final Combo combo = (Combo) gvField.getField();
				combo.setItems(displyedValueList.toArray(new String[displyedValueList.size()]));
				combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

				if (propertyConfiguration.getDefaultValue() != null) {
					combo.setText(propertyConfiguration.getDefaultValue());
				}
				controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);

				gvField.setFieldListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						SimpleProperty property = (SimpleProperty) combo.getData();
						if (property == null) {
							return;
						}

						String val = getRenderedValue(displyedValueList, values, combo.getText(), false);
						if (property.getValue() == null) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), val);
							EditorUtils.executeCommand(editor, command);
							return;
						}

						editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(val)) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), val);
							EditorUtils.executeCommand(editor, command);
						}

					}
				});
				gvField.setGvListener(new Listener() {
					@Override
					public void handleEvent(org.eclipse.swt.widgets.Event event) {
						// TODO Auto-generated method stub
						Object fieldData = gvField.getGvText().getData();
						if (fieldData instanceof SimpleProperty) {
							SimpleProperty property = (SimpleProperty) fieldData;
							if (GvUtil.isGlobalVar(property.getValue())) {
								String gvVal = GvUtil.getGvDefinedValue(editor.getProject(), property.getValue());
								if (!gvVal.isEmpty() && (propertyConfiguration.getPropertyName()
										.equalsIgnoreCase("SecurityRole")
										|| propertyConfiguration.getPropertyName().equalsIgnoreCase("Credential")
										|| propertyConfiguration.getPropertyName().equals("Trust_Type")
										)) {
									if(propertyConfiguration.getPropertyName().equals("Trust_Type")){
										updateAS3Security(controls, editor);
									}else{
										updateASChannelExtPropsSection(controls, editor);
									}
								}
							}
							if (property.getValue() == null) {
								Command command = new SetCommand(editor.getEditingDomain(), property,
										ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText());
								EditorUtils.executeCommand(editor, command);
								return;
							}
							editorModifiedOnReset(property, editor);
							if (!property.getValue().equalsIgnoreCase(gvField.getGvText().getText())) {
								Command command = new SetCommand(editor.getEditingDomain(), property,
										ModelPackage.eINSTANCE.getSimpleProperty_Value(), gvField.getGvText().getText());
								EditorUtils.executeCommand(editor, command);
							}
						}
					}
				});
				return gvField.getMasterComposite();

			} else {
				final Combo comboField = new Combo(parent, SWT.READ_ONLY);
				final List<String> displyedValueList = propertyConfiguration.getDisplayedValues();
				final List<String> values = propertyConfiguration.getValues();
				comboField.setItems(displyedValueList.toArray(new String[displyedValueList.size()]));
				comboField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				// The following default settings for Channel Configuration
				if (propertyConfiguration.getDefaultValue() != null) {
					comboField.setText(propertyConfiguration.getDefaultValue());
				}
				controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), comboField);
				comboField.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent e) {
						SimpleProperty property = (SimpleProperty) comboField.getData();
						if (property == null) {
							return;
						}
						
						//hard coded for jms destination
						if (!property.getName().isEmpty() && property.getName().equals("Queue")){
							updateJmsDestination(controls, driverType);
						}

						// hard coded for hawk channel driver properties
						if (!property.getName().isEmpty() && property.getName().equals("Transport")) {
							Control[] rvPropControls = new Control[3];
							Control[] emsPropControls = new Control[3];
							rvPropControls[0] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "RV_Service");
							rvPropControls[1] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "RV_Network");
							rvPropControls[2] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "RV_Daemon");

							emsPropControls[0] = (Control) controls
									.get(driverType + CommonIndexUtils.DOT + "EMS_ServerURL");
							emsPropControls[1] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "EMS_UserName");
							emsPropControls[2] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "EMS_Password");

							boolean isRV = "RendezVous".equals(comboField.getItem(comboField.getSelectionIndex()));
							for (int i = 0; i < 3; i++) {
								rvPropControls[i].setEnabled(isRV);
								emsPropControls[i].setEnabled(!isRV);
							}
						}

						if (!property.getName().isEmpty() && property.getName().equals("EnableSecurity")) {
							updateASChannelExtPropsSection(controls,editor);
						}
						if (!property.getName().isEmpty() && property.getName().equals("SecurityRole")) {
							updateASChannelExtPropsSection(controls,editor);
						}
						if (!property.getName().isEmpty() && property.getName().equals("Credential")) {
							updateASChannelExtPropsSection(controls, editor);
						}
						
						if (!property.getName().isEmpty() && property.getName().equals("Trust_Type")) {
							updateAS3Security(controls, editor);
						}

						// hard coded for hawk destination
						if (!property.getName().isEmpty() && property.getName().equals("MonitorType")) {
							Control subscriptionMethodURI = (Control)controls.get(driverType + CommonIndexUtils.DOT + "SubscriptionMethodURI");
							Control timeInterval = (Control)controls.get(driverType + CommonIndexUtils.DOT + "TimeInterval");
							Control arguments = (Control)controls.get(driverType + CommonIndexUtils.DOT + "Arguments");
							if ("MicroAgent Method Subscription".equals(comboField.getItem(comboField.getSelectionIndex()))) {
								subscriptionMethodURI.setEnabled(true);
								timeInterval.setEnabled(true);
								arguments.setEnabled(true);
							} else {
								subscriptionMethodURI.setEnabled(false);
								timeInterval.setEnabled(false);
								arguments.setEnabled(false);
							}
						}

						// hard coded for AS destination
						if (!property.getName().isEmpty() &&  property.getName().equals("ConsumptionMode")) {
							Control browserType = (Control)controls.get(driverType + CommonIndexUtils.DOT + "BrowserType");
							Control putEvent = (Control)controls.get(driverType + CommonIndexUtils.DOT + "PutEvent");
							Control takeEvent = (Control)controls.get(driverType + CommonIndexUtils.DOT + "TakeEvent");
							Control expireEvent = (Control)controls.get(driverType + CommonIndexUtils.DOT + "ExpireEvent");
							Control distributionScope = (Control)controls.get(driverType + CommonIndexUtils.DOT + "DistributionScope");
							Combo timescope = (Combo) controls.get(driverType + CommonIndexUtils.DOT + "TimeScope");
							Control prefetch = (Control)controls.get(driverType + CommonIndexUtils.DOT + "Prefetch");
							if ("EntryBrowser".equals(comboField.getItem(comboField.getSelectionIndex()))){
								browserType.setEnabled(true);
								distributionScope.setEnabled(true);
								putEvent.setEnabled(false);
								takeEvent.setEnabled(false);
								expireEvent.setEnabled(false);
								timescope.setEnabled(true);
								if(timescope.getItems().length==4){
									timescope.remove("NEW_EVENTS");
								}
								prefetch.setEnabled(true);
							} else if("EventListener".equals(comboField.getItem(comboField.getSelectionIndex()))){
								browserType.setEnabled(false);
								distributionScope.setEnabled(true);
								putEvent.setEnabled(true);
								takeEvent.setEnabled(true);
								expireEvent.setEnabled(true);
								timescope.setEnabled(true);
								if(timescope.getItems().length==3){
									timescope.add("NEW_EVENTS");
								}
								prefetch.setEnabled(false);
							} else {
								browserType.setEnabled(false);
								distributionScope.setEnabled(false);
								putEvent.setEnabled(false);
								takeEvent.setEnabled(false);
								expireEvent.setEnabled(false);
								timescope.setEnabled(false);
								prefetch.setEnabled(false);
							}
						}

						// hard coded for SB Channel
						if (!property.getName().isEmpty() &&  property.getName().equals("ClientType")) {
							Control predicateControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "Predicate");
							Button bufferingControl = (Button) controls.get(driverType + CommonIndexUtils.DOT + "EnableBuffering");
							Control bufferSizeControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "BufferSize");
							Control flushIntervalControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "FlushInterval");

							if ("Enqueuer".equals(comboField.getItem(comboField.getSelectionIndex()))) {
								bufferingControl.setEnabled(true);
								if (bufferingControl.getSelection()) {
									bufferSizeControl.setEnabled(true);
									flushIntervalControl.setEnabled(true);
								} else {
									bufferSizeControl.setEnabled(false);
									flushIntervalControl.setEnabled(false);
								}
								predicateControl.setEnabled(false);
							} else {
								bufferingControl.setEnabled(false);
								bufferSizeControl.setEnabled(false);
								flushIntervalControl.setEnabled(false);
								predicateControl.setEnabled(true);
							}
						}
						
						// handle http destination type
						if (!property.getName().isEmpty() &&  property.getName().equals("be.http.type")) {
							Combo destinationType = (Combo) controls.get(driverType + CommonIndexUtils.DOT + "be.http.type");
							updateHttpDestinationComponents(controls, driverType, destinationType.getText());
						}

						String val = getRenderedValue(displyedValueList, values, comboField.getText(), false);
						if (property.getValue() == null) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), val);
							EditorUtils.executeCommand(editor, command);
							return;
						}

						editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(val)) {
							Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
									.getSimpleProperty_Value(), val);
							EditorUtils.executeCommand(editor, command);
						}						
					}
				});

				return comboField;
			}

		} else {
			Hyperlink link = null;
			if (propertyConfiguration.getPropertyName().equalsIgnoreCase("be.http.pageFlowFunction")){
				link = StudioUIUtils.createLinkField(toolkit, parent, "Action RuleFunction");
				controls.put(driverType + CommonIndexUtils.DOT +".link."+"Action RuleFunction", link);
			}
			if(propertyConfiguration.isGvToggle() ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("TTL") || propertyConfiguration.getPropertyName().equalsIgnoreCase("SpaceName") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Filter") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("Subject") || propertyConfiguration.getPropertyName().equalsIgnoreCase("RVCM Pre Registration") || propertyConfiguration.getPropertyName().equalsIgnoreCase("MetaspaceName")||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("MemberName") || propertyConfiguration.getPropertyName().equalsIgnoreCase("DiscoveryUrl")||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("ListenUrl")|| propertyConfiguration.getPropertyName().equalsIgnoreCase("RemoteListenUrl")||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("IdentityPassword") || propertyConfiguration.getPropertyName().equalsIgnoreCase("PolicyFile") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("TokenFile") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Domain") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Username") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("Password") || propertyConfiguration.getPropertyName().equalsIgnoreCase("KeyFile") || propertyConfiguration.getPropertyName().equalsIgnoreCase("PrivateKey") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("Service") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Network") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Daemon") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("ProviderURL") || propertyConfiguration.getPropertyName().equalsIgnoreCase("IsTransacted") || propertyConfiguration.getPropertyName().equalsIgnoreCase("ClientID") || propertyConfiguration.getPropertyName().equalsIgnoreCase("StreamBaseServerURI") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("RealmServer") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Secondary") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("TrustStore") || propertyConfiguration.getPropertyName().equalsIgnoreCase("KeyStore") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("KeyStorePassword") || propertyConfiguration.getPropertyName().equalsIgnoreCase("KeyPassword") || propertyConfiguration.getPropertyName().equalsIgnoreCase("TrustStorePassword") ||
					propertyConfiguration.getPropertyName().equalsIgnoreCase("AppName") || propertyConfiguration.getPropertyName().equalsIgnoreCase("EndpointName") || propertyConfiguration.getPropertyName().equalsIgnoreCase("InstName") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Formats") || propertyConfiguration.getPropertyName().equalsIgnoreCase("DurableName") 
					|| propertyConfiguration.getPropertyName().equalsIgnoreCase("TableName") || propertyConfiguration.getPropertyName().equalsIgnoreCase("GridName") 
					){
				if(isPasswordProperty(propertyConfiguration.getPropertyName())){
					final GvField gvField = createGvPasswordTextField(parent,"",propertyConfiguration.getDefaultValue(),editor,propertyConfiguration);
					controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);
					return gvField;
				}
				final GvField gvField = createGvTextField(parent,"",propertyConfiguration.getDefaultValue(),editor,propertyConfiguration);
				controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), gvField);
				return gvField;
			}
			Text tempText = null;
			if (propertyConfiguration.getPropertyName().equalsIgnoreCase("Trust_File")) {
				Composite masterComp = new Composite(parent, SWT.NONE);
				GridData masterData = new GridData(GridData.FILL_HORIZONTAL);
				masterComp.setLayoutData(masterData);
				masterComp.setLayout(PanelUiUtil.getCompactGridLayout(2, false));
				tempText = toolkit.createText(masterComp, "", SWT.BORDER);
				GridData gd = new GridData();
				gd.minimumWidth = 500;
				gd.grabExcessHorizontalSpace = true;
				gd.horizontalAlignment = SWT.FILL;
				tempText.setLayoutData(gd);
				Button bIdentityBrowse = PanelUiUtil.createBrowsePushButton(masterComp, tempText);
				bIdentityBrowse.addListener(SWT.Selection, PanelUiUtil.getFileResourceSelectionListener(parent,
						editor.getProject(), new String[] { "id" }, tempText));
				controls.put(
						driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName() + "_" + "browse",
						bIdentityBrowse);
			} else {
				tempText = toolkit.createText(parent, "", propertyConfiguration.getPropertyName().contains("Password")
						? SWT.BORDER | SWT.PASSWORD : SWT.BORDER);
				tempText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			}
			final Text textField = tempText;
			controls.put(driverType + CommonIndexUtils.DOT + propertyConfiguration.getPropertyName(), textField);
			// The following default settings for Channel Configuration
			if (propertyConfiguration.getDefaultValue() != null)
				textField.setText(propertyConfiguration.getDefaultValue());
			textField.setData("PropertyConfigType", propertyConfiguration.getPropertyConfigType());
			if (propertyConfiguration.getDefaultValue() != null)
				textField.setData("DefaultValue", propertyConfiguration.getDefaultValue());
			textField.setToolTipText(textField.getText());

			if(propertyConfiguration.getPropertyName().equalsIgnoreCase("be.http.pageFlowFunction")){

				StudioUIUtils.addHyperLinkFieldListener(link, textField, editor , editor.getProject().getName(), false, false);
				Button browseButton = toolkit.createButton(parent, Messages.getString("Browse"), SWT.PUSH);
				browseButton.addSelectionListener(new SelectionAdapter(){
					public void widgetSelected(SelectionEvent e) {
						RuleFunctionSelector picker = new RuleFunctionSelector(Display.getDefault().getActiveShell(),editor.getProject().getName(), textField.getText().trim(), false);
						if (picker.open() == Dialog.OK) {
							textField.setText(picker.getFirstResult().toString());
						}

					}});
				controls.put(driverType + CommonIndexUtils.DOT + Messages.getString("Browse")+ Messages.getString("Property"), browseButton);
			}
			if(propertyConfiguration.getPropertyName().equalsIgnoreCase("Predicate")){
				// StreamBase filter predicate
				Button validateButton = toolkit.createButton(parent, Messages.getString("Validate"), SWT.PUSH);
				validateButton.addSelectionListener(new SelectionAdapter(){
					public void widgetSelected(SelectionEvent e) {
						try {
							if (textField.getText().trim().length() == 0) {
								MessageDialog.openInformation(editor.getSite().getShell(), "Validation Complete",
										"Empty filter expression.  No validation required.");
								return;
							}
							validateFilterPredicate(editor, textField.getText());
						} catch (Exception e1) {
							String message = Messages.getString("filter.expression.validation.error", textField.getText(), e1.getMessage());
							MessageDialog.openError(editor.getSite().getShell(), "Validation Error",
									message);
							EditorsUIPlugin.log(message, e1);
							return;
						}
						String message = Messages.getString("filter.expression.validation.success", textField.getText());
						MessageDialog.openInformation(editor.getSite().getShell(), "Validation Successful",
									message);
					}
					});
				controls.put(driverType + CommonIndexUtils.DOT + Messages.getString("Validate")+ Messages.getString("Property"), validateButton);
			}

			textField.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					textField.setToolTipText(textField.getText());
					textField.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					String type = textField.getData("PropertyConfigType").toString();
					Object data = textField.getData("DefaultValue");
					String deafultValue = (data != null) ? data.toString() : "";
					Object fieldData = textField.getData();
					if (fieldData instanceof SimpleProperty) {
						SimpleProperty property = (SimpleProperty) fieldData;
						if (!validatePropertyValue(type, textField, deafultValue/*
																				 * ,
																				 * propertyInstance
																				 */)) {
							textField.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
							String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString(
									"invalid.property.entry", textField.getText(), property.getName(), type);
							textField.setToolTipText(problemMessage);
							return;
						}
						if (property.getValue() == null) {
							Command command = new SetCommand(editor.getEditingDomain(), property,
									ModelPackage.eINSTANCE.getSimpleProperty_Value(), textField.getText());
							EditorUtils.executeCommand(editor, command);
							return;
						}
						editorModifiedOnReset(property, editor);
						if(propertyConfiguration.getPropertyName().contains("Password")){
							if (!property.getValue().equalsIgnoreCase(((Text)textField).getText())) {
								Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
										PasswordUtil.getEncodedString(((Text)textField).getText())) ;
								EditorUtils.executeCommand(editor, command);
							}
							validateField((Text)textField, propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getDisplayName(),editor.getProject().getName());
						}
						else if (propertyConfiguration.getPropertyName()
								.equalsIgnoreCase("be.http.pageFlowFunction")) {
							if (!property.getValue().equalsIgnoreCase(((Text) textField).getText())) {
								Command command = new SetCommand(editor.getEditingDomain(), property,
										ModelPackage.eINSTANCE.getSimpleProperty_Value(), textField.getText());
								EditorUtils.executeCommand(editor, command);
							}
							validRuleFunction(textField, editor.getProject().getName());
						} else if (!property.getValue().equalsIgnoreCase(textField.getText())) {
							Command command = new SetCommand(editor.getEditingDomain(), property,
									ModelPackage.eINSTANCE.getSimpleProperty_Value(), textField.getText());
							EditorUtils.executeCommand(editor, command);
						}
					}
				}
			});

			return textField;

		}
	}

	private void validateFilterPredicate(final ChannelFormEditor editor, String predicate) throws Exception {
		DriverConfig driver = editor.getChannel().getDriver();
		Map<String, String> props = new HashMap<>();
		if (driver.getConfigMethod() == CONFIG_METHOD.PROPERTIES) {
			EList<Entity> properties = driver.getProperties().getProperties();

			for (Entity entity : properties) {
				String val = ((SimpleProperty)entity).getValue();
				if (val == null) {
					val = "";
				}
				props.put(entity.getName(), val);
			}
		} else {
			// lookup referenced shared resource
			String reference = driver.getReference();
			IResource res = editor.getProject().findMember(new Path(reference));
			if (!res.exists()) {
				// show error
				MessageDialog.openError(editor.getSite().getShell(), "Validation failed",
									"Unable to connect to the StreamBase server. The referenced shared resource could not be found");
			}
			SAXParser newSAXParser = SAXParserFactory.newInstance().newSAXParser();
			SimpleXMLTagParser handler = new SimpleXMLTagParser();
			newSAXParser.parse(((IFile)res).getContents(), handler);
			props = handler.getTags();
		}

		String serverURI = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_SERVER_URI.getLocalName()));
		List<StreamBaseURI> uris = new ArrayList<StreamBaseURI>();
		String uname = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_AUTH_USERNAME.getLocalName()));
		String password = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_AUTH_PASSWORD.getLocalName()));
		if (password != null && password.startsWith("#!")) {
			password = new String(ObfuscationEngine.decrypt(password));
		}

		if (serverURI.indexOf(',') > 0) {
			// uri string contains multiple uris
			String[] serverURIs = serverURI.split(",");
			for (String sURI : serverURIs) {
				uris.add(new StreamBaseURI(sURI));
			}
		} else {
			uris.add(new StreamBaseURI(serverURI));
		}
		StreamBaseURI[] uriArr = new StreamBaseURI[uris.size()];
		uris.toArray(uriArr);
		uris.clear();
		String trustStore = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_TRUST_STORE.getLocalName()));
		String trustStorePass = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_TRUST_STORE_PASSWORD.getLocalName()));
		if (trustStorePass != null && trustStorePass.startsWith("#!")) {
			trustStorePass = new String(ObfuscationEngine.decrypt(trustStorePass));
		}
		String keyStore = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_KEY_STORE.getLocalName()));
		String keyStorePass = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_KEY_STORE_PASSWORD.getLocalName()));
		if (keyStorePass != null && keyStorePass.startsWith("#!")) {
			keyStorePass = new String(ObfuscationEngine.decrypt(keyStorePass));
		}
		String keyPass = GvUtil.getGvDefinedValue(editor.getProject(),props.get(SBConstants.CHANNEL_PROPERTY_KEY_PASSWORD.getLocalName()));
		if (keyPass != null && keyPass.startsWith("#!")) {
			keyPass = new String(ObfuscationEngine.decrypt(keyPass));
		}
		if (trustStore != null && trustStore.length() > 0) {
			System.setProperty("javax.net.ssl.trustStore", trustStore);
		}
		if (trustStorePass != null && trustStorePass.length() > 0) {
			System.setProperty("javax.net.ssl.trustStorePassword", trustStorePass);
		}
		boolean setUnameAndPass = uname != null && uname.trim().length() > 0 && password != null && password.trim().length() > 0;
		for (StreamBaseURI uri : uriArr) {
			Map<String, String> params = new HashMap<>();
			if (setUnameAndPass) {
				// only set uname/pass if both are specified
				params.put(StreamBaseURI.USER_PARAM, uname);
				params.put(StreamBaseURI.PASSWORD_PARAM, password);
			}
			if (keyStore != null && keyStore.length() > 0) {
				params.put(StreamBaseURI.KEYSTORE_PARAM, keyStore);
			}
			if (keyPass != null && keyPass.length() > 0) {
				params.put(StreamBaseURI.KEY_PASS_PARAM, keyPass);
			}
			if (keyStorePass != null && keyStorePass.length() > 0) {
				params.put(StreamBaseURI.KEYSTORE_PASS_PARAM, keyStorePass);
			}
			uris.add(new StreamBaseURI(uri.getHost(), uri.getContainer(), uri.getPort(), uri.isSSL(), params));
		}
		StreamBaseClient client = null;
		String streamName = "FraudDetected";
		StreamProperties streamProps = null;
		try {
			client = new StreamBaseClient(uris);
			streamProps = client.subscribe(streamName, null, predicate);
		} catch (StreamBaseException e) {
			throw e;
//			if (uname.trim().length() > 0) {
//				throw new StreamBaseException("Unable to connect to server '"+serverURI+"' with the specified credentials", e.getCause());
//			} else {
//				throw new StreamBaseException("Unable to connect to server '"+serverURI+"'", e.getCause());
//			}
		} finally {
			if (client != null) {
				if (streamProps != null && client.isSubscribed(streamProps)) {
					client.unsubscribe(streamProps);
				}
				client.close();
			}
		}
	}

	protected static boolean isPasswordProperty(final String propertyName) {
		return propertyName.equalsIgnoreCase("Password") || propertyName.equalsIgnoreCase("IdentityPassword") ||propertyName.equalsIgnoreCase("TrustStorePassword")
				|| propertyName.equalsIgnoreCase("KeyStorePassword") || propertyName.equalsIgnoreCase("KeyPassword");
	}

	/**
	 * @param displayValues
	 * @param values
	 * @param text
	 * @param isDisplay
	 * @return
	 */
	public static String getRenderedValue(List<String> displayValues, List<String> values, String text,
			boolean isDisplay) {
		int disIndex = displayValues.indexOf(text);
		int valIndex = values.indexOf(text);
		if (disIndex == -1 && valIndex == -1) return text;
		if (isDisplay) {
			return displayValues.get(valIndex);
		} else {
			return values.get(disIndex);
		}
	}

	/**
	 * @param type
	 * @param fieldName
	 * @param deafultValue
	 * @param propertyInstance
	 * @return
	 */
	private static boolean validatePropertyValue(String type, Text fieldName, String deafultValue/*
																								 * ,
																								 * PropertyInstance
																								 * propertyInstance
																								 */) {
		Object fieldData = fieldName.getData();
		if (type.intern() == PROPERTY_TYPES.INTEGER.getName().intern()) {
			if (!StudioUIUtils.isNumeric(fieldName.getText())) {
				// resetPropertyValues(fieldName, deafultValue,
				// propertyInstance.getValue());
				return false;
			}
		}
		if (type.intern() == PROPERTY_TYPES.DOUBLE.getName().intern()) {
			if (!isDouble(fieldName.getText())) {
				// resetPropertyValues(fieldName, deafultValue,
				// propertyInstance.getValue());
				return false;
			}
		}
		if (type.intern() == PROPERTY_TYPES.LONG.getName().intern()) {
			if (!isLong(fieldName.getText())) {
				// resetPropertyValues(fieldName, deafultValue,
				// propertyInstance.getValue());
				return false;
			}else if(fieldData instanceof SimpleProperty){//temporary fix for BE-25381
				SimpleProperty property = (SimpleProperty) fieldData;
				if((property.getName().equalsIgnoreCase("BufferSize") || property.getName().equalsIgnoreCase("FlushInterval")) && !fieldName.getText().equals("") && Long.parseLong(fieldName.getText()) < 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * @param fieldName
	 * @param defultValue
	 * @param value
	 */
	@SuppressWarnings("unused")
	private static void resetPropertyValues(Text fieldName, String defultValue, String value) {
		if (value == null) {
			fieldName.setText(defultValue);
		} else {
			fieldName.setText(value);
		}
	}

	/**
	 * This is for handling switch of destinations as well as Channel Property
	 * Configurations
	 *
	 * @param propertyInstance
	 * @param editor
	 */
	public static void editorModifiedOnReset(SimpleProperty propertyInstance, ChannelFormEditor editor) {

		if (propertyInstance.eContainer() instanceof DriverConfig && !editor.isChannelConfigurationReset()) {
			editor.modified();
		}
		if (propertyInstance.eContainer().eContainer() instanceof Destination && !editor.isDestinationReset()) {
			editor.modified();
		}
	}

	/**
	 * @param instance
	 * @param controls
	 */
	public static void createPropertyInstanceWidgets(PropertyMap propMap, String driverType, HashMap<Object, Object> controls) {
		EList<Entity> properties = propMap.getProperties();
		for (int i = 0; i < properties.size(); i++) {
			Entity entity = properties.get(i);
			String propName = entity.getName();
			if (controls.keySet().contains(driverType + CommonIndexUtils.DOT + propName)) {
				if (entity instanceof SimpleProperty) {
					if(isPasswordProperty(propName)){
						if(PasswordUtil.isEncodedString(((SimpleProperty) entity).getValue())){
							((SimpleProperty) entity).setValue(PasswordUtil.getDecodedString(((SimpleProperty) entity).getValue()));
						}
					}
					if(controls.get(driverType + CommonIndexUtils.DOT + propName) instanceof GvField){
						((Control) ((GvField)controls.get(driverType + CommonIndexUtils.DOT + propName)).getField()).setData((SimpleProperty)entity);
						((Control) ((GvField)controls.get(driverType + CommonIndexUtils.DOT + propName)).getGvText()).setData((SimpleProperty)entity);
					}
					else{
						((Control) controls.get(driverType + CommonIndexUtils.DOT + propName)).setData((SimpleProperty) entity);
					}
				}
			}
		}
		for (int i = 0; i < properties.size(); i++) {
			Entity entity = properties.get(i);
			String propName = entity.getName();
			if (entity instanceof SimpleProperty) {

				if(controls.get(driverType + CommonIndexUtils.DOT + propName) instanceof GvField){
					setIntialDataForRenderedComponents((Control)((GvField) controls.get(driverType + CommonIndexUtils.DOT + propName)).getField(), ((SimpleProperty)entity).getValue(), null,
							null, null, null);
					setIntialDataForRenderedComponents((Control)((GvField) controls.get(driverType + CommonIndexUtils.DOT + propName)).getGvText(), ((SimpleProperty)entity).getValue(), null,
							null, null, null);
					String value = ((SimpleProperty) entity).getValue();
					((GvField)(controls.get(driverType + CommonIndexUtils.DOT + propName))).setValueWithoutInit(value);
					((GvField)(controls.get(driverType + CommonIndexUtils.DOT + propName))).initializeGvToggleButtonUI();
				}
				else{
					setIntialDataForRenderedComponents((Control) controls.get(driverType + CommonIndexUtils.DOT + propName),
							((SimpleProperty) entity).getValue(), null, null, null, null);
				}

			}
		}

		// hard coded for hawk channel driver properties initialization
		if(driverType.equals("Hawk")){
			Control[] rvPropControls = new Control[3];
			Control[] emsPropControls = new Control[3];
			rvPropControls[0] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "RV_Service");
			rvPropControls[1] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "RV_Network");
			rvPropControls[2] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "RV_Daemon");

			emsPropControls[0] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "EMS_ServerURL");
			emsPropControls[1] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "EMS_UserName");
			emsPropControls[2] = (Control) controls.get(driverType + CommonIndexUtils.DOT + "EMS_Password");

			Combo comboField = (Combo) controls.get(driverType + CommonIndexUtils.DOT + "Transport");
			boolean isRV = "RendezVous".equals(comboField.getItem(comboField.getSelectionIndex()));
			for (int i = 0; i < 3; i++) {
				rvPropControls[i].setEnabled(isRV);
				emsPropControls[i].setEnabled(!isRV);
			}
		}

		// hard coded for AS channel driver properties initialization
		if(driverType.equals("ActiveSpaces")){
//		  /  updateASChannelExtPropsSection(controls,editor);
		}

		// for(Object propConfig:instance.getProperties()){
		// PropertyInstance propInstance = (PropertyInstance)propConfig;
		// if(controls.keySet().contains(propInstance.getDefinitionName())){
		// ((Control)controls.get(propInstance.getDefinitionName())).setData(propInstance);
		// }
		// }
		// for(Object propConfig:instance.getProperties()){
		// PropertyInstance propInstance = (PropertyInstance)propConfig;
		// setIntialDataForRenderedComponents((Control)controls.get(propInstance.getDefinitionName()),
		// propInstance.getValue(), null, null,null,null);
		// }

		for (int i = 0; i < properties.size(); i++) {
			Entity entity = properties.get(i);
			String propName = entity.getName();
			if (controls.keySet().contains(driverType + CommonIndexUtils.DOT + propName)) {
				if (entity instanceof SimpleProperty) {
					if(isPasswordProperty(propName)){
						String val = ((SimpleProperty) entity).getValue();
						if(val != null && val.length() > 0 && !PasswordUtil.isEncodedString(val)){
							((SimpleProperty) entity).setValue(PasswordUtil.getEncodedString(((SimpleProperty) entity).getValue()));
						}
					}
				}
			}
		}
	}

	public static void updateAS3Security(HashMap<Object, Object> controls, ChannelFormEditor editor) {
		String keyPrefix = "ActiveSpaces 3.x" + CommonIndexUtils.DOT;
		String selection = null;
		if ((((GvField) controls.get(keyPrefix + "useSsl"))).isGvMode()) {
			String gvVal = (((GvField) controls.get(keyPrefix + "useSsl"))).getGvText().getText();
			selection = GvUtil.getGvDefinedValue(editor.getProject(), gvVal);
		} else {
			Button enableSecurity = (Button) (((GvField) controls.get(keyPrefix + "useSsl")).getField());
			selection = enableSecurity.getSelection() + "";
		}
		String trustTypeVal = null;
		Combo trustType = (Combo) (((GvField) controls.get(keyPrefix + "Trust_Type")).getField());
		if ((((GvField) controls.get(keyPrefix + "Trust_Type"))).isGvMode()) {
			String gvVal = (((GvField) controls.get(keyPrefix + "Trust_Type"))).getGvText().getText();
			trustTypeVal = GvUtil.getGvDefinedValue(editor.getProject(), gvVal);
		} else {
			trustTypeVal = trustType.getItem(trustType.getSelectionIndex());
		}
		GvField trustTypeGvButton = (GvField) controls.get(keyPrefix + "Trust_Type");
		Control trustFile = (Control) controls.get(keyPrefix + "Trust_File");
		Control identityBrowseButton =(Control) controls.get(keyPrefix + "Trust_File" + "_" + "browse");

		if (Boolean.parseBoolean(selection)) {
			trustType.setEnabled(true);
			trustTypeGvButton.setEnabled(true);
			if ("TrustFile".equals(trustTypeVal)) {
				trustFile.setEnabled(true);
				identityBrowseButton.setEnabled(true);
			} else {
				trustFile.setEnabled(false);
				identityBrowseButton.setEnabled(false);
			}
		} else {
			trustType.setEnabled(false);
			trustTypeGvButton.setEnabled(false);
			trustFile.setEnabled(false);
			identityBrowseButton.setEnabled(false);
		}
	}

	public static void updateASChannelExtPropsSection(HashMap<Object, Object> controls, ChannelFormEditor editor) {
	    String keyPrefix = "ActiveSpaces" + CommonIndexUtils.DOT;
	    String selection;
	    if((((GvField) controls.get(keyPrefix + "EnableSecurity"))).isGvMode()){
	    	String gvVal=(((GvField) controls.get(keyPrefix + "EnableSecurity"))).getGvText().getText();
	    	selection=GvUtil.getGvDefinedValue(editor.getProject(),gvVal);
	    }else{
	    	Button enableSecurity = (Button)(((GvField) controls.get(keyPrefix + "EnableSecurity")).getField());
	    	selection = enableSecurity.getSelection()+"";
	    }

	    String securityRoleVal;
	    Combo securityrole = (Combo)(((GvField) controls.get(keyPrefix + "SecurityRole")).getField());
	    if((((GvField) controls.get(keyPrefix + "SecurityRole"))).isGvMode()){
	    	String gvVal=(((GvField) controls.get(keyPrefix + "SecurityRole"))).getGvText().getText();
	    	securityRoleVal=GvUtil.getGvDefinedValue(editor.getProject(),gvVal);
	    }else{
	    	securityRoleVal = securityrole.getItem(securityrole.getSelectionIndex());
	    }

	    String credentialVal;
	    Combo credential = (Combo)(((GvField) controls.get(keyPrefix + "Credential")).getField());
	    if((((GvField) controls.get(keyPrefix + "Credential"))).isGvMode()){
	    	String gvVal=(((GvField) controls.get(keyPrefix + "Credential"))).getGvText().getText();
	    	credentialVal=GvUtil.getGvDefinedValue(editor.getProject(),gvVal);
	    }else{
	    	credentialVal = credential.getItem(credential.getSelectionIndex());
	    }


        Button securityroleGvButton = (Button)(((GvField) controls.get(keyPrefix + "SecurityRole")).getGvToggleButton());
        Control securityroleGvText = (Text)(((GvField) controls.get(keyPrefix + "SecurityRole")).getGvText());
        Button credentialGvButton = (Button)(((GvField) controls.get(keyPrefix + "Credential")).getGvToggleButton());
        Control credentialGvText = (Text)(((GvField) controls.get(keyPrefix + "Credential")).getGvText());

        Control policyfile = (Control)(((GvField) controls.get(keyPrefix + "PolicyFile")).getField());
        Button policyFileGvButton = (Button)(((GvField) controls.get(keyPrefix + "PolicyFile")).getGvToggleButton());
        Control tokenfile = (Control)(((GvField) controls.get(keyPrefix + "TokenFile")).getField());
        Button tokenFileGvButton = (Button)(((GvField) controls.get(keyPrefix + "TokenFile")).getGvToggleButton());
        Control identityPassword = (Control)(((GvField) controls.get(keyPrefix + "IdentityPassword")).getField());
        Button identityPasswordGvButton = (Button)(((GvField) controls.get(keyPrefix + "IdentityPassword")).getGvToggleButton());
        Control domain = (Control)(((GvField) controls.get(keyPrefix + "Domain")).getField());
        Button domainGvButton = (Button)(((GvField) controls.get(keyPrefix + "Domain")).getGvToggleButton());
        Control username = (Control)(((GvField) controls.get(keyPrefix + "UserName")).getField());
        Button userNameGvButton = (Button)(((GvField) controls.get(keyPrefix + "UserName")).getGvToggleButton());
        Control password = (Control)(((GvField) controls.get(keyPrefix + "Password")).getField());
        Button passwordGvButton = (Button)(((GvField) controls.get(keyPrefix + "Password")).getGvToggleButton());
        Control keyfile = (Control)(((GvField) controls.get(keyPrefix + "KeyFile")).getField());
        Button keyFileGvButton = (Button)(((GvField) controls.get(keyPrefix + "KeyFile")).getGvToggleButton());
        Control privatekey = (Control)(((GvField) controls.get(keyPrefix + "PrivateKey")).getField());
        Button privateKeyGvButton = (Button)(((GvField) controls.get(keyPrefix + "PrivateKey")).getGvToggleButton());

       /* Control policyfile = (Control)controls.get(keyPrefix + "PolicyFile");
        Control tokenfile = (Control)controls.get(keyPrefix + "TokenFile");
        Control identityPassword = (Control)controls.get(keyPrefix + "IdentityPassword");
        Control domain = (Control)controls.get(keyPrefix + "Domain");
        Control username = (Control)controls.get(keyPrefix + "UserName");
        Control password = (Control)controls.get(keyPrefix + "Password");
        Control keyfile = (Control)controls.get(keyPrefix + "KeyFile");
        Control privatekey = (Control)controls.get(keyPrefix + "PrivateKey");
        */
        if (Boolean.parseBoolean(selection)) {
            if ("Requester".equals(securityRoleVal)) {
                securityrole.setEnabled(true);
                securityroleGvButton.setEnabled(true);
                securityroleGvText.setEnabled(true);
                policyfile.setEnabled(false);
                policyFileGvButton.setEnabled(false);
                tokenfile.setEnabled(true);
                tokenFileGvButton.setEnabled(true);
                identityPassword.setEnabled(true);
                identityPasswordGvButton.setEnabled(true);
                credential.setEnabled(true);
                credentialGvButton.setEnabled(true);
                credentialGvText.setEnabled(true);
                if ("USERPWD".equals(credentialVal)) {
                    domain.setEnabled(true);
                    domainGvButton.setEnabled(true);
                    username.setEnabled(true);
                    userNameGvButton.setEnabled(true);
                    password.setEnabled(true);
                    passwordGvButton.setEnabled(true);
                    keyfile.setEnabled(false);
                    keyFileGvButton.setEnabled(false);
                    privatekey.setEnabled(false);
                    privateKeyGvButton.setEnabled(false);
                } else {
                    domain.setEnabled(false);
                    domainGvButton.setEnabled(false);
                    username.setEnabled(false);
                    userNameGvButton.setEnabled(false);
                    password.setEnabled(false);
                    passwordGvButton.setEnabled(false);
                    keyfile.setEnabled(true);
                    keyFileGvButton.setEnabled(true);
                    privatekey.setEnabled(true);
                    privateKeyGvButton.setEnabled(true);
                }
            } else {
                securityrole.setEnabled(true);
                securityroleGvButton.setEnabled(true);
                securityroleGvText.setEnabled(true);
                policyfile.setEnabled(true);
                policyFileGvButton.setEnabled(true);
                tokenfile.setEnabled(false);
                tokenFileGvButton.setEnabled(false);
                identityPassword.setEnabled(true);
                identityPasswordGvButton.setEnabled(true);
                credential.setEnabled(false);
                credentialGvButton.setEnabled(false);
                credentialGvText.setEnabled(false);
                domain.setEnabled(false);
                domainGvButton.setEnabled(false);
                username.setEnabled(false);
                userNameGvButton.setEnabled(false);
                password.setEnabled(false);
                passwordGvButton.setEnabled(false);
                keyfile.setEnabled(false);
                keyFileGvButton.setEnabled(false);
                privatekey.setEnabled(false);
                privateKeyGvButton.setEnabled(false);
                tokenfile.setEnabled(false);
                tokenFileGvButton.setEnabled(false);
            }

        } else {
            securityrole.setEnabled(false);
            securityroleGvButton.setEnabled(false);
            securityroleGvText.setEnabled(false);
            policyfile.setEnabled(false);
            policyFileGvButton.setEnabled(false);
            tokenfile.setEnabled(false);
            tokenFileGvButton.setEnabled(false);
            credential.setEnabled(false);
            credentialGvButton.setEnabled(false);
            credentialGvText.setEnabled(false);
            identityPassword.setEnabled(false);
            identityPasswordGvButton.setEnabled(false);
            domain.setEnabled(false);
            domainGvButton.setEnabled(false);
            username.setEnabled(false);
            userNameGvButton.setEnabled(false);
            password.setEnabled(false);
            passwordGvButton.setEnabled(false);
            keyfile.setEnabled(false);
            keyFileGvButton.setEnabled(false);
            privatekey.setEnabled(false);
            privateKeyGvButton.setEnabled(false);
            tokenfile.setEnabled(false);
            tokenFileGvButton.setEnabled(false);
        }
    }
	
	private static void updateJmsDestination(HashMap<Object, Object> controls, String driverType) {
		Button isJSONPayload = (Button) controls.get(driverType + CommonIndexUtils.DOT + "IsJSONPayload");
		if (isJSONPayload == null) {
			return;
		}
		
		Control serializerDeserializerControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer"));
		String serializerDeserializerType = serializerDeserializerControl.toString();
		if (serializerDeserializerType != null 
				&& !serializerDeserializerType.isEmpty() 
				&& (serializerDeserializerType.contains("TextMessageSerializer") || serializerDeserializerType.contains("BytesMessageSerializer"))) {
			isJSONPayload.setEnabled(true);
		} else {
			isJSONPayload.setSelection(false);
			isJSONPayload.setEnabled(false);
		}
	}
	
	private static void updateHttpDestinationComponents(HashMap<Object, Object> controls, String driverType, String destinationType) {
		Control contextPathControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "be.http.contextPath");
		Control pageFlowFunctionControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + "be.http.pageFlowFunction");
		Control serializerDeserializerControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.SerializerDeserializer"));
		Control defaultEventControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("channel.destination.details.DefaultEvent"));
		Control isJSONPayload = (Control) controls.get(driverType + CommonIndexUtils.DOT + "be.http.jsonPayload");
		
		GvField includeEventTypeControl = null;
		if (driverType.equalsIgnoreCase(HTTP)) {
			includeEventTypeControl = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "be.http.IncludeEventType");
		}
		
		Control browseControl = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("Browse"));
		Control browseControlProperty = (Control) controls.get(driverType + CommonIndexUtils.DOT + Messages.getString("Browse")+Messages.getString("Property"));
		if (destinationType.equals("PageFlow")) {
			contextPathControl.setEnabled(true);
			pageFlowFunctionControl.setEnabled(true);
			serializerDeserializerControl.setEnabled(false);
			defaultEventControl.setEnabled(false);
			browseControl.setEnabled(false);
			browseControlProperty.setEnabled(true);
			if (includeEventTypeControl != null) {
				includeEventTypeControl.getField().setEnabled(false);
			}
			isJSONPayload.setEnabled(false);
		
		} else {
			contextPathControl.setEnabled(false);
			pageFlowFunctionControl.setEnabled(false);
			serializerDeserializerControl.setEnabled(true);
			defaultEventControl.setEnabled(true);
			browseControl.setEnabled(true);
			browseControlProperty.setEnabled(false);
			if (includeEventTypeControl != null) {
				includeEventTypeControl.getField().setEnabled(true);
			}
			
			String serializerDeserializerType = serializerDeserializerControl.toString();
			if (serializerDeserializerType != null && !serializerDeserializerType.isEmpty() && serializerDeserializerType.contains("SOAPMessageSerializer")) {
				if (isJSONPayload != null) {
					isJSONPayload.setEnabled(false);
				}
			} else {
				isJSONPayload.setEnabled(true);
			}
			
			Combo serializerDeserializerCombo = (Combo) serializerDeserializerControl;
			if (destinationType.equalsIgnoreCase("WebSocket")) {					
				serializerDeserializerCombo.setText("com.tibco.cep.driver.http.serializer.WebSocketMessageSerializer");
			} else if (serializerDeserializerCombo.getText().contains("WebSocketMessageSerializer")) {
				serializerDeserializerCombo.setText("com.tibco.cep.driver.http.serializer.RESTMessageSerializer");
			}
		}
	}

	/**
	 * @param driver
	 * @param delegate
	 * @param driverType
	 */
	public static void createExtendedConfiguration(DriverConfig driver, ChannelFormFeederDelegate delegate,
			String driverType) {
		ExtendedConfiguration extdConfig = ChannelFactory.eINSTANCE.createExtendedConfiguration();
		for (PropertyConfiguration propConfig : delegate.getExtendedConfiguration(driverType)) {
			SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(propConfig.getPropertyName());
			property.setValue(propConfig.getDefaultValue());
			extdConfig.getProperties().add(property);
		}
		driver.setExtendedConfiguration(extdConfig);
	}

	/**
	 * @param extdPropertyConfig
	 * @param controls
	 */
	public static void createExtendedPropertyInstanceWidgets(ExtendedConfiguration extdPropertyConfig, String driverType,
			HashMap<Object, Object> controls) {
		// for Simple properties
		for (SimpleProperty property : extdPropertyConfig.getProperties()) {
			if (controls.keySet().contains(driverType + CommonIndexUtils.DOT + property.getName())) {
				if(controls.get(driverType + CommonIndexUtils.DOT + property.getName()) instanceof GvField){
					((Control) ((GvField)controls.get(driverType + CommonIndexUtils.DOT + property.getName())).getField()).setData(property);
					((Control) ((GvField)controls.get(driverType + CommonIndexUtils.DOT + property.getName())).getGvText()).setData(property);
				}
				else{
					((Control) controls.get(driverType + CommonIndexUtils.DOT + property.getName())).setData(property);
				}
			}
		}
		for (SimpleProperty property : extdPropertyConfig.getProperties()) {

			if(controls.get(driverType + CommonIndexUtils.DOT + property.getName()) instanceof GvField){
				setIntialDataForRenderedComponents((Control)((GvField) controls.get(driverType + CommonIndexUtils.DOT + property.getName())).getField(), property.getValue(), null,
						null, null, null);
				setIntialDataForRenderedComponents((Control)((GvField) controls.get(driverType + CommonIndexUtils.DOT + property.getName())).getGvText(), property.getValue(), null,
						null, null, null);
			}
			else{
				setIntialDataForRenderedComponents((Control) controls.get(driverType + CommonIndexUtils.DOT + property.getName()), property.getValue(), null,
					null, null, null);
			}
		}
	}

	/**
	 * @param properties
	 * @param controls
	 */
	public static void createExtendedPropertyInstanceWidgets(List<SimpleProperty> properties, String driverType,
			HashMap<Object, Object> controls) {
		for (SimpleProperty property : properties) {
			if (controls.keySet().contains(property.getName())) {
				((Control) controls.get(driverType + CommonIndexUtils.DOT + property.getName())).setData(property);
			}
		}
		for (SimpleProperty property : properties) {
			setIntialDataForRenderedComponents((Control) controls.get(driverType + CommonIndexUtils.DOT + property.getName()), property.getValue(), null,
					null, null, null);
		}
	}

	/**
	 * @param delegate
	 * @param driverType
	 * @return
	 */
	public static PropertyMap getInstanceDriverType(ChannelFormFeederDelegate delegate, String driverType) {
		// Instance instance = ElementFactory.eINSTANCE.createInstance();
		PropertyMap properties = ModelFactory.eINSTANCE.createPropertyMap();
		List<PropertyConfiguration> driverProperties = delegate.getDriverProperties(driverType);
		for (PropertyConfiguration propConfig : driverProperties) {
			if (!isExtendedPropertiesPresent(propConfig.getPropertyName(), driverType, delegate)
					&& propConfig.getPropertyName() != null
					&& !propConfig.getPropertyName().trim().equalsIgnoreCase("")) {
				addInstanceProperty(properties, propConfig);
			}
		}
		backwardCompatibility(properties);
		return properties;
	}

	public static void backwardCompatibility(PropertyMap propertyMap) {
	    if (propertyMap != null) {
            for (Entity entity: propertyMap.getProperties()) {
                if ("SecurityRole".equals(entity.getName())
                        && ((SimpleProperty) entity).getValue() != null
                        && "Requestor".equals(((SimpleProperty) entity).getValue())) {
                    ((SimpleProperty) entity).setValue("Requester");
                    break; // for AS Channel Security Role property only.
                }
            }
	    }
    }

    /**
	 * @param propertyName
	 * @param driverType
	 * @param delegate
	 * @return
	 */
	public static boolean isExtendedPropertiesPresent(String propertyName, String driverType,
			ChannelFormFeederDelegate delegate) {
		for (PropertyConfiguration config : delegate.getExtendedConfiguration(driverType)) {
			if (config.getPropertyName().equalsIgnoreCase(propertyName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOnlyExtendedPropertiesPresent(String driverType, ChannelFormFeederDelegate delegate) {
		List<PropertyConfiguration> propertyConfigList = new ArrayList<PropertyConfiguration>();
		List<PropertyConfiguration> extendedConfigList = new ArrayList<PropertyConfiguration>();
		List<PropertyConfiguration> driverProperties = delegate.getDriverProperties(driverType);
		for (PropertyConfiguration propConfig : driverProperties) {
			if (propConfig.getPropertyName() != null
					&& !propConfig.getPropertyName().trim().equalsIgnoreCase("")) {
				for (PropertyConfiguration config : delegate.getExtendedConfiguration(driverType)) {
					if (config.getPropertyName().equalsIgnoreCase(propConfig.getPropertyName())) {
						extendedConfigList.add(config);
					} else {
						propertyConfigList.add(config);
					}
				}
			}
		}
		if (propertyConfigList.size() == 0 && extendedConfigList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param control
	 * @param value
	 * @param editingDomain
	 * @param owner
	 * @param feature
	 * @param defaultValue
	 */
	public static void setIntialDataForRenderedComponents(Control control, String value, EditingDomain editingDomain,
			EObject owner, EStructuralFeature feature, String defaultValue) {
		if (control instanceof Text) {
			((Text) control).setText(value != null ? value : "");
		}
		if (control instanceof Combo) {
			if (value != null) {
				((Combo) control).setText(value);
			} else {
				Command command = new SetCommand(editingDomain, owner, feature, defaultValue);
				editingDomain.getCommandStack().execute(command);
			}
		}
		if (control instanceof Button) {
			if (value != null && !value.equalsIgnoreCase("")) {
				((Button) control).setSelection(new Boolean(value).booleanValue());
			} else {
				Command command = new SetCommand(editingDomain, owner, feature, defaultValue);
				editingDomain.getCommandStack().execute(command);
			}
		}
	}

	/**
	 * @param instance
	 * @param propConfig
	 */
	public static void addInstanceProperty(PropertyMap properties, PropertyConfiguration propConfig) {
		SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
		property.setName(propConfig.getPropertyName());
		property.setValue(propConfig.getDefaultValue());
		properties.getProperties().add(property);
		property.setMandatory(propConfig.isMandatory());
		// PropertyInstance propInstance =
		// ElementFactory.eINSTANCE.createPropertyInstance();
		// propInstance.setDefinitionName(propConfig.getPropertyName());
		// propInstance.setValue(propConfig.getDefaultValue());
		// instance.getProperties().add(propInstance);
	}

	/**
	 * @param propMap
	 * @param map
	 */
	public static void getExtendedProperties(PropertyMap propMap, Map<Object, Object> map) {
		for (Entity entity : propMap.getProperties()) {
			if (entity instanceof ObjectProperty) {
				ObjectProperty property = (ObjectProperty) entity;
				if (property.getValue() != null && property.getValue() instanceof PropertyMap) {
					Map<Object, Object> subMap = new HashMap<Object, Object>();
					getExtendedProperties((PropertyMap) property.getValue(), subMap);
					map.put(property.getName(), subMap);
				}
			}
			if (entity instanceof SimpleProperty) {
				SimpleProperty property = (SimpleProperty) entity;
				map.put(property.getName(), property.getValue());
			}
		}
	}

	/**
	 * @param propMap
	 * @param map
	 */
	public static void getExtendedPropertiesForPayloadTree(PropertyMap propMap, List list) {
		for (Entity entity : propMap.getProperties()) {
			if (entity instanceof ObjectProperty) {
				ObjectProperty property = (ObjectProperty) entity;
				if (property.getValue() != null && property.getValue() instanceof PropertyMap) {
					Map<Object, Object> subMap = new HashMap<Object, Object>();
					getExtendedProperties((PropertyMap) property.getValue(), subMap);
					list.add(property.getName());
				}
			}
			if (entity instanceof SimpleProperty) {
				SimpleProperty property = (SimpleProperty) entity;
				list.add(property.getName());
			}
		}
	}


	/**
	 * @param propertyMap
	 * @param map
	 */
	public static void populateModelExtendedProperties(PropertyMap propertyMap, Map<Object, Object> map) {
		for (Object propKey : map.keySet()) {
			String key = (String) propKey;
			Object val = map.get(key);
			Entity extendedProperty = createProperty(key, val);
			propertyMap.getProperties().add(extendedProperty);
		}
	}

	/**
	 * @param key
	 * @param val
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Entity createProperty(String key, Object val) {
		if (val instanceof String) {
			SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
			simpleProperty.setName(key);
			simpleProperty.setValue((String) val);
			return simpleProperty;
		} else if (val instanceof Map) {
			Map propMap = (Map) val;
			PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();

			for (Object prop : propMap.keySet()) {
				String propKey = (String) prop;
				Object propObj = propMap.get(propKey);
				Entity subProperty = createProperty(propKey, propObj);
				propertyMap.getProperties().add(subProperty);
			}

			ObjectProperty objProperty = ModelFactory.eINSTANCE.createObjectProperty();
			objProperty.setName(key);
			objProperty.setValue(propertyMap);

			return objProperty;
		} else {
			SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
			simpleProperty.setName(key);
			simpleProperty.setValue(String.valueOf(val));
			return simpleProperty;
		}
	}

	/**
	 * @param entity
	 * @param newMap
	 * @param editor
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void persistExtendedProperties(final Entity entity, final Map newMap,
			final AbstractSaveableEntityEditorPart editor) {
		Display.getDefault().asyncExec(new Runnable() {

			/*
			 * (non-Javadoc)
			 *
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				try {
					if (persistExtendedProperties(entity, newMap)) {
						editor.modified();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @param entity
	 * @param newMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean persistExtendedProperties(final Entity entity, final Map newMap) {
		try {
			PropertyMap propMap = entity.getExtendedProperties();
			Map exmap = new HashMap();
			getExtendedProperties(propMap, exmap);
			Map newexmap = new HashMap();
			PropertyMap extendedPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			EditorUtils.populateModelExtendedProperties(extendedPropertyMap, newMap);
			getExtendedProperties(extendedPropertyMap, newexmap);
			if (!newMap.values().equals(exmap.values())) {
				entity.getExtendedProperties().getProperties().clear();
				entity.setExtendedProperties(extendedPropertyMap);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

//	/**
//	 * @param propMap
//	 * @param panel
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static void setExtendedPropertiesPanelData(PropertyMap propMap, final ExtendedPropertiesPanel panel) {
//		final Map map = new HashMap<Object, Object>();
//		getExtendedProperties(propMap, map);
////		SwingUtilities.invokeLater(new Runnable() {
////
////			@Override
////			public void run() {
////				panel.setExtendedProperties(map);
////			}
////		});
//
//	}
//
//	/**
//	 * @param propdef
//	 * @param isPropertyExtendedCheck
//	 * @param exPanel
//	 */
//	public static void setExtendePropertiesForPropertyDefinitions(PropertyDefinition propdef,
//			boolean isPropertyExtendedCheck, ExtendedPropertiesPanel exPanel) {
//		try {
//			if (isPropertyExtendedCheck) {
////				exPanel.setEntity(propdef);
//				if (propdef.getExtendedProperties() != null) {
//					setExtendedPropertiesPanelData(propdef.getExtendedProperties(), exPanel);
//				} else {
//					propdef.setExtendedProperties(getDefaultPropertiesMap());
//					setExtendedPropertiesPanelData(propdef.getExtendedProperties(), exPanel);
//				}
//			}
//		} catch (Exception e) {
//			// e.printStackTrace();
//		}
//	}

	/**
	 * @return
	 */
	public static PropertyMap getDefaultPropertiesMap() {
		// Default Property Map Creation
		PropertyMap map = ModelFactory.eINSTANCE.createPropertyMap();

		// ObjectProperty backingStoreObjProperty =
		// ModelFactory.eINSTANCE.createObjectProperty();
		// backingStoreObjProperty.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE);
		// SimpleProperty property = null;
		// {
		// PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
		//
		// property = ModelFactory.eINSTANCE.createSimpleProperty();
		// property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME);
		// property.setValue("");
		// propertyMap.getProperties().add(property);
		//
		// // property = ModelFactory.eINSTANCE.createSimpleProperty();
		// // property.setName("Nested Table Name");
		// // property.setValue("");
		// // propertyMap.getProperties().add(property);
		//
		// property = ModelFactory.eINSTANCE.createSimpleProperty();
		// property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE_MAXLENGTH);
		// property.setValue("");
		// propertyMap.getProperties().add(property);
		//
		// backingStoreObjProperty.setValue(propertyMap);
		// }
		//
		// map.getProperties().add(backingStoreObjProperty);

		// property = ModelFactory.eINSTANCE.createSimpleProperty();
		// property.setName("index");
		// property.setValue("false");
		// map.getProperties().add(property);

		return map;
	}

	public static PropertyMap getEmptyDefaultEntityPropertiesMap() {
		return ModelFactory.eINSTANCE.createPropertyMap();
	}

	/**
	 * @return
	 */
	public static PropertyMap getDefaultEntityPropertiesMap() {
		// Default Property Map Creation
		PropertyMap map = ModelFactory.eINSTANCE.createPropertyMap();

		ObjectProperty backingStoreObjProperty = ModelFactory.eINSTANCE.createObjectProperty();
		backingStoreObjProperty.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE);
		SimpleProperty property = null;

		{
			PropertyMap backingStorepropertyMap = ModelFactory.eINSTANCE.createPropertyMap();

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_TABLENAME);
			property.setValue("");
			backingStorepropertyMap.getProperties().add(property);

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_TYPENAME);
			property.setValue("");
			backingStorepropertyMap.getProperties().add(property);

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE);
			property.setValue("true");
			backingStorepropertyMap.getProperties().add(property);

			backingStoreObjProperty.setValue(backingStorepropertyMap);
		}

		ObjectProperty cacheObjProperty = ModelFactory.eINSTANCE.createObjectProperty();
		cacheObjProperty.setName("Cache");
		{
			PropertyMap cachepropertyMap = ModelFactory.eINSTANCE.createPropertyMap();

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_CONSTANT);
			property.setValue("false");
			cachepropertyMap.getProperties().add(property);

			// property = ModelFactory.eINSTANCE.createSimpleProperty();
			// property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_PRELOAD_ALL);
			// property.setValue("false");
			// cachepropertyMap.getProperties().add(property);
			//
			// property = ModelFactory.eINSTANCE.createSimpleProperty();
			// property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE);
			// property.setValue("0");
			// cachepropertyMap.getProperties().add(property);

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_REQUIRESVERSIONCHECK);
			property.setValue("true");
			cachepropertyMap.getProperties().add(property);

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_ISCACHELIMITED);
			property.setValue("true");
			cachepropertyMap.getProperties().add(property);

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_EVICTONUPDATE);
			property.setValue("true");
			cachepropertyMap.getProperties().add(property);

			cacheObjProperty.setValue(cachepropertyMap);
		}

		map.getProperties().add(backingStoreObjProperty);
		map.getProperties().add(cacheObjProperty);

		return map;
	}

	public static boolean updateProperties(PropertyMap extendedProperties) {
		boolean changed = false;
		for (Entity entity : extendedProperties.getProperties()) {
			if (entity instanceof ObjectProperty) {
				ObjectProperty property = (ObjectProperty) entity;
				if ("BackingStore".equals(property.getName())) {
					property.setName("Backing Store");
					changed = true;
				}
				if (property.getValue() != null && property.getValue() instanceof PropertyMap) {
					if (updateProperties((PropertyMap) property.getValue())) {
						changed = true;
					}
				}
			}
			if (entity instanceof SimpleProperty) {
				SimpleProperty property = (SimpleProperty) entity;
				if ("Has Backing Store".equals(property.getName())) {
					property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE_HASBACKINGSTORE);
					changed = true;
				}
				if ("BackingStore".equals(property.getName())) {
					property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_BACKINGSTORE);
					changed = true;
				}
				if ("Maximum Records to Load on Recovery [Set 0 for All]".equals(property.getName())) {
					property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_PRELOAD_FETCHSIZE);
					changed = true;
				}
				if ("Preload On Recovery [true | false]".equals(property.getName())) {
					property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_PRELOAD_ALL);
					changed = true;
				}
				if ("Is Cache Limited [true | false]".equals(property.getName())) {
					property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_ENTITY_CACHE_ISCACHELIMITED);
					changed = true;
				}
			}
		}
		return changed;
	}

	/**
	 * @return
	 */
	public static PropertyMap getDefaultStateMachinePropertiesMap() {
		// Default Property Map Creation
		PropertyMap map = ModelFactory.eINSTANCE.createPropertyMap();

		ObjectProperty backingStoreObjProperty = ModelFactory.eINSTANCE.createObjectProperty();
		backingStoreObjProperty.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE);
		SimpleProperty property = null;
		{
			PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();

			property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(com.tibco.cep.designtime.model.Entity.EXTPROP_PROPERTY_BACKINGSTORE_COLUMNNAME);
			property.setValue("");
			propertyMap.getProperties().add(property);

			backingStoreObjProperty.setValue(propertyMap);
		}
		map.getProperties().add(backingStoreObjProperty);

		property = ModelFactory.eINSTANCE.createSimpleProperty();
		property.setName("index");
		property.setValue("false");

		map.getProperties().add(property);

		return map;
	}

	/**
	 * @param propertyDefinition
	 * @param page
	 * @param projectName
	 * @param invokeFromEditor
	 */
	@SuppressWarnings("unchecked")
	public static boolean runDomainResourceSelector(PropertyDefinition propertyDefinition, IWorkbenchPage page,
			String projectName, boolean invokeFromEditor, boolean editorOpen) {
		try {
			DomainResourceSelector picker = new DomainResourceSelector(page.getWorkbenchWindow().getShell(),
					projectName, propertyDefinition.getDomainInstances(), propertyDefinition.getType());
			if (picker.open() == Dialog.OK) {
				if (picker.getFirstResult() != null) {
					Set<Object> domainObjects = (Set<Object>) picker.getFirstResult();
					propertyDefinition.getDomainInstances().clear();
					for (Object dm : domainObjects) {
						String fullPath = null;
						if (dm instanceof IFile) {
							IFile domainFile = (IFile) dm;
							@SuppressWarnings("unused")
							Domain domain = (Domain) IndexUtils.getEntity(projectName,
									IndexUtils.getFullPath(domainFile), ELEMENT_TYPES.DOMAIN);
							fullPath = IndexUtils.getFullPath(domainFile);
						}
						if (dm instanceof SharedEntityElement) {
							SharedEntityElement sharedEntityElement = (SharedEntityElement) dm;
							if (sharedEntityElement.getEntity() instanceof Domain) {
								fullPath = sharedEntityElement.getEntity().getFullPath();
							}
						}
						if (fullPath != null) {
							addDomainInstances(propertyDefinition, fullPath);
						}
					}

					if (!invokeFromEditor && !editorOpen) {
						Entity entity = (Entity) propertyDefinition.eContainer();
						ModelUtils.saveEObject(entity);
						// entity.eResource().save(ModelUtils.getPersistenceOptions());
						// Refreshing the Project Explorer
						IViewPart view = page.findView(ProjectExplorer.ID);
						if (view != null) {
							((ProjectExplorer) view).getCommonViewer().refresh();
						}
					}
				}
				return true;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * @param propertyDefinition
	 * @param domain
	 */


	public static void addDomainInstances(PropertyDefinition propertyDefinition, String path) {
		if (!doesDomainExist(propertyDefinition.getDomainInstances(), path)) {
			DomainInstance dmInstance = DomainFactory.eINSTANCE.createDomainInstance();
			dmInstance.setResourcePath(path);
			dmInstance.setOwnerProperty(propertyDefinition);
			// dmInstance.setGUID(GUIDGenerator.getGUID());
			propertyDefinition.getDomainInstances().add(dmInstance);
		}
	}

	/**
	 * @param instances
	 * @param domain
	 * @return
	 */
	public static boolean doesDomainExist(EList<DomainInstance> instances, String path) {

		for (DomainInstance instance : instances) {
			if (instance.getResourcePath().equals(path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param dmInstances
	 * @return
	 */
	public static String getDomainValues(EList<DomainInstance> dmInstances) {
		String domainPath = "";
		if (dmInstances.size() > 0) {
			DomainInstance firstDomainInstance = dmInstances.get(0);
			domainPath = firstDomainInstance.getResourcePath();
		}
		for (int i = 1; i < dmInstances.size(); i++) {
			domainPath = domainPath + DELIMITER + dmInstances.get(i).getResourcePath();
		}
		return domainPath;
	}

	/**
	 * @param pathSet
	 * @param page
	 */
	public static void checkDomainEditorReference(Set<String> pathSet, IWorkbenchPage page) {
		for (String path : pathSet) {
			for (IEditorReference reference : page.getEditorReferences()) {
				try {
					if (reference.getEditorInput() instanceof DomainFormEditorInput) {
						DomainFormEditorInput domainFormEditorInput = (DomainFormEditorInput) reference
								.getEditorInput();
						Domain domain = domainFormEditorInput.getDomain();
						if (domain.getFullPath().equals(path)) {
							DomainFormEditor editor = (DomainFormEditor) reference.getEditor(true);
							editor.getDomainFormViewer().getDomainDataTypesCombo()
									.setEnabled(domain.getInstances().size() > 0 ? false : true);
						}
					}

				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Resetting State Machine Combo
	 *
	 * @param stateMachineCombo
	 * @param concept
	 * @param editor
	 */
	public static void resetStatemachineCombo(PropertyTypeCombo stateMachineCombo, Concept concept,
			AbstractSaveableEntityEditorPart editor) {
		if (stateMachineCombo.getItemCount() > 0) {
			if (!compare(stateMachineCombo.getItems(), concept.getStateMachines())) {
				editor.modified();
			}
			stateMachineCombo.removeAll();
		}
		EList<StateMachine> instances = concept.getStateMachines();
		int selIndex = 0;
		int mainSMIndex = -1;
		for (StateMachine instance : instances) {
			if (instance.isMain()) {
				mainSMIndex = selIndex;
			}
			selIndex++;
			String resourcePath = IndexUtils.getFullPath(instance);
			stateMachineCombo.add(EditorsUIPlugin.getDefault().getImage("icons/state_machine.png"),
					resourcePath);
		}
		if (mainSMIndex != -1) {
			stateMachineCombo.select(mainSMIndex);
		} else {
			stateMachineCombo.add(EditorsUIPlugin.getDefault().getImage("icons/state_machine.png"), "");
			stateMachineCombo.select(0);
		}
	}

	/**
	 * @param items
	 * @param instances
	 * @return
	 */
	public static boolean compare(TableItem[] items, EList<StateMachine> instances) {
		if (items.length != instances.size())
			return false;
		StateMachine[] ar = new StateMachine[instances.size()];
		instances.toArray(ar);
		for (int i = 0; i < items.length; i++) {
			if (!items[i].getText().equals(IndexUtils.getFullPath(ar[i]))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param editor
	 * @param form
	 * @param project
	 * @return
	 */
	public static Action createDependencyDiagramAction(final IEditorPart editor, final ScrolledForm form,
			final IProject project) {
		Action dependencyDiagramAction = new Action("dependenyDiagram", Action.AS_PUSH_BUTTON) {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				// if(isChecked()){
				IWorkbenchPage page = editor.getEditorSite().getWorkbenchWindow().getActivePage();
				IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
				openDependencyDiagram(page, file, project);
				form.reflow(true);
				// }
			}
		};
		dependencyDiagramAction.setChecked(false);
		dependencyDiagramAction.setToolTipText(Messages.getString("dependency.diagram.tooltip"));
		dependencyDiagramAction.setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/dependency_diagram.png"));
		return dependencyDiagramAction;
	}

	/**
	 * @param page
	 * @param file
	 * @param project
	 */
	public static void openDependencyDiagram(final IWorkbenchPage page, IFile file, IProject project) {
		final IEditorPart editor = getDependencyDiagramEditorOpen(page, IndexUtils.getFullPath(file));
		if (editor == null) {
			DependencyDiagramEditorInput input = new DependencyDiagramEditorInput(file, project);
			openEditor(page, input, DependencyDiagramEditor.ID);
		} else {
			invokeOnDisplayThread(new Runnable() {
				public void run() {
					page.activate(editor);
				}
			}, false);
		}
	}

	/**
	 * @param page
	 * @param file
	 * @param project
	 */
	public static void openSequenceDiagram(final IWorkbenchPage page, IFile file, IProject project) {
		final IEditorPart editor = getSequenceDiagramEditorOpen(page, IndexUtils.getFullPath(file));
		if (editor == null) {
			SequenceDiagramEditorInput input = new SequenceDiagramEditorInput(file, project);
			openEditor(page, input, SequenceDiagramEditor.ID);
		} else {
			invokeOnDisplayThread(new Runnable() {
				public void run() {
					page.activate(editor);
				}
			}, false);
		}
	}

	/**
	 * @param editor
	 * @param form
	 * @param project
	 * @return
	 */
	public static Action createSequenceDiagramAction(final IEditorPart editor, final ScrolledForm form,
			final IProject project) {
		Action sequenceDiagramAction = new Action("sequenceDiagram", Action.AS_PUSH_BUTTON) {
			/*
			 * (non-Javadoc)
			 *
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				// if(isChecked()){
				IWorkbenchPage page = editor.getEditorSite().getWorkbenchWindow().getActivePage();
				IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
				openSequenceDiagram(page, file, project);
				form.reflow(true);
				// }
			}
		};
		sequenceDiagramAction.setChecked(false);
		sequenceDiagramAction.setToolTipText(Messages.getString("sequence.diagram.tooltip"));
		sequenceDiagramAction.setImageDescriptor(EditorsUIPlugin.getImageDescriptor("icons/sequence_diagram.png"));
		return sequenceDiagramAction;
	}

	/**
	 * @param concept
	 *            -> The {@link Concept} to associate the SM with
	 * @param stateMachine
	 *            -> The {@link StateMachine} in question
	 * @param isMain
	 */
	public static void addStateMachineAssocations(Concept concept, StateMachine stateMachine, boolean isMain) {
		if (!isSMAssociated(concept.getFullPath(), stateMachine)) {
			concept.getStateMachinePaths().add(IndexUtils.getFullPath(stateMachine));
			// TODO: this has to be synched UI Changes
			// stateMachine.setOwnerConceptPath(IndexUtils.getFullPath(concept));
		}
	}

	/**
	 * Remove index entries matching the particular {@link Entity} for this
	 * {@link IFile}.
	 *
	 * @param <T>
	 * @param index
	 * @param file
	 * @param property
	 *            -> The property under question : null for {@link SMInstance}
	 * @param type
	 */
	// @SuppressWarnings("unchecked")
	// public static <T extends BaseInstance> void
	// removeIndexEntry(DesignerProject index,
	// IFile file,
	// EObject property,
	// ELEMENT_TYPES type) {
	// ElementContainer parentFolder = IndexUtils
	// .getFolderForFile(index, file);
	// EList<DesignerElement> entries = parentFolder.getEntries();
	// List<DesignerElement> elementsToRemove = new
	// ArrayList<DesignerElement>();
	// String relativePath = IndexUtils.getFullPath(file);
	// //The relative path should point to the entity relative path
	// for (DesignerElement designerElement : entries) {
	// if (designerElement instanceof InstanceElement) {
	// InstanceElement<T> instanceElement = (InstanceElement<T>)
	// designerElement;
	// T instance = instanceElement.getInstance();
	// // if (instance instanceof SMInstance) {
	// // SMInstance smInstance = (SMInstance)instance;
	// // String conceptPath = smInstance.getOwnerConceptPath();
	// // if (conceptPath.equals(relativePath)) {
	// // //Only then add to removal
	// // elementsToRemove.add(instanceElement);
	// // }
	// // }
	// if (instance instanceof DomainInstance) {
	// DomainInstance domainInstance = (DomainInstance)instance;
	// PropertyDefinition ownerProperty = domainInstance.getOwnerProperty();
	// Entity entity = (Entity)ownerProperty.eContainer();
	// if (entity.getFullPath().equals(relativePath)) {
	// if (ownerProperty.equals(property)) {
	// //Only then add to removal
	// elementsToRemove.add(instanceElement);
	// }
	// }
	// }
	// }
	// }
	// if (!elementsToRemove.isEmpty()) {
	// parentFolder.getEntries().removeAll(elementsToRemove);
	//
	// switch (type) {
	// case DOMAIN_INSTANCE: {
	// List<InstanceElement<DomainInstance>> children = index
	// .getDomainInstanceElements();
	// children.removeAll(elementsToRemove);
	// break;
	// }
	// // case STATE_MACHINE_INSTANCE: {
	// // List<InstanceElement<SMInstance>> children = index
	// // .getStateMachineInstanceElements();
	// // children.removeAll(elementsToRemove);
	// // break;
	// // }
	// }
	// }
	// }

	/**
	 * @param instances
	 * @param path
	 * @return
	 */
	public static boolean isSMAssociated(String ownerConceptPath, StateMachine stateMachine) {
		if (stateMachine == null) {
			throw new IllegalArgumentException("State Machine cannot be null");
		}

		// Check if it has a concept path or if the concept path is the
		// same as the already associated one
		String conceptPath = stateMachine.getOwnerConceptPath();
		return (conceptPath == null || conceptPath.equals(ownerConceptPath)) ? false : true;
	}

	/**
	 * @param instances
	 */
	public static void setDefaultMain(EList<StateMachine> stateMachines) {
		boolean isMain = false;
		for (StateMachine stateMachine : stateMachines) {
			if (stateMachine.isMain()) {
				isMain = true;
				break;
			}
		}
		if (!isMain && !stateMachines.isEmpty()) {
			stateMachines.get(0).setMain(true);
		}
	}

	/**
	 * @param instances
	 */
	public static void setResetMain(EList<StateMachine> stateMachines, StateMachine stateMachine) {
		for (StateMachine instance : stateMachines) {
			if (instance.isMain() && instance != stateMachine) {
				instance.setMain(false);
				// Sandip>> TODO: this has to be synched UI Changes
				// Uncomment this when above done.
			}
		}
	}

	/**
	 * @param editor
	 * @param propertyDefinition
	 * @param entity
	 * @param index
	 */
	public static void refreshPropertyTable(final AbstractSaveableEntityEditorPart editor,
			final PropertyDefinition propertyDefinition, final Entity entity, final int index) {
		Display.getDefault().asyncExec(new Runnable() {


			@Override
			public void run() {
				try {
					JTable table = null;
					DefaultTableModel model = null;
					String val = getDomainValues(propertyDefinition.getDomainInstances());


					// if(table!=null){
					// table.setRowSelectionInterval(index, index);
					// table.updateUI();
					// }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void refreshDiagramEditor(IWorkbenchPage page, IFile file) {
		refreshDiagramEditor(page, file, null);
	}

	/**
	 * @param page
	 * @param file
	 * @param invocationContext
	 */
	public static void refreshDiagramEditor(IWorkbenchPage page, IFile file, Object invocationContext) {
		try {

			IEditorPart editor = getProjectDiagramEditor(page.getEditorReferences(), file.getFullPath()
					.toPortableString());
			if (editor != null) {
				IDiagramManager manager = null;
				// Changing the NONE Layout preference

				if (file.getFileExtension().equals("projectview")) {
					manager = ((ProjectDiagramEditor) editor).getDiagramManager();
				}
				if (file.getFileExtension().equals("conceptview")) {
					manager = ((ConceptDiagramEditor) editor).getDiagramManager();
				}
				if (file.getFileExtension().equals("eventview")) {
					manager = ((EventDiagramEditor) editor).getDiagramManager();
				}

				if (manager != null) {
					manager.setRefreshAction(true);
					DiagrammingPlugin
							.getDefault()
							.getPreferenceStore()
							.setValue(DiagramPreferenceConstants.RUN_LAYOUT_ON_CHANGES, DiagramPreferenceConstants.NONE);
					manager.getGraphManager().getMainDisplayGraph().nodes().clear();
					manager.getGraphManager().getMainDisplayGraph().edges().clear();
					manager.getDrawingCanvas().drawGraph();
					manager.getDrawingCanvas().repaint();
					manager.openModel();
					manager.getDrawingCanvas().drawGraph();
					manager.getDrawingCanvas().repaint();

				}

				if (page.getActiveEditor() != editor) {
					page.activate(editor);
				}

				// Changing back to default Incremental Layout preference
				DiagrammingPlugin
						.getDefault()
						.getPreferenceStore()
						.setValue(DiagramPreferenceConstants.RUN_LAYOUT_ON_CHANGES,
								DiagramPreferenceConstants.INCREMENTAL);

				manager.setRefreshAction(true);
			} else {
				openDiagramEditor(page, file, invocationContext);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void openDiagramEditor(IWorkbenchPage page, IFile file) {
		openDiagramEditor(page, file, null);
	}

	/**
	 * @param page
	 * @param project
	 */
	public static void openDiagramEditor(IWorkbenchPage page, IFile file, Object invocationContext) {
		EntityDiagramEditorInput input = new EntityDiagramEditorInput(file, file.getProject(), invocationContext);
		try {
			if (file.getFileExtension().equals("projectview")) {
				IEditorPart editor = page.findEditor(input);
				if (editor != null) {
					page.closeEditor(editor, true);
				}
				page.openEditor(input, ProjectDiagramEditor.ID);
			}
			if (file.getFileExtension().equals("conceptview")) {
				IEditorPart editor = page.findEditor(input);
				if (editor != null) {
					page.closeEditor(editor, true);
				}
				page.openEditor(input, ConceptDiagramEditor.ID);
			}
			if (file.getFileExtension().equals("eventview")) {
				IEditorPart editor = page.findEditor(input);
				if (editor != null) {
					page.closeEditor(editor, true);
				}
				page.openEditor(input, EventDiagramEditor.ID);
			}
			{
				try {
					file.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // refreshing the project explorer
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param references
	 * @param fullPath
	 * @return
	 */
	public static IEditorPart getProjectDiagramEditor(IEditorReference[] references, String fullPath) {
		IEditorPart editor = null;
		for (IEditorReference ref : references) {
			try {
				if (ref.getEditorInput() instanceof EntityDiagramEditorInput) {
					EntityDiagramEditorInput input = (EntityDiagramEditorInput) ref.getEditorInput();
					if (input.getFile().getFullPath().toPortableString().equals(fullPath)) {
						editor = ref.getEditor(true);
						return editor;
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param file
	 * @param entity
	 * @param propDefName
	 * @param handler
	 */
	public static void checkEditorReference(IFile file, Entity entity, String propDefName, AssociateDomains handler) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		for (IEditorReference reference : page.getEditorReferences()) {
			try {
				if (entity instanceof Concept) {
					if (reference.getEditorInput() instanceof ConceptFormEditorInput) {
						ConceptFormEditorInput conceptFormEditorInput = (ConceptFormEditorInput) reference
								.getEditorInput();
						if (conceptFormEditorInput.getFile().getFullPath().toPortableString()
								.equals(file.getFullPath().toPortableString())) {
							checkActiveEditorWithProperty(conceptFormEditorInput.getConcept().getProperties(),
									propDefName, reference, handler);
						}
					}
				}
				if (entity instanceof Scorecard) {
					if (reference.getEditorInput() instanceof ScorecardFormEditorInput) {
						ScorecardFormEditorInput scorecardFormEditorInput = (ScorecardFormEditorInput) reference
								.getEditorInput();
						if (scorecardFormEditorInput.getFile().getFullPath().toPortableString()
								.equals(file.getFullPath().toPortableString())) {
							checkActiveEditorWithProperty(scorecardFormEditorInput.getScorecard().getProperties(),
									propDefName, reference, handler);
						}
					}
				}
				if (entity instanceof Event) {
					if (reference.getEditorInput() instanceof EventFormEditorInput) {
						EventFormEditorInput eventFormEditorInput = (EventFormEditorInput) reference.getEditorInput();
						if (eventFormEditorInput.getFile().getFullPath().toPortableString()
								.equals(file.getFullPath().toPortableString())) {
							checkActiveEditorWithProperty(eventFormEditorInput.getSimpleEvent().getProperties(),
									propDefName, reference, handler);
						}
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param propertyDefList
	 * @param pDefName
	 * @param reference
	 * @param handler
	 */
	public static void checkActiveEditorWithProperty(EList<PropertyDefinition> propertyDefList, String pDefName,
			IEditorReference reference, AssociateDomains handler) {
		int row = 0;
		for (PropertyDefinition pDef : propertyDefList) {
			if (pDef.getName().equals(pDefName)) {
				handler.setPropertyDefinition(pDef);
				handler.setEditorOpen(true);
				handler.setEditor((AbstractSaveableEntityEditorPart) reference.getEditor(true));
				handler.setIndex(row);
				break;
			}
			row++;
		}
	}

	/**
	 * Closes opened editors for a project
	 *
	 * @param projectName
	 */
	public static void closeEditorsForProject(final String projectName) {
		if (projectName == null)
			return;
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page == null)
					return;
				for (final IEditorReference reference : page.getEditorReferences()) {
					try {
						if (reference.getEditorInput() instanceof FileEditorInput) {
							FileEditorInput fileEditorInput = (FileEditorInput) reference.getEditorInput();
							if (fileEditorInput == null)
								continue;
							IFile file = fileEditorInput.getFile();
							if (file == null)
								continue;
							if (projectName.equals(file.getProject().getName())) {
								page.closeEditor(reference.getEditor(true), false);
							}
						}
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Closes the editor for a resource
	 *
	 * @param resPath
	 */
	public static void closeEditorForResource(final String resPath) {
		if (resPath == null)
			return;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page == null)
					return;
				for (final IEditorReference reference : page.getEditorReferences()) {
					try {
						if (reference.getEditorInput() instanceof FileEditorInput) {
							FileEditorInput fileEditorInput = (FileEditorInput) reference.getEditorInput();
							if (fileEditorInput == null)
								continue;
							IFile file = fileEditorInput.getFile();
							if (file == null)
								continue;
							IPath path = Path.fromOSString(resPath);
							if (file.getFullPath().equals(path)) {
								page.closeEditor(reference.getEditor(true), false);

								break;
							}
						}
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	/**
	 * it returns a Editor Reference for IFile this method should be called from
	 * UI thread other wise #IWorkbenchPage reference will be null and it will
	 * return editor reference as null
	 *
	 * @param file
	 * @return
	 */
	public static AbstractSaveableEntityEditorPart getEditorReference(final IFile file) {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (page == null)
				return null;

			for (IEditorReference reference : page.getEditorReferences()) {
				try {
					if (reference.getEditorInput() instanceof FileEditorInput) {

						FileEditorInput fileEditorInput = (FileEditorInput) reference.getEditorInput();
						if (fileEditorInput.getFile().getFullPath().equals(file.getFullPath())) {
							return (AbstractSaveableEntityEditorPart) reference.getEditor(true);
						}
					}
				} catch (PartInitException e) {
					e.printStackTrace();
					return null;
				}
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getUniqueName(List<String> objs, String uniqueIdentifier) {
		List<Integer> noList = new ArrayList<Integer>();
		try {
			for (String obj : objs) {

				if (obj.startsWith(uniqueIdentifier)) {
					String no = obj.substring(obj.lastIndexOf("_") + 1);
					noList.add(getValidNo(no));
				}
			}
			if (noList.size() > 0) {
				java.util.Arrays.sort(noList.toArray());
				int no = noList.get(noList.size() - 1).intValue();
				no++;
				return uniqueIdentifier + no;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uniqueIdentifier + 0;
	}

	/**
	 * @param page
	 * @param conceptPath
	 * @return
	 */
	public static ConceptFormEditor getConceptEditorReference(IWorkbenchPage page, String conceptPath) {

		for (IEditorReference reference : page.getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof ConceptFormEditorInput) {
					ConceptFormEditorInput conceptFormEditorInput = (ConceptFormEditorInput) reference.getEditorInput();
					if (conceptFormEditorInput.getConcept().getFullPath().equals(conceptPath)) {
						return ((ConceptFormEditor) reference.getEditor(true));
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @param page
	 * @param path
	 * @return
	 */
	public static IEditorPart getSequenceDiagramEditorOpen(IWorkbenchPage page, String path) {

		for (IEditorReference reference : page.getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof SequenceDiagramEditorInput) {
					SequenceDiagramEditorInput seqDiagramEditorInput = (SequenceDiagramEditorInput) reference
							.getEditorInput();
					if (IndexUtils.getFullPath(seqDiagramEditorInput.getFile()).equals(path)) {
						return reference.getEditor(true);
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @param page
	 * @param path
	 * @return
	 */
	public static IEditorPart getDependencyDiagramEditorOpen(IWorkbenchPage page, String path) {

		for (IEditorReference reference : page.getEditorReferences()) {
			try {
				if (reference.getEditorInput() instanceof DependencyDiagramEditorInput) {
					DependencyDiagramEditorInput depDiagramEditorInput = (DependencyDiagramEditorInput) reference
							.getEditorInput();
					if (IndexUtils.getFullPath(depDiagramEditorInput.getFile()).equals(path)) {
						return reference.getEditor(true);
					}
				}
			} catch (PartInitException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @param no
	 * @return
	 */
	public static int getValidNo(String no) {
		int n;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return 0;
		}
		return n;
	}

	/**
	 * @param viewer
	 * @param ruler
	 * @param access
	 * @param sharedColors
	 */
	public static void setupSourceViewerDecorationSupport(ISourceViewer viewer, OverviewRuler ruler,
			DefaultMarkerAnnotationAccess access, EntitySharedTextColors sharedColors) {
		SourceViewerDecorationSupport fSourceViewerDecorationSupport = new SourceViewerDecorationSupport(viewer, ruler,
				access, sharedColors);
		configureSourceViewerDecorationSupport(fSourceViewerDecorationSupport);
	}

	/**
	 * @param support
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		Iterator e = EditorsPlugin.getDefault().getMarkerAnnotationPreferences().getAnnotationPreferences().iterator();
		while (e.hasNext())
			support.setAnnotationPreference((AnnotationPreference) e.next());

		support.setCursorLinePainterPreferenceKeys(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE,
				AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR);
		support.setMarginPainterPreferenceKeys(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN,
				AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLOR,
				AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLUMN);
		// support.setSymbolicFontName(getFontPropertyPreferenceKey());
		support.install(EditorsUI.getPreferenceStore());
	}

	/**
	 * @param projectName
	 * @param items
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<GlobalVariableDef> getGlobalVariableDefs(final String projectName, final Table table) {
		final Object[] ret = new Object[1];
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				if (table == null || table.isDisposed()) {
					return;
				}
				TableItem[] items = table.getItems();
				List<GlobalVariableDef> defs = new ArrayList<GlobalVariableDef>();
				for (TableItem tableItem : items) {
					String type = tableItem.getText(0);
					String name = tableItem.getText(1);
					GlobalVariableExtension gv = new GlobalVariableExtension(projectName);
					gv.setName(name);
					gv.setType(ModelUtils.convertPathToPackage(type));
					defs.add(gv);
				}
				ret[0] = defs;
			}
		});
		return (List<GlobalVariableDef>) ret[0];
	}

	public static void updateDeclarations(Table declarationsTable, Compilable rule, String projectName) {
		// declarations section
		if (rule == null) {
			return; // should it be null?
		}
		TableItem[] items = declarationsTable.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}
		// Collection<Symbol> symbols =
		// rule.getSymbols().getSymbolMap().values();
		for (Symbol symbol : rule.getSymbols().getSymbolList()) {
			TableItem tableItem = new TableItem(declarationsTable, SWT.NONE);
			DesignerElement element = IndexUtils.getElement(projectName, symbol.getType());
			if (element != null) {
				if (element.getElementType() == ELEMENT_TYPES.SIMPLE_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				} else if (element.getElementType() == ELEMENT_TYPES.TIME_EVENT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
				} else if (element.getElementType() == ELEMENT_TYPES.CONCEPT) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
				} else if (element.getElementType() == ELEMENT_TYPES.SCORECARD) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/scorecard.png"));
				}
			} else {
				if (symbol.getType().equalsIgnoreCase("Concept")) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/concept.png"));
				}
				if (symbol.getType().equalsIgnoreCase("SimpleEvent")
						|| symbol.getType().equalsIgnoreCase("AdvisoryEvent")) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/event.png"));
				}
				if (symbol.getType().equalsIgnoreCase("TimeEvent")) {
					tableItem.setImage(EditorsUIPlugin.getDefault().getImage("icons/time-event.gif"));
				}
			}
			tableItem.setText(0, symbol.getType());
			tableItem.setText(1, symbol.getIdName());
		}
	}

	/**
	 * @param field
	 * @param propeFieldType
	 * @param path
	 * @param isArray
	 */
	public static void setPopertyField(PropertyTypeCombo field, String propeFieldType, String path, boolean isArray) {
		if (propeFieldType.equalsIgnoreCase("Concept")) {
			field.setImage(getPropertyFieldImage(propeFieldType));
			field.setText(isArray ? path + "[]" : path);
		} else if (propeFieldType.equalsIgnoreCase("Event")) {
			field.setImage(getPropertyFieldImage(propeFieldType));
			field.setText(isArray ? path + "[]" : path);
		} else if (propeFieldType.equalsIgnoreCase("Void")) {
			field.setImage(getPropertyFieldImage(propeFieldType));
			field.setText(isArray ? propeFieldType + "[]" : propeFieldType);
		} else if (propeFieldType.equalsIgnoreCase("Object")) {
			field.setImage(getPropertyFieldImage(propeFieldType));
			field.setText(isArray ? propeFieldType + "[]" : propeFieldType);
		} else {
			PROPERTY_TYPES type = PROPERTY_TYPES.get(propeFieldType);
			if (type != null) {
				Image image = getPropertyImage(type);
				if (image != null) {
					field.setImage(image);
				}
			}
			field.setText(isArray ? propeFieldType + "[]" : propeFieldType);
		}
	}

	/**
	 * @param propeFieldType
	 * @return
	 */
	public static Image getPropertyFieldImage(String propeFieldType) {
		if (propeFieldType.equalsIgnoreCase("Concept")) {
			return EditorsUIPlugin.getDefault().getImage("icons/concept.png");
		} else if (propeFieldType.equalsIgnoreCase("Event")) {
			return EditorsUIPlugin.getDefault().getImage("icons/event.png");
		} else if (propeFieldType.equalsIgnoreCase("Void")) {
			return EditorsUIPlugin.getDefault().getImage("icons/no_type.png");
		} else if (propeFieldType.equalsIgnoreCase("Object")) {
			return EditorsUIPlugin.getDefault().getImage("icons/no_type.png");
		} else {
			// if(propeFieldType.equals("int")){
			// propeFieldType = "Integer";
			// }else{
			// propeFieldType = Character.toUpperCase(propeFieldType.charAt(0))
			// + propeFieldType.substring(1);
			// }
			PROPERTY_TYPES type = PROPERTY_TYPES.get(propeFieldType);
			if (type != null) {
				Image image = getPropertyImage(type);
				if (image != null) {
					return image;
				}
			}
		}
		return null;
	}

	/**
	 * @param page
	 * @param projectName
	 * @param editorDirtyCheck
	 * @return
	 */
	public static boolean saveRuleTemplateEditor(IWorkbenchPage page, String projectName, boolean editorDirtyCheck) {
		for (final IEditorPart editor : page.getDirtyEditors()) {
			try {
				if (editor.getEditorInput() instanceof RuleFormEditorInput) {
					RuleFormEditorInput ruleEditorInput = (RuleFormEditorInput) editor.getEditorInput();
					if (ruleEditorInput.getFile().getProject().getName().equals(projectName)) {
						if (editorDirtyCheck) {
							return true;
						} else {
							page.saveEditor(editor, false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @param page
	 * @param projectName
	 * @param editorDirtyCheck
	 * @return
	 */
	public static boolean saveConceptEditor(IWorkbenchPage page, String projectName, boolean editorDirtyCheck) {
		for (final IEditorPart editor : page.getDirtyEditors()) {
			try {
				if (editor.getEditorInput() instanceof ConceptFormEditorInput) {
					ConceptFormEditorInput conceptEditorInput = (ConceptFormEditorInput) editor.getEditorInput();
					if (conceptEditorInput.getFile().getProject().getName().equals(projectName)) {
						if (editorDirtyCheck) {
							return true;
						} else {
							page.saveEditor(editor, false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * @param page
	 * @param projectName
	 * @param editorDirtyCheck
	 * @return
	 */
	public static boolean saveEventEditor(IWorkbenchPage page, String projectName, boolean editorDirtyCheck) {
		for (final IEditorPart editor : page.getDirtyEditors()) {
			try {
				if (editor.getEditorInput() instanceof EventFormEditorInput) {
					EventFormEditorInput eventFormEditorInput = (EventFormEditorInput) editor.getEditorInput();
					if (eventFormEditorInput.getFile().getProject().getName().equals(projectName)) {
						if (editorDirtyCheck) {
							return true;
						} else {
							page.saveEditor(editor, false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * Check whether the given edit is valid (i.e. whether the underlying file
	 * is read only), and, if so, execute the command. This call will <b>not</b>
	 * revert the editor if the command cannot be executed
	 *
	 * @param editor
	 * @param command
	 */
	public static boolean executeCommand(AbstractSaveableEntityEditorPart editor, Command command) {
		return executeCommand(editor, command, false);
	}

	/**
	 * Check whether the given edit is valid (i.e. whether the underlying file
	 * is read only), and, if so, execute the command. If
	 * <code>revertOnFailure</code> is true and the command cannot be executed,
	 * then the editor is reverted.
	 *
	 * @param editor
	 * @param command
	 * @param revertOnFailure
	 *            - whether to revert the editor if the command cannot be
	 *            executed
	 */
	public static boolean executeCommand(AbstractSaveableEntityEditorPart editor, Command command,
			boolean revertOnFailure) {
		if (!checkPermissions(editor, command)) {
			if (revertOnFailure) {
				editor.doRevert();
			}
			return false;
		}
		editor.getEditingDomain().getCommandStack().execute(command);
		return true;
	}

	/**
	 * Check whether the given editor can be edited (i.e. whether the underlying
	 * file is read only)
	 *
	 * @param editor
	 */
	public static boolean checkPermissions(final AbstractSaveableEntityEditorPart editor) {
		return checkPermissions(editor, null);
	}

	/**
	 * Check whether the given editor can be edited (i.e. whether the underlying
	 * file is read only), and whether the command is executable.
	 *
	 * @param editor
	 * @param command
	 *            - the command to execute -- can be null
	 */
	public static boolean checkPermissions(final AbstractSaveableEntityEditorPart editor, Command command) {
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			final IFile file = ((FileEditorInput) editorInput).getFile();
			if (file != null) {
				EditingDomain editingDomain = editor.getEditingDomain();
				if (file.isReadOnly()) {
					// if applicable, remove this resource from the
					// resourceToReadOnlyMap, so that making the
					// IFile editable will allow the command to execute
					if (editingDomain instanceof AdapterFactoryEditingDomain && command instanceof SetCommand) {
						((AdapterFactoryEditingDomain) editingDomain).getResourceToReadOnlyMap().remove(
								((SetCommand) command).getOwner().eResource());
					}
				}
				final IStatus validateEdit = ResourcesPlugin.getWorkspace().validateEdit(new IFile[] { file },
						editor.getSite().getShell());
				if (!validateEdit.isOK()) {
					editor.getSite().getShell().getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							MessageDialog.openError(editor.getSite().getShell(), "Unable to execute command",
									validateEdit.getMessage());
						}
					});
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param ruleSource
	 * @param textWidget
	 * @param undoManager
	 * @param viewerConfig
	 * @param sourceViewer
	 */
	public static void setKeySupport(final IStudioRuleSourceCommon ruleSource, final StyledText textWidget,
			final TextViewerUndoManager undoManager, final RulesSourceViewerConfiguration viewerConfig,
			final SourceViewer sourceViewer) {

		setTraverseSupport(textWidget);
		setKeySupport(ruleSource, textWidget, undoManager, viewerConfig, sourceViewer, true);
	}

	/**
	 * @param ruleSource
	 * @param textWidget
	 * @param undoManager
	 * @param condViewerConfig
	 * @param viewerConfig
	 * @param condSourceViewer
	 * @param sourceViewer
	 */
	public static void setKeySupport(final IStudioRuleSourceCommon ruleSource, final StyledText textWidget,
			final TextViewerUndoManager undoManager, final RulesSourceViewerConfiguration viewerConfig,
			final SourceViewer sourceViewer, final boolean supportCopyPaste) {

		textWidget.addKeyListener(new KeyAdapter() {
			/*
			 * (non-Javadoc)
			 *
			 * @see
			 * org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt
			 * .events.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				StudioUIUtils.setKeySupport(e, textWidget, undoManager, supportCopyPaste);
				if (e.stateMask == SWT.CTRL && e.keyCode == ' ') {
					try {
						if (viewerConfig != null) {
							if (sourceViewer.getTextWidget().getCaretOffset() > 0) {
								RulesEditorContentAssistant entityContentAssistant = (RulesEditorContentAssistant) viewerConfig
										.getContentAssistant(sourceViewer);
								entityContentAssistant.showPossibleCompletions();
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (e.keyCode == SWT.F3) {
					ruleSource.openDeclaration();
				}
			}
		});
	}

	public static void createExtendedConfigurationForChoice(final DriverConfig driver,
			final ChannelFormFeederDelegate delegate, final String type, final String choiceValue) {
		final Choice choice = ChannelFactory.eINSTANCE.createChoice();
		final ExtendedConfiguration extdConfig = ChannelFactory.eINSTANCE.createExtendedConfiguration();
		for (PropertyConfiguration propConfig : delegate.getPropertiesForChoice(type, choiceValue)) {
			final SimpleProperty property = ModelFactory.eINSTANCE.createSimpleProperty();
			property.setName(propConfig.getPropertyName());
			property.setValue(propConfig.getDefaultValue());
			extdConfig.getProperties().add(property);
		}
		choice.setValue(choiceValue);
		choice.getExtendedConfiguration().add(extdConfig);
		driver.setChoice(choice);
	}

	public static ImageDescriptor getImageDescriptor(PROPERTY_TYPES propertyType) {
		switch (propertyType) {
		case INTEGER:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconInteger16.gif");
		case DOUBLE:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconReal16.gif");
		case LONG:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconLong16.gif");
		case BOOLEAN:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconBoolean16.gif");
		case DATE_TIME:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconDate16.gif");
		case STRING:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconString16.gif");
		case CONCEPT:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconConcept16.gif");
		case CONCEPT_REFERENCE:
			return EditorsUIPlugin.getImageDescriptor("/icons/iconConceptRef16.gif");
		default:
			return null;
		}
	}

    public static GvField createGvCheckboxField(Composite parent, String label,String defaultValue, ChannelFormEditor editor,PropertyConfiguration propertyConfiguration,String driverType, HashMap<Object, Object> controls) {
//		Label lField = PanelUiUtil.createLabel(parent, label);
    	propertyConfiguration.getValues();
     	GvField gvField = GvUiUtil.createCheckBoxGv(parent, defaultValue);
		setGvFieldListeners(gvField, SWT.Selection, editor,propertyConfiguration,driverType,controls);
		return gvField;

    }

    public static GvField createGvComboField(Composite parent, String label,ChannelFormEditor editor,PropertyConfiguration propertyConfiguration,String driverType, HashMap<Object, Object> controls) {
//		Label lField = PanelUiUtil.createLabel(parent, label);
    	propertyConfiguration.getValues();
     	GvField gvField = GvUiUtil.createComboGv(parent, propertyConfiguration.getDefaultValue());
		setGvFieldListeners(gvField, SWT.Selection, editor,propertyConfiguration,driverType,controls);
		return gvField;

    }

    protected static void setGvFieldListeners(GvField gvField, int eventType, ChannelFormEditor editor, PropertyConfiguration propertyConfiguration,String driverType, HashMap<Object, Object> controls) {
		gvField.setFieldListener(eventType, getListener(gvField.getField(), editor,propertyConfiguration,driverType,controls));
		gvField.setGvListener(getListener(gvField.getGvText(), editor,propertyConfiguration,driverType,controls));
    }

	public static Listener getListener(final Control field, final ChannelFormEditor editor, final PropertyConfiguration propertyConfiguration,final String driverType,final HashMap<Object, Object> controls) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {

				if (field instanceof Text) {
				//	SimpleProperty property = (SimpleProperty) field.getData();
				//  Text textGv=(Text)field;
					if(propertyConfiguration.getPropertyName().equalsIgnoreCase("Queue")){

					}

					String val = ((Text) field).getText();
					if (GvUtil.isGlobalVar(val)) {
						String gvVal = GvUtil.getGvDefinedValue(editor.getProject(), val);
						if (gvVal != null && (propertyConfiguration.getPropertyName().equalsIgnoreCase("EnableSecurity")
								|| propertyConfiguration.getPropertyName().equalsIgnoreCase("Credential")
								|| propertyConfiguration.getPropertyName().equalsIgnoreCase("SecurityRole") 
								|| propertyConfiguration.getPropertyName().equalsIgnoreCase("useSsl")
								|| propertyConfiguration.getPropertyName().equalsIgnoreCase("Trust_Type")
								)) {
							if (propertyConfiguration.getPropertyName().equalsIgnoreCase("useSsl") || propertyConfiguration.getPropertyName().equalsIgnoreCase("Trust_Type")) {
								updateAS3Security(controls, editor);
							}else{
								updateASChannelExtPropsSection(controls, editor);
							}
						}
					}

					field.setToolTipText(((Text)field).getText());
					field.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					Object fieldData = ((Text)field).getData();
					if (fieldData instanceof SimpleProperty) {
						SimpleProperty property = (SimpleProperty)fieldData;
						if (property.getValue() == null) {
							Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
									((Text)field).getText()) ;
							EditorUtils.executeCommand(editor, command);
							return;
						}
						EditorUtils.editorModifiedOnReset(property, editor);
						if (!property.getValue().equalsIgnoreCase(((Text)field).getText())) {
							Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
									((Text)field).getText()) ;
							EditorUtils.executeCommand(editor, command);
						}
					}
					validateField((Text)field, propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), propertyConfiguration.getPropertyName(),editor.getProject().getName());

				} else if (field instanceof Button) {
					SimpleProperty property = (SimpleProperty) field.getData();
					Button buttonGv=(Button)field;

					// This is hard coded check, to fix one CR 1-831VO3
					// {Support:TIBCO} Enhancement: defining a destination
					// against a JMS channel
					if (!property.getName().isEmpty()) {
						if (property.getName().equals("Queue")) {
							if (controls.get(driverType + CommonIndexUtils.DOT + "DurableSuscriberName") != null) {
								GvField cntrol = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "DurableSuscriberName");
								if (buttonGv.getSelection()) {
									cntrol.setEnabled(false);
								} else {
									cntrol.setEnabled(true);
								}
							}
							if (controls.get(driverType + CommonIndexUtils.DOT + "SharedSubscriptionName") != null) {
								GvField cntrol = (GvField) controls.get(driverType + CommonIndexUtils.DOT + "SharedSubscriptionName");
								if (buttonGv.getSelection()) {
									cntrol.setEnabled(false);
								} else {
									cntrol.setEnabled(true);
								}
							}
						}
						if (property.getName().equals("EnableSecurity")) {
							/*
							 * if (controls.get(driverType +
							 * CommonIndexUtils.DOT + "DurableSuscriberName") !=
							 * null) { Control cntrol = (Control)
							 * controls.get(driverType + CommonIndexUtils.DOT +
							 * "DurableSuscriberName"); if
							 * (buttonGv.getSelection()) {
							 * cntrol.setEnabled(false); } else {
							 * cntrol.setEnabled(true); } }
							 */
							updateASChannelExtPropsSection(controls, editor);
						}
						
						if (property.getName().equals("useSsl")) {
							updateAS3Security(controls, editor);
						}
					}
					String value = Boolean.toString(buttonGv.getSelection());
					if (property.getValue() == null) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), value);
						EditorUtils.executeCommand(editor, command);
						return;
					}
					editorModifiedOnReset(property, editor);
					if (!property.getValue().equalsIgnoreCase(value)) {
						Command command = new SetCommand(editor.getEditingDomain(), property, ModelPackage.eINSTANCE
								.getSimpleProperty_Value(), value);
						EditorUtils.executeCommand(editor, command);
					}
				} else if(field instanceof Combo){

					SimpleProperty property = (SimpleProperty) field.getData();
					Combo comboGv=(Combo)field;
					if (!property.getName().isEmpty()) {
						if (property.getName().equalsIgnoreCase("SecurityRole")
								|| property.getName().equalsIgnoreCase("Credential")
								|| property.getName().equalsIgnoreCase("Trust_Type")
								) {
							if (property.getName().equalsIgnoreCase("Trust_Type")) {
								updateAS3Security(controls, editor);
							} else {
								updateASChannelExtPropsSection(controls, editor);
							}
						}
					}
				}
			}
		};
		return listener;
	}

    public static GvField createGvTextField(Composite parent, String label, String defaultValue,ChannelFormEditor editor, PropertyConfiguration propertyConfiguration) {
    	GvField gvField = GvUiUtil.createTextGv(parent, defaultValue);
		setGvFieldListeners(gvField, SWT.Modify, editor,propertyConfiguration);
		return gvField;
    }

    public static GvField createGvPasswordTextField(Composite parent, String label, String defaultValue,ChannelFormEditor editor, PropertyConfiguration propertyConfiguration) {
    	GvField gvField = GvUiUtil.createPasswordGv(parent, defaultValue);
		setGvFieldListeners(gvField, SWT.Modify, editor,propertyConfiguration);
		return gvField;
    }

	public static Listener getListener(final Control field, final ChannelFormEditor editor, final PropertyConfiguration propertyConfiguration) {
		Listener listener = new Listener() {

			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				// TODO Auto-generated method stub
				if (field instanceof Text) {
					String val=((Text)field).getText();
					String gvVal=null;
					if(GvUtil.isGlobalVar(val)){
						gvVal=GvUtil.getGvDefinedValue(editor.getProject(),val);
					}
					if(!propertyConfiguration.isMask()){
						field.setToolTipText(((Text)field).getText());
					}
					//field.setToolTipText(((Text)field).getText());
					field.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
					Object fieldData = ((Text)field).getData();
					if (fieldData instanceof SimpleProperty) {
						SimpleProperty property = (SimpleProperty)fieldData;
						if (property.getValue() == null) {
							Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
									((Text)field).getText()) ;
							EditorUtils.executeCommand(editor, command);
							return;
						}
						EditorUtils.editorModifiedOnReset(property, editor);
						if(isPasswordProperty(propertyConfiguration.getPropertyName())){
							if (!property.getValue().equalsIgnoreCase(((Text)field).getText())) {
								Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
										PasswordUtil.getEncodedString(((Text)field).getText())) ;
								EditorUtils.executeCommand(editor, command);
								validateField((Text)field, "Password", propertyConfiguration.getDefaultValue(), propertyConfiguration.getDisplayName(),editor.getProject().getName());
							}

							return;
						}
						if (!property.getValue().equalsIgnoreCase(((Text)field).getText())) {
							Command command=new SetCommand(editor.getEditingDomain(),property,ModelPackage.eINSTANCE.getSimpleProperty_Value(),
									((Text)field).getText()) ;
							EditorUtils.executeCommand(editor, command);
						}
					}
					String displayNameInError = propertyConfiguration.getDisplayName();
					if(displayNameInError==null) {
						displayNameInError=propertyConfiguration.getPropertyName();
					}
					validateField((Text)field, propertyConfiguration.getPropertyConfigType(), propertyConfiguration.getDefaultValue(), displayNameInError, editor.getProject().getName());
				} else if (field instanceof Button) {

				}
			}
		};
		return listener;
	}


    protected static void setGvFieldListeners(GvField gvField, int eventType, ChannelFormEditor editor, PropertyConfiguration propertyConfiguration) {
		gvField.setFieldListener(eventType, getListener(gvField.getField(), editor,propertyConfiguration));
		gvField.setGvListener(getListener(gvField.getGvText(), editor,propertyConfiguration));
    }

	/**
	 * @param textField
	 * @param type
	 * @param deafultValue
	 * @param displayName
	 * @return
	 */
	static boolean validateField(Text textField, String type, String deafultValue,
			String displayName, String prjName) {
		final String problemMessage = validatePropertyValue(type, textField,
				deafultValue, displayName,prjName);
		if (problemMessage != null) {
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_RED));
			textField.setToolTipText(problemMessage);
			return false;
		} else if(problemMessage == null){
			textField.setForeground(Display.getDefault().getSystemColor(
					SWT.COLOR_BLACK));
			textField.setToolTipText("");
			return true;
		}
		return true;
	}

	/**
	 * @param type
	 * @param fieldName
	 * @param deafultValue
	 * @param propertyName
	 * @param propertyInstance
	 * @return
	 */
	private static String validatePropertyValue(String type, Text textField,
			String deafultValue, String propertyName, String projectName) {
		final String message = com.tibco.cep.studio.ui.util.Messages.getString(
				"invalid.property.entry", textField.getText(), propertyName,
				type);
		String text = textField.getText();
		boolean globalVar = false;
		if (text.length() > 4) {
			globalVar = GvUtil.isGlobalVar(text.trim());
		}
		if (globalVar) {
			// check if global var defined
			Map<String, GlobalVariableDescriptor> glbVars = DefaultResourceValidator
					.getGlobalVariableNameValues(projectName);

			GlobalVariableDescriptor gvd = glbVars.get(stripGvMarkers(text));
			if (gvd == null) {
				return Messages.getString("invalid.global.var.doesnotexist",
						text);
			}
			if (!gvd.getType().equalsIgnoreCase(type.intern())) {
				if (type.intern().equals("int")
						&& gvd.getType().equals("Integer")) {
					return null;
				}
				return Messages.getString("invalid.global.var.typemismatch",
						text, type.intern());
			}
			return null;
		}

		if (type.intern() == PROPERTY_TYPES.INTEGER.getName().intern()) {
			if (!StudioUIUtils.isNumeric(text)) {
				return message;
			}
		}

		if (type.equalsIgnoreCase("Integer")) {
			if (!StudioUIUtils.isNumeric(text)) {
				return message;
			}
		}
		if (type.intern() == PROPERTY_TYPES.DOUBLE.getName().intern()) {
			if (!isDouble(text)) {
				return message;
			}
		}
		if (type.intern() == PROPERTY_TYPES.LONG.getName().intern()) {
			if (!isLong(text)) {
				return message;
			}
		}
		return null;
	}

	private static String stripGvMarkers(String variable) {
		int firstIndex = variable.indexOf("%%");
		String stripVal = variable.substring(firstIndex + 2);
		stripVal = stripVal.substring(0, stripVal.indexOf("%%"));
		return stripVal;
	}

	/**
	 * Identifies whether or not to render the property on the UI.
	 * @param driverType
	 * @param propertyConfiguration
	 * @return
	 */
	public static boolean isHiddenProperty(String driverType, PropertyConfiguration propertyConfiguration) {
		if (DriverManagerConstants.DRIVER_KAFKA.equals(driverType)
				&& ("kafka.trusted.certs.folder".equals(propertyConfiguration.getPropertyName())
						|| "kafka.keystore.identity".equals(propertyConfiguration.getPropertyName())
						|| "kafka.truststore.password".equals(propertyConfiguration.getPropertyName()))) {
			return true;
		} else if (DriverManagerConstants.DRIVER_KAFKA_STREAMS.equals(driverType)
				&& ("kafka.trusted.certs.folder".equals(propertyConfiguration.getPropertyName())
						|| "kafka.keystore.identity".equals(propertyConfiguration.getPropertyName())
						|| "kafka.truststore.password".equals(propertyConfiguration.getPropertyName()))) {
			return true;
		}
		return false;
	}
	/**
	 * @param defEventText
	 * @return
	 */
	private static boolean validRuleFunction(Text defRuleFunctionText, String projectName) {
		if (!isValidRuleFunction(defRuleFunctionText.getText(), projectName)) {
			defRuleFunctionText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = Messages.getString("channel.destination.details.action.rulefucntion.error",
					defRuleFunctionText.getText());
			defRuleFunctionText.setToolTipText(problemMessage);
			return false;
		}
		return true;
	}

	/**
	 * @param ruleFunctionURI
	 * @return
	 */
	private static boolean isValidRuleFunction(String ruleFunctionURI, String projectName) {
		RuleElement ruleElement = IndexUtils.getRuleElement(projectName, ruleFunctionURI, ELEMENT_TYPES.RULE_FUNCTION);
		if (null!=ruleElement) {
			return true;
		}
		return false;
	}
}
