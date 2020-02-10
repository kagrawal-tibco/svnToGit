<xsl:stylesheet version="1.0"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:designtime="http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:output method="xml" indent="yes"/>
	<xsl:strip-space elements="extendedProperties" />
    
    <xsl:template match="node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*"/>
		</xsl:copy>
    </xsl:template>

	<!--  Match extended properties groups -->
	<xsl:template match="properties[@xsi:type='designtime:ObjectProperty' and @name='Backing Store']" />
	<xsl:template match="properties[@xsi:type='designtime:ObjectProperty' and @name='BackingStore']" />		<!--  variant used in UI -->
	<xsl:template match="properties[@xsi:type='designtime:ObjectProperty' and @name='Cache']" />
	
	<!--  Match individual properties. This is redundant in most cases. -->
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Type Name']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Table Name']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='hasBackingStore']" />	
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Has Backing Store']" />	<!--  variant used in UI -->
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Pre Load On Recovery [true | false]']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Preload On Recovery [true | false]']" />	<!--  variant used in UI -->
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Maximum Records to Load On Recovery [Set 0 for All]']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Maximum Records to Load on Recovery [Set 0 for All]']" />	<!--  variant used in UI -->
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Check for Version [true | false]']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Is Cache Limited[true | false]']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Is Cache Limited [true | false]']" />	<!--  variant used in UI -->
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Evict From Cache on Update [true | false]']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Constant [true | false]']" />

	<!--  Match extended properties of properties -->
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='index']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Column Name']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Max Length']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Nested Table Name']" />
	<xsl:template match="properties[@xsi:type='designtime:SimpleProperty' and @name='Reverse Ref']" />

</xsl:stylesheet>
