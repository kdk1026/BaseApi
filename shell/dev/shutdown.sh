#!/bin/bash

## Edit By Project
WAR_FILE_NM=BaseApi-0.1.war
PROFILE=dev

## Rest Fixed
pid=`ps -ef | grep -v grep | grep $WAR_FILE_NM | grep $PROFILE | awk '{print $2}'`

if [ -n "$pid" ]
then
        kill -9 $(ps -ef | grep -v grep | grep $WAR_FILE_NM | grep $PROFILE | awk '{print $2}')
else
        echo "PID is empty"
fi