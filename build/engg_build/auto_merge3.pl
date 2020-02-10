#!/usr/bin/perl
#
#
# Auto-merge script. Looks for changes in the $SRC_BR branch and applies them one by one to $TRGT_BR using "svn merge -c"
#
# This script should run from the BE $SRC_BR root folder as it uses relative paths
# 
#
#
use JSON;
use strict;
#require "build/engg_build/parseJsonFn.pl";this file is now appended here

die "Usage: auto_merge.pl <soruce working-dir> <source branch> <target working-dir> <target-branch> <clonestring> <target-version> <jira-username> <jira-password>" if (scalar @ARGV < 8);

my $SRC_DIR = $ARGV[0];
my $SRC_BR = $ARGV[1];
my $TRGT_DIR = $ARGV[2];
my $TRGT_BR = $ARGV[3];
my $CLONE_STR = $ARGV[4];
my $TARGET_VER = $ARGV[5];
my $jiraUsr = $ARGV[6];
my $jiraPwd = $ARGV[7];
my $fromVer = $ARGV[8];
my $uptoVer = $ARGV[9];
if ( ! -e $SRC_DIR) {
  print "Invalid path to BE $SRC_DIR workdir: $SRC_DIR\n";
  exit;
}
if ( ! -e $TRGT_DIR) {
  print "Invalid path to BE $TRGT_DIR workdir: $TRGT_DIR\n";
  exit;
}

my $BE_SVN = "https://svn.tibco.com/svn/be";

my $jiraUrl = "https://jira.tibco.com";
my $SRC_SVN_URL = $BE_SVN.$SRC_BR;
my $TRGT_SVN_URL = $BE_SVN.$TRGT_BR;
my $sendMailAddrs = "bgokhale\@tibco.com,rjain\@tibco.com";
#my $sendMailAddrs = "businesseventsengineering-pune\@tibco.com,aamaya\@tibco.com,fildiz\@tibco.com,anpatil\@tibco.com,abhave\@tibco.com,rhollom\@tibco.com";  #"bgokhale\@tibco.com,mgujrath\@tibco.com,rjain\@tibco.com";
my $fromMailAddr = "be_build\@tibco.com";


my @excludePatterns = fileToList ("build/engg_build/exclude_patterns_for_53_merge.txt");

#bala@bgokhale ~/private/bala/5.3 $ svn propget svn:mergeinfo|grep '$SRC_BR'
#/branches/5.2:69695-71870,72340-72341,72343,72353,72358,72361,72370

#executing svn revert at the start of script to clean the $TRGT_BR repository
my $svnRevertCmd = "cd $TRGT_DIR; svn revert -R . 2>&1";
my $svnRevertOp = `$svnRevertCmd`;
print "Svn revert Command's ouput : $svnRevertOp \n\n";


my $svnCmd = "cd $TRGT_DIR; svn up > /dev/null; svn propget svn:mergeinfo|grep '$SRC_BR' 2>&1";
print "$svnCmd\n";
my $svnLog = `$svnCmd`;
print "$svnLog\n";

my $merges = $svnLog;
$merges =~ s/$SRC_BR://g;

#break it into - and ,
my @mergedRevs = split (',|-', $merges);

#get the latest one..svn arranges them in ascending order, so the last one is good for use
my $lastMerged = $mergedRevs[ (scalar @mergedRevs) - 1];
chomp $lastMerged;
print "\#\#\#r$lastMerged\#\#\#\n";
$lastMerged += 1;
chomp $lastMerged;
if ($lastMerged < 10) {
  die "Error in computing last merged for branch $TRGT_BR. Will not proceed\n";
}

if ($fromVer ne "0") {
  $lastMerged = $fromVer;
}

my $svnLogsSinceLastMerge = "cd $SRC_DIR; svn log -r$lastMerged:$uptoVer | grep -P \"^r\\d+\"";

print "$svnLogsSinceLastMerge\n";

my $svnLogsOp = `$svnLogsSinceLastMerge`;

print $svnLogsOp;

my @revs;
my %revsToCommitBy;
my %jiras;
my @lines = split ("\n", $svnLogsOp);
foreach my $line (@lines) {
  if ($line =~ /(r\d+)\s+\|\s+(\w+)/) {
    my $rev = $1;
    my $commitBy = $2;
    push @revs, $rev;
    $revsToCommitBy{$rev} = $commitBy;
    #my @jirasForRev = getJirasForRev($rev);
    #foreach my $jira (@jirasForRev) {
      #$jiras{$jira} = $jira;
    #}
  }
}
#my $tot = scalar keys %jiras;
#print "Total Jiras = $tot\n";
#exit;
foreach my $rev (@revs) {
  my $svnLogCmd = "cd $SRC_DIR; svn log -v -r$rev";
  my $svnLogOp = `$svnLogCmd`;
  my $shouldMrg = shouldMerge ( \@excludePatterns, $rev, $svnLogOp);
  if ($shouldMrg == 1) {
    my ($mergeSuccess, $svnMergeLog) = tryMerge ($jiraUrl, $jiraUsr, $jiraPwd, $rev, $svnLogOp);
	
    if ($mergeSuccess == 1) { # success!!
    } elsif ($mergeSuccess == 0) {
      print "Conflict detected while trying to merge $rev. Auto-merge aborted. $svnMergeLog\n";

      #Send an email..
      sendConflictEmail($rev, $svnLogOp, $svnMergeLog);
      #and break out of the merge process till the conflict is manually resolved.
      last;
    } 
    elsif ($mergeSuccess == 3) { #indicates some error with jira calls
      print "Error while making Jira calls. Auto-merge aborted. error code: $mergeSuccess\n";
      last;
    } 
    elsif ($mergeSuccess == 4) { #indicates failed commit
      print "Error while commit. Auto-merge aborted. error code: $mergeSuccess\n";
      last;
    } 
    else { #unknown..
      print "Error while making Jira calls. Auto-merge aborted. error code: $mergeSuccess\n";
      last;
    }
  } elsif ($shouldMrg == 2) {
    # do not actually merge but record merge info
    #recordMerge($rev);
  } else {
    # Should not try to merge.
  }
}

sub sendConflictEmail {
  my @args = @_;
  my $rev = $args[0];
  my $svnLogOp = $args[1];
  my $svnMergeLog = $args[2];

  my %tokens;
  $tokens{"rev"} = $rev;
  $tokens{"user"} = $revsToCommitBy{$rev};
  $tokens{"svnlog"} = $svnLogOp;
  $tokens{"svnmergelog"} = $svnMergeLog;

  my ($emailSubject, $emailText) = getEmailText(\%tokens, $SRC_SVN_URL);
  print "Email subject:\n";
  print "$emailSubject\n";
  print "Email text:\n";
  print "$emailText\n";

  #sending Mail-------------------------------------
  open(MAIL, "|/usr/sbin/sendmail -t");
  print MAIL "To: $sendMailAddrs\n";
  print MAIL "From: $fromMailAddr\n";
  print MAIL "Subject: $emailSubject\n\n";
  print MAIL $emailText;
  #Closing Mail Pipe
  close(MAIL);
  #mail sent-----------------------------------------
}

sub tryMerge {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $rev = $args[3];
  my $svnLogOp = $args[4];

  if ($rev =~ /r(\d+)/) {
    $rev = $1;
  }

  print "Trying to merge revision r$rev to $TRGT_BR...\n";

  my $svnMergeDryRunCmd = "cd $TRGT_DIR; svn up; svn merge -c $rev --accept p --dry-run $SRC_SVN_URL $TRGT_DIR 2>&1";
  print "$svnMergeDryRunCmd\n";
  my $svnMergeLog = `$svnMergeDryRunCmd`;
  print "Merge log (dry run): $svnMergeLog\n";
  if ($svnMergeLog =~ /Summary of conflicts/) {
    return (0, $svnMergeLog);
  }

  # dry run succeeded, try the real thing..it could still conflict.
  my $svnMergeRunCmd = "cd $TRGT_DIR; svn up; svn merge -c $rev --accept p $SRC_SVN_URL $TRGT_DIR 2>&1";
  print "$svnMergeRunCmd\n";
  $svnMergeLog = `$svnMergeRunCmd`;
  print "Merge log (real): $svnMergeLog\n";
  if ($svnMergeLog =~ /"Summary of conflicts"/) {
    return (0, $svnMergeLog);
  }
  print "Merged revision r$rev locally..\n";

  my %jiraMap = cloneJirasFromRev($jiraUrl, $jiraUsr, $jiraPwd, $rev, $svnLogOp);
  foreach my $jira (keys %jiraMap) {
    my $newJira = $jiraMap{$jira};
    if ($newJira eq "error") {
      print "ERROR while trying to get/clone Jira: $jira. Will abort merge.\n";
      return (3, "");
    }
  }

  #replace 5.2.2 Jira references in the commit log with $TRGT_BR jira references
  my $svnCommitLog53 = getCommitLog (\%jiraMap, $svnLogOp);
  
  #escaping double quotes characters in svn commit message
  $svnCommitLog53 =~ s/\"/\\\"/g;

  #commit it!!
  my $svnCommitCmd = "cd $TRGT_DIR; svn commit -m \"$svnCommitLog53\" 2>&1";
  print "svn commit command: $svnCommitCmd\n";
  my $svnCommitOp = `$svnCommitCmd`;
  print "commit command o/p: $svnCommitOp\n";
  if ($svnCommitOp =~ /Commit failed/) {
    print "Commit FAILED: $svnCommitOp\n";
    print "Commit of merge for revision r$rev failed..\n";
    return (4, "commit failed");
  } else {
    print "** Merge for revision r$rev committed successfully **...\n";
  }

  return (1, "");
}

sub cloneJirasFromRev {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $rev = $args[3];
  my $svnLogOp = $args[4];

  my @jiras = getJirasForRev($rev, $svnLogOp);
  my %jiraMap;

  foreach my $jira (@jiras) {
    print "Jira: $jira\n";

    my $newJira = checkIfAlreadyClonedNew ($jiraUrl, $jiraUsr, $jiraPwd, $jira, $TRGT_BR);
    if ($newJira eq "") {
      # invoke curl and get response string
      my $result = jiraGet ($jiraUrl, $jiraUsr, $jiraPwd, $jira);
      if ($result =~ /expand/) {
        print "Going to clone this Jira $jira\n";
        my $payload = createClonePayloadSaveToFile ($result, "jsondata.txt", $CLONE_STR, $TARGET_VER);
        if ($payload eq "") {
          print "Error while creating/saving new payload to file for $jira\n";
          $jiraMap{$jira} = "error"; #insert poison pill
          last;
        } else { # the real work
          #url, usr, pwd, key, filename
          $newJira = createCloneInJira ($jiraUrl, $jiraUsr, $jiraPwd, $jira, "jsondata.txt");
          if ($newJira eq "error") {
            print "Error while creating a clone in Jira for $jira\n";
            $jiraMap{$jira} = "error"; #insert poison pill
            last;
          } else {
            print "Created a clone in Jira: $newJira\n";
#Here is where you create the clone links
            my $status = createCloneLinks ($jiraUrl, $jiraUsr, $jiraPwd, $jira, $newJira);
	    if ($status eq "error") {
              print "Error while linking up the clones in Jira for $jira and $newJira\n";
              last;
            }
            $jiraMap{$jira} = $newJira;
          }
        }
      } elsif ($result eq "skipForIssueTypeTask") {
        print "Skipping for JIRA:$jira as Jira Type is Task\n";
      } elsif ($result eq "error") {
        print "Error while getting Jira details for $jira\n";
        $jiraMap{$jira} = "error"; #insert poison pill
        last;
      }
    } elsif ($newJira eq "error") {
      print "Error while getting Jira details for $jira\n";
      $jiraMap{$jira} = "error"; #insert poison pill
      last;
    } else {
      $jiraMap{$jira} = $newJira;
    }
  }
  %jiraMap;
}

sub checkIfAlreadyClonedNew {


  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $jira = $args[3];
  my $targetBranch = $args[4];


  my $CURL_GET = "curl -D- -u %\%JIRA_USER%%:%\%JIRA_PWD%% -X GET -H \"Content-Type: application/json\" \"%\%jiraUrl%%\/rest/api/2/search?jql=project+=+BE+and+issueFunction+in+linkedIssuesOfRecursive(\\\"key+=+%\%JIRA_ISSUE%%\\\",+\\\"Cloned+to\\\")&fields=key,fixVersions\" 2>&1";
  #substitute jira user
  $CURL_GET =~ s/%\%jiraUrl%%/$jiraUrl/g;
  $CURL_GET =~ s/%\%JIRA_USER%%/$jiraUsr/g;
  $CURL_GET =~ s/%\%JIRA_PWD%%/$jiraPwd/g;

  $CURL_GET =~ s/%\%JIRA_ISSUE%%/$jira/g;

  print "Check duplicate command: = \n$CURL_GET\n";
  my $result = `$CURL_GET`;
  print "curl get returned: $result\n";

  my @resultlist = split ('\n', $result);
  foreach my $line (@resultlist) {
    if ($line =~ /\"startAt\"/) {
      $result = $line;
      last;
    }
  }


  my $parsedJson;
  my $status = eval {$parsedJson = from_json ($result)};
  if (! defined $status) {
    $result = "error";
  } else {

    my $issuesRef = $parsedJson->{"issues"};
    my @issues = @$issuesRef;

    my $issuesSz = scalar @issues;
    if ($issuesSz == 0) {
      return ""; #no cloned issues found!
    } else {
      foreach my $issueRef (@issues) {
        my $clonedTicket = $issueRef->{"key"};
	#print "KEY = $clonedTicket\n";
        my $fixVersionsRef = $issueRef->{"fields"}->{"fixVersions"};
        foreach my $fixVersionRef (@$fixVersionsRef) {
	  my $fixVersion = $fixVersionRef->{"name"};
	  #print "   Version = $fixVersion\n";
          my $clonedJira = getClonedJira ($clonedTicket, $fixVersion, $targetBranch);
	  if ($clonedJira ne "") {
            return $clonedJira; #means, do not clone it!
          }
        }
      }
    }
  }
  return "";
}

sub getClonedJira {
  my ($clonedTicket, $fixVersion, $targetBranch) = @_;

  my %versionToBranchMap;
  $versionToBranchMap{"5\\.1\\.4-HF\\d+"} = "/branches/5.1";
  $versionToBranchMap{"5\\.2\\.1-HF\\d+"} = "/branches/5.2.1-HF";
  $versionToBranchMap{"5\\.2\\.2-HF\\d+"} = "/branches/5.2.2-HF";
  $versionToBranchMap{"5\\.3\\.0"}        = "/branches/5.3";
  $versionToBranchMap{"5\\.3\\.0-HF\\d+"} = "/branches/5.3.0-HF";
  $versionToBranchMap{"5\\.4\\.0"}        = "/branches/5.4";
  $versionToBranchMap{"5\\.4\\.1"}        = "/branches/5.4";
  $versionToBranchMap{"5\\.4\\.1-HF\\d+"} = "/branches/5.4.1-HF";
  $versionToBranchMap{"5\\.5\\.0"}        = "/branches/5.5";
  $versionToBranchMap{"5\\.5\\.0-HF\\d+"} = "/branches/5.5.0-HF";
  $versionToBranchMap{"5\\.6\\.0"}        = "/branches/5.6";
  $versionToBranchMap{"6\\.0\\.0"}        = "/branches/6.0";

  print "Key = $clonedTicket, Version = $fixVersion\n";

  foreach my $patt (keys %versionToBranchMap) {
    if ($fixVersion =~ /$patt/) {
      my $fixVerBr = $versionToBranchMap{$patt};
      if ($fixVerBr eq $targetBranch) {
        return $clonedTicket;
      }
    }
  }
  
  "";
}

sub checkIfAlreadyCloned {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $jira = $args[3];

  my $CURL_GET = "curl -D- -u %\%JIRA_USER%%:%\%JIRA_PWD%% -X GET -H \"Content-Type: application/json\" \"%\%jiraUrl%%\/rest/api/2/search?jql=project+=+BE+and+summary+~$CLONE_STR+and+summary+~CLONE+and+summary+~+%\%JIRA_ISSUE%%&fields=key\" 2>&1";
  #substitute jira user
  $CURL_GET =~ s/%\%jiraUrl%%/$jiraUrl/g;
  $CURL_GET =~ s/%\%JIRA_USER%%/$jiraUsr/g;
  $CURL_GET =~ s/%\%JIRA_PWD%%/$jiraPwd/g;

  $CURL_GET =~ s/%\%JIRA_ISSUE%%/$jira/g;

  print "Check duplicate command: = $CURL_GET\n";
  my $result = `$CURL_GET`;
  print "curl get returned: $result\n";

  if ($result =~ /\"key\":\"(.*?)\"/) {
    $result = $1;
    print "Jira already exists for $jira :$result\n";
    return $result;
  } elsif ($result =~ /curl: \(6\)/) {
    $result = "error";
  } else {
    if ($result =~ /"issues":\[\]/) {
      $result = ""; #success
    } else {
      $result = "error";
    }
  }
  return $result;
}

sub jiraGet {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $jira = $args[3];

  my $CURL_GET = "curl -D- -u %%JIRA_USER%%:%%JIRA_PWD%% -X GET -H \"Content-Type: application/json\" \"%%jiraUrl%%/rest/api/2/issue/%%JIRA_ISSUE%%?fields=issuetype,summary,description,customfield_10024,customfield_10080,customfield_10150,components,priority,versions,fixVersions\"";
  #substitute jira user
  $CURL_GET =~ s/%%JIRA_USER%%/$jiraUsr/g;
  # subs jira pwd
  $CURL_GET =~ s/%%JIRA_PWD%%/$jiraPwd/g;
  # subs Jira URL
  $CURL_GET =~ s/%%jiraUrl%%/$jiraUrl/g;
  # subs jira ticket
  $CURL_GET =~ s/%%JIRA_ISSUE%%/$jira/g;

  print "Curl command = $CURL_GET\n";
  my $result = `$CURL_GET`;
  
  if ($result =~ /"issuetype":{.*?"name":"Task"/) {
    print "Skipping Jira creation for IssyeType=Task ; Jira Id : $jira";
    return "skipForIssueTypeTask";
  }
  

  my @resultlist = split ('\n', $result);
  foreach my $line (@resultlist) {
    if ($line =~ /{\"expand\"/) {
      return $line;
    }
  }
  "error";
}

sub fileToList {
  my @args = @_;
  my $patternFileNm = $args[0];
  open(my $patternFl, '<', $patternFileNm) || die "Cannot open $patternFileNm. Exiting..\n";
  # Get the pattens in a list.
  my @pattLst;
  while ( <$patternFl> ) {
    next if ( $_ =~ /^#/);
    my $ln = $_;
    chomp $ln;
    push @pattLst, $ln;
  #  print "pat = $ln\n";
  }
  close $patternFl;
  @pattLst;
}

sub shouldMerge {
  my @args = @_;
  my $pattLstRef = $args[0];
  my $rev = $args[1];
  my $svnLogOp = $args[2];

  my @filesToInclude;
  my @filesToExclude;
  my @pattLst = @$pattLstRef;

  #print $svnLogMsg;
  my @lst = split ('\n', $svnLogOp);

  foreach my $myLine (@lst) {
    my $matched = 0;
    if ($myLine =~ /$SRC_BR/) {
      a: foreach my $patt (@pattLst) {
        if ($myLine =~ /$patt/) {
          $matched = 1;
          push @filesToExclude, $myLine;
          last a;
        }
      }
      if ($matched == 0) {
        push @filesToInclude, $myLine;
      } 
    } else {
      if ($myLine =~ /no-merge/) {
        print "Will not merge $rev into $TRGT_BR (found 'no-merge' keyword in commit logs)\n";
        return 2;
      }
    }
  }

  my $excludesLen = scalar @filesToExclude;
  if ($excludesLen > 0) {
    print "Revision $rev is not qualified for merging into $TRGT_BR.\n";
    foreach my $ln ( @filesToExclude ) {
      print "$ln\n";
    }
    return 0;
  } 

  my $lstLen = scalar @filesToInclude;
  print "Revision $rev is qualified for merging into $TRGT_BR...\n";
  foreach my $ln ( @filesToInclude ) {
    print "$ln\n";
  }
  1;
}

sub getJirasForRev {

  my @args = @_;
  my $rev = $args[0];
  my $svnLogOp = $args[1];

  my %jiras;
  my @lines = split ('\n', $svnLogOp);

  foreach my $line (@lines) {
    # In each line, mask (clone of BE-YYYY): i.e; search for (clone of BE-XXXX) and replace with empty string
    $line =~ s/\(clone of BE-\d+\)//g;
    my @words = split(/\s|,|;/, $line);
    foreach my $word (@words) {
      if ( $word =~ /(^BE-\d+)/) {
        $jiras{$1}=$1;
      }
    }
  }
  return sort keys %jiras;
}

sub getCommitLog {
  my @args = @_;
  my $jiraMapRef = $args[0];
  my $commitLog = $args[1];

  # In the original commit log, mask out (clone of BE-YYYY): i.e; search for (clone of BE-XXXX) and replace with empty string
  $commitLog =~ s/\(clone of BE-\d+\)//g;

  my %jiraMap = %$jiraMapRef;
  foreach my $key (keys %jiraMap) {
    my $newTxt = $jiraMap{$key} . " (clone of ". $key . ") ";
    $commitLog =~ s/$key/$newTxt/g;
  }

  my @y = split ('\n', $commitLog);

  my $found = 0;
  my @logs;
  my $committer="";
  my $revNo="";
  foreach my $l (@y) {
    if ($l =~ /^r\d+/) {
     my @parts = split ('\|', $l);

     $revNo = $parts[0];
     $committer = $parts[1];

     $revNo =~ s/^\s+|\s+$//g;
     $committer =~ s/^\s+|\s+$//g;

    }

    if ($l =~ /^\s*?$/ ) {
      $found = 1;
      next;
    }
    if ($found == 1) {
      if ($l =~ /^-+$/) {
        next;
      }
      # Remove lines already starting with "Merged "... (these were from a previous merge)
      if ($l =~ /Merged /) {
        next;
      }
      push @logs, $l;
    }
  }
  my $mergeCommitMsg="Merged $revNo \(by $committer on the $SRC_BR branch\)";
  unshift(@logs, $mergeCommitMsg);
  my $newLog = join ("\n", @logs);

  $newLog;
}

sub getEmailText {
  my @args = @_;
  my $mapRef = $args[0];
  my $srcRepo = $args[1];
  my %tokens = %$mapRef;

  my $emailSubject = "Conflict while merging %%REV%% from $SRC_BR branch into $TRGT_BR";
  $emailSubject =~ s/%%REV%%/$tokens{"rev"}/g;

  my $emailText =
  "Auto-merging of revision r%%REV%% from $SRC_BR branch resulted in a merge conflict.\n\n".
  "svn merge -c %%REV%% %%SRC_SVN_URLREPO%% .\n\n".
  "%%SVNLOG%%\n".
  "%%USER%%:\n\n".
  "Run the following merge command, resolve the conflict manually and mark the conflict as resolved\n".
  "svn merge -c %%REV%% %%SRC_SVN_URLREPO%% .\n".
  "(Notice the last argument to the above command is '.' the base directory of your $TRGT_BR branch)\n".
  "Note: You svn client version needs to be atleast at version 1.5. Using the latest version (v1.9) is recommended.\n".
  "\n".
  "Thank you,\n".
  "BE Build Team\n";

  my $revNo = $tokens{"rev"};
  if ($revNo =~ /r(\d+)/) {
    $revNo = $1;
  }

  $emailText =~ s/%%REV%%/$revNo/g;
  $emailText =~ s/%%SVNLOG%%/$tokens{"svnmergelog"}/g;
  $emailText =~ s/%%USER%%/$tokens{"user"}/g;
  $emailText =~ s/%%SRC_SVN_URLREPO%%/$srcRepo/g;
  return ($emailSubject, $emailText);
}

sub recordMerge {
  my @args = @_;

  my $rev = $args[0];

  if ($rev =~ /r(\d+)/) {
    $rev = $1;
  }

  print "Trying to record merge revision r$rev to $TRGT_BR...\n";

  my $svnMergeRunCmd = "cd $TRGT_DIR; svn up; svn merge -c $rev --record-only $SRC_SVN_URL $TRGT_DIR 2>&1";
  print "$svnMergeRunCmd\n";
  my $svnMergeLog = `$svnMergeRunCmd`;
  print "Merge log (real): $svnMergeLog\n";
  if ($svnMergeLog =~ /"Summary of conflicts"/) {
    return 0;
  }
  print "Recorded merge revision r$rev to $TRGT_BR successfully.\n";
  1;
}

sub createClonePayloadSaveToFile {
  my @args = @_;
  my $jiraStr = $args[0];
  my $fileNm = $args[1];
  my $CLONE_STR = $args[2];
  my $TARGET_VER = $args[3];

  #Return variable
  my $data;

  if ($jiraStr eq "") {
    return "";
  }

  my $parsedJson;
  my $status = eval {$parsedJson = from_json ($jiraStr)};

#key
  my $key = $parsedJson-> {"key"};

#summary
  my $fieldsRef = $parsedJson->{"fields"};
  my %fieldsMap = %$fieldsRef;
  my $summary = $fieldsMap{"summary"};
  $summary = escape_special_chars ($summary);

#issuetype
  my $issuetypeRef = $fieldsMap{"issuetype"};
  my %issuetypeMap = %$issuetypeRef;
  my $issuetype = $issuetypeMap{"name"};

#components
  my $compsRef = $fieldsMap{"components"};
  my @compsList = @$compsRef;
  my @comps;
  for (my $i=0; $i<scalar @compsList; $i++) {
    my $compRef = $compsList[$i];
    my %compMap = %$compRef;
 
    my $comp = $compMap{"name"};
    push @comps, $comp;            
  }

#resolver
  my $customfield_10080Ref = $fieldsMap{"customfield_10080"};
  my $customfield_10080 = $customfield_10080Ref->{"name"};

#versions
  my $versRef = $fieldsMap{"versions"};
  my @versList = @$versRef;
  my @vers;
  for (my $i=0; $i<scalar @versList; $i++) {
    my $verRef = $versList[$i];
    my %verMap = %$verRef;
    my $ver = $verMap{"name"};
    push @vers, $ver;            
  }

#seibel
  my $customfield_10150Ref = $fieldsMap{"customfield_10150"};
  my @siebelSrs;
  if (defined $customfield_10150Ref) {
     @siebelSrs = @$customfield_10150Ref;
  }

#description
  my $description = $fieldsMap{"description"};
  $description = escape_special_chars ($description);

#fixVesion
  my $fixVersionsRef = $fieldsMap{"fixVersions"};
  my $fixVersionsMapRef = @$fixVersionsRef[0];
  my $fixVersion = $fixVersionsMapRef->{"name"};

#priority
  my $priorityRef = $fieldsMap{"priority"};
  my $priority = $priorityRef->{"name"};

#severity
  my $severityRef = $fieldsMap{"customfield_10024"};
  my $customfield_10024 = $severityRef->{"name"};
  if ( $customfield_10024 eq "") {
    $customfield_10024 = "3-Low";
  } 
  my %fieldMap;
  
  $fieldMap{"key"} = $key;
  $fieldMap{"summary"} = $summary;
  $fieldMap{"issuetype"} = $issuetype;
  $fieldMap{"components"} = \@comps;
  $fieldMap{"resolver"} = $customfield_10080;
  $fieldMap{"versions"} = \@vers;
  $fieldMap{"siebel"} = \@siebelSrs;
  $fieldMap{"description"} = $description;
  $fieldMap{"fixversion"} = $fixVersion;
  $fieldMap{"priority"} = $priority;
  $fieldMap{"severity"} = $customfield_10024;
  #printFldMap(\%fieldMap);
 
  $data = createJiraPayload (\%fieldMap, $CLONE_STR, $TARGET_VER);
 
  my $rmlog = `rm $fileNm`;
  open(my $fhw, '>', $fileNm) or die "Could not open file '$fileNm' for writing $!";
  print $fhw $data;
  close ($fhw);
  $data;
}

sub createCloneInJira {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $key = $args[3];
  my $filename = $args[4];

  my $CURL_CMD_PRINT = "curl -D- -u $jiraUsr:******** -X POST -d \@$filename -H \"Content-Type: application/json\" \"$jiraUrl/rest/api/2/issue/\"";
  my $CURL_CMD = "curl -D- -u $jiraUsr:$jiraPwd -X POST -d \@$filename -H \"Content-Type: application/json\" \"$jiraUrl/rest/api/2/issue/\"";

  print "$CURL_CMD_PRINT\n";

  print "Creating new cloned issue....\n";
  my $result;
  $result = `$CURL_CMD`;
  print "Creating issue result....: $result\n";


  my $newkey;
  if ($result =~ /\"key\":\"(.*?)\"/) {
    $newkey = $1;
    print "Created a (new) clone: $newkey cloned from: $key\n";
  } else {
    return "error";
  }
  $newkey;
}

sub printFldMap {
  my @args = @_;
  my $fldMapRef = $args[0];
  my %fldMap = %$fldMapRef;
  foreach my $key (keys %fldMap) {
    print "KEY=$key\n";
    my $verRef = $fldMap{"versions"};
    my @versions = @$verRef;
    #printFld("VERSIONS", @versions);
  }
}

sub getcomponents {
  my @args = @_;
  my $components = $args[0];
  my @compNms;
  
  my @comps = split ('},', $components);
  foreach my $comp (@comps) {
    if ($comp =~ /"name":"(.*?)"/) {
      my $compNm = $1;
      push @compNms, $compNm;
    }
  }
  @compNms;
} 

sub getversions {
  my @args = @_;
  my $versions = $args[0];
  my @verNms;
  
  my @vers = split ('},', $versions);
  foreach my $ver (@vers) {
    if ($ver =~ /"name":"(.*?)"/) {
      my $verNm = $1;
      push @verNms, $verNm;
    }
  }
  @verNms;
} 

sub getSeibelSRs {
  my @args = @_;
  my $srs = $args[0];
  my @siebelNms;
  
  my @siebels = split (',', $srs);
  foreach my $siebel (@siebels) {
    if ($siebel =~ /(\d+)/) {
      my $siebelNm = $1;
      push @siebelNms, $siebelNm;
    }
  }
  @siebelNms;
}

sub printFld {
  my @args = @_;
  my $nm = shift @args;
  print "Name: $nm\n";
  foreach my $val (@args) {
    print "  $val\n";
  }
}

sub createJiraPayload {
  my @args = @_;
  my $fldMapRef = $args[0];
  my %fldMap = %$fldMapRef;
  my $CLONE_STR = $args[1];
  my $TARGET_VER = $args[2];
  
  my $data;
  my $data = 
"{\n".
"    \"fields\": {\n".
"      \"project\": {\n".
"          \"key\": \"BE\"\n".
"       },\n".
"       \"issuetype\": {\"name\":\"%%ISSUETYPE%%\"},\n".
"       \"summary\": \"%%SUMMARY%%\",\n".
"       \"description\": \"%%DESCRIPTION%%\",\n".
"       \"components\" : [%%COMPONENTS%%],\n".
"       \"versions\": [%%VERSIONS%%],\n".
"       \"fixVersions\": [{\"name\": \"$TARGET_VER\"}],\n".
"       \"priority\": {\"name\": \"%%PRIORITY%%\"},\n".
"       \"customfield_10000\":{\"name\" : \"auto_BE\"},\n".
"       \"customfield_10024\": {\"value\": \"%%SEVERITY%%\"},\n".
"       \"customfield_10080\":{\"name\":\"%%RESOLVER%%\"}\n".
#"       \"customfield_10150\":%%SIEBEL%%\n".
"   }\n".
"}\n";

  $data =~ s/%%ISSUETYPE%%/$fldMap{"issuetype"}/g;
  my $key = $fldMap{"key"};

  #Replace Summary
  my $summary = $fldMap{"summary"};
  $summary = "CLONE " . $key . " ($CLONE_STR): " . $summary;
  $data =~ s/%%SUMMARY%%/$summary/g;
 
  #Replace Description
  my $description = $fldMap{"description"};
  $data =~ s/%%DESCRIPTION%%/$description/g;
 
  #Replace Priority
  my $priority = $fldMap{"priority"};
  $data =~ s/%%PRIORITY%%/$priority/g;

  #Replace Severity
  my $severity = $fldMap{"severity"};
  $data =~ s/%%SEVERITY%%/$severity/g;
 
  #Replace Resolver
  my $resolver = $fldMap{"resolver"};
  $data =~ s/%%RESOLVER%%/$resolver/g;
  #$data =~ s/%%RESOLVER%%/bgokhale/g;
 
  #Replace Components;
  my $compsStr = createNameStr(\%fldMap, "components");
  $data =~ s/%%COMPONENTS%%/$compsStr/g;
 
  #Replace Affected Versions
  my $versStr = createNameStr (\%fldMap, "versions");
  $data =~ s/%%VERSIONS%%/$versStr/g;

  #Replace Siebel SRs
  my $siebelSrs = createQuotedStr (\%fldMap, "siebel");
  $data =~ s/%%SIEBEL%%/$siebelSrs/g;
 
  #print $data;
  $data;
}

sub createQuotedStr {
  #Replace Components;
  my @args = @_;
  my $fldMapRef = $args[0];
  my $key = $args[1];
  my %fldMap = %$fldMapRef;
  my $valsRef = $fldMap{$key};
  my @vals = @$valsRef;
  my @qvals;
  #foreach my $val (@vals) {
    #my $qval = "\"" . $val . "\"";
    #push @qvals, $qval;
  #}
  my $joinedVals;
  if (scalar @vals == 1) {
    $joinedVals = join "", @vals;
  } elsif (scalar @vals > 1) {
    $joinedVals = join ",", @vals;
  } else {
    $joinedVals = "";
  }
  $joinedVals = "\"" . $joinedVals . "\"";
 
  $joinedVals;
}

sub createNameStr {
  #Replace Components;
  my @args = @_;
  my $fldMapRef = $args[0];
  my $key = $args[1];
  my %fldMap = %$fldMapRef;
  my $valsRef = $fldMap{$key};
  my @vals = @$valsRef;
  my @qvals;
  foreach my $val (@vals) {
    my $qval = "{\"name\":" . "\"" . $val . "\"}";
    push @qvals, $qval;
  }
  my $joinedVals;
  if (scalar @qvals == 1) {
    $joinedVals = join "", @qvals;
  } elsif (scalar @qvals > 1) {
    $joinedVals = join ",", @qvals;
  }
  
  $joinedVals;
}

sub createCloneLinks {
  my ($jiraUrl, $jiraUsr, $jiraPwd, $jira, $newJira ) = @_;

  my $linkJson = "";

  $linkJson =$linkJson . "\"{";
  $linkJson =$linkJson . " \\\"type\\\": {";
  $linkJson =$linkJson . "   \\\"name\\\": \\\"Cloners\\\"";
  $linkJson =$linkJson . " },";
  $linkJson =$linkJson . " \\\"inwardIssue\\\": {";
  $linkJson =$linkJson . "   \\\"key\\\": \\\"%%CLONEDTO%%\\\"";
  $linkJson =$linkJson . " },";
  $linkJson =$linkJson . " \\\"outwardIssue\\\": {";
  $linkJson =$linkJson . "   \\\"key\\\": \\\"%%CLONEDFROM%%\\\"";
  $linkJson =$linkJson . " },";
  $linkJson =$linkJson . " \\\"comment\\\": {";
  $linkJson =$linkJson . "   \\\"body\\\": \\\"Cloned from %%SOURCEBRANCH%%\\\"";
  $linkJson =$linkJson . " }";
  $linkJson =$linkJson . "}\"";

  my $myLink = $linkJson;
  $myLink =~ s/%\%CLONEDTO%%/$newJira/g;
  $myLink =~ s/%\%CLONEDFROM%%/$jira/g;
  $myLink =~ s/%\%SOURCEBRANCH%%/$SRC_BR/g;

  my $CURL_CMD = "curl -D- -u $jiraUsr:$jiraPwd -X POST -d $myLink -H \"Content-Type: application/json\" \"$jiraUrl/rest/api/2/issueLink/\"";
  print "Creating a Jira Link: $CURL_CMD\n";
  my $result;
  $result = `$CURL_CMD`;
  print "Creating a Jira Link result....: $result\n";
  1;
}

sub escape_special_chars {
  my ($description) = @_;
  #replace \ with \\
  $description =~ s/\\/\\\\/g;
  #replace / with \/
  $description =~ s/\//\\\//g;
  #replace " with \"
  $description =~ s/"/\\"/g;
  $description =~ s/\n/\\n/g;
  $description =~ s/\r//g;
  $description =~ s/\t/\\t/g;
  $description;
}
