FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE}  naming-server-0.0.1-SNAPSHOT.jar
ADD https://github.com/aws-observability/aws-otel-java-instrumentation/releases/download/v1.19.0/aws-opentelemetry-agent.jar ./aws-opentelemetry-agent.jar
ENV JAVA_TOOL_OPTIONS "-javaagent:aws-opentelemetry-agent.jar"
# OpenTelemetry agent configuration
ENV OTEL_TRACES_SAMPLER "always_on"
ENV OTEL_RESOURCE_ATTRIBUTES "service.name=naming-server"
ENV OTEL_TRACES_EXPORTER "otlp"
ENTRYPOINT ["java","-jar","naming-server-0.0.1-SNAPSHOT.jar"]
