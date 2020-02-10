<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL: http://svn.tibco.com/be/branches/5.2/build/pkg_info/bom/5.2.0/base/contrib_be-datagrid_5.2.0_FeatureConfig.tag $
 ! $LastChangedRevision: 52648 $
 ! $LastChangedDate: 2012-07-01 10:05:57 -0700 (Sun, 01 Jul 2012) $
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
    <productDef productType="contributor" name="businessevents-standard" id="businessevents-standard" version="@BE_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>    
		<variable name="be.product.standard.installed" value="true"/>
		<variable name="minimum.as.version" value="2.3.0"/>                   
    </customVariables>

	<installerFeature name="TIBCO BusinessEvents Studio" visible="false" version="@BE_VERSION@" plat="win_x86_64,linux26gl25_x86_64,macos_x86_64,sol_sparc_64,sol_x86_64,hpux112_ia64,aix61_power_64,linux26gl24_s390x">
		
	</installerFeature>

	<installerFeature name="TIBCO BusinessEvents DataGrid" visible="false" version="@BE_VERSION@" plat="win_x86,win_x86_64,linux26gl25_x86,linux26gl25_x86_64,sol_sparc,sol_x86,hpux111_hppa,aix61_power,hpux112_ia64,sol_sparc_64,sol_x86_64,aix61_power_64,hpux111_hppa_64,linux26gl24_s390x,macos_x86_64">
	
		
	</installerFeature>
    
	<installerFeature name="Monitoring and Management" visible="false" version="@BE_VERSION@">
		
	</installerFeature>
	
	<installerFeature name="Enterprise Administrator Agent" visible="false" version="@BE_VERSION@">
		
	</installerFeature>
    
    <installerFeature name="Eclipse Platform" visible="false" version="@BE_VERSION@" plat="win_x86_64,linux26gl25_x86_64,macos_x86_64,sol10_sparc_64,sol10_x86_64,hpux112_ia64,aix61_power_64,linux26gl24_s390x">
		
	</installerFeature>
    
	<installerFeature name="Examples" installLocation="$L{be.product.home}" visible="false" version="@BE_VERSION@">
		
	</installerFeature>

    <installerFeature name="Rules Management Server" visible="false" version="@BE_VERSION@">
			
	</installerFeature>

	<installerFeature name="Server" visible="false" version="@BE_VERSION@">
		
	</installerFeature>

	<customSettings promptForEclipse="true"></customSettings>
    
</TIBCOInstallerFeatures>



