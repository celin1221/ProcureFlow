package com.procureflow.gateway.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais do cliente OAuth 2.0 (Client Credentials Flow)")
public record AuthRequest(
        @Schema(description = "Identificador do cliente (ex: procureflow-platform, alfa-client)", example = "procureflow-platform")
        @NotBlank(message = "O clientId é obrigatório")
        String clientId,

        @Schema(description = "Segredo compartilhado do cliente", example = "platform-secret-123")
        @NotBlank(message = "O clientSecret é obrigatório")
        String clientSecret
) {
}
