package com.tibco.cep.studio.ui.validation.resolution;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xsd.XSDTerm;

import com.tibco.xml.mappermodel.emfapi.EMapperModelInputOutputAdapter;
import com.tibco.xml.mappermodel.emfapi.EMapperModelInputOutputProxy;
import com.tibco.xml.mappermodel.emfapi.VariableBindingKind;
import com.tibco.xml.mappermodel.exports.MapperFactory;
import com.tibco.xml.mappermodel.primary.IMapperModelForUI;
import com.tibco.xml.mappermodel.primary.TargetNode;
import com.tibco.xml.xmodel.bind.fix.BindingFixerChange;
import com.tibco.xml.xmodel.bind.fix.BindingFixerChangeList;
import com.tibco.xml.xmodel.mmodel.IMapperModel;

public class XPathMapperModelWrapper extends EMapperModelInputOutputAdapter {

	private IMapperModel mMapperModel;
//	private List<String> mVariableNames;
//	private List<? extends EObject> mSourceTerms;
	private EObject mTargetTerm;
	private String mXslt;
	private ResourceSet mResourceSet;
	private VariableBindingKind mVarBindingKind;
	private EMapperModelInputOutputProxy mMapperInputOutputProxy;
	private EMapperModelInputOutputAdapter mModelInput;

	
	public XPathMapperModelWrapper() {
		mMapperModel = MapperFactory.createXPathBuilderModel();
	}

	/**
	 * @param variableNames
	 *            A List<String> of variable names that contain the input for
	 *            the mapping.
	 * @param sourceTerms
	 *            A List<EObject> of terms which are bound to the variableNames.
	 *            Each term would typically be of type XSDElementDeclaration
	 *            (but we support wsdl messages too).
	 * @param bindingKind
	 *            A value from VariableBindingKind enum. Use ELEMENT to bind the
	 *            actual terms to the variables.
	 * @param targetTerm
	 *            An EObject. Typically of type XSDElementDeclaration.
	 * @param xslt
	 *            The actual XSLT.
	 * @param resourceSet
	 *            A ResourceSet that contains all the schemas and wsdls in the
	 *            workspace.
	 */
	public void setInput(EMapperModelInputOutputAdapter modelInput, VariableBindingKind bindingKind, EObject targetTerm, String xslt,
			ResourceSet resourceSet) {
		mModelInput = modelInput;
//		mVariableNames = variableNames;
//		mSourceTerms = sourceTerms;
		mTargetTerm = targetTerm;
		mXslt = xslt;
		mResourceSet = resourceSet;
		mVarBindingKind = bindingKind;
		mMapperInputOutputProxy = new EMapperModelInputOutputProxy(this);
		mMapperModel.setModelInputInternal(mMapperInputOutputProxy);
	}

	/**
	 * Returns the expanded XSLT in an array of length 2. If both entries in the
	 * array are the same then only 1 transformation is needed. If they are
	 * different, then you need 2 transformations. The 2nd transformation will
	 * need an additional variable called "__comboVar" which should be bound to
	 * the output document of the 1st transformation.
	 */
	public String[] doPingConvert() {
		return super.doPingConvert();
	}

	public boolean isPingEnabled() {
		return true;
	}

	public List<String> getSourceTreeRootLabels() {
		return this.mModelInput.getSourceTreeRootLabels();
	}

	public List<? extends EObject> getESourceTerms() {
		return this.mModelInput.getESourceTerms();
	}
	
	@Override
	public Map<String, String> getPrefixToNamespaceMap() {
		return this.mModelInput.getPrefixToNamespaceMap();
	}

	@Override
	public List<XSDTerm> getSourceXSDTerms() {
		return this.mModelInput.getSourceXSDTerms();
	}

	public String getTargetTreeRootLabel() {
		return this.mModelInput.getTargetTreeRootLabel();
	}

	public EObject getETargetTerm() {
		return this.mTargetTerm;//this.mIOAdapter.getETargetTerm();
	}

	@Override
	public String getXPath() {
		return mXslt;
	}

	public String getXSLT() {
		return mXslt;
	}

	public ResourceSet getResourceSet() {
		return mResourceSet;
	}

	public VariableBindingKind getVariableBindingKind() {
		return mVarBindingKind;
	}

	/**
	 * returns null if nothing fixed.
	 */
	public String fixTypeCastErrors(boolean indent) {
		return mMapperModel.fixTypeCastErrors(indent);
	}

	/**
	 * returns null if nothing fixed.
	 */
	public String fixErrors(boolean indent, boolean onlyCopyModeOutOfSync, boolean onlyTypeCastErrors) {
		BindingFixerChangeList fixerList = ((IMapperModelForUI)mMapperModel).getFixerList(onlyCopyModeOutOfSync, onlyTypeCastErrors);
		if (fixerList == null) {
			return null;
		}
		List<BindingFixerChange> changes = fixerList.getChanges();
		boolean canFix = false;
		for (BindingFixerChange bindingFixerChange : changes) {
			if (bindingFixerChange.isTypeCastError() || bindingFixerChange.isCopyModeOutOfSync() || bindingFixerChange.isUnexpectedOutputBinding()) {
				canFix = true;
				break;
			}
		}
		if (!canFix) {
			return null;
		}
		if (mMapperModel.autoFix(onlyCopyModeOutOfSync, onlyTypeCastErrors)) {
			TargetNode root = ((IMapperModelForUI)mMapperModel).getTargetRoot();
			if (root == null || root.getChildCount() == 0) {
				return null;
			}
			TargetNode targetNode = root.getChild(0);
			return targetNode.getBinding().getFormula();
		}
		return null;
	}

	public EMapperModelInputOutputProxy getMapperModelInputOutputProxy() {
		return this.mMapperInputOutputProxy;
	}
	
	@Override
	public String getXPathResultNativeTypeName() {
		return this.mModelInput.getXPathResultNativeTypeName();
	}

//	public static String fixTypeCastErrors(String projectName, List<String> variableNames, String xslt, EObject actInputElemDecl,
//			XslMapperModelWrapper mapperModelWrapper, List<? extends EObject> sourceTerms) {
//		int varNmSize = variableNames.size();
//		int srcTermSize = sourceTerms.size();
//		if (actInputElemDecl != null && varNmSize > 0 && srcTermSize > 0 && varNmSize == srcTermSize) {
//			mapperModelWrapper.setInput(variableNames, sourceTerms, VariableBindingKind.ELEMENT, actInputElemDecl, xslt, StudioCorePlugin.getSchemaCache(projectName).getResourceSet());
//			String fxslt = mapperModelWrapper.fixTypeCastErrors(false);
//			if (fxslt == null) {
//				fxslt = xslt;
//			}
//			return fxslt;
//		}
//		return xslt;
//	}

}
