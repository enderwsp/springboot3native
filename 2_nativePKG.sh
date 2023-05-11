export SPRING_CLOUD_CONFIG_URI=http://ebank:spdb1234@10.134.36.60:9804
export EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://ebank:spdb1234@10.137.107.223:9808/eureka/
mvn -X native:compile -Pnative -DskipTests=true
