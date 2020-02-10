#!/usr/bin/perl
#
#
# This script searches for "BE-XXXX" Jira issues for a given V-build (5.2.1.026)
# Then for each Jira issue that it finds, it updates Jira's "Resolver Build" field (in this example, "V26") using Jira's REST APIs.
#
#

use strict;

die "Usage: mark_resolver_builds.pl <vbuilds-file> <be-build-revision> <jira-username> <jira-password> [-f] \
            -f: Only show the command, do not execute it." if (scalar @ARGV < 4);

my $flNm = $ARGV[0];
my $bldNo = $ARGV[1];
my $jiraUsr = $ARGV[2];
my $jiraPwd = $ARGV[3];
my $fakeMode = $ARGV[4];

my $JIRA_URL = "https://jira.tibco.com";

open(my $fh, '<', $flNm);

my $startPattern = '>>>>>'.$bldNo.'>>>>>';
my $endPattern = '<<<<<'.$bldNo.'<<<<<';
#print "$startPattern    $endPattern\n";

my $found = 0;
my %jiraIssues;
while ( <$fh> ) {
  if ($_ =~ /$startPattern/) {
    $found = 1;
    next;
  } elsif ($_ =~ /$endPattern/) {
    $found = 0;
    next;
  }
  if ($found == 1) {
    my @ln = split(/\s|,|;/, $_);
    foreach my $word (@ln) {
      if ( $word =~ /(^BE-\d+)/) {
        $jiraIssues{$1}=$1;
      }
    }
  }
}

my $resolverBuild;

if ($bldNo =~ /^\d+?\.\d+?\.\d+?\.(\d+)/) {
  my $resBldNo=$1;
  $resBldNo =~ s/^0+//;
  $resolverBuild = sprintf ("V%02s", $resBldNo);
  print "Build No = $resolverBuild\n";
}

foreach my $jiraIssueNo (sort keys %jiraIssues) {
  my $JSON_STR = "\"{\\\"fields\\\": {\\\"customfield_10172\\\":{\\\"value\\\":\\\"$resolverBuild\\\"}}}\"";
  # check if fixversion is 5.5.0
  my $jiraJson =      `curl -D- -u $jiraUsr:$jiraPwd -X GET -H \"Content-Type: application/json\" \"$JIRA_URL/rest/api/2/issue/$jiraIssueNo?fields=fixVersions\" 2>&1`;
  my $fixVerStr;
  if ($jiraJson =~ /"fixVersions":\[(.*?)\]/) {
    $fixVerStr = $1;
  } else {
    print "Cannot find a fixversion, will not update Jira\n";
    last;
  }
  my @fixVers = getversions ($fixVerStr);
  my $found = 0;
  foreach my $ver (@fixVers) {
    my $ver1 = chomp($ver);
    if ($ver =~ /^5\.5\.0$/) {
      $found = 1;
    }
  }
  if ($found == 0) {
    next;
  }
  # fixversion is 5.5.0, continue
  my $CURL_CMD_PRINT = "curl -X PUT -u $jiraUsr:******** -d $JSON_STR -H \"Content-Type: application/json\" \"$JIRA_URL/rest/api/2/issue/$jiraIssueNo\"";
  my $CURL_CMD = "curl -X PUT -u $jiraUsr:$jiraPwd -d $JSON_STR -H \"Content-Type: application/json\" \"$JIRA_URL/rest/api/2/issue/$jiraIssueNo\"";
  print "$CURL_CMD_PRINT\n";
  if ($fakeMode ne "-f") {
    my $ret = `$CURL_CMD`;
    print $ret;
  }
}
  
sub getversions {
  my @args = @_;
  my $versions = $args[0];
  my @verNms;

  my @vers = split ('}', $versions);
  foreach my $ver (@vers) {
    if ($ver =~ /"name":"(.*?)"/) {
      my $verNm = $1;
      push @verNms, $verNm;
    }
  }
  @verNms;
}
