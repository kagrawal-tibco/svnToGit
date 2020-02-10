package com.tibco.cep.studio.ui.editors.display;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addHyperLinkFieldListener;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.createLinkField;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.DisplayProperties;
import com.tibco.cep.studio.core.util.DisplayUtils;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.rules.text.RulesTextContentProvider;
import com.tibco.cep.studio.ui.editors.rules.text.RulesTextLabelDecorator;
import com.tibco.cep.studio.ui.editors.utils.Messages;
import com.tibco.cep.studio.ui.forms.AbstractMasterDetailsFormViewer;
import com.tibco.cep.studio.ui.util.TableAutoResizeLayout;

public class DisplayFormDesignViewer extends AbstractMasterDetailsFormViewer {

	class DisplayViewerLabelProvider extends RulesTextLabelDecorator implements ITableLabelProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof DisplayProperties) {
				PropertyDefinition propDef = ((DisplayProperties) element).getPropertyDefinition();
				if (propDef != null) {
					return super.getImage(propDef);
				}
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof DisplayProperties) {
				return ((DisplayProperties) element).getTargetName();
			}
			return null;
		}
		
	}
	
	class DisplayViewerContentProvider implements ITreeContentProvider {

		@Override
		public void dispose() {
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof DisplayProperties) {
				List<DisplayProperties> properties = ((DisplayProperties) parentElement).getDisplayProperties();
				if (properties != null && properties.size() > 0) {
					return properties.toArray();
				}
			}
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}
		
	}
	
	protected static final String NAME_PROPERTY = "propertyName";
	protected static final String VALUE_PROPERTY = "displayText";

	private DisplayProperties displayProperties;
	private DisplayFormEditor displayEditor;
	private TableColumn propertyNameColumn;
//	private TableColumn displayValueColumn;
	private TableViewer displayTableViewer;
	private Hyperlink targetEntityLink;
	private Text targetEntityText;
	private Text entityDisplayText;
	private Text displayText;
	private Section detailsSection;
	private Text baseDisplayText;
	private Button hiddenButton;

	public DisplayFormDesignViewer(DisplayFormEditor displayFormEditor, DisplayProperties displayProperties) {
		this.displayEditor = displayFormEditor;
		this.displayProperties = displayProperties;
	}

	@Override
	protected void createGeneralPart(ScrolledForm form, FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR| Section.EXPANDED | Section.TWISTIE);
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(Messages.getString("GENERAL_SECTION_TITLE"));
		section.setDescription(Messages.getString("GENERAL_SECTION_DESCRIPTION"));
		
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		sectionClient.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;
		sectionClient.setLayoutData(gd);

		targetEntityLink = createLinkField(toolkit, sectionClient, Messages.getString("display.TargetEntity"));
		targetEntityText = toolkit.createText(sectionClient,"", SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		targetEntityText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		targetEntityText.setEnabled(false);
		targetEntityText.setText(this.displayProperties.getTargetName() != null? this.displayProperties.getTargetName() : "");
		
		addHyperLinkFieldListener(targetEntityLink, targetEntityText, displayEditor, displayEditor.getProject().getName(), false, false);

		toolkit.createLabel(sectionClient, Messages.getString("display.EntityDisplayText"));
		entityDisplayText = toolkit.createText(sectionClient,"", SWT.SINGLE | SWT.BORDER);
		entityDisplayText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		entityDisplayText.setText(this.displayProperties.getDisplayText() != null? this.displayProperties.getDisplayText() : "");
		entityDisplayText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				if (!displayProperties.getDisplayText().equals(entityDisplayText.getText())) {
					displayProperties.setDisplayText(entityDisplayText.getText());
					displayEditor.modified();
				}
			}
		});
		Locale locale = getLocale();

		toolkit.createLabel(sectionClient, Messages.getString("display.Language"));
		Text languageCodeText = toolkit.createText(sectionClient,"", SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		languageCodeText.setText(locale.getDisplayLanguage());
		languageCodeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		languageCodeText.setEnabled(false);
		
		toolkit.createLabel(sectionClient, Messages.getString("display.Country"));
		Text localeText = toolkit.createText(sectionClient,"", SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		localeText.setText(locale.getDisplayCountry());
		localeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		localeText.setEnabled(false);
		
		toolkit.createLabel(sectionClient, Messages.getString("display.Variant"));
		Text variantText = toolkit.createText(sectionClient,"", SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		variantText.setText(locale.getDisplayVariant());
		variantText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		variantText.setEnabled(false);
		
		
		createDisplayViewerPart(form, toolkit);
	}

	protected String lookupBaseDisplayText(DisplayProperties currentProp) {
		PropertyDefinition propDef = currentProp.getPropertyDefinition();
		if (propDef != null) {
			RuleParticipant owner = propDef.getOwner();
			Entity entity = IndexUtils.getEntity(owner.getOwnerProjectName(), displayProperties.getTargetName());
			String fullPath = owner.getFullPath();
			String fName = getFile().getFullPath().removeFileExtension().lastSegment();
			int idx = fName.indexOf('_');
			if (idx >= 0) {
				// this display model has a language/country variant.  First look at the base display model for base display text
				int lastIdx = fName.lastIndexOf('_');
				if (idx != lastIdx) {
					// there is both a lang and country variant.  Trim the last _xx part of the name and look for the 'parent' display model
					String basePath = fName.substring(0, lastIdx);
					basePath = entity.getFolder() + basePath + DisplayUtils.DISPLAY_SUFFIX;
					String text = getBaseText(currentProp, owner, basePath);
					if (text != null) {
						return text;
					}
				}
				String basePath = entity.getFullPath() + DisplayUtils.DISPLAY_SUFFIX;
				String text = getBaseText(currentProp, owner, basePath);
				if (text != null) {
					return text;
				}
			}

			entity = IndexUtils.getEntity(owner.getOwnerProjectName(), displayProperties.getTargetName());
			fullPath = owner.getFullPath();
			while (entity != null) {
				if (entity instanceof Concept) {
					fullPath = ((Concept) entity).getSuperConceptPath();
				} else if (entity instanceof Event) {
					fullPath = ((Event) entity).getSuperEventPath();
				}
				if (fullPath == null || fullPath.trim().length() == 0) {
					break;
				}
				String text = null;
				if (idx >= 0) {
					String suffix = fName.substring(idx);
					String dispPath = fullPath + suffix + DisplayUtils.DISPLAY_SUFFIX;
					text = getBaseText(currentProp, owner, dispPath);
					if (text != null) {
						return text;
					}
				}
				int lastIdx = fName.lastIndexOf('_');
				if (idx != lastIdx) {
					// there is both a lang and country variant.  Trim the last _xx part of the name and look for the 'parent' display model
					String suffix = fName.substring(lastIdx);
					String dispPath = fullPath + suffix + DisplayUtils.DISPLAY_SUFFIX;
					text = getBaseText(currentProp, owner, dispPath);
					if (text != null) {
						return text;
					}
				}
				String dispPath = fullPath + DisplayUtils.DISPLAY_SUFFIX;
				text = getBaseText(currentProp, owner, dispPath);
				if (text != null) {
					return text;
				}
				entity = IndexUtils.getEntity(owner.getOwnerProjectName(), fullPath);
			}
		}
		return null;
	}

	private String getBaseText(DisplayProperties currentProp,
			RuleParticipant owner, String dispPath) {
		IFile file;
		file = ResourcesPlugin.getWorkspace().getRoot().getProject(owner.getOwnerProjectName()).getFile(new Path(dispPath));
		if (file.exists()) {
			String baseText = getBasePropertyText(currentProp, file);
			if (baseText != null && baseText.trim().length() > 0) {
				return baseText;
			}
		}
		return null;
	}

	private String getBasePropertyText(DisplayProperties currentProp, IFile file) {
		InputStream contents = null;
		try {
			contents = file.getContents();
			Properties properties = new Properties();
			properties.load(contents);
			return properties.getProperty(currentProp.getTargetName()+DisplayUtils.DISPLAY_TEXT_SUFFIX);
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				contents.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Locale getLocale() {
		IFile file = getFile();
		String fName = file.getFullPath().removeFileExtension().lastSegment();
		int idx = fName.indexOf('_');
		if (idx >= 0) {
			/*String lc = fName.substring(idx+1);*/
			return DisplayUtils.getLocaleFromString(fName);
		}
		
		return Locale.getDefault();
	}

	private IFile getFile() {
		return displayEditor.getFile();
	}

	public void createPartControl(Composite container) {
		super.createPartControl(container, "Display Model: "+displayProperties.getTargetName(), null);
	}

	@Override
	protected void createMasterPart(IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent,	Section.NO_TITLE);
		Composite sectionClient = toolkit.createComposite(section);
		section.setClient(sectionClient);
		sectionClient.setLayout(new GridLayout(1, false));
		sectionClient.setLayoutData(new GridData(GridData.FILL_BOTH));
		createToolbar(sectionClient, false);
		Table displayTable = toolkit.createTable(sectionClient, SWT.FULL_SELECTION | SWT.SINGLE);
		displayTable.setLinesVisible(true);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = 300;
		displayTable.setLayoutData(gd);
		displayTable.setHeaderVisible(true);
		displayTable.setLinesVisible(false);

		propertyNameColumn = new TableColumn(displayTable, SWT.NONE);
		propertyNameColumn.setText(Messages.getString("Property"));
		propertyNameColumn.setWidth(200);
		propertyNameColumn.setMoveable(false);
		propertyNameColumn.setResizable(true);
		propertyNameColumn.setImage(EditorsUIPlugin.getDefault().getImage("icons/description_16x16.png"));
		
//		displayValueColumn = new TableColumn(displayTable, SWT.NONE);
//		displayValueColumn.setText(Messages.getString("DisplayText"));
//		displayValueColumn.setWidth(223);
//		displayValueColumn.setMoveable(false);
//		displayValueColumn.setResizable(true);
//		displayValueColumn.setImage(EditorsUIPlugin.getImageDescriptor("icons/description_16x16.png").createImage());
		
		displayTableViewer = new TableViewer(displayTable);
		displayTableViewer.setLabelProvider(new DisplayViewerLabelProvider());
		displayTableViewer.setContentProvider(new DisplayViewerContentProvider());
		displayTableViewer.setCellEditors(new CellEditor[] { new TextCellEditor(displayTable),null });
		displayTableViewer.setColumnProperties(new String[] { NAME_PROPERTY });
		displayTableViewer.getTable().setLinesVisible(false);
		displayTableViewer.setInput(displayProperties);
		displayTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof StructuredSelection) {
					Object firstElement = ((StructuredSelection) selection).getFirstElement();
					if (firstElement == null) {
						removeRowButton.setEnabled(false);
						updateDetailsPart(null);
						return;
					}
					updateDetailsPart((DisplayProperties)firstElement);
					removeRowButton.setEnabled(true);
					return;
				}
				removeRowButton.setEnabled(false);
			}
		});
//		tableEditor = DomainUtils.createTableViewer(viewer, getEditor()); 
		
		TableAutoResizeLayout autoTableLayout = new TableAutoResizeLayout(displayTable);
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		autoTableLayout.addColumnData(new ColumnWeightData(1));
		displayTable.setLayout(autoTableLayout);
		
		toolkit.paintBordersFor(sectionClient);
	}

	protected void updateDetailsPart(DisplayProperties displayProps) {
		if (displayProps == null) {
			displayText.setText("");
			baseDisplayText.setText("");
			hiddenButton.setSelection(false);
		} else {
			displayText.setText(displayProps.getDisplayText());
			String baseText = lookupBaseDisplayText(displayProps);
			if (baseText != null) {
				baseDisplayText.setText(baseText);
			} else {
				baseDisplayText.setText("");
			}
			hiddenButton.setSelection(displayProps.isHidden());
		}
	}

	protected void createDisplayViewerPart(final ScrolledForm form,final FormToolkit toolkit) {
		Section section = toolkit.createSection(form.getBody(), Section.TITLE_BAR );
		section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
		section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText(Messages.getString("display.section.properties"));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		section.setLayoutData(gd);
	}

	@Override
	protected void createDetailsPart(final IManagedForm managedForm, Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		detailsSection = toolkit.createSection(parent,ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE	| ExpandableComposite.EXPANDED);
		detailsSection.setText(Messages.getString("Details"));
		detailsSection.marginHeight = 10;
		detailsSection.addExpansionListener(new ExpansionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.ui.forms.events.ExpansionAdapter#expansionStateChanged(org.eclipse.ui.forms.events.ExpansionEvent)
			 */
			public void expansionStateChanged(ExpansionEvent e) {
				managedForm.getForm().reflow(true);
			}
		});
		Composite detailsComposite = toolkit.createComposite(detailsSection);
		detailsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		detailsComposite.setLayout(layout);
		detailsSection.setClient(detailsComposite);
		
		toolkit.createLabel(detailsComposite, Messages.getString("display.BaseDisplayText"));
		baseDisplayText = toolkit.createText(detailsComposite, "", SWT.SINGLE | SWT.BORDER);
		baseDisplayText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		baseDisplayText.setToolTipText(Messages.getString("display.BaseDisplayText.tooltip"));
		baseDisplayText.setEnabled(false);
		
		toolkit.createLabel(detailsComposite, Messages.getString("display.DisplayText"));
		displayText = toolkit.createText(detailsComposite, "", SWT.SINGLE | SWT.BORDER);
		displayText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		displayText.setToolTipText(Messages.getString("display.DisplayText.tooltip"));
		displayText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				DisplayProperties currentProp = getCurrentProp();
				if (currentProp != null && !currentProp.getDisplayText().equals(displayText.getText())) {
					currentProp.setDisplayText(displayText.getText());
					displayEditor.modified();
				}
			}
		});
		hiddenButton = toolkit.createButton(detailsComposite, Messages.getString("display.Hidden"), SWT.CHECK);
		hiddenButton.setToolTipText(Messages.getString("display.Hidden.tooltip"));
		hiddenButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				DisplayProperties currentProp = getCurrentProp();
				if (currentProp != null && currentProp.isHidden() != hiddenButton.getSelection()) {
					currentProp.setHidden(hiddenButton.getSelection());
					displayEditor.modified();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	protected DisplayProperties getCurrentProp() {
		ISelection selection = displayTableViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection).getFirstElement();
			if (firstElement instanceof DisplayProperties) {
				return (DisplayProperties) firstElement;
			}
		}
		return null;
	}
	
	class DisplayModelRulesTextContentProvider extends RulesTextContentProvider {
		
		public DisplayModelRulesTextContentProvider() {
		}
		
		@Override
		public Object[] getElements(Object inputElement) {
			Object[] elements = super.getElements(inputElement);
			
			Object[] displayModelElements = new Object[elements.length - displayProperties.getDisplayProperties().size()];
			int count = 0;
			for(Object element : elements) {
				if(element instanceof PropertyDefinition) {
					PropertyDefinition def = (PropertyDefinition)element;
					boolean isAdded = false;
					for(DisplayProperties dispProp : displayProperties.getDisplayProperties()) {
						if (def.getName().equals(dispProp.getTargetName())) {
							isAdded = true;
							break;
						}
					}
					if(!isAdded) {
						displayModelElements[count] = def;
						count++;
					}
				}
			}
			return displayModelElements;
		}	
	}

	@Override
	protected void add() {
		Entity targetEntity = displayEditor.getTargetEntity();
		// TODO : Set initial selections
		ListSelectionDialog selectionDialog = new ListSelectionDialog(Display.getDefault().getActiveShell(), targetEntity, new DisplayModelRulesTextContentProvider(), new RulesTextLabelDecorator(), "Select the properties to translate");
		int ret = selectionDialog.open();
		if (ret == IStatus.OK) {
			Object[] result = selectionDialog.getResult();
			for (Object object : result) {
				if (object instanceof PropertyDefinition) {
					DisplayProperties prop = new DisplayProperties((PropertyDefinition) object);
					prop.setTargetName(((PropertyDefinition) object).getName());
					displayProperties.getDisplayProperties().add(prop);
					displayEditor.modified();
				}
			}
			displayTableViewer.refresh();
		}

	}

	@Override
	protected void remove() {
		DisplayProperties currentProp = getCurrentProp();
		displayProperties.getDisplayProperties().remove(currentProp);
		displayEditor.modified();
		displayTableViewer.refresh();
	}

	@Override
	protected void duplicate() {
		// TODO Auto-generated method stub
		
	}

	
}
