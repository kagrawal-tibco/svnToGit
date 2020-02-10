<project name="ec_client_call" basedir=".">
	<import file="ec_client_call_common.xml"/>
	<target name="package" depends="setConditions,setwin,setlnx,setmac,-testClientExistence,-getClients" >
         <echo> ostype ${ostype} </echo>
         <antcall target="login" />
	 	
		<FULL-test-installpackaging-procedure 
	 	 	enable-zipassembly-creation="true" 
			svn-url="http://svn.tibco.com/be/branches/5.2/build/pkg_info/bom/5.2.0/businessevents-standard/base" 
			svn-revision="HEAD"
			featureconfig-file="businessevents-standard_FeatureConfig.xml"
			build-type="full build"
			ports="win/x86 win/x86_64 linux26gl25/x86 linux26gl25/x86_64 aix61/power_64"
			recipient-email-address="vbarot@tibco.com" 
			assembly_id="all"
			override-efault-export-dir="/ql/build/BE/leo/packaging/5.2.0"
			override-assembly-build-number="@BE_BUILD@"/>
		 <echo> Please see status of job at https://commander.na.tibco.com:5443/commander/jobs.php </echo>	 	
	</target>
</project>