#!/bin/sh

while [ $# -gt 0 ]
do
key="$1"
case $key in
    -p|--projlib)
    PROJLIB="$2"
    shift 2
    ;;
    -g|--groupId)
    GROUPID="$2"
    shift 2
    ;;
    -a|--artifactId)
    ARTIFACTID="$2"
    shift 2
    ;;
    -v|--version)
    VERSION="$2"
    shift 2
    ;;
    *)
    echo "Usage: install-projlib -p <project-library> -g <groupId> -a <artifactId> -v <version>"
    exit
    ;;
esac
done
if [ "${PROJLIB}" = "" ] || [ "${GROUPID}" = "" ] || [ "${ARTIFACTID}" = "" ] || [ "${VERSION}" = "" ];
then
    echo "Usage: install-projlib -p <project-library> -g <groupId> -a <artifactId> -v <version>"
    exit
fi

echo "PROJLIB    = ${PROJLIB}"
echo "GROUPID    = ${GROUPID}"
echo "ARTIFACTID = ${ARTIFACTID}"
echo "VERSION    = ${VERSION}" 

mvn install:install-file -Dfile=${PROJLIB} -DgroupId=${GROUPID} -DartifactId=${ARTIFACTID} -Dversion=${VERSION} -Dpackaging=projlib
