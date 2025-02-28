plugins {
    id("org.springframework.boot") version "3.1.4" // Замените на актуальную версию
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}
group = "org.contacts"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-security")
    implementation ("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation ("org.springframework.kafka:spring-kafka")
    implementation ("com.opencsv:opencsv:5.7.1")
    runtimeOnly ("org.postgresql:postgresql")
    implementation ("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
    compileOnly ("org.projectlombok:lombok")
    annotationProcessor ("org.projectlombok:lombok")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
