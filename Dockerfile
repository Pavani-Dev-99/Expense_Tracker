# Step 1: Use OpenJDK 22 base image
FROM openjdk:22-jdk-slim

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy Maven wrapper and pom.xml to leverage Docker cache
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Step 4: Download dependencies
RUN ./mvnw dependency:go-offline

# Step 5: Copy project source code
COPY src ./src

# Step 6: Build the project (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Step 7: Expose port 8080
EXPOSE 8080

# Step 8: Run the Spring Boot app
CMD ["java", "-jar", "target/Expense_Tracker-0.0.1-SNAPSHOT.jar"]
