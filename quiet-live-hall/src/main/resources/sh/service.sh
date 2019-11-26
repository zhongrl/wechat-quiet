#!/bin/bash

if [ $# != 2 ] ; then
 echo "参数有误，正确语法： service.sh [deploy|start|stop|log] [xx.jar]" && exit 1
fi

app=$2
method=$1
P_ID=`ps -ef | grep -w "$absolute_app" | grep -w "java" | grep -v "bash -c" | awk '{print $2}'`
if [ "$method" = "stop"  ]; then
   echo "kill quiet-live-hall.jar, pid=$P_ID"
   kill -9 $P_ID
fi

if [ "$method" = "deploy" ]; then
  rm -f $app
  echo "downloading..."
  echo "download success"
fi

if [ "$method" = "deploy" -o "$method" = "start" ]; then
  nohup java -jar quiet-live-hall.jar > log.file 2>&1 &
fi

if [ "$method" == "log" ] ;then
  tail -300f /jc/logs/quiet/quiet-live-hall/quiet-live-hall.log
fi


