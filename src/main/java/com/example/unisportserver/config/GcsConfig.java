// src/main/java/com/example/unisportserver/config/GcsConfig.java
package com.example.unisportserver.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class GcsConfig {

    @Value("${gcs.project-id}")
    private String projectId;

    // 서비스계정 키 JSON 전체를 넣은 환경변수
    @Value("${GCP_SA_KEY:}")
    private String gcpSaKeyJson;

    @Bean
    public Storage storage() throws Exception {
        StorageOptions.Builder builder = StorageOptions.newBuilder().setProjectId(projectId);

        GoogleCredentials credentials;
        if (gcpSaKeyJson != null && !gcpSaKeyJson.isBlank()) {
            // PowerShell 등에서 \n이 실제 줄바꿈으로 안 들어간 경우 대비
            String normalized = gcpSaKeyJson.replace("\\n", "\n");
            credentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(normalized.getBytes(StandardCharsets.UTF_8)));
        } else {
            // GCP 런타임(Cloud Run, GCE, GKE 등)에서는 ADC 사용
            credentials = GoogleCredentials.getApplicationDefault();
        }

        return builder.setCredentials(credentials).build().getService();
    }
}
