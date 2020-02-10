package com.tibco.cep.studio.ui.util;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.text.edits.InsertEdit;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ResourceTransfer;
import org.eclipse.ui.texteditor.ITextEditor;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.EDIT_TYPES;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.functions.model.EMFModelJavaFunction;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.MemberElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedDecisionTableElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.RuleSourceMatch;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener;
import com.tibco.cep.studio.core.index.update.IndexResourceChangeListener.UpdateReferencedIndexJob;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.resources.JarEntryFile;
import com.tibco.cep.studio.core.rules.DefaultProblemHandler;
import com.tibco.cep.studio.core.rules.IProblemHandler;
import com.tibco.cep.studio.core.rules.RulesParserManager;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.validation.ValidationUtils;
import com.tibco.cep.studio.ui.AbstractFormEditor;
import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.actions.ValidateProjectAction;
import com.tibco.cep.studio.ui.dialog.DoNotAskAgainMessageDialog;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteEntry;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tibco.cep.studio.ui.preferences.StudioUIPreferenceConstants;
import com.tibco.cep.studio.ui.property.PropertyTypeCombo;
import com.tibco.cep.studio.ui.resources.JarEntryEditorInput;
import com.tibco.cep.studio.ui.widgets.DomainResourceSelector;
import com.tibco.cep.studio.ui.wizards.AbstractNewEntityWizard;
import com.tibco.cep.studio.ui.wizards.IDiagramEntitySelection;
import com.tibco.cep.studio.ui.wizards.NewConceptWizard;
import com.tibco.cep.studio.ui.wizards.NewEventWizard;
import com.tibco.cep.studio.ui.wizards.NewJavaSourceWizard;
import com.tibco.cep.studio.ui.wizards.NewJavaTaskResourceWizard;
import com.tibco.cep.studio.ui.wizards.NewRuleFunctionWizard;
import com.tibco.cep.studio.ui.wizards.NewRuleWizard;
import com.tibco.cep.studio.ui.wizards.NewTimeEventWizard;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEDeleteSelectedCommand;
import com.tomsawyer.interactive.swing.editing.tool.TSEPasteTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;

public class StudioUIUtils {

	public static final int ID_COLUMN = 1;
	public static final int EXPR_COLUMN = 2;
	public static int NO_OPTION_SELECTED=0;
	
	private static int status;
	
	public static final String AND_OPERATOR = "&&";
	public static EObject getEntity(IEditorInput input) {

		if (!(input instanceof FileEditorInput)) {
			return null;
		}
		FileEditorInput fei = (FileEditorInput) input;

		return IndexUtils.loadEObject(fei.getURI());
	}

	public static IEditorPart openEditor(Entity element) {
		IFile file = IndexUtils.getFile(element.getOwnerProjectName(), element);
		try {
			if (file != null) {
				if (!file.exists()) {
					MessageDialog.openError(new Shell(), "Error", "File '"+file.getName()+"' cannot be found.  Unable to open");
					return null;
				}
				return IDE.openEditor(StudioUIPlugin.getActivePage(), file);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static IEditorPart openEditor(Entity element, Entity subElement) {
		if (element == null || subElement == null) {
			return null;
		}
		IFile file = IndexUtils.getFile(element.getOwnerProjectName(), element);
		try {
			if (file != null) {
				if (!file.exists()) {
//					MessageDialog.openError(new Shell(), "Error", "File '"+file.getName()+"' cannot be found.  Unable to open");
					return null;
				}

//				if (subElement instanceof Destination) {
//					Destination destination =  (Destination) subElement;
//					ChannelFormEditorInput editorInput = new ChannelFormEditorInput(file, (Channel)element);
//					editorInput.setSelectedDestination(destination);
//					return IDE.openEditor(StudioUIPlugin.getActivePage(), editorInput, "com.tibco.cep.studio.ui.editors.editor.channel");
//				}

				return IDE.openEditor(StudioUIPlugin.getActivePage(), file);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * @param site
	 */
	public static void refreshPaletteAndOverview(final IEditorSite site) {
		if (site == null) {
			return;
		}
		PaletteView paletteView = (PaletteView)site.getPage().findView(PaletteView.ID);
		if (paletteView !=null) {
			paletteView.setType(PALETTE.NONE);
			StudioPaletteUI.resetPalette(paletteView);
		}
		refreshOverview(site, false, false);
		//Table Overview handling
//		Display.getDefault().asyncExec(new Runnable() {
//			public void run() {
//				final CommonOverview tableOverview =(CommonOverview) site.getPage().findView(CommonOverview.ID);
//				if (tableOverview == null) {
//					return;
//				}
//				tableOverview.resetOverview();
//			}});
	}

//	/**
//	 * @param site
//	 */
//	public static void showRuleDependencyViews(final IEditorSite site, boolean isExclusiveRuleEditor) {
//		IViewPart catalogFunctionsView = site.getPage().findView("com.tibco.cep.studio.ui.views.CatalogFunctionsView");
//		IViewPart globalVarView = site.getPage().findView("com.tibco.cep.studio.ui.views.GlobalVariablesView");
//		IViewPart outlineView = site.getPage().findView(IPageLayout.ID_OUTLINE);
//		try {
//			if (catalogFunctionsView == null) {
//				site.getPage().showView("com.tibco.cep.studio.ui.views.CatalogFunctionsView");
//			}
//			if (globalVarView == null) {
//				site.getPage().showView("com.tibco.cep.studio.ui.views.GlobalVariablesView");
//			}
//			if (outlineView == null && isExclusiveRuleEditor) {
//				site.getPage().showView(IPageLayout.ID_OUTLINE);
//			}
//		} catch (PartInitException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public static IEditorPart openSharedEntity(SharedElement see , String projectName) {
		if (see == null || projectName == null || projectName.trim().length() ==0) return null;
		String entryPath = see.getEntryPath() + see.getFileName();
		String libRef = see.getArchivePath();
		IStorage jarEntryFile = new JarEntryFile(libRef , entryPath , projectName);
		IStorageEditorInput stInput = new JarEntryEditorInput((JarEntryFile)jarEntryFile, projectName);
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (page == null) return null;
//		IEditorReference[] editorRefs = page.getEditorReferences();
//		try {
//			for (IEditorReference editorReference : editorRefs) {
//				IEditorInput editorInput = editorReference.getEditorInput();
//				if (stInput.equals(editorInput)) {
//					return editorReference.getEditor(true);
//				}
//			}
//		} catch (Exception e) {
//		}
		IEditorDescriptor ied = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(entryPath);
		if (ied == null) return null;
		try {
			IEditorPart editorPart = page.openEditor(stInput, ied.getId(), true);
			return editorPart;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param se
	 * @return
	 */
	public static IStorageEditorInput getSharedElementJarInput(SharedElement se) {
		String libRef = se.getArchivePath();
		final String entryName = se.getFileName();
		String entryPath = se.getEntryPath() + entryName;
		String projectName = null;
		EObject root = StudioUIUtils.getRoot(se);
		if (root instanceof DesignerProject) {
			projectName = ((DesignerProject)root).getName();
		}
		JarEntryFile jarEntryFile = new JarEntryFile(libRef ,entryPath , projectName);
		IStorageEditorInput input = new JarEntryEditorInput(jarEntryFile, projectName);
		return input;
	}

	/**
	 * @param shell
	 * @param title
	 * @param message
	 */
	public static void openError(final Shell shell, final String title, final String message) {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				MessageDialog.openError(shell, title, message);
			}
		}, false);
	}

	public static IEditorPart openEditor(String prodlineName, TypeElement element) {
		if (element instanceof SharedElement) {
			return openSharedElement((SharedElement)element, false);
		}
		IFile file = IndexUtils.getFile(prodlineName, element);
		try {
			if (!file.exists()) {
				MessageDialog.openError(new Shell(), "Error", "File '"+file.getName()+"' cannot be found.  Unable to open");
				return null;
			}
			if (file != null) {
				return IDE.openEditor(StudioUIPlugin.getActivePage(), file);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static IEditorPart openEditor(TypeElement element) {
		DesignerProject designerProject = IndexUtils.getDesignerProject(element);
		if (designerProject == null) {
			// the element likely is in-memory and not part of the index
			if (element instanceof RuleElement) {
				return openEditor(((RuleElement)element).getIndexName(), element);
			}
			return null;
		}
		return openEditor(designerProject.getName(), element);
	}

	public static IEditorPart openEditor(IProject project, TypeElement element) {
		IFile file = IndexUtils.getFile(project.getName(), element);
		try {
			if (!file.exists()) {
				MessageDialog.openError(new Shell(), "Error", "File '"+file.getName()+"' cannot be found.  Unable to open");
				return null;
			}
			if (file != null) {
				return IDE.openEditor(StudioUIPlugin.getActivePage(), file);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static EObject getRoot(EObject child) {
		while (child.eContainer() != null) {
			EObject immediateRoot = child.eContainer();
			if (immediateRoot == null && child instanceof DesignerProject) {
				return child;
			}
			child = immediateRoot;
		}
		return child;
	}
	/**
	 * opens the Non Entity Resources for example : .txt, .xsd files
	 * @param se
	 * @return
	 */
	public static IEditorPart openSharedElement(SharedElement se, boolean wrapThread) {

		String libRef = se.getArchivePath();
		final String entryName = se.getFileName();
		String entryPath = se.getEntryPath() + entryName;
		String projectName = null;
		EObject root = getRoot(se);
		if (root instanceof DesignerProject) {
			projectName = ((DesignerProject)root).getName();
		}
		JarEntryFile jarEntryFile = new JarEntryFile(libRef ,entryPath , projectName);
		final String editorId ="org.eclipse.ui.DefaultTextEditor";

		// create IStorageInput Editor
		final IStorageEditorInput input = new JarEntryEditorInput(jarEntryFile, projectName);
		IEditorDescriptor defIed = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(entryPath);
		if (defIed == null) {
			defIed = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor("test.txt"); // lookup default text editor
		}
		final IEditorDescriptor ied = defIed;
		if (wrapThread) {
			openEditor(input, ied, editorId);
		} else {
			invokeOnDisplayThread(new Runnable() {

				@Override
				public void run() {
					openEditor(input, ied, editorId);
				}
			}, false);
		}
		return null;
	}
	
	public static void openEditor (IEditorInput input, IEditorDescriptor ied , String editorId) {
		try {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, ied.getId(), true);
			if (editorPart != null && editorPart instanceof AbstractFormEditor) {
				((AbstractFormEditor)editorPart).setEnabled(false);
			} else {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, editorId, true);
			}
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public static IEditorPart openElement(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof ElementMatch) {
			object = ((ElementMatch) object).getMatchedElement();
		}
		if (object instanceof EMFModelJavaFunction) {
			object = ((EMFModelJavaFunction) object).getModel();
		}
		if (object instanceof PropertyDefinition) {
			PropertyDefinition def = (PropertyDefinition) object;
			object = def.eContainer();
		}
		if (object instanceof Destination) {
			Destination def = (Destination) object;
			object = def.eContainer().eContainer();
		}
		if (object instanceof JavaSource && ((EObject) object).eContainer() instanceof SharedElement) {
			object = ((JavaSource) object).eContainer();
		}
		if (object instanceof SharedRuleElement) {
			SharedRuleElement ruleElement = (SharedRuleElement) object;
			EObject root = IndexUtils.getRootContainer(ruleElement);
			String projectName = ruleElement.getIndexName();
			if (root instanceof DesignerProject) {
				projectName = ((DesignerProject) root).getName();
			}
			JarEntryFile jarEntryFile = new JarEntryFile(ruleElement.getArchivePath(), ruleElement.getEntryPath()+ruleElement.getFileName(), projectName);
			JarEntryEditorInput input = new JarEntryEditorInput(jarEntryFile, projectName);
			IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
			IEditorDescriptor ed = registry.getDefaultEditor(((SharedRuleElement) object).getFileName());

			try {
				IEditorPart openEditor = IDE.openEditor(StudioUIPlugin.getActivePage(), input, ed.getId());
				if (openEditor instanceof AbstractStudioResourceEditorPart) {
					// set editor read only
					((AbstractStudioResourceEditorPart)openEditor).setEnabled(false);
				}
				return openEditor;
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} else if (object instanceof SharedEntityElement) {
			((SharedEntityElement) object).getEntity();
			EObject root = getRoot((SharedEntityElement) object);
			DesignerProject rootProject = null;
			String projectName = null;
			if (root instanceof DesignerProject){
				rootProject = (DesignerProject)root;
				projectName = rootProject.getName();
			}
			IEditorPart editorPart = openSharedEntity((SharedEntityElement) object,projectName);
			//IEditorPart editorPart = StudioUIUtils.openElement(((SharedEntityElement) element).getSharedEntity());

			if (editorPart instanceof AbstractStudioResourceEditorPart) {
				// set editor read only
				((AbstractStudioResourceEditorPart)editorPart).setEnabled(false);
			}
			return editorPart;
		}  else if (object instanceof SharedDecisionTableElement) {
			SharedDecisionTableElement se = (SharedDecisionTableElement)object;
			se.getImplementation();
			EObject root = getRoot((SharedDecisionTableElement) object);
			DesignerProject rootProject = null;
			String projectName = null;
			if (root instanceof DesignerProject){
				rootProject = (DesignerProject)root;
				projectName = rootProject.getName();
			}
			IEditorPart editorPart = openSharedEntity(se, projectName);
			if (editorPart instanceof AbstractStudioResourceEditorPart) {
				// set editor read only
				((AbstractStudioResourceEditorPart)editorPart).setEnabled(false);
			}
			return editorPart;
		} else if (object instanceof SharedElement) {
				SharedElement se = (SharedElement)object;
				return openSharedElement(se, true);
		}
		if (object instanceof EntityElement) {
			Entity entity = ((EntityElement)object).getEntity();
			return openEditor(entity);
		}
		if (object instanceof Entity) {
			return openEditor((Entity) object);
		}
		if (object instanceof EnterpriseArchive) {
			EnterpriseArchive resource = (EnterpriseArchive) object;
			String projectName = resource.getOwnerProjectName();
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			String fileLocation = resource.getFolder()+resource.getName()+"."+IndexUtils.getFileExtension(ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
			IFile file = project.getFile(new Path(fileLocation));
			try {
				if (file.exists()) {
					return IDE.openEditor(StudioUIPlugin.getActivePage(), file);
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		if (object instanceof RuleSourceMatch) {
			EObject container = ((RuleSourceMatch) object).getContainingRule();
			IEditorPart editor = null;
			if (container instanceof TypeElement) {
				editor = openEditor((TypeElement)container);
			} else if (container instanceof Entity) {
				editor = openEditor((Entity)container);
			}
			if (editor != null) {
				Object adapter = editor.getAdapter(ITextEditor.class);
				if (adapter instanceof ITextEditor) {
					((ITextEditor)adapter).selectAndReveal(((RuleSourceMatch) object).getOffset(), ((RuleSourceMatch) object).getLength());
				}
			}
			return editor;
		}
		if (object instanceof TypeElement) {
			return openEditor((TypeElement) object);
		} else if (object instanceof MemberElement) {
			DesignerElement context = IndexUtils.getVariableContext((EObject) object);
			IEditorPart editor = null;
			if (context instanceof EntityElement) {
				editor = openEditor(((EntityElement) context).getEntity());
			} else if (context instanceof TypeElement) {
				editor = openEditor((TypeElement) context);
			}
			Object adapter = editor.getAdapter(ITextEditor.class);
			if (adapter instanceof ITextEditor) {
				((ITextEditor)adapter).selectAndReveal(((MemberElement) object).getOffset(), ((MemberElement) object).getLength());
			}
			return editor;
		} else if (object instanceof ElementReference) {
			DesignerElement context = IndexUtils.getVariableContext((EObject) object);
			IEditorPart editor = null;
			if (context instanceof EntityElement) {
				editor = openEditor(((EntityElement) context).getEntity());
			} else if (context instanceof TypeElement) {
				editor = openEditor((TypeElement) context);
			}
			if (editor == null) {
				return null;
			}
			Object adapter = editor.getAdapter(ITextEditor.class);
			if (adapter instanceof ITextEditor) {
				((ITextEditor)adapter).selectAndReveal(((ElementReference) object).getOffset(), ((ElementReference) object).getLength());
			}
			return editor;
		} else if (object instanceof IFile) {
			try {
				if (!((IFile)object).exists()) {
					MessageDialog.openError(new Shell(), "Error", "File '"+((IFile) object).getName()+"' cannot be found.  Unable to open");
					return null;
				}
				return IDE.openEditor(StudioUIPlugin.getDefault().getActivePage(), (IFile) object);
			} catch (PartInitException e) {
			
				e.printStackTrace();
			}
		} else if (object != null) {
			/*
			SharedElement se = (SharedElement)object;
			String libRef = se.getArchivePath();
			String entryName = se.getName();
			*/

			System.out.println("Unable to open "+object);

		}
		return null;
	}

	/**
	 * @param canvas
	 */
	public static void resetCanvasAndPalette(final DrawingCanvas canvas) {
		if (canvas == null) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				resetCanvasDefaultSelection(canvas);
				resetPaletteSelection();
			}
		});
	}

	/**
	 * @param canvas
	 */
	public static void resetCanvasDefaultSelection(DrawingCanvas canvas) {
		canvas.getToolManager().setActiveTool(TSViewingToolHelper.getSelectTool(canvas.getToolManager()));
	}

	/**
	 * Reset Palette Selection
	 */
	public static void resetPaletteSelection() {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(PaletteView.ID);
				if (view != null) {
					PaletteView paletteView = (PaletteView) view;
					Palette palette = paletteView.getPalette();
					if (palette.getCurrentSelection()!= null) {
						palette.getCurrentSelection().setState(PaletteEntry.STATE_NOT_SELECTED);
					}
				}
			}
		}, false);
	}

	/**
	 * @param stateMachine
	 * @return
	 */
	public static boolean isValidOwnerConcept(StateMachine stateMachine) {
		Concept concept = stateMachine.getOwnerConcept();
		String stateMachinePath = stateMachine.getFullPath();
		if (concept!= null) {
			for(String path: concept.getStateMachinePaths()) {
				if (path.intern() == stateMachinePath.intern()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param se
	 */
	public static void updateStateMachineRuleSymbols(StateEntity se) {
		if (se instanceof StateSubmachine) {
			StateSubmachine ssm = (StateSubmachine)se;
			updateStateRulesSymbols(ssm);
		}
		else if (se instanceof StateComposite) {
			StateComposite sc = (StateComposite)se;
			updateStateRulesSymbols(sc);
			for (StateEntity s : sc.getStateEntities()) {
				updateStateMachineRuleSymbols(s);
			}
		} else if (se instanceof StateStart) {
			StateStart start = (StateStart)se;
			if (start.getExitAction() != null)
				start.getExitAction().getSymbols().getSymbolMap().clear();

		} else if (se instanceof StateEnd) {
			StateEnd end = (StateEnd)se;
			if (end.getEntryAction()!= null)
				end.getEntryAction().getSymbols().getSymbolMap().clear();

		} else if (se instanceof State) {
			State s = (State)se;
			updateStateRulesSymbols(s);
		}
	}

	/**
	 * @param stateMachine
	 * @param resource
	 */
	public static void updateStateTransitionRulesSymbols(StateMachine stateMachine, String conceptPath) {
		for (StateTransition stateTransition : stateMachine.getStateTransitions()) {
			if (stateTransition.getGuardRule() !=null) {
				removeTransitionRule(stateTransition, conceptPath);
			}
		}
	}

	/**
	 * @param stateTransition
	 * @param conceptPath
	 */
	public static void removeTransitionRule(StateTransition stateTransition, String conceptPath) {
		List<Symbol> symbols = new ArrayList<Symbol>();
		for(Symbol symbol:stateTransition.getGuardRule().getSymbols().getSymbolList()) {
			if (symbol.getType().equals(conceptPath)) {
				symbols.add(symbol);
			}
		}
		Symbol[] symbolArr = new Symbol[symbols.size()];
		symbols.toArray(symbolArr);
		if (stateTransition.getGuardRule().getSymbols().getSymbolMap().size() > 0) {
			for(Symbol symbol:symbolArr) {
				stateTransition.getGuardRule().getSymbols().getSymbolMap().remove(symbol.getIdName());
			}
		}
	}

	/**
	 * @param s
	 */
	public static void updateStateRulesSymbols(State s) {
		if (s.isInternalTransitionEnabled()) {
			if (s.getInternalTransitionRule() !=null)
				s.getInternalTransitionRule().getSymbols().getSymbolMap().clear();
		}
		if (s.getEntryAction()!=null)
			s.getEntryAction().getSymbols().getSymbolMap().clear();
		if (s.getExitAction() != null)
			s.getExitAction().getSymbols().getSymbolMap().clear();
		if (s.getTimeoutAction() !=null)
			s.getTimeoutAction().getSymbols().getSymbolMap().clear();
		if (s.getTimeoutExpression()!=null)
			s.getTimeoutExpression().getSymbols().getSymbolMap().clear();
	}

	/**
	 * @param path
	 * @param projectName
	 * @param symbols
	 */
	public static void addSymbol(String path, String projectName, Map<String, Symbol> symbols) {
		Entity entity =IndexUtils.getEntity(projectName, path);
		if (entity != null) {
			Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
			symbol.setType(path);
			symbol.setIdName(entity.getName().toLowerCase());
			symbols.put(symbol.getIdName(), symbol);
		}
	}

	/**
	 * @param path
	 * @param projectName
	 * @param symbol
	 */
	public static void updateSymbol(String path, String projectName, Symbol symbol) {
		Entity entity =IndexUtils.getEntity(projectName, path);
		if (entity != null) {
			symbol.setType(path);
			symbol.setIdName(entity.getName().toLowerCase());
		}
	}

	/**
	 * @param type
	 * @param oldIdName
	 * @param newIdName
	 * @param compilable
	 * @return
	 */
	public static boolean updateSymbol(String type, String oldIdName, String newIdName, Compilable compilable) {
		if (!oldIdName.equalsIgnoreCase(newIdName)) {
			for(Symbol symbol:compilable.getSymbols().getSymbolList()) {
				if (symbol.getIdName().equalsIgnoreCase(oldIdName)) {
					symbol.setIdName(newIdName);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<String> getSubDomains(String superPath, String ownerProjectName) {
	    List<String> subDomainPaths = new ArrayList<String>();
		List<Entity> domainList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.DOMAIN);
		traverseSubDomains(subDomainPaths, domainList, superPath, ownerProjectName);
		return subDomainPaths;
	}

	/**
	 * @param subDomainPaths
	 * @param domainList
	 * @param superPath
	 * @param ownerProjectName
	 */
	public static void traverseSubDomains( List<String> subDomainPaths,
			                                List<Entity> domainList ,
			                                String superPath,
			                                String ownerProjectName) {
		for(Entity entity: domainList) {
			Domain domain = (Domain)entity;
			if (domain.getSuperDomainPath() != null &&
					domain.getSuperDomainPath().equals(superPath)) {
				subDomainPaths.add(domain.getFullPath());
				traverseSubDomains(subDomainPaths, domainList, domain.getFullPath(), ownerProjectName);
			}
		}
	}

	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<String> getImmediateSubDomains( String superPath, String ownerProjectName) {
		List<Entity> domainList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.DOMAIN);
		List<String> subDomainPaths = new ArrayList<String>();
		for(Entity entity: domainList) {
			Domain domain = (Domain)entity;
			if (domain.getSuperDomainPath() != null &&
					domain.getSuperDomainPath().equals(superPath)) {
				subDomainPaths.add(domain.getFullPath());
			}
		}
		return subDomainPaths;
	}

	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<String> getSubConcepts(String superPath, String ownerProjectName) {
	    List<String> subConceptPaths = new ArrayList<String>();
		List<Entity> conceptList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.CONCEPT);
		traverseSubConcepts(subConceptPaths, conceptList, superPath, ownerProjectName);
		return subConceptPaths;
	}

	/**
	 * @param subConceptPaths
	 * @param conceptList
	 * @param superPath
	 * @param ownerProjectName
	 */
	public static void traverseSubConcepts( List<String> subConceptPaths,
			                                List<Entity> conceptList ,
			                                String superPath,
			                                String ownerProjectName) {
		for(Entity entity: conceptList) {
			Concept concept = (Concept)entity;
			if (concept.getSuperConceptPath() != null &&
					concept.getSuperConceptPath().equals(superPath)) {
				subConceptPaths.add(concept.getFullPath());
				traverseSubConcepts(subConceptPaths, conceptList, concept.getFullPath(), ownerProjectName);
			}
		}
	}

	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<String> getSubEvents(String superPath, String ownerProjectName) {
	    List<String> subEventPaths = new ArrayList<String>();
		List<Entity> eventList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.SIMPLE_EVENT);
		traverseSubEvents(subEventPaths, eventList, superPath, ownerProjectName);
		return subEventPaths;
	}

	/**
	 * @param subEventPaths
	 * @param eventList
	 * @param superPath
	 * @param ownerProjectName
	 */
	public static void traverseSubEvents( List<String> subEventPaths,
			                                List<Entity> eventList ,
			                                String superPath,
			                                String ownerProjectName) {
		for(Entity entity: eventList) {
			Event event = (Event)entity;
			if (event.getSuperEventPath() != null &&
					event.getSuperEventPath().equals(superPath)) {
				subEventPaths.add(event.getFullPath());
				traverseSubEvents(subEventPaths, eventList, event.getFullPath(), ownerProjectName);
			}
		}
	}

	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<PropertyDefinition> getSubConceptProperties(String superPath, String ownerProjectName) {
		List<Entity> conceptList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.CONCEPT);
		List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();
		traverseSubConceptProperties(conceptList, superPath, ownerProjectName, list);
		return list;
	}

	
	
	
	
	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<PropertyDefinition> getSubScorecardProperties(String superPath, String ownerProjectName) {
		List<Entity> scorecardList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.SCORECARD);
		List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();
		traverseSubScorecardProperties(scorecardList, superPath, ownerProjectName, list);
		return list;
	}

	/**
	 * @param conceptList
	 * @param superPath
	 * @param ownerProjectName
	 */
	public static void traverseSubConceptProperties(
			                                List<Entity> conceptList ,
			                                String superPath,
			                                String ownerProjectName, List<PropertyDefinition> list) {
		for(Entity entity: conceptList) {
			Concept concept = (Concept)entity;
			if (concept.getSuperConceptPath() != null &&
					concept.getSuperConceptPath().equals(superPath)) {
				list.addAll(concept.getProperties());
				traverseSubConceptProperties(conceptList, concept.getFullPath(), ownerProjectName, list);
			}
		}
	}

	
	
	
	
	/**
	 * @param conceptList
	 * @param superPath
	 * @param ownerProjectName
	 */
	public static void traverseSubScorecardProperties(
			                                List<Entity> scorecardList ,
			                                String superPath,
			                                String ownerProjectName, List<PropertyDefinition> list) {
		for(Entity entity: scorecardList) {
			Scorecard scorecard = (Scorecard)entity;
			if (scorecard.getSuperConceptPath() != null &&
					scorecard.getSuperConceptPath().equals(superPath)) {
				list.addAll(scorecard.getProperties());
				traverseSubScorecardProperties(scorecardList, scorecard.getFullPath(), ownerProjectName, list);
			}
		}
	}
	/**
	 * @param superPath
	 * @param ownerProjectName
	 * @return
	 */
	public static List<PropertyDefinition> getSubEventProperties(String superPath, String ownerProjectName) {
		List<Entity> eventList = IndexUtils.getAllEntities(ownerProjectName, ELEMENT_TYPES.SIMPLE_EVENT);
		List<PropertyDefinition> list = new ArrayList<PropertyDefinition>();
		traverseSubEventProperties(eventList, superPath, ownerProjectName, list);
		return list;
	}

	/**
	 * @param eventList
	 * @param superPath
	 * @param ownerProjectName
	 */
	public static void traverseSubEventProperties(
			                                List<Entity> eventList ,
			                                String superPath,
			                                String ownerProjectName, List<PropertyDefinition> list) {
		for(Entity entity: eventList) {
			Event event = (Event)entity;
			if (event.getSuperEventPath() != null &&
					event.getSuperEventPath().equals(superPath)) {
				list.addAll(event.getProperties());
				traverseSubEventProperties(eventList, event.getFullPath(), ownerProjectName, list);
			}
		}
	}

	/**
	 * @param entity
	 * @return
	 */
	public static String getStateGraphPath(StateEntity entity) {
		List<String> statesList = new ArrayList<String>();
		getStateEntityPath(entity,statesList);
		if(statesList.size()==0)
			return null;
		else{
			String path = statesList.get(0);
			for(int index = 1; index < statesList.size() ; index++) {
				path = statesList.get(index)+"/"+ path;
			}
			return path;
		}
	}

	/**
	 * @param entity
	 * @param stateMachine
	 * @param oldTag
	 * @param newTag
	 * @return
	 */
	public static String getStateGraphPath(StateEntity entity,
			                               StateMachine stateMachine,
			                               String oldTag,
			                               String newTag) {
		List<String> statesList = new ArrayList<String>();
		getStateEntityPath(entity,statesList);
		if(statesList.size()==0)
			return null;
		else{
			String path = statesList.get(0);
			for(int index = 1; index < statesList.size() ; index++) {
				path = statesList.get(index) +"/"+ path;
			}
			String smPath = stateMachine.getFullPath() + "." + IndexUtils.getFileExtension(stateMachine);
			path = path.replace(smPath  + "/" + oldTag , smPath + "/" + newTag );
			return path;
		}
	}

	/**
	 * @param entity
	 * @param statesList
	 */
	public static void getStateEntityPath(StateEntity entity, List<String> statesList) {
		if (entity == null) {
			return;
		}
		if (entity instanceof StateMachine) {
			StateMachine stateMachine = (StateMachine) entity;
			statesList.add(stateMachine.getFullPath()+"."+IndexUtils.getFileExtension(stateMachine));
			return;
		} else {
			statesList.add( entity.getName());
			if (entity.eContainer() !=null && entity.eContainer() instanceof StateEntity) {
				getStateEntityPath((StateEntity)entity.eContainer(),statesList);
			}
		}
	}

	/**
	 * @param entity
	 * @return
	 */
	public static String getStateGraphRelativePath(StateEntity entity) {
		List<String> statesList = new ArrayList<String>();
		getStateEntityRelatuvePath(entity,statesList);
		String path = statesList.get(0);
		for(int index = 1; index < statesList.size() ; index++) {
			path = statesList.get(index)+"/"+ path;
		}
		return path;
	}

	/**
	 * @param entity
	 */
	public static void getStateEntityRelatuvePath(StateEntity entity, List<String> statesList) {
		if (entity instanceof StateMachine) {
			return;
		}else{
			statesList.add( entity.getName());
			if (entity.eContainer() !=null && entity.eContainer() instanceof StateEntity) {
				getStateEntityRelatuvePath((StateEntity)entity.eContainer(),statesList);
			}
		}
	}

	/**
	 *
	 * @param cellValue
	 * @param dataType
	 * @return
	 */
	public static DomainEntry parseValuesToDomainEntry(String cellValue,
			                                           DOMAIN_DATA_TYPES dataType) {
		if (cellValue == null ) return null;
		if (DOMAIN_DATA_TYPES.STRING.equals(dataType)) {
			// it will be a single value in case of String
			Single singleValue = DomainFactory.eINSTANCE.createSingle();
//			if (!(cellValue.startsWith("\"") && cellValue.endsWith("\""))) {
//				cellValue = "\"" + cellValue + "\"";
//			}
			singleValue.setValue(cellValue);
			return singleValue;
		} else if (DOMAIN_DATA_TYPES.BOOLEAN.equals(dataType)) {
			// boolean type can be only single valued
			Single singleValue = DomainFactory.eINSTANCE.createSingle();
			singleValue.setValue(cellValue.trim());
			return singleValue;
		} else {
			// all other data types can be range valued, for example , int , long , double
			// parse cell value for operator
			// if both ">" && "<" operators are present
			String trimmedValue = cellValue.trim();
			int index1 = trimmedValue.indexOf(">");
			int index2 = trimmedValue.indexOf("<");
			int index3 =  trimmedValue.indexOf("&&");
			if (index1 != -1 &&  index2 != -1 && index3 != -1) {
				// range value
				// split
				String[] splValues = cellValue.split(AND_OPERATOR);
				if (splValues == null || splValues.length != 2) {
					// not a valid Range Value
					return null;
				}
				String leftVal = splValues[0].trim();
				String rightVal = splValues[1].trim();
				boolean isLowerBound = false;
				Range rangeVal = DomainFactory.eINSTANCE.createRange();

				if (leftVal.startsWith(">")) {
					isLowerBound = true;
					boolean isLBIncluded = false;
					leftVal = leftVal.substring(1).trim();
					if (leftVal.startsWith("=")) {
						leftVal = leftVal.substring(1).trim();
						isLBIncluded = true;
					}
					rangeVal.setLower(leftVal);
					rangeVal.setLowerInclusive(isLBIncluded);
					//rangeVal.set
				} else if (leftVal.startsWith("<")) {
					boolean isUBIncluded = false;
					leftVal = leftVal.substring(1).trim();
					if (leftVal.startsWith("=")) {
						leftVal = leftVal.substring(1).trim();
						isUBIncluded = true;
					}
					rangeVal.setUpper(leftVal);
					//rangeVal.setMultiple(true);
					rangeVal.setUpperInclusive(isUBIncluded);

				} else {
					// not a right cell value
					return null;
				}

				if (rightVal.startsWith(">")) {
					if (isLowerBound) {
						// not a valid range value
						return null;
					} else {
						boolean isLBIncluded = false;
						rightVal = rightVal.substring(1).trim();
						if (rightVal.startsWith("=")) {
							rightVal = rightVal.substring(1).trim();
							isLBIncluded = true;
						}
						rangeVal.setLower(rightVal);
						rangeVal.setLowerInclusive(isLBIncluded);
						//rangeVal.setMultiple(true);
						return rangeVal;
					}
				} else if (rightVal.startsWith("<")) {
					if (!isLowerBound) {
						// not a valid range value
						return null;
					} else {

						boolean isUBIncluded = false;
						rightVal = rightVal.substring(1).trim();
						if (rightVal.startsWith("=")) {
							rightVal = rightVal.substring(1).trim();
							isUBIncluded = true;
						}
						rangeVal.setUpper(rightVal);
						rangeVal.setUpperInclusive(isUBIncluded);
						//rangeVal.setMultiple(true);
						return rangeVal;
					}

				} else {
					// not a valid range value
					return null;
				}


			} else if (index1 != -1 ||  index2 != -1) {
				// range value
				String val = null;
				if (index1 != -1) {
					val = trimmedValue.substring(index1 + 1).trim();
					boolean lbIncluded = false;
					if (val.startsWith("=")) {
						val = val.substring(1);
						lbIncluded = true;
					}
					val = val.trim();
					Range rangeVal = DomainFactory.eINSTANCE.createRange();
					rangeVal.setLower(val);
					rangeVal.setUpper("Undefined");
					//rangeVal.setMultiple(true);
					rangeVal.setLowerInclusive(lbIncluded);
					return rangeVal;

				} else {
					val = trimmedValue.substring(index2 + 1).trim();
					boolean upBoundIncluded = false;
					if (val.startsWith("=")) {
						val = val.substring(1);
						upBoundIncluded = true;
					}
					val = val.trim();
					Range rangeVal = DomainFactory.eINSTANCE.createRange();
					rangeVal.setUpper(val);
					rangeVal.setLower("Undefined");
					//rangeVal.setMultiple(true);
					rangeVal.setUpperInclusive(upBoundIncluded);
					return rangeVal;
				}
			} else {
				// single value
				Single singleValue = DomainFactory.eINSTANCE.createSingle();
				singleValue.setValue(cellValue);
				return singleValue;
			}
		}
	}

	/**
	 * @param symbols
	 * @param tag
	 * @return
	 */
	public static String getUniqueTag(Collection<? extends Symbol> symbols, String tag) {
		List<Integer> noList = new ArrayList<Integer>();
		for(Symbol symbol: symbols) {
			if (symbol.getIdName().startsWith(tag)) {
				String no = symbol.getIdName().substring(symbol.getIdName().indexOf("_")+1);
				noList.add(getValidNo(no));
			}
		}
		if (noList.size() > 0) {
			java.util.Arrays.sort(noList.toArray());
			int no = noList.get(noList.size()-1).intValue();no++;
			return tag + no;
		}
		return tag + 1;
	}

	/**
	 * @param no
	 * @return
	 */
	public static int getValidNo(String no) {
		int n;
		try{
			n = Integer.parseInt(no);
		}catch(Exception e) {
			return 0;
		}
		return n;
	}

	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static boolean isSymbolPresent(Collection<? extends Symbol> symbols,String idName) {
		for(Symbol symbol: symbols) {
			if (symbol.getIdName().equalsIgnoreCase(idName)) {
				return true;
			}
		}
		return false;
	}

    /**
     * @param name
     * @return
     */
    public static String generateUniqueName(String name) {
        name = (name == null) ? "" : name;
        Random random = new Random(System.currentTimeMillis());
        int id = Math.abs((name.hashCode() << 8) + random.nextInt());
        String requestId = Integer.toString(id, 16);
        return name+"_"+requestId.toUpperCase();
    }

	 /**
	 * @param targetTextViewer
	 */
    public static void setDropTarget(final TextViewer targetTextViewer, final IWorkbenchPage page, final IEditorPart editor) {


    	Transfer[] types = new Transfer[] { TextTransfer.getInstance(),
    			FileTransfer.getInstance(),
    			ResourceTransfer.getInstance(),
    			LocalSelectionTransfer.getTransfer()};
    	final Collection<String> extn = CommonUtil.getSharedResourceExtensions();
    	extn.add("rule");
    	extn.add("channel");
    	DropTarget dropTarget = new DropTarget(targetTextViewer.getTextWidget(), DND.DROP_COPY | DND.DROP_MOVE);
    	dropTarget.setTransfer(types);
    	dropTarget.addDropListener(new DropTargetAdapter() {

    		/**
    		 * @param event
    		 */
    		 public void drop(DropTargetEvent event) {
    			 Object data  = event.data;
    			 if (data instanceof TreeSelection) {
    				 TreeSelection selection = (TreeSelection) data;
    				 for(Object obj:selection.toArray()) {
    					 if (obj instanceof IFolder) {
    						 IFolder folder = (IFolder)obj;
    						 String type = CommonUtil.replace( folder.getProjectRelativePath().toString(),"/",".");
    						 type = type + "\n";
    						 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    					 }
    					 if (obj instanceof IFile) {
    						 IFile file = (IFile)obj;
    						 //if Rule File, just drag n drop <URI>
    						 String type =extn.contains(file.getFileExtension())? "\"" + IndexUtils.getFullPath(file)+"\"":
    							 CommonUtil.replace(IndexUtils.getFullPath(file),"/",".").substring(1);
    						 type = type + "\n";
    						 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    					 }
    					 if (obj instanceof AbstractNavigatorNode) {
    						 AbstractNavigatorNode node = (AbstractNavigatorNode)obj;
    						 if (node.getEntity() instanceof Destination) {
    							 Destination destination = (Destination)node.getEntity();
    							 String type = "\""+ getDestinationPath(destination) +"\"";
    							 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    						 }
    					 } if (obj instanceof Folder) {
    						 Folder folder = (Folder)obj;
    						 StringBuilder path = new StringBuilder();
    						 path.append("");
    						 getPath(folder, path);
    						 String type = path.append(folder.getName()).toString();
    						 type = type + "\n";
    						 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    					 }
    					 if (obj instanceof SharedRuleElement) {
    						 SharedRuleElement ruleElement = (SharedRuleElement)obj;
    						 if ( ruleElement.getRule() instanceof RuleFunction) {
    							 String type = ruleElement.getRule().getFullPath();
    							 type = CommonUtil.replace(type,"/",".").substring(1);
    							 type = type + "\n";
    							 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    						 }else{
    							 String type = "\"" +ruleElement.getRule().getFullPath()+"\"";
    							 type = type + "\n";
    							 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    						 }
    					 }
    					 if (obj instanceof SharedEntityElement) {
    						 SharedEntityElement sharedElement = (SharedEntityElement)obj;
    						 String type = CommonUtil.replace(sharedElement.getSharedEntity().getFullPath(),"/",".").substring(1);
    						 type = type + "\n";
    						 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), type);
    					 }
    				 }
    			 }else if (data instanceof String) {
    				 updateDocument(targetTextViewer.getDocument(), targetTextViewer.getTextWidget().getCaretOffset(), (String)data);
    			 }
    			 if (page != null && editor != null) {
    				 page.activate(editor);
    				 targetTextViewer.getTextWidget().setFocus();
    			 }
    		 }
    	});
    }

    /**
     * @param folder
     * @param path
     */
    private static void getPath(Folder folder,  StringBuilder path) {
    	if (folder.eContainer() instanceof DesignerProject ) {
    		return;
    	}
    	if (folder.eContainer() instanceof Folder) {
    		Folder f = (Folder)folder.eContainer();
    		path.append(f.getName());
    		path.append(".");
    		getPath(f, path);
    	}
    }

    /**
     * @param destination
     * @return
     */
    public static String getDestinationPath(Destination destination) {
    	DriverConfig driverConfig = destination.getDriverConfig();
		Channel channel = driverConfig.getChannel();
		StringBuilder sBuilder = new StringBuilder();
		String path = sBuilder.append(channel.getFullPath())
								.append("/")
								.append(destination.getName())
								.toString();
		return path;
    }
    /**
     * @param targetText
     * @param type
     */
    public static void setDropTarget(final Text targetText,
    		                         final ELEMENT_TYPES type) {
	    Transfer[] types = new Transfer[] { TextTransfer.getInstance(),
	    		                            FileTransfer.getInstance(),
	    		                            ResourceTransfer.getInstance(),
	    		                            LocalSelectionTransfer.getTransfer()};
	    DropTarget dropTarget = new DropTarget(targetText, DND.DROP_COPY | DND.DROP_MOVE);
	    dropTarget.setTransfer(types);
	    dropTarget.addDropListener(new DropTargetAdapter() {
	      /**
	     * @param event
	     */
	    public void drop(DropTargetEvent event) {
	    	Object data  = event.data;
	    	if (data instanceof TreeSelection) {
	    		TreeSelection selection = (TreeSelection) data;
	    		if (selection.size() == 1) {
	    			Object[] obj = selection.toArray();
	    			if (obj[0] instanceof IFile) {
	    				IFile file = (IFile)obj[0];
	    				if (type ==  ELEMENT_TYPES.DESTINATION) {
	    					if (file.getFileExtension().equals(CommonIndexUtils.EVENT_EXTENSION)) {
	    						targetText.setText(IndexUtils.getFullPath(file));
	    					}
	    				}
	    			}
	    			if (obj[0] instanceof AbstractNavigatorNode) {
	    				AbstractNavigatorNode node = (AbstractNavigatorNode)obj[0];
	    				if (type ==  ELEMENT_TYPES.SIMPLE_EVENT) {
	    					if (node.getEntity() instanceof Destination) {
	    						Destination destination = (Destination)node.getEntity();
	    						DriverConfig driverConfig = destination.getDriverConfig();
	    						Channel channel = driverConfig.getChannel();
	    						StringBuilder sBuilder = new StringBuilder();
	    						String path = sBuilder.append(channel.getFullPath())
	    						.append(".channel")
	    						.append("/")
	    						.append(destination.getName())
	    						.toString();
	    						targetText.setText(path);
	    					}
	    				}
	    			}
	    	    }

	    	}
	      }
	    });
	  }

    /**
     * @param document
     * @param offset
     * @param data
     */
    public static void updateDocument(IDocument document, int offset, String data) {
    	try {
    		InsertEdit edit = new InsertEdit(offset , data);
    		edit.apply(document);
    	} catch (MalformedTreeException e) {
    		e.printStackTrace();
    	} catch (BadLocationException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * @param textWidget
     */
    public static void setKeySupport(final StyledText textWidget, final TextViewerUndoManager undoManager) {

    	setTraverseSupport(textWidget);

    	textWidget.addKeyListener(new KeyAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
			 */
			@Override
			public void keyReleased(KeyEvent e) {
				setKeySupport(e, textWidget, undoManager, true);
			}});
    }

    /**
     * @param textWidget
     */
    public static void setTraverseSupport( final StyledText textWidget) {
    	textWidget.addVerifyKeyListener(new VerifyKeyListener() {
	        /**
	         * @param e
	         */
	        public void verifyKey(VerifyEvent e) {
	            if (e.keyCode == SWT.TAB) {
	                if ((e.stateMask & SWT.SHIFT) != 0){
	                    e.doit = onTraverse(textWidget, true);
	                } else {
	                    e.doit = onTraverse(textWidget, false);
	                }
	            }
	        }
	    });
		textWidget.addTraverseListener(new TraverseListener() {
	        /**
	         * @param e
	         */
	        public void keyTraversed(TraverseEvent e) {
	            if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
	                e.doit = false; //allows verifyKey listener to fire
	            }
	        }
	    });
    }

	/**
	 * @param textWidget
	 * @param mask
	 * @return
	 */
	public static boolean onTraverse(final StyledText textWidget, boolean mask) {

		if (textWidget.getSelectionText().isEmpty()) {
	        return true;
	    }

	    Point range = textWidget.getSelectionRange();
	    int start = range.x;
	    int length = textWidget.getSelectionCount();

	    String txt = textWidget.getText();
	    while (start > 0 && txt.charAt(start-1) != '\n') {
	    	if (mask) {
	    		-- start;
	  	        ++ length;
	    	} else {
	    		start --;
	    		length ++;
	    	}
	    }

	    int replaceLength = length;
	    textWidget.setSelectionRange(start, length);
	    textWidget.showSelection();

	    String selText = textWidget.getSelectionText();
	    String[] lines = selText.split("\n");
	    String newText = "";

	    for (int x = 0; x < lines.length; x++){
	    	if (x > 0) {
	    		newText += '\n';
	    	}
	    	if (mask) {
	    		if (lines[x].charAt(0) == '\t'){
	    			newText += lines[x].substring(1);
	    			length--;
	    		} else if (lines[x].startsWith(" ")) {
	    			newText += lines[x].substring(1);
	    			length--;
	    		} else {
	    			newText += lines[x];
	    		}
	    	} else {
	    		newText += '\t';
	    		length++;
	    		newText += lines[x];
	    	}
	    }
	    textWidget.replaceTextRange(start, replaceLength, newText);
	    textWidget.setSelectionRange(start, length);
	    textWidget.showSelection();
	    return false;
	}
    /**
     * @param e
     * @param textWidget
     * @param undoManager
     */
    public static void setKeySupport(KeyEvent e, final StyledText textWidget, final TextViewerUndoManager undoManager, boolean supportCopyPaste) {
    	if (e.stateMask == SWT.CTRL && e.keyCode == 'z') {
			undoManager.undo();
		}
		else if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {
			textWidget.selectAll();
		}
		else if (e.stateMask == SWT.CTRL && e.keyCode == 'y') {
			undoManager.redo();
		}
		else if (e.stateMask == SWT.CTRL && e.keyCode == 'x' && supportCopyPaste) {
			textWidget.cut();
		}
		else if (e.stateMask == SWT.CTRL && e.keyCode == 'c' && supportCopyPaste) {
			textWidget.copy();
		}
    	//BE-22809 : Preventing duplicate paste in RuleFormEditor,RuleTemplateFormEditor,RuleFunctionFormEditor
		/*else if (e.stateMask == SWT.CTRL && e.keyCode == 'v' && supportCopyPaste) {
			textWidget.paste();
		}*/
		else if (e.keyCode == SWT.HOME) {
			StyledText text = (StyledText) e.getSource();
			int offset = text.getSelection().y;
			int line = text.getLineAtOffset(offset);
			int start = text.getOffsetAtLine(line);
			if (e.stateMask == SWT.SHIFT) {
				text.setSelection(offset, start);
			} else {
				text.setSelection(start);
			}
		}
		else if (e.keyCode == SWT.END) {
			StyledText text = (StyledText) e.getSource();
			int offset = text.getSelection().x;
			int line = text.getLineAtOffset(offset);
			int start = text.getOffsetAtLine(line);
			int delta = text.getLine(line).length();
			int end = start+delta;
			if (e.stateMask == SWT.SHIFT) {
				text.invokeAction(ST.LINE_END);
				text.setSelection(offset, end);
			} else {
				text.setSelection(end);
			}
		}
    }


    /**
     * @param textWidget
     * @param undoManager
     * @param isOpenDeclaration
     * @param ruleFormSource
     */
    public static void setEditContextMenuSupport(final StyledText textWidget,
									    		 final TextViewerUndoManager undoManager,
									    		 final boolean isOpenDeclaration,
									    		 final IStudioRuleSourceCommon ruleFormSource) {
    	Menu popupMenu = new Menu(textWidget);
    	popupMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuHidden(MenuEvent e) {
				// TODO Auto-generated method stub
			}

			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.MenuListener#menuShown(org.eclipse.swt.events.MenuEvent)
			 */
			@Override
			public void menuShown(MenuEvent e) {
				Menu menu =(Menu) e.getSource();
				int indx = 0;
				if (isOpenDeclaration) {
					menu.getItem(indx + 1).setEnabled(undoManager.undoable() ? true: false );//Undo menu
					menu.getItem(indx + 2).setEnabled(undoManager.redoable() ? true: false );//Redo menu
					menu.getItem(indx + 4).setEnabled(textWidget.getSelectionText().length() > 0 ? true: false );//cut menu
					menu.getItem(indx + 5).setEnabled(textWidget.getSelectionText().length() > 0 ? true: false );//copy menu
				}else{
					menu.getItem(indx).setEnabled(undoManager.undoable() ? true: false );//Undo menu
					menu.getItem(indx + 1).setEnabled(undoManager.redoable() ? true: false );//Redo menu
					menu.getItem(indx + 3).setEnabled(textWidget.getSelectionText().length() > 0 ? true: false );//cut menu
					menu.getItem(indx + 4).setEnabled(textWidget.getSelectionText().length() > 0 ? true: false );//copy menu
				}
			}});

    	if (isOpenDeclaration) {
    		MenuItem item = new MenuItem(popupMenu, SWT.PUSH);
    		item.setText(Messages.getString("rule.form.edit.open.decl"));
    		item.addListener(SWT.Selection, new Listener() {
    			/* (non-Javadoc)
    			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
    			 */
    			@Override
    			public void handleEvent(org.eclipse.swt.widgets.Event event) {
    				ruleFormSource.openDeclaration();
    			}
    		});
    		item = new MenuItem(popupMenu, SWT.SEPARATOR);
    	}

    	MenuItem item = new MenuItem(popupMenu, SWT.PUSH);
		item.setText(Messages.getString("rule.form.edit.undo"));
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/undo_edit.png"));
		item.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				undoManager.undo();
			}
		});

		item = new MenuItem(popupMenu, SWT.PUSH);
		item.setText(Messages.getString("rule.form.edit.redo"));
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/redo_edit.png"));

		item.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				undoManager.redo();
			}
		});

    	item = new MenuItem(popupMenu, SWT.SEPARATOR);

		item = new MenuItem(popupMenu, SWT.PUSH);
		item.setText(Messages.getString("rule.form.edit.cut"));
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/cut_edit.png"));
		item.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				textWidget.cut();
			}
		});

		item = new MenuItem(popupMenu, SWT.PUSH);
		item.setText(Messages.getString("rule.form.edit.copy"));
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/copy_edit.png"));
		item.addListener(SWT.Selection, new Listener() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				textWidget.copy();
			}
		});

		item = new MenuItem(popupMenu, SWT.PUSH);
		item.setText(Messages.getString("rule.form.edit.paste"));
		item.setImage(StudioUIPlugin.getDefault().getImage("icons/paste_edit.png"));
		item.addListener(SWT.Selection, new Listener() {

			/* (non-Javadoc)
			 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
			 */
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				textWidget.paste();
			}
		});

		textWidget.setMenu(popupMenu);
    }

	/**
	 * @param types
	 * @param site
	 * @param diagramManager
	 */
	public static void editGraph(final EDIT_TYPES types,
			                     final IEditorSite site,
							     final DiagramManager diagramManager) {
		try{
			SwingUtilities.invokeLater(new Runnable() {
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				public void run() {
					List<TSENode> selectedNodes = diagramManager.getSelectedNodes();
					List<TSEEdge> selectedEdges = diagramManager.getSelectedEdges();
//					List<TSENodeLabel> selectedNodeLabels = diagramManager.getSelectedNodeLabels();
//					List<TSEEdgeLabel> selectedEdgeLabels = diagramManager.getSelectedEdgeLabels();
					switch(types) {
						case CUT:
							if (!diagramManager.validateCutEdit(selectedNodes, selectedEdges)) {
								break;
							}
							for (TSENode node : selectedNodes) {
								diagramManager.traversePathAttributeNodeType(node, false);
							}

							//As TomSwayer doesn't have the "CUT" Edges functionality, here is the alternative.
							//Caching the edges.
							diagramManager.getEdgesClipBoard().clear();
							diagramManager.getEdgesClipBoard().addAll(selectedEdges);
							cutCommand(diagramManager, selectedNodes,selectedEdges, true);
							break;
						case COPY:
							if (!diagramManager.validateCopyEdit(selectedNodes, selectedEdges)) {
								break;
							}

							for (TSENode node : selectedNodes) {
								diagramManager.traversePathAttributeNodeType(node, true);
							}

							//As TomSwayer doesn't have the "COPY" Edges functionality, here is the alternative.
							//Caching the edges instead.
							diagramManager.getEdgesClipBoard().clear();
							diagramManager.getEdgesClipBoard().addAll(selectedEdges);
							diagramManager.addToEditGraphMap(diagramManager.getCopyMap(), selectedNodes,selectedEdges, true);
							diagramManager.getDrawingCanvas().copy(diagramManager.getLayoutManager().getInputData());
							break;
						case PASTE:
							if (!diagramManager.validatePasteEdit(selectedNodes, selectedEdges)) {
								break;
							}

                            if (diagramManager.handleTransitionsEditGraphMap()) {
                            	break;
                            }

							TSEPasteTool pasteTool = (TSEPasteTool)TSEditingToolHelper.getPasteTool(diagramManager.getDrawingCanvas().getToolManager());
							pasteTool.setServiceInputData(diagramManager.getLayoutManager().getInputData());
							diagramManager.getDrawingCanvas().getToolManager().setActiveTool(pasteTool);
							break;
						case DELETE:
							if (!diagramManager.validateDeleteEdit(selectedNodes, selectedEdges))
								break;
							/*if (!(selectedNodeLabels.isEmpty())) {
								openError(diagramManager.getEditor().getEditorSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
								break;
							}
							if (!(selectedEdgeLabels.isEmpty())) {
								openError(diagramManager.getEditor().getEditorSite().getShell(), Messages.getString("error_title"), Messages.getString("error_message"));
								break;
							}*/
							confirmDeleteBeforeEdit(diagramManager);
							break;
						}
					if ((types == EDIT_TYPES.CUT) || (types == EDIT_TYPES.PASTE) || (types == EDIT_TYPES.DELETE)) {
						refreshDiagram(diagramManager);
						refreshOverview(site, true, true);
					}
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param ruleFormEditor
	 */
	public static void addRemoveControlListener(IStudioRuleSourceCommon ruleFormEditor, final boolean isEditorEnabled) {
		final Table  declarationsTable = ruleFormEditor.getDeclarationTable();
		final ToolItem removeDeclButton = ruleFormEditor.getRemoveDeclarationButton();
		final ToolItem upDeclButton = ruleFormEditor.getUpDeclarationButton();
		final ToolItem downDeclButton = ruleFormEditor.getDownDeclarationButton();
		declarationsTable.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final int selectionCount = declarationsTable.getSelectionCount();
				final int selectionIndex=declarationsTable.getSelectionIndex();
				final int itemcount=declarationsTable.getItemCount();
						if (!isEditorEnabled) {
							return;
						}

						if (selectionCount > 0 ) {

							removeDeclButton.setEnabled(true);
							if(itemcount>1){
							if(selectionIndex==0){
								if (upDeclButton != null) {
									upDeclButton.setEnabled(false);
								}
								if (downDeclButton != null) {
									downDeclButton.setEnabled(true);
								}
							}else if(selectionIndex==itemcount-1){
								if (upDeclButton != null) {
									upDeclButton.setEnabled(true);
								}
								if (downDeclButton != null) {
									downDeclButton.setEnabled(false);
								}
							}else{
							if (upDeclButton != null) {
								upDeclButton.setEnabled(true);
							}
							if (downDeclButton != null) {
								downDeclButton.setEnabled(true);
							}
							}}
						} else {
							removeDeclButton.setEnabled(false);
							if (upDeclButton != null) {
								upDeclButton.setEnabled(false);
							}
							if (downDeclButton != null) {
								downDeclButton.setEnabled(false);
							}
						}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});

		declarationsTable.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {

				if (declarationsTable.getSelectionCount() > 0 ) {

				}

			}
			@Override
			public void focusLost(FocusEvent e) {

			}
		});

	}

	/**
	 * @param containerPath
	 * @return
	 */
	public static boolean isValidContainer(IPath containerPath) {
		// remove the first segment, which is the project name, not to be validated
		if (containerPath == null) return false;
		String folderPath = containerPath.removeFirstSegments(1).toOSString();
		folderPath = folderPath.replace(File.separatorChar, '/');
		if (!folderPath.startsWith("/")) {
			folderPath = "/"+folderPath;
		}
		boolean allowKeyWords = false;
		boolean isPathValid = !EntityNameHelper.containsInvalidNameChars(folderPath, true, false) &&
		EntityNameHelper.isValidEntityPath(folderPath, allowKeyWords);
		if (!isPathValid) {
			return false;
		}
		return true;
	}

	protected static int focusIndex=0;

	public static void setDeclarationTableEditableSupport(final Table table,
			final int editColumn,
			final Compilable compilable,
			final int ruleType,
			final int blockType,
			final int statementType,
			final IStudioRuleSourceCommon ruleSourceCommon,
			final boolean editable) {
		setDeclarationTableEditableSupport(table, editColumn, compilable, ruleType, blockType, statementType, ruleSourceCommon, editable, null, true);
	}
	
	public static void setDeclarationTableEditableSupport(final Table table,
			  final int editColumn,
			  final Compilable compilable,
			  final int ruleType,
			  final int blockType,
			  final int statementType,
			  final IStudioRuleSourceCommon ruleSourceCommon,
			  final boolean editable,
			  final boolean validate) {
		setDeclarationTableEditableSupport(table, editColumn, compilable, ruleType, blockType, statementType, ruleSourceCommon, editable, null, validate);
	}

	public static void setDeclarationTableEditableSupport(final Table table,
			final int editColumn,
			final Compilable compilable,
			final int ruleType,
			final int blockType,
			final int statementType,
			final IStudioRuleSourceCommon ruleSourceCommon,
			final boolean editable,
			final String[] comboItems,
			final boolean validate) {
		setDeclarationTableEditableSupport(table, editColumn, compilable, ruleType, blockType, statementType, ruleSourceCommon, editable, editable, comboItems, validate);
	}

	/**
	 * @param table
	 * @param editColumn
	 * @param compilable
	 * @param ruleType
	 * @param blockType
	 * @param statementType
	 * @param ruleSourceCommon
	 */
	public static void setDeclarationTableEditableSupport(final Table table,
														  final int editColumn,
														  final Compilable compilable,
														  final int ruleType,
														  final int blockType,
														  final int statementType,
														  final IStudioRuleSourceCommon ruleSourceCommon,
														  final boolean firstRowEditable,
														  final boolean editable,
														  final String[] comboItems,
														  final boolean validate) {
		final TableEditor editor = new TableEditor(table);
	    editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    table.addListener(SWT.MouseDown, new Listener() {
	      public void handleEvent(org.eclipse.swt.widgets.Event event) {
	        Rectangle clientArea = table.getClientArea();
	        Point pt = new Point(event.x, event.y);
	        int index = table.getTopIndex();
	        while (index < table.getItemCount()) {
	          boolean visible = false;
	          final TableItem item = table.getItem(index);    
	          final String type = item.getText(0);
	          for (int i = 0; i < table.getColumnCount(); i++) {
	        	  if (i == editColumn) {
	        		  Rectangle rect = item.getBounds(i);
	        		  if (rect.contains(pt)) {
	        			  final int column = i;
	        			  Control tmp = null;
	        			  if (comboItems != null) {
	        				  tmp = new Combo(table, /*SWT.BORDER |*/ SWT.READ_ONLY);
	        				  ((Combo)tmp).setItems(comboItems);
	        			  } else {
	        				  tmp = new Text(table, SWT.NONE);
	        			  }
	        			  final Control text = tmp;
	        			  text.addKeyListener(new KeyAdapter() {
	        				  /* (non-Javadoc)
	        				   * @see org.eclipse.swt.events.KeyAdapter#keyReleased(org.eclipse.swt.events.KeyEvent)
	        				   */
	        				  @Override
	        				  public void keyReleased(KeyEvent e) {
//	        					  if (e.stateMask == SWT.CTRL && e.keyCode == 'x') {
//	        						  text.cut();
//	        					  }
	        					  if (e.stateMask == SWT.CTRL && e.keyCode == 'c') {
	        						  if (text instanceof Text) {
	        							  ((Text) text).copy();
	        						  } else if (text instanceof Combo) {
	        							  ((Combo) text).copy();
	        						  }
	        					  }
	        					  if (e.stateMask == SWT.CTRL && e.keyCode == 'v') {
	        						  if (text instanceof Text) {
	        							  ((Text) text).paste();
	        						  } else if (text instanceof Combo) {
	        							  ((Combo) text).paste();
	        						  }
	        					  }
	        				  }});
						  if (text instanceof Text) {
		        			  if (table.getSelectionIndex() == 0) {
		        				  ((Text) text).setEditable(firstRowEditable);
		        			  } else {
		        				  ((Text) text).setEditable(editable);
		        			  }

						  }
	        			  Listener textListener = new Listener() {

							public void handleEvent(final org.eclipse.swt.widgets.Event e) {

	        					  String oldText = null;
	        					  String newText = null;
	        					  focusIndex=column;
        						  String id = item.getText(ID_COLUMN);
	        					  switch (e.type) {
	        					  case SWT.FocusOut:

	        						  oldText = item.getText(editColumn);
	        						  String colText = "";
	        						  if (text instanceof Text) {
	        							  colText = ((Text) text).getText();
	        						  } else if (text instanceof Combo) {
	        							  colText = ((Combo) text).getText();
	        						  }
	        						  item.setText(column, colText);
	        						  newText = item.getText(editColumn);
	        						  if (editColumn == ID_COLUMN && validate) {
	        							  if (!EntityNameHelper.isValidBEEntityIdentifier(newText)) {
	        								  item.setText(column, oldText);
	        								  text.dispose();
	        								  break;
	        							  }
	        							  if (isSymbolPresent(ruleSourceCommon.getCommonCompilable().getSymbols().getSymbolList(), newText)) {
	        								  item.setText(column, oldText);
	        								  text.dispose();
	        								  break;
	        							  }
	        						  } else if (editColumn == EXPR_COLUMN && validate) {
	        							  if (!isValidExpression(newText)) {
	        								  item.setText(column, oldText);
	        								  text.dispose();
	        								  break;
	        							  }
	        						  }
	        						  ruleSourceCommon.updateDeclarationStatements(type, id, oldText, newText, ruleType, blockType, statementType, compilable);
	        						  text.dispose();
	        						  break;
	        					  case SWT.Traverse:
	        						  switch (e.detail) {
	        						  case SWT.TRAVERSE_RETURN:

		        						  oldText = item.getText(editColumn);
		        						  colText = "";
		        						  if (text instanceof Text) {
		        							  colText = ((Text) text).getText();
		        						  } else if (text instanceof Combo) {
		        							  colText = ((Combo) text).getText();
		        						  }
		        						  item.setText(column, colText);
		        						  newText = item.getText(editColumn);
		        						  if (editColumn == ID_COLUMN && validate) {
		        							  if (!EntityNameHelper.isValidBEEntityIdentifier(newText)) {
		        								  item.setText(column, oldText);
		        								  text.dispose();
		        								  break;
		        							  }
		        							  if (isSymbolPresent(ruleSourceCommon.getCommonCompilable().getSymbols().getSymbolList(), newText)) {
		        								  item.setText(column, oldText);
		        								  text.dispose();
		        								  break;
		        							  }
		        						  } else if (editColumn == EXPR_COLUMN && validate) {
		        							  if (!isValidExpression(newText)) {
		        								  item.setText(column, oldText);
		        								  text.dispose();
		        								  break;
		        							  }
		        						  }
		        						  if (!ruleSourceCommon.updateDeclarationStatements(type, id, oldText, newText, ruleType, blockType, statementType, compilable)) {
		        							  item.setText(column, oldText);
	        								  text.dispose();
	        								  break;
		        						  }
		        						  text.dispose();
		        						  break;
	        							  // FALL THROUGH     
	        						  case SWT.TRAVERSE_ESCAPE:
	        							  text.dispose();
	        							  e.doit = false;
	        						  }
	        						  break;
	        					  }
	        				  }

							private boolean isValidExpression(String newText) {
								if (newText == null || newText.trim().length() == 0) {
									return true;
								}
								IProblemHandler collector = new DefaultProblemHandler();
								CommonTree tree = RulesParserManager.parseExpressionString("", newText, collector, false);
								if (tree == null) {
									return false;
								}
								if (collector.getHandledProblems().size() > 0) {
									return false;
								}
								return true;
							}
	        			  };

	        			  text.addListener(SWT.FocusOut, textListener);
	        			  text.addListener(SWT.Traverse, textListener);
	        			  //table.setSelection(item);
	        			  editor.setEditor(text, item, i);
						  if (text instanceof Text) {
							  ((Text)text).setText(item.getText(i));
							  ((Text)text).selectAll();
						  } else if (text instanceof Combo) {
							  ((Combo)text).setText(item.getText(i));
						  }
	        			  text.setFocus();

	        			  return;
	        		  }
	        		  if (!visible && rect.intersects(clientArea)) {
	        			  visible = true;
	        		  }
	        	  }
	          }
	          if (!visible)
	            return;

	          index++;

	        }
	      }
	    });
	}

	/**
	 * @param diagramManager
	 */
	public static void deleteCommand(DiagramManager diagramManager) {
		 TSEDeleteSelectedCommand com = new TSEDeleteSelectedCommand(diagramManager.getDrawingCanvas());
		 diagramManager.getDrawingCanvas().getCommandManager().transmit(com);
	}

	/**
	 * @param diagramManager
	 * @param selectedNodes
	 * @param selectedEdges
	 * @param isCopy
	 */
	public static void cutCommand(DiagramManager diagramManager,
			                      List<TSENode> selectedNodes,
			                      List<TSEEdge> selectedEdges,
			                      boolean isCopy) {

		diagramManager.addToEditGraphMap(diagramManager.getCutMap(),selectedNodes,selectedEdges, isCopy);

		//As TomSwayer doesn't have the "CUT" Edges functionality, here is the alternative.
		//Delete the edges instead.
		if (selectedEdges.size()>0) {
			 TSEDeleteSelectedCommand com = new TSEDeleteSelectedCommand(diagramManager.getDrawingCanvas());
			 diagramManager.getDrawingCanvas().getCommandManager().transmit(com);
		}

		diagramManager.getDrawingCanvas().cut(diagramManager.getLayoutManager().getInputData());
	}

	/**
	 * @param diagramManager
	 */
	//Confirmation before performing DELETE operation
	public static void confirmDeleteBeforeEdit(final DiagramManager diagramManager) {
		final Shell shell = diagramManager.getEditor().getEditorSite().getShell();
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				boolean deleteConfirm = MessageDialog.openQuestion(shell, Messages.getString("delete_title"), Messages.getString("delete_confirm"));
			 	if (deleteConfirm) {
			 			deleteCommand(diagramManager);
			 }
			}
		}, false);
	}

	/**
	 *
	 * @param property_types
	 * @param resourceType
	 * @return
	 */
	public static Image  getPropertyImage(PROPERTY_TYPES property_types) {
		switch (property_types) {
			case INTEGER:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconInteger16.gif");
			case DOUBLE:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconReal16.gif");
			case LONG:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconLong16.gif");
			case BOOLEAN:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconBoolean16.gif");
			case DATE_TIME:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconDate16.gif");
			case STRING:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconString16.gif");
			case CONCEPT:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconConcept16.gif");
			case CONCEPT_REFERENCE:
				return StudioUIPlugin.getDefault().getImage("icons/property/iconConceptRef16.gif");
			default:
				break;
		}
		return null;
	}

	/**
	 * Delegating workbench selection on Studio Elements selection
	 * @param object
	 * @param editor
	 */
	public static void setWorkbenchSelection(final Object object, final IEditorPart editor) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try{
					editor.getSite().getWorkbenchWindow().getSelectionService().getSelection();
					ISelectionProvider selectionProvider = editor.getEditorSite().getSelectionProvider();
					ISelection currentSelection = selectionProvider.getSelection();
					if (currentSelection instanceof StructuredSelection) {
						Object obj = ((StructuredSelection)currentSelection).getFirstElement();
						if (obj != null && obj.equals(object)) {
							return;
						}
					}
					ISelection selection = object == null ? StructuredSelection.EMPTY : new StructuredSelection(object);
					if (selection.isEmpty()) {
						if (editor instanceof IGraphDrawing) {
							selection = new StructuredSelection(((IGraphDrawing) editor).getDiagramManager().getCurrentSelection());
						}
					}
					selectionProvider.setSelection(selection);
				}
				catch(Exception e) {
					e.printStackTrace();
				}

			}});
	}

	public static String createStateMachineTooltip(StateMachine sm) {

		StringBuilder tooltip = new StringBuilder();

		tooltip.append("<p><b>Full path: </b>").
				append(sm.getFolder()).
				append(sm.getName()).
				append("</p>");

		tooltip.append("<p><b>Name: </b>").
	       append(sm.getName()).
	       append("</p>");

		if (!(sm.getDescription().isEmpty())) {
			tooltip.append("<p><b>Description: </b>").
			append(sm.getDescription()).
			append("</p>");
		}

		return tooltip.toString();
	}

	public static String createDomainModelTooltip(Domain dm) {

		StringBuilder tooltip = new StringBuilder();

		tooltip.append("<p><b>Full path: </b>").
				append(dm.getFolder()).
				append(dm.getName()).
				append("</p>");

		tooltip.append("<p><b>Name: </b>").
				       append(dm.getName()).
				       append("</p>");

		tooltip.append("<p><b>Description: </b>").
		append(dm.getDescription()).
		append("</p>");

		return tooltip.toString();
	}

	public static String createChannelTooltip(Channel channel) {

		StringBuilder tooltip = new StringBuilder();

		tooltip.append("<p><b>Full path: </b>").
				append(channel.getFolder()).
				append(channel.getName()).
				append("</p>");

		tooltip.append("<p><b>Name: </b>").
				       append(channel.getName()).
				       append("</p>");

		tooltip.append("<p><b>Description: </b>").
		append(channel.getDescription()).
		append("</p>");

		return tooltip.toString();
	}

	public static String createDestinationTooltip(Destination destination) {

		StringBuilder tooltip = new StringBuilder();

		tooltip.append("<p><b>Name: </b>").
				       append(destination.getName()).
				       append(" (destination) ").
				       append("</p>");

		tooltip.append("<p><b>Description: </b>").
		append(destination.getDescription()).
		append("</p>");

		return tooltip.toString();
	}
	
	public static String createRuleToolTip(Rule rule) {

		StringBuilder tooltip = new StringBuilder();
		
		tooltip.append("<p><b>Full path: </b>");
		tooltip.append(rule.getFullPath());
		tooltip.append("</p>");
		tooltip.append("<p><b>Name: </b>").
				       append(rule.getName()).
				       append(" (rule) ").
				       append("</p>");

		return tooltip.toString();
	}
	
	public static String createRuleFunctionToolTip(RuleFunction ruleFunction) {

		StringBuilder tooltip = new StringBuilder();
		
		tooltip.append("<p><b>Full path: </b>");
		tooltip.append(ruleFunction.getFullPath());
		tooltip.append("</p>");
		tooltip.append("<p><b>Name: </b>").
				       append(ruleFunction.getName()).
				       append(" (rule function) ").
				       append("</p>");

		
		return tooltip.toString();
	}
	
	public static String createDecisionTableToolTip(Implementation decisionTable) {
		
		StringBuilder tooltip = new StringBuilder();
			
		tooltip.append("<p><b>Full path: </b>");
		tooltip.append(decisionTable.getPath());
		tooltip.append("</p>");
		tooltip.append("<p><b>Name: </b>").
				       append(decisionTable.getName()).
				       append(" (decision table) ").
				       append("</p>");

		return tooltip.toString();
	
	}
	
	
	public static String createScoreCardTooltip(Scorecard scoreCard) {

		StringBuilder tooltip = new StringBuilder();

		tooltip.append("<p><b>Full path: </b>").
				append(scoreCard.getFolder()).
				append(scoreCard.getName()).
				append("</p>");

		tooltip.append("<p><b>Name: </b>").
				       append(scoreCard.getName()).
				       append("</p>");

		tooltip.append("<p><b>Description: </b>").
		append(scoreCard.getDescription()).
		append("</p>");

		List<PropertyDefinition> properties = scoreCard.getProperties();
		tooltip.append("<p><b>Properties: </b>").
		append(properties.size()).
		append("</p>");

		Iterator<PropertyDefinition> propIter = properties.iterator();
		PropertyDefinition property;
		String name;

		while (propIter.hasNext()) {
			property = propIter.next();
			name = property.getName();

			if (property.isArray()) {
				name += "[ ]";
			}

			if (property.getHistorySize() > 0) {
				name += " (" + property.getHistorySize() + ")";
			}
			switch (property.getType()) {
			case DATE_TIME:
				tooltip.append("<p>");
				tooltip.append(name);
				tooltip.append(" : DateTime</p>");
				break;
			case BOOLEAN:
				tooltip.append("<p>");
				tooltip.append(name);
				tooltip.append(" : Boolean</p>");
				break;
			case DOUBLE:
				tooltip.append("<p>");
				tooltip.append(name);
				tooltip.append(" : Double</p>");
				break;
			case LONG:
				tooltip.append("<p>");
				tooltip.append(name);
				tooltip.append(" : Long</p>");
				break;
			case INTEGER:
				tooltip.append("<p>");
				tooltip.append(name);
				tooltip.append(" : Integer</p>");
				break;
			case STRING:
				tooltip.append("<p>");
				tooltip.append(name);
				tooltip.append(" : String</p>");
				break;
			default:
				System.err.println("WARNING: unknown property type!");
			}
		}

		return tooltip.toString();
	}

	/**
	 * @param target
	 * @param contents
	 * @return
	 */
	public static boolean checkOwnerProjectValid(IContainer target, Object[] contents) {
		IProject project = getResourceOwner(contents);
		if (target instanceof IProject) {
			if (project != ((IProject)target)) {
				return false;
			}
		}else{
			if (project != target.getProject()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param contents
	 * @return
	 */
	public static IProject getResourceOwner(Object[] contents) {
		for (Object object : contents) {
			if (!(object instanceof IResource)) {
				continue;
			}
			IResource resource = (IResource) object;
			return resource.getProject();
		}
		return null;
	}

	/**
	 * @param projectName
	 * @param list
	 * @param mismatchMap
	 */
	public static void collectPropertyDomainTypeMismatch(String projectName,
						                                  List<PropertyDefinition> list,
						                                  Map<PropertyDefinition, String> mismatchMap) {
		for(PropertyDefinition pd:list) {
			String type = pd.getType().getName();
			for(DomainInstance inst:pd.getDomainInstances()) {
				Domain domain = IndexUtils.getDomain(projectName, inst.getResourcePath());
				if (domain != null && !domain.getDataType().getName().equalsIgnoreCase(type)) {
					mismatchMap.put(pd, domain.getDataType().getName());
				}
			}
		}
	}

	/**
	 * Utility method for invoking anything on Eclipse UI Thread
	 */
	public static void invokeOnDisplayThread(final Runnable runnable, boolean syncExec) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if (display == null) {
			display = Display.getDefault();
		}
		if (display != null && !display.isDisposed()) {
			if (display.getThread() != Thread.currentThread()) {
				if (syncExec) {
					display.syncExec(runnable);
				} else {
					display.asyncExec(runnable);
				}
			} else	{
				runnable.run();
			}
		}
	}

	/**
	 * @param project
	 * @param shell
	 * @param title
	 * @return
	 */
	public static boolean isValidProject(final IProject project, final Shell shell, final String title) {
		try{
			List<IProject> projectsToValidate = new ArrayList<IProject>();
			projectsToValidate.add(project);
			final ValidateProjectAction validateProjectAction = new ValidateProjectAction() {
				@Override
				protected void showError() {
					//TODO
				}
				@Override
				protected void showWarning() {
					//TODO
				}
			};
			validateProjectAction.performValidate(projectsToValidate);
			validateProjectAction.waitForValidateProjects();
			if (validateProjectAction.hasError(project.getName())) {
				MessageDialog.openError(shell,
						title, "The project is not valid:\n"+ validateProjectAction.getError());
				return false;
			}
		} catch(Exception e) {
			StudioUIPlugin.debug(StudioUIUtils.class.getName(), e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Close editors opened from project library (after removing project library from build path)
	 * @param jarFilePath
	 */
	public static void closeJarEntryFileEditor(final String jarFilePath) {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page != null) {
					for(IEditorReference reference:  page.getEditorReferences()) {
						try {
							if (reference.getEditorInput() instanceof JarEntryEditorInput) {
								JarEntryEditorInput editorInput =(JarEntryEditorInput) reference.getEditorInput();
								JarEntryFile jarEntryFile = (JarEntryFile)editorInput.getStorage();
								if (jarEntryFile.getJarFilePath().equals(jarFilePath)) {
									page.closeEditor(reference.getEditor(false), false);
								}
							}
						} catch (PartInitException e) {
							e.printStackTrace();
						}
						catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, false);

	}
	
	public static void closeProjectEditors(final String projectName) {
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (page != null) {
					for(IEditorReference reference:  page.getEditorReferences()) {
						try {
							if (reference.getEditorInput() instanceof JarEntryEditorInput) {
								JarEntryEditorInput editorInput =(JarEntryEditorInput) reference.getEditorInput();
								JarEntryFile jarEntryFile = (JarEntryFile)editorInput.getStorage();
								if (jarEntryFile.getProjectName().equals(projectName)) {
									page.closeEditor(reference.getEditor(false), false);
								}
							} else if (reference.getEditorInput() instanceof IFileEditorInput) {
								IFileEditorInput fei = (IFileEditorInput) reference.getEditorInput();
								if (fei.getFile() != null && fei.getFile().getProject().getName().equals(projectName)) {
									page.closeEditor(reference.getEditor(false), false);
								}
							}
						} catch (PartInitException e) {
							e.printStackTrace();
						}
						catch (CoreException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}, false);
		
	}

	/**
	 * @param projectName
	 * @param newRank
	 * @return
	 */
	public static boolean isSharedRankRuleElement(String projectName, String newRank) {
		DesignerProject project = IndexUtils.getIndex(projectName);
		List<SharedRuleElement> list = new ArrayList<SharedRuleElement>();
		for(DesignerProject refProject:project.getReferencedProjects()) {
			for(DesignerElement element:refProject.getEntries()) {
				if (element instanceof SharedRuleElement) {
					list.add((SharedRuleElement)element);
				}
				if (element instanceof Folder) {
					traverseSharedRuleFunction((Folder)element, list);
				}
			}
		}
		for(SharedRuleElement sharedRuleElement:list) {
			String rank = sharedRuleElement.getFolder() + sharedRuleElement.getName();
			if (rank.equals(newRank)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param folder
	 * @param list
	 */
	public static void traverseSharedRuleFunction(Folder folder, List<SharedRuleElement> list) {
		for(DesignerElement element:folder.getEntries()) {
			if (element instanceof SharedRuleElement) {
				SharedRuleElement sharedRuleElement = (SharedRuleElement)element;
				list.add(sharedRuleElement);
			}
			if (element instanceof Folder) {
				traverseSharedRuleFunction(folder, list);
			}
		}
	}

	/**
	 * @param projectName
	 * @param path
	 * @return
	 */
	public static SharedEntityElement getSharedEntityElement(String projectName, String path) {
		DesignerProject project = IndexUtils.getIndex(projectName);
		List<SharedEntityElement> list = new ArrayList<SharedEntityElement>();
		for(DesignerProject refProject:project.getReferencedProjects()) {
			for(DesignerElement element:refProject.getEntries()) {
				if (element instanceof SharedEntityElement) {
					list.add((SharedEntityElement)element);
				}
				if (element instanceof Folder) {
					traverseSharedEntitities((Folder)element, list);
				}
			}
		}
		for(SharedEntityElement sharedElement:list) {
			String p = sharedElement.getFolder() + sharedElement.getName();
			if (p.equals(path)) {
				return sharedElement;
			}
		}
		return null;
	}
	/**
	 * @param projectName
	 * @param path
	 * @return
	 */
	public static boolean sharedElementExists(String projectName, String path) {
		DesignerProject project = IndexUtils.getIndex(projectName);
		List<SharedEntityElement> list = new ArrayList<SharedEntityElement>();
		for(DesignerProject refProject:project.getReferencedProjects()) {
			for(DesignerElement element:refProject.getEntries()) {
				if (element instanceof SharedEntityElement) {
					list.add((SharedEntityElement)element);
				}
				if (element instanceof Folder) {
					traverseSharedEntitities((Folder)element, list);
				}
			}
		}
		for(SharedEntityElement sharedElement:list) {
			String p = sharedElement.getFolder() + sharedElement.getName();
			if (p.equals(path)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param folder
	 * @param list
	 */
	public static void traverseSharedEntitities(Folder folder, List<SharedEntityElement> list) {
		for(DesignerElement element:folder.getEntries()) {
			if (element instanceof SharedEntityElement) {
				SharedEntityElement sharedEntityElement = (SharedEntityElement)element;
				list.add(sharedEntityElement);
			}
			if (element instanceof Folder) {
				traverseSharedEntitities((Folder)element, list);
			}
		}
	}

	public static boolean saveDirtyEditor(IResource entityFile) {
		try {
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart[] dirtyEditors = activePage.getDirtyEditors();
			IEditorPart dirtyPart = null;
			for (IEditorPart editorPart : dirtyEditors) {
				if (editorPart.getEditorInput() instanceof FileEditorInput) {
					FileEditorInput fei = (FileEditorInput) editorPart.getEditorInput();
					if (entityFile.equals(fei.getFile())) {
						dirtyPart = editorPart;
						break;
					}
				}
			}
			if (dirtyPart != null) {
				if (MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Save editor", "You must save the modified resource before continuing.  Would you like to save now?")) {
					return activePage.saveEditor(dirtyPart, false);
				}
				return false;
			}
		} catch (NullPointerException e) {
		}
		return true;
	}

	/**
	 * @param page
	 * @param projectName
	 * @param editorDirtyCheck
	 * @return
	 * @throws Exception
	 */
	public static boolean saveAllEditors(IWorkbenchPage page,
										 String projectName,
			     						 boolean editorDirtyCheck) throws Exception{
		for(IEditorReference reference:page.getEditorReferences()) {
			if (reference.getEditorInput() instanceof FileEditorInput) {
				if (((FileEditorInput)reference.getEditorInput()).getFile().getProject().getName().equals(projectName)
						&& reference.getEditor(true).isDirty()) {
					if (editorDirtyCheck) {
						return true;
					}else{
						page.saveEditor(reference.getEditor(false), false);
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param toolkit
	 * @param sectionClient
	 * @param label
	 */
	public static Hyperlink createLinkField(final FormToolkit toolkit,
				                            final Composite sectionClient,
				                            final String label) {
		Hyperlink link = toolkit.createHyperlink(sectionClient, label,  SWT.NONE);
		link.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		return link;
	}


	public static IHyperlinkListener addHyperLinkFieldListener(final IDiagramEntitySelection select, 
			Hyperlink link,
			final Control control,
			final IEditorPart editor,
			final String projectName,
			final boolean isDestination,
			final boolean isNonEntity, 
			final ELEMENT_TYPES[] types, 
			final boolean customFunction) {
		IHyperlinkListener listener = new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				handleLinkEvent(select, control, editor, projectName, isDestination, isNonEntity, types, customFunction);
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void linkExited(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}
		};
		link.addHyperlinkListener(listener);

		return listener;
	}

	/**
	 * @param link
	 * @param textField
	 * @param editor
	 * @param projectName
	 * @param isDestination
	 * @param isNonEntity
	 */
	public static IHyperlinkListener addHyperLinkFieldListener(Hyperlink link,
					                             final Control control,
					                             final IEditorPart editor,
					                             final String projectName,
					                             final boolean isDestination,
					                             final boolean isNonEntity) {
		IHyperlinkListener listener = new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				handleLinkEvent(control, editor, projectName, isDestination, isNonEntity);
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void linkExited(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}
		};
		link.addHyperlinkListener(listener);
		return listener;
	}


	/**
	 * @param link
	 * @param control
	 * @param editor
	 * @param projectName
	 * @param isRule
	 */
	public static IHyperlinkListener addRuleHyperLinkFieldListener(Hyperlink link,
													 final Control control,
													 final IEditorPart editor,
													 final String projectName,
													 final boolean isRule) {
		IHyperlinkListener listener = new IHyperlinkListener() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				try {
					String fullPath = null;
					if (control instanceof Text) {
						fullPath = ((Text)control).getText();
					}
					if (control instanceof PropertyTypeCombo) {
						fullPath = ((PropertyTypeCombo)control).getText();
					}

					if (fullPath != null) {

						if (isRule) {
							fullPath = fullPath + "." + CommonIndexUtils.RULE_EXTENSION;
						} else {
							fullPath = fullPath + "." + CommonIndexUtils.RULEFUNCTION_EXTENSION;
						}

						IResource resource = ValidationUtils.resolveResourceReference(fullPath, projectName);
						if (resource != null) {
							IFile input = (IFile)resource;
							IDE.openEditor(editor.getSite().getPage(), input);
						}
						return;
					}
				} catch (Exception ex) {
					StudioUIPlugin.log(ex);
				}
			}

			@Override
			public void linkEntered(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void linkExited(HyperlinkEvent e) {
				// TODO Auto-generated method stub

			}
		};
		
		link.addHyperlinkListener(listener);
		
		return listener;

	}

	/**
	 * @param link
	 * @param textField
	 * @param editor
	 * @param projectName
	 * @param isDestination
	 * @param isNonEntity
	 */
	public static void addLinkFieldListener(Link link,
											final Text textField,
											final IEditorPart editor,
											final String projectName,
											final boolean isDestination,
											final boolean isNonEntity) {
		link.addListener (SWT.Selection, new Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				handleLinkEvent(textField, editor, projectName, isDestination, isNonEntity);
			}
		});
	}
	
	/**
	 * 
	 * @param control
	 * @param editor
	 * @param projectName
	 * @param isDestination
	 * @param isNonEntity
	 * @param types
	 */
	private static void handleLinkEvent(IDiagramEntitySelection select, final Control control,
			final IEditorPart editor,
			final String projectName,
			final boolean isDestination,
			final boolean isNonEntity,
			final ELEMENT_TYPES[] types, 
			boolean customFunction) {
		try {
			String fullPath = null;
			if (control instanceof Text) {
				fullPath = ((Text)control).getText();
			}
			if (control instanceof PropertyTypeCombo) {
				fullPath = ((PropertyTypeCombo)control).getText();
			}
			if (fullPath.isEmpty() && types.length > 0) {
				if (types.length == 1 
						&& (types[0] == ELEMENT_TYPES.RULE_FUNCTION 
						|| types[0] == ELEMENT_TYPES.SIMPLE_EVENT 
						|| types[0] == ELEMENT_TYPES.TIME_EVENT
						|| types[0] == ELEMENT_TYPES.JAVA_SOURCE
						||  types[0] == ELEMENT_TYPES.RULE)) {
					openNewResourceLink(select, control, types[0], projectName, customFunction);
				}
			} else {
				handleLinkEvent(control, editor, projectName, isDestination, isNonEntity);
			}
		} catch (Exception ex) {
			StudioUIPlugin.log(ex);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void openNewResourceLink(IDiagramEntitySelection select, 
			                               final Control control, 
			                               ELEMENT_TYPES fEntityType, 
			                               String projectName, 
			                               boolean customFunction) {
		AbstractNewEntityWizard wiz = null;
		switch (fEntityType) {
		case CONCEPT:
			wiz = new NewConceptWizard(select, projectName);
			break;
			
		case SIMPLE_EVENT:
			wiz = new NewEventWizard(select, projectName);
			break;
			
		case TIME_EVENT:
			wiz = new NewTimeEventWizard(select, projectName);
			break;
			
		case RULE_FUNCTION:
			wiz = new NewRuleFunctionWizard(select, projectName);
			break;
		case JAVA_SOURCE:
			if (customFunction) {
				wiz = new NewJavaSourceWizard(select, projectName);
			} else {
				wiz = new NewJavaTaskResourceWizard(select, projectName);
			}
			break;	
		case RULE:
			wiz = new NewRuleWizard(select, projectName);
			break;	
	     default:
			break;
		}
		if (wiz != null) {
			runWizard(select, control, wiz);
			
		}
		
	}

	@SuppressWarnings("rawtypes")
	private static boolean runWizard(IDiagramEntitySelection select, final Control control, final AbstractNewEntityWizard wiz) {
		status = -1;
		try{
			Display.getDefault().syncExec(new Runnable(){
				/* (non-Javadoc)
				 * @see java.lang.Runnable#run()
				 */
				@Override
				public void run() {
					wiz.setOpenEditor(false);
					WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),wiz) {
						@Override
						protected void createButtonsForButtonBar(Composite parent) {
							super.createButtonsForButtonBar(parent);
							Button finishButton = getButton(IDialogConstants.FINISH_ID);
							finishButton.setText(IDialogConstants.OK_LABEL);
						}
					};
					dialog.create();
					status = dialog.open();
				}});
			if (status == WizardDialog.CANCEL) {
				return false;
			}
			else if (status == WizardDialog.OK) {
				
//				waitForUpdate();
				
				IFile file = select.getEntityFile();
				String folder = StudioResourceUtils.getFolder(file);
				String name = file.getName().replace(CommonIndexUtils.DOT + file.getFileExtension(), "");
				if (control instanceof Text) {
					Text text = (Text)control;
					text.setText(folder + name);
					return true;		
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;		
	}
	
	public static void waitForUpdate() {
        IJobManager jobManager = Job.getJobManager();
        Job[] jobsOnProject = jobManager.find(IndexResourceChangeListener.UPDATE_INDEX_FAMILY);        
        for (int i = 0; i < jobsOnProject.length; i++) {
            if (jobsOnProject[i] instanceof UpdateReferencedIndexJob) {
            	UpdateReferencedIndexJob updateJob = (UpdateReferencedIndexJob) jobsOnProject[i];
            	try {
					updateJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
	}
	

	/**
	 * @param textField
	 * @param editor
	 * @param projectName
	 * @param isDestination
	 * @param isNonEntity
	 */

	private static void handleLinkEvent( final Control control,
										 final IEditorPart editor,
										 final String projectName,
										 final boolean isDestination,
										 final boolean isNonEntity) {
		try {
			String fullPath = null;
			if (control instanceof Text) {
				fullPath = ((Text)control).getText();
			}
			if (control instanceof PropertyTypeCombo) {
				fullPath = ((PropertyTypeCombo)control).getText();
			}

			if (fullPath != null && fullPath.isEmpty())
				return;
			
			Entity entity = null;
			if (fullPath != null) {
				if (isDestination) {
					Map<String, Destination> map = CommonIndexUtils.getAllDestinationsURIMaps(projectName);
					if (map.containsKey(fullPath)) {
						entity = map.get(fullPath).getDriverConfig().getChannel();
					}
					StudioUIPlugin.getDefault().setSelectedEntity(map.get(fullPath));
					openEditor(entity, map.get(fullPath));
					return;
				} else if (isNonEntity) {
					IResource resource = ValidationUtils.resolveResourceReference(fullPath, projectName);
					if (resource != null) {
						IFile input = (IFile)resource;
						IDE.openEditor(editor.getSite().getPage(), input);
					}
					else{
						IFile input = IndexUtils.getLinkedResource(projectName, fullPath);
						if (input.exists()) {
							IDE.openEditor(editor.getSite().getPage(), input);
						}
					}
						
					return;
				} else {
					entity = IndexUtils.getEntity(projectName, fullPath);
				}

				if (entity != null) {
					IFile input = IndexUtils.getFile(projectName, entity);
					if(input==null){
						input = IndexUtils.getLinkedResource(projectName, fullPath);
					}
					if (input!=null && !input.exists()) {
						input = IndexUtils.getLinkedResource(projectName, fullPath);
					}
					IDE.openEditor(editor.getSite().getPage(), input);
				} else {
					DesignerElement element = IndexUtils.getElement(projectName, fullPath);
					if (element instanceof TypeElement) {
						IFile input = IndexUtils.getFile(projectName, (TypeElement) element);
						if (!input.exists()) {
							input = IndexUtils.getLinkedResource(projectName, fullPath);
						}
						IDE.openEditor(editor.getSite().getPage(), input);
					}
				}
			}
		} catch (Exception ex) {
			StudioUIPlugin.log(ex);
		}
	}

	/**
	 * @param propertyDefinition
	 * @param page
	 * @param projectName
	 * @param selectedDomainpaths
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean runMultipleDomainResourceSelector(PropertyDefinition propertyDefinition,
					                                        IWorkbenchPage page,
					                                        String projectName,
					                                        Set<String> selectedDomainpaths) {
		try {
			DomainResourceSelector picker = new DomainResourceSelector(page.getWorkbenchWindow().getShell(),
																	   projectName,
																	   propertyDefinition.getDomainInstances(),
																	   propertyDefinition.getType());
			if (picker.open() == Dialog.OK) {
				if (picker.getFirstResult()!=null) {
					Set<Object> domainObjects =(Set<Object>) picker.getFirstResult();
					propertyDefinition.getDomainInstances().clear();
					for(Object dm: domainObjects) {
						String fullPath = null;
						if (dm instanceof IFile) {
							IFile domainFile = (IFile)dm;
							fullPath =  IndexUtils.getFullPath(domainFile);
						}
						if (dm instanceof SharedEntityElement) {
							fullPath = ((SharedEntityElement)dm).getEntity().getFullPath();
						}
						if (fullPath != null) {
							selectedDomainpaths.add(fullPath);
						}
					}
				}
				return true;
			}
		}
		catch (Exception e2) {
			StudioUIPlugin.debug(e2.getMessage());
			return false;
		}
		return false;
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
		}  else if (propeFieldType.equalsIgnoreCase("Process")) {
			field.setImage(getPropertyFieldImage(propeFieldType));
			field.setText(isArray ? path + "[]" : path);
		}else if (propeFieldType.equalsIgnoreCase("Void")) {
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
		if(propeFieldType.equalsIgnoreCase("Concept")){
			return StudioUIPlugin.getDefault().getImage("icons/concept.png");
		}else if(propeFieldType.equalsIgnoreCase("Event")){
			return StudioUIPlugin.getDefault().getImage("icons/event.png");
		}else if(propeFieldType.equalsIgnoreCase("Process")){
			return StudioUIPlugin.getDefault().getImage("icons/appicon16x16.gif");
		}else if(propeFieldType.equalsIgnoreCase("Void")){
			return StudioUIPlugin.getDefault().getImage("icons/no_type.png");
		}else if(propeFieldType.equalsIgnoreCase("Object")){
			return StudioUIPlugin.getDefault().getImage("icons/no_type.png");
		}
		else{
			//	    	 if(propeFieldType.equals("int")){
			//	    		 propeFieldType = "Integer";
			//	    	 }else{
			//	    		 propeFieldType = Character.toUpperCase(propeFieldType.charAt(0)) + propeFieldType.substring(1);
			//	    	 }
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
	 * @param file
	 * @param editorId
	 * @param path
	 * @param extension
	 * @param testDataExtension
	 */
	public static String createTestData(Entity entity, String projectName, String alias,
			String extension, String testDataExtension) {
		try {
			IFile file = IndexUtils.getFile(projectName, entity);
			String input_dir = StudioUIPlugin
					.getDefault()
					.getPreferenceStore()
					.getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);
			String path = file.getProject().getLocation().toOSString()
					+ input_dir;
			String testFilePath = path + entity.getFullPath() + "."
					+ testDataExtension;
			File testFile = new File(testFilePath);
			if (testFile.exists()) {
				
				MessageDialog messageDialog = new MessageDialog(
						Display.getDefault().getActiveShell(),
						com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.title"),
						null,
						com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.desc.with.alias", entity.getFullPath(), alias),
						MessageDialog.QUESTION_WITH_CANCEL, 
						new String[]{
							IDialogConstants.YES_LABEL, 
							IDialogConstants.NO_LABEL, 
							IDialogConstants.CANCEL_LABEL},
						0
						);
				
				switch(messageDialog.open()) {
				case 0: 
					testFile.delete();
					break;
				case 1:
					NO_OPTION_SELECTED=1;
					return testFilePath;
					
				case 2:
					return null;
					
				
				} 
			}
			
			else {
				if (!testFile.getParentFile().isDirectory()) {
					@SuppressWarnings("unused")
					boolean success = testFile.getParentFile().mkdirs();
				}
			}
			testFile.createNewFile();
			file.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
			return testFilePath;
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
		return null;
	}

	/**
	 * @param site
	 * @param perspectiveId
	 */
	public static void openPerspective(final IEditorSite site, final String perspectiveId) {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				_openPerspective(site, perspectiveId);
			}

		});
	}
	
	private static void _openPerspective(final IEditorSite site, final String perspectiveId) {
		IPerspectiveDescriptor perspectiveDescriptor = site.getPage() != null ? site.getPage().getPerspective() : null;
		
		if(perspectiveDescriptor!=null) {
			if (perspectiveId == null) {
				return;
			}
			if (perspectiveDescriptor.getId().equals("org.eclipse.debug.ui.DebugPerspective")) {
				return;
			}
	
			IPreferenceStore store = StudioUIPlugin.getDefault().getPreferenceStore();
			if (!perspectiveDescriptor.getId().equals(perspectiveId) && store.getBoolean(StudioUIPreferenceConstants.SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION)) {
				String perspectiveName = getPerspectiveName(perspectiveId);
				DoNotAskAgainMessageDialog dialog = new DoNotAskAgainMessageDialog(store, StudioUIPreferenceConstants.SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION, StudioUIPreferenceConstants.OPEN_CONFIRM_PERSPECTIVE_CHANGE,
						site.getShell(), Messages.getString("confirm.open.perspective.title"),
						Messages.getString("confirm.open.perspective.message", perspectiveName), MessageDialog.QUESTION);
				int confirm = dialog.open();
				try {
					if (confirm == Dialog.OK) {
						site.getWorkbenchWindow().getWorkbench().showPerspective(perspectiveId, site.getWorkbenchWindow());
						final IViewPart view = site.getWorkbenchWindow().getActivePage().findView(PaletteView.ID);
						if (view != null) {
							refocusPaletteView(site, view);
						}
					}
				} catch (WorkbenchException e) {
					StudioUIPlugin.log(e);
				}
			}
		}
	}

	private static void refocusPaletteView(final IEditorSite site, final IViewPart view) {
		Display.getCurrent().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					if (view != null) {
						view.setFocus();
					}
					site.getWorkbenchWindow().getActivePage().getActiveEditor().setFocus();
				} catch (Exception e) {
					// ignore - this is to initially populate the Palette view (BE-23804)
				}
			}
		});
	}

	/**
	 * @param id
	 * @return
	 */
	private static String getPerspectiveName(String id) {
		@SuppressWarnings("restriction")
		IPerspectiveRegistry registry = org.eclipse.ui.internal.WorkbenchPlugin.getDefault().getPerspectiveRegistry();
		for (IPerspectiveDescriptor pereDescriptor : registry.getPerspectives()) {
			if (pereDescriptor.getId().equals(id)){
				return pereDescriptor.getLabel();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param numstr
	 * @return
	 */
	public static boolean isNumeric(String numstr) {
		try {
			if (!numstr.trim().equalsIgnoreCase(""))
				Integer.parseInt(numstr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	// Workaround for Linux Platforms
	// BE-22214: BEStudio : CentOS 7 : Shared Resources are blank when created, 
	// user needs to close/re-open the editors
	public static void resetPerspective() {
		boolean isLinux = Platform.OS_LINUX.equals(Platform.getOS());
		if (isLinux) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().resetPerspective();
		}
	}
	
}