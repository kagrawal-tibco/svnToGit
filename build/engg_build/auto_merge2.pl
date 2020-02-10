#!/usr/bin/perl
#
#
# Auto-merge script. Looks for changes in the 5.2 branch and applies them one by one to 5.3 using "svn merge -c"
#
#

use strict;
require "parseJsonFn.pl";

die "Usage: auto_merge.pl <jira-username> <jira-password> <path to 5.2> <path to 5.3>" if (scalar @ARGV < 4);

my $jiraUsr = $ARGV[0];
my $jiraPwd = $ARGV[1];
my $BE52PATH = $ARGV[2];
my $BE53PATH = $ARGV[3];
if ( ! -e $BE53PATH) {
  print "Invalid path to BE 5.3 workdir: $BE53PATH\n";
  exit;
}

my $jiraUrl = "http://jira.tibco.com";
my $BE52 = "https://svn.tibco.com/svn/be/branches/5.2";
my $BE53 = "https://svn.tibco.com/svn/be/branches/private/bala/5.3";
my $sendMailAddrs="bgokhale@tibco.com,mgujrath@tibco.com,vasharma@tibco.com";
my $fromMailAddr="be_build@tibco.com";


my @excludePatterns = fileToList ("build/engg_build/exclude_patterns.txt");

#bala@bgokhale ~/private/bala/5.3 $ svn propget svn:mergeinfo|grep 'branches/5.2'
#/branches/5.2:69695-71870,72340-72341,72343,72353,72358,72361,72370

my $svnCmd = "cd $BE53PATH; svn up > /dev/null; svn propget svn:mergeinfo|grep 'branches/5.2' 2>&1";
my $svnLog = `$svnCmd`;

my $merges = $svnLog;
$merges =~ s/\/branches\/5.2://g;

#break it into - and ,
my @mergedRevs = split (',|-', $merges);

#get the latest one..svn arranges them in ascending order, so the last one is good for use
my $lastMerged = $mergedRevs[ (scalar @mergedRevs) - 1];

print $lastMerged;
$lastMerged += 1;
chomp $lastMerged;

my $svnLogsSinceLastMerge = "cd $BE52PATH; svn log -r$lastMerged:HEAD | grep -P \"^r\\d+\"";

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
  my $svnLogCmd = "cd $BE52PATH; svn log -v -r$rev";
  my $svnLogOp = `$svnLogCmd`;
  my $shouldMrg = shouldMerge ( \@excludePatterns, $rev, $svnLogOp);
  if ($shouldMrg == 1) {
    my $mergeSuccess = tryMerge ($jiraUrl, $jiraUsr, $jiraPwd, $rev, $svnLogOp);
    if ($mergeSuccess == 0) {
      print "Conflict detected while trying to merge $rev. Auto-merge aborted.\n";
      
      #Send an email..
      sendConflictEmail($rev, $svnLogOp);
      #and break out of the merge process till the conflict is manually resolved.
      last;
    }
  }
}

sub sendConflictEmail {
  my @args = @_;
  my $rev = $args[0];
  my $svnLogOp = $args[1];

  my %tokens;
  $tokens{"rev"} = $rev;
  $tokens{"user"} = $revsToCommitBy{$rev};
  $tokens{"svnlog"} = $svnLogOp;

  my ($emailSubject, $emailText) = getEmailText(\%tokens, $BE52);
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

  print "Trying to merge revision r$rev to 5.3...\n";
  
  my $svnMergeDryRunCmd = "cd $BE53PATH; svn up; svn merge -c $rev --accept p --dry-run $BE52 $BE53PATH 2>&1";
  print "$svnMergeDryRunCmd\n";
  my $svnMergeLog = `$svnMergeDryRunCmd`;
  print "Merge log (dry run): $svnMergeLog\n";
  if ($svnMergeLog =~ /Summary of conflicts/) {
    return 0;
  }

  # dry run succeeded, try the real thing..it could still conflict.
  my $svnMergeRunCmd = "cd $BE53PATH; svn up; svn merge -c $rev --accept p $BE52 $BE53PATH 2>&1";
  print "$svnMergeRunCmd\n";
  $svnMergeLog = `$svnMergeRunCmd`;
  print "Merge log (real): $svnMergeLog\n";
  if ($svnMergeLog =~ /"Summary of conflicts"/) {
    return 0; 
  }
  print "Merged revision r$rev locally..\n";
  
  my %jiraMap = cloneJirasFromRev($jiraUrl, $jiraUsr, $jiraPwd, $rev, $svnLogOp);

  #replace 5.2.2 Jira references in the commit log with 5.3 jira references
  my $svnCommitLog53 = getCommitLog (\%jiraMap, $svnLogOp);

  #commit it!!
  my $svnCommitCmd = "cd $BE53PATH; svn commit -m \"$svnCommitLog53\" 2>&1";
  print "svn commit command: $svnCommitCmd\n";
  my $svnCommitOp = `$svnCommitCmd`;
  if ($svnCommitOp =~ /Commit failed/) {
    print "Commit FAILED: $svnCommitOp\n";
    print "Commit of merge for revision r$rev failed..\n";
  } else {
    print "** Merge for revision r$rev committed successfully **...\n";
  }

  return 1;
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

    my $newJira = $jira."-new"; #checkIfAlreadyCloned ($jiraUrl, $jiraUsr, $jiraPwd, $jira);
    if ($newJira eq "") {
      # invoke curl and get response string
      my $result = jiraGet ($jiraUrl, $jiraUsr, $jiraPwd, $jira);
      if ($result =~ /expand/) {
        print "Going to clone this Jira $jira\n";
        my $payload = createClonePayloadSaveToFile ($result, "jsondata.txt");
        if ($payload ne "") {
          #url, usr, pwd, key, filename
          $newJira = createCloneInJira ($jiraUrl, $jiraUsr, $jiraPwd, $jira, "jsondata.txt"); 
          print "Created a clone in Jira: $newJira\n";
          $jiraMap{$jira} = $newJira;
        } else {
          $jiraMap{$jira} = $jira;
        }
      } else {
          $jiraMap{$jira} = $jira;
      }
  
    } else {
      $jiraMap{$jira} = $newJira;
    }
  }
  %jiraMap;
}

sub checkIfAlreadyCloned {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $jira = $args[3];

  my $CURL_GET = "curl -D- -u %%JIRA_USER%%:%%JIRA_PWD%% -X GET -H \"Content-Type: application/json\" %%jiraUrl%%:/rest/api/2/search?jql=project+=+BE+and+summary+~5.2.2+and+summary+~CLONE+and+summary+~+%%JIRA_ISSUE%%&fields=key";
  #substitute jira user
  $CURL_GET =~ s/%%jiraUrl%%/$jiraUrl/g;
  $CURL_GET =~ s/%%JIRA_USER%%/$jiraUsr/g;
  $CURL_GET =~ s/%%JIRA_PWD%%/$jiraPwd/g;

  $CURL_GET =~ s/%%JIRA_ISSUE%%/$jira/g;

  print "Check duplicate command: = $CURL_GET\n";
  my $result = `$CURL_GET`;
  if ($result =~ /\"key\":\"(.*?)\"/) {
    $result = $1;
  } else {
    $result = "";
  }
  print "Duplicates:$result\n";
  $result;
}

sub jiraGet {
  my @args = @_;
  my $jiraUrl = $args[0];
  my $jiraUsr = $args[1];
  my $jiraPwd = $args[2];
  my $jira = $args[3];

  my $CURL_GET = "curl -D- -u %%JIRA_USER%%:%%JIRA_PWD%% -X GET -H \"Content-Type: application/json\" %%jiraUrl%%:/rest/api/2/issue/%%JIRA_ISSUE%%?fields=issuetype,summary,description,customfield_10024,customfield_10080,customfield_10150,components,priority,versions,fixVersions";
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


  my @resultlist = split ('\n', $result);
  foreach my $line (@resultlist) {
    if ($line =~ /{\"expand\"/) {
      return $line;
    }
  }
  "";
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

  my @noPatterns;
  my @foundInExcludes;
  my @pattLst = @$pattLstRef;

  #print $svnLogMsg;
  my @lst = split ('\n', $svnLogOp);
  
  foreach my $myLine (@lst) {
    if ($myLine =~ /branches\/5.2/) {
      my $matched = 0; 
      a: foreach my $patt (@pattLst) {
  
        if ($myLine =~ /$patt/) {
          $matched = 1;
          push @foundInExcludes, $myLine;
          last a;
        }
      }
      
      if ($matched == 0) {
        push @noPatterns, $myLine;
      }
    }
  }
  
  my $lstLen = scalar @noPatterns; 
  if ($lstLen == 0) {
    print "Revision $rev is not qualified for merging into 5.3.\n";
    foreach my $ln ( @foundInExcludes ) {
      print "$ln\n";
    }
    0;
  } else {
    print "Revision $rev is qualified for merging into 5.3...\n";
    foreach my $ln ( @noPatterns ) {
      print "$ln\n";
    }
    1;
  }
}

sub getJirasForRev {
  
  my @args = @_;
  my $rev = $args[0];
  my $svnLogOp = $args[1];

  my %jiras;
  my @lines = split ('\n', $svnLogOp);

  foreach my $line (@lines) {
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

  my %jiraMap = %$jiraMapRef;
  foreach my $key (keys %jiraMap) {
    $commitLog =~ s/$key/$jiraMap{$key}/g;
  }
  
  my @y = split ('\n', $commitLog); 
  
  my $found = 0;
  my @logs;
  foreach my $l (@y) {
    if ($l =~ /^\s*?$/ ) {
      $found = 1;
      next;
    }
    if ($found == 1) {
      if ($l =~ /^-+$/) {
        next;
      }
      push @logs, $l;
    } 
  }
  my $newLog = join ("\n", @logs);
  $newLog;
}

sub getEmailText {
  my @args = @_;
  my $mapRef = $args[0];
  my $be52repo = $args[1];
  my %tokens = %$mapRef;

  my $emailSubject = "Conflict while merging %%REV%% from 5.2.2 branch into 5.3";
  $emailSubject =~ s/%%REV%%/$tokens{"rev"}/g;

  my $emailText =
  "Auto-merging of %%REV%% from 5.2 branch resulted in a merge conflict.\n\n".
  "svn log -v -r%%REV%%\n".
  "%%SVNLOG%%\n".
  "%%USER%%: Follow these steps to resolve the conflict:\n".
  "From the base directory of your workarea for 5.3, issue\n".
  "\n".
  "svn merge -c %%REV%% %%BE52REPO%% .\n".
  "\n".
  "Resolve the conflicts manually and check in your changes.\n".
  "\n".
  "Thank you\n".
  "BE Build Team\n";

  my $revNo = $tokens{"rev"};
  if ($revNo =~ /r(\d+)/) {
    $revNo = $1;
  }
  
  $emailText =~ s/%%REV%%/$revNo/g;
  $emailText =~ s/%%SVNLOG%%/$tokens{"svnlog"}/g;
  $emailText =~ s/%%USER%%/$tokens{"user"}/g;
  $emailText =~ s/%%BE52REPO%%/$be52repo/g;
  return ($emailSubject, $emailText);
}
