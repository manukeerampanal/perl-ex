#!/usr/bin/env perl -w

use strict;
no warnings 'once';
use Test::More;
#use ex::lib '../lib';
use FindBin;
use lib "$FindBin::Bin/..","$FindBin::Bin/../lib";
BEGIN { $::dist = shift @INC }

eval "use File::Find";
$@ and plan skip_all => "File::Find required for testing POD";

plan tests => 1;

my $found = 0;
opendir my $dir, $::dist;
while (defined ( $_ = readdir $dir )) {
	print;
	print "\n";
	$found='d' and last if -d "$::dist/$_" and /^(bin|scripts?|ex|eg|examples?|samples?|demos?)$/;
	$found='f' and last if -f "$::dist/$_" and /^(examples?|samples?|demos?)\.p(m|od)$/i;
}
ok($found, 'have example: '.$found.':'.$_);
closedir $dir;
