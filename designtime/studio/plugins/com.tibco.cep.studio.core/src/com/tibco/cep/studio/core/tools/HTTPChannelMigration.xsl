<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="2.0" exclude-result-prefixes="xsi xdt err fn" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes" xmlns:err="http://www.w3.org/2005/xqt-errors" xmlns:channel="http:///com/tibco/cep/designtime/core/model/service/channel">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="//driver">
		<xsl:variable name="driverTypeName" select="driverType/@driverTypeName"/><!-- Copy Driver type Name attribute -->
		<xsl:choose>
			<xsl:when test="$driverTypeName = 'HTTP'">
				<xsl:call-template name="add_xsi_type">
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:copy>
					<xsl:apply-templates select="@*|node()"/>
				</xsl:copy>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template><!-- Create the new element -->
	<xsl:template name="add_xsi_type">
		<xsl:element name="driver">
			<xsl:copy-of select="@*[name() != 'xsi:type']"></xsl:copy-of>
			<xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema">
				<xsl:text>channel:HttpChannelDriverConfig</xsl:text>
			</xsl:attribute>
			<xsl:copy-of select="node()"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
