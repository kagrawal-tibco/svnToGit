package com.tibco.cep.bemm.persistence.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.RandomStringUtils;

import com.tibco.cep.bemm.management.exception.BEMasterHostSaveException;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.impl.BEImpl;
import com.tibco.cep.bemm.model.impl.MasterHostImpl;
import com.tibco.cep.bemm.persistence.masterhost.model.Be;
import com.tibco.cep.bemm.persistence.masterhost.model.MasterHosts;
import com.tibco.cep.bemm.persistence.masterhost.model.ObjectFactory;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.service.BEMasterHostSerializer;

public class BEMasterHostSerializerImpl implements BEMasterHostSerializer {

	private static final String MASTER_HOSTS__JAXB_PACKAGE = "com.tibco.cep.bemm.persistence.masterhost.model";

	private String deployTopologiesRepoDirLocation = null;
	private ObjectFactory masterHostObjectFactory = null;

	public BEMasterHostSerializerImpl(String deployTopologiesRepoDirLocation) {
		this.masterHostObjectFactory = new ObjectFactory();
		this.deployTopologiesRepoDirLocation = deployTopologiesRepoDirLocation;
	}

	@Override
	public String getContentType() {
		return ".xml";
	}

	@Override
	public Map<String, MasterHost> deserialize(byte[] masterHostContents,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws Exception {
		Map<String, MasterHost> masterHostMap = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(masterHostContents);
			JAXBContext jaxbContext = JAXBContext.newInstance(MASTER_HOSTS__JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			MasterHosts masterHosts = (MasterHosts) unmarshaller.unmarshal(bais);
			if (null != masterHosts) {
				masterHostMap = new HashMap<String, MasterHost>();
				List<com.tibco.cep.bemm.persistence.masterhost.model.MasterHost> hosts = masterHosts.getMasterHost();
				if (null != hosts && !hosts.isEmpty()) {
					for (com.tibco.cep.bemm.persistence.masterhost.model.MasterHost masterHost : hosts) {
						if (null != masterHost) {
							MasterHost host = new MasterHostImpl();
							List<BE> bes = new ArrayList<>();
							if (null != masterHost.getBeHome() && !masterHost.getBeHome().trim().isEmpty()
									&& null != masterHost.getBeTra() && !masterHost.getBeTra().trim().isEmpty()) {
								BE beModel = new BEImpl();
								beModel.setBeHome(masterHost.getBeHome().trim());
								beModel.setBeTra(masterHost.getBeTra().trim());
								beModel.setVersion("5.3.0");
								beModel.setId(RandomStringUtils.randomNumeric(10));
								bes.add(beModel);
							}
							masterHost.setBeHome(null);
							masterHost.setBeTra(null);
							if (null != masterHost.getBe() && !masterHost.getBe().isEmpty()) {
								for (Be be : masterHost.getBe()) {
									if (null != be) {
										BE beServiceModel = new BEImpl();
										beServiceModel.setBeHome(be.getBeHome().trim());
										beServiceModel.setBeTra(be.getBeTra().trim());
										if(be.getVersion()!=null)
											beServiceModel.setVersion(be.getVersion().trim());
										beServiceModel.setId(be.getId());
										bes.add(beServiceModel);
									}
								}
								
							}
							host.setBE(bes);
							host.setHostName(masterHost.getHostName());
							host.setHostId(masterHost.getHostId());
							host.setHostIp(masterHost.getHostIPAddress());
							host.setOs(masterHost.getOs());
							host.setMachineName(masterHost.getMachineName());
							host.setUserName(masterHost.getUserName());
							host.setSshPort(masterHost.getSshPort());
							host.setPredefined(true);
							host.setDeploymentPath(masterHost.getDeploymentPath());
							host.setPassword(masterHost.getPassword());
							masterHostMap.put(host.getHostId(), host);
						}
					}
				}
			}

		} catch (JAXBException jbex) {
			jbex.printStackTrace();
			throw new Exception(jbex);
		}
		return masterHostMap;
	}

	@Override
	public byte[] serialize(Map<String, MasterHost> masterHostMap) throws Exception {

		ByteArrayOutputStream baos = null;
		try {
			MasterHosts masterHosts = createMasterHierarchy(masterHostMap);
			JAXBContext jaxbContext = JAXBContext.newInstance(MASTER_HOSTS__JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			baos = new ByteArrayOutputStream();
			marshaller.marshal(masterHosts, baos);
			baos.flush();
			return baos.toByteArray();
		} catch (PropertyException pe) {
			throw new BEMasterHostSaveException(pe.getLocalizedMessage(), pe);
		} catch (JAXBException jbe) {
			throw new BEMasterHostSaveException(jbe.getLocalizedMessage(), jbe);
		} catch (Exception e) {
			throw new BEMasterHostSaveException(e.getLocalizedMessage(), e);
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	private MasterHosts createMasterHierarchy(Map<String, MasterHost> masterHostMap) {
		MasterHosts masterHosts = masterHostObjectFactory.createMasterHosts();
		if (null != masterHostMap) {
			for (Entry<String, MasterHost> entry : masterHostMap.entrySet()) {
				if (null != entry) {
					MasterHost masterHost = entry.getValue();
					if (null != masterHost) {
						com.tibco.cep.bemm.persistence.masterhost.model.MasterHost host = masterHostObjectFactory
								.createMasterHost();
						host.setBeHome(null);
						host.setBeTra(null);
						host.setHostName(masterHost.getHostName());
						if (null != masterHost.getBE() && !masterHost.getBE().isEmpty()) {
							for (BE be : masterHost.getBE()) {
								if (null != be) {
									Be beModel = new Be();
									beModel.setBeHome(be.getBeHome());
									beModel.setBeTra(be.getBeTra());
									beModel.setId(be.getId());
									beModel.setVersion(be.getVersion());
									host.getBe().add(beModel);
								}
							}

						}
						host.setHostId(masterHost.getHostId());
						host.setHostIPAddress(masterHost.getHostIp());
						host.setOs(masterHost.getOs());
						host.setMachineName(masterHost.getMachineName());
						host.setUserName(masterHost.getUserName());
						host.setSshPort(masterHost.getSshPort());
						host.setPassword(masterHost.getPassword());
						host.setDeploymentPath(masterHost.getDeploymentPath());
						masterHosts.getMasterHost().add(host);
					}
				}
			}
		}
		return masterHosts;
	}

}
