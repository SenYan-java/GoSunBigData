create 'device',
{NAME => 'device', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0', COMPRESSION =>
'NONE', VERSIONS => '1', MIN_VERSIONS => '1', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65536',
 IN_MEMORY => 'true', BLOCKCACHE => 'true'}

create 'clusteringInfo',
{NAME => 'c', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
VERSIONS => '1', MIN_VERSIONS => '0', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65535',
IN_MEMORY => 'true', BLOCKCACHE => 'true'}

create 'searchRes',
{NAME => 'i', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
VERSIONS => '1', MIN_VERSIONS => '0', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65535',
IN_MEMORY => 'true', BLOCKCACHE => 'true', TTL => '604800'}

create 'peoplescheduler',
{NAME => 'rules', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
VERSIONS => '1', MIN_VERSIONS => '0', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65535',
IN_MEMORY => 'true', BLOCKCACHE => 'true'}

create 'peopleadd',
{NAME => 'add', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
VERSIONS => '1', MIN_VERSIONS => '0', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65535',
IN_MEMORY => 'true', BLOCKCACHE => 'true'}

create 'peoplerecognize',
{NAME => 'recognize', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
VERSIONS => '1', MIN_VERSIONS => '0', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65535',
IN_MEMORY => 'true', BLOCKCACHE => 'true'}

create 'personregion',
{NAME => 'region', DATA_BLOCK_ENCODING => 'NONE', BLOOMFILTER => 'ROW', REPLICATION_SCOPE => '0',
VERSIONS => '1', MIN_VERSIONS => '0', KEEP_DELETED_CELLS => 'false', BLOCKSIZE => '65535',
IN_MEMORY => 'true', BLOCKCACHE => 'true'}

exit