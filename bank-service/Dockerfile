FROM maven:3.6.2-jdk-12 AS build


RUN yum install -y epel-release maven wget \
&& yum clean all \
&& yum install -y  https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm \
&& yum install -y postgresql11-server postgresql11-contrib

COPY startUpScript.sh /
RUN chown root /startUpScript.sh \
&& chgrp root /startUpScript.sh \
&& chmod 777 /startUpScript.sh


COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

EXPOSE 9001
CMD ["/bin/bash","-c","/startUpScript.sh && tail -f /dev/null"]
CMD ["java","-jar","/home/app/target/bank-service-0.0.1-SNAPSHOT.jar"]