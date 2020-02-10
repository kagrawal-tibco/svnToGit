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
	<productDef name="TIBCO BusinessEvents Data Modeling @BE_VERSION@" version="@BE_VERSION@" id="businessevents-datamodeling" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@BE_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@" productType="servicepack"></productDef>
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
    
   <featureconfigincludes>
        <featureconfiginclude featureconfigname="contrib_be-dbconcepts_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-statemodeler_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
   </featureconfigincludes>
    
    <installerFeature name="Database Concepts SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-dbconcepts_be-dbconcepts" version="@BE_VERSION@" description="TIBCO BusinessEvents Database Concepts" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
        
	<installerFeature name="State Modeler SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-statemodeler_be-statemodeler" version="@BE_VERSION@" description="TIBCO BusinessEvents Database Concepts" />
		<dependency type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>

	<installerFeature name="Examples SP @BE_VERSION_UPDATE@" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
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
			<condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/studio/eclipse/studio.exe"/>
			</condition>
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Data Modeling Documentation" />
		</wizardAction>
	</installerFeature>

	<customSettings promptForEclipse="false"></customSettings>
</TIBCOInstallerFeatures>



