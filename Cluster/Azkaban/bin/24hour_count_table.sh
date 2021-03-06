#!/bin/bash
IP=172.18.18.119
PORT=4000
 mysql -u root -h ${IP} -P ${PORT} << EOF
use people;

INSERT INTO t_24hour_count (peopleid, hour, count, community)
SELECT peopleid, DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 HOUR),'%Y%m%d%H'),COUNT(peopleid),community
FROM t_people_recognize
WHERE DATE_FORMAT(capturetime,'%Y%m%d%H')= DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 HOUR),'%Y%m%d%H')
GROUP BY community,peopleid;
EOF
