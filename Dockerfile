# # Step 1: Use OpenJDK 22 base image
# FROM openjdk:21-jdk-slim

# # Step 2: Set working directory
# WORKDIR /app

# # Step 3: Copy Maven wrapper and pom.xml to leverage Docker cache
# COPY mvnw pom.xml ./
# COPY .mvn .mvn

# # Step 4: Make mvnw executable inside Docker container
# RUN chmod +x mvnw

# # Step 5: Download dependencies
# RUN ./mvnw dependency:go-offline

# # Step 6: Copy project source code
# COPY src ./src

# # Step 7: Build the project (skip tests for faster build)
# RUN ./mvnw clean package -DskipTests

# # Step 8: Expose dynamic port (Railway sets $PORT)
# EXPOSE 8080

# # Step 9: Run the Spring Boot app
# CMD ["java", "-jar", "target/Expense-Tracker-0.0.1-SNAPSHOT.jar"]


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/Expense-Tracker-0.0.1-SNAPSHOT.jar expense-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "expense-tracker.jar"]
