<!-- XSLT used to migrate array type columns in DTables in 4.0 format to the new 5.x format. -->
<xsl:stylesheet version="1.0" exclude-result-prefixes="xsi xdt err fn"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema"
	xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes"
	xmlns:err="http://www.w3.org/2005/xqt-errors" xmlns:xalan="http://xml.apache.org/xalan"
	xmlns:migration="http://www.tibco.com/be/studio/table/migration"
	extension-element-prefixes="migration">
	
	<!-- Root directory of the project -->
	<xsl:param name="projectRootDir"/>
	
	<xalan:component prefix="migration" functions="getMassagedColumnName">
		<xalan:script lang="javaclass" src="xalan://com.tibco.cep.studio.core.migration.helper.ArrayColumnMigrationHelper" />
	</xalan:component>

	<xsl:template match="//columns">
		<xsl:variable name="ArgPaths">
			<xsl:for-each select="parent::*/parent::*/child::argument">
				<xsl:value-of select="concat(./property/attribute::path, ',')" />
				<!-- Take back to the same columns -->
				<xsl:if test="//columns"/>
			</xsl:for-each>
		</xsl:variable>
		
		<xsl:variable name="ArgResourceTypes">
			<xsl:for-each select="parent::*/parent::*/child::argument">
				<xsl:value-of select="concat(./property/attribute::resourceType, ',')" />
				<xsl:if test="//columns"/>
			</xsl:for-each>
		</xsl:variable>

		<xsl:variable name="ArgAliases">
			<xsl:for-each select="parent::*/parent::*/child::argument">
				<xsl:value-of select="concat(./property/attribute::alias, ',')" />
				<xsl:if test="//columns"/>
			</xsl:for-each>
		</xsl:variable>

		<xsl:element name="columns">
			<xsl:for-each select="child::column">
				<xsl:variable name="ColumnName" select="@name" />
				<xsl:element name="column">
					<!-- Copy all attrs as is except name -->
					<xsl:copy-of select="@*[name() != 'name']"/>
					<xsl:variable name="MassagedColumnName"
						select="migration:getMassagedColumnName($projectRootDir, $ArgPaths, $ArgAliases, $ArgResourceTypes, $ColumnName)" />
					<xsl:attribute name="name">
                        <xsl:value-of select="$MassagedColumnName"/>
                    </xsl:attribute>	
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>