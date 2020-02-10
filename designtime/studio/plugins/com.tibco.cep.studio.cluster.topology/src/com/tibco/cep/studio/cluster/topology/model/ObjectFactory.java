//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.04.06 at 02:03:38 AM PDT 
//


package com.tibco.cep.studio.cluster.topology.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tibco.cep.studio.cluster.topology.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Tra_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "tra");
    private final static QName _EarMaster_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "ear-master");
    private final static QName _OsType_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "os-type");
    private final static QName _Home_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "home");
    private final static QName _Ip_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "ip");
    private final static QName _EarDeployed_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "ear-deployed");
    private final static QName _Hostname_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "hostname");
    private final static QName _CddMaster_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "cdd-master");
    private final static QName _CddDeployed_QNAME = new QName("http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", "cdd-deployed");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tibco.cep.studio.cluster.topology.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Cluster }
     * 
     */
    public Cluster createCluster() {
        return new Cluster();
    }

    /**
     * Create an instance of {@link Ssh }
     * 
     */
    public Ssh createSsh() {
        return new Ssh();
    }

    /**
     * Create an instance of {@link Software }
     * 
     */
    public Software createSoftware() {
        return new Software();
    }

    /**
     * Create an instance of {@link DeploymentMappings }
     * 
     */
    public DeploymentMappings createDeploymentMappings() {
        return new DeploymentMappings();
    }

    /**
     * Create an instance of {@link DeploymentUnits }
     * 
     */
    public DeploymentUnits createDeploymentUnits() {
        return new DeploymentUnits();
    }

    /**
     * Create an instance of {@link HostResources }
     * 
     */
    public HostResources createHostResources() {
        return new HostResources();
    }

    /**
     * Create an instance of {@link BeRuntime }
     * 
     */
    public BeRuntime createBeRuntime() {
        return new BeRuntime();
    }

    /**
     * Create an instance of {@link Be }
     * 
     */
    public Be createBe() {
        return new Be();
    }

    /**
     * Create an instance of {@link Clusters }
     * 
     */
    public Clusters createClusters() {
        return new Clusters();
    }

    /**
     * Create an instance of {@link RunVersion }
     * 
     */
    public RunVersion createRunVersion() {
        return new RunVersion();
    }

    /**
     * Create an instance of {@link DeploymentMapping }
     * 
     */
    public DeploymentMapping createDeploymentMapping() {
        return new DeploymentMapping();
    }

    /**
     * Create an instance of {@link MasterFiles }
     * 
     */
    public MasterFiles createMasterFiles() {
        return new MasterFiles();
    }

    /**
     * Create an instance of {@link DeploymentUnit }
     * 
     */
    public DeploymentUnit createDeploymentUnit() {
        return new DeploymentUnit();
    }

    /**
     * Create an instance of {@link UserCredentials }
     * 
     */
    public UserCredentials createUserCredentials() {
        return new UserCredentials();
    }

    /**
     * Create an instance of {@link StartPuMethod }
     * 
     */
    public StartPuMethod createStartPuMethod() {
        return new StartPuMethod();
    }

    /**
     * Create an instance of {@link HostResource }
     * 
     */
    public HostResource createHostResource() {
        return new HostResource();
    }

    /**
     * Create an instance of {@link Hawk }
     * 
     */
    public Hawk createHawk() {
        return new Hawk();
    }

    /**
     * Create an instance of {@link ProcessingUnitConfig }
     * 
     */
    public ProcessingUnitConfig createProcessingUnitConfig() {
        return new ProcessingUnitConfig();
    }

    /**
     * Create an instance of {@link Site }
     * 
     */
    public Site createSite() {
        return new Site();
    }

    /**
     * Create an instance of {@link ProcessingUnitsConfig }
     * 
     */
    public ProcessingUnitsConfig createProcessingUnitsConfig() {
        return new ProcessingUnitsConfig();
    }

    /**
     * Create an instance of {@link Pstools }
     * 
     */
    public Pstools createPstools() {
        return new Pstools();
    }

    /**
     * Create an instance of {@link DeployedFiles }
     * 
     */
    public DeployedFiles createDeployedFiles() {
        return new DeployedFiles();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "tra")
    public JAXBElement<String> createTra(String value) {
        return new JAXBElement<String>(_Tra_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "ear-master")
    public JAXBElement<String> createEarMaster(String value) {
        return new JAXBElement<String>(_EarMaster_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "os-type")
    public JAXBElement<String> createOsType(String value) {
        return new JAXBElement<String>(_OsType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "home")
    public JAXBElement<String> createHome(String value) {
        return new JAXBElement<String>(_Home_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "ip")
    public JAXBElement<String> createIp(String value) {
        return new JAXBElement<String>(_Ip_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "ear-deployed")
    public JAXBElement<String> createEarDeployed(String value) {
        return new JAXBElement<String>(_EarDeployed_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "hostname")
    public JAXBElement<String> createHostname(String value) {
        return new JAXBElement<String>(_Hostname_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "cdd-master")
    public JAXBElement<String> createCddMaster(String value) {
        return new JAXBElement<String>(_CddMaster_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd", name = "cdd-deployed")
    public JAXBElement<String> createCddDeployed(String value) {
        return new JAXBElement<String>(_CddDeployed_QNAME, String.class, null, value);
    }

}
