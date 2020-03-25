
use strict;
use warnings;

use MCP;

my $mcp = MCP->new();
my $sensor_0 = $mcp->get( 0 );
my $sensor_3 = $mcp->get( 3 );
