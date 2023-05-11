export SPRING_CLOUD_CONFIG_URI=http://ebank:spdb1234@10.134.36.60:9804
export EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://ebank:spdb1234@10.137.107.223:9808/eureka/

ARTIFACT=$(mvn -q \
-Dexec.executable=echo \
-Dexec.args='${project.artifactId}' \
--non-recursive \
exec:exec);
echo "artifactId is '$ARTIFACT'"


echo "[-->] Detect artifact run-args from pom.xml"
RunArgs=$(mvn -q \
  -Dexec.executable=echo \
  -Dexec.args='${run-args}' \
  --non-recursive \
  exec:exec);
echo "artifact RunArgs is '$RunArgs'"

./target/$ARTIFACT \
  $RunArgs

