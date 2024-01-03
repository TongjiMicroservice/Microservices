USER_SERVICE_JAR="./user-service/target/user-service-0.0.1-SNAPSHOT.jar"
TASK_SERVICE_JAR="./task-service/target/task-service-0.0.1-SNAPSHOT.jar"
GATEWAY_SERVICE_JAR="./teamsphere-gateway/target/gateway-service-0.0.1-SNAPSHOT.jar"
SCHEDULE_SERVICE_JAR="./schedule-service/target/schedule-service-0.0.1-SNAPSHOT.jar"
PROJECT_SERVICE_JAR="./project-service/target/project-service-0.0.1-SNAPSHOT.jar"
MEETING_SERVICE_JAR="./meeting-service/target/meeting-service-0.0.1-SNAPSHOT.jar"
CHAT_SERVICE_JAR="./chat-service/target/chat-service-0.0.1-SNAPSHOT.jar"
FILE_SERVICE_JAR="./file-service/target/file-service-0.0.1-SNAPSHOT.jar"

java -jar $USER_SERVICE_JAR &
java -jar $TASK_SERVICE_JAR &
java -jar $GATEWAY_SERVICE_JAR &
java -jar $SCHEDULE_SERVICE_JAR &
java -jar $PROJECT_SERVICE_JAR &
java -jar $MEETING_SERVICE_JAR &
java -jar $CHAT_SERVICE_JAR &
java -jar $FILE_SERVICE_JAR &
