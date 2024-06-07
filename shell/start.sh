#!/bin/bash

## Fix By Server
HOME_DIR=/home/webdev

## Edit By Project
WAR_DIR_NM=BaseApi
WAR_FILE_NM=BaseApi-0.1.jar

## Fix
WAR_DIR=$HOME_DIR/$WAR_DIR_NM

## Rest Fixed
echo "########## Move WAR_DIR ##########"
cd "$WAR_DIR"

echo "========== Run shutdown.sh =========="
./shutdown.sh

echo "########## java -jar Run ##########"
#java -jar $WAR_FILE_NM --spring.profiles.active=dev &
nohup java -jar $WAR_FILE_NM --spring.profiles.active=dev > /dev/null 2>&1 &