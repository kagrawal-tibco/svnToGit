export sourceDir="/home/vbarot/be-build/branches/5.1"
sp_version=1

export PATH=/mnt/ql-space/tsi-root/external/ant/1.7.1/linux26gl25/x86/bin:${PATH}

cd ${sourceDir}/build
export buildDir="${sourceDir}/build/Enterprise/exp/win/x86/dbg"
major=`grep "BE_VERSION_MAJOR=" version.properties | cut -f 2 -d=`
minor=`grep "BE_VERSION_MINOR=" version.properties | cut -f 2 -d=`
update=`grep "BE_VERSION_UPDATE=" version.properties | cut -f 2 -d=`
export version="${major}.${minor}.${update}"
export readmeversion="${major}.${minor}.${update}"
export sp="sp${sp_version}"
export enggDir="${sourceDir}/build/engg_build"
export logsDir="/home/vbarot/tmp/diag_dep_logs"
export releaseDir="/mnt/ql/build/BE/leo"
export assembliesExportDir="${releaseDir}/packaging/${version}"
export osWin=win
export archWin32=x86

export emailaddr="vbarot@tibco.com"

########  No Definitions beyond this line  ########

echo "Initializing Build..."

#cp ${enggDir}/user.properties.win32 ${sourceDir}/build/user.properties

cd ${sourceDir}/build
rm version.properties
svn up version.properties
cd ${sourceDir}
#${enggDir}/cleanupVersionFiles.sh
rm -rf ${sourceDir}/build/Enterprise
svn up

cd ${sourceDir}/build

# Build trigger notification
rm -f build.mail
cp ${enggDir}/blank.txt build.mail
#mail ${emailaddr} -s "Dep Diagramming Build - started" < build.mail

echo "[`date`] Build ${buildNum} Started..."
# Additional target build-ismp for installer build
time ant clobber-all clean-verf build-studio-diagramming-feature > ${logsDir}/status_build.log
buildRC=`grep -e '^BUILD SUCCESSFUL' ${logsDir}/status_build.log`
if [ "${buildRC}" = "" ];  then
   error_msg_2="Compilation failed, please check log in ${logsDir}/status_build.log."
   echo ${error_msg_2}
   mail ${emailaddr} -s "ATTENTION: BE ${version} Engineering Build - Compilation failed" < ${logsDir}/status_build.log
   exit 127
fi

# Compilation successful notification
#mail ${emailaddr} -s "Dep Diagramming Build - Compilation successful" < ${enggDir}/blank.txt

# Remove the Enterprise directory
cd ${sourceDir}/build
rm -rf ${sourceDir}/build/Enterprise 

echo "[`date`] Build Completed."