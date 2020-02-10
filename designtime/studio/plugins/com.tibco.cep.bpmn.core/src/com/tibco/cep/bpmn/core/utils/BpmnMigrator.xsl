<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="2.0" exclude-result-prefixes="xsi xdt err fn" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes" xmlns:err="http://www.w3.org/2005/xqt-errors" xmlns:palette="http://www.tibco.com/cep/studio/common/palette">

	<xsl:output method="xml" indent="yes"/>
	<xsl:param name="nS" select="'http://www.tibco.com/cep/studio/common/palette'"/>
	<xsl:template match="node()|@*">
		<xsl:copy>
  		 	<xsl:apply-templates select="*"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match ="*">
 		<xsl:element name="{name()}" namespace="{$nS}">
       		<xsl:apply-templates select="node()|@*"/>
 		</xsl:element>
	</xsl:template>
</xsl:stylesheet>