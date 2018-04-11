#!/bin/bash

mkdir out

protoc -I=. --java_out=out *.proto
cd out
rsync -avh . ../../src/main/java -R
cd ..

rm -rf out