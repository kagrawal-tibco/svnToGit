echo "Batch Started"
#Deleting Files from previous Run
#-----------------------------------------------------------------------
if [ -f "commitList.txt" ] ; then
	rm "commitList.txt"
fi
if [ -f "commitList.old" ] ; then
	rm "commitList.old"
fi
if [ -f "commitList.new" ] ; then
	rm "commitList.new"
fi
if [ -f "sourceList.txt" ] ; then
	rm "sourceList.txt"
fi
if [ -f "sourceList.old" ] ; then
	rm "sourceList.old"
fi
if [ -f "sourceList.new" ] ; then
	rm "sourceList.new"
fi
if [ -f "jarList.txt" ] ; then
	rm "jarList.txt"
fi
if [ -f "jarList.old" ] ; then
	rm "jarList.old"
fi
if [ -f "jarList.new" ] ; then
	rm "jarList.new"
fi
#-----------------------------------------------------------------------#



#-----------------------------------------------------------------------
echo Initializing empty files
touch commitList.txt 
touch jarList.txt 
touch sourceList.txt 
#-----------------------------------------------------------------------#


#-----------------------------------------------------------------------
#Local Vars
#this var is used to exclude build related commits
buildPath=/branches/5.2/build
excludePattern1=cep_
excludePattern2=be_
svnStartRev=69931
svnEndRev=HEAD
svnLogXmlLoc=$(pwd)/log.xml
batchLocation=$(pwd)
bomFileLoc=$(pwd)/bom-be-5.1.xml
echo Generating SVN logs between the following revisions : $svnStartRev - $svnEndRev
#Update this var to the location where the source files are checked out
svnRoot=/BE_Development/svn/5.2
#-----------------------------------------------------------------------#


#-----------------------------------------------------------------------
echo Updating SVN Logs
#Dumps the logs between the given revisions into log.xml
cd $svnRoot
svn log --xml -v -r $svnStartRev:$svnEndRev>$svnLogXmlLoc
cd $batchLocation
#-----------------------------------------------------------------------#


#-----------------------------------------------------------------------#
echo Analyzing SVN logs for any modifications in the source folders

count=$(xml sel -t  -v 'count(//path)' "$svnLogXmlLoc") 
echo Total Commits : $count
#-----------------------------------------------------------------------#


#-----------------------------------------------------------------------#
echo Excluding Build related and other similar commits
while read -r svnPath
do
	
	if [ -n "$svnPath" ] ; then
		if [ "${svnPath/$buildPath}" == "$svnPath" ] ; then
			
			result="false"
			fileName=$(basename $svnPath)
			
			if [ "${fileName/$excludePattern1}" != "$fileName" ] ; then
				result="true"
				#echo "Pattern One Present"
			fi
			
			if [ "${fileName/$excludePattern2}" != "$fileName" ] ; then
				result="true"
				#echo "Pattern Two Present"
			fi
			
			if [ "$result" == "false" ] ; then
					echo $svnPath>>commitList.txt
			fi
		fi
	fi
done < <(xml sel -t -m "//path" -v "." -n "$svnLogXmlLoc")
#----------------------------------------------------------------------#


#-----------------------------------------------------------------------
echo Removing redundant data from commitList.txt
sort -u commitList.txt>commitList.new
mv commitList.txt commitList.old
mv commitList.new commitList.txt
rm -f commitList.old
#-----------------------------------------------------------------------#


#-----------------------------------------------------------------------#
echo Populating Jar and Source List files
# since the xml file has namespace url included , it needs to be included here also.And the name chose needs to be prefixed to the seatch parameter
while read -r fullStr
do
	if [ -n "$fullStr" ] ; then
		subSource=
		name=
		valArr=(${fullStr//#/ })
		subSource="${valArr[0]}"
		name="${valArr[1]}"

		grep -q "$subSource" commitList.txt                                                                 
		if [ $? -eq 0 ] ; then
			echo "$name"$'\r'>>jarList.txt
			echo "$subSource"$'\r'>>sourceList.txt
		else
			dummy=
		fi
	fi
done < <(xml sel -N "xs=http://tibco.com/businessevents/releases/bom/5.1" -t -m "//xs:file-set" -v "xs:source" -o "#" -v "@name" -n "$bomFileLoc")
#-----------------------------------------------------------------------#



#---------------------------------------------------------------------#
echo Filtering files to remove redundant data

sort -u sourceList.txt>sourceList.new
mv sourceList.txt sourceList.old
mv sourceList.new sourceList.txt
rm -f sourceList.old

sort -u jarList.txt>jarList.new
mv jarList.txt jarList.old
mv jarList.new jarList.txt
rm -f jarList.old

#---------------------------------------------------------------------#

echo "Batch Processing Finished"






