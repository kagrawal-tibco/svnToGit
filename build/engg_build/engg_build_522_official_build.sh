#/usr/bin/sh

#-----Updating vbuilds.log
#---------------------------------------------------------------------
./update_vbuilds.pl vbuilds.log ../version_sp.properties > vbuilds_new.log
cat vbuilds.log >> vbuilds_new.log
mv vbuilds_new.log vbuilds.log
#---------------------------------------------------------------------


#-----Building mail Template with Jira URLs
#---------------------------------------------------------------------
export sourceDir="/var/lib/jenkins/workspace/BE522/5.2"
export enggDir="${sourceDir}/build/engg_build"

#export successemailaddr="businesseventsengineering-pune@tibco.com,be-qa@tibco.com,aamaya@tibco.com,fildiz@tibco.com,anpatil@tibco.com,abhave@tibco.com,rhollom@tibco.com,akunchef@tibco.com,kbalagur@tibco.com,mqian@tibco.com,magrawal@tibco.com,sjahagir@tibco.com,rshroff@tibco.com"

mailaddr="mgujrath@tibco.com,bgokhale@tibco.com,abhave@tibco.com,vasharma@tibco.com"
cd ${sourceDir}/build

export buildDir="${sourceDir}/build/Enterprise/exp/linux26gl25/x86/dbg"
major=`grep "BE_VERSION_MAJOR=" version_sp.properties | cut -f 2 -d=`
minor=`grep "BE_VERSION_MINOR=" version_sp.properties | cut -f 2 -d=`
update=`grep "BE_VERSION_UPDATE=" version_sp.properties | cut -f 2 -d=`
hotfix=`grep "BE_VERSION_HOTFIX=" version_sp.properties | cut -f 2 -d=`
export version="${major}.${minor}.${update}"

echo "Version:"$version

cd ${sourceDir}/build
export buildTag=`grep "env.BE_BUILD=" version_sp.properties | cut -f 2 -d=`
export buildFldr=`grep "env.BE_BUILD=" version_sp.properties | cut -b 14-16`
export buildNum=${version}.${buildTag}
echo "Build Tag=" $buildTag
echo "Build Folder="$buildFldr
echo "Build Number="$buildNum


# Send email notification
cd ${sourceDir}/build/engg_build
echo "Sending email notification..."
rm -f build.mail
cp ${enggDir}/GenericBuild_official.mail build_official.mail
# perl -p -i -e "s/Build-/Installer Build-/" build.mail
buildId=`echo ${buildTag} | cut -f 2 -d.`

if [[ $buildId == 0* ]]; then 
	buildNoShort=${buildId#"0"}
	echo "Short Build Numer:"$buildNoShort
else 
	buildNoShort=$buildId
fi

perl -p -i -e "s/xxx/${buildId}/" build_official.mail
perl -p -i -e "s/zzz/${buildNoShort}/" build_official.mail

chmod 777 ${sourceDir}/build/engg_build/get_jira_urls.pl
cd ${sourceDir}/build/engg_build
export jiraurls=`perl get_jira_urls.pl vbuilds.log ${buildNum}`
perl -p -i -e 's/XXX/$ENV{jiraurls}/' build_official.mail
mail ${mailaddr} -s "BE Official Build- BE ${buildNum}" < build_official.mail -- -f "be_build@tibco.com"

#---------------------------------------------------------------------

#------Marking JIRAs with resolver Builds
#---------------------------------------------------------------------
cd ${sourceDir}/build/engg_build
echo "Updating jira's.........."
chmod 777 ${sourceDir}/build/engg_build/mark_resolver_builds.pl
#perl mark_resolver_builds.pl vbuilds.log ${buildNum} $3 $4
#---------------------------------------------------------------------