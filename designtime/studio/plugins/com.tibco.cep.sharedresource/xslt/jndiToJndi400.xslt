<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:in="http://tibco.com/be/shared/jndi"
                xmlns:out="http://tibco.com/be/shared/jndi400"
                xmlns:aems="http://www.tibco.com/xmlns/aemeta/services/2002"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />

    <xsl:template match="/BWSharedResource">
		<Jndi>
			<General>
				<Name>
                	<xsl:value-of select="name"/>
                </Name>
                <ResourceType>
                    <xsl:value-of select="resourceType"/>
                </ResourceType>                
                <Description>
                    <xsl:value-of select="description"/>
                </Description>
            </General>
			<xsl:apply-templates select="/BWSharedResource/config" />
		</Jndi>		
    </xsl:template>		

	<xsl:template match="config">
		<Configuration>
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
		</Configuration>
    </xsl:template>
</xsl:stylesheet>
