<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:bpmn="http://schema.omg.org/spec/BPMN/2.0" xmlns="http://schema.omg.org/spec/BPMN/2.0">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

	<!-- BPMN 2.0 Transformation: XML-to-XMI -->

	<xsl:template name="activityResourceTemplate">
		<activityResource>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</activityResource>
	</xsl:template>

	<xsl:template name="adHocSubProcessAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:AdHocSubProcess</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
			<xsl:call-template name="flowElementTemplate"/>
			<xsl:call-template name="artifactTemplate"/>
			<xsl:for-each select="completionCondition"><completionCondition><xsl:call-template name="formalExpressionTemplate2"/></completionCondition></xsl:for-each>
		</flowElements>
	</xsl:template>

	<xsl:template name="artifactTemplate">
		<xsl:for-each select="association"><xsl:call-template name="associationAsArtifactTemplate"/></xsl:for-each>
		<xsl:for-each select="group"><xsl:call-template name="groupAsArtifactTemplate"/></xsl:for-each>
		<xsl:for-each select="textAnnotation"><xsl:call-template name="textAnnotationAsArtifactTemplate"/></xsl:for-each>
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

	<xsl:template name="associationAsArtifactTemplate">
		<artifacts>
			<xsl:attribute name="xsi:type">bpmn:Association</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="sourceRef"> <xsl:value-of select="./@sourceRef"/> </xsl:attribute>
			<xsl:attribute name="targetRef"> <xsl:value-of select="./@targetRef"/> </xsl:attribute>
			<xsl:attribute name="associationDirection"> <xsl:value-of select="./@associationDirection"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</artifacts>
	</xsl:template>

	<xsl:template name="auditingTemplate">
		<auditing>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
		</auditing>
	</xsl:template>
	
	<xsl:template name="boundaryEventAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:BoundaryEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="cancelActivity"> <xsl:value-of select="./@cancelActivity"/> </xsl:attribute>
			<xsl:attribute name="attachedToRef"> <xsl:value-of select="./@attachedToRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	
	
	<xsl:template name="businessRuleTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:BusinessRuleTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
	</xsl:template>	

	<xsl:template name="callActivityAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:CallActivity</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
	</xsl:template>	

	<xsl:template name="callChoreographyActivityAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:CallChoreographyActivity</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="calledElement"> <xsl:value-of select="./@calledElement"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="callConversationAsConversationNodeTemplate">
		<conversationNodes>
			<xsl:attribute name="xsi:type">bpmn:CallConversation</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="calledElementRef"> <xsl:value-of select="./@calledElementRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		</conversationNodes>
	</xsl:template>	

	<xsl:template name="cancelEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:CancelEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>	
	
	<xsl:template name="categoryAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Category</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValue"><xsl:call-template name="categoryValueTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>		
	
	<xsl:template name="categoryValueTemplate">
		<categoryValue>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="value"> <xsl:value-of select="./@value"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</categoryValue>
	</xsl:template>

	<xsl:template name="choreographyAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Choreography</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="isClose"> <xsl:value-of select="boolean(./@isClosed)"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:call-template name="flowElementTemplate"/>
			<xsl:call-template name="artifactTemplate"/>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="participant"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="conversation"><xsl:call-template name="conversationTemplate"/></xsl:for-each>
			<xsl:for-each select="conversationAssociation"><xsl:call-template name="conversationAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlowAssociation"><xsl:call-template name="messageFlowAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>		
	
	<xsl:template name="choreographySubprocessAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ChoreographySubprocess</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:call-template name="flowElementTemplate"/>
			<xsl:call-template name="artifactTemplate"/>
		</flowElements>
	</xsl:template>		
	
	<xsl:template name="choreographyTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ChoreographyTask</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>
	
	<xsl:template name="collaborationAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Collaboration</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isClosed"> <xsl:value-of select="boolean(./@isClosed)"/> </xsl:attribute>
			<xsl:attribute name="choreographyRef"> <xsl:value-of select="./@choreographyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participant"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:call-template name="artifactTemplate"/>
			<xsl:for-each select="conversation"><xsl:call-template name="conversationTemplate"/></xsl:for-each>
			<xsl:for-each select="conversationAssociation"><xsl:call-template name="conversationAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlowAssociation"><xsl:call-template name="messageFlowAssociationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>			
	
	<xsl:template name="collaborationTemplate2">
		<xsl:attribute name="xsi:type">bpmn:Collaboration</xsl:attribute>
		<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
		<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
		<xsl:attribute name="isClosed"> <xsl:value-of select="boolean(./@isClosed)"/> </xsl:attribute>
		<xsl:attribute name="choreographyRef"> <xsl:value-of select="./@choreographyRef"/> </xsl:attribute>
		<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		<xsl:for-each select="participant"><xsl:call-template name="participantTemplate"/></xsl:for-each>
		<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
		<xsl:call-template name="artifactTemplate"/>
		<xsl:for-each select="conversation"><xsl:call-template name="conversationTemplate"/></xsl:for-each>
		<xsl:for-each select="conversationAssociation"><xsl:call-template name="conversationAssociationTemplate"/></xsl:for-each>
		<xsl:for-each select="participantAssociation"><xsl:call-template name="participantAssociationTemplate"/></xsl:for-each>
		<xsl:for-each select="messageFlowAssociation"><xsl:call-template name="messageFlowAssociationTemplate"/></xsl:for-each>
	</xsl:template>			
	
	<xsl:template name="communicationTemplate">
		<communication>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="correlationKeyRef"> <xsl:value-of select="./@correlationKeyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</communication>
	</xsl:template>

	<xsl:template name="communicationAsConversationNodeTemplate">
		<conversationNodes>
			<xsl:attribute name="xsi:type">bpmn:Communication</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="correlationKeyRef"> <xsl:value-of select="./@correlationKeyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</conversationNodes>
	</xsl:template>

	<xsl:template name="compensateEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:CompensateEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="waitForCompletion"> <xsl:value-of select="boolean(./@waitForCompletion)"/> </xsl:attribute>
			<xsl:attribute name="activityRef"> <xsl:value-of select="./@activityRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>	
	
	<xsl:template name="complexBehaviorDefinitionTemplate">
		<complexBehaviorDefinition>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="condition"><xsl:call-template name="expressionTemplate2"/></xsl:for-each>
			<xsl:for-each select="event"><xsl:call-template name="eventTemplate"/></xsl:for-each>
		</complexBehaviorDefinition>
	</xsl:template>
	
	<xsl:template name="complexGatewayAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ComplexGateway</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
			<xsl:for-each select="activationCondition"><activationCondition><xsl:call-template name="expressionTemplate2"/></activationCondition></xsl:for-each>
		</flowElements>
	</xsl:template>

	<xsl:template name="conditionalEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:ConditionalEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="condition"><condition><xsl:call-template name="expressionTemplate2"/></condition></xsl:for-each>
		</eventDefinitions>
	</xsl:template>	
	
	<xsl:template name="conversationNodeTemplate">
		<xsl:for-each select="callConversation"><xsl:call-template name="callConversationAsConversationNodeTemplate"/></xsl:for-each>
		<xsl:for-each select="communication"><xsl:call-template name="communicationAsConversationNodeTemplate"/></xsl:for-each>
		<xsl:for-each select="subConversation"><xsl:call-template name="subConversationAsConversationNodeTemplate"/></xsl:for-each>
	</xsl:template>

	<xsl:template name="conversationTemplate">
		<conversation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:call-template name="conversationNodeTemplate"/>
			<xsl:for-each select="participant"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:call-template name="artifactTemplate"/>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationKey"><xsl:call-template name="correlationKeyTemplate"/></xsl:for-each>
		</conversation>
	</xsl:template>	

	<xsl:template name="conversationAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Conversation</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:call-template name="conversationNodeTemplate"/>
			<xsl:for-each select="participant"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:call-template name="artifactTemplate"/>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationKey"><xsl:call-template name="correlationKeyTemplate"/></xsl:for-each>
		</rootElements>
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

	<xsl:template name="correlationPropertyAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:CorrelationProperty</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationPropertyRetrievalExpressions"><xsl:call-template name="correlationPropertyRetrievalExpressionTemplate"/></xsl:for-each>
		</rootElements>
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

	<xsl:template name="dataObjectAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:DataObject</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:attribute name="isCollection"> <xsl:value-of select="boolean(./@isCollection)"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</flowElements>
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

	<xsl:template name="dataStoreAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:DataStore</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="capacity"> <xsl:value-of select="./@capacity"/> </xsl:attribute>
			<xsl:attribute name="isUnlimited"> <xsl:value-of select="boolean(./@isUnlimited)"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="dataStoreReferenceAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:DataStoreReference</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:attribute name="dataStoreRef"> <xsl:value-of select="./@dataStoreRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="definitionsTemplate" match="/">
		<xsl:for-each select="definitions">
		<definitions>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="targetNamespace"> <xsl:value-of select="./@targetNamespace"/> </xsl:attribute>
			<xsl:attribute name="expressionLanguage"> <xsl:value-of select="./@expressionLanguage"/> </xsl:attribute>
			<xsl:attribute name="typeLanguage"> <xsl:value-of select="./@typeLanguage"/> </xsl:attribute>
			<xsl:for-each select="import"> <xsl:call-template name="importTemplate"/> </xsl:for-each>
			<xsl:for-each select="extension"><xsl:call-template name="extensionTemplate"/> </xsl:for-each>
			<xsl:call-template name="rootElementTemplate"/>
			<xsl:for-each select="relationship"> <xsl:call-template name="relationshipTemplate"/> </xsl:for-each>
		</definitions>
		</xsl:for-each>	
	</xsl:template>	

	<xsl:template name="documentationTemplate">
		<documentation>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:value-of select="./@text"/>
		</documentation>	
	</xsl:template>	

	<xsl:template name="endEventAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:EndEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>

	<xsl:template name="endEventAsEventTemplate">
		<event>
			<xsl:attribute name="xsi:type">bpmn:EndEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</event>
	</xsl:template>
	
	<xsl:template name="endPointAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Endpoint</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="errorAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Error</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="errorEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:ErrorEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="errorCode"> <xsl:value-of select="./@errorCode"/> </xsl:attribute>
			<xsl:attribute name="errorRef"> <xsl:value-of select="./@errorRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>	

	<xsl:template name="eventDefinitionTemplate">
		<xsl:for-each select="cancelEventDefinition"><xsl:call-template name="cancelEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="compensateEventDefinition"><xsl:call-template name="compensateEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="conditionalEventDefinition"><xsl:call-template name="conditionalEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="errorEventDefinition"><xsl:call-template name="errorEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="escalationEventDefinition"><xsl:call-template name="escalationEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="linkEventDefinition"><xsl:call-template name="linkEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="messageEventDefinition"><xsl:call-template name="messageEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="signalEventDefinition"><xsl:call-template name="signalEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="terminateEventDefinition"><xsl:call-template name="terminateEventDefinitionTemplate"/></xsl:for-each>
		<xsl:for-each select="timerEventDefinition"><xsl:call-template name="timerEventDefinitionTemplate"/></xsl:for-each>
	</xsl:template>
	
	<xsl:template name="eventTemplate">
		<xsl:for-each select="startEvent"><xsl:call-template name="startEventAsEventTemplate"/></xsl:for-each>
		<xsl:for-each select="endEvent"><xsl:call-template name="endEventAsEventTemplate"/></xsl:for-each>
	</xsl:template>
	
	<xsl:template name="escalationAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Escalation</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>

	<xsl:template name="escalationEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:EscalationEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="escalationCode"> <xsl:value-of select="./@escalationCode"/> </xsl:attribute>
			<xsl:attribute name="escalationRef"> <xsl:value-of select="./@escalationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>	

	<xsl:template name="eventBasedGatewayAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:EventBasedGateway</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="instantiate"> <xsl:value-of select="./@instantiate"/> </xsl:attribute>
			<xsl:attribute name="eventGatewayType"> <xsl:value-of select="./@eventGatewayType"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="exclusiveGatewayAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ExclusiveGateway</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="expressionTemplate">
		<xsl:for-each select="expression"><expression><xsl:call-template name="expressionTemplate2"/></expression></xsl:for-each>
		<xsl:for-each select="formalExpression">
			<formalExpression>
				<xsl:attribute name="xsi:type">bpmn:FormalExpression</xsl:attribute>
				<xsl:call-template name="formalExpressionTemplate2"/>
			</formalExpression></xsl:for-each>
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
		<xsl:for-each select="adHocSubProcess"><xsl:call-template name="adHocSubProcessAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="boundaryEvent"><xsl:call-template name="boundaryEventAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="businessRuleTask"><xsl:call-template name="businessRuleTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="callActivity"><xsl:call-template name="callActivityAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="callChoreographyActivity"><xsl:call-template name="callChoreographyActivityAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="choreographySubprocess"><xsl:call-template name="choreographySubprocessAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="choreographyTask"><xsl:call-template name="choreographyTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="complexGateway"><xsl:call-template name="complexGatewayAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="dataObject"><xsl:call-template name="dataObjectAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="dataStoreReference"><xsl:call-template name="dataStoreReferenceAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="endEvent"><xsl:call-template name="endEventAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="eventBasedGateway"><xsl:call-template name="eventBasedGatewayAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="exclusiveGateway"><xsl:call-template name="exclusiveGatewayAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="implicitThrowEvent"><xsl:call-template name="implicitThrowEventAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="inclusiveGateway"><xsl:call-template name="inclusiveGatewayAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="intermediateCatchEvent"><xsl:call-template name="intermediateCatchEventAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="intermediateThrowEvent"><xsl:call-template name="intermediateThrowEventAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="manualTask"><xsl:call-template name="manualTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="parallelGateway"><xsl:call-template name="parallelGatewayAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="receiveTask"><xsl:call-template name="receiveTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="sequenceFlow"><xsl:call-template name="sequenceFlowAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="startEvent"><xsl:call-template name="startEventAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="scriptTask"><xsl:call-template name="scriptTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="sendTask"><xsl:call-template name="sendTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="serviceTask"><xsl:call-template name="serviceTaskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="subProcess"><xsl:call-template name="subProcessAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="task"><xsl:call-template name="taskAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="transaction"><xsl:call-template name="transactionAsFlowElementTemplate"/></xsl:for-each>
		<xsl:for-each select="userTask"><xsl:call-template name="userTaskAsFlowElementTemplate"/></xsl:for-each>
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

	<xsl:template name="globalBusinessRuleTaskAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalBusinessRuleTask</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="globalChoreographyTaskAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalChoreographyTask</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="initiatingParticipantRef"> <xsl:value-of select="./@initiatingParticipantRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participant"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="globalCommunicationAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalCommunication</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="participants"><xsl:call-template name="participantTemplate"/></xsl:for-each>
			<xsl:for-each select="messageFlow"><xsl:call-template name="messageFlowTemplate"/></xsl:for-each>
			<xsl:for-each select="correlationKeys"><xsl:call-template name="correlationKeyTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="globalManualTaskAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalManualTask</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="globalScriptTaskAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalScriptTask</xsl:attribute>
			<xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
			<xsl:attribute name="scriptLanguage"><xsl:value-of select="./@scriptLanguage"/></xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="script"><xsl:element name="script"><xsl:value-of select="./@script"/></xsl:element></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="globalTaskAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalTask</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="performer"><xsl:call-template name="performerTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	
	
	<xsl:template name="globalUserTaskAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:GlobalUserTask</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="implementation"> <xsl:value-of select="./@implementation"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="rendering"><xsl:call-template name="renderingTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="groupAsArtifactTemplate">
		<artifacts>
			<xsl:attribute name="xsi:type">bpmn:Group</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="categoryRef"> <xsl:value-of select="./@categoryRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</artifacts>
	</xsl:template>	

	<xsl:template name="humanPerformerTemplate">
		<performers>
			<xsl:attribute name="xsi:type">bpmn:HumanPerformer</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</performers>
	</xsl:template>	

	<xsl:template name="implicitThrowEventAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ImplicitThrowEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="importTemplate">
		<import>
			<xsl:attribute name="namespace"> <xsl:value-of select="./@namespace"/> </xsl:attribute>
			<xsl:attribute name="location"> <xsl:value-of select="./@location"/> </xsl:attribute>
			<xsl:attribute name="importType"> <xsl:value-of select="./@importType"/> </xsl:attribute>			
		</import>
	</xsl:template>	

	<xsl:template name="inclusiveGatewayAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:InclusiveGateway</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="default"> <xsl:value-of select="./@default"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
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

	<xsl:template name="interfaceAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Interface</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="operation"><xsl:call-template name="operationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="intermediateCatchEventAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:IntermediateCatchEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="intermediateThrowEventAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:IntermediateThrowEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
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
			<xsl:for-each select="dataInput"><xsl:call-template name="dataInputTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutput"><xsl:call-template name="dataOutputTemplate"/></xsl:for-each>
			<xsl:for-each select="inputSet"><xsl:call-template name="inputSetTemplate"/></xsl:for-each>
			<xsl:for-each select="outputSet"><xsl:call-template name="outputSetTemplate"/></xsl:for-each>
		</ioSpecification>
	</xsl:template>
	
	<xsl:template name="itemDefinitionAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:ItemDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:attribute name="isCollection"> <xsl:value-of select="./@isCollection"/> </xsl:attribute>
			<xsl:attribute name="itemKind"> <xsl:value-of select="./@itemKind"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="laneTemplate">
		<lane>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="partitionElementRef"> <xsl:value-of select="./@partitionElementRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="flowElementRef"><flowElementRefs><xsl:value-of select="."/></flowElementRefs></xsl:for-each>
			<xsl:for-each select="childLaneSet"><xsl:call-template name="laneSetTemplate"/></xsl:for-each>
		</lane>
	</xsl:template>

	<xsl:template name="laneSetTemplate">
		<laneSet>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="lane"><xsl:call-template name="laneTemplate"/></xsl:for-each>
		</laneSet>
	</xsl:template>

	<xsl:template name="linkEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:LinkEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>	

	<xsl:template name="loopCharacteristicsTemplate">
		<xsl:for-each select="multiInstanceLoopCharacteristics"><xsl:call-template name="multiInstanceLoopCharacteristicsTemplate"/></xsl:for-each>
		<xsl:for-each select="standardLoopCharacteristics"><xsl:call-template name="standardLoopCharacteristicsTemplate"/></xsl:for-each>
	</xsl:template>	

	<xsl:template name="manualTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ManualTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
	</xsl:template>	

	<xsl:template name="messageAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Message</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="messageEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:MessageEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="messageRef"> <xsl:value-of select="./@messageRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="operationRef"><operationRef><xsl:value-of select="."/></operationRef></xsl:for-each>
		</eventDefinitions>
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
		<messageFlowAssociations>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="innerMessageFlowRef"> <xsl:value-of select="./@innerMessageFlowRef"/> </xsl:attribute>
			<xsl:attribute name="outerMessageFlowRef"> <xsl:value-of select="./@outerMessageFlowRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</messageFlowAssociations>
	</xsl:template>	

	<xsl:template name="monitoringTemplate">
		<monitoring>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</monitoring>
	</xsl:template>	

	<xsl:template name="multiInstanceLoopCharacteristicsTemplate">
		<loopCharacteristics>
			<xsl:attribute name="xsi:type">bpmn:MultiInstanceLoopCharacteristics</xsl:attribute>
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
		</loopCharacteristics>
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

	<xsl:template name="parallelGatewayAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ParallelGateway</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	

	<xsl:template name="participantTemplate">
		<participants>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="partnerRoleRef"> <xsl:value-of select="./@partnerRoleRef"/> </xsl:attribute>
			<xsl:attribute name="partnerEntityRef"> <xsl:value-of select="./@partnerEntityRef"/> </xsl:attribute>
			<xsl:attribute name="processRef"> <xsl:value-of select="./@processRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="interfaceRef"><interfaceRef><xsl:value-of select="."/></interfaceRef></xsl:for-each>
			<xsl:for-each select="endPointRef"><endPointRef><xsl:value-of select="."/></endPointRef></xsl:for-each>
		</participants>
	</xsl:template>	

	<xsl:template name="participantAssociationTemplate">
		<participantAssociations>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="innerParticipantRef"><innerParticipantRef><xsl:value-of select="."/></innerParticipantRef></xsl:for-each>
			<xsl:for-each select="outerParticipantRef"><outerParticipantRef><xsl:value-of select="."/></outerParticipantRef></xsl:for-each>
		</participantAssociations>
	</xsl:template>	

	<xsl:template name="partnerEntityAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:PartnerEntity</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="partnerRoleAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:PartnerRole</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>	

	<xsl:template name="performerTemplate">
		<performers>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="resourceRef"> <xsl:value-of select="./@resourceRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"> <xsl:call-template name="documentationTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceAssignmentExpression"> <xsl:call-template name="resourceAssignmentExpressionTemplate"/> </xsl:for-each>
			<xsl:for-each select="resourceParameterBinding"> <xsl:call-template name="resourceParameterBindingTemplate"/> </xsl:for-each>
		</performers>
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

	<xsl:template name="processAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Process</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="processType"> <xsl:value-of select="./@processType"/> </xsl:attribute>
			<xsl:attribute name="isClosed"> <xsl:value-of select="./@isClosed"/> </xsl:attribute>
			<xsl:attribute name="definitionalCollaborationRef"> <xsl:value-of select="./@definitionalCollaborationRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="laneSet"><xsl:call-template name="laneSetTemplate"/></xsl:for-each>
			<xsl:call-template name="flowElementTemplate"/>
			<xsl:call-template name="artifactTemplate"/>
			<xsl:for-each select="supports"><supports><xsl:value-of select="."/></supports></xsl:for-each>
		</rootElements>
	</xsl:template>
	
	<xsl:template name="propertyTemplate">
		<properties>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="itemSubjectRef"> <xsl:value-of select="./@itemSubjectRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataState"><xsl:call-template name="dataStateTemplate"/></xsl:for-each>
		</properties>
	</xsl:template>
	
	<xsl:template name="receiveTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ReceiveTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
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

	<xsl:template name="resourceAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Resource</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="resourceParameter"><xsl:call-template name="resourceParameterTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>

	<xsl:template name="resourceAssignmentExpressionTemplate">
		<resourceAssignmentExpressions>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="expression"><xsl:call-template name="expressionTemplate"/></xsl:for-each>
		</resourceAssignmentExpressions>
	</xsl:template>

	<xsl:template name="resourceParameterTemplate">
		<resourceParameters>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="type"> <xsl:value-of select="./@type"/> </xsl:attribute>
			<xsl:attribute name="isRequired"> <xsl:value-of select="./@isRequired"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</resourceParameters>
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
		<xsl:for-each select="category"><xsl:call-template name="categoryAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="choreography"><xsl:call-template name="choreographyAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="collaboration"><xsl:call-template name="collaborationAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="conversation"><xsl:call-template name="conversationAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="correlationProperty"><xsl:call-template name="correlationPropertyAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="dataStore"><xsl:call-template name="dataStoreAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="endpoint"><xsl:call-template name="endPointAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="error"><xsl:call-template name="errorAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="escalation"><xsl:call-template name="escalationAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalBusinessRuleTask"><xsl:call-template name="globalBusinessRuleTaskAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalChoreographyTask"><xsl:call-template name="globalChoreographyTaskAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalCommunication"><xsl:call-template name="globalCommunicationAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalManualTask"><xsl:call-template name="globalManualTaskAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalScriptTask"><xsl:call-template name="globalScriptTaskAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalTask"><xsl:call-template name="globalTaskAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="globalUserTask"><xsl:call-template name="globalUserTaskAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="interface"><xsl:call-template name="interfaceAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="itemDefinition"><xsl:call-template name="itemDefinitionAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="message"><xsl:call-template name="messageAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="partnerEntity"><xsl:call-template name="partnerEntityAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="partnerRole"><xsl:call-template name="partnerRoleAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="process"><xsl:call-template name="processAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="resource"><xsl:call-template name="resourceAsRootElementTemplate"/></xsl:for-each>
		<xsl:for-each select="signal"><xsl:call-template name="signalAsRootElementTemplate"/></xsl:for-each>
	</xsl:template>

	<xsl:template name="scriptTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ScriptTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
			<xsl:for-each select="script"><xsl:element name="script"><xsl:value-of select="./@script"/></xsl:element></xsl:for-each>
		</flowElements>
	</xsl:template>

	<xsl:template name="sendTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:SendTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
	</xsl:template>

	<xsl:template name="sequenceFlowAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:SequenceFlow</xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="sourceRef"> <xsl:value-of select="./@sourceRef"/> </xsl:attribute>
			<xsl:attribute name="targetRef"> <xsl:value-of select="./@targetRef"/> </xsl:attribute>
			<xsl:attribute name="isImmediate"> <xsl:value-of select="./@isImmediate"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="conditionExpression"><conditionExpression><xsl:call-template name="expressionTemplate2"/></conditionExpression></xsl:for-each>
		</flowElements>
	</xsl:template>

	<xsl:template name="serviceTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:ServiceTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
	</xsl:template>

	<xsl:template name="signalAsRootElementTemplate">
		<rootElements>
			<xsl:attribute name="xsi:type">bpmn:Signal</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="structureRef"> <xsl:value-of select="./@structureRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</rootElements>
	</xsl:template>

	<xsl:template name="signalEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:SignalEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="signalRef"> <xsl:value-of select="./@signalRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>

	<xsl:template name="standardLoopCharacteristicsTemplate">
		<loopCharacteristics>
			<xsl:attribute name="xsi:type">bpmn:StandardLoopCharacteristics</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="testBefore"> <xsl:value-of select="./@testBefore"/> </xsl:attribute>
			<xsl:attribute name="loopMaximum"> <xsl:value-of select="./@loopMaximum"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="loopCondition"><loopCondition><xsl:call-template name="expressionTemplate2"/></loopCondition></xsl:for-each>
		</loopCharacteristics>
	</xsl:template>

	<xsl:template name="startEventAsEventTemplate">
		<event>
			<xsl:attribute name="xsi:type">bpmn:StartEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isInterrupting"> <xsl:value-of select="./@isInterrupting"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</event>
	</xsl:template>	
	
	<xsl:template name="startEventAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:StartEvent</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="name"> <xsl:value-of select="./@name"/> </xsl:attribute>
			<xsl:attribute name="isInterrupting"> <xsl:value-of select="./@isInterrupting"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="auditing"><xsl:call-template name="auditingTemplate"/></xsl:for-each>
			<xsl:for-each select="monitoring"><xsl:call-template name="monitoringTemplate"/></xsl:for-each>
			<xsl:for-each select="categoryValueRef"><categoryValueRef><xsl:value-of select="."/></categoryValueRef></xsl:for-each>
		</flowElements>
	</xsl:template>	
	
	<xsl:template name="subConversationAsConversationNodeTemplate">
		<conversationNodes>
			<xsl:attribute name="xsi:type">bpmn:SubConversation</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="correlationKeyRef"> <xsl:value-of select="./@correlationKeyRef"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:call-template name="conversationNodeTemplate"/>
			<xsl:call-template name="artifactTemplate"/>
		</conversationNodes>
	</xsl:template>
	
	<xsl:template name="subProcessAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:SubProcess</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
			<xsl:call-template name="flowElementTemplate"/>
			<xsl:call-template name="artifactTemplate"/>
		</flowElements>
	</xsl:template>

	<xsl:template name="taskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:Task</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
		</flowElements>
	</xsl:template>
	
	<xsl:template name="terminateEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:TerminateEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</eventDefinitions>
	</xsl:template>
	
	<xsl:template name="textAnnotationAsArtifactTemplate">
		<artifacts>
			<xsl:attribute name="xsi:type">bpmn:TextAnnotation</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="text"><xsl:value-of select="."/></xsl:for-each>
		</artifacts>
	</xsl:template>

	<xsl:template name="timerEventDefinitionTemplate">
		<eventDefinitions>
			<xsl:attribute name="xsi:type">bpmn:TimerEventDefinition</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
			<xsl:for-each select="timeDate"><timeDate><xsl:call-template name="expressionTemplate2"/></timeDate></xsl:for-each>
			<xsl:for-each select="timeCycle"><timeCycle><xsl:call-template name="expressionTemplate2"/></timeCycle></xsl:for-each>
		</eventDefinitions>
	</xsl:template>
	
	<xsl:template name="transactionAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:Transaction</xsl:attribute>
			<xsl:attribute name="id"> <xsl:value-of select="./@id"/> </xsl:attribute>
			<xsl:attribute name="method"> <xsl:value-of select="./@method"/> </xsl:attribute>
			<xsl:for-each select="documentation"><xsl:call-template name="documentationTemplate"/></xsl:for-each>
		</flowElements>
	</xsl:template>
	
	<xsl:template name="userTaskAsFlowElementTemplate">
		<flowElements>
			<xsl:attribute name="xsi:type">bpmn:UserTask</xsl:attribute>
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
			<xsl:for-each select="property"><xsl:call-template name="propertyTemplate"/></xsl:for-each>
			<xsl:for-each select="dataInputAssociation"><xsl:call-template name="dataInputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="dataOutputAssociation"><xsl:call-template name="dataOutputAssociationTemplate"/></xsl:for-each>
			<xsl:for-each select="activityResource"><xsl:call-template name="activityResourceTemplate"/></xsl:for-each>
			<xsl:call-template name="loopCharacteristicsTemplate"/>
			<xsl:for-each select="rendering"><xsl:call-template name="renderingTemplate"/></xsl:for-each>			
		</flowElements>
	</xsl:template>
	
</xsl:stylesheet>
