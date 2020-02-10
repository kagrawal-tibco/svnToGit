<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:aems="http://www.tibco.com/xmlns/aemeta/services/2002"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />

    <xsl:template match="General">
        <description>
            <xsl:value-of select="Description"/>
        </description>
	</xsl:template>

    <xsl:template match="Connection">
        <daemon>
            <xsl:value-of select="Daemon"/>
        </daemon>
        <network>
            <xsl:value-of select="Network"/>
        </network>
        <service>
            <xsl:value-of select="Service"/>
        </service>
        <xsl:choose>
            <xsl:when test="aems:ssl">
                <useSsl>true</useSsl>
                <xsl:copy-of select="aems:ssl"/>
            </xsl:when>
            <xsl:otherwise>
                <useSsl>false</useSsl>
                <aems:ssl/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="Certified">
        <workerWeight>1</workerWeight>
        <workerTasks>10</workerTasks>
        <workerCompleteTime>0</workerCompleteTime>
        <schedulerWeight>1</schedulerWeight>
        <scheduleHeartbeat>1.0</scheduleHeartbeat>
        <scheduleActivation>3.5</scheduleActivation>
        <showExpertSettings>certified</showExpertSettings>
        <xsl:apply-templates select="../Connection"/>
        <cmName>
            <xsl:value-of select="CmName"/>
        </cmName>
        <ledgerFile>
            <xsl:value-of select="LedgerFile"/>
        </ledgerFile>
        <syncLedger>
            <xsl:value-of select="SyncLedgerFile"/>
        </syncLedger>
        <requireOld>
            <xsl:value-of select="RequireOldMessage"/>
        </requireOld>
        <relayAgent>
            <xsl:value-of select="RelayAgent"/>
        </relayAgent>
        <operationTimeout>
            <xsl:value-of select="MessageTimeout"/>
        </operationTimeout>
    </xsl:template>

    <xsl:template match="DistributedQueue">
        <workerWeight>
            <xsl:value-of select="Advanced/WorkerWeight"/>
        </workerWeight>
        <workerTasks>
            <xsl:value-of select="WorkerTasks"/>
        </workerTasks>
        <workerCompleteTime>
            <xsl:value-of select="WorkerCompleteTime"/>
        </workerCompleteTime>
        <schedulerWeight>
            <xsl:value-of select="SchedulerWeight"/>
        </schedulerWeight>
        <scheduleHeartbeat>
            <xsl:value-of select="SchedulerHeartbeat"/>
        </scheduleHeartbeat>
        <scheduleActivation>
            <xsl:value-of select="SchedulerActivation"/>
        </scheduleActivation>
        <showExpertSettings>certifiedQ</showExpertSettings>
        <xsl:apply-templates select="../Connection"/>
    </xsl:template>

    <xsl:template match="Reliable">
        <workerWeight>1</workerWeight>
        <workerTasks>10</workerTasks>
        <workerCompleteTime>0</workerCompleteTime>
        <schedulerWeight>1</schedulerWeight>
        <scheduleHeartbeat>1.0</scheduleHeartbeat>
        <scheduleActivation>3.5</scheduleActivation>
        <showExpertSettings>reliable</showExpertSettings>
        <xsl:apply-templates select="../Connection"/>
    </xsl:template>

    <xsl:template match="/RendezVousTransport">
        <BWSharedResource>
            <config>
                <xsl:apply-templates select="General"/>
				<xsl:apply-templates select="Connection"/>
                <xsl:apply-templates select="Advanced"/>
            </config>
        </BWSharedResource>
    </xsl:template>

</xsl:stylesheet>
