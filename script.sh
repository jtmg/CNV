#!/bin/bash

export CLASSPATH=
export CLASSPATH="$CLASSPATH:/tmp/cnv:/tmp/cnv/samples:./"
export _JAVA_OPTIONS=
export _JAVA_OPTIONS="-XX:-UseSplitVerifier"
echo "CLASSPATH ficou em " $CLASSPATH
echo "_JAVA_OPTIONS ficou em " $_JAVA_OPTIONS
