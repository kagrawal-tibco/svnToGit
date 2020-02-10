package com.tibco.cep.studio.core.index.resolution;

import java.util.List;
import java.util.Map;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.studio.core.index.model.ElementReference;

public interface IElementResolutionProvider {

	/**
	 * Search the root container (i.e. the DesignerProject) for the
	 * given reference.  Serves as the initial resolution step for a
	 * qualified name, i.e. <code>Concepts.Departments.Payroll</code> would return
	 * the index element for the "Concepts" segment
	 * @param reference
	 * @param resolutionContext
	 * @param projectName
	 * @return
	 */
	public Object searchContainer(ElementReference reference,
			IResolutionContext resolutionContext, String projectName);
	
	/**
	 * Process the <code>qualifier</code> Object for the 
	 * given <code>ElementReference</code>.  For instance, for the
	 * qualified name <code>Concepts.Departments.Payroll</code>, the
	 * qualifier would be an <code>ElementContainer</code> corresponding to the <code>Departments</code>
	 * segment.  This method would then search the <code>ElementContainer</code> for
	 * an entry named <code>Payroll</code>
	 * @param reference
	 * @param qualifier
	 * @param projectName
	 * @param contentAssistMode
	 * @param isEntityRef
	 * @return
	 */
	public Object processQualifier(ElementReference reference,
			Object qualifier, String projectName, boolean contentAssistMode, boolean isEntityRef);

	
	/**
	 * Resolve the type within the scope of the given project
	 * @param element
	 * @return
	 */
	public Object resolveType(String projectName, String type);
	
	/**
	 * Resolve the attribute accessor (i.e. @length) given the qualifier
	 * @param attributeName
	 * @param qualifier
	 * @param reference
	 * @param projectName
	 * @return
	 */
	public Object resolveAttributeReference(String attributeName,
			Object qualifier, ElementReference reference, String projectName);

	/**
	 * Get the type of the attribute, used for semantic analysis
	 * @param attributeName
	 * @param array
	 * @return
	 */
	public NodeType getAttributeType(String attributeName, boolean array);

	/**
	 * Get Element Container Children given <code>Object</code>, for instance ElementContainer
	 * @param container
	 * @param projectName
	 * @return
	 */
	public Object[] getElementContainerChildren(Object container, String projectName);
	
	/**
	 * Get element text for Label Provider
	 * @param element
	 * @return
	 */
	public String getElementText(Object element);
	
	/**
	 * Get element image type for Rule Label Provider
	 * @param element
	 * @return
	 */
	public String getElementImageType(Object element);
	
	/**
	 * Get info given element type for Text hover information
	 * @param element
	 * @return
	 */
	public String getElementInfo(Object element, String projectName);
	
	/**
	 * Fetching attributes for given element
	 * @param element
	 * @return
	 */
	public List<Map<String, String>> getElementAttributes(Object element);

	public Object[] getChildren(Object element);

}
