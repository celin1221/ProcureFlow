# ==============================================================================
# Multi-stage Dockerfile para ProcureFlow Purchase Order Integration Gateway
# Java 21 LTS - Build e Runtime Otimizados
# ==============================================================================

# ------------------------------------------------------------------------------
# 1. Estágio de Build
# ------------------------------------------------------------------------------
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /build

# Cache de dependências Maven aproveitando camadas do Docker
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Compilação e empacotamento da aplicação
COPY src ./src
COPY sample-data ./sample-data
RUN ./mvnw clean package -DskipTests -B

# ------------------------------------------------------------------------------
# 2. Estágio de Runtime (Produção Enxuta e Segura)
# ------------------------------------------------------------------------------
FROM eclipse-temurin:21-jre-jammy AS runner

WORKDIR /app

# Instala curl para healthcheck
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

# Usuário de sistema sem privilégios para segurança
RUN groupadd -r procureflow && useradd -r -g procureflow -d /app -s /sbin/nologin procureflow

# Copia artefato compilado e arquivos de dados de exemplo
COPY --from=builder /build/target/*.jar app.jar
COPY sample-data ./sample-data

# Ajusta permissões
RUN chown -R procureflow:procureflow /app

USER procureflow

EXPOSE 8080

# Parâmetros otimizados de memória da JVM para contêineres
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
