package com.tibco.cep.studio.ui.compare;

import static com.tibco.cep.studio.core.index.utils.IndexUtils.getElement;
import static com.tibco.cep.studio.ui.compare.util.CompareUtils.getEObjectContents;
import static com.tibco.cep.studio.ui.compare.util.CompareUtils.load;
import static com.tibco.cep.studio.ui.compare.util.CompareUtils.loadTable;
import static com.tibco.cep.studio.ui.compare.util.CompareUtils.saveDirtyEditor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.Splitter;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.security.authz.utils.IOUtils;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.model.AbstractResourceNode;
import com.tibco.cep.studio.ui.compare.model.EMFResourceNode;
import com.tibco.cep.studio.ui.compare.model.RuleResourceNode;
import com.tibco.cep.studio.ui.compare.preferences.ComparePreferenceConstants;
import com.tibco.cep.studio.ui.util.Messages;



public abstract class EMFCompareEditorInput extends CompareEditorInput implements IStudioContentEditorInput {
	
	private StructuredSelection selection;
	private Viewer fViewer;
	private DiffNode fRoot;
	private IContentChangeListener fContentChangeListener;
	private CompareViewerPane fStructureInputPane;
	
	public EMFCompareEditorInput() {
		super(new CompareConfiguration(StudioUIPlugin.getDefault().getPreferenceStore()));
	}
	
	/**
	 * @param configuration
	 */
	public EMFCompareEditorInput(CompareConfiguration configuration) {
		super(configuration);
	}
	
	@Override
	public Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		if (contents instanceof Splitter) {
			((Splitter) contents).setWeights(new int[] { 65, 35 });
		}
		return contents;
	}

	@Override
	protected CompareViewerPane createStructureInputPane(Composite parent) {
		fStructureInputPane = super.createStructureInputPane(parent);
		return fStructureInputPane;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.compare.CompareEditorInput#createOutlineContents(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	public Control createOutlineContents(Composite parent, int direction) {
		Control outlineControl = super.createOutlineContents(parent, direction);
		if (outlineControl instanceof Splitter) {
			if (!StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP)) {
				((Splitter) outlineControl).setMaximizedControl(fStructureInputPane);
			}
		}
		return outlineControl;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.compare.CompareEditorInput#prepareInput(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected Object prepareInput(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		try {
			return getPreparedInput(getLeftInput(getCompareConfiguration()), getRightInput(getCompareConfiguration()));
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
		return monitor;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.history.compare.ICompareEditorInput#createResourceNode(java.lang.Object, boolean)
	 */
	public AbstractResourceNode createResourceNode(Object obj, boolean isLeft) throws IOException, CoreException {
		//fetch all data from compare configuration
		Object rev = getCompareConfiguration().getProperty(isLeft == true ? PROP_COMPARE_REVISION_ID_LEFT: PROP_COMPARE_REVISION_ID_RIGHT);
		String revision = rev != null? rev.toString(): "";   
		Object extnObj = getCompareConfiguration().getProperty(isLeft == true ? PROP_COMPARE_EXTN_LEFT: PROP_COMPARE_EXTN_RIGHT);
		String extn = extnObj != null? extnObj.toString(): "";   
 		boolean isRemote = (Boolean)getCompareConfiguration().getProperty(isLeft == true ? PROP_COMPARE_IS_LEFT_REMOTE : PROP_COMPARE_IS_RIGHT_REMOTE);
 		Object objName = getCompareConfiguration().getProperty(isLeft == true ? PROP_COMPARE_NAME_LEFT: PROP_COMPARE_NAME_RIGHT);
 		String name = objName == null? "" : objName.toString();
 		Object pathObj = getCompareConfiguration().getProperty(isLeft == true ? PROP_COMPARE_ABS_PATH_LEFT: PROP_COMPARE_ABS_PATH_RIGHT);
 		String path = pathObj == null? "" : pathObj.toString();
 		byte[] contents = null;
 		IFile file = null;
 		if (isRuleSource(extn)) { //check whether rule/rule function/any text compare type resource 
 			int featureId = extn.equals(CommonIndexUtils.RULE_EXTENSION)? RulePackage.RULE : RulePackage.RULE_FUNCTION;
 			if (obj instanceof IFile) {
				file = (IFile)obj;
				name = file.getName();
				path = file.getLocation().toString();
				contents = IOUtils.readBytes(((IFile)obj).getLocation().toFile().getPath());
			}
 			if (obj instanceof String) {
 				contents = obj.toString().getBytes();
 			}
			RuleResourceNode node = new RuleResourceNode(obj, name, path, extn, featureId, revision, isRemote, false);
			node.setLocalFile(file);
			node.setContent(contents);
			node.addContentChangeListener(getContentChangeListener());
			return node;
 		} else {
 			EObject model = null;
 			if (obj instanceof EObject) {
 				model = (EObject)obj;
 				contents = getEObjectContents(model);
 			}
 			if (obj instanceof IFile) {
 				file = (IFile)obj;
 				saveDirtyEditor(file);//Save corresponding dirty editor
 				if (file.getFileExtension().equals(CommonIndexUtils.RULE_FN_IMPL_EXTENSION)) {
 					DesignerElement element = getElement(file);
 					if (element instanceof DecisionTableElement) {
 						DecisionTableElement dtElement = (DecisionTableElement)element;
 						Implementation impl = (Implementation) ((DecisionTableElement) dtElement).getImplementation();
 						model = impl;
 						contents = getEObjectContents(model);
 					}
 				} else {
 					ResourceSet resourceSet = new ResourceSetImpl();
 					model = load(((IFile)obj).getContents(),file.getName(), resourceSet);
 					contents = IOUtils.readBytes(file.getLocation().toString());
 				}
 			}
 			if (obj instanceof String) {
 				contents = obj.toString().getBytes();
 				ResourceSet resourceSet = new ResourceSetImpl();
 				InputStream is = new ByteArrayInputStream(contents); 
 				if (extn.equals(CommonIndexUtils.RULE_FN_IMPL_EXTENSION)) {
 					model = loadTable(is, name, resourceSet);
 					((Table)model).getDecisionTable();
 					((Table)model).getExceptionTable();

 					contents = getDecisionTableByteArrayWithPrettyPrinting(obj.toString());
 					
 				} else {
 					model = load(is, name, resourceSet);
 				}
 			}
 			EMFResourceNode	node = new EMFResourceNode(model, revision, isRemote, false);	
 			node.setLocalFile(file);
 			node.addContentChangeListener(getContentChangeListener());
 			node.setContent(contents);
 			return node;
 		}
	}
	
	/**
	 * For pretty printing data
	 * @param dtString
	 * @return
	 */
	protected byte[] getDecisionTableByteArrayWithPrettyPrinting(String dtString) {
		byte[] contents = null;
		Source xmlInput = new StreamSource(new StringReader(dtString));
		StreamResult xmlOutput = new StreamResult(new StringWriter());
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(xmlInput, xmlOutput);
			contents = xmlOutput.getWriter().toString().getBytes(); 
		} catch (TransformerConfigurationException e) {
			StudioUIPlugin.debug(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			StudioUIPlugin.debug(e.getMessage());
		} 
		catch (TransformerException e) {
			StudioUIPlugin.debug(e.getMessage());
		}
		return contents;
	}
	
	/**
	 * Check for Rule or rule function
	 * can be overridden for any other only text compare type resource
	 * @param extn
	 * @return
	 */
	private boolean isRuleSource (String extn) {
		if (extn.equals(CommonIndexUtils.RULE_EXTENSION) || extn.equals(CommonIndexUtils.RULEFUNCTION_EXTENSION)) {
			return true;
		}
		return false;
	}

	private IContentChangeListener getContentChangeListener() {
		if (fContentChangeListener == null) {
			fContentChangeListener = new IContentChangeListener() {
				public void contentChanged(IContentChangeNotifier source) {
					if ( fViewer != null) {
						fViewer.refresh();
					}
					if (source instanceof AbstractResourceNode) {
						setDirty(((AbstractResourceNode)source).isDirty());
					}
				}
			};
		}
		return fContentChangeListener;
	}

	private Object getPreparedInput(AbstractResourceNode left, AbstractResourceNode right) {
		getCompareConfiguration().setLeftLabel(left.getName());
		getCompareConfiguration().setRightLabel(right.getName());
		String title = super.getTitle() + "(" + left.getShortName() + "-" + right.getShortName() + ")"; 
		setTitle(title);
		fRoot = new DiffNode(null, Differencer.CHANGE, null, left, right);
		return fRoot;
	}

	public void setSelection(StructuredSelection ss) {
		this.selection = ss;
	}

	@Override
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input,
			Composite parent) {
		fViewer = super.findContentViewer(oldViewer, input, parent);
		return fViewer;
	}

	public void saveChanges(IProgressMonitor pm) throws CoreException {
		super.saveChanges(pm);
		if (fRoot instanceof DiffNode) {
			try {
				commit(pm, (DiffNode) fRoot);
			} finally {
				setDirty(false);
			}
		}
	}
	
	private static void commit(IProgressMonitor pm, DiffNode node) throws CoreException {
		ITypedElement left= node.getLeft();
		if (left instanceof AbstractResourceNode) {
			AbstractResourceNode lNode = (AbstractResourceNode) left;
			if (!lNode.isRemote()) {
				lNode.commit(pm);
			}
		}
		ITypedElement right= node.getRight();
		if (right instanceof AbstractResourceNode) {
			AbstractResourceNode rNode = (AbstractResourceNode) right;
			if (!rNode.isRemote()) {
				rNode.commit(pm);
			}
		}
	}

	@Override
	public void setDirty(boolean dirty) {
		super.setDirty(dirty);
		if (dirty) {
			//need to enable save action...
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.compare.CompareEditorInput#getOKButtonLabel()
	 */
	public String getOKButtonLabel() {
		if (isEditable()) {
			return Messages.getString("Compare.save");
		}
		if (isEditionSelectionDialog()) {
			return Messages.getString("Compare.select");
		}
		return IDialogConstants.OK_LABEL;
	}

	private boolean isEditable() {
		return getCompareConfiguration().isLeftEditable() 
			|| getCompareConfiguration().isRightEditable();
	}
	
	/**
	 * @param isLeft
	 * @return
	 */
	protected Object getSelectedObject(boolean isLeft) throws Exception {
		if (getSelection() != null) {
			if (getSelection().size() == 2 && getSelection().getFirstElement() instanceof IFile) {
				Object[] selections = getSelection().toArray();
				Object leftFile = selections[0];
				Object rightFile = selections[1];
				getCompareConfiguration().setProperty(isLeft ==true ? PROP_COMPARE_IS_LEFT_REMOTE : PROP_COMPARE_IS_RIGHT_REMOTE, false);
				getCompareConfiguration().setProperty(isLeft == true ? PROP_COMPARE_EXTN_LEFT : PROP_COMPARE_EXTN_RIGHT, isLeft == true ? ((IFile)leftFile).getFileExtension() : 
					((IFile)rightFile).getFileExtension());
				getCompareConfiguration().setProperty(isLeft == true ? PROP_COMPARE_NAME_LEFT : PROP_COMPARE_NAME_RIGHT, 
						isLeft == true ? ((IFile)leftFile).getName(): ((IFile)rightFile).getName());
				getCompareConfiguration().setProperty(isLeft == true ? PROP_COMPARE_ABS_PATH_LEFT : PROP_COMPARE_ABS_PATH_RIGHT, 
						isLeft == true ? ((IFile)leftFile).getLocation().toString(): ((IFile)rightFile).getLocation().toString());
				return isLeft == true ? leftFile :  rightFile;
			}
		} 
		return null;
	}

	public StructuredSelection getSelection() {
		return selection;
	}
}