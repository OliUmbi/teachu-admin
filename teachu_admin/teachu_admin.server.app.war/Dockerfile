FROM tomcat:9.0.65-jdk11 AS teachu-admin-server

COPY target/ch.teachu.teachu_admin.server.war /usr/local/tomcat/webapps/teachu-admin-server.war

EXPOSE 8080

CMD [ "catalina.sh", "run" ]
