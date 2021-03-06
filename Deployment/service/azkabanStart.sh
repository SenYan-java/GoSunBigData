#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    azkabanStart.sh
## Description: 启动azkaban的脚本.
## Version:     1.0
## Author:      caodabao
## Created:     2018-1-12
################################################################################
#set -e
cd `dirname $0`
## 脚本所在目录
BIN_DIR=`pwd`
cd ..
## 安装包根目录
ROOT_HOME=`pwd`
## 配置文件目录
CONF_DIR=${ROOT_HOME}/conf

## 最终安装的根目录，所有bigdata 相关的根目录
INSTALL_HOME=$(grep Install_HomeDir ${CONF_DIR}/cluster_conf.properties|cut -d '=' -f2)
## Azkaban根目录
AZKABAN_HOME=${INSTALL_HOME}/Azkaban/azkaban
##mysql安装节点主机名 
MYSQL_HOSTNAME=$(grep Mysql_InstallNode ${CONF_DIR}/cluster_conf.properties|cut -d '=' -f2)

## webserver 日记目录
WEBLOG_DIR=${AZKABAN_HOME}/webserver/logs
## webserver 日记文件
WEBLOG_FILE=${WEBLOG_DIR}/webserver.log
## executor 日记目录
EXELOG_DIR=${AZKABAN_HOME}/executor/logs
## executor 日记文件
EXELOG_FILE=${EXELOG_DIR}/executor.log


ssh root@$MYSQL_HOSTNAME "source /etc/profile;chmod 755 ${AZKABAN_HOME}/webserver/bin/*; mkdir -p $WEBLOG_DIR; cd ${AZKABAN_HOME}/webserver; nohup bin/azkaban-web-start.sh >> ${WEBLOG_FILE} 2>&1 &"
if [ $? -eq 0 ];then
    echo  -e 'webserver start success \n'
else 
    echo  -e 'webserver start failed \n'
fi

ssh root@$MYSQL_HOSTNAME "source /etc/profile;chmod 755 ${AZKABAN_HOME}/executor/bin/*; mkdir -p $EXELOG_DIR; cd ${AZKABAN_HOME}/executor; nohup bin/azkaban-executor-start.sh >> ${EXELOG_FILE} 2>&1 &"
if [ $? -eq 0 ];then
    echo  -e 'executor start success \n'
else 
    echo  -e 'executor start failed \n'
fi

# 等待7秒后再验证Azkaban是否启动成功
echo -e "********************验证Azkaban是否启动成功*********************"
sleep 7s
source $(grep Source_File ${CONF_DIR}/cluster_conf.properties|cut -d '=' -f2)
xcall jps | grep -E 'AzkabanWebServer|AzkabanExecutorServer|jps show as bellow'

