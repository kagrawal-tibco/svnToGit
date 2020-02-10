<?xml version="1.0" encoding="UTF-8" standalone="yes"?>

<!-- If you can't use the graphical topology editor in TIBCO BusinessEvents Studio,
or need to make a change at deploytime, you can edit the topology file directly
in the .st file. This template shows an example of each element and attribute used
to define a deployment topology, with comments. 
For detailed documentation, refer to the TIBCO BusinessEvents Administration guide,
especially the chapter on configuring the Monitoring and Management component. -->

<!-- In this release the site level is not configurable and only one cluster is supported. -->

<site xmlns="http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd" name="Site">
    <description>Description goes here.</description>
    <clusters>


        <!-- In the cluster element project-cdd attribute, enter the absolute path to the location where TIBCO BusinessEvents Studio 
        topology file editor can access the CDD.
        The cluster name attribute must match the CDD cluster name. 
        Note: All directories specified in a topology file must already exist. All copies of the CDD must match exactly. -->

        <cluster project-cdd="C:/tibco/be/5.1/examples/standard/FraudDetectionCache/FraudDetectionCache/fdcache.cdd" name="fdcache">

            <!--In the master-files element, enter the absolute path to the location where MM accesses the master CDD and 
            master EAR for deployment. The master files must be located in the computer hosting the MM server.
            (When the MM server runs on the same host as BusinessEvents Studio, The project CDD and master CDD locations can be the same,
            as is often the case in test deployments.) -->

            <master-files>
                <cdd-master>C:/tibco/be/5.1/examples/standard/FraudDetectionCache/FraudDetectionCache/fdcache.cdd</cdd-master>
                <ear-master>C:/tibco/be/5.1/examples/standard/FraudDetectionCache/fdcache.ear</ear-master>
            </master-files>

            <!-- The value of the attribute  "version" in the element  "be-runtime" must match one of the values specified in the "version" 
            attribute found under one of the host-resource elements here: /site/host-resources/host-resource/software/be@version. -->

            <run-version>
                <be-runtime version="5.1.0"/>
            </run-version>

            <!-- Add a deployment-unit element for each  deployment unit (DU) you define. -->

            <deployment-units>
                <deployment-unit id="DU_1" name="DU_WindowsOS">

                    <!-- In the deployed-files element, enter the absolute path to the location where MM deploys the cluster's CDD and EAR 
                    files on the target host. -->

                    <deployed-files>
                        <cdd-deployed>C:\acme\fdcache.cdd</cdd-deployed>
                        <ear-deployed>C:\acme\fdcache.ear</ear-deployed>
                    </deployed-files>

                    <!-- Add a processing-unit-config element for each processing unit configuration (PUC) in this DU.
                    JMX port must be unique on a host. 
                    Use PU name (from the CDD) as puid. One PU can be used in multiple PUCs
                    Set the id of the processing unit configuration (PUC) as desired.
                    The id attribute can be used as the engine name. -->

                    <processing-units-config>
                        <processing-unit-config jmxport="5500" puid="default" use-as-engine-name="true" id="InferenceAgent"/>
                        <processing-unit-config jmxport="5501" puid="cache" use-as-engine-name="true" id="CacheServer"/>
                    </processing-units-config>
                </deployment-unit>
            </deployment-units>


            <!--In the deployment-mappings element, map each deployment unit to the host where it will be deployed, using their
            respective IDs. You are allowed to deploy multiple DU's to the same host, or the same DU to multiple hosts-->

            <deployment-mappings>
                <deployment-mapping host-ref="HR_0" deployment-unit-ref="DU_1"/>
            </deployment-mappings>
        </cluster>
    </clusters>




    <!--In the host-resources element, add a host-resource element for each target host. -->

    <host-resources>

    <!--In the host-resource element id attribute, specify a unique value for the host.
    In the hostname element, enter the exact, unique hostname for each host, including
    the domain name extension. Do not use "localhost".
    Note: Errors in the host name result in the host appearing in the MM Console UI as
	an unpredefined machine. In the ip element specify the IP address of the host.
	This is particularly important if the host has multiple network cards (NIC's).
    The sample value is the localhost loopback IP address, useful for test deployments
	on a single host.
    In the user-credentials element, enter credentials used to log onto the host machine.
	You can also leave the attributes empty. 
    At runtime a pop-up dialog prompts the user for the credentials. The credentials
	used are those configured for the remote connection utility used on the host
	(for example, pstools or copSSH). For security, you can paste an encrypted password here. 
    To create the encrypted password you must use TIBCO BusinessEvents Studio.
	When you configure the topology file in TIBCO BusinessEvents Studio, 
    the password is automatically encrypted. One way to do this is to create a dummy
	site topology file in TIBCO BusinessEvents Studio, enter the password, and save
	the file. Then open the file and copy the password to the site topology file you
	are configuring manually.
    In the os-type element, specify the operating system (OS) on the host.
    Allowed values are: Windows, Linux, Solaris, AIX, HP -->


        <host-resource id="HR_0">
            <hostname>acme-windows1</hostname>
            <ip>127.0.0.1</ip>
            <user-credentials password="" username=""/>
            <os-type>Windows</os-type>

			<!-- In the software element, specify the version of TIBCO BusinessEvents
			running on this host, the location of the be-engine.tra file to be loaded
			when starting an agent on this host, and the installation location of TIBCO
			BusinessEvents. -->

            <software>
                <be version="5.1.0">
                    <tra>C:/tibco/be/5.1/bin/be-engine.tra</tra>
                    <home>C:/tibco/be/5.1</home>
                </be>
            </software>
            
            <!-- In the start-pu-method element, use one nested element to indicate the
			method used to start the processing units (PUs) on this host. It is
			recommended that all hosts use the same method. 
            Specify the method by adding one of the following elements:
                <hawk/>, with no attributes. 
                <pstools/>, with no attributes. 
                <ssh port="NN"/>. The optional string attribute, "port" is not required
				if you use the default port, 22 (advised, unless the port is already in use). -->

            <start-pu-method>
                 <ssh port="22"/>
            </start-pu-method>

        </host-resource>
    </host-resources>
</site>
