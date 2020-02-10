echo "Starting Script"
while true
do

rm -r old_shared.properties
rm -r old_build.xml
\cp shared.properties old_shared.properties
\cp build.xml old_build.xml
sed -i -- 's/shared.properties/old_shared.properties/g' old_build.xml
svn update
for target in `ant -p`; 
do
	if [[ $target =~ "showenv-" && $target != "showenv-all" && $target != "showenv-external" && $target != "showenv-internal" ]] ; then
    	echo $target
		rm -r prev.txt
		rm -r new.txt
		/tsi-clean/tsi/root/external/ant/1.7.1/bin/ant -f old_build.xml $target > prev.txt
    	/tsi-clean/tsi/root/external/ant/1.7.1/bin/ant -f build.xml $target > new.txt
		
		cat prev.txt>>prev_all.txt
		cat new.txt>>new_all.txt

		echo $(diff prev.txt new.txt)
		echo $DIFF;
		sleep 10;
		if [[ $(diff prev.txt new.txt) =~ "_LIB" || $(diff prev.txt new.txt) =~ "_VERSION" ]] ; then
		
	    	echo "inside......................."
        	strsync="sync"
	    	target=${target/showenv/$strsync}
			echo "NEW TARGET : $target"
			
			if [ -d "/tsi_download_in_progress/tsi/root" ]
			then
			    echo "Directory /tsi_download_in_progress/tsi/root exists."
			else
				echo "Starting sync... for $target"
	 	        /tsi-clean/tsi/root/external/ant/1.7.1/bin/ant $target
			    cp -rT /tsi_download_in_progress/tsi/root /tsi-clean/tsi/root
			    rm -rf /tsi_download_in_progress/tsi/root			 			
			    /tsi-clean/tsi/root/external/ant/1.7.1/bin/ant -Denv.PORT=win/x86_64 $target
			 	cp -rT /tsi_download_in_progress/tsi/root /tsi-clean/tsi/root
			    rm -rf /tsi_download_in_progress/tsi/root
			fi
	 	fi
      fi
done		
sleep 18000
echo " going for sleep....."
done
