<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL$
 ! $LastChangedRevision$
 ! $LastChangedDate$
 !
 ! Copyright (c) 2004-2013 TIBCO Software Inc. All rights reserved.
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
	<!-- main definition for the product.  Use 4 digit version -->
	<productDef name="TIBCO BusinessEvents Process Orchestration @BE_BPMN_VERSION@" version="@BE_BPMN_VERSION@" id="businessevents-process" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@PROCESS_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@" productType="servicepack"></productDef>
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                          
    </customVariables>
    
    <featureconfigincludes>
        <featureconfiginclude featureconfigname="contrib_be-process-server_@BE_BPMN_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-process-modeler_@BE_BPMN_VERSION@_FeatureConfig.xml" plat="win_x86_64,linux_x86_64,sol_sparc_64,macos_x86_64"/>
   </featureconfigincludes>
    
	<installerFeature name="Process Orchestration Server SP @BE_VERSION_UPDATE@" visible="true" version="@BE_BPMN_VERSION@">
		<dependency type="feature" uid="be-process-server_be-process-server" version="@BE_BPMN_VERSION@" description="TIBCO BusinessEvents Process Orchestration Server" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" parentID="businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
	
	<installerFeature name="Process Modeler SP @BE_VERSION_UPDATE@" visible="true" version="@BE_BPMN_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-process-modeler_be-process-modeler" version="@BE_BPMN_VERSION@" description="TIBCO BusinessEvents Process Modeler" />
		<dependency type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-standard" parentID="businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-standard" parentID="businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" parentID="businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
	
	<installerFeature name="Examples SP @BE_VERSION_UPDATE@" installLocation="$L{be.product.home}" visible="true" version="@BE_BPMN_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_process_examples" version="@BE_BPMN_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-process_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Process Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-process_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Process Documentation"/>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Process Documentation" />
			<property shortcutTarget="$L{be.product.home}/doc/process/html/index.htm" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/doc/process/html" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Process Documentation" />
		</wizardAction>
	</installerFeature>
		
	<customSettings promptForEclipse="false"></customSettings>
</TIBCOInstallerFeatures>
