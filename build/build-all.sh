#!/bin/sh
#
# $HeadURL$ $Revision$ $Date$
#
# Copyright(c) 2004-2015 TIBCO Software Inc. All rights reserved.
#

# set -x

BASEDIR="`dirname ${0}`"
BASEDIR="`'cd' ${BASEDIR} ; pwd`"
BASEDIR="`dirname ${BASEDIR}`"
export BASEDIR

cmdline="${*}"

cflag=0
dflag=0
comflag=1

if [ "${1}" = "LOCAL" ] ; then
	shift 1
	ISLOCAL=1
else
	ISLOCAL=0
fi
export ISLOCAL

. ${BASEDIR}/build/setenv.sh

dollar0=`${CYGPATH} "${0}"`
bdollar0=`basename "${dollar0}"`
cmdline="${bdollar0} ${cmdline}"

case ${1} in
-nc)
	comflag=0
	shift 1
	;;
*)
	:
	;;
esac

task_list=""

case ${1} in
-T)
	shift 1
	task_list="${*}"
	;;
-hf)
	task_list="build-all build-assemblies-hotfix"
	cflag=1
	shift 1
	;;
-hfa)
	task_list="build-assemblies-hotfix"
	shift 1
	;;
-sp)
	task_list="build-all build-assemblies-servicepack build-studio-site"
	cflag=1
	shift 1
	;;
-spa)
	task_list="build-assemblies-servicepack"
	shift 1
	;;
-rp)
	task_list="repackage-all"
	shift 1
	;;
-rphf)
	task_list="repackage-all-hotfix"
	shift 1
	;;
-rpsp)
	task_list="repackage-all-servicepack"
	shift 1
	;;
-ball)
	task_list="build-all"
	shift 1
	;;
-bass)
	task_list="build-assemblies"
	shift 1
	;;
-bafhf)
	task_list="build-assemble-file-hotfix"
	shift 1
	;;
-obf)
	task_list="obfuscate-all"
	shift 1
	;;
-obfsj)
	task_list="obfuscate-studio-jars"
	shift 1
	;;
-obfsjv)
	task_list="-v obfuscate-studio-jars"
	shift 1
	;;
-ss)
	task_list="build-studio-site"
	shift 1
	;;
-doc)
	[ ${ISWINDOWS} -eq 1 ] && [ ${ISLOCAL} -eq 1 ] && task_list="sync-doc" && dflag=1
	shift 1
	;;
-mac)
	task_list="${task_list} build-all"
	cflag=1
	shift 1
	;;
*)
	task_list="build-all"
	[ ${ISLINUX} -eq 1 ] && task_list="${task_list} build-studio-site"
	cflag=1
	;;
esac

cd ${BASEDIR}

if [ ${dflag} -eq 1 ] ; then
	echo "####"
	echo "#### Removing Documentation local copy (`date`): ..."
	echo "####"
	echo
	ls -l ${DOC_HOME}/businessevents-*/${BE_VERSION}
	echo
	echo "rm -rf \"${DOC_HOME}/businessevents-*/${BE_VERSION}\""
	rm -rf ${DOC_HOME}/businessevents-*/${BE_VERSION}
fi

[ ${cflag} -eq 1 ] && \
for dir in exp obj ; do
	_dir="build/${BE_EDITION}/${dir}/${OS}"
	if [ -d ${_dir} ] ; then
		echo "####"
		echo "#### Cleaning ${BE_EDITION} ${dir} area `pwd`/${_dir} (`date`): ..."
		echo "####"
		/bin/rm -rf ${_dir} &
	fi
done

wait

if [ ${ISLINUX} -eq 1 ] ; then
	UNIX_ALL_PACKAGING_ENABLE="true"
	export UNIX_ALL_PACKAGING_ENABLE
fi

if [ ${ISWINDOWS} -eq 1 ] ; then
	WINDOWS_64BIT_PACKAGING_ENABLE="true"
	export WINDOWS_64BIT_PACKAGING_ENABLE
fi

USE_BEBW_EXTENDED_ACTIVITIES="true"
export USE_BEBW_EXTENDED_ACTIVITIES

BUILD_DOC="${BUILD_DOC:-on}"
export BUILD_DOC

cd build

BE_PRODUCT="`ant showenv-general | grep env.BE_PRODUCT | cut -d'[' -f3 | cut -d']' -f1`"
export BE_PRODUCT

COMMENT="${BE_PRODUCT} Version ${BE_VERSION}.${BE_BUILD}"
export COMMENT

logs="
	showenv-${BE_EDITION}-${OS}.log
	showenv-home-${BE_EDITION}-${OS}.log
	build-all-${OS}.log
"

for log in ${logs} ; do
	(
	echo '#'
	echo '# $'HeadURL'$'
	echo '# $'Revision'$'
	echo '# $'Date'$'
	echo '#'
	echo '# Copyright(c) 2010-2015 TIBCO Software Inc. All rights reserved.'
	echo '#'
	echo
	) > ${LOGDIR}/${log}
done

[ ${cflag} -eq 1 ] && \
(

echo "####"
echo "#### Environment (showenv): (`date`): ..."
echo "####"

ant showenv 2>&1

) 2>&1 | tee -a ${LOGDIR}/showenv-${BE_EDITION}-${OS}.log

[ ${cflag} -eq 1 ] && \
(

echo "####"
echo "#### References (showenv-home): (`date`): ..."
echo "####"

ant showenv-home 2>&1

) 2>&1 | tee -a ${LOGDIR}/showenv-home-${BE_EDITION}-${OS}.log

(

if [ -f ${LOGDIR}/rev-${_BE_EDITION}.log ] ; then
	svnrev="`cat ${LOGDIR}/rev-${_BE_EDITION}.log`"
else
	svnrev="HEAD"
fi

echo "####"
echo "#### Building ${COMMENT} (${svnrev}) (`date`): ..."
echo "####"
echo "#### `pwd`"
echo "####"
echo "#### `uname -a`"
echo "####"
echo "#### Command Line: ${cmdline}"
echo "####"
echo "#### ant ${task_list} 2>&1"
echo "####"

ant ${task_list} 2>&1
echo BUILD_STATUS=${?}

echo "####"
echo "#### Build of ${COMMENT} (${svnrev}) completed (`date`)."
echo "####"

) 2>&1 | tee -a ${LOGDIR}/build-all-${OS}.log

BUILD_STATUS="`grep BUILD_STATUS= ${LOGDIR}/build-all-${OS}.log | tail -1 | cut -d'=' -f2`"

[ -f tomcat.log ] && mv tomcat.log ${LOGDIR}/tomcat-${OS}.log

cd ${LOGDIR}

[ -f cep-engine.log ] && mv cep-engine.log cep-engine-${OS}.log

if [ ${BUILD_STATUS} -eq 0 ] ; then
	echo "####"
	echo "#### Checking License Deployment (`date`): ..."
	echo "####"
	_df="`grep \"Deployment Failed\" build-all-${OS}.log | wc -l`"
	if [ ${_df} -gt 0 ] ; then
		echo "####"
		echo "#### License Deployment *** FAILED *** (`date`)"
		echo "####"
		BUILD_STATUS=1
	else
		echo "####"
		echo "#### License Deployment OK (`date`)"
		echo "####"
	fi
fi

if [ ${comflag} -eq 1 ] ; then
	if [ ${BUILD_STATUS} -eq 0 ] ; then
		echo "####"
		echo "#### Committing Logs for ${COMMENT} (`date`): ..."
		echo "####"
		svn commit -m "${COMMENT}" *-${OS}.log rev-*.log obfuscation-logs-${OS}.zip
	fi
fi
