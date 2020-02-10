<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL: http://svn.tibco.com/be/branches/5.2/build/pkg_info/bom/5.2.0/base/contrib_be-pattern_5.2.0_FeatureConfig.tag $
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
    <productDef productType="contributor" name="be-pattern" id="be-pattern" version="@BE_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
	
    <installerFeature name="be-pattern" installLocation="$L{be.product.home}" visible="false" version="@BE_VERSION@.@BE_BUILD@">
	<assemblyList>
            <assembly uid="product_tibco_be_pattern" version="@BE_VERSION@.@BE_BUILD@"/>
	</assemblyList>
    </installerFeature>
	
</TIBCOInstallerFeatures>
