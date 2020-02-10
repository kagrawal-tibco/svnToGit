package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

/**
 * This class routes all ProcessMessages properties. It enables the use of dynamic(created post war compile) properties files.<br/>
 * If the requested property is not found in custom properties it simply uses the property strings from locale property files.
 * 
 * @author moshaikh
 *
 */
public class ProcessMessages implements I18nMessages {

	private Map<String, String> processMessages;

	public ProcessMessages(Map<String, String> processMessages) {
		this.processMessages = processMessages;
	}

	public String getPropertyValue(String key, String... replaceValues) {
		return GlobalMessages.getPropertyValue(processMessages, key, replaceValues);
	}

	public String palette_title() {
		return getPropertyValue("palette.title");
	}

	public String processPropertyTabTitleGeneral() {
		return getPropertyValue("process.propertytab.title.general");
	}

	public String processPropertyTabTitleDocumentation() {
		return getPropertyValue("process.propertytab.title.documentation");
	}

	public String processPropertyTabTitleVariables() {
		return getPropertyValue("process.propertytab.title.variables");
	}

	public String processPropertyTabGeneralName() {
		return getPropertyValue("process.propertytab.general.name");
	}

	public String processPropertyTabGeneralLabel() {
		return getPropertyValue("process.propertytab.general.label");
	}

	public String processPropertyTabGeneralCheckPoint() {
		return getPropertyValue("process.propertytab.general.checkpoint");
	}

	public String processPropertyTabGeneralResource() {
		return getPropertyValue("process.propertytab.general.resource");
	}

	public String processPropertyTabGeneralJavaResource() {
		return getPropertyValue("process.propertytab.general.javaresource");
	}

	public String processPropertyTabGeneralText() {
		return getPropertyValue("process.propertytab.general.text");
	}

	public String processPropertyTabGeneralService() {
		return getPropertyValue("process.propertytab.general.service");
	}

	public String processPropertyTabGeneralTriggerByEvent() {
		return getPropertyValue("process.propertytab.general.triggerbyevent");
	}

	public String processPropertyTabGeneralDefaultSequence() {
		return getPropertyValue("process.propertytab.general.defaultsequence");
	}

	public String processPropertyTabGeneralMergeExpression() {
		return getPropertyValue("process.propertytab.general.mergeExpression");
	}

	public String processPropertyTabGeneralJoinFunction() {
		return getPropertyValue("process.propertytab.general.joinFunction");
	}

	public String processPropertyTabGeneralForkFunction() {
		return getPropertyValue("process.propertytab.general.forkFunction");
	}

	public String processPropertyTabGeneralPort() {
		return getPropertyValue("process.propertytab.general.port");
	}

	public String processPropertyTabGeneralOperation() {
		return getPropertyValue("process.propertytab.general.operation");
	}

	public String processPropertyTabGeneralSoapAction() {
		return getPropertyValue("process.propertytab.general.soapaction");
	}

	public String processPropertyTabGeneralTimeOut() {
		return getPropertyValue("process.propertytab.general.timeout");
	}

	public String processPropertyTabGeneralAuthor() {
		return getPropertyValue("process.propertytab.general.author");
	}

	public String processPropertyTabGeneralRevision() {
		return getPropertyValue("process.propertytab.general.revision");
	}

	public String processPropertyTabGeneralProcessType() {
		return getPropertyValue("process.propertytab.general.processtype");
	}

	public String processPropertyTabGeneralProcessTypes() {
		return getPropertyValue("process.propertytab.general.processtypes");
	}

	public String processPropertyTabButtonBrowse() {
		return getPropertyValue("process.propertytab.button.browse");
	}

	public String processPropertyTabButtonSave() {
		return getPropertyValue("process.propertytab.button.save");
	}

	public String processPropertyTabButtonEdit() {
		return getPropertyValue("process.propertytab.button.edit");
	}

	public String processDeleteConfirmation() {
		return getPropertyValue("process.delete.element.confirmation.message");
	}

	public String processPropertyTabButtonRemove() {
		return getPropertyValue("process.propertytab.button.remove");
	}

	public String processPropertyTabGeneralImplURI() {
		return getPropertyValue("process.propertytab.general.impluri");
	}

	public String processPropertyTabGeneralKeyExpression() {
		return getPropertyValue("process.propertytab.general.keyexpression");
	}

	public String deleteNotAllowed() {
		return getPropertyValue("process.delete.element.not.allowed.message");
	}

	public String newProcessCreateSuccess() {
		return getPropertyValue("process.add.success.message");
	}

	public String noUniquiedError() {
		return getPropertyValue("process.validation.no.uniqueid.error.message");
	}

	public String invalidProcessProblem() {
		return getPropertyValue("process.validation.problem.error.message");
	}

	public String incorrectProcessProblem() {
		return getPropertyValue("process.validation.incorrect.process.error.message");
	}

	public String resourceValidationProblem() {
		return getPropertyValue("process.validation.resource.problem.error.message");
	}

	public String noResourceExist(String resourceName) {
		return getPropertyValue("process.validation.no.resource.error.message", resourceName);
	}

	public String emptyJoinRuleFunction() {
		return getPropertyValue("process.validation.joinrulefunction.empty.error.message");
	}

	public String emptyMergeExpression() {
		return getPropertyValue("process.validation.merge.expression.empty.error.message");
	}

	public String emptyForkRuleFunction() {
		return getPropertyValue("process.validation.forkrulefunction.empty.error.message");
	}

	public String emptyKeyExpression() {
		return getPropertyValue("process.validation.key.expression.missing.error.message");
	}

	public String noEndEvent(String resourceName) {
		return getPropertyValue("process.validstion.no.end.event.error.message", resourceName);
	}

	public String noStartEvent(String resourceName) {
		return getPropertyValue("process.validation.no.start.event.error.message", resourceName);
	}

	public String noEndpoint() {
		return getPropertyValue("process.validation.no.endpoint.error.message");
	}

	public String noSoapAction() {
		return getPropertyValue("process.validation.no.soapaction.error.message");
	}

	public String validationWait() {
		return getPropertyValue("process.validation.wait.message");
	}

	public String validationError() {
		return getPropertyValue("process.validation.error");
	}

	public String paletteGeneralGroup() {
		return getPropertyValue("process.palette.group.general");
	}

	public String paletteTaskGroup() {
		return getPropertyValue("process.palette.group.tasks");
	}

	public String paletteGatewaysGroup() {
		return getPropertyValue("process.palette.group.gateways");
	}

	public String paletteStartEventsGroup() {
		return getPropertyValue("process.palette.group.start.events");
	}

	public String paletteEndEventsGroup() {
		return getPropertyValue("process.palette.group.end.events");
	}

	public String paletteAnnotation() {
		return getPropertyValue("process.palette.group.general.annotation");
	}

	public String paletteAssociation() {
		return getPropertyValue("process.palette.group.general.association");
	}

	public String paletteSequence() {
		return getPropertyValue("process.palette.group.general.sequence");
	}

	public String paletteScript() {
		return getPropertyValue("process.palette.group.tasks.script");
	}

	public String paletteJavaTask() {
		return getPropertyValue("process.palette.group.tasks.java");
	}

	public String paletteBusinessTask() {
		return getPropertyValue("process.palette.group.tasks.business");
	}

	public String paletteSendMessage() {
		return getPropertyValue("process.palette.group.tasks.send.message");
	}

	public String paletteReceiveMessage() {
		return getPropertyValue("process.palette.group.tasks.receive.message");
	}

	public String paletteManualTask() {
		return getPropertyValue("process.palette.group.tasks.manual");
	}

	public String paletteInferenceTask() {
		return getPropertyValue("process.palette.group.tasks.inference");
	}

	public String paletteWebService() {
		return getPropertyValue("process.palette.group.tasks.webservice");
	}

	public String paletteCallAcitivity() {
		return getPropertyValue("process.palette.group.tasks.callactivity");
	}

	public String paletteSubProcess() {
		return getPropertyValue("process.palette.group.tasks.subprocess");
	}

	public String paletteExclusiveGateway() {
		return getPropertyValue("process.palette.group.gateways.exclusive");
	}

	public String paletteParallelGateway() {
		return getPropertyValue("process.palette.group.gateways.parallel");
	}

	public String paletteStartEvent() {
		return getPropertyValue("process.palette.group.start.event");
	}

	public String paletteMessageStartEvent() {
		return getPropertyValue("process.palette.group.start.event.message");
	}

	public String paletteSignalStartEvent() {
		return getPropertyValue("process.palette.group.start.event.signal");
	}

	public String paletteTimerStartEvent() {
		return getPropertyValue("process.palette.group.start.event.timer");
	}

	public String paletteEndEvent() {
		return getPropertyValue("process.palette.group.end.event");
	}

	public String paletteMessageEndEvent() {
		return getPropertyValue("process.palette.group.end.event.message");
	}

	public String paletteSignalEndEvent() {
		return getPropertyValue("process.palette.group.end.event.signal");
	}

	public String paletteErrorEndEvent() {
		return getPropertyValue("process.palette.group.end.event.error");
	}

	public String paletteAnnotationToolTip() {
		return getPropertyValue("process.palette.group.general.annotation.tooltip");
	}

	public String paletteAssociationToolTip() {
		return getPropertyValue("process.palette.group.general.association.tooltip");
	}

	public String paletteSequenceToolTip() {
		return getPropertyValue("process.palette.group.general.sequence.tooltip");
	}

	public String paletteScriptToolTip() {
		return getPropertyValue("process.palette.group.tasks.script.tooltip");
	}

	public String paletteJavaTaskToolTip() {
		return getPropertyValue("process.palette.group.tasks.java.tooltip");
	}

	public String paletteBusinessTaskToolTip() {
		return getPropertyValue("process.palette.group.tasks.business.tooltip");
	}

	public String paletteSendMessageToolTip() {
		return getPropertyValue("process.palette.group.tasks.send.message.tooltip");
	}

	public String paletteReceiveMessageToolTip() {
		return getPropertyValue("process.palette.group.tasks.receive.message.tooltip");
	}

	public String paletteManualTaskToolTip() {
		return getPropertyValue("process.palette.group.tasks.manual.tooltip");
	}

	public String paletteInferenceTaskToolTip() {
		return getPropertyValue("process.palette.group.tasks.inference.tooltip");
	}

	public String paletteWebServiceToolTip() {
		return getPropertyValue("process.palette.group.tasks.webservice.tooltip");
	}

	public String paletteCallAcitivityToolTip() {
		return getPropertyValue("process.palette.group.tasks.callactivity.tooltip");
	}

	public String paletteSubProcessToolTip() {
		return getPropertyValue("process.palette.group.tasks.subprocess.tooltip");
	}

	public String paletteExclusiveGatewayToolTip() {
		return getPropertyValue("process.palette.group.gateways.exclusive.tooltip");
	}

	public String paletteParallelGatewayToolTip() {
		return getPropertyValue("process.palette.group.gateways.parallel.tooltip");
	}

	public String paletteStartEventToolTip() {
		return getPropertyValue("process.palette.group.start.event.tooltip");
	}

	public String paletteMessageStartEventToolTip() {
		return getPropertyValue("process.palette.group.start.event.message.tooltip");
	}

	public String paletteSignalStartEventToolTip() {
		return getPropertyValue("process.palette.group.start.event.signal.tooltip");
	}

	public String paletteTimerStartEventToolTip() {
		return getPropertyValue("process.palette.group.start.event.timer.tooltip");
	}

	public String paletteEndEventToolTip() {
		return getPropertyValue("process.palette.group.end.event.tooltip");
	}

	public String paletteMessageEndEventToolTip() {
		return getPropertyValue("process.palette.group.end.event.message.tooltip");
	}

	public String paletteSignalEndEventToolTip() {
		return getPropertyValue("process.palette.group.end.event.signal.tooltip");
	}

	public String paletteErrorEndEventToolTip() {
		return getPropertyValue("process.palette.group.end.event.error.tooltip");
	}

	public String processUnderImpl() {
		return getPropertyValue("process.functionality.under.impl");
	}

	public String textAnnotationDefault() {
		return getPropertyValue("process.textannotation.default.value");
	}

	public String processHelpTitle() {
		return getPropertyValue("process.propertytab.help");
	}

	public String processComponentNameHelp() {
		return getPropertyValue("process.propertytab.help.name");
	}

	public String processComponentLabelHelp() {
		return getPropertyValue("process.propertytab.help.label");
	}

	public String processComponentCheckPointHelp() {
		return getPropertyValue("process.propertytab.help.checkpoint");
	}

	public String processScriptTaskResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.script.resource");
	}

	public String processBusinessRuleResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.business.rule.resource");
	}

	public String processBusinessRuleImplURIHelp() {
		return getPropertyValue("process.propertytab.help.task.business.rule.implurl");
	}

	public String processSendMessageResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.send.message.resource");
	}

	public String processReceiveMessageResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.receive.message.resource");
	}

	public String processReceiveMessageKeyExpressionHelp() {
		return getPropertyValue("process.propertytab.help.task.receive.message.keyexpression");
	}

	public String processWebServiceResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.webservice.resource");
	}

	public String processWebServiceServiceHelp() {
		return getPropertyValue("process.propertytab.help.task.webservice.service");
	}

	public String processWebServicePortHelp() {
		return getPropertyValue("process.propertytab.help.task.webservice.port");
	}

	public String processWebServiceOperationHelp() {
		return getPropertyValue("process.propertytab.help.task.webservice.operation");
	}

	public String processWebServiceSoapActionHelp() {
		return getPropertyValue("process.propertytab.help.task.webservice.soapaction");
	}

	public String processWebServiceTimeoutHelp() {
		return getPropertyValue("process.propertytab.help.task.webservice.timeout");
	}

	public String processInferenceTaskResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.inference.resource");
	}

	public String processCallActivityResourceHelp() {
		return getPropertyValue("process.propertytab.help.task.callactivity.resource");
	}

	public String processExclusiveGatewayDefSqeHelp() {
		return getPropertyValue("process.propertytab.help.gateway.exclusive.default.sequence");
	}

	public String processParallelGatewayJoinFunHelp() {
		return getPropertyValue("process.propertytab.help.gateway.parallel.join.function");
	}

	public String processParallelGatewayForkFunHelp() {
		return getPropertyValue("process.propertytab.help.gateway.parallel.fork.function");
	}

	public String processMessageStartEventResourceRHelp() {
		return getPropertyValue("process.propertytab.help.event.start.message.resource");
	}

	public String processTimerStartEventResourceHelp() {
		return getPropertyValue("process.propertytab.help.event.start.timer.resource");
	}

	public String processSignalStartEventResourceHelp() {
		return getPropertyValue("process.propertytab.help.event.start.signal.resource");
	}

	public String processMessageEndEventResourceRHelp() {
		return getPropertyValue("process.propertytab.help.event.end.message.resource");
	}

	public String processErrorEndEventResourceHelp() {
		return getPropertyValue("process.propertytab.help.event.end.error.resource");
	}

	public String processSignalEndEventResourceHelp() {
		return getPropertyValue("process.propertytab.help.event.end.signal.resource");
	}

	public String processToolbarPrintSetup() {
		return getPropertyValue("process.toolbar.print.setup");
	}

	public String processToolbarPrintPreview() {
		return getPropertyValue("process.toolbar.print.preview");
	}

	public String processToolbarPrint() {
		return getPropertyValue("process.toolbar.print");
	}

	public String processToolbarSelect() {
		return getPropertyValue("process.toolbar.select");
	}

	public String processToolbarPan() {
		return getPropertyValue("process.toolbar.pan");
	}

	public String processToolbarMarqueeZoom() {
		return getPropertyValue("process.toolbar.marquee.zoom");
	}

	public String processToolbarInteractiveZoom() {
		return getPropertyValue("process.toolbar.interactive.zoom");
	}

	public String processToolbarZoomIn() {
		return getPropertyValue("process.toolbar.zoom.in");
	}

	public String processToolbarZoomOut() {
		return getPropertyValue("process.toolbar.zoom.out");
	}

	public String processToolbarZoomFit() {
		return getPropertyValue("process.toolbar.zoom.fit");
	}

	public String processToolbarOverView() {
		return getPropertyValue("process.toolbar.overview");
	}

	public String processToolbarCircularLayout() {
		return getPropertyValue("process.toolbar.circular.layout");
	}

	public String processToolbarOrthogonalLayout() {
		return getPropertyValue("process.toolbar.orthogonal.layout");
	}

	public String processToolbarHierarchicalLayout() {
		return getPropertyValue("process.toolbar.hierarchical.layout");
	}

	public String processToolbarSymmetricLayout() {
		return getPropertyValue("process.toolbar.symmetric.layout");
	}

	public String processToolbarGlobalLayout() {
		return getPropertyValue("process.toolbar.global.layout");
	}

	public String processToolbarIncrementalLayout() {
		return getPropertyValue("process.toolbar.incremental.layout");
	}

	public String processToolbarLabeling() {
		return getPropertyValue("process.toolbar.labeling");
	}

	public String processToolbarEdgeRouting() {
		return getPropertyValue("process.toolbar.all.edge.routing");
	}

	public String processToolbarDelete() {
		return getPropertyValue("process.toolbar.delete");
	}

	public String processToolbarUndo() {
		return getPropertyValue("process.toolbar.undo");
	}

	public String processToolbarRedo() {
		return getPropertyValue("process.toolbar.redo");
	}

	public String processNavigationPanelCenterBtn() {
		return getPropertyValue("process.navigation.toolbar.scroll.center.button");
	}

	public String processNavigationPanelWestBtn() {
		return getPropertyValue("process.navigation.toolbar.scroll.west.button");
	}

	public String processNavigationPanelNorthBtn() {
		return getPropertyValue("process.navigation.toolbar.scroll.north.button");
	}

	public String processNavigationPanelSouthBtn() {
		return getPropertyValue("process.navigation.toolbar.scroll.south.button");
	}

	public String processNavigationPanelEastBtn() {
		return getPropertyValue("process.navigation.toolbar.scroll.east.button");
	}

	public String processNavigationPanelZoomInBtn() {
		return getPropertyValue("process.navigation.toolbar.zoom.in.button");
	}
	
	public String processNavigationPanelZoomOutBtn() {
		return getPropertyValue("process.navigation.toolbar.zoom.out.button");
	}

	public String processNavigationPanelZoomLevel200() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.200.button");
	}

	public String processNavigationPanelZoomLevel150() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.150.button");
	}

	public String processNavigationPanelZoomLevel100() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.100.button");
	}

	public String processNavigationPanelZoomLevel75() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.75.button");
	}

	public String processNavigationPanelZoomLevel50() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.50.button");
	}

	public String processNavigationPanelZoomLevel25() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.25.button");
	}

	public String processNavigationPanelZoomLevel10() {
		return getPropertyValue("process.navigation.toolbar.zoom.level.10.button");
	}

}
