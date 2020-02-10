#!/bin/sh
#
# $HeadURL$ $Revision$ $Date$
#
# Copyright© 2008-2008 TIBCO Software Inc. All rights reserved.
#

# set -x

echo "ENTERING build-all.sh on `date`"

. setenv.sh

eval `${LOGIN_HOME}/set_ref.sh bash ant 1.6.5`
eval `${LOGIN_HOME}/set_ref.sh bash jdk 1.5.0_15`

echo "EXITING build-all.sh on `date`"

exit 0

logdir="`pwd`/logs"

[ -d ${logdir} ] || mkdir -p ${logdir}

export logdir

cd build

BUILD_DOC="${BUILD_DOC:-on}"
export BUILD_DOC

task_list="build-all"

export task_list

if [ "${OS}" = "win" ] ; then
        MSVS_IDE="${MSVS_IDE:-D:/Program Files/Microsoft Visual Studio 8/Common7/IDE}"
        export MSVS_IDE
        task_list="${task_list} build-docmif"
fi

if [ \( ${ISLINUX} -eq 1 \) -a \( "${HOST}" = "rellinux64" \) ] ; then
  # There are older local installations of ant and jdk on this machine, which
  # must not be used, but they come earlier in the path that the right ones:
  PATH="${ANT_HOME}/${PORT}/bin:${JDK_HOME}/${PORT}/bin:${PATH}"
fi
export PATH

BE_EDITIONS="${BE_EDITIONS:-Enterprise Inference}"
export BE_EDITIONS

build_epe=0
build_inf=0

for edition in ${BE_EDITIONS} ; do
    for dir in exp obj ; do
        _dir="${edition}/${dir}/${OS}"
        [ -d ${_dir} ] && \
        echo "Cleaning ${edition} ${dir} area: `pwd`/${_dir} (`date`) ..." && \
        /bin/rm -rf ${_dir} &
    done
    case ${edition} in
    Enterprise) build_epe=1 ;;
    Inference)  build_inf=1 ;;
    *)          :           ;;
    esac
done

wait

USE_BEBW_EXTENDED_ACTIVITIES="${USE_BEBW_EXTENDED_ACTIVITIES:-true}"
export USE_BEBW_EXTENDED_ACTIVITIES

BE_LICENSE="${BE_LICENSE:-}"
export BE_LICENSE

if [ ${ISWINDOWS} -eq 1 ] ; then
    BUI_BUILD_ENABLE="${BUI_BUILD_ENABLE:-true}"
    export BUI_BUILD_ENABLE
fi

if [ ${build_epe} -eq 1 ] ; then

    BE_EDITION="Enterprise"
    export BE_EDITION

    __BE_EDITION="Suite"
    export __BE_EDITION

    (

    echo "####"
    echo "#### ${BE_EDITION} ${__BE_EDITION} BUILD STARTED on `date`"
    echo "####"

    ant ${task_list} 2>&1

    eval `${LOGIN_HOME}/set_ref.sh bash jdk 1.4.2_13`

    if [ \( ${ISLINUX} -eq 1 \) -a \( "${HOST}" = "rellinux64" \) ] ; then
      PATH="${JDK_HOME}/${PORT}/bin:${PATH}"
    fi
    export PATH

    ant build-ismp 2>&1

    echo "####"
    echo "#### ${BE_EDITION} ${__BE_EDITION} BUILD COMPLETED on `date`"
    echo "####"

    ) | tee ${logdir}/build-all-${BE_EDITION}_${OS}.log

fi

if [ ${build_inf} -eq 1 ] ; then

    BE_EDITION=Inference
    export BE_EDITION

    __BE_EDITION="Edition"
    export __BE_EDITION

    unset BUI_BUILD_ENABLE

    if [ ${build_epe} -eq 1 ] ; then

        echo
        echo "####"
        echo "#### Update the source workarea with the code for the"
        echo "#### ${BE_EDITION} ${__BE_EDITION} build to proceed"
        echo "####"
        echo "#### HIT RETURN TO CONTINUE:"

        read cont

    fi

    (

    echo "####"
    echo "#### ${BE_EDITION} ${__BE_EDITION} BUILD STARTED on `date`"
    echo "####"

    ant ${task_list} 2>&1

    eval `${LOGIN_HOME}/set_ref.sh bash jdk 1.4.2_13`

    if [ \( ${ISLINUX} -eq 1 \) -a \( "${HOST}" = "rellinux64" \) ] ; then
      PATH="${JDK_HOME}/${PORT}/bin:${PATH}"
    fi
    export PATH

    ant build-ismp 2>&1

    echo "####"
    echo "#### ${BE_EDITION} ${__BE_EDITION} BUILD COMPLETED on `date`"
    echo "####"

    ) | tee ${logdir}/build-all-${BE_EDITION}_${OS}.log

fi

cleanup_list=""

if [ "${cleanup_list}" != "" ] ; then

    (

    echo "####"
    echo "#### Cleaning up on `date`"
    echo "####"

    echo

    for d in ${cleanup_list} ; do
	if [ -d "${d}" ] ; then
        	echo "Removing: \"${d}\""
        	/bin/rm -rf "${d}"
	fi
    done

    ) | tee ${logdir}/build-all-cleanup_${OS}.log

fi

cd ..
