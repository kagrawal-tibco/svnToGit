export sourceDir="/home/build/be-build/branches/5.2"

cd ${sourceDir}/build
rm version.properties
svn up version.properties
export buildDir="${sourceDir}/build/Enterprise/exp/win/x86/dbg"
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
export logsDir="/home/build/be-build/logs/be"
export releaseDir="/mnt/ql/build/BE/${major}.${minor}"
export assembliesExportDir="/mnt/ql/build/BE/leo/packaging/${version}"
export osWin=win
export archWin32=x86
export osLin=linux26gl25
export archLin32=x86
export archLin64=x86_64
export doLin32Build=1
export doLin64Build=0

export bomBaseDir="${sourceDir}/build/pkg_info/bom/${version}"
export releaseBaseDir="/mnt/testinst"
export releasePuneDir="/mnt/punerel/rel/BE/${version}"
export releasePuneLocalDir="build@10.97.118.201:/home/build/rel/BE/${version}"
export buildersemailaddr="ggrigore@tibco.com,pdhar@tibco.com,nprade@tibco.com,sbagi@tibco.com,mdoshi@tibco.com,abhave@tibco.com,mgujrath@tibco.com"
#export successemailaddr="ql-build@tibco.com,be-qa@tibco.com,stg-engr@tibco.com,be-xsg@tibco.com,amkulkar@tibco.com,dyamashi@tibco.com"
export successemailaddr="ql-build@tibco.com,be-qa@tibco.com,stg-engr@tibco.com,mjovanov@tibco.com,dyamashi@tibco.com,jpark@tibco.com,mqian@tibco.com,be-xsg@tibco.com,magrawal@tibco.com,jakaur@tibco.com"
export successemailpuneaddr="BEIndiaDev@tibco.com,abhave@tibco.com"

export doSvnTagging=1
export doSyncToPune=0

# Test Mode params. To enable test mode, uncomment all lines below.


if [ "$1" = "test" ]
then

export successemailaddr=${buildersemailaddr}
export successemailpuneaddr=${buildersemailaddr}
export doSvnTagging=0
export doSyncToPune=0

fi

########  No Definitions beyond this line  ########

if [ "$1" = "test" ]
then
echo "Initializing Build in Test Mode..."
else
echo "Initializing Build..."
fi

if [ "$2" = "weeklybuild" ]
then
autobuild="Auto Weekly"
fi

oldRevision=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`
oldRevision=`expr $oldRevision + 1`
buildOldFldr=`grep "env.BE_BUILD=" version.properties | cut -b 14-16`
buildCnt=`grep "env.BE_BUILD=" version.properties | cut -f 2 -d=`
buildNext=`expr ${buildCnt} + 1`
buildNext=`printf '%03d' ${buildNext}`

rm -f version.properties.backup
mv version.properties version.properties.backup

cp ${enggDir}/user.properties.win32 ${sourceDir}/build/user.properties

cd ${sourceDir}/
${enggDir}/cleanupVersionFiles.sh
cd ${sourceDir}/build

# perform svn update and check return code
ant -f ${enggDir}/svn-update.xml
antRC=$?
if [ ${antRC} -ne 0 ]; then
   error_msg_1="svn update failed with conflicted files.  Please resolve and restart the build."
   echo ${error_msg_1}
   rm -f builderror.mail
   cp ${enggDir}/GenericBuildError.mail builderror.mail
   perl -p -i -e "s/<ERROR MESSAGE>/${error_msg_1}/" builderror.mail
   mail ${buildersemailaddr} -s "ATTENTION: BE ${version} ${autobuild} Engineering Build - SVN update failed" < builderror.mail
   rm -f version.properties builderror.mail
   mv version.properties.backup version.properties
   exit 127
fi

#buildNewFldr=`grep "env.BE_BUILD=" version.properties | cut -b 14-16`
#if [ ${buildNewFldr} -ne ${buildOldFldr} ] ; then
#   perl -p -i -e "s/BE_VERSION_UPDATE=./BE_VERSION_UPDATE=${update}/" version.properties 
#   perl -p -i -e "s/env\.BE\_BUILD\=${buildNewFldr}/env\.BE\_BUILD\=${buildNewFldr}\.${buildCnt}/" version.properties
#fi

perl -p -i -e "s/\=${buildCnt}/\=${buildNext}/" version.properties
buildCnt=${buildNext}

export buildTag=`grep "env.BE_BUILD=" version.properties | cut -f 2 -d=`
echo "Build Tag=" $buildTag
export buildFldr=`grep "env.BE_BUILD=" version.properties | cut -b 14-16`
export buildNum=${version}.${buildTag}
export revision=`grep "env.BE_REVISION=" version.properties | cut -f 2 -d=`

# Build trigger notification
rm -f build.mail
cp ${enggDir}/blank.txt build.mail
mail ${buildersemailaddr} -s "BE Engineering Build - ${buildNum} - ${autobuild} Build started by $1" < build.mail
echo "[`date`] ${autobuild} Build ${buildNum} Started..."

# Tagging
ant clobber-all pre-all
cd ${bomBaseDir}
export updated_bom_files=`svn diff | grep Index: | cut -c 8- | sed "s@^@${bomBaseDir}/@g"`
cd ${sourceDir}/build

echo "doSvnTagging="${doSvnTagging}
if [ "${doSvnTagging}" = "1" ]; then
echo "committing files"
svn commit ${sourceDir}/runtime/modules/interpreter/src/com/tibco/cep/interpreter/cep_interpreterVersion.java ${sourceDir}/runtime/modules/channels/as/src/com/tibco/cep/driver/as/cep_as_channelVersion.java ${sourceDir}/runtime/modules/channels/hawk/src/com/tibco/cep/driver/hawk/cep_hawk_channelVersion.java ${sourceDir}/build/version.properties ${sourceDir}/runtime/core/backingstore-bdb/src/com/tibco/cep/persister/be_bdbVersion.java ${sourceDir}/common/src/com/tibco/be/viewsrt/cep_viewsrtVersion.java ${sourceDir}/runtime/core/datagrid/activespaces/src/com/tibco/cep/as/cep_datagrid_tibcoVersion.java ${sourceDir}/runtime/core/datagrid/coherence/src/com/tibco/cep/cep_datagrid_oracleVersion.java ${sourceDir}/runtime/core/kernel/src/com/tibco/cep/kernel/cep_kernelVersion.java ${sourceDir}/runtime/core/container/src/com/tibco/cep/container/cep_containerVersion.java ${sourceDir}/runtime/core/common/src/com/tibco/cep/cep_commonVersion.java ${sourceDir}/runtime/core/functions/src/com/tibco/be/functions/be_functionsVersion.java ${sourceDir}/runtime/core/base/be/src/com/tibco/cep/cep_baseVersion.java ${sourceDir}/runtime/core/backingstore/src/com/tibco/be/jdbcstore/be_jdbcstoreVersion.java ${sourceDir}/runtime/core/backingstore/src/com/tibco/be/oracle/be_oracleVersion.java ${sourceDir}/runtime/core/drivers/src/com/tibco/cep/driver/cep_driversVersion.java ${sourceDir}/runtime/modules/interpreter/src/com/tibco/cep/interpreter/cep_interpreterVersion.java ${sourceDir}/runtime/modules/tools/migration/src/com/tibco/be/dbutils/be_dbutilsVersion.java ${sourceDir}/runtime/modules/tools/migration/src/com/tibco/be/migration/cep_migrationVersion.java ${sourceDir}/runtime/modules/rms/src/com/tibco/be/rms/cep_rmsVersion.java ${sourceDir}/runtime/modules/dbconcepts/src/com/tibco/cep/modules/cep_modulesVersion.java ${sourceDir}/runtime/modules/query/common/src/com/tibco/cep/query/cep_queryVersion.java ${sourceDir}/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/cep_metricVersion.java ${sourceDir}/runtime/modules/dashboard/agent/common/src/com/tibco/cep/dashboard/cep_dashboardVersion.java ${sourceDir}/runtime/modules/loadbalancer/loadbalancer-be/src/com/tibco/cep/loadbalancer/cep_loadbalancerVersion.java ${sourceDir}/runtime/modules/security/src/com/tibco/cep/security/cep_securityVersion.java ${sourceDir}/runtime/modules/bw/src/com/tibco/be/bw/plugin/be_bwVersion.java ${sourceDir}/runtime/modules/bpmn/src/com/tibco/cep/bpmn/cep_bpmnVersion.java ${sourceDir}/runtime/modules/pattern/pattern-be/src/com/tibco/cep/pattern/cep_patternVersion.java ${sourceDir}/runtime/mm/administrator/src/com/tibco/be/deployment/administrator/be_adminVersion.java ${sourceDir}/runtime/mm/em/src/com/tibco/be/bemm/functions/be_mmVersion.java ${sourceDir}/common/src/com/tibco/be/uirms/cep_uirmsVersion.java ${sourceDir}/common/src/com/tibco/be/uirt/cep_uirtVersion.java ${sourceDir}/designtime/studio/plugins/com.tibco.cep.studio.application/about.mappings ${sourceDir}/designtime/studio/functions/src/com/tibco/be/functions/be_studiofunctionsVersion.java ${sourceDir}/runtime/modules/analytics/analytics-core/src/com/tibco/cep/analytics/cep_analyticsVersion.java ${updated_bom_files} -m "TIBCO BusinessEvents Engineering Build Version ${buildNum} ${autobuild}"
fi

# Compilation
export targets="build-all"
if [ "${update}" != "0" ];  then
	export targets="${targets} build-assemblies-servicepack"
fi
if [ "${hotfix}" != "0" ];  then
	export targets="${targets} build-assemblies-hotfix"
fi

# Compilation
time ant clobber-all ${targets} build-studio-site > ${logsDir}/status_${buildNum}_build.log
buildRC=`grep -e '^BUILD SUCCESSFUL' ${logsDir}/status_${buildNum}_build.log`
if [ "${buildRC}" = "" ];  then
   error_msg_2="Compilation failed, please check log in ${logsDir}/status_${buildNum}_build.log."
   echo ${error_msg_2}
   rm -f builderror.mail
   cp ${enggDir}/GenericBuildError.mail builderror.mail
   msg_2=`echo ${error_msg_2} | sed -e 's:/:\\\/:g'`
   perl -p -i -e "s/<ERROR MESSAGE>/${msg_2}/" builderror.mail
   mail ${buildersemailaddr} -s "ATTENTION: BE ${version} Engineering Build - Compilation failed" < ${logsDir}/status_${buildNum}_build.log
   rm -f version.properties builderror.mail
   mv version.properties.backup version.properties
   svn commit -m reset ${sourceDir}/build/version.properties
   exit 127
fi

if [ "${doLin32Build}" = "1" ]; then
   cp ${enggDir}/user.properties.lin32 ${sourceDir}/build/user.properties
   time ant ${targets} >> ${logsDir}/status_${buildNum}_build.log
fi

if [ "${doLin64Build}" = "1" ]; then
   cp ${enggDir}/user.properties.lin64 ${sourceDir}/build/user.properties
   time ant ${targets} >> ${logsDir}/status_${buildNum}_build.log
fi

# Commit the log files
echo "doSvnTagging="${doSvnTagging}
if [ "${doSvnTagging}" = "1" ]; then
echo "committing log files"
svn commit ${sourceDir}/build/logs -m "TIBCO BusinessEvents Engineering Build Version ${buildNum} ${autobuild}- logs"
fi

# Compilation successful notification
rm -f build.mail
cp ${enggDir}/GenericAssemblies.mail build.mail
mail ${buildersemailaddr} -s "BE Engineering Installer Build - ${buildNum} ${bebuild} - Compilation successful" < build.mail

#create svn update log
cd ${sourceDir}; svn log -r $oldRevision:${revision} > ${logsDir}/status_${buildNum}_svn.log
echo "[`date`] Build Completed."

# Export assemblies
echo "[`date`] Exporting assemblies to ${assembliesExportDir}..."
rm -rf ${assembliesExportDir}/*
cp -r ${sourceDir}/build/Enterprise/exp/${osWin}/${archWin32}/dbg/Final/* ${assembliesExportDir}
if [ "${doLin32Build}" = "1" ]; then
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/linux* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/aix* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/hpux* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/sol* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/macosx* ${assembliesExportDir}
fi
if [ "${doLin64Build}" = "1" ]; then
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin64}/dbg/Final/linux* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/aix* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/hpux* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/sol* ${assembliesExportDir}
    cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/macosx* ${assembliesExportDir}
fi
echo "[`date`] Exporting assemblies done."

# Create the installers
if [ "${hotfix}" != "0" ]
then
	cd ${enggDir}/integration-preflight-tool
	time ant build-hf -DbuildCnt=${buildCnt} > ${logsDir}/status_${buildNum}_installer.log
else
	cd ${enggDir}/integration-preflight-tool
	time ant build-all -DqaTests=false > ${logsDir}/status_${buildNum}_installer.log
fi

buildInst=`grep -e 'error' ${logsDir}/status_${buildNum}_installer.log`
if [ "${buildInst}" != "" ];  then
   error_msg_2="Installer failed, please check log in ${logsDir}/status_${buildNum}_installer.log."
   echo ${error_msg_2}
   rm -f builderror.mail
   cp ${enggDir}/GenericBuildError.mail builderror.mail
   msg_2=`echo ${error_msg_2} | sed -e 's:/:\\\/:g'`
   perl -p -i -e "s/<ERROR MESSAGE>/${msg_2}/" builderror.mail
   mail ${buildersemailaddr} -s "ATTENTION: BE ${version} Engineering Build - Installer Creation failed" < ${logsDir}/status_${buildNum}_build.log
   rm -f version.properties builderror.mail
   exit 127
fi


cd ${sourceDir}/build

# Copy the necessary files to the Release area
export buildReleaseDir=${buildNum}"(R${revision})"
export buildReleaseLocation=${releaseDir}/${version}.${buildFldr}/${buildReleaseDir}
echo "[`date`] Exporting Service Pack to Release area - ${buildReleaseLocation}..."
mkdir -p ${buildReleaseLocation}
sleep 3
addOnBuildDir=${releaseBaseDir}/businessevents-standard/${version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}
addOnBuildDir=${releaseBaseDir}/businessevents-datamodeling/${version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}
addOnBuildDir=${releaseBaseDir}/businessevents-decisionmanager/${version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}
addOnBuildDir=${releaseBaseDir}/businessevents-eventstreamprocessing/${version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}
addOnBuildDir=${releaseBaseDir}/businessevents-express/${version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}
addOnBuildDir=${releaseBaseDir}/businessevents-process/${bpmn_version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}
addOnBuildDir=${releaseBaseDir}/businessevents-views/${version}/test-installers
latestBuildDir=`ls -t ${addOnBuildDir} | head -1`
iDir=${addOnBuildDir}/${latestBuildDir}
echo "Copying installers from ${iDir}..."
cp ${iDir}/*.zip ${buildReleaseLocation}

echo "[`date`] Installer available at ${buildReleaseLocation}."
cp ${sourceDir}/build/engg_build/engg-build.readme ${buildReleaseLocation}
cp ${sourceDir}/build/engg_build/engg-fix.readme ${buildReleaseLocation}
#cp -f ${logsDir}/status_${buildNum}_svn.log ${buildReleaseLocation}

# Organize into PORTs
mkdir ${buildReleaseLocation}/win_x86_64
mv ${buildReleaseLocation}/*win_x86_64.zip ${buildReleaseLocation}/win_x86_64
cp ${buildReleaseLocation}/engg-* ${buildReleaseLocation}/win_x86_64

mkdir ${buildReleaseLocation}/linux26gl25_x86_64
mv ${buildReleaseLocation}/*linux26gl25_x86_64.zip ${buildReleaseLocation}/linux26gl25_x86_64
cp ${buildReleaseLocation}/engg-* ${buildReleaseLocation}/linux26gl25_x86_64

mkdir ${buildReleaseLocation}/aix61_power_64
mv ${buildReleaseLocation}/*aix61_power_64.zip ${buildReleaseLocation}/aix61_power_64
cp ${buildReleaseLocation}/engg-* ${buildReleaseLocation}/aix61_power_64

mkdir ${buildReleaseLocation}/hpux112_ia64
mv ${buildReleaseLocation}/*hpux112_ia64.zip ${buildReleaseLocation}/hpux112_ia64
cp ${buildReleaseLocation}/engg-* ${buildReleaseLocation}/hpux112_ia64

mkdir ${buildReleaseLocation}/sol10_x86_64
mv ${buildReleaseLocation}/*sol10_x86_64.zip ${buildReleaseLocation}/sol10_x86_64
cp ${buildReleaseLocation}/engg-* ${buildReleaseLocation}/sol10_x86_64

mkdir ${buildReleaseLocation}/macosx_x86_64
mv ${buildReleaseLocation}/*macosx_x86_64.zip ${buildReleaseLocation}/macosx_x86_64
cp ${buildReleaseLocation}/engg-* ${buildReleaseLocation}/macosx_x86_64

# Copy Studio SDK Site
mkdir -p ${buildReleaseLocation}_SDK
cp ${buildDir}/TIB_*.zip ${buildReleaseLocation}_SDK

# Build cleanup
rm -f version.properties.backup build.mail
cp ${enggDir}/user.properties.win32 ${sourceDir}/build/user.properties

cd ${sourceDir}/build

# Send email notification
echo "Sending email notification..."
rm -f build.mail
cp ${enggDir}/GenericBuild.mail build.mail
perl -p -i -e "s/Build-/Installer Build-/" build.mail
buildId=`echo ${buildTag} | cut -f 2 -d.`
perl -p -i -e "s/xxx/${buildId}/" build.mail
rDir=`echo ${releaseDir} | sed -e 's:/:\\\\\\\\:g' -e 's:mnt:\\\\\\\\na-h-filer2:'`
echo ${rDir}
perl -p -i -e "s/<LINK>/${rDir}\\\\${version}.${buildFldr}\\\\${buildNum}(R${revision})/" build.mail
asJarPath=`ant showenv-as | awk '/AS_LIB/ {print $4}' | tr -d "[]" | awk '{printf("%s/as-common.jar",$1)}'`
asVersion=`unzip -p ${asJarPath} META-INF/MANIFEST.MF | awk '/Build-Version/ {print $2}'`
echo "AS version used: ${asVersion}"
perl -p -i -e "s/<ASVERSION>/${asVersion}/" build.mail
mail ${successemailaddr} -s "BE Engineering ${autobuild} Build - ${buildNum}" < build.mail
rm -f version.properties.backup build.mail

# Sync to Punehome
if [ "${doSyncToPune}" = "1" ]; then
echo "Sync to Punehome and to qllinux -- ${buildReleaseLocation} to ${releasePuneDir}"
mkdir ${releasePuneDir}/${buildReleaseDir}
sleep 15
cp -r ${buildReleaseLocation}/* ${releasePuneDir}/${buildReleaseDir}
rsync -avv  ${buildReleaseLocation}/* ${releasePuneLocalDir}/${buildReleaseDir}
echo "\\\\punehome\\punerel\\rel\\BE\\5.2.0\\${buildNum}(R${revision})"  > punemail.txt
mail ${successemailpuneaddr} -s "BE Engineering ${autobuild} Build for ${buildNum} - Available on Punehome" < punemail.txt
rm punemail.txt
fi

# Remove the Enterprise directory
cd ${sourceDir}/build
mv ${sourceDir}/build/Enterprise /home/build/be-build/Enterprise_Areas/Enterprise_${buildNum}
rm -rf ${sourceDir}/build/Enterprise_${buildNum}
