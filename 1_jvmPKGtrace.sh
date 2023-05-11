export SPRING_CLOUD_CONFIG_URI=http://ebank:spdb1234@10.134.36.60:9804
export EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://ebank:spdb1234@10.137.107.223:9808/eureka/
echo $(date "+%Y%m%d_%H:%M:%SS")'=======================mvn -X clean -DskipTests package=================sh 1_build.sh==starting:'
mvn clean -DskipTests package
#mvn -X clean -DskipTests package
echo $(date "+%Y%m%d_%H:%M:%SS")'=======================mvn -X clean -DskipTests package=================sh 1_build.sh==end:'
echo $(date "+%Y%m%d_%H:%M:%SS")'=======================agent run=================sh 2_agent.sh==starting:'


echo "[-->] Detect artifactId from pom.xml"
ARTIFACT=$(mvn -q \
-Dexec.executable=echo \
-Dexec.args='${project.artifactId}' \
--non-recursive \
exec:exec);
echo "artifactId is '$ARTIFACT'"

echo "[-->] Detect artifact version from pom.xml"
VERSION=$(mvn -q \
  -Dexec.executable=echo \
  -Dexec.args='${project.version}' \
  --non-recursive \
  exec:exec);
echo "artifact version is '$VERSION'"

# echo "[-->] Detect artifact rspring-args from pom.xml"
# springArgs=$(mvn -q \
#   -Dexec.executable=echo \
#   -Dexec.args='${spring-args}' \
#   --non-recursive \
#   exec:exec);
# echo "artifact springArgs is '$springArgs'"

echo "[-->] Detect artifact run-args from pom.xml"
RunArgs=$(mvn -q \
  -Dexec.executable=echo \
  -Dexec.args='${run-args}' \
  --non-recursive \
  exec:exec);
echo "artifact RunArgs is '$RunArgs'"



echo "[-->] Detect  agentPath from pom.xml"
agentPath=$(mvn -q \
  -Dexec.executable=echo \
  -Dexec.args='${graalvm.agent.path}' \
  --non-recursive \
  exec:exec);
echo " agentPath is '$agentPath'"

JAR="$ARTIFACT-$VERSION.jar"
echo "[-->] starting the Spring Boot fat '$JAR'"

agents="-agentlib:native-image-agent=config-output-dir=$agentPath"
rm -rf $agentPath
#java -Dspring.aot.enabled=true \
java -Dspring.aot.enabled=true \
 $RunArgs \
 $agents \
 -jar target/$JAR



echo $(date "+%Y%m%d_%H:%M:%SS")'=======================agent run=================sh 2_agent.sh==end:'
