<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />
  	
    <xsl:template match="/Jdbc">
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
	</xsl:template>

    <xsl:template match="Configuration">
		<config>
			<driver>
				<xsl:value-of select="Driver"/>
			</driver>
			<maxConnections>
				<xsl:value-of select="MaxConnections"/>
			</maxConnections>
			<loginTimeout>
				<xsl:value-of select="LoginTimeout"/>
			</loginTimeout>
			<connectionType>
				<xsl:value-of select="ConnectionType"/>
			</connectionType>
			<UseSharedJndiConfig>
				<xsl:value-of select="UseSharedJndiConfig"/>
			</UseSharedJndiConfig>
			<location>
				<xsl:value-of select="Location"/>
			</location>
			<user>
				<xsl:value-of select="User"/>
			</user>
			<password>
				<xsl:value-of select="Password"/>
			</password>
			<jndiDataSourceName>
				<xsl:value-of select="JndiDataSourceName"/>
			</jndiDataSourceName>
			<xaDsClass>
				<xsl:value-of select="XaDsClass"/>
			</xaDsClass>
		</config>
	</xsl:template>

</xsl:stylesheet>
