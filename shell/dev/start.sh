#!/bin/bash

## Fix By Server
HOME_DIR=/home/webdev

## Edit By Project
JAR_DIR_NM=BaseApi
JAR_FILE_NM=BaseApi-0.1.war

## Fix
JAR_DIR=$HOME_DIR/$JAR_DIR_NM

## Rest Fixed
echo "########## Move JAR_DIR ##########"
cd "$JAR_DIR"

echo "========== Run shutdown.sh =========="
./shutdown.sh

echo "########## java -jar Run ##########"
#java -jar $WAR_FILE_NM --spring.profiles.active=dev &
nohup java -jar $WAR_FILE_NM --spring.profiles.active=dev > /dev/null 2>&1 &
