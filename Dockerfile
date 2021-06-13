FROM amazoncorretto:11-alpine-jdk
RUN mkdir -p /home/stm/schemasPool/
RUN mkdir -p /home/stm/invalid/
RUN mkdir -p /home/stm/js/
RUN mkdir -p /home/stm/pages/
RUN mkdir -p /home/stm/css/
RUN touch /home/stm/favicon.ico
RUN chmod -R 777 /home/stm/
RUN apk add postgresql
RUN mkdir /run/postgresql
RUN chown postgres:postgres /run/postgresql
USER postgres
RUN cd && pwd
RUN mkdir /var/lib/postgresql/data
RUN chmod 0700 /var/lib/postgresql/data
RUN initdb -D /var/lib/postgresql/data
RUN pg_ctl start -D /var/lib/postgresql/data &&\
    psql -U postgres -c "alter user postgres with password 'root';" &&\
    psql -U postgres -c "create database stm;"
COPY target/taskManager-2.5.0.jar stm.jar
# TODO: try start app
CMD pg_ctl start -D /var/lib/postgresql/data && java -jar stm.jar
EXPOSE 2000
