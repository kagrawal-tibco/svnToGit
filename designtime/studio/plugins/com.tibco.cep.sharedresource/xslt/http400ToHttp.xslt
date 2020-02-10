<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
				xmlns:ns0="www.tibco.com/shared/HTTPConnection"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />
  	
    <xsl:template match="/Http">
        <ns0:httpSharedResource>
        	<config>
				<xsl:apply-templates select="General"/>
				<xsl:apply-templates select="Configuration"/>
			</config>	
        </ns0:httpSharedResource>
    </xsl:template>

    <xsl:template match="General">
        <description>
            <xsl:value-of select="Description"/>
        </description>
	</xsl:template>

    <xsl:template match="Configuration">
		<Host>
			<xsl:value-of select="Host"/>
		</Host>
		<Port>
			<xsl:value-of select="Port"/>
		</Port>
		<enableLookups>
			<xsl:value-of select="EnableLookups"/>
		</enableLookups>
	</xsl:template>

</xsl:stylesheet>
