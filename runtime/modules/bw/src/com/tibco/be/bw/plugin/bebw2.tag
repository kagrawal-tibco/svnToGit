<!--
 ! $HeadURL$ $Revision$ $Date$
 !
 ! Copyright(c) 2004-2012 TIBCO Software Inc. All rights reserved.
 !
 ! be-bw palette
 !
 !-->

<!--





	AUTOMATICALLY GENERATED AT BUILD TIME !!!!

	DO NOT EDIT !!!





 !
 ! "bebw.aepalette" is automatically generated at
 ! build time from "bebw.tag"
 !
 ! Any maintenance changes MUST be applied to "bebw.tag"
 ! and an official build triggered to propagate such changes to
 ! "bebw.aepalette"
 !
 ! If maintenance changes must be applied immediately without going
 ! through an official build, then they MUST be applied to *BOTH*
 ! "bebw.tag" *AND* "bebw.aepalette"
 !
 !-->

<palette
    name="ae.palette.BEpalette"
    bundle="com.tibco.be.bw.plugin.Resources,com.tibco.be.bw.plugin.MessageCode"
    version="@BE_VERSION@"
    group="ae.activity.palettegroup">
    <palette.entry type="ae.activities.BESendEvent"/>
    <palette.entry type="ae.activities.BEReceiveEvent"/>
    <palette.entry type="ae.activities.BESignalInEvent"/>
    <palette.entry type="ae.activities.BEInvokeRuleFunction"/>
    <palette.entry type="ae.sharedresource.BERuleServiceProvider"/>
    <!-- <palette.entry type="ae.activities.BEAddFact"/>   -->
    <!-- <palette.entry type="ae.activities.BEModifyFact"/>   -->
    <!-- <palette.entry type="ae.activities.BEDeleteFact"/>   -->
    <!-- <palette.entry type="ae.activities.BEGetFact"/>   -->
    <palette.category category="ae.activity.resource"/>
    <palette.category category="ae.resource"/>
</palette>
