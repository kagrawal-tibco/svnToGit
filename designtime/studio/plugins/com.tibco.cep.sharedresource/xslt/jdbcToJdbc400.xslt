<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:in="http://tibco.com/be/shared/jdbc"
                xmlns:out="http://tibco.com/be/shared/jdbc400"
                xmlns:aems="http://www.tibco.com/xmlns/aemeta/services/2002"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />

    <xsl:template match="/BWSharedResource">
		<Jdbc>
			<General>
				<Name>
                	<xsl:value-of select="name"/>
                </Name>
                <ResourceType>
                    <xsl:value-of select="resourceType"/>
                </ResourceType>                
            </General>
			<xsl:apply-templates select="/BWSharedResource/config" />
		</Jdbc>		
    </xsl:template>		

	<xsl:template match="config">
		<Configuration>
			<Driver>
				<xsl:value-of select="driver"/>
			</Driver>
			<MaxConnections>
				<xsl:value-of select="maxConnections"/>
			</MaxConnections>
			<LoginTimeout>
				<xsl:value-of select="loginTimeout"/>
			</LoginTimeout>
			<ConnectionType>
				<xsl:value-of select="connectionType"/>
			</ConnectionType>
			<UseSharedJndiConfig>
				<xsl:value-of select="UseSharedJndiConfig"/>
			</UseSharedJndiConfig>
			<Location>
				<xsl:value-of select="location"/>
			</Location>
			<User>
				<xsl:value-of select="user"/>
			</User>
			<Password>
				<xsl:value-of select="password"/>
			</Password>
			<JndiDataSourceName>
				<xsl:value-of select="jndiDataSourceName"/>
			</JndiDataSourceName>
			<XaDsClass>
				<xsl:value-of select="xaDsClass"/>
			</XaDsClass>
		</Configuration>
    </xsl:template>
</xsl:stylesheet>
