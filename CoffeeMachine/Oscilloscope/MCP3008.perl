package MCP;
use strict;

use HiPi::Device::SPI qw( :all );
use HiPi::Interface::MCP3008 qw( :mcp );

our @pins = (
	MCP3008_S0,
	MCP3008_S1,
	MCP3008_S2,
	MCP3008_S3,
	MCP3008_S4,
	MCP3008_S5,
	MCP3008_S6,
	MCP3008_S7
);

sub new
{
	my $class = shift;
	my %arg = @_;

	my $self = bless {
			mcp => HiPi::Interface::MCP3008->new(
				devicename   => '/dev/spidev0.0',
				speed        => SPI_SPEED_KHZ_500,
				bitsperword  => 8
			)
		}, $class;

	return $self;
} # sub new

sub get
{
	my $self = shift;
	my $pin = shift || 0;

	return $self->{mcp}->read( $pins[$pin] );
} # sub get

1;
