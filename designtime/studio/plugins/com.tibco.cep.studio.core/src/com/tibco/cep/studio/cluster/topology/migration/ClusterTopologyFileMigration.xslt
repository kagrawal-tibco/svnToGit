
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
xmlns:CurrentVersion = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd" exclude-result-prefixes="CurrentVersion">
<xsl:param name="arg1"/>

	<xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>


    <xsl:template match="//clusters/cluster/run-version/be-runtime">

        <xsl:variable name="CurrentVersion">

            <xsl:value-of select="."/>

        </xsl:variable>

        <xsl:element xmlns = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd" name="be-runtime">

            <xsl:attribute xmlns = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd" name="version" ><xsl:value-of select="$arg1"/> </xsl:attribute></xsl:element>

    </xsl:template>
  
    <xsl:template match="//host-resources/host-resource/software/be/@version">

        <xsl:variable name="CurrentVersion">
           	<xsl:value-of select="."/>
        </xsl:variable>
        
        <xsl:attribute xmlns = "http://www.tibco.com/businessevents/BEMM/2009/09/BEMMTopology.xsd" name="version">
      		<xsl:value-of select="$arg1"/>
   		</xsl:attribute>
  		
    </xsl:template>
    
   	<xsl:template match="//host-resources/host-resource/software/be">
   		<xsl:copy>
      		<xsl:apply-templates select="@*" />
      		<xsl:apply-templates select="node()"/>
     	</xsl:copy>
   	</xsl:template>

</xsl:stylesheet>