FROM openjdk:17

# Set work directory as /app
WORKDIR /app

ARG VERSION
ENV VERSION=$VERSION

# Copy current all under directory into 'app' directory
COPY build/libs/omoidasu-api-$VERSION.jar /app/

EXPOSE 8080

CMD ["java", "-Dspring.datasource.url=jdbc:postgresql://db:5432/omoidasu", "-Djdk.xml.entityExpansionLimit=0", "-jar", "/app/omoidasu-api-${echo $VERSION}.jar"]
