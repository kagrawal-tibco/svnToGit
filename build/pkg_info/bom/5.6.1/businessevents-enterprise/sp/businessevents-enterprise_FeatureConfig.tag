<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL$
 ! $LastChangedRevision$
 ! $LastChangedDate$
 !
 ! Copyright(c) 2012-2014 TIBCO Software Inc. All rights reserved.
 !
 ! BusinessEvents BOM File
 !
 !    AUTOMATICALLY GENERATED AT BUILD TIME !!!!
 !
 !    DO NOT EDIT !!!
 !
 ! This file "xyz.xml" is automatically generated at build time from "xyz.tag"
 !
 ! Any maintenance changes MUST be applied to "xyz.tag" and the ant
 ! task "pre-bom" triggered to propagate such changes to "xyz.xml"
 !
 !-->
<TIBCOInstallerFeatures>
	<productDef name="TIBCO BusinessEvents Enterprise @BE_VERSION@" version="@BE_VERSION@" id="businessevents-enterprise" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@BE_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@" compatDisplayName="TIBCO BusinessEvents" compatType="be" installDir="be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@" productType="servicepack"/>
   
   <!-- Alpha release doc override -->
   <!--  <documentation>
	     <readmeFile path="/potsticker/products/pubs/docs4release/businessevents-standard/@BE_VERSION@/TIB_businessevents-standard_@BE_VERSION@_ALPHA-001_readme.txt"  />
	     <relnotesFile path="/potsticker/products/pubs/docs4release/businessevents-standard/@BE_VERSION@/TIB_businessevents-standard_@BE_VERSION@_ALPHA-001_relnotes.pdf" />
	     <installguideFile path="/potsticker/products/pubs/docs4release/businessevents-standard/@BE_VERSION@/TIB_businessevents-standard_@BE_VERSION@_ALPHA-001_installation.pdf" />
    </documentation> -->
    
    
   <!-- <documentation>
		<readmeFile path="/potsticker/products/pubs/docs4release/businessevents-standard/5.1.3/TIB_businessevents_5.1.3_ENGR-001_readme.txt"  />
	</documentation> -->
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
		<variable name="be.product.standard.installed" value="true"/>
		<variable name="minimum.as.version" value="2.3.0"/>
    </customVariables>

    <!-- TODO add featureconfig includes -->
    <featureconfigincludes>
        <featureconfiginclude featureconfigname="contrib_be-studio_@BE_VERSION@_FeatureConfig.xml"/>
<!--        <featureconfiginclude featureconfigname="contrib_be-monitoringandmanagement_@BE_VERSION@_FeatureConfig.xml"/>-->
        <featureconfiginclude featureconfigname="contrib_be-rulesmanagement_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-datagrid_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-server_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-eclipse_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_ecc_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_businessevents-standard_@BE_VERSION@_FeatureConfig.xml"/>
       
		<!--<featureconfiginclude featureconfigname="contrib_activespaces-runtimedummy_@AS_VERSION@.0@AS_BUILD_VERSION@_FeatureConfig.xml" />-->
		 
		 <!-- JRE Includes -->
  		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/11.0" featureconfigname="contrib_tibcojava64-oracle_11.0.4.010_FeatureConfig.xml" plat="win_x86_64,linux_x86_64,sol_sparc_64,macos_x86_64"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-hpux_1.8.0.212_FeatureConfig.xml" plat="hpux_ia64"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-ibm_1.8.0.212_FeatureConfig.xml" plat="aix_power_64,linux_s390x"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-oracle_1.8.0.221_FeatureConfig.xml" plat="sol_x86_64"/>

		<!-- Wrapper Includes -->
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_wrapper_2.4.3_FeatureConfig.xml" plat="win_x86_64,linux_x86_64,macos_x86_64,sol_sparc_64,sol_x86_64,hpux_ia64,aix_power_64,linux_s390x,"/>
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_gwrapper_2.4.3_FeatureConfig.xml" plat="macos_x86_64"/>
        
        <!-- Datamodeling Includes -->
        <featureconfiginclude featureconfigname="contrib_be-dbconcepts_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-statemodeler_@BE_VERSION@_FeatureConfig.xml"/>
        
        <!-- Decisionmanager Includes -->
        <featureconfiginclude featureconfigname="contrib_be-decisionmanager_@BE_VERSION@_FeatureConfig.xml"/>
        
        <!-- Eventstreaming Includes -->
        <featureconfiginclude featureconfigname="contrib_be-query_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-pattern_@BE_VERSION@_FeatureConfig.xml"/>
        
        <!-- Standard Includes -->
        <featureconfiginclude featureconfigname="contrib_be-teagent_@BE_VERSION@_FeatureConfig.xml" plat="win_x86_64,linux_x86_64,macos_x86_64,sol_sparc_64,sol_x86_64,hpux_ia64,aix_power_64,linux_s390x"/>
        
        <!--  LDM includes -->
        <featureconfiginclude featureconfigname="contrib_be-liveview_@BE_VERSION@_FeatureConfig.xml"/>
        
    </featureconfigincludes>

	
	<!-- Standard Installer Features -->
	<installerFeature name="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-studio_be-studio" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Studio" />
			<property shortcutTarget="$L{be.product.home}/studio/eclipse/Studio.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/studio/eclipse" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Studio" />
		</wizardAction>
	</installerFeature>

	<installerFeature name="TIBCO BusinessEvents DataGrid SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="win_x86_64,linux_x86_64,macos_x86_64,sol_sparc_64,sol_x86_64,hpux_ia64,aix_power_64,linux_s390x">
		<dependency type="feature" uid="be-datagrid_be-datagrid"  mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents DataGrid" />
<!-- 		<dependency type="feature" uid="main_activespaces-admin" version="@AS_VERSION@" description="Tibco ActiveSpaces Admin" /> -->
        <dependency type="feature" uid="main_activespaces-common" version="${minimum.as.version}" description="TIBCO ActiveSpaces Common" />
        <dependency type="feature" uid="main_activespaces-runtime" version="${minimum.as.version}" description="TIBCO ActiveSpaces Runtime" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise"  mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
<!-- 		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCheckForInstallLoc" sequence="post-selection"> -->
<!-- 			<property productAcronym="as"/> -->
<!-- 			<property minimumVersion="${minimum.as.version}"/> -->
<!-- 			<property vpdUID="as"/> -->
<!-- 			<property productAssemblyUID="product_tibco_activespaces_admin"/> -->
<!-- 			<property productAssemblyMinVersion="${minimum.as.version}"/> -->
<!-- 		</wizardAction> -->
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCheckForInstallLoc" sequence="install">
			<property productAcronym="as"/>
			<property minimumVersion="${minimum.as.version}"/>
			<property vpdUID="as"/>
			<property productAssemblyUID="product_tibco_activespaces_common"/>
			<property productAssemblyMinVersion="${minimum.as.version}"/>
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCheckForInstallLoc" sequence="install">
			<property productAcronym="as"/>
			<property minimumVersion="${minimum.as.version}"/>
			<property vpdUID="as"/>
			<property productAssemblyUID="product_tibco_activespaces_agent"/>
			<property productAssemblyMinVersion="${minimum.as.version}"/>
		</wizardAction>
		
	</installerFeature>
    
	<!--<installerFeature name="Monitoring and Management SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-monitoringandmanagement_be-monitoringandmanagement" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Monitoring and Management" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Monitoring and Management Server" />
			<property shortcutTarget="$L{be.product.home}/mm/bin/be-mm.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/mm/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Monitoring and Management Server" />
		</wizardAction>
	</installerFeature>-->
	
	<installerFeature name="Enterprise Administrator Agent SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-teagent_be-teagent" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO Enterprise Administrator Agent" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Enterprise Administrator Agent" />
			<property shortcutTarget="$L{be.product.home}/teagent/bin/be-teagent.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/teagent/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Enterprise Administrator Agent" />
		</wizardAction>
	</installerFeature>
    
    <installerFeature name="Eclipse Platform SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-eclipse_be-eclipse" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Eclipse Platform" />
	</installerFeature>
    
	<installerFeature name="Examples SP @BE_VERSION_UPDATE@" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_standard_examples" version="@BE_VERSION@.@BE_BUILD@"/>
			<assembly uid="product_tibco_be_datamodeling_examples" version="@BE_VERSION@.@BE_BUILD@"/>
			<assembly uid="product_tibco_be_decisionmanager_examples" version="@BE_VERSION@.@BE_BUILD@"/>
			<assembly uid="product_tibco_be_eventstreamprocessing_examples" version="@BE_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise"  mustBeInstalled="false" version="5.0.0" description="TIBCO BusinessEvents Server" />				
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-standard_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Standard Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-standard_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Standard Documentation"/>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-datamodeling_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Data Modeling Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-datamodeling_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Data Modeling Documentation"/>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-eventstreamprocessing_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Event Stream Processing Documentation"/>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-decisionmanager_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Decision Manager Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-decisionmanager_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Decision Manager Documentation"/>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Documentation" />
			<property shortcutTarget="${tibco.home}/release_notes/TIB_businessevents-standard_@BE_VERSION@_docinfo.html" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="${tibco.home}/release_notes" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Documentation" />
		</wizardAction>
	</installerFeature>

    <installerFeature name="Rules Management Server SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-rulesmanagement_be-rulesmanagement" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Rules Management Server" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Rules Management Server" />
			<property shortcutTarget="$L{be.product.home}/rms/bin/be-rms.exe" />
			<property shortcutArgs="-c RMS.cdd -u default RMS.ear" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/rms/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Rules Management Server" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="WebStudio" />
			<property shortcutTarget="http://localhost:8090/WebStudio" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/rms/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="WebStudio" />
		</wizardAction>		
	</installerFeature>

	<installerFeature name="Server SP @BE_VERSION_UPDATE@" visible="false" version="@BE_VERSION@">
		<dependency type="feature" uid="be-server_be-server" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		
		<dependency type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="TIBCO BusinessEvents DataGrid SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents DataGrid" />
		<!--<dependency type="feature" uid="Monitoring and Management SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="Monitoring and Management" />-->
		<dependency type="feature" uid="Enterprise Administrator Agent SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="Enterprise Administrator Agent" />
		<dependency type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Examples SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="Examples" />
		<dependency type="feature" uid="Rules Management Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="Rules Management Server" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		
				
		
        <deploymentInfo>
            <component
                    name="serverfiles"
                    componentName="be-engine"
                    componentDisplayName="BE Engine"
                    isDeployable="true"
                    isJava="true"
                    processName="be-engine"
                    paletteResource="tibco.be.archive"
                    relativeProcessLocation="bin"
                    pluginfilename="be-admin.jar"
                    deployPlugInClassName="com.tibco.be.deployment.tsm.TSMBEEngineDeploy"
                    deployActionsPlugInClassName="com.tibco.be.deployment.tsm.TSMBEEngineDeployActions"
                    unDeployPlugInClassName="com.tibco.tra.tsm.plugin.TSMComponentUndeploy"
                    unDeployActionsPlugInClassName="com.tibco.tra.tsm.plugin.TSMComponentUndeployActions"
                    />
        </deploymentInfo>
	</installerFeature>
	
	<!-- Datamodeling Installer Features -->
	<installerFeature name="Database Concepts SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-dbconcepts_be-dbconcepts" version="@BE_VERSION@" description="TIBCO BusinessEvents Database Concepts" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
        
	<installerFeature name="State Modeler SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-statemodeler_be-statemodeler" version="@BE_VERSION@" description="TIBCO BusinessEvents Database Concepts" />
		<dependency type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>

<!-- <installerFeature name="Data Modeling Examples" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_datamodeling_examples" version="@BE_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-datamodeling_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Data Modeling Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-datamodeling_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Data Modeling Documentation"/>
		<wizardAction
			class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Data Modeling Documentation" />
			<property shortcutTarget="${tibco.home}/release_notes/TIB_businessevents-datamodeling_@BE_VERSION@_docinfo.html" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="${tibco.home}/release_notes" />
		</wizardAction>
		<wizardAction
			class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction"
			sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Data Modeling Documentation" />
		</wizardAction>
	</installerFeature>	
-->

	<!-- Decisionmanager Installer Features -->
	<installerFeature name="Decision Manager SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-decisionmanager_be-decisionmanager" version="@BE_VERSION@" description="TIBCO BusinessEvents Decision Manager" />
		<dependency type="feature" uid="Studio SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Decision Manager" />
			<property shortcutTarget="$L{be.product.home}/decisionmanager/eclipse/decisionmanager.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/decisionmanager/eclipse" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Decision Manager" />
		</wizardAction>
	</installerFeature>
 
<!--     
	<installerFeature name="Decision Manager Examples" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_decisionmanager_examples" version="@BE_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-decisionmanager_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Decision Manager Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-decisionmanager_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Decision Manager Documentation"/>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Decision Manager Documentation" />
			<property shortcutTarget="${tibco.home}/release_notes/TIB_businessevents-decisionmanager_@BE_VERSION@_docinfo.html" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="${tibco.home}/release_notes" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Decision Manager Documentation" />
		</wizardAction>
	</installerFeature>

	<installerFeature name="Server" version="@BE_VERSION@" visible="false" dependencyOperator="OR">
		<dependency description="TIBCO BusinessEvents Server" type="feature" uid="Server_businessevents-enterprise" version="@BE_VERSION@"/>
		<dependency description="TIBCO BusinessEvents Server" type="feature" uid="Server_businessevents-express" version="@BE_VERSION@"/>
	</installerFeature>
	 -->
	 
	 	<!-- LDM Installer Features -->
	<installerFeature name="Live DataMart Plugin SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-liveview_be-liveview" version="@BE_VERSION@" description="TIBCO BusinessEvents Live DataMart Plugin" />
		<dependency type="feature" uid="Studio SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		
	<!--  	<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Decision Manager" />
			<property shortcutTarget="$L{be.product.home}/decisionmanager/eclipse/decisionmanager.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/decisionmanager/eclipse" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Decision Manager" />
		</wizardAction> -->
	</installerFeature>
	 
	
	<installerFeature name="Studio SP @BE_VERSION_UPDATE@" version="@BE_VERSION@" visible="false" dependencyOperator="OR">
		<dependency description="TIBCO BusinessEvents Studio" type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@"/>
		<dependency description="TIBCO BusinessEvents Studio" type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-express" version="@BE_VERSION@"/>
	</installerFeature>
	
	<installerFeature name="Eclipse SP @BE_VERSION_UPDATE@" version="@BE_VERSION@" visible="false" dependencyOperator="OR">
		<dependency description="Eclipse Platform" type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@"/>
		<dependency description="Eclipse Platform" type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-express" version="@BE_VERSION@"/>
	</installerFeature>
    
    <!-- Evenstreaming Installer features -->
	    <installerFeature name="Query SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-query_be-query" version="@BE_VERSION@" description="TIBCO BusinessEvents Query" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
    
    <installerFeature name="Pattern Matcher Framework SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-pattern_be-pattern" version="@BE_VERSION@" description="TIBCO BusinessEvents Pattern Matcher Framework" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-enterprise" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
<!--
	<installerFeature name="Examples" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_eventstreamprocessing_examples" version="@BE_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-eventstreamprocessing_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Event Stream Processing Documentation"/>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Event Stream Processing Documentation" />
			<property shortcutTarget="${tibco.home}/release_notes/TIB_businessevents-eventstreamprocessing_@BE_VERSION@_docinfo.html" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="${tibco.home}/release_notes" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Event Stream Processing Documentation" />
		</wizardAction>
	</installerFeature>
-->
	<installerFeature name="Oracle Elliptic Curve Cryptography Library SP @BE_VERSION_UPDATE@" version="@BE_VERSION@" visible="true" plat="@SUNEC_PLATFORMS@">
		<dependency description="Oracle Elliptic Curve Cryptography Library" mustBeInstalled="false" type="feature" uid="ecc_ecc" version="@BE_VERSION@"/>
	</installerFeature>
	
	
	<customSettings promptForEclipse="true"></customSettings>
	
	
</TIBCOInstallerFeatures>



