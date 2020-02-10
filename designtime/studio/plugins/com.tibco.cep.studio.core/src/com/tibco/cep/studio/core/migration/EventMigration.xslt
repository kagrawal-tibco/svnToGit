<xsl:stylesheet version="1.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:designtime="http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:event="http:///com/tibco/cep/designtime/core/model/event">
	<xsl:output method="xml" indent="yes" />
	
	<xsl:variable name="ownerProjectName" select="event:SimpleEvent/@ownerProjectName" />
	<xsl:variable name="ownerPath">
		<xsl:value-of select="concat(event:SimpleEvent/@folder, event:SimpleEvent/@name)" />
	</xsl:variable>
	
	<xsl:template match="@*|*">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="event:SimpleEvent/properties">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
			<!-- Create ownerProjectName and ownerPath attributes if not present (BE-21005) -->
			<xsl:if test="not(@ownerProjectName)">
				<xsl:attribute name="ownerProjectName"><xsl:value-of select="$ownerProjectName" /></xsl:attribute>
			</xsl:if>
			<xsl:if test="not(@ownerPath)">
				<xsl:attribute name="ownerPath"><xsl:value-of select="$ownerPath" /></xsl:attribute>
			</xsl:if>
		</xsl:copy>
	</xsl:template>
	
</xsl:stylesheet>
