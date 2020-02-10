package com.tibco.cep.dashboard.psvr.user;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGalleryCategory;
import com.tibco.cep.dashboard.psvr.mal.MALElementManagerFactory;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.mal.MALTransaction;
import com.tibco.cep.dashboard.psvr.mal.managers.MALComponentGalleryFolderManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALElementManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponentGalleryFolder;
import com.tibco.cep.dashboard.psvr.mal.model.MALUserPreference;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 *
 * @author anpatil
 * @deprecated decide whether we need this when we do component editing
 */
class SaveOnDemandCategory extends MALComponentGalleryCategory {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 5349232045784359613L;

	public static final String DEFAULT_CATEGORY_NAME = "My Components";

	protected String defaultCategoryName;

	private MALSession session;

	private MALUserPreference userPreference;

	SaveOnDemandCategory(Logger logger,MALSession session) {
		this(logger,session, DEFAULT_CATEGORY_NAME, true);
	}

	SaveOnDemandCategory(Logger logger,MALSession session, boolean displayAscending) {
		this(logger,session, DEFAULT_CATEGORY_NAME, displayAscending);
	}

	SaveOnDemandCategory(Logger logger,MALSession session, String defaultCategoryName, boolean displayAscending) {
		super(logger,null,true,displayAscending);
		this.editable = true;
		this.displayAscending = displayAscending;
		this.session = session;
		if (StringUtil.isEmptyOrBlank(defaultCategoryName) == true) {
			defaultCategoryName = DEFAULT_CATEGORY_NAME;
		} else {
			this.defaultCategoryName = defaultCategoryName;
		}
		this.name = this.defaultCategoryName;
	}

	@Override
	public void addComponent(MALComponent component) throws MALException {
		if (rawCategory == null) {
			createRawCategory(defaultCategoryName);
		}
		super.addComponent(component);
	}

	private void createRawCategory(String defaultCategoryName) throws MALException {
		MALElementManager manager = MALElementManagerFactory.getInstance().getManager(MALComponentGalleryFolderManager.DEFINITION_TYPE);
		//PORT not sure if we can pass parent as null here
		rawCategory = (MALComponentGalleryFolder) manager.create(null,defaultCategoryName);
		userPreference = session.getUserPreference();
		if (userPreference == null) {
			MALTransaction currentTransaction = MALTransaction.getCurrentTransaction();
			if (currentTransaction == null) {
				throw new IllegalArgumentException("cannot create user preference outside of a transaction");
			}
			userPreference = currentTransaction.createUserPreference();
		}
		userPreference.setGallery(rawCategory);
	}

	@Override
	public void removeComponent(MALComponent component) {
		if (rawCategory == null) {
			throw new IllegalStateException("cannot remove " + component + " from non-persisted category");
		}
		super.removeComponent(component);
	}

	@Override
	public void replace(MALComponent component, MALComponent replacement) {
		if (rawCategory == null) {
			throw new IllegalStateException("cannot replace " + component + " with " + replacement + " in non-persisted category");
		}
		super.replace(component, replacement);
	}

	@Override
	public MALComponent resetComponent(MALComponent component) {
		if (rawCategory == null) {
			throw new IllegalStateException("cannot reset " + component + " in non-persisted category");
		}
		return super.resetComponent(component);
	}

}
