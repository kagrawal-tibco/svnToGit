package com.tibco.cep.studio.ui.compare.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.compare.BufferedContent;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.IStructureComparator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.core.compare.impl.EntityResourceImpl;
import com.tibco.cep.studio.core.compare.impl.TableResourceImpl;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.compare.provider.StructureContentProvider;

@SuppressWarnings({"rawtypes","unchecked"})
public class EMFCompareNode extends BufferedContent implements IStructureComparator,
		ITypedElement, IEditableContent {

	protected static final Object[] EMPTY_CHILDREN = new Object[0];
	
	protected AbstractTreeNode input;

	private EObject parent;

	private boolean fAutoMerge = false;
	protected static ITreeContentProvider provider = new StructureContentProvider();
	
	public EMFCompareNode(EObject parent, AbstractTreeNode input) {
		this(parent, input, false);
	}
	
	public EMFCompareNode(EObject parent, AbstractTreeNode input, boolean autoMerge) {
		this.parent = parent;
		this.input = input;
		this.fAutoMerge = autoMerge;
	}

	@Override
	protected InputStream createStream() throws CoreException {
		return getContents();
	}

	/**
	 * Delegates to the IContentProvider to get the children.
	 * Clients should override if no content provider is set
	 */
	public Object[] getChildren() {
		if (provider == null) {
			return EMPTY_CHILDREN;
		}
		Object[] objectChildren = provider.getChildren(input);
		Object[] children = new Object[objectChildren.length];
		if (children.length == 0) {
			return EMPTY_CHILDREN;
		}
		for (int i=0; i < objectChildren.length; i++) {
			children[i] = new EMFCompareNode(parent, (AbstractTreeNode) objectChildren[i], fAutoMerge);
		}

		return children;
	}

	public Image getImage() {
		return null;
	}

	public String getName() {
		return input.getClass().getSimpleName();
	}

	public String getType() {
		return TEXT_TYPE;
	}

	/**
	 * This is a "specialized" equals method, as it is not
	 * intended to behave like the normal "equals" method.
	 * Instead, two compare objects should be considered equal
	 * if they are meant to be compared with each other.  For instance,
	 * a <code>TableRule</code> can be considered equal if the IDs are equal, even
	 * though the contents of the rule could be different.<br/>
	 * If two <code>TableRule</code>s with the same ID are not determined
	 * to be equal, one will be considered as an "ADDITION" and the other will
	 * be considered as a "DELETION" (see the <code>Differencer</code> class)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EMFCompareNode)) {
			return false;
		}
		return getInput().isEqualTo(((EMFCompareNode)obj).getInput());
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	/**
	 * This method attempts to obtain the xmi for the EMF object
	 * for this node, and returns a {@link ByteArrayInputStream} with
	 * that xmi.  This method is used to populate the text area of the
	 * compare window.<br/><br/>
	 * Clients should override if a different text representation of the
	 * input object is required.
	 */
	@Override
	public InputStream getContents() throws CoreException {
		XMIResourceImpl resource = null;
		Object object = input.getInput();
		if (object instanceof EObject) {
			if (object instanceof Table) {
				resource = new TableResourceImpl(URI.createURI(""));	
			} else {
				resource = new EntityResourceImpl(URI.createURI(""));
			}
			resource.getContents().add(EcoreUtil.copy((EObject)object));
			Map<?, ?> options = new HashMap<Object, Object>();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try {
				resource.save(outputStream, options);
				ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
				return stream;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			try {
				return new ByteArrayInputStream(getName().getBytes(ModelUtils.DEFAULT_ENCODING));
			} catch (Exception e) {
			}
		} else if (object instanceof String) {
			try {
				return new ByteArrayInputStream(((String)object).getBytes(ModelUtils.DEFAULT_ENCODING));
			} catch (Exception e) {
			}
		}
		return null;
	}

	public AbstractTreeNode getInput() {
		return input;
	}

	public static void setProvider(ITreeContentProvider provider) {
		EMFCompareNode.provider = provider;
	}

	public boolean isEditable() {
		return false;
	}

	public ITypedElement replace(ITypedElement destination, ITypedElement source) {
//		use EMF info to add/set the eStructuralFeature that corresponds to src's input
		if ((destination != null && !(destination instanceof EMFCompareNode)) 
				|| (source != null && !(source instanceof EMFCompareNode))) {
			return destination;
		}
		EMFCompareNode dest = (EMFCompareNode) destination;
		EMFCompareNode src = (EMFCompareNode) source;
		Object srcObject = getElementInput(src);
		Object destObject = getElementInput(dest);
		Object object = input.getInput();
		if (object instanceof TableRuleVariable) {
			
		}
		if (object == null && destObject instanceof EObject && ((EObject)destObject).eContainer() != null) {
			object = ((EObject)destObject).eContainer();
		} else if (object == null) {
			return dest;
		}
		if (object instanceof EObject) {
			EObject targetParent = (EObject)object;
	
			EStructuralFeature feature = null;
			if (targetParent instanceof TableRuleVariable) {
				// special case, as the feature is an EAttribute (a String), not an EReference (EMF object)
				if (src != null) {
					feature = targetParent.eClass().getEStructuralFeature(src.getInput().getFeatureId());
				} else if (dest != null) {
					feature = targetParent.eClass().getEStructuralFeature(dest.getInput().getFeatureId());
				}
			} else if (destObject instanceof EObject) {
				feature = ((EObject) destObject).eClass().getEStructuralFeature(dest.getInput().getFeatureId());
			} else if (srcObject instanceof EObject) {
				feature = ((EObject) srcObject).eClass().getEStructuralFeature(src.getInput().getFeatureId());
			}
			if (feature == null) {
				return dest;
			}
			
			if (src == null) {
				// this is a 'remove', remove dest from targetParent
				//return handleRemove(dest, src, destObject, targetParent, feature);
				return dest;				
			}
			
			if (dest == null && srcObject != null) {
				// this is an 'add', add src to targetParent
				return handleAdd(src, srcObject, targetParent, feature);
			} else {
				// replace 'dest' with 'src'.  Care must be taken to maintain containment relationships
				return handleReplace(dest, src, srcObject, destObject,
						targetParent, feature);
			}
		}
		return null;
	}

	private ITypedElement handleReplace(EMFCompareNode dest,
			EMFCompareNode src, Object srcObject,
			Object destObject, EObject targetParent,
			EStructuralFeature feature) {
		Object featureObject = targetParent.eGet(feature);
		Object newObject = null;
		if (featureObject instanceof List) {
			int i = ((List)featureObject).indexOf(destObject);
			((List)featureObject).remove(destObject);
			if (srcObject instanceof EObject) {
				newObject = EcoreUtil.copy((EObject) srcObject);
			}
			if (i != -1) {
				((List)featureObject).add(i, newObject); // do an insert here at the same spot as the replaced object
			} else {
				((List)featureObject).add(newObject);
			}
		} else {
			if (srcObject instanceof EObject) {
				newObject = EcoreUtil.copy((EObject) srcObject);
			}
			targetParent.eSet(feature, newObject);
		}
		if (newObject instanceof EObject) {
			getInput().handleReplace(dest.getInput(), src.getInput(), (EObject) newObject);
		}
		return dest;
	}

	private ITypedElement handleAdd(EMFCompareNode src, Object srcObject,
			EObject targetParent, EStructuralFeature feature) {
		Object featureObject = targetParent.eGet(feature);
		Object newObject = srcObject;
		if (srcObject instanceof EObject) {
			newObject = EcoreUtil.copy((EObject) srcObject);
		}
		if (featureObject instanceof List) {
			((List)featureObject).add(newObject);
		} else {
			if (targetParent instanceof TableRuleVariable) {
				TableRuleVariable var = (TableRuleVariable) targetParent;
				if ((var.isModified() || !featureObject.equals(newObject)) && fAutoMerge ) {
					// do not do the merge, as there is a conflict
					throw new IllegalArgumentException("Conflict detected");
				}
			}
			targetParent.eSet(feature, newObject);
		}
		if (newObject instanceof EObject) {
			getInput().handleReplace(null, src.getInput(), (EObject) newObject);
		}
		return src;
	}

//	private ITypedElement handleRemove(EMFCompareNode dest, EMFCompareNode src,
//			Object destObject, EObject targetParent,
//			EStructuralFeature feature) {
//		Object featureObject = targetParent.eGet(feature);
//		AbstractTreeNode input = dest.getInput();
//		if (!input.isUnsettable()) {
//			if (!fAutoMerge) {
//				MessageDialog.openError(new Shell(), "Error", "Selected feature can not be set to null.");
//			}
//			return src;
//		}
//		if (featureObject instanceof List) {
//			if (fAutoMerge && destObject instanceof TableRule) {
//				TableRule rule = (TableRule) destObject;
//				if (rule.isModified()) {
//					// do not do the merge, as there is a conflict
//					throw new IllegalArgumentException("Conflict detected");
//				}
//			}
////			((TableRuleSet)targetParent).getMetaData(); // need to set modified index here
//			((List)featureObject).remove(destObject);
//		} else {
//			if (targetParent instanceof TableRuleVariable) {
//				TableRuleVariable var = (TableRuleVariable) targetParent;
//				if (var.isModified() && fAutoMerge ) {
//					// do not do the merge, as there is a conflict
//					throw new IllegalArgumentException("Conflict detected");
//				}
//			}
//			targetParent.eSet(feature, null);
//		}
//		
//		getInput().handleReplace(dest.getInput(), null, null);
//		return src;
//	}

	private Object getElementInput(ITypedElement element) {
		if (!(element instanceof EMFCompareNode)) {
			return null;
		}
		EMFCompareNode source = (EMFCompareNode) element;
		AbstractTreeNode node = source.getInput();
		Object obj = null;
//		if (node instanceof ExpressionTreeNode) {
//			obj = (EObjectImpl) ((ExpressionTreeNode) node).getParentVariable();
//		} else {
			obj = node.getInput();
//		}
		
		return obj;
	}
}
