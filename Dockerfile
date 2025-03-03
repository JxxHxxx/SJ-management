# 사용할 이미지(도커헙에서 검색하면 나옴)
FROM tomcat:10-jdk17
# 빌드 단계에서 사용할 변수 선언
ARG BUILD_FILE=build/libs/*.war
# Tomcat server.xml 8080 -> 8000 포트로 수정(요 애플리케이션은 8000 포트를 사용), 야매 방법
RUN sed -i 's/8080/8000/g' /usr/local/tomcat/conf/server.xml
# 로컬에서 빌드한 war 파일을 컨테이너 내부 경로에 복사
COPY ${BUILD_FILE} /usr/local/tomcat/webapps/manage.war
# 리스닝할 포트 선언
EXPOSE 8000
# 한줄 요약 어려움, https://docs.docker.com/reference/dockerfile/#cmd 참고
CMD ["catalina.sh", "run"]