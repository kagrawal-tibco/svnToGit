<?xml version="1.0" encoding="UTF-8" ?>

<!-- New document created with EditiX at Tue Aug 31 12:36:16 IST 2010 -->

<xsl:stylesheet version="2.0" exclude-result-prefixes="xs xdt err fn" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes" xmlns:err="http://www.w3.org/2005/xqt-errors">
	<xsl:output method="xml" indent="yes"/>
	<xsl:param name="newProjectName"/>
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="@ownerProjectName[parent::node()]">
		<xsl:attribute name="ownerProjectName">
			<xsl:value-of select="$newProjectName"/>
		</xsl:attribute>
	</xsl:template>
</xsl:stylesheet>
