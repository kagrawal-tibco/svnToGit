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
	<productDef name="TIBCO BusinessEvents Event Stream Processing @BE_VERSION@" version="@BE_VERSION@" id="businessevents-eventstreamprocessing" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@BE_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@"></productDef>

   <featureconfigincludes>
        <featureconfiginclude featureconfigname="contrib_be-query_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-pattern_@BE_VERSION@_FeatureConfig.xml"/>
   </featureconfigincludes>
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
	
    <installerFeature name="Query" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-query_be-query" version="@BE_VERSION@" description="TIBCO BusinessEvents Query" />
		<dependency type="feature" uid="Server_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
    
    <installerFeature name="Pattern Matcher Framework" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-pattern_be-pattern" version="@BE_VERSION@" description="TIBCO BusinessEvents Pattern Matcher Framework" />
		<dependency type="feature" uid="Server_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>

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

	<customSettings promptForEclipse="false"></customSettings>
</TIBCOInstallerFeatures>
