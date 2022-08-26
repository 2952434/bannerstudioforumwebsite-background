# 该镜像需要依赖的基础镜像
FROM hub.c.163.com/library/java:latest
# 将当前目录下的jar包复制到docker容器的/目录下
ADD BannerStudioForumBackground-v1.0.jar ./BannerStudioForumBackground.jar
# 运行过程中创建一个api.jar文件
RUN bash -c 'touch /BannerStudioForumBackground-v1.0.jar' && \
echo "Asia/Shanghai" > /etc/timezone
EXPOSE 8084
# 指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8099", "-jar","/BannerStudioForumBackground.jar"]
# 指定维护者的名字
MAINTAINER bannerstudio
RUN echo 'BannerStudio'