package com.tibco.cep.bemm.persistence.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.cep.bemm.management.exception.BEApplicationSaveException;
import com.tibco.cep.bemm.management.export.model.Application;
import com.tibco.cep.bemm.management.export.model.ObjectFactory;
import com.tibco.cep.bemm.model.impl.ApplicationBuilderImpl;
import com.tibco.cep.bemm.model.impl.DeploymentVariableType;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.persistence.service.BEApplicationExportImportSerializer;
import com.tibco.cep.bemm.persistence.service.BEApplicationsManagementDataStoreService;
import com.tibco.cep.bemm.persistence.topology.model.Site;

public class BEApplicationExportImportSerializerImpl implements BEApplicationExportImportSerializer {

	private static final String CLUSTER_TOPOLOGY_JAXB_PACKAGE = "com.tibco.cep.bemm.management.export.model";


	@Override
	public String getContentType() {
		return ".xml";
	}

	@Override
	public Application deserialize(byte[] siteTopologyContents,
			BEApplicationsManagementDataStoreService<?> dataStoreService) throws Exception {

		ByteArrayInputStream bais = new ByteArrayInputStream(siteTopologyContents);
		JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE,
				this.getClass().getClassLoader());
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Application application = (Application) unmarshaller.unmarshal(bais);

		return application;
	}

	@Override
	public byte[] serialize(Application application) throws Exception {
		ByteArrayOutputStream baos = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(CLUSTER_TOPOLOGY_JAXB_PACKAGE,
					this.getClass().getClassLoader());
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			baos = new ByteArrayOutputStream();
			marshaller.marshal(application, baos);
			baos.flush();
			return baos.toByteArray();
		} catch (PropertyException pe) {
			throw new BEApplicationSaveException(pe.getLocalizedMessage(), pe);
		} catch (JAXBException jbe) {
			throw new BEApplicationSaveException(jbe.getLocalizedMessage(), jbe);
		} catch (Exception e) {
			throw new BEApplicationSaveException(e.getLocalizedMessage(), e);
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

}
