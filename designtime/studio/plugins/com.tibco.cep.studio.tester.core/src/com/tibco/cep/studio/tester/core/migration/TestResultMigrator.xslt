<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:transform xmlns:ns1="www.tibco.com/be/studio/tester"
	version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="new-root-node">
		<xsl:text>ns1:TesterResults</xsl:text>
	</xsl:variable>
	<xsl:template match="/*">
		<xsl:element name="{$new-root-node}">
			<xsl:for-each select="@*">
				<xsl:attribute name="{name()}">
<xsl:value-of select="." />
</xsl:attribute>
			</xsl:for-each>
			<xsl:copy-of select="*" />
		</xsl:element>
	</xsl:template>
</xsl:transform>