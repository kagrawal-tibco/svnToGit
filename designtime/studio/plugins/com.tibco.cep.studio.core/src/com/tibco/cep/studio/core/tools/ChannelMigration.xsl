<?xml version="1.0" encoding="UTF-8" ?>

<xsl:stylesheet version="2.0" exclude-result-prefixes="xsi xdt err fn" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes" xmlns:err="http://www.w3.org/2005/xqt-errors" xmlns:channel="http:///com/tibco/cep/designtime/core/model/service/channel">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="//driver">
		<xsl:variable name="driverTypeAttr" select="@driverType"/><!-- Check if it has driverType attribute -->
		<xsl:if test="string-length($driverTypeAttr) != 0">
			<xsl:call-template name="Create_DriverType_Element">
				<xsl:with-param name="driverTypeName" select="$driverTypeAttr"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="string-length($driverTypeAttr) = 0">
			<xsl:variable name="RVDriver">
				<xsl:text>RendezVous</xsl:text>
			</xsl:variable>
			<xsl:if test="not(self::node()/driverType)">
				<xsl:call-template name="Create_DriverType_Element">
					<xsl:with-param name="driverTypeName" select="$RVDriver"/>
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
		<xsl:if test="self::node()/driverType">
			<xsl:copy>
				<xsl:apply-templates select="@*|node()"/>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template><!-- Create the new element -->
	<xsl:template name="Create_DriverType_Element">
		<xsl:param name="driverTypeName"/>
		<xsl:element name="driver">
			<xsl:copy-of select="@*[name() != 'driverType']"/>
			<xsl:element name="driverType">
				<xsl:attribute name="xsi:type" namespace="http://www.w3.org/2001/XMLSchema">
					<xsl:text>channel:DriverTypeInfo</xsl:text>
				</xsl:attribute>
				<xsl:attribute name="driverTypeName">
					<xsl:value-of select="$driverTypeName"/>
				</xsl:attribute>
			</xsl:element>
			<xsl:copy-of select="node()"/>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>
