###
# We choose the compile jar in host not in container, because it can use the host's performance.
###

FROM openjdk:17

# Set work directory as /app
WORKDIR /app

# Copy current all under directory into 'app' directory
COPY ./build/libs/omoidasu-api-*-SNAPSHOT.jar /app/omoidasu-api.jar

EXPOSE 8080

CMD ["java", "-Dspring.datasource.url=jdbc:postgresql://db:5432/omoidasu", "-Djdk.xml.entityExpansionLimit=0", "-jar", "/app/omoidasu-api.jar"]
