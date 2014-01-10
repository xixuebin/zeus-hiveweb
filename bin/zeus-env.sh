#!/bin/bash

#The home directory for hella engine.
export ZEUS_HOME=${PWD%/*}

# The java implementation to use.  Required
#export JAVA_HOME=

#The maximum amount of heap to use, in MB. Default is 2000.
export ZEUS_HEAPSIZE=2000

#JVM parameter configuration
export OPT="-Xmx${ZEUS_HEAPSIZE}m -Xss256k -XX:+UseParallelGC -XX:ParallelGCThreads=20"

# Where log files are stored.  $ZEUS_HOME/logs by default.
export ZEUS_LOG_DIR=$ZEUS_HOME/logs
