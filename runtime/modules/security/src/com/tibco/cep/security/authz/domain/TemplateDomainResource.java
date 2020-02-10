/**
 * 
 */
package com.tibco.cep.security.authz.domain;

import com.tibco.cep.security.authz.utils.ResourceType;

/**
 * @author aathalye
 * 
 */
public class TemplateDomainResource extends DomainResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5401691778530270118L;

	public TemplateDomainResource(final String id,
			                      final ResourceType resourceType) {
		super(null, id, resourceType);
	}

	public TemplateDomainResource(final ResourceType resourceType) {
		super(null, resourceType);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TemplateDomainResource)) {
			return false;
		}
		if (this == o) {
			return true;
		}
		IDomainResource other = (IDomainResource)o;
		if (!(resourceType.equals(other.getType()))) {
			return false;
		}
		return true;
	}

	@Override
	public boolean implies(IDomainResource target) {
		if (target.getType().equals(resourceType)) {
			return true;
		}
		return false;
	}
}
