<?xml  version="1.0"?>
<!--
 !
 ! $HeadURL: http://svn.tibco.com/be/branches/5.2/build/pkg_info/bom/5.2.0/base/contrib_be-process-modeler_1.0.0_FeatureConfig.tag $
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
    <productDef productType="contributor-servicepack" name="be-process-modeler" id="be-process-modeler" version="@BE_BPMN_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
	
    <installerFeature visible="false" name="be-process-modeler" installLocation="$L{be.product.home}" version="@BE_BPMN_VERSION@.@BE_BUILD@" plat="@BE_DESIGNTIME_PLATFORMS@">
		<assemblyList>
			<assembly uid="product_tibco_be_process_editor" plat="@BE_DESIGNTIME_PLATFORMS@" version="@BE_BPMN_VERSION@.@BE_BUILD@"/>
		</assemblyList>
		<postInstallAction sequence="post-assembly-ref" target="post-install" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/post-process_editor-install.xml" description="Performing post-installation tasks for BusinessEvents Process Editor"/>
		<postInstallAction sequence="pre-uninstall-ref" target="pre-uninstall" taskFile="${TIBCO_HOME}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@/uninstaller_scripts/pre-process_editor-uninstall.xml" description="Performing pre-uninstallation tasks for BusinessEvents Process Editor"/>
	</installerFeature>
</TIBCOInstallerFeatures>
