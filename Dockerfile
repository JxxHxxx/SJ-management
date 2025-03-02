FROM tomcat:10-jdk17
ARG JAR_FILE=build/libs/*.war
# Tomcat의 server.xml 파일을 수정하여 포트 변경
RUN sed -i 's/8080/8000/g' /usr/local/tomcat/conf/server.xml

COPY ${JAR_FILE} /usr/local/tomcat/webapps/manage.war
EXPOSE 8000
CMD ["catalina.sh", "run"]