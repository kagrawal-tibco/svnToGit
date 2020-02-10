package com.tibco.cep.bemm.management.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.impl.BEImpl;
import com.tibco.cep.bemm.persistence.topology.model.Be;
import com.tibco.tea.agent.be.version.be_teagentVersion;

/**
 * Convert Master host details from UI model to Service Model
 * 
 * @author dijadhav
 *
 */
public class MasterHostConvertor {
	/**
	 * Convert ui be details to service ui details
	 * 
	 * @param beUIModels
	 *            - UI BE models
	 * @return Service BE Models
	 */
	public static List<BE> convertBEUIModelToBEServiceModel(List<com.tibco.tea.agent.be.ui.model.BE> beUIModels) {
		List<BE> bes = new ArrayList<BE>();
		if (null != beUIModels && !beUIModels.isEmpty()) {

			for (com.tibco.tea.agent.be.ui.model.BE be : beUIModels) {
				if (null != be) {
					BE beServiceModel = new BEImpl();
					if (null != be.getId() && !be.getId().trim().isEmpty())
						beServiceModel.setId(be.getId());
					else
						beServiceModel.setId(getNextId(bes, beUIModels));
					if (null == be.getVersion() || be.getVersion().isEmpty()) {
						String version = (be_teagentVersion.getVersion()).split(",")[0];
						be.setVersion(version);
					}
					if (null != be.getVersion() && !be.getVersion().trim().isEmpty())
						beServiceModel.setVersion(be.getVersion().trim());
					if (null != be.getBeHome() && !be.getBeHome().trim().isEmpty())
						beServiceModel.setBeHome(be.getBeHome().trim());
					if (null != be.getBeTra() && !be.getBeTra().trim().isEmpty())
						beServiceModel.setBeTra(be.getBeTra().trim());
					if (null != be.getBeHome() && !be.getBeHome().trim().isEmpty() && null != be.getBeTra()
							&& !be.getBeTra().trim().isEmpty())
						bes.add(beServiceModel);
				}
			}

		}
		return bes;
	}

	private static String getNextId(List<BE> bes, List<com.tibco.tea.agent.be.ui.model.BE> beUIModels) {

		long nextId = Long.parseLong(RandomStringUtils.randomNumeric(10));
		List<Long> ids = new ArrayList<>();
		if (null != beUIModels && !beUIModels.isEmpty()) {
			for (com.tibco.tea.agent.be.ui.model.BE be : beUIModels) {
				if (null != be) {
					String id = be.getId();
					if (null != id && !id.trim().isEmpty()) {
						ids.add(Long.valueOf(id));
					}
				}
			}
		}
		if (ids.contains(nextId)) {
			Long max = Collections.max(ids);
			nextId = max + 1;
		}
		if (null != bes && !bes.isEmpty()) {
			List<Long> beids = new ArrayList<>();
			for (BE be : bes) {
				if (null != be) {
					if (null != be.getId() && !be.getId().trim().isEmpty()
							&& be.getId().trim().equals(String.valueOf(nextId))) {
						beids.add(Long.valueOf(be.getId()));
					}
				}
			}
			if (beids.contains(nextId)) {
				Long max = Collections.max(beids);
				nextId = max + 1;
			}
		}

		
		return String.valueOf(nextId);
	}

	public static List<BE> convertBEPeristentModelToBEServiceModel(List<Be> beDAOs) {

		List<BE> bes = new ArrayList<BE>();
		if (null != beDAOs && !beDAOs.isEmpty()) {

			for (Be be : beDAOs) {
				if (null != be) {
					BE beServiceModel = new BEImpl();
					if (null != be.getId() && !be.getId().trim().isEmpty())
						beServiceModel.setId(be.getId());
					else
						beServiceModel.setId(getId(beDAOs));
					if (null == be.getVersion() || be.getVersion().isEmpty())
						be.setVersion(be_teagentVersion.getVersion());

					beServiceModel.setVersion(be.getVersion().trim());
					beServiceModel.setBeHome(be.getHome().trim());
					beServiceModel.setBeTra(be.getTra().trim());
					bes.add(beServiceModel);
				}
			}
		}
		return bes;
	}

	private static String getId(List<Be> beDAOs) {
		long nextId = Long.parseLong(RandomStringUtils.randomNumeric(10));
		if (null != beDAOs && !beDAOs.isEmpty()) {
			for (Be be : beDAOs) {
				if (null != be) {
					String id = be.getId();
					if (null != id && !id.trim().isEmpty()) {
						if (Long.parseLong(id) > nextId) {
							nextId = Long.parseLong(id);
						}
					}
				}
			}
		}
		return String.valueOf(nextId + 1);
	}

	public static List<BE> convertBEExportIportModelToBEServiceModel(
			List<com.tibco.cep.bemm.management.export.model.Be> importedBE) {

		List<BE> bes = new ArrayList<BE>();
		if (null != importedBE && !importedBE.isEmpty()) {

			for (com.tibco.cep.bemm.management.export.model.Be be : importedBE) {
				if (null != be) {
					BE beServiceModel = new BEImpl();
					if (null != be.getId() && !be.getId().trim().isEmpty())
						beServiceModel.setId(be.getId());
					else
						beServiceModel.setId(getImportedId(importedBE));
					if (null == be.getVersion() || be.getVersion().isEmpty())
						be.setVersion(be_teagentVersion.getVersion());
					beServiceModel.setVersion(be.getVersion().trim());
					beServiceModel.setBeHome(be.getBehome().trim());
					beServiceModel.setBeTra(be.getBetrapath().trim());
					bes.add(beServiceModel);
				}
			}
		}
		return bes;
	}

	private static String getImportedId(List<com.tibco.cep.bemm.management.export.model.Be> importedBE) {
		long nextId = Long.parseLong(RandomStringUtils.randomNumeric(10));
		if (null != importedBE && !importedBE.isEmpty()) {
			for (com.tibco.cep.bemm.management.export.model.Be be : importedBE) {
				if (null != be) {
					String id = be.getId();
					if (null != id && !id.trim().isEmpty()) {
						if (Long.parseLong(id) > nextId) {
							nextId = Long.parseLong(id);
						}
					}
				}
			}
		}
		return String.valueOf(nextId + 1);
	}

	public static Collection<? extends com.tibco.cep.bemm.management.export.model.Be> convertBEServiceModelExportImportModel(
			List<BE> beDetails) {
		List<com.tibco.cep.bemm.management.export.model.Be> bes = new ArrayList<com.tibco.cep.bemm.management.export.model.Be>();
		if (null != beDetails && !beDetails.isEmpty()) {

			for (BE be : beDetails) {
				if (null != be) {
					com.tibco.cep.bemm.management.export.model.Be beExporteModel = new com.tibco.cep.bemm.management.export.model.Be();
					beExporteModel.setBehome(be.getBeHome().trim());
					beExporteModel.setBetrapath(be.getBeTra().trim());
					beExporteModel.setId(be.getId());
					if (null == be.getVersion() || be.getVersion().isEmpty())
						be.setVersion(be_teagentVersion.getVersion());
					beExporteModel.setVersion(be.getVersion().trim());
					bes.add(beExporteModel);
				}
			}
		}
		return bes;
	}

	public static List<com.tibco.tea.agent.be.ui.model.BE> convertBEServiceModelUIModel(List<BE> beHomes) {

		List<com.tibco.tea.agent.be.ui.model.BE> beUIModels = new ArrayList<com.tibco.tea.agent.be.ui.model.BE>();
		if (null != beHomes && !beHomes.isEmpty()) {

			for (BE be : beHomes) {
				if (null != be) {
					com.tibco.tea.agent.be.ui.model.BE beExporteModel = new com.tibco.tea.agent.be.ui.model.impl.BEImpl();
					beExporteModel.setBeHome(be.getBeHome().trim());
					beExporteModel.setBeTra(be.getBeTra().trim());
					beExporteModel.setId(be.getId());
					if (null == be.getVersion() || be.getVersion().isEmpty())
						be.setVersion(be_teagentVersion.getVersion());
					beExporteModel.setVersion(be.getVersion().trim());
					beUIModels.add(beExporteModel);
				}
			}
		}
		return beUIModels;

	}

}
