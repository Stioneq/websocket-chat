#!/bin/bash

mkdir out

protoc -I=. --java_out=out *.proto
protoc -I=. --js_out=import_style=commonjs,binary:out --ts_out=service=true:out *.proto
cd out
rsync -avh . ../../src/main/java -R
cd ..

rm -rf out