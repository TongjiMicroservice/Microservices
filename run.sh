#!/bin/bash

# 编译
if mvn clean package; then
    # 定义微服务项目的 JAR 包路径和命令
    USER_SERVICE_JAR="./user-service/target/user-service-0.0.1-SNAPSHOT.jar"
    TASK_SERVICE_JAR="./task-service/target/task-service-0.0.1-SNAPSHOT.jar"
    GATEWAY_SERVICE_JAR="./teamsphere-gateway/target/gateway-service-0.0.1-SNAPSHOT.jar"
    SCHEDULE_SERVICE_JAR="./schedule-service/target/schedule-service-0.0.1-SNAPSHOT.jar"
    MEMBER_SERVICE_JAR="./member-service/target/member-service-0.0.1-SNAPSHOT.jar"
    MEETING_SERVICE_JAR="./meeting-service/target/meeting-service-0.0.1-SNAPSHOT.jar"
    CHAT_SERVICE_JAR="./chat-service/target/chat-service-0.0.1-SNAPSHOT.jar"

    java -jar $USER_SERVICE_JAR &
    java -jar $TASK_SERVICE_JAR &
    java -jar $GATEWAY_SERVICE_JAR &
    java -jar $SCHEDULE_SERVICE_JAR &
    java -jar $MEMBER_SERVICE_JAR &
    java -jar $MEETING_SERVICE_JAR &
    java -jar $CHAT_SERVICE_JAR &
else
    echo "编译失败。无法继续执行。"
fi