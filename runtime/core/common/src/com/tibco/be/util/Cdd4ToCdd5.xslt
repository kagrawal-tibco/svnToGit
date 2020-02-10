<xsl:stylesheet
        version="1.0"
        xmlns="http://tibco.com/businessevents/configuration/5.0"
        xmlns:cdd4="http://tibco.com/businessevents/configuration/4.0.0/2009/11/25"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        exclude-result-prefixes="cdd4 xsl">

    <xsl:output method="xml" indent="yes"/>

    <xsl:variable name="cdd4" select="'http://tibco.com/businessevents/configuration/4.0.0/2009/11/25'"/>


    <xsl:template match="comment()|processing-instruction()">
        <xsl:copy>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="cdd4:cluster">
        <cluster>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </cluster>
    </xsl:template>

    <xsl:template match="*">
        <xsl:choose>
            <xsl:when test="namespace-uri() = $cdd4">
                <xsl:element name="{local-name()}">
                    <xsl:apply-templates select="@*" mode="args"/>
                    <xsl:apply-templates/>
                </xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:copy-of select="@*"/>
                    <xsl:apply-templates/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="cdd4:property/@name[.='tangosol.coherence.distributed.localstorage']" mode="args">
        <xsl:attribute name="{name()}">be.engine.cluster.isSeeder</xsl:attribute>
    </xsl:template>

    <xsl:template match="@*" mode="args">
        <xsl:copy-of select="."/>
    </xsl:template>

</xsl:stylesheet>