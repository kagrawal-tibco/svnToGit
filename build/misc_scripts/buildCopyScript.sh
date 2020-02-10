echo "Batch Started"
#------------------------------------------------------------------------------------------
echo "Initailizing variables"

#currrent BE Version
beVersion=5.2.2
#Update this var to change script's sleep time interval
sleepTime=1m
#This is the place a folder with build number is created through jenkins
folderPath=/BE_Builds/downloads/test-installers/$beVersion/
#This is the intermediatery copy location where the build is initially located
copyPath=/BE_Builds/installer_copy_progress/
#This the the home where the script, logs and the oldBuild.txt is located
homePath=/BE_Builds/copyInstallerScript/
#This is the location where the build/installer files are located
mountPath=/mnt/builder/testinst/businessevents-standard/$beVersion/test-installers/
#Final location where the full downloads will be copied for public access
finalInstallerLocation=/BE_Builds/test-installers/be/5.2
win="win"
linux="linux"
#------------------------------------------------------------------------------------------
cd $homePath
while true 
do
	startTime=$(date +"%T")
	echo "----------------Iteration Start time : $startTime"

	#------------------------------------------------------------------------------------------
	#Latest build number
	latestBuild=$(ls -t "$folderPath" | head -1)
	echo "Latest Folder : $latestBuild"
	if [ -z "$latestBuild" ]; then
		echo "Latest Folder empty , Initializing"
		latestBuild="$beVersion.000"
	fi
	#------------------------------------------------------------------------------------------
	echo "Getting previous build number"
	if [ -f "oldBuild.txt" ] ; then
		echo oldBuild.txt File Present
	else
		touch oldBuild.txt 
		echo -e "0.0.0" >> oldBuild.txt
	fi
	previousBuild=$(head -n 1 oldBuild.txt)
	#------------------------------------------------------------------------------------------
	
	echo "Last modified folder : $latestBuild"
	echo "Entry in oldBuild.txt : $previousBuild"
	
	if [ ! "$previousBuild" = "$latestBuild" ] ; then
		
		mountPath=/mnt/builder/testinst/businessevents-standard/$beVersion/test-installers/
		cd $mountPath
		newBuildFolder=$(ls -td */ |head -1)
		echo "Standard Build Present In : /mnt/builder/testinst/businessevents-standard/$beVersion/test-installers/$newBuildFolder"
		#------------------------------------------------------------------------------------------
		#The copy locations for each addon
		copyUrl[0]=/mnt/builder/testinst/businessevents-standard/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-standard_${beVersion}_win_x86_64.zip
		copyUrl[1]=/mnt/builder/testinst/businessevents-standard/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-standard_${beVersion}_linux26gl25_x86_64.zip
		
		mountPath=/mnt/builder/testinst/businessevents-datamodeling/$beVersion/test-installers/
		cd $mountPath
		newBuildFolder=$(ls -td */ |head -1)
		echo "Datamodeling Build Present In : /mnt/builder/testinst/businessevents-datamodeling/$beVersion/test-installers/$newBuildFolder"

		
		copyUrl[2]=/mnt/builder/testinst/businessevents-datamodeling/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-datamodeling_${beVersion}_win_x86_64.zip
		copyUrl[3]=/mnt/builder/testinst/businessevents-datamodeling/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-datamodeling_${beVersion}_linux26gl25_x86_64.zip
		
		mountPath=/mnt/builder/testinst/businessevents-decisionmanager/$beVersion/test-installers/
		cd $mountPath
		newBuildFolder=$(ls -td */ |head -1)
		echo "Decision Manager Build Present In : /mnt/builder/testinst/businessevents-decisionmanager/$beVersion/test-installers/$newBuildFolder"
		
		copyUrl[4]=/mnt/builder/testinst/businessevents-decisionmanager/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-decisionmanager_${beVersion}_win_x86_64.zip
		copyUrl[5]=/mnt/builder/testinst/businessevents-decisionmanager/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-decisionmanager_${beVersion}_linux26gl25_x86_64.zip
		
		mountPath=/mnt/builder/testinst/businessevents-eventstreamprocessing/$beVersion/test-installers/
		cd $mountPath
		newBuildFolder=$(ls -td */ |head -1)
		echo "ESP Build Present In : /mnt/builder/testinst/businessevents-eventstreamprocessing/$beVersion/test-installers/$newBuildFolder"
		
		copyUrl[6]=/mnt/builder/testinst/businessevents-eventstreamprocessing/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-eventstreamprocessing_${beVersion}_win_x86_64.zip
		copyUrl[7]=/mnt/builder/testinst/businessevents-eventstreamprocessing/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-eventstreamprocessing_${beVersion}_linux26gl25_x86_64.zip
		
		mountPath=/mnt/builder/testinst/businessevents-process/1.2.2/test-installers/
		cd $mountPath
		newBuildFolder=$(ls -td */ |head -1)
		echo "Process Build Present In : /mnt/builder/testinst/businessevents-process/$beVersion/test-installers/$newBuildFolder"
		
		copyUrl[8]=/mnt/builder/testinst/businessevents-process/1.2.2/test-installers/$newBuildFolder/TIB_businessevents-process_1.2.2_win_x86_64.zip
		copyUrl[9]=/mnt/builder/testinst/businessevents-process/1.2.2/test-installers/$newBuildFolder/TIB_businessevents-process_1.2.2_linux26gl25_x86_64.zip
		
		mountPath=/mnt/builder/testinst/businessevents-views/$beVersion/test-installers/
		cd $mountPath
		newBuildFolder=$(ls -td */ |head -1)
		echo "Views Build Present In : /mnt/builder/testinst/businessevents-views/$beVersion/test-installers/$newBuildFolder"
		
		copyUrl[10]=/mnt/builder/testinst/businessevents-views/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-views_${beVersion}_win_x86_64.zip
		copyUrl[11]=/mnt/builder/testinst/businessevents-views/$beVersion/test-installers/$newBuildFolder/TIB_businessevents-views_${beVersion}_linux26gl25_x86_64.zip
		
		cd $homePath
		#---------------------------------------------------------------------------------------------
		
		#---------------------------------------------------------------------------------------------
		echo "New Build Present.Starting Download at location $downloadPath for Build : $latestBuild"
		echo "Total Downloads : ${#copyUrl[@]}"
		#---------------------------------------------------------------------------------------------
		
		#---------------------------------------------------------------------------------------------
		echo "Making empty directories"
		mkdir -p $copyPath/$latestBuild
		mkdir -p $copyPath/$latestBuild/$win
		mkdir -p $copyPath/$latestBuild/$linux
		mkdir -p $finalInstallerLocation/$latestBuild
		#----------------------------------------------------------------------------------------------
		
		#----------------------------------------------------------------------------------------------
		echo "Copying each file"
		for index in "${!copyUrl[@]}"
			do
			echo "Copying : ${copyUrl[$index]}"
			if [[ ${copyUrl[$index]} == *"linux"* ]] ; then
				if [ -f "${copyUrl[$index]}" ] ; then
					cp  ${copyUrl[$index]} $copyPath/$latestBuild/$linux
				fi	
			else 
				if [ -f "${copyUrl[$index]}" ] ; then
					cp  ${copyUrl[$index]} $copyPath/$latestBuild/$win
				fi	
				#wget --user user --password pass --quiet --directory-prefix=$downloadPath/win ${copyUrl[$index]}
			fi
		done
		#----------------------------------------------------------------------------------------------
		echo "Copying all files to final location"
		cp -r $copyPath/$latestBuild $finalInstallerLocation/
		
		echo "Updating oldBuild.txt with $latestBuild"
		rm oldBuild.txt
		touch oldBuild.txt
		echo -e $latestBuild > oldBuild.txt
		
		buildNo=${latestBuild##*.}
		
		cd $homePath
		#sed 's/_beBuild_/${latestBuild}/g' < mail_template.txt >mail_temp.txt
		#sed 's/_beBuildNo_/${buildNo}/g' < mail_temp.txt >mail.txt
		
		sed -e s/"_beBuild_"/"${latestBuild}"/g < mail_template.txt >mail_temp.txt
		sed -e s/"_beBuildNo_"/"${buildNo}"/g < mail_temp.txt >mail.txt
		
		echo "Sending mail"
		sendmail -f "be_build@tibco.com" businesseventsengineering-pune@tibco.com,beindiaqa@tibco.com <mail.txt
		rm mail.txt
		rm mail_temp.txt
	fi
	endTime=$(date +"%T")
	echo "----------------Iteration End time : $endTime"
	sleep $sleepTime
done
echo "Batch Completed"