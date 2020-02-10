<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:bpmn="http://schema.omg.org/spec/BPMN/2.0" xmlns="http://schema.omg.org/spec/BPMN/2.0">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<!-- BPMN 2.0 Transformation: XMI-to-XML -->

	<xsl:template name="activityResourceTemplate">
		<activityResource>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</activityResource>
	</xsl:template>

	<xsl:template name="adHocSubProcessTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:AdHocSubProcess'">
		<adHocSubProcess>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="triggeredByEvent"> <xsl:value-of select="./@triggeredByEvent"/> </xsl:attribute>
			<xsl:attribute name="cancelRemainingInstances"> <xsl:value-of select="boolean(./@cancelRemainingInstances)"/> </xsl:attribute>
			<xsl:attribute name="ordering"> <xsl:value-of select="./@ordering"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
			<xsl:for-each select="flowElements"><xsl:call-template name="flowElementTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
			<xsl:for-each select="completionCondition"><completionCondition><xsl:call-template name="formalExpressionTemplate2"/></completionCondition></xsl:for-each>
		</adHocSubProcess>
		</xsl:if>
	</xsl:template>

	<xsl:template name="artifactTemplate">
		<xsl:call-template name="associationTemplate"/>
		<xsl:call-template name="groupTemplate"/>
		<xsl:call-template name="textAnnotationTemplate"/>
	</xsl:template>	

	<xsl:template name="assignmentTemplate">
		<assignment>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="language"> <xsl:value-of select="./@language"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<from><xsl:value-of select="./@from"/></from>
			<to><xsl:value-of select="./@to"/></to>
		</assignment>
	</xsl:template>

	<xsl:template name="associationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Association'">
		<association>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="sourceRef"> <xsl:value-of select="./@sourceRef"/> </xsl:attribute>
			<xsl:attribute name="targetRef"> <xsl:value-of select="./@targetRef"/> </xsl:attribute>
			<xsl:attribute name="associationDirection"> <xsl:value-of select="./@associationDirection"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</association>
		</xsl:if>
	</xsl:template>

	<xsl:template name="auditingTemplate">
		<auditing>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
		</auditing>
	</xsl:template>
	
	<xsl:template name="boundaryEventTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:BoundaryEvent'">
		<boundaryEvent>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="cancelActivity"> <xsl:value-of select="./@cancelActivity"/> </xsl:attribute>
			<xsl:attribute name="attachedToRef"> <xsl:value-of select="./@attachedToRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</boundaryEvent>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="businessRuleTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:BusinessRuleTask'">
		<businessRuleTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</businessRuleTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="callActivityTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:CallActivity'">
		<callActivity>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="calledElement"><xsl:value-of select="./@calledElement"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</callActivity>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="callChoreographyActivityTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:CallChoreographyActivity'">
		<callChoreographyActivity>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="calledElement"> <xsl:value-of select="./@calledElement"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		</callChoreographyActivity>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="callConversationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:CallConversation'">
		<callConversation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="calledElementRef"> <xsl:value-of select="./@calledElementRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		</callConversation>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="cancelEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:CancelEventDefinition'">
		<cancelEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</cancelEventDefinition>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="categoryTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Category'">
		<category>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValue"><xsl:call-template name="categoryValueTemplate"/></xsl:for-each>
		</category>
		</xsl:if>
	</xsl:template>		
	
	<xsl:template name="categoryValueTemplate">
		<categoryValue>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="value"> <xsl:value-of select="./@value"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</categoryValue>
	</xsl:template>

	<xsl:template name="choreographyTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Choreography'">
		<choreography>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="isClose"> <xsl:value-of select="boolean(./@isClosed)"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="flowElements"><xsl:call-template name="flowElementTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="participants"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="conversations"><xsl:call-template name="conversationTemplate"/></xsl:for-each>
			<xsl:for-each select="conversationAssociations"><xsl:call-template name="conversationAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlowAssociations"><xsl:call-template name="messageFlowAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="participantAssociations"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		</choreography>
		</xsl:if>
	</xsl:template>		
	
	<xsl:template name="choreographySubprocessTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ChoreographySubprocess'">
		<choreographySubprocess>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="flowElements"><xsl:call-template name="flowElementTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
		</choreographySubprocess>
		</xsl:if>
	</xsl:template>		
	
	<xsl:template name="choreographyTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ChoreographyTask'">
		<choreographyTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</choreographyTask>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="collaborationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Collaboration'">
		<collaboration>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isClosed"> <xsl:value-of select="boolean(./@isClosed)"/> </xsl:attribute>
			<xsl:attribute name="choreographyRef"> <xsl:value-of select="./@choreographyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participants"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
			<xsl:for-each select="conversation"><xsl:call-template name="conversationTemplate"/></xsl:for-each>
			<xsl:for-each select="conversationAssociation"><xsl:call-template name="conversationAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlowAssociation"><xsl:call-template name="messageFlowAssociationTemplate"/></xsl:for-each>
		</collaboration>
		</xsl:if>
	</xsl:template>			
	
	<xsl:template name="communicationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Communication'">
		<communication>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="correlationKeyRef"> <xsl:value-of select="./@correlationKeyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</communication>
		</xsl:if>
	</xsl:template>

	<xsl:template name="compensateEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:CompensateEventDefinition'">
		<compensateEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="waitForCompletion"> <xsl:value-of select="boolean(./@waitForCompletion)"/> </xsl:attribute>
			<xsl:attribute name="activityRef"> <xsl:value-of select="./@activityRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</compensateEventDefinition>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="complexBehaviorDefinitionTemplate">
		<complexBehaviorDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="condition"><xsl:call-template name="expressionTemplate2"/></xsl:for-each>
			<xsl:for-each select="event"><xsl:call-template name="eventTemplate"/></xsl:for-each>
		</complexBehaviorDefinition>
	</xsl:template>
	
	<xsl:template name="complexGatewayTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ComplexGateway'">
		<complexGateway>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="activationCondition"><activationCondition><xsl:call-template name="expressionTemplate2"/></activationCondition></xsl:for-each>
		</complexGateway>
		</xsl:if>
	</xsl:template>

	<xsl:template name="conditionalEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ConditionalEventDefinition'">
		<conditionalEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="condition"><condition><xsl:call-template name="expressionTemplate2"/></condition></xsl:for-each>
		</conditionalEventDefinition>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="conversationNodeTemplate">
		<xsl:call-template name="callConversationTemplate"/>
		<xsl:call-template name="communicationTemplate"/>
		<xsl:call-template name="subConversationTemplate"/>
	</xsl:template>

	<xsl:template name="conversationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Conversation'">
		<conversation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="conversationNodes"><xsl:call-template name="conversationNodeTemplate"/></xsl:for-each>
			<xsl:for-each select="participants"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationKeys"><xsl:call-template name="correlationKeyTemplate"/></xsl:for-each>
		</conversation>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="conversationAssociationTemplate">
		<conversationAssociation>
			<xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
			<xsl:attribute name="conversationRef"><xsl:value-of select="./@conversationRef"/></xsl:attribute>
			<xsl:attribute name="correlationKeyRef"><xsl:value-of select="./@correlationKeyRef"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</conversationAssociation>
	</xsl:template>	

	<xsl:template name="correlationKeyTemplate">
		<correlationKey>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</correlationKey>
	</xsl:template>	

	<xsl:template name="correlationPropertyTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:CorrelationProperty'">
		<correlationProperty>
			<xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationPropertyRetrievalExpressions"><xsl:call-template name="correlationPropertyRetrievalExpressionTemplate"/></xsl:for-each>
		</correlationProperty>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="correlationPropertyBindingTemplate">
		<correlationPropertyBinding>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="correlationPropertyRef"> <xsl:value-of select="./@correlationPropertyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataPath"><dataPath><xsl:call-template name="formalExpressionTemplate2"/></dataPath></xsl:for-each>
		</correlationPropertyBinding>
	</xsl:template>	

	<xsl:template name="correlationPropertyRetrievalExpressionTemplate">
		<correlationPropertyRetrievalExpression>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="messageRef"> <xsl:value-of select="./@messageRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="messagePath"><messagePath><xsl:call-template name="formalExpressionTemplate2"/></messagePath></xsl:for-each>
		</correlationPropertyRetrievalExpression>
	</xsl:template>	

	<xsl:template name="correlationSubscriptionTemplate">
		<correlationSubscription>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="process"> <xsl:value-of select="./@process"/> </xsl:attribute>
			<xsl:attribute name="correlationKeyRef"> <xsl:value-of select="./@correlationKeyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationPropertyBinding"><xsl:call-template name="correlationPropertyBindingTemplate"/></xsl:for-each>
		</correlationSubscription>
	</xsl:template>	

	<xsl:template name="dataAssociationTemplate">
		<dataAssociation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="transformation"><transformation><xsl:call-template name="formalExpressionTemplate2"/></transformation></xsl:for-each>
			<xsl:for-each select="assignments"><xsl:call-template name="assignmentTemplate"/></xsl:for-each>
		</dataAssociation>
	</xsl:template>	

	<xsl:template name="dataInputTemplate">
		<dataInput>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:attribute name="isCollection"> <xsl:value-of select="boolean(./@isCollection)"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</dataInput>
	</xsl:template>	
	
	<xsl:template name="dataInputAssociationTemplate">
		<dataInputAssociation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<targetRef><xsl:value-of select="./@targetRef"/></targetRef>
		</dataInputAssociation>
	</xsl:template>		

	<xsl:template name="dataObjectTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:DataObject'">
		<dataObject>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:attribute name="isCollection"> <xsl:value-of select="boolean(./@isCollection)"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</dataObject>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="dataOutputTemplate">
		<dataOutput>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:attribute name="isCollection"> <xsl:value-of select="boolean(./@isCollection)"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</dataOutput>
	</xsl:template>	

	<xsl:template name="dataOutputAssociationTemplate">
		<dataOutputAssociation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<targetRef><xsl:value-of select="./@targetRef"/></targetRef>
		</dataOutputAssociation>
	</xsl:template>	
	
	<xsl:template name="dataStateTemplate">
		<dataState>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</dataState>
	</xsl:template>	

	<xsl:template name="dataStoreTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:DataStore'">
		<dataStore>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="capacity"> <xsl:value-of select="./@capacity"/> </xsl:attribute>
			<xsl:attribute name="isUnlimited"> <xsl:value-of select="boolean(./@isUnlimited)"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</dataStore>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="dataStoreReferenceTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:DataStoreReference'">
		<dataStore>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:attribute name="dataStoreRef"> <xsl:value-of select="./@dataStoreRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</dataStore>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="definitionsTemplate" match="/">
		<xsl:for-each select="definitions">
		<definitions>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="targetNamespace"> <xsl:value-of select="./@targetNamespace"/> </xsl:attribute>
			<xsl:attribute name="expressionLanguage"> <xsl:value-of select="./@expressionLanguage"/> </xsl:attribute>
			<xsl:attribute name="typeLanguage"> <xsl:value-of select="./@typeLanguage"/> </xsl:attribute>
			<xsl:for-each select="imports"> <xsl:call-template name="importTemplate"/> </xsl:for-each>
			<xsl:for-each select="extensions">	<xsl:call-template name="extensionTemplate"/> </xsl:for-each>
			<xsl:for-each select="rootElements"> <xsl:call-template name="rootElementTemplate"/> </xsl:for-each>
			<xsl:for-each select="relationships"> <xsl:call-template name="relationshipTemplate"/> </xsl:for-each>
		</definitions>
		</xsl:for-each>	
	</xsl:template>	

	<xsl:template name="documentationTemplate">
		<documentation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:value-of select="./@text"/>
		</documentation>	
	</xsl:template>	

	<xsl:template name="endEventTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:EndEvent'">
		<endEvent>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</endEvent>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="endpointTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Endpoint'">
		<endPoint>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</endPoint>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="errorTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Error'">
		<error>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</error>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="errorEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ErrorEventDefinition'">
		<errorEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="errorCode"> <xsl:value-of select="./@errorCode"/> </xsl:attribute>
			<xsl:attribute name="errorRef"> <xsl:value-of select="./@errorRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</errorEventDefinition>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="eventDefinitionTemplate">
		<xsl:call-template name="cancelEventDefinitionTemplate"/>
		<xsl:call-template name="compensateEventDefinitionTemplate"/>
		<xsl:call-template name="conditionalEventDefinitionTemplate"/>
		<xsl:call-template name="errorEventDefinitionTemplate"/>
		<xsl:call-template name="escalationEventDefinitionTemplate"/>
		<xsl:call-template name="linkEventDefinitionTemplate"/>
		<xsl:call-template name="messageEventDefinitionTemplate"/>
		<xsl:call-template name="signalEventDefinitionTemplate"/>
		<xsl:call-template name="terminateEventDefinitionTemplate"/>
		<xsl:call-template name="timerEventDefinitionTemplate"/>
	</xsl:template>
	
	<xsl:template name="eventTemplate">
		<xsl:call-template name="startEventTemplate"/>
		<xsl:call-template name="endEventTemplate"/>
	</xsl:template>
	
	<xsl:template name="escalationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Escalation'">
		<escalation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</escalation>
		</xsl:if>
	</xsl:template>

	<xsl:template name="escalationEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:EscalationEventDefinition'">
		<escalationEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="escalationCode"> <xsl:value-of select="./@escalationCode"/> </xsl:attribute>
			<xsl:attribute name="escalationRef"> <xsl:value-of select="./@escalationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</escalationEventDefinition>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="eventBasedGatewayTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:EventBasedGateway'">
		<eventBasedGateway>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="instantiate"> <xsl:value-of select="./@instantiate"/> </xsl:attribute>
			<xsl:attribute name="eventGatewayType"> <xsl:value-of select="./@eventGatewayType"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</eventBasedGateway>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="exclusiveGatewayTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ExclusiveGateway'">
		<exclusiveGateway>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</exclusiveGateway>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="expressionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Expression'">
			<expression><xsl:call-template name="expressionTemplate2"/></expression>
		</xsl:if>
		<xsl:if test="./@xsi:type eq 'bpmn:FormalExpression'">
			<formalExpression><xsl:call-template name="formalExpressionTemplate2"/></formalExpression>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="expressionTemplate2">
		<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
		<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		<xsl:value-of select="."/>
	</xsl:template>	

	<xsl:template name="extensionTemplate">
		<extension>
			<xsl:attribute name="definition"> <xsl:value-of select="./@definition"/> </xsl:attribute>
			<xsl:attribute name="mustUnderstand"> <xsl:value-of select="./@mustUnderstand"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</extension>
	</xsl:template>	

	<xsl:template name="flowElementTemplate">
		<xsl:call-template name="adHocSubProcessTemplate"/>
		<xsl:call-template name="boundaryEventTemplate"/>
		<xsl:call-template name="businessRuleTaskTemplate"/>
		<xsl:call-template name="callActivityTemplate"/>
		<xsl:call-template name="callChoreographyActivityTemplate"/>
		<xsl:call-template name="choreographySubprocessTemplate"/>
		<xsl:call-template name="choreographyTaskTemplate"/>
		<xsl:call-template name="complexGatewayTemplate"/>
		<xsl:call-template name="dataObjectTemplate"/>
		<xsl:call-template name="dataStoreReferenceTemplate"/>
		<xsl:call-template name="endEventTemplate"/>
		<xsl:call-template name="eventBasedGatewayTemplate"/>
		<xsl:call-template name="exclusiveGatewayTemplate"/>
		<xsl:call-template name="implicitThrowEventTemplate"/>
		<xsl:call-template name="inclusiveGatewayTemplate"/>
		<xsl:call-template name="intermediateCatchEventTemplate"/>
		<xsl:call-template name="intermediateThrowEventTemplate"/>
		<xsl:call-template name="manualTaskTemplate"/>
		<xsl:call-template name="parallelGatewayTemplate"/>
		<xsl:call-template name="receiveTaskTemplate"/>
		<xsl:call-template name="sequenceFlowTemplate"/>
		<xsl:call-template name="startEventTemplate"/>
		<xsl:call-template name="scriptTaskTemplate"/>
		<xsl:call-template name="sendTaskTemplate"/>
		<xsl:call-template name="serviceTaskTemplate"/>
		<xsl:call-template name="subProcessTemplate"/>
		<xsl:call-template name="taskTemplate"/>
		<xsl:call-template name="transactionTemplate"/>
		<xsl:call-template name="userTaskTemplate"/>
	</xsl:template>	

	<xsl:template name="formalExpressionTemplate">
		<formalExpression>
			<xsl:call-template name="formalExpressionTemplate2"/>
		</formalExpression>
	</xsl:template>	

	<xsl:template name="formalExpressionTemplate2">
		<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
		<xsl:attribute name="language"> <xsl:value-of select="./@language"/> </xsl:attribute>
		<xsl:attribute name="evaluatesToTypeRef"> <xsl:value-of select="./@evaluatesToTypeRef"/> </xsl:attribute>
		<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
	</xsl:template>	

	<xsl:template name="globalBusinessRuleTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalBusinessRuleTask'">
		<globalBusinessRuleTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</globalBusinessRuleTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="globalChoreographyTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalChoreographyTask'">
		<globalChoreographyTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="initiatingParticipantRef"> <xsl:value-of select="./@initiatingParticipantRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participants"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
		</globalChoreographyTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="globalCommunicationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalCommunication'">
		<globalCommunication>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participants"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationKeys"><xsl:call-template name="correlationKeyTemplate"/></xsl:for-each>
		</globalCommunication>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="globalManualTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalManualTask'">
		<globalManualTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</globalManualTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="globalScriptTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalScriptTask'">
		<globalScriptTask>
			<xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
			<xsl:attribute name="scriptLanguage"><xsl:value-of select="./@scriptLanguage"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="script"><xsl:element name="script"><xsl:value-of select="./@script"/></xsl:element></xsl:for-each>
		</globalScriptTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="globalTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalTask'">
		<globalTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="performers"><xsl:call-template name="performerTemplate"/></xsl:for-each>
		</globalTask>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="globalUserTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:GlobalUserTask'">
		<globalUserTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="implementation"> <xsl:value-of select="./@implementation"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="rendering"><xsl:call-template name="renderingTemplate"/></xsl:for-each>
		</globalUserTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="groupTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Group'">
		<group>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="categoryRef"> <xsl:value-of select="./@categoryRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</group>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="humanPerformerTemplate">
		<humanPerformer>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</humanPerformer>
	</xsl:template>	

	<xsl:template name="implicitThrowEventTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ImplicitThrowEvent'">
		<implicitThrowEvent>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</implicitThrowEvent>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="importTemplate">
		<import>
			<xsl:attribute name="namespace"> <xsl:value-of select="./@namespace"/> </xsl:attribute>
			<xsl:attribute name="location"> <xsl:value-of select="./@location"/> </xsl:attribute>
			<xsl:attribute name="importType"> <xsl:value-of select="./@importType"/> </xsl:attribute>			
		</import>
	</xsl:template>	

	<xsl:template name="inclusiveGatewayTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:InclusiveGateway'">
		<inclusiveGateway>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</inclusiveGateway>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="inputSetTemplate">
		<inputSet>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputRefs"><dataInputRefs><xsl:value-of select="."/></dataInputRefs></xsl:for-each>
			<xsl:for-each select="optionalInputRefs"><dataInputRefs><xsl:value-of select="."/></dataInputRefs></xsl:for-each>
			<xsl:for-each select="whileExecutingInputRefs"><dataInputRefs><xsl:value-of select="."/></dataInputRefs></xsl:for-each>
			<xsl:for-each select="outputSetRefs"><dataInputRefs><xsl:value-of select="."/></dataInputRefs></xsl:for-each>
		</inputSet>
	</xsl:template>	

	<xsl:template name="interfaceTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Interface'">
		<interface>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="operations"><xsl:call-template name="operationTemplate"/></xsl:for-each>
		</interface>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="intermediateCatchEventTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:IntermediateCatchEvent'">
		<intermediateCatchEvent>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</intermediateCatchEvent>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="intermediateThrowEventTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:IntermediateThrowEvent'">
		<intermediateThrowEvent>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</intermediateThrowEvent>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="ioBindingTemplate">
		<ioBinding>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="operationRef"> <xsl:value-of select="./@operationRef"/> </xsl:attribute>
			<xsl:attribute name="inputDataRef"> <xsl:value-of select="./@inputDataRef"/> </xsl:attribute>
			<xsl:attribute name="outputDataRef"> <xsl:value-of select="./@outputDataRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</ioBinding>
	</xsl:template>	

	<xsl:template name="ioSpecificationTemplate">
		<ioSpecification>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputs"><xsl:call-template name="dataInputTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputs"><xsl:call-template name="dataOutputTemplate"/></xsl:for-each>
			<xsl:for-each select="inputSets"><xsl:call-template name="inputSetTemplate"/></xsl:for-each>
			<xsl:for-each select="outputSets"><xsl:call-template name="outputSetTemplate"/></xsl:for-each>
		</ioSpecification>
	</xsl:template>
	
	<xsl:template name="itemDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ItemDefinition'">
		<itemDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:attribute name="isCollection"> <xsl:value-of select="./@isCollection"/> </xsl:attribute>
			<xsl:attribute name="itemKind"> <xsl:value-of select="./@itemKind"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</itemDefinition>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="laneTemplate">
		<lane>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="partitionElementRef"> <xsl:value-of select="./@partitionElementRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="flowElementRefs"><flowElementRef><xsl:value-of select="."/></flowElementRef></xsl:for-each>
			<xsl:for-each select="childLaneSets"><xsl:call-template name="laneSetTemplate"/></xsl:for-each>
		</lane>
	</xsl:template>

	<xsl:template name="laneSetTemplate">
		<laneSet>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="lanes"><xsl:call-template name="laneTemplate"/></xsl:for-each>
		</laneSet>
	</xsl:template>

	<xsl:template name="linkEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:LinkEventDefinition'">
		<linkEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</linkEventDefinition>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="loopCharacteristicsTemplate">
		<xsl:call-template name="multiInstanceLoopCharacteristicsTemplate"/>
		<xsl:call-template name="standardLoopCharacteristicsTemplate"/>
	</xsl:template>	

	<xsl:template name="manualTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ManualTask'">
		<manualTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</manualTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="messageTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Message'">
		<message>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</message>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="messageEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:MessageEventDefinition'">
		<messageEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="messageRef"> <xsl:value-of select="./@messageRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="operationRef"><operationRef><xsl:value-of select="."/></operationRef></xsl:for-each>
		</messageEventDefinition>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="messageFlowTemplate">
		<messageFlow>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="sourceRef"> <xsl:value-of select="./@sourceRef"/> </xsl:attribute>
			<xsl:attribute name="targetRef"> <xsl:value-of select="./@targetRef"/> </xsl:attribute>
			<xsl:attribute name="messageRef"> <xsl:value-of select="./@messageRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</messageFlow>
	</xsl:template>	

	<xsl:template name="messageFlowAssociationTemplate">
		<messageFlowAssociation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="innerMessageFlowRef"> <xsl:value-of select="./@innerMessageFlowRef"/> </xsl:attribute>
			<xsl:attribute name="outerMessageFlowRef"> <xsl:value-of select="./@outerMessageFlowRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</messageFlowAssociation>
	</xsl:template>	

	<xsl:template name="monitoringTemplate">
		<monitoring>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</monitoring>
	</xsl:template>	

	<xsl:template name="multiInstanceLoopCharacteristicsTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:MultiInstanceLoopCharacteristics'">
		<multiInstanceLoopCharacteristics>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="isSequential"> <xsl:value-of select="./@isSequential"/> </xsl:attribute>
			<xsl:attribute name="behavior"> <xsl:value-of select="./@behavior"/> </xsl:attribute>
			<xsl:attribute name="oneBehaviorEventRef"> <xsl:value-of select="./@oneBehaviorEventRef"/> </xsl:attribute>
			<xsl:attribute name="noneBehaviorEventRef"> <xsl:value-of select="./@noneBehaviorEventRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCardinality"><loopCardinality><xsl:call-template name="expressionTemplate2"/></loopCardinality></xsl:for-each>
			<xsl:for-each select="loopDataInput"><xsl:call-template name="dataInputTemplate"/></xsl:for-each>
			<xsl:for-each select="loopDataOutput"><xsl:call-template name="dataOutputTemplate"/></xsl:for-each>
			<xsl:for-each select="complexBehaviorDefinition"><xsl:call-template name="complexBehaviorDefinitionTemplate"/></xsl:for-each>
			<xsl:for-each select="completionCondition"><completionCondition><xsl:call-template name="expressionTemplate2"/></completionCondition></xsl:for-each>
		</multiInstanceLoopCharacteristics>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="operationTemplate">
		<operation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="inMessageRef"><inMessageRef><xsl:value-of select="."/></inMessageRef></xsl:for-each>
			<xsl:for-each select="outMessageRef"><outMessageRef><xsl:value-of select="."/></outMessageRef></xsl:for-each>
			<xsl:for-each select="errorRef"><errorRef><xsl:value-of select="."/></errorRef></xsl:for-each>
		</operation>
	</xsl:template>	

	<xsl:template name="outputSetTemplate">
		<outputSet>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputRefs"><dataOutputRefs><xsl:value-of select="."/></dataOutputRefs></xsl:for-each>
			<xsl:for-each select="optionalOutputRefs"><optionalOutputRefs><xsl:value-of select="."/></optionalOutputRefs></xsl:for-each>
			<xsl:for-each select="whileExecutingOutputRefs"><whileExecutingOutputRefs><xsl:value-of select="."/></whileExecutingOutputRefs></xsl:for-each>
			<xsl:for-each select="inputSetRefs"><inputSetRefs><xsl:value-of select="."/></inputSetRefs></xsl:for-each>
		</outputSet>
	</xsl:template>	

	<xsl:template name="parallelGatewayTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ParallelGateway'">
		<parallelGateway>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</parallelGateway>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="participantTemplate">
		<participant>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="partnerRoleRef"> <xsl:value-of select="./@partnerRoleRef"/> </xsl:attribute>
			<xsl:attribute name="partnerEntityRef"> <xsl:value-of select="./@partnerEntityRef"/> </xsl:attribute>
			<xsl:attribute name="processRef"> <xsl:value-of select="./@processRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="interfaceRef"><interfaceRef><xsl:value-of select="."/></interfaceRef></xsl:for-each>
			<xsl:for-each select="endPointRef"><endPointRef><xsl:value-of select="."/></endPointRef></xsl:for-each>
		</participant>
	</xsl:template>	

	<xsl:template name="participantAssociationTemplate">
		<participantAssociation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="innerParticipantRef"><innerParticipantRef><xsl:value-of select="."/></innerParticipantRef></xsl:for-each>
			<xsl:for-each select="outerParticipantRef"><outerParticipantRef><xsl:value-of select="."/></outerParticipantRef></xsl:for-each>
		</participantAssociation>
	</xsl:template>	

	<xsl:template name="partnerEntityTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:PartnerEntity'">
		<partnerEntity>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</partnerEntity>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="partnerRoleTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:PartnerRole'">
		<partnerRole>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</partnerRole>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="performerTemplate">
		<performer>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</performer>
	</xsl:template>	

	<xsl:template name="potentialOwnerTemplate">
		<potentialOwner>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</potentialOwner>
	</xsl:template>	

	<xsl:template name="processTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Process'">
		<process>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="processType"> <xsl:value-of select="./@processType"/> </xsl:attribute>
			<xsl:attribute name="isClosed"> <xsl:value-of select="./@isClosed"/> </xsl:attribute>
			<xsl:attribute name="definitionalCollaborationRef"> <xsl:value-of select="./@definitionalCollaborationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="laneSets"><xsl:call-template name="laneSetTemplate"/></xsl:for-each>
			<xsl:for-each select="flowElements"><xsl:call-template name="flowElementTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
			<xsl:for-each select="supports"><supports><xsl:value-of select="."/></supports></xsl:for-each>
		</process>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="propertyTemplate">
		<property>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</property>
	</xsl:template>
	
	<xsl:template name="receiveTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ReceiveTask'">
		<receiveTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="implementation"> <xsl:value-of select="./@implementation"/> </xsl:attribute>
			<xsl:attribute name="instantiate"> <xsl:value-of select="./@instantiate"/> </xsl:attribute>
			<xsl:attribute name="messageRef"> <xsl:value-of select="./@messageRef"/> </xsl:attribute>
			<xsl:attribute name="operationRef"> <xsl:value-of select="./@operationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</receiveTask>
		</xsl:if>
	</xsl:template>	

	<xsl:template name="relationshipTemplate">
		<relationship>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="type"> <xsl:value-of select="./@type"/> </xsl:attribute>
			<xsl:attribute name="direction"> <xsl:value-of select="./@direction"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="source"><source><xsl:value-of select="."/></source></xsl:for-each>
			<xsl:for-each select="target"><target><xsl:value-of select="."/></target></xsl:for-each>
		</relationship>	
	</xsl:template>

	<xsl:template name="renderingTemplate">
		<rendering>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rendering>
	</xsl:template>

	<xsl:template name="resourceTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Resource'">
		<resource>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="resourceParameters"><xsl:call-template name="resourceParameterTemplate"/></xsl:for-each>
		</resource>
		</xsl:if>
	</xsl:template>

	<xsl:template name="resourceAssignmentExpressionTemplate">
		<resourceAssignmentExpression>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="expression"><xsl:call-template name="expressionTemplate"/></xsl:for-each>
		</resourceAssignmentExpression>
	</xsl:template>

	<xsl:template name="resourceParameterTemplate">
		<resourceParameter>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="type"> <xsl:value-of select="./@type"/> </xsl:attribute>
			<xsl:attribute name="isRequired"> <xsl:value-of select="./@isRequired"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</resourceParameter>
	</xsl:template>

	<xsl:template name="resourceParameterBindingTemplate">
		<resourceParameterBinding>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="parameterRef"> <xsl:value-of select="./@parameterRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="expression"><xsl:call-template name="expressionTemplate"/></xsl:for-each>
		</resourceParameterBinding>
	</xsl:template>

	<xsl:template name="rootElementTemplate">
		<xsl:call-template name="categoryTemplate"/>
		<xsl:call-template name="choreographyTemplate"/>
		<xsl:call-template name="collaborationTemplate"/>
		<xsl:call-template name="conversationTemplate"/>
		<xsl:call-template name="correlationPropertyTemplate"/>
		<xsl:call-template name="dataStoreTemplate"/>
		<xsl:call-template name="endpointTemplate"/>
		<xsl:call-template name="errorTemplate"/>
		<xsl:call-template name="escalationTemplate"/>
		<xsl:call-template name="globalBusinessRuleTaskTemplate"/>
		<xsl:call-template name="globalChoreographyTaskTemplate"/>
		<xsl:call-template name="globalCommunicationTemplate"/>
		<xsl:call-template name="globalManualTaskTemplate"/>
		<xsl:call-template name="globalScriptTaskTemplate"/>
		<xsl:call-template name="globalTaskTemplate"/>
		<xsl:call-template name="globalUserTaskTemplate"/>
		<xsl:call-template name="interfaceTemplate"/>
		<xsl:call-template name="itemDefinitionTemplate"/>
		<xsl:call-template name="messageTemplate"/>
		<xsl:call-template name="partnerEntityTemplate"/>
		<xsl:call-template name="partnerRoleTemplate"/>
		<xsl:call-template name="processTemplate"/>
		<xsl:call-template name="resourceTemplate"/>
		<xsl:call-template name="signalTemplate"/>
	</xsl:template>

	<xsl:template name="scriptTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ScriptTask'">
		<scriptTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="scriptLanguage"><xsl:value-of select="./@scriptLanguage"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
			<xsl:for-each select="script"><xsl:element name="script"><xsl:value-of select="./@script"/></xsl:element></xsl:for-each>
		</scriptTask>
		</xsl:if>
	</xsl:template>

	<xsl:template name="sendTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:SendTask'">
		<sendTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="implementation"> <xsl:value-of select="./@implementation"/> </xsl:attribute>
			<xsl:attribute name="messageRef"> <xsl:value-of select="./@messageRef"/> </xsl:attribute>
			<xsl:attribute name="operationRef"> <xsl:value-of select="./@operationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</sendTask>
		</xsl:if>
	</xsl:template>

	<xsl:template name="sequenceFlowTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:SequenceFlow'">
			<sequenceFlow>
				<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
				<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
				<xsl:attribute name="sourceRef"> <xsl:value-of select="./@sourceRef"/> </xsl:attribute>
				<xsl:attribute name="targetRef"> <xsl:value-of select="./@targetRef"/> </xsl:attribute>
				<xsl:attribute name="isImmediate"> <xsl:value-of select="./@isImmediate"/> </xsl:attribute>
				<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
				<xsl:for-each select="conditionExpression"><conditionExpression><xsl:call-template name="expressionTemplate2"/></conditionExpression></xsl:for-each>
			</sequenceFlow>
		</xsl:if>
	</xsl:template>

	<xsl:template name="serviceTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:ServiceTask'">
		<serviceTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="implementation"> <xsl:value-of select="./@implementation"/> </xsl:attribute>
			<xsl:attribute name="operationRef"> <xsl:value-of select="./@operationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</serviceTask>
		</xsl:if>
	</xsl:template>

	<xsl:template name="signalTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Signal'">
		<signal>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</signal>
		</xsl:if>
	</xsl:template>

	<xsl:template name="signalEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:SignalEventDefinition'">
		<signalEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="signalRef"> <xsl:value-of select="./@signalRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</signalEventDefinition>
		</xsl:if>
	</xsl:template>

	<xsl:template name="standardLoopCharacteristicsTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:StandardLoopCharacteristics'">
		<standardLoopCharacteristics>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="testBefore"> <xsl:value-of select="./@testBefore"/> </xsl:attribute>
			<xsl:attribute name="loopMaximum"> <xsl:value-of select="./@loopMaximum"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCondition"><loopCondition><xsl:call-template name="expressionTemplate2"/></loopCondition></xsl:for-each>
		</standardLoopCharacteristics>
		</xsl:if>
	</xsl:template>

	<xsl:template name="startEventTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:StartEvent'">
		<startEvent>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isInterrupting"> <xsl:value-of select="./@isInterrupting"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</startEvent>
		</xsl:if>
	</xsl:template>	
	
	<xsl:template name="subConversationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:SubConversation'">
		<subConversation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="correlationKeyRef"> <xsl:value-of select="./@correlationKeyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="conversationNodes"><xsl:call-template name="conversationNodeTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
		</subConversation>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="subProcessTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:SubProcess'">
		<subProcess>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="triggeredByEvent"> <xsl:value-of select="./@triggeredByEvent"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
			<xsl:for-each select="flowElements"><xsl:call-template name="flowElementTemplate"/></xsl:for-each>
			<xsl:for-each select="artifacts"><xsl:call-template name="artifactTemplate"/></xsl:for-each>
		</subProcess>
		</xsl:if>
	</xsl:template>

	<xsl:template name="taskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Task'">
		<task>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
		</task>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="terminateEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:TerminateEventDefinition'">
		<terminateEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</terminateEventDefinition>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="textAnnotationTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:TextAnnotation'">
		<textAnnotation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="text"><xsl:value-of select="."/></xsl:for-each>
		</textAnnotation>
		</xsl:if>
	</xsl:template>

	<xsl:template name="timerEventDefinitionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:TimerEventDefinition'">
		<timerEventDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="timeDate"><timeDate><xsl:call-template name="expressionTemplate2"/></timeDate></xsl:for-each>
			<xsl:for-each select="timeCycle"><timeCycle><xsl:call-template name="expressionTemplate2"/></timeCycle></xsl:for-each>
		</timerEventDefinition>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="transactionTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:Transaction'">
		<transaction>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="method"> <xsl:value-of select="./@method"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</transaction>
		</xsl:if>
	</xsl:template>
	
	<xsl:template name="userTaskTemplate">
		<xsl:if test="./@xsi:type eq 'bpmn:UserTask'">
		<userTask>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isForCompensation"> <xsl:value-of select="./@isForCompensation"/> </xsl:attribute>
			<xsl:attribute name="startQuantity"> <xsl:value-of select="./@startQuantity"/> </xsl:attribute>
			<xsl:attribute name="completionQuantity"> <xsl:value-of select="./@completionQuantity"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:attribute name="implementation"> <xsl:value-of select="./@implementation"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="ioSpecification"><xsl:call-template name="ioSpecificationTemplate"/></xsl:for-each>
			<xsl:for-each select="properties"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCharacteristics"><xsl:call-template name="loopCharacteristicsTemplate"/></xsl:for-each>
			<xsl:for-each select="rendering"><xsl:call-template name="renderingTemplate"/></xsl:for-each>			
		</userTask>
		</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>
