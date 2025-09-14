# Setup Guide

## Prerequisites

### Required Software
- **Java 17+** - OpenJDK or Oracle JDK
- **Maven 3.6+** - Build tool
- **Git** - Version control
- **IDE** - IntelliJ IDEA, Eclipse, or VS Code

### Optional Tools
- **Postman** - API testing
- **H2 Console** - Database inspection
- **Browser Developer Tools** - Frontend debugging

## Installation Steps

### 1. Clone Repository
```bash
git clone <repository-url>
cd ddd-approach-java
```

### 2. Verify Java Installation
```bash
java -version
# Should show Java 17 or higher
```

### 3. Verify Maven Installation
```bash
mvn -version
# Should show Maven 3.6 or higher
```

### 4. Build Project
```bash
mvn clean compile
```

### 5. Run Tests (Optional)
```bash
mvn test
```

### 6. Start Application
```bash
mvn spring-boot:run
```

**Alternative**: Run from IDE
- Import as Maven project
- Run `DddApproachApplication.java`

## Verification

### 1. Check Application Status
- Open browser: `http://localhost:8080`
- Should see the profile creation form

### 2. Test API Endpoints
```bash
# Health check (if implemented)
curl http://localhost:8080/actuator/health

# Create profile
curl -X POST http://localhost:8080/api/profiles \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"name":"Test User","email":"test@example.com","password":"password"}'
```

### 3. Access H2 Console (Optional)
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Configuration

### Application Properties
File: `src/main/resources/application.properties`

```properties
# Server configuration
server.port=8080

# H2 Database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console (development only)
spring.h2.console.enabled=true
```

### Environment Variables
```bash
# Optional: Override default port
export SERVER_PORT=8081

# Optional: Database configuration
export SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
```

## Development Setup

### IDE Configuration

#### IntelliJ IDEA
1. Import project as Maven project
2. Set Project SDK to Java 17+
3. Enable annotation processing
4. Install Spring Boot plugin (optional)

#### Eclipse
1. Import → Existing Maven Projects
2. Set Java Build Path to Java 17+
3. Install Spring Tools Suite (optional)

#### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Open project folder

### Hot Reload (Development)
Add to `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

## Troubleshooting

### Common Issues

#### Port Already in Use
```bash
# Find process using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Mac/Linux

# Kill process or change port
export SERVER_PORT=8081
```

#### Java Version Issues
```bash
# Check Java version
java -version

# Set JAVA_HOME (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk-17

# Set JAVA_HOME (Mac/Linux)
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
```

#### Maven Build Failures
```bash
# Clean and rebuild
mvn clean install

# Skip tests if failing
mvn clean install -DskipTests

# Update dependencies
mvn dependency:resolve
```

#### Database Connection Issues
- Check H2 console at `http://localhost:8080/h2-console`
- Verify JDBC URL: `jdbc:h2:mem:testdb`
- Ensure `spring.h2.console.enabled=true`

### Logging Configuration
Add to `application.properties`:
```properties
# Enable debug logging
logging.level.com.kvn.ddd=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## Production Deployment

### Build JAR
```bash
mvn clean package
java -jar target/ddd-approach-0.0.1-SNAPSHOT.jar
```

### Docker (Future Enhancement)
```dockerfile
FROM openjdk:17-jre-slim
COPY target/ddd-approach-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment-Specific Profiles
- `application-dev.properties`
- `application-prod.properties`
- `application-test.properties`

Run with profile:
```bash
java -jar app.jar --spring.profiles.active=prod
```