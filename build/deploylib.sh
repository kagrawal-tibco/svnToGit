#!/bin/sh
#
# $HeadURL$
# $Revision$
# $Date$
#
# Copyright(c) 2012-2013 TIBCO Software Inc. All rights reserved.
#

# set -x

BASEDIR="`dirname ${0}`"
BASEDIR="`'cd' ${BASEDIR} ; pwd`"
BASEDIR="`dirname ${BASEDIR}`"
export BASEDIR

if [ "${1}" = "LOCAL" ] ; then
	shift 1
	ISLOCAL=1
else
	ISLOCAL=0
fi
export ISLOCAL

. ${BASEDIR}/build/setenv.sh

from="Enterprise/exp/linux26gl25/x86/dbg"

build="${INTERNAL}/be/5.2.0/L"

to="${build}/linux26gl25/x86"

[ -d ${from}/lib ] || exit 1

[ -d ${to} ] || exit 2

cp -rp ${from}/lib ${to}/lib

ln -s ../../linux26gl25/x86_64/lib ${build}/aix61/power_64/lib
ln -s ../../linux26gl25/x86_64/lib ${build}/hpux112/ia64/lib
ln -s ../../linux26gl25/x86_64/lib ${build}/linux26gl24/s390x/lib
ln -s ../../linux26gl25/x86_64/lib ${build}/macosx/x86_64/lib
ln -s ../../linux26gl25/x86_64/lib ${build}/sol/sparc_64/lib
ln -s ../../linux26gl25/x86_64/lib ${build}/sol/x86_64/lib
ln -s ../../linux26gl25/x86_64/lib ${build}/win/x86_64/lib

ln -s ../x86/lib ${build}/linux26gl25/x86_64/lib

ls -l ${build}/aix61/power_64/lib
ls -l ${build}/hpux112/ia64/lib
ls -l ${build}/linux26gl24/s390x/lib
ls -l ${build}/macosx/x86_64/lib
ls -l ${build}/sol/sparc_64/lib
ls -l ${build}/sol/x86_64/lib
ls -l ${build}/win/x86_64/lib

ls -l ${build}/linux26gl25/x86_64/lib

exit 0
