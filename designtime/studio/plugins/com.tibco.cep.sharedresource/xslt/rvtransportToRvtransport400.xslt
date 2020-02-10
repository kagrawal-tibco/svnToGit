<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:in="http://tibco.com/be/shared/rv"
                xmlns:out="http://tibco.com/be/shared/rv400"
                xmlns:aems="http://www.tibco.com/xmlns/aemeta/services/2002"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  	<xsl:output method="xml" indent="yes" />
  
    <xsl:template match="/BWSharedResource/config">

        <RendezVousTransport>

            <General>
                <Description>
                    <xsl:value-of select="description"/>
                </Description>
            </General>

            <Connection>
                <Daemon>
                    <xsl:value-of select="daemon"/>
                </Daemon>
                <Network>
                    <xsl:value-of select="network"/>
                </Network>
                <Service>
                    <xsl:value-of select="service"/>
                </Service>
                <xsl:if test="useSsl = 'true'">
                    <xsl:copy-of select="aems:ssl"/>
                </xsl:if>
            </Connection>

            <Advanced>
                <xsl:choose>

                    <xsl:when test="showExpertSettings = 'certified'">
                        <Certified>
                            <CmName>
                                <xsl:value-of select="cmName"/>
                            </CmName>
                            <LedgerFile>
                                <xsl:value-of select="ledgerFile"/>
                            </LedgerFile>
                            <SyncLedgerFile>
                                <xsl:value-of select="syncLedger"/>
                            </SyncLedgerFile>
                            <RelayAgent>
                                <xsl:value-of select="relayAgent"/>
                            </RelayAgent>
                            <RequireOldMessage>
                                <xsl:value-of select="requireOld"/>
                            </RequireOldMessage>
                            <MessageTimeout>
                                <xsl:value-of select="operationTimeout"/>
                            </MessageTimeout>
                        </Certified>
                    </xsl:when>

                    <xsl:when test="showExpertSettings = 'certifiedQ'">
                        <DistributedQueue>
                            <CmqName>
                                <xsl:value-of select="cmqName"/>
                            </CmqName>
                            <WorkerWeight>
                                <xsl:value-of select="workerWeight"/>
                            </WorkerWeight>
                            <WorkerTasks>
                                <xsl:value-of select="workerTasks"/>
                            </WorkerTasks>
                            <WorkerCompleteTime>
                                <xsl:value-of select="workerCompleteTime"/>
                            </WorkerCompleteTime>
                            <SchedulerWeight>
                                <xsl:value-of select="schedulerWeight"/>
                            </SchedulerWeight>
                            <SchedulerHeartbeat>
                                <xsl:value-of select="scheduleHeartbeat"/>
                            </SchedulerHeartbeat>
                            <SchedulerActivation>
                                <xsl:value-of select="scheduleActivation"/>
                            </SchedulerActivation>
                        </DistributedQueue>
                    </xsl:when>

                    <xsl:otherwise>
                        <Reliable/>
                    </xsl:otherwise>
                    
                </xsl:choose>
            </Advanced>

        </RendezVousTransport>

    </xsl:template>

</xsl:stylesheet>
