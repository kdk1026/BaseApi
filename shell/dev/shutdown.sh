#!/bin/bash

## Edit By Project
WAR_FILE_NM=BaseApi-0.1.jar
PROFILE=dev

## Rest Fixed
pid=`ps -ef | grep -v grep | grep $WAR_FILE_NM | grep $PROFILE | awk '{print $2}'`

if [ -n "$pid" ]; then
	echo "Sending SIGTERM to process $pid"

	kill -15 $pid

	sleep 10

	if ps -p $pid > /dev/null; then
		echo "Process $pid did not terminate, sending SIGKILL"
		kill -9 $pid
	else
		echo "Process $pid terminated successfully"
	fi
else
	echo "PID is empty"
fi