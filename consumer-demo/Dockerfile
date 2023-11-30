# 使用 Maven 镜像作为构建阶段
FROM maven:3.8.1-openjdk-11-slim AS build

# 复制源代码到容器中
COPY src /home/app/src
COPY pom.xml /home/app
COPY .mvn /home/app/.mvn
COPY mvnw /home/app

# 在 /home/app 目录下执行 mvnw package 命令
RUN cd /home/app && ./mvnw package

# 使用 Java 镜像作为运行阶段
FROM eclipse-temurin:17-jdk-alpine

# 从构建阶段复制编译出的 jar 包到运行阶段
COPY --from=build /home/app/target/*.jar /app.jar

# 设置容器启动命令
ENTRYPOINT ["java","-jar","/app.jar"]