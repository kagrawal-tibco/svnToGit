<?xml version="1.0" encoding="UTF-8" ?>

<!-- New document created with EditiX at Thu Jun 25 10:41:22 IST 2009 -->

<xsl:stylesheet version="1.0" exclude-result-prefixes="xs xdt err fn" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes" xmlns:err="http://www.w3.org/2005/xqt-errors">
	<xsl:output method="xml" indent="no"/>
             <xsl:strip-space elements="*"/>
	<xsl:template name="replaceTRV">
		<xsl:if test="not('//Table/@since')">
			<xsl:call-template name="replaceConditions"/>
			<xsl:call-template name="replaceActions"/>
			<xsl:call-template name="deleteExpression"/>
		</xsl:if>
	</xsl:template>
	<xsl:template name="replaceMetadataSection" match="//metadata">
		<xsl:call-template name="createProperty">
			<xsl:with-param name="mdElementName" select="metadata"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="createProperty">
		<xsl:param name="mdElementName"/>
		<xsl:element name="md">
			<xsl:for-each select="self::node()/property">
				<xsl:variable name="propertyValue" select="@value"/>
				<xsl:if test="string-length($propertyValue) != 0">
					<xsl:element name="prop">
						<xsl:copy-of select="@*"/>
						<xsl:copy-of select="*[name() != '$mdElementName']"/>
					</xsl:element>
				</xsl:if>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<xsl:template name="replaceMetaDataSection" match="//metaData">
		<xsl:call-template name="createProperty">
			<xsl:with-param name="mdElementName" select="metaData"/>
		</xsl:call-template>
	</xsl:template>
	<xsl:template name="replaceConditions" match="//condition">
		<xsl:variable name="trvid" select="ancestor::rule/@id"/>
		<xsl:variable name="columnId" select="@columnId"/>
		<xsl:variable name="modified" select="@modified"/>
		<xsl:variable name="expression">
			<xsl:value-of select="self::node()/expression/body/text()"/>
		</xsl:variable>
		<xsl:element name="cond">
			<xsl:attribute name="id">
				<xsl:value-of select="concat($trvid, '_', $columnId)"/>
			</xsl:attribute>
			<xsl:attribute name="colId">
				<xsl:value-of select="$columnId"/>
			</xsl:attribute>
			<xsl:attribute name="expr">
				<xsl:value-of select="$expression"/>
			</xsl:attribute>
		</xsl:element>
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template name="replaceActions" match="//action">
		<xsl:variable name="trvid" select="ancestor::rule/@id"/>
		<xsl:variable name="columnId" select="@columnId"/>
		<xsl:variable name="expression">
			<xsl:value-of select="self::node()/expression/body/text()"/>
		</xsl:variable>
		<xsl:element name="act">
			<xsl:attribute name="id">
				<xsl:value-of select="concat($trvid, '_', $columnId)"/>
			</xsl:attribute>
			<xsl:attribute name="colId">
				<xsl:value-of select="$columnId"/>
			</xsl:attribute>
			<xsl:attribute name="expr">
				<xsl:value-of select="$expression"/>
			</xsl:attribute>
		</xsl:element>
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template name="deleteExpression" match="//expression"/>
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>
