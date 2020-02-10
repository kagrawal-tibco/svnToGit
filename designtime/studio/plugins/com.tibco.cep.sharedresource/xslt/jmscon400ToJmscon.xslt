<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />
  	
    <xsl:template match="/JmsCon">
        <BWSharedResource>
			<xsl:apply-templates select="General"/>
			<config>
				<xsl:apply-templates select="NamingEnvironment"/>			
				<xsl:apply-templates select="ConnectionAttributes"/>
				<xsl:apply-templates select="Configuration"/>
			</config>				
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
	        <username>
	            <xsl:value-of select="Username"/>
	        </username>
	        <password>
	            <xsl:value-of select="Password"/>
	        </password>
	        <clientID>
	            <xsl:value-of select="ClientID"/>
	        </clientID>
	        <autoGenClientID>
	            <xsl:value-of select="AutoGenClientID"/>
	        </autoGenClientID>
		</ConnectionAttributes>
	</xsl:template>

    <xsl:template match="Configuration">
		<UseXACF>
			<xsl:value-of select="UseXACF"/>
		</UseXACF>
		<useSsl>
			<xsl:value-of select="UseSsl"/>
		</useSsl>
	</xsl:template>

</xsl:stylesheet>
