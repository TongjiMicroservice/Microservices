#!/bin/bash
pids=$(pgrep -u root -f 'java -jar')
if [ ! -z "$pids" ]; then
    kill $pids
fi
