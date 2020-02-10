#!/bin/sh
#
# $HeadURL$ $Revision$ $Date$
#
# Copyright(c) 2004-2013 TIBCO Software Inc. All rights reserved.
#
# environment set up for Unix, Linux and Windows (cygwin)
#

# set -x

# Unix/Linux/Windows:

uname_s="`uname -s`"

case ${uname_s} in
Linux*)
  ISLINUX=1
  ISUNIX=1
  ISWINDOWS=0
  ISMACOSX=0
  ;;
CYGWIN32_NT|CYGWIN_NT-[45].*)
  ISLINUX=0
  ISUNIX=0
  ISWINDOWS=1
  ISMACOSX=0
  ;;
Darwin)
  ISLINUX=0
  ISUNIX=1
  ISWINDOWS=0
  ISMACOSX=1
  ;;
*)
  ISLINUX=0
  ISUNIX=1
  ISWINDOWS=0
  ISMACOSX=0
  ;;
esac
export ISLINUX ISUNIX ISWINDOWS ISMACOSX

if [ ${ISWINDOWS} -eq 1 ] ; then
  LOCAL_ROOT="C:/root"
  NET_ROOT="R:"
  CYGPATH="cygpath -m"
else
  LOCAL_ROOT="/local/root"
  NET_ROOT="/tsi/root"
  CYGPATH="echo"
fi
export LOCAL_ROOT NET_ROOT CYGPATH

BASEDIR="`dirname ${0}`"
BASEDIR="`'cd' ${BASEDIR} ; pwd`"
BASEDIR="`dirname ${BASEDIR}`"
BASEDIR="`${CYGPATH} ${BASEDIR}`"
export BASEDIR

SRC_ROOT="${BASEDIR}"
export SRC_ROOT

HOST="${HOST:-`hostname`}"
export HOST

VPROP="${BASEDIR}/build/version.properties"
export VPROP

SPROP="${BASEDIR}/build/shared.properties"
export SPROP

PRODUCT="`grep PRODUCT= ${SPROP} | head -1 | cut -d'=' -f2`"
export PRODUCT

  ANT_VERSION="`grep env.ANT_VERSION=   ${SPROP} | cut -d'=' -f2`"
  JDK_VERSION="`grep env.JDK_VERSION=   ${SPROP} | cut -d'=' -f2`"
 USER_VERSION="`grep env.USER_VERSION=  ${SPROP} | cut -d'=' -f2`"

_BE_VERSION="`grep BE_VERSION_MAJOR= ${VPROP} | \
	cut -d'=' -f2`.`grep BE_VERSION_MINOR= ${VPROP} | cut -d'=' -f2`"
BE_VERSION="${_BE_VERSION}.`grep BE_VERSION_UPDATE= ${VPROP} | cut -d'=' -f2`"
export _BE_VERSION BE_VERSION

BE_BUILD="`grep env.BE_BUILD= ${VPROP} | cut -d'=' -f2`"
_BE_BUILD="`expr ${BE_BUILD} + 0`"
export BE_BUILD _BE_BUILD

# Subversion:

SVN_VERSION="1.8.10"
if [ ${ISWINDOWS} -eq 1 ] ; then
	SVN_VERSION="1.8.8"
else
	if [ ${ISMACOSX} -eq 1 ] ; then
		SVN_VERSION="1.6.17"
	fi
fi
export SVN_VERSION

export ANT_VERSION JDK_VERSION SVN_VERSION USER_VERSION

ANT_OPTS="-Xmx1024M"
export ANT_OPTS

BE_EDITION="Enterprise"
_BE_EDITION="epe"
export BE_EDITION _BE_EDITION

BE_LICENSE=""
export BE_LICENSE

BUILD_TYPE="${BUILD_TYPE:-dbg}"
export BUILD_TYPE

LOGDIR="${BASEDIR}/build/logs"
export LOGDIR

[ -d ${LOGDIR} ] || mkdir -p ${LOGDIR}

if [ ${ISLOCAL} -eq 1 ] ; then
  TSI_ROOT="${LOCAL_ROOT}"
else
  TSI_ROOT="${NET_ROOT}"
fi
export TSI_ROOT

SYNC_ROOT="${NET_ROOT}"
export SYNC_ROOT

TOOLS_ROOT="${TSI_ROOT}"
export TOOLS_ROOT

PKG_ROOT="${TSI_ROOT}/pkg"
export PKG_ROOT

EXTERNAL="${TSI_ROOT}/external"
export EXTERNAL

SYNC_EXTERNAL="${SYNC_ROOT}/external"
export SYNC_EXTERNAL

INTERNAL="${TSI_ROOT}/tibco"
export INTERNAL

SYNC_INTERNAL="${SYNC_ROOT}/tibco"
export SYNC_INTERNAL

if [ ${ISLOCAL} -eq 1 ] ; then
  DOC_HOME=${INTERNAL}/doc
fi
export DOC_HOME

LOGIN_VERSION="${LOGIN_VERSION:-4.0.3}"
export LOGIN_VERSION

LOGIN_HOME="${INTERNAL}/login/${LOGIN_VERSION}"
export LOGIN_HOME

eval `${LOGIN_HOME}/set_port.sh sh`

[ ${ISMACOSX} -eq 1 ] && eval `${LOGIN_HOME}/reset_port.sh ${PORT}_64 sh`

JDK_HOME="${EXTERNAL}/jdk/${JDK_VERSION}"
export JDK_HOME

JAVA_HOME="${JDK_HOME}/${PORT}"
export JAVA_HOME

USER_HOME=${INTERNAL}/user/${USER_VERSION}
export USER_HOME

if [ ${ISWINDOWS} -eq 1 ] ; then
	PATH="`cygpath -u ${ANT_HOME}/${PORT}/bin`:`cygpath -u \
	${SVN_HOME}/${PORT}/bin`:`cygpath -u \
	${JDK_HOME}/${PORT}/bin`:`cygpath -u \
	${SRC_ROOT}/build`:${PATH}"
	JAVA_HOME="`cygpath -m ${JAVA_HOME}`"
else
	eval `${LOGIN_HOME}/set_ref.sh bash ant   ${ANT_VERSION}  `
	eval `${LOGIN_HOME}/set_ref.sh bash jdk   ${JDK_VERSION}  `
	eval `${LOGIN_HOME}/set_ref.sh bash svn   ${SVN_VERSION}  `
	PATH="${ANT_HOME}/${PORT}/bin:${SVN_HOME}/${PORT}/bin:${JDK_HOME}/${PORT}/bin:${PATH}"
fi
export PATH
