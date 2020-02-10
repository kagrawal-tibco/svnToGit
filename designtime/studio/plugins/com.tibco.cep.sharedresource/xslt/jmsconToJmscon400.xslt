<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:in="http://tibco.com/be/shared/jmscon"
                xmlns:out="http://tibco.com/be/shared/jmscon400"
                xmlns:aems="http://www.tibco.com/xmlns/aemeta/services/2002"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />

    <xsl:template match="/BWSharedResource">
		<JmsCon>
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
		</JmsCon>		
    </xsl:template>		

	<xsl:template match="config">
		<xsl:apply-templates select="/BWSharedResource/config/NamingEnvironment" />
		<xsl:apply-templates select="/BWSharedResource/config/ConnectionAttributes" />
		<Configuration>
			<UseXACF>
				<xsl:value-of select="UseXACF"/>
			</UseXACF>
			<UseSsl>
				<xsl:value-of select="useSsl"/>
			</UseSsl>
		</Configuration>
    </xsl:template>

	<xsl:template match="NamingEnvironment">
		<NamingEnvironment>
			<UseJNDI>
				<xsl:value-of select="UseJNDI"/>
			</UseJNDI>
			<ProviderURL>
				<xsl:value-of select="ProviderURL"/>
			</ProviderURL>
			<NamingURL>
				<xsl:value-of select="NamingURL"/>
			</NamingURL>
			<NamingInitialContextFactory>
				<xsl:value-of select="NamingInitialContextFactory"/>
			</NamingInitialContextFactory>
			<TopicFactoryName>
				<xsl:value-of select="TopicFactoryName"/>
			</TopicFactoryName>
			<QueueFactoryName>
				<xsl:value-of select="QueueFactoryName"/>
			</QueueFactoryName>
			<NamingPrincipal>
				<xsl:value-of select="NamingPrincipal"/>
			</NamingPrincipal>
			<NamingCredential>
				<xsl:value-of select="NamingCredential"/>
			</NamingCredential>
		</NamingEnvironment>	
	</xsl:template>
	
	<xsl:template match="ConnectionAttributes">
		<ConnectionAttributes>
			<Username>
				<xsl:value-of select="username"/>
			</Username>
			<Password>
				<xsl:value-of select="password"/>
			</Password>
			<ClientID>
				<xsl:value-of select="clientID"/>
			</ClientID>
			<AutoGenClientID>
				<xsl:value-of select="autoGenClientID"/>
			</AutoGenClientID>
		</ConnectionAttributes>
	</xsl:template>	

</xsl:stylesheet>
