#!/usr/bin/perl
#
# perl script to update vbuilds.log manually after an official build, since the official build
# this script updates the vbuilds.log file with an entry for the official vbuild that includes the svn revisions part of this build
#

my $vbuildsFile = $ARGV[0];
my $versionsFile = $ARGV[1];

open(my $vBld, '<', $vbuildsFile);

my $startPattern = '>>>>>'.$bldNo.'>>>>>';
my $endPattern = '<<<<<'.$bldNo.'<<<<<';
#print "$startPattern    $endPattern\n";

my $fromRev;
my $toRev;

while ( <$vBld> ) {
  if ($_ =~ /^r(\d+)/) {
    $fromRev = $1;
    last;
  }
}

$fromRev += 1;
#print $fromRev;

my $beRev = `grep BE_REVISION $versionsFile`;
if ($beRev =~ /.*?(\d+)/) {
  $toRev=$1;
}
my $toRev   = `grep "BE_REVISION=" $versionsFile | cut -f 2 -d=`;
my $major   = `grep "BE_VERSION_MAJOR=" $versionsFile | cut -f 2 -d=`;
my $minor   = `grep "BE_VERSION_MINOR=" $versionsFile | cut -f 2 -d=`;
my $update  = `grep "BE_VERSION_UPDATE=" $versionsFile | cut -f 2 -d=`;
my $buildRev= `grep "env.BE_BUILD=" $versionsFile | cut -f 2 -d=`;
chop $toRev;
chop $major; 
chop $minor;
chop $update;
chop $buildRev;
my $buildNo=$major.'.'.$minor.'.'.$update.'.'.$buildRev;

#print "$buildNo";
my $date = `TZ=":US/Pacific" date`;
my $svnLogCmd = "svn log -r".$toRev.":".$fromRev.' https://svn.tibco.com/svn/be/branches/5.3';
#print "Svn command: $svnLogCmd\n";
my $svnLogMsg = `$svnLogCmd`;

my $msg = ">>>>>".$buildNo.">>>>>\n";
$msg = "$msg"."$date";
$msg = "$msg"."$svnLogMsg";
$msg = "$msg"."<<<<<"."$buildNo"."<<<<<\n";
print "$msg\n\n";

