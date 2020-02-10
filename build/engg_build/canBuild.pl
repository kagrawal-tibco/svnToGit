#!/usr/bin/perl
#
# perl script to update vbuilds.log manually after an official build, since the official build
# this script updates the vbuilds.log file with an entry for the official vbuild that includes the svn revisions part of this build
#

use strict;

die "Usage: canBuild.pl <fromRev> <toRev> <exclude-pattern file>" if (scalar @ARGV != 3);
    

my $x = canBuild ();

sub canBuild {
  my $toRev=$ARGV[0];
  my $fromRev=$ARGV[1];
  my $patternFileNm = $ARGV[2];
  my @noPatterns;

  open(my $patternFl, '<', $patternFileNm);
  my $svnLogCmd = "svn log --non-interactive --username=qlbuild --password='T1bc0!23' -v -r".$toRev.":".$fromRev." https://svn.tibco.com/svn/be/trunk/be-main | grep 'trunk'";
  my $svnLogMsg = `$svnLogCmd`;
  #print $svnLogMsg;
  my @lst = split ('\n', $svnLogMsg);
  #print @lst;
  
  # Get the pattens in a list.
  my @pattLst;
  while ( <$patternFl> ) {
    next if ( $_ =~ /^#/);
    my $ln = $_;
    chomp $ln;
    push @pattLst, $ln;
    #print "pat = $ln\n";
  }
  
  foreach my $myLine (@lst) {
    if ($myLine =~ /trunk\/be-main/) {
      my $matched = 0; 
      a: foreach my $patt (@pattLst) {
  
        if ($myLine =~ /$patt/) {
          $matched = 1;
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
    print "Build relevant checkin not found. Build need not proceed.\n";
    0;
  } else {
    print "Checkins detected:\n";
    foreach my $ln ( @noPatterns ) {
      print "$ln\n";
    }
    1;
  }
}
