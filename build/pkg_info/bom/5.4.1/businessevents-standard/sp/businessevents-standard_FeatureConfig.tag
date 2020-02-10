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
	<productDef name="TIBCO BusinessEvents @BE_VERSION@" version="@BE_VERSION@" id="businessevents-standard" releaseType="@BE_RELEASE_TYPE@" supportedPlatforms="@BE_SUPPORTED_PLATFORMS@" universalinstallerrelease="@INSTALLER_RELEASE@" universalinstallerversion="@INSTALLER_VERSION@" compatDisplayName="TIBCO BusinessEvents" compatType="be" installDir="be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@" productType="servicepack" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
		<variable name="be.product.standard.installed" value="true"/>
		<variable name="minimum.as.version" value="2.2.0"/>
	</customVariables>

    <!-- TODO add featureconfig includes -->
    <featureconfigincludes>
        <featureconfiginclude featureconfigname="contrib_be-studio_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-monitoringandmanagement_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-teagent_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-eclipse_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-rulesmanagement_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-datagrid_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude featureconfigname="contrib_be-server_@BE_VERSION@_FeatureConfig.xml" plat="win_x86,win_x86_64,linux_x86,linux_x86_64,sol_sparc,hpux_ia64,sol_sparc_64,sol_x86_64,aix_power_64,linux_s390x,macos_x86_64"/>

		 <!-- AS Includes -->
		<!--<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/activespaces/tags/as-@AS_VERSION@.009/pkg_info/bom/@AS_VERSION@/base" featureconfigname="contrib_activespaces-admin_@AS_VERSION@_FeatureConfig.xml" />
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/activespaces/tags/as-@AS_VERSION@.009/pkg_info/bom/@AS_VERSION@/base" featureconfigname="contrib_activespaces-common_@AS_VERSION@_FeatureConfig.xml"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/activespaces/tags/as-@AS_VERSION@.009/pkg_info/bom/@AS_VERSION@/base" featureconfigname="contrib_activespaces-runtime_@AS_VERSION@_FeatureConfig.xml" />
		-->
		<!--<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/activespaces/tags/as-@AS_VERSION@.0@AS_BUILD_VERSION@-GA/pkg_info/bom/@AS_VERSION@/base" featureconfigname="contrib_activespaces-admin_@AS_VERSION@_FeatureConfig.xml" />
			<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/activespaces/tags/as-@AS_VERSION@.0@AS_BUILD_VERSION@-GA/pkg_info/bom/@AS_VERSION@/base" featureconfigname="contrib_activespaces-common_@AS_VERSION@_FeatureConfig.xml"/>
			<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/activespaces/tags/as-@AS_VERSION@.0@AS_BUILD_VERSION@-GA/pkg_info/bom/@AS_VERSION@/base" featureconfigname="contrib_activespaces-runtime_@AS_VERSION@_FeatureConfig.xml" />
		
		<featureconfiginclude featureconfigname="contrib_activespaces-runtimedummy_@AS_VERSION@.0@AS_BUILD_VERSION@_FeatureConfig.xml" />
		-->		 
		 <!-- JRE Includes -->
  		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-hpux_1.8.0.121_FeatureConfig.xml" plat="hpux_ia64"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-ibm_1.8.0.121_FeatureConfig.xml" plat="aix_power_64,linux_s390x"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava-oracle_1.8.0.131_FeatureConfig.xml" plat="sol_sparc,sol_x86"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava-oracle_1.8.0.131_FeatureConfig.xml" plat="win_x86,linux_x86"/>
		<featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/java/1.8" featureconfigname="contrib_tibcojava64-oracle_1.8.0.131_FeatureConfig.xml" plat="win_x86_64,linux_x86_64,sol_sparc_64,sol_x86_64,macos_x86_64"/>

		<!-- Wrapper Includes -->
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_wrapper_2.4.3_FeatureConfig.xml" plat="win_x86,linux_x86,sol_sparc,sol_x86,hpux_hppa,aix_power,win_x86_64,linux_x86_64,hpux_ia64,sol_sparc_64,sol_x86_64,hpux_hppa_64,aix_power_64,linux_s390x,macos_x86_64"/>
        <featureconfiginclude svnrev="HEAD" svnurlloc="http://svn.tibco.com/install/trunk/contribs/wrapper/2.4" featureconfigname="contrib_gwrapper_2.4.3_FeatureConfig.xml" plat="macos_x86_64"/>	
    </featureconfigincludes>

	
	<installerFeature name="TIBCO BusinessEvents Studio SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<dependency type="feature" uid="be-studio_be-studio" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Studio" />
			<property shortcutTarget="$L{be.product.home}/studio/eclipse/Studio.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/studio/eclipse" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			<condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/studio/eclipse/studio.exe"/>
			</condition>
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Studio" />
		</wizardAction>
	</installerFeature>
	
	<installerFeature name="Studio SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="sol10_sparc,hpux112_ia64,sol10_sparc_64,sol10_x86_64,aix61_power_64,linux26gl24_s390x">
		<dependency type="feature" uid="be-studio_be-studio" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Studio" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
	</installerFeature>
	
<!--	<installerFeature name="Eclipse Platform" visible="true" version="@BE_VERSION@" plat="win_x86_64,linux26gl25_x86_64,macos_x86_64,sol10_sparc_64,sol10_x86_64,hpux112_ia64,aix61_power_64,linux26gl24_s390x">
		<dependency type="feature" uid="be-eclipse_be-eclipse" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Eclipse Platform" />
	</installerFeature> -->

	<installerFeature name="DataGrid SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@" plat="win_x86,win_x86_64,linux26gl25_x86,linux26gl25_x86_64,sol10_sparc,sol10_x86,hpux111_hppa,aix61_power,hpux112_ia64,sol10_sparc_64,sol10_x86_64,aix61_power_64,hpux111_hppa_64,linux26gl24_s390x,macos_x86_64">
		<dependency type="feature" uid="be-datagrid_be-datagrid"  mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents DataGrid" />
<!-- 		<dependency type="feature" uid="main_activespaces-admin" version="@AS_VERSION@" description="Tibco ActiveSpaces Admin" /> -->
        <dependency type="feature" uid="main_activespaces-common" version="@AS_VERSION@" description="TIBCO ActiveSpaces Common" />
        <dependency type="feature" uid="main_activespaces-runtime" version="@AS_VERSION@" description="TIBCO ActiveSpaces Runtime" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard"  mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
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
    
	<installerFeature name="Monitoring and Management SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-monitoringandmanagement_be-monitoringandmanagement" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Monitoring and Management" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Monitoring and Management Server" />
			<property shortcutTarget="$L{be.product.home}/mm/bin/be-mm.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/mm/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
		    <condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/mm/bin/be-mm.tra"/>
			</condition>
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Monitoring and Management Server" />
		</wizardAction>
	</installerFeature>
	
	<installerFeature name="Enterprise Administrator Agent SP @BE_VERSION_UPDATE@" visible="true" version="@BE_VERSION@">
		<dependency type="feature" uid="be-teagent_be-teagent" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Monitoring and Management" />
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Enterprise Administrator Agent" />
			<property shortcutTarget="$L{be.product.home}/teagent/bin/be-teagent.exe" />
			<property shortcutArgs="" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/teagent/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
			 <condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/mm/bin/be-mm.tra"/>
			</condition>
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
		</assemblyList>
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard"  mustBeInstalled="false" version="5.0.0" description="TIBCO BusinessEvents Server" />				
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-standard_documentation-install.xml" description="Performing post-installation tasks for BusinessEvents Standard Documentation"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-standard_documentation-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Standard Documentation"/>
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
		<dependency type="feature" uid="Server SP @BE_VERSION_UPDATE@_businessevents-standard" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
		<wizardAction class="com.tibco.installer.wizard.action.TIBCOCreateWindowsShortcutWizardAction" sequence="post-install">
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="Start Rules Management Server" />
			<property shortcutTarget="$L{be.product.home}/rms/bin/be-rms.exe" />
			<property shortcutArgs="-c RMS.cdd -u default RMS.ear" />
			<property shortcutIcon="" />
			<property shortcutWorkingDirectory="$L{be.product.home}/rms/bin" />
		</wizardAction>
		<wizardAction class="com.tibco.installer.wizard.action.TIBCORemoveWindowsShortcutWizardAction" sequence="post-uninstall">
		    <condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/rms/bin/be-rms.tra"/>
			</condition>
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
			<condition class="com.tibco.installer.wizard.condition.TIBCOFileExistsWizardCondition">
				<property mustBeMet="false"/>
				<property filename="${tibco.home}/be/5.4/rms/bin/be-rms.tra"/>
			</condition>
			<property shortcutFolder="TIBCO/${tibco.environment.name}/TIBCO BusinessEvents $L{be.shortVersion}" />
			<property shortcutName="WebStudio" />
		</wizardAction>		
	</installerFeature>

	<installerFeature name="Server SP @BE_VERSION_UPDATE@" visible="false" version="@BE_VERSION@">
		<dependency type="feature" uid="be-server_be-server" mustBeInstalled="false" version="@BE_VERSION@" description="TIBCO BusinessEvents Server" />
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

	<customSettings promptForEclipse="true"></customSettings>
	
</TIBCOInstallerFeatures>



