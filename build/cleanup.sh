#!/bin/sh
#
# $HeadURL$ $Revision$ $Date$
#

svn status --no-ignore ${*} | sort -u > _status

grep "^?" _status > _status_new

grep "^M" _status > _status_mod

grep "^A" _status > _status_add

grep "^I" _status > _status_ign

cat _status_new _status_ign | sort -u > __status

diff _status __status > _status_dif

_err="${?}"

grep "^[<>]" _status_dif | sed -e 's,^[<>] ,,' > __status_dif
mv __status_dif _status_dif

cat _status_new | sed -e 's,^\? ,,' > __status_new
mv __status_new _status_new

cat _status_ign | sed -e 's,^I ,,' > __status_ign
mv __status_ign _status_ign

echo "####"

if [ ${_err} -ne 0 ] ; then
	echo "#### ERROR! Found UNRESOLVED changes (`date`): ..."
else
	echo "#### Cleaning up: ${*} (`date`): ..."
fi

echo "####"
echo

if [ ${_err} -ne 0 ] ; then
	cat _status_dif
	echo
	exit 1
else
	cat _status_new _status_ign
	/bin/rm -rf `cat _status_new _status_ign`
fi

/bin/rm -f _status __status _status_new _status_ign _status_mod _status_add _status_dif

echo

exit 0
