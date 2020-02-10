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
	<productDef name="TIBCO BusinessEvents @BE_VERSION@" version="@BE_VERSION@" id="businessevents-express" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@EXPRESS_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@"></productDef>
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
	
    <featureconfigincludes>
      	<!-- BE Includes -->
      	<featureconfiginclude featureconfigname="contrib_be-studio_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x"/>
        <featureconfiginclude featureconfigname="contrib_be-monitoringandmanagement_@BE_VERSION@_FeatureConfig.xml"/>
        <featureconfiginclude featureconfigname="contrib_be-eclipse_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x"/>
        <featureconfiginclude featureconfigname="contrib_be-server_@BE_VERSION@_FeatureConfig.xml"/>
        
       	<!-- JRE Includes -->
  		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-hpux_1.8.0.102_FeatureConfig.xml" plat="hpux_ia64"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-ibm_1.8.0.101_FeatureConfig.xml" plat="aix_power_64,linux_s390x"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava-oracle_1.8.0.102_FeatureConfig.xml" plat="sol_sparc,sol_x86"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava-oracle_1.8.0.112_FeatureConfig.xml" plat="win_x86,linux_x86"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-oracle_1.8.0.112_FeatureConfig.xml" plat="win_x86_64,linux_x86_64,sol_sparc_64,sol_x86_64,macos_x86_64"/>

		<!-- Wrapper Includes -->
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_wrapper_2.4.3_FeatureConfig.xml" plat="win_x86,linux_x86,sol_sparc,sol_x86,hpux_hppa,aix_power,win_x86_64,linux_x86_64,hpux_ia64,sol_sparc_64,sol_x86_64,hpux_hppa_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_gwrapper_2.4.3_FeatureConfig.xml" plat="macos_x86_64"/>
    </featureconfigincludes>

	<installerFeature name="TIBCO BusinessEvents Studio" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-studio_be-studio" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Server_businessevents-express" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
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
	
	<installerFeature name="Monitoring and Management" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-monitoringandmanagement_be-monitoringandmanagement" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Monitoring and Management" />
		<dependency type="feature" uid="Server_businessevents-express" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
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
	</installerFeature>

    <installerFeature name="Eclipse Platform" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-eclipse_be-eclipse" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Eclipse Platform" />
	</installerFeature>

	<installerFeature name="Examples" installLocation="$L{be.product.home}" visible="true" version="@BE_VERSION@">
		<assemblyList>
			<assembly uid="product_tibco_be_express_examples" version="@BE_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<dependency type="feature" uid="Server_businessevents-express" parentID="businessevents-express" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />		
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-express_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Express Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-express_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Express Documentation"/>		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Documentation" />
			<property shortcutTarget="$L{be.product.home}/doc/standard/html/index.htm" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/doc/standard/html" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}/Documentation" />
			<property shortcutName="Documentation" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Readme" />
			<property shortcutTarget="${tibco.home}/release_notes/TIB_businessevents-standard_@BE_VERSION@_readme.txt" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Readme" />
		</wizardAction>
	</installerFeature>

	<installerFeature name="Server" visible="false" version="@BE_VERSION@">
		<dependency type="feature" uid="be-server_be-server" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>

	<customSettings promptForEclipse="true"></customSettings>
</TIBCOInstallerFeatures>
