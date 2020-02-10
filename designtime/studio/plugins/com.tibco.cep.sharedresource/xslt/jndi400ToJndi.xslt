<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />
  	
    <xsl:template match="/Jndi">
        <BWSharedResource>
			<xsl:apply-templates select="General"/>
			<xsl:apply-templates select="Configuration"/>
        </BWSharedResource>
    </xsl:template>

    <xsl:template match="General">
        <name>
            <xsl:value-of select="Name"/>
        </name>
        <resourceType>
            <xsl:value-of select="ResourceType"/>
        </resourceType>
        <description>
            <xsl:value-of select="Description"/>
        </description>
	</xsl:template>

    <xsl:template match="Configuration">
		<config>
			<ValidateJndiSecurityContext>
				<xsl:value-of select="ValidateJndiSecurityContext"/>
			</ValidateJndiSecurityContext>
			<ContextFactory>
				<xsl:value-of select="ContextFactory"/>
			</ContextFactory>
			<ProviderUrl>
				<xsl:value-of select="ProviderUrl"/>
			</ProviderUrl>
			<SecurityPrincipal>
				<xsl:value-of select="SecurityPrincipal"/>
			</SecurityPrincipal>
			<SecurityCredentials>
				<xsl:value-of select="SecurityCredentials"/>
			</SecurityCredentials>
			<xsl:copy-of select="OptionalJNDIProperties"/>
		</config>
	</xsl:template>

</xsl:stylesheet>
