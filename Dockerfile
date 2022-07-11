FROM alpine:latest
EXPOSE 8888
RUN apk add openjdk11
COPY /target/test-task-for-the-inside-0.1.0.jar /test-task-for-the-inside.jar

ENTRYPOINT ["java", "-jar", "/test-task-for-the-inside.jar"]

#docker build -t test-task-for-the-inside .
#docker run -it -p 8888:8888 test-task-for-the-inside

#docker tag test-task-for-the-inside rujfyls/test-task-for-the-inside:0.1.0
#docker push rujfyls/test-task-for-the-inside:0.1.0

