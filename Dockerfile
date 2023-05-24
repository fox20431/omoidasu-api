FROM openjdk:17

# Set work directory as /app
WORKDIR /app

ARG VERSION
ENV VERSION=$VERSION

# Copy current all under directory into 'app' directory
COPY build/libs/omoidasu-api-$VERSION.jar /app/

EXPOSE 8080

CMD ["java",  "-Dspring.profiles.active=prod", "-Djdk.xml.entityExpansionLimit=0", "-jar", "/app/omoidasu-api-$VERSION.jar"]
