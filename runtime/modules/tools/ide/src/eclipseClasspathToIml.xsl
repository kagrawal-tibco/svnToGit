<xsl:stylesheet
        version="1.0"
        xmlns:str="http://exslt.org/strings"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml" indent="yes"/>

    <xsl:param name="libraries"/>
    <xsl:param name="modules"/>

    <xsl:template match="/classpath">
        <module type="JAVA_MODULE" version="4">
            <component name="NewModuleRootManager" inherit-compiler-output="false">
                <xsl:variable name="outputPath">
                    <xsl:value-of select="classpathentry[@kind='output']/@path"/>
                </xsl:variable>
                <output>
                    <xsl:attribute name="url">
                        <xsl:choose>
                            <xsl:when test="string-length($outputPath) > 0">
                                <xsl:value-of
                                        select="concat('file://$MODULE_DIR$/',$outputPath)"/>
                            </xsl:when>
                            <xsl:otherwise>file://$MODULE_DIR$/classes</xsl:otherwise>
                        </xsl:choose>
                    </xsl:attribute>
                </output>
                <output-test url="file://$MODULE_DIR$/test"/>
                <exclude-output/>
                <content url="file://$MODULE_DIR$">
                    <xsl:apply-templates select="classpathentry" mode="src"/>
                </content>
                <orderEntry type="inheritedJdk"/>
                <orderEntry type="sourceFolder" forTests="false"/>
                <xsl:apply-templates select="classpathentry" mode="library"/>
                <xsl:apply-templates select="classpathentry" mode="module"/>
                <xsl:apply-templates select="classpathentry" mode="module-library"/>
                <xsl:call-template name="extra-libraries">
                    <xsl:with-param name="list" select="$libraries"/>
                </xsl:call-template>
                <xsl:call-template name="extra-modules">
                    <xsl:with-param name="list" select="$modules"/>
                </xsl:call-template>
            </component>
        </module>
    </xsl:template>

    <xsl:template
            mode="library"
            match="classpathentry[@kind='con' and substring(@path,1,29)='org.eclipse.jdt.USER_LIBRARY/']">
        <orderEntry type="library" exported="" name="{substring(@path, 30)}" level="project"/>
    </xsl:template>

    <!--<xsl:template-->
            <!--mode="library"-->
            <!--match="classpathentry[@kind='con' and @path='org.eclipse.pde.core.requiredPlugins']">-->
        <!--<orderEntry type="library" exported="" name="TSI-EXTERNAL-ECLIPSE-3.7" level="project"/>-->
    <!--</xsl:template>-->

    <xsl:template
            mode="module"
            match="classpathentry[@kind='src' and substring(@path,1,1)='/']">
        <orderEntry type="module" exported="" module-name="{substring(@path, 2)}"/>
    </xsl:template>

    <xsl:template
            mode="module-library"
            match="classpathentry[@kind='lib' and substring(@path,1,2)!='R:']">
        <orderEntry type="module-library" exported="">
            <library>
                <CLASSES>
                    <root url="jar://$MODULE_DIR$/{@path}!/"/>
                </CLASSES>
                <JAVADOC/>
                <SOURCES/>
            </library>
        </orderEntry>
    </xsl:template>

    <xsl:template
            mode="module-library"
            match="classpathentry[@kind='lib' and substring(@path,1,3)='R:/']">
        <xsl:variable name="path1">
            <xsl:call-template name="replace">
                <xsl:with-param name="string" select="substring(@path, 4)"/>
                <xsl:with-param name="search" select="'/win/x86_64/'"/>
                <xsl:with-param name="replacement" select="'/$PLATFORM$/'"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="path2">
            <xsl:call-template name="replace">
                <xsl:with-param name="string" select="$path1"/>
                <xsl:with-param name="search" select="'/win/x86/'"/>
                <xsl:with-param name="replacement" select="'/$PLATFORM$/'"/>
            </xsl:call-template>
        </xsl:variable>
        <orderEntry type="module-library" exported="">
            <library>
                <CLASSES>
                    <root url="jar:///R/{$path2}!/"/>
                </CLASSES>
                <JAVADOC/>
                <SOURCES/>
            </library>
        </orderEntry>
    </xsl:template>

    <xsl:template
            mode="src"
            match="classpathentry[@kind='src' and substring(@path,1,1)!='/']">
        <sourceFolder>
            <xsl:attribute name="url">file://$MODULE_DIR$/<xsl:value-of select="@path"/></xsl:attribute>
            <xsl:attribute name="isTestSource">false</xsl:attribute>
        </sourceFolder>
    </xsl:template>

    <xsl:template name="extra-libraries">
        <xsl:param name="list"/>
        <xsl:for-each select="str:tokenize($list, ',')">
            <orderEntry type="library" exported="" name="{text()}" level="project"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="extra-modules">
        <xsl:param name="list"/>
        <xsl:for-each select="str:tokenize($list, ',')">
            <orderEntry type="module" exported="" module-name="{text()}"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="*|@*|text()"/>


    <xsl:template name="replace">
        <xsl:param name="string"/>
        <xsl:param name="search"/>
        <xsl:param name="replacement"/>
        <xsl:variable name="before" select="substring-before($string, $search)"/>
        <xsl:choose>
            <xsl:when test="$before">
                <xsl:value-of select="concat($before, $replacement)" />
                <xsl:call-template name="replace">
                    <xsl:with-param name="string" select="substring-after($string, $search)"/>
                    <xsl:with-param name="search" select="$search"/>
                    <xsl:with-param name="replacement" select="replacement"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$string"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>