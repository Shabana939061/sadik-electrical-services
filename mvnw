#!/bin/sh
# ----------------------------------------------------------------------------
# Maven Wrapper script for Linux / macOS / Git Bash
# ----------------------------------------------------------------------------

export MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(pwd)}"
WRAPPER_JAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
    mkdir -p "$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
    curl -fsSL "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar" -o "$WRAPPER_JAR" || true
fi

if [ -n "$JAVA_HOME" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

exec "$JAVACMD" -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" -jar "$WRAPPER_JAR" "$@"
