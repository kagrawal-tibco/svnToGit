<?xml  version="1.0"?>
<TIBCOInstallerFeatures>
    <productDef productType="contributor-servicepack" name="be-liveview" id="be-liveview" version="@BE_VERSION@.@BE_BUILD@" />
    
    <customVariables>
        <localVariable name="be.shortVersion" value="@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>
        <localVariable name="be.version" value="@BE_VERSION@"/>
		<localVariable name="be.product.home" value="${tibco.home}/be/@BE_VERSION_MAJOR@.@BE_VERSION_MINOR@"/>                       
    </customVariables>
	
    <installerFeature visible="false" name="be-liveview" installLocation="$L{be.product.home}" version="@BE_VERSION@.@BE_BUILD@" plat="@BE_DESIGNTIME_PLATFORMS@" >
		<assemblyList>
			<assembly uid="product_tibco_be_liveview" version="@BE_VERSION@.@BE_BUILD@" plat="@BE_DESIGNTIME_PLATFORMS@"/>
		</assemblyList>
	</installerFeature>
</TIBCOInstallerFeatures>
