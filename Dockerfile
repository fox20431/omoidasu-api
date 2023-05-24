FROM openjdk:17

# Set work directory as /app
WORKDIR /app

ARG VERSION
ARG DB_USERNAME
ARG DB_PASSWORD
ENV VERSION=$VERSION
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD

# Copy current all under directory into 'app' directory
COPY build/libs/omoidasu-api-$VERSION.jar /app/omoidasu-api-$VERSION.jar

EXPOSE 8080

CMD ["sh", "-c", "java -Dspring.profiles.active=prod -Dspring.datasource.username=$DB_USERNAME -Dspring.datasource.password=$DB_PASSWORD -Djdk.xml.entityExpansionLimit=0 -jar /app/omoidasu-api-$VERSION.jar"]
