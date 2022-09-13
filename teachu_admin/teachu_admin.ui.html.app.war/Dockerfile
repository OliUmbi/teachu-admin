FROM tomcat:9.0.65-jdk11 AS teachu-admin

COPY target/ch.teachu.teachu_admin.ui.html.war /usr/local/tomcat/webapps/teachu-admin-ui.war

EXPOSE 8080

CMD [ "catalina.sh", "run" ]
