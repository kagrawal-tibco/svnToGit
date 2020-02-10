export sourceDir="/var/lib/jenkins/workspace/BE562/java11"
rm -rf /var/lib/jenkins/workspace/BE562/java11/build/Enterprise/
cd ${sourceDir}/build

export buildDir="${sourceDir}/build/Enterprise/exp/linux26gl25/x86_64/dbg"

major=`grep "BE_VERSION_MAJOR=" version.properties | cut -f 2 -d=`

minor=`grep "BE_VERSION_MINOR=" version.properties | cut -f 2 -d=`

update=`grep "BE_VERSION_UPDATE=" version.properties | cut -f 2 -d=`

bpmn_major=`grep "BE_BPMN_MAJOR=" version.properties | cut -f 2 -d=`

bpmn_minor=`grep "BE_BPMN_MINOR=" version.properties | cut -f 2 -d=`

bpmn_update=`grep "BE_BPMN_UPDATE=" version.properties | cut -f 2 -d=`

hotfix=`grep "BE_VERSION_HOTFIX=" version.properties | cut -f 2 -d=`

export version="${major}.${minor}.${update}"

export bpmn_version="${bpmn_major}.${bpmn_minor}.${bpmn_update}"

export readmeversion="${major}.${minor}.${update}"

export autobuild=""

export enggDir="${sourceDir}/build/engg_build"

export logsDir="/var/lib/jenkins/workspace/BE562/logs"

export releaseDir="/ql/build/BE/${major}.${minor}"

export assembliesExportDir="/mnt/ql/build/BE/leo/packaging/${version}"

export osLin=linux26gl25

export archLin32=x86

export doLin32Build=1

export doLin64Build=0



export bomBaseDir="${sourceDir}/build/pkg_info/bom/${version}"

export releaseBaseDir="/mnt/testinst"

export buildersemailaddr="rjadhav@tibco.com rjain@tibco.com ssinghal@tibco.com vshende@tibco.com"

export punebuildersemailaddr="rjadhav@tibco.com rjain@tibco.com ssinghal@tibco.com vshende@tibco.com"

export devqaemailaddr="rjadhav@tibco.com rjain@tibco.com ssinghal@tibco.com vshende@tibco.com"

export successemailaddr="rjadhav@tibco.com rjain@tibco.com ssinghal@tibco.com vshende@tibco.com"

#export punebuildersemailaddr="mgujrath@tibco.com,bgokhale@tibco.com,pdeokar@tibco.com,rjain@tibco.com"

#export devqaemailaddr="stg-engr@tibco.com,be-qa@tibco.com"

#export successemailaddr="stg-engr@tibco.com,be-qa@tibco.com,akunchef@tibco.com,kbalagur@tibco.com,mqian@tibco.com,magrawal@tibco.com"



########  No Definitions beyond this line  ########

# Initial SVN Update.

cd ${sourceDir}

svn update --non-interactive --username=qlbuild --password='T1bc0!23'

svn revert -R *



# SVN Checks.

#############################################################################

echo $1 $2 

if [ "$2" != "-force" ]

then

	echo "STARTING SVN CHECKS"

	cd ${sourceDir}/build

	lastSuccessRevNo=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`

	lastSuccessRevNo=`expr $lastSuccessRevNo + 1`

	svn update --non-interactive --username=qlbuild --password='T1bc0!23'

	cd ${sourceDir}

	export currBrHd=`svn log --non-interactive --username=qlbuild --password='T1bc0!23'|grep "line"|head -1|cut -c1-6|cut -c2-6`

	

	if [ $currBrHd -le $lastSuccessRevNo ]

	then

		mailx -s "BE 5.6.2 Engineering Build - ${buildNum} - ${autobuild} Build will not proceed (No new checkins)" ${devqaemailaddr} < /dev/null

   		echo "Exiting build....."

   		exit 1

	fi

	cd ${sourceDir}/build

	lastSuccessRevNo=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`

#	export otherFiles=`svn log --non-interactive --username=qlbuild --password='T1bc0!23' -v -r $currBrHd:$lastSuccessRevNo|grep 'branches/private/java11' |grep -v -e '_.*Version\.java'|grep -v 'vbuilds.log' | grep -v 'version.properties' |grep -v 'build/jenkins' |grep -v -e '_.*FeatureConfig\.xml' |grep -v 'about.mappings'`

	cd ${sourceDir}/build/engg_build

	chmod 777 ${sourceDir}/build/engg_build/canBuild.pl

    export toBuild=`perl canBuild.pl $lastSuccessRevNo $currBrHd exclude_patterns.txt`

    echo "toBuild.." $toBuild

	if [ "$toBuild" = "Build relevant checkin not found. Build need not proceed." ]

	then

		export otherFiles=`svn log --non-interactive --username=qlbuild --password='T1bc0!23' -v -r $currBrHd:$lastSuccessRevNo|grep 'branches/private/java11'|sort -u`	

	    echo "$otherFiles" | mailx -s "BE 5.6.2-Java11 daily Engineering Build - ${buildNum} - ${autobuild} Build will not proceed (No new checkins)" ${punebuildersemailaddr} 

   		echo "Exiting build....."

   		exit 1

	fi

fi

#############################################################################



echo "Initializing Build..."

cd ${sourceDir}/build

lastSuccessRevNo=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`

oldRevision=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`

oldRevision=`expr $oldRevision + 1`

buildOldFldr=`grep "env.BE_BUILD=" version.properties | cut -b 14-16`

buildCnt=`grep "env.BE_BUILD=" version.properties | cut -f 2 -d=`

buildNext=`expr ${buildCnt} + 1`

buildNext=`printf '%03d' ${buildNext}`



rm -f version.properties.backup

cp version.properties version.properties.backup

perl -p -i -e "s/\=${buildCnt}/\=${buildNext}/" version.properties

buildCnt=${buildNext}



cd ${sourceDir}/build/engg_build

rm -f vbuilds.log.backup

cp vbuilds.log vbuilds.log.backup



cd ${sourceDir}

export svnlatest=`svn info |grep Revision: |cut -c11-`

cd ${sourceDir}

export currBrHd=`svn log --non-interactive --username=qlbuild --password='T1bc0!23'|grep "line"|head -1|cut -c1-6|cut -c2-6`

cd ${sourceDir}/build

perl -p -i -e "s/env\.BE_REVISION\=.*/env\.BE_REVISION\=${currBrHd}/" version.properties



export buildTag=`grep "env.BE_BUILD=" version.properties | cut -f 2 -d=`

echo "Build Tag=" $buildTag

export buildFldr=`grep "env.BE_BUILD=" version.properties | cut -b 14-16`

export buildNum=${version}.${buildTag}

export revision=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`



# Build trigger notification

#############################################################################

rm -f build.mail

cp ${enggDir}/blank.txt build.mail

#echo $toBuild >> build.mail

#export changedFiles=`svn log --non-interactive --username=qlbuild --password='T1bc0!23' -v -r $currBrHd:$lastSuccessRevNo|grep 'branches/private/java11' |grep -v -e '_.*Version\.java'|grep -v 'vbuilds.log' | grep -v 'version.properties' |grep -v 'build/jenkins' |grep -v -e '_.*FeatureConfig\.xml' |grep -v 'about.mappings'`

#echo "changedFiles var....." $changedFiles

cd ${sourceDir}/build/engg_build

chmod 777 ${sourceDir}/build/engg_build/canBuild.pl

export toBuild=`perl canBuild.pl $lastSuccessRevNo $currBrHd exclude_patterns.txt |sort -u`

echo "toBuild.." $toBuild

echo "$toBuild" | mailx -s "BE 5.6.2-Java11 Engineering Build - ${buildNum} - ${autobuild} Build started by $1 (Checkins detected)" ${punebuildersemailaddr}

mailx -s "BE 5.6.2-Java11 Engineering Build - ${buildNum} - ${autobuild} Build started by $1 (Checkins detected)" ${devqaemailaddr} < /dev/null

echo "[`date`] Build ${buildNum} Started..."

#############################################################################



# Tagging

#############################################################################

cd ${sourceDir}/build

/mnt/tsi/root/external/ant/1.10.6/bin/ant -f build.xml clobber-all pre-all

cd ${sourceDir}/build

export bomBaseDir="${sourceDir}/build/pkg_info/bom/5.6.2"

export enggDir="${sourceDir}/build/engg_build"

cd ${bomBaseDir}

export updated_bom_files=`svn diff | grep Index: | cut -c 8- | sed "s@^@${bomBaseDir}/@g"`

cd ${sourceDir}

export currBrHd=`svn log --non-interactive --username=qlbuild --password='T1bc0!23'|grep "line"|head -1|cut -c1-6|cut -c2-6`

cd ${sourceDir}/build

lastSuccessRevNo=`grep "env.BE_REVISION=" version.properties.backup | cut -f 2 -d=`

lastSuccessRevNo=`expr $lastSuccessRevNo + 1`



# *****************************

cd ${sourceDir}/build/engg_build

chmod 777 ${sourceDir}/build/engg_build/removeVBuildEntry.pl

perl -w removeVBuildEntry.pl vbuilds.log $buildNum > temp.log

mv temp.log vbuilds.log



cd ${sourceDir}

export svnlogs=`svn log --non-interactive --username=qlbuild --password='T1bc0!23' -r $currBrHd:$lastSuccessRevNo`

cd ${sourceDir}/build/engg_build

\cp vbuilds.log temp.log

echo ">>>>>"$buildNum">>>>>" > vbuilds_new.log

date >> vbuilds_new.log

echo "$svnlogs" >> vbuilds_new.log

echo "<<<<<"$buildNum"<<<<<" >> vbuilds_new.log

echo "" >> vbuilds_new.log

echo "" >> vbuilds_new.log

cat temp.log >> vbuilds_new.log

mv vbuilds_new.log vbuilds.log



# *****************************



cd ${sourceDir}/build

svn commit --non-interactive --username=qlbuild --password='T1bc0!23' ${sourceDir}/runtime/modules/interpreter/src/com/tibco/cep/interpreter/cep_interpreterVersion.java ${sourceDir}/runtime/modules/channels/as/src/com/tibco/cep/driver/as/cep_as_channelVersion.java ${sourceDir}/runtime/modules/channels/hawk/src/com/tibco/cep/driver/hawk/cep_hawk_channelVersion.java ${sourceDir}/build/version.properties ${sourceDir}/runtime/core/backingstore-bdb/src/com/tibco/cep/persister/be_bdbVersion.java ${sourceDir}/common/src/com/tibco/be/viewsrt/cep_viewsrtVersion.java ${sourceDir}/runtime/core/datagrid/activespaces/src/com/tibco/cep/as/cep_datagrid_tibcoVersion.java ${sourceDir}/runtime/core/datagrid/coherence/src/com/tibco/cep/cep_datagrid_oracleVersion.java ${sourceDir}/runtime/core/kernel/src/com/tibco/cep/kernel/cep_kernelVersion.java ${sourceDir}/runtime/core/container/src/com/tibco/cep/container/cep_containerVersion.java ${sourceDir}/runtime/core/common/src/com/tibco/cep/cep_commonVersion.java ${sourceDir}/runtime/core/functions/src/com/tibco/be/functions/be_functionsVersion.java ${sourceDir}/runtime/core/base/be/src/com/tibco/cep/cep_baseVersion.java ${sourceDir}/runtime/core/backingstore/src/com/tibco/be/jdbcstore/be_jdbcstoreVersion.java ${sourceDir}/runtime/core/backingstore/src/com/tibco/be/oracle/be_oracleVersion.java ${sourceDir}/runtime/core/drivers/src/com/tibco/cep/driver/cep_driversVersion.java ${sourceDir}/runtime/modules/tools/migration/src/com/tibco/be/dbutils/be_dbutilsVersion.java ${sourceDir}/runtime/modules/tools/migration/src/com/tibco/be/migration/cep_migrationVersion.java ${sourceDir}/runtime/modules/rms/src/com/tibco/be/rms/cep_rmsVersion.java ${sourceDir}/runtime/modules/dbconcepts/src/com/tibco/cep/modules/cep_modulesVersion.java ${sourceDir}/runtime/modules/query/common/src/com/tibco/cep/query/cep_queryVersion.java ${sourceDir}/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/cep_metricVersion.java ${sourceDir}/runtime/modules/dashboard/agent/common/src/com/tibco/cep/dashboard/cep_dashboardVersion.java ${sourceDir}/runtime/modules/loadbalancer/loadbalancer-be/src/com/tibco/cep/loadbalancer/cep_loadbalancerVersion.java ${sourceDir}/runtime/modules/security/src/com/tibco/cep/security/cep_securityVersion.java ${sourceDir}/runtime/modules/bw/src/com/tibco/be/bw/plugin/be_bwVersion.java ${sourceDir}/runtime/modules/bpmn/src/com/tibco/cep/bpmn/cep_bpmnVersion.java ${sourceDir}/runtime/modules/pattern/pattern-be/src/com/tibco/cep/pattern/cep_patternVersion.java ${sourceDir}/runtime/mm/administrator/src/com/tibco/be/deployment/administrator/be_adminVersion.java ${sourceDir}/runtime/mm/em/src/com/tibco/be/bemm/functions/be_mmVersion.java ${sourceDir}/common/src/com/tibco/be/uirms/cep_uirmsVersion.java ${sourceDir}/runtime/mm/em/src/com/tibco/be/bemm/functions/be_mmVersion.java ${sourceDir}/common/src/com/tibco/be/uirms/cep_uirmsVersion.java ${sourceDir}/common/src/com/tibco/be/uirt/cep_uirtVersion.java ${sourceDir}/designtime/studio/plugins/com.tibco.cep.studio.application/about.mappings ${sourceDir}/designtime/studio/functions/src/com/tibco/be/functions/be_studiofunctionsVersion.java ${updated_bom_files} ${sourceDir}/build/engg_build/vbuilds.log ${sourceDir}/runtime/modules/channels/ftl/src/com/tibco/cep/functions/channel/ftl/cep_ftlVersion.java ${sourceDir}/runtime/modules/channels/sb/src/com/tibco/cep/driver/sb/cep_sb_channelVersion.java ${sourceDir}/runtime/modules/analytics/analytics-core/src/com/tibco/cep/analytics/cep_analyticsVersion.java ${sourceDir}/runtime/mm/tea/src/com/tibco/tea/agent/be/version/be_teagentVersion.java ${sourceDir}/runtime/core/common/src/com/tibco/be/util/docker/version/cep_dockerVersion.java -m "TIBCO BusinessEvents Nightly Build Version ${buildNum}"

# *****************************************************



cd ${sourceDir}

svn update --non-interactive --username=qlbuild --password='T1bc0!23'

cd ${sourceDir}/build

export lastRev=`svn info version.properties |grep "Last Changed Rev:" |cut -c18-`

cd ${sourceDir}

export currBrHd=`svn log --non-interactive --username=qlbuild --password='T1bc0!23'|grep "line"|head -1|cut -c1-6|cut -c2-6`

export prevBrHd=$currBrHd



echo "BEFORE WHILE.............." 

chmod 777 ${sourceDir}/build



while [ $currBrHd -gt $lastRev ]

do

   echo "In While loop....."

   export prevBrHd=$currBrHd

   cd ${sourceDir}/build

   perl -p -i -e "s/env\.BE_REVISION\=.*/env\.BE_REVISION\=${currBrHd}/" version.properties



   cd ${sourceDir}/build/engg_build

   chmod 777 ${sourceDir}/build/engg_build/removeVBuildEntry.pl

   perl -w removeVBuildEntry.pl vbuilds.log $buildNum >> temp.log

   mv temp.log vbuilds.log



   cd ${sourceDir}

   export svnlogs=`svn log --non-interactive --username=qlbuild --password='T1bc0!23' -r $currBrHd:$lastSuccessRevNo`

   cd ${sourceDir}/build/engg_build

   \cp vbuilds.log temp.log

   echo ">>>>>"$buildNum">>>>>" > vbuilds_new.log

   date >> vbuilds_new.log

   echo "$svnlogs" >> vbuilds_new.log

   echo "<<<<<"$buildNum"<<<<<" >> vbuilds_new.log

   echo "" >> vbuilds_new.log

   echo "" >> vbuilds_new.log

   cat temp.log >> vbuilds_new.log

   mv vbuilds_new.log vbuilds.log

svn commit --non-interactive --username=qlbuild --password='T1bc0!23' ${sourceDir}/build/engg_build/vbuilds.log ${sourceDir}/build/version.properties -m "TIBCO BusinessEvents Nightly Build Version ${buildNum}"

   cd ${sourceDir}

   svn update --non-interactive --username=qlbuild --password='T1bc0!23'

   cd ${sourceDir}/build

   export lastRev=`svn info version.properties |grep "Last Changed Rev:" |cut -c18-`

   cd ${sourceDir}

   export currBrHd=`svn log --non-interactive --username=qlbuild --password='T1bc0!23'|grep "line"|head -1|cut -c1-6|cut -c2-6`

done



cd ${sourceDir}

svn update --non-interactive --username=qlbuild --password='T1bc0!23' -r $prevBrHd



# *******************************************************

#############################################################################



# Compilation

#############################################################################

cd ${sourceDir}/build

time /mnt/tsi/root/external/ant/1.10.6/bin/ant -f build.xml clobber-all build-all build-studio-site > ${logsDir}/status_${buildNum}_build.log

buildRC=`grep -e '^BUILD SUCCESSFUL' ${logsDir}/status_${buildNum}_build.log`

if [ "${buildRC}" = "" ];  then

   error_msg_2="Compilation failed, please check log in ${logsDir}/status_${buildNum}_build.log."
   echo ${error_msg_2}
   rm -f builderror.mail
   cp ${enggDir}/GenericBuildError.mail builderror.mail
   msg_2=`echo ${error_msg_2} | sed -e 's:/:\\\/:g'`
   perl -p -i -e "s/<ERROR MESSAGE>/${msg_2}/" builderror.mail
   tail -n 200 ${logsDir}/status_${buildNum}_build.log > mailtext.txt
   mailx -s "ATTENTION: BE ${buildNum}-Java11 Engineering Build - Compilation failed" ${devqaemailaddr} < mailtext.txt
   cd ${sourceDir}/build
   rm -f version.properties
   mv version.properties.backup version.properties
   cd ${sourceDir}/build/engg_build
   rm -f vbuilds.log
   mv vbuilds.log.backup vbuilds.log
   cd ${sourceDir}
   svn update --non-interactive --username=qlbuild --password='T1bc0!23'
svn commit --non-interactive --username=qlbuild --password='T1bc0!23' ${sourceDir}/build/engg_build/vbuilds.log ${sourceDir}/build/version.properties -m "reverted to previous version"
   exit 127

fi



#############################################################################



# Compilation successful notification

#############################################################################

rm -f build.mail

cp ${enggDir}/GenericAssemblies.mail build.mail

mailx -s "BE Engineering Installer Build - ${buildNum} ${bebuild} - Compilation successful" ${devqaemailaddr}  < build.mail

#############################################################################



# Export assemblies 

#############################################################################

cd ${sourceDir}/build

export enggDir="${sourceDir}/build/engg_build"

export assembliesExportDir="/mnt/ql/build/BE/leo/packaging/${version}"

export osLin=linux26gl25

export archLin32=x86_64



echo "[`date`] Exporting assemblies to ${assembliesExportDir}..."

rm -rf ${assembliesExportDir}/*

cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/* ${assembliesExportDir}

echo "[`date`] Exporting assemblies done."

chmod 777 ${enggDir}/integration-preflight-tool/linux_x86/ectool

#############################################################################



# Create the installers

#############################################################################

cd ${enggDir}/integration-preflight-tool

time /mnt/tsi/root/external/ant/1.10.6/bin/ant build-all-sp -DqaTests=true > ${logsDir}/status_${buildNum}_installer.log

cd ${logsDir}

buildInst=`grep -e 'error' status_${buildNum}_installer.log`

if [ "${buildInst}" != "" ];  then

   error_msg_2="Installer failed, please check log in ${logsDir}/status_${buildNum}_installer.log."

   echo ${error_msg_2}

   rm -f builderror.mail

   cp ${enggDir}/GenericBuildError.mail builderror.mail

   msg_2=`echo ${error_msg_2} | sed -e 's:/:\\\/:g'`

   perl -p -i -e "s/<ERROR MESSAGE>/${msg_2}/" builderror.mail

   mailx-s "ATTENTION: BE ${version}-Java11 Engineering Build - Installer Creation failed" ${devqaemailaddr} < ${logsDir}/status_${buildNum}_installer.log
   cd ${sourceDir}/build
   rm -f version.properties
   mv version.properties.backup version.properties
   cd ${sourceDir}/build/engg_build
   rm -f vbuilds.log
   mv vbuilds.log.backup vbuilds.log
   cd ${sourceDir}
   svn update --non-interactive --username=qlbuild --password='T1bc0!23'
svn commit --non-interactive --username=qlbuild --password='T1bc0!23' ${sourceDir}/build/engg_build/vbuilds.log ${sourceDir}/build/version.properties -m "reverted to previous version"

   exit 127

fi

#############################################################################



cd ${sourceDir}/build

export enggDir="${sourceDir}/build/engg_build"

export releaseBaseDir="/mnt/testinst"

export releaseDir="/ql/build/BE/${major}.${minor}"



# Send email notification

cd ${sourceDir}/build/engg_build

echo "Sending email notification..."

rm -f build.mail

cp ${enggDir}/GenericBuild.mail build.mail

# perl -p -i -e "s/Build-/Installer Build-/" build.mail

buildId=`echo ${buildTag} | cut -f 2 -d.`

perl -p -i -e "s/xxx/${buildId}/" build.mail

#asJarPath=`/mnt/tsi/root/external/ant/1.10.6/bin/ant -f build.xml showenv-as | awk '/AS_LIB/ {print $4}' | tr -d "[]" | awk '{printf("%s/as-common.jar",$1)}'`
#echo $asJarPath
#asVersion=`unzip -p ${asJarPath} META-INF/MANIFEST.MF | awk '/Build-Version/ {print $2}'`

#echo "AS version used: ${asVersion}"

#perl -p -i -e "s/<ASVERSION>/${asVersion}/" build.mail

chmod 777 ${sourceDir}/build/engg_build/get_jira_urls.pl

cd ${sourceDir}/build/engg_build

export jiraurls=`perl get_jira_urls.pl vbuilds.log ${buildNum}`

perl -p -i -e 's/XXX/$ENV{jiraurls}/' build.mail

mailx -s "BE Engg Build- BE ${buildNum}" ${successemailaddr} < build.mail



mkdir -p /mnt/punebuilds/downloads/test-installers/${version}/${version}.${buildFldr}

cd ${sourceDir}/build/engg_build

echo "Updating jira's.........."

chmod 777 ${sourceDir}/build/engg_build/mark_resolver_builds.pl

perl mark_resolver_builds.pl vbuilds.log ${buildNum} $3 $4


