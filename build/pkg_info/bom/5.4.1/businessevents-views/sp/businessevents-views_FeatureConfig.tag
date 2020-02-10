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
	<productDef name="TIBCO BusinessEvents Views @BE_VERSION@" version="@BE_VERSION@" id="businessevents-views" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@BE_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@" productType="servicepack"></productDef>
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
    
   <featureconfigincludes>
        <featureconfiginclude featureconfigname="contrib_be-views-server_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-views-modeler_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_wrapper_2.4.3_FeatureConfig.xml" plat="win_x86,linux_x86,sol_sparc,sol_x86,hpux_hppa,aix_power,win_x86_64,linux_x86_64,hpux_ia64,sol_sparc_64,sol_x86_64,hpux_hppa_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_gwrapper_2.4.3_FeatureConfig.xml" plat="macos_x86_64"/>	
   </featureconfigincludes>

    <installerFeature name="Server SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-views-server_be-views-server" version="@BE_VERSION@" description="TIBCO BusinessEvents Views Server" />
		<dependency type="feature" uid="Server SP 1_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>

    <installerFeature name="Modeler SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-views-modeler_be-views-modeler" version="@BE_VERSION@" description="TIBCO BusinessEvents Views Modeler" />
		<dependency type="feature" uid="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Eclipse Platform SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="Eclipse Platform" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
	
	<installerFeature name="Examples SP @BE_VERSION_UPDATE@" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_views_examples" version="@BE_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-views_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Views Documentation"/>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Views Documentation" />
			<property shortcutTarget="${tibco.home}/release_notes/TIB_businessevents-views_@BE_VERSION@_docinfo.html" />
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
			<property shortcutName="Views Documentation" />
		</wizardAction>
	</installerFeature>

	<customSettings promptForEclipse="false"></customSettings>
</TIBCOInstallerFeatures>
