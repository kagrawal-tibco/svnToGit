<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL: http://svn.tibco.com/be/branches/5.2/build/pkg_info/bom/5.2.0/base/contrib_be-eclipse_5.2.0_FeatureConfig.tag $
 ! $LastChangedRevision: 53929 $
 ! $LastChangedDate: 2012-08-16 00:03:18 -0700 (Thu, 16 Aug 2012) $
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
    <productDef productType="contributor" name="be-eclipse" id="be-eclipse" version="@BE_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>

    <installerFeature name="be-eclipse"  visible="false" version="@BE_VERSION@.@BE_BUILD@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<assemblyList>
			<assembly uid="product_tibco_be_eclipse" plat="@BE_DESIGNTIME_PLATFORMS@" version="@BE_VERSION@.@BE_BUILD@" overrideTargetDirectory="$L{be.product.home}"/>
		</assemblyList>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="linux_x86_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_be_eclipse_lgpl_4.6.3.001_linux_x86_64.zip" />
    		<property targetInstallFeature="be-eclipse_be-eclipse" />
    		<property assemblyUID="product_tibco_be_eclipse_lgpl" />
    		<property assemblyVersion="4.6.3.001" />
    		<property url="http://public.tibco.com/pub/tibco_oss/eclipse_lgpl" />
    		<property installLocation="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/eclipse-platform/eclipse" />
    		<property lgplAssemblyDisplayName="Gnome Binding" />
		</wizardAction>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="linux_s390x">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_be_eclipse_lgpl_4.6.3.001_linux_s390x.zip" />
    		<property targetInstallFeature="be-eclipse_be-eclipse" />
    		<property assemblyUID="product_tibco_be_eclipse_lgpl" />
    		<property assemblyVersion="4.6.3.001" />
    		<property url="http://public.tibco.com/pub/tibco_oss/eclipse_lgpl" />
    		<property installLocation="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/eclipse-platform/eclipse" />
    		<property lgplAssemblyDisplayName="Gnome Binding" />
		</wizardAction>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="aix_power_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_be_eclipse_lgpl_4.6.3.001_aix_power_64.zip" />
    		<property targetInstallFeature="be-eclipse_be-eclipse" />
    		<property assemblyUID="product_tibco_be_eclipse_lgpl" />
    		<property assemblyVersion="4.6.3.001" />
    		<property url="http://public.tibco.com/pub/tibco_oss/eclipse_lgpl" />
    		<property installLocation="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/eclipse-platform/eclipse" />
    		<property lgplAssemblyDisplayName="Gnome Binding" />
		</wizardAction>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="hpux_ia64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_be_eclipse_lgpl_4.6.3.001_hpux_ia64.zip" />
    		<property targetInstallFeature="be-eclipse_be-eclipse" />
    		<property assemblyUID="product_tibco_be_eclipse_lgpl" />
    		<property assemblyVersion="4.6.3.001" />
    		<property url="http://public.tibco.com/pub/tibco_oss/eclipse_lgpl" />
    		<property installLocation="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/eclipse-platform/eclipse" />
    		<property lgplAssemblyDisplayName="Gnome Binding" />
		</wizardAction>

		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="sol_sparc_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_be_eclipse_lgpl_4.6.3.001_sol_sparc_64.zip" />
    		<property targetInstallFeature="be-eclipse_be-eclipse" />
    		<property assemblyUID="product_tibco_be_eclipse_lgpl" />
    		<property assemblyVersion="4.6.3.001" />
    		<property url="http://public.tibco.com/pub/tibco_oss/eclipse_lgpl" />
    		<property installLocation="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/eclipse-platform/eclipse" />
    		<property lgplAssemblyDisplayName="Gnome Binding" />
		</wizardAction>

		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="sol_x86_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_be_eclipse_lgpl_4.6.3.001_sol_x86_64.zip" />
    		<property targetInstallFeature="be-eclipse_be-eclipse" />
    		<property assemblyUID="product_tibco_be_eclipse_lgpl" />
    		<property assemblyVersion="4.6.3.001" />
    		<property url="http://public.tibco.com/pub/tibco_oss/eclipse_lgpl" />
    		<property installLocation="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/eclipse-platform/eclipse" />
    		<property lgplAssemblyDisplayName="Gnome Binding" />
		</wizardAction>

	</installerFeature>
    
	
</TIBCOInstallerFeatures>



