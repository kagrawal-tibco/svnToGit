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
	<productDef name="TIBCO BusinessEvents Decision Manager @BE_VERSION@" version="@BE_VERSION@" id="businessevents-decisionmanager" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@BE_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@" productType="servicepack"></productDef>
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>


<featureconfigincludes>
    <featureconfiginclude featureconfigname="contrib_be-decisionmanager_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
</featureconfigincludes>

    
	<installerFeature name="Decision Manager SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-decisionmanager_be-decisionmanager" version="@BE_VERSION@" description="TIBCO BusinessEvents Decision Manager" />
		<dependency type="feature" uid="Studio SP @BE_VERSION_UPDATE@_businessevents-decisionmanager" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse SP @BE_VERSION_UPDATE@_businessevents-decisionmanager" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-decisionmanager" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
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
    
	<installerFeature name="Examples SP @BE_VERSION_UPDATE@" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
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
			<condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/studio/eclipse/studio.exe"/>
			</condition>
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Decision Manager Documentation" />
		</wizardAction>
	</installerFeature>
	
	<installerFeature name="Server SP @BE_VERSION_UPDATE@" version="@BE_VERSION@" visible="false" dependencyOperator="OR">
		<dependency description="TIBCO BusinessEvents Server" type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@"/>
		<dependency description="TIBCO BusinessEvents Server" type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-express" version="@BE_VERSION@"/>
	</installerFeature>
	
	<installerFeature name="Studio SP @BE_VERSION_UPDATE@" version="@BE_VERSION@" visible="false" dependencyOperator="OR">
		<dependency description="TIBCO BusinessEvents Studio" type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@"/>
		<dependency description="TIBCO BusinessEvents Studio" type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-express" version="@BE_VERSION@"/>
	</installerFeature>
	
	<installerFeature name="Eclipse SP @BE_VERSION_UPDATE@" version="@BE_VERSION@" visible="false" dependencyOperator="OR">
		<dependency description="Eclipse Platform" type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@"/>
		<dependency description="Eclipse Platform" type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-express" version="@BE_VERSION@"/>
	</installerFeature>

	<customSettings promptForEclipse="false"></customSettings>
</TIBCOInstallerFeatures>
