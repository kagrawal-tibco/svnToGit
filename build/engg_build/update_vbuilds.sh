#/usr/bin/sh
./update_vbuilds.pl vbuilds.log ../version.properties > vbuilds_new.log
cat vbuilds.log >> vbuilds_new.log
mv vbuilds_new.log vbuilds.log
