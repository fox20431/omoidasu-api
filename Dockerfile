FROM openjdk:17

# Set work directory as /app
WORKDIR /app

ARG VERSION
ARG DB_USERNAME
ARG DB_PASSWORD
ARG DB_NAME

ENV VERSION=$VERSION
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD
ENV DB_NAME=$DB_NAME

# Copy current all under directory into 'app' directory
COPY build/libs/omoidasu-api-$VERSION.jar /app/omoidasu-api-$VERSION.jar

EXPOSE 8080

CMD ["sh", "-c", "java \
    -Dspring.profiles.active=prod \
    -Dspring.datasource.username=$DB_USERNAME \
    -Dspring.datasource.password=$DB_PASSWORD \
    -Dspring.datasource.url=$DB \
    -Djdk.xml.entityExpansionLimit=jdbc:postgresql://db:5432/$DB_NAME \
    -jar /app/omoidasu-api-$VERSION.jar"]
