#!/bin/bash

USG="Usage: $0  start|stop [-debug]"
if [ $# -lt 1 ] ; then
  echo $USG
  exit 1
fi

source ./zeus-env.sh

CLASSPATH=.:$JAVA_HOME/lib/tools.jar
LIBPATH=$ZEUS_HOME/lib

cd $ZEUS_HOME

for f in `find $LIBPATH -name '*.jar'`
  do
    CLASSPATH=$CLASSPATH:$f
  done
  
CLASSPATH=$CLASSPATH:$ZEUS_HOME/conf
# ******************************************************************
# ** Set java runtime options                                     **
# ** Change 256m to higher values in case you run out of memory.  **
# ******************************************************************

OPT="$OPT -cp $CLASSPATH"
DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,address=10005,server=y,suspend=n"
if [ "$2" = "-debug" ] ; then
  OPT="$DEBUG $OPT"
fi 

# ***************
# ** Run...    **
# ***************

pid=$ZEUS_LOG_DIR/zeus.pid
log=$ZEUS_LOG_DIR/zeus.log

if [ "$1" = "start" ] ; then
    echo "start zeus , logging to $ZEUS_LOG_DIR/zeus.log"
    ENV="-Dengine.log.dir=$ZEUS_LOG_DIR"
    nohup java $ENV $OPT  com.zhangyue.zeus.ZeusHiveBootStrap  > $log 2>&1 < /dev/null & 
    echo $! > $pid
    sleep 1
    head $log
elif [ "$1" = "stop" ] ; then
    if [ -f $pid ]; then
      if kill -0 `cat $pid` > /dev/null 2>&1; then
        echo "stop zeus process ..."
        kill `cat $pid`
      else
        echo "no zeus to stop"
      fi
    else
      echo "no zeus to stop"
    fi
fi
