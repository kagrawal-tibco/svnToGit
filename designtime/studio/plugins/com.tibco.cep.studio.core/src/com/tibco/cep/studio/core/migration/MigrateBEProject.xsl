<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>
	
	<!-- Do nothing so as this node is not matched -->
	<xsl:template match="//decisionResourceSettings" />

</xsl:stylesheet>