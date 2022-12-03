########build stage########
FROM maven:3.6.2-jdk-12 as maven_build

# To resolve dependencies in a safe way (no re-download when the source code changes)
COPY pom.xml /home/app/
RUN mvn -B -f /home/app/pom.xml dependency:go-offline

# To package the application
COPY src /home/app/src
WORKDIR /home/app
RUN mvn -B install  -Dmaven.test.skip



########run stage########
FROM maven:3.6.2-jdk-12
#Install Postgres
RUN yum install -y epel-release maven wget \
&& yum clean all \
&& yum install -y  https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm \
&& yum install -y postgresql11-server postgresql11-contrib

#Copy startup scripts
COPY dockerETC/dockerStartUpScript.sh /
COPY dockerETC/postgresStartUpScript.sh /

RUN chown root /postgresStartUpScript.sh \
&& chgrp root /postgresStartUpScript.sh \
&& chmod 777 /postgresStartUpScript.sh


COPY --from=maven_build /home/app/target/bank-service-0.0.1-SNAPSHOT.jar /home/app/target/bank-service-0.0.1-SNAPSHOT.jar


EXPOSE 9001
CMD ["bash", "dockerStartUpScript.sh"]