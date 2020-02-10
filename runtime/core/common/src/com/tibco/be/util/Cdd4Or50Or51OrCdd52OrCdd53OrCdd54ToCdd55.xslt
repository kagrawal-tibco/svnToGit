<xsl:stylesheet
        version="1.0"
        xmlns="http://tibco.com/businessevents/configuration/5.5"
        xmlns:cdd4="http://tibco.com/businessevents/configuration/4.0.0/2009/11/25"
        xmlns:cdd50="http://tibco.com/businessevents/configuration/5.0"
        xmlns:cdd51="http://tibco.com/businessevents/configuration/5.1"
        xmlns:cdd52="http://tibco.com/businessevents/configuration/5.2"
        xmlns:cdd53="http://tibco.com/businessevents/configuration/5.3"
		xmlns:cdd54="http://tibco.com/businessevents/configuration/5.4"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        exclude-result-prefixes="cdd4 cdd50 xsl">

    <xsl:output method="xml" indent="yes"/>

    <xsl:variable name="cdd4" select="'http://tibco.com/businessevents/configuration/4.0.0/2009/11/25'"/>
    <xsl:variable name="cdd50" select="'http://tibco.com/businessevents/configuration/5.0'"/>


    <xsl:template match="comment()|processing-instruction()">
        <xsl:copy>
            <xsl:apply-templates/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="cdd4:cluster | cdd50:cluster ">
        <cluster>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </cluster>
    </xsl:template>

    <xsl:template
            match="cdd4:cache-manager/cdd4:backing-store/cdd4:enabled|cdd50:cache-manager/cdd50:backing-store/cdd50:enabled">
        <xsl:choose>
            <xsl:when test="normalize-space(text()) = 'true'">
                <persistence-option>Shared All</persistence-option>
            </xsl:when>
            <xsl:otherwise>
                <persistence-option>None</persistence-option>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="*">
        <xsl:choose>
            <xsl:when test="(namespace-uri() = $cdd4) or (namespace-uri() = $cdd50)">
                <xsl:element name="{local-name()}">
                    <xsl:apply-templates select="@*" mode="args"/>
                    <xsl:apply-templates/>
                    <xsl:apply-templates select="." mode="add"/>
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

    <xsl:template match="cdd4:cache-manager/cdd4:backing-store|cdd50:cache-manager/cdd50:backing-store" mode="add">
        <data-store-path/>
        <persistence-policy>ASYNC</persistence-policy>
    </xsl:template>

    <xsl:template match="*" mode="add" />


</xsl:stylesheet>