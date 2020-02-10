<?xml version="1.0"?>

<xsl:stylesheet
        version="1.0"
        xmlns="http://tibco.com/businessevents/configuration/5.0"
        xmlns:archive="http:///com/tibco/cep/designtime/core/model/archive"
        xmlns:java="java"
        xmlns:tmp="http://tibco.com/businessevents/migration/ArchiveToCdd/tmp"
        xmlns:xalan="http://xml.apache.org/xalan"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        exclude-result-prefixes="archive cdd java tmp xalan xsl">


    <xsl:strip-space elements="*"/>
    <xsl:output method="xml" indent="yes"/>


    <xsl:variable
            name="now"
            select="java:format(java:text.SimpleDateFormat.new('yyyy-MM-dd HH:mm:ss'), java:util.Date.new())"/>

    <xsl:variable
            name="is-om-cache"
            select="count(//omEnable[text() = 'tangosol']) > 0"/>

    <xsl:variable
            name="is-om-bdb"
            select="not($is-om-cache) and (count(//omEnable[text() = 'bdb']) > 0)"/>

    <xsl:variable
            name="is-om-memory"
            select="not($is-om-cache or $is-om-bdb)"/>


    <xsl:template match="/">
        <xsl:apply-templates select="//enterpriseArchive"/>
        <xsl:apply-templates select="//archive:EnterpriseArchive"/>
    </xsl:template>


    <xsl:template match="enterpriseArchive">
        <xsl:call-template name="archive"/>
    </xsl:template>


    <xsl:template match="archive:EnterpriseArchive">
        <xsl:call-template name="archive"/>
    </xsl:template>


    <xsl:template name="archive">
        <cluster>
            <xsl:call-template name="revision"/>
            <xsl:call-template name="cluster-name"/>
            <xsl:call-template name="om"/>
            <rulesets/>
            <function-groups/>
            <destination-groups/>
            <xsl:call-template name="log-configs"/>
            <xsl:call-template name="agent-classes"/>
            <xsl:call-template name="processing-units"/>
            <xsl:call-template name="misc-props"/>
        </cluster>
    </xsl:template>


    <xsl:template match="*">
        <xsl:comment>
            <xsl:value-of select="concat('Ignored node: ', name())"/>
        </xsl:comment>
    </xsl:template>


    <xsl:template name="agent-classes">
        <agent-classes>
            <xsl:if test="$is-om-cache">
                <cache-agent-class id="cache-class"/>
            </xsl:if>
            <xsl:apply-templates select="BusinessEventsArchive" mode="agent-class"/>
            <xsl:apply-templates select="businessEventsArchives" mode="agent-class"/>
        </agent-classes>
    </xsl:template>


    <xsl:template name="agent-name-normalizer">
        <xsl:param name="be-archive"/>
        <xsl:variable name="base-name">
            <xsl:choose>
                <xsl:when test="$is-om-cache">
                    <xsl:value-of select="omtgGlobalCache"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="@name"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="escaped-name">
            <xsl:value-of select="translate($base-name, ' /\:;,?`~!@#$%^*()=+{}[]|&amp;&quot;&lt;&gt;', '_____________________________')"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when
                    test="translate(substring($escaped-name, 1, 1), 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_', 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx') = 'x'">
                <xsl:value-of select="$escaped-name"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat('_', $escaped-name)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template match="businessEventsArchives" mode="agent-class" name="businessEventsArchive-agent-class">
        <xsl:call-template name="BusinessEventsArchive-agent-class"/>
    </xsl:template>


    <xsl:template match="BusinessEventsArchive" mode="agent-class" name="BusinessEventsArchive-agent-class">
        <xsl:choose>
            <xsl:when test="archiveType = 'QUERY'">
                <xsl:call-template name="query-agent-class"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="inference-agent-class"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template match="businessEventsArchives" mode="processing-unit" name="businessEventsArchive-processing-unit">
        <xsl:call-template name="BusinessEventsArchive-processing-unit"/>
    </xsl:template>


    <xsl:template match="BusinessEventsArchive" mode="processing-unit" name="BusinessEventsArchive-processing-unit">
        <agent>
            <ref>
                <xsl:call-template name="agent-name-normalizer">
                    <xsl:with-param name="be-archive" select="."/>
                </xsl:call-template>
            </ref>
            <!--<key><xsl:value-of select="concat('agent-key-', generate-id())"/></key>-->
        </agent>
    </xsl:template>


    <xsl:template name="cluster-name">
        <name>
            <xsl:value-of select="name"/>
        </name>
    </xsl:template>


    <xsl:template name="domain-objects">
        <xsl:variable name="all-settings">
            <tmp:all-settings>
                <xsl:for-each select="//omtgAdvancedEntitySettings[1]">
                    <xsl:call-template name="entity-settings-converter">
                        <xsl:with-param name="text" select="text()"/>
                    </xsl:call-template>
                </xsl:for-each>
            </tmp:all-settings>
        </xsl:variable>
        <xsl:for-each select="xalan:nodeset($all-settings)//tmp:entity[not(uri=preceding-sibling::tmp:entity/tmp:uri)]">
            <xsl:sort select="tmp:uri" data-type="text" order="ascending"/>
            <xsl:variable name="uri" select="tmp:uri"/>
            <domain-object>
                <uri>
                    <xsl:value-of select="$uri"/>
                </uri>
                <mode>
                    <xsl:choose>
                        <xsl:when
                                test="count(xalan:nodeset($all-settings)//tmp:entity[tmp:uri=$uri and tmp:cache-mode='cacheAndMemory']) > 0">
                            <xsl:text>cacheAndMemory</xsl:text>
                        </xsl:when>
                        <xsl:when
                                test="count(xalan:nodeset($all-settings)//tmp:entity[tmp:uri=$uri and tmp:cache-mode='cache']) > 0">
                            <xsl:text>cache</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text>memory</xsl:text>
                        </xsl:otherwise>
                    </xsl:choose>
                </mode>
                <xsl:if test="tmp:recovery-function/text()">
                    <pre-processor>
                        <xsl:value-of select="tmp:recovery-function"/>
                    </pre-processor>
                </xsl:if>
                <xsl:if test="tmp:subscribe/text()">
                    <subscribe>
                        <xsl:value-of select="tmp:subscribe"/>
                    </subscribe>
                </xsl:if>
            </domain-object>
        </xsl:for-each>
    </xsl:template>


    <xsl:template name="entity-settings-converter">
        <xsl:param name="text"/>
        <tmp:entity-settings>
            <xsl:call-template name="entity-settings-converter-inner">
                <xsl:with-param name="text" select="$text"/>
            </xsl:call-template>
        </tmp:entity-settings>
    </xsl:template>


    <xsl:template name="entity-settings-converter-inner">
        <xsl:param name="text"/>
        <xsl:if test="$text">
            <xsl:variable name="after-row-start" select="substring-after($text,'&lt;row&gt;')"/>
            <xsl:if test="$after-row-start">
                <tmp:entity>
                    <xsl:variable name="row" select="substring-before($after-row-start, '&lt;/row&gt;')"/>
                    <tmp:uri>
                        <xsl:value-of select="substring-before(substring-after($row,'&lt;uri&gt;'), '&lt;/uri&gt;')"/>
                    </tmp:uri>
                    <tmp:deployed>
                        <xsl:value-of
                                select="substring-before(substring-after($row,'&lt;deployed&gt;'), '&lt;/deployed&gt;')"/>
                    </tmp:deployed>
                    <tmp:cache-mode>
                        <xsl:value-of
                                select="substring-before(substring-after($row,'&lt;cacheMode&gt;'), '&lt;/cacheMode&gt;')"/>
                    </tmp:cache-mode>
                    <tmp:recovery-function>
                        <xsl:value-of
                                select="substring-before(substring-after($row,'&lt;recoveryFunction&gt;'), '&lt;/recoveryFunction&gt;')"/>
                    </tmp:recovery-function>
                    <tmp:subscribe>
                        <xsl:value-of
                                select="substring-before(substring-after($row,'&lt;subscribe&gt;'), '&lt;/subscribe&gt;')"/>
                    </tmp:subscribe>
                </tmp:entity>
                <xsl:call-template name="entity-settings-converter-inner">
                    <xsl:with-param name="text" select="substring-after($after-row-start,'&lt;/row&gt;')"/>
                </xsl:call-template>
            </xsl:if>
        </xsl:if>
    </xsl:template>


    <xsl:template name="inference-agent-class">
        <inference-agent-class>
            <xsl:attribute name="id">
                <xsl:call-template name="agent-name-normalizer">
                    <xsl:with-param name="be-archive" select="."/>
                </xsl:call-template>
            </xsl:attribute>
            <xsl:call-template name="rules"/>
            <xsl:call-template name="inputDestinations"/>
            <xsl:call-template name="startup"/>
            <xsl:call-template name="shutdown"/>
            <concurrent-rtc>false</concurrent-rtc>
        </inference-agent-class>
    </xsl:template>


    <xsl:template name="inputDestinations">
        <destinations>
            <!--<xsl:comment>-->
            <!--<xsl:value-of select="concat('destinationsRuleFunctions: ', destinationsRuleFunctions)"/>-->
            <!--</xsl:comment>-->
            <!--<xsl:comment>-->
            <!--<xsl:value-of select="concat('destinationsqueuesize: ', destinationsqueuesize)"/>-->
            <!--</xsl:comment>-->
            <!--<xsl:comment>-->
            <!--<xsl:value-of select="concat('destinationsqueueweight: ', destinationsqueueweight)"/>-->
            <!--</xsl:comment>-->
            <!--<xsl:comment>-->
            <!--<xsl:value-of select="concat('destinationsInputEnabled: ', destinationsInputEnabled)"/>-->
            <!--</xsl:comment>-->
            <!--<xsl:comment>-->
            <!--<xsl:value-of select="concat('destinationsworkers     : ', destinationsworkers)"/>-->
            <!--</xsl:comment>-->
            <xsl:variable name="prepro-map">
                <xsl:call-template name="map-decoder">
                    <xsl:with-param name="encoded-map" select="destinationsRuleFunctions"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:variable name="queue-size-map">
                <xsl:call-template name="map-decoder">
                    <xsl:with-param name="encoded-map" select="destinationsqueuesize"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:variable name="queue-weight-map">
                <xsl:call-template name="map-decoder">
                    <xsl:with-param name="encoded-map" select="destinationsqueueweight"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:variable name="uri-map">
                <xsl:call-template name="map-decoder">
                    <xsl:with-param name="encoded-map" select="destinationsInputEnabled"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:variable name="workers-map">
                <xsl:call-template name="map-decoder">
                    <xsl:with-param name="encoded-map" select="destinationsworkers"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:for-each select="xalan:nodeset($uri-map)//tmp:name"><!-- Merges the data -->
                <xsl:variable name="uri" select="text()"/>
                <xsl:if test="string-length($uri) > 1">
                    <xsl:variable name="destination-name">
                        <xsl:call-template name="get-name-from-path">
                            <xsl:with-param name="path" select="$uri"/>
                            <xsl:with-param name="separator" select="'/'"/>
                        </xsl:call-template>
                    </xsl:variable>
                    <destination>
                        <xsl:attribute name="id"><xsl:value-of select="concat($destination-name, '-', generate-id(.))"/></xsl:attribute>
                        <uri>
                            <xsl:call-template name="replace-all">
                                <xsl:with-param name="text" select="$uri"/>
                                <xsl:with-param name="replaced" select="'.channel'"/>
                                <xsl:with-param name="replacing" select="''"/>
                            </xsl:call-template>
                        </uri>
                        <xsl:variable name="pre-processor" select="xalan:nodeset($prepro-map)//tmp:entry[tmp:name = $uri]"/>
                        <xsl:if test="$pre-processor">
                            <pre-processor>
                                <xsl:value-of select="xalan:nodeset($pre-processor)/tmp:value"/>
                            </pre-processor>
                        </xsl:if>
                        <xsl:variable name="workers" select="xalan:nodeset($workers-map)//tmp:entry[tmp:name = $uri]"/>
                        <xsl:if test="$workers">
                            <xsl:variable name="num-workers" select="xalan:nodeset($workers)/tmp:value"/>
                            <xsl:choose>
                                <xsl:when test="$num-workers &lt; 0">
                                    <threading-model>
                                        <xsl:text>caller</xsl:text>
                                    </threading-model>
                                </xsl:when>
                                <xsl:when test="$num-workers = 0">
                                    <threading-model>
                                        <xsl:text>shared-queue</xsl:text>
                                    </threading-model>
                                </xsl:when>
                                <xsl:otherwise>
                                    <threading-model>
                                        <xsl:text>workers</xsl:text>
                                    </threading-model>
                                    <thread-count>
                                        <xsl:value-of select="$num-workers"/>
                                    </thread-count>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:if>
                        <xsl:variable name="queue-size" select="xalan:nodeset($queue-size-map)//tmp:entry[tmp:name = $uri]"/>
                        <xsl:if test="$queue-size and (xalan:nodeset($queue-size)/tmp:value >= 0)">
                            <queue-size>
                                <xsl:value-of select="xalan:nodeset($queue-size)/tmp:value"/>
                            </queue-size>
                        </xsl:if>
                    </destination>
                </xsl:if>
            </xsl:for-each>
        </destinations>
    </xsl:template>


    <xsl:template name="log-configs">
        <log-configs>
            <log-config id="logConfig">
                <enabled>true</enabled>
                <roles>*:info</roles>
                <files>
                    <enabled>true</enabled>
                    <dir>logs</dir>
                    <max-number>10</max-number>
                    <max-size>10000000</max-size>
                    <append>true</append>
                </files>
            </log-config>
        </log-configs>
    </xsl:template>


    <xsl:template name="map-decoder">
        <xsl:param name="encoded-map"/>
        <tmp:map>
            <xsl:call-template name="map-decoder-inner">
                <xsl:with-param name="encoded-map" select="$encoded-map"/>
            </xsl:call-template>
        </tmp:map>
    </xsl:template>


    <xsl:template name="map-decoder-inner">
        <xsl:param name="encoded-map"/>
        <xsl:variable name="encoded-entry">
            <xsl:choose>
                <xsl:when test="contains($encoded-map, ',')">
                    <xsl:value-of select="substring-before($encoded-map, ',')"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$encoded-map"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:variable>
        <xsl:variable name="decoded-entry">
            <xsl:value-of select="java:net.URLDecoder.decode($encoded-entry, 'UTF-16BE')"/>
        </xsl:variable>
        <tmp:entry>
            <xsl:choose>
                <xsl:when test="contains($decoded-entry, '=')">
                    <tmp:name>
                        <xsl:value-of select="substring-before($decoded-entry, '=')"/>
                    </tmp:name>
                    <tmp:value>
                        <xsl:value-of select="substring-after($decoded-entry, '=')"/>
                    </tmp:value>
                </xsl:when>
                <xsl:otherwise>
                    <tmp:name>
                        <xsl:value-of select="$decoded-entry"/>
                    </tmp:name>
                    <tmp:value/>
                </xsl:otherwise>
            </xsl:choose>
        </tmp:entry>
        <xsl:variable name="after-separator" select="substring-after($encoded-map, ',')"/>
        <xsl:if test="$after-separator">
            <xsl:call-template name="map-decoder-inner">
                <xsl:with-param name="encoded-map" select="$after-separator"/>
            </xsl:call-template>
        </xsl:if>
    </xsl:template>


    <xsl:template name="misc-props">
        <property-group name="misc">
        </property-group>
    </xsl:template>


    <xsl:template name="om">
        <object-management>
            <xsl:choose>
                <xsl:when test="$is-om-cache">
                    <cache-manager>
                        <provider>
                            <name>TIBCO</name>
                        </provider>
                        <domain-objects>
                            <default-mode>cacheAndMemory</default-mode>
                            <xsl:call-template name="domain-objects"/>
                        </domain-objects>
                    </cache-manager>
                </xsl:when>
                <xsl:when test="$is-om-bdb">
                    <berkeley-db-manager>
                        <checkpoint-interval>
                            <xsl:value-of select="BusinessEventsArchive/omCheckPtInterval"/>
                        </checkpoint-interval>
                        <checkpoint-ops-limit>
                            <xsl:value-of select="BusinessEventsArchive/omCheckPtOpsLimit"/>
                        </checkpoint-ops-limit>
                        <db-dir>
                            <xsl:value-of select="BusinessEventsArchive/omDbEnvDir"/>
                        </db-dir>
                        <delete-retracted>
                            <xsl:value-of select="BusinessEventsArchive/omDeletePolicy"/>
                        </delete-retracted>
                        <skip-recovery>
                            <xsl:value-of select="BusinessEventsArchive/omNoRecovery"/>
                        </skip-recovery>
                        <property-cache-size>
                            <xsl:value-of select="BusinessEventsArchive/omPropCacheSize"/>
                        </property-cache-size>
                    </berkeley-db-manager>
                </xsl:when>
                <xsl:otherwise>
                    <memory-manager/>
                </xsl:otherwise>
            </xsl:choose>
        </object-management>
    </xsl:template>


    <xsl:template name="processing-units">
        <processing-units>
            <xsl:if test="$is-om-cache">
                <processing-unit id="cache">
                    <agents>
                        <agent>
                            <ref>cache-class</ref>
                        </agent>
                    </agents>
                    <logs>logConfig</logs>
                </processing-unit>
            </xsl:if>
            <processing-unit id="default">
                <agents>
                    <xsl:apply-templates select="BusinessEventsArchive" mode="processing-unit"/>
                    <xsl:apply-templates select="businessEventsArchives" mode="processing-unit"/>
                </agents>
                <cache-storage-enabled>false</cache-storage-enabled>
                <logs>logConfig</logs>
            </processing-unit>
        </processing-units>
    </xsl:template>


    <xsl:template name="query-agent-class">
        <query-agent-class>
            <xsl:attribute name="id">
                <xsl:call-template name="agent-name-normalizer">
                    <xsl:with-param name="be-archive" select="."/>
                </xsl:call-template>
            </xsl:attribute>
            <xsl:call-template name="inputDestinations"/>
            <xsl:call-template name="startup"/>
            <xsl:call-template name="shutdown"/>
        </query-agent-class>
    </xsl:template>


    <xsl:template name="revision">
        <revision>
            <version>
                <xsl:choose>
                    <xsl:when test="string(number(versionProperty))='NaN'">
                        <xsl:value-of select="versionProperty"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="1 + number(versionProperty)"/>
                    </xsl:otherwise>
                </xsl:choose>
            </version>
            <author>
                <xsl:value-of select="authorProperty"/>
            </author>
            <date>
                <xsl:value-of select="$now"/>
            </date>
            <comment>
                <xsl:value-of select="concat('Automatic import of version ', versionProperty, '. ')"/>
                <xsl:value-of select="concat('EAR file location was set to: ', fileLocationProperty)"/>
            </comment>
        </revision>
    </xsl:template>


    <xsl:template name="rules">
        <rules>
            <xsl:choose>
                <xsl:when test="includeAllRuleSets = 'true'">
                    <uri>/</uri>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates select="ruleSets" mode="rulesets"/>
                </xsl:otherwise>
            </xsl:choose>
        </rules>
    </xsl:template>


    <xsl:template match="ruleSets" mode="rulesets">
        <xsl:if test="string-length(text()) > 0">
            <xsl:variable name="rulesets">
                <xsl:call-template name="map-decoder">
                    <xsl:with-param name="encoded-map" select="text()"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:for-each select="xalan:nodeset($rulesets)//tmp:entry/tmp:name">
                <xsl:call-template name="ruleset"/>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>


    <xsl:template name="ruleset">
        <uri>
            <xsl:choose>
                <xsl:when test="contains(text(), '.')">
                    <xsl:value-of select="substring-before(text(), '.')"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="string-length(text()) > 1">
                        <xsl:value-of select="text()"/>
                    </xsl:if>
                </xsl:otherwise>
            </xsl:choose>
        </uri>
    </xsl:template>


    <xsl:template name="shutdown">
        <xsl:variable name="functions">
            <xsl:call-template name="map-decoder">
                <xsl:with-param name="encoded-map" select="shutdown"/>
            </xsl:call-template>
        </xsl:variable>
        <shutdown>
            <xsl:for-each select="xalan:nodeset($functions)//tmp:entry">
                <xsl:if test="string-length(tmp:name) > 1">
                    <uri>
                        <xsl:value-of select="tmp:name"/>
                    </uri>
                </xsl:if>
            </xsl:for-each>
        </shutdown>
    </xsl:template>


    <xsl:template name="startup">
        <xsl:variable name="functions">
            <xsl:call-template name="map-decoder">
                <xsl:with-param name="encoded-map" select="startup"/>
            </xsl:call-template>
        </xsl:variable>
        <startup>
            <xsl:for-each select="xalan:nodeset($functions)//tmp:entry">
                <xsl:if test="string-length(tmp:name) > 1">
                    <uri>
                        <xsl:value-of select="tmp:name"/>
                    </uri>
                </xsl:if>
            </xsl:for-each>
        </startup>
    </xsl:template>


    <xsl:template name="get-name-from-path">
        <xsl:param name="path"/>
        <xsl:param name="separator"/>
        <xsl:variable name="sub-path" select="substring-after($path,$separator)"/>
        <xsl:choose>
            <xsl:when test="$sub-path">
                <xsl:call-template name="get-name-from-path">
                    <xsl:with-param name="path" select="$sub-path"/>
                    <xsl:with-param name="separator" select="$separator"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$path"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    
    <xsl:template name="replace-all">
        <xsl:param name="text"/>
        <xsl:param name="replaced"/>
        <xsl:param name="replacing"/>
        <xsl:choose>
            <xsl:when test="contains($text, $replaced)">
                <xsl:value-of select="substring-before($text,$replaced)"/>
                <xsl:value-of select="$replacing"/>
                <xsl:call-template name="replace-all">
                    <xsl:with-param name="text" select="substring-after($text,$replaced)"/>
                    <xsl:with-param name="replaced" select="$replaced"/>
                    <xsl:with-param name="replacing" select="$replacing"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$text"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


</xsl:stylesheet>
