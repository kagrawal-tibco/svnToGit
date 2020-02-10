package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.domain.DomainInstance;

public class DomainInstanceNode {
	 protected DomainInstance dmInstance;
		
		public DomainInstanceNode() {
		}

		public DomainInstanceNode(DomainInstance dmInstance) {
			this.dmInstance = dmInstance;
		}
		
		public DomainInstance getInstance() {
			return dmInstance;
		}

		public String getPath() {
			return dmInstance.getResourcePath();
		}

	}