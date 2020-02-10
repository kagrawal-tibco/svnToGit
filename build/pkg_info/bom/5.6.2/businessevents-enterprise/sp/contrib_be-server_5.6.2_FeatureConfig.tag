<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL: http://svn.tibco.com/be/branches/5.2/build/pkg_info/bom/5.2.0/base/contrib_be-server_5.2.0_FeatureConfig.tag $
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
    <productDef productType="contributor" name="be-server" id="be-server" version="@BE_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>

	<installerFeature name="be-server" visible="false" version="@BE_VERSION@.@BE_BUILD@">
		<assemblyList>
			<assembly uid="product_tibco_be_server" version="@BE_VERSION@.@BE_BUILD@" overrideTargetDirectory="$L{be.product.home}"/>
			<dependency type="feature" uid="main_gwrapper" version="2.4.1" description="gwrapper" plat="macos_x86_64" />
			<dependency type="feature" uid="main_wrapper" version="2.4.1" description="Wrapper" plat="win_x86_64,linux_x86_64,sol_sparc_64,macos_x86_64" />
			<dependency type="feature" uid="main_tibcojava64-oracle" version="11" description="64bit JRE Oracle" plat="win_x86_64,linux_x86_64,sol_sparc_64,macos_x86_64"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-server-install.xml" description="Performing post-installation tasks for BusinessEvents Server"/>
	</installerFeature>

</TIBCOInstallerFeatures>



