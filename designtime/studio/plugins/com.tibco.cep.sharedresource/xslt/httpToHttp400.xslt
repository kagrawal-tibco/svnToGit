<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:in="http://tibco.com/be/shared/http"
                xmlns:out="http://tibco.com/be/shared/http400"
				xmlns:ns0="www.tibco.com/shared/HTTPConnection"                
                xmlns:aems="http://www.tibco.com/xmlns/aemeta/services/2002"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />

    <xsl:template match="/ns0:httpSharedResource">
		<Http>
			<xsl:apply-templates select="/ns0:httpSharedResource/config" />
		</Http>		
    </xsl:template>		

	<xsl:template match="config">
		<General>
			<Description>
				<xsl:value-of select="description"/>
			</Description>
		</General>
		<Configuration>
			<xsl:copy-of select="Host"/>
			<xsl:copy-of select="Port"/>
			<EnableLookups>
				<xsl:value-of select="enableLookups"/>
			</EnableLookups>
		</Configuration>
    </xsl:template>
</xsl:stylesheet>
