package com.tibco.cep.studio.core.index.utils;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.core.index.DefaultStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.StructuredElement;
import com.tibco.cep.studio.core.index.model.TypeElement;

public class ElementFinderVisitor extends DefaultStructuredElementVisitor {

	private ELEMENT_TYPES[] fTypes;
	private String fName;
	private List<DesignerElement> fFoundElements = new ArrayList<DesignerElement>();
	private boolean fHasMultiple;
	private boolean fFoundMatch = false;

	public ElementFinderVisitor(ELEMENT_TYPES type, String name, boolean hasMultiple) {
		this(new ELEMENT_TYPES[] { type }, name, hasMultiple);
	}
	
	public ElementFinderVisitor(ELEMENT_TYPES type, String name) {
		this(new ELEMENT_TYPES[] { type }, name, true);
	}
	
	public ElementFinderVisitor(ELEMENT_TYPES[] types, String name, boolean hasMultiple) {
		this.fTypes = types;
		this.fName = name;
		this.fHasMultiple = hasMultiple;
	}
	
	public ElementFinderVisitor(ELEMENT_TYPES[] types, String name) {
		this(types, name, true);
	}

	@Override
	public boolean visit(StructuredElement element) {
		if (fFoundMatch && !fHasMultiple) {
			return false;
		}
		if (element instanceof TypeElement) {
			ELEMENT_TYPES elementType = ((TypeElement) element).getElementType();
			if (fTypes != null && !CommonIndexUtils.containsElementType(elementType, fTypes)) {
				if (CommonIndexUtils.containsElementType(ELEMENT_TYPES.SIMPLE_EVENT, fTypes) && elementType == ELEMENT_TYPES.TIME_EVENT) {
					if (fName != null) {
						if (!(fName.equals(((TypeElement) element).getName()))) {
							return true;
						}
					}
					fFoundElements.add((TypeElement) element);
					fFoundMatch = true;
				}
				return true;
			}
			if (fName != null) {
				if (!(fName.equals(((TypeElement) element).getName()))) {
					return true;
				}
			}
			fFoundElements.add((TypeElement) element);
			fFoundMatch = true;
		}
		return true;
	}

	public List<DesignerElement> getFoundElements() {
		return fFoundElements;
	}
	
	public DesignerElement getFirstFoundElement() {
		if (fFoundElements.size() > 0) {
			return fFoundElements.get(0);
		}
		return null;
	}
}
