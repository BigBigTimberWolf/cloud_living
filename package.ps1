$version = "s20-0.0.1"
mvn clean package '-Dmaven.test.skip=true' -T 1C
docker build -t server-living .
docker tag server-living registry.cn-hangzhou.aliyuncs.com/kichina/server-living:$version
docker push registry.cn-hangzhou.aliyuncs.com/kichina/server-living:$version