# 编译
if (mvn clean package) {
    # 定义微服务项目的 JAR 包路径和命令
    $USER_SERVICE_JAR = "./user-service/target/user-service-0.0.1-SNAPSHOT.jar"
    $TASK_SERVICE_JAR = "./task-service/target/task-service-0.0.1-SNAPSHOT.jar"
    $GATEWAY_SERVICE_JAR = "./teamsphere-gateway/target/gateway-service-0.0.1-SNAPSHOT.jar"
    $SCHEDULE_SERVICE_JAR = "./schedule-service/target/schedule-service-0.0.1-SNAPSHOT.jar"
    $PROJECT_SERVICE_JAR = "./project-service/target/project-service-0.0.1-SNAPSHOT.jar"
    $MEETING_SERVICE_JAR = "./meeting-service/target/meeting-service-0.0.1-SNAPSHOT.jar"
    $CHAT_SERVICE_JAR = "./chat-service/target/chat-service-0.0.1-SNAPSHOT.jar"
    $FILE_SERVICE_JAR = "./file-service/target/file-service-0.0.1-SNAPSHOT.jar"

    # 启动微服务
    Start-Process java -ArgumentList "-jar", $USER_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $TASK_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $GATEWAY_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $SCHEDULE_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $PROJECT_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $MEETING_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $CHAT_SERVICE_JAR -NoNewWindow
    Start-Process java -ArgumentList "-jar", $FILE_SERVICE_JAR -NoNewWindow
}
else {
    Write-Host "compile failed"
}

