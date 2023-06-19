#设置镜像来源为jdk17
FROM openjdk:17

#设置维护者信息
MAINTAINER xiaohan.yuan<xiaohan.yuan@kichina.com.cn>



VOLUME /home/cloud-living

ADD ./target/cloud_living-0.0.1-SNAPSHOT.jar app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

#设置环境变量，并制定默认值
ENV PROFILE="server"

#运行app.jar
ENTRYPOINT ["java", "-Xms500m", "-Xmx500m", "-Xss256k","-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]


#暴露服务端口
EXPOSE 8888
