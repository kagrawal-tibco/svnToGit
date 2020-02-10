package com.tibco.cep.decision.table.navigation;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.utils.LegacyDecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.studio.common.legacy.adapters.RuleFunctionModelTransformer;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;

/**
 * 
 * @author sasahoo
 *
 */
public class DecisionTableNavigatorContentProvider extends AbstractResourceContentProvider {


	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object [] getChildren(Object object) {
		if (!(object instanceof IFile)) {
			return EMPTY_CHILDREN;
		}
		try {
			IFile file = (IFile) object;
			EObject eo = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
			if (eo instanceof Table) {
				Table table = (Table)eo;
				RuleFunction vrf = getImplementedVRF(table, file.getProject().getName());
				com.tibco.cep.decisionproject.ontology.Argument argument = null;
				argument = OntologyFactory.eINSTANCE.createArgument();

				LegacyDecisionTableUtil.buildArgumentModelDesiderata(vrf, 
																	table, 
																	file.getProject().getName(), 
																	argument);
				if (argument != null) {

					return 	getAdapterFactoryContentProvider().getChildren(argument);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY_CHILDREN;
	}

	/**
	 * @param tableEModel
	 * @param projectName
	 * @return
	 */
	private static RuleFunction getImplementedVRF(Table tableEModel, String projectName) {
		//Get path of implemented VRF
		String vrfPath = tableEModel.getImplements();
		RuleElement ruleFunctionNewElement = 
			IndexUtils.getRuleElement(projectName, vrfPath, ELEMENT_TYPES.RULE_FUNCTION);
		com.tibco.cep.designtime.core.model.rule.RuleFunction newVRF = 
			(com.tibco.cep.designtime.core.model.rule.RuleFunction)ruleFunctionNewElement.getRule();
		RuleFunctionModelTransformer transformer = new RuleFunctionModelTransformer();
		RuleFunction oldVRF = OntologyFactory.eINSTANCE.createRuleFunction();
		transformer.transform(newVRF, oldVRF);
		return oldVRF;
	}

	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
	}
}
