#!/usr/bin/perl -w

use strict;
use lib::abs '../lib';
use XML::RPC::Enc::LibXML;
use XML::Hash::LX;
use Test::More;
use Test::NoWarnings;
use MIME::Base64 'encode_base64';
use R::Dump;
use Encode;

# Encoder

plan tests => 22;

my $enc = XML::RPC::Enc::LibXML->new();
is_deeply xml2hash( $enc->request( test => 1 ) ),
	{ methodCall => { methodName => "test", params => { param => { value => { i4 => 1 } } } } },
	'request i4';
is_deeply xml2hash( $enc->request( test => 1.1 ) ),
	{ methodCall => { methodName => "test", params => { param => { value => { double => 1.1 } } } } },
	'request double';
is_deeply xml2hash( $enc->request( test => 'z' ) ),
	{ methodCall => { methodName => "test", params => { param => { value => { string => 'z' } } } } },
	'request string';

is_deeply xml2hash( $enc->request( test => { a => 1 } ) ),
	{ methodCall => { methodName => "test", params => { param => { value => {
		struct => { member => { name => 'a', value => { i4 => 1 } } }
	} } } } },
	'request struct';

is_deeply xml2hash( $enc->request( test => [ 1,2 ] ) ),
	{ methodCall => { methodName => "test", params => { param => { value => {
		array => { data => { value => [ {i4 => 1},{i4 => 2} ] } }
	} } } } },
	'request array';

is_deeply xml2hash( $enc->request( test => sub {{ custom => '12345' }}, ) ),
	{ methodCall => { methodName => "test", params => { param => { value => {
		custom => '12345'
	} } } } },
	'request custom compat';

is_deeply xml2hash( $enc->request( test => bless( do{\(my $o = '12345')}, 'custom' ) ) ),
	{ methodCall => { methodName => "test", params => { param => { value => {
		custom => '12345'
	} } } } },
	'request custom bless';

is_deeply xml2hash( $enc->request( test => bless( do{\(my $o = { a => 1 })}, 'custom' ) ) ),
	{ methodCall => { methodName => "test", params => { param => { value => {
		custom => { a => 1 }
	} } } } },
	'request custom bless';

is_deeply xml2hash( $enc->response( 1 ) ),
	{ methodResponse => { params => { param => { value => { i4 => 1 } } } } },
	'response i4';
is_deeply xml2hash( $enc->response( 1.1 ) ),
	{ methodResponse => { params => { param => { value => { double => 1.1 } } } } },
	'response double';
is_deeply xml2hash( $enc->response( 'z' ) ),
	{ methodResponse => { params => { param => { value => { string => 'z' } } } } },
	'response string';

is_deeply xml2hash( $enc->fault( 555,'test' ) ),
	{ methodResponse => { fault => { value => { struct => { member => [
		{name => faultCode => value => { i4 => 555 }},
		{name => faultString => value => { string => 'test' }},
	]}}}}},
	'fault';

{
    local $enc->{external_encoding} = 'windows-1251';
    is $enc->response( Encode::decode utf8 => "тест" ),
    Encode::encode( $enc->{external_encoding} => Encode::decode utf8 => qq{<?xml version="1.0" encoding="windows-1251"?>\n<methodResponse><params><param><value><string>тест</string></value></param></params></methodResponse>\n} ),
    'external_encoding';
}

# Decoder

is_deeply [ $enc->decode( ( $enc->request( test => 1 ) ) ) ],
	[ test => 1 ],
	'decode i4';

is_deeply [ $enc->decode( ( $enc->request( test => 1.2 ) ) ) ],
	[ test => 1.2 ],
	'decode double';

is_deeply [ $enc->decode( ( $enc->request( test => 'z' ) ) ) ],
	[ test => 'z' ],
	'decode string';

is_deeply [ $enc->decode( ( $enc->request( test => sub{{ custom => '12345'}} ) ) ) ],
	[ test => bless(do{\(my $o = '12345')}, 'custom') ],
	'decode custom compat';

is_deeply [ $enc->decode( ( $enc->request( test => bless( do{\(my $o = '12345')}, 'custom' ) ) ) ) ],
	[ test => bless(do{\(my $o = '12345')}, 'custom') ],
	'decode custom bless';

is_deeply [ $enc->decode( ( $enc->request( test => bless( do{\(my $o = {a => 1})}, 'custom' ) ) ) ) ],
	[ test => bless(do{\(my $o = {a => 1})}, 'custom') ],
	'decode custom bless struct';

is_deeply [ $enc->decode( ( $enc->request( test => sub{{ base64 => encode_base64('test') }} ) ) ) ],
	[ test => 'test' ],
	'decode base64';

use DateTime::Format::ISO8601;
is_deeply [ $enc->decode( ( $enc->request( test => sub {{ 'dateTime.iso8601' => '20090816T010203.04+0330' }} ) ) ) ],
	[ test => DateTime::Format::ISO8601->parse_datetime('20090816T010203.04+0330') ],
	'decode datetime';

__END__
my $hash = [
	{
		name => 'rec',
		entries => {
			name => 'ent',
			fields => [ a => 1 ]
		},
	}
];

my @prm = (
	1, 0.1,
	a => { my => [ test => 1 ], -is => 1},
	bless( do{\(my $o = '12345')}, 'estring' ),
	bless( do{\(my $o = { inner => 1 })}, 'xval' ),
	sub {{ bool => '1' }},
	sub {{ base64 => encode_base64('test') } },
	sub {{  }},
#	bless( {}, 'zzz' ),
	sub {{ custom => 'cusval' }},
	#sub {[ { subs => 'subval' }, { -x => 1 } ]},
);
#print $t->parse(my $xml = $enc->encode( test => @prm ))->sprint;
#print $t->parse(my $xml = $enc->response( @prm ))->sprint;
print $t->parse(my $xml = $enc->fault( 111, 'err' ))->sprint;