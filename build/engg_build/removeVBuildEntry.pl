#!/usr/bin/perl

my $flNm = $ARGV[0];
my $bldNo = $ARGV[1];

open(my $fh, '<', $flNm);

my $startPattern = '>>>>>'.$bldNo.'>>>>>';
my $endPattern = '<<<<<'.$bldNo.'<<<<<';
#print "$startPattern    $endPattern\n";

my $found = 0;

while ( <$fh> ) {
  if ($_ =~ /$startPattern/) {
    $found = 1;
    next;
  } elsif ($_ =~ /$endPattern/) {
    $found = 0;
    next;
  }
  if ($found == 0) {
    print $_;
  }
}
