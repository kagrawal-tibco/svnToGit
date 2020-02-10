#!/bin/sh
#
# $HeadURL$ $LastChangedRevision$ $LastChangedDate$
# 
# Copyright(c) 2006-2013 TIBCO Software Inc. All rights reserved.
#
# =====================================
# Set *_BUILD macros for a given *_HOME
# =====================================
#

ISLOCAL=${ISLOCAL:-0}

cd "${1}"
if [ ${ISLOCAL} -eq 1 ] ; then
	[ -f V.txt ] && cat V.txt
else
	if [ -h L ] ; then
		ls -l L | cut -d '>' -f2- | cut -d' ' -f2-
	else
		echo "L"
	fi
fi
