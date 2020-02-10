#!/bin/sh
#
# $HeadURL$ $LastChangedRevision$ $LastChangedDate$
# 
# Copyright(c) 2007-2013 TIBCO Software Inc. All rights reserved.
#

# set -x

_msg ()
{
	echo
	echo "####"
	case ${1} in
	b)      shift 1
		echo "#### ${*} (`date`): ..."
		;;
	e)      shift 1
		echo "#### ${*} (`date`)."
		;;
	esac
	echo "####"
	echo
}

basedir="`dirname ${0}`"
basedir="`'cd' ${basedir} ; pwd`"
basedir="`dirname ${basedir}`"

if [ "${1}" = "LOCAL" ] ; then
	ISLOCAL=1
else
	ISLOCAL=0
fi
export ISLOCAL

pw="`pwd`"

. ${basedir}/build/setenv.sh ${1}

COMFLAG=1
export COMFLAG

case ${1} in
-nc)
	# Do not do any svn commit
	COMFLAG=0
	shift 1
	;;
*)
	:
	;;
esac

(

cd ${basedir}

task_list="pre-all"

cd build

BE_PRODUCT="`ant showenv-general | grep env.BE_PRODUCT | cut -d'[' -f3 | cut -d']' -f1`"
export BE_PRODUCT

comment="${BE_PRODUCT} Version ${BE_VERSION}.${BE_BUILD}"

echo ${comment} > ${LOGDIR}/_comment.tmp

_msg b "Preparing source for \"${comment}\""

ant ${task_list} 2>&1

_msg b Checking version files status

cd ..

svn status --no-ignore 2>&1 | egrep -v "build/logs|_comment.tmp" > _status.log

grep '^M' _status.log | sed -e 's,^M ,,' | \
	egrep "Version.java$|/about.mappings$|_FeatureConfig.xml$" \
	> _modified.log

grep '^M' _status.log | sed -e 's,^M ,,' | \
	egrep -v "Version.java$|/about.mappings$|_FeatureConfig.xml$" \
	> _skipping.log

/bin/rm -f _status.log

_msg b Skipping modified files

cat _skipping.log

/bin/rm -f _skipping.log

_msg b Checking version files diffs

[ -s _modified.log ] && svn diff `cat _modified.log`

if [ ${COMFLAG} -eq 1 ] ; then
	_msg b "Committing version files for \"${comment}\""
	svn commit -m "${comment}" `cat _modified.log`
fi

/bin/rm -f _modified.log

) 2>&1 | tee ${LOGDIR}/pre-all-${OS}.log

if [ ${COMFLAG} -eq 1 ] ; then
	comment="`cat ${LOGDIR}/_comment.tmp ; /bin/rm -f ${LOGDIR}/_comment.tmp`"
	svnrev="`grep \"Committed revision\" ${LOGDIR}/pre-all-${OS}.log | \
		head -1 | cut -d' ' -f3 | cut -d'.' -f1`"
	svnrev="`expr ${svnrev} + 1`"
	echo "${svnrev}" > ${LOGDIR}/rev-epe.log
        _msg b "Committing Logs for ${comment}"
        cd ${LOGDIR}
        svn commit -m "${comment}" pre-all-${OS}.log rev-epe.log
fi

cd ${pw}

exit 0
