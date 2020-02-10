export sourceDir="${WORKSPACE}/5.2"
cd ${sourceDir}/build
export buildDir="${sourceDir}/build/Enterprise/exp/linux26gl25/x86/dbg"
major=`grep "BE_VERSION_MAJOR=" version_sp.properties | cut -f 2 -d=`
minor=`grep "BE_VERSION_MINOR=" version_sp.properties | cut -f 2 -d=`
update=`grep "BE_VERSION_UPDATE=" version_sp.properties | cut -f 2 -d=`
bpmn_major=`grep "BE_BPMN_MAJOR=" version_sp.properties | cut -f 2 -d=`
bpmn_minor=`grep "BE_BPMN_MINOR=" version_sp.properties | cut -f 2 -d=`
bpmn_update=`grep "BE_BPMN_UPDATE=" version_sp.properties | cut -f 2 -d=`
hotfix=`grep "BE_VERSION_HOTFIX=" version_sp.properties | cut -f 2 -d=`
export version="${major}.${minor}.${update}"
export bpmn_version="${bpmn_major}.${bpmn_minor}.${bpmn_update}"
export readmeversion="${major}.${minor}.${update}"
export autobuild=""
export enggDir="${sourceDir}/build/engg_build"
export logsDir="/home/build/be-build/logs/be"
export releaseDir="/mnt/ql/build/BE/${major}.${minor}"
export assembliesExportDir="/mnt/ql/build/BE/leo/packaging/${version}"
export osLin=linux26gl25
export archLin32=x86
export doLin32Build=1
export doLin64Build=0

export bomBaseDir="${sourceDir}/build/pkg_info/bom/${version}"
export releaseBaseDir="/mnt/testinst"
export buildersemailaddr="mgujrath@tibco.com,vasharma@tibco.com,sbagi@tibco.com,bgokhale@tibco.com,abhave@tibco.com"
export successemailaddr="ql-build@tibco.com,be-qa@tibco.com,stg-engr@tibco.com,magrawal@tibco.com,jpark@tibco.com,mqian@tibco.com,be-xsg@tibco.com,jakaur@tibco.com"

########  No Definitions beyond this line  ########


echo "Initializing Build..."

oldRevision=`grep "env.BE_REVISION=" version_sp.properties | cut -f 2 -d=`
oldRevision=`expr $oldRevision + 1`
buildOldFldr=`grep "env.BE_BUILD=" version_sp.properties | cut -b 14-16`
buildCnt=`grep "env.BE_BUILD=" version_sp.properties | cut -f 2 -d=`
buildNext=`expr ${buildCnt} + 1`
buildNext=`printf '%03d' ${buildNext}`

rm -f version_sp.properties.backup
cp version_sp.properties version_sp.properties.backup

perl -p -i -e "s/\=${buildCnt}/\=${buildNext}/" version_sp.properties
buildCnt=${buildNext}

cd ${sourceDir}
export svnlatest=`svn info |grep Revision: |cut -c11-`
cd ${sourceDir}/build
perl -p -i -e "s/env\.BE_REVISION\=.*/env\.BE_REVISION\=${svnlatest}/" version_sp.properties

export buildTag=`grep "env.BE_BUILD=" version_sp.properties | cut -f 2 -d=`
echo "Build Tag=" $buildTag
export buildFldr=`grep "env.BE_BUILD=" version_sp.properties | cut -b 14-16`
export buildNum=${version}.${buildTag}
export revision=`grep "env.BE_REVISION=" version_sp.properties | cut -f 2 -d=`

# Build trigger notification
rm -f build.mail
cp ${enggDir}/blank.txt build.mail
mail ${buildersemailaddr} -s "BE Jenkins Build ${buildNum} started at ${BUILD_URL}" < build.mail
echo "[`date`] Build ${buildNum} Started..."
# --------------------------------------------------------------------------------------------------

export sourceDir="${WORKSPACE}/5.2"
cd ${sourceDir}/build
export bomBaseDir="${sourceDir}/build/pkg_info/bom/5.2.1"

export enggDir="${sourceDir}/build/engg_build"
major=`grep "BE_VERSION_MAJOR=" version_sp.properties | cut -f 2 -d=`
minor=`grep "BE_VERSION_MINOR=" version_sp.properties | cut -f 2 -d=`
update=`grep "BE_VERSION_UPDATE=" version_sp.properties | cut -f 2 -d=`
export version="${major}.${minor}.${update}"

export buildTag=`grep "env.BE_BUILD=" version_sp.properties | cut -f 2 -d=`
echo "Build Tag=" $buildTag
export buildFldr=`grep "env.BE_BUILD=" version_sp.properties | cut -b 14-16`
export buildNum=${version}.${buildTag}
export revision=`grep "env.BE_REVISION=" version_sp.properties | cut -f 2 -d=`
cd ${bomBaseDir}
export updated_bom_files=`svn diff | grep Index: | cut -c 8- | sed "s@^@${bomBaseDir}/@g"`

cd ${sourceDir}/build
svn commit --non-interactive --username=qlbuild --password=T!bco123 ${sourceDir}/runtime/modules/interpreter/src/com/tibco/cep/interpreter/cep_interpreterVersion.java ${sourceDir}/runtime/modules/channels/as/src/com/tibco/cep/driver/as/cep_as_channelVersion.java ${sourceDir}/runtime/modules/channels/hawk/src/com/tibco/cep/driver/hawk/cep_hawk_channelVersion.java ${sourceDir}/build/version_sp.properties ${sourceDir}/runtime/core/backingstore-bdb/src/com/tibco/cep/persister/be_bdbVersion.java ${sourceDir}/common/src/com/tibco/be/viewsrt/cep_viewsrtVersion.java ${sourceDir}/runtime/core/datagrid/activespaces/src/com/tibco/cep/as/cep_datagrid_tibcoVersion.java ${sourceDir}/runtime/core/datagrid/coherence/src/com/tibco/cep/cep_datagrid_oracleVersion.java ${sourceDir}/runtime/core/kernel/src/com/tibco/cep/kernel/cep_kernelVersion.java ${sourceDir}/runtime/core/container/src/com/tibco/cep/container/cep_containerVersion.java ${sourceDir}/runtime/core/common/src/com/tibco/cep/cep_commonVersion.java ${sourceDir}/runtime/core/functions/src/com/tibco/be/functions/be_functionsVersion.java ${sourceDir}/runtime/core/base/be/src/com/tibco/cep/cep_baseVersion.java ${sourceDir}/runtime/core/backingstore/src/com/tibco/be/jdbcstore/be_jdbcstoreVersion.java ${sourceDir}/runtime/core/backingstore/src/com/tibco/be/oracle/be_oracleVersion.java ${sourceDir}/runtime/core/drivers/src/com/tibco/cep/driver/cep_driversVersion.java ${sourceDir}/runtime/modules/interpreter/src/com/tibco/cep/interpreter/cep_interpreterVersion.java ${sourceDir}/runtime/modules/tools/migration/src/com/tibco/be/dbutils/be_dbutilsVersion.java ${sourceDir}/runtime/modules/tools/migration/src/com/tibco/be/migration/cep_migrationVersion.java ${sourceDir}/runtime/modules/rms/src/com/tibco/be/rms/cep_rmsVersion.java ${sourceDir}/runtime/modules/dbconcepts/src/com/tibco/cep/modules/cep_modulesVersion.java ${sourceDir}/runtime/modules/query/common/src/com/tibco/cep/query/cep_queryVersion.java ${sourceDir}/runtime/modules/dashboard/metricengine/src/com/tibco/cep/metric/cep_metricVersion.java ${sourceDir}/runtime/modules/dashboard/agent/common/src/com/tibco/cep/dashboard/cep_dashboardVersion.java ${sourceDir}/runtime/modules/loadbalancer/loadbalancer-be/src/com/tibco/cep/loadbalancer/cep_loadbalancerVersion.java ${sourceDir}/runtime/modules/security/src/com/tibco/cep/security/cep_securityVersion.java ${sourceDir}/runtime/modules/bw/src/com/tibco/be/bw/plugin/be_bwVersion.java ${sourceDir}/runtime/modules/bpmn/src/com/tibco/cep/bpmn/cep_bpmnVersion.java ${sourceDir}/runtime/modules/pattern/pattern-be/src/com/tibco/cep/pattern/cep_patternVersion.java ${sourceDir}/runtime/mm/administrator/src/com/tibco/be/deployment/administrator/be_adminVersion.java ${sourceDir}/runtime/mm/em/src/com/tibco/be/bemm/functions/be_mmVersion.java ${sourceDir}/common/src/com/tibco/be/uirms/cep_uirmsVersion.java ${sourceDir}/common/src/com/tibco/be/uirt/cep_uirtVersion.java ${sourceDir}/designtime/studio/plugins/com.tibco.cep.studio.application/about.mappings ${sourceDir}/designtime/studio/functions/src/com/tibco/be/functions/be_studiofunctionsVersion.java ${updated_bom_files} -m "TIBCO BusinessEvents Nightly Build Version ${buildNum}"

#### SVN Update command to be added ####

# ---------------------------------------------------------------------------------------------------



# Compilation successful notification
export sourceDir="${WORKSPACE}/5.2"
cd ${sourceDir}/build
export enggDir="${sourceDir}/build/engg_build"
major=`grep "BE_VERSION_MAJOR=" version_sp.properties | cut -f 2 -d=`
minor=`grep "BE_VERSION_MINOR=" version_sp.properties | cut -f 2 -d=`
update=`grep "BE_VERSION_UPDATE=" version_sp.properties | cut -f 2 -d=`
export version="${major}.${minor}.${update}"
export assembliesExportDir="/mnt/ql/build/BE/leo/packaging/${version}"

export buildTag=`grep "env.BE_BUILD=" version_sp.properties | cut -f 2 -d=`
echo "Build Tag=" $buildTag
export buildFldr=`grep "env.BE_BUILD=" version_sp.properties | cut -b 14-16`
export buildNum=${version}.${buildTag}
export revision=`grep "env.BE_REVISION=" version_sp.properties | cut -f 2 -d=`

export osLin=linux26gl25
export archLin32=x86

export buildersemailaddr="mgujrath@tibco.com,vasharma@tibco.com,sbagi@tibco.com,bgokhale@tibco.com,abhave@tibco.com"
export successemailaddr="ql-build@tibco.com,be-qa@tibco.com,stg-engr@tibco.com,dyamashi@tibco.com,jpark@tibco.com,mqian@tibco.com,be-xsg@tibco.com,
magrawal@tibco.com,jakaur@tibco.com"

cd ${sourceDir}/build
rm -f build.mail
cp ${enggDir}/GenericAssemblies.mail build.mail
mail ${buildersemailaddr} -s "BE Jenkins Build - ${buildNum} - Compilation successful" < build.mail

# Export assemblies
echo "[`date`] Exporting assemblies to ${assembliesExportDir}..."
rm -rf ${assembliesExportDir}/*
cp -r ${sourceDir}/build/Enterprise/exp/${osLin}/${archLin32}/dbg/Final/* ${assembliesExportDir}

echo "[`date`] Exporting assemblies done."

chmod 777 ${enggDir}/integration-preflight-tool/linux_x86/ectool

# -----------------------------------------------------------------------------------------------------------

export sourceDir="${WORKSPACE}/5.2"
cd ${sourceDir}/build
export buildDir="${sourceDir}/build/Enterprise/exp/linux26gl25/x86/dbg"
export enggDir="${sourceDir}/build/engg_build"
major=`grep "BE_VERSION_MAJOR=" version_sp.properties | cut -f 2 -d=`
minor=`grep "BE_VERSION_MINOR=" version_sp.properties | cut -f 2 -d=`
update=`grep "BE_VERSION_UPDATE=" version_sp.properties | cut -f 2 -d=`
bpmn_major=`grep "BE_BPMN_MAJOR=" version_sp.properties | cut -f 2 -d=`
bpmn_minor=`grep "BE_BPMN_MINOR=" version_sp.properties | cut -f 2 -d=`
bpmn_update=`grep "BE_BPMN_UPDATE=" version_sp.properties | cut -f 2 -d=`
hotfix=`grep "BE_VERSION_HOTFIX=" version_sp.properties | cut -f 2 -d=`
export version="${major}.${minor}.${update}"
export bpmn_version="${bpmn_major}.${bpmn_minor}.${bpmn_update}"

export buildTag=`grep "env.BE_BUILD=" version_sp.properties | cut -f 2 -d=`
echo "Build Tag=" $buildTag
export buildFldr=`grep "env.BE_BUILD=" version_sp.properties | cut -b 14-16`
export buildNum=${version}.${buildTag}
export revision=`grep "env.BE_REVISION=" version_sp.properties | cut -f 2 -d=`

export releaseBaseDir="/mnt/testinst"
export releaseDir="/mnt/ql/build/BE/${major}.${minor}"

export buildersemailaddr="mgujrath@tibco.com,vasharma@tibco.com,sbagi@tibco.com,bgokhale@tibco.com,abhave@tibco.com"
export successemailaddr="ql-build@tibco.com,be-qa@tibco.com,stg-engr@tibco.com,dyamashi@tibco.com,jpark@tibco.com,mqian@tibco.com,be-xsg@tibco.com,
magrawal@tibco.com,jakaur@tibco.com"

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
mail ${successemailaddr} -s "BE Jenkins Build - ${buildNum}" < build.mail

mkdir -p /BE_Builds/builder_build_notifications/5.2/test-installers/${version}.${buildFldr}/${revision}