plugins {
    id("java")
    // spring boot plugin with the latest version
    id("org.springframework.boot") version "3.0.6"
    // spring dependency manage
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "com.hihusky.omoidasu"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // spring boot
    implementation("org.springframework.boot:spring-boot-starter")
    // spring boot test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // spring web
    implementation("org.springframework.boot:spring-boot-starter-web")
    // spring jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // postgresql driver
    implementation("org.postgresql:postgresql:42.5.4")
    // jaxb
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    // jaxb runtime
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.2")
    implementation("jakarta.inject:jakarta.inject-api:2.0.1")
    // swagger
    // implementation("io.springfox:springfox-swagger2:3.0.0")
    // implementation("io.springfox:springfox-swagger-ui:3.0.0")
    // javax.servlet
    // compileOnly("javax.servlet:javax.servlet-api:4.0.1")

    // https://mvnrepository.com/artifact/org.javassist/javassist
    implementation("org.javassist:javassist:3.29.2-GA")

    // lombok
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    testCompileOnly("org.projectlombok:lombok:1.18.26")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.26")

    // junit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}