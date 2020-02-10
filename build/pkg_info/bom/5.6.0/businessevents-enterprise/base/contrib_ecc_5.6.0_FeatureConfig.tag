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
    <productDef productType="contributor" name="ecc" id="ecc" version="@BE_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
    </customVariables>

    <installerFeature name="ecc"  visible="false" version="@BE_VERSION@.@BE_BUILD@" plat="@SUNEC_PLATFORMS@">
		<assemblyList>
		</assemblyList>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="linux_x86_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_sunec_1.8.0.212_linux_x86_64.zip"/>
    		<property targetInstallFeature="ecc_ecc" />
    		<property assemblyUID="product_tibco_sunec" />
    		<property assemblyVersion="1.8.0.212" />
    		<property url="http://public.tibco.com/pub/tibco_oss/sunec"/>
    		<property installLocation="${tibco.home}/tibcojre64/1.8.0"/>
    		<property lgplAssemblyDisplayName="Oracle Elliptic Curve Cryptography Library"/>
		</wizardAction>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="win_x86_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_sunec_1.8.0.212_win_x86_64.zip"/>
    		<property targetInstallFeature="ecc_ecc" />
    		<property assemblyUID="product_tibco_sunec" />
    		<property assemblyVersion="1.8.0.212" />
    		<property url="http://public.tibco.com/pub/tibco_oss/sunec"/>
    		<property installLocation="${tibco.home}/tibcojre64/1.8.0"/>
    		<property lgplAssemblyDisplayName="Oracle Elliptic Curve Cryptography Library"/>
		</wizardAction>
		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="sol_sparc_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_sunec_1.8.0.212_sol_sparc_64.zip"/>
    		<property targetInstallFeature="ecc_ecc" />
    		<property assemblyUID="product_tibco_sunec" />
    		<property assemblyVersion="1.8.0.212" />
    		<property url="http://public.tibco.com/pub/tibco_oss/sunec"/>
    		<property installLocation="${tibco.home}/tibcojre64/1.8.0"/>
    		<property lgplAssemblyDisplayName="Oracle Elliptic Curve Cryptography Library"/>
		</wizardAction>

		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="sol_x86_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_sunec_1.8.0.212_sol_x86_64.zip"/>
    		<property targetInstallFeature="ecc_ecc" />
    		<property assemblyUID="product_tibco_sunec" />
    		<property assemblyVersion="1.8.0.212" />
    		<property url="http://public.tibco.com/pub/tibco_oss/sunec"/>
    		<property installLocation="${tibco.home}/tibcojre64/1.8.0"/>
    		<property lgplAssemblyDisplayName="Oracle Elliptic Curve Cryptography Library"/>
		</wizardAction>

		
		<wizardAction class="com.tibco.installer.wizard.action.TIBCODownloadAssemblyWizardAction" sequence="post-selection" plat="macosx_x86_64">
    		<property licenseFile="license.txt" />
    		<!-- The content of this assembly doesn't depend on linux kernel version. -->
    		<property filename="product_tibco_sunec_1.8.0.212_macosx_x86_64.zip"/>
    		<property targetInstallFeature="ecc_ecc" />
    		<property assemblyUID="product_tibco_sunec" />
    		<property assemblyVersion="1.8.0.212" />
    		<property url="http://public.tibco.com/pub/tibco_oss/sunec"/>
    		<property installLocation="${tibco.home}/tibcojre64/1.8.0"/>
    		<property lgplAssemblyDisplayName="Oracle Elliptic Curve Cryptography Library"/>
		</wizardAction>
	</installerFeature>
    
	
</TIBCOInstallerFeatures>



