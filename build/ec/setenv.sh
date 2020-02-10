#
# $HeadURL$ $Revision$ $Date$
#
# Copyright© 2008-2008 TIBCO Software Inc. All rights reserved.
#
# environment set up for Unix, Linux and Windows (cygwin)
#

# set -x

echo "ENTERING setenv.sh on `date`"

# SRC_ROOT="`dirname ${0}`"
SRC_ROOT="`pwd`"
SRC_ROOT="`cd ${SRC_ROOT}/../.. ; pwd`"

# Unix/Linux/Windows:

uname_s="`uname -s`"

case ${uname_s} in
Linux*)
  ISLINUX=1
  ISUNIX=1
  ISWINDOWS=0
  ;;
CYGWIN32_NT|CYGWIN_NT-[45].*)
  ISLINUX=0
  ISUNIX=0
  ISWINDOWS=1
  ;;
*)
  ISLINUX=0
  ISUNIX=1
  ISWINDOWS=0
  ;;
esac
export ISLINUX ISUNIX ISWINDOWS

if [ ${ISUNIX} -eq 1 ] ; then
  TSI_ROOT="${TSI_ROOT:-/tsi/root}"
  CYGPATH="echo"
else
  TSI_ROOT="${TSI_ROOT:-R:}"
  CYGPATH="cygpath -m"
fi
export TSI_ROOT CYGPATH

TOOLS_ROOT="${TSI_ROOT}"
export TOOLS_ROOT

PKG_ROOT="${TSI_ROOT}/pkg"
export PKG_ROOT

EXTERNAL="${EXTERNAL:-${TSI_ROOT}/external}"
export EXTERNAL

INTERNAL="${INTERNAL:-${TSI_ROOT}/tibco}"
export INTERNAL

LOGIN_VERSION="${LOGIN_VERSION:-4.0.0}"
export LOGIN_VERSION

LOGIN_HOME="${INTERNAL}/login/${LOGIN_VERSION}"
export LOGIN_HOME

eval `${LOGIN_HOME}/set_port.sh sh`

MINCLUDE_VERSION="2.0.0"
export MINCLUDE_VERSION

MINCLUDE_HOME="${INTERNAL}/minclude/${MINCLUDE_VERSION}"
export MINCLUDE_HOME

MINCLUDE="${MINCLUDE_HOME}/GNUmakefile"
export MINCLUDE

SRC_ROOT="`${CYGPATH} ${SRC_ROOT}`"
export SRC_ROOT

WORKAREA="${SRC_ROOT}"
export WORKAREA

BUILD_TYPE="${BUILD_TYPE:-dbg}"
export BUILD_TYPE

#upside# DISPLAY="10.105.137.31:0.0"
DISPLAY="${DISPLAY:-upside.na.tibco.com:0.0}"
export DISPLAY

# for ISMP Installer

PROD_ACRONYM="${PROD}"
PKG_ABBRV="TIBBE"
PROD_DIRNAME="${PROD}"
PROD_APPEND_VERSION="2"
ISMP_PKG_TYPE="simple"
ISMP_VERSION="5.0.3"
ISMP_BUILD_JAVA_HOME="${JDK_HOME}/${PORT}"

export PROD_ACRONYM PKG_ABBRV PROD_DIRNAME
export PROD_APPEND_VERSION ISMP_PKG_TYPE ISMP_VERSION
export ISMP_BUILD_JAVA_HOME

TIBCOINSTALLER_VERSION="5.0.7"
export TIBCOINSTALLER_VERSION

echo "EXITING setenv.sh on `date`"
