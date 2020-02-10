#!/bin/sh
#
# $HeadURL$ $Revision$ $Date$
# 
# Copyright© 2008-2008 TIBCO Software Inc. All rights reserved.
#

# set -x

echo "ENTERING installer.sh on `date`"

. setenv.sh

echo "EXITING installer.sh on `date`"

exit 0

BE_EDITIONS="${BE_EDITIONS:-Enterprise Inference}"

logdir="logs"

[ -d ${logdir} ] || mkdir -p ${logdir}

export BE_EDITION

for BE_EDITION in ${BE_EDITIONS} ; do
    gmake export_pkg 2>&1 | tee ${logdir}/export_pkg_${BE_EDITION}_${OS}.log
    find build/${BE_EDITION}/exp -type d -exec chmod ug+w {} \;
done
