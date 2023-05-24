plugins {
    id("java")
    // spring boot plugin with the latest version
    id("org.springframework.boot") version "3.1.0"
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
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-devtools")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
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

// tasks.register("printVersion") {
//     doLast {
//         val version: String by project
//         println("$version")
//     }
// }